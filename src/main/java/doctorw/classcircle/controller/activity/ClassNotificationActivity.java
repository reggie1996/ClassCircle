package doctorw.classcircle.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import doctorw.classcircle.R;
import doctorw.classcircle.controller.adapter.NotiAdapter;
import doctorw.classcircle.model.bean.Noti;
import doctorw.classcircle.model.http.Datas;
import doctorw.classcircle.utils.Constants;
import doctorw.classcircle.utils.DatasUtil;
import doctorw.classcircle.utils.SpUtils;
import doctorw.classcircle.utils.Urls;
import okhttp3.Call;
import okhttp3.Response;

public class ClassNotificationActivity extends BaseActivity {


    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.lv_noti)
    ListView lvNoti;
     @Bind(R.id.iv_edit)
    ImageView ivEdit;

    private NotiAdapter notiAdapter;
    private Context mContext;

    private List<Noti> notis;

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_notification);
        mContext = this;
    }


    @Override
    protected void initListener() {

        //显示具体通知
        lvNoti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Noti noti = notis.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("noti", noti);
                Intent intent = new Intent(mContext, NotiDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
//                intent.putExtras(bundle);
//                startActivityForResult(intent,100);
            }
        });
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

        if (SpUtils.getInstance().getString("type","").equals(Constants.TYPE_PARENT)){
            ivEdit.setVisibility(View.GONE);
        }


        notis = new ArrayList<>();
        notis = DatasUtil.initNoti();
        loadNotis();

//        notiAdapter = new NotiAdapter(mContext, notis, R.layout.item_noti);
//        lvNoti.setAdapter(notiAdapter);
    }

    private void loadNotis() {

        HashMap<String, String> params = new HashMap<>();
//        params.put("groupid", SpUtils.getInstance().getString("groupid", ""));
        params.put("classNo","001");

        Datas.loadNotis(Urls.URL_GETNOTI, mContext, params, new Datas.HandleResponse() {
            @Override
            public void handleResponse(String s, Call call, Response response) {
                notis = JSON.parseArray(s,Noti.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        notiAdapter = new NotiAdapter(mContext, notis, R.layout.item_noti);
                        lvNoti.setAdapter(notiAdapter);
                    }
                });
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_edit, R.id.iv_back, R.id.tv_title2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_edit:
                //去到编辑页面
                Intent intent = new Intent(this, WriteNotiActivity.class);
                startActivity(intent);
//                startActivityForResult(intent, 101);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    //未使用
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case 101://班级页面
//                //返回新的通知，刷新数据
//                Bundle bundle = data.getExtras();
//                break;
//            case 100://查看详情页面，判断是否删除，刷新页面
//                //返回ID删除
//                break;
//        }
//    }


    @Override
    protected void onResume() {
        super.onResume();
        loadNotis();
    }
}

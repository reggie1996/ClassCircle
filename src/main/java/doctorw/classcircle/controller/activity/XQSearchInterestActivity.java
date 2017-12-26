package doctorw.classcircle.controller.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import doctorw.classcircle.R;
import doctorw.classcircle.controller.adapter.CircleItemAdapter;
import doctorw.classcircle.model.bean.CircleDetail;
import doctorw.classcircle.utils.SpUtils;
import doctorw.classcircle.utils.Urls;
import okhttp3.Call;
import okhttp3.Response;

public class XQSearchInterestActivity extends BaseActivity {

    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.lv_search)
    ListView lvSearch;
    @Bind(R.id.tip1)
    TextView tip1;
    @Bind(R.id.tip2)
    TextView tip2;

    private Context mContext;
    private List<CircleDetail> circleDetails = new ArrayList<>();
    private CircleItemAdapter circleItemAdapter;
    //实例化adapter监听器
    private CircleItemAdapter.OnAddCircleListener mOnAddCircleListener = new CircleItemAdapter.OnAddCircleListener() {
        @Override
        public void OnAddCircleItem(final int position) {
            //加入圈子
//            showToast(DatasUtil.initCircle().get(position).getCircleId());
            //提示加入圈子成功
            OkGo.post(Urls.URL_JOININTEREST)
                    .params("userNo", SpUtils.getInstance().getString("userid", ""))
                    .params("id", circleDetails.get(position).getCircleId())
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            //跳转到相应的圈子界面
                            //将信息打包写到下一个活动
                            CircleDetail circleDetail = circleDetails.get(position);
                            Bundle bundle = new Bundle();
                            bundle.putString("focus", "YES");
                            bundle.putParcelable("circleDetail", circleDetail);
                            startActivity(XQCircleActivity.class, bundle);
                        }
                    });
        }

        @Override
        public void OnEnterItem(int position) {
            CircleDetail circleDetail = circleDetails.get(position);
            Bundle bundle = new Bundle();
            bundle.putParcelable("circleDetail", circleDetail);
            bundle.putString("focus", "NO");
            startActivity(XQCircleActivity.class, bundle);
        }

    };

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_xqsearch_interest);
        mContext = this;
    }


    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        tip1.setVisibility(View.VISIBLE);


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_cancel, R.id.bt_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_cancel:
                finish();
                break;
            case R.id.bt_search:
                String key = etSearch.getText().toString();
                OkGo.post(Urls.URL_SEARCHINTEREST)
                        .params("name", key)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                tip1.setVisibility(View.GONE);
                                circleDetails = JSON.parseArray(s, CircleDetail.class);
                                circleItemAdapter = new CircleItemAdapter(mContext, circleDetails, mOnAddCircleListener);
                                lvSearch.setAdapter(circleItemAdapter);
                                if (circleDetails.size() == 0 || circleDetails == null) {
                                   tip2.setVisibility(View.VISIBLE);
                                }else{
                                    tip2.setVisibility(View.GONE);
                                }
                            }
                        });
                break;
        }
    }
}

package doctorw.classcircle.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import doctorw.classcircle.R;
import doctorw.classcircle.model.bean.Noti;
import doctorw.classcircle.utils.Constants;
import doctorw.classcircle.utils.SpUtils;
import doctorw.classcircle.utils.Urls;
import okhttp3.Call;
import okhttp3.Response;

public class NotiDetailActivity extends BaseActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_context)
    TextView tvContext;
     @Bind(R.id.tv_delete)
    TextView tvDelete;
 @Bind(R.id.tv_name)
    TextView tvName;

    private Context mContext;
    private Noti noti;

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_noti_detail);
        mContext = this;
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        noti = (Noti) bundle.getSerializable("noti");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        tvTitle.setText(noti.getTitle());
        tvContext.setText(noti.getContext());
        tvTime.setText(noti.getTime());
        tvName.setText(noti.getName());
    }

    @Override
    protected void initView() {
        if(SpUtils.getInstance().getString("type","").equals(Constants.TYPE_PARENT)){
            tvDelete.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back, R.id.tv_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_delete:

                deleteNoti();
                break;
        }
    }

    //通过通知ID删除通知
    private void deleteNoti() {
        OkGo.post(Urls.URL_DELNOTI)
                .tag(this)
                .params("id", noti.getNotiId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showToast("删除成功");
                                finish();
                            }
                        });
                    }
                });
    }
}

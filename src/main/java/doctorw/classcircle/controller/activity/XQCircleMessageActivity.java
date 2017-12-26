package doctorw.classcircle.controller.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import doctorw.classcircle.R;
import doctorw.classcircle.model.bean.CircleDetail;
import doctorw.classcircle.utils.SpUtils;
import doctorw.classcircle.utils.Urls;
import okhttp3.Call;
import okhttp3.Response;

public class XQCircleMessageActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.iv_head)
    ImageView ivHead;
    @Bind(R.id.tv_cirname)
    TextView tvCirname;
    @Bind(R.id.tv_pernum)
    TextView tvPernum;
    @Bind(R.id.bt_addcir)
    Button btAddcir;
    @Bind(R.id.tv_desc)
    TextView tvDesc;
    @Bind(R.id.tv_exit)
    TextView tvExit;
    private CircleDetail circleDetail;

    private Context mContaxt;

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_circle_message);
        mContaxt = this;
        Bundle bundle = getIntent().getExtras();
        circleDetail = bundle.getParcelable("circleDetail");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        btAddcir.setVisibility(View.GONE);
        tvCirname.setText(circleDetail.getCirName());
        tvDesc.setText(circleDetail.getDesc());
        tvPernum.setText(circleDetail.getPerNum());

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private long time = 0;

    @OnClick({R.id.iv_back, R.id.tv_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_exit:
                if (System.currentTimeMillis() - time > 2000) {//获得当前的时间
                    time = System.currentTimeMillis();
                    showToast("再点击一次退出圈子");
                } else {//点击在两秒以内
                    OkGo.post(Urls.URL_LISTINTERESTCIRCLE)
                            .params("userNo", SpUtils.getInstance().getString("userid", ""))
                            .params("id", circleDetail.getCircleId())
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    showToast("成功退出");
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            tvExit.setText("加入圈子");
                                        }
                                    });
                                }
                            });

                }
                break;
        }
    }
}

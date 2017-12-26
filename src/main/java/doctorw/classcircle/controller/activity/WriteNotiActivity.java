package doctorw.classcircle.controller.activity;

import android.os.Bundle;
import android.view.View;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;
import doctorw.classcircle.R;
import doctorw.classcircle.utils.Urls;
import doctorw.classcircle.view.YEditText;
import okhttp3.Call;
import okhttp3.Response;


public class WriteNotiActivity extends BaseActivity {


    @Bind(R.id.et_title)
    YEditText etTitle;
    @Bind(R.id.et_context)
    YEditText etContext;

    private String title;
    private String context;

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_write_noti);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {


    }

    @Override
    protected void initView() {


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @OnClick({R.id.iv_back, R.id.tv_pulish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_pulish:
                showToast("sadfsad");
                title = getText(etTitle);
                context = getText(etContext);
                if (!title.isEmpty() && !context.isEmpty()) {
                    publishNoti(title, context);
                } else {
                    showToast("不能为空");
                }

                break;
        }
    }

    private void publishNoti(String mtitle, String mcontext) {
        HashMap<String, String> params = new HashMap<>();
        showToast(mtitle+mcontext+"");
        params.put("title", mtitle);
//        params.put("teacherNo", SpUtils.getInstance().getString("userid", ""));
        params.put("teacherNo","T2014");
        params.put("content", mcontext);

        OkGo.post(Urls.URL_PUBLISHNOTI)
                .tag(this)
                .params(params)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        showToast("发布成功");
                        finish();

                    }
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        showToast(e.toString());
                    }
                });

    }
}

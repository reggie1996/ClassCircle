package doctorw.classcircle.controller.activity;

import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import doctorw.classcircle.R;
import doctorw.classcircle.utils.Constants;

public class ChooseActivity extends BaseActivity {


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
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_choose);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.choose_teacher, R.id.choose_parent})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.choose_teacher:
                Bundle bundle1 = new Bundle();
                bundle1.putString("type", Constants.TYPE_TEACHER);
                showToast(Constants.TYPE_TEACHER);
                startActivity(RegisterActivity.class, bundle1);
                removeActivity();
                break;
            case R.id.choose_parent:
                Bundle bundle2 = new Bundle();
                bundle2.putString("type",Constants.TYPE_PARENT);
                showToast(Constants.TYPE_PARENT);
                startActivity(RegisterActivity.class, bundle2);
                removeActivity();
                break;
        }
    }
}

package doctorw.classcircle.controller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import doctorw.classcircle.R;
import doctorw.classcircle.controller.activity.StudyWebActivity;


public class Study2Fragment extends BaseFragment {

    private static final String URL_STUDY1 = "http://www.miaobi100.com/article/article";
    private static final String URL_STUDY2 = "http://m.zuoyetong.com.cn/tk?partner=zyt";
    private static final String URL_STUDY3 = "http://www.jiesen365.com/";
    private static final String URL_STUDY6 = "http://www.leleketang.com/cr/";
    private static final String URL_STUDY5 = "http://www.leleketang.com/zidian/c.php";
    private static final String URL_STUDY4 = "http://www.leleketang.com/chengyu/jielong.php";
    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_study2, container, false);
        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.iv_study1, R.id.iv_study2, R.id.iv_study3, R.id.iv_study4, R.id.iv_study5, R.id.iv_study6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_study1:
                Bundle bundle1 = new Bundle();
                bundle1.putString("weburl",URL_STUDY1);
                startActivity(StudyWebActivity.class,bundle1);
                break;
            case R.id.iv_study2:
                Bundle bundle2 = new Bundle();
                bundle2.putString("weburl",URL_STUDY2);
                startActivity(StudyWebActivity.class,bundle2);
                break;
            case R.id.iv_study3:
                Bundle bundle3 = new Bundle();
                bundle3.putString("weburl",URL_STUDY3);
                startActivity(StudyWebActivity.class,bundle3);
                showToast("study3");
                break;
            case R.id.iv_study4:
                Bundle bundle4 = new Bundle();
                bundle4.putString("weburl",URL_STUDY4);
                startActivity(StudyWebActivity.class,bundle4);
                showToast("study4");
                break;
            case R.id.iv_study5:
                Bundle bundle5 = new Bundle();
                bundle5.putString("weburl",URL_STUDY5);
                startActivity(StudyWebActivity.class,bundle5);
                showToast("study5");
                break;
            case R.id.iv_study6:
                Bundle bundle6 = new Bundle();
                bundle6.putString("weburl",URL_STUDY6);
                startActivity(StudyWebActivity.class,bundle6);
                showToast("study6");
                break;
        }
    }
}

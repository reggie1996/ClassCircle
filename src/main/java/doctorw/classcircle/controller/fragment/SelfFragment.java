package doctorw.classcircle.controller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import doctorw.classcircle.R;
import doctorw.classcircle.controller.activity.ClassDongTai2Activity;
import doctorw.classcircle.controller.activity.ClassNotificationActivity;
import doctorw.classcircle.controller.activity.GroupDetailActivity;
import doctorw.classcircle.controller.activity.MyPhotosActivity;
import doctorw.classcircle.controller.activity.SelfInfoActivity;
import doctorw.classcircle.controller.activity.SettingsActivity;
import doctorw.classcircle.controller.activity.ShouCangActivity;
import doctorw.classcircle.controller.activity.TestActivity2;
import doctorw.classcircle.utils.CommonUtil;
import doctorw.classcircle.utils.Constants;
import doctorw.classcircle.utils.SpUtils;


public class SelfFragment extends BaseFragment {


    private static final String TAG = SelfFragment.class.getSimpleName();//"CommonFragment"
    @Bind(R.id.title_change)
    TextView titleChange;
    @Bind(R.id.circleImageView)
    CircleImageView circleImageView;
    @Bind(R.id.testActivity)
    LinearLayout testActivity;
    @Bind(R.id.tv_name)
    TextView tvName;

    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fg_self, container, false);
        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    protected void initView() {
        // 初始化头像
        CommonUtil.initTouxiang(circleImageView);
        tvName.setText(SpUtils.getInstance().getString("myname","宵夜"));

    }

    @Override
    protected void initData() {
        titleChange.setText("我");
    }

    @Override
    protected void initListener() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 100:
                CommonUtil.initTouxiang(circleImageView);
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.ll_user_info, R.id.testActivity, R.id.ll_message, R.id.ll_myclass, R.id.ll_dongtai, R.id.ll_shoucang, R.id.ll_pics, R.id.ll_setting, R.id.ll_invite})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_user_info:
                Intent intent = new Intent(getActivity(), SelfInfoActivity.class);
                startActivityForResult(intent, 100);
                break;
            case R.id.testActivity:
                startActivity(TestActivity2.class);
                break;
            case R.id.ll_message:
                startActivity(ClassNotificationActivity.class);
                break;
            case R.id.ll_myclass:
                //显示班级主页信息
                Intent intent1 = new Intent(mContext, GroupDetailActivity.class);
                // 群id
                intent1.putExtra(Constants.GROUP_ID, SpUtils.getInstance().getString("groupid", ""));
                startActivity(intent1);
                break;
            case R.id.ll_dongtai:
                //不需要做
                startActivity(ClassDongTai2Activity.class);
                break;
            case R.id.ll_shoucang:
                //需要做
                startActivity(ShouCangActivity.class);
                break;
            case R.id.ll_pics:
                //进入后分局用户ID获取
                //需要做
                startActivity(MyPhotosActivity.class);
                break;
            case R.id.ll_setting:
                //做
                startActivity(SettingsActivity.class);
                break;
            case R.id.ll_invite:
                //分享功能，分行啊到QQ，微信，微博
                break;
        }
    }
}

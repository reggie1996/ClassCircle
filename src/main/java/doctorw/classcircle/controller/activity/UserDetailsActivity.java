package doctorw.classcircle.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.EaseConstant;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import doctorw.classcircle.R;
import doctorw.classcircle.utils.CommonUtil;

public class UserDetailsActivity extends BaseActivity {


    @Bind(R.id.iv_headpic)
    ImageView ivHeadpic;
    @Bind(R.id.ci_userhead)
    CircleImageView ciUserhead;
    @Bind(R.id.tv_username)
    TextView tvUsername;
    @Bind(R.id.tv_user_ident)
    TextView tvUserIdent;
    @Bind(R.id.user_desc)
    TextView userDesc;

    private String username;

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_details);
        Bundle bundle = getIntent().getExtras();
        username = (String) bundle.get("username");


    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        CommonUtil.initTouxiang(ciUserhead);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_headpic, R.id.ci_userhead, R.id.ll_back, R.id.bt_chat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_headpic:
                showToast("点击更换封面");
                break;
            case R.id.ci_userhead:

                break;
            case R.id.ll_back:
                finish();
                break;
            case R.id.bt_chat:
                Intent intent = new Intent(UserDetailsActivity.this, ChatActivity.class);
                // 传递参数
                intent.putExtra(EaseConstant.EXTRA_USER_ID,username);
                startActivity(intent);
                break;
        }
    }
}

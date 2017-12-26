package doctorw.classcircle.controller.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import doctorw.classcircle.R;
import doctorw.classcircle.model.Model;
import doctorw.classcircle.view.YEditText;

public class AddGroupActivity extends BaseActivity {

    @Bind(R.id.et_checkgroup_name)
    YEditText etCheckgroupName;

    @Bind(R.id.et_addreason)
    YEditText ecAddReason;

    private EMGroup group;

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_group);
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
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back1, R.id.bt_checkgroup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back1:
                removeActivity();
                break;
            case R.id.bt_checkgroup:
                final String groupId = getText(etCheckgroupName);
                if (TextUtils.isEmpty(groupId)) {
                    showToast("不能为空");
                } else {
                    Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                        @Override
                        public void run() {
                            EMGroup group = null;
                            try {
                                group = EMClient.getInstance().groupManager().getGroupFromServer(groupId);
                            } catch (HyphenateException e) {
                                e.printStackTrace();
                            }
//                                EMClient.getInstance().groupManager().applyJoinToGroup(groupId, "adsfas");
                            showToast("groupId");
                            if (group == null) {
                                showToast("重新输入");
                            } else {
                                Bundle bundle = new Bundle();
                                final String reason = getText(ecAddReason);
                                bundle.putString("groupId", groupId);
                                bundle.putString("addreason", reason);
                                startActivity(GroupMessageActivity.class, bundle);
                                removeActivity();
                            }
                        }
                    });
//                        group = EMClient.getInstance().groupManager().getGroupFromServer(groupId);

                }
                break;
        }
    }

//    //判断是否存在
//    private Boolean isExist() {
//        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
//            @Override
//            public void run() {
//                try {//成功获取
//                    group = EMClient.getInstance().groupManager().getGroupFromServer(groupId);
//                    if (group == null) {
//                        group = EMClient.getInstance().groupManager().getGroupFromServer(groupId);
//                    }
////                    EMGroup group = EMClient.getInstance().groupManager().getGroup(getText(etCheckgroupName));
//                    if (group == null) {
//                        isExist = false;
//                    } else {
//                        isExist = true;
//                    }
//                } catch (HyphenateException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        return isExist;
//    }
}

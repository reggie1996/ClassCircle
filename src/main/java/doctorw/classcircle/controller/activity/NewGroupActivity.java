package doctorw.classcircle.controller.activity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.exceptions.HyphenateException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import doctorw.classcircle.R;
import doctorw.classcircle.model.Model;
import doctorw.classcircle.utils.SpUtils;
import doctorw.classcircle.utils.Urls;
import okhttp3.Call;
import okhttp3.Response;

import static doctorw.classcircle.R.id.cb_newgroup_invite;
import static doctorw.classcircle.R.id.cb_newgroup_public;
import static doctorw.classcircle.R.id.et_newgroup_desc;
import static doctorw.classcircle.R.id.et_newgroup_name;

public class NewGroupActivity extends BaseActivity {


    @Bind(et_newgroup_name)
    EditText etNewgroupName;
    @Bind(et_newgroup_desc)
    EditText etNewgroupDesc;
    @Bind(cb_newgroup_public)
    CheckBox cbNewgroupPublic;
    @Bind(cb_newgroup_invite)
    CheckBox cbNewgroupInvite;
    private List<EMGroup> mGroups = null;


    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_new_group);
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

    @OnClick(R.id.bt_newgroup_create)
    public void onViewClicked() {
        // 创建按钮的点击事件处理
        createGroup();
    }

    // 创建群
    private void createGroup() {
        // 群名称
        final String groupName = etNewgroupName.getText().toString();
        // 群描述
        final String groupDesc = etNewgroupDesc.getText().toString();

        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                // 去环信服务器创建群
                // 参数一：群名称；参数二：群描述；参数三：群成员；参数四：原因；参数五：参数设置
                EMGroupManager.EMGroupOptions options = new EMGroupManager.EMGroupOptions();

                options.maxUsers = 200;//群最多容纳多少人
                EMGroupManager.EMGroupStyle groupStyle = null;
                String[] members = new String[0];
                if (cbNewgroupPublic.isChecked()) {//公开
                    if (cbNewgroupInvite.isChecked()) {// 开放群邀请
                        groupStyle = EMGroupManager.EMGroupStyle.EMGroupStylePublicOpenJoin;
                    } else {
                        groupStyle = EMGroupManager.EMGroupStyle.EMGroupStylePublicJoinNeedApproval;
                    }
                } else {
                    if (cbNewgroupInvite.isChecked()) {// 开放群邀请
                        groupStyle = EMGroupManager.EMGroupStyle.EMGroupStylePrivateMemberCanInvite;
                    } else {
                        groupStyle = EMGroupManager.EMGroupStyle.EMGroupStylePrivateOnlyOwnerInvite;
                    }
                }
                options.style = groupStyle; // 创建群的类型
                try {
                    EMClient.getInstance().groupManager().createGroup(groupName, groupDesc, members, "申请加入群", options);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("创建群成功");
                                //需异步处理
                            try {
                                mGroups =  EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                            } catch (HyphenateException e) {
                                e.printStackTrace();
                            }
                            String groupId = mGroups.get(0).getGroupId();
                            HashMap<String ,String> params = new HashMap<>();
                            params.put("classNo",groupId);
                            params.put("className",groupName);
                            params.put("classDesc",groupDesc);
                            params.put("teacherNo ", SpUtils.getInstance().getString("UserId",""));

                            OkGo.post(Urls.URL_CREATECLASS)
                                    .tag(this)
                                    .params(params)
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {

                                        }
                                    });

                            // 结束当前页面
                            finish();
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           showToast("创建群失败");
                        }
                    });
                }
            }
        });
    }
}

package doctorw.classcircle.controller.activity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import doctorw.classcircle.R;
import doctorw.classcircle.model.Model;

import static doctorw.classcircle.utils.CommonUtil.getGroup;

public class GroupMessageActivity extends BaseActivity {

    @Bind(R.id.groupid)
    TextView groupid;
    @Bind(R.id.groupname)
    TextView groupname;
    @Bind(R.id.group_desc)
    TextView groupDesc;
    @Bind(R.id.bt_addgroup)
    Button btAddgroup;

    private String addreason;
    private String groupId;

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_group_message);
    }

    @Override
    protected void initListener() {


    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        //从intent对象中把封装好的数据取出来
        Bundle bundle = intent.getExtras();
        groupId = bundle.getString("groupId");
        addreason = bundle.getString("addreason");
        final EMGroup group = getGroup(groupId);
        if (group == null) {
            return;
        } else {
            groupid.setText(group.getGroupId());
            groupDesc.setText(group.getDescription());
            groupname.setText(group.getGroupName());
        }
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

    @OnClick(R.id.bt_addgroup)
    public void onViewClicked() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().groupManager().applyJoinToGroup(groupId, addreason);
                    showToast("发送请求成功,等待审核");
                    removeActivity();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        });
        removeActivity();
    }
}

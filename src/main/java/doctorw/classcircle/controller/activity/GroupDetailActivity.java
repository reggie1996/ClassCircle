package doctorw.classcircle.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import doctorw.classcircle.R;
import doctorw.classcircle.model.Model;
import doctorw.classcircle.utils.Constants;

public class GroupDetailActivity extends BaseActivity {

    @Bind(R.id.group_owner)
    TextView groupOwner;
    @Bind(R.id.group_desc)
    TextView groupDesc;
    @Bind(R.id.group_id)
    TextView groupId;
    @Bind(R.id.group_nickname)
    TextView groupNickname;
    @Bind(R.id.group_exit)
    TextView groupExit;
    @Bind(R.id.group_jiesan)
    TextView groupJiesan;
    private EMGroup mGroup;
    private String groupId_;

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_group_detail);
        Intent intent = getIntent();
        groupId_ = intent.getStringExtra(Constants.GROUP_ID);
        if (groupId_ == null) {
            return;
        } else {
            mGroup = EMClient.getInstance().groupManager().getGroup(groupId_);
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

        // 初始化button显示
        initDisplay();
        // 初始化gridview
//        initGridview();
        // 从环信服务器获取所有的群成员
//        getMembersFromHxServer();

    }

    private void getMembersFromHxServer() {
//        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    // 从环信服务器获取所有的群成员信息
//                    EMGroup emGroup = EMClient.getInstance().groupManager().getGroupFromServer(groupId_);
//                    List<String> members = emGroup.getMembers();
//
//                    if (members != null && members.size() >= 0) {
//                        mUsers = new ArrayList<UserInfo>();
//
//                        // 转换
//                        for (String member : members) {
//                            UserInfo userInfo = new UserInfo(member);
//                            mUsers.add(userInfo);
//                        }
//                    }
//
//                    // 更新页面
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            // 刷新适配器
//                            groupDetailAdapter.refresh(mUsers);
//                        }
//                    });
//                } catch (final HyphenateException e) {
//                    e.printStackTrace();
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(GroupDetailActivity.this, "获取群信息失败" + e.toString(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            }
//        });
    }

    private void initDisplay() {
        groupId.setText(groupId_);
        groupDesc.setText(mGroup.getDescription());
        groupOwner.setText(mGroup.getOwner());
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                // 判断当前用户是否是群主
                if (EMClient.getInstance().getCurrentUser().equals(mGroup.getOwner())) {// 群主
                    groupExit.setVisibility(View.GONE);
                    groupJiesan.setVisibility(View.VISIBLE);
                } else {
                    //退群
                    groupExit.setVisibility(View.VISIBLE);
                    groupJiesan.setVisibility(View.GONE);
                }
            }
        });

    }


    // 发送退群和解散群广播
    private void exitGroupBroatCast() {
        LocalBroadcastManager mLBM = LocalBroadcastManager.getInstance(GroupDetailActivity.this);
        Intent intent = new Intent(Constants.EXIT_GROUP);
        intent.putExtra(Constants.GROUP_ID, mGroup.getGroupId());
        mLBM.sendBroadcast(intent);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    private long time = 0;

    @OnClick({R.id.group_exit, R.id.group_jiesan, R.id.ll_allmembers})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.group_exit:
                Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // 告诉环信服务器退群
                            EMClient.getInstance().groupManager().leaveGroup(mGroup.getGroupId());
                            // 发送退群广播
                            exitGroupBroatCast();

                            // 更新页面
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(GroupDetailActivity.this, "退群成功", Toast.LENGTH_SHORT).show();


                                    finish();
                                }
                            });
                        } catch (final HyphenateException e) {
                            e.printStackTrace();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(GroupDetailActivity.this, "退群失败" + e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });


                break;
            case R.id.group_jiesan:
                //解散群

                if (System.currentTimeMillis() - time > 2000) {//获得当前的时间
                    time = System.currentTimeMillis();
                    showToast("再点击一次退出应用程序");
                } else {
                    Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                // 去环信服务器解散群
                                EMClient.getInstance().groupManager().destroyGroup(mGroup.getGroupId());
                                startActivity(MainActivity.class);
                                // 发送退群的广播
                                exitGroupBroatCast();
                                // 更新页面
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(GroupDetailActivity.this, "解散群成功", Toast.LENGTH_SHORT).show();
                                        // 结束当前页面
                                        startActivity(MainActivity.class);
                                        finish();
                                    }
                                });
                            } catch (final HyphenateException e) {
                                e.printStackTrace();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(GroupDetailActivity.this, "解散群失败" + e.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
                }
                break;

            case R.id.ll_allmembers:
                startActivity(AllMembersActivity.class);
                break;
        }
    }
}

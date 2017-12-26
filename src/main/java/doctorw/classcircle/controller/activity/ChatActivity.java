package doctorw.classcircle.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;

import doctorw.classcircle.R;
import doctorw.classcircle.utils.Constants;

// 会话详情页面
public class ChatActivity extends FragmentActivity {

    private String mHxid;
    private EaseChatFragment easeChatFragment;
    //    private LocalBroadcastManager mLBM;
    private int mChatType;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mContext = this;
        initData();
        initListener();
    }

    private void initData() {
        // 创建一个会话的fragment
        easeChatFragment = new EaseChatFragment();
        mHxid = getIntent().getStringExtra(EaseConstant.EXTRA_USER_ID);
        Log.d("test2", mHxid);
//
//        // 获取聊天类型
//        mChatType = getIntent().getExtras().getInt(EaseConstant.EXTRA_CHAT_TYPE);
        easeChatFragment.setArguments(getIntent().getExtras());
//        // 替换fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_chat, easeChatFragment).commit();
//        // 获取发送广播的管理者
//        mLBM = LocalBroadcastManager.getInstance(ChatActivity.this);
    }

    private void initListener() {
        easeChatFragment.setChatFragmentListener(new EaseChatFragment.EaseChatFragmentHelper() {
            @Override
            public void onSetMessageAttributes(EMMessage message) {

            }
            @Override
            public void onEnterToChatDetails() {
                Intent intent = new Intent(ChatActivity.this, GroupDetailActivity.class);
                // 群id
                intent.putExtra(Constants.GROUP_ID, mHxid);
                startActivity(intent);
            }

            //头像点击事件
            @Override
            public void onAvatarClick(String username) {
//                Toast.makeText(ChatActivity.this, username, Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(ChatActivity.this, ChatActivity.class);
//                // 传递参数
//                intent.putExtra(EaseConstant.EXTRA_USER_ID,username);
//                startActivity(intent);

                Intent intent = new Intent(mContext,UserDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("username",username);
                intent.putExtras(bundle);
                startActivity(intent);
//                startActivity(UserDetailsActivity.class);
            }

            @Override
            public void onAvatarLongClick(String username) {

            }

            @Override
            public boolean onMessageBubbleClick(EMMessage message) {
                return false;
            }

            @Override
            public void onMessageBubbleLongClick(EMMessage message) {

            }

            @Override
            public boolean onExtendMenuItemClick(int itemId, View view) {
                return false;
            }

            @Override
            public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
                return null;
            }
        });
        // 如果当前类型为群聊
//        if(mChatType == EaseConstant.CHATTYPE_GROUP) {
//            // 注册退群广播
//            BroadcastReceiver ExitGroupReceiver = new BroadcastReceiver() {
//                @Override
//                public void onReceive(Context context, Intent intent) {
//                    if(mHxid.equals(intent.getStringExtra(Constants.GROUP_ID))) {
//                        // 结束当前页面
//                        finish();
//                    }
//                }
//            };
//            mLBM.registerReceiver(ExitGroupReceiver, new IntentFilter(Constants.EXIT_GROUP));
//        }
    }


}

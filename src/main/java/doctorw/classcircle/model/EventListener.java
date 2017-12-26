package doctorw.classcircle.model;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.hyphenate.EMContactListener;
import com.hyphenate.EMGroupChangeListener;
import com.hyphenate.chat.EMClient;

import java.util.List;

import doctorw.classcircle.model.bean.GroupInfo;
import doctorw.classcircle.model.bean.InvationInfo;
import doctorw.classcircle.model.bean.UserInfo;
import doctorw.classcircle.utils.Constants;
import doctorw.classcircle.utils.SpUtils;


// 全局事件监听类
public class EventListener {

    private Context mContext;
    private final LocalBroadcastManager mLBM;

    public EventListener(Context mContext) {
        this.mContext = mContext;

        mLBM = LocalBroadcastManager.getInstance(mContext);
        EMClient.getInstance().groupManager().addGroupChangeListener(eMGroupChangeListener);
        //联系人变化的监听
        EMClient.getInstance().contactManager().setContactListener(emContactListener);
    }


    private final EMGroupChangeListener eMGroupChangeListener = new EMGroupChangeListener() {

        //收到 群邀请
        @Override
        public void onInvitationReceived(String groupId, String groupName, String inviter, String reason) {
            //数据更新 更新服务器信息
             //新的群邀请  InvationInfo.InvitationStatus.NEW_GROUP_APPLICATION
            //封装邀请信息
            // 数据更新
            InvationInfo invitationInfo = new InvationInfo();
            invitationInfo.setReason(reason);
            invitationInfo.setGroup(new GroupInfo(groupName, groupId, inviter));
            invitationInfo.setStatus(InvationInfo.InvitationStatus.NEW_GROUP_INVITE);
            Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invitationInfo);
            //红点处理
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, true);

            //发送广播
            mLBM.sendBroadcast(new Intent(Constants.GROUP_INVITE_CHANGED));
        }

        //收到 群申请通知
        @Override
        public void onApplicationReceived(String groupId, String groupName, String applicant, String reason) {
            // 数据更新
            InvationInfo invitationInfo = new InvationInfo();
            invitationInfo.setReason(reason);
            invitationInfo.setGroup(new GroupInfo(groupName, groupId, applicant));
            invitationInfo.setStatus(InvationInfo.InvitationStatus.NEW_GROUP_APPLICATION);
            Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invitationInfo);
            //红点处理
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, true);

            //发送广播
            mLBM.sendBroadcast(new Intent(Constants.GROUP_INVITE_CHANGED));

        }

        //收到 群申请被接受
        @Override
        public void onApplicationAccept(String groupId, String groupName, String accepter) {
            // 更新数据
            InvationInfo invitationInfo = new InvationInfo();
            invitationInfo.setGroup(new GroupInfo(groupName,groupId,accepter));
            invitationInfo.setStatus(InvationInfo.InvitationStatus.GROUP_APPLICATION_ACCEPTED);

            Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invitationInfo);

            //红点处理
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, true);
            //发送广播
            mLBM.sendBroadcast(new Intent(Constants.GROUP_INVITE_CHANGED));

        }

        //收到 群申请被拒绝
        @Override
        public void onApplicationDeclined(String groupId, String groupName, String decliner, String reason) {
            // 更新数据
            InvationInfo invitationInfo = new InvationInfo();
            invitationInfo.setReason(reason);
            invitationInfo.setGroup(new GroupInfo(groupName, groupId, decliner));
            invitationInfo.setStatus(InvationInfo.InvitationStatus.GROUP_APPLICATION_DECLINED);

            Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invitationInfo);

            //红点处理

            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, true);

            //发送广播
            mLBM.sendBroadcast(new Intent(Constants.GROUP_INVITE_CHANGED));

        }

        //收到 群邀请被同意
        @Override
        public void onInvitationAccepted(String groupId, String inviter, String reason) {
             // 更新数据
            InvationInfo invitationInfo = new InvationInfo();
            invitationInfo.setReason(reason);
            invitationInfo.setGroup(new GroupInfo(groupId, groupId, inviter));
            invitationInfo.setStatus(InvationInfo.InvitationStatus.GROUP_INVITE_ACCEPTED);

            Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invitationInfo);

            //红点处理

            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, true);

            //发送广播
            mLBM.sendBroadcast(new Intent(Constants.GROUP_INVITE_CHANGED));

        }

        //收到 群邀请被拒绝
        @Override
        public void onInvitationDeclined(String groupId, String inviter, String reason) {
            // 更新数据
            InvationInfo invitationInfo = new InvationInfo();
            invitationInfo.setReason(reason);
            invitationInfo.setGroup(new GroupInfo(groupId, groupId, inviter));
            invitationInfo.setStatus(InvationInfo.InvitationStatus.GROUP_INVITE_DECLINED);

            Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invitationInfo);

            // 红点处理
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, true);

            //发送广播
            mLBM.sendBroadcast(new Intent(Constants.GROUP_INVITE_CHANGED));

        }

        //收到 群成员被删除
        @Override
        public void onUserRemoved(String groupId, String groupName) {
//红点处理

            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, true);

            //发送广播
            mLBM.sendBroadcast(new Intent(Constants.GROUP_INVITE_CHANGED));

        }

        //收到 群被解散
        @Override
        public void onGroupDestroyed(String groupId, String groupName) {
            //红点处理
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, true);

            //发送广播
            mLBM.sendBroadcast(new Intent(Constants.GROUP_INVITE_CHANGED));

        }

        //收到 群邀请被自动接受
        @Override
        public void onAutoAcceptInvitationFromGroup(String groupId, String inviter, String inviteMessage) {
            // 更新数据
            InvationInfo invitationInfo = new InvationInfo();
            invitationInfo.setReason(inviteMessage);
            invitationInfo.setGroup(new GroupInfo(groupId, groupId, inviter));
            invitationInfo.setStatus(InvationInfo.InvitationStatus.GROUP_INVITE_ACCEPTED);

            Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invitationInfo);

            // 红点处理
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, true);

            //发送广播
            mLBM.sendBroadcast(new Intent(Constants.GROUP_INVITE_CHANGED));

        }
    };


    //   //联系人变化的监听
    private final EMContactListener emContactListener = new EMContactListener() {

        //添加联系人
        @Override
        public void onContactAdded(String hxid) {
            //数据更新，想服务器发送消息
            //发送联系人变化的广播，处理APP的界面
            mLBM.sendBroadcast(new Intent(Constants.CONTACT_CHANGED));

        }

        //删除联系人后执行的方法
        @Override
        public void onContactDeleted(String hxid) {
            //数据库的变化
            //发送广播
        }

        //接受到个人邀请的方法
        @Override
        public void onContactInvited(String hxid, String reason) {
            //数据更新，想服务器发送消息
            //用户消息的处理，红点通知
            //发送联系人变化的广播，处理APP的界面
        }

        // 别人同意了你的好友邀请
        @Override
        public void onContactAgreed(String hxid) {

        }

        // 别人拒绝了你好友邀请
        @Override
        public void onContactRefused(String s) {

        }
    };
}

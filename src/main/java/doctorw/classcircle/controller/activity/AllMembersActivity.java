package doctorw.classcircle.controller.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import doctorw.classcircle.R;
import doctorw.classcircle.controller.adapter.MembersAdapter;
import doctorw.classcircle.model.Model;
import doctorw.classcircle.utils.SpUtils;
import doctorw.classcircle.view.ListSlideView;

public class AllMembersActivity extends BaseActivity {

    @Bind(R.id.lv_members)
    ListSlideView lvMembers;

    private EMGroup mGroup;
    private String groupId;
    private Context mContext;
    private MembersAdapter mAdapter;
    private  List<String> members;

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_all_members);
        mContext = this;
        groupId = SpUtils.getInstance().getString("groupid","");
    }

    @Override
    protected void initListener() {


    }

    @Override
    protected void initData() {


    }

    @Override
    protected void initView() {
//        initSlideview();
        getMembersFromHxServer();

    }

    private void initSlideview() {
        mAdapter = new MembersAdapter(mContext,members);
        lvMembers.setAdapter(mAdapter);
        mAdapter.setRemoveListener(new MembersAdapter.OnRemoveListener() {
            @Override
            public void onRemoveItem(int position) {
                members.remove(position);
                mAdapter.refresh(members);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_back1)
    public void onViewClicked() {
        finish();
    }


    private void getMembersFromHxServer() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // 从环信服务器获取所有的群成员信息
                    EMGroup emGroup = EMClient.getInstance().groupManager().getGroupFromServer(groupId);
                     members = emGroup.getMembers();
                    // 更新页面
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 刷新适配器
                            initSlideview();
//                            mAdapter.refresh(members);
                        }
                    });
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "获取群信息失败" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}

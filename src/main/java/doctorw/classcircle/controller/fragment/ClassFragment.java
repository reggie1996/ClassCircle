package doctorw.classcircle.controller.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import doctorw.classcircle.ClassCircleApplication;
import doctorw.classcircle.R;
import doctorw.classcircle.controller.activity.AddGroupActivity;
import doctorw.classcircle.controller.activity.ChatActivity;
import doctorw.classcircle.controller.activity.ClassDongTai2Activity;
import doctorw.classcircle.controller.activity.ClassNotificationActivity;
import doctorw.classcircle.controller.activity.ClassScoreActivity;
import doctorw.classcircle.controller.activity.ClassSmallCirActivity;
import doctorw.classcircle.controller.activity.MyPhotosActivity;
import doctorw.classcircle.controller.activity.NewGroupActivity;
import doctorw.classcircle.controller.activity.TestActivity2;
import doctorw.classcircle.controller.adapter.ClassDongTaiAdapter;
import doctorw.classcircle.controller.adapter.ImageAdapter;
import doctorw.classcircle.model.Model;
import doctorw.classcircle.model.bean.DongTai;
import doctorw.classcircle.model.http.Datas;
import doctorw.classcircle.utils.CommonUtil;
import doctorw.classcircle.utils.Constants;
import doctorw.classcircle.utils.SpUtils;
import doctorw.classcircle.utils.Urls;
import doctorw.classcircle.utils.goodview.GoodView;
import doctorw.classcircle.view.GalleryView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by asus on 2017/4/20.
 */

public class ClassFragment extends BaseFragment {
    @Bind(R.id.iv_add_class)
    ImageView ivAddClass;
    @Bind(R.id.iv_contact_red)
    ImageView ivContactRed;
    @Bind(R.id.iv_new_class)
    ImageView ivNewClass;
    @Bind(R.id.ll_bg_null)
    LinearLayout llBgNull;
    @Bind(R.id.ll_main)
    LinearLayout llMain;
    @Bind(R.id.lv_dongtai)
    ListView lvDongTai;
    @Bind(R.id.scroll_view)
    ScrollView scrollView;
    @Bind(R.id.mygallery)
    GalleryView gallery;
    @Bind(R.id.tvPicTitle)
    TextView tvPicTitle;

    private Context mContext;
    private GoodView mGoodView;
    private ClassCircleApplication mApplication;
    private View mView;
    private ClassDongTaiAdapter dongTaiAdapter;
    private List<DongTai> mDongTais = new ArrayList<>();
    private ImageAdapter adapter;
    //所有的群组信息
    private List<EMGroup> mGroups = null;
    private LocalBroadcastManager mLBM;
    //红点变化监听
    private BroadcastReceiver GroupChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 显示红点
            ivContactRed.setVisibility(View.VISIBLE);
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, true);
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fg_classcircle, container, false);
        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    protected void initView() {

        scrollView.scrollTo(0, 0);
        initRes();
        mContext = getActivity();
        mGoodView = new GoodView(mContext);
        mApplication = new ClassCircleApplication();
        mLBM = LocalBroadcastManager.getInstance(getActivity());
        mLBM.registerReceiver(GroupChangeReceiver, new IntentFilter(Constants.GROUP_INVITE_CHANGED));
        refresh();
    }

    private void initRes() {

        adapter = new ImageAdapter(getActivity());
        adapter.createReflectedImages();
        gallery.setAdapter(adapter);

        gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvPicTitle.setText(adapter.titles[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {            // 设置点击事件监听
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, "img " + (position + 1) + " selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void refresh() {
        getGroupsFromServer();

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }


    //获取群组信息
    private void getGroupsFromServer() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                // 从网络获取数据
                try {
                    mGroups = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();//需异步处理

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            showToast("加载信息成功");Log.e("create_group1", mGroups.toString());
//                            if (mGroups != null && !mGroups.isEmpty()) { //没有加群，隐藏班级圈页面
//                            EMGroup group = EMClient.getInstance().groupManager().getGroup("111");

//                        ?Log.e("create_groupid", mGroups.get(0).getGroupId().toString());
                            if (mGroups != null && mGroups.size() > 0) {
                                Log.e("test_create_group1", mGroups.toString());
                                Log.e("test_create_group1", SpUtils.getInstance().getString("type", ""));
                                String groupId = mGroups.get(0).getGroupId();
                                SpUtils.getInstance().save("groupid", groupId);
                                Log.e("test_groupid", SpUtils.getInstance().getString("groupid", " "));
                                //（2）判断进来的用户的类型 老师，家长

                                llMain.setVisibility(View.VISIBLE);
//                                requestDongtai();
                                llBgNull.setVisibility(View.GONE);
                            } else {
                                Log.e("create_group1", "null");
                                llMain.setVisibility(View.GONE);
                                llBgNull.setVisibility(View.VISIBLE);
                                if (SpUtils.getInstance().getString("type", "").equals(Constants.TYPE_TEACHER)) {
                                    ivAddClass.setVisibility(View.GONE);
                                    ivNewClass.setVisibility(View.VISIBLE);
                                } else {
                                    ivAddClass.setVisibility(View.VISIBLE);
                                    ivNewClass.setVisibility(View.GONE);
                                }
                            }
//                            groupListAdapter.refresh(mGroups);
                            // 刷新
//                            refresh();
                        }
                    });

                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
//                    mGroups = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                // 更新页面

            }
        });
    }


    private void requestDongtai() {
        HashMap<String, String> params = new HashMap<>();
        params.put("classNo", SpUtils.getInstance().getString("groupid", ""));
        Datas.loadClassDongtai(Urls.URL_LOADCLASSDT, mContext, params, new Datas.HandleResponse() {
            @Override
            public void handleResponse(String s, Call call, Response response) {
                mDongTais = JSON.parseArray(s, DongTai.class);
                if (mDongTais != null && mDongTais.size() > 0) {
                    dongTaiAdapter = new ClassDongTaiAdapter(mContext, mDongTais, (FragmentActivity) getActivity());
                    lvDongTai.setAdapter(dongTaiAdapter);
                    CommonUtil.setListViewHeightBasedOnChildren(lvDongTai);
                } else {
                    showToast("加载失败");
                }
            }
        });
    }


    @OnClick({R.id.iv_add_class, R.id.iv_new_class, R.id.ll_todongtai, R.id.iv_add, R.id.iv_noti, R.id.iv_score, R.id.iv_pictures, R.id.iv_kcb, R.id.iv_activitycir, R.id.iv_dongtai, R.id.ll_enterchat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_todongtai:
                startActivity(ClassDongTai2Activity.class);
                break;
            case R.id.iv_add:
//                showToast("add");
                show();
                break;
            case R.id.iv_noti:
//                showToast("noti");
                // 红点处理
//                ivContactRed.setVisibility(View.GONE);
//                SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, false);
                startActivity(ClassNotificationActivity.class);
//                startActivity(InviteActivity.class);
                break;
            case R.id.iv_score:
                showToast("score");
                startActivity(ClassScoreActivity.class);
                break;
            case R.id.iv_pictures:
//                showToast("pic");
                startActivity(MyPhotosActivity.class);
                break;
            case R.id.iv_kcb:
//                showToast("manager");
                startActivity(TestActivity2.class);
                break;
            case R.id.iv_activitycir:
//                showToast("home");
                startActivity(ClassSmallCirActivity.class);
                break;

            case R.id.iv_dongtai:
//                showToast("home");
//                startActivity(ClassDongtaiActivity.class);
                startActivity(ClassDongTai2Activity.class);
                break;

            case R.id.ll_enterchat:
                Log.e("create_group1", mGroups.get(0).toString());
                if (mGroups == null || mGroups.isEmpty()) {
                    return;
                }
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                // 传递会话类型
                intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);
                // 群id
//                EMGroup emGroup = EMClient.getInstance().groupManager().getAllGroups().get(0);
                EMGroup emGroup = mGroups.get(0);
                intent.putExtra(EaseConstant.EXTRA_USER_ID, emGroup.getGroupId());
                startActivity(intent);
//                startActivity(ChatActivity.class);
                break;

            case R.id.iv_add_class:
                //家长申请加入班级圈的事件处理
//                showToast("" + SpUtils.getInstance().getString("type",""));
                startActivity(AddGroupActivity.class);
                break;
            case R.id.iv_new_class:
                //老师新建班级圈的事件处理
                if (mGroups != null && !mGroups.isEmpty()) {
                    Log.e("create_group2", mGroups.toString());
                } else {
                    Log.e("create_group2", "null");
                }
                startActivity(NewGroupActivity.class);
                break;
        }
    }


    private void show() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        // 重新加载页面
        refresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLBM.unregisterReceiver(GroupChangeReceiver);
    }
}

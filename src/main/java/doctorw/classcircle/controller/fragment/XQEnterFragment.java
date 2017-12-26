package doctorw.classcircle.controller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import doctorw.classcircle.R;
import doctorw.classcircle.controller.activity.XQCircleActivity;
import doctorw.classcircle.controller.activity.XQSearchInterestActivity;
import doctorw.classcircle.controller.adapter.CircleItemAdapter;
import doctorw.classcircle.controller.adapter.CircleItemAdapter2;
import doctorw.classcircle.model.bean.CircleDetail;
import doctorw.classcircle.utils.CommonUtil;
import doctorw.classcircle.utils.DatasUtil;
import doctorw.classcircle.utils.SpUtils;
import doctorw.classcircle.utils.Urls;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by asus on 2017/4/25.
 */

public class XQEnterFragment extends BaseFragment {

    @Bind(R.id.iv_nocircle)
    ImageView ivNocircle;
    @Bind(R.id.lv_mycirle)
    ListView lvMycirle;
    @Bind(R.id.lv_allcircle)
    ListView lvAllcircle;
    @Bind(R.id.iv_tips)
    ImageView ivTips;
    @Bind(R.id.ll_search)
    LinearLayout llSearch;

    private View mView;
    private List<CircleDetail> circleDetailsAdd = new ArrayList<>();
    private CircleItemAdapter2 circleItemAdapter2;


    private List<CircleDetail> circleDetails = new ArrayList<>();
    private CircleItemAdapter circleItemAdapter;
    //实例化adapter监听器
    private CircleItemAdapter.OnAddCircleListener mOnAddCircleListener = new CircleItemAdapter.OnAddCircleListener() {
        @Override
        public void OnAddCircleItem(final int position) {
            //加入圈子
//            showToast(DatasUtil.initCircle().get(position).getCircleId());
            //提示加入圈子成功
            OkGo.post(Urls.URL_JOININTEREST)
                    .params("userNo", SpUtils.getInstance().getString("userid", ""))
                    .params("id", circleDetails.get(position).getCircleId())
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            //跳转到相应的圈子界面
                            //将信息打包写到下一个活动
                            CircleDetail circleDetail = circleDetails.get(position);
                            Bundle bundle = new Bundle();
                            bundle.putString("focus", "YES");
                            bundle.putParcelable("circleDetail", circleDetail);
                            startActivity(XQCircleActivity.class, bundle);
                        }
                    });
        }

        @Override
        public void OnEnterItem(int position) {
            CircleDetail circleDetail = circleDetails.get(position);
            Bundle bundle = new Bundle();
            bundle.putParcelable("circleDetail", circleDetail);
            bundle.putString("focus", "NO");
            startActivity(XQCircleActivity.class, bundle);
        }

    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fg_xqenter, container, false);
        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    protected void initData() {

    }

    //加载所有的群信息
    private void loadAllCircle() {

        OkGo.get(Urls.URL_RECOMMEND)
                .params("userNo", SpUtils.getInstance().getString("userid", ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        circleDetailsAdd = JSON.parseArray(s, CircleDetail.class);
                        circleItemAdapter = new CircleItemAdapter(mContext, circleDetailsAdd, mOnAddCircleListener);
                        lvAllcircle.setAdapter(circleItemAdapter);
                        CommonUtil.setListViewHeightBasedOnChildren(lvAllcircle);
                    }
                });
    }

    //加载已经加入的群信息
    private void loadMyCircle() {

        Log.e("test_", SpUtils.getInstance().getString("userid", ""));
        OkGo.get(Urls.URL_MYINTEREST)
                .params("userNo", SpUtils.getInstance().getString("userid", ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("test_1111", "11111" + s);

                        if (TextUtils.isEmpty(s)) {
                            return;
                        } else {
                            circleDetails = JSON.parseArray(s, CircleDetail.class);
                            if (circleDetails == null || circleDetails.size() == 0) {
                                ivTips.setVisibility(View.VISIBLE);
                            } else {
                                ivTips.setVisibility(View.GONE);
                            }
                            circleItemAdapter2 = new CircleItemAdapter2(mContext, circleDetails, R.layout.item_circle2);
                            lvMycirle.setAdapter(circleItemAdapter2);
                            CommonUtil.setListViewHeightBasedOnChildren(lvMycirle);
                        }
                    }
                });
    }

    @Override
    protected void initListener() {
        lvMycirle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //将信息打包写到下一个活动
                CircleDetail circleDetail = circleDetailsAdd.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("focus", "YES");
                bundle.putParcelable("circleDetail", circleDetail);
                startActivity(XQCircleActivity.class, bundle);
            }
        });

//        lvAllcircle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                CircleDetail circleDetail = circleDetails.get(position);
//                Bundle bundle = new Bundle();
//                bundle.putParcelable("circleDetail",circleDetail);
//                bundle.putString("focus","NO");
//                startActivity(XQCircleActivity.class,bundle);
//            }
//        });
    }

    @Override
    protected void initView() {
        //用本地数据测试
        loadMyCircle();
        loadAllCircle();
//        testView();
    }

    //测试函数
    private void testView() {
        //初始化我的圈子
        circleDetailsAdd = DatasUtil.initCircle();
        if (circleDetailsAdd.size() == 0 || circleDetailsAdd == null) {
            ivTips.setVisibility(View.VISIBLE);
        } else {
            ivTips.setVisibility(View.GONE);
        }

        circleItemAdapter2 = new CircleItemAdapter2(mContext, DatasUtil.initCircle(), R.layout.item_circle2);
        lvMycirle.setAdapter(circleItemAdapter2);
        CommonUtil.setListViewHeightBasedOnChildren(lvMycirle);

        //初始化推荐圈子
        circleDetails = DatasUtil.initCircle();
        circleItemAdapter = new CircleItemAdapter(mContext, DatasUtil.initCircle(), mOnAddCircleListener);
        lvAllcircle.setAdapter(circleItemAdapter);
        CommonUtil.setListViewHeightBasedOnChildren(lvAllcircle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.ll_mycircle, R.id.ll_checkall, R.id.tv_checkall, R.id.ll_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_search:

                startActivity(XQSearchInterestActivity.class);
                break;
            case R.id.ll_mycircle:
                showToast("进入我的圈子");
                break;
            case R.id.ll_checkall:
                showToast("进入所有圈子");
                break;
            case R.id.tv_checkall:
                showToast("进入所有圈子");
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }
}

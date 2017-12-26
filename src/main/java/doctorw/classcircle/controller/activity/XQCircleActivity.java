package doctorw.classcircle.controller.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import doctorw.classcircle.R;
import doctorw.classcircle.controller.adapter.XQCircleDtAdapter;
import doctorw.classcircle.model.bean.CircleDetail;
import doctorw.classcircle.model.bean.CircleDongTai;
import doctorw.classcircle.utils.CommonUtil;
import doctorw.classcircle.utils.DatasUtil;
import doctorw.classcircle.utils.SpUtils;
import doctorw.classcircle.utils.Urls;
import doctorw.classcircle.utils.goodview.GoodView;
import doctorw.classcircle.view.MyPopupWindow;
import okhttp3.Call;
import okhttp3.Response;

public class XQCircleActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.lv_xqdongtai)
    ListView lvXqdongtai;
    @Bind(R.id.tv_circlename)
    TextView tvCirclename;
    @Bind(R.id.iv_circle_pic)
    ImageView ivCirclePic;
    @Bind(R.id.iv_head)
    ImageView ivHeadPic;
    @Bind(R.id.tv_cirname)
    TextView tvCirname;
    @Bind(R.id.tv_perNum)
    TextView tvPerNum;
    @Bind(R.id.tv_dtNum)
    TextView tvDtNum;
    @Bind(R.id.sv_circle)
    ScrollView svCircle;

    @Bind(R.id.bt_add)
    Button btAdd;
    //根据用户圈子拥有者的Id获取圈子信息和圈子动态
    private int[] res = {R.id.iv_add, R.id.iv_voice, R.id.iv_movie, R.id.iv_pics};
    private boolean flag = true;
    private List<ImageView> imageViewList = new ArrayList<>();
    private Context mContext;
    private String focus;
    private CircleDetail circleDetail;
    private List<CircleDongTai> mCircleDongTais = new ArrayList<>();
    private XQCircleDtAdapter xqCircleDtAdapter;
    private GoodView mGoodView;
    //初始化监听器
    private XQCircleDtAdapter.OnCircleListener mOnCircleListener = new XQCircleDtAdapter.OnCircleListener() {

        //显示删除，举报
        @Override
        public void OnClickMore(int position) {
            showPopupWindow(position);
        }

        //分享
        @Override
        public void OnClickShare(int position) {
            showToast("" + position);
        }

        //进入评论界面
        @Override
        public void OnClickComment(int position) {
            //携带数据
            CircleDongTai circleDongTai = mCircleDongTais.get(position);
            Bundle bundle = new Bundle();
            bundle.putParcelable("circleDongTai", circleDongTai);
            startActivity(XQDetilsActivity.class, bundle);
        }

        //点赞
        @Override
        public void OnClickLove(int position) {

        }
    };

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_xqcircle);
        mContext = this;
        Bundle bundle = getIntent().getExtras();
        circleDetail = bundle.getParcelable("circleDetail");
        focus = (String) bundle.get("focus");
        mGoodView = new GoodView(mContext);
    }

    @Override
    protected void initListener() {
        // 为所有的iamgeView添加点击事件
        for (int i = 0; i < res.length; i++) {
            ImageView imageView = (ImageView) findViewById(res[i]);
            imageView.setOnClickListener(this);
            imageViewList.add(imageView);
        }
    }

    @Override
    protected void initData() {
        loadDatas();
//        testDatas();
    }

    private void loadDatas() {
        OkGo.get(Urls.URL_LISTINTERESTCIRCLE)
                .params("interestId", circleDetail.getCircleId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        mCircleDongTais = JSON.parseArray(s, CircleDongTai.class);
                        xqCircleDtAdapter = new XQCircleDtAdapter(mContext, mCircleDongTais, mOnCircleListener);
                        lvXqdongtai.setAdapter(xqCircleDtAdapter);
                        CommonUtil.setListViewHeightBasedOnChildren(lvXqdongtai);
                    }
                });
    }

    private void testDatas() {
        //初始化我的圈子
        xqCircleDtAdapter = new XQCircleDtAdapter(mContext, DatasUtil.initDongtais(), mOnCircleListener);
        lvXqdongtai.setAdapter(xqCircleDtAdapter);
        CommonUtil.setListViewHeightBasedOnChildren(lvXqdongtai);
    }

    @Override
    protected void initView() {


        //判断是否已经加入
        //初始化头部界面
        btAdd.setVisibility(focus.equals("NO") ? View.VISIBLE : View.GONE);
        Glide.with(mContext).load(R.drawable.bg_classcircle_chat).into(ivCirclePic);
        Glide.with(mContext).load(R.drawable.baby).into(ivHeadPic);
        tvCirname.setText(circleDetail.getCirName());
        tvDtNum.setText(circleDetail.getDtNum());
        tvPerNum.setText(circleDetail.getPerNum());
        svCircle.scrollTo(0, 0);
    }

    private void startAnim() {
        // TODO Auto-generated method stub
        for (int i = 1; i < res.length; i++) {
            ObjectAnimator animator1 = ObjectAnimator.ofFloat(
                    imageViewList.get(i), "translationY", 0F, -300 * (float) Math.sin((33 * (i - 1)) * Math.PI / 180));
            ObjectAnimator animator2 = ObjectAnimator.ofFloat(
                    imageViewList.get(i), "translationX", 0F, -300 * (float) Math.cos((33 * (i - 1)) * Math.PI / 180));
            ObjectAnimator animator3 = ObjectAnimator.ofFloat(
                    imageViewList.get(i), "rotation", 0F, 360F);
            AnimatorSet set = new AnimatorSet();
            set.setDuration(300);
            set.setInterpolator(new BounceInterpolator());
            set.playTogether(animator1, animator2, animator3);
            set.start();

            flag = false;
        }
    }

    private void closeAnim() {
        // TODO Auto-generated method stub
        for (int i = 1; i < res.length; i++) {
            ObjectAnimator animator1 = ObjectAnimator.ofFloat(
                    imageViewList.get(i), "translationY", -300 * (float) Math.sin((33 * (i - 1)) * Math.PI / 180), 0F);
            ObjectAnimator animator2 = ObjectAnimator.ofFloat(
                    imageViewList.get(i), "translationX", -300 * (float) Math.cos((33 * (i - 1)) * Math.PI / 180), 0F);
            ObjectAnimator animator3 = ObjectAnimator.ofFloat(
                    imageViewList.get(i), "rotation", 360F, 0F);
            AnimatorSet set = new AnimatorSet();

            set.setDuration(300);
            set.setInterpolator(new AccelerateInterpolator(2f));
//            set.setInterpolator(new BounceInterpolator());
            set.playTogether(animator1, animator2, animator3);
            set.start();

            flag = true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add:
                if (flag) {
                    startAnim();
                } else {
                    closeAnim();
                }
                break;
            case R.id.iv_movie:
                closeAnim();
                startActivity(XQPublishCircleMovieActivity.class);
                break;
            case R.id.iv_voice:
                closeAnim();
                startActivity(XQPublishCircleVoiceActivity.class);
                break;
            case R.id.iv_pics:
                closeAnim();
                startActivity(XQPublishCircleActivity.class);
                break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_circle_pic, R.id.iv_share1, R.id.ll_back, R.id.iv_head, R.id.bt_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_circle_pic:
                //点击更换圈子图片

                break;
            case R.id.iv_share1:
                //

                break;
            case R.id.iv_head:
                //点击头像进入用户详情界面

                Bundle bundle = new Bundle();
                bundle.putParcelable("circleDetail", circleDetail);
                startActivity(XQCircleMessageActivity.class, bundle);

                break;
            case R.id.bt_add:
                //点击加入
                showToast("加入成功");
                OkGo.post(Urls.URL_JOININTEREST)
                        .params("userNo", SpUtils.getInstance().getString("userid", ""))
                        .params("id", circleDetail.getCircleId())
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                showToast("加入成功");
                                btAdd.setVisibility(View.GONE);
                            }
                        });
                break;
            case R.id.ll_back:
                //返回
                finish();
                break;
        }
    }

    private void showPopupWindow(final int position1) {
        MyPopupWindow popupWindow = new MyPopupWindow(this);
        String[] str = {"举报", "删除"};
        popupWindow.showPopupWindowForFoot(str, new MyPopupWindow.Callback() {
            @Override
            public void callback(String text, final int position) {
                switch (position) {
                    case 0: //对于不好的动态向系统举报
                        showToast("举报成功，等待处理");
                        break;
                    case 1: //对于不好的动态向系统举报
                        String infoId = mCircleDongTais.get(position1).getDongtaiId();

                        OkGo.post(Urls.URL_DELETECIRCLEDT)
                                .params("infoId", infoId)
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(String s, Call call, Response response) {
                                        showToast("删除成功");
                                        mCircleDongTais.remove(position1);
                                        xqCircleDtAdapter.refresh(mCircleDongTais);
                                    }
                                });
                        break;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDatas();
    }
}

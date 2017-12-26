//package doctorw.classcircle.controller.activity;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.support.v4.content.ContextCompat;
//import android.view.View;
//import android.widget.ImageView;
//
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import doctorw.classcircle.R;
//import doctorw.classcircle.utils.goodview.GoodView;
//
///**
// * Created by asus on 2017/4/17.
// */
//public class ClassCircleActivity extends BaseActivity {
//
//    private Context mContext;
//    private GoodView mGoodView;
//
//    @Override
//    protected void onActivityCreate(Bundle savedInstanceState) {
//        setContentView(R.layout.activity_classcircle);
//        mContext = this;
//        mGoodView = new GoodView(this);
//    }
//
//    @Override
//    protected void initListener() {
//
//    }
//
//    @Override
//    protected void initData() {
//
//    }
//
//    @Override
//    protected void initView() {
//
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // TODO: add setContentView(...) invocation
//        ButterKnife.bind(this);
//    }
//
//    @OnClick({R.id.ll_toChat,R.id.iv_back, R.id.iv_add, R.id.iv_noti, R.id.iv_score, R.id.iv_pictures, R.id.iv_dongtai, R.id.ll_enterchat, R.id.dt_iv_dianzan, R.id.dt_iv_comment, R.id.dt_iv_share})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.ll_toChat:
//                startActivity(ClassChatActivity.class);
//                break;
//            case R.id.iv_back:
//                finish();
//                break;
//            case R.id.iv_add:
//                showToast("add");
//                show();
//                break;
//            case R.id.iv_noti:
//                showToast("noti");
//                startActivity(ClassNotificationActivity.class);
//                break;
//            case R.id.iv_score:
//                showToast("score");
//                startActivity(ClassScoreActivity.class);
//                break;
//            case R.id.iv_pictures:
//                showToast("pic");
//                startActivity(ClassPicturesActivity.class);
//                break;
//
//            case R.id.iv_dongtai:
//                showToast("home");
//                startActivity(ClassDongTai2Activity.class);
////                startActivity(ClassDongtaiActivity.class);
//                break;
//            case R.id.ll_enterchat:
//                showToast("chat");
//                startActivity(ClassChatActivity.class);
//                break;
//            case R.id.dt_iv_dianzan:
//                ((ImageView) view).setImageResource(R.drawable.good_checked);
//                mGoodView.setImage(ContextCompat.getDrawable(mContext, R.drawable.good_checked));
//                mGoodView.show(view);
//                break;
//            case R.id.dt_iv_comment:
//                ((ImageView) view).setImageResource(R.drawable.good_checked);
//                mGoodView.setImage(ContextCompat.getDrawable(mContext, R.drawable.good_checked));
//                mGoodView.show(view);
//                break;
//            case R.id.dt_iv_share:
//                ((ImageView) view).setImageResource(R.drawable.good_checked);
//                mGoodView.setImage(ContextCompat.getDrawable(mContext, R.drawable.good_checked));
//                mGoodView.show(view);
//                break;
//        }
//    }
//
//    //弹出选项
//    private void show() {
//    }
//}

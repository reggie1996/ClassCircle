package doctorw.classcircle.controller.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import doctorw.classcircle.ClassCircleApplication;
import doctorw.classcircle.R;
import doctorw.classcircle.controller.fragment.BaseFragment;
import doctorw.classcircle.controller.fragment.DongTaiClassFragment;
import doctorw.classcircle.controller.fragment.DongTaiSelfFragment;

public class ClassDongtaiActivity extends FragmentActivity {

    private RadioGroup mRg_main;
    private List<BaseFragment> mBaseFragment;
    /**
     * 选中的Fragment的对应的位置
     */
    private int position;
    /**
     * 上次切换的Fragment
     */
    private Fragment mContent;
    private ClassCircleApplication mApplication;

    //当前下拉菜单是否为显示状态
    private boolean showItems = false;
    //添加数据按钮
    private ImageView addDataIv;
    //下拉功能菜单
    private LinearLayout dropDownView;
    private View bgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mApplication == null) {
            // 得到Application对象
            mApplication = (ClassCircleApplication) getApplication();
        }
        mApplication.addActivity_(this);
        //初始化View
        initView();
        //初始化Fragment
        initFragment();
        //设置RadioGroup的监听
        setListener();
    }

    class DongTaiOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_dt_class://班级动态
                    position = 0;
                    break;
                case R.id.rb_dt_self://个人动态
                    position = 1;
                    break;
                default:
                    position = 0;
                    break;
            }

            //根据位置得到对应的Fragment
            BaseFragment to = getFragment();
            //替换
            switchFrament(mContent, to);
        }
    }


    /**
     * @param from 刚显示的Fragment,马上就要被隐藏了
     * @param to   马上要切换到的Fragment，一会要显示
     */
    private void switchFrament(Fragment from, Fragment to) {
        if (from != to) {
            mContent = to;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //才切换
            //判断有没有被添加
            if (!to.isAdded()) {
                //to没有被添加
                //from隐藏
                if (from != null) {
                    ft.hide(from);
                }
                //添加to
                if (to != null) {
                    ft.add(R.id.fl_content_class, to).commit();
                }
            } else {
                //to已经被添加
                // from隐藏
                if (from != null) {
                    ft.hide(from);
                }
                //显示to
                if (to != null) {
                    ft.show(to).commit();
                }
            }
        }

    }

    /**
     * 根据位置得到对应的Fragment
     *
     * @return
     */
    private BaseFragment getFragment() {
        BaseFragment fragment = mBaseFragment.get(position);
        return fragment;
    }

    private void initFragment() {
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(new DongTaiClassFragment());
        mBaseFragment.add(new DongTaiSelfFragment());
    }

    private void initView() {
        setContentView(R.layout.activity_class_dongtai);
        mRg_main = (RadioGroup) findViewById(R.id.rg_dongtai);

        addDataIv = (ImageView) findViewById(R.id.main_newData);
        dropDownView = (LinearLayout) findViewById(R.id.dropDownView);
        bgView = findViewById(R.id.main_bg);
    }


    //显示下拉菜单
    private void showView() {
        addDataIv.startAnimation(AnimationUtils.loadAnimation(ClassDongtaiActivity.this, R.anim.rotate_open));
//        ObjectAnimator anim2 = ObjectAnimator .ofFloat(addDataIv, "rotationX", 0f, 45f);
//        anim2.setDuration(300);
//        anim2.start();

        dropDownView.setVisibility(View.VISIBLE);
        ObjectAnimator anim = ObjectAnimator .ofFloat(dropDownView, "translationY", -50f, 0f);
        anim.setDuration(300);
        anim.start();

        bgView.setVisibility(View.VISIBLE);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(bgView, "alpha", 0f, 0.5f);
        alpha.setDuration(300);//设置动画时间
        alpha.start();
        showItems = true;
    }

    //隐藏下拉菜单
    private void dismissView() {
//        ObjectAnimator anim = ObjectAnimator .ofFloat(addDataIv, "rotationX", 45f, 0f);
//        anim.setDuration(300);
//        anim.start();

        addDataIv.startAnimation(AnimationUtils.loadAnimation(ClassDongtaiActivity.this, R.anim.rotate_close));
        ObjectAnimator anim2 = ObjectAnimator .ofFloat(dropDownView, "translationY", 0f, -250f);
        anim2.setDuration(300);
        anim2.start();
        dropDownView.setVisibility(View.GONE);


        ObjectAnimator alpha = ObjectAnimator.ofFloat(bgView, "alpha", 0.5f, 0f);
        alpha.setDuration(300);//设置动画时间
        alpha.start();
        bgView.setVisibility(View.GONE);

        showItems = false;
    }

    private void setListener() {

        findViewById(R.id.iv_back_dt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRg_main.setOnCheckedChangeListener(new DongTaiOnCheckedChangeListener());
        //设置默认选中常用框架
        mRg_main.check(R.id.rb_dt_class);


        addDataIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!showItems) {
                    showView();
                } else {
                    dismissView();
                }
            }
        });

        //点击空白隐藏弹出框
        bgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showItems) {
                    dismissView();
                }
            }
        });

        //下拉功能菜单上按钮的点击事件
        View.OnClickListener l = new OnClickImpl();
        findViewById(R.id.dropDown_item1).setOnClickListener(l);
        findViewById(R.id.dropDown_item2).setOnClickListener(l);
        findViewById(R.id.dropDown_item3).setOnClickListener(l);
//        findViewById(R.id.dropDown_item4).setOnClickListener(l);
    }


    private class OnClickImpl implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.dropDown_item1:
                    Toast.makeText(ClassDongtaiActivity.this, "item1 onClick", Toast.LENGTH_SHORT).show();
                    dismissView();
                    break;

                case R.id.dropDown_item2:
                    Toast.makeText(ClassDongtaiActivity.this, "item2 onClick", Toast.LENGTH_SHORT).show();
                    dismissView();
                    break;

                case R.id.dropDown_item3:
                    Toast.makeText(ClassDongtaiActivity.this, "item3 onClick", Toast.LENGTH_SHORT).show();
                    dismissView();
                    break;

//                case R.id.dropDown_item4:
//                    Toast.makeText(ClassDongtaiActivity.this, "item4 onClick", Toast.LENGTH_SHORT).show();
//                    dismissView();
//                    break;

                default:
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mApplication.removeActivity_(this);
    }

    //点击返回键时，如果下拉菜单是显示状态，则隐藏它
    @Override
    public void onBackPressed() {
        if (showItems) {
            dismissView();
            return;
        }
        super.onBackPressed();
    }
}

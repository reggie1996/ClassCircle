package doctorw.classcircle.controller.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import doctorw.classcircle.ClassCircleApplication;
import doctorw.classcircle.R;
import doctorw.classcircle.controller.fragment.BaseFragment;
import doctorw.classcircle.controller.fragment.ClassFragment;
import doctorw.classcircle.controller.fragment.SelfFragment;
import doctorw.classcircle.controller.fragment.SheQuFragment;
import doctorw.classcircle.controller.fragment.Study2Fragment;
import doctorw.classcircle.controller.fragment.XQFragment;


public class MainActivity  extends FragmentActivity {

    private RadioGroup mRg_main;
    private List<BaseFragment> mBaseFragment;

    private ClassCircleApplication mApplication;

    /**
     * 选中的Fragment的对应的位置
     */
    private int position;

    /**
     * 上次切换的Fragment
     */
    private Fragment mContent;
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

    private void setListener() {
        mRg_main.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //设置默认选中常用框架
        mRg_main.check(R.id.rb_school);
    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_school://
                    position = 0;
                    break;
                case R.id.rb_xingqu:
                    position = 1;
                    break;
                case R.id.rb_shequ://
                    position = 2;
                    break;
                case R.id.rb_study://
                    position = 3;
                    break;
                case R.id.rb_self://
                    position = 4;
                    break;
                default:
                    position = 0;
                    break;
            }

            //根据位置得到对应的Fragment
            BaseFragment to = getFragment();
            //替换
            switchFrament(mContent,to);
        }
    }


    /**
     *
     * @param from 刚显示的Fragment,马上就要被隐藏了
     * @param to 马上要切换到的Fragment，一会要显示
     */
    private void switchFrament(Fragment from,Fragment to) {
        if(from != to){
            mContent = to;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //才切换
            //判断有没有被添加
            if(!to.isAdded()){
                //to没有被添加
                //from隐藏
                if(from != null){
                    ft.hide(from);
                }
                //添加to
                if(to != null){
                    ft.add(R.id.fl_content,to).commit();
                }
            }else{
                //to已经被添加
                // from隐藏
                if(from != null){
                    ft.hide(from);
                }
                //显示to
                if(to != null){
                    ft.show(to).commit();
                }
            }
        }

    }

    /**
     * 根据位置得到对应的Fragment
     * @return
     */
    private BaseFragment getFragment() {
        BaseFragment fragment = mBaseFragment.get(position);
        return fragment;
    }

    private void initFragment() {
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(new ClassFragment());
//        mBaseFragment.add(new XingquFragment());
        mBaseFragment.add(new XQFragment());
//        mBaseFragment.add(new Study2Fragment());
        mBaseFragment.add(new SheQuFragment());
        mBaseFragment.add(new Study2Fragment());
        mBaseFragment.add(new SelfFragment());
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        mRg_main = (RadioGroup) findViewById(R.id.rg_main);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mApplication.removeActivity_(this);
    }
}

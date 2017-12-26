package doctorw.classcircle.controller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import doctorw.classcircle.R;
import doctorw.classcircle.controller.activity.CreateInterestActivity;
import doctorw.classcircle.controller.activity.XQSearchInterestActivity;


public class XQFragment extends BaseFragment {
    private static final String TAG = XQFragment.class.getSimpleName();//"CommonFragment"
    @Bind(R.id.iv_newqz)
    ImageView ivNewqz;

    private View mView;
    private RadioGroup mRg_main;
    private List<BaseFragment> mBaseFragment;
    private PopupWindow mPopWindow;
    /**
     * 选中的Fragment的对应的位置
     */
    private int position;
    /**
     * 上次切换的Fragment
     */
    private Fragment mContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fg_quanzi, container, false);
        initFragment();
        ButterKnife.bind(this, mView);
        return mView;
    }

    private void initFragment() {
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(new XQEnterFragment());
        mBaseFragment.add(new XQLookFragment());
    }

    @Override
    protected void initData() {

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

    @Override
    protected void initListener() {
        mRg_main.setOnCheckedChangeListener(new StudyOnCheckedChangeListener());
        //设置默认选中常用框架
        mRg_main.check(R.id.rb_enter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick(R.id.iv_newqz)
    public void onViewClicked() {
        showPopupWindows();
    }

    private void showPopupWindows() {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.popup_addqz, null);
        mPopWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        TextView popAdd = (TextView) contentView.findViewById(R.id.pop_add);
        TextView popCheck = (TextView) contentView.findViewById(R.id.pop_check);

        popAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(CreateInterestActivity.class);
                mPopWindow.dismiss();
            }
        });
        popCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(XQSearchInterestActivity.class);
                mPopWindow.dismiss();
            }
        });
        mPopWindow.showAsDropDown(ivNewqz, 0, 0);
    }



    class StudyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_enter://热门文章
                    position = 0;
                    break;
                case R.id.rb_hot://学习资料
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
//            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
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
                    ft.add(R.id.fl_content_study, to).commit();
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

    @Override
    protected void initView() {
        mRg_main = (RadioGroup) mView.findViewById(R.id.rg_study_top);
    }
}

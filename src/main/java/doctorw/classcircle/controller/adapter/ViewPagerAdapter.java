package doctorw.classcircle.controller.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import doctorw.classcircle.controller.fragment.ArticleFragment;
import doctorw.classcircle.controller.fragment.MyFragment;
/**
 * Created by asus on 2017/4/15.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragments;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }
    /**
     * 根据位置返回对应的Fragment
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    /**
     * 得到页面的标题
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0){
            return "热门推荐";
        }else {
            return ((ArticleFragment)(fragments.get(position))).getTitle();
        }
    }
}


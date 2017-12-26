package doctorw.classcircle.controller.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import doctorw.classcircle.R;
import doctorw.classcircle.controller.adapter.ViewPagerAdapter;
import doctorw.classcircle.model.bean.ArticleTheme;
import doctorw.classcircle.model.http.Datas;
import doctorw.classcircle.utils.Urls;
import okhttp3.Call;
import okhttp3.Response;


public class SheQuFragment extends BaseFragment {

    @Bind(R.id.tableLayout_Shequ)
    TabLayout tableLayoutShequ;
    @Bind(R.id.vPager_shequ)
    ViewPager vPagerShequ;
    private View mView;

    List<Fragment> fragments;
    ViewPagerAdapter adapter;

    List<ArticleTheme> themes = new ArrayList<>();
    private String[] mStrings = {"biaoti1", "biaoti2", "biaoti3", "biaoti4"};

    //    private String[] mStrings;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        mView = inflater.inflate(R.layout.fg_shequ, container, false);
        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    protected void initData() {
//        initViews();
        Datas.getDatas(Urls.URL_GETTHEME, mContext, new Datas.HandleResponse() {
            @Override
            public void handleResponse(String s, Call call, Response response) {
                themes = JSON.parseArray(s, ArticleTheme.class);
                showToast("加载成功");
                if (themes != null && themes.size() > 0) {
                    initViews();
                } else {
                    showToast("加载失败");
                    return;
                }
            }
        });
    }

    private void initViews() {
        //添加第一个页面
        fragments = new ArrayList<>();
        fragments.add(new SQuFirstFragment());
        //初始化数据
        BaseFragment base;

        for (int i = 0; i < themes.size(); i++) {
            base = new ArticleFragment(themes.get(i).getNewsTypeName());
            fragments.add(base);
        }
//        for (int i = 0; i < mStrings.length; i++) {
//            base = new ArticleFragment(mStrings[i]);
//            fragments.add(base);
//        }


        //设置ViewPager的适配器
        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), fragments);
        vPagerShequ.setAdapter(adapter);
        //关联ViewPager
        tableLayoutShequ.setupWithViewPager(vPagerShequ);
        //设置固定的
//        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tableLayoutShequ.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

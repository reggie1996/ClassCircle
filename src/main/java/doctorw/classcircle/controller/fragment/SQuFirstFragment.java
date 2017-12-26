package doctorw.classcircle.controller.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import doctorw.classcircle.ClassCircleApplication;
import doctorw.classcircle.R;
import doctorw.classcircle.controller.activity.ArticleWebActivity;
import doctorw.classcircle.controller.activity.WriteArtcileActivity;
import doctorw.classcircle.controller.adapter.ArticleAdapter;
import doctorw.classcircle.model.bean.Article;
import doctorw.classcircle.tools.imageLoader.GlideImageLoader;
import doctorw.classcircle.utils.Urls;
import doctorw.classcircle.view.SwipeRefreshView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by asus on 2017/4/15.
 */
public class SQuFirstFragment extends BaseFragment {

    @Bind(R.id.sr_lv)
    SwipeRefreshView swipeRefreshView;
    private Banner headerBanner;
    @Bind(R.id.lv_articlefirst)
    ListView lvArticlefirst;
    private View headerView;
    private View mView;
    private ArticleAdapter mAdapter;
    private List<Article> articlesList;

    private LinearLayout ll_write;
    private String TITLE = "热门推荐";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fg_shequfirst, container, false);
        ButterKnife.bind(this, mView);
        return mView;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    protected void initView() {
        View header = LayoutInflater.from(getContext()).inflate(R.layout.header_banner, null);
//        headerView = View.inflate(getContext(), R.layout.header_banner, null);
        headerBanner = (Banner) header.findViewById(R.id.header_banner);
        //设置Banner的高和宽
        headerBanner.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ClassCircleApplication.H / 4));
        lvArticlefirst.addHeaderView(header);

        View header1 = LayoutInflater.from(mContext).inflate(R.layout.header_banner2,null);
        ll_write = (LinearLayout) header1.findViewById(R.id.ll_write);
        lvArticlefirst.addHeaderView(header1);

        // 不能在onCreate中设置，这个表示当前是刷新状态，如果一进来就是刷新状态，SwipeRefreshLayout会屏蔽掉下拉事件
        //swipeRefreshLayout.setRefreshing(true);
        // 设置颜色属性的时候一定要注意是引用了资源文件还是直接设置16进制的颜色，因为都是int值容易搞混
        // 设置下拉进度的背景颜色，默认就是白色的
        swipeRefreshView.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉进度的主题颜色
        swipeRefreshView.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
    }

    protected void initData() {
        //集合数据
        articlesList = new ArrayList<>();
        Article art;//new Article("希望大家说都开开学习","http://img.zcool.cn/community/01b72057a7e0790000018c1bf4fce0.png","https://zhidao.baidu.com/question/1885817326500180748.html","0","0");

//        for (int i = 0; i < 10; i++) {
//            art = new Article("希望大家说都开开学习", "http://img.zcool.cn/community/01b72057a7e0790000018c1bf4fce0.png", "https://zhidao.baidu.com/question/1885817326500180748.html", "" + i, "" + i);
//            articlesList.add(art);
//        }

        OkGo.post(Urls.URL_LOADART)
                .params("typeName", TITLE)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //获得
                        articlesList = JSON.parseArray(s, Article.class);
                        mAdapter = new ArticleAdapter(mContext, articlesList, R.layout.item_article);
                        //设置ListView的适配器
                        lvArticlefirst.setAdapter(mAdapter);
                    }
                });


        //设置ListView的item的点击事件
        //简单使用--Banner加载图片地址
        headerBanner.setImages(ClassCircleApplication.images)
                .setImageLoader(new GlideImageLoader())
                .start();
    }

    @Override
    protected void initListener() {

        lvArticlefirst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0 || position == 1 ){
                    return ;
                }else{
                    Article art = articlesList.get(position - 2);
                    Log.e("test_id",art.toString());
                    showToast("" + (position - 2));
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("article",art);
                    startActivity(ArticleWebActivity.class, bundle);
                }
            }
        });

        ll_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(WriteArtcileActivity.class);
            }
        });
        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        swipeRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 开始刷新，设置当前为刷新状态
                //swipeRefreshLayout.setRefreshing(true);

                // 这里是主线程
                // 一些比较耗时的操作，比如联网获取数据，需要放到子线程去执行
                // TODO 获取数据
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //需要刷新的数据到服务器获取
                        //刷新banner
                        String[] urls = getResources().getStringArray(R.array.url);
                        List list = Arrays.asList(urls);
                        List arrayList = new ArrayList(list);
                        headerBanner.update(arrayList);

                        //刷新文章里面的数据
                        articlesList.add(0, new Article());
                        mAdapter.notifyDataSetChanged();
                        showToast("刷新了一条数据");
                        // 加载完数据设置为不刷新状态，将下拉进度收起来
                        swipeRefreshView.setRefreshing(false);
                    }
                }, 2000);
                // System.out.println(Thread.currentThread().getName());
                // 这个不能写在外边，不然会直接收起来
                //swipeRefreshLayout.setRefreshing(false);
            }
        });

        // 设置下拉加载更多
        swipeRefreshView.setOnLoadListener(new SwipeRefreshView.OnLoadListener() {
            @Override
            public void onLoad() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        articlesList.add(new Article());
                        // 这里要放在里面刷新，放在外面会导致刷新的进度条卡住
                        mAdapter.notifyDataSetChanged();

                        // 加载完数据设置为不加载状态，将加载进度收起来
                        swipeRefreshView.setLoading(false);
                    }
                }, 2000);
            }
        });

        headerBanner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                showToast(""+position);
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}

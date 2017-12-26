package doctorw.classcircle.controller.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import doctorw.classcircle.R;
import doctorw.classcircle.controller.activity.ArticleWebActivity;
import doctorw.classcircle.controller.adapter.ArticleAdapter;
import doctorw.classcircle.model.bean.Article;
import doctorw.classcircle.utils.Urls;
import okhttp3.Call;
import okhttp3.Response;

import static doctorw.classcircle.ClassCircleApplication.getGlobalApplication;

public class ArticleFragment extends BaseFragment {

    @Bind(R.id.pull_refresh_list)
    PullToRefreshListView mPullRefreshListView;
    /**
     * 标题
     */
    private final String title;
    private ArrayList<Article> articlesList = new ArrayList<>();
    private ArticleAdapter mAdapter;
    private ListView listview;


    /**
     * 得到标题
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    public ArticleFragment(String title) {
        this.title = title;
    }

    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fg_article, container, false);
        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    protected void initData() {
        // Set a listener to be invoked when the list should be refreshed.
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            /**
             * 下拉刷新
             * @param refreshView
             */
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //得到当前刷新的时间
                String label = DateUtils.formatDateTime(getGlobalApplication(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                // Update the LastUpdatedLabel
                //设置更新时间
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
//                Toast.makeText(mContext, "下拉刷新", Toast.LENGTH_SHORT).show();
                new GetDataTask().execute();
            }

            /**
             * 上拉刷新
             * @param refreshView
             */
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //得到当前刷新的时间
                String label = DateUtils.formatDateTime(getGlobalApplication(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                // Update the LastUpdatedLabel
                //设置更新时间
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
//                Toast.makeText(mContext, "上拉刷新!", Toast.LENGTH_SHORT).show();
                new GetDataTask().execute();
            }

        });

        // Add an end-of-list listener
        //设置监听最后一条
        mPullRefreshListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                Toast.makeText(mContext, "滑动到最后一条了!", Toast.LENGTH_SHORT).show();
            }
        });

        //从服务器获取数据
        //得到ListView
        listview = mPullRefreshListView.getRefreshableView();
        articlesList = new ArrayList<>();

//        Article art;//new Article("希望大家说都开开学习","http://img.zcool.cn/community/01b72057a7e0790000018c1bf4fce0.png","https://zhidao.baidu.com/question/1885817326500180748.html","0","0");
//        for (int i = 0; i < 10; i++) {
//            art = new Article("希望大家说都开开学习", "http://img.zcool.cn/community/01b72057a7e0790000018c1bf4fce0.png", "https://zhidao.baidu.com/question/1885817326500180748.html", "" + i, "" + i, "" + i);
//            articlesList.add(art);
//        }


        OkGo.post(Urls.URL_LOADART)
                .params("typeName", title)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //获得
                        articlesList = (ArrayList<Article>) JSON.parseArray(s, Article.class);
                        mAdapter = new ArticleAdapter(mContext, articlesList, R.layout.item_article);
                        //设置ListView的适配器
                        listview.setAdapter(mAdapter);
                    }
                });

//        mAdapter = new ArticleAdapter(mContext, articlesList, R.layout.item_article);
//        //设置ListView的适配器
//        listview.setAdapter(mAdapter);
//        /**
//         * Add Sound Event Listener
//         * 添加刷新事件并且发出声音
//         */
//        SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(this);
//        soundListener.addSoundEvent(State.PULL_TO_REFRESH, R.raw.pull_event);
//        soundListener.addSoundEvent(State.RESET, R.raw.reset_sound);
//        soundListener.addSoundEvent(State.REFRESHING, R.raw.refreshing_sound);
//        mPullRefreshListView.setOnPullEventListener(soundListener);
//
//        // You can also just use setListAdapter(mAdapter) or
//        // mPullRefreshListView.setAdapter(mAdapter)
        //设置适配器
//        listview.setAdapter(mAdapter);

        //设置上拉刷新或者下拉刷新
//        mPullRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

    }

    private class GetDataTask extends AsyncTask<Void, Void, String[]> {
        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            return new String[0];
        }

        @Override
        protected void onPostExecute(String[] result) {
            if (mPullRefreshListView.getMode() == PullToRefreshBase.Mode.PULL_FROM_START) {
                //下拉刷新
                //从服务器获取数据

                Article arts = new Article();
                arts.setTitle("first");
                Log.e("test", "first");
                articlesList.add(0, arts);
            } else if (mPullRefreshListView.getMode() == PullToRefreshBase.Mode.PULL_FROM_END) {

                Article arts = new Article();
                arts.setTitle("lat");
                Log.e("test", "last");
                int num = articlesList.size();
                articlesList.add(num, arts);
            }
            mAdapter.notifyDataSetChanged();
            // Call onRefreshComplete when the list has been refreshed.
            mPullRefreshListView.onRefreshComplete();
            super.onPostExecute(result);
        }
    }

    @Override
    protected void initListener() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Article art = articlesList.get(position-1);
                showToast("" + position);
                Bundle bundle = new Bundle();
                bundle.putParcelable("article", art);
                startActivity(ArticleWebActivity.class, bundle);

            }
        });

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


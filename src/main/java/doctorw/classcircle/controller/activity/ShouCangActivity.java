package doctorw.classcircle.controller.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import doctorw.classcircle.R;
import doctorw.classcircle.controller.adapter.NewsAdapter;
import doctorw.classcircle.model.bean.Article;
import doctorw.classcircle.utils.SpUtils;
import doctorw.classcircle.utils.Urls;
import doctorw.classcircle.view.ListSlideView;
import okhttp3.Call;
import okhttp3.Response;

public class ShouCangActivity extends BaseActivity {

    @Bind(R.id.lv_article)
    ListSlideView lvArticle;

    private List<Article> articles = new ArrayList<>();
    private NewsAdapter newsAdapter;
    private Context mContext;

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_shou_cang);
        mContext = this;

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

        loadArticles();
    }

    private void loadArticles() {
        OkGo.get(Urls.URL_SCHOUCANGART)
                .params("userNo", SpUtils.getInstance().getString("userid", ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        articles = JSON.parseArray(s, Article.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                newsAdapter = new NewsAdapter(mContext, articles);
                                lvArticle.setAdapter(newsAdapter);
                                newsAdapter.setRemoveListener(new NewsAdapter.OnRemoveListener() {
                                    @Override
                                    public void onRemoveItem(int position) {
                                        String id =  articles.get(position).getArticleId();
                                        articles.remove(position);
                                        newsAdapter.refresh(articles);
                                        deleteArt(id);
                                        //删除收藏
                                    }
                                });
                            }
                        });
                    }
                });
    }

    //删除收藏
    private void deleteArt(String articleId) {
        OkGo.post(Urls.URL_DELETEART)
                .params("newsId", articleId)
                .params("userNo",SpUtils.getInstance().getString("userid",""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                    }
                });
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back2:
                break;
        }
    }
}

package doctorw.classcircle.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import doctorw.classcircle.R;
import doctorw.classcircle.controller.adapter.ArticleCommentAdapter;
import doctorw.classcircle.model.bean.Article;
import doctorw.classcircle.model.bean.ArticleComment;
import doctorw.classcircle.utils.CommonUtil;
import doctorw.classcircle.utils.SpUtils;
import doctorw.classcircle.utils.Urls;
import doctorw.classcircle.utils.goodview.GoodView;
import okhttp3.Call;
import okhttp3.Response;

public class ArticleWebActivity extends BaseActivity {

    @Bind(R.id.tv_title_art)
    TextView tvTitle2;
    @Bind(R.id.wv_art)
    WebView wvArt;

    @Bind(R.id.lv_comment)
    ListView lvComment;
    @Bind(R.id.ll_comment)
    LinearLayout ll_comment;
    @Bind(R.id.dt_iv_dianzan)
    ImageView dtIvDianzan;
    @Bind(R.id.scroll_view)
    ScrollView scrollView;
    @Bind(R.id.et_comment)
    EditText etComment;
    @Bind(R.id.bt_send)
    Button btSend;
    @Bind(R.id.tv_num)
    TextView tvNum;
    @Bind(R.id.iv_favour)
    ImageView ivFavour;

    private String title;
    private String webUrl;

    private String articleId;
    private String comnum;

    private Context mContext;

    private ArticleCommentAdapter articleCommentAdapter;
    private List<ArticleComment> articleComments = new ArrayList<>();
    private GoodView mGoodView;
    public static final String HEADURL = "http://img2.imgtn.bdimg.com/it/u=2279370626,1858315540&fm=214&gp=0.jpg";

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_web);
        mContext = this;
        mGoodView = new GoodView(mContext);

        Intent intent = getIntent();
        //从intent对象中把封装好的数据取出来
        Bundle bundle = intent.getExtras();
        Article article = bundle.getParcelable("article");
        Log.e("test_id", article.toString());
        title = article.getTitle();
        webUrl = article.getWeburl();
        articleId = article.getArticleId();
        comnum = article.getCommentNum();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {


        //设置评论的数目
        tvNum.setText(comnum);

        tvTitle2.setText(title);
        wvArt.loadUrl(webUrl);
        wvArt.getSettings().setJavaScriptEnabled(true);
        //设置可自由缩放网页
        wvArt.getSettings().setSupportZoom(true);
        wvArt.getSettings().setBuiltInZoomControls(true);
        wvArt.getSettings().setDisplayZoomControls(false);
        // 如果页面中链接，如果希望点击链接继续在当前browser中响应，
        // 而不是新开Android的系统browser中响应该链接，必须覆盖webview的WebViewClient对象
        wvArt.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    protected void initView() {
        scrollView.scrollTo(0, 0);
//        showToast(articleId);
        loadComment();
    }

    private void loadComment() {
        OkGo.get(Urls.URL_LOADARTCOMMENT)
                .params("newsId", articleId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        articleComments = JSON.parseArray(s, ArticleComment.class);

                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              tvNum.setText("" + articleComments.size());
                                          }
                                      }
                        );

                        if (articleComments == null || articleComments.size() == 0) {
                            ll_comment.setVisibility(View.GONE);
                        }
                        articleCommentAdapter = new ArticleCommentAdapter(mContext, articleComments, R.layout.item_article_comment);
                        lvComment.setAdapter(articleCommentAdapter);
                        CommonUtil.setListViewHeightBasedOnChildren(lvComment);
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_back_art)
    public void onViewClicked() {

    }

    @OnClick({R.id.iv_back_art, R.id.dt_iv_dianzan, R.id.iv_money, R.id.iv_comment, R.id.iv_favour, R.id.bt_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back_art:
                finish();
                break;
            case R.id.dt_iv_dianzan:
                //点赞功能

                Glide.with(this).load(R.drawable.icon_dzart1).into(dtIvDianzan);
                mGoodView.setImage(ContextCompat.getDrawable(mContext, R.drawable.good_checked));
                mGoodView.show(dtIvDianzan);

                OkGo.get(Urls.URL_ARTFACOR)
                        .params("newsId", articleId)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                //后续处理
                            }
                        });
                break;
            case R.id.iv_money:
                //赏红包
                showToast("红包");
                break;
            case R.id.iv_comment:
                //移动到评论区域
                lvComment.smoothScrollToPosition(0);
                break;
            case R.id.iv_favour:
                //收藏
                Glide.with(this).load(R.drawable.icon_lovepressed).into(ivFavour);
                mGoodView.setImage(ContextCompat.getDrawable(mContext, R.drawable.icon_lovepressed));
                mGoodView.show(ivFavour);
                OkGo.post(Urls.URL_ARTCOLLECTION)
                        .params("userNo", SpUtils.getInstance().getString("userid", ""))
                        .params("newsId", articleId)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {


                            }
                        });
//                OkGo.post(Urls.)
                break;
            case R.id.bt_send:
                //发送评论
                HashMap<String, String> params = new HashMap<>();
                final String comment = etComment.getText().toString().trim();
                if (TextUtils.isEmpty(comment)) {
                    showToast("不能为空");
                    return;
                }
                etComment.setText("");
                params.put("text", comment);
                params.put("newsId", articleId);
                params.put("userNo", SpUtils.getInstance().getString("userid", ""));
                OkGo.post(Urls.URL_POSTARTCOMMENT)
                        .params(params)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showToast("成功");
                                        articleComments.add(new ArticleComment(HEADURL, SpUtils.getInstance().getString("username", "小野家长"), comment));
                                        articleCommentAdapter.notifyDataSetChanged();
                                    }
                                });
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);

                            }
                        });
                break;
        }
    }
}

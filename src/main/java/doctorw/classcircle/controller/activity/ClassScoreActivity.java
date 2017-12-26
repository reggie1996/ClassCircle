package doctorw.classcircle.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import doctorw.classcircle.R;

public class ClassScoreActivity extends BaseActivity {


    @Bind(R.id.tv_title_art)
    TextView tvTitle2;
    @Bind(R.id.wv_art)
    WebView wvArt;
    private String title;
    private String webUrl;


    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_score);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        //从intent对象中把封装好的数据取出来
//        Bundle bundle = intent.getExtras();
//        Article article = (Article) bundle.getSerializable("article");
//        title = article.getTitle();
//        webUrl = article.getWeburl();
        tvTitle2.setText("成绩分析");
//        wvArt.loadUrl("http://loger.iask.in/classcircle/showcourse.jsp");
        wvArt.loadUrl("http://loger.iask.in/classcircle/TshowGrade.do");

        wvArt.getSettings().setJavaScriptEnabled(true);
        //设置可自由缩放网页
        wvArt.getSettings().setSupportZoom(true);
        wvArt.getSettings().setBuiltInZoomControls(true);
        wvArt.getSettings().setDisplayZoomControls(false);
        // 如果页面中链接，如果希望点击链接继续在当前browser中响应，
        // 而不是新开Android的系统browser中响应该链接，必须覆盖webview的WebViewClient对象
        wvArt.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
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

    @OnClick(R.id.iv_back_art)
    public void onViewClicked() {
        finish();
    }
}

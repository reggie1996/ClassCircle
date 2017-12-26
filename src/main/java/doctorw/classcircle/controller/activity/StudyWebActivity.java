package doctorw.classcircle.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import doctorw.classcircle.R;

public class StudyWebActivity extends BaseActivity {

    @Bind(R.id.webView)
    WebView webView;

    private String webUrl;
    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_study_web);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        webUrl = (String) bundle.get("weburl");
        webView.loadUrl(webUrl);
        webView.getSettings().setJavaScriptEnabled(true);
        // 如果页面中链接，如果希望点击链接继续在当前browser中响应，
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

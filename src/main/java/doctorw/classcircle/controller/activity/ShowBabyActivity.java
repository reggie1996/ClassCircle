package doctorw.classcircle.controller.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import doctorw.classcircle.R;

public class ShowBabyActivity extends BaseActivity {


    @Bind(R.id.iv_back_art)
    ImageView ivBackArt;
    @Bind(R.id.tv_title_art)
    TextView tvTitleArt;
    @Bind(R.id.wv_art)
    WebView wvArt;

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
        setContentView(R.layout.activity_show_baby);
        tvTitleArt.setText("晒娃");

//        wvArt.loadUrl("http://loger.iask.in/classcircle/show/index.html");
        Intent intent= new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse("http://loger.iask.in/classcircle/show/index.html");
        intent.setData(content_url);
        startActivity(intent);
//        wvArt.loadUrl("http://loger.iask.in/classcircle/show/index.html");
//        wvArt.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                // TODO Auto-generated method stub
//                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
//                view.loadUrl(url);
//                return true;
//            }
//        });
        //设置可自由缩放网页
//        wvArt.getSettings().setSupportZoom(true);
//        wvArt.getSettings().setBuiltInZoomControls(true);
//        wvArt.getSettings().setDisplayZoomControls(false);
        // 如果页面中链接，如果希望点击链接继续在当前browser中响应，
        // 而不是新开Android的系统browser中响应该链接，必须覆盖webview的WebViewClient对象
//        wvArt.setWebViewClient(new WebViewClient() {
//            public boolean shouldOverrideUrlLoading(WebView view, String url)
//            {
//                //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
//                view.loadUrl(url);
//                return true;
//            }
//        });

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

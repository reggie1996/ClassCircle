package doctorw.classcircle.controller.activity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import butterknife.Bind;
import butterknife.ButterKnife;
import doctorw.classcircle.R;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class TestActivity extends BaseActivity {



    @Bind(R.id.videoplayer)
    JCVideoPlayerStandard videoplayer;


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
        setContentView(R.layout.activity_test2);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        String local = path+"/11111.mp4";

        Log.e("test",local);
        videoplayer.setUp(local
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "嫂子不信");

//        videoplayer.setUp("http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4"
//                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "嫂子不信");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

//    @Override
//    protected void onActivityCreate(Bundle savedInstanceState) {
//        setContentView(R.layout.activity_test);
//        ShareSDK.initSDK(this);
//
//        shareBtn = (Button) findViewById(R.id.shareBtn);
//
//        shareBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                OnekeyShare oks = new OnekeyShare();
//                oks.setTitle("趣分享");
//                oks.setText("我为技术带盐，我骄傲，我自豪");
//                oks.setImageUrl("http://7sby7r.com1.z0.glb.clouddn.com/CYSJ_02.jpg");
//                oks.setTitleUrl("http://wwww.baidu.com");
//                oks.show(TestActivity.this);
//            }
//        });
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        ShareSDK.stopSDK(this);
//    }
}

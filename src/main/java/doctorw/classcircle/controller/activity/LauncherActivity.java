package doctorw.classcircle.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.hyphenate.chat.EMClient;

import doctorw.classcircle.R;
import doctorw.classcircle.model.Model;

public class LauncherActivity extends BaseActivity {

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            //当前Ativity已经退出 不处理handler中的消息
            if(isFinishing()){
                return;
            }
            //判断进入主页面还是登陆页面
            toMainOrLogin();
        }
    };

    private void toMainOrLogin() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
               //判断当前用户是否已经登录过
                if(EMClient.getInstance().isLoggedInBefore()){
                    //获取到当前用户信息（待处理）
                    //判断用户信息是否存在
                    //（1）不存在 跳转到登录界面
                    //（2）存在  跳转到主界面
                    Intent intent = new Intent(LauncherActivity.this,MainActivity.class);
                    startActivity(intent);
                }else {//没登陆过
                    //跳转到登陆界面
                    Intent intent = new Intent(LauncherActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                //结束当前界面
                finish();
            }
        });
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_launcher);
//
//        handler.sendMessageDelayed(Message.obtain(),2000);
////        new Handler().postDelayed(new Runnable() {
////            @Override
////            public void run() {
////                //在主线程中执行
////                startMainActivity();
////            }
////        }, 2000);
//    }


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
        setContentView(R.layout.activity_launcher);

        handler.sendMessageDelayed(Message.obtain(),2000);
    }

    /**
     * 启动主页面
     */
//    private void startMainActivity() {
//        Intent intent = new Intent(this,MainActivity.class);
//        startActivity(intent);
//        //关闭当前页面
//        finish();
//
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(null);
    }
}

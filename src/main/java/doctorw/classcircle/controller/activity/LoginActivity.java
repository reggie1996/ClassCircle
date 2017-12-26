package doctorw.classcircle.controller.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import doctorw.classcircle.R;
import doctorw.classcircle.model.Model;
import doctorw.classcircle.model.bean.ParentInfo;
import doctorw.classcircle.model.bean.TeacherInfo;
import doctorw.classcircle.model.bean.UserInfo;
import doctorw.classcircle.utils.Constants;
import doctorw.classcircle.utils.SpUtils;
import doctorw.classcircle.utils.Urls;
import okhttp3.Call;
import okhttp3.Response;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.tv_phone)
    EditText tvPhone;
    @Bind(R.id.tv_password)
    EditText tvPassword;


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//        ButterKnife.bind(this);
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
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.denglu, R.id.tv_forget, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.denglu:
                UserLogin();
//                startActivity(MainActivity.class);
                break;
            case R.id.tv_forget:
                forget();
                break;
            case R.id.tv_register:
                startActivity(ChooseActivity.class);
//                startActivity(RegisterActivity.class);
                break;
            default:
                break;
        }
    }

    //忘记密码
    private void forget() {
        showToast("怪我喽......");
    }


    //上传数据到自己的服务器
    private void UserLogin() {

        final String phone = getText(tvPhone);
        final String password = getText(tvPassword);

        // 2 校验输入的用户名和密码
        if(TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
            showToast("输入的手机或密码不能为空");
            return;
        }

        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                // （2）环信服务器登录
                EMClient.getInstance().login(phone, password, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        //对模型层做的处理
                        Model.getInstance().loginSuccess(new UserInfo(phone));
                        //保存用户账号信息到数据库（保存到服务器数据库）

                        // 保存用户账号信息到本地数据库
                        Model.getInstance().getUserAccountDao().addAccount(new UserInfo(phone));

//                        Model.getInstance().getParentUserAccountDao().addParentAccount(new ParentInfo(phone));
                        //切换到主线程
                        //自己的服务器注册
                        HashMap<String, String> params = new HashMap<>();
                        params.put("phone", phone);
                        params.put("password", password);
                        OkGo.post(Urls.URL_LOGIN)//
                                .tag(this)//
                                .params(params)
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(String s, Call call, Response response) {
                                        //上传成功返回用户登录界面，返回用户名和密码，直接填写到EditText

                                        if(s.contains("teacherName")){//教师用户
                                            TeacherInfo accountTeacher = JSON.parseObject(s, TeacherInfo.class);
                                            //保存用户Id；
                                            SpUtils.getInstance().save("userid",accountTeacher.getTeacherNo());
                                            Log.e("test",SpUtils.getInstance().getString("userid"," "));
// ClassCircleApplication.setAccounttype(Constants.TYPE_TEACHER);
                                            SpUtils.getInstance().save("type",Constants.TYPE_TEACHER);
                                            Model.getInstance().getTeacherUserAccountDao().addTeacherAccount(accountTeacher);
                                        }else{//家长用户
                                            showToast("parent-success");
                                            ParentInfo accountParent = JSON.parseObject(s, ParentInfo.class);
//                                            ClassCircleApplication.setAccounttype(Constants.TYPE_PARENT);
                                            SpUtils.getInstance().save("userid",accountParent.getParentNo());

                                            Log.e("test",SpUtils.getInstance().getString("userid"," "));

                                            SpUtils.getInstance().save("type",Constants.TYPE_PARENT);
                                            Model.getInstance().getParentUserAccountDao().addParentAccount(accountParent);
                                        }

                                    }
                                    @Override
                                    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                                        //这里回调上传进度(该回调在主线程,可以直接更新ui)
                                    }
                                });

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                //提是登录成功
                                showToast("登录成功");
                                //跳转到主界面
                                startActivity(MainActivity.class);
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onError(int i, final String s) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showToast("登录失败" + s.toString());
                            }
                        });
                    }

                    @Override
                    public void onProgress(int i, final String s) {

                    }
                });
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}



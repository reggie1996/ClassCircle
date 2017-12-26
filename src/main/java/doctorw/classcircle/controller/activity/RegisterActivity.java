package doctorw.classcircle.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.HashMap;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import doctorw.classcircle.R;
import doctorw.classcircle.model.Model;
import doctorw.classcircle.utils.Constants;
import doctorw.classcircle.utils.TimeCountUtil;
import doctorw.classcircle.utils.Urls;
import okhttp3.Call;
import okhttp3.Response;

import static com.mob.tools.utils.ResHelper.getStringRes;

public class RegisterActivity extends BaseActivity {
    @Bind(R.id.et_code)
    EditText etCode;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_nickname)
    EditText etNickname;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.et_password_confirm)
    EditText etPasswordConfirm;
    @Bind(R.id.bt_code)
    Button btCode;
    @Bind(R.id.cb_agree)
    CheckBox cbAgree;

    private boolean isAgree = false;
    private String phone;
    private String nickName;
    private String etpassword;
    private String etpasswordconfirm;
    private String code;
    private String type;

    EventHandler eh = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {
            Message msg = new Message();
            msg.arg1 = event;
            msg.arg2 = result;
            msg.obj = data;
            handler.sendMessage(msg);
        }
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            Log.e("event", "event=" + event);
            if (result == SMSSDK.RESULT_COMPLETE) {
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //验证码正确
                    register();

                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    Toast.makeText(getApplicationContext(), "验证码已经发送",
                            Toast.LENGTH_SHORT).show();
                    TimeCountUtil timeCountUtil = new TimeCountUtil(
                            RegisterActivity.this, 60000, 1000, btCode);
                    timeCountUtil.start();
                }
            } else {
                ((Throwable) data).printStackTrace();
                int resId = getStringRes(RegisterActivity.this,
                        "smssdk_network_error");
                Toast.makeText(RegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT)
                        .show();
                if (resId > 0) {
                    Toast.makeText(RegisterActivity.this, resId, Toast.LENGTH_SHORT)
                            .show();
                }
            }
        }
    };

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//        ButterKnife.bind(this);
//        initView();
//        SMSSDK.registerEventHandler(eh);
//    }

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        type = bundle.getString("type");

//        initView();
        SMSSDK.registerEventHandler(eh);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

        cbAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    isAgree = true;
                }else{
                    isAgree = false;
                }
            }
        });
    }

    //    private void initView() {
//        cbAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    isAgree = true;
//                }else{
//                    isAgree = false;
//                }
//            }
//        });
//    }
    @OnClick({R.id.back_register, R.id.bt_code, R.id.tv_xieyi, R.id.bt_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_register:
                //返回
                this.finish();
                break;
            case R.id.bt_code:
                getCode();
                //获取验证码
                break;
            case R.id.tv_xieyi:
                //查看协议
                checkXieYi();
                break;
            case R.id.bt_register:
                //注册
//                register();
                testcode();
                break;
            default:
                break;
        }
    }

    private void testcode() {
        if (!TextUtils.isEmpty(etCode.getText().toString())) {
            SMSSDK.submitVerificationCode("86", etPhone.getText().toString().trim(), etCode
                    .getText().toString());
        } else {
            Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
        }
    }
    private void getCode() {

//        if(judgeUser()){//用户已经存在 返回
//            showToast("此手机已经被注册");
       // return;
//        }else{
//
//        }
        if(!TextUtils.isEmpty(etPhone.getText().toString().trim())){
            SMSSDK.getVerificationCode("86",etPhone.getText().toString().trim());
            phone = etPhone.getText().toString().trim();
        }else{
            Toast.makeText(this, "电话错误", Toast.LENGTH_SHORT).show();
        }
    }
    //去后台判断用户是否已经注册
    private boolean judgeUser() {
        return true;
    }
    private void checkXieYi() {
        showToast("查看协议");
    }
    //注册
    private void register() {
        //1 获取输入的用户名和密码
        nickName = etNickname.getText().toString();
        etpassword = etPassword.getText().toString().trim();
        etpasswordconfirm = etPasswordConfirm.getText().toString().trim();
        //用户名和密码是否符合要求
        if (!isOk()) {
            return;
        }
        //3 去服务器注册
        registerService(type,phone,nickName,etpassword);

    }
    private boolean isOk() {
        boolean flag = true;
        if (TextUtils.isEmpty(nickName)) {
            showToast("用户名不能为空");
            flag = false;
        }
        if (TextUtils.isEmpty(etpassword) || TextUtils.isEmpty(etpasswordconfirm)) {
            showToast("密码不能为空");
            flag = false;
        }
//        if ((etPassword.equals(etPasswordConfirm))) {
//            flag = true;
//
//        }else{
//            showToast("两次密码不一致");
//            flag=false;
//        }
//        if(isAgree == false){
//            showToast("请同意用户注册协议");
//            return  false;
//        }

        return flag;
    }


//    public boolean judgephone(String phone) {
//        String str = "";
//        str = phone;
//        String pattern = "(13\\d|14[57]|15[^4,\\D]|17[678]|18\\d)\\d{8}|170[059]\\d{7}";
//        Pattern r = Pattern.compile(pattern);
//        Matcher m = r.matcher(str);
//        return m.matches();
//    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

    public void registerService(final String type, final String phone, final String nickName, final String etpassword){

        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                //环信服务器注册
                try {
                    EMClient.getInstance().createAccount(phone, etpassword);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("注册成功");
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("注册失败");
                        }
                    });
                }
                //自己的服务器注册
                HashMap<String, String> params = new HashMap<>();
                params.put("phone", phone);
                params.put("name", nickName);
                params.put("password", etpassword);
//                Log.d("register_phone",phone);
                String url;
                if(type.equals(Constants.TYPE_TEACHER)){
                    url = Urls.URL_TREGIS;
                }else if(type.equals(Constants.TYPE_PARENT)){
                    url= Urls.URL_PREGIS;
                }else{
                    return;
                }

                OkGo.post(url)//
                        .tag(this)//
                        .params(params)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(final String s, final Call call, final Response response) {
                                //上传成功返回用户登录界面，返回用户名和密码，直接填写到EditText
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivity(LoginActivity.class);
                                        removeActivity();
                                    }
                                });
                            }
                            @Override
                            public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                                //这里回调上传进度(该回调在主线程,可以直接更新ui)
                            }
                        });
            }
        });
    }
}

package doctorw.classcircle.controller.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.io.IOException;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import doctorw.classcircle.R;
import doctorw.classcircle.model.bean.RecordPlayer;
import doctorw.classcircle.model.bean.UPlayer;
import doctorw.classcircle.utils.SpUtils;
import doctorw.classcircle.utils.SystemUtils;
import doctorw.classcircle.utils.Urls;
import doctorw.classcircle.view.MyAlertDialog;
import doctorw.classcircle.view.RecordButton;
import okhttp3.Call;
import okhttp3.Response;

public class XQPublishCircleVoiceActivity extends BaseActivity {


    @Bind(R.id.et_input)
    EditText etInput;
    @Bind(R.id.bt_voice)
    Button btVoice;
    @Bind(R.id.btn_send_circle)
    Button btnSendCircle;
    @Bind(R.id.record_voice)
    RecordButton recordVoice;
    @Bind(R.id.tv_voicetime)
    TextView tvVoicetime;
    @Bind(R.id.ll_voice)
    LinearLayout llVoice;
    @Bind(R.id.et_title)
    EditText etTitle;

    private Context mContext;
    private String voicePath;
    private UPlayer voiceplayer;
    private int isPlay = 0;
    private RecordPlayer recordPlayer;
    private MediaPlayer md = new MediaPlayer();

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_publish_circle_voice_xq);
        mContext = this;
    }

    @Override
    protected void initListener() {
        recordVoice.setOnFinishedRecordListener(new RecordButton.OnFinishedRecordListener() {
            @Override
            public void onFinishedRecord(String audioPath) {
                Log.e("test", audioPath);
                voicePath = audioPath;
                try {
                    md.setDataSource(audioPath);
                    md.prepare();
                    tvVoicetime.setText("" + (md.getDuration() / 1000));
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                voiceplayer = new UPlayer(voicePath);
                recordPlayer = new RecordPlayer(mContext, audioPath, btVoice);
                llVoice.setVisibility(View.VISIBLE);
                recordVoice.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void initData() {

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

    @OnClick({R.id.iv_back, R.id.bt_voice, R.id.btn_send_circle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                exitHint();
                finish();
                break;

            case R.id.bt_voice://
                //0从头开始播放
                //1正在播放
                if (isPlay == 0) {//未播放
                    recordPlayer.playRecordFile();
                    isPlay = 1;
                } else if (isPlay == 1) {
                    recordPlayer.stopPalyer();
                    isPlay = 0;
                }
//                voiceplayer.start();
                break;

            case R.id.btn_send_circle://发送
                upLoadVoice();
                break;


        }
    }

    //上传声音
    private void upLoadVoice() {
        String content = getText(etInput);
        String title = getText(etTitle);
        if (TextUtils.isEmpty(title)){
            title ="音频动态";
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("userNo", SpUtils.getInstance().getString("userid", ""));
        params.put("text", content);
        params.put("title", title);
        params.put("interestId ", "11");

        if (!TextUtils.isEmpty(voicePath)) {
            params.put("record", voicePath);
        }

//            List<File> imagesfiles = new ArrayList<>();
//            for (int i = 0; i < list.size(); i++) {
//                File file = new File(list.get(i));
//                imagesfiles.add(file);
//            }

        // 重新瓶装一个DongTai 用setResult 返回到上一个活动，刷新数据
//        DongTai mDongTai = new DongTai();


//        File file = new File(Environment.getExternalStorageDirectory(), "head.jpg");
//        File file2 = new File(Environment.getExternalStorageDirectory(), "head.jpg");
//
//        showToast(Environment.getExternalStorageDirectory() + "head.jpg");
//        showToast(Environment.getExternalStorageDirectory() + "head.jpg");
//        showToast(Environment.getExternalStorageDirectory() + "head.jpg");
//        if (!file.exists() || !file2.exists()) {
//            Toast.makeText(this, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        List<File> files = new ArrayList<>();
//        files.add(file);
//        files.add(file2);
//        File videofile = null;
//        if (!TextUtils.isEmpty(moviePath)){
//            videofile = new File(moviePath);
//        }

        /*
         还有录音（record）和视频文件（video）
         */
        OkGo.post(Urls.URL_ADDINTERESTINFO)
                .params(params)
                .isMultipart(true)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                        showToast("发送成功");
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("dongtai",);
//                        Intent data = new Intent();
//                        data.putExtras(bundle);
//                        setResult(TYPE_DONGT);
                        finish();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }

                    @Override
                    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        super.upProgress(currentSize, totalSize, progress, networkSpeed);

                    }
                });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitHint();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void exitHint() {
        // 判断是否有改动
        if (etInput.getText().toString().length() > 0 || !TextUtils.isEmpty(voicePath)) {
            isKeyboardShownToHideKeyboard();
            // 弹出提示框提示是否确认退出
            MyAlertDialog.showDialogForIOS(this, "提示", "确认要舍弃内容并退出当前页面吗？", new MyAlertDialog.OnCallbackListener() {
                @Override
                public void onConfrimClick(AlertDialog dialog) {
                    dialog.dismiss();
                    XQPublishCircleVoiceActivity.this.finish();
                }

                @Override
                public void onCancelClick(AlertDialog dialog) {
                    dialog.dismiss();
                }
            });
        } else {
            finish();
        }
    }

    /**
     * 判断软键盘是否弹起如弹起则隐藏
     */
    private void isKeyboardShownToHideKeyboard() {
        if (SystemUtils.isKeyboardShown(etInput.getRootView())) {
            SystemUtils.hideKeyboard(this, etInput.getApplicationWindowToken());
        }
    }
}

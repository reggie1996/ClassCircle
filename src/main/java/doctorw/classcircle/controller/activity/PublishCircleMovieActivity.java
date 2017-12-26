package doctorw.classcircle.controller.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.io.File;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import doctorw.classcircle.R;
import doctorw.classcircle.utils.CommonUtil;
import doctorw.classcircle.view.MyAlertDialog;
import doctorw.classcircle.view.MyPopupWindow;
import doctorw.classcircle.utils.SpUtils;
import doctorw.classcircle.utils.SystemUtils;
import doctorw.classcircle.utils.Urls;
import fm.jiecao.jcvideoplayer_lib.JCUserAction;
import fm.jiecao.jcvideoplayer_lib.JCUserActionStandard;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import okhttp3.Call;
import okhttp3.Response;

public class PublishCircleMovieActivity extends BaseActivity {

    @Bind(R.id.et_input)
    EditText etInput;
    @Bind(R.id.jc_movie)
    JCVideoPlayerStandard jcMovie;
    @Bind(R.id.iv_addmoive)
    ImageView ivAddmoive;

    private String moviePath = "";
    private Bitmap bitmap= null;


    JCVideoPlayer.JCAutoFullscreenListener mSensorEventListener;
    //传感器
    SensorManager mSensorManager;

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_publish_circle_movie);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        String local = path + "/11.mp4";
//
//        Log.e("test", local);
//        jcMovie.setVisibility(View.VISIBLE);
//        jcMovie.setUp(local
//                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "嫂子不信");
    }

    @Override
    protected void initView() {
        JCVideoPlayer.setJcUserAction(new MyUserActionStandard());
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorEventListener = new JCVideoPlayer.JCAutoFullscreenListener();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back, R.id.jc_movie, R.id.iv_addmoive, R.id.btn_send_circle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                exitHint();
                finish();
                break;
            case R.id.jc_movie:
                break;
            case R.id.iv_addmoive:
                showPopupWindow();
                break;
            case R.id.btn_send_circle:
                upLoadMoive();
                break;
        }
    }

    private void showPopupWindow() {
        isKeyboardShownToHideKeyboard();
        MyPopupWindow popupWindow = new MyPopupWindow(this);
        String[] str = {"拍摄视频", "从本地选择"};
        popupWindow.showPopupWindowForFoot(str, new MyPopupWindow.Callback() {
            @Override
            public void callback(String text, int position) {
                switch (position) {
                    case 0: //拍摄视频
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(),
                                RecorderVideoActivity.class);
                        startActivityForResult(intent, 100);
                        break;
                    case 1: //从本地选择视频

                        Intent intent2 = new Intent(Intent.ACTION_PICK);
                        intent2.setType("video/*");
                        startActivityForResult(intent2, 101);

                        break;
                }
            }
        });
    }

    //对返回结果进行处理
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 100) { //拍摄视频
                Uri uri = data.getParcelableExtra("uri");
                String[] projects = new String[]{MediaStore.Video.Media.DATA,
                        MediaStore.Video.Media.DURATION};
                Cursor cursor = getContentResolver().query(uri, projects, null,
                        null, null);
                int duration = 0;
                String filePath = null;

                if (cursor.moveToFirst()) {
                    // 路径：MediaStore.Audio.Media.DATA
                    filePath = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                    // 总播放时长：MediaStore.Audio.Media.DURATION
                    duration = cursor
                            .getInt(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                    Toast.makeText(this, "路径 = " + filePath, Toast.LENGTH_SHORT)
                            .show();

                    if (!TextUtils.isEmpty(filePath)){
                        addMovie(filePath);
                    }

                    Log.e("test",filePath);
                }
                if (cursor != null) {
                    cursor.close();
                    cursor = null;
                }

            }else if (requestCode == 101){ //获取选择的视频
                Uri uri = data.getData();
                File file = getFileByUri(uri);
                MediaMetadataRetriever mmr=new MediaMetadataRetriever();//实例化MediaMetadataRetriever对象
                mmr.setDataSource(file.getAbsolutePath());
                Log.e("test_bendi",file.getAbsolutePath());
//                bitmap=mmr.getFrameAtTime();//获得视频第一帧的Bitmap对象
                String duration = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION);//时长(毫秒)
                Log.d("ddd","duration=="+duration);
                int int_duration= Integer.parseInt(duration);

                if(int_duration>45000){
                    Toast.makeText(getApplicationContext(), "视频时长超过45秒请重新选择", Toast.LENGTH_SHORT).show();;
                }

                addMovie(file.getAbsolutePath());
//                ivAddmoive.setImageBitmap(bitmap);
            }
        }
    }

    private void addMovie(String filePath) {
        moviePath = filePath ;
        jcMovie.setVisibility(View.VISIBLE);
        jcMovie.setUp(moviePath, JCVideoPlayer.SCREEN_LAYOUT_NORMAL,"");
        jcMovie.thumbImageView.setImageBitmap(CommonUtil.getVideoBitmap(filePath));
        ivAddmoive.setVisibility(View.GONE);
    }

    //上传视频动态
    private void upLoadMoive() {
            String content = getText(etInput);
            HashMap<String, String> params = new HashMap<>();
            params.put("no", SpUtils.getInstance().getString("userid", ""));
            params.put("text", content);
            params.put("classNo", SpUtils.getInstance().getString("groupid", ""));

        if (!TextUtils.isEmpty(moviePath)){
            params.put("video",moviePath);
        }


        /*
         还有录音（record）和视频文件（video）
         */
            OkGo.post(Urls.URL_ADDDONGTAI)
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

    /**
     * 判断软键盘是否弹起如弹起则隐藏
     */
    private void isKeyboardShownToHideKeyboard() {
        if (SystemUtils.isKeyboardShown(etInput.getRootView())) {
            SystemUtils.hideKeyboard(this, etInput.getApplicationWindowToken());
        }
    }

    public File getFileByUri(Uri uri) {
        String path = null;
        if ("file".equals(uri.getScheme())) {
            path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = this.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=").append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[] { MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATA }, buff.toString(), null,
                        null);
                int index = 0;
                int dataIdx = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    index = cur.getInt(index);
                    dataIdx = cur.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    path = cur.getString(dataIdx);
                }
                cur.close();
                if (index == 0) {
                } else {
                    Uri u = Uri.parse("content://media/external/images/media/" + index);
                    System.out.println("temp uri is :" + u);
                }
            }
            if (path != null) {
                return new File(path);
            }
        } else if ("content".equals(uri.getScheme())) {
            // 4.2.2以后
            String[] proj = { MediaStore.Images.Media.DATA };
            Cursor cursor = this.getContentResolver().query(uri, proj, null, null, null);
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                path = cursor.getString(columnIndex);
            }
            cursor.close();

            return new File(path);
        } else {
            Log.i("test", "Uri Scheme:" + uri.getScheme());
        }
        return null;
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
        if (etInput.getText().toString().length() > 0 || !TextUtils.isEmpty(moviePath)) {
            isKeyboardShownToHideKeyboard();
            // 弹出提示框提示是否确认退出
            MyAlertDialog.showDialogForIOS(this, "提示", "确认要舍弃内容并退出当前页面吗？", new MyAlertDialog.OnCallbackListener() {
                @Override
                public void onConfrimClick(AlertDialog dialog) {
                    dialog.dismiss();
                    PublishCircleMovieActivity.this.finish();
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


    @Override
    protected void onResume() {
        super.onResume();
        //注册传感器
        Sensor accelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(mSensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //取消注册传感器
        mSensorManager.unregisterListener(mSensorEventListener);
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }


    class MyUserActionStandard implements JCUserActionStandard {

        @Override
        public void onEvent(int type, String url, int screen, Object... objects) {
            switch (type) {
                case JCUserAction.ON_CLICK_START_ICON:
                    Log.i("USER_EVENT", "ON_CLICK_START_ICON" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_START_ERROR:
                    Log.i("USER_EVENT", "ON_CLICK_START_ERROR" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_START_AUTO_COMPLETE:
                    Log.i("USER_EVENT", "ON_CLICK_START_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_PAUSE:
                    Log.i("USER_EVENT", "ON_CLICK_PAUSE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_RESUME:
                    Log.i("USER_EVENT", "ON_CLICK_RESUME" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_SEEK_POSITION:
                    Log.i("USER_EVENT", "ON_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_AUTO_COMPLETE:
                    Log.i("USER_EVENT", "ON_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_ENTER_FULLSCREEN:
                    Log.i("USER_EVENT", "ON_ENTER_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_QUIT_FULLSCREEN:
                    Log.i("USER_EVENT", "ON_QUIT_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_ENTER_TINYSCREEN:
                    Log.i("USER_EVENT", "ON_ENTER_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_QUIT_TINYSCREEN:
                    Log.i("USER_EVENT", "ON_QUIT_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_TOUCH_SCREEN_SEEK_VOLUME:
                    Log.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_VOLUME" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_TOUCH_SCREEN_SEEK_POSITION:
                    Log.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;

                case JCUserActionStandard.ON_CLICK_START_THUMB:
                    Log.i("USER_EVENT", "ON_CLICK_START_THUMB" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserActionStandard.ON_CLICK_BLANK:
                    Log.i("USER_EVENT", "ON_CLICK_BLANK" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                default:
                    Log.i("USER_EVENT", "unknow");
                    break;
            }
        }
    }

}

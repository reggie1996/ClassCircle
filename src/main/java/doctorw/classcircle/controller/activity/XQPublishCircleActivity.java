package doctorw.classcircle.controller.activity;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import doctorw.classcircle.R;
import doctorw.classcircle.utils.Bimp;
import doctorw.classcircle.utils.FileUtils;
import doctorw.classcircle.utils.SpUtils;
import doctorw.classcircle.utils.SystemUtils;
import doctorw.classcircle.utils.Urls;
import doctorw.classcircle.view.MyAlertDialog;
import doctorw.classcircle.view.MyPopupWindow;
import doctorw.classcircle.view.NoScrollGridView;
import okhttp3.Call;
import okhttp3.Response;


public class XQPublishCircleActivity extends BaseActivity implements TextWatcher {

    private static final int TYPE_DONGT = 100;
    private static final int MAX_INPUT_LENGTH = 1000;// 最大输入长度
    private static final int TAKE_PICTURE = 0x000000;
    private NoScrollGridView mGridView;
    private EditText mEditText;
    private TextView mHintView;
    private Button mSendCircle;
    private GridAdapter mAdapter;
    private String mImageFileName;
    public static String mImagePath;
    private ImageView iv_back;

    private EditText etTitle;
    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_publish_circle_pic_xq);


    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitHint();
            }
        });
        mSendCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCircle();
            }
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        mSendCircle = (Button) findViewById(R.id.btn_send_circle);
        mEditText = (EditText) findViewById(R.id.et_input);
        mHintView = (TextView) findViewById(R.id.tv_hint);
        mGridView = (NoScrollGridView) findViewById(R.id.gv_gridview);
        etTitle = (EditText) findViewById(R.id.et_title);

        mEditText.addTextChangedListener(this);
        mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mAdapter = new GridAdapter(this);
        mAdapter.update();
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (arg2 == Bimp.mBmps.size()) {
                    showPopupWindow();
                } else {
                    Intent intent = new Intent(XQPublishCircleActivity.this, PhotoActivity.class);
                    intent.putExtra("ID", arg2);
                    startActivity(intent);
                }
            }
        });
    }

    private void showPopupWindow() {
        isKeyboardShownToHideKeyboard();
        MyPopupWindow popupWindow = new MyPopupWindow(this);
        String[] str = {"拍照", "从相册中选择"};
        popupWindow.showPopupWindowForFoot(str, new MyPopupWindow.Callback() {
            @Override
            public void callback(String text, int position) {
                switch (position) {
                    case 0:
                        photo();
                        break;
                    case 1:
                        Intent intent = new Intent(XQPublishCircleActivity.this, TestPicActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }


    @SuppressLint("HandlerLeak")
    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater; // 视图容器
        private int selectedPosition = -1;// 选中的位置
        private boolean shape;

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public GridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void update() {
            loading();
        }

        public int getCount() {
            return (Bimp.mBmps.size() + 1);
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_published_grida, parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView.findViewById(R.id.item_grida_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == Bimp.mBmps.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_addpic_unfocused));
                if (position == 9) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                holder.image.setImageBitmap(Bimp.mBmps.get(position));
            }
            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
        }

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        mAdapter.notifyDataSetChanged();
                        break;
                }
                super.handleMessage(msg);
            }
        };

        public void loading() {
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        if (Bimp.mMax == Bimp.mDrr.size()) {
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                            break;
                        } else {
                            if (Bimp.mDrr.size() <= 0)
                                return;
                            String path = Bimp.mDrr.get(Bimp.mMax);
                            Bitmap bm = null;
                            try {
                                bm = Bimp.revitionImageSize(path);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Bimp.mBmps.add(bm);
                            String newStr = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
                            FileUtils.saveBitmap(bm, "" + newStr);
                            Bimp.mMax += 1;
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        }
                    }
                }
            }).start();
        }
    }

    public String getString(String s) {
        String path = null;
        if (s == null)
            return "";
        for (int i = s.length() - 1; i > 0; i++) {
            s.charAt(i);
        }
        return path;
    }

    protected void onRestart() {
        mAdapter.update();
        super.onRestart();
    }

    /**
     * 拍照
     */
    public void photo() {
        // 随机缓存照片名
        mImageFileName = FileUtils.getFileNameForSystemTime(".jpg");
        // 首先判断SD卡是否存在
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), mImageFileName)));
            startActivityForResult(intent, TAKE_PICTURE);
        } else {
            showToast("内存卡不存在");
        }
    }


    //保存拍照名
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // 拍照
            if (requestCode == TAKE_PICTURE) {
                if (Bimp.mDrr.size() < 9 && resultCode == -1) {
                    File file = new File(Environment.getExternalStorageDirectory() + "/" + mImageFileName);
                    mImagePath = file.getPath();
                    Bimp.mDrr.add(mImagePath);
                }
            }
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        int length = mEditText.getText().toString().length();
        mHintView.setVisibility(length >= 1 ? View.VISIBLE : View.GONE);
        mHintView.setTextColor(length < MAX_INPUT_LENGTH ? Color.BLACK : Color.RED);
        mHintView.setText(String.valueOf(MAX_INPUT_LENGTH - length));
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    private void sendCircle() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < Bimp.mDrr.size(); i++) {
            String Str = Bimp.mDrr.get(i).substring(Bimp.mDrr.get(i).lastIndexOf("/") + 1, Bimp.mDrr.get(i).lastIndexOf("."));
            list.add(FileUtils.SDPATH + Str + ".JPEG");
            Log.e("test+path", FileUtils.SDPATH + Str + ".JPEG" + i);
        }
// for (int i = 0; i < Bimp.mDrr.size(); i++) {
//            String Str = Bimp.mDrr.get(i).substring(Bimp.mDrr.get(i).lastIndexOf("/") + 1, Bimp.mDrr.get(i).lastIndexOf("."));
//            list.add(FileUtils.SDPATH + Str + ".JPEG");
//            Log.e("test+path", FileUtils.SDPATH + Str + ".JPEG" + i);
//        }

        // 高清的压缩图片全部就在  list 路径里面了
        // 高清的压缩过的 bmp 对象  都在 Bimp.mBmps里面
        // 完成上传服务器后删除缓存

        Log.e("test_",list.toString());
        if ((list == null || list.size() == 0) && TextUtils.isEmpty(getText(mEditText))) {
            showToast("内容为空");
        } else {
            uploadDongtai(list);
        }


    }

    private void uploadDongtai(final List<String> list) {
        String content = getText(mEditText);
        String title = getText(etTitle);
        if (TextUtils.isEmpty(title)){
            title ="图片动态";
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("userNo", SpUtils.getInstance().getString("userid", ""));
        params.put("text", content);
        params.put("title", title);
        params.put("interestId ", "11");

        List<File> imagesfiles = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            File file = new File(list.get(i));
            imagesfiles.add(file);
        }

        /*
         还有录音（record）和视频文件（video）
         */
        OkGo.post(Urls.URL_ADDINTERESTINFO)
                .addFileParams("images", imagesfiles)
                .params(params)
                .isMultipart(true)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        FileUtils.deleteDir();
                        list.clear();
                        Bimp.mBmps.clear();
                        Bimp.mDrr.clear();
                        Bimp.mMax = 0;
                        showToast("发送成功");

//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("dongtai",);
//                        Intent data = new Intent();
//                        data.putExtras(bundle);
//                        setResult(TYPE_DONGT);
                        removeActivity();
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

    /**
     * 退出提示
     */

    private void exitHint() {
        // 判断是否有改动
        if (mEditText.getText().toString().length() > 0 || Bimp.mDrr.size() > 0 || !TextUtils.isEmpty(mImagePath)) {
            isKeyboardShownToHideKeyboard();
            // 弹出提示框提示是否确认退出
            MyAlertDialog.showDialogForIOS(this, "提示", "确认要舍弃内容并退出当前页面吗？", new MyAlertDialog.OnCallbackListener() {
                @Override
                public void onConfrimClick(AlertDialog dialog) {
                    dialog.dismiss();
                    FileUtils.deleteDir();
                    Bimp.mBmps.clear();
                    Bimp.mDrr.clear();
                    Bimp.mMax = 0;
                    XQPublishCircleActivity.this.finish();
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
        if (SystemUtils.isKeyboardShown(mEditText.getRootView())) {
            SystemUtils.hideKeyboard(this, mEditText.getApplicationWindowToken());
        }
    }
}

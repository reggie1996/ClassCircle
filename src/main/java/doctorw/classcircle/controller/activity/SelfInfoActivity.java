package doctorw.classcircle.controller.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import doctorw.classcircle.R;
import doctorw.classcircle.model.Model;
import doctorw.classcircle.utils.CommonUtil;
import doctorw.classcircle.utils.SpUtils;

public class SelfInfoActivity extends BaseActivity {
    @Bind(R.id.circleImageViewinfo)
    CircleImageView circleImageViewinfo;
    @Bind(R.id.tv_birth)
    TextView tvBirth;
    @Bind(R.id.tv_myname)
    TextView tvMyname;
    @Bind(R.id.tv_childname)
    TextView tvChildname;

    @Bind(R.id.ll_changemyname)
    LinearLayout llChangeMyName;
    private TextView tv_changetitle;
    private EditText et_changename;
    private static String PATH = "/sdcard/myHead/";// sd路径
    private Bitmap head;// 头像Bitmap
    private View popup_touxiang, popup_changename;
    private PopupWindow popup;
    private Context mContext;
    private boolean isChanged = false;

    //定义显示时间控件
    private Calendar calendar; //通过Calendar获取系统时间

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        calendar = Calendar.getInstance();
    }

    @Override
    protected void initView() {
        CommonUtil.initTouxiang(circleImageViewinfo);
        popup_touxiang = View.inflate(mContext, R.layout.popuwindow_changetouxiang, null);// 弹框
        popup_changename = View.inflate(mContext, R.layout.popup_changemename, null);// 改变姓名
        et_changename = (EditText) popup_changename.findViewById(R.id.et_changename);

        tvMyname.setText(SpUtils.getInstance().getString("myname","宵夜"));
        tvChildname.setText(SpUtils.getInstance().getString("childname","小野"));
        tv_changetitle = (TextView) popup_changename.findViewById(R.id.tv_changetitle);
    }

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_self_info);
        mContext = this;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_changechildname, R.id.ll_changemyname, R.id.ll_touxiang, R.id.ll_changephone, R.id.layoutdate, R.id.tv_exit, R.id.iv_back2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back2:
//                intent.putExtra("user","bcoder");
                // 设置返回结果为RESULT_OK, intent可以传入一些其他的参数, 在onActivityResult中的data中可以获取到
                if (isChanged == true) {
                    setResult(RESULT_OK);
                } else {
                    setResult(RESULT_CANCELED);
                }
                finish();
//                (new ClassCircleApplication()).removeActivity_(this);
                break;
            case R.id.ll_touxiang:
                initPopupWindow(view);
                break;
            case R.id.ll_changephone:
                showToast("换手机");
                break;
            case R.id.layoutdate:
                choosedate();
                //mDialogYearMonthDay.show(getFragmentManager(), "year_month_day");
                break;
            case R.id.ll_changemyname:
                changMyName(view,"请输入真实的姓名",tvMyname,1);
                break;
            case R.id.ll_changechildname:
                changMyName(view,"请输入孩子的姓名",tvChildname,2);
                break;
            case R.id.tv_exit:
                exit();
                break;
        }
    }

    private void changMyName(View view, String title, final TextView tv, final int type) {
        final PopupWindow popup2 = new PopupWindow(popup_changename, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ColorDrawable cd = new ColorDrawable(0x000000);
        tv_changetitle.setText(title);
        popup2.setBackgroundDrawable(cd);
        // 产生背景变暗效果
        WindowManager.LayoutParams lp = getWindow()
                .getAttributes();
        lp.alpha = 0.4f;
        getWindow().setAttributes(lp);
        // 点击popup之外的
        popup2.setOutsideTouchable(true);
        popup2.setFocusable(true);
        popup2.showAtLocation((View) view.getParent(), Gravity.CENTER
                | Gravity.CENTER_HORIZONTAL, 0, 0);

        // 不设置
        popup_changename.findViewById(R.id.tv_back).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        // popup.dismiss();
                        popup2.dismiss();
                    }
                });
        // 改变昵称
        popup_changename.findViewById(R.id.tv_certain).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        // popup.dismiss();
                        String name = et_changename.getText().toString().trim();
                        if (!TextUtils.isEmpty(name)) {
                            tv.setText(name);
                            if (type == 1){
                                SpUtils.getInstance().save("myname",name);
                            }else if(type == 2){
                                SpUtils.getInstance().save("childname",name);
                            }else{
                                return ;
                            }
                            et_changename.setText(""); // 每次出现都是空的

                            //上传服务器
                        } else {
                            // 不做事情
                        }
                        popup2.dismiss();
                    }
                });

        popup2.update();
        popup2.setOnDismissListener(new PopupWindow.OnDismissListener() {
            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow()
                        .getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }


    private long time = 0;

    private void exit() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                // 登录环信服务器退出登录
                EMClient.getInstance().logout(false, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        //去服务器退出
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // 更新ui显示
                                if (System.currentTimeMillis() - time > 2000) {//获得当前的时间
                                    time = System.currentTimeMillis();
                                    showToast("再点击一次退出应用程序");
                                } else {//点击在两秒以内
                                    removeALLActivity();//执行移除所以Activity方法
                                    startActivity(LoginActivity.class);
                                }
                            }
                        });
                        // 关闭DBHelper
//                        Model.getInstance().getDbManager().close();
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                // 更新ui显示
//                                startActivity(LoginActivity.class);
//                                finish();
//                            }
//                        });
                    }

                    @Override
                    public void onError(int i, final String s) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showToast("退出失败");
                            }
                        });
                    }

                    @Override
                    public void onProgress(int i, String s) {
                    }
                });
            }
        });
    }

    private void choosedate() {
        //通过自定义控件AlertDialog实现
        AlertDialog.Builder builder = new AlertDialog.Builder(SelfInfoActivity.this);
        View view = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_date, null);
        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
        //设置日期简略显示 否则详细显示 包括:星期周
        datePicker.setCalendarViewShown(false);
        //初始化当前日期
        calendar.setTimeInMillis(System.currentTimeMillis());
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), null);
        //设置date布局
        builder.setView(view);
        builder.setTitle("设置日期信息");
        builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //日期格式
                StringBuffer sb = new StringBuffer();
                sb.append(String.format("%d - %02d-%02d",
                        datePicker.getYear(),
                        datePicker.getMonth() + 1,
                        datePicker.getDayOfMonth()));
                tvBirth.setText(sb);
                //赋值后面闹钟使用
                dialog.cancel();
            }
        });
        builder.setNegativeButton("取  消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    //改变头像
    private void initPopupWindow(View v) {
        // TODO Auto-generated method stub
        popup = new PopupWindow(popup_touxiang,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        ColorDrawable cd = new ColorDrawable(0x000000);
        popup.setBackgroundDrawable(cd);
        // 产生背景变暗效果
        WindowManager.LayoutParams lp = getWindow()
                .getAttributes();
        lp.alpha = 0.4f;
        getWindow().setAttributes(lp);

        // 点击popup之外的
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);
        popup.showAtLocation((View) v.getParent(), Gravity.CENTER
                | Gravity.CENTER_HORIZONTAL, 0, 0);

        // 拍照
        popup_touxiang.findViewById(R.id.takephoto).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        // popup.dismiss();
                        Intent intent2 = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                                .fromFile(new File(Environment
                                        .getExternalStorageDirectory(),
                                        "head.jpg")));
                        startActivityForResult(intent2, 2);// 采用ForResult打开
                        popup.dismiss();
                    }
                });
        // 从相册中选择
        popup_touxiang.findViewById(R.id.choose).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        // popup.dismiss();
                        Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                        intent1.setDataAndType(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                "image/*");
                        startActivityForResult(intent1, 1);
                        popup.dismiss();
                    }
                });
        popup_touxiang.findViewById(R.id.quxiao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popup.dismiss();
            }
        });

        popup.update();
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow()
                        .getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                isChanged = true;
                // if (resultCode == RESULT_OK) {
                if (resultCode == -1) {
                    cropPhoto(data.getData());// 裁剪图片
                }
                break;
            case 2:
                isChanged = true;
                if (resultCode == -1) {
                    File temp = new File(Environment.getExternalStorageDirectory()
                            + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));// 裁剪图片
                }
                break;
            case 3:
                isChanged = true;
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
                        /**
                         * 上传服务器代码
                         */
                        setPicToView(head);// 保存在SD卡中
                        circleImageViewinfo.setImageBitmap(head);// 用ImageView显示出来
                    }
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 调用系统的裁剪
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(PATH);
        file.mkdirs();// 创建文件夹
        String fileName = PATH + "head.jpg";// 图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

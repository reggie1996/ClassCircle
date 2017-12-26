package doctorw.classcircle.controller.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.io.File;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import doctorw.classcircle.R;
import doctorw.classcircle.model.bean.CircleDetail;
import doctorw.classcircle.utils.SpUtils;
import doctorw.classcircle.utils.Urls;
import doctorw.classcircle.view.YEditText;
import okhttp3.Call;
import okhttp3.Response;

public class CreateInterestActivity extends BaseActivity {


    @Bind(R.id.profile_image)
    CircleImageView profileImage;
    @Bind(R.id.et_newgroup_name)
    YEditText etNewgroupName;
    @Bind(R.id.et_newgroup_desc)
    YEditText etNewgroupDesc;


    private String name;
    private String desc;

    private Context mContext;


    private final String PATH = Environment.getExternalStorageState() + "/circle/";// sd路径
    private final int IMAGE_CODE = 0;   //这里的IMAGE_CODE是自己任意定义的
    private File picFile;

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_create_interest);
        mContext = this;
    }

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back1, R.id.profile_image, R.id.bt_newgroup_create})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back1:
                finish();
                break;
            case R.id.profile_image:
                //点击选择头像
//                initPopupWindow(view);
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, IMAGE_CODE); //xxx 为自定义的一个整数
                break;
            case R.id.bt_newgroup_create:
                postInterest();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_CODE && resultCode == RESULT_OK) {
            if (requestCode == IMAGE_CODE) {
                if (resultCode != RESULT_OK) {
                    return;
                }
                if (data == null) return;

                Uri uri = data.getData();

                 picFile = getFileByUri(uri);

//                String path = getAbsoluteImagePath(uri);
                showToast(uri.toString() + picFile.getAbsolutePath());
                Glide.with(mContext).load(uri).into(profileImage);
            }
        }
    }


    private void postInterest() {
        HashMap<String, String> params = new HashMap<>();
        name = etNewgroupName.getText().toString();
        desc = etNewgroupDesc.getText().toString();
        if (TextUtils.isEmpty(name)) {
            showToast("兴趣圈名字不能为空");
            return;
        }
        params.put("userNo", SpUtils.getInstance().getString("userid", ""));
        params.put("interestName", name);
        params.put("desc", desc);

        OkGo.post(Urls.URL_CREATEINTEREST)
                .params(params)
                .params("headPic", picFile)
                .isMultipart(true)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        showToast("新建成功");
                        CircleDetail circleDetail = JSON.parseObject(s, CircleDetail.class);
                        showToast(circleDetail.toString());
                        Bundle bundle = new Bundle();
                        bundle.putString("focus", "YES");
                        bundle.putParcelable("circleDetail", circleDetail);
                        startActivity(XQCircleActivity.class, bundle);
                        removeActivity();
                        finish();
                    }
                });
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
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATA}, buff.toString(), null, null);
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
            String[] proj = {MediaStore.Images.Media.DATA};
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
}

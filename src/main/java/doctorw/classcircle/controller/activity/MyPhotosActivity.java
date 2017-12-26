package doctorw.classcircle.controller.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import doctorw.classcircle.R;
import doctorw.classcircle.controller.adapter.PhotosAdapter;
import doctorw.classcircle.model.bean.photosBean;
import doctorw.classcircle.utils.SpUtils;
import doctorw.classcircle.utils.Urls;
import okhttp3.Call;
import okhttp3.Response;

public class MyPhotosActivity extends BaseActivity {


    @Bind(R.id.lv_photos)
    ListView lvPhotos;
    @Bind(R.id.tv_show)
    TextView tvShow;

    private PhotosAdapter photosAdapter;

    private Context mContext;
    private List<photosBean> photos;

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_photos);
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
        photos = new ArrayList<>();
        OkGo.post(Urls.URL_ALBUM)
                .tag(this)
                .params("userNo", SpUtils.getInstance().getString("userid", ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        photos = JSON.parseArray(s, photosBean.class);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                photosAdapter = new PhotosAdapter(mContext, photos, R.layout.item_photos);
                                lvPhotos.setAdapter(photosAdapter);
                            }
                        });
                    }
                });
//        lvPhotos.setAdapter(photoAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @OnClick(R.id.iv_back2)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onResume() {
        initView();
        super.onResume();
    }

    @OnClick({R.id.iv_back2, R.id.tv_show})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back2:
                finish();
                break;
            case R.id.tv_show:
                startActivity(ShowBabyActivity.class);
                break;
        }
    }
}

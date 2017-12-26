package doctorw.classcircle.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import doctorw.classcircle.R;
import doctorw.classcircle.controller.adapter.ClassDongTaiAdapter;
import doctorw.classcircle.model.bean.DongTai;

public class DongTaiDetailActivity extends BaseActivity {

    @Bind(R.id.lv_one)
    ListView lvOne;
    private ListAdapter dtAdapter;

    private Context mContext;
    private List<DongTai> mDongTais ;
    private DongTai mDongTai;

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_dong_tai_detail);
        mContext = this;

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mDongTai = bundle.getParcelable("dongtai");
        showToast(mDongTai.toString());

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mDongTais = new ArrayList<>();
        mDongTais.add(mDongTai);
        dtAdapter = new ClassDongTaiAdapter(mContext,mDongTais,this);
        lvOne.setAdapter(dtAdapter);
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

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}

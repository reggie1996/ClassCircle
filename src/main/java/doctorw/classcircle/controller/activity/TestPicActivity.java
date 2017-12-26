package doctorw.classcircle.controller.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import java.io.Serializable;
import java.util.List;

import doctorw.classcircle.R;
import doctorw.classcircle.controller.adapter.ImageBucketAdapter;
import doctorw.classcircle.model.bean.ImageBucket;
import doctorw.classcircle.utils.AlbumHelper;
import doctorw.classcircle.utils.UiUtils;


/**
 * 选择相册页
 *
 * @author zhouyou
 */
public class TestPicActivity extends BaseActivity {
    public static final String EXTRA_IMAGE_LIST = "imagelist";
    private ImageBucketAdapter mAdapter;
    private List<ImageBucket> mDataList;
    private GridView mGridView;
    private AlbumHelper mHelper;
    public static Bitmap mBimap;

//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//	}


    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_image_bucket);
        mHelper = AlbumHelper.getHelper();
        mHelper.init(getApplicationContext());
        initData_();
        initView_();
    }
    @Override
    protected void initListener() {

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    /**
     * 初始化数据
     */
    private void initData_() {
        mDataList = mHelper.getImagesBucketList(false);
        mBimap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_addpic_unfocused);
    }

    /**
     * 初始化view视图
     */
    private void initView_() {
        setTitle(UiUtils.getString(R.string.album));
        mGridView = (GridView) findViewById(R.id.gridview);
        mAdapter = new ImageBucketAdapter(TestPicActivity.this, mDataList);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /**
                 * 通知适配器，绑定的数据发生了改变，应当刷新视图
                 */
                Intent intent = new Intent(TestPicActivity.this, ImageGridActivity.class);
                intent.putExtra(TestPicActivity.EXTRA_IMAGE_LIST, (Serializable) mDataList.get(position).imageList);
                startActivity(intent);
                finish();
            }
        });
    }

}

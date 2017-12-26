package doctorw.classcircle.controller.adapter.baseAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import doctorw.classcircle.R;
import doctorw.classcircle.controller.activity.ImagePagerActivity;
import doctorw.classcircle.controller.adapter.CircleGridAdapter;
import doctorw.classcircle.utils.cache.ImageLoaderCache;
import doctorw.classcircle.view.NoScrollGridView;


public class ViewHolder {
    private final DisplayImageOptions options;
    private SparseArray<View> mViews;

    private int mPosition;
    private View mConvertView;

    public  static  final  String HEADURL = "http://img2.imgtn.bdimg.com/it/u=2279370626,1858315540&fm=214&gp=0.jpg";
    private Context mContext;
    private ImageLoader imageLoader;

    public ViewHolder(Context context, ViewGroup parent, int layoutId,
                         int position) {

        this.mPosition = position;
        this.mContext = context;
        this.imageLoader = ImageLoader.getInstance();
        this.mViews = new SparseArray<View>();
        this.options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.ic_launcher)          // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_launcher)  // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_launcher)       // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)                          // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(0))  // 设置成圆角图片
                .build();                                   // 创建配置过得DisplayImageOption对象

        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        mConvertView.setTag(this);
    }

    // 入口函数,获得ViewHolder
    public static ViewHolder get(Context context, View convertView,
                                 ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position);
        } else {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;//更新position
            return holder;
        }
    }

    //通过viewId获取控件
    public  <T extends View > T getView(int viewId){
        View view = mViews.get(viewId);
        if(view ==null){
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T)view;
    }

    public View getmConvertView() {
        return mConvertView;
    }

    //写设置textview的方法,可以实现链式编程
    /**
     * 设置TextView的值
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId,String text){
        TextView tv = getView(viewId);
        tv.setText(text);
        return  this;
    }

//    /**
//     * 设置ImageView的值
//     * @param viewId
//     * @param resourceId
//     * @return
//     */
    public ViewHolder setImageResource(int viewId,int resId){
        ImageView iv = getView(viewId);
        iv.setImageResource(resId);
        return  this;
    }

    /**
     * 设置Bitmap
     * @param viewId
     * @param bitmap
     * @return
     */
    public ViewHolder setImageBitmap(int viewId,Bitmap bitmap){
        ImageView iv = getView(viewId);
        iv.setImageBitmap(bitmap);
        return  this;
    }

    /**
     * 加载网络图片
     * @param viewId
     * @param url
     * @return
     */
    public ViewHolder setImageURI(int viewId,String url){
        ImageView view  = getView(viewId);
        // 获取ImageLoader实例
        if (TextUtils.isEmpty(url)){
            Glide.with(mContext).load(R.drawable.baby).into(view);
        }
        imageLoader.displayImage(url,view,options);
//         view.setImageURI(url);
//        ImageLoaderCache.getInstance().DisplayImage(url,view);
        return  this;
    }

    public ViewHolder setCircleImageURI(int viewId,String url){
        CircleImageView view  = getView(viewId);
        // 获取ImageLoader实例
//        if (TextUtils.isEmpty(url)){
//            Glide.with(mContext).load(HEADURL).into(view);
//        }else{
//            imageLoader.displayImage(url,view,options);
//        }

//         view.setImageURI(url);
        ImageLoaderCache.getInstance().DisplayImage(HEADURL,view);
        return  this;
    }


    public ViewHolder setImages(int viewId, List<String> photosUrls){

        final String[] photos = photosUrls.toArray(new String[photosUrls.size()]);
        NoScrollGridView gridView = getView(viewId);
        gridView.setAdapter(new CircleGridAdapter(photos));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                enterPhotoDetailed(photos, position);
            }
        });
        return this;
    }

    protected void enterPhotoDetailed(String[] urls, int position) {
        Intent intent = new Intent(mContext, ImagePagerActivity.class);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        mContext.startActivity(intent);
    }
}

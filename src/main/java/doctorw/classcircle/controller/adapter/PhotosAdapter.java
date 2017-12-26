package doctorw.classcircle.controller.adapter;

import android.content.Context;

import java.util.List;

import doctorw.classcircle.R;
import doctorw.classcircle.controller.adapter.baseAdapter.CommonAdapter;
import doctorw.classcircle.controller.adapter.baseAdapter.ViewHolder;
import doctorw.classcircle.model.bean.photosBean;

/**
 * Created by asus on 2017/5/2.
 */

public class PhotosAdapter extends CommonAdapter<photosBean> {
    public PhotosAdapter(Context context, List<photosBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, photosBean photosBean) {
        holder.setText(R.id.tv_time,photosBean.getDate());
        holder.setImages(R.id.photos,photosBean.getPhotosUrl());
    }
}

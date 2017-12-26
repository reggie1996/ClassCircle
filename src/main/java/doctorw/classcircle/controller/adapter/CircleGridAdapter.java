package doctorw.classcircle.controller.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import doctorw.classcircle.R;
import doctorw.classcircle.utils.MeasureUtils;
import doctorw.classcircle.utils.UiUtils;
import doctorw.classcircle.utils.cache.ImageLoaderCache;

public class CircleGridAdapter extends BaseAdapter {
	
	private String[] mFiles;
	private LayoutInflater mLayoutInflater;
	
	public CircleGridAdapter(String[] files) {
		this.mFiles = files;
		mLayoutInflater = LayoutInflater.from(UiUtils.getContext());
	}

	@Override
	public int getCount() {
		return mFiles == null ? 0 : mFiles.length;
	}

	@Override
	public String getItem(int position) {
		return mFiles[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.item_gridview_circle,parent, false);
			holder.imageView = (ImageView) convertView.findViewById(R.id.album_image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// 根据屏幕宽度动态设置图片宽高
		int width = MeasureUtils.getWidth(UiUtils.getContext());
		int imageWidth = (width / 3 - 40);
		holder.imageView.setLayoutParams(new LayoutParams(imageWidth, imageWidth));
		String url = getItem(position);
//		ImageLoader.getInstance().displayImage(url, holder.imageView);
		ImageLoaderCache.getInstance().DisplayImage(url,holder.imageView);
			return convertView;
	}

	private static class ViewHolder {
		ImageView imageView;
	}
}

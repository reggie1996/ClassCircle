package doctorw.classcircle.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static doctorw.classcircle.utils.Constants.PATH_TX;

/**
 * Created by asus on 2017/4/20.
 */

public class CommonUtil {

    public static void initTouxiang(CircleImageView circleImageView) {
        Bitmap bt = BitmapFactory.decodeFile(PATH_TX + "head.jpg");// 从Sd中找头像，转换成Bitmap
        if (bt != null) {
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);// 转换成drawable
            circleImageView.setImageDrawable(drawable);
        } else {

            /**
             * 如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中
             *
             */
        }
    }

    public static  EMGroup getGroup(final String groupId) {
        EMGroup group = null;
        group = EMClient.getInstance().groupManager().getGroup(groupId);
        return group;
    }


    /**
     *
     * @param path
     *            媒体文件绝对路径
     * @return 返回媒体文件缩略图
     */
    public static Bitmap getVideoBitmap(String path) {
        Log.e("Icon", "path:" + path);
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(path);
            Bitmap frameAtTime = retriever.getFrameAtTime();
            return frameAtTime;
        } catch (Exception e) {
            return null;
        } finally {
            retriever.release();
        }

    }


    //获取网络视频的撕破罗图
    public static Bitmap createVideoThumbnail(String url) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int kind = MediaStore.Video.Thumbnails.MINI_KIND;
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(url, new HashMap<String, String>());
            } else {
                retriever.setDataSource(url);
            }
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, 300, 100,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() *
                (listAdapter.getCount() - 1));
        params.height += 5;// if without this statement,the listview will be a
// little short
        listView.setLayoutParams(params);
    }
}

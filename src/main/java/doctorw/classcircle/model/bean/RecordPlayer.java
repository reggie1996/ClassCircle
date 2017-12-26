package doctorw.classcircle.model.bean;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import doctorw.classcircle.R;

import static doctorw.classcircle.utils.UiUtils.getResources;

/**
 * Created by asus on 2017/5/7.
 */

public class RecordPlayer {
    private  MediaPlayer mediaPlayer;

    private Context mcontext;
    private String voicepath;

    private Button btVoice;

    public RecordPlayer(Context context, String path, Button btVoice) {
        this.mcontext = context;
        this.voicepath = path;
        this.btVoice = btVoice;
    }

    // 播放录音文件
    public void playRecordFile() {
        File file = new File(voicepath);
        if (file.exists() && file != null) {
            if (mediaPlayer == null) {
                Uri uri = Uri.fromFile(file);
                mediaPlayer = MediaPlayer.create(mcontext, uri);
            }
            mediaPlayer.start();

            btVoice.setText("正在播放");
            Drawable drawable = getResources().getDrawable(R.drawable.voice_stop);
            // 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            btVoice.setCompoundDrawables(drawable, null, null, null);
            //监听MediaPlayer播放完成
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer paramMediaPlayer) {
                    // TODO Auto-generated method stub
                    //弹窗提示
                    Drawable drawable = getResources().getDrawable(R.drawable.voice_play);
                    // 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    btVoice.setCompoundDrawables(drawable, null, null, null);
                    btVoice.setText("点击播放");
                    Toast.makeText(mcontext,
                            "播放完成",
                            Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    // 暂停播放录音
    public void pausePalyer() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            Log.e("TAG", "暂停播放");
        }

    }

    // 停止播放录音
    public void stopPalyer() {
        // 这里不调用stop()，调用seekto(0),把播放进度还原到最开始
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
            btVoice.setText("点击播放");
            Log.e("TAG", "停止播放");
        }
    }
}

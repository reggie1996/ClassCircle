package doctorw.classcircle.controller.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import android.widget.Toast;

import doctorw.classcircle.R;
import doctorw.classcircle.controller.adapter.ImageAdapter;
import doctorw.classcircle.view.GalleryView;

public class Grallery3DActivity extends Activity {

    private TextView tvTitle;
    private GalleryView gallery;
    private ImageAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grallery_layout);

        initRes();
    }

    private void initRes() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        gallery = (GalleryView) findViewById(R.id.mygallery);

        adapter = new ImageAdapter(this);
        adapter.createReflectedImages();
        gallery.setAdapter(adapter);

        gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvTitle.setText(adapter.titles[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        gallery.setOnItemClickListener(new OnItemClickListener() {            // ���õ���¼�����
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Grallery3DActivity.this, "img " + (position + 1) + " selected", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
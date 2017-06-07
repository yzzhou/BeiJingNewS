package myapplication.beijingnews.activity.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import myapplication.beijingnews.R;

public class PicassoSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picasso_sample);
        Uri data = getIntent().getData();
        final PhotoView photoView = (PhotoView) findViewById(R.id.iv_photo);

        Picasso.with(this)
                .load(data)
                .into(photoView);
    }
}

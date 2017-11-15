package com.cqupt.kindergarten.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alexvasilkov.gestures.views.GestureImageView;
import com.bumptech.glide.Glide;
import com.cqupt.kindergarten.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageDetailsActivity extends AppCompatActivity {

    @BindView(R.id.image_details_image)
    GestureImageView gestureImage;

    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        imageUrl = getIntent().getStringExtra("ImageUrl");
        gestureImage.getController().getSettings()
                .setMaxZoom(4f);
        Glide.with(getApplicationContext())
                .load(imageUrl)
                .placeholder(R.drawable.default_image)
                .into(gestureImage);
    }
}

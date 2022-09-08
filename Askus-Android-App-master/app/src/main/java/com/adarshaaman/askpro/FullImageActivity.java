package com.adarshaaman.askpro;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class FullImageActivity extends AppCompatActivity {
    public  static  Mentor mentor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.large_image);

        ImageView iv = findViewById(R.id.large_Image);

        Glide.with(this).load(mentor.getImageurl()).into(iv);



    }
}

package com.hkct.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ShopActivity extends AppCompatActivity {

    private ImageButton btnProductPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        btnProductPost = (ImageButton) findViewById(R.id.create_post);
        btnProductPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ShopActivity.this,ProductPostActivity.class);
                startActivity(intent);
            }
        });
    }

    public void addClick(View v) {
        startActivity(new Intent(this, ProductPostActivity.class));

        int version = Integer.valueOf(android.os.Build.VERSION.SDK);
        if (version >= 5) {

//            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
        this.finish();
    }




    }


package com.hkct.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ShopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
    }

    public void addClick(View v) {
        startActivity(new Intent(this, ProducePostActivity.class));

        int version = Integer.valueOf(android.os.Build.VERSION.SDK);
        if (version >= 5) {

//            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
        this.finish();
    }

}
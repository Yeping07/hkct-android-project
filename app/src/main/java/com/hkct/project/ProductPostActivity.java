package com.hkct.project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class ProductPostActivity extends AppCompatActivity {

    private ImageView mProductImage;
    private EditText mProductName;
    private EditText mProductPrice;
    private EditText mProductComment;
    private Button mBtnProductPost;
    private Uri productImageUri = null;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_post);

        mBtnProductPost = findViewById(R.id.btn_product_post);
        mProductImage = findViewById(R.id.post_product);
        mProductName = findViewById(R.id.productname);
        mProductPrice = findViewById(R.id.productprice);
        mProductComment = findViewById(R.id.productcomment);

        mProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(3,2)
                        .setMinCropResultSize(512, 512)
                        .start(ProductPostActivity.this);
            }
        });


    }
}
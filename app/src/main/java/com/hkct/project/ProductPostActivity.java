package com.hkct.project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

public class ProductPostActivity extends AppCompatActivity {

    private Button mProductPostbtn;
    private EditText mProductName;
    private EditText mProductPrice;
    private EditText mProductComment;
    private ImageView mProductPostImage;
    private ProgressBar mProductProgressBar;
    private Uri ProductpostImageUri = null;
    private StorageReference storage_Reference;
    private FirebaseFirestore Firestore;
    private FirebaseAuth auth;

    private ImageButton mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_post);

        mProductPostbtn = findViewById(R.id.btn_product_post);
        mProductName = findViewById(R.id.productname);
        mProductPrice = findViewById(R.id.productprice);
        mProductComment = findViewById(R.id.productcomment);
        mProductPostImage = findViewById(R.id.post_product);

        mProductProgressBar = findViewById(R.id.product_pBar);
        mProductProgressBar.setVisibility(View.INVISIBLE);

        mBackButton = findViewById(R.id.btn_back);

        storage_Reference = FirebaseStorage.getInstance().getReference();
        Firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        mProductPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(3,2)
                        .setMinCropResultSize(512, 512)
                        .start(ProductPostActivity.this);
            }
        });
        mProductPostbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProductProgressBar.setVisibility(View.VISIBLE);
                String productname = mProductName.getText().toString();
                String productprice = mProductPrice.getText().toString();
                String productcomment = mProductComment.getText().toString();
                if (!productname.isEmpty() && ProductpostImageUri != null) {
                    StorageReference postRef = storage_Reference.child("product_images").child(FieldValue.serverTimestamp().toString() + ".jpg");
                    postRef.putFile(ProductpostImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                postRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        HashMap<String, Object> postMap = new HashMap<>();
                                        postMap.put("prooduct_image", uri.toString());
                                        postMap.put("product_name", productname);
                                        postMap.put("product_price", productprice);
                                        postMap.put("product_comment", productcomment);
                                        postMap.put("time", FieldValue.serverTimestamp());

                                        Firestore.collection("Product").add(postMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                if (task.isSuccessful()) {
                                                    mProductProgressBar.setVisibility(View.INVISIBLE);
                                                    Toast.makeText(ProductPostActivity.this, "Your Post is Uploaded!!", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(ProductPostActivity.this, ShopDiscoverActivity.class));
                                                } else {
                                                    mProductProgressBar.setVisibility(View.INVISIBLE);
                                                    Toast.makeText(ProductPostActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                });
                            } else {
                                mProductProgressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(ProductPostActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    mProductProgressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(ProductPostActivity.this, "Please Add Image and Write Your Caption", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ProductPostActivity.this, ShopActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                ProductpostImageUri = result.getUri();
                mProductPostImage.setImageURI(ProductpostImageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, result.getError().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }




}
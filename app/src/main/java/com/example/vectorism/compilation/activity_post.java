package com.example.vectorism.compilation;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class activity_post extends AppCompatActivity {
    private static final int Image_Request = 1;
    EditText p_title;
    EditText p_desc;
    Button p_upload;
    ImageButton p_image;
    ImageView p_img_view;

    private Uri img_url;

    private DatabaseReference dbReference;
    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        p_title = (EditText) findViewById(R.id.post_title);
        p_desc = (EditText) findViewById(R.id.post_desc);
        p_upload = (Button) findViewById(R.id.post_upload);
        p_image = (ImageButton) findViewById(R.id.add_image);
        p_img_view = (ImageView) findViewById(R.id.img_view);

        storageReference = FirebaseStorage.getInstance().getReference("post_img");
        dbReference = FirebaseDatabase.getInstance().getReference();

        p_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        p_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });
    }
    private void openFileChooser(){
        Intent open_img = new Intent();
        open_img.setType("image/*");
        open_img.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(open_img, Image_Request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Image_Request && resultCode == RESULT_OK && data != null
                && data.getData() != null){
            img_url=data.getData();
            Picasso.with(this).load(img_url).into(p_img_view);
        }
    }

    private void savePost(){
        String description =  p_desc.getText().toString().trim();
        String title = p_title.getText().toString().trim();

    }
}

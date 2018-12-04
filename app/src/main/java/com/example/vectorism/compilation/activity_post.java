package com.example.vectorism.compilation;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class activity_post extends AppCompatActivity {
    private static final int Image_Request = 1;
    EditText p_title;
    EditText p_desc;
    Button p_upload;
    ImageButton p_image;
    ImageView p_img_view;
    Spinner kategori;

    private Uri img_url;

    private DatabaseReference dbReference;
    private StorageReference storageReference;

    private long count = 0;
    private String select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        p_title = (EditText) findViewById(R.id.post_title);
        p_desc = (EditText) findViewById(R.id.post_desc);
        p_upload = (Button) findViewById(R.id.post_upload);
        p_image = (ImageButton) findViewById(R.id.add_image);
        p_img_view = (ImageView) findViewById(R.id.img_view);
        kategori = (Spinner) findViewById(R.id.kategori);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categori, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kategori.setAdapter(adapter);
        kategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                select = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                select= adapterView.getItemAtPosition(0).toString();
            }
        });

        storageReference = FirebaseStorage.getInstance().getReference("post_img");
        dbReference = FirebaseDatabase.getInstance().getReference("post");

        p_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePost();
                finish();
            }
        });
        p_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                count = dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
        final String description =  p_desc.getText().toString().trim();
        final String title = p_title.getText().toString().trim();
        final String post_date = getDate();
        final String uid = AccountController.getUser().uID;
        final String kategori = select;

        final String img = System.currentTimeMillis()+"." + getFileExtension(img_url);

        StorageReference fileReference = storageReference.child(img);

        fileReference.putFile(img_url).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(activity_post.this, "Post Uploaded",Toast.LENGTH_LONG).show();
                Post post = new Post(description,title,kategori);
                Log.e("url",taskSnapshot.toString());
                post.setImg_url("post_img/"+img);
                post.setPost_date(post_date);
                dbReference.child(uid).child("post"+""+count+"").setValue(post);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity_post.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private String getDate() {
        long time;
        time = System.currentTimeMillis();
        SimpleDateFormat tanggal = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTimeInMillis(time);
        return tanggal.format(calendar.getTime());
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getMimeTypeFromExtension(cR.getType(uri));
    }

}

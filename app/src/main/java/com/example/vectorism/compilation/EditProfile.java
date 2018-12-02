package com.example.vectorism.compilation;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class EditProfile extends Fragment {

    final int image_request = 1;

    ImageView userImage;
    TextView username;
    TextView email;
    EditText pass;
    EditText pass2;
    CompUser cur_user;
    ImageView done;
    ImageView ubah;

    Uri img_url;

    FirebaseAuth auth;
    DatabaseReference db;
    StorageReference storage;
    Boolean upload = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        cur_user = AccountController.getUser();
        db = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance().getReference("profile_pictures");
        username = view.findViewById(R.id.ep_username);
        userImage = view.findViewById(R.id.ep_foto);
        email = view.findViewById(R.id.ep_email);
        pass = view.findViewById(R.id.ep_pass);
        pass2 = view.findViewById(R.id.ep_pass2);
        username.setText(cur_user.getUsername());
        if(cur_user.urlImage.equals("empty")){
            userImage.setImageResource(R.drawable.user_defaut_pic);
        }else{
            StorageReference sref = FirebaseStorage.getInstance().getReference();
            sref.child(cur_user.urlImage).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.with(getActivity()).load(uri).into(userImage);
                }
            });
        }
        auth = FirebaseAuth.getInstance();
        email.setText(auth.getCurrentUser().getEmail());
        done = view.findViewById(R.id.done_button);
        ubah = view.findViewById(R.id.ubah_foto_button);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Done();
            }
        });
        ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UbahFoto();
            }
        });
        return view;
    }

    private void Done(){
        String p = pass.getText().toString().trim();
        String p2 = pass2.getText().toString().trim();
        UbahPassword(p,p2);
        UploadFoto();
        ProfileController.setOpen_edit(false);
        getFragmentManager().beginTransaction().replace(R.id.fragment_container,new Profile()).commit();
    }

    private void UploadFoto(){
        if(!upload){
            return;
        }
        final String img = System.currentTimeMillis()+"."+getFileExtension(img_url);
        StorageReference fileRef = storage.child(img);
        fileRef.putFile(img_url).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                String img2 = "profile_pictures/"+img;
                cur_user.setUrlImage(img2);
                db.child("users").child(cur_user.uID).child("urlImage").setValue(img2);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),"Edit Profile Failed",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void UbahPassword(String p,String p2){
        if(p.isEmpty()||p2.isEmpty()){
            return;
        }
        if(!p.equals(p2)){
            Toast.makeText(getActivity(),"Password must same on both field",Toast.LENGTH_SHORT);
            return;
        }
        auth.getCurrentUser().updatePassword(p).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("PASSWORD","CHANGED");
            }
        });
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getMimeTypeFromExtension(cR.getType(uri));
    }

    private void UbahFoto(){
        Intent open_img = new Intent();
        open_img.setType("image/*");
        open_img.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(open_img, image_request);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == image_request && resultCode == getActivity().RESULT_OK && data != null
                && data.getData() != null){
            img_url=data.getData();
            upload = true;
            Picasso.with(getActivity()).load(img_url).into(userImage);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}

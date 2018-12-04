package com.example.vectorism.compilation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.Transaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Profile extends Fragment{

    TextView username;
    ImageView userimage;
    CompUser cur_user;

    ImageView edit_button;
    ImageView create_post;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        username = view.findViewById(R.id.op_userName);
        userimage = view.findViewById(R.id.op_userImage);
        cur_user = AccountController.getUser();
        username.setText(cur_user.username);
        if(cur_user.urlImage.equals("empty")){
            userimage.setImageResource(R.drawable.user_defaut_pic);
        }else{
            StorageReference sref = FirebaseStorage.getInstance().getReference();
            sref.child(cur_user.urlImage).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.with(getActivity()).load(uri).into(userimage);
                }
            });
        }
        edit_button = view.findViewById(R.id.p_editProfile);
        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileController.setOpen_edit(true);
                FragmentTransaction trans = getFragmentManager().beginTransaction();
                trans.replace(R.id.fragment_container,new EditProfile()).commit();
            }
        });
        create_post = view.findViewById(R.id.p_buatPost);
        create_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeToPost();
            }
        });
        return view;
    }
    private void changeToPost(){
        Intent intent = new Intent(getActivity(),activity_post.class);
        startActivity(intent);
    }

}

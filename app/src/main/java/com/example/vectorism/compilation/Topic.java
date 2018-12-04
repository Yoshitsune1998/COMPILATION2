package com.example.vectorism.compilation;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class Topic extends Fragment {

    ImageView topic_image;
    TextView topic_text;
    TextView desc;
    ImageView p_userImage;
    TextView p_userName;
    ExpandableHeightGridView gridView;
    StorageReference sref;
    String s1="";
    String s2="";

    Context mContext;

    View tempView = null;
    long com_count=0;
    Post post = null;

    DatabaseReference dbref;
    DatabaseReference dbcom;

    EditText commentar;
    Button send_com;

    List<Comment> commentList = new ArrayList<>();
    List<CompUser> userlist = new ArrayList<>();

    CompUser postUser;

    public Topic() {
        TopicController.setActive_topic(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_active_topic, container, false);
        tempView = view;
        s1 = getArguments().getString("S1");
        s2 = getArguments().getString("S2");
        dbref = FirebaseDatabase.getInstance().getReference("post");
        dbcom = FirebaseDatabase.getInstance().getReference("comment/"+s2);
        gridView = view.findViewById(R.id.grid_comment);

        gridView.setExpanded(true);
        DatabaseReference dbuser = FirebaseDatabase.getInstance().getReference("users");
        dbuser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot user:dataSnapshot.getChildren()){
                    if(user.getValue(CompUser.class).uID.equals(s1)){
                        postUser = user.getValue(CompUser.class);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        topic_image = view.findViewById(R.id.topicImage_a);
        topic_text = view.findViewById(R.id.topicText_a);
        desc = view.findViewById(R.id.p_userComment);

        setUserPost(view);

        commentar = view.findViewById(R.id.comment_text);
        send_com = view.findViewById(R.id.com_button);
        send_com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = commentar.getText().toString().trim();
                if(comment.isEmpty()){
                    Toast.makeText(getActivity(),"You must input a comment first",Toast.LENGTH_SHORT).show();
                }else{
                    commentSomething(comment);
                }
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot user: dataSnapshot.getChildren()){
                    for(DataSnapshot data: user.getChildren()){
                        if(user.getKey().equals(s1) && data.getKey().equals(s2)){
                            post = data.getValue(Post.class);
                            finalSetter(tempView);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        dbcom.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot com: dataSnapshot.getChildren()){
                    Comment c = com.getValue(Comment.class);
                    commentList.add(c);
                }
                getAllUsersProfile();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getAllUsersProfile(){
        DatabaseReference dbuser = FirebaseDatabase.getInstance().getReference("users");
        dbuser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(Comment u:commentList){
                    for (DataSnapshot users:dataSnapshot.getChildren()){
                        if(u.getUID().equals(users.getValue(CompUser.class).uID)){
                            CompUser us = users.getValue(CompUser.class);
                            userlist.add(us);
                        }
                    }

                }
                gridView.setAdapter(new CommentAdapter(commentList));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setUserPost(final View view){
        p_userImage = view.findViewById(R.id.p_userImage);
        p_userName = view.findViewById(R.id.p_userName);
    }

    private void commentSomething(String comment){
        getCommentNumber();
        DatabaseReference comRef = FirebaseDatabase.getInstance().getReference("comment");
        Comment comm = new Comment(AccountController.getUser().uID,comment);
        String commentNumber = "comment"+(com_count+1);
        comRef.child(s2).child(commentNumber).setValue(comm);
        Toast.makeText(getActivity(),"Your comment has been sent",Toast.LENGTH_SHORT).show();
        commentar.clearFocus();
        InputMethodManager in = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(commentar.getWindowToken(), 0);
        commentar.setText("");
    }

    private void getCommentNumber(){
        DatabaseReference cdb = FirebaseDatabase.getInstance().getReference("comment");
        cdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                com_count = dataSnapshot.child(s2).getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void finalSetter(View view){
        p_userName.setText(postUser.getUsername());
        topic_text.setText(post.getTitle());
        desc.setText(post.getDescription());

        sref = FirebaseStorage.getInstance().getReference();
        sref.child(post.getImg_url()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(mContext).load(uri).into(topic_image);
            }
        });
        sref.child(postUser.getUrlImage()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(mContext).load(uri).into(p_userImage);
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    private class CommentAdapter extends BaseAdapter {

        View tempView;
        List<Comment>comments;

        public CommentAdapter(List<Comment>comments){
            this.comments = comments;
        }

        @Override
        public int getCount() {
            return comments.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            tempView = getLayoutInflater().inflate(R.layout.comment_topic, parent, false);
            TextView name = tempView.findViewById(R.id.c_userName);
            TextView comment = tempView.findViewById(R.id.c_userComment);
            final ImageView image = tempView.findViewById(R.id.c_userImage);
            Log.e("TOTAL",comments.size()+" "+userlist.size());
            name.setText(userlist.get(i).getUsername());
            comment.setText(comments.get(i).getComment());
            StorageReference sref = FirebaseStorage.getInstance().getReference();
            sref.child(userlist.get(i).getUrlImage()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(mContext).load(uri).asBitmap().into(image);
                }
            });
            return tempView;
        }
    }

}

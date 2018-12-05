package com.example.vectorism.compilation;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

public class IosFragment extends Fragment {

    ExpandableHeightGridView gridView;
    List<Post> postIOS = new ArrayList<>();
    List<String>secret1 = new ArrayList<>();
    List<String>secret2 = new ArrayList<>();

    DatabaseReference dbIOS;
    Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ios, container, false);
        gridView = view.findViewById(R.id.grid_ios);
        dbIOS = FirebaseDatabase.getInstance().getReference("post");
        gridView.setExpanded(true);
        gridView.setExpanded(true);
        final Bundle args = new Bundle();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                args.putString("S1",secret1.get(i));
                args.putString("S2",secret2.get(i));
                Topic fragment = new Topic();
                fragment.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment,"TOPIC").commit();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        dbIOS.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postIOS.clear();
                for(DataSnapshot p : dataSnapshot.getChildren()){
                    for (DataSnapshot gameSp : p.getChildren()){
                        Post post = gameSp.getValue(Post.class);
                        if(post.getKategori().equals("IOS")){
                            postIOS.add(post);
                            secret1.add(p.getKey());
                            secret2.add(gameSp.getKey());
                        }
                    }
                }
                gridView.setAdapter(new IOSAdapter(postIOS));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        if(getView() == null){
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){

                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private class IOSAdapter extends BaseAdapter{

        private View tempView;
        private List<Post> postList;

        public IOSAdapter(List<Post> list){
            postList = list;
        }

        @Override
        public int getCount() {
            return postList.size();
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
            Post post = postList.get(i);
            if(!post.getKategori().equals("IOS")){
                return null;
            }
            tempView = getLayoutInflater().inflate(R.layout.topic_beranda, parent, false);
            TextView text = tempView.findViewById(R.id.topicText_b);
            final ImageView image = tempView.findViewById(R.id.topicImage_b);
            text.setText(post.getTitle());
            StorageReference sref = FirebaseStorage.getInstance().getReference();
            sref.child(post.getImg_url()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(mContext).load(uri).into(image);
                }
            });
            return tempView;
        }
    }

}

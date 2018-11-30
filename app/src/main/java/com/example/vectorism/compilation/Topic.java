package com.example.vectorism.compilation;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class Topic extends Fragment {

    ImageView topic_image;
    TextView topic_text;
    ImageView p_userImage;
    TextView p_userName;
    TextView p_userComment;
    ExpandableHeightGridView gridView;

    String ps_name="Abdul Yasir";
    String ps_comment="Bisa dibantu gak gan, ini saya bingung dibagian ini";
    int ps_image = R.drawable.user_defaut_pic;

    int[] user_images = new int[]{R.drawable.user_defaut_pic,R.drawable.user_defaut_pic,R.drawable.user_defaut_pic,R.drawable.user_defaut_pic,R.drawable.user_defaut_pic};
    String[] user_names = new String[]{"Bayu Kurr","Admin Wannabee","Santai Man","Abdul Yasir","Anonymous"};
    String[] user_comments = new String[]{
        "Up Gan h3h3h3",
        "Woi ini post melanggar peraturan, tak hapus ye",
        "Sans aja gan, dicoba pasti bisa kok wkwkkw",
        "Nah jadi gan, itu harusnya begini",
        "Tak hek akunmu"
    };

    public Topic() {
        TopicController.setActive_topic(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_active_topic, container, false);
        topic_image = view.findViewById(R.id.topicImage_a);
        topic_text = view.findViewById(R.id.topicText_a);
        String value = getArguments().getString("TEXT");
        topic_text.setText(value);
        topic_image.setImageResource(getArguments().getInt("IMAGE"));
        setUserPost(view);
        gridView = view.findViewById(R.id.grid_comment);
        gridView.setAdapter(new CommentAdapter());
        gridView.setExpanded(true);
        return view;
    }

    private void setUserPost(View view){
        p_userImage = view.findViewById(R.id.p_userImage);
        p_userName = view.findViewById(R.id.p_userName);
        p_userComment = view.findViewById(R.id.p_userComment);
        p_userImage.setImageResource(ps_image);
        p_userName.setText(ps_name);
        p_userComment.setText(ps_comment);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private class CommentAdapter extends BaseAdapter {

        View tempView;

        public CommentAdapter(){
        }

        @Override
        public int getCount() {
            return user_names.length;
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
            ImageView image = tempView.findViewById(R.id.c_userImage);
            name.setText(user_names[i]);
            comment.setText(user_comments[i]);
            image.setImageResource(user_images[i]);
            return tempView;
        }
    }

}

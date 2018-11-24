package com.example.vectorism.compilation;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class Topic extends Fragment {

    ImageView topic_image;
    TextView topic_text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_active_topic, container, false);
        topic_image = view.findViewById(R.id.topicImage_a);
        topic_text = view.findViewById(R.id.topicText_a);
        String value = getArguments().getString("TEXT");
        topic_text.setText(value);
        topic_image.setImageResource(getArguments().getInt("IMAGE"));
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}

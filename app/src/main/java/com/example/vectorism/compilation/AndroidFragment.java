package com.example.vectorism.compilation;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class AndroidFragment extends Fragment {

    GridView gridView;
    int[] topicImages = new int[]{R.drawable.post1};
    String[] topicText = new String[]{"Belajar Android Studio"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_android, container, false);
        gridView = view.findViewById(R.id.grid_android);
        gridView.setAdapter(new AndroidAdapter());
        final Bundle args = new Bundle();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                args.putString("TEXT",topicText[i]);
                args.putInt("IMAGE",topicImages[i]);
                Topic fragment = new Topic();
                fragment.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment,"TOPIC").commit();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private class AndroidAdapter extends BaseAdapter{

        View tempView;

        @Override
        public int getCount() {
            return topicImages.length;
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
            tempView = getLayoutInflater().inflate(R.layout.topic_beranda, parent, false);
            TextView text = tempView.findViewById(R.id.topicText_b);
            ImageView image = tempView.findViewById(R.id.topicImage_b);
            text.setText(topicText[i]);
            image.setImageResource(topicImages[i]);
            return tempView;
        }
    }

}

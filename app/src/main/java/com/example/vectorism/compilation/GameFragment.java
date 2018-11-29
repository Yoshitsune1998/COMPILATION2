package com.example.vectorism.compilation;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class GameFragment extends Fragment{

    ExpandableHeightGridView gridView;
    int[] topicImages = new int[]{R.drawable.post2,R.drawable.post2,R.drawable.post2,R.drawable.post2,R.drawable.post2};
    String[] topicText = new String[]{"Belajar Unity","Coding Rigidbody Unity","Membuat Game Flappy Bird",
            "Teknik dalam Pembuatan Game","Bagaimana Cara Membuat Pergerakan Animasi?"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        gridView = view.findViewById(R.id.grid_game);
        gridView.setAdapter(new GameAdapter());
        gridView.setExpanded(true);
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
    }

    private class GameAdapter extends BaseAdapter{

        View tempView;

        public GameAdapter(){
        }

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

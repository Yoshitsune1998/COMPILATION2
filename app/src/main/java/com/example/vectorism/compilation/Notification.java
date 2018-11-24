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

public class Notification extends Fragment {

    GridView gridView;
    int[] profilePictures = new int[]{R.drawable.user_defaut_pic,R.drawable.user_defaut_pic,R.drawable.user_defaut_pic};
    String[] names = new String[]{"Bayu Kurniawan","Didin Pro Dota","Bayu Kurniawan"};
    String whatDo = "commented on your post";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        gridView = view.findViewById(R.id.grid_notif);
        gridView.setAdapter(new NotificationAdapter());
//        final Bundle args = new Bundle();
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
//                args.putString("TEXT", names[i]);
//                args.putInt("IMAGE", profilePictures[i]);
//                Topic fragment = new Topic();
//                fragment.setArguments(args);
//                getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment,"NOTIF").commit();
//            }
//        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private class NotificationAdapter extends BaseAdapter {

        View tempView;

        public NotificationAdapter(){
        }

        @Override
        public int getCount() {
            return profilePictures.length;
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
            tempView = getLayoutInflater().inflate(R.layout.notification_view, parent, false);
            TextView text = tempView.findViewById(R.id.notif_text);
            ImageView image = tempView.findViewById(R.id.notif_image);
            text.setText(names[i]+" "+whatDo);
            image.setImageResource(profilePictures[i]);
            return tempView;
        }
    }

}

package com.example.vectorism.compilation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class Bantuan extends Fragment {

    View[] helps = new View[3];
    ImageView[] buttons = new ImageView[3];
    boolean[] click = new boolean[]{false,false,false};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bantuan, container, false);

        helps[0] = view.findViewById(R.id.help1a);
        helps[1] = view.findViewById(R.id.help2a);
        helps[2] = view.findViewById(R.id.help3a);

        buttons[0] = view.findViewById(R.id.ddb1);
        buttons[1] = view.findViewById(R.id.ddb2);
        buttons[2] = view.findViewById(R.id.ddb3);

        for (int i=0;i<helps.length;i++){
            final int x = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(click[x]){
                        helps[x].setVisibility(View.GONE);
                        click[x] = false;
                        buttons[x].setImageResource(R.drawable.dropdownbutton);
                    }else{
                        helps[x].setVisibility(View.VISIBLE);
                        click[x] = true;
                        buttons[x].setImageResource(R.drawable.dropupbutton);
                    }
                }
            });
        }

        return view;
    }

}

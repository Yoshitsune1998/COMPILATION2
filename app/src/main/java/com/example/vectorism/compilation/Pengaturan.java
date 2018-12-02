package com.example.vectorism.compilation;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;


public class Pengaturan extends Fragment implements CompoundButton.OnCheckedChangeListener{

    SwitchCompat sw;
    ImageView privacy;
    ImageView security;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pengaturan, container, false);
        sw = (SwitchCompat)view.findViewById(R.id.switchNotif);
        sw.setOnCheckedChangeListener(this);
        privacy = view.findViewById(R.id.privacy_view);
        security = view.findViewById(R.id.login_security_view);
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PengaturanController.isOpen = true;
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,new PrivacyView()).commit();
            }
        });
        security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PengaturanController.isOpen = true;
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,new SecurityView()).commit();
            }
        });
        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.d("SWITCH","CHECKED");
    }
}

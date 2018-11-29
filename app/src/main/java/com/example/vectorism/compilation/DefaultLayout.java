package com.example.vectorism.compilation;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class DefaultLayout extends AppCompatActivity{

    DrawerLayout drawer;
    ActionBarDrawerToggle toogle;
    BottomNavigationView btn_navbar;
    ImageView userImage;
    NavigationView navigationView;

    Notification c_notif = null;
    Pengaturan c_pgtrn = null;
    Bantuan c_bantu = null;
    Profile c_profile = null;
    GameFragment f_game = null;
    AndroidFragment f_andro = null;
    IosFragment f_ios = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.default_activity);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.beranda:
                        navigationView.setCheckedItem(R.id.beranda);
                        VisibilityBottomNav(true);
                        BerandaChanger(BerandaController.getActive_navbar(),true);
                        break;
                    case R.id.notif:
                        VisibilityBottomNav(false);
                        if(c_notif==null){
                            c_notif = new Notification();
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                c_notif).commit();
                        navigationView.setCheckedItem(R.id.notif);
                        break;
                    case R.id.pengaturan:
                        VisibilityBottomNav(false);
                        if(c_pgtrn==null){
                            c_pgtrn = new Pengaturan();
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                c_pgtrn).commit();
                        navigationView.setCheckedItem(R.id.pengaturan);
                        break;
                    case R.id.bantu:
                        VisibilityBottomNav(false);
                        if(c_bantu==null){
                            c_bantu = new Bantuan();
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                c_bantu).commit();
                        navigationView.setCheckedItem(R.id.bantu);
                        break;
                    case R.id.logout:
//                        soon
                        break;
                }
                drawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });
        View navbar_view = findViewById(R.id.included_navbar);
        btn_navbar = (BottomNavigationView) navbar_view.findViewById(R.id.navbar);
        btn_navbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.gamenav:
                        BerandaChanger(0,false);
                        Log.e("TAG","0");
                        break;
                    case R.id.androidnav:
                        BerandaChanger(1,false);
                        Log.e("TAG","1");
                        break;
                    case R.id.iosnav:
                        BerandaChanger(2,false);
                        Log.e("TAG","2");
                        break;
                }
                return false;
            }
        });
        View headerlayout = navigationView.getHeaderView(0);
        userImage = (ImageView) headerlayout.findViewById(R.id.userimage_nav);
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeNavbar();
                unCheckNavbar();
                VisibilityBottomNav(false);
                if(c_profile==null){
                    c_profile = new Profile();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,c_profile).commit();
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toogle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        drawer.addDrawerListener(toogle);
        toogle.syncState();
        if(savedInstanceState == null){
            VisibilityBottomNav(true);
            BerandaChanger(0,false);
            navigationView.setCheckedItem(R.id.beranda);
        }
  }

    private void BerandaChanger(int num,boolean change){
        int index = BerandaController.getActive_navbar();
        Log.e("INDEX",index+"");
        if(!change){
            if(num==index){
                return;
            }
        }
        BerandaController.setActive_navbar(num);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment selectedFragment = getActiveBeranda(num);
        if(selectedFragment!=null){
            transaction.replace(R.id.fragment_container,selectedFragment,"FRAGMENT CHANGED").commit();
        }
    }

    private Fragment getActiveBeranda(int num){
        Fragment selectedFragment = null;
        switch (num){
            case 0 :
                if(f_game==null){
                    f_game = new GameFragment();
                }
                selectedFragment = f_game;
                btn_navbar.getMenu().findItem(R.id.gamenav).setChecked(true);
                break;
            case 1:
                if(f_andro==null){
                    f_andro = new AndroidFragment();
                }
                selectedFragment = f_andro;
                btn_navbar.getMenu().findItem(R.id.androidnav).setChecked(true);
                break;
            case 2:
                if(f_ios==null){
                    f_ios = new IosFragment();
                }
                selectedFragment = f_ios;
                btn_navbar.getMenu().findItem(R.id.iosnav).setChecked(true);
                break;
            default:
                Log.d("FRAGMENT","NOT FOUND");
                break;
        }
        return selectedFragment;
    }

    private void closeNavbar(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    private void unCheckNavbar(){
        int length = navigationView.getMenu().size();
        for (int i=0;i<length;i++){
            navigationView.getMenu().getItem(i).setChecked(false);
        }
    }

    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            closeNavbar();
        }else{
            if(TopicController.getActive_topic()){
                FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                Fragment view = getActiveBeranda(BerandaController.getActive_navbar());
                Log.e("BACK",""+view);
                trans.replace(R.id.fragment_container,getActiveBeranda(BerandaController.getActive_navbar())).commit();
                TopicController.setActive_topic(false);
                Log.e("BACK","PRESSED");
            }else{
                super.onBackPressed();
            }
        }
    }

    private void VisibilityBottomNav(boolean condition){
        android.widget.RelativeLayout.LayoutParams lp = null;
        if(condition){
            lp =new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            btn_navbar.setVisibility(View.VISIBLE);
        }else{
            lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,0);
            btn_navbar.setVisibility(View.INVISIBLE);
        }
        btn_navbar.setLayoutParams(lp);
    }
}

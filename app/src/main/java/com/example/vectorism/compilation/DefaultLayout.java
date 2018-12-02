package com.example.vectorism.compilation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class DefaultLayout extends AppCompatActivity{

    DrawerLayout drawer;
    ActionBarDrawerToggle toogle;
    BottomNavigationView btn_navbar;
    ImageView userImage;
    TextView userName;
    NavigationView navigationView;
    TextView title;

    FirebaseAuth auth;
    public static CompUser cuser;

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
        title = findViewById(R.id.logoTitle);
        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this,Login.class));
        }
        AccountController.nullAccount();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.beranda:
                        navigationView.setCheckedItem(R.id.beranda);
                        VisibilityBottomNav(true);
                        title.setText("Beranda");
                        BerandaChanger(BerandaController.getActive_navbar(),true);
                        break;
                    case R.id.notif:
                        VisibilityBottomNav(false);
                        title.setText("Notifikasi");
                        if(c_notif==null){
                            c_notif = new Notification();
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                c_notif).commit();
                        navigationView.setCheckedItem(R.id.notif);
                        break;
                    case R.id.pengaturan:
                        VisibilityBottomNav(false);
                        title.setText("Pengaturan");
                        if(c_pgtrn==null){
                            c_pgtrn = new Pengaturan();
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                c_pgtrn).commit();
                        navigationView.setCheckedItem(R.id.pengaturan);
                        break;
                    case R.id.bantu:
                        VisibilityBottomNav(false);
                        title.setText("Bantuan");
                        if(c_bantu==null){
                            c_bantu = new Bantuan();
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                c_bantu).commit();
                        navigationView.setCheckedItem(R.id.bantu);
                        break;
                    case R.id.logout:
                        navigationView.setCheckedItem(R.id.logout);
                        Logout();
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
                title.setText("Profil");
                if(c_profile==null){
                    c_profile = new Profile();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,c_profile).commit();
            }
        });
        userName = (TextView) headerlayout.findViewById(R.id.username_nav);
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("users");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot compdata:dataSnapshot.getChildren()){
                    if(compdata.getValue(CompUser.class).uID.equals(auth.getUid())){
                        cuser = compdata.getValue(CompUser.class);
                        setBerandaProfile();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toogle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        drawer.addDrawerListener(toogle);
        toogle.syncState();
        if(savedInstanceState == null){
            VisibilityBottomNav(true);
            title.setText("Beranda");
            BerandaChanger(0,false);
            navigationView.setCheckedItem(R.id.beranda);
        }
  }

    private void setBerandaProfile(){
        AccountController.setUser(cuser);
        userName.setText(cuser.username);
        if(cuser.urlImage.equals("empty")){
            userImage.setImageResource(R.drawable.user_defaut_pic);
        }else{
            StorageReference sref = FirebaseStorage.getInstance().getReference();
            sref.child(cuser.urlImage).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.with(DefaultLayout.this).load(uri).into(userImage);
                }
            });
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
            if(TopicController.getActive_topic()) {
                FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                Fragment view = getActiveBeranda(BerandaController.getActive_navbar());
                Log.e("BACK", "" + view);
                trans.replace(R.id.fragment_container, view).commit();
                TopicController.setActive_topic(false);
                Log.e("BACK", "PRESSED");
            }else if(ProfileController.getOpen_edit()){
                FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                Fragment view = new Profile();
                trans.replace(R.id.fragment_container,view).commit();
                ProfileController.setOpen_edit(false);
            }else if(PengaturanController.isOpen){
                FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                Fragment view = new Pengaturan();
                trans.replace(R.id.fragment_container,view).commit();
                PengaturanController.isOpen = false;
            }
            else
            {
                super.onBackPressed();
            }
        }
    }

    private void VisibilityBottomNav(boolean condition){
        android.widget.RelativeLayout.LayoutParams lp;
        if(condition){
            lp =new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            btn_navbar.setVisibility(View.VISIBLE);
        }else{
            lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,0);
            btn_navbar.setVisibility(View.INVISIBLE);
        }
        btn_navbar.setLayoutParams(lp);
    }

    private void Logout(){
        if(auth.getCurrentUser()!=null){
            auth.signOut();
            finish();
            AccountController.nullAccount();
            Intent intent = new Intent(this,Login.class);
            startActivity(intent);
        }
    }

}

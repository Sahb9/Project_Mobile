package com.android.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.myapplication.Others.BottomNavigationBehavior;
import com.android.myapplication.Others.DarkModePrefManager;
import com.android.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView txtTime;
    RelativeLayout layout_schedule,layout_fitness,layout_timeManagement,layout_Eating,layout_BMI,layout_reading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // bật dark mode
        setDarkMode(getWindow());
        setContentView(R.layout.activity_main);
        addControl();
        addEvent();

        xuLySchool();

    }
    void addControl()
    {
        layout_schedule = findViewById(R.id.layout_schedule);
        layout_fitness = findViewById(R.id.layout_fitness);
        layout_timeManagement=findViewById(R.id.layout_timeManagement);
        layout_Eating = findViewById(R.id.layout_eating);
        layout_BMI = findViewById(R.id.layout_BMI);
        layout_reading = findViewById(R.id.layout_reading);
    }
    void addEvent()
    {
        layout_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ScheduleActivity.class);
                Toast.makeText(MainActivity.this, "Schedule", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        layout_BMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BMIActivity.class);
                Toast.makeText(MainActivity.this,"Dăng nhập thành công",Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });
    }
    void xuLySchool()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
  //      setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //thanh bên trái
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        // set up hình ảnh và username
        getDataFromLogin(navigationView);
        //
        navigationView.setNavigationItemSelectedListener(this);
        // thanh bên dưới
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        bottomNavigationView.setSelectedItemId(R.id.navigationHome);




    }
    //school

    private BottomNavigationView bottomNavigationView;


    final private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigationMyProfile:
                    return true;
                case R.id.navigationMyCourses:
                    return true;
                case R.id.navigationHome:
                    return true;
                case  R.id.navigationSearch:
                    return true;
                case  R.id.navigationMenu:
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.openDrawer(GravityCompat.START);
                    return true;
            }
            return false;
        }
    };
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_dark_mode) {
            //code for setting dark mode
            //true for dark mode, false for day mode, currently toggling on each click
            DarkModePrefManager darkModePrefManager = new DarkModePrefManager(this);
            darkModePrefManager.setDarkMode(!darkModePrefManager.isNightMode());
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            recreate();

        }
        else if(id == R.id.nav_signOut)
        {
            FirebaseAuth.getInstance().signOut();
            Intent intent =new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(MainActivity.this, "Dang xuat thanh cong", Toast.LENGTH_SHORT).show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //create a seperate class file, if required in multiple activities
    public void setDarkMode(Window window){
        if(new DarkModePrefManager(this).isNightMode()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            changeStatusBar(0,window);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            changeStatusBar(1,window);
        }
    }
    public void changeStatusBar(int mode, Window window){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(this.getResources().getColor(R.color.contentBodyColor));
            //white mode
            if(mode==1){
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }
    public void getDataFromLogin(NavigationView navigationView)
    {
        View headerView =navigationView.getHeaderView(0);
        ImageView ivHeaderPhoto = headerView.findViewById(R.id.imageViewAvatar);
        ivHeaderPhoto.setImageResource(R.drawable.anh_user1);
        TextView textHeader = headerView.findViewById(R.id.textviewtitleHeader);


        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("dulieubundle");
        if(bundle!=null)
        {
            String ten = bundle.getString("username");

            textHeader.setText("Hello swan4567890@gmail.com"   );
        }
    }
    public void DungChoCalendar()
    {

//        txtTime = findViewById(R.id.textViewTime);
//        Calendar calendar = Calendar.getInstance();
//        txtTime.setText(calendar.getTime() + "\n");
//        txtTime.append(calendar.get(Calendar.DATE)+"\n");
//        txtTime.append(calendar.get(Calendar.MONTH)+"\n");// tháng đúng phải +1
//        txtTime.append(calendar.get(Calendar.YEAR)+"\n");//
//
//        //format ngày tháng năm
//        SimpleDateFormat dinhDangNgay = new SimpleDateFormat("dd/MM/yyyy");
//        txtTime.append(dinhDangNgay.format(calendar.getTime()));
//
//        txtTime.append(calendar.get(Calendar.HOUR)+"\n");// định dạng 12h
//        txtTime.append(calendar.get(Calendar.HOUR_OF_DAY)+"\n");// định dạng 24h
//
//        SimpleDateFormat dinhDangGio = new SimpleDateFormat("hh:mm:ss ");
//        txtTime.append(dinhDangGio.format(calendar.getTime()));
    }
}
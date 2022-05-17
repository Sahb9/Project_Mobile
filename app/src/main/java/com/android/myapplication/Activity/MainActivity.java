package com.android.myapplication.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.myapplication.DAO.AccountDAO;
import com.android.myapplication.Fragment.HabitsFragment;
import com.android.myapplication.Fragment.HistoryFragment;
import com.android.myapplication.Fragment.HomeFragment;
import com.android.myapplication.Fragment.UserFragment;
import com.android.myapplication.Others.BottomNavigationBehavior;
import com.android.myapplication.Others.ChangeDarkMode;
import com.android.myapplication.Others.DarkModePrefManager;
import com.android.myapplication.R;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TextView txtTime;
    private AccountDAO accountDAO = new AccountDAO();
    private ChangeDarkMode changeDarkMode;
    private UserFragment userFragment = new UserFragment(this);
    private RelativeLayout layout_schedule,layout_fitness,layout_timeManagement,layout_Eating,layout_BMI,layout_reading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // bật dark mode
        //setDarkMode(getWindow());
        changeDarkMode = new ChangeDarkMode(this);
        changeDarkMode.setModeScreen();
        setContentView(R.layout.activity_main);
        replaceFragment(new HomeFragment(this));
        hanhdleSchool();
    }

    public void hanhdleSchool() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //thanh bên trái
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        // set up hình ảnh và username
        //accountDAO.getInforUserHeader(navigationView,this);
        getInforUserHeader(navigationView,this);

        navigationView.setNavigationItemSelectedListener(this);
        // thanh bên dưới
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

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
            switch (item.getItemId()) {
                case R.id.navigationMyProfile:
                    replaceFragment(userFragment);
                    return true;
                case R.id.navigationMyCourses:
                    replaceFragment(new HistoryFragment(MainActivity.this));
                    return true;
                case R.id.navigationHome:
                    replaceFragment(new HomeFragment(MainActivity.this));
                    return true;
                case  R.id.navigationSearch:
                    replaceFragment(new HabitsFragment());
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
//            DarkModePrefManager darkModePrefManager = new DarkModePrefManager(this);
//            darkModePrefManager.setDarkMode(!darkModePrefManager.isNightMode());
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//            recreate();
            if(changeDarkMode.PRIVATE_MODE) {
                // thì chuyển sang chế độ sáng
                changeDarkMode.PRIVATE_MODE = false;
                changeDarkMode.modeNight();
            }// nếu đang là chế độ sáng
            else {
                // thì chuyển sang chế độ tối
                changeDarkMode.PRIVATE_MODE = true;
                changeDarkMode.modeDark();
            }
        } else if(id == R.id.nav_signOut) {
            if(changeDarkMode.PRIVATE_MODE) {
                // nhưng đây là trg hợp đặc biệt nên cho cố định là nền tối
                changeDarkMode.PRIVATE_MODE = false;
                // thì chuyển sang chế độ sáng
                changeDarkMode.modeNight();
            }
            // nếu đang là chế độ sáng
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(MainActivity.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    //create a seperate class file, if required in multiple activities
    public void setDarkMode(Window window) {
        if(new DarkModePrefManager(this).isNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            changeStatusBar(0,window);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            changeStatusBar(1,window);
        }
    }

    public void changeStatusBar(int mode, Window window) {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(this.getResources().getColor(R.color.contentBodyColor));
            //white mode
            if(mode == 1) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction =  getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_content_main,fragment);
        fragmentTransaction.commit();
    }

    public void DungChoCalendar() {
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery();
            Intent intent =  new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            activityOpenFolder.launch(intent);
        } else {
            Toast.makeText(this, "Bạn không đồng ý cho mở folder", Toast.LENGTH_SHORT).show();
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        activityOpenFolder.launch(Intent.createChooser(intent,"Select Picture"));
    }

    ActivityResultLauncher<Intent> activityOpenFolder  = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent intent = result.getData();

                    if(intent!=null) {
                        Uri uri = intent.getData();
                        userFragment.setUri(uri);
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                            userFragment.setBitmapImageView(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    public static final int KITKAT_VALUE = 1002;

    public void openMediaDocuments() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            startActivityForResult(intent, KITKAT_VALUE);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            startActivityForResult(intent, KITKAT_VALUE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == KITKAT_VALUE) {

        }
    }
//    ActivityResultLauncher<Intent> activityOpenFolder  = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    Intent intent = result.getData();
//                    if(intent!=null)
//                    {
//                        Uri uri = intent.getData();
//                        userFragment.setUri(uri);
//                        try {
//                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
//                            userFragment.setBitmapImageView(bitmap);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }
//            });

    public void getInforUserHeader(NavigationView navigationView, Context context) {
        View headerView =navigationView.getHeaderView(0);
        ImageView ivHeaderPhoto = headerView.findViewById(R.id.imageViewAvatar);
        //   ivHeaderPhoto.setImageResource(R.drawable.anh_user1);
        TextView textName = headerView.findViewById(R.id.textviewtitleHeader);
        TextView textEmail = headerView.findViewById(R.id.textviewtitleEmail);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
            if(name == null) {
                textName.setVisibility(View.GONE);
            } else {
                textName.setVisibility(View.VISIBLE);
            }

            textName.setText("Name: "+name);
            textEmail.setText("Email: "+ email);
            Glide.with(context).load(photoUrl).error(R.drawable.user1).into(ivHeaderPhoto);
        }
    }
}
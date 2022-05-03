package com.android.myapplication.DAO;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.myapplication.Activity.LoginActivity;
import com.android.myapplication.Activity.MainActivity;
import com.android.myapplication.Entity.Account;
import com.android.myapplication.Models.DatabaseActivity;
import com.android.myapplication.Models.Database;
import com.android.myapplication.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executor;

public class AccountDAO extends AppCompatActivity {
    private DatabaseReference mDatabase;



    public void AddUserAuth( String email, String password)
    {
        FirebaseAuth auth;
        auth =  FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password);
//                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Toast.makeText(context, "Authentication success",
//                                    Toast.LENGTH_SHORT).show();
//
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Toast.makeText(context, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                });

    }
    public void addInforUser(String phone,String email,String username,String password)
    {
        //FirebaseAuth auth=FirebaseAuth.getInstance() ;
        //add vào authentication
        //auth.createUserWithEmailAndPassword(email, password);
        //add information
        mDatabase= FirebaseDatabase.getInstance().getReference("Account");

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //lấy size và tăng thêm 1
                Long a= (Long) snapshot.child("AccountSize").getValue();
                a++;
                //set value mới lại cho AccountSize
                mDatabase= FirebaseDatabase.getInstance().getReference("Account").child("AccountSize");
                mDatabase.setValue( a );
                //

                //Đăng nhập trước rồi mới lấy đc Uid
                FirebaseAuth finalAuth =  FirebaseAuth.getInstance();

                //Cho đăng nhập cái đã
                finalAuth.signInWithEmailAndPassword(email, password);
                //
                FirebaseUser finalUser = finalAuth.getCurrentUser();
                if(finalUser != null)
                {

                    String userid =  finalAuth.getInstance().getCurrentUser().getUid(); //lấy id bằng chuỗi của firebase
                    mDatabase= FirebaseDatabase.getInstance().getReference("Account").child(userid) ;
                }
                else {
                    mDatabase= FirebaseDatabase.getInstance().getReference("Account").child(a.toString()) ;

                }
                Account account = new Account(phone,email,username,password);
                mDatabase.setValue(account);

                //

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public boolean SignIn(String email,String password) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

         mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information


                            //finishAffinity();
                        } else {

                        }
                    }
                });
        return true;
    }
    public void sendNewPasswordbyEmail(String email)
    {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = email;

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Log.d(TAG, "Email sent.");
                        }
                    }
                });
    }
    public void getInforUserHeader(NavigationView navigationView,Context context)
    {
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
            if(name ==null)
            {
                textName.setVisibility(View.GONE);
            }
            else
                textName.setVisibility(View.VISIBLE);

            textName.setText("Name: "+name);
            textEmail.setText("Email: "+ email);
            Glide.with(context).load(photoUrl).error(R.drawable.user1).into(ivHeaderPhoto);
        }
    }

}

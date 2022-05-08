package com.android.myapplication.DAO;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
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
import com.android.myapplication.callback.CallBack;
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

    public void AddUserAuth(String email, String password, CallBack<Boolean> callBack) {
        FirebaseAuth auth =  FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        callBack.onCallBack(task.isSuccessful());
                    }
                });

    }

    public void addInformationUser(String phone, String email, String username, String password) {
        //FirebaseAuth auth=FirebaseAuth.getInstance() ;
        //add vào authentication
        //auth.createUserWithEmailAndPassword(email, password);
        //add information
        mDatabase = FirebaseDatabase.getInstance().getReference("Account");

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long a = (Long)snapshot.child("AccountSize").getValue();
                a++;

                mDatabase= FirebaseDatabase.getInstance().getReference("Account").child("AccountSize");
                mDatabase.setValue(a);

                FirebaseAuth finalAuth = FirebaseAuth.getInstance();
                // Want to get user id
                finalAuth.signInWithEmailAndPassword(email, password);
                FirebaseUser finalUser = finalAuth.getCurrentUser();

                if(finalUser != null) {
                    String uerId =  finalAuth.getInstance().getCurrentUser().getUid();
                    Log.d("Add Information User", "onDataChange: " + uerId);
                    mDatabase= FirebaseDatabase.getInstance().getReference("Account").child(uerId) ;
                } else {
                    mDatabase= FirebaseDatabase.getInstance().getReference("Account").child(a.toString()) ;
                }

                Account account = new Account(phone,email,username,password);
                mDatabase.setValue(account);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void SignIn(String email, String password, CallBack<Boolean> callBack) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    callBack.onCallBack(task.isSuccessful());
                }
            });
    }

    public void sendNewPasswordbyEmail(String email) {
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
    public void getInforUserHeader(NavigationView navigationView,Context context) {
        View headerView =navigationView.getHeaderView(0);
        ImageView ivHeaderPhoto = headerView.findViewById(R.id.imageViewAvatar);
        //ivHeaderPhoto.setImageResource(R.drawable.anh_user1);
        TextView textName = headerView.findViewById(R.id.textviewtitleHeader);
        TextView textEmail = headerView.findViewById(R.id.textviewtitleEmail);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
            if(name == null) {
                textName.setVisibility(View.GONE);
            } else
                textName.setVisibility(View.VISIBLE);

            textName.setText("Name: "+name);
            textEmail.setText("Email: "+ email);
            Glide.with(context).load(photoUrl).error(R.drawable.user1).into(ivHeaderPhoto);
        }
    }
}

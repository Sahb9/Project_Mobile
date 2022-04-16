package com.android.myapplication.DAO;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.myapplication.Entity.Account;
import com.android.myapplication.Models.DatabaseActivity;
import com.android.myapplication.Models.Database;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executor;

public class AccountDAO {
    private DatabaseReference mDatabase;

    public boolean checkLogin(Activity activity,String username , String password)
    {
        DatabaseActivity.sqLiteDatabase= Database.initDatabase(activity,DatabaseActivity.DBName);
        Cursor cursor = DatabaseActivity.sqLiteDatabase.rawQuery("SELECT * FROM Account WHERE userName = ? and passWord= ?",new String[]{username,password});
        if(cursor.getCount() !=0)
        {
            return true;
        }
        return false;
    }
    public void addUser(Activity activity, String username, String password, String phoneNumber, String email)
    {
        // cach 3
        DatabaseActivity.sqLiteDatabase= Database.initDatabase(activity,DatabaseActivity.DBName);
        ContentValues row = new ContentValues();
        row.put("userName",username);
        row.put("passWord",password);
        row.put("phone",phoneNumber);
        row.put("email",email);

        DatabaseActivity.sqLiteDatabase.insert("Account","userName,passWord,phone,email",row);
        // insert("Account","userName,passWord,phone,email",row);

    }

    public void AddUserAuth( String email, String password)
    {
        FirebaseUser user;
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
//        user = auth.getInstance().getCurrentUser();
//
//        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                .setDisplayName(username)
//                .build();
//
//        user.updateProfile(profileUpdates);
    }


}

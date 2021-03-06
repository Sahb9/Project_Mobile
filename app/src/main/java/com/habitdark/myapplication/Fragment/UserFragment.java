package com.habitdark.myapplication.Fragment;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.habitdark.myapplication.Activity.MainActivity;
import com.habitdark.myapplication.DAO.AccountDAO;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.habitdark.myapplication.R;


public class UserFragment extends Fragment {
    public Context context;
    private ProgressDialog progressDialog;
    public Uri uri;
    MainActivity mainActivity;
    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public static final int MY_REQUEST_CODE=10;




    public UserFragment() {
        // Required empty public constructor
    }
    public UserFragment(Context context) {
        // Required empty public constructor
        this.context=context;
    }

    ImageView imgUser;
    EditText edtName;
    TextView txtUserid,txtEmailVerified,edtEmail;
    Button btnUpdate,btnChangeUserPassword;
    AccountDAO accountDAO =new AccountDAO();
    DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user, container, false);
        mainActivity = (MainActivity) getActivity();
        progressDialog = new ProgressDialog(getActivity());

        addControl(view);
        addEvents();
        getInforUserPorfolio();

        //get data to View
        return view;
    }

    public void DialogLogin() {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // ph???i ????? tr?????c setContent View
        dialog.setContentView(R.layout.dialog_custom);
        Window window = dialog.getWindow();

        if(window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity = Gravity.CENTER;
        window.setAttributes(windowAttribute);

        EditText edtOldPassword = dialog.findViewById(R.id.editTextOldPassword);
        EditText edtNewPassword = dialog.findViewById(R.id.editTextNewPassword);
        Button btnDongY = dialog.findViewById(R.id.buttonChange);
        Button btnhuy = dialog.findViewById(R.id.buttonCancel);

        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();

                String newPassword = edtNewPassword.getText().toString().trim();
                String oldPassword = edtOldPassword.getText().toString().trim();
                String userId =  FirebaseAuth.getInstance().getCurrentUser().getUid();

                mDatabase = FirebaseDatabase.getInstance().getReference("Account").child(userId);

                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String userIdofFireBase= (String) snapshot.child("passWord").getValue();
                        if(userIdofFireBase.equals(oldPassword)) {
                            // l??u trong authentication
                            sendNewPassword(newPassword);
                            // sau ???? l??u trong real-time Database
                            mDatabase= FirebaseDatabase.getInstance().getReference("Account").child(userId).child("passWord") ;
                            mDatabase.setValue(newPassword);
                            progressDialog.dismiss();
                            Toast.makeText(context, "Change your password successfully", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(context, "Cannot Change your password ", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

        //        dialog.setTitle("Th??ng b??o");
        //
        //        dialog.setCanceledOnTouchOutside(false);//  Click b??n ngo??i c?? h???y lu??n hay kh??ng
        //        EditText edtOldPassword = dialog.findViewById(R.id.editTextOldPassword);
        //        EditText edtNewPassword = dialog.findViewById(R.id.editTextNewPassword);
        //        Button btnDongY = dialog.findViewById(R.id.buttonDongY);
        //        Button btnhuy = dialog.findViewById(R.id.buttonHuy);
        //        dialog.show();
    }

    public void sendNewPassword(String password)
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String newPassword = password;

        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Log.d(TAG, "User password updated.");
                        }
                    }
                });
    }

    void addControl(View view) {
        imgUser = view.findViewById(R.id.imageViewUser);
        edtEmail = view.findViewById(R.id.editTextEmailUser);
        edtName = view.findViewById(R.id.editTextNameUser);
        btnUpdate = view.findViewById(R.id.buttonUpdateUser);
        txtUserid= view.findViewById(R.id.textViewUserid);
        txtEmailVerified=view.findViewById(R.id.textViewEmailVerify);
        btnChangeUserPassword = view.findViewById(R.id.buttonChangePasswordUser);
    }

    void addEvents() {
        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},88);
                updateUser();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userid =  edtName.getText().toString().trim();
                presentAlertDialog(userid,uri);
               // updateInforUser(userid,uri);
//                NavigationView navigationView = mainActivity.findViewById(R.id.nav_view);
//
//                mainActivity.getInforUserHeader(navigationView,getActivity());
            }
        });

        btnChangeUserPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogLogin();
            }
        });

    }

    private void updateUser() {
        if(mainActivity == null)
            return;

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mainActivity.openGallery();
            return;
        }

        if(getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            mainActivity.openGallery();
            mainActivity.openMediaDocuments();
        } else {
            String [] permissions ={Manifest.permission.READ_EXTERNAL_STORAGE};
            getActivity().requestPermissions(permissions,MY_REQUEST_CODE);
        }
    }

    public void getInforUserPorfolio() {
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

            edtName.setText(name);
            edtEmail.setText(email);
            txtUserid.setText(uid);
            //edtEmail.setVisibility(View.INVISIBLE);
            if(emailVerified) {
                txtEmailVerified.setText("Yes");
            } else {
                txtEmailVerified.setText("No");
            }

            //n???u m?? l???i ho???c ko c?? th?? set up h??nh m???c ?????nh
            Glide.with(this).load(photoUrl).error(R.drawable.user1).into(imgUser);
        }
    }
    public void updateInforUser(String username, Uri uri) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .setPhotoUri(uri)
                .build();

        progressDialog.show();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
//                            Log.d(TAG, "User profile updated.");
                        }
                    }
                });
    }

    public void setBitmapImageView (Bitmap bitmapImageView) {
        imgUser.setImageBitmap(bitmapImageView);
    }
    private void presentAlertDialog(String username, Uri uri)
    {
        AlertDialog.Builder allertDialog= new AlertDialog.Builder(mainActivity);
        allertDialog.setTitle("Notice!");
        allertDialog.setIcon(R.mipmap.ic_launcher);
        allertDialog.setMessage("Do you want to update this information? ");
        allertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                updateInforUser(username,uri);
                progressDialog.show();
            }
        });
        allertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        progressDialog.dismiss();
        allertDialog.show();
    }
}
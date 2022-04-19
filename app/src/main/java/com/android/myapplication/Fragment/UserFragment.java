package com.android.myapplication.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.myapplication.Activity.MainActivity;
import com.android.myapplication.DAO.AccountDAO;
import com.android.myapplication.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserFragment() {
        // Required empty public constructor
    }
    public UserFragment(Context context) {
        // Required empty public constructor
        this.context=context;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    ImageView imgUser;
    EditText edtEmail,edtName;
    TextView txtUserid,txtEmailVerified;
    Button btnUpdate;
    AccountDAO accountDAO =new AccountDAO();
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
    void addControl(View view)
    {
        imgUser = view.findViewById(R.id.imageViewUser);
        edtEmail = view.findViewById(R.id.editTextEmailUser);
        edtName = view.findViewById(R.id.editTextNameUser);
        btnUpdate = view.findViewById(R.id.buttonUpdateUser);
        txtUserid= view.findViewById(R.id.textViewUserid);
        txtEmailVerified=view.findViewById(R.id.textViewEmailVerify);
    }
    void addEvents()
    {
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

                updateInforUser(userid,uri);
//                NavigationView navigationView = mainActivity.findViewById(R.id.nav_view);
//
//                mainActivity.getInforUserHeader(navigationView,getActivity());
            }
        });
    }

    private void updateUser() {

        if(mainActivity==null)
            return;
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        {
            mainActivity.openGallery();
            return;
        }
        if(getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)
        {
            mainActivity.openGallery();
        }
        else
        {
            String [] permissions ={Manifest.permission.READ_EXTERNAL_STORAGE};
            getActivity().requestPermissions(permissions,MY_REQUEST_CODE);

        }
    }



    public void getInforUserPorfolio()
    {
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
            //
            edtName.setText(name);
            edtEmail.setText(email);
            txtUserid.setText(uid);
            if(emailVerified)
            {
                txtEmailVerified.setText("Yes");
            }
            else
                txtEmailVerified.setText("No");
            //nếu mà lỗi hoặc ko có thì set up hình mặc định

            Glide.with(this).load(photoUrl).error(R.drawable.user1).into(imgUser);
        }
    }
    public void updateInforUser(String username,Uri uri)
    {
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
    public void setBitmapImageView (Bitmap bitmapImageView)
    {
        imgUser.setImageBitmap(bitmapImageView);
    }
}
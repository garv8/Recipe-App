package com.garv.bookhub;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

public class BlankFragment extends Fragment {
    Button signout;
    GoogleSignInClient mGoogleSignInClient;
    Button Continue;
    TextView emailid, name;
    ImageView profilepic;

    String c;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    public void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener((Activity)getActivity(), new OnCompleteListener<Void>() {
                    @SuppressLint("CommitPrefEdits")
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        System.out.println("you");
                        c="3";
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), login.class);
                        intent.putExtra("c",c);
                        startActivity(intent);
                        getActivity().finish();

                    }
                });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        name=view.findViewById(R.id.name);
        emailid=view.findViewById(R.id.emailid);
        signout=view.findViewById(R.id.signout);
        Continue=view.findViewById(R.id.Continue);
        profilepic=view.findViewById(R.id.profilepic);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("uu");
                signOut();


            }
        });
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            name.setText(personName);
            System.out.println("name");
            emailid.setText(personEmail);
            Uri personPhoto = acct.getPhotoUrl();
            /*Glide.with(this).load(String.valueOf(personPhoto)).into(profilepic);*/
            /*Picasso.get().load(String.valueOf(personPhoto)).into(profilepic);*/
        }


        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_blank, container, false);
        return view;
    }
}
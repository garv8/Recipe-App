package com.garv.bookhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

public class CheckInfo extends AppCompatActivity {
Button signout;
GoogleSignInClient mGoogleSignInClient;
Button Continue;
TextView emailid, name;
ImageView profilepic;

String c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_info);
        emailid=findViewById(R.id.emailid);
        name=findViewById(R.id.name);
        signout=findViewById(R.id.signout);
        Continue=findViewById(R.id.Continue);
        profilepic=findViewById(R.id.profilepic);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signOut();


            }
        });
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            name.setText(personName);
            emailid.setText(personEmail);
            Uri personPhoto = acct.getPhotoUrl();
            /*Glide.with(this).load(String.valueOf(personPhoto)).into(profilepic);*/
            Picasso.get().load(String.valueOf(personPhoto)).into(profilepic);
        }
        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(CheckInfo.this, MainActivity.class);
                startActivity(intent);
                CheckInfo.this.finish();
            }
        });


    }
    public void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @SuppressLint("CommitPrefEdits")
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        c="3";
                        Intent intent = new Intent();
                        intent.setClass(CheckInfo.this, login.class);
                        intent.putExtra("c",c);
                        startActivity(intent);
                        CheckInfo.this.finish();

                    }
                });
    }

}
package com.example.networkandperistence;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences getShared = getSharedPreferences("login",MODE_PRIVATE);
        Boolean LoggedIn = getShared.getBoolean("logged in",false);
        if(LoggedIn){
            getSupportFragmentManager().beginTransaction().replace(R.id.Container,new Recommended()).commit();
        }
        else {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.Container, new LoginPage()).commit();
        }


    }
}
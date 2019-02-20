package com.example.reservation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.drm.DrmStore;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void buttonListener2(View v){
        Intent it = new Intent(getApplicationContext(),SettingActivity.class);
        startActivity(it);

    }
    public void buttonListener1(View v){
        Intent it = new Intent(getApplicationContext(),FinderActivity.class);
        startActivity(it);
    }
    public void buttonListener3(View v){
        Intent it = new Intent(getApplicationContext(),MemberActivity.class);
        startActivity(it);
    }
}

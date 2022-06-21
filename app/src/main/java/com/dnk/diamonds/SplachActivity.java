package com.dnk.shairugems;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.dnk.shairugems.Utils.Const;
import com.dnk.shairugems.Utils.Pref;

public class SplachActivity extends AppCompatActivity {

    Context context = SplachActivity.this;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splach_activity);
        Pref.setStringValue(context, Const.isSignUp, "0");

//        img = findViewById(R.id.img);
//////        Animation an2 = AnimationUtils.loadAnimation(context, R.anim.bounce);
//////        img.startAnimation(an2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(context, NewLoginActivity.class));
                finish();
            }
        }, 2500);
    }
}
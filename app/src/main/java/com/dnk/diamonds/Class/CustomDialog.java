package com.dnk.shairugems.Class;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.dnk.shairugems.R;


public class CustomDialog extends Dialog {
    Context objContext;
    ImageView imgGIF;

    public CustomDialog(Context context) {
        super(context);
        objContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_progress);

        imgGIF = findViewById(R.id.imgGIF);

        Animation anim=AnimationUtils.loadAnimation(objContext, R.anim.rotateprogress);
        imgGIF.setAnimation(anim);

        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().getAttributes().gravity = Gravity.CENTER;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK)
            return true;
        return super.onKeyDown(keyCode, event);
    }
}
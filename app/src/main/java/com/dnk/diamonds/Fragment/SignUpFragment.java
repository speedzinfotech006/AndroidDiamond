package com.dnk.shairugems.Fragment;


import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;

import androidx.fragment.app.Fragment;

import com.dnk.shairugems.R;
import com.dnk.shairugems.Utils.Const;

public class SignUpFragment extends Fragment {

    ScrollView llLoginRound;
    Button btnSignup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sign_up_fragment, container, false);

        initView(v);

        llLoginRound.setBackgroundDrawable(Const.setBackgoundBorder(0, 20, Color.WHITE, Color.TRANSPARENT));
        btnSignup.setBackgroundDrawable(setBackgoundBorder(0, 20, getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorPrimary)));

        return v;
    }

    private GradientDrawable setBackgoundBorder(int width, int radius, int backcolor, int colorcorner) {
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setColor(backcolor);
        gd.setStroke(width, colorcorner);
        gd.setCornerRadii(new float[]{0, 0, 0, 0, radius, radius, radius, radius});
        return gd;
    }

    private void initView(View v) {
        llLoginRound = v.findViewById(R.id.llLoginRound);
        btnSignup = v.findViewById(R.id.btnSignup);
    }

}

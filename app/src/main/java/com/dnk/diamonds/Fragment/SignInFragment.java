package com.dnk.shairugems.Fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.dnk.shairugems.HomeActivity;
import com.dnk.shairugems.R;
import com.dnk.shairugems.Utils.Const;

public class SignInFragment extends Fragment {

    Button btnLogin;
    ImageView imgShowHide;
    EditText etPassword;
    LinearLayout llLoginRound;
    boolean isshow = false;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sign_in_fragment, container, false);
        context = getActivity();
        initView(v);

        llLoginRound.setBackgroundDrawable(Const.setBackgoundBorder(0, 20, Color.WHITE, Color.TRANSPARENT));
        btnLogin.setBackgroundDrawable(setBackgoundBorder(0, 20, getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorPrimary)));

        btnLogin.setOnClickListener(clickListener);
        imgShowHide.setOnClickListener(clickListener);

        return v;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnLogin:
                    startActivity(new Intent(context, HomeActivity.class));
                    break;
                case R.id.imgShowHide:
                    if (!etPassword.getText().toString().equalsIgnoreCase("")) {
                        if (isshow) {
                            isshow = false;
                            etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                            imgShowHide.setImageResource(R.drawable.ic_hide);
                            etPassword.setSelection(etPassword.getText().length());
                        } else {
                            isshow = true;
                            etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            imgShowHide.setImageResource(R.drawable.ic_show);
                            etPassword.setSelection(etPassword.getText().length());
                        }
                    } else {
                        if (isshow) {
                            isshow = false;
                            imgShowHide.setImageResource(R.drawable.ic_show);
                        } else {
                            isshow = true;
                            imgShowHide.setImageResource(R.drawable.ic_hide);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };

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
        btnLogin = v.findViewById(R.id.btnLogin);
        etPassword = v.findViewById(R.id.etPassword);
        imgShowHide = v.findViewById(R.id.imgShowHide);
    }

}
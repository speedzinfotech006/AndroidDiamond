package com.dnk.shairugems;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutUsActivity extends BaseDrawerActivity {

    WebView textContent;
    ImageView imgMenu, imgBack;
    TextView tvTitle;
    Context context = AboutUsActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_about_us, frameLayout);

        initView();

    }
    private void initView() {
        imgMenu = findViewById(R.id.imgMenu);
        textContent = findViewById(R.id.textContent);

        String text;
        text = "<html><head><style type=\"text/css\">@font-face {font-family:proxima_nova}body file:///android_res/drawable/\", {font-family: proxima_nova justify;}</style></head><body>";
        text += "The Shairu Gems Group is a leading polished diamond supplier headquartered in Mumbai with manufacturing operations centrally located in the world’s largest diamond cutting and polishing centre in Surat, India. The company employs more than 1500 employees across a global footprint that covers the traditional markets of America and Europe, as well the fastest growing regions of India and Far East Asia. <br><br> Shairu Gems' core strengths as a manufacturer can be summarised as follows:</br></br> <br> &#8226;Pipeline integrity resulting in enhanced consumer reassurance from rough to polished.</br> <br>&#8226;Consistent rough supply allowing long-term planning through long-lasting relationships with preeminent mining houses; the Diamond Trading Company, De Beers’ distribution arm among others.</br> <br>&#8226;Full technological integration implying traceability and greater efficiencies throughout the cutting and polishing process.</br> <br>&#8226;Round brilliants with a light return of 97% or more ensuring optimal brilliance through ideal proportions.</br> <br>&#8226;Development department dedicated to special and proprietary cuts to cater to the marketing needs of our retail partners.</br> <br>&#8226;Balanced distribution channels both in terms of geography and pipeline segment, allowing a full picture of the latest industry trends.</br> <br>&#8226;Young and versatile management present at every stage of the value chain.</br>";
        text += "</p></body></html>";
        textContent.loadData(text, "text/html", "utf-8");
        textContent.setBackgroundColor(Color.TRANSPARENT);

        imgMenu.setOnClickListener(clickListener);

    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @SuppressLint("WrongConstant")
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.imgMenu:
                    drawerLayout.openDrawer(Gravity.START);
                    break;
                default:
                    break;
            }
        }
    };

    public Boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) AboutUsActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }
}
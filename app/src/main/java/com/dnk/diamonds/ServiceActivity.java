package com.dnk.shairugems;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

public class ServiceActivity extends BaseDrawerActivity {

    ImageView imgMenu;
    Context context = ServiceActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_service, frameLayout);

        initView();

    }

    private void initView() {
        imgMenu = findViewById(R.id.imgMenu);

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
        ConnectivityManager cm = (ConnectivityManager) ServiceActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }
}
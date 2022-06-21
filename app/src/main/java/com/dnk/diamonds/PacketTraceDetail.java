package com.dnk.shairugems;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dnk.shairugems.Utils.Const;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class PacketTraceDetail extends BaseDrawerActivity {
    TextView tvTitle;
    ImageView imgMenu, imgOne, imgBack;
    ProgressBar pbBarWeb, pbBarImage, pbBarCerti;
    WebView wbView, wbViewCerti;
    RelativeLayout rlVideo, rlImage, rlCertificate;
    TextView tvDetails, tvImageNot, tvCertiNot, tvVideoNot;
    Context context = PacketTraceDetail.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_packet_trace_detail, frameLayout);

        initView();
    }

    private void initView() {
        imgMenu = findViewById(R.id.imgMenu);
        imgBack = findViewById(R.id.imgBack);
        wbViewCerti = findViewById(R.id.wbViewCerti);
        rlCertificate = findViewById(R.id.rlCertificate);
        pbBarCerti = findViewById(R.id.pbBarCerti);
        imgOne = findViewById(R.id.imgOne);
        wbView = findViewById(R.id.wbView);
        rlVideo = findViewById(R.id.rlVideo);
        rlImage = findViewById(R.id.rlImage);
        pbBarWeb = findViewById(R.id.pbBarWeb);
        pbBarImage = findViewById(R.id.pbBarImage);
        tvImageNot = findViewById(R.id.tvImageNot);
        tvVideoNot = findViewById(R.id.tvVideoNot);
        tvCertiNot = findViewById(R.id.tvCertiNot);
        tvTitle = findViewById(R.id.tvTitle);

        imgMenu.setOnClickListener(clickListener);
        imgBack.setOnClickListener(clickListener);

        if (getIntent().hasExtra("GIA")) {
            if (getIntent().getStringExtra("GIA").equalsIgnoreCase("gia")) {
                rlCertificate.setVisibility(View.VISIBLE);
                tvTitle.setText("Packet Trace GIA");
                if (!Const.hmPacketTrace.get("certi_no").equalsIgnoreCase("") && Const.hmPacketTrace.get("lab").equalsIgnoreCase("GIA")) {
                    wbViewCerti.setWebViewClient(new myWebCerti());
                    wbViewCerti.getSettings().setLoadWithOverviewMode(true);
                    wbViewCerti.getSettings().setJavaScriptEnabled(true);
                    wbViewCerti.getSettings().setUseWideViewPort(true);
                    wbViewCerti.getSettings().setBuiltInZoomControls(true);
                    wbViewCerti.loadUrl("https://docs.google.com/gview?embedded=true&url=" + "https://www.sunrisediamonds.com.hk/certi/" + Const.hmPacketTrace.get("certi_no") + ".pdf");
                } else {
                    pbBarCerti.setVisibility(View.GONE);
                    tvCertiNot.setVisibility(View.VISIBLE);
                }
            } else {
                rlImage.setVisibility(View.GONE);
                rlVideo.setVisibility(View.GONE);
            }
        }

        if (getIntent().hasExtra("IMAGE")) {
            if (getIntent().getStringExtra("IMAGE").equalsIgnoreCase("image")) {
                rlImage.setVisibility(View.VISIBLE);
                tvTitle.setText("Packet Trace Image");
                if (!Const.hmPacketTrace.get("ref_no").equalsIgnoreCase("") && Const.hmPacketTrace.get("web_img_status").equalsIgnoreCase("Y")) {
                    Picasso.with(context)
                            .load(Const.SiteUrl + Const.hmPacketTrace.get("certi_no") + "/PR.jpg")
                            .resize(512, 512)
                            .into(imgOne, new Callback() {
                                @Override
                                public void onSuccess() {
                                    pbBarImage.setVisibility(View.GONE);
                                }

                                @Override
                                public void onError() {
                                    pbBarImage.setVisibility(View.GONE);
                                    tvImageNot.setVisibility(View.VISIBLE);
                                }
                            });
                } else {
                    pbBarImage.setVisibility(View.GONE);
                    tvImageNot.setVisibility(View.VISIBLE);
                }
            } else {
                rlVideo.setVisibility(View.GONE);
                rlCertificate.setVisibility(View.GONE);
            }
        }

        if (getIntent().hasExtra("VIDEO")) {
            if (getIntent().getStringExtra("VIDEO").equalsIgnoreCase("video")) {
                rlVideo.setVisibility(View.VISIBLE);
                tvTitle.setText("Packet Trace Video");
                if (!Const.hmPacketTrace.get("seq_no").equalsIgnoreCase("")) {
                    wbView.setWebViewClient(new myWebClient());
                    wbView.getSettings().setLoadWithOverviewMode(true);
                    wbView.getSettings().setJavaScriptEnabled(true);
                    wbView.getSettings().setUseWideViewPort(true);
                    wbView.getSettings().setBuiltInZoomControls(true);
                    wbView.loadUrl("https://www.sunrisediamonds.com.hk/ViewVideoMp4.aspx?seqno=" + Const.hmPacketTrace.get("seq_no"));
                } else {
                    pbBarWeb.setVisibility(View.GONE);
                    tvVideoNot.setVisibility(View.VISIBLE);
                }
            } else {
                rlImage.setVisibility(View.GONE);
                rlCertificate.setVisibility(View.GONE);
            }
        }
    }
    private class myWebClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            pbBarWeb.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            pbBarWeb.setVisibility(View.GONE);
            view.setVisibility(View.VISIBLE);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            view.stopLoading();
            tvVideoNot.setVisibility(View.VISIBLE);
            view.loadUrl("about:blank");
        }
    }

    private class myWebCerti extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            pbBarCerti.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            pbBarCerti.setVisibility(View.GONE);
            view.setVisibility(View.VISIBLE);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            view.stopLoading();
            tvCertiNot.setVisibility(View.VISIBLE);
            view.loadUrl("about:blank");
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @SuppressLint("WrongConstant")
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.imgMenu:
                    drawerLayout.openDrawer(Gravity.START);
                    break;
                case R.id.imgBack:
                    onBackPressed();
                    overridePendingTransition(0, 0);
                    break;
                default:
                    break;
            }
        }
    };
}

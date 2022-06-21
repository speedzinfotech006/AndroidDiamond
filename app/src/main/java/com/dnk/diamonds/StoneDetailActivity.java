package com.dnk.shairugems;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.MimeTypeMap;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dnk.shairugems.Class.BindData;
import com.dnk.shairugems.Class.DownloadImage;
import com.dnk.shairugems.Utils.Const;
import com.dnk.shairugems.Utils.Pref;
import com.dnk.shairugems.Volly.VolleyCallback;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.dnk.shairugems.Utils.Const.setBackgoundBorder;

public class StoneDetailActivity extends BaseDrawerActivity {

    ImageView imgMenu, imgOne, imgTwo, imgThree, imgFour, imgBack;
    LinearLayout llTab;
    SharedPreferences sp;
    RelativeLayout rlImages,rlwbImage, rlVideo, rlImage, rlCertificate;
    LinearLayout llPlaceOrder, llCart, llDownload, llShare, llCertificate;
    ScrollView svDetails;
    ProgressBar pbBarWebImage, pbBarWeb, pbBarImage, pbBarCerti;
    WebView wbViewImage, wbView, wbViewCerti;
    TextView tvDetails, tvwebImageNot, tvImageNot, tvCertiNot, tvVideoNot, tvImage, tvVideo, tvCertificate, tvStoneID, tvLab, tvShape, tvBgm, tvLength, tvCertiNo, tvFls, tvMsrmnt,
            tvColor, tvClarity, tvSym, tvCut, tvShade, tvPolish, tvLuster, tvSize, tvTotalDepthPer, tvRapRate, tvRapAmt, tvDisc, tvTotalAmt, tvStatus, tvLocation,
            tvTableBlack, tvCrownBlack, tvTableWhite, tvCrownWhite, tvTablePer, tvGirdlePer, tvCrownAngle, tvGirdelCondition, tvPavilionAngle, tvCrownHeight, tvPavilionHeight,
            tvStarLength, tvLowerHalves, tvGirdleType, tvEyeClean, tvCuletSize, tvInscription, tvSymbol, tvComment, tvFinalDisc, tvFinalValue,
            tvTableOpen, tvCrownOpen, tvPavOpen, tvGirdleOpen;
    TextView tvCountCart;
    List<HashMap<String, String>> arrayCount;
    long downID = 0;
    String randomNumber = "";
    DownloadManager downloadManager;
    FrameLayout overlay;
    Context context = StoneDetailActivity.this;
    List<HashMap<String, String>> holdItem = new ArrayList<>();
    List<HashMap<String, String>> unHoldItem = new ArrayList<>();
    List<String> loadedImageArray = new ArrayList<>();
    int imgCount = 0;
    String image1String = "";
    String image2String = "";
    String image3String = "";
    String image4String = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.stone_detail_activity, frameLayout);

        initView();

        getStoneDetail();
        getHomeCount();
        get_companyName();

        sp = PreferenceManager.getDefaultSharedPreferences(context);
        if (isNetworkAvailable()) {
            if (!sp.getBoolean("OverlayDisplayedStoneDetail", false)) {
                overlay.setVisibility(View.VISIBLE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("OverlayDisplayedStoneDetail", true);
                editor.apply();
            } else {
                overlay.setVisibility(View.GONE);
            }
        } else {
            Const.DisplayNoInternet(context);
        }
    }

    private void initView() {
        tvStoneID = findViewById(R.id.tvStoneID);
        tvCertiNo = findViewById(R.id.tvCertiNo);
        tvShape = findViewById(R.id.tvShape);
        tvBgm = findViewById(R.id.tvBgm);
        tvColor = findViewById(R.id.tvColor);
        tvStatus = findViewById(R.id.tvStatus);
        tvClarity = findViewById(R.id.tvClarity);
        tvLocation = findViewById(R.id.tvLocation);
        tvSize = findViewById(R.id.tvSize);
        tvLength = findViewById(R.id.tvLength);
        tvTotalDepthPer = findViewById(R.id.tvTotalDepthPer);
        tvFls = findViewById(R.id.tvFls);
        tvCuletSize = findViewById(R.id.tvCuletSize);
        tvRapRate = findViewById(R.id.tvRapRate);
        tvRapAmt = findViewById(R.id.tvRapAmt);
        tvDisc = findViewById(R.id.tvDisc);
        tvTotalAmt = findViewById(R.id.tvTotalAmt);
        tvTableBlack = findViewById(R.id.tvTableBlack);
        tvTableWhite = findViewById(R.id.tvTableWhite);
        tvCrownBlack = findViewById(R.id.tvCrownBlack);
        tvCrownWhite = findViewById(R.id.tvCrownWhite);
        tvCrownAngle = findViewById(R.id.tvCrownAngle);
        tvPavilionAngle = findViewById(R.id.tvPavilionAngle);
        tvCrownHeight = findViewById(R.id.tvCrownHeight);
        tvPavilionHeight = findViewById(R.id.tvPavilionHeight);
        tvGirdlePer = findViewById(R.id.tvGirdlePer);
        tvGirdleType = findViewById(R.id.tvGirdleType);
        tvStarLength = findViewById(R.id.tvStarLength);
        tvLowerHalves = findViewById(R.id.tvLowerHalves);
        tvGirdelCondition = findViewById(R.id.tvGirdelCondition);
        tvInscription = findViewById(R.id.tvInscription);
        tvTableOpen = findViewById(R.id.tvTableOpen);
        tvCrownOpen = findViewById(R.id.tvCrownOpen);
        tvPavOpen = findViewById(R.id.tvPavOpen);
        tvGirdleOpen = findViewById(R.id.tvGirdleOpen);

        tvCountCart = findViewById(R.id.tvCountCart);

        tvSymbol = findViewById(R.id.tvSymbol);
        tvComment = findViewById(R.id.tvComment);

        rlImages = findViewById(R.id.rlImages);
        rlwbImage = findViewById(R.id.rlwbImage);
        wbViewImage = findViewById(R.id.wbViewImage);
        pbBarWebImage = findViewById(R.id.pbBarWebImage);
        tvwebImageNot = findViewById(R.id.tvwebImageNot);

        tvLab = findViewById(R.id.tvLab);
        tvVideoNot = findViewById(R.id.tvVideoNot);
        tvCertiNot = findViewById(R.id.tvCertiNot);
        tvImageNot = findViewById(R.id.tvImageNot);
        tvSym = findViewById(R.id.tvSym);
        tvCut = findViewById(R.id.tvCut);
        tvMsrmnt = findViewById(R.id.tvMsrmnt);
        tvPolish = findViewById(R.id.tvPolish);
        tvTablePer = findViewById(R.id.tvTablePer);

        tvFinalDisc = findViewById(R.id.tvFinalDisc);
        tvFinalValue = findViewById(R.id.tvFinalValue);

        imgMenu = findViewById(R.id.imgMenu);
        imgFour = findViewById(R.id.imgFour);
        imgThree = findViewById(R.id.imgThree);
        imgTwo = findViewById(R.id.imgTwo);
        imgBack = findViewById(R.id.imgBack);
        wbViewCerti = findViewById(R.id.wbViewCerti);
        tvCertificate = findViewById(R.id.tvCertificate);
        rlCertificate = findViewById(R.id.rlCertificate);
        pbBarCerti = findViewById(R.id.pbBarCerti);
        imgOne = findViewById(R.id.imgOne);
        tvVideo = findViewById(R.id.tvVideo);
        tvImage = findViewById(R.id.tvImage);
        wbView = findViewById(R.id.wbView);
        tvDetails = findViewById(R.id.tvDetails);
        svDetails = findViewById(R.id.svDetails);
        rlVideo = findViewById(R.id.rlVideo);
        rlImage = findViewById(R.id.rlImage);
        pbBarWeb = findViewById(R.id.pbBarWeb);
        pbBarImage = findViewById(R.id.pbBarImage);
        llTab = findViewById(R.id.llTab);
        overlay = findViewById(R.id.overlay);

        llPlaceOrder = findViewById(R.id.llPlaceOrder);
        llCart = findViewById(R.id.llCart);
        llDownload = findViewById(R.id.llDownload);
        llShare = findViewById(R.id.llShare);
        llCertificate = findViewById(R.id.llCertificate);

        tvDetails.setTextColor(Color.WHITE);
        tvDetails.setBackgroundDrawable(Const.setBackgoundLeftBorder(0, 50, getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorPrimary)));
        imgMenu.setOnClickListener(clickListener);
//        tvDetails.setOnClickListener(clickListener);
//        tvImage.setOnClickListener(clickListener);
//        tvVideo.setOnClickListener(clickListener);
//        tvCertificate.setOnClickListener(clickListener);
        imgFour.setOnClickListener(clickListener);
        imgThree.setOnClickListener(clickListener);
        imgTwo.setOnClickListener(clickListener);
        imgOne.setOnClickListener(clickListener);
        imgBack.setOnClickListener(clickListener);
        overlay.setOnClickListener(clickListener);

        llPlaceOrder.setOnClickListener(clickListener);
        llCart.setOnClickListener(clickListener);
        llDownload.setOnClickListener(clickListener);
        llShare.setOnClickListener(clickListener);
    }

    private void getStoneDetail() {
        Const.showProgress(context);
        Map<String, String> map = new HashMap<>();
        map.put("StoneID", getIntent().getStringExtra("stoneid"));
        Const.callPostApi(context, "Stock/GetSearchStockByStoneID", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Const.dismissProgress();
                try {
                    JSONObject ob = new JSONObject(result);
                    Iterator<String> keys = ob.keys();
                    HashMap<String, String> hm = new HashMap<>();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        hm.put(key.toLowerCase(), ob.optString(key).trim());
                    }
                    Const.hmStoneDetail = hm;

                    holdItem.clear();
                    unHoldItem.clear();

                    if (!Const.hmStoneDetail.get("view_certi_url").equalsIgnoreCase("")  && !Const.hmStoneDetail.get("view_certi_url").equalsIgnoreCase("null")) {
                        String url = "";
                        if (Const.hmStoneDetail.get("isoverseas").equalsIgnoreCase("1")) {
                            if (!Const.hmStoneDetail.get("overseas_certi_download_link").equalsIgnoreCase("") && !Const.hmStoneDetail.get("overseas_certi_download_link").equalsIgnoreCase("null") && !Const.hmStoneDetail.get("overseas_certi_download_link").endsWith(".Pdf")) {
                                if (Const.hmStoneDetail.get("isoverseas").equalsIgnoreCase("0")) {
                                    url =  Const.hmStoneDetail.get("overseas_certi_download_link");
                                } else {
                                    if (Const.hmStoneDetail.get("overseas_certi_download_link").endsWith(".pdf")) {
                                        url =  Const.hmStoneDetail.get("overseas_certi_download_link");
                                    } else {
                                        url = Const.hmStoneDetail.get("overseas_certi_download_link");
                                    }
                                }
                            } else {
                                url =  Const.hmStoneDetail.get("overseas_certi_download_link");
                            }
                        } else {
                            url =  Const.hmStoneDetail.get("view_certi_url");
                        }

                        Log.e("Url  ", url.toString());

//                        showPdfFile(url);


//                        wbViewCerti.setWebViewClient(new myWebCerti());
//                        wbViewCerti.getSettings().setJavaScriptEnabled(true);
//                        wbViewCerti.getSettings().setLoadWithOverviewMode(true);
//                        wbViewCerti.getSettings().setUseWideViewPort(true);
//                        wbViewCerti.getSettings().setBuiltInZoomControls(true);
//                        wbViewCerti.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//                        wbViewCerti.setScrollContainer(true);
//                        wbViewCerti.loadUrl(url);

                    }


                    tvStoneID.setText(Const.hmStoneDetail.get("stone_ref_no"));
                    tvCertiNo.setText(Const.hmStoneDetail.get("certi_no"));
                    tvColor.setText(Const.hmStoneDetail.get("color"));
                    tvFls.setText(Const.hmStoneDetail.get("fls"));
                    tvDisc.setText(String.format("%,.2f", Double.parseDouble(Const.hmStoneDetail.get("sales_disc_per"))));
                    if (!Const.hmStoneDetail.get("clarity").equalsIgnoreCase("null") && !Const.hmStoneDetail.get("clarity").equalsIgnoreCase("")){
                        tvClarity.setText(Const.hmStoneDetail.get("clarity"));
                    } else {
                        tvClarity.setText("");
                    }
                    tvTotalAmt.setText(String.format("%,.2f", Double.parseDouble(Const.hmStoneDetail.get("net_amount"))));
                    tvSize.setText(String.format("%,.2f", Double.parseDouble(Const.hmStoneDetail.get("cts"))));
                    tvLength.setText(Const.hmStoneDetail.get("length") + "x" + Const.hmStoneDetail.get("width") + "x" + Const.hmStoneDetail.get("depth"));
                    tvLength.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                    tvCut.setText(Const.hmStoneDetail.get("cut") + "-" + Const.hmStoneDetail.get("polish") + "-" + Const.hmStoneDetail.get("symm"));
                    if (Const.hmStoneDetail.get("cut").equalsIgnoreCase("3EX")) {
                        tvCut.setTypeface(Typeface.DEFAULT_BOLD);
                    } else {
                        tvCut.setTypeface(Typeface.DEFAULT);
                    }
                    tvLocation.setText(Const.hmStoneDetail.get("location"));
                    tvShape.setText(Const.hmStoneDetail.get("shape"));
                    tvBgm.setText(Const.hmStoneDetail.get("bgm"));
                    tvMsrmnt.setText(Const.hmStoneDetail.get("measurement"));
                    tvRapRate.setText(String.format("%,.2f", Double.parseDouble(Const.hmStoneDetail.get("cur_rap_rate"))));
                    tvRapAmt.setText(String.format("%,.2f", Double.parseDouble(Const.hmStoneDetail.get("rap_amount"))));
                    tvStatus.setText(Const.hmStoneDetail.get("status"));
                    tvLab.setText(Const.hmStoneDetail.get("lab"));
                    tvTotalDepthPer.setText(Const.hmStoneDetail.get("depth_per") + "-" + Const.hmStoneDetail.get("table_per"));
                    tvTableBlack.setText(Const.hmStoneDetail.get("table_natts"));
                    tvTableWhite.setText(Const.hmStoneDetail.get("inclusion"));
                    tvCrownBlack.setText(Const.hmStoneDetail.get("crown_natts"));
                    tvCrownWhite.setText(Const.hmStoneDetail.get("crown_inclusion"));
                    tvCrownAngle.setText(Const.hmStoneDetail.get("crown_angle"));
                    tvPavilionAngle.setText(Const.hmStoneDetail.get("pav_angle"));
                    tvCrownHeight.setText(Const.hmStoneDetail.get("crown_height"));
                    tvPavilionHeight.setText(Const.hmStoneDetail.get("pav_height"));
                    tvGirdlePer.setText(Const.hmStoneDetail.get("girdle_per"));
                    tvGirdleType.setText(Const.hmStoneDetail.get("girdle_type"));
                    tvStarLength.setText(Const.hmStoneDetail.get("sstrln"));
                    tvLowerHalves.setText(Const.hmStoneDetail.get("slrhalf"));
                    tvGirdelCondition.setText(Const.hmStoneDetail.get("girdle"));

                    boolean isOpenAvailable = false;

                    if (!Const.hmStoneDetail.get("table_open").equalsIgnoreCase("null") && !Const.hmStoneDetail.get("table_open").equalsIgnoreCase("")){
                        tvTableOpen.setText(Const.hmStoneDetail.get("table_open"));
                        if(!Const.hmStoneDetail.get("table_open").equalsIgnoreCase("NN")){
                            isOpenAvailable = true;
                        }
                    } else {
                        tvTableOpen.setText("");
                    }
                    if (!Const.hmStoneDetail.get("crown_open").equalsIgnoreCase("null") && !Const.hmStoneDetail.get("crown_open").equalsIgnoreCase("")){
                        tvCrownOpen.setText(Const.hmStoneDetail.get("crown_open"));
                        if(!Const.hmStoneDetail.get("crown_open").equalsIgnoreCase("NN")){
                            isOpenAvailable = true;
                        }
                    } else {
                        tvCrownOpen.setText("");
                    }
                    if (!Const.hmStoneDetail.get("pav_open").equalsIgnoreCase("null") && !Const.hmStoneDetail.get("pav_open").equalsIgnoreCase("")){
                        tvPavOpen.setText(Const.hmStoneDetail.get("pav_open"));
                        if(!Const.hmStoneDetail.get("pav_open").equalsIgnoreCase("NN")){
                            isOpenAvailable = true;
                        }
                    } else {
                        tvPavOpen.setText("");
                    }
                    if (!Const.hmStoneDetail.get("girdle_open").equalsIgnoreCase("null") && !Const.hmStoneDetail.get("girdle_open").equalsIgnoreCase("")){
                        tvGirdleOpen.setText(Const.hmStoneDetail.get("girdle_open"));
                        if(!Const.hmStoneDetail.get("girdle_open").equalsIgnoreCase("NN")){
                            isOpenAvailable = true;
                        }
                    } else {
                        tvGirdleOpen.setText("");
                    }

                    if(isOpenAvailable){
                        llCertificate.setBackgroundColor(getResources().getColor(R.color.open_avail));
                    }else{
                        llCertificate.setBackgroundColor(getResources().getColor(R.color.white));
                    }

//                    hold_compname

                    if(!Const.hmStoneDetail.get("hold_compname").equalsIgnoreCase("") && !Const.hmStoneDetail.get("hold_compname").equalsIgnoreCase("null")){
                        holdItem.add(hm);
                    }
                    if (!Pref.getStringValue(context, Const.loginIsCust, "").equalsIgnoreCase("1")) {
                        if (Const.hmStoneDetail.get("status").equalsIgnoreCase("AVAILABLE") || Const.hmStoneDetail.get("status").equalsIgnoreCase("NEW")) {
                            unHoldItem.add(hm);
                        }
                    }

                    Double maindisc = Double.parseDouble("0.88");
                    Double avgdisc = 0.00;
                    Double finaldisc = 0.00;
                    if (Const.hmStoneDetail.get("cur_rap_rate").equalsIgnoreCase("0.0")){
                        avgdisc = 0.00;
                    } else {
                        avgdisc = (100 - (((Double.parseDouble(Const.hmStoneDetail.get("net_amount"))) * 100) / (Double.parseDouble(Const.hmStoneDetail.get("rap_amount"))))) * -1;
                    }

                    Double offerval = Double.valueOf(Const.hmStoneDetail.get("net_amount"));
                    Double webdisc = (offerval * (maindisc / 100));
                    Double finalvalue = (offerval - webdisc);
                    if (Const.hmStoneDetail.get("cur_rap_rate").equalsIgnoreCase("0.0")){
                        finaldisc = 0.00;
                    } else {
                        finaldisc = ((1 - (finalvalue / Double.parseDouble(Const.hmStoneDetail.get("rap_amount")))) * 100) * -1;
                    }
                    tvFinalDisc.setText(String.format("%,.2f", finaldisc));
                    tvFinalValue.setText(String.format("%,.2f", finalvalue));

                    if(!Const.hmStoneDetail.get("sinscription").equalsIgnoreCase("") && !Const.hmStoneDetail.get("sinscription").equalsIgnoreCase("null")){
                        tvInscription.setText(Const.hmStoneDetail.get("sinscription"));
                    }else{
                        tvInscription.setText("");
                    }
                    if(!Const.hmStoneDetail.get("symbol").equalsIgnoreCase("null")){
                        tvSymbol.setText(Const.hmStoneDetail.get("symbol"));
                    }else{
                        tvSymbol.setText("");
                    }
                    tvComment.setText(Const.hmStoneDetail.containsKey("scomments") ? Const.hmStoneDetail.get("scomments") : "");
                    tvCuletSize.setText(Const.hmStoneDetail.containsKey("sculet") ? Const.hmStoneDetail.get("sculet") : "");
/*
                    if (getIntent().hasExtra("flag")) {
                        if (getIntent().getStringExtra("flag").equalsIgnoreCase("video")) {
                            tvVideo.performClick();
                        } else {
                            tvCertificate.performClick();
                        }
                    }*/

//                    if (!Const.hmStoneDetail.get("movie_url").equalsIgnoreCase("") && !Const.hmStoneDetail.get("movie_url").equalsIgnoreCase("null")) {
////                        wbView.setWebViewClient(new myWebClient());
////                        wbView.getSettings().setLoadWithOverviewMode(true);
////                        wbView.getSettings().setJavaScriptEnabled(true);
////                        wbView.getSettings().setUseWideViewPort(true);
////                        wbView.getSettings().setBuiltInZoomControls(true);
////                        wbView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
////                        wbView.loadUrl(Const.hmStoneDetail.get("movie_url"));
////                    } else {
////                        pbBarWeb.setVisibility(View.GONE);
////                        tvVideoNot.setVisibility(View.VISIBLE);
////                    }

                    if (!Const.hmStoneDetail.get("isoverseas").equalsIgnoreCase("1")) {
                        rlImages.setVisibility(View.VISIBLE);
                        rlwbImage.setVisibility(View.GONE);
                        DisplayMetrics displayMetrics = new DisplayMetrics();
                        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                        int widthh = displayMetrics.widthPixels - 20;
                        widthh = widthh / 2;
                        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(0, widthh - 100, 2.0f);
                        parms.setMargins(1, 1, 1, 1);
                        imgOne.setLayoutParams(parms);
                        imgTwo.setLayoutParams(parms);
                        imgThree.setLayoutParams(parms);
                        imgFour.setLayoutParams(parms);

                        Picasso.with(context)
                                .load(Const.SiteUrl + Const.hmStoneDetail.get("certi_no") + "/PR.jpg")
                                .resize(512, 512)
                                .centerCrop()
                                .into(imgOne, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        loadedImageArray.add(Const.SiteUrl + Const.hmStoneDetail.get("certi_no") + "/PR.jpg");
                                        imgCount = imgCount + 1;
                                        if(imgCount == 4){
                                            setOversesImages();
                                        }
                                    }

                                    @Override
                                    public void onError() {
                                        imgCount = imgCount + 1;
                                        if(imgCount == 4){
                                            setOversesImages();
                                        }
                                        imgOne.setVisibility(View.GONE);
                                        pbBarImage.setVisibility(View.GONE);
                                        tvImageNot.setVisibility(View.VISIBLE);
                                        rlImages.setVisibility(View.GONE);
                                        rlwbImage.setVisibility(View.VISIBLE);
                                        if (!Const.hmStoneDetail.get("image_url").equalsIgnoreCase("") && !Const.hmStoneDetail.get("image_url").equalsIgnoreCase("null")) {
                                            wbViewImage.setWebViewClient(new myWebImageClient());
                                            wbViewImage.getSettings().setLoadWithOverviewMode(true);
                                            wbViewImage.getSettings().setJavaScriptEnabled(true);
                                            wbViewImage.getSettings().setUseWideViewPort(true);
                                            wbViewImage.getSettings().setBuiltInZoomControls(true);
                                            wbViewImage.loadUrl(Const.hmStoneDetail.get("image_url"));
                                        } else {
                                            pbBarWebImage.setVisibility(View.GONE);
                                            tvwebImageNot.setVisibility(View.VISIBLE);
                                        }
                                    }
                                });
                        Picasso.with(context)
                                .load(Const.SiteUrl + Const.hmStoneDetail.get("certi_no") + "/AS.jpg")
                                .resize(512, 512)
                                .centerCrop()
                                .into(imgTwo, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        loadedImageArray.add(Const.SiteUrl + Const.hmStoneDetail.get("certi_no") + "/AS.jpg");
                                        imgCount = imgCount + 1;
                                        if(imgCount == 4){
                                            setOversesImages();
                                        }
                                    }

                                    @Override
                                    public void onError() {
                                        imgCount = imgCount + 1;
                                        if(imgCount == 4){
                                            setOversesImages();
                                        }
                                    }
                                });
                        Picasso.with(context)
                                .load(Const.SiteUrl + Const.hmStoneDetail.get("certi_no") + "/HT.jpg")
                                .resize(512, 512)
                                .centerCrop()
                                .into(imgThree, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        loadedImageArray.add(Const.SiteUrl + Const.hmStoneDetail.get("certi_no") + "/HT.jpg");
                                        imgCount = imgCount + 1;
                                        if(imgCount == 4){
                                            setOversesImages();
                                        }
                                    }

                                    @Override
                                    public void onError() {
                                        imgThree.setVisibility(View.GONE);
                                        imgCount = imgCount + 1;
                                        if(imgCount == 4){
                                            setOversesImages();
                                        }
                                    }
                                });
                        Picasso.with(context)
                                .load(Const.SiteUrl + Const.hmStoneDetail.get("certi_no") + "/HB.jpg")
                                .resize(512, 512)
                                .centerCrop()
                                .into(imgFour, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        loadedImageArray.add(Const.SiteUrl + Const.hmStoneDetail.get("certi_no") + "/HB.jpg");
                                        pbBarImage.setVisibility(View.GONE);
                                        imgCount = imgCount + 1;
                                        if(imgCount == 4){
                                            setOversesImages();
                                        }
                                    }

                                    @Override
                                    public void onError() {
                                        imgFour.setVisibility(View.GONE);
                                        pbBarImage.setVisibility(View.GONE);
                                        imgCount = imgCount + 1;
                                        if(imgCount == 4){
                                            setOversesImages();
                                        }
                                    }
                                });
                    } else {
//                        rlImages.setVisibility(View.GONE);
//                        rlwbImage.setVisibility(View.VISIBLE);
//                        if (!Const.hmStoneDetail.get("image_url").equalsIgnoreCase("") && !Const.hmStoneDetail.get("image_url").equalsIgnoreCase("null")) {
//                            wbViewImage.setWebViewClient(new myWebImageClient());
//                            wbViewImage.getSettings().setLoadWithOverviewMode(true);
//                            wbViewImage.getSettings().setJavaScriptEnabled(true);
//                            wbViewImage.getSettings().setUseWideViewPort(true);
//                            wbViewImage.getSettings().setBuiltInZoomControls(true);
//                            wbViewImage.loadUrl(Const.hmStoneDetail.get("image_url"));
//                        } else {
//                            pbBarWebImage.setVisibility(View.GONE);
//                            tvwebImageNot.setVisibility(View.VISIBLE);
//                        }
                        rlImages.setVisibility(View.VISIBLE);
                        rlwbImage.setVisibility(View.GONE);
                        DisplayMetrics displayMetrics = new DisplayMetrics();
                        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                        int widthh = displayMetrics.widthPixels - 20;
                        widthh = widthh / 2;
                        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(0, widthh - 100, 2.0f);
                        parms.setMargins(1, 1, 1, 1);
                        imgOne.setLayoutParams(parms);
                        imgTwo.setLayoutParams(parms);
                        imgThree.setLayoutParams(parms);
                        imgFour.setLayoutParams(parms);

                        if(!Const.hmStoneDetail.get("overseas_image_download_link").equalsIgnoreCase("") && !Const.hmStoneDetail.get("overseas_image_download_link").equalsIgnoreCase("null")){
                            loadedImageArray.add(Const.hmStoneDetail.get("overseas_image_download_link"));
                        }
                        if(!Const.hmStoneDetail.get("overseas_image_download_link1").equalsIgnoreCase("") && !Const.hmStoneDetail.get("overseas_image_download_link1").equalsIgnoreCase("null")){
                            loadedImageArray.add(Const.hmStoneDetail.get("overseas_image_download_link1"));
                        }
                        if(!Const.hmStoneDetail.get("overseas_image_download_link2").equalsIgnoreCase("") && !Const.hmStoneDetail.get("overseas_image_download_link2").equalsIgnoreCase("null")){
                            loadedImageArray.add(Const.hmStoneDetail.get("overseas_image_download_link2"));
                        }
                        if(!Const.hmStoneDetail.get("overseas_image_download_link3").equalsIgnoreCase("") && !Const.hmStoneDetail.get("overseas_image_download_link3").equalsIgnoreCase("null")){
                            loadedImageArray.add(Const.hmStoneDetail.get("overseas_image_download_link3"));
                        }

                        setOversesImages();
//                        Picasso.with(context)
//                                .load(Const.hmStoneDetail.get("overseas_image_download_link"))
//                                .resize(512, 512)
//                                .centerCrop()
//                                .into(imgOne, new Callback() {
//                                    @Override
//                                    public void onSuccess() {
//                                    }
//
//                                    @Override
//                                    public void onError() {
//                                        imgOne.setVisibility(View.GONE);
//                                    }
//                                });
//                        Picasso.with(context)
//                                .load(Const.hmStoneDetail.get("overseas_image_download_link1"))
//                                .resize(512, 512)
//                                .centerCrop()
//                                .into(imgTwo, new Callback() {
//                                    @Override
//                                    public void onSuccess() {
//                                    }
//
//                                    @Override
//                                    public void onError() {
//                                        imgTwo.setVisibility(View.GONE);
//                                    }
//                                });
//                        Picasso.with(context)
//                                .load(Const.hmStoneDetail.get("overseas_image_download_link2"))
//                                .resize(512, 512)
//                                .centerCrop()
//                                .into(imgThree, new Callback() {
//                                    @Override
//                                    public void onSuccess() {
//                                    }
//
//                                    @Override
//                                    public void onError() {
//                                        imgThree.setVisibility(View.GONE);
//                                    }
//                                });
//                        Picasso.with(context)
//                                .load(Const.hmStoneDetail.get("overseas_image_download_link3"))
//                                .resize(512, 512)
//                                .centerCrop()
//                                .into(imgFour, new Callback() {
//                                    @Override
//                                    public void onSuccess() {
//                                        pbBarImage.setVisibility(View.GONE);
//                                    }
//
//                                    @Override
//                                    public void onError() {
//                                        imgFour.setVisibility(View.GONE);
//                                        pbBarImage.setVisibility(View.GONE);
//                                    }
//                                });
                    }
                    llTab.setBackgroundDrawable(Const.setBackgoundBorder(2, 50, Color.TRANSPARENT, getResources().getColor(R.color.colorPrimary)));

                    tvDetails.setOnClickListener(clickListener);
                    tvImage.setOnClickListener(clickListener);
                    tvVideo.setOnClickListener(clickListener);
                    tvCertificate.setOnClickListener(clickListener);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailerResponse(String error) {
                Const.dismissProgress();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        getStoneDetail();
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    public void setOversesImages(){
        if(loadedImageArray.size() > 0){
            imgOne.setImageResource(0);
            imgTwo.setImageResource(0);
            imgThree.setImageResource(0);
            imgFour.setImageResource(0);
            for (int i = 0; i < loadedImageArray.size(); i++) {
                if(loadedImageArray.size() - 1 == i){
                    pbBarImage.setVisibility(View.GONE);
                }
                if(i == 0){
                    image1String = loadedImageArray.get(i);
                    Picasso.with(context)
                            .load(loadedImageArray.get(i))
                            .resize(512, 512)
                            .centerCrop()
                            .into(imgOne);
                }else if(i == 1){
                    image2String = loadedImageArray.get(i);
                    Picasso.with(context)
                            .load(loadedImageArray.get(i))
                            .resize(512, 512)
                            .centerCrop()
                            .into(imgTwo);
                }else if(i == 2){
                    image3String = loadedImageArray.get(i);
                    Picasso.with(context)
                            .load(loadedImageArray.get(i))
                            .resize(512, 512)
                            .centerCrop()
                            .into(imgThree);
                }else if(i == 3){
                    image4String = loadedImageArray.get(i);
                    Picasso.with(context)
                            .load(loadedImageArray.get(i))
                            .resize(512, 512)
                            .centerCrop()
                            .into(imgFour);
                }
            }
        }
    }

    private void getHomeCount() {
        // Const.showProgress(context);
        arrayCount = new ArrayList<>();
        final Map<String, String> map = new HashMap<>();
        Const.callPostApi(context, "Stock/GetDashboardCnt", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                // Const.dismissProgress();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.optString("Status").equalsIgnoreCase("1")) {
                        JSONArray array = object.optJSONArray("Data");
                        if (array.length() != 0) {
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object1 = array.optJSONObject(i);
                                Iterator<String> keys = object1.keys();
                                HashMap<String, String> hm = new HashMap<>();
                                while (keys.hasNext()) {
                                    String key = keys.next();
                                    hm.put(key.toLowerCase(), object1.optString(key).trim());
                                }
                                arrayCount.add(hm);
                            }
                            for (int i = 0; i < arrayCount.size(); i++) {
                                if (arrayCount.get(i).get("type").equalsIgnoreCase("MyCart")) {
                                    tvCountCart.setText(arrayCount.get(i).get("scnt"));
                                }
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailerResponse(String error) {
                Const.dismissProgress();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        getHomeCount();
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
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

//    public class myWebClient extends WebViewClient {
//
//        @Override
//        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            // TODO Auto-generated method stub
//            super.onPageStarted(view, url, favicon);
//        }
//
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            // TODO Auto-generated method stub
//            pbBarWeb.setVisibility(View.VISIBLE);
//            view.loadUrl(url);
//            return true;
//        }
//
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            // TODO Auto-generated method stub
//            super.onPageFinished(view, url);
//            pbBarWeb.setVisibility(View.GONE);
//            view.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//            // TODO Auto-generated method stub
//            super.onReceivedError(view, errorCode, description, failingUrl);
//            view.stopLoading();
//            view.loadUrl("about:blank");
//        }
//    }

    private class myWebImageClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            pbBarWebImage.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            pbBarWebImage.setVisibility(View.GONE);
            view.setVisibility(View.VISIBLE);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            view.stopLoading();
            tvwebImageNot.setVisibility(View.VISIBLE);
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

    private void fullImage(String url) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LinearLayout layout = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(params);
        layout.setOrientation(LinearLayout.VERTICAL);
        dialog.setContentView(layout);

        ImageView img = new ImageView(context);
        Picasso.with(context)
                .load(url)
                .resize(712,712)
                .centerCrop()
                .into(img);

        layout.addView(img);

        dialog.show();
    }

    private void downloadmedia() {
        Const.showProgress(context);
        final Map<String, String> map = new HashMap<>();
        map.put("StoneID", Const.hmStoneDetail.get("stone_ref_no"));
        map.put("PageNo", "1");
        map.put("DownloadMedia", "video");
        Const.callPostApi(context, "Stock/DownloadStockMedia", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result1) {
                Const.dismissProgress();
                try {
                    String result = result1.replaceAll("^[\"']+|[\"']+$", "");
                    if (!result.startsWith("Error to download video")) {
                        try {
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(result.toString()));
                            request.setDescription("download");
                            request.setTitle(Const.hmStoneDetail.get("stone_ref_no").toString());

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                request.allowScanningByMediaScanner();
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            }
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, Const.hmStoneDetail.get("stone_ref_no").toString() + ".zip");
                            DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                            manager.enqueue(request);
                            Toast.makeText(context, "stones to download information successfully!", Toast.LENGTH_LONG).show();
                        } catch (Exception ex) {

                        }
                    } else {
                        Const.showErrorDialog(context,"Error to download video, video is not MP4..!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailerResponse(String error) {
                Const.dismissProgress();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        downloadmedia();
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    private void downloadOversesImage() {
        Const.showProgress(context);
        final Map<String, String> map = new HashMap<>();
        map.put("StoneID", Const.hmStoneDetail.get("stone_ref_no"));
        map.put("PageNo", "1");
        map.put("DownloadMedia", "image");
        Const.callPostApi(context, "Stock/DownloadStockMedia", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result1) {
                Const.dismissProgress();
                try {
                    String result = result1.replaceAll("^[\"']+|[\"']+$", "");
                    if (!result.startsWith("Image is not available")) {
                        try {
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(result.toString()));
                            request.setDescription("download");
                            request.setTitle(Const.hmStoneDetail.get("stone_ref_no").toString());

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                request.allowScanningByMediaScanner();
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            }
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, Const.hmStoneDetail.get("stone_ref_no").toString() + ".zip");
                            DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                            manager.enqueue(request);
                            Toast.makeText(context, "stones to download information successfully!", Toast.LENGTH_LONG).show();
                        } catch (Exception ex) {

                        }
                    } else {
                        Const.showErrorDialog(context,"Image is not available in this stone !");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailerResponse(String error) {
                Const.dismissProgress();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        downloadOversesImage();
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    private void CartDialog(JSONObject object) {
        final Dialog dialog = new Dialog(context, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.layout_cart_dialog);
        final TextView tvMsg = dialog.findViewById(R.id.tvMsg);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnSend = dialog.findViewById(R.id.btnSend);

        tvMsg.setText(object.optString("Message"));

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, MyCartActivity.class));
                overridePendingTransition(0, 0);
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getHomeCount();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /*private void PlaceOrder(final String comments) {
        Const.showProgress(context);
        final Map<String, String> map = new HashMap<>();
        // Offer & Busy Status stone customer add to cart k place order nahi karse All page par thi
        if (Pref.getStringValue(context, Const.loginIsCust, "").equalsIgnoreCase("1")) {
            if(Const.hmStoneDetail.get("status").equalsIgnoreCase("AVAILABLE OFFER") || Const.hmStoneDetail.get("status").equalsIgnoreCase("BUSS. PROCESS")) {
                map.put("StoneID", "");
            } else {
                map.put("StoneID", Const.hmStoneDetail.get("stone_ref_no"));
            }
        } else {
            map.put("StoneID", Const.hmStoneDetail.get("stone_ref_no"));
        }
        map.put("Comments", Const.notNullString(comments,""));
        Const.callPostApiPlaceOrder(context, "Order/PurchaseConfirmOrder", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Const.dismissProgress();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.optString("Status").equalsIgnoreCase("SUCCESS")) {
                        String ob = new JSONObject(result).optString("Message").replace("<br>","\n").replace("&nbsp;","\n");
                        String message = ob.toString().replaceAll("\\<.*?>","");
                        showDialogPlaceOrder(context, Const.notNullString(message, "This Stone(s) are subject to avaibility 'BZM-318692'"));
                    } else {
                        String ob = new JSONObject(result).optString("Message");
                        Const.showErrorDialog(context, Const.notNullString(ob, "Something want wrong please try again later"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailerResponse(String error) {
                Const.dismissProgress();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        PlaceOrder(comments);
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }*/

    public void showDialogPlaceOrder(final Context context, String message) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_error_dialog);

        TextView tvMessage = (TextView) dialog.findViewById(R.id.tvMessage);
        tvMessage.setText(message);

        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, OrderHistoryActivity.class));
                overridePendingTransition(0, 0);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void DownloadExcel() {
        Const.showProgress(context);
        final Map<String, String> map = new HashMap<>();
        map.put("StoneID", Const.hmStoneDetail.get("stone_ref_no"));
        map.put("FormName", "Search Stock");
        map.put("ActivityType", "Excel Export");
        map.put("PgSize", "0");
        map.put("full", "N");
        map.put("IsAll", "0");
        Const.callPostApi(context, "Stock/DownloadStockExcel", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result1) {
                Const.dismissProgress();
                try {
                    String result = result1.replaceAll("^[\"']+|[\"']+$", "");
                    if (!result.equalsIgnoreCase("No data found.") && (!result.startsWith("Something Went wrong"))){
                        try {
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(result.toString()));
                            request.setDescription("download");
                            Date date = new Date();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy") ;
                            request.setTitle(dateFormat.format(date));

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                request.allowScanningByMediaScanner();
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            }
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, dateFormat.format(date) + ".xlsx");
                            DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                            manager.enqueue(request);
                            Toast.makeText(context, "Download Excel Successfully!", Toast.LENGTH_LONG).show();
                        } catch (Exception ex) {
                        }
                    } else {
                        Const.showErrorDialog(context,"No data found.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailerResponse(String error) {
                Const.dismissProgress();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        DownloadExcel();
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    private void ShareExcel() {
        Const.showProgress(context);
        final Map<String, String> map = new HashMap<>();
        map.put("StoneID", Const.hmStoneDetail.get("stone_ref_no"));
        map.put("FormName", "Search Stock");
        map.put("ActivityType", "Excel Export");
        map.put("PgSize", "0");
        map.put("full", "N");
        map.put("IsAll", "0");
        Const.callPostApi(context, "Stock/DownloadStockExcel", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result1) {
                //  Const.dismissProgress();
                try {
                    String result = result1.replaceAll("^[\"']+|[\"']+$", "");
                    if (!result.equalsIgnoreCase("No data found.") && (!result.startsWith("Something Went wrong"))){
                        Date date = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy") ;

                        Random objGenerator = new Random();
                        for (int iCount = 0; iCount< 1; iCount++){
                            randomNumber = String.valueOf(objGenerator.nextInt(1000));
                            System.out.println("Random No : " + randomNumber);
                        }
                        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), dateFormat.format(date) + "-" +  randomNumber + ".xlsx");
                        registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(result.toString()));
                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                        request.setTitle(dateFormat.format(date) + "-" + randomNumber);
                        request.allowScanningByMediaScanner();
                        // request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, dateFormat.format(date) + "-" + randomNumber + ".xlsx");
                        request.setMimeType(MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                                MimeTypeMap.getFileExtensionFromUrl(result.toString())));
                        downID = downloadManager.enqueue(request);

                    } else {
                        Const.dismissProgress();
                        Const.showErrorDialog(context,"No data found.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailerResponse(String error) {
                Const.dismissProgress();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        ShareExcel();
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Const.dismissProgress();
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy") ;
            if (downID == id) {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), dateFormat.format(date) + "-" + randomNumber + ".xlsx");
                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.setType("application/vnd.ms-excel");
                Uri uri = FileProvider.getUriForFile(StoneDetailActivity.this, BuildConfig.APPLICATION_ID + ".provider",file);
                share.putExtra(Intent.EXTRA_STREAM, uri);
                share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                builder.detectFileUriExposure();
                startActivity(Intent.createChooser(share, "Share via"));
            }
            unregisterReceiver(onDownloadComplete);
        }
    };

    private void shareDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.layout_share_info_new);

        LinearLayout llExcel = dialog.findViewById(R.id.llExcel);
        LinearLayout llDetails = dialog.findViewById(R.id.llDetails);
        LinearLayout llImages = dialog.findViewById(R.id.llImages);
        LinearLayout llVideo = dialog.findViewById(R.id.llVideo);
        LinearLayout llCerti = dialog.findViewById(R.id.llCerti);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                if (v.getId() == R.id.llExcel) {
                    ShareExcel();
                } else if (v.getId() == R.id.llDetails) {
                    String s = " ";

                    String si ="";
                    si = si+"\uD83D\uDC8E"+" "+Const.hmStoneDetail.get("stone_ref_no")+" | "+ Const.notNullString(Const.CamelCase(Const.hmStoneDetail.get("shape")),"")+"\n";
                    si = si+"\uD83D\uDCD1"+" "+ Const.hmStoneDetail.get("lab") + " " +Const.hmStoneDetail.get("certi_no")+"\n";

                    si = si + "\n";

                    if (Const.hmStoneDetail.get("location").equalsIgnoreCase("Hong Kong")) {
                        si = si + "\uD83C\uDDED\uD83C\uDDF0 ";
                    } else if (Const.hmStoneDetail.get("location").equalsIgnoreCase("India")) {
                        si = si + "\uD83C\uDDEE\uD83C\uDDF3 ";
                    } else if (Const.hmStoneDetail.get("location").equalsIgnoreCase("Upcoming")) {
                        si = si + "✈ ";
                    } else if (Const.hmStoneDetail.get("location").equalsIgnoreCase("SHOW")) {
                        si = si + "\uD83C\uDFA1";
                    } else if (Const.hmStoneDetail.get("location").equalsIgnoreCase("Dubai")) {
                        si = si + "\uD83C\uDDE6\uD83C\uDDEA ";
                    }

                    if (Const.hmStoneDetail.get("status").equalsIgnoreCase("AVAILABLE")){
                        si = si + "*"+"Available"+"*";
                    } else if (Const.hmStoneDetail.get("status").equalsIgnoreCase("AVAILABLE OFFER")){
                        si =  si + "*"+"Offer"+"*";
                    } else if (Const.hmStoneDetail.get("status").equalsIgnoreCase("NEW")){
                        si = si + "*"+"New"+"*";
                    } else if (Const.hmStoneDetail.get("status").equalsIgnoreCase("BUSS. PROCESS")){
                        si =  si + "*"+"Busy"+"*";
                    }

                    si =  si +" - ";

                    if (Const.hmStoneDetail.get("bgm").equalsIgnoreCase("NO BGM")) {
                        si =  si +"No bgm";
                    } else if (Const.hmStoneDetail.get("bgm").equalsIgnoreCase("BROWN")) {
                        si =  si + "Brown";
                    } else if (Const.hmStoneDetail.get("bgm").equalsIgnoreCase("MILKY")) {
                        si =  si + "Milky";
                    }

                    si = si +"\n\n"+ Const.notNullString(Const.hmStoneDetail.get("color"),"-")+"    "+Const.notNullString(Const.hmStoneDetail.get("clarity"),"-")+"    *"+String.format("%.2f", Double.parseDouble(Const.hmStoneDetail.get("cts")))+"*";
                    si = si + "\n";

                    si = si + Const.notNullString(Const.hmStoneDetail.get("cut"),"-") + "    " + Const.notNullString(Const.hmStoneDetail.get("polish"),"-") + "    " + Const.notNullString(Const.hmStoneDetail.get("symm"),"-") + "    " + Const.notNullString(Const.hmStoneDetail.get("fls"),"-");

                    si = si + "\n\nRap Price $ : "+String.format("%,.2f", Double.parseDouble(Const.hmStoneDetail.get("cur_rap_rate")));
                    si = si + "\nRap Amount $ : "+String.format("%,.2f", Double.parseDouble(Const.hmStoneDetail.get("rap_amount")));

                    si = si + "\n\n*Discount % : " + String.format("%.2f", Double.parseDouble(Const.hmStoneDetail.get("sales_disc_per"))) +"*";
                    si = si + "\n*Value $ : " + String.format("%,.2f", Double.parseDouble(Const.hmStoneDetail.get("net_amount")))+"*";

                    si = si + "\n\n"+String.format("%.2f", Double.parseDouble(Const.hmStoneDetail.get("length"))) + " x " + String.format("%.2f", Double.parseDouble(Const.hmStoneDetail.get("width"))) + " x " + String.format("%.2f", Double.parseDouble(Const.hmStoneDetail.get("depth")));

                    si = si + "\n\nDepth% : "+String.format("%.1f", Double.parseDouble(Const.hmStoneDetail.get("depth_per")))+" , Table% : "+String.format("%.0f", Double.parseDouble(Const.hmStoneDetail.get("table_per")));

                    boolean isImage = false;
                    if (!Const.hmStoneDetail.get("image_url").equalsIgnoreCase("") ||
                            !Const.hmStoneDetail.get("image_url1").equalsIgnoreCase("") ||
                            !Const.hmStoneDetail.get("image_url2").equalsIgnoreCase("") ||
                            !Const.hmStoneDetail.get("image_url3").equalsIgnoreCase("")) {
                        isImage = true;
                    }
                    if(isImage){
                        si = si.concat("\n\n\uD83D\uDCF7 " + Const.ShareImageUrl + Const.hmStoneDetail.get("stone_ref_no"));
                    }

                    if (!Const.hmStoneDetail.get("movie_url").equalsIgnoreCase("")) {
                        si = si.concat("\n\n\uD83C\uDFA5 " + Const.ShareVideoUrl + Const.hmStoneDetail.get("stone_ref_no"));
                    }

                    if (!Const.hmStoneDetail.get("view_certi_url").equalsIgnoreCase("")) {
                        si = si.concat("\n\n\uD83D\uDCD1 " + Const.ShareCertificateUrl + Const.hmStoneDetail.get("stone_ref_no") + "\n");
                    }

//                    if (!Const.hmStoneDetail.get("image_url").equalsIgnoreCase("")) {
//                        si = si.concat("\n\n\uD83D\uDCF7 " + Const.hmStoneDetail.get("image_url"));
//                    }
//                    if (!Const.hmStoneDetail.get("movie_url").equalsIgnoreCase("")) {
//                        si = si.concat("\n\n\uD83C\uDFA5 " + Const.hmStoneDetail.get("movie_url"));
//                    }
//                    if (!Const.hmStoneDetail.get("view_certi_url").equalsIgnoreCase("")) {
//                        si = si.concat("\n\n\uD83D\uDCD1 " + Const.hmStoneDetail.get("view_certi_url") + "\n");
//                    }

                    s = s.concat(si);
                    s = s.concat("\n------------------------------------------\n\n");
                    s = s.substring(1, s.length() - 1);
                    i.putExtra(Intent.EXTRA_TEXT, s);
                    startActivity(Intent.createChooser(i, "Share via"));
                } else if (v.getId() == R.id.llImages) {
                    String sk = "";
                    String so = "";
                    String flag = "1";
                    ArrayList<BindData> image_list = new ArrayList<BindData>();
                    if (Const.hmStoneDetail.get("isoverseas").equalsIgnoreCase("0")) {
                        if (Const.hmStoneDetail.get("bimage").equalsIgnoreCase("true")) {
                            if (!Const.hmStoneDetail.get("image_url").equalsIgnoreCase("")) {
                                HashMap<String, String> child = new HashMap<String, String>();
                                child.put("stone_ref_no", Const.hmStoneDetail.get("stone_ref_no"));
                                child.put("certi_no", Const.hmStoneDetail.get("certi_no"));
                                String si = "success";
                                sk = sk.concat(si);
                                image_list.add(new BindData(Const.hmStoneDetail.get("stone_ref_no"), Const.hmStoneDetail.get("certi_no"), Const.hmStoneDetail.get("bimage"), Const.hmStoneDetail.get("image_url")));
                            }
                        } else if (Const.hmStoneDetail.get("bimage").equalsIgnoreCase("false")) {
                            if (!Const.hmStoneDetail.get("image_url").equalsIgnoreCase("")) {
                                HashMap<String, String> child = new HashMap<String, String>();
                                child.put("stone_ref_no", Const.hmStoneDetail.get("stone_ref_no"));
                                child.put("certi_no", Const.hmStoneDetail.get("certi_no"));
                                String si = "success";
                                sk = sk.concat(si);
                                image_list.add(new BindData(Const.hmStoneDetail.get("stone_ref_no"), Const.hmStoneDetail.get("certi_no"), Const.hmStoneDetail.get("bimage"), Const.hmStoneDetail.get("image_url")));
                            }
                        }
                    } else {
                        if (Const.hmStoneDetail.get("image_url") != null && !Const.hmStoneDetail.get("image_url").equalsIgnoreCase("")) {
//                            String si = "\nStone ID : " + Const.hmStoneDetail.get("stone_ref_no") + "\n"
//                                    + "Image Link : " + Const.hmStoneDetail.get("image_url") + "\n";
                            String si = "\nStone ID : " + Const.hmStoneDetail.get("stone_ref_no") + "\n"
                                    + "Image Link : " + Const.ShareImageUrl + Const.hmStoneDetail.get("stone_ref_no") + "\n";
                            so = so.concat(si);
                        }
                    }
                    if (!sk.equals("") && !so.equals("")) {
                        new DownloadImage().download(context, image_list, so);
                        flag = "0";
                    } else {
                        if (!sk.equals("")) {
                            new DownloadImage().download(context, image_list, "");
                            flag = "0";
                        }
                        if (!so.equals("")) {
                            i.putExtra(Intent.EXTRA_TEXT, so);
                            startActivity(Intent.createChooser(i, "Share via"));
                            flag = "0";
                        }
                    }
                    if (flag == "1") {
                        Const.showErrorDialog(context, "Sorry no image(s) available for selected stone(s) to share");
                    }
                } else if (v.getId() == R.id.llVideo) {
                    String sv = "";
                    if (!Const.hmStoneDetail.get("movie_url").equalsIgnoreCase("")) {
//                        String si = "\nStone ID : " + Const.hmStoneDetail.get("stone_ref_no") + "\n"
//                                + "Video Link : " + Const.hmStoneDetail.get("movie_url") + "\n";
                        String si = "\nStone ID : " + Const.hmStoneDetail.get("stone_ref_no") + "\n"
                                + "Video Link : " + Const.ShareVideoUrl + Const.hmStoneDetail.get("stone_ref_no") + "\n";
                        sv = sv.concat(si);
                    }
                    if (!sv.equals("")) {
                        i.putExtra(Intent.EXTRA_TEXT, sv);
                        startActivity(Intent.createChooser(i, "Share via"));
                    } else {
                        Const.showErrorDialog(context, "Sorry no video link(s) available for selected stone(s) to share");
                    }
                } else if (v.getId() == R.id.llCerti) {
                    Const.showProgress(context);
                    final StringBuilder cer = new StringBuilder();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (!Const.hmStoneDetail.get("view_certi_url").equalsIgnoreCase("")) {
                                try {
                                    URL url1 = new URL(Const.hmStoneDetail.get("view_certi_url"));
                                    URLConnection conn1 = url1.openConnection();
                                    HttpURLConnection httpconn1 = (HttpURLConnection) conn1;
                                    httpconn1.setAllowUserInteraction(false);
                                    httpconn1.setConnectTimeout(10000);
                                    httpconn1.setReadTimeout(35000);
                                    httpconn1.setInstanceFollowRedirects(true);
                                    httpconn1.setRequestMethod("GET");
                                    httpconn1.connect();
                                    int res1 = httpconn1.getResponseCode();
                                    if (res1 == HttpURLConnection.HTTP_OK) {
//                                        cer.append("\nStone ID : " + Const.hmStoneDetail.get("stone_ref_no") + "\n"
//                                                + "Pdf Link : " + Const.hmStoneDetail.get("view_certi_url") + "\n");
                                        cer.append("\nStone ID : " + Const.hmStoneDetail.get("stone_ref_no") + "\n"
                                                + "Pdf Link : " + Const.ShareCertificateUrl + Const.hmStoneDetail.get("stone_ref_no") + "\n");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Const.dismissProgress();
                                    if (cer.length() > 0) {
                                        i.putExtra(Intent.EXTRA_TEXT, cer.toString());
                                        startActivity(Intent.createChooser(i, "Share via"));
                                    } else {
                                        Const.showErrorDialog(context, "Sorry no pdf link(s) available for selected stone(s) to share");
                                    }
                                }
                            });
                        }
                    }).start();
                }
                dialog.dismiss();
            }
        };

        llExcel.setOnClickListener(listener);
        llDetails.setOnClickListener(listener);
        llImages.setOnClickListener(listener);
        llVideo.setOnClickListener(listener);
        llCerti.setOnClickListener(listener);

        dialog.show();
    }

    private void downloadDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_download_info);

        final CheckBox cbBoxExcel = dialog.findViewById(R.id.cbBoxExcel);
        final CheckBox cbBoxImages = dialog.findViewById(R.id.cbBoxImages);
        final CheckBox cbBoxVideo = dialog.findViewById(R.id.cbBoxVideo);
        final CheckBox cbBoxCerti = dialog.findViewById(R.id.cbBoxCerti);
        final CheckBox cbBoxAll = dialog.findViewById(R.id.cbBoxAll);
        TextView btnOk = dialog.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                final Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                if (cbBoxExcel.isChecked()) {
                    dialog.dismiss();
                    DownloadExcel();
                }
                if (cbBoxImages.isChecked()) {
                    dialog.dismiss();
                    String sk = "";
                    ArrayList<BindData> image_list = new ArrayList<BindData>();
                    if (Const.hmStoneDetail.get("isoverseas").equalsIgnoreCase("0")) {
                        if (Const.hmStoneDetail.get("bimage").equalsIgnoreCase("true")) {
                            if (!Const.hmStoneDetail.get("image_url").equalsIgnoreCase("")) {
                                HashMap<String, String> child = new HashMap<String, String>();
                                child.put("stone_ref_no", Const.hmStoneDetail.get("stone_ref_no"));
                                child.put("certi_no", Const.hmStoneDetail.get("certi_no"));
                                String si = "success";
                                sk = sk.concat(si);
                                image_list.add(new BindData(Const.hmStoneDetail.get("stone_ref_no"), Const.hmStoneDetail.get("certi_no"), Const.hmStoneDetail.get("bimage"), Const.hmStoneDetail.get("image_url")));
                            }
                        } else if (Const.hmStoneDetail.get("bimage").equalsIgnoreCase("false")) {
                            if (!Const.hmStoneDetail.get("image_url").equalsIgnoreCase("")) {
                                HashMap<String, String> child = new HashMap<String, String>();
                                child.put("stone_ref_no", Const.hmStoneDetail.get("stone_ref_no"));
                                child.put("certi_no", Const.hmStoneDetail.get("certi_no"));
                                String si = "success";
                                sk = sk.concat(si);
                                image_list.add(new BindData(Const.hmStoneDetail.get("stone_ref_no"), Const.hmStoneDetail.get("certi_no"), Const.hmStoneDetail.get("bimage"), Const.hmStoneDetail.get("image_url")));
                            }
                        }
                        if (!sk.equals("")) {
                            new DownloadImage().download1(context, image_list);
                        } else {
                            Const.showErrorDialog(context, "Sorry no image available for this stone to download");
                        }
                    } else {
                        downloadOversesImage();
                    }
                }
                if (cbBoxVideo.isChecked()) {
                    dialog.dismiss();
                    downloadmedia();
                }
                if (cbBoxCerti.isChecked()) {
                    dialog.dismiss();
                    Const.showProgress(context);
                    final StringBuilder cernew = new StringBuilder();
                    final StringBuilder cer = new StringBuilder();
                    final StringBuilder cername = new StringBuilder();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (! Const.hmStoneDetail.get("view_certi_url").equalsIgnoreCase("")) {
                                try {
                                    URL url1 = new URL( Const.hmStoneDetail.get("view_certi_url"));
                                    URLConnection conn1 = url1.openConnection();
                                    HttpURLConnection httpconn1 = (HttpURLConnection) conn1;
                                    httpconn1.setAllowUserInteraction(false);
                                    httpconn1.setConnectTimeout(10000);
                                    httpconn1.setReadTimeout(35000);
                                    httpconn1.setInstanceFollowRedirects(true);
                                    httpconn1.setRequestMethod("GET");
                                    httpconn1.connect();
                                    int res1 = httpconn1.getResponseCode();
                                    if (res1 == HttpURLConnection.HTTP_OK) {
                                        cernew.append( Const.hmStoneDetail.get("view_certi_url"));
                                        cer.append( Const.hmStoneDetail.get("view_certi_url"));
                                        cername.append( Const.hmStoneDetail.get("stone_ref_no"));

                                        try {
                                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(cer.toString()));
                                            request.setDescription("download");
                                            request.setTitle(cername.toString());

                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                                request.allowScanningByMediaScanner();
                                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                            }
                                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, cername.toString() + ".pdf");
                                            DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                                            manager.enqueue(request);
                                            cer.delete(0, cer.length());
                                            cername.delete(0, cername.length());
                                        } catch (Exception ex) {
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                           //        }
                            }
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Const.dismissProgress();
                                    if (cernew.length() > 0) {
                                        i.putExtra(Intent.EXTRA_TEXT, cer.toString());
                                        Toast.makeText(context, "stones to download information successfully!", Toast.LENGTH_LONG).show();
                                    } else {
                                        Const.showErrorDialog(context, "Sorry no pdf link(s) available for selected stone(s) to download");
                                    }
                                }
                            });
                        }
                    }).start();
                }
                if (cbBoxAll.isChecked()) {
                    dialog.dismiss();
                    DownloadExcel();
                    String sk = "";
                    ArrayList<BindData> image_list = new ArrayList<BindData>();
                    if (Const.hmStoneDetail.get("isoverseas").equalsIgnoreCase("0")) {
                        if (Const.hmStoneDetail.get("bimage").equalsIgnoreCase("true")) {
                            if (!Const.hmStoneDetail.get("image_url").equalsIgnoreCase("")) {
                                HashMap<String, String> child = new HashMap<String, String>();
                                child.put("stone_ref_no", Const.hmStoneDetail.get("stone_ref_no"));
                                child.put("certi_no", Const.hmStoneDetail.get("certi_no"));
                                String si = "success";
                                sk = sk.concat(si);
                                image_list.add(new BindData(Const.hmStoneDetail.get("stone_ref_no"), Const.hmStoneDetail.get("certi_no"), Const.hmStoneDetail.get("bimage"), Const.hmStoneDetail.get("image_url")));
                            }
                        } else if (Const.hmStoneDetail.get("bimage").equalsIgnoreCase("false")) {
                            if (!Const.hmStoneDetail.get("image_url").equalsIgnoreCase("")) {
                                HashMap<String, String> child = new HashMap<String, String>();
                                child.put("stone_ref_no", Const.hmStoneDetail.get("stone_ref_no"));
                                child.put("certi_no", Const.hmStoneDetail.get("certi_no"));
                                String si = "success";
                                sk = sk.concat(si);
                                image_list.add(new BindData(Const.hmStoneDetail.get("stone_ref_no"), Const.hmStoneDetail.get("certi_no"), Const.hmStoneDetail.get("bimage"), Const.hmStoneDetail.get("image_url")));
                            }
                        }
                        if (!sk.equals("")) {
                            new DownloadImage().download1(context, image_list);
                        } else {
                            Const.showErrorDialog(context, "Sorry no image available for this stone to download");
                        }
                    } else {
                        downloadOversesImage();
                    }

                    downloadmedia();

                    Const.showProgress(context);
                    final StringBuilder cernew = new StringBuilder();
                    final StringBuilder cer = new StringBuilder();
                    final StringBuilder cername = new StringBuilder();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                          if (! Const.hmStoneDetail.get("view_certi_url").equalsIgnoreCase("")) {
                              try {
                                    URL url1 = new URL( Const.hmStoneDetail.get("view_certi_url"));
                                    URLConnection conn1 = url1.openConnection();
                                    HttpURLConnection httpconn1 = (HttpURLConnection) conn1;
                                    httpconn1.setAllowUserInteraction(false);
                                    httpconn1.setConnectTimeout(10000);
                                    httpconn1.setReadTimeout(35000);
                                    httpconn1.setInstanceFollowRedirects(true);
                                    httpconn1.setRequestMethod("GET");
                                    httpconn1.connect();
                                    int res1 = httpconn1.getResponseCode();
                                    if (res1 == HttpURLConnection.HTTP_OK) {
                                        cernew.append( Const.hmStoneDetail.get("view_certi_url"));
                                        cer.append( Const.hmStoneDetail.get("view_certi_url"));
                                        cername.append( Const.hmStoneDetail.get("stone_ref_no"));

                                        try {
                                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(cer.toString()));
                                            request.setDescription("download");
                                            request.setTitle(cername.toString());

                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                                request.allowScanningByMediaScanner();
                                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                            }
                                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, cername.toString() + ".pdf");
                                            DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                                            manager.enqueue(request);
                                            cer.delete(0, cer.length());
                                            cername.delete(0, cername.length());
                                        } catch (Exception ex) {
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                //        }
                            }
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Const.dismissProgress();
                                    if (cernew.length() > 0) {
                                        i.putExtra(Intent.EXTRA_TEXT, cer.toString());
                                        Toast.makeText(context, "stones to download information successfully!", Toast.LENGTH_LONG).show();
                                    } else {
                                        Const.showErrorDialog(context, "Sorry no pdf link(s) available for selected stone(s) to download");
                                    }
                                }
                            });
                        }
                    }).start();
                }

            }
        });
        dialog.show();
    }

    private void addToCart() {

        final Map<String, String> map = new HashMap<>();
        // Offer & Busy Status stone customer add to cart k place order nahi karse All page par thi
        map.put("OfferTrans", "0");
        map.put("TransType", "A");
        String holdStoneID_value="";

        List<String> valuedelete=new ArrayList<>();
        valuedelete.clear();

        if (Pref.getStringValue(context, Const.loginIsCust, "").equalsIgnoreCase("1")) {
            if(Const.hmStoneDetail.get("status").equalsIgnoreCase("AVAILABLE OFFER") || Const.hmStoneDetail.get("status").equalsIgnoreCase("BUSS. PROCESS")) {
                if (Const.hmStoneDetail.get("forcust_hold").equals("1")) {
                    map.put("StoneID", Const.hmStoneDetail.get("stone_ref_no"));
                    add_to_cart_Api(map);
                }
                else
                {
                    contact_dialog("cart");
                }
            }
            else
            {
                map.put("StoneID", Const.hmStoneDetail.get("stone_ref_no"));
                add_to_cart_Api(map);
            }
        }
        else
        {
            map.put("StoneID", Const.hmStoneDetail.get("stone_ref_no"));
            Log.e("Input Parameter ",map.toString());
            add_to_cart_Api(map);
        }

    }


    private void PlaceOrder_process() {
        iuseridComp = "0";
        final Map<String, String> map = new HashMap<>();
        // Offer & Busy Status stone customer add to cart k place order nahi karse All page par thi
        String holdStoneID_value="";

        List<String> valuedelete=new ArrayList<>();
        valuedelete.clear();

        if (Pref.getStringValue(context, Const.loginIsCust, "").equalsIgnoreCase("1")) {
            if(Const.hmStoneDetail.get("status").equalsIgnoreCase("AVAILABLE OFFER") || Const.hmStoneDetail.get("status").equalsIgnoreCase("BUSS. PROCESS")) {
                if (Const.hmStoneDetail.get("forcust_hold").equals("1")) {
                    holdStoneID_value = Const.hmStoneDetail.get("stone_ref_no").toString() + "_" + Const.hmStoneDetail.get("hold_party_code").toString();
                    map.put("StoneID", Const.hmStoneDetail.get("stone_ref_no"));
                    map.put("Hold_StoneID",holdStoneID_value);
                    PlaceOrderDialog(map);
                }
                else
                {
                    contact_dialog("order");
                }
            } else {
                map.put("StoneID", Const.hmStoneDetail.get("stone_ref_no"));
                map.put("Hold_StoneID",holdStoneID_value);
                PlaceOrderDialog(map);
            }
        } else {
            map.put("StoneID", Const.hmStoneDetail.get("stone_ref_no"));
            map.put("Hold_StoneID",holdStoneID_value);
            if(Const.hmStoneDetail.get("status").equalsIgnoreCase("BUSS. PROCESS")) {
                if (Pref.getStringValue(context, Const.loginIsEmp, "").equalsIgnoreCase("1")) {
                    if(Const.hmStoneDetail.get("forassist_hold").equals("0")){
                        Const.showErrorDialog(context, "You are not Authorize to Place Order of this Stone Id " +
                                Const.hmStoneDetail.get("stone_ref_no") +"...!");
                    }else{
                        openPlaceOrder(Const.hmStoneDetail.get("stone_ref_no"));
                    }
                }else{
                    openPlaceOrder(Const.hmStoneDetail.get("stone_ref_no"));
                }
            }else{
                if(Const.hmStoneDetail.get("status").equalsIgnoreCase("AVAILABLE") || Const.hmStoneDetail.get("status").equalsIgnoreCase("NEW")) {
                    PlaceOrderDialogNew1(map,Const.hmStoneDetail.get("stone_ref_no"));
                }else{
                    PlaceOrderDialog(map);
                }
            }
        }

    }

    private void contact_dialog(String flag_cartOrder) {
        String msg ="";
        if (flag_cartOrder.equals("cart")) {
            msg = "This Stone is not Available\nfor Add to Cart ! \n\nPlease Conatact Your Sales Person.\n\n";
        }
        else if (flag_cartOrder.equals("order"))
        {
            msg = "This Stone is not Available\nfor Place Order ! \n\nPlease Conatact Your Sales Person.\n\n";
        }
        Map<String, String> map = new HashMap<>();
        final String finalMsg = msg;
        Const.callPostApiLogin(context, "Order/GetAssistPersonDetail", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    Iterator<String> keys = object.keys();
                    HashMap<String, String> hm = new HashMap<>();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        hm.put(key.toLowerCase(), object.optString(key).trim());
                    }
                    Const.showErrorDialog(context, finalMsg + Const.notNullString(hm.get("message"), "Something Want Wrong, Please try Again Later"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailerResponse(String error) {
                Const.showErrorDialog(context, finalMsg);
            }
        });

    }

    private RecyclerView rv, rvHoldedComp;
    private LinearLayout llHorizontalComp;
    private RelativeLayout relCompany;
    private Dialog dialog;
    EditText etSr;
    TextView tvInfo;
    HorizontalScrollView hview_rv, hrCompany;
    LinearLayout noresult_layout;
    List<HashMap<String, String>> compMaps = new ArrayList<>();
    List<HashMap<String, String>> compList = new ArrayList<>();
    ResultAdapter resultAdapter;
    String iuseridComp = "0";

    private void openPlaceOrder(String stoneId) {
        dialog = new Dialog(StoneDetailActivity.this, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.layout_selection_company);

        llHorizontalComp=dialog.findViewById(R.id.llHorizontal);
        llHorizontalComp.setVisibility(View.GONE);
        tvInfo = dialog.findViewById(R.id.tvInfo);
        hview_rv=dialog.findViewById(R.id.hview_rv);
        hrCompany=dialog.findViewById(R.id.hrCompany);
        noresult_layout=dialog.findViewById(R.id.noresult_layout);
        EditText etComments = dialog.findViewById(R.id.etComments);

        relCompany=dialog.findViewById(R.id.relCompany);
        relCompany.setVisibility(View.GONE);

        etSr = dialog.findViewById(R.id.etSearch);
        rv = dialog.findViewById(R.id.rvComp);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        dialog.setCancelable(false);
        dialog.show();

        if (compMaps.size() > 0)
        {
            resultAdapter = new ResultAdapter(StoneDetailActivity.this);
            rv.setAdapter(resultAdapter);
            resultAdapter.addAll(compMaps);
        }else{
            relCompany.setVisibility(View.GONE);
            llHorizontalComp.setVisibility(View.GONE);
            hview_rv.setVisibility(View.GONE);
            noresult_layout.setVisibility(View.VISIBLE);
        }

        List<String> holdString = new ArrayList<>();
        for (int i = 0; i < holdItem.size(); i++) {
            if (holdItem.get(i) != null) {
                if(!holdItem.get(i).get("hold_compname").equalsIgnoreCase("") && !holdItem.get(i).get("hold_compname").equalsIgnoreCase("null")){
                    holdString.add(holdItem.get(i).get("stone_ref_no"));
                }
            }
        }

        rvHoldedComp = dialog.findViewById(R.id.rvHoldedComp);
        rvHoldedComp.setHasFixedSize(true);
        rvHoldedComp.setLayoutManager(new LinearLayoutManager(this));
        ResultHoldedAdapter adapter = new ResultHoldedAdapter(StoneDetailActivity.this, holdItem);
        rvHoldedComp.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        if(holdItem.size() <= 0){
            hrCompany.setVisibility(View.GONE);
        }else{
            String holdStoneId = holdString.toString().replace("[", "").replace("]", "").replace(", ", ",");
            tvInfo.setText("Please select a company for Hold, Stone Id " + holdStoneId + "...!");
            if(holdItem.size() > 4){
                ViewGroup.LayoutParams params = hrCompany.getLayoutParams();
                params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180, getResources().getDisplayMetrics());;
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                hrCompany.setLayoutParams(params);
            }
        }

        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnSend = dialog.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if(etSr.getText().toString().length() <= 0){
//                    Const.showErrorDialog(context, "Please select Company name, Fortune party code and User name");
//                    return;
//                }
                if(etComments.getText().toString().length() <= 0){
                    Const.showErrorDialog(context, "Please enter comments");
                    return;
                }

                JSONArray Orders= new JSONArray();
                for (int i=0; i<holdItem.size(); i++)
                {
                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("sRefNo",holdItem.get(i).get("stone_ref_no"));
                        obj.put("Hold_Party_Code",holdItem.get(i).get("hold_party_code"));
                        obj.put("Hold_CompName",holdItem.get(i).get("hold_compname"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Orders.put(obj);
                }

                Log.e("Hold_Stone_List ",Orders.toString());

                JSONObject jsonObject1 = new JSONObject();
                try {
                    jsonObject1.put("Comments",etComments.getText().toString());
                    jsonObject1.put("StoneID",stoneId);
                    jsonObject1.put("Userid",iuseridComp);
                    jsonObject1.put("IsAdminEmp_Hold",iuseridComp.equals("0") ? "false" : "true");
                    jsonObject1.put("Hold_Stone_List",Orders);
                    jsonObject1.put("IsFromAPI","false");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.e("aa new ",jsonObject1.toString());

                PlaceOrder_ApiNew(jsonObject1);
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iuseridComp = "0";
                dialog.dismiss();
            }
        });

        etSr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (String.valueOf(charSequence).isEmpty())
                {
                    relCompany.setVisibility(View.GONE);
                    llHorizontalComp.setVisibility(View.GONE);
                }
                else
                {
                    relCompany.setVisibility(View.VISIBLE);
                    hview_rv.setVisibility(View.VISIBLE);
                    noresult_layout.setVisibility(View.GONE);

                    llHorizontalComp.setVisibility(View.VISIBLE);
                    if(resultAdapter != null){
                        List<HashMap<String,String>> searchMainList = new ArrayList<>();
                        for (HashMap<String, String> data : compMaps) {
                            if (Const.notNullString(data.get("compname"), "").toLowerCase().contains(charSequence.toString().toLowerCase())
                                    || Const.notNullString(data.get("username"), "").toLowerCase().contains(charSequence.toString().toLowerCase())
                                    || Const.notNullString(data.get("custname"), "").toLowerCase().contains(charSequence.toString().toLowerCase())) {
                                searchMainList.add(data);
                            }
                        }
                        resultAdapter.addAll(searchMainList);
                        Log.e("Search Result : ", searchMainList.size() + "");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void get_companyName() {
        compMaps.clear();
        compList.clear();
        Const.callPostApi(StoneDetailActivity.this, "User/GetCompanyForHoldStonePlaceOrder", null, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.optString("Status").equalsIgnoreCase("1")) {
                        JSONArray array = new JSONObject(result).optJSONArray("Data");
                        if (array.length() != 0) {
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object1 = array.optJSONObject(i);
                                Iterator<String> keys = object1.keys();
                                HashMap<String, String> hm = new HashMap<>();
                                while (keys.hasNext()) {
                                    String key = keys.next();
                                    hm.put(key.toLowerCase(), object1.optString(key).trim());
                                }
                                compMaps.add(hm);
                                compList.add(hm);
                            }
                            for (Map<String, String> a:compMaps) {
                                Log.e("print ",a.toString());
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailerResponse(String error) {

            }
        });
    }

    private void getHoldStoneCustomer(final String userId, String stoneId) {
        final ArrayList<HashMap<String, String>> maps = new ArrayList<>();
        maps.clear();
        final Map<String, String> map = new HashMap<>();
        map.put("UserID", userId);
        map.put("StoneID", stoneId);
        Const.callPostApi(StoneDetailActivity.this, "Stock/Hold_Stone_Avail_Customers", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Log.e("Hold_Stone_Avail : ",result);
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.optString("Status").equalsIgnoreCase("1")) {

                    } else {
                        if(etSr != null){
                            etSr.setText("");
                        }
                        if(etSr1 != null){
                            etSr1.setText("");
                        }
                        String ob = new JSONObject(result).optString("Message");
                        Const.showErrorDialog(context, Const.notNullString(ob, "Something want wrong please try again later"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailerResponse(String error) {
            }
        });
    }

    public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder>
    {
        Context context;
        private List<HashMap<String,String>> mainlists = new ArrayList<>();

        public ResultAdapter(Context context) {
            this.context = context;
        }

        public void addAll(List<HashMap<String, String>> files) {

            try {
                this.mainlists.clear();
                this.mainlists.addAll(files);

            } catch (Exception e) {
                e.printStackTrace();
            }

            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.layout_company, parent, false);
            ResultAdapter.ViewHolder viewHolder = new ResultAdapter.ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ResultAdapter.ViewHolder holder, int position) {
            final HashMap<String, String> map = mainlists.get(position);
            holder.tvCompName.setText(Const.notNullString(map.get("compname"), ""));
            holder.tvFpc.setText(Const.notNullString(map.get("fortunepartycode"), ""));
            holder.tvUserName.setText(Const.notNullString(map.get("username"), ""));
            holder.tvCustName.setText(Const.notNullString(map.get("custname"), ""));
            holder.llMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iuseridComp = Const.notNullString(map.get("iuserid"), "");
                    etSr.setText(Const.notNullString(map.get("compname"), ""));
                    etSr.setSelection(etSr.getText().toString().length());
                    relCompany.setVisibility(View.GONE);
                    llHorizontalComp.setVisibility(View.GONE);
                    hview_rv.setVisibility(View.GONE);
                    getHoldStoneCustomer(iuseridComp,Const.hmStoneDetail.get("stone_ref_no"));
//                    dialog.dismiss();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mainlists.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            LinearLayout llMain;
            TextView tvCompName,tvFpc,tvUserName,tvCustName;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                llMain=itemView.findViewById(R.id.llMain);
                tvCompName=itemView.findViewById(R.id.tvCompName);
                tvFpc=itemView.findViewById(R.id.tvFpc);
                tvUserName=itemView.findViewById(R.id.tvUserName);
                tvCustName=itemView.findViewById(R.id.tvCustName);

            }
        }
    }

    public class ResultHoldedAdapter extends RecyclerView.Adapter<ResultHoldedAdapter.ViewHolder>
    {
        Context context;
        List<HashMap<String,String>> mainlist;

        public ResultHoldedAdapter(Context context, List<HashMap<String, String>> mainlist) {
            this.context = context;
            this.mainlist = mainlist;
        }

        @NonNull
        @Override
        public ResultHoldedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.layout_company_holded, parent, false);
            ResultHoldedAdapter.ViewHolder viewHolder = new ResultHoldedAdapter.ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ResultHoldedAdapter.ViewHolder holder, int position) {
            final HashMap<String, String> map = mainlist.get(position);
            holder.tvCompany.setText(Const.notNullString(map.get("hold_compname"), ""));
            holder.tvStockID.setText(Const.notNullString(map.get("stone_ref_no"), ""));
            if(Const.notNullString(map.get("hold_party_code"), "").equalsIgnoreCase("0")){
                holder.tvPartyCode.setText("");
            }else{
                holder.tvPartyCode.setText(Const.notNullString(map.get("hold_party_code"), ""));
            }
            holder.llMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mainlist.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            LinearLayout llMain;
            TextView tvCompany,tvPartyCode,tvStockID;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                llMain=itemView.findViewById(R.id.llMain);
                tvCompany=itemView.findViewById(R.id.tvCompany);
                tvPartyCode=itemView.findViewById(R.id.tvPartyCode);
                tvStockID=itemView.findViewById(R.id.tvStockID);

            }
        }
    }

    EditText etSr1;
    ResultAdapter1 resultAdapter1;
    RelativeLayout relCompany1;
    HorizontalScrollView hview_rv1;
    RecyclerView rv1;
    LinearLayout llAssistBy1;

    private void PlaceOrderDialogNew1(final Map<String, String> map, String stoneId) {

        final Dialog dialog = new Dialog(context, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.layout_placeorder_new_one);
        final EditText etComments = dialog.findViewById(R.id.etComments);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnSend = dialog.findViewById(R.id.btnSend);

        etSr1 = dialog.findViewById(R.id.etSearch);
        llAssistBy1 = dialog.findViewById(R.id.llAssistBy);
        rv1 = dialog.findViewById(R.id.rvComp);
        rv1.setHasFixedSize(true);
        rv1.setLayoutManager(new LinearLayoutManager(this));
        hview_rv1=dialog.findViewById(R.id.hview_rv);
        relCompany1=dialog.findViewById(R.id.relCompany);
        relCompany1.setVisibility(View.GONE);

        if (Pref.getStringValue(context, Const.loginIsAdmin, "").equalsIgnoreCase("1")) {
            llAssistBy1.setVisibility(View.VISIBLE);
        } else {
            llAssistBy1.setVisibility(View.GONE);
        }

        if (compMaps.size() > 0)
        {
            resultAdapter1 = new ResultAdapter1(StoneDetailActivity.this);
            rv1.setAdapter(resultAdapter1);
            resultAdapter1.addAll(compMaps);
        }else{
            relCompany1.setVisibility(View.GONE);
            hview_rv1.setVisibility(View.GONE);
        }

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                map.put("Comments",etComments.getText().toString());

                if(etComments.getText().toString().length() <= 0){
                    Const.showErrorDialog(context, "Please enter comments");
                    return;
                }

                JSONArray Orders= new JSONArray();
                for (int i=0; i<holdItem.size(); i++)
                {
                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("sRefNo",holdItem.get(i).get("stone_ref_no"));
                        obj.put("Hold_Party_Code",holdItem.get(i).get("hold_party_code"));
                        obj.put("Hold_CompName",holdItem.get(i).get("hold_compname"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Orders.put(obj);
                }

                Log.e("Hold_Stone_List ",Orders.toString());

                JSONArray unHoldOrder= new JSONArray();
                for (int i=0; i<unHoldItem.size(); i++)
                {
                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("sRefNo",unHoldItem.get(i).get("stone_ref_no"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    unHoldOrder.put(obj);
                }

                Log.e("UnHold_Stone_List ",unHoldOrder.toString());

                JSONObject jsonObject1 = new JSONObject();
                try {
                    jsonObject1.put("Comments",etComments.getText().toString());
                    jsonObject1.put("StoneID",stoneId);
                    jsonObject1.put("Userid",iuseridComp);
                    jsonObject1.put("IsAdminEmp_Hold",iuseridComp.equals("0") ? "false" : "true");
                    jsonObject1.put("Hold_Stone_List",Orders);
                    jsonObject1.put("UnHold_Stone_List",unHoldOrder);
                    jsonObject1.put("IsFromAPI","false");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.e("aa new ",jsonObject1.toString());

//                PlaceOrder_Api(maps);
                PlaceOrder_ApiNew(jsonObject1);
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        etSr1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (String.valueOf(charSequence).isEmpty())
                {
                    relCompany1.setVisibility(View.GONE);
                }
                else
                {
                    relCompany1.setVisibility(View.VISIBLE);
                    hview_rv1.setVisibility(View.VISIBLE);

                    if(resultAdapter1 != null){
                        List<HashMap<String,String>> searchMainList = new ArrayList<>();
                        for (HashMap<String, String> data : compMaps) {
                            if (Const.notNullString(data.get("compname"), "").toLowerCase().contains(charSequence.toString().toLowerCase())
                                    || Const.notNullString(data.get("username"), "").toLowerCase().contains(charSequence.toString().toLowerCase())
                                    || Const.notNullString(data.get("custname"), "").toLowerCase().contains(charSequence.toString().toLowerCase())) {
                                searchMainList.add(data);
                            }
                        }
                        resultAdapter1.addAll(searchMainList);
                        Log.e("Search Result : ", searchMainList.size() + "");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        dialog.show();
    }

    public class ResultAdapter1 extends RecyclerView.Adapter<ResultAdapter1.ViewHolder>
    {
        Context context;
        private List<HashMap<String,String>> mainlists = new ArrayList<>();

        public ResultAdapter1(Context context) {
            this.context = context;
        }

        public void addAll(List<HashMap<String, String>> files) {

            try {
                this.mainlists.clear();
                this.mainlists.addAll(files);

            } catch (Exception e) {
                e.printStackTrace();
            }

            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ResultAdapter1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.layout_company_one, parent, false);
            ResultAdapter1.ViewHolder viewHolder = new ResultAdapter1.ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ResultAdapter1.ViewHolder holder, int position) {
            final HashMap<String, String> map = mainlists.get(position);
            holder.tvCompName.setText(Const.notNullString(map.get("compname"), ""));
            holder.tvFpc.setText(Const.notNullString(map.get("fortunepartycode"), ""));
            holder.tvAssistBy.setText(Const.notNullString(map.get("assistby"), ""));
            holder.tvCustName.setText(Const.notNullString(map.get("custname"), ""));

            if (Pref.getStringValue(context, Const.loginIsAdmin, "").equalsIgnoreCase("1")){
                holder.llAssistBy.setVisibility(View.VISIBLE);
            }else{
                holder.llAssistBy.setVisibility(View.GONE);
            }

            holder.llMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iuseridComp = Const.notNullString(map.get("iuserid"), "");
                    etSr1.setText(Const.notNullString(map.get("compname"), ""));
                    etSr1.setSelection(etSr1.getText().toString().length());
                    relCompany1.setVisibility(View.GONE);
                    hview_rv1.setVisibility(View.GONE);
                    getHoldStoneCustomer(iuseridComp,Const.hmStoneDetail.get("stone_ref_no"));
//                    dialog.dismiss();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mainlists.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            LinearLayout llMain, llAssistBy;
            TextView tvCompName,tvFpc,tvAssistBy,tvCustName;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                llMain=itemView.findViewById(R.id.llMain);
                llAssistBy=itemView.findViewById(R.id.llAssistBy);
                tvCompName=itemView.findViewById(R.id.tvCompName);
                tvFpc=itemView.findViewById(R.id.tvFpc);
                tvAssistBy=itemView.findViewById(R.id.tvAssistBy);
                tvCustName=itemView.findViewById(R.id.tvCustName);

            }
        }
    }

    private void PlaceOrderDialog(final Map<String, String> map) {
        final Dialog dialog = new Dialog(context, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.layout_placeorder);
        final EditText etComments = dialog.findViewById(R.id.etComments);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnSend = dialog.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.put("Comments",etComments.getText().toString());

                if(etComments.getText().toString().length() <= 0){
                    Const.showErrorDialog(context, "Please enter comments");
                    return;
                }

                JSONArray Orders= new JSONArray();
                for (int i=0; i<holdItem.size(); i++)
                {
                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("sRefNo",holdItem.get(i).get("stone_ref_no"));
                        obj.put("Hold_Party_Code",holdItem.get(i).get("hold_party_code"));
                        obj.put("Hold_CompName",holdItem.get(i).get("hold_compname"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Orders.put(obj);
                }

                Log.e("Hold_Stone_List ",Orders.toString());

                JSONObject jsonObject1 = new JSONObject();
                try {
                    jsonObject1.put("Comments",etComments.getText().toString());
                    jsonObject1.put("StoneID",Const.hmStoneDetail.get("stone_ref_no"));
                    jsonObject1.put("Userid",iuseridComp);
                    jsonObject1.put("IsAdminEmp_Hold",iuseridComp.equals("0") ? "false" : "true");
                    jsonObject1.put("Hold_Stone_List",Orders);
                    jsonObject1.put("IsFromAPI","false");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.e("aa new ",jsonObject1.toString());

//                PlaceOrder_Api(map);
                PlaceOrder_ApiNew(jsonObject1);

                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void PlaceOrder_Api(final Map<String, String> map) {
        Const.showProgress(context);
        Const.callPostApiPlaceOrder(context, "Order/PurchaseConfirmOrder", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Const.dismissProgress();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.optString("Status").equalsIgnoreCase("SUCCESS")) {
                        String ob = new JSONObject(result).optString("Message").replace("<br>","\n").replace("&nbsp;","\n");
                        String message = ob.toString().replaceAll("\\<.*?>","");
                        showDialogPlaceOrder(context, Const.notNullString(message, "This Stone(s) are subject to avaibility 'BZM-318692'"));
                    } else {
                        String ob = new JSONObject(result).optString("Message");
                        Const.showErrorDialog(context, Const.notNullString(ob, "Something want wrong please try again later"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailerResponse(String error) {
                Const.dismissProgress();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        PlaceOrder_Api(map);
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    private void PlaceOrder_ApiNew(final JSONObject placeorderMap) {
        Log.e("PlaceOrder_ApiNew ", placeorderMap.toString());
        Const.showProgress(context);
        Const.callPostApiPlaceOrderSupplier(context, Const.isDummy ? "Order/PurchaseConfirmOrder_Web_1" : "Order/PurchaseConfirmOrder", placeorderMap, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Log.e("PlaceOrder_ApiNew Res ", result);
                Const.dismissProgress();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.optString("Status").equalsIgnoreCase("SUCCESS")) {
                        if (Const.isNotNull(object.optString("Error")) && object.optString("Error").contains("_")){
                            callAutoPlaceAPI(object.optString("Error"));
                        }
                        String ob = new JSONObject(result).optString("Message").replace("<br>","\n").replace("&nbsp;","\n");
                        String message = ob.toString().replaceAll("\\<.*?>","");
                        iuseridComp = "0";
                        showDialogPlaceOrder(context, Const.notNullString(message, "This Stone(s) are subject to avaibility 'BZM-318692'"));
                    } else {
                        String ob = new JSONObject(result).optString("Message");
                        Const.showErrorDialog(context, Const.notNullString(ob, "Something want wrong please try again later"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailerResponse(String error) {
                Const.dismissProgress();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        PlaceOrder_ApiNew(placeorderMap);
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    private void callAutoPlaceAPI(String id){
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("iOrderid_sRefNo",id);
            jsonObject1.put("DeviceType","ANDROID");
            jsonObject1.put("IpAddress",Const.getIpAddress());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("AutoPlaceOrderAPI new ",jsonObject1.toString());

        AutoPlaceOrderAPI(jsonObject1);
    }

    private void AutoPlaceOrderAPI(final JSONObject jsonObject) {
        Log.e("AutoPlaceOrderAPI ", jsonObject.toString());
        Const.callPostApiPlaceOrderSupplier(context, "ConfirmOrder/AUTO_PlaceConfirmOrderUsingApi_Application", jsonObject, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Log.e("AutoPlaceOrderAPI Res ", result);
            }

            @Override
            public void onFailerResponse(String error) {

            }
        });
    }

    private void add_to_cart_Api(final Map<String, String> map) {
        Const.showProgress(context);

        Const.callPostApi(context, "Order/AddRemoveToCart", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Const.dismissProgress();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.optString("Status").equalsIgnoreCase("1")) {
                        CartDialog(object);
                        // String ob = new JSONObject(result).optString("Message");
                        // Const.showErrorDialog(context, Const.notNullString(ob, "Stone(s) added in cart successfully"));
                    } else {
                        String ob = new JSONObject(result).optString("Message");
                        Const.showErrorDialog(context, Const.notNullString(ob, "Something want wrong please try again later"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailerResponse(String error) {
                Const.dismissProgress();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        add_to_cart_Api(map);
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @SuppressLint("WrongConstant")
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.imgMenu:
                    drawerLayout.openDrawer(Gravity.START);
                    break;
                case R.id.overlay:
                    overlay.setVisibility(View.GONE);
                    break;
                case R.id.imgOne:
                    if (!image1String.equalsIgnoreCase("")) {
                        fullImage(image1String);
                    }
                    break;
                case R.id.imgTwo:
                    if (!image2String.equalsIgnoreCase("")) {
                        fullImage(image2String);
                    }
                    break;
                case R.id.imgThree:
                    if (!image3String.equalsIgnoreCase("")) {
                        fullImage(image3String);
                    }
                    break;
                case R.id.imgFour:
                    if (!image4String.equalsIgnoreCase("")) {
                        fullImage(image4String);
                    }
                    break;
                case R.id.imgBack:
                    onBackPressed();
                    overridePendingTransition(0, 0);
                    break;
                case R.id.llPlaceOrder:
                    if (Pref.getStringValue(context, Const.loginIsCust, "").equals("1")) {
                        if (Pref.getStringValue(context, Const.PlaceOrder, "").equals("true")) {
                            PlaceOrder_process();
                        }
                        else
                        {
                            Const.showErrorDialog(context, "You have not right to access Place Order");
                        }
                    }
                    else {
                        PlaceOrder_process();
                    }
                    break;
                case R.id.llCart:
                    if (Pref.getStringValue(context, Const.loginIsCust, "").equalsIgnoreCase("1")) {
                        if (Const.hmStoneDetail.get("status").equalsIgnoreCase("AVAILABLE OFFER") || Const.hmStoneDetail.get("status").equalsIgnoreCase("BUSS. PROCESS")) {
                            contact_dialog("cart");
                        } else {
                            if (Pref.getStringValue(context, Const.MyCart, "").equals("true")) {
                                addToCart();
                            } else {
                                Const.showErrorDialog(context, "You have not right for Add to Cart");
                            }
                        }
                    } else {
                        if (Pref.getStringValue(context, Const.MyCart, "").equals("true")) {
                            addToCart();
                        } else {
                            Const.showErrorDialog(context, "You have not right for Add to Cart");
                        }
                    }
                    break;
                case R.id.llDownload:
                    downloadDialog();
                    break;
                case R.id.llShare:
                    shareDialog();
                    break;
                case R.id.tvVideo:
                    if (rlVideo.getVisibility() != View.VISIBLE) {
                        tvVideo.setTextColor(Color.WHITE);
                        tvVideo.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        tvImage.setBackgroundDrawable(null);
                        tvImage.setTextColor(getResources().getColor(R.color.colorPrimary));
                        tvDetails.setBackgroundDrawable(null);
                        tvDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
                        tvCertificate.setBackgroundDrawable(null);
                        tvCertificate.setTextColor(getResources().getColor(R.color.colorPrimary));
                        if (svDetails.getVisibility() == View.VISIBLE) {
                            Animation animRight2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_left);
                            svDetails.startAnimation(animRight2);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Animation animRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_left);
                                    rlVideo.startAnimation(animRight);
                                    svDetails.setVisibility(View.GONE);
                                    rlVideo.setVisibility(View.VISIBLE);
                                }
                            }, 50);
                        } else if (rlCertificate.getVisibility() == View.VISIBLE) {
                            Animation animRight2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_left);
                            rlCertificate.startAnimation(animRight2);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Animation animRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_left);
                                    rlVideo.startAnimation(animRight);
                                    rlCertificate.setVisibility(View.GONE);
                                    rlVideo.setVisibility(View.VISIBLE);
                                }
                            }, 50);
                        } else {
                            Animation animRight2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_left);
                            rlImage.startAnimation(animRight2);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Animation animRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_left);
                                    rlVideo.startAnimation(animRight);
                                    rlImage.setVisibility(View.GONE);
                                    rlVideo.setVisibility(View.VISIBLE);
                                }
                            }, 50);
                        }
                    }

                    if (!Const.hmStoneDetail.get("movie_url").equalsIgnoreCase("") && !Const.hmStoneDetail.get("movie_url").equalsIgnoreCase("null")) {
                        wbView.setWebViewClient(new myWebClient());
                        wbView.getSettings().setLoadWithOverviewMode(true);
                        wbView.getSettings().setJavaScriptEnabled(true);
                        wbView.getSettings().setUseWideViewPort(true);
                        wbView.getSettings().setBuiltInZoomControls(true);
                        wbView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                        wbView.loadUrl(Const.hmStoneDetail.get("movie_url"));
//                        wbView.loadUrl("https://4e0s0i2r4n0u1s0.com:8121/ViewImageVideoCerti?T=V&DT=M&StoneId=" + tvStoneID.getText().toString());
                    } else {
                        pbBarWeb.setVisibility(View.GONE);
                        tvVideoNot.setVisibility(View.VISIBLE);
                    }

                  /*  if (!Const.hmStoneDetail.get("movie_url").equalsIgnoreCase("") && !Const.hmStoneDetail.get("movie_url").equalsIgnoreCase("null")) {
                        Log.e("chintan",Const.hmStoneDetail.get("movie_url"));
                        String MovieUrl = "";
                        if(Const.hmStoneDetail.get("movie_url").contains("x=M") ||
                           Const.hmStoneDetail.get("movie_url").contains("T=V"))
                        {
                            MovieUrl = Const.hmStoneDetail.get("movie_url") + "&resize=1060";
                        }
                        else{
                            MovieUrl = Const.hmStoneDetail.get("movie_url") ;
                        }
                        wbView.setWebViewClient(new myWebClient());
                        wbView.getSettings().setLoadWithOverviewMode(true);
                        wbView.getSettings().setJavaScriptEnabled(true);
                        wbView.getSettings().setUseWideViewPort(true);
                        wbView.getSettings().setBuiltInZoomControls(true);
                        wbView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                        wbView.loadUrl(MovieUrl+"&DT=M");
                    } else {
                        pbBarWeb.setVisibility(View.GONE);
                        tvVideoNot.setVisibility(View.VISIBLE);
                    } */
                    break;

                case R.id.tvImage:
                    if (rlImage.getVisibility() != View.VISIBLE) {
                        tvImage.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        tvImage.setTextColor(Color.WHITE);
                        tvVideo.setBackgroundDrawable(null);
                        tvVideo.setTextColor(getResources().getColor(R.color.colorPrimary));
                        tvDetails.setBackgroundDrawable(null);
                        tvDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
                        tvCertificate.setBackgroundDrawable(null);
                        tvCertificate.setTextColor(getResources().getColor(R.color.colorPrimary));
                        if (svDetails.getVisibility() == View.VISIBLE) {
                            Animation animRight2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_left);
                            svDetails.startAnimation(animRight2);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Animation animRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_left);
                                    rlImage.startAnimation(animRight);
                                    svDetails.setVisibility(View.GONE);
                                    rlImage.setVisibility(View.VISIBLE);
                                }
                            }, 50);
                        } else if (rlCertificate.getVisibility() == View.VISIBLE) {
                            Animation animRight2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
                            rlCertificate.startAnimation(animRight2);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Animation animRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
                                    rlImage.startAnimation(animRight);
                                    rlCertificate.setVisibility(View.GONE);
                                    rlImage.setVisibility(View.VISIBLE);
                                }
                            }, 50);
                        } else {
                            Animation animLeft2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
                            rlVideo.startAnimation(animLeft2);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Animation animLeft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
                                    rlImage.startAnimation(animLeft);
                                    rlVideo.setVisibility(View.GONE);
                                    rlImage.setVisibility(View.VISIBLE);
                                }
                            }, 50);
                        }
                    }
                    break;
                case R.id.tvCertificate:
                    if (rlCertificate.getVisibility() != View.VISIBLE) {
                        tvCertificate.setBackgroundDrawable(Const.setBackgoundRightBorder(0, 50, getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorPrimary)));
                        tvCertificate.setTextColor(Color.WHITE);
                        tvImage.setBackgroundDrawable(null);
                        tvImage.setTextColor(getResources().getColor(R.color.colorPrimary));
                        tvVideo.setBackgroundDrawable(null);
                        tvVideo.setTextColor(getResources().getColor(R.color.colorPrimary));
                        tvDetails.setBackgroundDrawable(null);
                        tvDetails.setTextColor(getResources().getColor(R.color.colorPrimary));
                        if (svDetails.getVisibility() == View.VISIBLE) {
                            Animation animRight2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_left);
                            svDetails.startAnimation(animRight2);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Animation animRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_left);
                                    rlCertificate.startAnimation(animRight);
                                    svDetails.setVisibility(View.GONE);
                                    rlCertificate.setVisibility(View.VISIBLE);
                                }
                            }, 10);
                        } else if (rlImage.getVisibility() == View.VISIBLE) {
                            Animation animRight2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_left);
                            rlImage.startAnimation(animRight2);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Animation animRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_left);
                                    rlCertificate.startAnimation(animRight);
                                    rlImage.setVisibility(View.GONE);
                                    rlCertificate.setVisibility(View.VISIBLE);
                                }
                            }, 10);
                        } else {
                            Animation animLeft2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
                            rlVideo.startAnimation(animLeft2);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Animation animLeft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
                                    rlCertificate.startAnimation(animLeft);
                                    rlVideo.setVisibility(View.GONE);
                                    rlCertificate.setVisibility(View.VISIBLE);
                                }
                            }, 10);
                        }
                    }
                    if (!Const.hmStoneDetail.get("view_certi_url").equalsIgnoreCase("")  && !Const.hmStoneDetail.get("view_certi_url").equalsIgnoreCase("null")) {
                        String url = "";
                        if (Const.hmStoneDetail.get("isoverseas").equalsIgnoreCase("1")) {
                            if(!Const.hmStoneDetail.get("overseas_certi_download_link").equalsIgnoreCase("") && !Const.hmStoneDetail.get("overseas_certi_download_link").equalsIgnoreCase("null") && !Const.hmStoneDetail.get("overseas_certi_download_link").endsWith(".Pdf")) {
                                if (Const.hmStoneDetail.get("isoverseas").equalsIgnoreCase("0")) {
                                    url = "http://docs.google.com/viewer?embedded=true&url=" + Const.hmStoneDetail.get("overseas_certi_download_link");
                                } else {
                                    if(Const.hmStoneDetail.get("overseas_certi_download_link").endsWith(".pdf")){
                                        url = "http://docs.google.com/viewer?embedded=true&url=" + Const.hmStoneDetail.get("overseas_certi_download_link");
                                    }else {
                                        url = Const.hmStoneDetail.get("overseas_certi_download_link");
                                    }
                                }
                            } else {
                                url = "http://docs.google.com/viewer?embedded=true&url=" + Const.hmStoneDetail.get("overseas_certi_download_link");
                            }
                        }else{
                            url = "http://docs.google.com/viewer?embedded=true&url=" + Const.hmStoneDetail.get("view_certi_url");
                        }


                        if (Const.hmStoneDetail.get("certi_type").equalsIgnoreCase("IMAGE"))
                        {
                            wbViewCerti.getSettings().setJavaScriptEnabled(true);
                            wbViewCerti.getSettings().setLoadWithOverviewMode(true);
                            wbViewCerti.getSettings().setBuiltInZoomControls(true);
                            wbViewCerti.setInitialScale(1);
                            wbViewCerti.getSettings().setUseWideViewPort(true);
                            wbViewCerti.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                            wbViewCerti.loadUrl(url);
                            wbViewCerti.setWebViewClient(new myWebCerti());
                        }
                        else
                        {
                            showPdfFile(url);
                        }

                    } else {
                        pbBarCerti.setVisibility(View.GONE);
                        tvCertiNot.setVisibility(View.VISIBLE);
                    }
                    break;

                case R.id.tvDetails:
                    if (svDetails.getVisibility() != View.VISIBLE) {
                        tvDetails.setTextColor(Color.WHITE);
                        tvDetails.setBackgroundDrawable(Const.setBackgoundLeftBorder(0, 50, getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorPrimary)));
                        tvVideo.setBackgroundDrawable(null);
                        tvVideo.setTextColor(getResources().getColor(R.color.colorPrimary));
                        tvImage.setBackgroundDrawable(null);
                        tvImage.setTextColor(getResources().getColor(R.color.colorPrimary));
                        tvCertificate.setBackgroundDrawable(null);
                        tvCertificate.setTextColor(getResources().getColor(R.color.colorPrimary));
                        if (rlImage.getVisibility() == View.VISIBLE) {
                            Animation animLeft2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
                            rlImage.startAnimation(animLeft2);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Animation animLeft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
                                    svDetails.startAnimation(animLeft);
                                    rlImage.setVisibility(View.GONE);
                                    svDetails.setVisibility(View.VISIBLE);
                                }
                            }, 50);
                        } else if (rlCertificate.getVisibility() == View.VISIBLE) {
                            Animation animRight2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
                            rlCertificate.startAnimation(animRight2);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Animation animRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
                                    svDetails.startAnimation(animRight);
                                    rlCertificate.setVisibility(View.GONE);
                                    svDetails.setVisibility(View.VISIBLE);
                                }
                            }, 50);
                        } else {
                            Animation animLeft2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
                            rlVideo.startAnimation(animLeft2);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Animation animLeft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
                                    svDetails.startAnimation(animLeft);
                                    rlVideo.setVisibility(View.GONE);
                                    svDetails.setVisibility(View.VISIBLE);
                                }
                            }, 50);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };
    private String removePdfTopIcon = "javascript:(function() {" + "document.querySelector('[role=\"toolbar\"]').remove();})()";
    private void showPdfFile(final String imageString) {
        pbBarCerti.setVisibility(View.VISIBLE);
        wbViewCerti.invalidate();
        wbViewCerti.getSettings().setLoadWithOverviewMode(true);
        wbViewCerti.getSettings().setUseWideViewPort(true);
        wbViewCerti.getSettings().setJavaScriptEnabled(true);
        wbViewCerti.getSettings().setBuiltInZoomControls(true);
        wbViewCerti.getSettings().setSupportZoom(true);
        wbViewCerti.getSettings().setLoadsImagesAutomatically(true);
        wbViewCerti.loadUrl(imageString);
        wbViewCerti.setWebViewClient(new WebViewClient() {
            boolean checkOnPageStartedCalled = false;

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                checkOnPageStartedCalled = true;
                pbBarCerti.setVisibility(View.GONE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (checkOnPageStartedCalled) {
                    wbViewCerti.loadUrl(removePdfTopIcon);
                } else {
                    showPdfFile(imageString);
                }
            }
        });
    }
}
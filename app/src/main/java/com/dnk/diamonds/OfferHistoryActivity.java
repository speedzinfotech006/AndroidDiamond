package com.dnk.shairugems;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.webkit.MimeTypeMap;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dnk.shairugems.Utils.Const;
import com.dnk.shairugems.Utils.Pref;
import com.dnk.shairugems.Volly.VolleyCallback;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class OfferHistoryActivity extends BaseDrawerActivity {

    ImageView imgMenu, imgSearch, imgDown;
    LinearLayout llSearchTop,llViewCount,llSearch, llClear;
    RelativeLayout rlMain;
    RecyclerView rvList;
    LinearLayoutManager lm;
    CheckBox cbSelectAll;
    FloatingActionMenu Menu;
    TextView tvTitle, tvTotalCount, tvTotalPcs, tvTotalCts, tvTotalAvgDiscPer, tvTotalAmt;
    EditText etSearch, etToDate, etFromDate, etCompName;
    HashMap<Integer, String> hmSelected = new HashMap<>();
    ArrayList<HashMap<String, String>> maps = new ArrayList<>();
    ResultAdp adp;
    com.github.clans.fab.FloatingActionButton llShare,llDownload;
    FloatingActionButton llPlus;
    RelativeLayout toolBar;
    boolean isChange = true;
    Double rap = 0.0, totalRap = 0.0;
    String totalCts = "0", totalAmount = "0", totalPricePerCts = "0", totalAvgDicPer = "0";
    int pastVisiblesItems, visibleItemCount, totalItemCount, pageCount = 1, totalSize = 0;

    private Dialog d;
    ImageView imgPlus, img_Share, img_Download;
    TextView tv_Share, tv_Download;
    LinearLayout Menu_Share,Menu_Download,Menu_Wishlist,Menu_Compare,Menu_Copy,Menu_Stock,Menu_Clear,Menu_Back;
    View div_Share,div_Download,div_Wishlist,div_Compare,div_Copy,div_Stock;

    long downID = 0;
    String randomNumber = "";
    DownloadManager downloadManager;
    LinearLayout llSearchTwo;
    Context context = OfferHistoryActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_offer_history, frameLayout);

        initView();

        etFromDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etFromDate.performClick();
                }
            }
        });

        etToDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etToDate.performClick();
                }
            }
        });

        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = lm.getChildCount();
                    totalItemCount = lm.getItemCount();
                    pastVisiblesItems = lm.findFirstVisibleItemPosition();
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        if (totalSize != maps.size()) {
                            if (!Const.isProgressShowing()) {
                                pageCount++;
                                getSearchResult();
                            }
                        }
                    }
                }
            }
        });

        cbSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearTotalAll();
                if (cbSelectAll.isChecked()) {
                    if (adp != null) {
                        for (int i = 0; i < maps.size(); i++) {
                            hmSelected.put(i, maps.get(i).get("srefno"));
                        }
                        adp.notifyDataSetChanged();
                    }
                } else {
                    clearTotal();
                    if (adp != null) {
                        adp.notifyDataSetChanged();
                    }
                }
            }
        });

//        Menu.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
//            @Override
//            public void onMenuToggle(boolean opened) {
//                if (opened) {
//                    rlMain.setBackgroundColor(Color.LTGRAY);
//                } else {
//                    rlMain.setBackgroundColor(Color.TRANSPARENT);
//                }
//            }
//        });

        Pref.removeValue(context, Const.stone_id);
        Pref.removeValue(context, Const.comp_name);
        getSearchResult();
    }

    private void initView() {
        Menu = findViewById(R.id.Menu);
        rlMain = findViewById(R.id.rlMain);
        imgMenu = findViewById(R.id.imgMenu);
        etSearch = findViewById(R.id.etSearch);
        etCompName = findViewById(R.id.etCompName);
        imgSearch = findViewById(R.id.imgSearch);
        llSearch = findViewById(R.id.llSearch);
        llClear = findViewById(R.id.llClear);
        rvList = findViewById(R.id.rvList);
        etFromDate = findViewById(R.id.etFromDate);
        etToDate = findViewById(R.id.etToDate);
        toolBar = findViewById(R.id.toolBar);
        llShare = findViewById(R.id.llShare);
        llDownload = findViewById(R.id.llDownload);
        imgDown = findViewById(R.id.imgDown);
        llPlus = findViewById(R.id.llPlus);
        cbSelectAll = findViewById(R.id.cbSelectAll);

        llSearchTop = findViewById(R.id.llSearchTop);
        llSearchTwo = findViewById(R.id.llSearchTwo);
        llViewCount = findViewById(R.id.llViewCount);
        tvTotalAmt = findViewById(R.id.tvTotalAmt);
        tvTotalCts = findViewById(R.id.tvTotalCts);
        tvTotalAvgDiscPer = findViewById(R.id.tvTotalAvgDiscPer);
        tvTotalCount = findViewById(R.id.tvTotalCount);
        tvTotalPcs = findViewById(R.id.tvTotalPcs);


        lm = new LinearLayoutManager(context);
        rvList.setLayoutManager(lm);

        imgMenu.setOnClickListener(clickListener);
        imgDown.setOnClickListener(clickListener);
        llClear.setOnClickListener(clickListener);
        llSearch.setOnClickListener(clickListener);
        etFromDate.setOnClickListener(clickListener);
        etToDate.setOnClickListener(clickListener);

        llPlus.setOnClickListener(clickListener);
        llShare.setOnClickListener(clickListener);
        llDownload.setOnClickListener(clickListener);

        llShare.setBackgroundDrawable(Const.setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));
        llDownload.setBackgroundDrawable(Const.setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));

        d = new Dialog(this, R.style.Dialog);
        WindowManager.LayoutParams wmpl = d.getWindow().getAttributes();
        LayoutInflater inflate = LayoutInflater.from(context);
        d.setCancelable(true);
        View view = inflate.inflate(R.layout.left_menu_new, null);
        wmpl.gravity = Gravity.CENTER | Gravity.CENTER_HORIZONTAL;
        d.setContentView(view);

        Menu_Share =  view.findViewById(R.id.Menu_Share);
        Menu_Download =  view.findViewById(R.id.Menu_Download);
        Menu_Wishlist =  view.findViewById(R.id.Menu_Wishlist);
        Menu_Compare =  view.findViewById(R.id.Menu_Compare);
        Menu_Copy =  view.findViewById(R.id.Menu_Copy);
        Menu_Stock =  view.findViewById(R.id.Menu_Stock);
        Menu_Clear =  view.findViewById(R.id.Menu_Clear);
        Menu_Back =  view.findViewById(R.id.Menu_Back);

        div_Share =  view.findViewById(R.id.div_Share);
        div_Download =  view.findViewById(R.id.div_Download);
        div_Wishlist =  view.findViewById(R.id.div_Wishlist);
        div_Compare =  view.findViewById(R.id.div_Compare);
        div_Copy =  view.findViewById(R.id.div_Copy);
        div_Stock =  view.findViewById(R.id.div_Stock);

        img_Share =  view.findViewById(R.id.img_Share);
        img_Download =  view.findViewById(R.id.img_Download);
        tv_Share =  view.findViewById(R.id.tv_Share);
        tv_Download =  view.findViewById(R.id.tv_Download);

        img_Share.setImageDrawable(getResources().getDrawable(R.drawable.ic_share_excel));
        tv_Share.setText("Share Excel");
        img_Download.setImageDrawable(getResources().getDrawable(R.drawable.ic_excel_blue));
        tv_Download.setText("Download Excel");

        Menu_Share.setVisibility(View.VISIBLE);
        div_Share.setVisibility(View.VISIBLE);
        Menu_Download.setVisibility(View.GONE);
        div_Download.setVisibility(View.GONE);
        Menu_Wishlist.setVisibility(View.GONE);
        div_Wishlist.setVisibility(View.GONE);
        Menu_Compare.setVisibility(View.GONE);
        div_Compare.setVisibility(View.GONE);
        Menu_Copy.setVisibility(View.GONE);
        div_Copy.setVisibility(View.GONE);
        Menu_Stock.setVisibility(View.VISIBLE);
        div_Stock.setVisibility(View.VISIBLE);
        Menu_Clear.setVisibility(View.VISIBLE);

        Menu_Share.setOnClickListener(clickListener);
        Menu_Download.setOnClickListener(clickListener);
        Menu_Wishlist.setOnClickListener(clickListener);
        Menu_Compare.setOnClickListener(clickListener);
        Menu_Copy.setOnClickListener(clickListener);
        Menu_Stock.setOnClickListener(clickListener);
        Menu_Clear.setOnClickListener(clickListener);
        Menu_Back.setOnClickListener(clickListener);

        SimpleDateFormat dateFormat= new SimpleDateFormat("dd-MMM-yyyy");
        Calendar currentCal = Calendar.getInstance();
        String currentdate=dateFormat.format(currentCal.getTime());
        currentCal.add(Calendar.MONTH, -1);
        String FromDate=dateFormat.format(currentCal.getTime());
        etFromDate.setText(FromDate);
        etToDate.setText(new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date()));

        Pref.setStringValue(context, Const.from_date, etFromDate.getText().toString());
        Pref.setStringValue(context, Const.to_date, etToDate.getText().toString());

        if (Pref.getStringValue(context, Const.loginIsAdmin, "").equalsIgnoreCase("1")) {
            llSearchTwo.setVisibility(View.VISIBLE);
        } else {
            llSearchTwo.setVisibility(View.GONE);
        }
    }

    private void getSearchResult() {
        Const.showProgress(context);
        final Map<String, String> map = new HashMap<>();
        map.put("FromDate", Pref.getStringValue(context, Const.from_date, ""));
        map.put("ToDate", Pref.getStringValue(context, Const.to_date, ""));
        map.put("PageNo", pageCount + "");
        map.put("RefNo", Pref.getStringValue(context, Const.stone_id, ""));
        map.put("CompanyName", Pref.getStringValue(context, Const.comp_name, ""));
        map.put("PageSize", "50");
        Const.callPostApi(context, "Offer/OfferHistoryDetail", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Const.dismissProgress();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.optString("Data") != null && !object.optString("Data").equalsIgnoreCase("null")) {
                        if (new JSONObject(result).optJSONArray("Data").optJSONObject(0).optJSONObject("DataList") != null) {
                            startActivity(new Intent(context, NewLoginActivity.class));
                        } else {
                            JSONArray array = new JSONObject(result).optJSONArray("Data").optJSONObject(0).getJSONArray("DataList");
                            JSONObject obj = new JSONObject(result).optJSONArray("Data").optJSONObject(0).optJSONObject("DataSummary");

                            if (array.length() != 0) {
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object1 = array.optJSONObject(i);
                                    Iterator<String> keys = object1.keys();
                                    HashMap<String, String> hm = new HashMap<>();
                                    while (keys.hasNext()) {
                                        String key = keys.next();
                                        hm.put(key.toLowerCase(), object1.optString(key).trim());
                                    }
                                    maps.add(hm);
                                }
                                totalSize = Integer.parseInt(obj.optString("TOT_PCS"));

                                totalCts = String.format("%.2f", obj.optDouble("TOT_CTS"));
                                totalAmount = "$ " + String.format("%,.0f", obj.optDouble("TOT_NET_AMOUNT"));
                                totalAvgDicPer = String.format("%.2f", obj.optDouble("AVG_SALES_DISC_PER")) + " %";
                                totalRap = obj.optDouble("TOT_RAP_AMOUNT");
                                tvTotalCount.setText("Showing 1 to " + maps.size() + " of " + obj.optString("TOT_PCS") + " entries");
                            }
                            tvTotalCts.setText(totalCts);
                            tvTotalAmt.setText(totalAmount);
                            tvTotalAvgDiscPer.setText(totalAvgDicPer);
                            tvTotalPcs.setText(obj.optString("TOT_PCS"));
                            llViewCount.setVisibility(View.VISIBLE);
                            if (pageCount == 1) {
                                adp = new ResultAdp(context, maps, isChange);
                                rvList.setAdapter(adp);
                            }
                            adp.notifyDataSetChanged();
                        }
                    } else {
                        adp = null;
                        rvList.setAdapter(null);
                        llViewCount.setVisibility(View.GONE);
                        Const.showErrorDialog(context, "No Result Available");
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
                        getSearchResult();
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    private void ShareExcel() {
        Const.showProgress(context);
        List<String> RefNo = new ArrayList<>(hmSelected.values());
        final Map<String, String> map = new HashMap<>();
        map.put("FromDate", Pref.getStringValue(context, Const.from_date, ""));
        map.put("ToDate", Pref.getStringValue(context, Const.to_date, ""));
        map.put("PageNo", "1");
        if (hmSelected.size()>=1){
            map.put("RefNo", RefNo.toString().replace("[", "").replace("]", "").replace(", ", ","));
        }else{
            map.put("RefNo", Pref.getStringValue(context, Const.stone_id, ""));
        }
        map.put("CompanyName", Pref.getStringValue(context, Const.comp_name, ""));
        if (Pref.getStringValue(context, Const.loginIsAdmin, "").equalsIgnoreCase("1")) {
            map.put("isAdmin", "true");
        }else{
            map.put("isAdmin", "false");
        }
        if (Pref.getStringValue(context, Const.loginIsEmp, "").equalsIgnoreCase("1")) {
            map.put("isEmp", "true");
        }else{
            map.put("isEmp", "false");
        }
        map.put("PageSize", "50");
        map.put("FormName", "Offer History");
        map.put("ActivityType", "Excel Export");
        Const.callPostApi(context, "Offer/DownloadOfferHistoryDetail", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                //  Const.dismissProgress();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.optString("Status").equalsIgnoreCase("1")) {
                        Date date = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy") ;

                        Random objGenerator = new Random();
                        for (int iCount = 0; iCount< 1; iCount++){
                            randomNumber = String.valueOf(objGenerator.nextInt(1000));
                            System.out.println("Random No : " + randomNumber);
                        }
                        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "OfferHistory " + dateFormat.format(date) + "-" +  randomNumber + ".xlsx");
                        registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(object.optString("Message")));
                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                        request.setTitle("OfferHistory " + dateFormat.format(date) + "-" + randomNumber);
                        request.allowScanningByMediaScanner();
                        // request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "OfferHistory " + dateFormat.format(date) + "-" + randomNumber + ".xlsx");
                        request.setMimeType(MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                                MimeTypeMap.getFileExtensionFromUrl(object.optString("Message"))));
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

    private void DownloadExcel() {
        Const.showProgress(context);
        List<String> RefNo = new ArrayList<>(hmSelected.values());
        final Map<String, String> map = new HashMap<>();
        map.put("FromDate", Pref.getStringValue(context, Const.from_date, ""));
        map.put("ToDate", Pref.getStringValue(context, Const.to_date, ""));
        map.put("PageNo", "1");
        if (hmSelected.size()>=1){
            map.put("RefNo", RefNo.toString().replace("[", "").replace("]", "").replace(", ", ","));
        }else{
            map.put("RefNo", Pref.getStringValue(context, Const.stone_id, ""));
        }
        map.put("CompanyName", Pref.getStringValue(context, Const.comp_name, ""));
        if (Pref.getStringValue(context, Const.loginIsAdmin, "").equalsIgnoreCase("1")) {
            map.put("isAdmin", "true");
        }else{
            map.put("isAdmin", "false");
        }
        if (Pref.getStringValue(context, Const.loginIsEmp, "").equalsIgnoreCase("1")) {
            map.put("isEmp", "true");
        }else{
            map.put("isEmp", "false");
        }
        map.put("PageSize", "50");
        map.put("FormName", "Offer History");
        map.put("ActivityType", "Excel Export");
        Const.callPostApi(context, "Offer/DownloadOfferHistoryDetail", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Const.dismissProgress();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.optString("Status").equalsIgnoreCase("1")) {
                        try {
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(object.optString("Message")));
                            request.setDescription("download");
                            Date date = new Date();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy") ;
                            request.setTitle(dateFormat.format(date));

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                request.allowScanningByMediaScanner();
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            }
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "OfferHistory " + dateFormat.format(date) + ".xlsx");
                            DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                            manager.enqueue(request);
                            Const.showErrorDialog(context,"Download Excel Successfully!");
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

    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Const.dismissProgress();
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy") ;
            if (downID == id) {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "OfferHistory " + dateFormat.format(date) + "-" + randomNumber + ".xlsx");
                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.setType("application/vnd.ms-excel");
                Uri uri = FileProvider.getUriForFile(OfferHistoryActivity.this, BuildConfig.APPLICATION_ID + ".provider",file);
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

    private void openFromDatePickerDialog() {
        int dd, mm, yy;
        if (Const.isEmpty(etFromDate)) {
            dd = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            mm = Calendar.getInstance().get(Calendar.MONTH);
            yy = Calendar.getInstance().get(Calendar.YEAR);
        } else {
            String[] ddmmyy = etFromDate.getText().toString().trim().split("-");
            dd = Integer.parseInt(ddmmyy[0]);
            mm = Const.nameTonumMonth(ddmmyy[1]);
            yy = Integer.parseInt(ddmmyy[2]);
        }
        DatePickerDialog date = new DatePickerDialog(context, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Integer dd = datePicker.getDayOfMonth();
                String d = dd.toString().length() == 1 ? "0" + dd : dd + "";
                etFromDate.setText(d + "-" + Const.getMonthAbbr(datePicker.getMonth()) + "-" + datePicker.getYear());
                if (!Const.compareDate(etFromDate.getText().toString(), etToDate.getText().toString())) {
                    if (!Const.isEmpty(etToDate)) {
                        etToDate.setText(d + "-" + Const.getMonthAbbr(datePicker.getMonth()) + "-" + datePicker.getYear());
                    }
                }
            }
        }, yy, mm, dd);
        //  date.getDatePicker().setMaxDate(new Date().getTime() - 10000);
        date.show();
    }

    private void openToDatePickerDialog() {
        int dd, mm, yy;
        if (Const.isEmpty(etToDate)) {
            dd = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            mm = Calendar.getInstance().get(Calendar.MONTH);
            yy = Calendar.getInstance().get(Calendar.YEAR);
        } else {
            String[] ddmmyy = etToDate.getText().toString().trim().split("-");
            dd = Integer.parseInt(ddmmyy[0]);
            mm = Const.nameTonumMonth(ddmmyy[1]);
            yy = Integer.parseInt(ddmmyy[2]);
        }
        DatePickerDialog date = new DatePickerDialog(context, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Integer dd = datePicker.getDayOfMonth();
                String d = dd.toString().length() == 1 ? "0" + dd : dd + "";
                etToDate.setText(d + "-" + Const.getMonthAbbr(datePicker.getMonth()) + "-" + datePicker.getYear());
            }
        }, yy, mm, dd);
        date.getDatePicker().setMinDate(Const.timeTomss(etFromDate.getText().toString()));
        //  date.getDatePicker().setMaxDate(new Date().getTime() - 10000);
        date.show();
    }

    private void clearTotal() {
        hmSelected = new HashMap<>();
        rap = totalRap;
        tvTotalPcs.setText(String.valueOf(totalSize));
        tvTotalCts.setText(totalCts);
        tvTotalAmt.setText(totalAmount);
        tvTotalAvgDiscPer.setText(totalAvgDicPer);
    }

    private void clearTotalAdp() {
        rap = 0.0;
        tvTotalPcs.setText("0");
        tvTotalCts.setText("0");
        tvTotalAmt.setText("$ 0");
        tvTotalAvgDiscPer.setText("0 %");
        // tvTotalPricePerCts.setText("$ 0");
    }

    private void clearTotalAll() {
        hmSelected = new HashMap<>();
        rap = totalRap;
        double totalcts = 0;
        double totalamt = 0;
        double totalavgdiscper = 0;
        double totalpricepercts = 0;
        double totalrap = 0;
        for (int i = 0; i < maps.size(); i++) {

            if (!maps.get(i).get("cts").equalsIgnoreCase("null")) {
                totalcts += Double.parseDouble(maps.get(i).get("cts"));
            }
            if (!maps.get(i).get("netamount").equalsIgnoreCase("null")) {
                totalamt += Double.parseDouble(maps.get(i).get("netamount"));
            }
            if (!maps.get(i).get("rapamount").equalsIgnoreCase("null")) {
                totalrap += Double.parseDouble(maps.get(i).get("rapamount"));
            }
        }

        tvTotalPcs.setText(maps.size() + "");
        tvTotalCts.setText(String.format("%.2f", totalcts));
        tvTotalAmt.setText("$ " + String.format("%,.0f", totalamt));
        Double avgDisc = (100 - ((Double.parseDouble(String.valueOf(totalamt)) * 100) / totalrap)) * -1;
        tvTotalAvgDiscPer.setText(avgDisc.isNaN() ? "0" : String.format("%.2f", avgDisc) + " %");
    }

    private class ResultAdp extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        ArrayList<HashMap<String, String>> arraylist;
        Context context;
        int isShow = -1;

        private ResultAdp(Context context, ArrayList<HashMap<String, String>> arraylist, boolean isLayout) {
            this.arraylist = arraylist;
            this.context = context;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_offer_history_design, parent, false);
            return new ResultAdp.GridViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            final HashMap<String, String> map = arraylist.get(position);

            if (holder instanceof ResultAdp.GridViewHolder) {
                final ResultAdp.GridViewHolder h = (ResultAdp.GridViewHolder) holder;

                h.orderid.setText("Offer : " +"#" + map.get("iofferid"));
                h.stoneid.setText(map.get("srefno"));
                if (map.get("scertino") != null && map.get("scertino") != "" && !map.get("scertino").equalsIgnoreCase("null")) {
                    h.certino.setText(map.get("scertino"));
                }else{
                    h.certino.setText("");
                }
                if (map.get("bgm") != null && map.get("bgm") != "" && !map.get("bgm").equalsIgnoreCase("null")) {
                    h.bgm.setText(map.get("bgm"));
                }else{
                    h.bgm.setText("");
                }
                h.date.setText(map.get("offerdate"));
                h.rap.setText("Rap : " + String.format("%,.2f", Double.parseDouble(map.get("rapamount"))));
                h.disc.setText("Disc : " + String.format("%,.2f", Double.parseDouble(map.get("sofferper"))) + " %");
//                String offerAmt = "";
//                if (!h.disc.getText().toString().equalsIgnoreCase("")) {
//                    String disc = String.valueOf(Float.parseFloat(h.disc.getText().toString()));
//                    String min, max;
//                    if (h.disc.getText().toString().length() > 0) {
//                        min = disc - OfferPercentage;
//                        max = disc + OfferPercentage;
//                    }
//                    else {
//                        min = disc + OfferPercentage;
//                        max = disc - OfferPercentage;
//                    }
//                    String val = (String.format("%,.2f", Double.parseDouble(map.get("sofferper"))));
//
//                    if (!(String.format("%,.2f", Double.parseDouble(map.get("sofferper")))).equalsIgnoreCase("")){
//                        if ((disc > 0 && val >= min && val <= max) || (disc < 0 && val <= min && val >= max)) {
//                            var cts = parseFloat(params.data.Cts);
//                            var rapaport = parseFloat(params.data.cur_rap_rate);
//                            var newRate;
//                            if (val > 0) {
//                                newRate = rapaport - ((rapaport * ((-1) * val)) / 100);
//                                offerAmt = newRate * cts;
//                            }
//                            else {
//                                newRate = rapaport + ((rapaport * val) / 100);
//                                offerAmt = newRate * cts;
//                            }
//                            h.amt.setText("$ " + String.format("%,.2f", Double.parseDouble(offerAmt)));
//                        }
//                    }
//                }
                h.amt.setText("$ " + String.format("%,.2f", Double.parseDouble(map.get("netamount"))));

                if (map.get("slocation").equalsIgnoreCase("Hong Kong")) {
                    h.location.setImageResource(R.drawable.ic_hk);
                } else if (map.get("slocation").equalsIgnoreCase("India")) {
                    h.location.setImageResource(R.drawable.ic_india);
                } else if (map.get("slocation").equalsIgnoreCase("Upcoming")) {
                    h.location.setImageResource(R.drawable.ic_plane);
                } else if (map.get("slocation").equalsIgnoreCase("SHOW")) {
                    h.location.setImageResource(R.drawable.ic_fair);
                } else if (map.get("location").equalsIgnoreCase("Dubai")) {
                    h.location.setImageResource(R.drawable.ic_dubai);
                }

                if (map.get("scolor").equalsIgnoreCase("FAINT PINK")) {
                    h.colour.setText("FP");
                }else{
                    h.colour.setText(map.get("scolor"));
                }

                h.clarity.setText(map.get("sclarity"));
                h.cts.setText(String.format("%.2f", Double.parseDouble(map.get("cts"))));
                h.cps.setText(map.get("scut") + "-" + map.get("spolish") + "-" + map.get("ssymm"));
                h.fls.setText(map.get("sfls"));

                if (map.get("scut").equalsIgnoreCase("3EX")) {
                    h.cps.setTypeface(Typeface.DEFAULT_BOLD);
                }
//                if (map.get("sstonestatus") != null && map.get("sstonestatus") != "" && !map.get("sstonestatus").equalsIgnoreCase("")) {
//                    h.status.setText(map.get("sstonestatus"));
//                } else {
//                    h.status.setText("");
//                }
                if (map.get("stonestatus").matches("A")) {
                    Spannable Available = new SpannableString("Available");
                    Available.setSpan(new BackgroundColorSpan(getResources().getColor(R.color.green)), 0, Available.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    h.status.setText(Available);
                    h.status.setTextSize(10);
                    h.status.setTextColor(Color.WHITE);
                } else if (map.get("stonestatus").matches("O")) {
                    Spannable Offer = new SpannableString("Offer");
                    Offer.setSpan(new BackgroundColorSpan(getResources().getColor(R.color.ao)), 0, Offer.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    h.status.setText(Offer);
                    h.status.setTextSize(10);
                    h.status.setTextColor(Color.BLACK);
                } else if (map.get("stonestatus").matches("N")) {
                    Spannable New = new SpannableString("New");
                    New.setSpan(new BackgroundColorSpan(getResources().getColor(R.color.pink)), 0, New.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    h.status.setText(New);
                    h.status.setTextSize(10);
                    h.status.setTextColor(Color.BLACK);
                } else if (map.get("stonestatus").matches("B")) {
                    Spannable Busy = new SpannableString("Busy");
                    Busy.setSpan(new BackgroundColorSpan(getResources().getColor(R.color.orange)), 0, Busy.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    h.status.setText(Busy);
                    h.status.setTextSize(10);
                    h.status.setTextColor(Color.WHITE);
                }

                if (map.get("slocation") != null && map.get("slocation") != "" && map.get("slocation").equalsIgnoreCase("Upcoming")) {
                    h.llMain.setBackgroundColor(Color.rgb(255, 255, 153));
                }

                if (hmSelected.containsKey(position)) {
                    if (map.get("location") != null && map.get("location") != "" && map.get("location").equalsIgnoreCase("Upcoming")) {
                        h.llMain.setBackgroundDrawable(getResources().getDrawable(R.drawable.upcoming));
                        h.tvtick.setVisibility(View.VISIBLE);
                    }else{
                        h.llMain.setBackgroundDrawable(getResources().getDrawable(R.drawable.clickable));
                        h.tvtick.setVisibility(View.VISIBLE);
                    }
                } else {
                    h.tvtick.setVisibility(View.GONE);
                    if (map.get("location") != null && map.get("location") != "" && map.get("location").equalsIgnoreCase("Upcoming")) {
                        h.llMain.setBackgroundColor(Color.rgb(255, 255, 153));
                    }else {
                        h.llMain.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_background_box));
                    }
                }


                h.llMain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (hmSelected.containsKey(position)) {
                            hmSelected.remove(position);
                            cbSelectAll.setChecked(false);

                            tvTotalPcs.setText(hmSelected.size() + "");
                            Double cts = Double.parseDouble(tvTotalCts.getText().toString()) - Double.parseDouble(map.get("cts"));
                            tvTotalCts.setText(String.format("%.2f", cts));

                            String Amount = tvTotalAmt.getText().toString().replace("$","").replace(",","");
                            Double amt = Double.parseDouble(Amount) - Double.parseDouble(map.get("netamount"));
                            tvTotalAmt.setText("$ " + String.format("%,.0f", amt));

                            rap = rap - Double.parseDouble(map.get("rapamount"));
                            Double avgDisc = (100 - ((Double.parseDouble(String.valueOf(amt)) * 100) / rap)) * -1;
                            tvTotalAvgDiscPer.setText(avgDisc.isNaN() ? "0" : String.format("%.2f", avgDisc) + " %");
                            if (hmSelected.size() == 0) {
                                clearTotal();
                            }
                               if (map.get("slocation") != null && map.get("slocation") != "" && map.get("slocation").equalsIgnoreCase("Upcoming")){
                                    h.llMain.setBackgroundColor(Color.rgb(255, 255, 153));
                                    h.tvtick.setVisibility(View.GONE);
                                } else {
                                    h.llMain.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_background_box));
                                    h.tvtick.setVisibility(View.GONE);
                                }

                        } else {
                            hmSelected.put(position, map.get("srefno"));
                            if (hmSelected.size() == arraylist.size()) {
                                cbSelectAll.setChecked(true);
                                h.llMain.setBackgroundDrawable(getResources().getDrawable(R.drawable.clickable));
                                h.tvtick.setVisibility(View.VISIBLE);
                            }
                            if (hmSelected.size() == 1) {
                                clearTotalAdp();
                            }
                            tvTotalPcs.setText(hmSelected.size() + "");
                            Double cts = Double.parseDouble(tvTotalCts.getText().toString()) + Double.parseDouble(map.get("cts"));
                            tvTotalCts.setText(String.format("%.2f", cts));

                            String Amount = tvTotalAmt.getText().toString().replace("$","").replace(",","");
                            Double amt = Double.parseDouble(Amount) + Double.parseDouble(map.get("netamount"));
                            tvTotalAmt.setText("$ " + String.format("%,.0f", amt));

                            rap = rap + Double.parseDouble(map.get("rapamount"));
                            Double avgDisc = (100 - ((Double.parseDouble(String.valueOf(amt)) * 100) / rap)) * -1;
                            tvTotalAvgDiscPer.setText(avgDisc.isNaN() ? "0" : String.format("%.2f", avgDisc) + " %");

                            if (map.get("location") != null && map.get("location") != "" && map.get("location").equalsIgnoreCase("Upcoming")) {
                                h.llMain.setBackgroundDrawable(getResources().getDrawable(R.drawable.upcoming));
                                h.tvtick.setVisibility(View.VISIBLE);
                            }else{
                                h.llMain.setBackgroundDrawable(getResources().getDrawable(R.drawable.clickable));
                                h.tvtick.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return arraylist.size();
        }

        private class GridViewHolder extends RecyclerView.ViewHolder {

            TextView orderid, stoneid, certino, bgm, date, rap, disc, amt, colour, clarity, cts, cps, fls, status, custname, username;
            LinearLayout llMain;
            ImageView location;
            TextView tvtick;

            private GridViewHolder(@NonNull View v) {
                super(v);
                orderid = v.findViewById(R.id.odetail_orderid);
                stoneid =  v.findViewById(R.id.odetail_stoneid);
                certino =  v.findViewById(R.id.odetail_certino);
                bgm =  v.findViewById(R.id.odetail_bgm);
                date =  v.findViewById(R.id.odetail_date);
                rap =  v.findViewById(R.id.odetail_rap);
                disc =  v.findViewById(R.id.odetail_disc);
                amt =  v.findViewById(R.id.odetail_price);
                location =  v.findViewById(R.id.odetail_location);
                colour =  v.findViewById(R.id.odetail_color);
                clarity =  v.findViewById(R.id.odetail_clarity);
                cts =  v.findViewById(R.id.odetail_cts);
                cps =  v.findViewById(R.id.odetail_cut);
                fls =  v.findViewById(R.id.odetail_fls);
                status =  v.findViewById(R.id.odetail_status);
                llMain =  v.findViewById(R.id.llMain);
                tvtick =  v.findViewById(R.id.tvtick);
            }
        }
    }

    private void expand(final View v) {
        int matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View) v.getParent()).getWidth(), View.MeasureSpec.EXACTLY);
        int wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(matchParentMeasureSpec, wrapContentMeasureSpec);
        final int targetHeight = v.getMeasuredHeight();
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1 ? ViewGroup.LayoutParams.WRAP_CONTENT : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density) + 200);
        v.startAnimation(a);
    }

    private void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density) + 200);
        v.startAnimation(a);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @SuppressLint("WrongConstant")
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.imgMenu:
                    drawerLayout.openDrawer(Gravity.START);
                    break;
                case R.id.etFromDate:
                    openFromDatePickerDialog();
                    break;
                case R.id.etToDate:
                    openToDatePickerDialog();
                    break;
                case R.id.llSearch:
                    Menu.close(true);
                    Pref.setStringValue(context, Const.from_date, etFromDate.getText().toString());
                    Pref.setStringValue(context, Const.to_date, etToDate.getText().toString());
                    Pref.setStringValue(context, Const.stone_id, etSearch.getText().toString().replaceAll(" ", ",").replaceAll("\n", ",").replaceAll("\t", ",").replaceAll("  ", ","));
                    Pref.setStringValue(context, Const.comp_name, etCompName.getText().toString().replaceAll(" ", ",").replaceAll("\n", ",").replaceAll("\t", ",").replaceAll("  ", ","));
                    maps = new ArrayList<>();
                    clearTotal();
                    pageCount = 1;
                    totalSize = 0;
                    Const.hideSoftKeyboard(OfferHistoryActivity.this);
                    // Const.resetParameters(context);
                    getSearchResult();
                    break;
                case R.id.llPlus:
                    d.show();
                    break;
                case R.id.Menu_Back:
                    d.dismiss();
                    break;
                case R.id.Menu_Share:
                    d.dismiss();
                    ShareExcel();
                    break;
                case R.id.Menu_Stock:
                    d.dismiss();
                    DownloadExcel();
                    break;
                case R.id.llClear:
                    etSearch.setText("");
                    etCompName.setText("");
                    Menu.close(true);
                    if (Pref.getStringValue(context, Const.loginIsAdmin, "").equalsIgnoreCase("1")) {
                        SimpleDateFormat dateFormat= new SimpleDateFormat("dd-MMM-yyyy");
                        Calendar currentCal = Calendar.getInstance();
                        String currentdate=dateFormat.format(currentCal.getTime());
                        currentCal.add(Calendar.DATE, -7);
                        String FromDate=dateFormat.format(currentCal.getTime());
                        etFromDate.setText(FromDate);
                        etToDate.setText(new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date()));
                    }else{
                        SimpleDateFormat dateFormat= new SimpleDateFormat("dd-MMM-yyyy");
                        Calendar currentCal = Calendar.getInstance();
                        String currentdate=dateFormat.format(currentCal.getTime());
                        currentCal.add(Calendar.MONTH, -1);
                        String FromDate=dateFormat.format(currentCal.getTime());
                        etFromDate.setText(FromDate);
                        etToDate.setText(new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date()));
                    }
                    Pref.removeValue(context, Const.stone_id);
                    Pref.removeValue(context, Const.comp_name);
                    Pref.removeValue(context, Const.Status);
                    maps = new ArrayList<>();
                    clearTotal();
                    pageCount = 1;
                    totalSize = 0;
                    Const.hideSoftKeyboard(OfferHistoryActivity.this);
                    getSearchResult();
                    break;
                case R.id.Menu_Clear:
                    d.dismiss();
                    clearTotal();
                    if (adp != null) {
                        adp.notifyDataSetChanged();
                    }
                    break;
                case R.id.imgDown:
                    if (llSearchTop.getVisibility() == View.VISIBLE) {
                        imgDown.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_new));
                        collapse(llSearchTop);
                    } else {
                        imgDown.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_up));
                        expand(llSearchTop);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.searchview, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItem itemInfo = menu.findItem(R.id.action_info);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        TextView searchText = (TextView) searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchText.setTypeface(ResourcesCompat.getFont(context, R.font.proxima_nova));
        searchView.setQueryHint("Stone ID");

        itemInfo.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                BottomSheetDialog dialog = new BottomSheetDialog(context);
                dialog.setContentView(R.layout.layout_status_info);

//                TextView tvC = dialog.findViewById(R.id.tvC);
//                TextView tvCA = dialog.findViewById(R.id.tvCA);
//                TextView tvNA = dialog.findViewById(R.id.tvNA);
//
//                tvNA.setBackgroundDrawable(Const.setBackgoundBorder(0, 10, getResources().getColor(android.R.color.holo_red_light), Color.TRANSPARENT));
//                tvCA.setBackgroundDrawable(Const.setBackgoundBorder(0, 10, getResources().getColor(R.color.yelloww), Color.TRANSPARENT));
//                tvC.setBackgroundDrawable(Const.setBackgoundBorder(0, 10, getResources().getColor(R.color.green), Color.TRANSPARENT));

                dialog.show();
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

}

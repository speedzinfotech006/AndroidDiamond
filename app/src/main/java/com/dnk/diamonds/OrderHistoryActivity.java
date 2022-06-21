package com.dnk.shairugems;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.webkit.MimeTypeMap;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dnk.shairugems.BaseDrawerActivity;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class OrderHistoryActivity extends BaseDrawerActivity {

    ImageView imgSumm, imgMenu, imgSearch, imgDown, imgViewCount;
    LinearLayout llSumm, llSearchTop,llViewCount,llSearch, llClear;
    RelativeLayout rlMain;
    RecyclerView rvList;
    LinearLayoutManager lm;
    CheckBox cbSelectAll;
    TextView tvTitle, tvTotalCount, tvTotalPcs, tvTotalCts, tvTotalAvgDiscPer, tvTotalPricePerCts, tvTotalAmt, tvTotalAmount;
    EditText etSearch, etToDate, etFromDate, etStatus, etUserSearch;
    HashMap<Integer, String> hmSelected = new HashMap<>();
    HashMap<Integer, String> hmSelectedCust = new HashMap<>();
    ArrayList<HashMap<String, String>> maps = new ArrayList<>();
    ResultAdp adp;
    FloatingActionMenu Menu;
    com.github.clans.fab.FloatingActionButton llShare,llDownload;
    FloatingActionButton llPlus;
    RelativeLayout toolBar;
    boolean isChange = true;
    SharedPreferences sp;
    Double rap = 0.0, raprate = 0.0, totalRap = 0.0, totalamt = 0.0;
    Double totaldisc = 0.00, totalvalue = 0.00;
    String totalPcs = "0", totalCts = "0", totalAmount = "0", totalPricePerCts = "0", totalAvgDicPer = "0";
    int pastVisiblesItems, visibleItemCount, totalItemCount, pageCount = 1, totalSize = 0;
    String[] status = new String[]{"Select All", "Confirmed", "Not Available", "Checking Avaibility"};

    private Dialog d;
    ImageView imgPlus, img_Share, img_Download;
    TextView tv_Share, tv_Download;
    LinearLayout Menu_Share,Menu_Download,Menu_Wishlist,Menu_Compare,Menu_Copy,Menu_Stock,Menu_Clear,Menu_Back;
    View div_Share,div_Download,div_Wishlist,div_Compare,div_Copy,div_Stock;

    long downID = 0, shareID = 0;
    String downloadNumber = "", shareNumber = "";
    DownloadManager downloadManager, shareManager;
    LinearLayout llSearchThree;
    Double cts, amt, avgDisc, ppc, amtnew;
    FrameLayout overlay;
    Context context = OrderHistoryActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.order_history_activity, frameLayout);

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

        etStatus.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etStatus.performClick();
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
                            hmSelectedCust.put(i, maps.get(i).get("iuserid") + maps.get(i).get("fullorderdate"));
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


        //  setSupportActionBar(toolBar);
//        Pref.removeValue(context, Const.from_date);
//        Pref.removeValue(context, Const.to_date);
        Pref.removeValue(context, Const.stone_id);
        Pref.removeValue(context, Const.comp_name);
        Pref.removeValue(context, Const.Status);

        if (isNetworkAvailable()) {
            getSearchResult();
        }else{
            Const.DisplayNoInternet(context);
        }

        sp = PreferenceManager.getDefaultSharedPreferences(context);
        if (isNetworkAvailable()) {
            if (!sp.getBoolean("OverlayDisplayedOrderHistory", false)) {
                overlay.setVisibility(View.VISIBLE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("OverlayDisplayedOrderHistory", true);
                editor.apply();
            } else {
                overlay.setVisibility(View.GONE);
            }
        } else {
            Const.DisplayNoInternet(context);
        }
    }

    private void initView() {
        overlay = findViewById(R.id.overlay);
        Menu = findViewById(R.id.Menu);
        rlMain = findViewById(R.id.rlMain);
        imgSumm = findViewById(R.id.imgSumm);
        imgMenu = findViewById(R.id.imgMenu);
        etSearch = findViewById(R.id.etSearch);
        etUserSearch = findViewById(R.id.etUserSearch);
        imgSearch = findViewById(R.id.imgSearch);
        llSearch = findViewById(R.id.llSearch);
        llClear = findViewById(R.id.llClear);
        imgViewCount = findViewById(R.id.imgViewCount);
        rvList = findViewById(R.id.rvList);
        etStatus = findViewById(R.id.etStatus);
        etFromDate = findViewById(R.id.etFromDate);
        etToDate = findViewById(R.id.etToDate);
        toolBar = findViewById(R.id.toolBar);
        llShare = findViewById(R.id.llShare);
        llPlus = findViewById(R.id.llPlus);
        llDownload = findViewById(R.id.llDownload);
        imgDown = findViewById(R.id.imgDown);
        cbSelectAll = findViewById(R.id.cbSelectAll);

        llSearchTop = findViewById(R.id.llSearchTop);
        llSearchThree = findViewById(R.id.llSearchThree);
        llSumm = findViewById(R.id.llSumm);
        llViewCount = findViewById(R.id.llViewCount);
        tvTotalAmt = findViewById(R.id.tvTotalAmt);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        tvTotalCts = findViewById(R.id.tvTotalCts);
        tvTotalAvgDiscPer = findViewById(R.id.tvTotalAvgDiscPer);
        tvTotalCount = findViewById(R.id.tvTotalCount);
        tvTotalPcs = findViewById(R.id.tvTotalPcs);
        tvTotalPricePerCts = findViewById(R.id.tvTotalPricePerCts);

        lm = new LinearLayoutManager(context);
        rvList.setLayoutManager(lm);

        imgMenu.setOnClickListener(clickListener);
        imgViewCount.setOnClickListener(clickListener);
        imgDown.setOnClickListener(clickListener);
        llClear.setOnClickListener(clickListener);
        llSearch.setOnClickListener(clickListener);
        etFromDate.setOnClickListener(clickListener);
        etToDate.setOnClickListener(clickListener);
        etStatus.setOnClickListener(clickListener);
        overlay.setOnClickListener(clickListener);

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

        imgSumm.setOnClickListener(clickListener);

        if (Pref.getStringValue(context, Const.loginIsAdmin, "").equalsIgnoreCase("1")) {
            SimpleDateFormat dateFormat= new SimpleDateFormat("dd-MMM-yyyy");
            Calendar currentCal = Calendar.getInstance();
            String currentdate=dateFormat.format(currentCal.getTime());
            currentCal.add(Calendar.DATE, -7);
            String FromDate=dateFormat.format(currentCal.getTime());
            etFromDate.setText(FromDate);
            etToDate.setText(new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date()));

            Pref.setStringValue(context, Const.from_date, etFromDate.getText().toString());
            Pref.setStringValue(context, Const.to_date, etToDate.getText().toString());

        }else{
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
                llSearchThree.setVisibility(View.VISIBLE);
            } else {
                llSearchThree.setVisibility(View.GONE);
            }
        }
    }

    private void getSearchResult() {
        if (isNetworkAvailable()) {
            Const.showProgress(context);
            final Map<String, String> map = new HashMap<>();
            map.put("FromDate", Pref.getStringValue(context, Const.from_date, ""));
            map.put("ToDate", Pref.getStringValue(context, Const.to_date, ""));
            map.put("PageNo", pageCount + "");
            map.put("RefNo", Pref.getStringValue(context, Const.stone_id, ""));
            map.put("CompanyName", "");
            map.put("CommonName", Pref.getStringValue(context, Const.comp_name, ""));
            map.put("Status", Pref.getStringValue(context, Const.Status, ""));
            map.put("CustomerName", "");
            map.put("Location", "");
            map.put("OrderBy", "");
            map.put("PgSize", "50");
            Const.callPostApi(context, "Order/GetOrderHistory", map, new VolleyCallback() {
                @Override
                public void onSuccessResponse(String result) {
                    Const.dismissProgress();
                    try {
                        JSONObject object = new JSONObject(result);
                        if (object.optString("Data") != null && !object.optString("Data").equalsIgnoreCase("[]") && !object.optString("Data").equalsIgnoreCase("null")) {
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

                                    totalPcs = String.format("%,.0f", obj.optDouble("TOT_PCS"));
                                    totalCts = String.format("%,.2f", obj.optDouble("TOT_CTS"));
                                    totalAmount = "$ " + String.format("%,.0f", obj.optDouble("TOT_NET_AMOUNT"));
                                    totalamt = obj.optDouble("TOT_NET_AMOUNT");
                                    totalAvgDicPer = String.format("%.2f", obj.optDouble("AVG_SALES_DISC_PER")) + " %";
                                    totalRap = obj.optDouble("TOT_RAP_AMOUNT");
                                    tvTotalCount.setText("Showing 1 to " + maps.size() + " of " + obj.optString("TOT_PCS") + " entries");
                                }
                                tvTotalCts.setText(totalCts);
                                Double maindisc = Double.parseDouble("0.88");
                                Double offerval = totalamt;
                                Double webdisc = (offerval * (maindisc / 100));
                                totalvalue = (offerval - webdisc);

                                totaldisc = ((1 - (totalvalue / totalRap)) * 100) * -1;

                                tvTotalAmt.setText("$ " + String.format("%,.0f", totalvalue));
                                tvTotalAmount.setText(totalAmount);
                                tvTotalAvgDiscPer.setText(totaldisc.isInfinite() ? "0.00 %" : String.format("%,.2f", totaldisc) + " %");
                                tvTotalPcs.setText(totalPcs);
                                llViewCount.setVisibility(View.VISIBLE);
                                llSumm.setVisibility(View.VISIBLE);
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
                            llSumm.setVisibility(View.GONE);
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
        } else {
            Const.DisplayNoInternet(context);
        }
    }

    private void DownloadExcel() {
        if (isNetworkAvailable()) {
            Const.showProgress(context);
            List<String> RefNo = new ArrayList<>(hmSelected.values());
            List<String> Custlist = new ArrayList<>(hmSelectedCust.values());
            final Map<String, String> map = new HashMap<>();
            map.put("FromDate", Pref.getStringValue(context, Const.from_date, ""));
            map.put("ToDate", Pref.getStringValue(context, Const.to_date, ""));
            map.put("PageNo", "0");
            if (hmSelected.size() >= 1) {
                map.put("RefNo", RefNo.toString().replace("[", "").replace("]", "").replace(", ", ","));
            } else {
                map.put("RefNo", Pref.getStringValue(context, Const.stone_id, ""));
            }
            map.put("CompanyName", "");
            map.put("CommonName", Pref.getStringValue(context, Const.comp_name, ""));
            map.put("Status", Pref.getStringValue(context, Const.Status, ""));
            if (Pref.getStringValue(context, Const.loginIsAdmin, "").equalsIgnoreCase("1")) {
                map.put("isAdmin", "true");
            } else {
                map.put("isAdmin", "false");
            }
            if (Pref.getStringValue(context, Const.loginIsEmp, "").equalsIgnoreCase("1")) {
                map.put("isEmp", "true");
            } else {
                map.put("isEmp", "false");
            }
            map.put("iUserid_FullOrderDate", Custlist.toString().replace("[", "").replace("]", "").replace(", ", ","));
            map.put("PgSize", "50");
            Const.callPostApi(context, "Order/DownloadOrderHistory", map, new VolleyCallback() {
                @Override
                public void onSuccessResponse(String result1) {
                    Const.dismissProgress();
                    try {
                        String result = result1.replaceAll("^[\"']+|[\"']+$", "");
                        if (!result.equalsIgnoreCase("No data found.") && (!result.startsWith("Something Went wrong"))) {
                            Date date = new Date();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

                            Random objGenerator = new Random();
                            for (int iCount = 0; iCount < 1; iCount++) {
                                downloadNumber = String.valueOf(objGenerator.nextInt(1000));
                                System.out.println("Random No : " + downloadNumber);
                            }
                            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "OrderHistory " + dateFormat.format(date) + "-" + downloadNumber + ".xlsx");
                            registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                            downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(result.toString()));
                            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                            request.setTitle("OrderHistory " + dateFormat.format(date) + "-" + downloadNumber);
                            request.allowScanningByMediaScanner();
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "OrderHistory " + dateFormat.format(date) + "-" + downloadNumber + ".xlsx");
                            request.setMimeType(MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                                    MimeTypeMap.getFileExtensionFromUrl(result.toString())));
                            downID = downloadManager.enqueue(request);

                        } else {
                            Const.dismissProgress();
                            Const.showErrorDialog(context, "No data found.");
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
        } else {
            Const.DisplayNoInternet(context);
        }
    }

    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy") ;
            if (downID == id) {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "OrderHistory " + dateFormat.format(date) + "-" + downloadNumber + ".xlsx");
                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.setType("application/vnd.ms-excel");
                Uri uri = FileProvider.getUriForFile(OrderHistoryActivity.this, BuildConfig.APPLICATION_ID + ".provider",file);
                share.putExtra(Intent.EXTRA_STREAM, uri);
                share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                builder.detectFileUriExposure();
                openDownloadedAttachment(context, downID);
            }
            unregisterReceiver(onDownloadComplete);
        }
    };

    private void openDownloadedAttachment(final Context context, final long downloadId) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
        Cursor cursor = downloadManager.query(query);
        if (cursor.moveToFirst()) {
            int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            String downloadLocalUri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
            String downloadMimeType = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE));
            if ((downloadStatus == DownloadManager.STATUS_SUCCESSFUL) && downloadLocalUri != null) {
                openDownloadedAttachment(context, Uri.parse(downloadLocalUri), downloadMimeType);
            }
        }
        cursor.close();
    }

    private void openDownloadedAttachment(final Context context, Uri attachmentUri, final String attachmentMimeType) {
        if(attachmentUri!=null) {
            // Get Content Uri.
            if (ContentResolver.SCHEME_FILE.equals(attachmentUri.getScheme())) {
                // FileUri - Convert it to contentUri.
                File file = new File(attachmentUri.getPath());
                attachmentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider",file);
            }

            final Intent openAttachmentIntent = new Intent(Intent.ACTION_VIEW);
            openAttachmentIntent.setDataAndType(attachmentUri, attachmentMimeType);
            openAttachmentIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
            dialog.setCancelable(false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.layout_update_dialog);

            TextView tvMessage = (TextView) dialog.findViewById(R.id.tvMessage);
            Button btnYes = (Button) dialog.findViewById(R.id.btnYes);
            Button btnNo = (Button) dialog.findViewById(R.id.btnNo);

            tvMessage.setText("Do you want to open this file ?");
            btnYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Const.dismissProgress();
                    dialog.dismiss();
                    try {
                        context.startActivity(openAttachmentIntent);
                        dialog.dismiss();
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(context, "No Apps Available to Open File", Toast.LENGTH_LONG).show();
                    }
                }
            });
            btnNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isNetworkAvailable()) {
                        Const.dismissProgress();
                        dialog.dismiss();
                    }
                }
            });
            dialog.show();
        }
    }

    private void ShareExcel() {
        if (isNetworkAvailable()) {
            Const.showProgress(context);
            List<String> RefNo = new ArrayList<>(hmSelected.values());
            List<String> Custlist = new ArrayList<>(hmSelectedCust.values());
            final Map<String, String> map = new HashMap<>();
            map.put("FromDate", Pref.getStringValue(context, Const.from_date, ""));
            map.put("ToDate", Pref.getStringValue(context, Const.to_date, ""));
            map.put("PageNo", "0");
            if (hmSelected.size() >= 1) {
                map.put("RefNo", RefNo.toString().replace("[", "").replace("]", "").replace(", ", ","));
            } else {
                map.put("RefNo", Pref.getStringValue(context, Const.stone_id, ""));
            }
            map.put("CompanyName", "");
            map.put("CommonName", Pref.getStringValue(context, Const.comp_name, ""));
            map.put("Status", Pref.getStringValue(context, Const.Status, ""));
            if (Pref.getStringValue(context, Const.loginIsAdmin, "").equalsIgnoreCase("1")) {
                map.put("isAdmin", "true");
            } else {
                map.put("isAdmin", "false");
            }
            if (Pref.getStringValue(context, Const.loginIsEmp, "").equalsIgnoreCase("1")) {
                map.put("isEmp", "true");
            } else {
                map.put("isEmp", "false");
            }

            map.put("iUserid_FullOrderDate", Custlist.toString().replace("[", "").replace("]", "").replace(", ", ","));
            map.put("PgSize", "50");
            Const.callPostApi(context, "Order/DownloadOrderHistory", map, new VolleyCallback() {
                @Override
                public void onSuccessResponse(String result1) {
                    // Const.dismissProgress();
                    try {
                        String result = result1.replaceAll("^[\"']+|[\"']+$", "");
                        if (!result.equalsIgnoreCase("No data found.") && (!result.startsWith("Something Went wrong"))) {
                            Date date = new Date();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

                            Random objGenerator = new Random();
                            for (int iCount = 0; iCount < 1; iCount++) {
                                shareNumber = String.valueOf(objGenerator.nextInt(1000));
                                System.out.println("Random No : " + shareNumber);
                            }
                            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "OrderHistory " + dateFormat.format(date) + "-" + shareNumber + ".xlsx");
                            registerReceiver(onShareComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                            shareManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(result.toString()));
                            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                            request.setTitle("OrderHistory " + dateFormat.format(date) + "-" + shareNumber);
                            request.allowScanningByMediaScanner();
                            // request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "OrderHistory " + dateFormat.format(date) + "-" + shareNumber + ".xlsx");
                            request.setMimeType(MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                                    MimeTypeMap.getFileExtensionFromUrl(result.toString())));
                            shareID = shareManager.enqueue(request);
                        } else {
                            Const.dismissProgress();
                            Const.showErrorDialog(context, "No data found.");
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
        } else {
            Const.DisplayNoInternet(context);
        }
    }

    private BroadcastReceiver onShareComplete = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onReceive(Context context, Intent intent) {
            Const.dismissProgress();
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy") ;
            if (shareID == id) {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "OrderHistory " + dateFormat.format(date) + "-" + shareNumber + ".xlsx");
                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.setType("application/vnd.ms-excel");
                Uri uri = FileProvider.getUriForFile(OrderHistoryActivity.this, BuildConfig.APPLICATION_ID + ".provider",file);
                share.putExtra(Intent.EXTRA_STREAM, uri);
                share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                builder.detectFileUriExposure();
                startActivity(Intent.createChooser(share, "Share via"));
            }
            unregisterReceiver(onShareComplete);
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

    private void SummDialog() {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.layout_dialog_summary, null);
        final PopupWindow popupWindow = new PopupWindow(popupView,android.view.ViewGroup.LayoutParams.WRAP_CONTENT,android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.DialogAnimation);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        rlMain.setBackgroundResource(R.color.transgray);

        popupWindow.setTouchInterceptor(new View.OnTouchListener()
        {
            public boolean onTouch(View v, MotionEvent event)
            {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popupWindow.dismiss();
                    return true;
                }
                rlMain.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                return false;
            }
        });

        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        TextView tvPcs = popupView.findViewById(R.id.tvPcs);
        TextView tvCts = popupView.findViewById(R.id.tvCts);
        TextView tvOfferDisc = popupView.findViewById(R.id.tvOfferDisc);
        TextView tvOfferValue = popupView.findViewById(R.id.tvOfferValue);

        TextView tvPriceCts = popupView.findViewById(R.id.tvPriceCts);
        TextView tvWebDisc = popupView.findViewById(R.id.tvWebDisc);
        TextView tvFinalDisc = popupView.findViewById(R.id.tvFinalDisc);
        TextView tvFinalValue = popupView.findViewById(R.id.tvFinalValue);
        TextView tvOk = popupView.findViewById(R.id.tvOk);

        if(cbSelectAll.isChecked())
        {

            double totalcts = 0;
            double totalamt = 0;
            double totalavgdiscper = 0;
            double totalpricepercts = 0;
            double totalrap = 0;
            double totalraprate = 0;
            for (int i = 0; i < maps.size(); i++) {
                if (!maps.get(i).get("dcts").equalsIgnoreCase("null")) {
                    totalcts += Double.parseDouble(maps.get(i).get("dcts"));
                }
                if (!maps.get(i).get("dnetprice").equalsIgnoreCase("null")) {
                    totalamt += Double.parseDouble(maps.get(i).get("dnetprice"));
                }
                if (!maps.get(i).get("drapamount").equalsIgnoreCase("null")) {
                    totalrap += Double.parseDouble(maps.get(i).get("drapamount"));
                }
                if (!maps.get(i).get("drepprice").equalsIgnoreCase("null")) {
                    totalraprate += Double.parseDouble(maps.get(i).get("drepprice"));
                }
            }

            Double maindisc = Double.parseDouble("0.88");
            Double avgdisc = 0.00;
            if(totalraprate == 0.0) {
                avgdisc = 0.00;
            } else {
                avgdisc = (100 - ((Double.parseDouble(tvTotalAmount.getText().toString().replace("$","").replace(",","").split(" ")[1]) * 100) / totalrap)) * -1;
            }
            Double ppc = Double.parseDouble(tvTotalAmount.getText().toString().replace("$","").replace(",","").split(" ")[1]) / Double.parseDouble(tvTotalCts.getText().toString().replace(",",""));

            Double offerval = totalamt;
            Double webdisc = (offerval * (maindisc / 100));
            Double finalvalue = (offerval - webdisc);

            Double finaldisc = 0.00;
            if(totalraprate == 0.0) {
                finaldisc = 0.00;
            } else {
                finaldisc = ((1 - (finalvalue / totalrap)) * 100) * -1;
            }

            tvPcs.setText(hmSelected.size() + "");
            tvCts.setText(String.format("%,.2f", totalcts));
            tvOfferDisc.setText(String.format("%.2f", avgdisc) + " %");
            tvOfferValue.setText("$ " + String.format("%,.0f", totalamt));

            tvPriceCts.setText(String.format("%,.2f", ppc));
            tvWebDisc.setText("$ " + String.format("%.2f", webdisc));
            tvFinalDisc.setText(finaldisc.isInfinite() ? "0.00 %" : String.format("%,.2f", finaldisc) + " %");
            tvFinalValue.setText("$ " + String.format("%,.2f", finalvalue));
        } else {
            double totalraprate = 0;
            for (int i = 0; i < maps.size(); i++) {
                if (!maps.get(i).get("drepprice").equalsIgnoreCase("null")) {
                    totalraprate += Double.parseDouble(maps.get(i).get("drepprice"));
                }
            }
            Double maindisc = Double.parseDouble("0.88");

            if(totalraprate == 0.0) {
                avgDisc = 0.00;
            }

            Double offerval = amt;
            Double webdisc = (offerval * (maindisc / 100));
            Double finalvalue = (offerval - webdisc);

            Double finaldisc = 0.00;
            if(totalraprate == 0.0) {
                finaldisc = 0.00;
            } else {
                finaldisc = ((1 - (finalvalue / rap)) * 100) * -1;
            }

            tvPcs.setText(hmSelected.size() + "");
            tvCts.setText(String.format("%,.2f", cts));
            tvOfferDisc.setText(avgDisc.isInfinite() ? "0.00 %" : String.format("%.2f", avgDisc) + " %");
            tvOfferValue.setText("$ " + String.format("%,.0f", amt));

            tvPriceCts.setText(String.format("%,.2f", ppc));
            tvWebDisc.setText("$ " + String.format("%.2f", webdisc));
            tvFinalDisc.setText(finaldisc.isInfinite() ? "0.00 %" : String.format("%,.2f", finaldisc) + " %");
            tvFinalValue.setText("$ " + String.format("%,.2f", finalvalue));
        }

        tvOk.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                rlMain.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }});

        popupView.setOnTouchListener(new View.OnTouchListener() {
            private float dx = 0;
            private float dy = 0;
            private int mPosX = 0;
            private int mPosY = 0;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        dx = mPosX - motionEvent.getRawX();
                        dy = mPosY - motionEvent.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mPosX = (int) (motionEvent.getRawX() + dx);
                        mPosY = (int) (motionEvent.getRawY() + dy);
                        popupWindow.update(mPosX, mPosY, -1, -1);
                        break;
                }
                rlMain.setBackgroundResource(R.color.transgray);
                return true;
            }
        });
    }

    private void openSatutsDialog() {
        final Dialog dialog = new Dialog(context, R.style.Theme_Dialog);

        LinearLayout layout = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(params);
        layout.setOrientation(LinearLayout.VERTICAL);

        final ListView listview = new ListView(context);
        listview.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        listview.setAdapter(new ArrayAdapter<String>(this, R.layout.layout_multi_select_item, status));

        LinearLayout layoutt = new LinearLayout(context);

        LinearLayout.LayoutParams paramss = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 2);
        layoutt.setOrientation(LinearLayout.HORIZONTAL);

        Button buttonOk = new Button(context);
        buttonOk.setTypeface(ResourcesCompat.getFont(context, R.font.proxima_nova));
        buttonOk.setText("OK");
        buttonOk.setLayoutParams(paramss);

        Button buttonCancel = new Button(context);
        buttonCancel.setTypeface(ResourcesCompat.getFont(context, R.font.proxima_nova));
        buttonCancel.setText("CANCEL");
        buttonCancel.setLayoutParams(paramss);

        layoutt.addView(buttonOk);
        layoutt.addView(buttonCancel);

        if (!Const.isEmpty(etStatus)) {
            ArrayList<String> list = new ArrayList<String>(Arrays.asList(status));
            String[] array = etStatus.getText().toString().split(",");
            for (int i = 0; i < array.length; i++) {
                listview.setItemChecked(list.indexOf(array[i]), true);
            }
            if (array.length == (list.size() - 1)) {
                listview.setItemChecked(0, true);
            }
        }

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> list = new ArrayList<>();
                for (int i = 1; i < status.length; i++) {
                    if (listview.isItemChecked(i)) {
                        list.add(listview.getItemAtPosition(i).toString());
                    }
                }
                etStatus.setText(list.toString().replace("[", "").replace("]", ""));
                Pref.removeValue(context, Const.Status);
                Pref.setStringValue(context, Const.Status, etStatus.getText().toString().replaceAll(", ",","));
                dialog.dismiss();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        layout.addView(listview);
        layout.addView(layoutt);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    if (listview.isItemChecked(position)) {
                        for (int i = 1; i < status.length; i++) {
                            listview.setItemChecked(i, true);
                        }
                    } else {
                        for (int i = 1; i < status.length; i++) {
                            listview.setItemChecked(i, false);
                        }
                    }
                } else {
                    boolean b = false;
                    for (int i = 1; i < status.length; i++) {
                        if (listview.isItemChecked(i)) {
                            b = true;
                        } else {
                            b = false;
                            break;
                        }
                    }

                    if (b) {
                        listview.setItemChecked(0, true);
                    } else {
                        listview.setItemChecked(0, false);
                    }
                }
            }
        });

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layout);
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dialog.getWindow().setBackgroundDrawable(Const.setBackgoundBorder(0, 20, Color.WHITE, Color.WHITE));
        dialog.show();
    }

    private void clearTotal() {
        hmSelected = new HashMap<>();
        hmSelectedCust = new HashMap<>();
        rap = totalRap;
        tvTotalPcs.setText(String.valueOf(totalSize));
        tvTotalCts.setText(totalCts);
        tvTotalAmt.setText(totalAmount);
        tvTotalAmount.setText(totalAmount);
        tvTotalAvgDiscPer.setText(totaldisc.isInfinite() ? "0.00 %" : String.format("%.2f", totaldisc) + " %");
    }

    private void clearTotalAdp() {
        rap = 0.0;
        tvTotalPcs.setText("0");
        tvTotalCts.setText("0");
        tvTotalAmt.setText("$ 0");
        tvTotalAmount.setText("$ 0");
        tvTotalAvgDiscPer.setText("0 %");
        tvTotalPricePerCts.setText("$ 0");
    }

    private void clearTotalAll() {
        hmSelected = new HashMap<>();
        hmSelectedCust = new HashMap<>();
        rap = totalRap;
        double totalcts = 0;
        double totalamt = 0;
        double totalavgdiscper = 0;
        double totalpricepercts = 0;
        double totalrap = 0;
        double totalraprate = 0;
        for (int i = 0; i < maps.size(); i++) {

            if (!maps.get(i).get("dcts").equalsIgnoreCase("null")) {
                totalcts += Double.parseDouble(maps.get(i).get("dcts"));
            }
            if (!maps.get(i).get("dnetprice").equalsIgnoreCase("null")) {
                totalamt += Double.parseDouble(maps.get(i).get("dnetprice"));
            }
            if (!maps.get(i).get("drapamount").equalsIgnoreCase("null")) {
                totalrap += Double.parseDouble(maps.get(i).get("drapamount"));
            }
            if (!maps.get(i).get("drepprice").equalsIgnoreCase("null")) {
                totalraprate += Double.parseDouble(maps.get(i).get("drepprice"));
            }
        }

        tvTotalPcs.setText(maps.size() + "");
        tvTotalCts.setText(String.format("%,.2f", totalcts));
        Double maindisc = Double.parseDouble("0.88");

        Double offerval = totalamt;
        Double webdisc = (offerval * (maindisc / 100));
        Double finalvalue = (offerval - webdisc);

        Double finaldisc = 0.00;
        finaldisc = ((1 - (finalvalue / totalrap)) * 100) * -1;

        tvTotalAmt.setText("$ " + String.format("%,.0f", finalvalue));
        tvTotalAmount.setText("$ " + String.format("%,.0f", totalamt));
        Double avgDisc = (100 - ((Double.parseDouble(tvTotalAmount.getText().toString().replace("$","").replace(",","").split(" ")[1]) * 100) / totalrap)) * -1;
        if(totalraprate == 0.0) {
            tvTotalAvgDiscPer.setText("0.00 %");
        } else {
            tvTotalAvgDiscPer.setText(finaldisc.isInfinite() ? "0.00 %" : String.format("%.2f", finaldisc) + " %");
        }
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
                View view = LayoutInflater.from(context).inflate(R.layout.layout_order_history_design, parent, false);
                return new ResultAdp.GridViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            final HashMap<String, String> map = arraylist.get(position);

                if (holder instanceof ResultAdp.GridViewHolder) {
                    final ResultAdp.GridViewHolder h = (ResultAdp.GridViewHolder) holder;

                    if (Pref.getStringValue(context, Const.loginIsAdmin, "").equalsIgnoreCase("1") || Pref.getStringValue(context, Const.loginIsEmp, "").equalsIgnoreCase("1")) {
                        h.custname.setVisibility(View.VISIBLE);
                    } else {
                        h.custname.setVisibility(View.GONE);
                    }

                    h.orderid.setText("Order : " +"#" + map.get("iorderid"));
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
                    h.date.setText(map.get("orderdate"));
                    h.rap.setText("Rap : " + String.format("%,.2f", Double.parseDouble(map.get("drapamount"))));
                    h.disc.setText("Disc : " +String.format("%,.2f", Double.parseDouble(map.get("ddisc"))) + " %");
                    h.amt.setText("$ " + String.format("%,.2f", Double.parseDouble(map.get("dnetprice"))));

//                    if (map.get("scolor").equalsIgnoreCase("FAINT PINK")) {
//                        h.colour.setText("FP");
//                    }else{
                    h.colour.setText(map.get("scolor") + "  " + map.get("sclarity") + "  " + String.format("%.2f", Double.parseDouble(map.get("dcts"))));
//                    }

                    h.location.setText(map.get("location"));
//                    h.clarity.setText(map.get("sclarity"));
//                    h.cts.setText(String.format("%.2f", Double.parseDouble(map.get("dcts"))));
                    h.cps.setText(map.get("scut") + "-" + map.get("spolish") + "-" + map.get("ssymm"));
                    h.fls.setText(map.get("sfls"));

                    if (map.get("companyname") != null && map.get("companyname") != "" && !map.get("companyname").equalsIgnoreCase("null") && !map.get("companyname").equalsIgnoreCase("")) {
                        h.custname.setText("Company : " + map.get("companyname"));
                    } else {
                        h.custname.setText("");
                        h.custname.setVisibility(View.GONE);
                    }

                    if (map.get("scut").equalsIgnoreCase("3EX")) {
                        h.cps.setTypeface(Typeface.DEFAULT_BOLD);
                    }
                    if (map.get("sstonestatus") != null && map.get("sstonestatus") != "" && !map.get("sstonestatus").equalsIgnoreCase("")) {
                        h.status.setText(map.get("sstonestatus"));
                    } else {
                        h.status.setText("");
                    }

                    if (map.get("final_disc") != null) {
                        h.finaldisc.setText("F Disc % : " + String.format("%,.2f", Double.parseDouble(map.get("final_disc"))));
                    } else {
                        h.finaldisc.setText("");
                    }
                    if (map.get("net_value") != null) {
                        h.finalvalue.setText("F Value $ : " + String.format("%,.2f", Double.parseDouble(map.get("net_value"))));
                    } else {
                        h.finalvalue.setText("");
                    }
                    if (map.get("web_benefit") != null) {
                        h.finalwebdisc.setText("Web Disc $ : " + String.format("%,.2f", Double.parseDouble(map.get("web_benefit"))));
                    } else {
                        h.finalwebdisc.setText("");
                    }

                    if (map.get("exp_del_date") != null && map.get("exp_del_date") != "" && !map.get("exp_del_date").equalsIgnoreCase("") && !map.get("exp_del_date").equalsIgnoreCase("null")) {
                        h.odetail_arrival.setText("Arrival : " + map.get("exp_del_date"));
                    } else {
                        h.odetail_arrival.setText("Arrival : -");
                    }

                    if (map.get("pickup_status") != null && map.get("pickup_status") != "" && !map.get("pickup_status").equalsIgnoreCase("") && !map.get("pickup_status").equalsIgnoreCase("null")) {
                        if (map.get("pickup_status").equalsIgnoreCase("Yes")) {
                            h.odetail_pickup.setText(map.get("pickup_status"));
                            h.odetail_pickup.setTextColor(Color.parseColor("#FF0B8B42"));
                        } else {
                            h.odetail_pickup.setText(map.get("pickup_status"));
                            h.odetail_pickup.setTextColor(Color.parseColor("#FF0000"));
                        }
                    } else {
                        h.odetail_pickup.setText("-");
                    }

                    if (map.get("delivery_date") != null && map.get("delivery_date") != "" && !map.get("delivery_date").equalsIgnoreCase("")  && !map.get("delivery_date").equalsIgnoreCase("null")) {
                        h.odetail_collected.setText("Collect : " + map.get("delivery_date"));
                    } else {
                        h.odetail_collected.setText("Collect : -");
                    }

                    if (map.get("location") != null && map.get("location") != "" && map.get("location").equalsIgnoreCase("Upcoming")) {
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
                                hmSelectedCust.remove(position);
                                cbSelectAll.setChecked(false);

                                tvTotalPcs.setText(hmSelected.size() + "");
                                cts = Double.parseDouble(tvTotalCts.getText().toString()) - Double.parseDouble(map.get("dcts"));
                                tvTotalCts.setText(String.format("%,.2f", cts));


                                totalamt = Double.parseDouble(tvTotalAmt.getText().toString().replace("$","").replace(",","").split(" ")[1]) - Double.parseDouble(map.get("net_value"));
                                amt = Double.parseDouble(tvTotalAmt.getText().toString().replace("$","").replace(",","").split(" ")[1]) - Double.parseDouble(map.get("dnetprice"));
                                amtnew = Double.parseDouble(tvTotalAmt.getText().toString().replace("$","").replace(",","").split(" ")[1]) - Double.parseDouble(map.get("dnetprice"));
                                tvTotalAmount.setText("$ " + String.format("%,.0f", amtnew));

                                rap = rap - Double.parseDouble(map.get("drapamount"));
                                avgDisc = (100 - ((Double.parseDouble(tvTotalAmount.getText().toString().replace("$","").replace(",","").split(" ")[1]) * 100) / rap)) * -1;
                                // tvTotalAvgDiscPer.setText(avgDisc.isInfinite() ? "0.00 %" : String.format("%.2f", avgDisc) + " %");

                                Double maindisc = Double.parseDouble("0.88");
                                raprate = raprate - Double.parseDouble(map.get("drepprice"));

                                Double offerval = amt;
                                Double webdisc = (offerval * (maindisc / 100));
                                Double finalvalue = (offerval - webdisc);

                                Double finaldisc = 0.00;
                                if(rap == 0.0) {
                                    finaldisc = 0.00;
                                } else {
                                    finaldisc = ((1 - (finalvalue / rap)) * 100) * -1;
                                }
                                tvTotalAmt.setText("$ " + String.format("%,.0f", totalamt));
                                tvTotalAvgDiscPer.setText(finaldisc.isInfinite() ? "0.00 %" : String.format("%.2f", finaldisc) + " %");

                                if (hmSelected.size() == 0) {
                                    clearTotal();
                                }

                                if (map.get("location") != null && map.get("location") != "" && map.get("location").equalsIgnoreCase("Upcoming")){
                                    h.llMain.setBackgroundColor(Color.rgb(255, 255, 153));
                                    h.tvtick.setVisibility(View.GONE);
                                } else {
                                    h.llMain.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_background_box));
                                    h.tvtick.setVisibility(View.GONE);
                                }

                            } else {
                                hmSelected.put(position, map.get("srefno"));
                                hmSelectedCust.put(position, map.get("iuserid") + map.get("fullorderdate"));
                                if (hmSelected.size() == arraylist.size()) {
                                    cbSelectAll.setChecked(true);
                                    h.llMain.setBackgroundDrawable(getResources().getDrawable(R.drawable.clickable));
                                    h.tvtick.setVisibility(View.VISIBLE);
                                }
                                if (hmSelected.size() == 1) {
                                    clearTotalAdp();
                                }

                                tvTotalPcs.setText(hmSelected.size() + "");
                                cts = Double.parseDouble(tvTotalCts.getText().toString()) + Double.parseDouble(map.get("dcts"));
                                tvTotalCts.setText(String.format("%,.2f", cts));

                                amt = Double.parseDouble(tvTotalAmt.getText().toString().replace("$","").replace(",","").split(" ")[1]) + Double.parseDouble(map.get("dnetprice"));
                                amtnew = Double.parseDouble(tvTotalAmount.getText().toString().replace("$","").replace(",","").split(" ")[1]) + Double.parseDouble(map.get("dnetprice"));
                                totalamt = Double.parseDouble(tvTotalAmt.getText().toString().replace("$","").replace(",","").split(" ")[1]) + Double.parseDouble(map.get("net_value"));
                                tvTotalAmount.setText("$ " + String.format("%,.0f", amtnew));

                                rap = rap + Double.parseDouble(map.get("drapamount"));
                                avgDisc = (100 - ((Double.parseDouble(tvTotalAmount.getText().toString().replace("$","").replace(",","").split(" ")[1]) * 100) / rap)) * -1;

                                Double maindisc = Double.parseDouble("0.88");
                                raprate = raprate + Double.parseDouble(map.get("drepprice"));

                                Double offerval = amt;
                                Double webdisc = (offerval * (maindisc / 100));
                                Double finalvalue = (offerval - webdisc);

                                Double finaldisc = 0.00;
                                if(rap == 0.0) {
                                    finaldisc = 0.00;
                                } else {
                                    finaldisc = ((1 - (finalvalue / rap)) * 100) * -1;
                                }
                                tvTotalAmt.setText("$ " + String.format("%,.0f", totalamt));
                                tvTotalAvgDiscPer.setText(finaldisc.isInfinite() ? "0.00 %" : String.format("%.2f", finaldisc) + " %");

                                if (map.get("location") != null && map.get("location") != "" && map.get("location").equalsIgnoreCase("Upcoming")) {
                                    h.llMain.setBackgroundDrawable(getResources().getDrawable(R.drawable.upcoming));
                                    h.tvtick.setVisibility(View.VISIBLE);
                                }else{
                                    h.llMain.setBackgroundDrawable(getResources().getDrawable(R.drawable.clickable));
                                    h.tvtick.setVisibility(View.VISIBLE);
                                }
                            }
                            ppc = Double.parseDouble(tvTotalAmt.getText().toString().replace("$","").replace(",","").split(" ")[1]) / Double.parseDouble(tvTotalCts.getText().toString().replace(",",""));
                            tvTotalPricePerCts.setText("$ " + (ppc.isNaN() ? "0" : String.format("%,.2f", ppc)));
                        }
                    });
                }
        }

        @Override
        public int getItemCount() {
            return arraylist.size();
        }

        private class GridViewHolder extends RecyclerView.ViewHolder {

            TextView orderid, stoneid, certino, bgm, date, rap, disc, amt, colour, clarity, cts, cps, fls, status, location, custname, username,
                        finaldisc, finalvalue, finalwebdisc, odetail_arrival, odetail_pickup, odetail_collected;
            LinearLayout llMain;
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
                custname =  v.findViewById(R.id.odetail_cust);
                llMain =  v.findViewById(R.id.llMain);
                tvtick =  v.findViewById(R.id.tvtick);

                finaldisc = (TextView) v.findViewById(R.id.odetail_fDisc);
                finalvalue = (TextView) v.findViewById(R.id.odetail_fvalue);
                finalwebdisc = (TextView) v.findViewById(R.id.odetail_webdisc);

                odetail_arrival = (TextView) v.findViewById(R.id.odetail_arrival);
                odetail_pickup = (TextView) v.findViewById(R.id.odetail_pickup);
                odetail_collected = (TextView) v.findViewById(R.id.odetail_collected);
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
                case R.id.overlay:
                    overlay.setVisibility(View.GONE);
                    break;
                case R.id.etFromDate:
                    openFromDatePickerDialog();
                    break;
                case R.id.etToDate:
                    openToDatePickerDialog();
                    break;
                case R.id.etStatus:
                    openSatutsDialog();
                    break;
                case R.id.llSearch:
                    Menu.close(true);
                    Pref.setStringValue(context, Const.from_date, etFromDate.getText().toString());
                    Pref.setStringValue(context, Const.to_date, etToDate.getText().toString());
                    Pref.setStringValue(context, Const.stone_id, etSearch.getText().toString().replaceAll(" ", ",").replaceAll("\n", ",").replaceAll("\t", ",").replaceAll("  ", ","));
                    Pref.setStringValue(context, Const.comp_name, etUserSearch.getText().toString().replaceAll("\n", ",").replaceAll("\t", ","));
                    maps = new ArrayList<>();
                    clearTotal();
                    pageCount = 1;
                    totalSize = 0;
                    Const.hideSoftKeyboard(OrderHistoryActivity.this);
                   // Const.resetParameters(context);
                    getSearchResult();
                    break;
                case R.id.llPlus:
                    d.show();
                    break;
                case R.id.Menu_Back:
                    d.dismiss();
                    break;
                case R.id.imgSumm:
                    if (hmSelected.size() >= 1) {
                        SummDialog();
                    } else {
                        Const.showErrorDialog(context, "Please select at least one stone");
                    }
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
                    etStatus.setText("");
                    etSearch.setText("");
                    etUserSearch.setText("");
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
                    Pref.setStringValue(context, Const.from_date, etFromDate.getText().toString());
                    Pref.setStringValue(context, Const.to_date, etToDate.getText().toString());
                    Pref.removeValue(context, Const.stone_id);
                    Pref.removeValue(context, Const.comp_name);
                    Pref.removeValue(context, Const.Status);
                    maps = new ArrayList<>();
                    clearTotal();
                    pageCount = 1;
                    totalSize = 0;
                    Const.hideSoftKeyboard(OrderHistoryActivity.this);
                    getSearchResult();
                    break;
                case R.id.Menu_Clear:
                    d.dismiss();
                    cbSelectAll.setChecked(false);
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
                case R.id.imgViewCount:
                    if ((llViewCount.getVisibility() == View.VISIBLE)  && (llSumm.getVisibility() == View.VISIBLE)){
                        imgViewCount.setImageDrawable(getResources().getDrawable(R.drawable.ic_down));
                        collapse(llViewCount);
                        collapse(llSumm);
                    } else {
                        imgViewCount.setImageDrawable(getResources().getDrawable(R.drawable.ic_up));
                        expand(llViewCount);
                        expand(llSumm);
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
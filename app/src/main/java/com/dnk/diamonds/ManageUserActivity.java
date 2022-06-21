package com.dnk.shairugems;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dnk.shairugems.Class.LoginData;
import com.dnk.shairugems.Utils.Const;
import com.dnk.shairugems.Utils.Pref;
import com.dnk.shairugems.Utils.RestClient;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dnk.shairugems.Utils.Const.setBackgoundBorder;

public class ManageUserActivity extends BaseDrawerActivity {

    ImageView imgMenu, imgSearch, imgDown;
    LinearLayout llSearchTop,llSearch, llClear;
    RecyclerView rvList;
    LinearLayoutManager lm;
    RelativeLayout rlMain;
    LinearLayout llHorizontal;
    EditText etSearch, etUserType, etActive, etType;
    HashMap<Integer, String> hmSelected = new HashMap<>();
    ArrayList<HashMap<String, String>> maps = new ArrayList<>();
    ResultAdp adp;
    com.github.clans.fab.FloatingActionButton llAddEmp,llShare,llDownload;
    FloatingActionMenu Menu;
    FloatingActionButton llPlus;
    RelativeLayout toolBar;
    boolean isChange = true;
    Double rap = 0.0, totalRap = 0.0;
    String totalCts = "0", totalAmount = "0", totalPricePerCts = "0", totalAvgDicPer = "0";
    int pastVisiblesItems, visibleItemCount, totalItemCount, pageCount = 1, totalSize = 0;
    ArrayList<String> arrayUserType = new ArrayList<>();
    ArrayList<String> arrayActive = new ArrayList<>();
    ArrayList<String> arrayType = new ArrayList<>();

    private Dialog d;
    ImageView imgPlus, img_Share, img_Download;
    TextView tv_Share, tv_Download;
    LinearLayout Menu_AddEmp,Menu_Share,Menu_Download,Menu_Wishlist,Menu_Compare,Menu_Copy,Menu_Stock,Menu_Clear,Menu_Back;
    View div_AddEmp, div_Share,div_Download,div_Wishlist,div_Compare,div_Copy,div_Stock;

    long downID = 0, shareID = 0;
    String downloadNumber = "", shareNumber = "";
    DownloadManager downloadManager, shareManager;
    ArrayAdapter adapter;
    Context context = ManageUserActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_manage_user, frameLayout);

        initView();

        etUserType.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    openSelection(etUserType, arrayUserType, "Select User Type");
                }
            }
        });

        etActive.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    openSelection(etActive, arrayActive, "Select Status");
                }
            }
        });

        etType.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    openSelection(etType, arrayType, "Select Type");
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

        Pref.removeValue(context, Const.User_Type);
        Pref.removeValue(context, Const.Type);
        Pref.removeValue(context, Const.User_Status);
        Pref.removeValue(context, Const.Search);
        getSearchResult();
    }

    private void initView() {
        rlMain = findViewById(R.id.rlMain);
        Menu = findViewById(R.id.Menu);
        imgMenu = findViewById(R.id.imgMenu);
        etSearch = findViewById(R.id.etSearch);
        imgSearch = findViewById(R.id.imgSearch);
        llSearch = findViewById(R.id.llSearch);
        llClear = findViewById(R.id.llClear);
        rvList = findViewById(R.id.rvList);
        etUserType = findViewById(R.id.etUserType);
        etActive = findViewById(R.id.etActive);
        etType = findViewById(R.id.etType);
        toolBar = findViewById(R.id.toolBar);
        llPlus = findViewById(R.id.llPlus);
        llShare = findViewById(R.id.llShare);
        llDownload = findViewById(R.id.llDownload);
        llAddEmp = findViewById(R.id.llAddEmp);
        imgDown = findViewById(R.id.imgDown);

        llHorizontal = findViewById(R.id.llHorizontal);
        llSearchTop = findViewById(R.id.llSearchTop);

        lm = new LinearLayoutManager(context);
        rvList.setLayoutManager(lm);

        imgMenu.setOnClickListener(clickListener);
        imgDown.setOnClickListener(clickListener);
        llClear.setOnClickListener(clickListener);
        llSearch.setOnClickListener(clickListener);
        llPlus.setOnClickListener(clickListener);
        etUserType.setOnClickListener(clickListener);
        etActive.setOnClickListener(clickListener);
        etType.setOnClickListener(clickListener);

        llAddEmp.setOnClickListener(clickListener);
        llShare.setOnClickListener(clickListener);
        llDownload.setOnClickListener(clickListener);

        llAddEmp.setBackgroundDrawable(setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));
        llShare.setBackgroundDrawable(setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));
        llDownload.setBackgroundDrawable(setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));

        d = new Dialog(this, R.style.Dialog);
        WindowManager.LayoutParams wmpl = d.getWindow().getAttributes();
        LayoutInflater inflate = LayoutInflater.from(context);
        d.setCancelable(true);
        View view = inflate.inflate(R.layout.left_menu_new, null);
        wmpl.gravity = Gravity.CENTER | Gravity.CENTER_HORIZONTAL;
        d.setContentView(view);

        Menu_AddEmp =  view.findViewById(R.id.Menu_AddEmp);
        Menu_Share =  view.findViewById(R.id.Menu_Share);
        Menu_Download =  view.findViewById(R.id.Menu_Download);
        Menu_Wishlist =  view.findViewById(R.id.Menu_Wishlist);
        Menu_Compare =  view.findViewById(R.id.Menu_Compare);
        Menu_Copy =  view.findViewById(R.id.Menu_Copy);
        Menu_Stock =  view.findViewById(R.id.Menu_Stock);
        Menu_Clear =  view.findViewById(R.id.Menu_Clear);
        Menu_Back =  view.findViewById(R.id.Menu_Back);

        div_AddEmp =  view.findViewById(R.id.div_AddEmp);
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

        Menu_AddEmp.setVisibility(View.VISIBLE);
        div_AddEmp.setVisibility(View.VISIBLE);
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
        Menu_Clear.setVisibility(View.GONE);

        Menu_AddEmp.setOnClickListener(clickListener);
        Menu_Share.setOnClickListener(clickListener);
        Menu_Download.setOnClickListener(clickListener);
        Menu_Wishlist.setOnClickListener(clickListener);
        Menu_Compare.setOnClickListener(clickListener);
        Menu_Copy.setOnClickListener(clickListener);
        Menu_Stock.setOnClickListener(clickListener);
        Menu_Clear.setOnClickListener(clickListener);
        Menu_Back.setOnClickListener(clickListener);

        arrayUserType.add("Admin");
        arrayUserType.add("Employee");
        arrayUserType.add("Registered");

        arrayActive.add("All");
        arrayActive.add("Active");
        arrayActive.add("InActive");
        arrayActive.add("Suspended");

        arrayType.add("User Name");
        arrayType.add("Company Name");
        arrayType.add("Customer Name");
    }

    private void getSearchResult() {
        Const.showProgress(context);
        final Map<String, String> map = new HashMap<>();
        map.put("CountryName", "");
        if (Pref.getStringValue(context, Const.Type, "").equalsIgnoreCase("User Name")) {
            map.put("UserName", Pref.getStringValue(context, Const.Search, ""));
        } else if(Pref.getStringValue(context, Const.Type, "").equalsIgnoreCase("Company Name")){
            map.put("CompanyName", Pref.getStringValue(context, Const.Search, ""));
        } else if(Pref.getStringValue(context, Const.Type, "").equalsIgnoreCase("Customer Name")) {
            map.put("UserFullName", Pref.getStringValue(context, Const.Search, ""));
        }else{
            map.put("UserName", "");
            map.put("CompanyName", "");
            map.put("UserFullName", "");
        }
        if (Pref.getStringValue(context, Const.User_Type, "").equalsIgnoreCase("Admin")) {
            map.put("UserType", "1");
        } else if(Pref.getStringValue(context, Const.User_Type, "").equalsIgnoreCase("Employee")){
            map.put("UserType", "2");
        } else if(Pref.getStringValue(context, Const.User_Type, "").equalsIgnoreCase("Registered")) {
            map.put("UserType", "3");
        }else{
            map.put("UserType", "");
        }
        if (Pref.getStringValue(context, Const.User_Status, "").equalsIgnoreCase("Active")) {
            map.put("UserStatus", "A");
        } else if(Pref.getStringValue(context, Const.User_Status, "").equalsIgnoreCase("In Active")){
            map.put("UserStatus", "I");
        } else if(Pref.getStringValue(context, Const.User_Status, "").equalsIgnoreCase("Suspended")) {
            map.put("UserStatus", "S");
        }else{
            map.put("UserStatus", "");
        }
        map.put("PageNo", pageCount + "");
        map.put("PrimaryUser","true");
        Const.callPostApi(context, "User/GetFullUserList", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Const.dismissProgress();
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
                                    maps.add(hm);
                                    if (i==0)
                                    {
//                                        Log.e("response :",maps.get(0).toString());
                                    }
                                }
                            }
                            if (pageCount == 1) {
                                adp = new ResultAdp(context, maps, isChange);
                                rvList.setAdapter(adp);
                            }
                        llHorizontal.setVisibility(View.VISIBLE);
                        adp.notifyDataSetChanged();

                    } else {
                        adp = null;
                        rvList.setAdapter(null);
                        llHorizontal.setVisibility(View.GONE);
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

//    private void DeleteUser(final String UserID) {
//        Const.showProgress(context);
//        Map<String, String> map = new HashMap<>();
//        map.put("UserID",  Const.notNullString(UserID,""));
//        Const.callPostApi(context, Const.BaseUrl + "User/DeleteUser", map, new VolleyCallback() {
//            @Override
//            public void onSuccessResponse(String result) {
//                Const.dismissProgress();
//                try {
//                    JSONObject object = new JSONObject(result);
//                    if (object.optString("Status").equalsIgnoreCase("1")) {
//                        String ob = new JSONObject(result).optString("Message");
//                        adp.notifyDataSetChanged();
//                        Const.showErrorDialog(context, Const.notNullString(ob, "User delete successfully."));
//                    } else {
//                        String ob = new JSONObject(result).optString("Message");
//                        Const.showErrorDialog(context, Const.notNullString(ob, "Something want wrong please try again later"));
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailerResponse(String error) {
//                Const.dismissProgress();
//                Runnable runnable = new Runnable() {
//                    @Override
//                    public void run() {
//                        DeleteUser(UserID);
//                    }
//                };
//                Const.showErrorApiDialog(context, runnable);
//            }
//        });
//    }

    public void DeleteUser(final String UserID){
        Const.showProgress(this);
        new RestClient(context).getInstance().get().DeleteUser(UserID).enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
//                Debug.e(TAG, new Gson().toJson(response.body()));
                Const.dismissProgress();
                if (response.body() != null) {
                    if (response.body().Status.equals("1")) {
                        adp.notifyDataSetChanged();
                        Const.showErrorDialog(context, Const.notNullString(response.body().Message, "User delete successfully."));
                    } else {
                        Const.showErrorDialog(context, response.body().Message);
                    }
                } else {
                    Toast.makeText(context, response.body().Message, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {
                //  Toast.makeText(context, context.getResources().getString(R.string.SOMETHING_WENT_WRONG), Toast.LENGTH_SHORT).show();
//                Debug.e(TAG, t.getLocalizedMessage());
                Const.dismissProgress();
            }
        });
    }

    private void DownloadExcel() {
        Const.showProgress(context);
       // List<String> RefNo = new ArrayList<>(hmSelected.values());
        final Map<String, String> map = new HashMap<>();
        map.put("CountryName", "");
        if (Pref.getStringValue(context, Const.Type, "").equalsIgnoreCase("User Name")) {
            map.put("UserName", Pref.getStringValue(context, Const.Search, ""));
        } else if(Pref.getStringValue(context, Const.Type, "").equalsIgnoreCase("Company Name")){
            map.put("CompanyName", Pref.getStringValue(context, Const.Search, ""));
        } else if(Pref.getStringValue(context, Const.Type, "").equalsIgnoreCase("Customer Name")) {
            map.put("UserFullName", Pref.getStringValue(context, Const.Search, ""));
        }else{
            map.put("UserName", "");
            map.put("CompanyName", "");
            map.put("UserFullName", "");
        }
        map.put("UserFullName", "");
        if (Pref.getStringValue(context, Const.User_Type, "").equalsIgnoreCase("Admin")) {
            map.put("UserType", "1");
        } else if(Pref.getStringValue(context, Const.User_Type, "").equalsIgnoreCase("Employee")){
            map.put("UserType", "2");
        } else if(Pref.getStringValue(context, Const.User_Type, "").equalsIgnoreCase("Registered")) {
            map.put("UserType", "3");
        }else{
            map.put("UserType", "");
        }
        if (Pref.getStringValue(context, Const.User_Status, "").equalsIgnoreCase("Active")) {
            map.put("UserStatus", "A");
        } else if(Pref.getStringValue(context, Const.User_Status, "").equalsIgnoreCase("In Active")){
            map.put("UserStatus", "I");
        } else if(Pref.getStringValue(context, Const.User_Status, "").equalsIgnoreCase("Suspended")) {
            map.put("UserStatus", "S");
        }else{
            map.put("UserStatus", "");
        }
        map.put("PageNo", "");
        map.put("FormName", "Manage User");
        map.put("ActivityType", "Excel Export");
        map.put("PrimaryUser","true");

        Const.callPostApi(context, "User/DownloadUser", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result1) {
             //   Const.dismissProgress();
                try {
                    String result = result1.replaceAll("^[\"']+|[\"']+$", "");
                    if (!result.equalsIgnoreCase("No data found.") && (!result.startsWith("Something Went wrong"))){
                        Date date = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy") ;

                        Random objGenerator = new Random();
                        for (int iCount = 0; iCount< 1; iCount++){
                            downloadNumber = String.valueOf(objGenerator.nextInt(1000));
                            System.out.println("Random No : " + downloadNumber);
                        }
                        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "User " + dateFormat.format(date) + "-" +  downloadNumber + ".xlsx");
                        registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(result.toString()));
                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                        request.setTitle("User " + dateFormat.format(date) + "-" + downloadNumber);
                        request.allowScanningByMediaScanner();
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "User " + dateFormat.format(date) + "-" + downloadNumber + ".xlsx");
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
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy") ;
            if (downID == id) {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "User " + dateFormat.format(date) + "-" + downloadNumber + ".xlsx");
                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.setType("application/vnd.ms-excel");
                Uri uri = FileProvider.getUriForFile(ManageUserActivity.this, BuildConfig.APPLICATION_ID + ".provider",file);
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
        Const.showProgress(context);
        //   List<String> RefNo = new ArrayList<>(hmSelected.values());
        final Map<String, String> map = new HashMap<>();
        map.put("CountryName", "");
        if (Pref.getStringValue(context, Const.Type, "").equalsIgnoreCase("User Name")) {
            map.put("UserName", Pref.getStringValue(context, Const.Search, ""));
        } else if(Pref.getStringValue(context, Const.Type, "").equalsIgnoreCase("Company Name")){
            map.put("CompanyName", Pref.getStringValue(context, Const.Search, ""));
        } else if(Pref.getStringValue(context, Const.Type, "").equalsIgnoreCase("Customer Name")) {
            map.put("UserFullName", Pref.getStringValue(context, Const.Search, ""));
        }else{
            map.put("UserName", "");
            map.put("CompanyName", "");
            map.put("UserFullName", "");
        }
        map.put("UserFullName", "");
        if (Pref.getStringValue(context, Const.User_Type, "").equalsIgnoreCase("Admin")) {
            map.put("UserType", "1");
        } else if(Pref.getStringValue(context, Const.User_Type, "").equalsIgnoreCase("Employee")){
            map.put("UserType", "2");
        } else if(Pref.getStringValue(context, Const.User_Type, "").equalsIgnoreCase("Registered")) {
            map.put("UserType", "3");
        }else{
            map.put("UserType", "");
        }
        if (Pref.getStringValue(context, Const.User_Status, "").equalsIgnoreCase("Active")) {
            map.put("UserStatus", "A");
        } else if(Pref.getStringValue(context, Const.User_Status, "").equalsIgnoreCase("In Active")){
            map.put("UserStatus", "I");
        } else if(Pref.getStringValue(context, Const.User_Status, "").equalsIgnoreCase("Suspended")) {
            map.put("UserStatus", "S");
        }else{
            map.put("UserStatus", "");
        }
        map.put("PageNo", "");
        map.put("FormName", "Manage User");
        map.put("ActivityType", "Excel Export");
        map.put("PrimaryUser","true");

        Const.callPostApi(context, "User/DownloadUser", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result1) {
                //  Const.dismissProgress();
                try {
                    String result = result1.replaceAll("^[\"']+|[\"']+$", "");
                    if (!result.equalsIgnoreCase("No data found.") && (!result.startsWith("Something Went wrong"))){
                        Date date = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy") ;

                        Random objGenerator = new Random();
                        for (int iCount = 0; iCount< 1; iCount++){
                            shareNumber = String.valueOf(objGenerator.nextInt(1000));
                            System.out.println("Random No : " + shareNumber);
                        }
                        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "User " + dateFormat.format(date) + "-" +  shareNumber + ".xlsx");
                        registerReceiver(onShareComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                        shareManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(result.toString()));
                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                        request.setTitle("User " + dateFormat.format(date) + "-" + shareNumber);
                        request.allowScanningByMediaScanner();
                        // request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "User " + dateFormat.format(date) + "-" + shareNumber + ".xlsx");
                        request.setMimeType(MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                                MimeTypeMap.getFileExtensionFromUrl(result.toString())));
                        shareID = shareManager.enqueue(request);

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

    private BroadcastReceiver onShareComplete = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onReceive(Context context, Intent intent) {
            Const.dismissProgress();
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy") ;
            if (shareID == id) {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "User " + dateFormat.format(date) + "-" + shareNumber + ".xlsx");
                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.setType("application/vnd.ms-excel");
                Uri uri = FileProvider.getUriForFile(ManageUserActivity.this, BuildConfig.APPLICATION_ID + ".provider",file);
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

    private void openSelection(final EditText editText, ArrayList<String> array, final String title) {
        final Dialog dialog = new Dialog(context, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dialog.getWindow().setBackgroundDrawable(setBackgoundBorder(0, 20, Color.WHITE, Color.WHITE));
        dialog.setContentView(R.layout.layout_selection_dialog);

        LinearLayout llNoData = dialog.findViewById(R.id.llNoData);
        TextView tvNoData = dialog.findViewById(R.id.tvNoData);
        Button btnClear = dialog.findViewById(R.id.btnClear);
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        final EditText etSearch = dialog.findViewById(R.id.etSearch);
        final ListView listView = dialog.findViewById(R.id.listView);
        etSearch.setVisibility(View.GONE);
        tvTitle.setText(title);
        if (array.size() != 0) {
            adapter = new ArrayAdapter<String>(context, R.layout.layout_spinner_item, array);
            listView.setAdapter(adapter);
        } else {
            adapter = new ArrayAdapter<String>(this, R.layout.layout_spinner_item, new ArrayList<String>());
            llNoData.setVisibility(View.VISIBLE);
            String[] name = title.split(" ");
            tvNoData.setText(name[1] + " Not Available");
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editText.setText(listView.getItemAtPosition(i).toString());
                dialog.dismiss();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
                dialog.dismiss();
            }
        });

        etSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (etSearch.getCompoundDrawables()[2] != null) {
                        if (event.getRawX() >= (etSearch.getRight() - etSearch.getCompoundDrawables()[2].getBounds().width())) {
                            etSearch.setText("");
                            etSearch.setCompoundDrawables(null, null, null, null);
                            return true;
                        }
                    }
                }
                return false;
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equalsIgnoreCase("")) {
                    Drawable wrappedDrawable = DrawableCompat.wrap(AppCompatResources.getDrawable(context, R.drawable.ic_close));
                    DrawableCompat.setTint(wrappedDrawable, Color.BLACK);
                    etSearch.setCompoundDrawablesWithIntrinsicBounds(null, null, wrappedDrawable, null);
                } else {
                    etSearch.setCompoundDrawables(null, null, null, null);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                adapter.getFilter().filter(editable.toString());
            }
        });

        dialog.show();
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
            View view = LayoutInflater.from(context).inflate(R.layout.layout_manage_user_view, parent, false);
            return new ResultAdp.GridViewHolder(view);
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            final HashMap<String, String> map = arraylist.get(position);

            if (holder instanceof ResultAdp.GridViewHolder) {
                final ResultAdp.GridViewHolder h = (ResultAdp.GridViewHolder) holder;

                h.tvSrNo.setText(Const.notNullString(map.get("isr")+".", ""));
                h.tvCreateDate.setText(Const.notNullString(map.get("screateddate"), ""));
                h.tvCustName.setText(Const.notNullString(map.get("sfullname"), ""));
                h.tvUserName.setText(Const.notNullString(map.get("susername"), ""));
                h.tvCompName.setText(Const.notNullString(map.get("scompname"), ""));
                h.tvAssist1.setText(Const.notNullString(map.get("assistby1"), ""));
                h.tvAssist2.setText(Const.notNullString(map.get("assistby2"), ""));
                h.tvUserType.setText(Const.notNullString(map.get("susertype"), ""));
                h.tvAcSuspended.setText(Const.notNullString(map.get("suspended"), ""));
                h.tvMessageName.setText(Const.notNullString(map.get("messagename"), ""));
                if(map.get("bisactive").equalsIgnoreCase("true")){
                    h.ivActive.setVisibility(View.VISIBLE);
                }else{
                    h.ivActive.setVisibility(View.GONE);
                }

                if (Const.notNullString(map.get("isprimaryuser"),"").equals("true")) {
                    h.tvPrimaryUser.setBackground(getResources().getDrawable(R.drawable.shape_yes));
                    h.tvPrimaryUser.setText("YES");
                } else if (Const.notNullString(map.get("isprimaryuser"),"").equals("false")) {
                    h.tvPrimaryUser.setBackground(getResources().getDrawable(R.drawable.shape_no));
                    h.tvPrimaryUser.setText("NO");
                }

                if (Const.notNullString(map.get("subusercount"),"").equals("true")) {
                    h.tvSubUser.setBackground(getResources().getDrawable(R.drawable.shape_yes));
                    h.tvSubUser.setText("YES");
                } else if (Const.notNullString(map.get("subusercount"),"").equals("false")) {
                    h.tvSubUser.setBackground(getResources().getDrawable(R.drawable.shape_no));
                    h.tvSubUser.setText("NO");
                }

                h.tvActiveDate.setText(Const.notNullString(map.get("smodifieddate"), ""));


                if (Const.notNullString(map.get("deleteuser"),"").equals("true"))
                {
                    h.ivDelete.setVisibility(View.VISIBLE);
                }
                else if(Const.notNullString(map.get("deleteuser"),"").equals("false"))
                {
                    h.ivDelete.setVisibility(View.INVISIBLE);
                }

                h.ivUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog dialog = new Dialog(context);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.setContentView(R.layout.layout_update_dialog);

                        TextView tvMessage = (TextView) dialog.findViewById(R.id.tvMessage);
                        Button btnYes = (Button) dialog.findViewById(R.id.btnYes);
                        Button btnNo = (Button) dialog.findViewById(R.id.btnNo);

                        tvMessage.setText("Are you sure you want to update?");
                        btnYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (isNetworkAvailable()) {
                                    startActivity(new Intent(context, UpdateUserActivity.class).putExtra("map", map));
                                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                    adp.notifyDataSetChanged();
                                    dialog.dismiss();
                                }
                            }
                        });
                        btnNo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (isNetworkAvailable()) {
                                    dialog.dismiss();
                                }
                            }
                        });
                        dialog.show();
                    }
                });

                h.ivDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog dialog = new Dialog(context);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.setContentView(R.layout.layout_update_dialog);

                        TextView tvMessage = (TextView) dialog.findViewById(R.id.tvMessage);
                        Button btnYes = (Button) dialog.findViewById(R.id.btnYes);
                        Button btnNo = (Button) dialog.findViewById(R.id.btnNo);

                        tvMessage.setText("Are you sure you want to delete?");
                        btnYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (isNetworkAvailable()) {
                                    DeleteUser(map.get("iuserid"));
                                    maps.remove(arraylist.get(position));
                                    adp.notifyDataSetChanged();
                                    dialog.dismiss();
                                }
                            }
                        });
                        btnNo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (isNetworkAvailable()) {
                                    dialog.dismiss();
                                }
                            }
                        });
                        dialog.show();
                    }
                });

            }
        }

        @Override
        public int getItemCount() {
            return arraylist.size();
        }

        private class GridViewHolder extends RecyclerView.ViewHolder {

            TextView tvSrNo, tvCreateDate, tvCustName, tvUserName, tvCompName, tvAssist1, tvAssist2, tvUserType, tvAcSuspended, tvActiveDate, tvMessageName;
            TextView tvPrimaryUser,tvSubUser;
            LinearLayout llMain;
            ImageView ivActive,ivUpdate,ivDelete;

            private GridViewHolder(@NonNull View v) {
                super(v);
                tvSrNo = v.findViewById(R.id.tvSrNo);
                tvCreateDate =  v.findViewById(R.id.tvCreateDate);
                tvCustName =  v.findViewById(R.id.tvCustName);
                tvUserName =  v.findViewById(R.id.tvUserName);
                tvCompName =  v.findViewById(R.id.tvCompName);
                tvAssist1 =  v.findViewById(R.id.tvAssist1);
                tvAssist2 =  v.findViewById(R.id.tvAssist2);
                tvUserType =  v.findViewById(R.id.tvUserType);
                tvAcSuspended =  v.findViewById(R.id.tvAcSuspended);
                ivActive =  v.findViewById(R.id.ivActive);
                tvActiveDate =  v.findViewById(R.id.tvActiveDate);
                tvPrimaryUser = v.findViewById(R.id.tvPrimaryUser);
                tvSubUser = v.findViewById(R.id.tvSubUser);
                tvMessageName = v.findViewById(R.id.tvMessageName);

                ivUpdate =  v.findViewById(R.id.ivUpdate);
                ivDelete =  v.findViewById(R.id.ivDelete);

                llMain =  v.findViewById(R.id.llMain);
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
                case R.id.etUserType:
                    openSelection(etUserType, arrayUserType, "Select User Type");
                    break;
                case R.id.etActive:
                    openSelection(etActive, arrayActive, "Select Status");
                    break;
                case R.id.etType:
                    openSelection(etType, arrayType, "Select Type");
                    break;
                case R.id.llSearch:
                    Menu.close(true);
                    Pref.setStringValue(context, Const.User_Type, etUserType.getText().toString());
                    Pref.setStringValue(context, Const.Type, etType.getText().toString().trim());
                    Pref.setStringValue(context, Const.User_Status, etActive.getText().toString());
                    Pref.setStringValue(context, Const.Search, etSearch.getText().toString().trim());
                    maps = new ArrayList<>();
                    pageCount = 1;
                    totalSize = 0;
                    Const.hideSoftKeyboard(ManageUserActivity.this);
                    // Const.resetParameters(context);
                    getSearchResult();
                    break;
                case R.id.llPlus:
                   d.show();
                    break;
                case R.id.Menu_Back:
                    d.dismiss();
                    break;
                case R.id.Menu_AddEmp:
                    d.dismiss();
                    startActivity(new Intent(context, AddEmployeeActivity.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
                    etUserType.setText("");
                    etActive.setText("");
                    etType.setText("");
                    etSearch.setText("");
                    Menu.close(true);
                    Pref.removeValue(context, Const.User_Type);
                    Pref.removeValue(context, Const.Type);
                    Pref.removeValue(context, Const.User_Status);
                    Pref.removeValue(context, Const.Search);
                    maps = new ArrayList<>();
                    pageCount = 1;
                    totalSize = 0;
                    Const.hideSoftKeyboard(ManageUserActivity.this);
                    getSearchResult();
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

    public Boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) ManageUserActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        getSearchResult();
//    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

package com.dnk.shairugems;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dnk.shairugems.Class.LoginData;
import com.dnk.shairugems.Utils.Const;
import com.dnk.shairugems.Utils.Pref;
import com.dnk.shairugems.Utils.RestClient;
import com.dnk.shairugems.Volly.VolleyCallback;
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

public class UserMgtActivity extends BaseDrawerActivity implements View.OnClickListener{

    ImageView imgMenu, imgSearch, imgReset , imgDown;
    RelativeLayout rlActive,rlInactive;
    LinearLayout llSearchTop,llSearch, llClear;
    EditText etSearch;

    boolean isChange = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount, pageCount = 1, totalSize = 0;
    ArrayList<HashMap<String, String>> maps = new ArrayList<>();

    RecyclerView rvList;
    ResultAdp adp;
    LinearLayoutManager lm;

    RelativeLayout rlMain;
    LinearLayout llHorizontal;

    private Dialog d;
    ImageView imgPlus, img_Share, img_Download;
    TextView tv_adduser,tv_Share, tv_Download;
    LinearLayout Menu_AddEmp,Menu_Share,Menu_Download,Menu_Wishlist,Menu_Compare,Menu_Copy,Menu_Stock,Menu_Clear,Menu_Back;
    View div_AddEmp, div_Share,div_Download,div_Wishlist,div_Compare,div_Copy,div_Stock;

    long downID = 0, shareID = 0;
    String downloadNumber = "", shareNumber = "";
    DownloadManager downloadManager, shareManager;

    FloatingActionButton llPlus;

    boolean flag_Active=false, flag_Inactive=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_user_mgt, frameLayout);

        initview();

    }

    private void initview() {

        rlMain = findViewById(R.id.rlMain);
        imgMenu = findViewById(R.id.imgMenu);
        imgDown = findViewById(R.id.imgDown);
        etSearch = findViewById(R.id.etSearch);
        imgReset = findViewById(R.id.imgReset);
        imgSearch = findViewById(R.id.imgSearch);
        llSearch = findViewById(R.id.llSearch);
        llClear = findViewById(R.id.llClear);
        llSearchTop = findViewById(R.id.llSearchTop);
        llSearchTop.setVisibility(View.GONE);

        rlActive=findViewById(R.id.rlActive);
        rlInactive=findViewById(R.id.rlInactive);

        llHorizontal = findViewById(R.id.llHorizontal);
        rvList=findViewById(R.id.rvList);

        lm = new LinearLayoutManager(context);
        rvList.setLayoutManager(lm);

        llPlus=findViewById(R.id.llPlus);
        llPlus.setOnClickListener(this);

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

        tv_adduser=view.findViewById(R.id.tv_addEmployee);
        tv_adduser.setText("Add User");
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

        rlMain.setOnClickListener(this);
        imgMenu.setOnClickListener(this);
        imgDown.setOnClickListener(this);
        etSearch.setOnClickListener(this);
        imgSearch.setOnClickListener(this);
        llSearch.setOnClickListener(this);
        llClear.setOnClickListener(this);
        imgReset.setOnClickListener(this);

        rlActive.setOnClickListener(this);
        rlInactive.setOnClickListener(this);

        Menu_AddEmp.setOnClickListener(this);
        Menu_Share.setOnClickListener(this);
        Menu_Download.setOnClickListener(this);
        Menu_Wishlist.setOnClickListener(this);
        Menu_Compare.setOnClickListener(this);
        Menu_Copy.setOnClickListener(this);
        Menu_Stock.setOnClickListener(this);
        Menu_Clear.setOnClickListener(this);
        Menu_Back.setOnClickListener(this);

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
                                get_All_user("");
                            }
                        }
                    }
                }
            }
        });

        if (isNetworkAvailable()) {
            get_All_user("");
        } else {
            Toast.makeText(UserMgtActivity.this, "Internet is not available", Toast.LENGTH_LONG).show();
        }

    }

    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.rlActive:
                if (!flag_Active) {
                    if (flag_Inactive)
                    {
                        flag_Inactive=false;
                        rlInactive.setBackgroundResource(R.drawable.shape_inactive);
                    }
                    flag_Active=true;
                    rlActive.setBackgroundResource(R.drawable.shape_active);
                }
                else {
                    flag_Active=false;
                    rlActive.setBackgroundResource(R.drawable.shape_inactive);
                }

                break;
            case R.id.rlInactive:
                if (!flag_Inactive) {
                    if (flag_Active)
                    {
                        flag_Active=false;
                        rlActive.setBackgroundResource(R.drawable.shape_inactive);
                    }
                    flag_Inactive=true;
                    rlInactive.setBackgroundResource(R.drawable.shape_active);
                }
                else {
                    flag_Inactive=false;
                    rlInactive.setBackgroundResource(R.drawable.shape_inactive);
                }
                break;
            case R.id.imgMenu:
                drawerLayout.openDrawer(Gravity.START);
                break;
            case R.id.imgDown:
                if (!Pref.getStringValue(context, Const.IsPrimary, "").equalsIgnoreCase("true")) {
                    if (llSearchTop.getVisibility() == View.VISIBLE) {
                        imgDown.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_new));
                        collapse(llSearchTop);
                    } else {
                        imgDown.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_up));
                        expand(llSearchTop);
                    }
                }
                break;
            case R.id.imgSearch:
                if (llSearchTop.getVisibility() == View.VISIBLE) {
                    imgDown.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_new));
                    collapse(llSearchTop);
                }
                maps = new ArrayList<>();
                pageCount = 1;
                totalSize = 0;
                Const.hideSoftKeyboard(UserMgtActivity.this);
                // Const.resetParameters(context);
                get_All_user("");
                break;

            case R.id.imgReset:
                flag_Active=false;
                rlActive.setBackgroundResource(R.drawable.shape_inactive);
                flag_Inactive=false;
                rlInactive.setBackgroundResource(R.drawable.shape_inactive);
                etSearch.setText("");
                if (llSearchTop.getVisibility() == View.VISIBLE) {
                    imgDown.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_new));
                    collapse(llSearchTop);
                }
                maps = new ArrayList<>();
                pageCount = 1;
                totalSize = 0;
                Const.hideSoftKeyboard(UserMgtActivity.this);
                // Const.resetParameters(context);
                get_All_user("");
                break;
            case R.id.llPlus:
                d.show();
                break;
            case R.id.Menu_Back:
                d.dismiss();
                break;
            case R.id.Menu_AddEmp:
                d.dismiss();
                Const.isAdd=true;
                startActivity(new Intent(context, AddUserActivity.class));
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
        }

    }

    private void get_All_user(final String search_string) {

        Log.e("Active ",flag_Active+"");
        Log.e("Inactive ",flag_Inactive+"");

        Const.showProgress(context);
        final Map<String, String> map = new HashMap<>();
        map.put("iPgNo", pageCount+"");
        map.put("iPgSize", "50");
        map.put("Search",  Const.notNullString(etSearch.getText().toString(),""));
        map.put("Active", flag_Active+"");
        map.put("InActive", flag_Inactive+"");

        Const.callPostApi(context, "User/Get_UserMgt", map, new VolleyCallback() {
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
                                if (i!=0) {
                                    maps.add(hm);
                                }
                            }
                        }

                      /*  for (int i=0;i<maps.size();i++)
                        {
                            Log.e("record "+i,maps.get(i).toString());
                        }*/

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
                        get_All_user(search_string);
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });

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


    private void DownloadExcel() {
        Const.showProgress(context);
        final Map<String, String> map = new HashMap<>();
//        map.put("iUserId", "");
        map.put("iPgNo", "1");
        map.put("iPgSize", "50");
//        map.put("sOrderBy", "");
        map.put("Search",  Const.notNullString(etSearch.getText().toString(),""));
//        map.put("Active", "");
//        map.put("InActive", "");

        Const.callPostApi(context, "User/Excel_UserMgt", map, new VolleyCallback() {
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
                        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "UserMgt " + dateFormat.format(date) + "-" +  downloadNumber + ".xlsx");
                        registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(result.toString()));
                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                        request.setTitle("UserMgt " + dateFormat.format(date) + "-" + downloadNumber);
                        request.allowScanningByMediaScanner();
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "UserMgt " + dateFormat.format(date) + "-" + downloadNumber + ".xlsx");
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

    private void ShareExcel() {
        Const.showProgress(context);
        final Map<String, String> map = new HashMap<>();
//        map.put("iUserId", "");
        map.put("iPgNo", "1");
        map.put("iPgSize", "50");
//        map.put("sOrderBy", "");
        map.put("Search",  Const.notNullString(etSearch.getText().toString(),""));
//        map.put("Active", "");
//        map.put("InActive", "");

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
                        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "UserMgt" + dateFormat.format(date) + "-" +  shareNumber + ".xlsx");
                        registerReceiver(onShareComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                        shareManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(result.toString()));
                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                        request.setTitle("UserMgt " + dateFormat.format(date) + "-" + shareNumber);
                        request.allowScanningByMediaScanner();
                        // request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "UserMgt " + dateFormat.format(date) + "-" + shareNumber + ".xlsx");
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

    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onReceive(Context context, Intent intent) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy") ;
            if (downID == id) {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "UserMgt " + dateFormat.format(date) + "-" + downloadNumber + ".xlsx");
                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.setType("application/vnd.ms-excel");
                Uri uri = FileProvider.getUriForFile(UserMgtActivity.this, BuildConfig.APPLICATION_ID + ".provider",file);
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

    private BroadcastReceiver onShareComplete = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onReceive(Context context, Intent intent) {
            Const.dismissProgress();
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy") ;
            if (shareID == id) {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "UserMgt " + dateFormat.format(date) + "-" + shareNumber + ".xlsx");
                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.setType("application/vnd.ms-excel");
                Uri uri = FileProvider.getUriForFile(UserMgtActivity.this, BuildConfig.APPLICATION_ID + ".provider",file);
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


    private void DeleteUser(final String UserID) {
        Const.showProgress(this);
        new RestClient(context).getInstance().get().DeleteUser(UserID).enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
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
//              Debug.e(TAG, t.getLocalizedMessage());
                Const.dismissProgress();
            }
        });
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
            View view = LayoutInflater.from(context).inflate(R.layout.layout_user_mgt_view, parent, false);
            return new UserMgtActivity.ResultAdp.GridViewHolder(view);
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            final HashMap<String, String> map = arraylist.get(position);

            if (holder instanceof GridViewHolder) {
                final GridViewHolder h = (GridViewHolder) holder;

                h.tvSrNo.setText(Const.notNullString(map.get("isr")+".", ""));
                h.tvCreateDate.setText(Const.notNullString(map.get("createddate"), ""));

                h.tvUserName.setText(Const.notNullString(map.get("susername"), ""));
                h.tvCompName.setText(Const.notNullString(map.get("scompname"), ""));
                h.tvCustName.setText(Const.notNullString(map.get("custname"), ""));

                h.tvEmailId.setText(Const.notNullString(map.get("scompemail"), ""));
                h.tvMobileNo.setText(Const.notNullString(map.get("scompmobile"), ""));

                if(map.get("isactive").equalsIgnoreCase("true")){
                    h.tvActive.setBackground(getResources().getDrawable(R.drawable.shape_yes));
                    h.tvActive.setText("YES");
                }else{
                    h.tvActive.setBackground(getResources().getDrawable(R.drawable.shape_no));
                    h.tvActive.setText("NO");
                }

                h.tvSearchStock.setText(Const.notNullString(map.get("searchstock"), ""));
                if (h.tvSearchStock.getText().toString().equals("true"))
                {
                    h.tvSearchStock.setBackground(getResources().getDrawable(R.drawable.shape_yes));
                    h.tvSearchStock.setText("YES");
                }
                else if (h.tvSearchStock.getText().toString().equals("false"))
                {
                    h.tvSearchStock.setBackground(null);
                    h.tvSearchStock.setText("");
                }


                h.tvPlaceOrder.setText(Const.notNullString(map.get("placeorder"), ""));
                if (h.tvPlaceOrder.getText().toString().equals("true"))
                {
                    h.tvPlaceOrder.setBackground(getResources().getDrawable(R.drawable.shape_yes));
                    h.tvPlaceOrder.setText("YES");
                }
                else if (h.tvPlaceOrder.getText().toString().equals("false"))
                {
                    h.tvPlaceOrder.setBackground(null);
                    h.tvPlaceOrder.setText("");
                }

                h.tvOrderHistory.setText(Const.notNullString(map.get("orderhisrory"), ""));
                if (h.tvOrderHistory.getText().toString().equals("true"))
                {
                    h.tvOrderHistory.setBackground(getResources().getDrawable(R.drawable.shape_yes));
                    h.tvOrderHistory.setText("YES");
                }
                else if (h.tvOrderHistory.getText().toString().equals("false"))
                {
                    h.tvOrderHistory.setBackground(null);
                    h.tvOrderHistory.setText("");
                }

                h.tvMyCart.setText(Const.notNullString(map.get("mycart"), ""));
                if (h.tvMyCart.getText().toString().equals("true"))
                {
                    h.tvMyCart.setBackground(getResources().getDrawable(R.drawable.shape_yes));
                    h.tvMyCart.setText("YES");
                }
                else if (h.tvMyCart.getText().toString().equals("false"))
                {
                    h.tvMyCart.setBackground(null);
                    h.tvMyCart.setText("");
                }


                h.tvMyWishlist.setText(Const.notNullString(map.get("mywishlist"), ""));
                if (h.tvMyWishlist.getText().toString().equals("true"))
                {
                    h.tvMyWishlist.setBackground(getResources().getDrawable(R.drawable.shape_yes));
                    h.tvMyWishlist.setText("YES");
                }
                else if (h.tvMyWishlist.getText().toString().equals("false"))
                {
                    h.tvMyWishlist.setBackground(null);
                    h.tvMyWishlist.setText("");
                }


                h.tvQuickSearch.setText(Const.notNullString(map.get("quicksearch"), ""));
                if (h.tvQuickSearch.getText().toString().equals("true"))
                {
                    h.tvQuickSearch.setBackground(getResources().getDrawable(R.drawable.shape_yes));
                    h.tvQuickSearch.setText("YES");
                }
                else if (h.tvQuickSearch.getText().toString().equals("false"))
                {
                    h.tvQuickSearch.setBackground(null);
                    h.tvQuickSearch.setText("");
                }


                h.ivUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Const.isAdd=false;
                        Const.iUserId=Const.notNullString(map.get("iuserid"), "");
                        Const.cmpName=Const.notNullString(map.get("scompname"),"");
                        Const.uname=Const.notNullString(map.get("susername"),"");

                        startActivity(new Intent(context, AddUserActivity.class));
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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

            TextView tvSrNo, tvCreateDate, tvCustName, tvUserName, tvCompName,tvEmailId,tvMobileNo,tvActive;
            TextView tvSearchStock,tvPlaceOrder,tvOrderHistory,tvMyCart,tvMyWishlist,tvQuickSearch;
            LinearLayout llMain;
            ImageView ivUpdate,ivDelete;

            private GridViewHolder(@NonNull View v) {
                super(v);
                tvSrNo = v.findViewById(R.id.tvSrNo);
                tvCreateDate =  v.findViewById(R.id.tvCreateDate);
                tvCustName =  v.findViewById(R.id.tvCustName);
                tvUserName =  v.findViewById(R.id.tvUserName);
                tvCompName =  v.findViewById(R.id.tvCompName);
                tvEmailId =  v.findViewById(R.id.tvEmailId);
                tvMobileNo = v.findViewById(R.id.tvMobileNo);

                tvSearchStock =  v.findViewById(R.id.tvSearchStock);
                tvPlaceOrder =  v.findViewById(R.id.tvPlaceOrder);
                tvOrderHistory =  v.findViewById(R.id.tvOrderHistory);
                tvMyCart = v.findViewById(R.id.tvMyCart);
                tvMyWishlist = v.findViewById(R.id.tvMyWishlist);
                tvQuickSearch = v.findViewById(R.id.tvQuickSearch);

                tvActive = v.findViewById(R.id.tvActive);
                ivUpdate =  v.findViewById(R.id.ivUpdate);
                ivDelete =  v.findViewById(R.id.ivDelete);

                llMain =  v.findViewById(R.id.llMain);
            }
        }
    }

}

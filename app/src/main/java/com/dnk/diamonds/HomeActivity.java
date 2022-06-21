package com.dnk.shairugems;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dnk.shairugems.Adapter.AdapterDashboard;
import com.dnk.shairugems.Class.DashboardClass;
import com.dnk.shairugems.Utils.Const;
import com.dnk.shairugems.Utils.Pref;
import com.dnk.shairugems.Volly.VolleyCallback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HomeActivity extends BaseDrawerActivity {

    SwipeRefreshLayout pullToRefresh_dashboard;

    ImageView imgMenu, Notification, imgLogout, imgSingleSearch;
//    RelativeLayout rlNewArrival, rlCart, rlWishList, rlOrderHistory, rlTotalStock, rlOffer, rlPairSearch, rlRecentSearch, rlSaveSearch, rlRevisedPrice;
    RecyclerView rvList;
    LinearLayout llLastLogin;
//    TextView tvOrderTitle, tvTotalStock, tvNewArrival, tvOffer, tvOrderHistory, tvCart, tvWhishlist, tvRecentSearch, tvSaveSearch;

    RecyclerView rv_dashboard;
    AdapterDashboard adapterDashboard;
    List<DashboardClass> dashboardMenuList;
    AdapterDashboard.DashboardAdapterListener rvdashboardcallback;

    LinearLayout dash_bottom_layout;
    TextView tvLoginDate;
    EditText etSearch;
    List<HashMap<String, String>> arrayCount;
    SharedPreferences sp;
    FrameLayout overlay;
    Context context = HomeActivity.this;

    int flag_swipe=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.home_activity, frameLayout);

        initView();

        if(Pref.getStringValue(context,Const.IsLoginTime,"false").equalsIgnoreCase("true")){
            if (!Pref.getStringValue(context, Const.MessageId, "").equalsIgnoreCase("") &&
                    !Pref.getStringValue(context, Const.MessageShow, "").equalsIgnoreCase("false")) {
                Pref.setStringValue(context, Const.IsLoginTime, "false");
                get_Message_Data();
            }
        }

        overlay.setOnClickListener(clickListener);
        imgLogout.setOnClickListener(clickListener);
        Notification.setOnClickListener(clickListener);
        imgSingleSearch.setOnClickListener(clickListener);
        imgMenu.setOnClickListener(clickListener);
        rlNewArrival.setOnClickListener(clickListener);


//        rlCart.setOnClickListener(clickListener);
//        rlTotalStock.setOnClickListener(clickListener);
//        rlOffer.setOnClickListener(clickListener);
//        rlWishList.setOnClickListener(clickListener);


        rlPairSearch.setOnClickListener(clickListener);
        rlRecentSearch.setOnClickListener(clickListener);
        rlSaveSearch.setOnClickListener(clickListener);
        rlRevisedPrice.setOnClickListener(clickListener);
        rlOrderHistory.setOnClickListener(clickListener);

        swipe_refresh();

        etSearch.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!Const.isEmpty(etSearch)) {
                        Const.resetParameters(context);
                      //  if (etSearch.getText().toString().matches(".*[A-Za-z].*")) {
                            Pref.setStringValue(context, Const.stone_id, etSearch.getText().toString().replaceAll(" ",",").replaceAll("\n",",").replaceAll("\t",",").replaceAll("\r",",").replaceAll("  ",","));
                     //   }
                        Const.flag_searchResult=1;
                        startActivity(new Intent(context, SearchResultActivity.class));
                        overridePendingTransition(0, 0);
                    }
                    return true;
                }
                return false;
            }
        });

        if (isNetworkAvailable()) {
            flag_swipe=0;
            getImage();
            getHomeCount();
        } else {
            Const.DisplayNoInternet(context);
        }

        sp = PreferenceManager.getDefaultSharedPreferences(context);
        if (isNetworkAvailable()) {
            if (!sp.getBoolean("OverlayDisplayedHome", false)) {
                overlay.setVisibility(View.VISIBLE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("OverlayDisplayedHome", true);
                editor.apply();
            } else {
                overlay.setVisibility(View.GONE);
            }
        } else {
            Const.DisplayNoInternet(context);
        }
    }

    private void swipe_refresh() {
            pullToRefresh_dashboard.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    pullToRefresh_dashboard.setRefreshing(true);
                    refreshData();
                    pullToRefresh_dashboard.setRefreshing(false);
                }
            });

    }

    private void get_Account_Data() {
        Const.showProgress(context);
        Map<String, String> map = new HashMap<>();
        map.put("UserName", Pref.getStringValue(context, Const.loginName, ""));
        map.put("Password", Pref.getStringValue(context, Const.loginPassword, ""));
        Const.callPostApiLogin(context, "User/GetKeyAccountData", map, new VolleyCallback() {
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

                                Pref.setStringValue(context, Const.SearchStock, Const.notNullString(hm.get("searchstock"), "false"));
                                Pref.setStringValue(context, Const.PlaceOrder, Const.notNullString(hm.get("placeorder"), "false"));
                                Pref.setStringValue(context, Const.OrderHisrory, Const.notNullString(hm.get("orderhisrory"), "false"));
                                Pref.setStringValue(context, Const.MyCart, Const.notNullString(hm.get("mycart"), "false"));
                                Pref.setStringValue(context, Const.MyWishlist, Const.notNullString(hm.get("mywishlist"), "false"));
                                Pref.setStringValue(context, Const.QuickSearch, Const.notNullString(hm.get("quicksearch"), "false"));

                                Pref.setStringValue(context, Const.IsAssistByForAnyUser, Const.notNullString(hm.get("isassistbyforanyuser"), "false"));
                                Pref.setStringValue(context, Const.IsPrimary, Const.notNullString(hm.get("isprimary"), "false"));
                                Pref.setStringValue(context, Const.CompanyName, Const.notNullString(hm.get("scompname"), ""));

                                pullToRefresh_dashboard.setRefreshing(false);

                            }
                        }
                    }
                    else
                    {
                        Const.showErrorDialog(context, object.optString("Message"));
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
                        get_Account_Data();
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    private void get_Message_Data() {
        Map<String, String> map = new HashMap<>();
        map.put("Id", Pref.getStringValue(context,Const.MessageId,""));
        Const.callPostApiLogin(context, "User/Get_MessageMst", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Log.e("Message : ", result);
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

                                final Dialog dialog = new Dialog(context);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.setCancelable(false);
                                dialog.setContentView(R.layout.layout_error_dialog);
                                TextView tvMessage = (TextView) dialog.findViewById(R.id.tvMessage);
                                tvMessage.setText(Const.notNullString(hm.get("message"), "Something Want Wrong, Please try Again Later"));
                                Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
                                btnOk.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(Pref.getStringValue(context,Const.IsLogout,"false").equalsIgnoreCase("true")){
                                            Const.showErrorApiDialog(context, null);
                                        }
                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();
                            }
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailerResponse(String error) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                    }
                };
            }
        });
    }

    private void refreshData() {
        if (isNetworkAvailable()) {
            flag_swipe=1;
            get_Account_Data();
            getImage();
            getHomeCount();
        } else {
            Const.DisplayNoInternet(context);
        }
    }

    private void initView() {

        pullToRefresh_dashboard=findViewById(R.id.pullToRefresh_dashboard);
        pullToRefresh_dashboard.setRefreshing(false);

        llLastLogin = findViewById(R.id.llLastLogin);
        overlay = findViewById(R.id.overlay);
        imgMenu = findViewById(R.id.imgMenu);
        rlNewArrival = findViewById(R.id.rlNewArrival);
        rlOrderHistory = findViewById(R.id.rlOrderHistory);

        rlPairSearch = findViewById(R.id.rlPairSearch);
        rlRecentSearch = findViewById(R.id.rlRecentSearch);
        rlSaveSearch = findViewById(R.id.rlSaveSearch);
        tvLoginDate = findViewById(R.id.tvLoginDate);
        rlOfferHistory = findViewById(R.id.rlOfferHistory);
        rlRevisedPrice = findViewById(R.id.rlRevisedPrice);
        etSearch = findViewById(R.id.etSearch);
        rvList = findViewById(R.id.rvList);
        imgLogout = findViewById(R.id.imgLogout);
        Notification = findViewById(R.id.Notification);
        imgSingleSearch = findViewById(R.id.imgSingleSearch);

        rvList.setLayoutManager(new LinearLayoutManager(context));
        etSearch.setBackgroundDrawable(Const.setBackgoundBorder(1, 60, getResources().getColor(R.color.white), getResources().getColor(R.color.gray)));

        dash_bottom_layout=findViewById(R.id.dash_bottom_layout);
        dash_bottom_layout.setVisibility(View.GONE);

        rv_dashboard=findViewById(R.id.rv_dashboard);
        rv_dashboard.setLayoutManager(new GridLayoutManager(this, 2));


        dashboardMenuList=new ArrayList<DashboardClass>();

//        tvCart = findViewById(R.id.tvCart);
//        tvWhishlist = findViewById(R.id.tvWhishlist);
//        rlCart = findViewById(R.id.rlCart);
//        tvOrderHistory = findViewById(R.id.tvOrderHistory);
//
//        rlOffer = findViewById(R.id.rlOffer);
//        tvOffer = findViewById(R.id.tvOffer);
//        tvNewArrival = findViewById(R.id.tvNewArrival);
//        rlTotalStock = findViewById(R.id.rlTotalStock);
//        tvTotalStock = findViewById(R.id.tvTotalStock);
//        rlWishList = findViewById(R.id.rlWishList);
//        tvRecentSearch = findViewById(R.id.tvRecentSearch);
//        tvSaveSearch = findViewById(R.id.tvSaveSearch);
//        tvOrderTitle = findViewById(R.id.tvOrderTitle);

    }

    private void getImage() {
        final Map<String, String> map = new HashMap<>();
        Const.callPostApi(context, "Information/GetFutureInformations", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.optString("Status").equalsIgnoreCase("1")) {
                        if (object.optString("Data") != null && !object.optString("Data").equalsIgnoreCase("null")) {
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
                                    if(hm.get("filename") != null && !hm.get("filename").equalsIgnoreCase("")){
                                        fullImage("https://sunrisediamonds.com.hk:8122/InfoImages/" + hm.get("filename"));
                                    }
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
                        getImage();
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    private void fullImage(String url) {
        final Dialog dialog = new Dialog(HomeActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        LinearLayout layout = new LinearLayout(HomeActivity.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(params);
        layout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout layoutt = new LinearLayout(HomeActivity.this);
        LinearLayout.LayoutParams paramss = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutt.setOrientation(LinearLayout.VERTICAL);

        ImageView imgclose = new ImageView(HomeActivity.this);
        imgclose.setImageResource(R.drawable.ic_close);
        imgclose.setColorFilter(Color.argb(255, 255, 255, 255));

        ImageView img = new ImageView(HomeActivity.this);
        Picasso.with(HomeActivity.this)
                .load(url)
                .into(img);

        layout.addView(img);
        layoutt.addView(imgclose);

        layout.addView(layoutt);

        dialog.setContentView(layout);

        imgclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void getHomeCount() {

        if (flag_swipe==0) { Const.showProgress(context); }

        arrayCount = new ArrayList<>();
        final Map<String, String> map = new HashMap<>();
        Const.callPostApi(context, "Stock/GetDashboardCnt", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                List<DashboardClass> rawlist;
                rawlist=new ArrayList<DashboardClass>();

            //    Const.dismissProgress();
                try {
                    rawlist.clear();
                    dashboardMenuList.clear();
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
                                if (arrayCount.get(i).get("type").equalsIgnoreCase("Stock")) {
                                    if (Pref.getStringValue(context, Const.loginIsCust, "").equals("1")) {
                                            if (Pref.getStringValue(context, Const.SearchStock, "").equals("true")) {
                                                rawlist.add(new DashboardClass("Search Stock", String.format("%,.0f", Double.parseDouble(arrayCount.get(i).get("scnt"))), R.drawable.ic_total_stock));
                                            }
                                    }
                                    else{
                                        rawlist.add(new DashboardClass("Search Stock", String.format("%,.0f", Double.parseDouble(arrayCount.get(i).get("scnt"))), R.drawable.ic_total_stock));
                                    }
                                }
                                else if (arrayCount.get(i).get("type").equalsIgnoreCase("offer")) {
                                      rawlist.add(new DashboardClass("Offer Stone", String.format("%,.0f", Double.parseDouble(arrayCount.get(i).get("scnt"))), R.drawable.ic_offer));
                                }
                                else if (arrayCount.get(i).get("type").equalsIgnoreCase("NewArrival")) {
                                    rawlist.add(new DashboardClass("New Arrival",String.format("%,.0f",Double.parseDouble(arrayCount.get(i).get("scnt"))),R.drawable.ic_new_arrival));
                                }
                                else if (arrayCount.get(i).get("type").equalsIgnoreCase("MyOrder")) {
                                    if (Pref.getStringValue(context, Const.loginIsCust, "").equals("1")) {
                                        if (Pref.getStringValue(context, Const.OrderHisrory, "").equals("true")) {
                                            rawlist.add(new DashboardClass("Order History", String.format("%,.0f", Double.parseDouble(arrayCount.get(i).get("scnt"))), R.drawable.ic_order_history));
                                        }
                                    }
                                    else
                                    {
                                        rawlist.add(new DashboardClass("Order History", String.format("%,.0f", Double.parseDouble(arrayCount.get(i).get("scnt"))), R.drawable.ic_order_history));
                                    }
                                }
                                else if (arrayCount.get(i).get("type").equalsIgnoreCase("MyCart")) {
                                    if (Pref.getStringValue(context, Const.loginIsCust, "").equals("1")) {
                                        if (Pref.getStringValue(context, Const.MyCart, "").equals("true")) {
                                            rawlist.add(new DashboardClass("My Shopping Cart", String.format("%,.0f", Double.parseDouble(arrayCount.get(i).get("scnt"))), R.drawable.ic_cart_));
                                        }
                                    }
                                    else
                                    {
                                        rawlist.add(new DashboardClass("My Shopping Cart", String.format("%,.0f", Double.parseDouble(arrayCount.get(i).get("scnt"))), R.drawable.ic_cart_));
                                    }
                                }
                                else if (arrayCount.get(i).get("type").equalsIgnoreCase("RecentSearch")) {
                                    rawlist.add(new DashboardClass("Recent Search",String.format("%,.0f",Double.parseDouble(arrayCount.get(i).get("scnt"))),R.drawable.ic_recent_search));
                                }

                                else if (arrayCount.get(i).get("type").equalsIgnoreCase("WishList")) {
                                    if (Pref.getStringValue(context, Const.loginIsCust, "").equals("1")) {
                                        if (Pref.getStringValue(context, Const.MyWishlist, "").equals("true")) {
                                            rawlist.add(new DashboardClass("WishList", String.format("%,.0f", Double.parseDouble(arrayCount.get(i).get("scnt"))), R.drawable.ic_wishlist));
                                        }
                                    } else{
                                        rawlist.add(new DashboardClass("WishList", String.format("%,.0f", Double.parseDouble(arrayCount.get(i).get("scnt"))), R.drawable.ic_wishlist));
                                    }
                                }
                                else if (arrayCount.get(i).get("type").equalsIgnoreCase("SaveSearch")) {
                                    rawlist.add(new DashboardClass("Save Search",String.format("%,.0f",Double.parseDouble(arrayCount.get(i).get("scnt"))),R.drawable.ic_diamond));
                                }

                            }

//                            rawlist.add(new DashboardClass("Supplier Order","55",R.drawable.ic_supplier_order));


                        }


                        if (rawlist.size()>0)
                        {
                            Arrange_ordering(rawlist);

                            manage_dashboard_callback();
                            adapterDashboard=new AdapterDashboard(HomeActivity.this,dashboardMenuList,rvdashboardcallback);
                            rv_dashboard.setAdapter(adapterDashboard);
                            dash_bottom_layout.setVisibility(View.VISIBLE);

                        }

                        getLastLogin();
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
                        if (flag_swipe==1) { flag_swipe=1; }
                        else if(flag_swipe==0) { flag_swipe=0; }

                        getHomeCount();
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    private void Arrange_ordering(List<DashboardClass> rawlist) {
        dashboardMenuList.clear();
        for (DashboardClass obj:rawlist) {
             if (obj.getMainData().equals("Search Stock"))
             {
                 dashboardMenuList.add(new DashboardClass(obj.getMainData(),obj.getSubData(),obj.getImg()));
             }
        }

        for (DashboardClass obj:rawlist) {
            if (obj.getMainData().equals("Offer Stone"))
            {
                dashboardMenuList.add(new DashboardClass(obj.getMainData(),obj.getSubData(),obj.getImg()));
            }
        }

        for (DashboardClass obj:rawlist) {
            if (obj.getMainData().equals("New Arrival"))
            {
                dashboardMenuList.add(new DashboardClass(obj.getMainData(),obj.getSubData(),obj.getImg()));
            }
        }

        for (DashboardClass obj:rawlist) {
            if (obj.getMainData().equals("Order History"))
            {
                dashboardMenuList.add(new DashboardClass(obj.getMainData(),obj.getSubData(),obj.getImg()));
            }
        }





        for (DashboardClass obj:rawlist) {
            if (obj.getMainData().equals("My Shopping Cart"))
            {
                dashboardMenuList.add(new DashboardClass(obj.getMainData(),obj.getSubData(),obj.getImg()));
            }
        }

        for (DashboardClass obj:rawlist) {
            if (obj.getMainData().equals("Recent Search"))
            {
                dashboardMenuList.add(new DashboardClass(obj.getMainData(),obj.getSubData(),obj.getImg()));
            }
        }

        for (DashboardClass obj:rawlist) {
            if (obj.getMainData().equals("WishList"))
            {
                dashboardMenuList.add(new DashboardClass(obj.getMainData(),obj.getSubData(),obj.getImg()));
            }
        }

        for (DashboardClass obj:rawlist) {
            if (obj.getMainData().equals("Save Search"))
            {
                dashboardMenuList.add(new DashboardClass(obj.getMainData(),obj.getSubData(),obj.getImg()));
            }
        }

//        for (DashboardClass obj:rawlist) {
//            if (obj.getMainData().equals("Supplier Order"))
//            {
//                dashboardMenuList.add(new DashboardClass(obj.getMainData(),obj.getSubData(),obj.getImg()));
//            }
//        }

    }

    private void manage_dashboard_callback() {
        rvdashboardcallback=new AdapterDashboard.DashboardAdapterListener() {
            @Override
            public void onclick(DashboardClass obj,int pos) {
                Log.e("Selected data ",obj.toString());
                if (obj.getMainData().equals("Search Stock"))
                {
                    Const.flag_searchResult=1;
                    startActivity(new Intent(context, SearchFilterActivity.class));
                    overridePendingTransition(0, 0);
                }
                else if (obj.getMainData().equals("Offer Stone"))
                {
                    startActivity(new Intent(context, OfferStoneActivity.class));
                    overridePendingTransition(0, 0);
                }
                else if (obj.getMainData().equals("New Arrival"))
                {
                    startActivity(new Intent(context, NewArrivalActivity.class));
                    overridePendingTransition(0, 0);
                }
                else if (obj.getMainData().equals("Order History"))
                {
                    startActivity(new Intent(context, OrderHistoryActivity.class));
                    overridePendingTransition(0, 0);
                }
                else if (obj.getMainData().equals("My Shopping Cart"))
                {
                    startActivity(new Intent(context, MyCartActivity.class));
                    overridePendingTransition(0, 0);
                }
                else if (obj.getMainData().equals("Recent Search"))
                {
                    startActivity(new Intent(context, RecentSearchActivity.class));
                    overridePendingTransition(0, 0);
                }
                else if (obj.getMainData().equals("WishList"))
                {
                    startActivity(new Intent(context, WishListActivity.class));
                    overridePendingTransition(0, 0);
                }
                else if (obj.getMainData().equals("Save Search"))
                {
                    startActivity(new Intent(context, SaveSearchActivity.class));
                    overridePendingTransition(0, 0);
                }
//                else if (obj.getMainData().equals("Supplier Order"))
//                {
//                    startActivity(new Intent(context, SupplierOrderActivity.class));
//                    overridePendingTransition(0, 0);
//                }

            }
        };
    }

    private void getLastLogin() {
        // Const.showProgress(context);
        final Map<String, String> map = new HashMap<>();
        map.put("DeviceType", "ANDROID");
        Const.callPostApi(context, "Stock/GetLastLoggedin", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Const.dismissProgress();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.optString("Status").equalsIgnoreCase("1")) {
                        if (object.optString("Data") != null && !object.optString("Data").equalsIgnoreCase("[]")) {
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
                                    if(hm.get("logindate") != null && !hm.get("logindate").equalsIgnoreCase("")){
                                        String LoginData = hm.get("logindate");
                                        String LoginDate = "", LoginTime = "" ;
                                        if (LoginData.length() > 12) {
                                            LoginDate = LoginData.substring(0, 12);
                                            LoginTime = LoginData.substring(12, 20);
                                        } else {
                                            llLastLogin.setVisibility(View.GONE);
                                            tvLoginDate.setText("-");
                                        }
                                        tvLoginDate.setText(LoginDate + "\n" + LoginTime);
                                    } else {
                                        llLastLogin.setVisibility(View.GONE);
                                        tvLoginDate.setText("-");
                                    }
                                }
                            }
                        } else {
                            llLastLogin.setVisibility(View.GONE);
                            tvLoginDate.setText("-");
                        }
                    } else {
                        llLastLogin.setVisibility(View.GONE);
                        tvLoginDate.setText("-");
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
                        getLastLogin();
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @SuppressLint("WrongConstant")
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.overlay:
                    overlay.setVisibility(View.GONE);
                    break;
                case R.id.imgMenu:
                    drawerLayout.openDrawer(Gravity.START);
                    break;
                case R.id.imgLogout:
                    Const.LogoutDialog(context);
                    break;
                case R.id.Notification:
                    startActivity(new Intent(context, NotificationActivity.class));
                    overridePendingTransition(0, 0);
                    break;
                case R.id.imgSingleSearch:
                    startActivity(new Intent(context, SearchFilterActivity.class));
                    overridePendingTransition(0, 0);
                    break;
                case R.id.rlCart:
                    startActivity(new Intent(context, MyCartActivity.class));
                    overridePendingTransition(0, 0);
                    break;
                case R.id.rlNewArrival:
                    startActivity(new Intent(context, NewArrivalActivity.class));
                    overridePendingTransition(0, 0);
                    break;
                case R.id.rlOrderHistory:
                    startActivity(new Intent(context, OrderHistoryActivity.class));
                    overridePendingTransition(0, 0);
                    break;
                case R.id.rlWishList:
                    startActivity(new Intent(context, WishListActivity.class));
                    overridePendingTransition(0, 0);
                    break;
                case R.id.rlTotalStock:
                    startActivity(new Intent(context, SearchFilterActivity.class));
                    overridePendingTransition(0, 0);
                    break;
                case R.id.rlOffer:
                    startActivity(new Intent(context, OfferStoneActivity.class));
                    overridePendingTransition(0, 0);
                    break;
                case R.id.rlPairSearch:
                    startActivity(new Intent(context, PairSearchFilterActivity.class));
                    overridePendingTransition(0, 0);
                    break;
                case R.id.rlRecentSearch:
                    startActivity(new Intent(context, RecentSearchActivity.class));
                    overridePendingTransition(0, 0);
                    break;
                case R.id.rlSaveSearch:
                    startActivity(new Intent(context, SaveSearchActivity.class));
                    overridePendingTransition(0, 0);
                    break;
                case R.id.rlRevisedPrice:
                    startActivity(new Intent(context, RevisedPriceActivity.class));
                    overridePendingTransition(0, 0);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity();
            }
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        getHomeCount();
//    }
}
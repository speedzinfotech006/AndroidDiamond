package com.dnk.shairugems;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.dnk.shairugems.Class.LoginData;
import com.dnk.shairugems.Utils.Const;
import com.dnk.shairugems.Utils.Pref;
import com.dnk.shairugems.Utils.RestClient;
import com.dnk.shairugems.Volly.VolleyCallback;
import com.infideap.drawerbehavior.Advance3DDrawerLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseDrawerActivity extends AppCompatActivity {

    Advance3DDrawerLayout drawerLayout;
    FrameLayout frameLayout;
    ImageView imgProfile, imgEdit;
    TextView tvUserName, tvCountWish, tvCountCart;
    RelativeLayout rlHome, rlSearchStock, rlLabStock, rlPairSearch, rlQuickSearch, rlOfferStone, rlNewArrival, rlRevisedPrice,
            rlMyCart, rlOrderHistory, rlsupplierOrder, rlOfferHistory, rlManageUser, rlConfirmOrder, rlUserMgt,
            rlPacketTrace, rlRecentSearch, rlSaveSearch, rlWishlist, rlSearchStockFour,
            rlManagePassword, rlService, rlAboutUs, rlContactUs, rlHoldStoneReport;
    LinearLayout llLogout;
    List<HashMap<String, String>> arrayCount;
    Context context = BaseDrawerActivity.this;

    TextView tvMenu_Home,tvMenu_Searchstock,tvMenu_OrderHistory,tvMenu_supplierOrder,tvMenu_MyCart,tvMenu_MyWishList,tvMenu_RecentSearch,
            tvMenu_OfferStone,tvMenu_SaveSearch,tvMenu_PairSearch,tvMenu_NewArrival,tvMenu_HoldStoneReport,

    tvMenu_QuickSearch,tvMenu_offerHistory,tvMenu_LabStock,tvMenu_manageUser,tvMenu_confirmOrder,
    tvMenu_UserMgt, tvMenu_PacketTrace,tvMenu_ManagePassword,tvMenu_AboutUs,tvMenu_ContactUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_drawer_activity);

        Const.navigation_flag=0;

        initView();

        rlHome.setOnClickListener(clickListener);
        rlSearchStock.setOnClickListener(clickListener);
        rlLabStock.setOnClickListener(clickListener);
        rlPairSearch.setOnClickListener(clickListener);
        rlQuickSearch.setOnClickListener(clickListener);
        rlOfferStone.setOnClickListener(clickListener);
        rlNewArrival.setOnClickListener(clickListener);
        rlRevisedPrice.setOnClickListener(clickListener);
        llLogout.setOnClickListener(clickListener);
        rlSearchStockFour.setOnClickListener(clickListener);
        rlMyCart.setOnClickListener(clickListener);
        rlOrderHistory.setOnClickListener(clickListener);
        rlsupplierOrder.setOnClickListener(clickListener);
        rlOfferHistory.setOnClickListener(clickListener);
        rlRecentSearch.setOnClickListener(clickListener);
        rlSaveSearch.setOnClickListener(clickListener);
        rlWishlist.setOnClickListener(clickListener);
        rlManageUser.setOnClickListener(clickListener);
        rlConfirmOrder.setOnClickListener(clickListener);
        rlHoldStoneReport.setOnClickListener(clickListener);
        rlUserMgt.setOnClickListener(clickListener);
        rlPacketTrace.setOnClickListener(clickListener);
        rlManagePassword.setOnClickListener(clickListener);
        rlService.setOnClickListener(clickListener);
        rlAboutUs.setOnClickListener(clickListener);
        rlContactUs.setOnClickListener(clickListener);
        imgEdit.setOnClickListener(clickListener);

        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                animateMenuItems();
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                Const.navigation_flag=0;
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

    }

    private void animateMenuItems() {

        if (Const.navigation_flag==0) {

            Const.navigation_flag++;

            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_to_right);

            tvMenu_Home.startAnimation(animation);
            tvMenu_Searchstock.startAnimation(animation);
            tvMenu_OrderHistory.startAnimation(animation);
            tvMenu_supplierOrder.startAnimation(animation);
            tvMenu_MyCart.startAnimation(animation);
            tvMenu_MyWishList.startAnimation(animation);

            tvMenu_RecentSearch.startAnimation(animation);
            tvMenu_OfferStone.startAnimation(animation);
            tvMenu_SaveSearch.startAnimation(animation);
            tvMenu_PairSearch.startAnimation(animation);
            tvMenu_NewArrival.startAnimation(animation);

            tvMenu_QuickSearch.startAnimation(animation);
            tvMenu_offerHistory.startAnimation(animation);
            tvMenu_LabStock.startAnimation(animation);
            tvMenu_manageUser.startAnimation(animation);
            tvMenu_confirmOrder.startAnimation(animation);
            tvMenu_HoldStoneReport.startAnimation(animation);

            tvMenu_UserMgt.startAnimation(animation);
            tvMenu_PacketTrace.startAnimation(animation);
            tvMenu_ManagePassword.startAnimation(animation);
            tvMenu_AboutUs.startAnimation(animation);
            tvMenu_ContactUs.startAnimation(animation);
        }
    }

    private void initView() {
        frameLayout = findViewById(R.id.content_frame);

        tvMenu_Home=findViewById(R.id.tvMenu_Home);
        tvMenu_Searchstock=findViewById(R.id.tvMenu_Searchstock);
        tvMenu_OrderHistory=findViewById(R.id.tvMenu_OrderHistory);
        tvMenu_supplierOrder=findViewById(R.id.tvMenu_supplierOrder);
        tvMenu_MyCart=findViewById(R.id.tvMenu_MyCart);
        tvMenu_MyWishList=findViewById(R.id.tvMenu_MyWishList);

        tvMenu_RecentSearch=findViewById(R.id.tvMenu_RecentSearch);
        tvMenu_OfferStone=findViewById(R.id.tvMenu_OfferStone);
        tvMenu_SaveSearch=findViewById(R.id.tvMenu_SaveSearch);
        tvMenu_PairSearch=findViewById(R.id.tvMenu_PairSearch);
        tvMenu_NewArrival=findViewById(R.id.tvMenu_NewArrival);

        tvMenu_QuickSearch=findViewById(R.id.tvMenu_QuickSearch);
        tvMenu_offerHistory=findViewById(R.id.tvMenu_offerHistory);
        tvMenu_LabStock=findViewById(R.id.tvMenu_LabStock);
        tvMenu_manageUser=findViewById(R.id.tvMenu_manageUser);
        tvMenu_confirmOrder=findViewById(R.id.tvMenu_confirmOrder);
        tvMenu_HoldStoneReport=findViewById(R.id.tvMenu_holdStoneReport);

        tvMenu_UserMgt=findViewById(R.id.tvMenu_UserMgt);
        tvMenu_PacketTrace=findViewById(R.id.tvMenu_PacketTrace);
        tvMenu_ManagePassword=findViewById(R.id.tvMenu_ManagePassword);
        tvMenu_AboutUs=findViewById(R.id.tvMenu_AboutUs);
        tvMenu_ContactUs=findViewById(R.id.tvMenu_ContactUs);

        rlHome = findViewById(R.id.rlHome);
        rlRecentSearch = findViewById(R.id.rlRecentSearch);
        rlSaveSearch = findViewById(R.id.rlSaveSearch);
        rlWishlist = findViewById(R.id.rlWishlist);
        tvUserName = findViewById(R.id.tvUserName);
        imgEdit = findViewById(R.id.imgEdit);
        imgProfile = findViewById(R.id.imgProfile);
        rlSearchStockFour = findViewById(R.id.rlSearchStockFour);
        rlSearchStock = findViewById(R.id.rlSearchStock);
        rlLabStock = findViewById(R.id.rlLabStock);
        rlPairSearch = findViewById(R.id.rlPairSearch);
        rlQuickSearch = findViewById(R.id.rlQuickSearch);
        rlOfferStone = findViewById(R.id.rlOfferStone);
        rlNewArrival = findViewById(R.id.rlNewArrival);
        rlRevisedPrice = findViewById(R.id.rlRevisedPrice);
        rlOrderHistory = findViewById(R.id.rlOrderHistory);
        rlsupplierOrder = findViewById(R.id.rlsupplierOrder);
        rlOfferHistory = findViewById(R.id.rlOfferHistory);
        rlMyCart = findViewById(R.id.rlMyCart);
        rlManageUser = findViewById(R.id.rlManageUser);
        rlConfirmOrder = findViewById(R.id.rlConfirmOrder);
        rlHoldStoneReport = findViewById(R.id.rlHoldStoneReport);
        rlUserMgt=findViewById(R.id.rlUserMgt);
        rlPacketTrace = findViewById(R.id.rlPacketTrace);
        rlManagePassword = findViewById(R.id.rlManagePassword);
        rlService = findViewById(R.id.rlService);
        rlAboutUs = findViewById(R.id.rlAboutUs);
        rlContactUs = findViewById(R.id.rlContactUs);
        drawerLayout = findViewById(R.id.drawer_layout);
        llLogout = findViewById(R.id.llLogout);

        tvCountWish = findViewById(R.id.tvCountWish);
        tvCountCart = findViewById(R.id.tvCountCart);


        drawerLayout.useCustomBehavior(View.FOCUS_LEFT);
//        drawerLayout.setViewScale(Gravity.START, 0.7f);
        drawerLayout.setRadius(Gravity.START, 35f);
//        drawerLayout.setViewElevation(Gravity.START, 20f);

//        drawerLayout.setViewScale(Gravity.START, 0.9f);
//        drawerLayout.setRadius(Gravity.START, 35f);
//        drawerLayout.setViewElevation(Gravity.START, 20f);

//        old design
//        drawerLayout.setViewRotation(Gravity.START, 15);
//        drawerLayout.setViewScale(Gravity.START, 0.9f);
//        drawerLayout.setViewElevation(Gravity.START, 20);
//        drawerLayout.setViewScrimColor(Gravity.START, Color.TRANSPARENT);
//        drawerLayout.setDrawerElevation(Gravity.START, 20);
//        drawerLayout.setContrastThreshold(3);
//        drawerLayout.setRadius(Gravity.START, 25);

//        GetProfilePicture();
//        getHomeCount();

        tvUserName.setText(Pref.getStringValue(context, Const.loginName, ""));
        rlAboutUs.setVisibility(View.GONE);

        if (Pref.getStringValue(context, Const.loginIsAdmin, "").equalsIgnoreCase("1")) {
            rlPairSearch.setVisibility(View.GONE);
            rlRecentSearch.setVisibility(View.GONE);
            rlSaveSearch.setVisibility(View.GONE);
            rlsupplierOrder.setVisibility(View.VISIBLE);
        } else if (Pref.getStringValue(context, Const.loginIsEmp, "").equalsIgnoreCase("1")) {
            rlManageUser.setVisibility(View.GONE);
            rlConfirmOrder.setVisibility(View.GONE);
            rlPacketTrace.setVisibility(View.GONE);
            rlsupplierOrder.setVisibility(View.GONE);
            rlLabStock.setVisibility(View.GONE);
            if(Pref.getStringValue(context, Const.loginId, "").equalsIgnoreCase("10") ||
                    Pref.getStringValue(context, Const.loginId, "").equalsIgnoreCase("15") ||
                    Pref.getStringValue(context, Const.loginId, "").equalsIgnoreCase("39") ||
                    Pref.getStringValue(context, Const.loginId, "").equalsIgnoreCase("9")){
                rlsupplierOrder.setVisibility(View.VISIBLE);
                rlLabStock.setVisibility(View.VISIBLE);
            }
        } else {
            rlsupplierOrder.setVisibility(View.GONE);
            rlHoldStoneReport.setVisibility(View.GONE);
            if (Pref.getStringValue(context, Const.loginIsCust, "").equals("1")) {
                if (Pref.getStringValue(context, Const.SearchStock, "").equals("true")) {
                    rlSearchStock.setVisibility(View.VISIBLE);
                }
                else
                {
                    rlSearchStock.setVisibility(View.GONE);
                }


                if (Pref.getStringValue(context, Const.OrderHisrory, "").equals("true")) {
                    rlOrderHistory.setVisibility(View.VISIBLE);
                }
                else
                {
                    rlOrderHistory.setVisibility(View.GONE);
                }


                if (Pref.getStringValue(context, Const.MyCart,"").equals("true")) {
                    rlMyCart.setVisibility(View.VISIBLE);
                } else {
                    rlMyCart.setVisibility(View.GONE);
                }

                if (Pref.getStringValue(context, Const.MyWishlist, "").equals("true")) {
                    rlWishlist.setVisibility(View.VISIBLE);
                }
                else
                {
                    rlWishlist.setVisibility(View.GONE);
                }


                if (Pref.getStringValue(context, Const.QuickSearch, "").equals("true")) {
                    rlQuickSearch.setVisibility(View.VISIBLE);
                }
                else
                {
                    rlQuickSearch.setVisibility(View.GONE);
                }

            }

            if (Pref.getStringValue(context, Const.loginIsAdmin, "").equalsIgnoreCase("1")
                    || Pref.getStringValue(context, Const.IsAssistByForAnyUser, "").equalsIgnoreCase("true")
                    || Pref.getStringValue(context, Const.IsPrimary, "").equalsIgnoreCase("true"))
            {
                rlUserMgt.setVisibility(View.VISIBLE);
            }
            else
            {
                rlUserMgt.setVisibility(View.GONE);
            }


            rlLabStock.setVisibility(View.GONE);
            rlManageUser.setVisibility(View.GONE);
            rlConfirmOrder.setVisibility(View.GONE);
            rlPacketTrace.setVisibility(View.GONE);

            if(Pref.getStringValue(context, Const.loginId, "").equalsIgnoreCase("10") ||
                    Pref.getStringValue(context, Const.loginId, "").equalsIgnoreCase("15") ||
                    Pref.getStringValue(context, Const.loginId, "").equalsIgnoreCase("39") ||
                    Pref.getStringValue(context, Const.loginId, "").equalsIgnoreCase("9")){
                rlsupplierOrder.setVisibility(View.VISIBLE);
                rlLabStock.setVisibility(View.VISIBLE);
            }
        }

    }



    private void GetProfilePicture() {
        // Const.showProgress(context);
        final Map<String, String> map = new HashMap<>();
        Const.callPostApi(context, "User/GetUserProfilePicture", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result1) {
                //   Const.dismissProgress();
                try {
                    String result = result1.replaceAll("^[\"']+|[\"']+$", "");
                    if (!result.equalsIgnoreCase("")) {
                        try {
                            byte[] decodedString = Base64.decode(result, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            imgProfile.setImageBitmap(decodedByte);
                        } catch (Exception ex) {

                        }
                    } else {
                        //   Const.showErrorDialog(context,"Error to download video, video is not MP4..!");
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
                        GetProfilePicture();
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
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
                                } else if (arrayCount.get(i).get("type").equalsIgnoreCase("WishList")) {
                                    tvCountWish.setText(arrayCount.get(i).get("scnt"));
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

    public void changePassword(final Dialog dialog, final String newPassword){
        Const.showProgress(this);
        new RestClient(context).getInstance().get().changePassword(newPassword).enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
//                Debug.e(TAG, new Gson().toJson(response.body()));
                Const.dismissProgress();
                dialog.dismiss();
                if (response.body() != null) {
                    if (response.body().Status.equals("1")) {
                        Pref.setStringValue(context, Const.loginPassword, newPassword);
                        Toast.makeText(context, response.body().Message, Toast.LENGTH_SHORT).show();
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

//    private void changePassword(final Dialog dialog, final String newPassword) {
//        Const.showProgress(this);
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("Password", newPassword);
//        Const.callPostApi(context, Const.BaseUrl + "User/UpdatePassword", map, new VolleyCallback() {
//            @Override
//            public void onSuccessResponse(String result) {
//                Const.dismissProgress();
//                dialog.dismiss();
//                try {
//                    JSONObject object = new JSONObject(result);
//                    if (object.optString("Status").equalsIgnoreCase("1")) {
//                        Pref.setStringValue(context, Const.loginPassword, newPassword);
//                        Toast.makeText(context, object.optString("Message"), Toast.LENGTH_SHORT).show();
//                    } else {
//                        Const.showErrorDialog(context, object.optString("Message"));
//                    }
//                } catch (JSONException e) {
//                    Log.e("errror", e.toString());
//                    Const.showErrorDialog(context, "Something went wrong.");
//                }
//            }
//
//            @Override
//            public void onFailerResponse(String error) {
//                Const.dismissProgress();
//                Runnable runnable = new Runnable() {
//                    @Override
//                    public void run() {
//                        changePassword(dialog,newPassword);
//                    }
//                };
//                Const.showErrorApiDialog(context, runnable);
//            }
//        });
//    }

    private void ChangePasword() {
        View contentView = View.inflate(BaseDrawerActivity.this, R.layout.layout_change_pass, null);
        final Dialog sheetDialog = new Dialog(BaseDrawerActivity.this, R.style.Theme_Dialog);
        sheetDialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        Window window = sheetDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        sheetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        sheetDialog.setContentView(contentView);
        sheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView tvClose = sheetDialog.findViewById(R.id.tvClose);
        final ImageView imgShowHide = sheetDialog.findViewById(R.id.imgShowHide2);
        final EditText etOldPassword = sheetDialog.findViewById(R.id.etOldPassword);
        final EditText etNewPassword = sheetDialog.findViewById(R.id.etNewPassword);
        final ImageView imgShowHide3 = sheetDialog.findViewById(R.id.imgShowHide3);
        final EditText etConfNewPassword = sheetDialog.findViewById(R.id.etConfNewPassword);
        final Button btnChangePass = sheetDialog.findViewById(R.id.btnChangePass);

        imgShowHide3.setOnClickListener(new View.OnClickListener() {

            boolean isshow = false;

            @Override
            public void onClick(View view) {
                if (!etOldPassword.getText().toString().equalsIgnoreCase("")) {
                    if (isshow) {
                        isshow = false;
                        etOldPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        imgShowHide3.setImageResource(R.drawable.ic_hide);
                        etOldPassword.setSelection(etOldPassword.getText().length());
                    } else {
                        isshow = true;
                        etOldPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                        imgShowHide3.setImageResource(R.drawable.ic_show);
                        etOldPassword.setSelection(etOldPassword.getText().length());
                    }
                } else {
                    if (isshow) {
                        isshow = false;
                        imgShowHide3.setImageResource(R.drawable.ic_show);
                    } else {
                        isshow = true;
                        imgShowHide3.setImageResource(R.drawable.ic_hide);
                    }
                }
            }
        });

        etConfNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equalsIgnoreCase("")) {
                    etConfNewPassword.setTypeface(Const.setFontTypeface(BaseDrawerActivity.this));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etOldPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equalsIgnoreCase("")) {
                    etOldPassword.setTypeface(Const.setFontTypeface(BaseDrawerActivity.this));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        imgShowHide.setOnClickListener(new View.OnClickListener() {

            boolean isshow = true;

            @Override
            public void onClick(View view) {
                if (!etConfNewPassword.getText().toString().equalsIgnoreCase("")) {
                    if (isshow) {
                        isshow = false;
                        etConfNewPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        imgShowHide.setImageResource(R.drawable.ic_hide);
                        etConfNewPassword.setSelection(etConfNewPassword.getText().length());
                    } else {
                        isshow = true;
                        etConfNewPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                        imgShowHide.setImageResource(R.drawable.ic_show);
                        etConfNewPassword.setSelection(etConfNewPassword.getText().length());
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
            }
        });

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!etOldPassword.getText().toString().trim().equalsIgnoreCase(Pref.getStringValue(context, Const.loginPassword, ""))) {
                    Toast.makeText(context, "Please enter valid old password", Toast.LENGTH_SHORT).show();
                } else if (Const.isEmpty(etNewPassword)) {
                    Toast.makeText(context, "Please enter new password", Toast.LENGTH_SHORT).show();
                } else if (etNewPassword.getText().toString().length() < 6) {
                    Toast.makeText(context, "Please enter minimum 6 character password", Toast.LENGTH_SHORT).show();
                } else if (!etConfNewPassword.getText().toString().trim().equalsIgnoreCase(etNewPassword.getText().toString().trim())) {
                    Toast.makeText(context, "Please enter valid confirm new password", Toast.LENGTH_SHORT).show();
                } else {
                    changePassword(sheetDialog, etConfNewPassword.getText().toString().trim());
                }

            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            tvClose.setBackground(Const.setBackgoundBorder(2, 40, Color.TRANSPARENT, Color.WHITE));
        }
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheetDialog.dismiss();
            }
        });
        sheetDialog.show();
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            drawerLayout.closeDrawers();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    switch (view.getId()) {
                        case R.id.rlHome:
                            startActivity(new Intent(context, HomeActivity.class));
                            overridePendingTransition(0, 0);
                            break;
                        case R.id.rlSearchStock:
                            Const.flag_searchResult=1;
                            startActivity(new Intent(context, SearchFilterActivity.class));
                            overridePendingTransition(0, 0);
                            break;
                        case R.id.rlLabStock:
                            startActivity(new Intent(context, LabStockSearchActivity.class));
                            overridePendingTransition(0, 0);
                            break;
                        case R.id.rlPairSearch:
                            startActivity(new Intent(context, PairSearchFilterActivity.class));
                            overridePendingTransition(0, 0);
                            break;
                        case R.id.rlQuickSearch:
                            startActivity(new Intent(context, QuickSearchActivity.class));
                            overridePendingTransition(0, 0);
                            break;
                        case R.id.rlOfferStone:
                            startActivity(new Intent(context, OfferStoneActivity.class));
                            overridePendingTransition(0, 0);
                            break;
                        case R.id.rlNewArrival:
                            startActivity(new Intent(context, NewArrivalActivity.class));
                            overridePendingTransition(0, 0);
                            break;
                        case R.id.rlRevisedPrice:
                            startActivity(new Intent(context, RevisedPriceActivity.class));
                            overridePendingTransition(0, 0);
                            break;
                        case R.id.rlSearchStockFour:
                            startActivity(new Intent(context, SearchResult4Activity.class));
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
                        case R.id.rlWishlist:
                            startActivity(new Intent(context, WishListActivity.class));
                            overridePendingTransition(0, 0);
                            break;
                        case R.id.rlOrderHistory:
                            startActivity(new Intent(context, OrderHistoryActivity.class));
                            overridePendingTransition(0, 0);
                            break;
                        case R.id.rlsupplierOrder:
                            startActivity(new Intent(context, SupplierOrderActivity.class));
                            overridePendingTransition(0, 0);
                            break;
                        case R.id.rlOfferHistory:
                            startActivity(new Intent(context, OfferHistoryActivity.class));
                            overridePendingTransition(0, 0);
                            break;
                        case R.id.imgEdit:
                            startActivity(new Intent(context, ProfileActivity.class));
                            overridePendingTransition(0, 0);
                            break;
                        case R.id.rlMyCart:
                            startActivity(new Intent(context, MyCartActivity.class));
                            overridePendingTransition(0, 0);
                            break;
                        case R.id.rlManageUser:
                            startActivity(new Intent(context, ManageUserActivity.class));
                            overridePendingTransition(0, 0);
                            break;
                        case R.id.rlConfirmOrder:
                            startActivity(new Intent(context, ConfirmOrderActivity.class));
                            overridePendingTransition(0, 0);
                            break;
                        case R.id.rlHoldStoneReport:
                            startActivity(new Intent(context, HoldStoneReportActivity.class));
                            overridePendingTransition(0, 0);
                            break;
                        case R.id.rlUserMgt:
                            startActivity(new Intent(context, UserMgtActivity.class));
                            overridePendingTransition(0, 0);
                            break;
                        case R.id.rlPacketTrace:
                            startActivity(new Intent(context, PacketTrace.class));
                            overridePendingTransition(0, 0);
                            break;
                        case R.id.rlAboutUs:
                            startActivity(new Intent(context, AboutUsActivity.class));
                            overridePendingTransition(0, 0);
                            break;
                        case R.id.rlManagePassword:
                            ChangePasword();
                            overridePendingTransition(0, 0);
                            break;
                        case R.id.rlService:
                            startActivity(new Intent(context, ServiceActivity.class));
                            overridePendingTransition(0, 0);
                            break;
                        case R.id.rlContactUs:
                            startActivity(new Intent(context, ContactUsActivity.class));
                            overridePendingTransition(0, 0);
                            break;
                        case R.id.llLogout:
                            Const.LogoutDialog(context);
                            break;
                        default:
                            break;
                    }
                }
            }, 150);
        }
    };

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getHomeCount();
        GetProfilePicture();
    }

    public Boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }
}
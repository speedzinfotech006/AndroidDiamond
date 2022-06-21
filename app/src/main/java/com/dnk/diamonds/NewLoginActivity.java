package com.dnk.shairugems;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.dnk.shairugems.Utils.Const;
import com.dnk.shairugems.Utils.Pref;
import com.dnk.shairugems.Volly.VolleyCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static android.Manifest.permission.MANAGE_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class NewLoginActivity extends AppCompatActivity {

    Button btnSignIn, btnSignUp;
    TextView tvForgetPass, tvTerms, tvPrivacyPolicy;
    CheckBox cbBox;
    EditText etUserName, etPassword;
    String locationString = "";
    String version = "";
    Context context = NewLoginActivity.this;
    private String regId;
    String fcmToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginnew);

        Const.deleteCache(context);

        Pref.setStringValue(context, Const.isSignUp, "0");

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        fcmToken = token;
                    }
                });

        initView();

       /* if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                try {
                    ActivityCompat.requestPermissions(this, new String[]{
                                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }*/

        check_location_permission();

        if (Pref.getBooleanValue(context, Const.isSave, false)) {
            etUserName.setText(Pref.getStringValue(context, Const.loginName, ""));
            etPassword.setText(Pref.getStringValue(context, Const.loginPassword, ""));
            cbBox.setChecked(true);
        }

        if (Pref.getStringValue(context, Const.isSignUp, "").equalsIgnoreCase("1")) {
            Const.showErrorDialog(context, Pref.getStringValue(context, Const.SignUpMsg, ""));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 200:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    check_location_permission();
                } else {
                    Toast.makeText(this, "Please Grant Write Storage permission ", Toast.LENGTH_SHORT).show();
                }
                return;
            case 201:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    check_write_permission();
                } else {
                    Toast.makeText(this, "Please allow access location permission ", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    private void check_write_permission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                // success
            } else {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, 200);
            }
        }
        else {
//            check_location_permission();
        }

    }

    private void check_location_permission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                check_write_permission();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 201);
            }
        }
        else
        {
            check_write_permission();
        }
    }

    private void initView() {
        cbBox = findViewById(R.id.cbBox);
        tvForgetPass = findViewById(R.id.tvForgetPass);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        etPassword = findViewById(R.id.etPassword);
        etUserName = findViewById(R.id.etUserName);

        tvTerms = findViewById(R.id.tvTerms);
        tvPrivacyPolicy = findViewById(R.id.tvPrivacyPolicy);

        btnSignIn.setOnClickListener(clickListener);
        btnSignUp.setOnClickListener(clickListener);
        tvForgetPass.setOnClickListener(clickListener);
        tvTerms.setOnClickListener(clickListener);
        tvPrivacyPolicy.setOnClickListener(clickListener);

        if (isNetworkAvailable()) {
            getVersion();
        } else {
            Toast.makeText(NewLoginActivity.this, "Internet is not available", Toast.LENGTH_LONG).show();
        }

//        if (isNetworkAvailable()) {
//            getVersion();
//        } else {
//            Toast.makeText(NewLoginActivity.this, "Internet is not available", Toast.LENGTH_LONG).show();
//        }
    }

//    private void loginWithLocation() {
//        Const.showProgress(context);
//        LocationProvider.requestSingleUpdate(context, new LocationProvider.LocationCallback() {
//            @Override
//            public void onNewLocationAvailable(LocationProvider.GPSCoordinates location) {
//                if (location != null) {
//                    locationString = location.latitude + "," + location.longitude;
//                    login();
//                }
//            }
//        });
//    }

    private void getVersion() {
        Const.showProgress(context);
        final Map<String, String> map = new HashMap<>();
        map.put("DeviceType", "ANDROID");
        Const.callPostApi(context, "User/Get_App_info", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Const.dismissProgress();
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
                                    if(hm.get("appversion") != null && !hm.get("appversion").equalsIgnoreCase("")){
                                       version = hm.get("appversion");

                                        if (Integer.valueOf(version) <= Integer.valueOf(String.valueOf(getAppVersion()))) {
                                            Log.e("customer ",Pref.getStringValue(context, Const.loginIsAdmin, ""));
                                       } else {
                                            final Dialog dialog = new Dialog(context);
                                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                            dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
                                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                            dialog.setCancelable(false);
                                            dialog.setContentView(R.layout.layout_error_dialog);
                                            TextView tvMessage = (TextView) dialog.findViewById(R.id.tvMessage);
                                            tvMessage.setText("Please Update Your Application");
                                            Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
                                            btnOk.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Intent i = new Intent(android.content.Intent.ACTION_VIEW);
//                                                    i.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
//                                                     i.setData(Uri.parse("https://appgallery7.huawei.com/#/app/C101553987"));
                                                    startActivity(i);
                                                    dialog.dismiss();
                                                }
                                            });
                                            dialog.show();
                                        }
                                    } else {
                                        Const.showErrorDialog(context, "Version Code Not Found!");
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
//                Runnable runnable = new Runnable() {
//                    @Override
//                    public void run() {
//                        getVersion();
//                    }
//                };
//                Const.showErrorApiDialog(context, runnable);
            }
        });
    }


    private int getAppVersion() {
        PackageManager manager = getApplicationContext().getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
//		   String packageName = info.packageName;
            int versionCode = info.versionCode;
//		   String versionName = info.versionName;
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return -999;
        }
    }

    private void login() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        fcmToken = token;
                    }
                });

        Const.showProgress(context);
        Map<String, String> map = new HashMap<>();
        map.put("UserName", etUserName.getText().toString());
        map.put("Password", etPassword.getText().toString());
        map.put("Source", "");
        map.put("IpAddress", Const.getIpAddress());
//        map.put("UDID", "");
        map.put("UDID", fcmToken);
        map.put("LoginMode", "");
        map.put("DeviseType", "ANDROID");
        map.put("DeviceName", Const.getDeviceName());
        map.put("AppVersion", Const.getAppVersion(context) + "");
        map.put("Location", locationString);
        map.put("Login", "");
        map.put("grant_type", "password");

        Log.e("input ",map.toString());

        Const.callPostApiLogin(context, "User/Login", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Log.e("Token ",result);
                try {
                    JSONObject object = new JSONObject(result);
                    Iterator<String> keys = object.keys();
                    HashMap<String, String> hm = new HashMap<>();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        hm.put(key.toLowerCase(), object.optString(key).trim());
                    }
                    
                    if (Const.notNullString(hm.get("status") , "").equalsIgnoreCase("1")) {
                        Pref.setStringValue(context, Const.loginId, Const.notNullString(hm.get("userid"), ""));
                        Pref.setStringValue(context, Const.loginToken, Const.notNullString(hm.get("access_token"), ""));
                        Pref.setStringValue(context, Const.loginName, Const.notNullString(hm.get("username"), ""));
                        Pref.setStringValue(context, Const.loginPassword, etPassword.getText().toString());
                        Pref.setStringValue(context, Const.loginIsAdmin, Const.notNullString(hm.get("isadmin"), ""));
                        Pref.setStringValue(context, Const.loginIsEmp, Const.notNullString(hm.get("isemp"), ""));
                        if (Pref.getStringValue(context, Const.loginIsAdmin, "").equalsIgnoreCase("0") && Pref.getStringValue(context, Const.loginIsEmp, "").equalsIgnoreCase("0")) {
                            Pref.setStringValue(context, Const.loginIsCust, "1");
                        } else {
                            Pref.setStringValue(context, Const.loginIsCust, "0");
                        }
                        if (cbBox.isChecked()) {
                            Pref.setBooleanValue(context, Const.isSave, true);
                        } else {
                            Pref.removeValue(context, Const.isSave);
                        }


                        get_Account_Data();


                      /*  //version = play store version  &  getAppVersion() = user app version
                        if (Integer.valueOf(version) <= Integer.valueOf(String.valueOf(getAppVersion()))) {
                            Log.e("customer ",Pref.getStringValue(context, Const.loginIsAdmin, ""));
                            startActivity(new Intent(context, HomeActivity.class));
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        } else {
                            final Dialog dialog = new Dialog(context);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.layout_error_dialog);
                            TextView tvMessage = (TextView) dialog.findViewById(R.id.tvMessage);
                            tvMessage.setText("Please Update Your Application");
                            Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
                            btnOk.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                                    i.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
                                    // i.setData(Uri.parse("https://appgallery7.huawei.com/#/app/C101553987"));
                                    startActivity(i);
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }*/
                    }
                    else if (Const.notNullString(hm.get("error") , "").equalsIgnoreCase("invalid_grant")){
                        Const.dismissProgress();
                        Log.e("msg ",Const.notNullString(hm.get("error_description"), "Something Want Wrong, Please try Again Later"));
//                        Const.showErrorDialog(context, Const.notNullString(hm.get("error_description"), "Something Want Wrong, Please try Again Later"));
                        if (Const.notNullString(hm.get("error_description"), "Something Want Wrong, Please try Again Later").contains("Suspended"))
                        {
                            Const.showErrorDialog(context,"Hello " + etUserName.getText().toString() + " ,\n\n" + Const.notNullString(hm.get("error_description"), "Something Want Wrong, Please try Again Later"));
                        }
                        else
                        {
                            Const.showErrorDialog(context, Const.notNullString(hm.get("error_description"), "Something Want Wrong, Please try Again Later"));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailerResponse(String error) {
                Const.dismissProgress();
//                Runnable runnable = new Runnable() {
//                    @Override
//                    public void run() {
//                        login();
//                    }
//                };
                Const.showErrorDialog(context, "Wrong User Name or Password");
            }
        });
    }

    private void get_Account_Data() {
        Map<String, String> map = new HashMap<>();
        map.put("UserName", etUserName.getText().toString());
        map.put("Password", etPassword.getText().toString());
        Const.callPostApiLogin(context, "User/GetKeyAccountData", map, new VolleyCallback() {
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

                                Pref.setStringValue(context, Const.SearchStock, Const.notNullString(hm.get("searchstock"), "false"));
                                Pref.setStringValue(context, Const.PlaceOrder, Const.notNullString(hm.get("placeorder"), "false"));
                                Pref.setStringValue(context, Const.OrderHisrory, Const.notNullString(hm.get("orderhisrory"), "false"));
                                Pref.setStringValue(context, Const.MyCart, Const.notNullString(hm.get("mycart"), "false"));
                                Pref.setStringValue(context, Const.MyWishlist, Const.notNullString(hm.get("mywishlist"), "false"));
                                Pref.setStringValue(context, Const.QuickSearch, Const.notNullString(hm.get("quicksearch"), "false"));

                                Pref.setStringValue(context, Const.IsAssistByForAnyUser, Const.notNullString(hm.get("isassistbyforanyuser"), "false"));
                                Pref.setStringValue(context, Const.IsPrimary, Const.notNullString(hm.get("isprimary"), "false"));
                                Pref.setStringValue(context, Const.CompanyName, Const.notNullString(hm.get("scompname"), ""));

                                Pref.setStringValue(context, Const.IsLogout, Const.notNullString(hm.get("islogout"), "false"));
                                Pref.setStringValue(context, Const.MessageShow, Const.notNullString(hm.get("messageshow"), ""));
                                Pref.setStringValue(context, Const.MessageId, Const.notNullString(hm.get("messageid"), ""));
                                Pref.setStringValue(context, Const.IsLoginTime, "true");

                                startActivity(new Intent(context, HomeActivity.class));
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                finish();

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

//    public static void showErrorDialog(final Context context, String message) {
//        final Dialog dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.layout_error_dialog_new);
//
//        TextView tvMessage = (TextView) dialog.findViewById(R.id.tvMessage);
//        tvMessage.setText(message);
//
//        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
//        btnOk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//    }

    private void ForgotPassword(final Dialog dialog, final String UserName) {
        Const.showProgress(this);
        Map<String, String> map = new HashMap<String, String>();
        map.put("UserName", UserName);
        Const.callPostApi2(context, Const.BaseUrl + "User/ForgotPassword", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Const.dismissProgress();
                dialog.dismiss();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.optString("Status").equalsIgnoreCase("1")) {
                        Pref.setStringValue(context, Const.loginName, UserName);
                        Toast.makeText(context, object.optString("Message"), Toast.LENGTH_SHORT).show();
                    } else {
                        Const.showErrorDialog(context, object.optString("Message"));
                    }
                } catch (JSONException e) {
                    Log.e("errror", e.toString());
                    Const.showErrorDialog(context, "Something went wrong.");
                }
            }

            @Override
            public void onFailerResponse(String error) {
                Const.dismissProgress();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        ForgotPassword(dialog,UserName);
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    private void ForgotPasword() {
        View contentView = View.inflate(context, R.layout.layout_forgot_pass, null);
        final Dialog sheetDialog = new Dialog(context, R.style.Theme_Dialog);
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
        final EditText etUserName = sheetDialog.findViewById(R.id.etUserName);
        final Button btnForgotPass = sheetDialog.findViewById(R.id.btnForgotPass);

        etUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equalsIgnoreCase("")) {
                    etUserName.setTypeface(Const.setFontTypeface(context));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Const.isEmpty(etUserName)) {
                    Toast.makeText(context, "Please Enter UserName", Toast.LENGTH_SHORT).show();
                } else {
                    ForgotPassword(sheetDialog, etUserName.getText().toString().trim());
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
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnSignIn:
                    if (Const.isEmpty(etUserName)) {
                        etUserName.setError("Empty Username");
                    } else if (Const.isEmpty(etPassword)) {
                        etPassword.setError("Empty Password");
                    } else if (isNetworkAvailable()) {
                        login();
                    } else {
                        Toast.makeText(NewLoginActivity.this, "Internet is not available", Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.btnSignUp:
                    startActivity(new Intent(context, SignUpActivity.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    break;
                case R.id.tvForgetPass:
                    ForgotPasword();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    break;
                case R.id.tvTerms:
                   // ForgotPasword();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    break;
                case R.id.tvPrivacyPolicy:
                    // ForgotPasword();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    break;
                default:
                    break;
            }
        }
    };

    public Boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        }
    }
}
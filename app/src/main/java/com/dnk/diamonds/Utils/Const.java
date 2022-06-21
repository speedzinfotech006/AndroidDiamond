package com.dnk.shairugems.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ImageSpan;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dnk.shairugems.Class.CustomDialog;
import com.dnk.shairugems.HomeActivity;
import com.dnk.shairugems.NewLoginActivity;
import com.dnk.shairugems.R;
import com.dnk.shairugems.Volly.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Const {

    public static String APP_NAME = "NEWSHAIRUAPP!";
    public static final String PREF_FILE = APP_NAME + "_PREF";
    public static CustomDialog custDailog = null;

    //    WebService :-

//      public static String BaseUrl = "https://test.brainwaves.co.in:8122/api/";
    public static String BaseUrl = "https://sunrisediamonds.com.hk:8122/api/";

      //TODO : Please check every time
      public static boolean isDummy = true;

    public static String SiteUrl="https://4e0s0i2r4n0u1s0.com/img/";

    public static String ShareImageUrl="https://4e0s0i2r4n0u1s0.com:8121/ViewImageVideoCerti?T=I&StoneId=";
    public static String ShareVideoUrl="https://4e0s0i2r4n0u1s0.com:8121/ViewImageVideoCerti?T=V&StoneId=";
    public static String ShareCertificateUrl="https://4e0s0i2r4n0u1s0.com:8121/ViewImageVideoCerti?T=C&StoneId=";
    public static int navigation_flag=0;

    public static String loginToken = "loginToken", loginId = "loginId", loginName = "loginName", loginPassword = "loginPassword", isSave = "isSave";

    // Start Search Parameters
    public static String stone_id = "stone_id", certi_no = "certi_no", shape = "shape", lab = "lab", colortype = "colortype", color = "color", intencitycolor = "intencitycolor", overtonecolor = "overtonecolor",
            fancycolor = "fancycolor", clarity = "clarity", cut = "cut", polish = "polish", symmetry = "symmetry", fi = "fi", bgm = "bgm", location = "location", inclusion = "inclusion",
            natts = "natts", image = "image", video = "video", pointer = "pointer", from_cts = "from_cts", to_cts = "to_cts", from_disc = "from_disc", to_disc = "to_disc", from_pcu = "from_pcu",
            to_pcu = "to_pcu", from_na = "from_na", to_na = "to_na", from_length = "from_length", to_length = "to_length", from_width = "from_width", to_width = "to_width", from_depth = "from_depth",
            to_depth = "to_depth", from_depth_p = "from_depth_p", to_depth_p = "to_depth_p", from_table_p = "from_table_p", to_table_p = "to_table_p", from_crang_p = "from_crang_p", to_crang_p = "to_crang_p",
            from_crht_p = "from_crht_p", to_crht_p = "to_crht_p", from_pavang_p = "from_pavang_p", to_pavang_p ="to_pavang_p", from_pavht_p ="from_pavht_p", to_pavht_p = "to_pavht_p", crown_inclusion = "crown_inclusion",
            crown_natts = "crown_natts", keytosymbol = "keytosymbol", loginIsAdmin = "loginIsAdmin", from_date = "from_date", to_date = "to_date", TransId = "TransId", Status = "Status", loginIsEmp = "loginIsEmp", loginIsCust = "loginIsCust",
            smart_search = "smart_search", comp_name = "comp_name", User_Type ="User_Type", Type = "Type",Search="Search", User_Status = "User_Status", isSignUp = "isSignUp", SignUpMsg = "SignUpMsg", CountryCode = "CountryCode"
            , table_open = "table_open", crown_open = "crown_open", pav_open = "pav_open", girdle_open = "girdle_open", keytosymboltrue = "keytosymboltrue", keytosymbolfalse = "keytosymbolfalse"
            , blank_symbol = "blank_symbol", blank_length = "blank_length", blank_width = "blank_width", blank_depth = "blank_depth", blank_depth_per = "blank_depth_per"
            , blank_table_per = "blank_table_per", blank_cr_ang = "blank_cr_ang", blank_cr_hit = "blank_cr_hit", blank_pav_ang = "blank_pav_ang", blank_pav_hit = "blank_pav_hit";
    // End Search Parameters


    public static String SearchStock="SearchStock",PlaceOrder="PlaceOrder",OrderHisrory="OrderHisrory",
            MyCart="MyCart",MyWishlist="MyWishlist",QuickSearch="QuickSearch",
            IsAssistByForAnyUser="IsAssistByForAnyUser",IsPrimary="IsPrimary",CompanyName="CompanyName",UserId="UserID"
            ,MessageId="MessageId",IsLogout="IsLogout",MessageShow="MessageShow",IsLoginTime="IsLoginTime";

    public static boolean isAdd=true;
    public static String iUserId="",cmpName="",uname="";

    public static int flag_searchResult=0;

    public static HashMap<String, String> hmPacketTrace = new HashMap<>();
    public static HashMap<String, String> hmStoneDetail = new HashMap<>();
    public static List<HashMap<String, String>> arrayCompareStone = new ArrayList<>();

    public static void resetParameters(Context context) {
        Pref.removeValue(context, shape);
        Pref.removeValue(context, lab);
        Pref.removeValue(context, color);
        Pref.removeValue(context, clarity);
        Pref.removeValue(context, cut);
        Pref.removeValue(context, polish);
        Pref.removeValue(context, symmetry);
        Pref.removeValue(context, fi);
        Pref.removeValue(context, bgm);
        Pref.removeValue(context, location);
        Pref.removeValue(context, inclusion);
        Pref.removeValue(context, natts);
        Pref.removeValue(context, image);
        Pref.removeValue(context, video);
        Pref.removeValue(context, stone_id);
        Pref.removeValue(context, smart_search);
        Pref.removeValue(context, certi_no);
        Pref.removeValue(context, pointer);
        Pref.removeValue(context, from_cts);
        Pref.removeValue(context, to_cts);
        Pref.removeValue(context, from_disc);
        Pref.removeValue(context, to_disc);
        Pref.removeValue(context, from_pcu);
        Pref.removeValue(context, to_pcu);
        Pref.removeValue(context, from_na);
        Pref.removeValue(context, to_na);
        Pref.removeValue(context, from_length);
        Pref.removeValue(context, to_length);
        Pref.removeValue(context, from_width);
        Pref.removeValue(context, to_width);
        Pref.removeValue(context, from_depth);
        Pref.removeValue(context, to_depth);
        Pref.removeValue(context, from_depth_p);
        Pref.removeValue(context, to_depth_p);
        Pref.removeValue(context, from_table_p);
        Pref.removeValue(context, to_table_p);

        Pref.removeValue(context, table_open);
        Pref.removeValue(context, crown_open);
        Pref.removeValue(context, pav_open);
        Pref.removeValue(context, girdle_open);
    }

    public static boolean isValidEmail(EditText email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email.getText().toString());
        return !Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches();
    }

    public static boolean isValidMobile(EditText view) {
        return view == null || view.getText().toString().trim().length() <= 14;
    }

//    public static Typeface setFontTypeface(Context context) {
//        return ResourcesCompat.getFont(context, R.font.average_sans);
//    }

    public static void LogoutDialog(final Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.layout_logout_dialog);

        Button btnYes = (Button) dialog.findViewById(R.id.btnYes);
        Button btnNo = (Button) dialog.findViewById(R.id.btnNo);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Pref.removeValue(context, Const.isSignUp);
                Pref.removeValue(context, Const.loginIsAdmin);
                Pref.removeValue(context, Const.loginId);
                Pref.removeValue(context, Const.loginToken);
                Pref.removeValue(context, Const.loginIsEmp);
                Pref.removeValue(context, Const.loginIsCust);

                Pref.removeValue(context, Const.SearchStock);
                Pref.removeValue(context, Const.PlaceOrder);
                Pref.removeValue(context, Const.QuickSearch);
                Pref.removeValue(context, Const.MyWishlist);
                Pref.removeValue(context, Const.OrderHisrory);
                Pref.removeValue(context, Const.MyCart);

                context.startActivity(new Intent(context, NewLoginActivity.class));
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public static void DisplayNoInternet(Context context) {
        Toast.makeText(context, "Internet is not available", Toast.LENGTH_SHORT).show();
    }

    public static boolean isEmpty(EditText view) {
        return view == null || view.getText().toString().trim().length() == 0;
    }

    public static void showErrorApiDialog(final Context context, final Runnable runnable) {
//        Toast.makeText(context, "Internet is not available", Toast.LENGTH_SHORT).show();
//        AlertDialog alertDialog = new AlertDialog.Builder(context, R.style.Theme_Dialog).create();
//        alertDialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
//        alertDialog.getWindow().setBackgroundDrawable(setBackgoundBorder(0, 20, Color.WHITE, Color.WHITE));
//        alertDialog.setTitle(R.string.app_name_dialog);
//        alertDialog.setMessage("Internet is not available");
//        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//               // new Handler().post(runnable);
//            }
//        });
//        alertDialog.show();
//        alertDialog.getButton(alertDialog.BUTTON1).setTextColor(Color.BLACK);
        //  Pref.removeValue(context, Const.isSave);
        Pref.removeValue(context, Const.isSignUp);
        Pref.removeValue(context, Const.loginIsAdmin);
        Pref.removeValue(context, Const.loginId);
        Pref.removeValue(context, Const.loginToken);
        //  Pref.removeValue(context, Const.loginName);
        //  Pref.removeValue(context, Const.loginPassword);
        Pref.removeValue(context, Const.loginIsEmp);
        Pref.removeValue(context, Const.loginIsCust);


        Pref.removeValue(context, Const.SearchStock);
        Pref.removeValue(context, Const.PlaceOrder);
        Pref.removeValue(context, Const.QuickSearch);
        Pref.removeValue(context, Const.MyWishlist);
        Pref.removeValue(context, Const.OrderHisrory);
        Pref.removeValue(context, Const.MyCart);

        context.startActivity(new Intent(context, NewLoginActivity.class));
    }

    public static void showErrorDialog(final Context context, String message) {
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
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public static void showErrorDialog(final Context context, String title, String message) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_error_dialog);

        TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
        tvTitle.setText(title);

        TextView tvMessage = (TextView) dialog.findViewById(R.id.tvMessage);
        tvMessage.setText(message);

        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public static String notNullString(String fieldName, String hintValue) {
        String filanlValue = "";
        filanlValue = fieldName == null ? hintValue : fieldName.equalsIgnoreCase("null") ? hintValue : fieldName.equalsIgnoreCase("") ? hintValue : fieldName;
        return filanlValue;
    }

    public static boolean isNotNull(String fieldName) {
        return fieldName == null ? false : fieldName.equalsIgnoreCase("null") ? false : fieldName.equalsIgnoreCase("") ? false : true;
    }

    public static long timeTomss(String ddmmmyy) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        long timeInMilliseconds = 0;
        try {
            Date mDate = sdf.parse(ddmmmyy);
            timeInMilliseconds = mDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeInMilliseconds;
    }

    public static String timeConvert(String time12) {
        //12 to 24
        String newTime = "";
        try {
            Date date = null;
            date = new SimpleDateFormat("hh:mm a").parse(time12);
            newTime = new SimpleDateFormat("HH:mm").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newTime;
    }

    public static String tConvert(String time24) {
        //24 to 12
        String newTime = "";
        try {
            Date date = null;
            date = new SimpleDateFormat("HH:mm").parse(time24);
            newTime = new SimpleDateFormat("hh:mm a").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newTime;
    }

    public static String getMonthAbbr(int monthOfYear) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, monthOfYear);
        return new SimpleDateFormat("LLL").format(c.getTime());
    }

    public static String dateFormateChange(String ddmmmyyyy) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        String str = "";
        try {
            Date date = inputFormat.parse(ddmmmyyyy);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static boolean compareDate(String selectedDate, String otherDate) {
        boolean b = false;
        Date strDate = null;
        try {
            strDate = new SimpleDateFormat("dd-MMM-yyyy").parse(selectedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (timeToms(otherDate) > strDate.getTime()) {
            b = true;
        } else {
            b = false;
        }
        return b;
    }

    public static boolean compareTime(String time) {
        boolean b = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        Date EndTime = null;
        try {
            EndTime = dateFormat.parse(time);
            Date CurrentTime = dateFormat.parse(dateFormat.format(new Date()));
            if (CurrentTime.after(EndTime)) {
                b = true;
            } else {
                b = false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return b;
    }

    public static long timeToms(String ddmmmyy) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        long timeInMilliseconds = 0;
        try {
            Date mDate = sdf.parse(ddmmmyy);
            timeInMilliseconds = mDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeInMilliseconds;
    }

    public static String dfChange(String ddmmyyyy) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String str = "";
        try {
            Date date = inputFormat.parse(ddmmyyyy);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static int nameTonumMonth(String monthNameMMM) {
        int monthNumber = 0;
        try {
            Date date = new SimpleDateFormat("LLL").parse(monthNameMMM);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            monthNumber = cal.get(Calendar.MONTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return monthNumber;
    }

    public static boolean isOdd(int val) {
        return (val & 0x01) != 0;
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getWindow().getDecorView().getRootView().getWindowToken(), 0);
    }

    public static boolean isInternetConnected(Context context) {
        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED || connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {
                return true;
            } else {
            }
        } catch (Exception e) {
            ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting())
                return true;
            else
                return false;
        }
        return false;
    }

    public static void clearForm(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText) view).setText("");
            }
            if (view instanceof ViewGroup && (((ViewGroup) view).getChildCount() > 0))
                clearForm((ViewGroup) view);
        }
        group.clearFocus();
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    //textView.setText(new SpannableString(textView.getText()));
    //TextJustification.justify(textView);
    public static void justify(final TextView textView) {

        final AtomicBoolean isJustify = new AtomicBoolean(false);

        final String textString = textView.getText().toString();

        final TextPaint textPaint = textView.getPaint();

        final SpannableStringBuilder builder = new SpannableStringBuilder();

        textView.post(new Runnable() {
            @Override
            public void run() {

                if (!isJustify.get()) {

                    final int lineCount = textView.getLineCount();
                    final int textViewWidth = textView.getWidth();

                    for (int i = 0; i < lineCount; i++) {

                        int lineStart = textView.getLayout().getLineStart(i);
                        int lineEnd = textView.getLayout().getLineEnd(i);

                        String lineString = textString.substring(lineStart, lineEnd);

                        if (i == lineCount - 1) {
                            builder.append(new SpannableString(lineString));
                            break;
                        }

                        String trimSpaceText = lineString.trim();
                        String removeSpaceText = lineString.replaceAll(" ", "");

                        float removeSpaceWidth = textPaint.measureText(removeSpaceText);
                        float spaceCount = trimSpaceText.length() - removeSpaceText.length();

                        float eachSpaceWidth = (textViewWidth - removeSpaceWidth) / spaceCount;

                        SpannableString spannableString = new SpannableString(lineString);
                        for (int j = 0; j < trimSpaceText.length(); j++) {
                            char c = trimSpaceText.charAt(j);
                            if (c == ' ') {
                                Drawable drawable = new ColorDrawable(0x00ffffff);
                                drawable.setBounds(0, 0, (int) eachSpaceWidth, 0);
                                ImageSpan span = new ImageSpan(drawable);
                                spannableString.setSpan(span, j, j + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }
                        }

                        builder.append(spannableString);
                    }

                    textView.setText(builder);
                    isJustify.set(true);
                }
            }
        });
    }

    public static void showProgress(Context context) {
        try {
            if (Const.custDailog != null && Const.custDailog.isShowing())
                Const.custDailog.dismiss();

            if (Const.custDailog == null)
                Const.custDailog = new CustomDialog(context);
            Const.custDailog.setCancelable(false);
            Const.custDailog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isProgressShowing() {
        if (Const.custDailog == null) {
            return false;
        } else if (Const.custDailog.isShowing()) {
            return true;
        } else {
            return false;
        }
    }

    public static void dismissProgress() {
        if (Const.custDailog != null && Const.custDailog.isShowing())
            Const.custDailog.dismiss();
        Const.custDailog = null;
    }

    public static GradientDrawable setBackgoundRightBorder(int width, int radius, int backcolor, int colorcorner) {
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setColor(backcolor);
        gd.setStroke(width, colorcorner);
        gd.setCornerRadii(new float[]{0, radius, radius, radius, radius, radius, radius, 0});
        return gd;
    }

    public static GradientDrawable setBackgoundLeftBorder(int width, int radius, int backcolor, int colorcorner) {
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setColor(backcolor);
        gd.setStroke(width, colorcorner);
        gd.setCornerRadii(new float[]{radius, radius, radius, 0, 0, radius, radius, radius});
        return gd;
    }

    public static GradientDrawable setBackgoundBorder(int width, int radius, int backcolor, int colorcorner) {
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setColor(backcolor);
        gd.setStroke(width, colorcorner);
        String radiuss = radius + 0f + "";
        gd.setCornerRadius(Float.parseFloat(radiuss));
        return gd;
    }

    //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    //StrictMode.setThreadPolicy(policy);
    public static Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return model.toUpperCase();
        } else {
            return (manufacturer + " " + model).toUpperCase();
        }
    }

    public static String getIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    public static int getAppVersion(Context context) {
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            int versionCode = info.versionCode;
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return -999;
        }
    }

    public static Typeface setFontTypeface(Context context) {
        return ResourcesCompat.getFont(context, R.font.proxima_nova);
    }

    public static String convertStreamToString(InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
//        BufferedReader r = new BufferedReader(new InputStreamReader(is));
//        StringBuilder total = new StringBuilder();
//        for (String line; (line = r.readLine()) != null; ) {
//            total.append(line).append('\n');
//        }
//        return total.toString();
    }

    public static void callGetApi(Context context, String url, final VolleyCallback callback) {
        final StringRequest stringReq = new StringRequest(Request.Method.GET, BaseUrl + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = XML.toJSONObject(response + "");
                    callback.onSuccessResponse(json.toString() + "");
                } catch (JSONException je) {
                    callback.onSuccessResponse("");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailerResponse(error.getMessage() + "");
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
        };
        Volley.newRequestQueue(context).add(stringReq.setRetryPolicy(new DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));
    }

    public static void callPostApi(final Context context, String url, final Map<String, String> params, final VolleyCallback callback) {
        final StringRequest stringReq = new StringRequest(Request.Method.POST, BaseUrl + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();
                response = Html.fromHtml(response).toString();
                callback.onSuccessResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailerResponse(error.getMessage() + " error1111");
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                String accesstoken = Pref.getStringValue(context, Const.loginToken, "");
                params.put("Authorization", "Bearer " + accesstoken);
                //  params.put("cookie", "ASP.NET_SessionId=" + Pref.getStringValue(context, Const.loginToken, "") + ";Path=/;HttpOnly");
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }

        };
        Volley.newRequestQueue(context).add(stringReq.setRetryPolicy(new DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));
    }

    public static void callPostApiPlaceOrder(final Context context, String url, final Map<String, String> params, final VolleyCallback callback) {
        final StringRequest stringReq = new StringRequest(Request.Method.POST, BaseUrl + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
             //   response = response.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();
             //   response = Html.fromHtml(response).toString();
                callback.onSuccessResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailerResponse(error.getMessage() + "");
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                String accesstoken = Pref.getStringValue(context, Const.loginToken, "");
                params.put("Authorization", "Bearer " + accesstoken);
                //  params.put("cookie", "ASP.NET_SessionId=" + Pref.getStringValue(context, Const.loginToken, "") + ";Path=/;HttpOnly");
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }


        };
        Volley.newRequestQueue(context).add(stringReq.setRetryPolicy(new DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));
    }

    public static void callPostApiPlaceOrderSupplier(final Context context, String url, JSONObject jsonBody, final VolleyCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String URL = BaseUrl + url;
        final String requestBody = jsonBody.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("VOLLEY", response);
                callback.onSuccessResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
                callback.onFailerResponse(error.getMessage() + "");
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                String accesstoken = Pref.getStringValue(context, Const.loginToken, "");
                params.put("Authorization", "Bearer " + accesstoken);
                //  params.put("cookie", "ASP.NET_SessionId=" + Pref.getStringValue(context, Const.loginToken, "") + ";Path=/;HttpOnly");
                return params;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString =
                            new String(
                                    response.data,
                                    HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                    return Response.success(
                            jsonString, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }
        };

        Volley.newRequestQueue(context).add(stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));
    }

    public static void callPostApiSupplierOrderSupplier(final Context context, String url, JSONObject jsonBody, final VolleyCallback callback) {
        String URL = BaseUrl + url;
        final String requestBody = jsonBody.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("VOLLEY", response);
                callback.onSuccessResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
                callback.onFailerResponse(error.getMessage() + "");
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                String accesstoken = Pref.getStringValue(context, Const.loginToken, "");
                params.put("Authorization", "Bearer " + accesstoken);
                //  params.put("cookie", "ASP.NET_SessionId=" + Pref.getStringValue(context, Const.loginToken, "") + ";Path=/;HttpOnly");
                return params;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString =
                            new String(
                                    response.data,
                                    HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                    return Response.success(
                            jsonString, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }
        };

        Volley.newRequestQueue(context).add(stringRequest.setRetryPolicy(new DefaultRetryPolicy(200000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));
    }

    public static void callPostApiLogin(final Context context, String url, final Map<String, String> params, final VolleyCallback callback) {
        final StringRequest stringReq = new StringRequest(Request.Method.POST, BaseUrl + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();
                response = Html.fromHtml(response).toString();
                callback.onSuccessResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                try {
                    if(error.networkResponse != null){
                        String responseBody = new String(error.networkResponse.data, "utf-8");
                        responseBody = responseBody.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim().replace("</td>","\n");
                        responseBody = Html.fromHtml(responseBody).toString();
                        callback.onSuccessResponse(responseBody);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
//                callback.onFailerResponse(error.getMessage() + "");
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                String accesstoken = Pref.getStringValue(context, Const.loginToken, "");
                params.put("Authorization", "Bearer " + accesstoken);
                //  params.put("cookie", "ASP.NET_SessionId=" + Pref.getStringValue(context, Const.loginToken, "") + ";Path=/;HttpOnly");
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }

        };
        Volley.newRequestQueue(context).add(stringReq.setRetryPolicy(new DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));
    }

    public static void callPostApi1(Context context, String url, final Map<String, String> params, final VolleyCallback callback) {
        final StringRequest stringReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();
                response = Html.fromHtml(response).toString();
                callback.onSuccessResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailerResponse("Connection Error");
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }

        };
        Volley.newRequestQueue(context).add(stringReq.setRetryPolicy(new DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)).setTag("volly")).setTag("volly");
    }

    public static void callPostApi2(Context context, String url, final Map<String, String> params, final VolleyCallback callback) {
        final StringRequest stringReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();
                response = Html.fromHtml(response).toString();
                callback.onSuccessResponse(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailerResponse("Connection Error");
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }

        };
        Volley.newRequestQueue(context).add(stringReq.setRetryPolicy(new DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));

    }

    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return notNullString(encoded, "");
    }

    public static String CamelCase(String sh) {
        String finalSt="";
        for(int k=0;k<sh.length();k++)
        {
            if (k==0) {
                finalSt = finalSt + String.valueOf(sh.charAt(k)).toUpperCase();
            }
            else
            {
                finalSt = finalSt + String.valueOf(sh.charAt(k)).toLowerCase();
            }
        }
        return finalSt;
    }
}
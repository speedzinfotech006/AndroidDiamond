package com.dnk.shairugems;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dnk.shairugems.Class.BindData;
import com.dnk.shairugems.Class.DownloadImage;
import com.dnk.shairugems.Class.FooRequest;
import com.dnk.shairugems.Class.LoginData;
import com.dnk.shairugems.Class.MailRequest;
import com.dnk.shairugems.Utils.Const;
import com.dnk.shairugems.Utils.Pref;
import com.dnk.shairugems.Utils.RestClient;
import com.dnk.shairugems.Volly.VolleyCallback;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dnk.shairugems.Utils.Const.setBackgoundBorder;

public class SearchResult4Activity extends BaseDrawerActivity {

    ImageView imgSumm, imgMenu, imgOMenu, imgViewCount, imgLayout, imgSearch;
    LinearLayout llSumm, llPlus, llViewCount, llModify, llCart, llHorizontal, llPlaceOrder, llDownload, llEmailStone;
    RelativeLayout rlMain;
    TextView tvTitle, tvTotalCount, tvTotalPcs, tvTotalCts, tvTotalAvgDiscPer, tvTotalPricePerCts, tvTotalAmt;
    TextView Result,tvCountCart;
    EditText etSearch;
    CheckBox cbSelectAll;
    RecyclerView rvList;
    LinearLayoutManager lm;
    ResultAdp adp;
    com.github.clans.fab.FloatingActionMenu Menu;
    com.github.clans.fab.FloatingActionButton llShare,llWish,llCompare,llCopy,llStatus, llDownloadStock;
    LinearLayout.LayoutParams parms, parms2;
    HashMap<Integer, String> hmSelected = new HashMap<>();
    ArrayList<HashMap<String, String>> maps = new ArrayList<>();
    int pastVisiblesItems, visibleItemCount, totalItemCount, pageCount = 1, totalSize = 0;
    Double rap = 0.0, totalRap = 0.0;
    String totalPcs = "",totalCts = "0", totalAmount = "0", totalPricePerCts = "0", totalAvgDicPer = "0";
    boolean isChange = true;
    DecimalFormat df = new DecimalFormat("#,##,###.00");
    DecimalFormat df1 = new DecimalFormat("##,###,###");
    List<HashMap<String, String>> arrayCount;
    ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();
    String SearchCriteria = "";
    boolean isFABOpen = false;

    private Dialog d;
    private Dialog d2;
    ImageView imgPlus;
    LinearLayout Menu_Share,Menu_Download,Menu_Wishlist,Menu_Compare,Menu_Copy,Menu_Stock,Menu_Clear;
    View div_Share,div_Download,div_Wishlist,div_Compare,div_Copy,div_Stock;

    long downID = 0;
    String randomNumber = "", downloadNumber = "";
    DownloadManager downloadManager;
    Double cts, amt, avgDisc, ppc;
    Context context = SearchResult4Activity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_search_result4, frameLayout);

        Log.e("Activity Open ","Search Result 4 Activity");

        initView();

        etSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (etSearch.getCompoundDrawables()[2] != null) {
                        if (event.getRawX() >= (etSearch.getRight() - etSearch.getCompoundDrawables()[2].getBounds().width())) {
                            if (Const.isEmpty(etSearch)) {
                                etSearch.setVisibility(View.GONE);
                                imgSearch.setVisibility(View.VISIBLE);
                                tvTitle.setVisibility(View.VISIBLE);
                                Const.hideSoftKeyboard(SearchResult4Activity.this);
                                etSearch.clearFocus();
                            } else {
                                etSearch.setText("");
                            }
                            return true;
                        }
                    }
                }
                return false;
            }
        });

        etSearch.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!Const.isEmpty(etSearch)) {
                        totalSize = 0;
                        pageCount = 1;
                        maps = new ArrayList<>();
                        clearTotal();
                        cbSelectAll.setChecked(false);
                        Const.resetParameters(context);
                        //   if (etSearch.getText().toString().matches(".*[A-Za-z].*")) {
                        Pref.setStringValue(context, Const.stone_id, etSearch.getText().toString().replaceAll(" ",",").replaceAll("\n",",").replaceAll("\t",",").replaceAll("\r",",").replaceAll("  ",","));
                        //  }
                        getSearchResult();
                    }
                    return true;
                }
                return false;
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
                            hmSelected.put(i, maps.get(i).get("stone_ref_no"));
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


        getSearchResult();
        getHomeCount();
    }

    private void initView() {
        Menu = findViewById(R.id.Menu);
        rlMain = findViewById(R.id.rlMain);
        imgSumm = findViewById(R.id.imgSumm);
        imgMenu = findViewById(R.id.imgMenu);
        imgOMenu = findViewById(R.id.imgOMenu);
        imgPlus = findViewById(R.id.imgPlus);
        etSearch = findViewById(R.id.etSearch);
        cbSelectAll = findViewById(R.id.cbSelectAll);
        tvCountCart = findViewById(R.id.tvCountCart);
        tvTotalPricePerCts = findViewById(R.id.tvTotalPricePerCts);
        tvTotalAmt = findViewById(R.id.tvTotalAmt);
        tvTotalCts = findViewById(R.id.tvTotalCts);
        tvTotalAvgDiscPer = findViewById(R.id.tvTotalAvgDiscPer);
        tvTotalPcs = findViewById(R.id.tvTotalPcs);
        imgSearch = findViewById(R.id.imgSearch);
        imgViewCount = findViewById(R.id.imgViewCount);
        tvTitle = findViewById(R.id.tvTitle);
        tvTotalCount = findViewById(R.id.tvTotalCount);
        imgLayout = findViewById(R.id.imgLayout);
        llSumm = findViewById(R.id.llSumm);
        llViewCount = findViewById(R.id.llViewCount);
        llHorizontal = findViewById(R.id.llHorizontal);
        llPlus = findViewById(R.id.llPlus);
        llModify = findViewById(R.id.llModify);
        llPlaceOrder = findViewById(R.id.llPlaceOrder);
        llDownload = findViewById(R.id.llDownload);
        llEmailStone = findViewById(R.id.llEmailStone);

        llCart = findViewById(R.id.llCart);
        rvList = findViewById(R.id.rvList);

        llShare = findViewById(R.id.llShare);
        llWish = findViewById(R.id.llWish);
        llCompare = findViewById(R.id.llCompare);
        llCopy = findViewById(R.id.llCopy);
        llStatus = findViewById(R.id.llStatus);
        llDownloadStock = findViewById(R.id.llDownloadStock);

        lm = new LinearLayoutManager(context);
        rvList.setLayoutManager(lm);

        imgMenu.setOnClickListener(clickListener);
        imgViewCount.setOnClickListener(clickListener);
        imgOMenu.setOnClickListener(clickListener);
        llCart.setOnClickListener(clickListener);
        imgSearch.setOnClickListener(clickListener);
        llModify.setOnClickListener(clickListener);
        llPlaceOrder.setOnClickListener(clickListener);
        llDownload.setOnClickListener(clickListener);
        llEmailStone.setOnClickListener(clickListener);
        imgLayout.setOnClickListener(clickListener);

        llPlus.setOnClickListener(clickListener);
        llShare.setOnClickListener(clickListener);
        llWish.setOnClickListener(clickListener);
        llCompare.setOnClickListener(clickListener);
        llCopy.setOnClickListener(clickListener);
        llStatus.setOnClickListener(clickListener);
        llDownloadStock.setOnClickListener(clickListener);

        llCart.setBackgroundDrawable(Const.setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));
        llModify.setBackgroundDrawable(Const.setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));

        llShare.setBackgroundDrawable(Const.setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));
        llWish.setBackgroundDrawable(Const.setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));
        llCompare.setBackgroundDrawable(Const.setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));
        llCopy.setBackgroundDrawable(Const.setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));
        llStatus.setBackgroundDrawable(Const.setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));
        llDownloadStock.setBackgroundDrawable(Const.setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));

        parms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        parms2 = new LinearLayout.LayoutParams(displayMetrics.widthPixels, ViewGroup.LayoutParams.MATCH_PARENT);
        rvList.setLayoutParams(parms2);

        Drawable wrappedDrawable = DrawableCompat.wrap(AppCompatResources.getDrawable(context, R.drawable.ic_close));
        etSearch.setCompoundDrawablesWithIntrinsicBounds(null, null, wrappedDrawable, null);

        d = new Dialog(this, R.style.Dialog);
        WindowManager.LayoutParams wmpl = d.getWindow().getAttributes();
        LayoutInflater inflate = LayoutInflater.from(context);
        d.setCancelable(true);
        View view = inflate.inflate(R.layout.left_menu_new, null);
        wmpl.gravity = Gravity.CENTER | Gravity.CENTER_HORIZONTAL;
        d.setContentView(view);

//        d2 = new Dialog(this, R.style.Dialog);
//        WindowManager.LayoutParams wmpl2 = d2.getWindow().getAttributes();
//        LayoutInflater inflate2 = LayoutInflater.from(context);
//        d2.setCancelable(true);
//        View view2 = inflate2.inflate(R.layout.layout_dialog_summary, null);
//        wmpl.gravity = Gravity.CENTER | Gravity.CENTER_HORIZONTAL;
//        d2.setContentView(view2);

        Menu_Share =  view.findViewById(R.id.Menu_Share);
        Menu_Download =  view.findViewById(R.id.Menu_Download);
        Menu_Wishlist =  view.findViewById(R.id.Menu_Wishlist);
        Menu_Compare =  view.findViewById(R.id.Menu_Compare);
        Menu_Copy =  view.findViewById(R.id.Menu_Copy);
        Menu_Stock =  view.findViewById(R.id.Menu_Stock);
        Menu_Clear =  view.findViewById(R.id.Menu_Clear);

        div_Share =  view.findViewById(R.id.div_Share);
        div_Download =  view.findViewById(R.id.div_Download);
        div_Wishlist =  view.findViewById(R.id.div_Wishlist);
        div_Compare =  view.findViewById(R.id.div_Compare);
        div_Copy =  view.findViewById(R.id.div_Copy);
        div_Stock =  view.findViewById(R.id.div_Stock);

        Menu_Share.setVisibility(View.VISIBLE);
        div_Share.setVisibility(View.VISIBLE);
        Menu_Download.setVisibility(View.VISIBLE);
        div_Download.setVisibility(View.VISIBLE);
        Menu_Wishlist.setVisibility(View.VISIBLE);
        div_Wishlist.setVisibility(View.VISIBLE);
        Menu_Compare.setVisibility(View.VISIBLE);
        div_Compare.setVisibility(View.VISIBLE);
        Menu_Copy.setVisibility(View.VISIBLE);
        div_Copy.setVisibility(View.VISIBLE);
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

        imgSumm.setOnClickListener(clickListener);

    }

    private void openPopupMenu() {
        Context wrapper = new ContextThemeWrapper(context, R.style.PopupMenu);
        PopupMenu popup = new PopupMenu(wrapper, imgOMenu);
        popup.getMenuInflater().inflate(R.menu.result_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getTitle().toString().equalsIgnoreCase("Reset")) {
                    clearTotal();
                    cbSelectAll.setChecked(false);
                    maps = new ArrayList<>();
                    pageCount = 1;
                    totalSize = 0;
                    etSearch.setText("");
                    etSearch.setVisibility(View.GONE);
                    imgSearch.setVisibility(View.VISIBLE);
                    tvTitle.setVisibility(View.VISIBLE);
                    Const.hideSoftKeyboard(SearchResult4Activity.this);
                    etSearch.clearFocus();
                    Const.resetParameters(context);
                    getSearchResult();
                } else if (item.getTitle().toString().equalsIgnoreCase("Clear Selection")) {
                    cbSelectAll.setChecked(false);
                    clearTotal();
                    if (adp != null) {
                        adp.notifyDataSetChanged();
                    }
                } else if (item.getTitle().toString().equalsIgnoreCase("Compare Stone")) {
                    if (hmSelected.size() == 0) {
                        Const.showErrorDialog(context, "Please select at least 1 item");
                    } else if (hmSelected.size() > 4) {
                        Const.showErrorDialog(context, "You can compare maximum 4 item");
                    } else {
                        Const.arrayCompareStone = new ArrayList<>();
                        for (int i = 0; i < maps.size(); i++) {
                            if (hmSelected.get(i) != null) {
                                Const.arrayCompareStone.add(maps.get(i));
                                if (hmSelected.size() == Const.arrayCompareStone.size()) {
                                    break;
                                }
                            }
                        }
                        startActivity(new Intent(context, CompareStoneActivity.class));
                    }
                } else if (item.getTitle().toString().equalsIgnoreCase("Status Information")) {
                    statusInfoAlert();
                } else if (item.getTitle().toString().equalsIgnoreCase("Share")) {
                    if (hmSelected.size() > 0) {
                        shareDialog();
                    } else {
                        Const.showErrorDialog(context, "Please select at least 1 stone");
                    }
                }
                return true;
            }
        });
        popup.show();
    }

    private void statusInfoAlert() {
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        dialog.setContentView(R.layout.layout_status_info);
//        TextView tvC = dialog.findViewById(R.id.tvC);
//        TextView tvNA = dialog.findViewById(R.id.tvNA);
//        TextView tvCtext = dialog.findViewById(R.id.tvCtext);
//        TextView tvNAtext = dialog.findViewById(R.id.tvNAtext);
//        LinearLayout llCa = dialog.findViewById(R.id.llCa);
//        llCa.setVisibility(View.GONE);
//        tvNA.setBackgroundDrawable(Const.setBackgoundBorder(0, 10, getResources().getColor(R.color.green), Color.TRANSPARENT));
//        tvC.setBackgroundDrawable(Const.setBackgoundBorder(0, 10, getResources().getColor(R.color.ao), Color.TRANSPARENT));
//        tvC.setText("O");
//        tvCtext.setText("Offer");
//        tvC.setTextColor(Color.BLACK);
//        tvNA.setText("A");
//        tvNAtext.setText("Available");

        dialog.show();
    }

    private void getSearchResult() {
        Const.showProgress(context);
        final Map<String, String> map = new HashMap<>();
        map.put("SmartSearch", Pref.getStringValue(context, Const.stone_id, ""));
        map.put("CertiNo", Pref.getStringValue(context, Const.certi_no, ""));
        map.put("Shape", Pref.getStringValue(context, Const.shape, ""));
        map.put("Pointer", Pref.getStringValue(context, Const.pointer, ""));
        map.put("Color", Pref.getStringValue(context, Const.color, ""));
        map.put("Clarity", Pref.getStringValue(context, Const.clarity, ""));
        map.put("Cut", Pref.getStringValue(context, Const.cut, ""));
        map.put("Polish", Pref.getStringValue(context, Const.polish, ""));
        map.put("Symm", Pref.getStringValue(context, Const.symmetry, ""));
        map.put("Fls", Pref.getStringValue(context, Const.fi, ""));
        map.put("Lab", Pref.getStringValue(context, Const.lab, ""));
        map.put("Inclusion", Pref.getStringValue(context, Const.inclusion, ""));
        map.put("Natts", Pref.getStringValue(context, Const.natts, ""));
        map.put("Shade", "");
        map.put("FromCts", Pref.getStringValue(context, Const.from_cts, ""));
        map.put("ToCts", Pref.getStringValue(context, Const.to_cts, ""));
        map.put("FormDisc", Pref.getStringValue(context, Const.from_disc, ""));
        map.put("ToDisc", Pref.getStringValue(context, Const.to_disc, ""));
        map.put("FormPricePerCts", Pref.getStringValue(context, Const.from_pcu, ""));
        map.put("ToPricePerCts", Pref.getStringValue(context, Const.to_pcu, ""));
        map.put("FormNetAmt", Pref.getStringValue(context, Const.from_na, ""));
        map.put("ToNetAmt", Pref.getStringValue(context, Const.to_na, ""));
        map.put("FormDepth", Pref.getStringValue(context, Const.from_depth, ""));
        map.put("ToDepth", Pref.getStringValue(context, Const.to_depth, ""));
        map.put("FormLength", Pref.getStringValue(context, Const.from_length, ""));
        map.put("ToLength", Pref.getStringValue(context, Const.to_length, ""));
        map.put("FormWidth", Pref.getStringValue(context, Const.from_width, ""));
        map.put("ToWidth", Pref.getStringValue(context, Const.to_width, ""));
        map.put("FormDepthPer", Pref.getStringValue(context, Const.from_depth_p, ""));
        map.put("ToDepthPer", Pref.getStringValue(context, Const.to_depth_p, ""));
        map.put("FormTablePer", Pref.getStringValue(context, Const.from_table_p, ""));
        map.put("ToTablePer", Pref.getStringValue(context, Const.to_table_p, ""));
        map.put("HasImage", Pref.getStringValue(context, Const.image, ""));
        map.put("HasHDMovie", Pref.getStringValue(context, Const.video, ""));
        map.put("IsPromotion", "");
        map.put("CrownInclusion", Pref.getStringValue(context, Const.crown_inclusion, ""));
        map.put("CrownNatts", Pref.getStringValue(context, Const.crown_natts, ""));
        map.put("Luster", "");
        map.put("Location", Pref.getStringValue(context, Const.location, ""));
        map.put("PageNo", pageCount + "");
        map.put("TokenNo", Pref.getStringValue(context, Const.loginToken, ""));
        map.put("StoneStatus", "");
        map.put("FromCrownAngle", Pref.getStringValue(context, Const.from_crang_p, ""));
        map.put("ToCrownAngle", Pref.getStringValue(context, Const.to_crang_p, ""));
        map.put("FromCrownHeight", Pref.getStringValue(context, Const.from_crht_p, ""));
        map.put("ToCrownHeight", Pref.getStringValue(context, Const.to_crht_p, ""));
        map.put("FromPavAngle", Pref.getStringValue(context, Const.from_pavang_p, ""));
        map.put("ToPavAngle", Pref.getStringValue(context, Const.to_pavang_p, ""));
        map.put("FromPavHeight", Pref.getStringValue(context, Const.from_pavht_p, ""));
        map.put("ToPavHeight", Pref.getStringValue(context, Const.to_pavht_p, ""));
        map.put("BGM", Pref.getStringValue(context, Const.bgm, ""));
        map.put("BLACK", "");
        map.put("KeyToSymbol", Pref.getStringValue(context, Const.keytosymbol, ""));
        map.put("ReviseStockFlag", "");
        map.put("ColorType", "Regular");
        map.put("IsTripalEx", "false");
        map.put("IsTripalVg", "false");
        Const.callPostApi(context, "Stock/GetSearchStock", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Const.dismissProgress();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.optString("Data") != null && !object.optString("Data").equalsIgnoreCase("[]")) {
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
                                //  totalPricePerCts = String.valueOf(df.format(Double.parseDouble(String.valueOf(obj.optDouble("AVG_PRICE_PER_CTS")))));
                                //  totalAmount = String.valueOf(df1.format(Double.parseDouble(String.valueOf(obj.optDouble("TOT_NET_AMOUNT")))));

                                totalPcs = String.format("%,.0f", obj.optDouble("TOT_PCS"));
                                totalCts = String.format("%,.2f", obj.optDouble("TOT_CTS"));
                                totalAmount = "$ " + String.format("%,.0f", obj.optDouble("TOT_NET_AMOUNT"));
                                totalPricePerCts = "$ " + String.format("%,.2f", obj.optDouble("AVG_PRICE_PER_CTS"));
                                totalAvgDicPer = String.format("%.2f", obj.optDouble("AVG_SALES_DISC_PER")) + " %";
                                totalRap = obj.optDouble("TOT_RAP_AMOUNT");
                                tvTotalCount.setText("Showing 1 to " + maps.size() + " of " + obj.optString("TOT_PCS") + " entries");
                            }
//                            }
                            tvTotalCts.setText(totalCts);
                            tvTotalAmt.setText(totalAmount);
                            tvTotalPricePerCts.setText(totalPricePerCts);
                            tvTotalAvgDiscPer.setText(totalAvgDicPer);
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
                        tvTotalCount.setText("");
                        llViewCount.setVisibility(View.GONE);
                        llSumm.setVisibility(View.GONE);
                        NoStockshowDialog(context);
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

    public void NoStockshowDialog(final Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_no_stock_found);

        RadioGroup rgNoStock = (RadioGroup) dialog.findViewById(R.id.rgNoStock);
        final RadioButton rbNotifyMe = (RadioButton) dialog.findViewById(R.id.rbNotifyMe);
        RadioButton rbEmailMe = (RadioButton) dialog.findViewById(R.id.rbEmailMe);
        ImageView ivClose = (ImageView) dialog.findViewById(R.id.ivClose);
        final EditText etValidityDate = (EditText) dialog.findViewById(R.id.etValidityDate);

        Animation anim = AnimationUtils.loadAnimation(context, R.anim.rotate);
        ivClose.startAnimation(anim);

        etValidityDate.setText(new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date()));

        etValidityDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etValidityDate.performClick();
                }
            }
        });

        final Button btnSave = (Button) dialog.findViewById(R.id.btnSave);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);

        etValidityDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dd, mm, yy;
                if (Const.isEmpty(etValidityDate)) {
                    dd = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                    mm = Calendar.getInstance().get(Calendar.MONTH);
                    yy = Calendar.getInstance().get(Calendar.YEAR);
                } else {
                    String[] ddmmyy = etValidityDate.getText().toString().trim().split("-");
                    dd = Integer.parseInt(ddmmyy[0]);
                    mm = Const.nameTonumMonth(ddmmyy[1]);
                    yy = Integer.parseInt(ddmmyy[2]);
                }
                DatePickerDialog date = new DatePickerDialog(context, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Integer dd = datePicker.getDayOfMonth();
                        String d = dd.toString().length() == 1 ? "0" + dd : dd + "";
                        etValidityDate.setText(d + "-" + Const.getMonthAbbr(datePicker.getMonth()) + "-" + datePicker.getYear());
                    }
                }, yy, mm, dd);
                date.show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rbNotifyMe.isChecked()){
                    NoStockFound("Notify Me", etValidityDate.getText().toString().trim());
                }else{
                    NoStockFound("Email Me", etValidityDate.getText().toString().trim());
                }
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SearchFilterActivity.class));
                dialog.dismiss();
            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SearchFilterActivity.class));
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void NoStockFound(final String Action, final String ValidityDate){
        FooRequest fooRequest = new FooRequest();
        fooRequest.SmartSearch = Pref.getStringValue(context, Const.stone_id, "");
        fooRequest.StoneID = "";
        fooRequest.CertiNo = Pref.getStringValue(context, Const.certi_no, "");
        fooRequest.Shape = Pref.getStringValue(context, Const.shape, "");
        fooRequest.Pointer = Pref.getStringValue(context, Const.pointer, "");
        fooRequest.Color = Pref.getStringValue(context, Const.color, "");;
        fooRequest.Clarity = Pref.getStringValue(context, Const.clarity, "");;
        fooRequest.Cut = Pref.getStringValue(context, Const.cut, "");
        fooRequest.Polish = Pref.getStringValue(context, Const.polish, "");
        fooRequest.Symm = Pref.getStringValue(context, Const.symmetry, "");
        fooRequest.Fls = Pref.getStringValue(context, Const.fi, "");
        fooRequest.Lab = Pref.getStringValue(context, Const.lab, "");
        fooRequest.Inclusion = Pref.getStringValue(context, Const.inclusion, "");
        fooRequest.Natts = Pref.getStringValue(context, Const.natts, "");
        fooRequest.Shade = "";
        fooRequest.FromCts = Pref.getStringValue(context, Const.from_cts, "");
        fooRequest.ToCts = Pref.getStringValue(context, Const.to_cts, "");
        fooRequest.FormDisc = Pref.getStringValue(context, Const.from_disc, "");
        fooRequest.ToDisc = Pref.getStringValue(context, Const.to_disc, "");
        fooRequest.FormPricePerCts = Pref.getStringValue(context, Const.from_pcu, "");
        fooRequest.ToPricePerCts = Pref.getStringValue(context, Const.to_pcu, "");
        fooRequest.FormNetAmt = Pref.getStringValue(context, Const.from_na, "");
        fooRequest.ToNetAmt = Pref.getStringValue(context, Const.to_na, "");
        fooRequest.FormDepth = Pref.getStringValue(context, Const.from_depth, "");
        fooRequest.ToDepth = Pref.getStringValue(context, Const.to_depth, "");
        fooRequest.FormLength = Pref.getStringValue(context, Const.from_length, "");
        fooRequest.ToLength = Pref.getStringValue(context, Const.to_length, "");
        fooRequest.FormWidth = Pref.getStringValue(context, Const.from_width, "");
        fooRequest.ToWidth = Pref.getStringValue(context, Const.to_width, "");
        fooRequest.FormDepthPer = Pref.getStringValue(context, Const.from_depth_p, "");
        fooRequest.ToDepthPer = Pref.getStringValue(context, Const.to_depth_p, "");
        fooRequest.FormTablePer = Pref.getStringValue(context, Const.from_table_p, "");
        fooRequest.ToTablePer = Pref.getStringValue(context, Const.to_table_p, "");
        fooRequest.HasImage = Pref.getStringValue(context, Const.image, "");
        fooRequest.HasHDMovie = Pref.getStringValue(context, Const.video, "");
        fooRequest.IsPromotion = "";
        fooRequest.CrownInclusion = Pref.getStringValue(context, Const.crown_inclusion, "");
        fooRequest.CrownNatts = Pref.getStringValue(context, Const.crown_natts, "");
        fooRequest.Luster = "";
        fooRequest.Location = Pref.getStringValue(context, Const.location, "");
        fooRequest.StoneStatus = "";
        fooRequest.FromCrownAngle = Pref.getStringValue(context, Const.from_crang_p, "");
        fooRequest.ToCrownAngle = Pref.getStringValue(context, Const.to_crang_p, "");
        fooRequest.FromCrownHeight = Pref.getStringValue(context, Const.from_crht_p, "");
        fooRequest.ToCrownHeight = Pref.getStringValue(context, Const.to_crht_p, "");
        fooRequest.FromPavAngle = Pref.getStringValue(context, Const.from_pavang_p, "");
        fooRequest.ToPavAngle = Pref.getStringValue(context, Const.to_pavang_p, "");
        fooRequest.FromPavHeight = Pref.getStringValue(context, Const.from_pavht_p, "");
        fooRequest.ToPavHeight = Pref.getStringValue(context, Const.to_pavht_p, "");
        fooRequest.BGM = Pref.getStringValue(context, Const.bgm, "");
        fooRequest.BLACK ="";
        fooRequest.PageNo ="";
        fooRequest.Action = Action;
        fooRequest.dTransDate = ValidityDate;
        fooRequest.KeyToSymbol = Pref.getStringValue(context, Const.keytosymbol, "");
        fooRequest.ReviseStockFlag = "";
        Const.showProgress(context);
        new RestClient(context).getInstance().get().NoStockFound(fooRequest).enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
//                Debug.e(TAG, new Gson().toJson(response.body()));
                Const.dismissProgress();
                if (response.body() != null) {
                    if (response.body().Status.equals("1")) {
                        if (Action.equalsIgnoreCase("Notify Me")){
                            showDialog(context, "We will update soon in Search Stock Notification !!");
                        }else{
                            showDialog(context, "We will update soon in Email !!");
                        }
                    } else {
                        Const.showErrorDialog(context, response.body().Message);
                    }
                } else {
                    Toast.makeText(context, response.body().Message, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {
                Const.showErrorDialog(context, "Something went wrong.");
                //  Toast.makeText(context, context.getResources().getString(R.string.SOMETHING_WENT_WRONG), Toast.LENGTH_SHORT).show();
//                Debug.e(TAG, t.getLocalizedMessage());
                Const.dismissProgress();
            }
        });
    }

    public void showDialog(final Context context, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context, R.style.Theme_Dialog).create();
        alertDialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        alertDialog.getWindow().setBackgroundDrawable(setBackgoundBorder(0, 20, Color.WHITE, Color.WHITE));
        alertDialog.setTitle(R.string.app_name_dialog);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(context, SearchFilterActivity.class));
                dialog.dismiss();
            }
        });

        alertDialog.show();
        alertDialog.getButton(alertDialog.BUTTON1).setTextColor(Color.BLACK);
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
//                                } else if (arrayCount.get(i).get("type").equalsIgnoreCase("WishList")) {
//                                    tvWhishlist.setText(arrayCount.get(i).get("scnt"));
//                                }
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

    private void addToCart() {
        Const.showProgress(context);
        List<String> list = new ArrayList<>(hmSelected.values());
        final Map<String, String> map = new HashMap<>();
        map.put("StoneID", list.toString().replace("[", "").replace("]", "").replace(", ", ","));
        map.put("OfferTrans", "0");
        map.put("TransType", "A");
        Const.callPostApi(context, "Order/AddRemoveToCart", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Const.dismissProgress();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.optString("Status").equalsIgnoreCase("1")) {
                        CartDialog(object);
                        // String ob = new JSONObject(result).optString("Message");
                        clearTotal();
                        adp.notifyDataSetChanged();
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
                        addToCart();
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    private void addTowishList() {
        Const.showProgress(context);
        List<String> list = new ArrayList<>(hmSelected.values());
        final Map<String, String> map = new HashMap<>();
        map.put("StoneID", list.toString().replace("[", "").replace("]", "").replace(", ", ","));
        map.put("TransType", "A");
        Const.callPostApi(context, "Order/AddRemoveToWishList", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Const.dismissProgress();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.optString("Status").equalsIgnoreCase("1")) {
                        WishListDialog(object);
                        // String ob = new JSONObject(result).optString("Message");
                        clearTotal();
                        adp.notifyDataSetChanged();
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
                        addTowishList();
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    private void PlaceOrder(final String comments) {
        Const.showProgress(context);
        List<String> list = new ArrayList<>(hmSelected.values());
        final Map<String, String> map = new HashMap<>();
        map.put("StoneID", list.toString().replace("[", "").replace("]", "").replace(", ", ","));
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
                        clearTotal();
                        adp.notifyDataSetChanged();
                        Const.showErrorDialog(context, Const.notNullString(message, "This Stone(s) are subject to avaibility 'BZM-318692'"));
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
    }

    private void PlaceOrderDialog() {
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
                    PlaceOrder(etComments.getText().toString());
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

    private void SummDialog() {
        final Dialog dialog = new Dialog(context, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dialog.getWindow().setBackgroundDrawable(Const.setBackgoundBorder(0, 20, Color.WHITE, Color.WHITE));
        dialog.setContentView(R.layout.layout_dialog_summary);

        TextView tvPcs = dialog.findViewById(R.id.tvPcs);
        TextView tvCts = dialog.findViewById(R.id.tvCts);
        TextView tvOfferDisc = dialog.findViewById(R.id.tvOfferDisc);
        TextView tvOfferValue = dialog.findViewById(R.id.tvOfferValue);

        TextView tvPriceCts = dialog.findViewById(R.id.tvPriceCts);
        TextView tvWebDisc = dialog.findViewById(R.id.tvWebDisc);
        TextView tvFinalDisc = dialog.findViewById(R.id.tvFinalDisc);
        TextView tvFinalValue = dialog.findViewById(R.id.tvFinalValue);
        TextView tvOk = dialog.findViewById(R.id.tvOk);

        if(cbSelectAll.isChecked()){
            double totalcts = 0;
            double totalamt = 0;
            double totalavgdiscper = 0;
            double totalpricepercts = 0;
            double totalrap = 0;
            double totalraprate = 0;
            for (int i = 0; i < maps.size(); i++) {
                if (!maps.get(i).get("cts").equalsIgnoreCase("null")) {
                    totalcts += Double.parseDouble(maps.get(i).get("cts"));
                }
                if (!maps.get(i).get("net_amount").equalsIgnoreCase("null")) {
                    totalamt += Double.parseDouble(maps.get(i).get("net_amount"));
                }
                if (!maps.get(i).get("rap_amount").equalsIgnoreCase("null")) {
                    totalrap += Double.parseDouble(maps.get(i).get("rap_amount"));
                }
                if (!maps.get(i).get("cur_rap_rate").equalsIgnoreCase("null")) {
                    totalraprate += Double.parseDouble(maps.get(i).get("cur_rap_rate"));
                }
            }

            Double maindisc = Double.parseDouble("0.88");

            Double avgdisc = 0.00;
            if(totalraprate == 0.0) {
                avgdisc = 0.00;
            } else {
                avgdisc = (100 - ((Double.parseDouble(tvTotalAmt.getText().toString().replace("$","").replace(",","").split(" ")[1]) * 100) / totalrap)) * -1;
            }
            Double ppc = Double.parseDouble(tvTotalAmt.getText().toString().replace("$","").replace(",","").split(" ")[1]) / Double.parseDouble(tvTotalCts.getText().toString().replace(",",""));

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
            tvOfferDisc.setText(avgDisc.isInfinite() ? "0.00 %" : String.format("%.2f", avgdisc) + " %");
            tvOfferValue.setText("$ " + String.format("%,.0f", totalamt));

            tvPriceCts.setText(String.format("%,.2f", ppc));
            tvWebDisc.setText("$ " + String.format("%.2f", webdisc));
            tvFinalDisc.setText(finaldisc.isInfinite() ? "0.00 %" : String.format("%,.2f", finaldisc) + " %");
            tvFinalValue.setText("$ " + String.format("%,.2f", finalvalue));
        } else {
            double totalraprate = 0;
            for (int i = 0; i < maps.size(); i++) {
                if (!maps.get(i).get("cur_rap_rate").equalsIgnoreCase("null")) {
                    totalraprate += Double.parseDouble(maps.get(i).get("cur_rap_rate"));
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

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
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
                adp.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getHomeCount();
                adp.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void WishListDialog(JSONObject object) {
        final Dialog dialog = new Dialog(context, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.layout_cart_dialog);
        final TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        final TextView tvMsg = dialog.findViewById(R.id.tvMsg);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnSend = dialog.findViewById(R.id.btnSend);

        tvTitle.setText("WishList");
        btnSend.setText("Go To WishList");
        tvMsg.setText(object.optString("Message"));

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, WishListActivity.class));
                overridePendingTransition(0, 0);
                adp.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adp.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void SendMail(final String toaddress, final String comments) {
        Const.showProgress(context);
        List<String> list = new ArrayList<>(hmSelected.values());
        final Map<String, String> map = new HashMap<>();
        map.put("StoneID", list.toString().replace("[", "").replace("]", "").replace(", ", ","));
        map.put("ToAddress", Const.notNullString(toaddress,""));
        map.put("Comments", Const.notNullString(comments,""));
        Const.callPostApi(context, "Stock/EmailStones", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Const.dismissProgress();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.optString("Status").equalsIgnoreCase("1")) {
                        String ob = new JSONObject(result).optString("Message");
                        clearTotal();
                        cbSelectAll.setChecked(false);
                        adp.notifyDataSetChanged();
                        Const.showErrorDialog(context, Const.notNullString(ob, "Mail sent successfully."));
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
                        SendMail(toaddress,comments);
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    public void SendMailAll(final String toaddress, final String comments){
        FooRequest fooRequest = new FooRequest();
        fooRequest.SmartSearch = Pref.getStringValue(context, Const.stone_id, "");
        fooRequest.CertiNo = Pref.getStringValue(context, Const.certi_no, "");
        fooRequest.Shape = Pref.getStringValue(context, Const.shape, "");
        fooRequest.Pointer = Pref.getStringValue(context, Const.pointer, "");
        fooRequest.Color = Pref.getStringValue(context, Const.color, "");;
        fooRequest.Clarity = Pref.getStringValue(context, Const.clarity, "");;
        fooRequest.Cut = Pref.getStringValue(context, Const.cut, "");
        fooRequest.Polish = Pref.getStringValue(context, Const.polish, "");
        fooRequest.Symm = Pref.getStringValue(context, Const.symmetry, "");
        fooRequest.Fls = Pref.getStringValue(context, Const.fi, "");
        fooRequest.Lab = Pref.getStringValue(context, Const.lab, "");
        fooRequest.Inclusion = Pref.getStringValue(context, Const.inclusion, "");
        fooRequest.Natts = Pref.getStringValue(context, Const.natts, "");
        fooRequest.Shade = "";
        fooRequest.FromCts = Pref.getStringValue(context, Const.from_cts, "");
        fooRequest.ToCts = Pref.getStringValue(context, Const.to_cts, "");
        fooRequest.FormDisc = Pref.getStringValue(context, Const.from_disc, "");
        fooRequest.ToDisc = Pref.getStringValue(context, Const.to_disc, "");
        fooRequest.FormPricePerCts = Pref.getStringValue(context, Const.from_pcu, "");
        fooRequest.ToPricePerCts = Pref.getStringValue(context, Const.to_pcu, "");
        fooRequest.FormNetAmt = Pref.getStringValue(context, Const.from_na, "");
        fooRequest.ToNetAmt = Pref.getStringValue(context, Const.to_na, "");
        fooRequest.FormDepth = Pref.getStringValue(context, Const.from_depth, "");
        fooRequest.ToDepth = Pref.getStringValue(context, Const.to_depth, "");
        fooRequest.FormLength = Pref.getStringValue(context, Const.from_length, "");
        fooRequest.ToLength = Pref.getStringValue(context, Const.to_length, "");
        fooRequest.FormWidth = Pref.getStringValue(context, Const.from_width, "");
        fooRequest.ToWidth = Pref.getStringValue(context, Const.to_width, "");
        fooRequest.FormDepthPer = Pref.getStringValue(context, Const.from_depth_p, "");
        fooRequest.ToDepthPer = Pref.getStringValue(context, Const.to_depth_p, "");
        fooRequest.FormTablePer = Pref.getStringValue(context, Const.from_table_p, "");
        fooRequest.ToTablePer = Pref.getStringValue(context, Const.to_table_p, "");
        fooRequest.HasImage = Pref.getStringValue(context, Const.image, "");
        fooRequest.HasHDMovie = Pref.getStringValue(context, Const.video, "");
        fooRequest.IsPromotion = "";
        fooRequest.CrownInclusion = Pref.getStringValue(context, Const.crown_inclusion, "");
        fooRequest.CrownNatts = Pref.getStringValue(context, Const.crown_natts, "");
        fooRequest.Luster = "";
        fooRequest.Location = Pref.getStringValue(context, Const.location, "");
        fooRequest.StoneStatus = "";
        fooRequest.FromCrownAngle = Pref.getStringValue(context, Const.from_crang_p, "");
        fooRequest.ToCrownAngle = Pref.getStringValue(context, Const.to_crang_p, "");
        fooRequest.FromCrownHeight = Pref.getStringValue(context, Const.from_crht_p, "");
        fooRequest.ToCrownHeight = Pref.getStringValue(context, Const.to_crht_p, "");
        fooRequest.FromPavAngle = Pref.getStringValue(context, Const.from_pavang_p, "");
        fooRequest.ToPavAngle = Pref.getStringValue(context, Const.to_pavang_p, "");
        fooRequest.FromPavHeight = Pref.getStringValue(context, Const.from_pavht_p, "");
        fooRequest.ToPavHeight = Pref.getStringValue(context, Const.to_pavht_p, "");
        fooRequest.BGM = Pref.getStringValue(context, Const.bgm, "");
        fooRequest.BLACK ="";
        fooRequest.PageNo ="";
        fooRequest.KeyToSymbol = Pref.getStringValue(context, Const.keytosymbol, "");
        fooRequest.ReviseStockFlag = "";

        MailRequest mailRequest = new MailRequest();
        mailRequest.ToAddress = toaddress;
        mailRequest.Comments = comments;
        mailRequest.IsRevised = "false";
        mailRequest.IsAll = "1";
        mailRequest.SearchCriteria = fooRequest;
        Const.showProgress(context);
        new RestClient(context).getInstance().get().SendMailAll(mailRequest).enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
//                Debug.e(TAG, new Gson().toJson(response.body()));
                Const.dismissProgress();
                if (response.body() != null) {
                    if (response.body().Status.equals("1")) {
//                        Toast.makeText(context, response.body().Message, Toast.LENGTH_SHORT).show();
                        clearTotal();
                        cbSelectAll.setChecked(false);
                        adp.notifyDataSetChanged();
                        Const.showErrorDialog(context, Const.notNullString(response.body().Message, "Mail sent successfully."));
                    } else {
                        Const.showErrorDialog(context, response.body().Message);
                    }
                } else {
                    Toast.makeText(context, response.body().Message, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {
                Const.showErrorDialog(context, "Something went wrong.");
                //  Toast.makeText(context, context.getResources().getString(R.string.SOMETHING_WENT_WRONG), Toast.LENGTH_SHORT).show();
//                Debug.e(TAG, t.getLocalizedMessage());
                Const.dismissProgress();
            }
        });
    }

    private void CopyDialog() {
        final List<HashMap<String, String>> list = new ArrayList<>();
        for (int i = 0; i < maps.size(); i++) {
            if (hmSelected.get(i) != null) {
                list.add(maps.get(i));
                if (hmSelected.size() == list.size()) {
                    break;
                }
            }
        }
        final Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");

        Result = (TextView) findViewById(R.id.ResultDiam);

        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        String s = " ";

        for (int j = 0; j < list.size(); j++) {
            String si ="";
            si = si+"\uD83D\uDC8E"+" "+list.get(j).get("stone_ref_no")+" | "+ Const.notNullString(Const.CamelCase(list.get(j).get("shape")),"")+"\n";
            si = si+"\uD83D\uDCD1"+" "+ list.get(j).get("lab") + " " + list.get(j).get("certi_no")+"\n";

            si = si + "\n";

            if (list.get(j).get("location").equalsIgnoreCase("Hong Kong")) {
                si = si + "\uD83C\uDDED\uD83C\uDDF0 ";
            } else if (list.get(j).get("location").equalsIgnoreCase("India")) {
                si = si + "\uD83C\uDDEE\uD83C\uDDF3 ";
            } else if (list.get(j).get("location").equalsIgnoreCase("Upcoming")) {
                si = si + "??? ";
            } else if (list.get(j).get("location").equalsIgnoreCase("SHOW")) {
                si = si + "\uD83C\uDFA1";
            } else if (list.get(j).get("location").equalsIgnoreCase("Dubai")) {
                si = si + "\uD83C\uDDE6\uD83C\uDDEA ";
            }

            if (list.get(j).get("status").equalsIgnoreCase("AVAILABLE")){
                si = si + "*"+"Available"+"*";
            } else if (list.get(j).get("status").equalsIgnoreCase("AVAILABLE OFFER")){
                si =  si + "*"+"Offer"+"*";
            } else if (list.get(j).get("status").equalsIgnoreCase("NEW")){
                si = si + "*"+"New"+"*";
            } else if (list.get(j).get("status").equalsIgnoreCase("BUSS. PROCESS")){
                si =  si + "*"+"Busy"+"*";
            }

            si =  si +" - ";

            if (list.get(j).get("bgm").equalsIgnoreCase("NO BGM")) {
                si =  si +"No bgm";
            } else if (list.get(j).get("bgm").equalsIgnoreCase("BROWN")) {
                si =  si + "Brown";
            } else if (list.get(j).get("bgm").equalsIgnoreCase("MILKY")) {
                si =  si + "Milky";
            }


            si = si +"\n\n"+ Const.notNullString(list.get(j).get("color"),"-")+"    "+Const.notNullString(list.get(j).get("clarity"),"-")+"    *"+String.format("%.2f", Double.parseDouble(list.get(j).get("cts")))+"*";
            si = si + "\n";

            si = si + Const.notNullString(list.get(j).get("cut"),"-") + "    " + Const.notNullString(list.get(j).get("polish"),"-") + "    " + Const.notNullString(list.get(j).get("symm"),"-") + "    " + Const.notNullString(list.get(j).get("fls"),"-");


            si = si + "\n\nRap Price $ : "+String.format("%,.2f", Double.parseDouble(list.get(j).get("cur_rap_rate")));
            si = si + "\nRap Amount $ : "+String.format("%,.2f", Double.parseDouble(list.get(j).get("rap_amount")));

            si = si + "\n\n*Discount % : " + String.format("%.2f", Double.parseDouble(list.get(j).get("sales_disc_per"))) +"*";
            si = si + "\n*Value $ : " + String.format("%,.2f", Double.parseDouble(list.get(j).get("net_amount")))+"*";

            si = si + "\n\n"+String.format("%.2f", Double.parseDouble(list.get(j).get("length"))) + " x " + String.format("%.2f", Double.parseDouble(list.get(j).get("width"))) + " x " + String.format("%.2f", Double.parseDouble(list.get(j).get("depth")));

            si = si + "\n\nDepth% : "+String.format("%.1f", Double.parseDouble(list.get(j).get("depth_per")))+" , Table% : "+String.format("%.0f", Double.parseDouble(list.get(j).get("table_per")));

            if (!list.get(j).get("image_url").equalsIgnoreCase("")) {
                si = si.concat("\n\n\uD83D\uDCF7 " + list.get(j).get("image_url"));
            }
            if (!list.get(j).get("movie_url").equalsIgnoreCase("")) {
                si = si.concat("\n\n\uD83C\uDFA5 " + list.get(j).get("movie_url"));
            }
            if (!list.get(j).get("view_certi_url").equalsIgnoreCase("")) {
                si = si.concat("\n\n\uD83D\uDCD1 " + list.get(j).get("view_certi_url") + "\n");
            }

            s = s.concat(si);
            s = s.concat("\n------------------------------------------\n\n");
        }

        s = s.substring(1, s.length() - 1);
        Result.setText(s);
        ClipData clip = ClipData.newPlainText("Copied Text" , Result.getText());
        clipboard.setPrimaryClip(clip);

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_container));

        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText("Copied Text");

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    private void emailDialog() {
        final Dialog dialog = new Dialog(context, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.layout_email);
        final EditText etEmail = dialog.findViewById(R.id.etEmail);
        final EditText etComments = dialog.findViewById(R.id.etComments);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnSend = dialog.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Const.isValidEmail(etEmail)) {
                    Const.showErrorDialog(context, "Please enter valid email id");
                } else {
                    if(hmSelected.size() > 0){
                        SendMail(etEmail.getText().toString(),etComments.getText().toString());
                    }else{
                        SendMailAll(etEmail.getText().toString(),etComments.getText().toString());
                    }
                    dialog.dismiss();
                }
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

    private void downloadmedia(final String stoneid) {
        Const.showProgress(context);
        final Map<String, String> map = new HashMap<>();
        map.put("StoneID", Const.notNullString(stoneid,""));
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
                            request.setTitle(stoneid);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                request.allowScanningByMediaScanner();
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            }
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, stoneid + ".zip");
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
                        downloadmedia(stoneid);
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    private void downloadOversesImages(final String stoneid) {
        Const.showProgress(context);
        final Map<String, String> map = new HashMap<>();
        map.put("StoneID", Const.notNullString(stoneid,""));
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
                            request.setTitle(stoneid);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                request.allowScanningByMediaScanner();
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            }
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, stoneid + ".zip");
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
                        downloadOversesImages(stoneid);
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
        map.put("StoneID", RefNo.toString().replace("[", "").replace("]", "").replace(", ", ","));
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
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                            request.setTitle("Sunrise Diamonds Selection " + dateFormat.format(date));

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                request.allowScanningByMediaScanner();
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            }
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Sunrise Diamonds Selection " + dateFormat.format(date) + ".xlsx");
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

    private void DownloadStockExcel() {
        Const.showProgress(context);
        // List<String> RefNo = new ArrayList<>(hmSelected.values());
        final Map<String, String> map = new HashMap<>();
        map.put("StoneID", "");
        map.put("FormName", "Search Stock");
        map.put("ActivityType", "Excel Export");
        map.put("PgSize", "0");
        map.put("full", "N");
        map.put("IsAll", "0");
        Const.callPostApi(context, "Stock/DownloadStockExcel", map, new VolleyCallback() {
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
                            downloadNumber = String.valueOf(objGenerator.nextInt(1000));
                            System.out.println("Random No : " + downloadNumber);
                        }
                        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Sunrise Diamonds Selection " + dateFormat.format(date) + "-" + downloadNumber + ".xlsx");
                        registerReceiver(onDownload, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(result.toString()));
                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                        request.setTitle("Sunrise Diamonds Selection " + dateFormat.format(date) + "-" + downloadNumber);
                        request.allowScanningByMediaScanner();
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Sunrise Diamonds Selection " + dateFormat.format(date) + "-" + downloadNumber + ".xlsx");
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
                        DownloadStockExcel();
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    private BroadcastReceiver onDownload = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            if (downID == id) {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Sunrise Diamonds Selection " + dateFormat.format(date) + "-" + downloadNumber + ".xlsx");
                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.setType("application/vnd.ms-excel");
                Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider",file);
                share.putExtra(Intent.EXTRA_STREAM, uri);
                share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                builder.detectFileUriExposure();
                openDownloadedAttachment(context, downID);
            }
            unregisterReceiver(onDownload);
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
        List<String> RefNo = new ArrayList<>(hmSelected.values());
        final Map<String, String> map = new HashMap<>();
        map.put("StoneID", RefNo.toString().replace("[", "").replace("]", "").replace(", ", ","));
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
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

                        Random objGenerator = new Random();
                        for (int iCount = 0; iCount< 1; iCount++){
                            randomNumber = String.valueOf(objGenerator.nextInt(1000));
                            System.out.println("Random No : " + randomNumber);
                        }
                        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Sunrise Diamonds Selection " + dateFormat.format(date) + "-" +  randomNumber + ".xlsx");
                        registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(result.toString()));
                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                        request.setTitle("Sunrise Diamonds Selection " + dateFormat.format(date) + "-" +  randomNumber);
                        request.allowScanningByMediaScanner();
                        // request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Sunrise Diamonds Selection " + dateFormat.format(date) + "-" +  randomNumber + ".xlsx");
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
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            if (downID == id) {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Sunrise Diamonds Selection " + dateFormat.format(date) + "-" +  randomNumber + ".xlsx");
                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.setType("application/vnd.ms-excel");
                Uri uri = FileProvider.getUriForFile(SearchResult4Activity.this, BuildConfig.APPLICATION_ID + ".provider",file);
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
                final List<HashMap<String, String>> list = new ArrayList<>();
                for (int i = 0; i < maps.size(); i++) {
                    if (hmSelected.get(i) != null) {
                        list.add(maps.get(i));
                        if (hmSelected.size() == list.size()) {
                            break;
                        }
                    }
                }

                final Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                if (v.getId() == R.id.llExcel) {
                    ShareExcel();
                } else if (v.getId() == R.id.llDetails) {
                    String s = " ";
                    for (int j = 0; j < list.size(); j++) {
                        String si ="";
                        si = si+"\uD83D\uDC8E"+" "+list.get(j).get("stone_ref_no")+" | "+ Const.notNullString(Const.CamelCase(list.get(j).get("shape")),"")+"\n";
                        si = si+"\uD83D\uDCD1"+" "+ list.get(j).get("lab") + " " + list.get(j).get("certi_no")+"\n";

                        si = si + "\n";

                        if (list.get(j).get("location").equalsIgnoreCase("Hong Kong")) {
                            si = si + "\uD83C\uDDED\uD83C\uDDF0 ";
                        } else if (list.get(j).get("location").equalsIgnoreCase("India")) {
                            si = si + "\uD83C\uDDEE\uD83C\uDDF3 ";
                        } else if (list.get(j).get("location").equalsIgnoreCase("Upcoming")) {
                            si = si + "??? ";
                        } else if (list.get(j).get("location").equalsIgnoreCase("SHOW")) {
                            si = si + "\uD83C\uDFA1";
                        } else if (list.get(j).get("location").equalsIgnoreCase("Dubai")) {
                            si = si + "\uD83C\uDDE6\uD83C\uDDEA ";
                        }

                        if (list.get(j).get("status").equalsIgnoreCase("AVAILABLE")){
                            si = si + "*"+"Available"+"*";
                        } else if (list.get(j).get("status").equalsIgnoreCase("AVAILABLE OFFER")){
                            si =  si + "*"+"Offer"+"*";
                        } else if (list.get(j).get("status").equalsIgnoreCase("NEW")){
                            si = si + "*"+"New"+"*";
                        } else if (list.get(j).get("status").equalsIgnoreCase("BUSS. PROCESS")){
                            si =  si + "*"+"Busy"+"*";
                        }

                        si =  si +" - ";

                        if (list.get(j).get("bgm").equalsIgnoreCase("NO BGM")) {
                            si =  si +"No bgm";
                        } else if (list.get(j).get("bgm").equalsIgnoreCase("BROWN")) {
                            si =  si + "Brown";
                        } else if (list.get(j).get("bgm").equalsIgnoreCase("MILKY")) {
                            si =  si + "Milky";
                        }


                        si = si +"\n\n"+ Const.notNullString(list.get(j).get("color"),"-")+"    "+Const.notNullString(list.get(j).get("clarity"),"-")+"    *"+String.format("%.2f", Double.parseDouble(list.get(j).get("cts")))+"*";
                        si = si + "\n";

                        si = si + Const.notNullString(list.get(j).get("cut"),"-") + "    " + Const.notNullString(list.get(j).get("polish"),"-") + "    " + Const.notNullString(list.get(j).get("symm"),"-") + "    " + Const.notNullString(list.get(j).get("fls"),"-");


                        si = si + "\n\nRap Price $ : "+String.format("%,.2f", Double.parseDouble(list.get(j).get("cur_rap_rate")));
                        si = si + "\nRap Amount $ : "+String.format("%,.2f", Double.parseDouble(list.get(j).get("rap_amount")));

                        si = si + "\n\n*Discount % : " + String.format("%.2f", Double.parseDouble(list.get(j).get("sales_disc_per"))) +"*";
                        si = si + "\n*Value $ : " + String.format("%,.2f", Double.parseDouble(list.get(j).get("net_amount")))+"*";

                        si = si + "\n\n"+String.format("%.2f", Double.parseDouble(list.get(j).get("length"))) + " x " + String.format("%.2f", Double.parseDouble(list.get(j).get("width"))) + " x " + String.format("%.2f", Double.parseDouble(list.get(j).get("depth")));

                        si = si + "\n\nDepth% : "+String.format("%.1f", Double.parseDouble(list.get(j).get("depth_per")))+" , Table% : "+String.format("%.0f", Double.parseDouble(list.get(j).get("table_per")));

                        if (!list.get(j).get("image_url").equalsIgnoreCase("")) {
                            si = si.concat("\n\n\uD83D\uDCF7 " + list.get(j).get("image_url"));
                        }
                        if (!list.get(j).get("movie_url").equalsIgnoreCase("")) {
                            si = si.concat("\n\n\uD83C\uDFA5 " + list.get(j).get("movie_url"));
                        }
                        if (!list.get(j).get("view_certi_url").equalsIgnoreCase("")) {
                            si = si.concat("\n\n\uD83D\uDCD1 " + list.get(j).get("view_certi_url") + "\n");
                        }

                        s = s.concat(si);
                        s = s.concat("\n------------------------------------------\n\n");
                    }
                    s = s.substring(1, s.length() - 1);
                    i.putExtra(Intent.EXTRA_TEXT, s);
                    startActivity(Intent.createChooser(i, "Share via"));
                } else if (v.getId() == R.id.llImages) {
                    String sk = "";
                    String so = "";
                    String flag = "1";
                    ArrayList<BindData> image_list = new ArrayList<BindData>();
                    for (int j = 0; j < list.size(); j++) {
                        if (list.get(j).get("isoverseas").equalsIgnoreCase("0")) {
                            if (list.get(j).get("bimage").equalsIgnoreCase("true")) {
                            if (!list.get(j).get("image_url").equalsIgnoreCase("")) {
                                HashMap<String, String> child = new HashMap<String, String>();
                                child.put("stone_ref_no", list.get(j).get("stone_ref_no"));
                                child.put("certi_no", list.get(j).get("certi_no"));
                                String si = "success";
                                sk = sk.concat(si);
                                image_list.add(new BindData(list.get(j).get("stone_ref_no"), list.get(j).get("certi_no"), list.get(j).get("bimage"), list.get(j).get("image_url")));
                            }
                        } else if (list.get(j).get("bimage").equalsIgnoreCase("false")) {
                            if (!list.get(j).get("image_url").equalsIgnoreCase("")) {
                                HashMap<String, String> child = new HashMap<String, String>();
                                child.put("stone_ref_no", list.get(j).get("stone_ref_no"));
                                child.put("certi_no", list.get(j).get("certi_no"));
                                String si = "success";
                                sk = sk.concat(si);
                                image_list.add(new BindData(list.get(j).get("stone_ref_no"), list.get(j).get("certi_no"), list.get(j).get("bimage"), list.get(j).get("image_url")));
                            }
                        }
                    } else {
                            if (list.get(j).get("image_url") != null && !list.get(j).get("image_url").equalsIgnoreCase("")) {
                                String si = "\nStone ID : " + list.get(j).get("stone_ref_no") + "\n"
                                        + "Image Link : " + list.get(j).get("image_url") + "\n";
                                so = so.concat(si);
                            }
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
                    for (int j = 0; j < list.size(); j++) {
                        if (!list.get(j).get("movie_url").equalsIgnoreCase("")) {
                            String si = "\nStone ID : " + list.get(j).get("stone_ref_no") + "\n"
                                    + "Video Link : " + list.get(j).get("movie_url") + "\n";
                            sv = sv.concat(si);
                        }
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
                            for (int j = 0; j < list.size(); j++) {
                                if (!list.get(j).get("view_certi_url").equalsIgnoreCase("")) {
                                    try {
                                        URL url1 = new URL(list.get(j).get("view_certi_url"));
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
                                            cer.append("\nStone ID : " + list.get(j).get("stone_ref_no") + "\n"
                                                    + "Pdf Link : " + list.get(j).get("view_certi_url") + "\n");
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
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
                final List<HashMap<String, String>> list = new ArrayList<>();
                for (int i = 0; i < maps.size(); i++) {
                    if (hmSelected.get(i) != null) {
                        list.add(maps.get(i));
                        if (hmSelected.size() == list.size()) {
                            break;
                        }
                    }
                }
                final Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                if (cbBoxExcel.isChecked()) {
                    DownloadExcel();
                }
                if (cbBoxImages.isChecked()) {
                    String sk = "";
                    ArrayList<BindData> image_list = new ArrayList<BindData>();
                    for (int j = 0; j < list.size(); j++) {
                        if (list.get(j).get("isoverseas").equalsIgnoreCase("0")) {
                            if (list.get(j).get("bimage").equalsIgnoreCase("true")) {
                            if (!list.get(j).get("image_url").equalsIgnoreCase("")) {
                                HashMap<String, String> child = new HashMap<String, String>();
                                child.put("stone_ref_no", list.get(j).get("stone_ref_no"));
                                child.put("certi_no", list.get(j).get("certi_no"));
                                String si = "success";
                                sk = sk.concat(si);
                                image_list.add(new BindData(list.get(j).get("stone_ref_no"), list.get(j).get("certi_no"), list.get(j).get("bimage"), list.get(j).get("image_url")));
                            }
                        } else if (list.get(j).get("bimage").equalsIgnoreCase("false")) {
                            if (!list.get(j).get("image_url").equalsIgnoreCase("")) {
                                HashMap<String, String> child = new HashMap<String, String>();
                                child.put("stone_ref_no", list.get(j).get("stone_ref_no"));
                                child.put("certi_no", list.get(j).get("certi_no"));
                                String si = "success";
                                sk = sk.concat(si);
                                image_list.add(new BindData(list.get(j).get("stone_ref_no"), list.get(j).get("certi_no"), list.get(j).get("bimage"), list.get(j).get("image_url")));
                            }
                        }
                            if (!sk.equals("")) {
                                new DownloadImage().download1(context, image_list);
                            } else {
                                Const.showErrorDialog(context, "Sorry no image available for this stone to download");
                            }
                    } else {
                            downloadOversesImages(list.get(j).get("stone_ref_no"));
                        }
                    }
                }
                if (cbBoxVideo.isChecked()) {
                    for (int j = 0; j < list.size(); j++) {
                        downloadmedia(list.get(j).get("stone_ref_no"));
                    }
                }
                if (cbBoxCerti.isChecked()) {
                    Const.showProgress(context);
                    final StringBuilder cernew = new StringBuilder();
                    final StringBuilder cer = new StringBuilder();
                    final StringBuilder cername = new StringBuilder();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (int j = 0; j < list.size(); j++) {
                                if (!list.get(j).get("view_certi_url").equalsIgnoreCase("")) {
                                    try {
                                        URL url1 = new URL(list.get(j).get("view_certi_url"));
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
                                            cernew.append(list.get(j).get("view_certi_url"));
                                            cer.append(list.get(j).get("view_certi_url"));
                                            cername.append(list.get(j).get("stone_ref_no"));

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
                                }
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
                    String sk = "";
                    ArrayList<BindData> image_list = new ArrayList<BindData>();
                    for (int j = 0; j < list.size(); j++) {
                        if (list.get(j).get("bimage").equalsIgnoreCase("true")) {
                            if (!list.get(j).get("image_url").equalsIgnoreCase("")) {
                                HashMap<String, String> child = new HashMap<String, String>();
                                child.put("stone_ref_no", list.get(j).get("stone_ref_no"));
                                child.put("certi_no", list.get(j).get("certi_no"));
                                String si = "success";
                                sk = sk.concat(si);
                                image_list.add(new BindData(list.get(j).get("stone_ref_no"), list.get(j).get("certi_no"), list.get(j).get("bimage"), list.get(j).get("image_url")));
                            }
                        } else if (list.get(j).get("bimage").equalsIgnoreCase("false")) {
                            if (!list.get(j).get("image_url").equalsIgnoreCase("")) {
                                HashMap<String, String> child = new HashMap<String, String>();
                                child.put("stone_ref_no", list.get(j).get("stone_ref_no"));
                                child.put("certi_no", list.get(j).get("certi_no"));
                                String si = "success";
                                sk = sk.concat(si);
                                image_list.add(new BindData(list.get(j).get("stone_ref_no"), list.get(j).get("certi_no"), list.get(j).get("bimage"), list.get(j).get("image_url")));
                            }
                        }
                    }
                    if (!sk.equals("")) {
                        new DownloadImage().download1(context, image_list);
                    } else {
                        Const.showErrorDialog(context, "Sorry no image available for this stone to download");
                    }

                    for (int j = 0; j < list.size(); j++) {
                        downloadmedia(list.get(j).get("stone_ref_no"));
                    }

                    Const.showProgress(context);
                    final StringBuilder cernew = new StringBuilder();
                    final StringBuilder cer = new StringBuilder();
                    final StringBuilder cername = new StringBuilder();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (int j = 0; j < list.size(); j++) {
                                if (!list.get(j).get("view_certi_url").equalsIgnoreCase("")) {
                                    try {
                                        URL url1 = new URL(list.get(j).get("view_certi_url"));
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
                                            cernew.append(list.get(j).get("view_certi_url"));
                                            cer.append(list.get(j).get("view_certi_url"));
                                            cername.append(list.get(j).get("stone_ref_no"));

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
                                }
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

    private void clearTotal() {
        hmSelected = new HashMap<>();
        rap = totalRap;
        tvTotalPcs.setText(String.valueOf(totalSize));
        tvTotalCts.setText(totalCts);
        tvTotalAmt.setText(totalAmount);
        tvTotalAvgDiscPer.setText(totalAvgDicPer);
        tvTotalPricePerCts.setText(totalPricePerCts);
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
            if (!maps.get(i).get("net_amount").equalsIgnoreCase("null")) {
                totalamt += Double.parseDouble(maps.get(i).get("net_amount"));
            }
            if (!maps.get(i).get("rap_amount").equalsIgnoreCase("null")) {
                totalrap += Double.parseDouble(maps.get(i).get("rap_amount"));
            }
        }

        tvTotalPcs.setText(maps.size() + "");
        tvTotalCts.setText(String.format("%,.2f", totalcts));
        tvTotalAmt.setText("$ " + String.format("%,.0f", totalamt));
        Double avgdisc = (100 - ((Double.parseDouble(tvTotalAmt.getText().toString().replace("$","").replace(",","").split(" ")[1]) * 100) / totalrap)) * -1;
        tvTotalAvgDiscPer.setText(avgdisc.isNaN() ? "0" : String.format("%.2f", avgdisc) + " %");
        Double ppc = Double.parseDouble(tvTotalAmt.getText().toString().replace("$","").replace(",","").split(" ")[1]) / Double.parseDouble(tvTotalCts.getText().toString().replace(",",""));
        tvTotalPricePerCts.setText("$ " + (ppc.isNaN() ? "0" : String.format("%,.2f", ppc)));
    }

    private void clearTotalAdp() {
        rap = 0.0;
        tvTotalPcs.setText("0");
        tvTotalCts.setText("0");
        tvTotalAmt.setText("$ 0");
        tvTotalAvgDiscPer.setText("0 %");
        tvTotalPricePerCts.setText("$ 0");
    }

    private class ResultAdp extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        ArrayList<HashMap<String, String>> arraylist;
        Context context;
        boolean isLayout;
        int isShow = -1;

        private ResultAdp(Context context, ArrayList<HashMap<String, String>> arraylist, boolean isLayout) {
            this.arraylist = arraylist;
            this.isLayout = isLayout;
            this.context = context;

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
            if (isLayout) {
                View view = LayoutInflater.from(context).inflate(R.layout.resultdiamondrow_newdesign, parent, false);
                return new ResultAdp.GridViewHolder(view);
            } else {
                View view = LayoutInflater.from(context).inflate(R.layout.layout_hori_view_new, parent, false);
                return new ResultAdp.ListViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            final HashMap<String, String> map = arraylist.get(position);
            if (isLayout) {
                if (holder instanceof ResultAdp.GridViewHolder) {
                    final ResultAdp.GridViewHolder h = (ResultAdp.GridViewHolder) holder;

                    h.tvStoneID.setText(map.get("stone_ref_no"));
                    h.tvCertiNo.setText(map.get("certi_no"));
                    h.tvColor.setText(map.get("color"));
                    h.tvCPS.setText(map.get("cut") + "-" + map.get("polish") + "-" + map.get("symm"));
                    if (map.get("cut").equalsIgnoreCase("3EX")) {
                        h.tvCPS.setTypeface(Typeface.DEFAULT_BOLD);
                    } else {
                        h.tvCPS.setTypeface(Typeface.DEFAULT);
                    }
                    h.tvFls.setText(map.get("fls"));
                    h.tvDisc.setText(String.format("%.2f", Double.parseDouble(map.get("sales_disc_per"))) + " %");
                    h.tvClarity.setText(map.get("clarity"));
                    h.tvCts.setText(String.format("%.2f", Double.parseDouble(map.get("cts"))));
                    if (map.get("location").equalsIgnoreCase("Hong Kong")) {
                        h.imgLocation.setImageResource(R.drawable.ic_hk);
                    } else if (map.get("location").equalsIgnoreCase("India")) {
                        h.imgLocation.setImageResource(R.drawable.ic_india);
                    } else if (map.get("location").equalsIgnoreCase("Upcoming")) {
                        h.imgLocation.setImageResource(R.drawable.ic_plane);
                    } else if (map.get("location").equalsIgnoreCase("SHOW")) {
                        h.imgLocation.setImageResource(R.drawable.ic_fair);
                    } else if (map.get("location").equalsIgnoreCase("Dubai")) {
                        h.imgLocation.setImageResource(R.drawable.ic_dubai);
                    }

                    if (map.get("status").equalsIgnoreCase("AVAILABLE")) {
                        Spannable Available = new SpannableString("Available");
                        Available.setSpan(new BackgroundColorSpan(getResources().getColor(R.color.green)), 0, Available.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        h.tvStatus.setText(Available);
                        h.tvStatus.setTextColor(Color.WHITE);
                    } else if (map.get("status").equalsIgnoreCase("AVAILABLE OFFER")) {
                        Spannable Offer = new SpannableString("Offer");
                        Offer.setSpan(new BackgroundColorSpan(getResources().getColor(R.color.ao)), 0, Offer.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        h.tvStatus.setText(Offer);
                        h.tvStatus.setTextColor(Color.BLACK);
                    } else if (map.get("status").equalsIgnoreCase("NEW")) {
                        Spannable New = new SpannableString("New");
                        New.setSpan(new BackgroundColorSpan(getResources().getColor(R.color.pink)), 0, New.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        h.tvStatus.setText(New);
                        h.tvStatus.setTextColor(Color.BLACK);
                    } else if (map.get("status").equalsIgnoreCase("BUSS. PROCESS")) {
                        Spannable Busy = new SpannableString("Busy");
                        Busy.setSpan(new BackgroundColorSpan(getResources().getColor(R.color.orange)), 0, Busy.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        h.tvStatus.setText(Busy);
                        h.tvStatus.setTextColor(Color.WHITE);
                    }

                    h.tvPriceCts.setText("$/Cts: " + String.format("%,.2f", Double.parseDouble(map.get("price_per_cts"))));
                    h.tvMsrmnt.setText(String.format("%.2f", Double.parseDouble(map.get("length"))) + "x" + String.format("%.2f", Double.parseDouble(map.get("width"))) + "x" + String.format("%.2f", Double.parseDouble(map.get("depth"))));
                    h.tvMsrmnt.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                    h.tvMsrmnt.setSingleLine(true);
                    h.tvMsrmnt.setMarqueeRepeatLimit(5);
                    h.tvMsrmnt.setSelected(true);
                    h.tvLab.setText(map.get("lab"));
                    h.tvShape.setText(map.get("sshort_name"));
                    h.tvRapAmt.setText("$: " + String.valueOf(df.format(Double.parseDouble(map.get("net_amount")))));
                    h.tvRapPrice.setText("Rap: " + String.valueOf(df.format(Double.parseDouble(map.get("cur_rap_rate")))));
                    h.tvBgm.setText(map.get("bgm"));

                    if (map.get("location") != null && map.get("location") != "" && map.get("location").equalsIgnoreCase("Upcoming")) {
                        h.llMain.setBackgroundColor(Color.rgb(255, 255, 153));
                    }

//                    h.tlClick.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            startActivity(new Intent(context, StoneDetailActivity.class).putExtra("stoneid", map.get("stone_ref_no")));
//                            overridePendingTransition(0, 0);
//                        }
//                    });

                    h.llClick2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(context, StoneDetailActivity.class).putExtra("stoneid", map.get("stone_ref_no")));
                            overridePendingTransition(0, 0);
                        }
                    });

                    if (hmSelected.containsKey(position)) {
                        h.cbBox.setChecked(true);
                        if (map.get("location") != null && map.get("location") != "" && map.get("location").equalsIgnoreCase("Upcoming")) {
                            h.llMain.setBackgroundDrawable(getResources().getDrawable(R.drawable.upcoming));
                            h.tvtick.setVisibility(View.VISIBLE);
                        }else{
                            h.llMain.setBackgroundDrawable(getResources().getDrawable(R.drawable.clickable));
                            h.tvtick.setVisibility(View.VISIBLE);
                        }
                    } else {
                        h.cbBox.setChecked(false);
                        h.tvtick.setVisibility(View.GONE);
                        if (map.get("location") != null && map.get("location") != "" && map.get("location").equalsIgnoreCase("Upcoming")) {
                            h.llMain.setBackgroundColor(Color.rgb(255, 255, 153));
                        }else {
                            h.llMain.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_background_box));
                        }
                    }

                    h.llClick.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (hmSelected.containsKey(position)) {
                                hmSelected.remove(position);
                                cbSelectAll.setChecked(false);

                                tvTotalPcs.setText(hmSelected.size() + "");
                                cts = Double.parseDouble(tvTotalCts.getText().toString()) - Double.parseDouble(map.get("cts"));
                                tvTotalCts.setText(String.format("%,.2f", cts));

                                amt = Double.parseDouble(tvTotalAmt.getText().toString().replace("$","").replace(",","").split(" ")[1]) - Double.parseDouble(map.get("net_amount"));
                                tvTotalAmt.setText("$ " + String.format("%,.0f", amt));

                                rap = rap - Double.parseDouble(map.get("rap_amount"));
                                avgDisc = (100 - ((Double.parseDouble(tvTotalAmt.getText().toString().replace("$","").replace(",","").split(" ")[1]) * 100) / rap)) * -1;
                                tvTotalAvgDiscPer.setText(avgDisc.isNaN() ? "0" : String.format("%.2f", avgDisc) + " %");

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
                                hmSelected.put(position, map.get("stone_ref_no"));
                                if (hmSelected.size() == arraylist.size()) {
                                    cbSelectAll.setChecked(true);
                                    h.llMain.setBackgroundDrawable(getResources().getDrawable(R.drawable.clickable));
                                    h.tvtick.setVisibility(View.VISIBLE);
                                }
                                if (hmSelected.size() == 1) {
                                    clearTotalAdp();
                                }
                                tvTotalPcs.setText(hmSelected.size() + "");
                                cts = Double.parseDouble(tvTotalCts.getText().toString()) + Double.parseDouble(map.get("cts"));
                                tvTotalCts.setText(String.format("%,.2f", cts));

                                amt = Double.parseDouble(tvTotalAmt.getText().toString().replace("$","").replace(",","").split(" ")[1]) + Double.parseDouble(map.get("net_amount"));
                                tvTotalAmt.setText("$ " + String.format("%,.0f", amt));

                                rap = rap + Double.parseDouble(map.get("rap_amount"));
                                avgDisc = (100 - ((Double.parseDouble(tvTotalAmt.getText().toString().replace("$","").replace(",","").split(" ")[1]) * 100) / rap)) * -1;
                                tvTotalAvgDiscPer.setText(avgDisc.isNaN() ? "0" : String.format("%.2f", avgDisc) + " %");

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
            } else {
                if (holder instanceof ResultAdp.ListViewHolder) {
                    final ResultAdp.ListViewHolder h = (ResultAdp.ListViewHolder) holder;

                    if (map.get("location").equalsIgnoreCase("Hong Kong")) {
                        h.imgLocation.setImageResource(R.drawable.ic_hk);
                    } else if (map.get("location").equalsIgnoreCase("India")) {
                        h.imgLocation.setImageResource(R.drawable.ic_india);
                    } else if (map.get("location").equalsIgnoreCase("Upcoming")) {
                        h.imgLocation.setImageResource(R.drawable.ic_plane);
                    } else if (map.get("location").equalsIgnoreCase("SHOW")) {
                        h.imgLocation.setImageResource(R.drawable.ic_fair);
                    } else if (map.get("location").equalsIgnoreCase("Dubai")) {
                        h.imgLocation.setImageResource(R.drawable.ic_dubai);
                    }

                    h.tvStoneID.setText(map.get("stone_ref_no"));

                    if (map.get("status").equalsIgnoreCase("AVAILABLE")) {
                        h.tvStatus.setText("A");
                        h.tvStatus.setBackgroundDrawable(Const.setBackgoundBorder(0, 10, getResources().getColor(R.color.green), Color.TRANSPARENT));
                        h.tvStatus.setTextColor(Color.WHITE);
                    } else if (map.get("status").equalsIgnoreCase("AVAILABLE OFFER")) {
                        h.tvStatus.setText("O");
                        h.tvStatus.setBackgroundDrawable(Const.setBackgoundBorder(0, 10, getResources().getColor(R.color.ao), Color.TRANSPARENT));
                        h.tvStatus.setTextColor(Color.BLACK);
                    } else if (map.get("status").equalsIgnoreCase("NEW")) {
                        h.tvStatus.setText("N");
                        h.tvStatus.setBackgroundDrawable(Const.setBackgoundBorder(0, 10, getResources().getColor(R.color.pink), Color.TRANSPARENT));
                        h.tvStatus.setTextColor(Color.BLACK);
                    } else if (map.get("status").equalsIgnoreCase("BUSS. PROCESS")) {
                        h.tvStatus.setText("B");
                        h.tvStatus.setBackgroundDrawable(Const.setBackgoundBorder(0, 10, getResources().getColor(R.color.orange), Color.TRANSPARENT));
                        h.tvStatus.setTextColor(Color.WHITE);
                    }

                    h.tvShape.setText(map.get("shape"));
                    h.tvPointer.setText(map.get("pointer"));
                    h.tvLab.setText(map.get("lab"));
                    h.tvCertiNo.setText(map.get("certi_no"));
                    h.tvBgm.setText(map.get("bgm"));
                    h.tvColor.setText(map.get("color"));
                    h.tvClarity.setText(map.get("clarity"));
                    h.tvCts.setText(String.format("%,.2f", Double.parseDouble(map.get("cts"))));
                    h.tvRapPrice.setText(String.format("%,.2f", Double.parseDouble(map.get("cur_rap_rate"))));
                    h.tvRapAmt.setText(String.format("%,.2f", Double.parseDouble(map.get("rap_amount"))));
                    h.tvDiscPer.setText(String.format("%.2f", Double.parseDouble(map.get("sales_disc_per"))));
                    h.tvNetAmt.setText(String.format("%,.2f", Double.parseDouble(map.get("net_amount"))));
                    h.tvPriceCts.setText(String.format("%,.2f", Double.parseDouble(map.get("price_per_cts"))));

                    if (map.get("cut").equalsIgnoreCase("3EX")) {
                        h.tvCut.setTypeface(Typeface.DEFAULT_BOLD);
                        h.tvPolish.setTypeface(Typeface.DEFAULT_BOLD);
                        h.tvSymm.setTypeface(Typeface.DEFAULT_BOLD);
                    } else {
                        h.tvCut.setTypeface(Typeface.DEFAULT);
                        h.tvPolish.setTypeface(Typeface.DEFAULT);
                        h.tvSymm.setTypeface(Typeface.DEFAULT);
                    }

                    h.tvCut.setText(map.get("cut"));
                    h.tvPolish.setText(map.get("polish"));
                    h.tvSymm.setText(map.get("symm"));
                    h.tvFls.setText(map.get("fls"));
                    h.tvLength.setText(map.get("length"));
                    h.tvWidth.setText(map.get("width"));
                    h.tvDepth.setText(map.get("depth"));
                    h.tvDepthPer.setText(map.get("depth_per"));
                    h.tvTablePer.setText(map.get("table_per"));
                    if(!map.get("symbol").equalsIgnoreCase("null")){
                        h.tvKeyToSymbol.setText(map.get("symbol"));
                    }else{
                        h.tvKeyToSymbol.setText("");
                    }
                    h.tvCulet.setText(map.get("sculet"));
                    h.tvTableBlack.setText(map.get("table_natts"));
                    h.tvCrownBlack.setText(map.get("crown_natts"));
                    h.tvTableWhite.setText(map.get("inclusion"));
                    h.tvCrownWhite.setText(map.get("crown_inclusion"));
                    h.tvCrownAngle.setText(map.get("crown_angle"));
                    h.tvCrownHeight.setText(map.get("crown_height"));
                    h.tvPavilionAngle.setText(map.get("pav_angle"));
                    h.tvPavilionHeight.setText(map.get("pav_height"));
                    h.tvGirdlePer.setText(map.get("girdle_per"));
                    h.tvGirdleType.setText(map.get("girdle_type"));
                    h.tvLaserInsc.setText(map.get("sinscription"));


                    if (map.get("location") != null && map.get("location") != "" && map.get("location").equalsIgnoreCase("Upcoming")) {
                        h.llMain.setBackgroundColor(Color.rgb(255, 255, 153));
                    }

                    if (hmSelected.containsKey(position)) {
                        h.cbBox.setChecked(true);
                        h.llMain.setBackgroundColor(Color.rgb(135, 206, 250));
                    } else {
                        h.cbBox.setChecked(false);
                    }

                    h.cbBox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (hmSelected.containsKey(position)) {
                                hmSelected.remove(position);
                                cbSelectAll.setChecked(false);

                                tvTotalPcs.setText(hmSelected.size() + "");
                                cts = Double.parseDouble(tvTotalCts.getText().toString().replace(",","")) - Double.parseDouble(map.get("cts"));
                                tvTotalCts.setText(String.format("%,.2f", cts));

                                amt = Double.parseDouble(tvTotalAmt.getText().toString().replace("$","").replace(",","").split(" ")[1]) - Double.parseDouble(map.get("net_amount"));
                                tvTotalAmt.setText("$ " + String.format("%,.0f", amt));

                                rap = rap - Double.parseDouble(map.get("rap_amount"));
                                avgDisc = (100 - ((Double.parseDouble(tvTotalAmt.getText().toString().replace("$","").replace(",","").split(" ")[1]) * 100) / rap)) * -1;
                                tvTotalAvgDiscPer.setText(avgDisc.isNaN() ? "0" : String.format("%.2f", avgDisc) + " %");
                                if (hmSelected.size() == 0) {
                                    clearTotal();
                                }
                                if (map.get("location") != null && map.get("location") != "" && map.get("location").equalsIgnoreCase("Upcoming")){
                                    h.llMain.setBackgroundColor(Color.rgb(255, 255, 153));
                                } else {
                                    h.llMain.setBackgroundDrawable(getResources().getDrawable(R.color.white));
                                }
                            } else {
                                hmSelected.put(position, map.get("stone_ref_no"));
                                if (hmSelected.size() == arraylist.size()) {
                                    cbSelectAll.setChecked(true);
                                }
                                if (hmSelected.size() == 1) {
                                    clearTotalAdp();
                                }
                                tvTotalPcs.setText(hmSelected.size() + "");
                                cts = Double.parseDouble(tvTotalCts.getText().toString().replace(",","")) + Double.parseDouble(map.get("cts"));
                                tvTotalCts.setText(String.format("%,.2f", cts));

                                amt = Double.parseDouble(tvTotalAmt.getText().toString().replace("$","").replace(",","").split(" ")[1]) + Double.parseDouble(map.get("net_amount"));
                                tvTotalAmt.setText("$ " + String.format("%,.0f", amt));

                                rap = rap + Double.parseDouble(map.get("rap_amount"));
                                avgDisc = (100 - ((Double.parseDouble(tvTotalAmt.getText().toString().replace("$","").replace(",","").split(" ")[1]) * 100) / rap)) * -1;
                                tvTotalAvgDiscPer.setText(avgDisc.isNaN() ? "0" : String.format("%.2f", avgDisc) + " %");

                                h.llMain.setBackgroundColor(Color.rgb(135, 206, 250));
                            }
                            ppc = Double.parseDouble(tvTotalAmt.getText().toString().replace("$","").replace(",","").split(" ")[1]) / Double.parseDouble(tvTotalCts.getText().toString().replace(",",""));
                            tvTotalPricePerCts.setText("$ " + (ppc.isNaN() ? "0" : String.format("%,.2f", ppc)));
                        }
                    });

                }
            }
        }

        @Override
        public int getItemCount() {
            return arraylist.size();
        }

        private class GridViewHolder extends RecyclerView.ViewHolder {

            CheckBox cbBox;
            RelativeLayout llClick;
            TableLayout tlClick;
            LinearLayout llMain, llClick2;
            TextView tvStatus, tvColor, tvCPS, tvBgm, tvShape, tvRapAmt, tvFls, tvRapPrice, tvDisc, tvClarity, tvPolish, tvAmt, tvCts, tvSym, tvPriceCts, tvMsrmnt, tvLab, tvStoneID, tvCertiNo, tvtick;
            ImageView imgLocation;

            private GridViewHolder(@NonNull View v) {
                super(v);
                cbBox = v.findViewById(R.id.cbBox);
                llMain = v.findViewById(R.id.llMain);
                llClick = v.findViewById(R.id.llClick);
                llClick2 = v.findViewById(R.id.llClick2);
                tlClick = v.findViewById(R.id.tlClick);
                tvRapPrice = v.findViewById(R.id.tvRapPrice);
                tvBgm = v.findViewById(R.id.tvBgm);
                imgLocation = v.findViewById(R.id.imgLocation);
                tvRapAmt = v.findViewById(R.id.tvRapAmt);
                tvShape = v.findViewById(R.id.tvShape);
                tvStatus = v.findViewById(R.id.tvStatus);
                tvColor = v.findViewById(R.id.tvColor);
                tvCPS = v.findViewById(R.id.tvCPS);
                tvFls = v.findViewById(R.id.tvFls);
                tvDisc = v.findViewById(R.id.tvDisc);
                tvClarity = v.findViewById(R.id.tvClarity);
                tvPolish = v.findViewById(R.id.tvPolish);
                tvCts = v.findViewById(R.id.tvCts);
                tvSym = v.findViewById(R.id.tvSym);
                tvPriceCts = v.findViewById(R.id.tvPriceCts);
                tvMsrmnt = v.findViewById(R.id.tvMsrmnt);
                tvLab = v.findViewById(R.id.tvLab);
                tvStoneID = v.findViewById(R.id.tvStoneID);
                tvCertiNo = v.findViewById(R.id.tvCertiNo);
                tvtick = v.findViewById(R.id.tvtick);
            }
        }

        private class ListViewHolder extends RecyclerView.ViewHolder {

            CheckBox cbBox;
            ImageView imgLocation;
            TextView tvStoneID, tvStatus, tvShape, tvPointer, tvLab, tvCertiNo, tvBgm, tvColor, tvClarity, tvCts, tvRapPrice,
                    tvRapAmt, tvDiscPer, tvNetAmt, tvPriceCts, tvCut, tvPolish, tvSymm, tvFls, tvLength, tvWidth, tvDepth, tvDepthPer,
                    tvTablePer, tvKeyToSymbol, tvCulet, tvTableBlack, tvCrownBlack, tvTableWhite, tvCrownWhite, tvCrownAngle,
                    tvCrownHeight, tvPavilionAngle, tvPavilionHeight, tvGirdlePer, tvGirdleType, tvLaserInsc;
            LinearLayout llMain;

            private ListViewHolder(@NonNull View v) {
                super(v);
                llMain = v.findViewById(R.id.llMain);
                cbBox = v.findViewById(R.id.cbBox);
                imgLocation = v.findViewById(R.id.imgLocation);
                tvStoneID = v.findViewById(R.id.tvStoneID);
                tvStatus = v.findViewById(R.id.tvStatus);
                tvShape = v.findViewById(R.id.tvShape);
                tvPointer = v.findViewById(R.id.tvPointer);
                tvLab = v.findViewById(R.id.tvLab);
                tvCertiNo = v.findViewById(R.id.tvCertiNo);
                tvBgm = v.findViewById(R.id.tvBgm);
                tvColor = v.findViewById(R.id.tvColor);
                tvClarity = v.findViewById(R.id.tvClarity);
                tvCts = v.findViewById(R.id.tvCts);
                tvRapPrice = v.findViewById(R.id.tvRapPrice);
                tvRapAmt = v.findViewById(R.id.tvRapAmt);
                tvDiscPer = v.findViewById(R.id.tvDiscPer);
                tvNetAmt = v.findViewById(R.id.tvNetAmt);
                tvPriceCts = v.findViewById(R.id.tvPriceCts);
                tvCut = v.findViewById(R.id.tvCut);
                tvPolish = v.findViewById(R.id.tvPolish);
                tvSymm = v.findViewById(R.id.tvSymm);
                tvFls = v.findViewById(R.id.tvFls);
                tvLength = v.findViewById(R.id.tvLength);
                tvWidth = v.findViewById(R.id.tvWidth);
                tvDepth = v.findViewById(R.id.tvDepth);
                tvDepthPer = v.findViewById(R.id.tvDepthPer);
                tvTablePer = v.findViewById(R.id.tvTablePer);
                tvKeyToSymbol = v.findViewById(R.id.tvKeyToSymbol);
                tvCulet = v.findViewById(R.id.tvCulet);
                tvTableBlack = v.findViewById(R.id.tvTableBlack);
                tvCrownBlack = v.findViewById(R.id.tvCrownBlack);
                tvTableWhite = v.findViewById(R.id.tvTableWhite);
                tvCrownWhite = v.findViewById(R.id.tvCrownWhite);
                tvCrownAngle = v.findViewById(R.id.tvCrownAngle);
                tvCrownHeight = v.findViewById(R.id.tvCrownHeight);
                tvPavilionAngle = v.findViewById(R.id.tvPavilionAngle);
                tvPavilionHeight = v.findViewById(R.id.tvPavilionHeight);
                tvGirdlePer = v.findViewById(R.id.tvGirdlePer);
                tvGirdleType = v.findViewById(R.id.tvGirdleType);
                tvLaserInsc = v.findViewById(R.id.tvLaserInsc);
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
                case R.id.imgOMenu:
                    clearTotal();
                    cbSelectAll.setChecked(false);
                    maps = new ArrayList<>();
                    pageCount = 1;
                    totalSize = 0;
                    etSearch.setText("");
                    etSearch.setVisibility(View.GONE);
                    imgSearch.setVisibility(View.VISIBLE);
                    tvTitle.setVisibility(View.VISIBLE);
                    Const.hideSoftKeyboard(SearchResult4Activity.this);
                    etSearch.clearFocus();
                    Const.resetParameters(context);
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
                case R.id.llPlus:
                    d.show();
                    break;
                case R.id.imgSumm:
                    if (hmSelected.size() >= 1) {
                        SummDialog();
                    } else {
                        Const.showErrorDialog(context, "Please select at least one stone");
                    }
                    break;
                case R.id.imgSearch:
                    tvTitle.setVisibility(View.GONE);
                    imgSearch.setVisibility(View.GONE);
                    etSearch.setVisibility(View.VISIBLE);
                    etSearch.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(etSearch, InputMethodManager.SHOW_IMPLICIT);
                    break;
                case R.id.llCart:
                    if (hmSelected.size() >= 1) {
                        addToCart();
                    } else {
                        Const.showErrorDialog(context, "Please select at least one stone");
                    }
                    break;
                case R.id.imgLayout:
                    if (isChange) {
                        isChange = false;
                        imgLayout.setImageDrawable(getResources().getDrawable(R.drawable.ic_list));
                        llHorizontal.setVisibility(View.VISIBLE);
                        rvList.setLayoutParams(parms);
                        adp = new ResultAdp(context, maps, false);
                        rvList.setAdapter(adp);
                        adp.notifyDataSetChanged();
                    } else {
                        isChange = true;
                        imgLayout.setImageDrawable(getResources().getDrawable(R.drawable.ic_grid));
                        llHorizontal.setVisibility(View.GONE);
                        rvList.setLayoutParams(parms2);
                        adp = new ResultAdp(context, maps, true);
                        rvList.setAdapter(adp);
                        adp.notifyDataSetChanged();
                    }
                    break;
                case R.id.llModify:
                    if (getIntent().getFlags() == 555) {
                        HashMap<String, String> hm = (HashMap<String, String>) getIntent().getSerializableExtra("selected");
                        Intent i = new Intent(context, SearchFilterActivity.class);
                        i.putExtra("selected", hm);
                        i.setFlags(555);
                        context.startActivity(i);
                    }else{
                        onBackPressed();
                    }
                    break;
                case R.id.llPlaceOrder:
                    if (hmSelected.size() >= 1) {
                        PlaceOrderDialog();
                    } else {
                        Const.showErrorDialog(context, "Please select at least one stone");
                    }
                    break;
                case R.id.llEmailStone:
                    emailDialog();
                    break;
                case R.id.Menu_Share:
                    d.dismiss();
                    if (hmSelected.size() > 5) {
                        Const.showErrorDialog(context, "You can select maximum 5 stones to share information!");
                    } else if (hmSelected.size() > 0) {
                        shareDialog();
                    } else {
                        Const.showErrorDialog(context, "Please select at least 1 stone");
                    }
                    break;
                case R.id.Menu_Wishlist:
                    d.dismiss();
                    if (hmSelected.size() > 0) {
                        addTowishList();
                    } else {
                        Const.showErrorDialog(context, "Please select at least 1 stone");
                    }
                    break;
                case R.id.Menu_Copy:
                    d.dismiss();
                    if (hmSelected.size() > 5) {
                        Const.showErrorDialog(context, "You can select maximum 5 stones to download information!");
                    }else if (hmSelected.size() > 0) {
                        CopyDialog();
                    } else {
                        Const.showErrorDialog(context, "Please select at least 1 stone");
                    }
                    break;
                case R.id.Menu_Download:
                    d.dismiss();
                    if (hmSelected.size() > 5) {
                        Const.showErrorDialog(context, "You can select maximum 5 stones to download information!");
                    }else if (hmSelected.size() > 0) {
                        downloadDialog();
                    } else {
                        Const.showErrorDialog(context, "Please select at least 1 stone");
                    }
                    break;
                case R.id.llStatus:
                    statusInfoAlert();
                    break;
                case R.id.Menu_Stock:
                    d.dismiss();
                    DownloadStockExcel();
                    break;
                case R.id.Menu_Compare:
                    d.dismiss();
                    if (hmSelected.size() == 0) {
                        Const.showErrorDialog(context, "Please select at least 2 stone");
                    } else if (hmSelected.size() == 1) {
                        Const.showErrorDialog(context, "Please select at least 2 stone");
                    } else if (hmSelected.size() > 4) {
                        Const.showErrorDialog(context, "You can compare maximum 4 stone");
                    } else {
                        Const.arrayCompareStone = new ArrayList<>();
                        for (int i = 0; i < maps.size(); i++) {
                            if (hmSelected.get(i) != null) {
                                Const.arrayCompareStone.add(maps.get(i));
                                if (hmSelected.size() == Const.arrayCompareStone.size()) {
                                    break;
                                }
                            }
                        }
                        startActivity(new Intent(context, CompareStoneActivity.class));
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
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

}
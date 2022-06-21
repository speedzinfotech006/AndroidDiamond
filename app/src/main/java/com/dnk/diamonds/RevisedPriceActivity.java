package com.dnk.shairugems;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dnk.shairugems.Class.BindData;
import com.dnk.shairugems.Class.DownloadImage;
import com.dnk.shairugems.Utils.Const;
import com.dnk.shairugems.Utils.Pref;
import com.dnk.shairugems.Volly.VolleyCallback;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RevisedPriceActivity extends BaseDrawerActivity {

    ImageView imgMenu, imgOMenu, imgViewCount, imgLayout, imgSearch;
    LinearLayout llViewCount, llModify, llCart, llHorizontal, llPlaceOrder, llDownload, llEmailStone;
    TextView tvTitle, tvTotalCount, tvTotalPcs, tvTotalCts, tvTotalAvgDiscPer, tvTotalPricePerCts, tvTotalAmt;
    EditText etSearch;
    CheckBox cbSelectAll;
    RecyclerView rvList;
    LinearLayoutManager lm;
    ResultAdp adp;
    com.github.clans.fab.FloatingActionButton llShare,llWish,llCompare,llStatus;
    LinearLayout.LayoutParams parms, parms2;
    HashMap<Integer, String> hmSelected = new HashMap<>();
    ArrayList<HashMap<String, String>> maps = new ArrayList<>();
    int pastVisiblesItems, visibleItemCount, totalItemCount, pageCount = 1, totalSize = 0;
    Double rap = 0.0, totalRap = 0.0;
    String totalPcs = "0", totalCts = "0", totalAmount = "0", totalPricePerCts = "0", totalAvgDicPer = "0";
    DecimalFormat df = new DecimalFormat("#,##,###.00");
    DecimalFormat df1 = new DecimalFormat("##,###,###");
    boolean isChange = true;
    Context context = RevisedPriceActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_revised_price, frameLayout);

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
                                Const.hideSoftKeyboard(RevisedPriceActivity.this);
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
                        if (etSearch.getText().toString().matches(".*[A-Za-z].*")) {
                            Pref.setStringValue(context, Const.stone_id, etSearch.getText().toString());
                        } else {
                            Pref.setStringValue(context, Const.certi_no, etSearch.getText().toString());
                        }
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

    }

    private void initView() {
        imgMenu = findViewById(R.id.imgMenu);
        imgOMenu = findViewById(R.id.imgOMenu);
        etSearch = findViewById(R.id.etSearch);
        cbSelectAll = findViewById(R.id.cbSelectAll);
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
        llViewCount = findViewById(R.id.llViewCount);
        llHorizontal = findViewById(R.id.llHorizontal);
        llModify = findViewById(R.id.llModify);
        llPlaceOrder = findViewById(R.id.llPlaceOrder);
        llDownload = findViewById(R.id.llDownload);
        llEmailStone = findViewById(R.id.llEmailStone);

        llCart = findViewById(R.id.llCart);
        rvList = findViewById(R.id.rvList);

        llShare = findViewById(R.id.llShare);
        llWish = findViewById(R.id.llWish);
        llCompare = findViewById(R.id.llCompare);
        llStatus = findViewById(R.id.llStatus);

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

        llShare.setOnClickListener(clickListener);
        llWish.setOnClickListener(clickListener);
        llCompare.setOnClickListener(clickListener);
        llStatus.setOnClickListener(clickListener);

        llCart.setBackgroundDrawable(Const.setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));
        llModify.setBackgroundDrawable(Const.setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));

        llShare.setBackgroundDrawable(Const.setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));
        llWish.setBackgroundDrawable(Const.setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));
        llCompare.setBackgroundDrawable(Const.setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));
        llStatus.setBackgroundDrawable(Const.setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));

        parms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        parms2 = new LinearLayout.LayoutParams(displayMetrics.widthPixels, ViewGroup.LayoutParams.MATCH_PARENT);
        rvList.setLayoutParams(parms2);

        Drawable wrappedDrawable = DrawableCompat.wrap(AppCompatResources.getDrawable(context, R.drawable.ic_close));
        etSearch.setCompoundDrawablesWithIntrinsicBounds(null, null, wrappedDrawable, null);

        llModify.setVisibility(View.GONE);
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
                    Const.hideSoftKeyboard(RevisedPriceActivity.this);
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
        map.put("CertiNo", Pref.getStringValue(context, Const.stone_id, ""));
        map.put("PageNo", pageCount + "");
        map.put("TokenNo", Pref.getStringValue(context, Const.loginToken, ""));
        Const.callPostApi(context, "Stock/GetRevisedStock", map, new VolleyCallback() {
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
        Const.callPostApi(context, "Order/PurchaseConfirmOrder", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Const.dismissProgress();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.optString("Status").equalsIgnoreCase("SUCCESS")) {
                        String ob = new JSONObject(result).optString("Message");
                        clearTotal();
                        adp.notifyDataSetChanged();
                        Const.showErrorDialog(context, Const.notNullString(ob, "This Stone(s) are subject to avaibility 'BZM-318692'"));
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
        final TextView tvMsg = dialog.findViewById(R.id.tvMsg);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnSend = dialog.findViewById(R.id.btnSend);

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
                    SendMail(etEmail.getText().toString(),etComments.getText().toString());
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

    private void shareDialog() {
        final BottomSheetDialog dialog = new BottomSheetDialog(context);
        dialog.setContentView(R.layout.layout_share);

        TextView tvDetails = dialog.findViewById(R.id.tvDetails);
        TextView tvImage = dialog.findViewById(R.id.tvImage);
        TextView tvVideo = dialog.findViewById(R.id.tvVideo);
        TextView tvCertificate = dialog.findViewById(R.id.tvCertificate);
        TextView tvAll = dialog.findViewById(R.id.tvAll);

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
                if (v.getId() == R.id.tvDetails) {
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
                            si = si + "✈ ";
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
                } else if (v.getId() == R.id.tvImage) {
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
                        new DownloadImage().download(context, image_list, "");
                    } else {
                        Const.showErrorDialog(context, "Sorry no image(s) available for selected stone(s) to share");
                    }
                } else if (v.getId() == R.id.tvVideo) {
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
                } else if (v.getId() == R.id.tvCertificate) {
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
                } else {
                   String s = "";
                    s=" Sunrise Diamond\nhttps://sunrisediamonds.com.hk/"+"\n\n";
                    for (int j = 0; j < list.size(); j++) {
                        String si = "\nStone ID : " + list.get(j).get("stone_ref_no") + "\n"
                                + "Status : " + list.get(j).get("status") + "\n"
                                + "Lab : " + list.get(j).get("lab") + "\n"
                                + "Shape : " + list.get(j).get("shape") + "\n"
                                + "Certi No : " + list.get(j).get("certi_no") + "\n"
                                + "Color : " + list.get(j).get("color") + "\n"
                                + "Clarity : " + list.get(j).get("clarity") + "\n"
                                + "Carat : " + String.format("%.2f", Double.parseDouble(list.get(j).get("cts"))) + "\n"
                                + "Rap Price : " + String.format("%,.2f", Double.parseDouble(list.get(j).get("cur_rap_rate"))) + " $\n"
                                + "Rap Amount : " + String.format("%,.2f", Double.parseDouble(list.get(j).get("rap_amount"))) + " $\n"
                                + "Discount : " + String.format("%.2f", Double.parseDouble(list.get(j).get("sales_disc_per"))) + " %\n"
                                + "Net Amount : " + String.format("%,.2f", Double.parseDouble(list.get(j).get("net_amount"))) + " $\n"
                                + "Cut : " + list.get(j).get("cut") + "\n"
                                + "Polish : " + list.get(j).get("polish") + "\n"
                                + "Symmetry : " + list.get(j).get("symm") + "\n"
                                + "Fluorescence : " + list.get(j).get("fls") + "\n"
                                + "Measurements : " + list.get(j).get("measurement") + "\n"
                                + "Depth(%) : " + list.get(j).get("depth_per") + "\n"
                                + "Table(%) : " + list.get(j).get("table_per") + "\n";
                        if (!list.get(j).get("image_url").equalsIgnoreCase("")) {
                            si = si.concat("Image Link : " + list.get(j).get("image_url") + "\n");
                        }
                        if (!list.get(j).get("movie_url").equalsIgnoreCase("")) {
                            si = si.concat("Video Link : " + list.get(j).get("movie_url") + "\n");
                        }
                        if (!list.get(j).get("view_certi_url").equalsIgnoreCase("")) {
                            si = si.concat("Pdf Link : " + list.get(j).get("view_certi_url") + "\n");
                        }
                        s = s.concat(si);
                        s = s.concat("------------------------------------------");
                    }
                    s = s.substring(1, s.length() - 1);
                    i.putExtra(Intent.EXTRA_TEXT, s);
                    startActivity(Intent.createChooser(i, "Share via"));
                }
                dialog.dismiss();
            }
        };

        tvDetails.setOnClickListener(listener);
        tvImage.setOnClickListener(listener);
        tvVideo.setOnClickListener(listener);
        tvCertificate.setOnClickListener(listener);
        tvAll.setOnClickListener(listener);

        dialog.show();
    }

    private void downloadDialog() {
        final BottomSheetDialog dialog = new BottomSheetDialog(context);
        dialog.setContentView(R.layout.layout_share);

        TextView tvDetails = dialog.findViewById(R.id.tvDetails);
        TextView tvImage = dialog.findViewById(R.id.tvImage);
        TextView tvVideo = dialog.findViewById(R.id.tvVideo);
        TextView tvCertificate = dialog.findViewById(R.id.tvCertificate);
        TextView tvAll = dialog.findViewById(R.id.tvAll);

        tvDetails.setVisibility(View.GONE);

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
                if (v.getId() == R.id.tvImage) {
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
                } else if (v.getId() == R.id.tvVideo) {
                    for (int j = 0; j < list.size(); j++) {
                        downloadmedia(list.get(j).get("stone_ref_no"));
                    }
                } else if (v.getId() == R.id.tvCertificate) {
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
                                        Const.showErrorDialog(context, "Sorry no pdf link(s) available for selected stone(s) to share");
                                    }
                                }
                            });
                        }
                    }).start();
                } else {
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

        tvDetails.setOnClickListener(listener);
        tvImage.setOnClickListener(listener);
        tvVideo.setOnClickListener(listener);
        tvCertificate.setOnClickListener(listener);
        tvAll.setOnClickListener(listener);

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
        Double avgDisc = (100 - ((Double.parseDouble(tvTotalAmt.getText().toString().replace("$","").replace(",","").split(" ")[1]) * 100) / totalrap)) * -1;
        tvTotalAvgDiscPer.setText(avgDisc.isNaN() ? "0" : String.format("%.2f", avgDisc) + " %");
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
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (isLayout) {
                View view = LayoutInflater.from(context).inflate(R.layout.resultdiamondrow_newdesign, parent, false);
                return new ResultAdp.GridViewHolder(view);
            } else {
                View view = LayoutInflater.from(context).inflate(R.layout.layout_hori_view, parent, false);
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
                    } else if ("BUSS. PROCESS".equals(map.get("status"))) {
                        h.llMain.setBackgroundColor(Color.rgb(122, 255, 230));
                    } else if (map.get("soffer").equalsIgnoreCase("1")) {
                        h.llMain.setBackgroundColor(Color.rgb(204, 255, 255));
                    } else if (map.get("location") != null && map.get("Location") != "" && map.get("location").equalsIgnoreCase("SHOW")) {
                        h.llMain.setBackgroundColor(Color.rgb(224, 219, 214));
                    } else if ("Y".equalsIgnoreCase(map.get("new_arrival"))) {
                        h.llMain.setBackgroundColor(Color.rgb(255, 194, 194));
                    } else if ("Y".equalsIgnoreCase(map.get("promotion"))) {
                        h.llMain.setBackgroundColor(Color.rgb(216, 255, 68));
                    }

                    h.tlClick.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(context, StoneDetailActivity.class).putExtra("stoneid", map.get("stone_ref_no")));
                            overridePendingTransition(0, 0);
                        }
                    });

                    h.llClick2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(context, StoneDetailActivity.class).putExtra("stoneid", map.get("stone_ref_no")));
                            overridePendingTransition(0, 0);

//                            Intent intent = new Intent(getBaseContext(), StoneDetailActivity.class);
//                            intent.putExtra("stoneid", map.get("stone_ref_no"));
//                            overridePendingTransition(0, 0);
                        }
                    });

//                    h.imgVideo.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (map.containsKey("movie_url")) {
//                                Const.hmStoneDetail = map;
//                                startActivity(new Intent(context, StoneDetailActivity.class).putExtra("flag", "video"));
//                                overridePendingTransition(0, 0);
//                            }
//                        }
//                    });
//
//                    h.imgCerti.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Const.hmStoneDetail = map;
//                            startActivity(new Intent(context, StoneDetailActivity.class).putExtra("flag", "certi"));
//                            overridePendingTransition(0, 0);
//                        }
//                    });
//
//                    h.imgView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (isShow == position) {
//                                isShow = -1;
//                            } else {
//                                isShow = position;
//                            }
//                            notifyDataSetChanged();
//                        }
//                    });
//
//                    if (isShow == position) {
//                        h.imgView.setImageDrawable(getResources().getDrawable(R.drawable.ic_minus));
//                        h.rlAdvView.setVisibility(View.VISIBLE);
//                    } else {
//                        h.imgView.setImageDrawable(getResources().getDrawable(R.drawable.ic_plus));
//                        h.rlAdvView.setVisibility(View.GONE);
//                    }

//                    h.llClick.setOnClickListener(new View.OnClickListener() {
//                        boolean clicked = true;
//
//                        @Override
//                        public void onClick(View v) {
//                            if(!clicked){
//                                h.llMain.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_background_box));
//                                h.cbBox.setChecked(false);
//                                clicked = true;
//                            }else{
//                                h.llMain.setBackgroundColor(Color.rgb(135, 206, 250));
//                                h.cbBox.setChecked(true);
//                                clicked = false;
//                            }
//                        }
//                    });
                    if (hmSelected.containsKey(position)) {
                        h.cbBox.setChecked(true);
                        h.llMain.setBackgroundDrawable(getResources().getDrawable(R.drawable.clickable));
                        h.tvtick.setVisibility(View.VISIBLE);
                    } else {
                        h.cbBox.setChecked(false);
//                        if (map.get("location") != null && map.get("location") != "" && map.get("location").equalsIgnoreCase("Upcoming")){
//                            h.llMain.setBackgroundColor(Color.rgb(255, 255, 153));
//                        } else if ("BUSS. PROCESS".equals(map.get("status"))) {
//                            h.llMain.setBackgroundColor(Color.rgb(122, 255, 230));
//                        } else if (map.get("soffer").equalsIgnoreCase("1")) {
//                            h.llMain.setBackgroundColor(Color.rgb(204, 255, 255));
//                        } else if (map.get("location") != null && map.get("Location") != "" && map.get("location").equalsIgnoreCase("SHOW")) {
//                            h.llMain.setBackgroundColor(Color.rgb(224, 219, 214));
//                        } else if ("Y".equalsIgnoreCase(map.get("new_arrival"))) {
//                            h.llMain.setBackgroundColor(Color.rgb(255, 194, 194));
//                        } else if ("Y".equalsIgnoreCase(map.get("promotion"))) {
//                            h.llMain.setBackgroundColor(Color.rgb(216, 255, 68));
//                        } else {
                        h.llMain.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_background_box));
                        h.tvtick.setVisibility(View.GONE);
                    //      }
                    }

//                    h.cbBox.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            h.llClick.performClick();
//                        }
//                    })

                    h.llClick.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (hmSelected.containsKey(position)) {
                                hmSelected.remove(position);
                                cbSelectAll.setChecked(false);

                                tvTotalPcs.setText(hmSelected.size() + "");
                                Double cts = Double.parseDouble(tvTotalCts.getText().toString()) - Double.parseDouble(map.get("cts"));
                                tvTotalCts.setText(String.format("%,.2f", cts));

                                Double amt = Double.parseDouble(tvTotalAmt.getText().toString().replace("$","").replace(",","").split(" ")[1]) - Double.parseDouble(map.get("net_amount"));
                                tvTotalAmt.setText("$ " + String.format("%,.0f", amt));

                                rap = rap - Double.parseDouble(map.get("rap_amount"));
                                Double avgDisc = (100 - ((Double.parseDouble(tvTotalAmt.getText().toString().replace("$","").replace(",","").split(" ")[1]) * 100) / rap)) * -1;
                                tvTotalAvgDiscPer.setText(avgDisc.isNaN() ? "0" : String.format("%.2f", avgDisc) + " %");

                                if (hmSelected.size() == 0) {
                                    clearTotal();
                                }
//                                if (map.get("location") != null && map.get("location") != "" && map.get("location").equalsIgnoreCase("Upcoming")){
//                                    h.llMain.setBackgroundColor(Color.rgb(255, 255, 153));
//                                } else if ("BUSS. PROCESS".equals(map.get("status"))) {
//                                    h.llMain.setBackgroundColor(Color.rgb(122, 255, 230));
//                                } else if (map.get("soffer").equalsIgnoreCase("1")) {
//                                    h.llMain.setBackgroundColor(Color.rgb(204, 255, 255));
//                                } else if (map.get("location") != null && map.get("Location") != "" && map.get("location").equalsIgnoreCase("SHOW")) {
//                                    h.llMain.setBackgroundColor(Color.rgb(224, 219, 214));
//                                } else if ("Y".equalsIgnoreCase(map.get("new_arrival"))) {
//                                    h.llMain.setBackgroundColor(Color.rgb(255, 194, 194));
//                                } else if ("Y".equalsIgnoreCase(map.get("promotion"))) {
//                                    h.llMain.setBackgroundColor(Color.rgb(216, 255, 68));
//                                } else {
                                h.llMain.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_background_box));
                                h.tvtick.setVisibility(View.GONE);
                                //  }

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
                                Double cts = Double.parseDouble(tvTotalCts.getText().toString()) + Double.parseDouble(map.get("cts"));
                                tvTotalCts.setText(String.format("%,.2f", cts));

                                Double amt = Double.parseDouble(tvTotalAmt.getText().toString().replace("$","").replace(",","").split(" ")[1]) + Double.parseDouble(map.get("net_amount"));
                                tvTotalAmt.setText("$ " + String.format("%,.0f", amt));

                                rap = rap + Double.parseDouble(map.get("rap_amount"));
                                Double avgDisc = (100 - ((Double.parseDouble(tvTotalAmt.getText().toString().replace("$","").replace(",","").split(" ")[1]) * 100) / rap)) * -1;
                                tvTotalAvgDiscPer.setText(avgDisc.isNaN() ? "0" : String.format("%.2f", avgDisc) + " %");

                                // h.llMain.setBackgroundColor(Color.rgb(135, 206, 250));

//                                if (map.get("location") != null && map.get("location") != "" && map.get("location").equalsIgnoreCase("Upcoming")){
//                                    h.llMain.setBackgroundColor(Color.rgb(255, 255, 153));
//                                    Drawable unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.clickable);
//                                    Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
//                                    DrawableCompat.setTint(wrappedDrawable, Color.rgb(255, 255, 153));
//                                    h.llMain.setBackgroundDrawable(unwrappedDrawable);
//
//                                    Drawable mDrawable = ContextCompat.getDrawable(context,R.drawable.clickable);
//                                    mDrawable.setColorFilter(new
//                                            PorterDuffColorFilter(0xff2196F3,PorterDuff.Mode.SRC_IN));
//                                    h.tvtick.setVisibility(View.VISIBLE);
//
//                                } else if ("BUSS. PROCESS".equals(map.get("status"))) {
//                                    h.llMain.setBackgroundColor(Color.rgb(122, 255, 230));
//                                    Drawable unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.clickable);
//                                    Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
//                                    DrawableCompat.setTint(wrappedDrawable, Color.rgb(122, 255, 230));
//                                    h.llMain.setBackgroundDrawable(unwrappedDrawable);
//                                    h.tvtick.setVisibility(View.VISIBLE);
//                                } else if (map.get("soffer").equalsIgnoreCase("1")) {
//                                    h.llMain.setBackgroundColor(Color.rgb(204, 255, 255));
//                                    Drawable unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.clickable);
//                                    Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
//                                    DrawableCompat.setTint(wrappedDrawable, Color.rgb(204, 255, 255));
//                                    h.llMain.setBackgroundDrawable(unwrappedDrawable);
//                                    h.tvtick.setVisibility(View.VISIBLE);
//                                } else if (map.get("location") != null && map.get("Location") != "" && map.get("location").equalsIgnoreCase("SHOW")) {
//                                    h.llMain.setBackgroundColor(Color.rgb(224, 219, 214));
//                                    Drawable unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.clickable);
//                                    Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
//                                    DrawableCompat.setTint(wrappedDrawable, Color.rgb(224, 219, 214));
//                                    h.llMain.setBackgroundDrawable(unwrappedDrawable);
//                                    h.tvtick.setVisibility(View.VISIBLE);
//                                } else if ("Y".equalsIgnoreCase(map.get("new_arrival"))) {
//                                    h.llMain.setBackgroundColor(Color.rgb(255, 194, 194));
//                                    Drawable unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.clickable);
//                                    Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
//                                    DrawableCompat.setTint(wrappedDrawable, Color.rgb(255, 194, 194));
//                                    h.llMain.setBackgroundDrawable(unwrappedDrawable);
//                                    h.tvtick.setVisibility(View.VISIBLE);
//                                } else if ("Y".equalsIgnoreCase(map.get("promotion"))) {
//                                    h.llMain.setBackgroundColor(Color.rgb(216, 255, 68));
//                                    Drawable unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.clickable);
//                                    Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
//                                    DrawableCompat.setTint(wrappedDrawable, Color.rgb(216, 255, 68));
//                                    h.llMain.setBackgroundDrawable(unwrappedDrawable);
//                                    h.tvtick.setVisibility(View.VISIBLE);
//                                } else {
//                                    h.llMain.setBackgroundDrawable(getResources().getDrawable(R.drawable.clickable));
//                                    h.tvtick.setVisibility(View.VISIBLE);
//                                }

                                h.llMain.setBackgroundDrawable(getResources().getDrawable(R.drawable.clickable));
                                h.tvtick.setVisibility(View.VISIBLE);
                            }
                            Double ppc = Double.parseDouble(tvTotalAmt.getText().toString().replace("$","").replace(",","").split(" ")[1]) / Double.parseDouble(tvTotalCts.getText().toString().replace(",",""));
                            tvTotalPricePerCts.setText("$ " + (ppc.isNaN() ? "0" : String.format("%,.2f", ppc)));
                        }
                    });
                }
            } else {
                if (holder instanceof ResultAdp.ListViewHolder) {
                    final ResultAdp.ListViewHolder h = (ResultAdp.ListViewHolder) holder;

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

                    h.tvStoneID.setText(map.get("stone_ref_no"));
                    h.tvShape.setText(map.get("shape"));
                    h.tvRapPrice.setText(String.format("%,.2f", Double.parseDouble(map.get("rap_amount"))));
                    h.tvColor.setText(map.get("color"));
                    h.tvCut.setText(map.get("cut"));
                    if (map.get("cut").equalsIgnoreCase("3EX")) {
                        h.tvCut.setTypeface(Typeface.DEFAULT_BOLD);
                        h.tvPolish.setTypeface(Typeface.DEFAULT_BOLD);
                        h.tvSymm.setTypeface(Typeface.DEFAULT_BOLD);
                    } else {
                        h.tvCut.setTypeface(Typeface.DEFAULT);
                        h.tvPolish.setTypeface(Typeface.DEFAULT);
                        h.tvSymm.setTypeface(Typeface.DEFAULT);
                    }
                    h.tvFls.setText(map.get("fls"));
                    h.tvDiscPer.setText(String.format("%.2f", Double.parseDouble(map.get("sales_disc_per"))));
                    h.tvClarity.setText(map.get("clarity"));
                    h.tvPolish.setText(map.get("polish"));
                    h.tvTotalAmt.setText(String.format("%,.2f", Double.parseDouble(map.get("net_amount"))));
                    h.tvCts.setText(String.format("%,.2f", Double.parseDouble(map.get("cts"))));
                    h.tvSymm.setText(map.get("symm"));
                    h.tvPriceCts.setText(String.format("%,.2f", Double.parseDouble(map.get("price_per_cts"))));
                    h.tvMsrmnt.setText(map.get("length") + "x" + map.get("width") + "x" + map.get("depth"));
                    h.tvLab.setText(map.get("lab"));
                    h.tvDepthPer.setText(map.get("depth_per"));
                    h.tvTablePer.setText(map.get("table_per"));

                    if (map.get("location") != null && map.get("location") != "" && map.get("location").equalsIgnoreCase("Upcoming")) {
                        h.llMain.setBackgroundColor(Color.rgb(255, 255, 153));
                    } else if ("BUSS. PROCESS".equals(map.get("status"))) {
                        h.llMain.setBackgroundColor(Color.rgb(122, 255, 230));
                    } else if (map.get("soffer").equalsIgnoreCase("1")) {
                        h.llMain.setBackgroundColor(Color.rgb(204, 255, 255));
                    } else if (map.get("location") != null && map.get("Location") != "" && map.get("location").equalsIgnoreCase("SHOW")) {
                        h.llMain.setBackgroundColor(Color.rgb(224, 219, 214));
                    } else if ("Y".equalsIgnoreCase(map.get("new_arrival"))) {
                        h.llMain.setBackgroundColor(Color.rgb(255, 194, 194));
                    } else if ("Y".equalsIgnoreCase(map.get("promotion"))) {
                        h.llMain.setBackgroundColor(Color.rgb(216, 255, 68));
                    }

//                    if (Const.isOdd(position)) {
//                        h.llMain.setBackgroundColor(getResources().getColor(R.color.white));
//                    } else {
//                        h.llMain.setBackgroundColor(getResources().getColor(R.color.white));
//                    }

                    if (hmSelected.containsKey(position)) {
                        h.cbBox.setChecked(true);
                        h.llMain.setBackgroundColor(Color.rgb(135, 206, 250));
                    } else {
                        h.cbBox.setChecked(false);
                        h.llMain.setBackgroundDrawable(getResources().getDrawable(R.color.white));
                    }

                    h.cbBox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (hmSelected.containsKey(position)) {
                                hmSelected.remove(position);
                                cbSelectAll.setChecked(false);

                                tvTotalPcs.setText(hmSelected.size() + "");
                                Double cts = Double.parseDouble(tvTotalCts.getText().toString().replace(",","")) - Double.parseDouble(map.get("cts"));
                                tvTotalCts.setText(String.format("%,.2f", cts));

                                Double amt = Double.parseDouble(tvTotalAmt.getText().toString().replace("$","").replace(",","").split(" ")[1]) - Double.parseDouble(map.get("net_amount"));
                                tvTotalAmt.setText("$ " + String.format("%,.0f", amt));

                                rap = rap - Double.parseDouble(map.get("rap_amount"));
                                Double avgDisc = (100 - ((Double.parseDouble(tvTotalAmt.getText().toString().replace("$","").replace(",","").split(" ")[1]) * 100) / rap)) * -1;
                                tvTotalAvgDiscPer.setText(avgDisc.isNaN() ? "0" : String.format("%.2f", avgDisc) + " %");
                                if (hmSelected.size() == 0) {
                                    clearTotal();
                                }
                                if (map.get("location") != null && map.get("location") != "" && map.get("location").equalsIgnoreCase("Upcoming")){
                                    h.llMain.setBackgroundColor(Color.rgb(255, 255, 153));
                                } else if ("BUSS. PROCESS".equals(map.get("status"))) {
                                    h.llMain.setBackgroundColor(Color.rgb(122, 255, 230));
                                } else if (map.get("soffer").equalsIgnoreCase("1")) {
                                    h.llMain.setBackgroundColor(Color.rgb(204, 255, 255));
                                } else if (map.get("location") != null && map.get("Location") != "" && map.get("location").equalsIgnoreCase("SHOW")) {
                                    h.llMain.setBackgroundColor(Color.rgb(224, 219, 214));
                                } else if ("Y".equalsIgnoreCase(map.get("new_arrival"))) {
                                    h.llMain.setBackgroundColor(Color.rgb(255, 194, 194));
                                } else if ("Y".equalsIgnoreCase(map.get("promotion"))) {
                                    h.llMain.setBackgroundColor(Color.rgb(216, 255, 68));
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
                                Double cts = Double.parseDouble(tvTotalCts.getText().toString().replace(",","")) + Double.parseDouble(map.get("cts"));
                                tvTotalCts.setText(String.format("%,.2f", cts));

                                Double amt = Double.parseDouble(tvTotalAmt.getText().toString().replace("$","").replace(",","").split(" ")[1]) + Double.parseDouble(map.get("net_amount"));
                                tvTotalAmt.setText("$ " + String.format("%,.0f", amt));

                                rap = rap + Double.parseDouble(map.get("rap_amount"));
                                Double avgDisc = (100 - ((Double.parseDouble(tvTotalAmt.getText().toString().replace("$","").replace(",","").split(" ")[1]) * 100) / rap)) * -1;
                                tvTotalAvgDiscPer.setText(avgDisc.isNaN() ? "0" : String.format("%.2f", avgDisc) + " %");

                                h.llMain.setBackgroundColor(Color.rgb(135, 206, 250));
                            }
                            Double ppc = Double.parseDouble(tvTotalAmt.getText().toString().replace("$","").replace(",","").split(" ")[1]) / Double.parseDouble(tvTotalCts.getText().toString().replace(",",""));
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
            TextView tvStatus, tvStoneID, tvShape, tvColor, tvClarity, tvCts, tvCut, tvPolish, tvSymm, tvFls, tvLab, tvMsrmnt, tvDepthPer, tvTablePer, tvRapPrice, tvDiscPer, tvPriceCts, tvTotalAmt;
            LinearLayout llMain;

            private ListViewHolder(@NonNull View v) {
                super(v);
                llMain = v.findViewById(R.id.llMain);
                cbBox = v.findViewById(R.id.cbBox);
                tvStatus = v.findViewById(R.id.tvStatus);
                tvStoneID = v.findViewById(R.id.tvStoneID);
                tvShape = v.findViewById(R.id.tvShape);
                tvLab = v.findViewById(R.id.tvLab);
                tvCts = v.findViewById(R.id.tvCts);
                tvTotalAmt = v.findViewById(R.id.tvTotalAmt);
                tvPolish = v.findViewById(R.id.tvPolish);
                tvSymm = v.findViewById(R.id.tvSymm);
                tvColor = v.findViewById(R.id.tvColor);
                tvPriceCts = v.findViewById(R.id.tvPriceCts);
                tvRapPrice = v.findViewById(R.id.tvRapPrice);
                tvClarity = v.findViewById(R.id.tvClarity);
                tvTablePer = v.findViewById(R.id.tvTablePer);
                tvDepthPer = v.findViewById(R.id.tvDepthPer);
                tvDiscPer = v.findViewById(R.id.tvDiscPer);
                tvFls = v.findViewById(R.id.tvFls);
                tvCut = v.findViewById(R.id.tvCut);
                tvMsrmnt = v.findViewById(R.id.tvMsrmnt);
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
                    if (adp != null) {
                        adp.notifyDataSetChanged();
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
                    onBackPressed();
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
                case R.id.llShare:
                    if (hmSelected.size() > 5) {
                        Const.showErrorDialog(context, "You can select maximum 5 stones to share information!");
                    } else if (hmSelected.size() > 0) {
                        shareDialog();
                    } else {
                        Const.showErrorDialog(context, "Please select at least 1 stone");
                    }
                    break;
                case R.id.llWish:
                    if (hmSelected.size() > 0) {
                        addTowishList();
                    } else {
                        Const.showErrorDialog(context, "Please select at least 1 stone");
                    }
                    break;
                case R.id.llDownload:
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
                case R.id.llCompare:
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
                    if (llViewCount.getVisibility() == View.VISIBLE) {
                        imgViewCount.setImageDrawable(getResources().getDrawable(R.drawable.ic_down));
                        collapse(llViewCount);
                    } else {
                        imgViewCount.setImageDrawable(getResources().getDrawable(R.drawable.ic_up));
                        expand(llViewCount);
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


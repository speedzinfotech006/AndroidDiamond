package com.dnk.shairugems;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dnk.shairugems.Utils.Const;
import com.dnk.shairugems.Utils.Pref;
import com.dnk.shairugems.Volly.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ConfirmOrderActivity extends BaseDrawerActivity {

    ImageView imgMenu, imgSearch, imgDown, imgSave;
    LinearLayout llSearchTop,llSearch, llClear;
    RecyclerView rvList;
    LinearLayoutManager lm;
    LinearLayout llHorizontal;
    EditText etFromDate, etToDate, etSearchStock, etUserSearch;
    HashMap<Integer, String> hmSelected = new HashMap<>();
    ArrayList<HashMap<String, String>> maps = new ArrayList<>();
    ResultAdp adp;
    int pastVisiblesItems, visibleItemCount, totalItemCount, pageCount = 1, totalSize = 0;
    boolean isChange = true;
    RelativeLayout toolBar;
    CheckBox cbSelectAll;
    Context context = ConfirmOrderActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_confirm_order, frameLayout);

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
                                getConfirmOrderList();
                            }
                        }
                    }
                }
            }
        });

        cbSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hmSelected = new HashMap<>();
                if (cbSelectAll.isChecked()) {
                    if (adp != null) {
                        for (int i = 0; i < maps.size(); i++) {
                            hmSelected.put(i, maps.get(i).get("iorderdetid"));
                        }
                        adp.notifyDataSetChanged();
                    }
                } else {
                    hmSelected = new HashMap<>();
                    if (adp != null) {
                        adp.notifyDataSetChanged();
                    }
                }
            }
        });


        Pref.removeValue(context, Const.smart_search);
        Pref.removeValue(context, Const.comp_name);
        getConfirmOrderList();
    }

    private void initView() {
        imgMenu = findViewById(R.id.imgMenu);
        imgSearch = findViewById(R.id.imgSearch);
        etFromDate = findViewById(R.id.etFromDate);
        etToDate = findViewById(R.id.etToDate);
        etSearchStock = findViewById(R.id.etSearchStock);
        etUserSearch = findViewById(R.id.etUserSearch);
        llSearch = findViewById(R.id.llSearch);
        llClear = findViewById(R.id.llClear);
        cbSelectAll = findViewById(R.id.cbSelectAll);
        rvList = findViewById(R.id.rvList);
        toolBar = findViewById(R.id.toolBar);
        imgSave = findViewById(R.id.imgSave);
        imgDown = findViewById(R.id.imgDown);

        llHorizontal = findViewById(R.id.llHorizontal);
        llSearchTop = findViewById(R.id.llSearchTop);

        lm = new LinearLayoutManager(context);
        rvList.setLayoutManager(lm);

        imgMenu.setOnClickListener(clickListener);
        imgSave.setOnClickListener(clickListener);
        imgDown.setOnClickListener(clickListener);
        llClear.setOnClickListener(clickListener);
        llSearch.setOnClickListener(clickListener);
        etFromDate.setOnClickListener(clickListener);
        etToDate.setOnClickListener(clickListener);

        etFromDate.setText(new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date()));
        etToDate.setText(new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date()));

        Pref.setStringValue(context, Const.from_date, etFromDate.getText().toString());
        Pref.setStringValue(context, Const.to_date, etToDate.getText().toString());
    }

    private void getConfirmOrderList() {
        Const.showProgress(context);
        final Map<String, String> map = new HashMap<>();
        map.put("FromDate", Pref.getStringValue(context, Const.from_date, ""));
        map.put("ToDate", Pref.getStringValue(context, Const.to_date, ""));
        map.put("RefNo", Pref.getStringValue(context, Const.smart_search, ""));
        map.put("PageSize", "50");
        map.put("PageNo", pageCount + "");
        map.put("CompanyName", Pref.getStringValue(context, Const.comp_name, ""));
        map.put("OrderBy", "");
        Const.callPostApi(context, "/Order/GetConfirmOrderList", map, new VolleyCallback() {
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
                                    totalSize = Integer.parseInt(hm.get("itotalrec"));
                                }
                            }
                            if (pageCount == 1) {
                                adp = new ResultAdp(context, maps, isChange);
                                rvList.setAdapter(adp);
                            }
                            llHorizontal.setVisibility(View.VISIBLE);
                            adp.notifyDataSetChanged();
                        }
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
                        getConfirmOrderList();
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    private void SaveOrder() {
        Const.showProgress(context);
        List<String> list = new ArrayList<>(hmSelected.values());
        final Map<String, String> map = new HashMap<>();
        map.put("OrderId", list.toString().replace("[", "").replace("]", "").replace(", ", ","));
        map.put("bIsExcludeStk", "False");
        Const.callPostApi(context, "/Order/ExcludeStoneFromStockInsert", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Const.dismissProgress();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.optString("Status").equalsIgnoreCase("1")) {
                        String ob = new JSONObject(result).optString("Message");
                        hmSelected = new HashMap<>();
                        adp.notifyDataSetChanged();
                        Const.showErrorDialog(context, Const.notNullString("" + ob, "Order Update successfully"));
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
                        SaveOrder();
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

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
            View view = LayoutInflater.from(context).inflate(R.layout.layout_confirm_order, parent, false);
            return new ResultAdp.GridViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            final HashMap<String, String> map = arraylist.get(position);

            if (holder instanceof ResultAdp.GridViewHolder) {
                final ResultAdp.GridViewHolder h = (ResultAdp.GridViewHolder) holder;

                h.tvSrNo.setText(Const.notNullString(map.get("isr") + ".", ""));
                h.tvDate.setText(Const.notNullString(map.get("dtorderdate1"), ""));
                h.tvOrderNo.setText(Const.notNullString(map.get("iorderid"), ""));
                h.tvCompName.setText(Const.notNullString(map.get("scompname"), ""));
                h.tvUserName.setText(Const.notNullString(map.get("susername"), ""));
                h.tvStockID.setText(Const.notNullString(map.get("srefno"), ""));

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
                            h.llMain.setBackgroundDrawable(getResources().getDrawable(R.color.white));
                        } else {
                            hmSelected.put(position, map.get("iorderdetid"));
                            h.llMain.setBackgroundColor(Color.rgb(135, 206, 250));
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

            TextView tvSrNo, tvDate, tvOrderNo, tvCompName, tvUserName, tvStockID;
            CheckBox cbBox;
            LinearLayout llMain;

            private GridViewHolder(@NonNull View v) {
                super(v);
                tvSrNo = v.findViewById(R.id.tvSrNo);
                tvDate = v.findViewById(R.id.tvDate);
                tvOrderNo = v.findViewById(R.id.tvOrderNo);
                tvCompName = v.findViewById(R.id.tvCompName);
                tvUserName = v.findViewById(R.id.tvUserName);
                tvStockID = v.findViewById(R.id.tvStockID);

                cbBox = v.findViewById(R.id.cbBox);

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
                case R.id.etFromDate:
                    openFromDatePickerDialog();
                    break;
                case R.id.etToDate:
                    openToDatePickerDialog();
                    break;
                case R.id.imgSave:
                    if (hmSelected.size() >= 1) {
                        SaveOrder();
                    } else {
                        Const.showErrorDialog(context, "Please select at least one stone");
                    }
                    break;
                case R.id.llSearch:
                    Pref.setStringValue(context, Const.smart_search, etSearchStock.getText().toString());
                    Pref.setStringValue(context, Const.comp_name, etUserSearch.getText().toString().trim());
                    maps = new ArrayList<>();
                    pageCount = 1;
                    totalSize = 0;
                    Const.hideSoftKeyboard(ConfirmOrderActivity.this);
                    getConfirmOrderList();
                    break;
                case R.id.llClear:
                    etSearchStock.setText("");
                    etUserSearch.setText("");
                    Pref.removeValue(context, Const.smart_search);
                    Pref.removeValue(context, Const.comp_name);
                    maps = new ArrayList<>();
                    pageCount = 1;
                    totalSize = 0;
                    Const.hideSoftKeyboard(ConfirmOrderActivity.this);
                    getConfirmOrderList();
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

    public Boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) ConfirmOrderActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

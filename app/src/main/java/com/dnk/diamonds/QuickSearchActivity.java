package com.dnk.shairugems;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dnk.shairugems.Utils.Const;
import com.dnk.shairugems.Volly.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class QuickSearchActivity extends BaseDrawerActivity {

    ImageView imgMenu, imgDown;
    RecyclerView rlMain,rlSub;
    QuickColorAdp adp1;
    LinearLayout llSearchTop;
    Dialog dialogCut;
    Dialog dialogFLS;
    String Cut = "", FLS = "";
    ListView cut_list;
    ListView fls_list;
    CheckBox all;
    private String lstcut, lstFLS;
    HorizontalScrollView hsvPointer,horiScrollQuick;
    private ArrayList<HashMap<String, String>> arrayList;
    private ArrayList<HashMap<String, String>> SubarrayList;
   // ArrayList<String> Heads;
    private ArrayList<String> Heads = new ArrayList<String>();
    private ArrayList<String> ColorList = new ArrayList<String>();
    private ArrayList<String> PurityList = new ArrayList<String>();
    private ArrayList<String> PointerList = new ArrayList<String>();
    private ArrayList<String> pointer_list = new ArrayList<String>();
    List<String> arrayCut = new ArrayList<>();
    List<String> arrayFLS = new ArrayList<>();
    EditText etCut,etFLS;
    private ArrayList<ArrayList<String>> row_list = new ArrayList<ArrayList<String>>();
    LinearLayout llPoiter;
    LinearLayout llClear, llSearch;
    String values[];
    Context context = QuickSearchActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_quick_search, frameLayout);

        initView();

        getQuickSearch();
        getListValue();

        etCut.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etCut.performClick();
                }
            }
        });

        etFLS.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etFLS.performClick();
                }
            }
        });

        RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (recyclerView == rlSub) {
                    rlMain.removeOnScrollListener(this);
                    rlMain.scrollBy(0, dy);
                    rlMain.addOnScrollListener(this);
                } else if (recyclerView == rlMain) {
                    rlSub.removeOnScrollListener(this);
                    rlSub.scrollBy(0, dy);
                    rlSub.addOnScrollListener(this);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            horiScrollQuick.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                    hsvPointer.scrollTo(horiScrollQuick.getScrollX(), 0);
                }
            });

            hsvPointer.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                    horiScrollQuick.scrollTo(hsvPointer.getScrollX(), 0);
                }
            });

            rlMain.addOnScrollListener(scrollListener);
            rlSub.addOnScrollListener(scrollListener);
    }

        PointerList.add("018_022");
        PointerList.add("023_029");
        PointerList.add("030_039");
        PointerList.add("040_049");
        PointerList.add("050_059");
        PointerList.add("060_069");
        PointerList.add("070_079");
        PointerList.add("080_089");
        PointerList.add("090_099");
        PointerList.add("100_119");
        PointerList.add("120_149");
        PointerList.add("150_199");
        PointerList.add("200_299");
        PointerList.add("300_9999");

        pointer_list.add("0.18-0.22");
        pointer_list.add("0.23-0.29");
        pointer_list.add("0.30-0.39");
        pointer_list.add("0.40-0.49");
        pointer_list.add("0.50-0.59");
        pointer_list.add("0.60-0.69");
        pointer_list.add("0.70-0.79");
        pointer_list.add("0.80-0.89");
        pointer_list.add("0.90-0.99");
        pointer_list.add("1.00-1.19");
        pointer_list.add("1.20-1.49");
        pointer_list.add("1.50-1.99");
        pointer_list.add("2.00-2.99");
        pointer_list.add("3.00-99.99");

//        Heads.add("fl");
//        Heads.add("if");
//        Heads.add("vvs1");
//        Heads.add("vvs2");
//        Heads.add("total");

    }

    private void getQuickSearch() {
        Const.showProgress(context);
        arrayList = new ArrayList<>();
        row_list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("Cut", Cut);
        map.put("Fls", FLS);
        map.put("FormName", "Quick Search");
        map.put("ActivityType", "Get");
        Const.callPostApi(context, "Stock/GetQuickSearch", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Const.dismissProgress();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.optString("Status").equalsIgnoreCase("1")) {
                        if (new JSONObject(result).optJSONArray("Data").optJSONObject(0).optJSONObject("DataList") != null) {
                            startActivity(new Intent(context, NewLoginActivity.class));
                        } else {
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
                                    arrayList.add(hm);
                                }
                                for (int i = 0; i < arrayList.size(); i++) {
                                    if (!ColorList.contains(arrayList.get(i).get("color"))) {
                                        ColorList.add(arrayList.get(i).get("color"));
                                    }
                                }

                                for (int i = 0; i < arrayList.size(); i++) {
                                    PurityList.add(arrayList.get(i).get("purity"));
                                }

                                if (pointer_list.size() != 0) {
                                    for (int i = 0; i < pointer_list.size(); i++) {
                                        TextView tv = new TextView(context);
                                        int weight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
                                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(weight, (int) LinearLayout.LayoutParams.MATCH_PARENT);
                                        tv.setLayoutParams(params);
                                        tv.setGravity(Gravity.CENTER);
                                        tv.setTextColor(Color.WHITE);
                                        tv.setBackgroundResource(R.drawable.quick_text_clarity);
                                        String[] spilir = pointer_list.get(i).split("-");
                                        String tesxtx = spilir[0] + "\n-\n" + spilir[1];
                                        tv.setText(tesxtx);
                                        llPoiter.addView(tv);
                                    }
                                }

                                if (arrayList.size() != 0) {
                                    for (int i = 0; i < arrayList.size(); i++) {
                                        ArrayList<String> plist = new ArrayList<String>();
                                        HashMap<String, String> hm = arrayList.get(i);
                                        for (int j = 0; j < PointerList.size(); j++) {
                                            if (hm.containsKey("p_" + PointerList.get(j))) {
                                                plist.add(hm.get("p_" + PointerList.get(j)));
                                            } else {
                                                plist.add("0");
                                            }
                                        }
                                        row_list.add(plist);
                                    }
                                    if (row_list.size() > 0) {
                                        ResultAdapter rowadapter = new ResultAdapter(context, row_list, arrayList);
                                        rlSub.setAdapter(rowadapter);
                                    }
                                }

                                if (ColorList.size() != 0) {
                                    adp1 = new QuickColorAdp(context, ColorList, PurityList);
                                    rlMain.setAdapter(adp1);
                                }
//                                adp = new QuickSearchAdp(context, arrayList);
//                                rlSub.setAdapter(adp);
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
                        getQuickSearch();
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    private void GetSubQuickSearch(final String Pointer,final String ColorGroup, final String PurityGroup) {
        Const.showProgress(context);
        SubarrayList = new ArrayList<>();
        Heads = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("Cut", Cut);
        map.put("Fls", FLS);
        map.put("Pointer", Pointer);
        map.put("ColorGroup", ColorGroup);
        map.put("PurityGroup", PurityGroup);
        Const.callPostApi(context, "Stock/GetSubQuickSearch", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Const.dismissProgress();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.optString("Status").equalsIgnoreCase("1")) {
                        if (new JSONObject(result).optJSONArray("Data").optJSONObject(0).optJSONObject("DataList") != null) {
                            startActivity(new Intent(context, NewLoginActivity.class));
                        } else {
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
                                    SubarrayList.add(hm);
                                    if (!Heads.contains(("total"))) {
                                        if (hm.get("is_header").equalsIgnoreCase("1")) {
                                            if (hm.get("is_fl").equalsIgnoreCase("1")) {
                                                Heads.add("fl");
                                            }
                                            if (hm.get("is_if").equalsIgnoreCase("1")) {
                                                Heads.add("if");
                                            }
                                            if (hm.get("is_vvs1").equalsIgnoreCase("1")) {
                                                Heads.add("vvs1");
                                            }
                                            if (hm.get("is_vvs2").equalsIgnoreCase("1")) {
                                                Heads.add("vvs2");
                                            }
                                            if (hm.get("is_vs1").equalsIgnoreCase("1")) {
                                                Heads.add("vs1");
                                            }
                                            if (hm.get("is_vs2").equalsIgnoreCase("1")) {
                                                Heads.add("vs2");
                                            }
                                            if (hm.get("is_si1").equalsIgnoreCase("1")) {
                                                Heads.add("si1");
                                            }
                                            if (hm.get("is_si2").equalsIgnoreCase("1")) {
                                                Heads.add("si2");
                                            }
                                            if (hm.get("is_i1").equalsIgnoreCase("1")) {
                                                Heads.add("i1");
                                            }
                                            if (hm.get("is_i2").equalsIgnoreCase("1")) {
                                                Heads.add("i2");
                                            }
                                            if (hm.get("is_i3").equalsIgnoreCase("1")) {
                                                Heads.add("i3");
                                            }
                                            Heads.add("total");
                                        }
                                    }

                                }
                            }else{
                                Const.showErrorDialog(context, "No Result Found");
                            }
                        }
                        showSubQuickSearch(Heads, SubarrayList, values[2]);
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
                        GetSubQuickSearch(Pointer,ColorGroup,PurityGroup);
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    private void getListValue() {
        Const.showProgress(context);
        arrayCut = new ArrayList<>();
        arrayFLS = new ArrayList<>();
        final Map<String, String> map = new HashMap<>();
        map.put("ListValue", "DP");
        Const.callPostApi(context, "Stock/GetListValue", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Const.dismissProgress();
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
                                if(hm.get("listtype").equalsIgnoreCase("CUT")){
                                    arrayCut.add(hm.get("value"));
                                }
                                if(hm.get("listtype").equalsIgnoreCase("FLS")){
                                    arrayFLS.add(hm.get("value"));
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
                        getListValue();
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    private void openCutDialog() {
        dialogCut = new Dialog(context);
        dialogCut.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogCut.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogCut.setContentView(R.layout.layout_dialog);
        final TextView tvTitle = dialogCut.findViewById(R.id.tvTitle);
        cut_list = dialogCut.findViewById(R.id.pointer_list);
        all = dialogCut.findViewById(R.id.cbAll);
        final Button btnBack = dialogCut.findViewById(R.id.btnBack);
        final Button btnOk = dialogCut.findViewById(R.id.btnOk);

        tvTitle.setText("Select Cut");

        if (arrayCut != null) {
            cut_list.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.layout_checkedtextview, arrayCut));
        }

        if (lstcut != null) {
            String[] lst = lstcut.split(",");

            for (int j = 0; j < cut_list.getCount(); j++) {
                for (int i = 0; i < lst.length; i++) {
                    if (lst[i].contains(cut_list.getItemAtPosition(j).toString())) {
                        cut_list.setItemChecked(j, true);
                    }
                }
            }
            if (cut_list.getCount() == lst.length) {
                all.setChecked(true);
            }

        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCut.dismiss();
                final ArrayList<String> selectedCut = new ArrayList<String>();
                Cut = "";
                for (int i = 0; i < cut_list.getCount(); i++) {
                    if (cut_list.isItemChecked(i)) {
                        selectedCut.add(cut_list.getItemAtPosition(i).toString());
                    }
                }
                if (selectedCut.size() != 0) {
                    Cut = selectedCut.toString().replace("[", "").replace("]", "").replaceAll(" ","").trim();
                    etCut.setText(selectedCut.size() + " - Selected ");
                } else {
                    etCut.setText(null);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCut.dismiss();
            }
        });


        cut_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                if (cut_list.isItemChecked(position)) {
                    if (cut_list.getCheckedItemCount() == arrayCut.size()) {
                        all.setChecked(true);
                    }
                } else {
                    all.setChecked(false);
                }
            }
        });

        all.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Boolean is = all.isChecked();
                if (is) {
                    for (int i = 0; i < cut_list.getCount(); i++) {
                        cut_list.setItemChecked(i, true);
                        all.setChecked(true);
                    }
                } else {

                    for (int i = 0; i < cut_list.getCount(); i++) {
                        cut_list.setItemChecked(i, false);
                        all.setChecked(false);
                    }
                }
            }
        });
    }

    private void openFLSDialog() {
        dialogFLS = new Dialog(context);
        dialogFLS.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogFLS.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogFLS.setContentView(R.layout.layout_dialog);
        final TextView tvTitle = dialogFLS.findViewById(R.id.tvTitle);
        fls_list = dialogFLS.findViewById(R.id.pointer_list);
        all = dialogFLS.findViewById(R.id.cbAll);
        final Button btnBack = dialogFLS.findViewById(R.id.btnBack);
        final Button btnOk = dialogFLS.findViewById(R.id.btnOk);

        tvTitle.setText("Select FLS");

        if (arrayFLS != null) {
            fls_list.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.layout_checkedtextview, arrayFLS));
        }

        if (lstFLS != null) {
            String[] lst = lstFLS.split(",");

            for (int j = 0; j < fls_list.getCount(); j++) {
                for (int i = 0; i < lst.length; i++) {
                    if (lst[i].contains(fls_list.getItemAtPosition(j).toString())) {
                        fls_list.setItemChecked(j, true);
                    }
                }
            }
            if (fls_list.getCount() == lst.length) {
                all.setChecked(true);
            }

        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFLS.dismiss();
                final ArrayList<String> selectedFLS = new ArrayList<String>();
                FLS = "";
                for (int i = 0; i < fls_list.getCount(); i++) {
                    if (fls_list.isItemChecked(i)) {
                        selectedFLS.add(fls_list.getItemAtPosition(i).toString());
                    }
                }
                if (selectedFLS.size() != 0) {
                    FLS = selectedFLS.toString().replace("[", "").replace("]", "").replaceAll(" ","").trim();
                    etFLS.setText(selectedFLS.size() + " - Selected ");
                } else {
                    etFLS.setText(null);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFLS.dismiss();
            }
        });


        fls_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                if (fls_list.isItemChecked(position)) {
                    if (fls_list.getCheckedItemCount() == arrayFLS.size()) {
                        all.setChecked(true);
                    }
                } else {
                    all.setChecked(false);
                }
            }
        });

        all.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Boolean is = all.isChecked();
                if (is) {
                    for (int i = 0; i < fls_list.getCount(); i++) {
                        fls_list.setItemChecked(i, true);
                        all.setChecked(true);
                    }
                } else {

                    for (int i = 0; i < fls_list.getCount(); i++) {
                        fls_list.setItemChecked(i, false);
                        all.setChecked(false);
                    }
                }
            }
        });
    }

    private void initView() {
        imgMenu = findViewById(R.id.imgMenu);
        rlMain = findViewById(R.id.rlMain);
        rlSub = findViewById(R.id.rlSub);
        llPoiter = findViewById(R.id.llPoiter);
        hsvPointer = findViewById(R.id.hsvPointer);
        horiScrollQuick = findViewById(R.id.horiScrollQuick);
        llSearchTop = findViewById(R.id.llSearchTop);
        imgDown = findViewById(R.id.imgDown);

        etCut = findViewById(R.id.etCut);
        etFLS = findViewById(R.id.etFLS);
        llSearch = findViewById(R.id.llSearch);
        llClear = findViewById(R.id.llClear);

        rlMain.setLayoutManager(new LinearLayoutManager(context));
        rlSub.setLayoutManager(new LinearLayoutManager(context));

        imgMenu.setOnClickListener(clickListener);
        imgDown.setOnClickListener(clickListener);
        etCut.setOnClickListener(clickListener);
        etFLS.setOnClickListener(clickListener);

        llSearch.setOnClickListener(clickListener);
        llClear.setOnClickListener(clickListener);
    }

    private class QuickColorAdp extends RecyclerView.Adapter<QuickColorAdp.MyViewHolder> {

        private ArrayList<String> colorclarity, purity;
        private Context context;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            LinearLayout llAddClarity, llColor;

            public MyViewHolder(View v) {
                super(v);
                llAddClarity = (LinearLayout) v.findViewById(R.id.llAddClarity);
                llColor = (LinearLayout) v.findViewById(R.id.llColor);
            }
        }

        public QuickColorAdp(Context context, ArrayList<String> colorclarity, ArrayList<String> purity) {
            this.colorclarity = colorclarity;
            this.purity = purity;
            this.context = context;
        }

        @Override
        public QuickColorAdp.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_quick_color_clarity, null);
            return new QuickColorAdp.MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(QuickColorAdp.MyViewHolder holder, final int position) {

            if (holder.llColor.getChildCount() > 0) {
                holder.llColor.removeAllViews();
            }

            TextView tvColor = new TextView(QuickSearchActivity.this);
            LinearLayout.LayoutParams paramss = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            tvColor.setLayoutParams(paramss);
            tvColor.setGravity(Gravity.CENTER);
            tvColor.setTextColor(Color.WHITE);
            tvColor.setBackgroundResource(R.drawable.quick_text_color);
            tvColor.setText(colorclarity.get(position));
            holder.llColor.addView(tvColor);


            if (holder.llAddClarity.getChildCount() > 0) {
                holder.llAddClarity.removeAllViews();
            }

            for (int i = 0; i < purity.size(); i++) {
                TextView tvClarity = new TextView(QuickSearchActivity.this);
                int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
                tvClarity.setLayoutParams(params);
                tvClarity.setGravity(Gravity.CENTER);
                tvClarity.setTextColor(Color.WHITE);
                tvClarity.setBackgroundResource(R.drawable.quick_text_clarity);
                tvClarity.setText(purity.get(i));
                holder.llAddClarity.addView(tvClarity);
            }

        }

        @Override
        public int getItemCount() {
            return colorclarity.size();
        }
    }

    private class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.MyViewHolder> {

        private ArrayList<ArrayList<String>> list;
        private ArrayList<HashMap<String, String>> hasmap;
        private Context context;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView srno;
            LinearLayout lladdhori;

            public MyViewHolder(View v) {
                super(v);
                lladdhori = (LinearLayout) v.findViewById(R.id.lladdhori);
            }
        }

        public ResultAdapter(Context context, ArrayList<ArrayList<String>> moviesList, ArrayList<HashMap<String, String>> hashMaps) {
            this.list = moviesList;
            this.hasmap = hashMaps;
            this.context = context;
        }

        @Override
        public ResultAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_quick_result, null);
            return new ResultAdapter.MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ResultAdapter.MyViewHolder holder, final int position) {

            if (holder.lladdhori.getChildCount() > 0) {
                holder.lladdhori.removeAllViews();
            }

            for (int i = 0; i < list.get(position).size(); i++) {

                final TextView tv = new TextView(QuickSearchActivity.this);
                int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
                int weight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(weight, height);
                tv.setLayoutParams(params);
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(Color.BLACK);
                tv.setBackgroundResource(R.drawable.quick_text_corner);
                tv.setText(list.get(position).get(i).equalsIgnoreCase("0") ? "-" : list.get(position).get(i));
                tv.setTag(hasmap.get(position).get("col_grp_sr") + "," + hasmap.get(position).get("purity_grp_sr") + "," + pointer_list.get(i));
                if (!tv.getText().toString().equalsIgnoreCase("-")) {
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            values = tv.getTag().toString().split(",");
                            GetSubQuickSearch(values[2], values[0], values[1]);
                        }
                    });
                }
                holder.lladdhori.addView(tv);
            }

        }

        @Override
        public int getItemCount() {
            return list.size();
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

    void showSubQuickSearch(ArrayList<String> headers, ArrayList<HashMap<String, String>> values, final String p) {

        final ArrayList<String> scp = new ArrayList<String>();
        final ArrayList<String> color = new ArrayList<String>();
        final ArrayList<String> clarity = new ArrayList<String>();
        LayoutInflater inflate = LayoutInflater.from(context);
        View view = inflate.inflate(R.layout.subquick_alert, null);
        LinearLayout li = (LinearLayout) view.findViewById(R.id.subquick_layout);
        TextView pointer = (TextView) view.findViewById(R.id.subquick_pointer);
        ImageView imgClearsign = (ImageView) view.findViewById(R.id.imgClearsign);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.rotate);
        imgClearsign.startAnimation(anim);
        li.setGravity(Gravity.CENTER);
        pointer.setText(p);
        TableLayout table = new TableLayout(context);
        table.setGravity(Gravity.CENTER);
        for (int i = 0; i < values.size(); i++) {
            final HashMap<String, String> hm = values.get(i);
            TableRow tr = new TableRow(context);
            TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
            tableParams.setMargins(0, 0, 0, 5);
            tr.setGravity(Gravity.CENTER);
            tr.setLayoutParams(tableParams);
            if (hm.get("is_header").equals("1")) {
                TextView head = new TextView(context);
                head.setText(hm.get("shape_color"));
                head.setTextColor(Color.rgb(0, 61, 102));
                head.setTypeface(Typeface.DEFAULT_BOLD);
                head.setTextSize(12);
                head.setAllCaps(true);
                head.setGravity(Gravity.CENTER);
                tr.addView(head);
                for (int k = 0; k < headers.size(); k++) {
                    TextView tv = new TextView(context);
                    tv.setGravity(Gravity.CENTER);
                    tv.setTextColor(Color.rgb(0, 61, 102));
                    tv.setTypeface(Typeface.DEFAULT_BOLD);
                    tv.setAllCaps(true);
                    tv.setText(headers.get(k));
                    tv.setTextSize(13);
                    tr.addView(tv);
                    ViewGroup.MarginLayoutParams para = (ViewGroup.MarginLayoutParams) tv.getLayoutParams();
                    para.setMargins(0, 0, 5, 0);
                }
            } else {
                TextView head = new TextView(context);
                head.setText(hm.get("shape_color"));
                head.setTextColor(Color.rgb(0, 61, 102));
                head.setTypeface(Typeface.DEFAULT_BOLD);
                head.setTextSize(12);
                head.setAllCaps(true);
                head.setGravity(Gravity.CENTER);
                tr.addView(head);
                for (int k = 0; k < headers.size(); k++) {
                    final String h = headers.get(k);
                    final String v = hm.get(h);
                    if (!h.equalsIgnoreCase("total") && !hm.get("shape_color").equals("TOTAL")) {

                        final ToggleButton tg = new ToggleButton(context);
                        tg.setChecked(false);
                        tg.setText(v);
                        tg.setTextOn(v);
                        tg.setTextOff(v);
                        tg.setTextSize(13);
                        tg.setBackgroundColor(Color.rgb(205, 201, 201));
                        if (!v.equals("0")) {
                            tg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                                    // TODO Auto-generated method stub
                                    if (arg1) {
                                        tg.setBackgroundColor(Color.rgb(0, 61, 102));
                                        tg.setTextColor(Color.rgb(255, 255, 255));
                                        if (hm.get("is_fancy").equals("0")) {
                                            scp.add("0:" + hm.get("shape_color") + ":" + h);
                                        } else {
                                            scp.add("1:" + hm.get("shape_color") + ":" + h);
                                        }
                                        color.add(hm.get("shape_color"));
                                        clarity.add(h);
                                    } else {
                                        tg.setBackgroundColor(Color.rgb(205, 201, 201));
                                        tg.setTextColor(Color.rgb(0, 0, 0));
                                        if (hm.get("is_fancy").equals("0")) {
                                            scp.remove("0:" + hm.get("shape_color") + ":" + h);
                                        } else {
                                            scp.remove("1:" + hm.get("shape_color") + ":" + h);
                                        }
                                        color.remove(hm.get("shape_color"));
                                        clarity.remove(h);
                                    }
                                }
                            });
                        }
                        tr.addView(tg);
                        ViewGroup.MarginLayoutParams para = (ViewGroup.MarginLayoutParams) tg.getLayoutParams();
                        para.setMargins(0, 0, 5, 0);
                    } else {
                        final TextView tv = new TextView(context);
                        tv.setTextSize(13);
                        tv.setGravity(Gravity.CENTER);
                        tv.setTextColor(Color.rgb(0, 61, 102));
                        tv.setTypeface(Typeface.DEFAULT_BOLD);
                        tv.setAllCaps(true);
                        tv.setText(v);
                        tr.addView(tv);
                        ViewGroup.MarginLayoutParams para = (ViewGroup.MarginLayoutParams) tv.getLayoutParams();
                        para.setMargins(0, 0, 5, 0);
                    }
                }
            }
            table.addView(tr);
        }
        li.addView(table);
        Button search = (Button) view.findViewById(R.id.subquick_search);
        Button b = (Button) view.findViewById(R.id.subquick_close);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog alert = builder.create();
        alert.getWindow().setLayout(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
        alert.setView(view);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                alert.dismiss();
            }
        });
        imgClearsign.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                alert.dismiss();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (!scp.isEmpty()) {
                    Const.flag_searchResult=2;
                    Intent i = new Intent(context, FinalQuickResultActivity.class);
                    i.putExtra("SCP", scp);
                    i.putExtra("pointer", p);
                    i.putExtra("Cut", Cut);
                    i.putExtra("FLS", FLS);
                    i.putExtra("color", color);
                    i.putExtra("clarity", clarity);
                    alert.dismiss();
                    startActivity(i);
                }
            }
        });
        alert.show();
        alert.setCancelable(false);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @SuppressLint("WrongConstant")
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.imgMenu:
                    drawerLayout.openDrawer(Gravity.START);
                    break;
                case R.id.etCut:
                    if (arrayCut.size() != 0) {
                        lstcut = etCut.getText().toString().trim();
                        openCutDialog();
                        dialogCut.show();
                    } else {
                        Const.showErrorDialog(context, "Cut Not Available");
                    }
                    break;
                case R.id.etFLS:
                    if (arrayFLS.size() != 0) {
                        lstFLS = etFLS.getText().toString().trim();
                        openFLSDialog();
                        dialogFLS.show();
                    } else {
                        Const.showErrorDialog(context, "FLS Not Available");
                    }
                    break;
                case R.id.llSearch:
                    getQuickSearch();
                    break;
                case R.id.llClear:
                     etCut.setText("");
                     etFLS.setText("");
                     Cut = "";
                     FLS = "";
                     getQuickSearch();
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
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

}

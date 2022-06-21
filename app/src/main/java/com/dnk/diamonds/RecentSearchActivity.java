package com.dnk.shairugems;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dnk.shairugems.Utils.Const;
import com.dnk.shairugems.Utils.Pref;
import com.dnk.shairugems.Volly.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RecentSearchActivity extends BaseDrawerActivity {

    ImageView imgMenu;
    RecyclerView rvList;
    RecentSearchAdp adp;
    List<HashMap<String, String>> arrayList = new ArrayList<>();
    Context context = RecentSearchActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.recent_search_activity, frameLayout);

        initView();

        getRecentSearch();
    }

    private void getRecentSearch() {
        Const.showProgress(context);
        arrayList = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        Const.callPostApi(context, "Stock/GetRecentSearch", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Const.dismissProgress();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.optString("Data") != null && !object.optString("Data").equalsIgnoreCase("[]")) {
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
                                adp = new RecentSearchAdp(context, arrayList);
                                rvList.setAdapter(adp);
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
                        getRecentSearch();
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    private void initView() {
        imgMenu = findViewById(R.id.imgMenu);
        rvList = findViewById(R.id.rvList);

        rvList.setLayoutManager(new LinearLayoutManager(context));

        arrayList=new ArrayList<HashMap<String, String>>();
        arrayList.clear();

//        arrayList.add(new HashMap<String, String>());
//        arrayList.add(new HashMap<String, String>());
//        arrayList.add(new HashMap<String, String>());

        adp = new RecentSearchAdp(context, arrayList);
        rvList.setAdapter(adp);
        adp.notifyDataSetChanged();

        imgMenu.setOnClickListener(clickListener);
    }

    private class RecentSearchAdp extends RecyclerView.Adapter<RecentSearchAdp.RecyclerViewHolder> {

        List<HashMap<String, String>> arraylist;
        Context context;
        Drawable drawableBack = Const.setBackgoundBorder(2, 15, getResources().getColor(R.color.gray), Color.TRANSPARENT);

        private RecentSearchAdp(Context context, List<HashMap<String, String>> arraylist) {
            this.arraylist = arraylist;
            this.context = context;
        }

        @Override
        public RecentSearchAdp.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recent_search, parent, false);
            RecentSearchAdp.RecyclerViewHolder viewHolder = new RecentSearchAdp.RecyclerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final RecentSearchAdp.RecyclerViewHolder holder, final int position) {

            final HashMap<String, String> map = arraylist.get(position);

//            if (Const.isOdd(position)) {
//                holder.rlClick.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_background_box));
//            } else {
//                holder.rlClick.setBackgroundColor(getResources().getColor(R.color.white));
//            }
            holder.rlClick.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_background_box));

            holder.tvDate.setText(map.get("dtransdate"));
            holder.tvTotalFound.setText(map.get("total_rec"));
            // holder.tvCriteriya.setText(Const.isNotNull(map.get("shape")) ? map.get("shape")+", " : Const.isNotNull(map.get("image")) ? map.get("image") : "");
            holder.tvCriteriya.setText(map.get("description"));

            holder.llSearch.setBackgroundDrawable(drawableBack);
            holder.llModify.setBackgroundDrawable(drawableBack);

            holder.llSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Pref.setStringValue(context, Const.stone_id, map.get("stone_ref_no") == "null" ? "" : map.get("stone_ref_no"));
                    Pref.setStringValue(context, Const.certi_no,map.get("certi_no") == "null" ? "" : map.get("certi_no"));
                    Pref.setStringValue(context, Const.shape, map.get("shape") == "null" ? "" : map.get("shape"));
                    Pref.setStringValue(context, Const.lab,  map.get("lab") == "null" ? "" : map.get("lab"));
                    Pref.setStringValue(context, Const.color,map.get("color") == "null" ? "" : map.get("color"));
                    Pref.setStringValue(context, Const.colortype,map.get("colortype") == "null" ? "" : map.get("colortype"));
                    Pref.setStringValue(context, Const.intencitycolor,map.get("intensity") == "null" ? "" : map.get("intensity"));
                    Pref.setStringValue(context, Const.overtonecolor,map.get("overtone") == "null" ? "" : map.get("overtone"));
                    Pref.setStringValue(context, Const.fancycolor,map.get("fancy_color") == "null" ? "" : map.get("fancy_color"));
                    Pref.setStringValue(context, Const.clarity, map.get("clarity") == "null" ? "" : map.get("clarity"));
                    Pref.setStringValue(context, Const.cut, map.get("cut") == "null" ? "" : map.get("cut"));
                    Pref.setStringValue(context, Const.polish, map.get("polish") == "null" ? "" : map.get("polish"));
                    Pref.setStringValue(context, Const.symmetry, map.get("symm") == "null" ? "" : map.get("symm"));
                    Pref.setStringValue(context, Const.fi, map.get("fls") == "null" ? "" : map.get("fls"));
                    Pref.setStringValue(context, Const.bgm, "");
                    Pref.setStringValue(context, Const.location, map.get("location") == "null" ? "" : map.get("location"));
                    Pref.setStringValue(context, Const.inclusion,  map.get("inclusion") == "null" ? "" : map.get("inclusion"));
                    Pref.setStringValue(context, Const.natts, map.get("natts") == "null" ? "" : map.get("natts"));
                    Pref.setStringValue(context, Const.crown_inclusion,  map.get("crown_inclusion") == "null" ? "" : map.get("crown_inclusion"));
                    Pref.setStringValue(context, Const.crown_natts, map.get("crown_natts") == "null" ? "" : map.get("crown_natts"));
                    Pref.setStringValue(context, Const.table_open, map.get("table_open") == "null" ? "" : map.get("table_open"));
                    Pref.setStringValue(context, Const.crown_open, map.get("crown_open") == "null" ? "" : map.get("crown_open"));
                    Pref.setStringValue(context, Const.pav_open, map.get("pav_open") == "null" ? "" : map.get("pav_open"));
                    Pref.setStringValue(context, Const.girdle_open, map.get("girdle_open") == "null" ? "" : map.get("girdle_open"));
                    Pref.setStringValue(context, Const.image,  map.get("image") == "null" ? "" : map.get("image"));
                    Pref.setStringValue(context, Const.video, map.get("movie") == "null" ? "" : map.get("movie"));
                    Pref.setStringValue(context, Const.pointer, map.get("pointer") == "null" ? "" : map.get("pointer"));
                    if(!map.get("from_disc").equalsIgnoreCase("0.0") && !map.get("to_disc").equalsIgnoreCase("0.0")){
                        Pref.setStringValue(context, Const.from_disc, map.get("from_disc"));
                        Pref.setStringValue(context, Const.to_disc, map.get("to_disc"));
                    }else{
                        Pref.setStringValue(context, Const.from_disc, "");
                        Pref.setStringValue(context, Const.to_disc, "");
                    }
                    if(!map.get("from_pricects").equalsIgnoreCase("0.0") && !map.get("to_pricects").equalsIgnoreCase("0.0")){
                        Pref.setStringValue(context, Const.from_pcu, map.get("from_pricects"));
                        Pref.setStringValue(context, Const.to_pcu, map.get("to_pricects"));
                    }else{
                        Pref.setStringValue(context, Const.from_pcu, "");
                        Pref.setStringValue(context, Const.to_pcu, "");
                    }
                    if(!map.get("from_netamt").equalsIgnoreCase("0.0") && !map.get("to_netamt").equalsIgnoreCase("0.0")){
                        Pref.setStringValue(context, Const.from_na, map.get("from_netamt"));
                        Pref.setStringValue(context, Const.to_na, map.get("to_netamt"));
                    }else{
                        Pref.setStringValue(context, Const.from_na, "");
                        Pref.setStringValue(context, Const.to_na, "");
                    }
                    if(!map.get("from_length").equalsIgnoreCase("0.0") && !map.get("to_length").equalsIgnoreCase("0.0")){
                        Pref.setStringValue(context, Const.from_length, map.get("from_length"));
                        Pref.setStringValue(context, Const.to_length, map.get("to_length"));
                    }else{
                        Pref.setStringValue(context, Const.from_length, "");
                        Pref.setStringValue(context, Const.to_length, "");
                    }
                    if(!map.get("from_width").equalsIgnoreCase("0.0") && !map.get("to_width").equalsIgnoreCase("0.0")){
                        Pref.setStringValue(context, Const.from_width, map.get("from_width"));
                        Pref.setStringValue(context, Const.to_width, map.get("to_width"));
                    }else{
                        Pref.setStringValue(context, Const.from_width, "");
                        Pref.setStringValue(context, Const.to_width, "");
                    }
                    if(!map.get("from_depth").equalsIgnoreCase("0.0") && !map.get("to_depth").equalsIgnoreCase("0.0")){
                        Pref.setStringValue(context, Const.from_depth, map.get("from_depth"));
                        Pref.setStringValue(context, Const.to_depth, map.get("to_depth"));
                    }else{
                        Pref.setStringValue(context, Const.from_depth, "");
                        Pref.setStringValue(context, Const.to_depth, "");
                    }
                    if(!map.get("from_depth_per").equalsIgnoreCase("0.0") && !map.get("to_depth_per").equalsIgnoreCase("0.0")){
                        Pref.setStringValue(context, Const.from_depth_p, map.get("from_depth_per"));
                        Pref.setStringValue(context, Const.to_depth_p, map.get("to_depth_per"));
                    }else{
                        Pref.setStringValue(context, Const.from_depth_p, "");
                        Pref.setStringValue(context, Const.to_depth_p, "");
                    }
                    if(!map.get("from_table_per").equalsIgnoreCase("0.0") && !map.get("to_table_per").equalsIgnoreCase("0.0")){
                        Pref.setStringValue(context, Const.from_table_p, map.get("from_table_per"));
                        Pref.setStringValue(context, Const.to_table_p, map.get("to_table_per"));
                    }else{
                        Pref.setStringValue(context, Const.from_table_p, "");
                        Pref.setStringValue(context, Const.to_table_p, "");
                    }
                    if(!map.get("from_cr_ang").equalsIgnoreCase("0.0") && !map.get("to_cr_ang").equalsIgnoreCase("0.0")){
                        Pref.setStringValue(context, Const.from_crang_p, map.get("from_cr_ang"));
                        Pref.setStringValue(context, Const.to_crang_p, map.get("to_cr_ang"));
                    }else{
                        Pref.setStringValue(context, Const.from_crang_p, "");
                        Pref.setStringValue(context, Const.to_crang_p, "");
                    }
                    if(!map.get("from_cr_ht").equalsIgnoreCase("0.0") && !map.get("to_cr_ht").equalsIgnoreCase("0.0")){
                        Pref.setStringValue(context, Const.from_crht_p, map.get("from_cr_ht"));
                        Pref.setStringValue(context, Const.to_crht_p, map.get("to_cr_ht"));
                    }else{
                        Pref.setStringValue(context, Const.from_crht_p, "");
                        Pref.setStringValue(context, Const.to_crht_p, "");
                    }
                    if(!map.get("from_pav_ang").equalsIgnoreCase("0.0") && !map.get("to_pav_ang").equalsIgnoreCase("0.0")){
                        Pref.setStringValue(context, Const.from_pavang_p, map.get("from_pav_ang"));
                        Pref.setStringValue(context, Const.to_pavang_p, map.get("to_pav_ang"));
                    }else{
                        Pref.setStringValue(context, Const.from_pavang_p, "");
                        Pref.setStringValue(context, Const.to_pavang_p, "");
                    }
                    if(!map.get("from_pav_ht").equalsIgnoreCase("0.0") && !map.get("to_pav_ht").equalsIgnoreCase("0.0")){
                        Pref.setStringValue(context, Const.from_pavht_p, map.get("from_pav_ht"));
                        Pref.setStringValue(context, Const.to_pavht_p, map.get("to_pav_ht"));
                    }else{
                        Pref.setStringValue(context, Const.from_pavht_p, "");
                        Pref.setStringValue(context, Const.to_pavht_p, "");
                    }
                    if(!map.get("from_cts").equalsIgnoreCase("0.0") && !map.get("to_cts").equalsIgnoreCase("0.0")){
                        Pref.setStringValue(context, Const.from_cts, map.get("from_cts"));
                        Pref.setStringValue(context, Const.to_cts, map.get("to_cts"));
                    }else{
                        Pref.setStringValue(context, Const.from_cts, "");
                        Pref.setStringValue(context, Const.to_cts, "");
                    }
                    Pref.setStringValue(context, Const.keytosymbol, map.get("skeytosymbol") == "null" ? "" : map.get("skeytosymbol"));
                    Const.flag_searchResult=3;
                    startActivity(new Intent(context, SearchResultActivity.class));
                    overridePendingTransition(0, 0);
                }
            });

            holder.llModify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(context, SearchFilterActivity.class));
                    overridePendingTransition(0, 0);
                }
            });

        }

        @Override
        public int getItemCount() {
            return arraylist.size();
        }

        private class RecyclerViewHolder extends RecyclerView.ViewHolder {
            RelativeLayout rlClick;
            TextView tvDate, tvTotalFound, tvCriteriya;
            LinearLayout llSearch, llModify;

            private RecyclerViewHolder(View v) {
                super(v);
                tvDate = v.findViewById(R.id.tvDate);
                tvTotalFound = v.findViewById(R.id.tvTotalFound);
                rlClick = v.findViewById(R.id.rlClick);
                llModify = v.findViewById(R.id.llModify);
                tvCriteriya = v.findViewById(R.id.tvCriteriya);
                llSearch = v.findViewById(R.id.llSearch);
            }
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @SuppressLint("WrongConstant")
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.imgMenu:
                    drawerLayout.openDrawer(Gravity.START);
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
package com.dnk.shairugems;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
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

import static com.dnk.shairugems.Utils.Const.setBackgoundBorder;

public class SaveSearchActivity extends BaseDrawerActivity {

    ImageView imgMenu;
    RecyclerView rvList;
    SaveSearchAdp adp;
    ArrayList<HashMap<String, String>> maps = new ArrayList<>();
    Context context = SaveSearchActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_save_search, frameLayout);

        initView();

        getSaveSearch();
    }

    private void getSaveSearch() {
        Const.showProgress(context);
        maps = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        Const.callPostApi(context, "Stock/GetUserSearch", map, new VolleyCallback() {
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
                                    maps.add(hm);
                                }
                                adp = new SaveSearchAdp(context, maps);
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
                        getSaveSearch();
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    private void DeleteSearch(final String SearchID) {
        Const.showProgress(context);
        Map<String, String> map = new HashMap<>();
        map.put("SearchID",  Const.notNullString(SearchID,""));
        Const.callPostApi(context, Const.BaseUrl + "Stock/UserSearchDelete", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Const.dismissProgress();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.optString("Status").equalsIgnoreCase("SUCCESS")) {
                        String ob = new JSONObject(result).optString("Message");
                        adp.notifyDataSetChanged();
                        Const.showErrorDialog(context, Const.notNullString(ob, "User Search delete successfully."));
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
                        DeleteSearch(SearchID);
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

        imgMenu.setOnClickListener(clickListener);
    }

    private class SaveSearchAdp extends RecyclerView.Adapter<SaveSearchAdp.RecyclerViewHolder> {

        List<HashMap<String, String>> arraylist;
        Context context;
        Drawable drawableBack = Const.setBackgoundBorder(2, 15, getResources().getColor(R.color.gray), Color.TRANSPARENT);

        private SaveSearchAdp(Context context, List<HashMap<String, String>> arraylist) {
            this.arraylist = arraylist;
            this.context = context;
        }

        @Override
        public SaveSearchAdp.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_save_search, parent, false);
            SaveSearchAdp.RecyclerViewHolder viewHolder = new SaveSearchAdp.RecyclerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final SaveSearchAdp.RecyclerViewHolder holder, final int position) {

            final HashMap<String, String> map = arraylist.get(position);

//            if (Const.isOdd(position)) {
//                holder.rlClick.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_background_box));
//            } else {
//                holder.rlClick.setBackgroundColor(getResources().getColor(R.color.white));
//            }
            holder.rlClick.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_background_box));

            holder.tvDate.setText(map.get("transdate"));
            holder.tvTotalFound.setText(map.get("ssearchname"));
            // holder.tvCriteriya.setText(Const.isNotNull(map.get("shape")) ? map.get("shape")+", " : Const.isNotNull(map.get("image")) ? map.get("image") : "");
            holder.tvCriteriya.setText(map.get("sdescription"));

            holder.llSearch.setBackgroundDrawable(drawableBack);
            holder.llDelete.setBackgroundDrawable(drawableBack);

            holder.llSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Pref.setStringValue(context, Const.stone_id, map.get("srefno") == "null" ? "" : map.get("srefno"));
                    Pref.setStringValue(context, Const.certi_no, map.get("scertino") == "null" ? "" : map.get("scertino"));
                    Pref.setStringValue(context, Const.shape, map.get("sshape") == "null" ? "" : map.get("sshape"));
                    Pref.setStringValue(context, Const.lab,  map.get("slab") == "null" ? "" : map.get("slab"));
                    Pref.setStringValue(context, Const.color,map.get("scolor") == "null" ? "" : map.get("scolor"));
                    Pref.setStringValue(context, Const.colortype,map.get("colortype") == "null" ? "" : map.get("colortype"));
                    Pref.setStringValue(context, Const.intencitycolor,map.get("intensity") == "null" ? "" : map.get("intensity"));
                    Pref.setStringValue(context, Const.overtonecolor,map.get("overtone") == "null" ? "" : map.get("overtone"));
                    Pref.setStringValue(context, Const.fancycolor,map.get("fancy_color") == "null" ? "" : map.get("fancy_color"));
                    Pref.setStringValue(context, Const.clarity, map.get("sclarity") == "null" ? "" : map.get("sclarity"));
                    Pref.setStringValue(context, Const.cut, map.get("scut") == "null" ? "" : map.get("scut"));
                    Pref.setStringValue(context, Const.polish, map.get("spolish") == "null" ? "" : map.get("spolish"));
                    Pref.setStringValue(context, Const.symmetry, map.get("ssymm") == "null" ? "" : map.get("ssymm"));
                    Pref.setStringValue(context, Const.fi, map.get("sfls") == "null" ? "" : map.get("sfls"));
                    Pref.setStringValue(context, Const.bgm, map.get("bgm") == "null" ? "" : map.get("bgm"));
                    Pref.setStringValue(context, Const.location, map.get("location1") == "null" ? "" : map.get("location1"));
                    Pref.setStringValue(context, Const.inclusion,  map.get("sinclusion") == "null" ? "" : map.get("sinclusion"));
                    Pref.setStringValue(context, Const.natts, map.get("snatts") == "null" ? "" : map.get("snatts"));
                    Pref.setStringValue(context, Const.crown_inclusion,  map.get("scrowninclusion") == "null" ? "" : map.get("scrowninclusion"));
                    Pref.setStringValue(context, Const.crown_natts, map.get("scrownnatts") == "null" ? "" : map.get("scrownnatts"));
                    Pref.setStringValue(context, Const.table_open, map.get("table_open") == "null" ? "" : map.get("table_open"));
                    Pref.setStringValue(context, Const.crown_open, map.get("crown_open") == "null" ? "" : map.get("crown_open"));
                    Pref.setStringValue(context, Const.pav_open, map.get("pav_open") == "null" ? "" : map.get("pav_open"));
                    Pref.setStringValue(context, Const.girdle_open, map.get("girdle_open") == "null" ? "" : map.get("girdle_open"));
                    Pref.setStringValue(context, Const.image,  map.get("bimage") == "null" ? "" : map.get("bimage"));
                    Pref.setStringValue(context, Const.video, map.get("bhdmovie") == "null" ? "" : map.get("bhdmovie"));
                    Pref.setStringValue(context, Const.pointer, map.get("spointer") == "null" ? "" : map.get("spointer"));
                    if(!map.get("dfromdisc").equalsIgnoreCase("null") && !map.get("dtodisc").equalsIgnoreCase("null")){
                        Pref.setStringValue(context, Const.from_disc, map.get("dfromdisc"));
                        Pref.setStringValue(context, Const.to_disc, map.get("dtodisc"));
                    }else{
                        Pref.setStringValue(context, Const.from_disc, "");
                        Pref.setStringValue(context, Const.to_disc, "");
                    }
                    if(!map.get("dfrompricects").equalsIgnoreCase("null") && !map.get("dtopricects").equalsIgnoreCase("null")){
                        Pref.setStringValue(context, Const.from_pcu, map.get("dfrompricects"));
                        Pref.setStringValue(context, Const.to_pcu, map.get("dtopricects"));
                    }else{
                        Pref.setStringValue(context, Const.from_pcu, "");
                        Pref.setStringValue(context, Const.to_pcu, "");
                    }
                    if(!map.get("dfromnetprice").equalsIgnoreCase("null") && !map.get("dtonetprice").equalsIgnoreCase("null")){
                        Pref.setStringValue(context, Const.from_na, map.get("dfromnetprice"));
                        Pref.setStringValue(context, Const.to_na, map.get("dtonetprice"));
                    }else{
                        Pref.setStringValue(context, Const.from_na, "");
                        Pref.setStringValue(context, Const.to_na, "");
                    }
                    if(!map.get("dfromlength").equalsIgnoreCase("null") && !map.get("dtolength").equalsIgnoreCase("null")){
                        Pref.setStringValue(context, Const.from_length, map.get("dfromlength"));
                        Pref.setStringValue(context, Const.to_length, map.get("dtolength"));
                    }else{
                        Pref.setStringValue(context, Const.from_length, "");
                        Pref.setStringValue(context, Const.to_length, "");
                    }
                    if(!map.get("dfromwidth").equalsIgnoreCase("null") && !map.get("dtowidth").equalsIgnoreCase("null")){
                        Pref.setStringValue(context, Const.from_width, map.get("dfromwidth"));
                        Pref.setStringValue(context, Const.to_width, map.get("dtowidth"));
                    }else{
                        Pref.setStringValue(context, Const.from_width, "");
                        Pref.setStringValue(context, Const.to_width, "");
                    }
                    if(!map.get("dfromdepth").equalsIgnoreCase("null") && !map.get("dtodepth").equalsIgnoreCase("null")){
                        Pref.setStringValue(context, Const.from_depth, map.get("dfromdepth"));
                        Pref.setStringValue(context, Const.to_depth, map.get("dtodepth"));
                    }else{
                        Pref.setStringValue(context, Const.from_depth, "");
                        Pref.setStringValue(context, Const.to_depth, "");
                    }
                    if(!map.get("dfromdepthper").equalsIgnoreCase("null") && !map.get("dtodepthper").equalsIgnoreCase("null")){
                        Pref.setStringValue(context, Const.from_depth_p, map.get("dfromdepthper"));
                        Pref.setStringValue(context, Const.to_depth_p, map.get("dtodepthper"));
                    }else{
                        Pref.setStringValue(context, Const.from_depth_p, "");
                        Pref.setStringValue(context, Const.to_depth_p, "");
                    }
                    if(!map.get("dfromtableper").equalsIgnoreCase("null") && !map.get("dtotableper").equalsIgnoreCase("null")){
                        Pref.setStringValue(context, Const.from_table_p, map.get("dfromtableper"));
                        Pref.setStringValue(context, Const.to_table_p, map.get("dtotableper"));
                    }else{
                        Pref.setStringValue(context, Const.from_table_p, "");
                        Pref.setStringValue(context, Const.to_table_p, "");
                    }
                    if(!map.get("dfromcrang").equalsIgnoreCase("null") && !map.get("dtocrang").equalsIgnoreCase("null")){
                        Pref.setStringValue(context, Const.from_crang_p, map.get("dfromcrang"));
                        Pref.setStringValue(context, Const.to_crang_p, map.get("dtocrang"));
                    }else{
                        Pref.setStringValue(context, Const.from_crang_p, "");
                        Pref.setStringValue(context, Const.to_crang_p, "");
                    }
                    if(!map.get("dfromcrht").equalsIgnoreCase("null") && !map.get("dtocrht").equalsIgnoreCase("null")){
                        Pref.setStringValue(context, Const.from_crht_p, map.get("dfromcrht"));
                        Pref.setStringValue(context, Const.to_crht_p, map.get("dtocrht"));
                    }else{
                        Pref.setStringValue(context, Const.from_crht_p, "");
                        Pref.setStringValue(context, Const.to_crht_p, "");
                    }
                    if(!map.get("dfrompavang").equalsIgnoreCase("null") && !map.get("dtopavang").equalsIgnoreCase("null")){
                        Pref.setStringValue(context, Const.from_pavang_p, map.get("dfrompavang"));
                        Pref.setStringValue(context, Const.to_pavang_p, map.get("dtopavang"));
                    }else{
                        Pref.setStringValue(context, Const.from_pavang_p, "");
                        Pref.setStringValue(context, Const.to_pavang_p, "");
                    }
                    if(!map.get("dfrompavht").equalsIgnoreCase("null") && !map.get("dtopavht").equalsIgnoreCase("null")){
                        Pref.setStringValue(context, Const.from_pavht_p, map.get("dfrompavht"));
                        Pref.setStringValue(context, Const.to_pavht_p, map.get("dtopavht"));
                    }else{
                        Pref.setStringValue(context, Const.from_pavht_p, "");
                        Pref.setStringValue(context, Const.to_pavht_p, "");
                    }
                    if(!map.get("dfromcts").equalsIgnoreCase("null") && !map.get("dtocts").equalsIgnoreCase("null")){
                        Pref.setStringValue(context, Const.from_cts, map.get("dfromcts"));
                        Pref.setStringValue(context, Const.to_cts, map.get("dtocts"));
                    }else{
                        Pref.setStringValue(context, Const.from_cts, "");
                        Pref.setStringValue(context, Const.to_cts, "");
                    }
                    Pref.setStringValue(context, Const.keytosymbol, map.get("skeytosymbol") == "null" ? "" : map.get("skeytosymbol"));
                    Const.flag_searchResult=4;
                    Intent i = new Intent(context, SearchResultActivity.class);
                    i.putExtra("selected", map);
                    i.setFlags(555);
                    context.startActivity(i);
                }
            });

            holder.llDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog alertDialog = new AlertDialog.Builder(context, R.style.Theme_Dialog).create();
                    alertDialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
                    alertDialog.getWindow().setBackgroundDrawable(setBackgoundBorder(0, 20, Color.WHITE, Color.WHITE));
                    alertDialog.setTitle(R.string.app_name_dialog);
                    alertDialog.setMessage("Are you sure you want to delete?");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,"YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (isNetworkAvailable()) {
                                DeleteSearch(map.get("isearchid"));
                                maps.remove(arraylist.get(position));
                                adp.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        }
                    });

                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (isNetworkAvailable()) {
                                dialog.dismiss();
                            }
                        }
                    });

                    alertDialog.show();
                    ((TextView) alertDialog.findViewById(android.R.id.message)).setTypeface(ResourcesCompat.getFont(context, R.font.proxima_nova));
                    alertDialog.getButton(alertDialog.BUTTON1).setTextColor(Color.BLACK);
                    alertDialog.getButton(alertDialog.BUTTON2).setTextColor(Color.BLACK);
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
            LinearLayout llSearch, llDelete;

            private RecyclerViewHolder(View v) {
                super(v);
                tvDate = v.findViewById(R.id.tvDate);
                tvTotalFound = v.findViewById(R.id.tvTotalFound);
                rlClick = v.findViewById(R.id.rlClick);
                llDelete = v.findViewById(R.id.llDelete);
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

    public Boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) SaveSearchActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

}
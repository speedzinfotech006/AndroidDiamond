package com.dnk.shairugems;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dnk.shairugems.Utils.Const;
import com.dnk.shairugems.Utils.Pref;
import com.dnk.shairugems.Volly.VolleyCallback;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PacketTrace extends BaseDrawerActivity {

    ImageView imgMenu, imgSearch, imgDown;
    LinearLayout llSearchTop,llSearch, llClear;
    LinearLayout llButtom,llGIA,llImage, llVideo;
    RecyclerView rvList;
    LinearLayoutManager lm;
    LinearLayout llHorizontal;
    EditText etStockID, etCertiNo;
    ArrayList<HashMap<String, String>> maps = new ArrayList<>();
    ResultAdp adp;
    FloatingActionMenu menu;
    RelativeLayout toolBar;
    boolean isChange = true;
    Context context = PacketTrace.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_packet_trace, frameLayout);

        initView();

        Pref.removeValue(context, Const.stone_id);
        Pref.removeValue(context, Const.certi_no);
    }

    private void initView() {
        imgMenu = findViewById(R.id.imgMenu);
        imgSearch = findViewById(R.id.imgSearch);
        etStockID = findViewById(R.id.etStockID);
        etCertiNo = findViewById(R.id.etCertiNo);
        llSearch = findViewById(R.id.llSearch);
        llClear = findViewById(R.id.llClear);
        rvList = findViewById(R.id.rvList);
        toolBar = findViewById(R.id.toolBar);
        menu = findViewById(R.id.menu);
        imgDown = findViewById(R.id.imgDown);

        llHorizontal = findViewById(R.id.llHorizontal);
        llSearchTop = findViewById(R.id.llSearchTop);

        llButtom = findViewById(R.id.llButtom);
        llGIA = findViewById(R.id.llGIA);
        llImage = findViewById(R.id.llImage);
        llVideo = findViewById(R.id.llVideo);

        lm = new LinearLayoutManager(context);
        rvList.setLayoutManager(lm);

        imgMenu.setOnClickListener(clickListener);
        imgDown.setOnClickListener(clickListener);
        llClear.setOnClickListener(clickListener);
        llSearch.setOnClickListener(clickListener);
    }

    private void getSearchResult() {
        Const.showProgress(context);
        maps = new ArrayList<>();
        final Map<String, String> map = new HashMap<>();
        map.put("StockId", Pref.getStringValue(context, Const.stone_id, ""));
        map.put("CertiNo", Pref.getStringValue(context, Const.certi_no, ""));
        Const.callPostApi(context, "User/PacketTraceGetList", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Const.dismissProgress();
                try {
                    JSONObject object = new JSONObject(result);
                    if (!object.optString("Data").equalsIgnoreCase("[]")) {
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
                        }
                        adp = new ResultAdp(context, maps, isChange);
                        rvList.setAdapter(adp);

                        llHorizontal.setVisibility(View.VISIBLE);
                        llButtom.setVisibility(View.VISIBLE);
                        adp.notifyDataSetChanged();

                    } else {
                        adp = null;
                        rvList.setAdapter(null);
                        llHorizontal.setVisibility(View.GONE);
                        llButtom.setVisibility(View.GONE);
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
            View view = LayoutInflater.from(context).inflate(R.layout.layout_packet_trace, parent, false);
            return new ResultAdp.GridViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            final HashMap<String, String> map = arraylist.get(position);

            if (holder instanceof ResultAdp.GridViewHolder) {
                final ResultAdp.GridViewHolder h = (ResultAdp.GridViewHolder) holder;

                h.tvSrNo.setText((position + 1) + ".");

                String[] dateTime = map.get("trans_date").split(" ");
                h.tvTransDate.setText(Const.notNullString(dateTime[0],""));
                h.tvProcess.setText(Const.notNullString(map.get("process"), ""));
                h.tvParty.setText(Const.notNullString(map.get("party"), ""));
                h.tvSourceParty.setText(Const.notNullString(map.get("source_party"), ""));
                h.tvStockId.setText(Const.notNullString(map.get("ref_no"), ""));
                h.tvCertiNo.setText(Const.notNullString(map.get("certi_no"), ""));
                h.tvCts.setText(Const.notNullString(map.get("cts"), ""));
                h.tvColor.setText(Const.notNullString(map.get("color"), ""));
                h.tvClarity.setText(Const.notNullString(map.get("purity"), ""));
                h.tvCut.setText(Const.notNullString(map.get("cut"), ""));
                h.tvPolish.setText(Const.notNullString(map.get("polish"), ""));
                h.tvSymm.setText(Const.notNullString(map.get("symm"), ""));
                h.tvFLS.setText(Const.notNullString(map.get("fls"), ""));
                h.tvDisc.setText(Const.notNullString(map.get("disc_offer"), ""));
                h.tvRapPrice.setText(Const.notNullString(map.get("rap_price"), ""));
                h.tvRapAmt.setText(Const.notNullString(map.get("rap_value"), ""));
                h.tvBGM.setText(Const.notNullString(map.get("bgm"), ""));

                llGIA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Const.hmPacketTrace = map;
                        startActivity(new Intent(context, PacketTraceDetail.class).putExtra("GIA", "gia"));
                        overridePendingTransition(0, 0);
                    }
                });

                llImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Const.hmPacketTrace = map;
                        startActivity(new Intent(context, PacketTraceDetail.class).putExtra("IMAGE", "image"));
                        overridePendingTransition(0, 0);
                    }
                });

                llVideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Const.hmPacketTrace = map;
                        startActivity(new Intent(context, PacketTraceDetail.class).putExtra("VIDEO", "video"));
                        overridePendingTransition(0, 0);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return arraylist.size();
        }

        private class GridViewHolder extends RecyclerView.ViewHolder {

            TextView tvSrNo, tvTransDate, tvProcess, tvParty, tvSourceParty, tvStockId, tvCertiNo, tvCts, tvColor, tvClarity,
                    tvCut, tvPolish, tvSymm, tvFLS, tvDisc, tvRapPrice, tvRapAmt, tvBGM;
            LinearLayout llMain;

            private GridViewHolder(@NonNull View v) {
                super(v);
                tvSrNo = v.findViewById(R.id.tvSrNo);
                tvTransDate =  v.findViewById(R.id.tvTransDate);
                tvProcess =  v.findViewById(R.id.tvProcess);
                tvParty =  v.findViewById(R.id.tvParty);
                tvSourceParty =  v.findViewById(R.id.tvSourceParty);
                tvStockId =  v.findViewById(R.id.tvStockId);
                tvCertiNo =  v.findViewById(R.id.tvCertiNo);
                tvCts =  v.findViewById(R.id.tvCts);
                tvColor =  v.findViewById(R.id.tvColor);
                tvClarity =  v.findViewById(R.id.tvClarity);
                tvCut =  v.findViewById(R.id.tvCut);
                tvPolish =  v.findViewById(R.id.tvPolish);
                tvSymm =  v.findViewById(R.id.tvSymm);
                tvFLS =  v.findViewById(R.id.tvFLS);
                tvDisc =  v.findViewById(R.id.tvDisc);
                tvRapPrice =  v.findViewById(R.id.tvRapPrice);
                tvRapAmt =  v.findViewById(R.id.tvRapAmt);
                tvBGM =  v.findViewById(R.id.tvBGM);

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
                case R.id.llSearch:
                   // menu.close(true);
                    Pref.removeValue(context, Const.stone_id);

                    Pref.setStringValue(context, Const.stone_id, etStockID.getText().toString().trim());
                    Pref.setStringValue(context, Const.certi_no, etCertiNo.getText().toString().trim());
                    Const.hideSoftKeyboard(PacketTrace.this);
                    if(!etStockID.getText().toString().trim().equalsIgnoreCase("") || !etCertiNo.getText().toString().trim().equalsIgnoreCase("")){
                        getSearchResult();
                    }else{
                        Const.showErrorDialog(context, "Either Stock Id or Certi No is Required");
                    }
                    break;
                case R.id.llClear:
                    etStockID.setText("");
                    etCertiNo.setText("");
                   // menu.close(true);
                    llButtom.setVisibility(View.GONE);
                    Pref.removeValue(context, Const.stone_id);
                    Pref.removeValue(context, Const.certi_no);
                    maps = new ArrayList<>();
                    Const.resetParameters(context);
                    Const.hideSoftKeyboard(PacketTrace.this);
                    getSearchResult();
                    break;
//                case R.id.llGIA:
//                    Const.hmStoneDetail = map;
//                    startActivity(new Intent(context, NewArrivalActivity.class));
//                    overridePendingTransition(0, 0);
//                    break;
//                case R.id.llImage:
//                    startActivity(new Intent(context, NewArrivalActivity.class));
//                    overridePendingTransition(0, 0);
//                    break;
//                case R.id.llVideo:
//                    startActivity(new Intent(context, NewArrivalActivity.class));
//                    overridePendingTransition(0, 0);
//                    break;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.searchview, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItem itemInfo = menu.findItem(R.id.action_info);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        TextView searchText = (TextView) searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchText.setTypeface(ResourcesCompat.getFont(context, R.font.proxima_nova));
        searchView.setQueryHint("Stone ID");

        itemInfo.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                BottomSheetDialog dialog = new BottomSheetDialog(context);
                dialog.setContentView(R.layout.layout_status_info);

//                TextView tvC = dialog.findViewById(R.id.tvC);
//                TextView tvCA = dialog.findViewById(R.id.tvCA);
//                TextView tvNA = dialog.findViewById(R.id.tvNA);
//
//                tvNA.setBackgroundDrawable(Const.setBackgoundBorder(0, 10, getResources().getColor(android.R.color.holo_red_light), Color.TRANSPARENT));
//                tvCA.setBackgroundDrawable(Const.setBackgoundBorder(0, 10, getResources().getColor(R.color.yelloww), Color.TRANSPARENT));
//                tvC.setBackgroundDrawable(Const.setBackgoundBorder(0, 10, getResources().getColor(R.color.green), Color.TRANSPARENT));

                dialog.show();
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public Boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) PacketTrace.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

}

package com.dnk.shairugems;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class NotificationActivity extends BaseDrawerActivity {

    ImageView imgMenu;
    RecyclerView rvList;
    LinearLayout llNoData;

    NotificationAdapter adp;
    List<HashMap<String, String>> arrayList = new ArrayList<>();
    Context context = NotificationActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_notification, frameLayout);

        initView();

        getNotificationList();
    }

    private void getNotificationList() {
        Const.showProgress(context);
        arrayList = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("iUserId", "");
        Const.callPostApi(context, "User/NotifyGet_User", map, new VolleyCallback() {
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
                                    arrayList.add(hm);
                                }
                                adp = new NotificationAdapter(context, arrayList);
                                rvList.setAdapter(adp);
                        }
                    }else{
                        adp = null;
                        rvList.setAdapter(null);
                        llNoData.setVisibility(View.VISIBLE);
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
                        getNotificationList();
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    private void DismissList(final String NotifyId) {
        Const.showProgress(context);
        arrayList = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("NotifyId", Const.notNullString(NotifyId,""));
        Const.callPostApi(context, "User/NotifySave", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Const.dismissProgress();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.optString("Status").equalsIgnoreCase("1")) {
                        getNotificationList();
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
                        DismissList(NotifyId);
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    private void initView() {
        imgMenu = findViewById(R.id.imgMenu);
        llNoData = findViewById(R.id.llNoData);
        rvList = findViewById(R.id.rvList);
        rvList.setLayoutManager(new LinearLayoutManager(context));

        imgMenu.setOnClickListener(clickListener);
    }

    private class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.RecyclerViewHolder> {

        List<HashMap<String, String>> arraylist;
        Context context;

        private NotificationAdapter(Context context, List<HashMap<String, String>> arraylist) {
            this.arraylist = arraylist;
            this.context = context;
        }

        @Override
        public NotificationAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_notification, parent, false);
            NotificationAdapter.RecyclerViewHolder viewHolder = new NotificationAdapter.RecyclerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final NotificationAdapter.RecyclerViewHolder holder, final int position) {

            final HashMap<String, String> map = arraylist.get(position);

            holder.tvDateTime.setText(Const.notNullString(map.get("validityfromdate"),""));
            holder.tvTitle.setText(Const.notNullString(map.get("message"),""));

            holder.tvSkip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

            holder.tvDismiss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DismissList(map.get("id"));
                }
            });
        }

        @Override
        public int getItemCount() {
            return arraylist.size();
        }

        private class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView tvDateTime, tvMessage, tvTitle;
            TextView tvSkip, tvDismiss;

            private RecyclerViewHolder(View v) {
                super(v);
                tvDateTime = v.findViewById(R.id.tvDateTime);
                tvTitle = v.findViewById(R.id.tvTitle);
                tvSkip = v.findViewById(R.id.tvSkip);
                tvDismiss = v.findViewById(R.id.tvDismiss);
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

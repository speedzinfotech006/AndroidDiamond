package com.dnk.shairugems;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dnk.shairugems.Utils.Const;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class CompareStoneActivity extends AppCompatActivity {

    RecyclerView rvList;
    NestedScrollView nsvList, nsvHeader;
    Toolbar toolBar;
    CompareStoneAdp adb;
    Context context = CompareStoneActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compare_stone_activity);

        initView();

        setSupportActionBar(toolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        NestedScrollView.OnScrollChangeListener listener = new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (v == nsvHeader) {
                    nsvList.scrollTo(0, scrollY);
                    nsvList.setOnScrollChangeListener(this);
                } else if (v == nsvList) {
                    nsvHeader.scrollTo(0, scrollY);
                    nsvHeader.setOnScrollChangeListener(this);
                }
            }
        };

        nsvHeader.setOnScrollChangeListener(listener);
        nsvList.setOnScrollChangeListener(listener);

    }

    private void fullImage(String url) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        LinearLayout layout = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(params);
        layout.setOrientation(LinearLayout.VERTICAL);
        dialog.setContentView(layout);

        ImageView img = new ImageView(context);
        Picasso.with(context)
                .load(url)
                .into(img);

        layout.addView(img);

        dialog.show();
    }

    private void initView() {
        toolBar = findViewById(R.id.toolBar);
        nsvList = findViewById(R.id.nsvList);
        nsvHeader = findViewById(R.id.nsvHeader);
        rvList = findViewById(R.id.rvList);
        rvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        adb = new CompareStoneAdp(context, Const.arrayCompareStone);
        rvList.setAdapter(adb);
        adb.notifyDataSetChanged();

    }

    private class CompareStoneAdp extends RecyclerView.Adapter<CompareStoneAdp.RecyclerViewHolder> {
        List<HashMap<String, String>> array;
        Context context;

        private CompareStoneAdp(Context context, List<HashMap<String, String>> array) {
            this.array = array;
            this.context = context;
        }

        @Override
        public CompareStoneAdp.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_compare_stone, parent, false);
            CompareStoneAdp.RecyclerViewHolder viewHolder = new CompareStoneAdp.RecyclerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final CompareStoneAdp.RecyclerViewHolder holder, final int position) {

            final HashMap<String, String> hm = array.get(position);

            holder.tvTablePer.setText(hm.get("table_per"));
            holder.tvDepthPer.setText(hm.get("depth_per"));
            holder.tvLength.setText(hm.get("length"));
            holder.tvWidth.setText(hm.get("width"));
            holder.tvDepth.setText(hm.get("depth"));
            holder.tvFls.setText(hm.get("fls"));
            holder.tvSymm.setText(hm.get("symm"));
            holder.tvPolish.setText(hm.get("polish"));
            holder.tvCut.setText(hm.get("cut"));
            if (hm.get("cut").equalsIgnoreCase("3EX")) {
                holder.tvCut.setTypeface(Typeface.DEFAULT_BOLD);
                holder.tvPolish.setTypeface(Typeface.DEFAULT_BOLD);
                holder.tvSymm.setTypeface(Typeface.DEFAULT_BOLD);
            } else {
                holder.tvCut.setTypeface(Typeface.DEFAULT);
                holder.tvPolish.setTypeface(Typeface.DEFAULT);
                holder.tvSymm.setTypeface(Typeface.DEFAULT);
            }
            holder.tvTotalAmt.setText(String.format("%,.2f", Double.parseDouble(hm.get("net_amount"))));
            holder.tvDiscPer.setText(String.format("%.2f", Double.parseDouble(hm.get("sales_disc_per"))));
            holder.tvRapAmt.setText(String.format("%,.2f", Double.parseDouble(hm.get("rap_amount"))));
            holder.tvRapPrice.setText(String.format("%,.2f", Double.parseDouble(hm.get("cur_rap_rate"))));
            holder.tvCts.setText(String.format("%,.2f", Double.parseDouble(hm.get("cts"))));
            holder.tvClarity.setText(hm.get("clarity"));
            holder.tvColor.setText(hm.get("color"));
//            holder.tvShade.setText(hm.get("shade"));
            holder.tvCertiNo.setText(hm.get("lab") + " "+ hm.get("certi_no"));
            holder.tvShape.setText(hm.get("shape"));
//            holder.tvLab.setText(hm.get("lab"));
            holder.tvStatus.setText(hm.get("status"));
            holder.tvStoneID.setText(hm.get("stone_ref_no"));
            holder.tvBgm.setText(hm.get("bgm"));
            if(!hm.get("symbol").equalsIgnoreCase("null")){
                holder.tvSymbol.setText(hm.get("symbol"));
            }else{
                holder.tvSymbol.setText("");
            }
            holder.tvTableBlack.setText(hm.get("table_natts"));
            holder.tvCrownBlack.setText(hm.get("crown_natts"));
            holder.tvTableWhite.setText(hm.get("inclusion"));
            holder.tvCrownWhite.setText(hm.get("crown_inclusion"));
            holder.tvCrownAngle.setText(hm.get("crown_angle"));
            holder.tvCrownHeight.setText(hm.get("crown_height"));
            holder.tvPavilionAngle.setText(hm.get("pav_angle"));
            holder.tvPavilionHeight.setText(hm.get("pav_height"));
            holder.tvGirdleType.setText(hm.get("girdle_type"));

            Picasso.with(context)
                    .load(Const.SiteUrl + hm.get("certi_no") + "/PR.jpg")
                    .resize(512, 512)
                    .error(R.drawable.ic_no_image)
                    .into(holder.imgImage);

            holder.imgImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fullImage(Const.SiteUrl + hm.get("certi_no") + "/PR.jpg");
                }
            });

        }

        @Override
        public int getItemCount() {
            return array.size();
        }

        private class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView tvTablePer, tvDepthPer, tvLength, tvWidth, tvDepth, tvFls, tvSymm, tvPolish, tvCut, tvTotalAmt, tvDiscPer,
                        tvRapAmt, tvRapPrice, tvCts, tvClarity, tvColor, tvShade, tvCertiNo, tvShape, tvLab, tvStatus, tvStoneID,
                            tvBgm, tvSymbol, tvTableWhite, tvCrownWhite, tvTableBlack, tvCrownBlack, tvCrownAngle, tvCrownHeight,
                                tvPavilionAngle, tvPavilionHeight, tvGirdleType;
            ImageView imgImage;

            private RecyclerViewHolder(View v) {
                super(v);
                tvTablePer = v.findViewById(R.id.tvTablePer);
                tvDepthPer = v.findViewById(R.id.tvDepthPer);
                tvLength = v.findViewById(R.id.tvLength);
                tvWidth = v.findViewById(R.id.tvWidth);
                tvDepth = v.findViewById(R.id.tvDepth);
                tvFls = v.findViewById(R.id.tvFls);
                tvSymm = v.findViewById(R.id.tvSymm);
                tvPolish = v.findViewById(R.id.tvPolish);
                tvCut = v.findViewById(R.id.tvCut);
                tvTotalAmt = v.findViewById(R.id.tvTotalAmt);
                tvDiscPer = v.findViewById(R.id.tvDiscPer);
                tvRapAmt = v.findViewById(R.id.tvRapAmt);
                tvRapPrice = v.findViewById(R.id.tvRapPrice);
                tvCts = v.findViewById(R.id.tvCts);
                tvClarity = v.findViewById(R.id.tvClarity);
                tvColor = v.findViewById(R.id.tvColor);
//                tvShade = v.findViewById(R.id.tvShade);
                tvCertiNo = v.findViewById(R.id.tvCertiNo);
                tvShape = v.findViewById(R.id.tvShape);
//                tvLab = v.findViewById(R.id.tvLab);
                tvStatus = v.findViewById(R.id.tvStatus);
                tvStoneID = v.findViewById(R.id.tvStoneID);
                imgImage = v.findViewById(R.id.imgImage);
                tvBgm = v.findViewById(R.id.tvBgm);
                tvSymbol = v.findViewById(R.id.tvSymbol);
                tvTableWhite = v.findViewById(R.id.tvTableWhite);
                tvCrownWhite = v.findViewById(R.id.tvCrownWhite);
                tvTableBlack = v.findViewById(R.id.tvTableBlack);
                tvCrownBlack = v.findViewById(R.id.tvCrownBlack);
                tvCrownAngle = v.findViewById(R.id.tvCrownAngle);
                tvCrownHeight = v.findViewById(R.id.tvCrownHeight);
                tvPavilionAngle = v.findViewById(R.id.tvPavilionAngle);
                tvPavilionHeight = v.findViewById(R.id.tvPavilionHeight);
                tvGirdleType = v.findViewById(R.id.tvGirdleType);
            }
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
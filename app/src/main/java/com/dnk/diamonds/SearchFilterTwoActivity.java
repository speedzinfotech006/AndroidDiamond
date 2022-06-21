package com.dnk.shairugems;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dnk.shairugems.Utils.Const;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class SearchFilterTwoActivity extends BaseDrawerActivity {

    Context context = SearchFilterTwoActivity.this;
    TextView tvSelectedSize;
    EditText etSymbol;
    ImageView imgMenu, imgCall, imgEmail, imgSizeBtn;
    RecyclerView rvShape, rvLab, rvColor, rvClarity, rvCut, rvPolish, rvSymmetry, rvFi, rvBgm, rvSize, rvInclusion, rvNatts;
    HorizontalScrollView hsSize;
    RelativeLayout rlMain;
    CheckBox cbMovie, cbImage;
    LinearLayout llSizeFromTo, llSearch, llReset;
    ShapeAdp shapeAdp;
    LabAdp labAdp;
    ColorAdp colorAdp;
    ClarityAdp clarityAdp;
    CutAdp cutAdp;
    PolishAdp polishAdp;
    SymmetryAdp symmetryAdp;
    FiAdp fiAdp;
    BgmAdp bgmAdp;
    SizeAdp sizeAdp;
    InclusionAdp inclusionAdp;
    NattsAdp nattsAdp;
    List<String> arraySymbol = new ArrayList<>();
    HashMap<Integer, String> selectedSymbol = new HashMap<>();
    HashMap<Integer, String> selectedShape = new HashMap<>();
    HashMap<Integer, String> selectedLab = new HashMap<>();
    HashMap<Integer, String> selectedColor = new HashMap<>();
    HashMap<Integer, String> selectedClarity = new HashMap<>();
    HashMap<Integer, String> selectedCut = new HashMap<>();
    HashMap<Integer, String> selectedPolish = new HashMap<>();
    HashMap<Integer, String> selectedSymmetry = new HashMap<>();
    HashMap<Integer, String> selectedFi = new HashMap<>();
    HashMap<Integer, String> selectedBgm = new HashMap<>();
    HashMap<Integer, String> selectedSize = new HashMap<>();
    HashMap<Integer, String> selectedInclusion = new HashMap<>();
    HashMap<Integer, String> selectedNatts = new HashMap<>();

    LinearLayout llAll, llRound, llCushion, llEmerald, llOval, llHeart, llPear, llMarquise, llPrincess, llAsscher, llRadiant, llTriangle, llOthers;
    TextView tvAll, tvRound, tvCushion, tvEmerald, tvOval, tvHeart, tvPear, tvMarquise, tvPrincess, tvAsscher, tvRadiant, tvTriangle, tvOthers;
    ImageView imgAll, imgRound, imgCushion, imgEmerald, imgOval, imgHeart, imgPear, imgMarquise, imgPrincess, imgAsscher, imgRadiant, imgTriangle, imgOthers;
    boolean all = false, round = false, cushion = false, emerald = false, oval = false, heart = false, pear = false, marquise = false, princess = false, asscher = false, radiant = false, triangle = false, others = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.search_filter_two_activity, frameLayout);

        Log.e("Activity Open ","Search Filter two Activity");

        initView();

        etSymbol.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etSymbol.performClick();
                }
            }
        });

//        shapeAdp = new ShapeAdp(context, getResources().getStringArray(R.array.shape), getResources().obtainTypedArray(R.array.shape_image));
//        rvShape.setAdapter(shapeAdp);

        labAdp = new LabAdp(context, getResources().getStringArray(R.array.lab));
        rvLab.setAdapter(labAdp);

        colorAdp = new ColorAdp(context, getResources().getStringArray(R.array.color));
        rvColor.setAdapter(colorAdp);

        clarityAdp = new ClarityAdp(context, getResources().getStringArray(R.array.clarity));
        rvClarity.setAdapter(clarityAdp);

        cutAdp = new CutAdp(context, getResources().getStringArray(R.array.cut));
        rvCut.setAdapter(cutAdp);

        polishAdp = new PolishAdp(context, getResources().getStringArray(R.array.polish_symmetry));
        rvPolish.setAdapter(polishAdp);

        symmetryAdp = new SymmetryAdp(context, getResources().getStringArray(R.array.polish_symmetry));
        rvSymmetry.setAdapter(symmetryAdp);

        fiAdp = new FiAdp(context, getResources().getStringArray(R.array.fi));
        rvFi.setAdapter(fiAdp);

        bgmAdp = new BgmAdp(context, getResources().getStringArray(R.array.bgm));
        rvBgm.setAdapter(bgmAdp);

        sizeAdp = new SizeAdp(context, getResources().getStringArray(R.array.size));
        rvSize.setAdapter(sizeAdp);

        inclusionAdp = new InclusionAdp(context, getResources().getStringArray(R.array.table_black));
        rvInclusion.setAdapter(inclusionAdp);

        nattsAdp = new NattsAdp(context, getResources().getStringArray(R.array.table_black));
        rvNatts.setAdapter(nattsAdp);

    }

    private void initView() {
        imgMenu = findViewById(R.id.imgMenu);
        tvSelectedSize = findViewById(R.id.tvSelectedSize);
        llSearch = findViewById(R.id.llSearch);
        etSymbol = findViewById(R.id.etSymbol);
        cbImage = findViewById(R.id.cbImage);
        cbMovie = findViewById(R.id.cbMovie);
        hsSize = findViewById(R.id.hsSize);
        rlMain = findViewById(R.id.rlMain);
        llSizeFromTo = findViewById(R.id.llSizeFromTo);
        imgEmail = findViewById(R.id.imgEmail);
        imgCall = findViewById(R.id.imgCall);
        imgSizeBtn = findViewById(R.id.imgSizeBtn);
        llReset = findViewById(R.id.llReset);
   //     rvShape = findViewById(R.id.rvShape);
        rvLab = findViewById(R.id.rvLab);
        rvColor = findViewById(R.id.rvColor);
        rvClarity = findViewById(R.id.rvClarity);
        rvCut = findViewById(R.id.rvCut);
        rvPolish = findViewById(R.id.rvPolish);
        rvSymmetry = findViewById(R.id.rvSymmetry);
        rvFi = findViewById(R.id.rvFi);
        rvBgm = findViewById(R.id.rvBgm);
        rvSize = findViewById(R.id.rvSize);
        rvInclusion = findViewById(R.id.rvInclusion);
        rvNatts = findViewById(R.id.rvNatts);

        llAll = findViewById(R.id.llAll);
        llRound = findViewById(R.id.llRound);
        llCushion = findViewById(R.id.llCushion);
        llEmerald = findViewById(R.id.llEmerald);
        llOval = findViewById(R.id.llOval);
        llHeart = findViewById(R.id.llHeart);
        llPear = findViewById(R.id.llPear);
        llMarquise = findViewById(R.id.llMarquise);
        llPrincess = findViewById(R.id.llPrincess);
        llAsscher = findViewById(R.id.llAsscher);
        llRadiant = findViewById(R.id.llRadiant);
        llTriangle = findViewById(R.id.llTriangle);
        llOthers = findViewById(R.id.llOthers);

        imgAll = findViewById(R.id.imgAll);
        imgRound = findViewById(R.id.imgRound);
        imgCushion = findViewById(R.id.imgCushion);
        imgEmerald = findViewById(R.id.imgEmerald);
        imgOval = findViewById(R.id.imgOval);
        imgHeart = findViewById(R.id.imgHeart);
        imgPear = findViewById(R.id.imgPear);
        imgMarquise = findViewById(R.id.imgMarquise);
        imgPrincess = findViewById(R.id.imgPrincess);
        imgAsscher = findViewById(R.id.imgAsscher);
        imgRadiant = findViewById(R.id.imgRadiant);
        imgTriangle = findViewById(R.id.imgTriangle);
        imgOthers = findViewById(R.id.imgOthers);

        tvAll = findViewById(R.id.tvAll);
        tvRound = findViewById(R.id.tvRound);
        tvCushion = findViewById(R.id.tvCushion);
        tvEmerald = findViewById(R.id.tvEmerald);
        tvOval = findViewById(R.id.tvOval);
        tvHeart = findViewById(R.id.tvHeart);
        tvPear = findViewById(R.id.tvPear);
        tvMarquise = findViewById(R.id.tvMarquise);
        tvPrincess = findViewById(R.id.tvPrincess);
        tvAsscher = findViewById(R.id.tvAsscher);
        tvRadiant = findViewById(R.id.tvRadiant);
        tvTriangle = findViewById(R.id.tvTriangle);
        tvOthers = findViewById(R.id.tvOthers);

        llAll.setOnClickListener(clickListener);
        llRound.setOnClickListener(clickListener);
        llCushion.setOnClickListener(clickListener);
        llEmerald.setOnClickListener(clickListener);
        llOval.setOnClickListener(clickListener);
        llHeart.setOnClickListener(clickListener);
        llPear.setOnClickListener(clickListener);
        llMarquise.setOnClickListener(clickListener);
        llPrincess.setOnClickListener(clickListener);
        llAsscher.setOnClickListener(clickListener);
        llRadiant.setOnClickListener(clickListener);
        llTriangle.setOnClickListener(clickListener);
        llOthers.setOnClickListener(clickListener);

        llAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvAll.setBackgroundColor(getResources().getColor(R.color.gray));
        tvAll.setTextColor(getResources().getColor(R.color.colorPrimary));
        imgAll.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);

        llRound.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvRound.setBackgroundColor(getResources().getColor(R.color.gray));
        tvRound.setTextColor(getResources().getColor(R.color.colorPrimary));
        imgRound.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);

        llCushion.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvCushion.setBackgroundColor(getResources().getColor(R.color.gray));
        tvCushion.setTextColor(getResources().getColor(R.color.colorPrimary));
        imgCushion.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);

        llEmerald.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvEmerald.setBackgroundColor(getResources().getColor(R.color.gray));
        tvEmerald.setTextColor(getResources().getColor(R.color.colorPrimary));
        imgEmerald.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);

        llOval.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvOval.setBackgroundColor(getResources().getColor(R.color.gray));
        tvOval.setTextColor(getResources().getColor(R.color.colorPrimary));
        imgOval.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);

        llHeart.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvHeart.setBackgroundColor(getResources().getColor(R.color.gray));
        tvHeart.setTextColor(getResources().getColor(R.color.colorPrimary));
        imgHeart.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);

        llPear.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvPear.setBackgroundColor(getResources().getColor(R.color.gray));
        tvPear.setTextColor(getResources().getColor(R.color.colorPrimary));
        imgPear.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);

        llMarquise.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvMarquise.setBackgroundColor(getResources().getColor(R.color.gray));
        tvMarquise.setTextColor(getResources().getColor(R.color.colorPrimary));
        imgMarquise.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);

        llPrincess.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvPrincess.setBackgroundColor(getResources().getColor(R.color.gray));
        tvPrincess.setTextColor(getResources().getColor(R.color.colorPrimary));
        imgPrincess.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);

        llAsscher.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvAsscher.setBackgroundColor(getResources().getColor(R.color.gray));
        tvAsscher.setTextColor(getResources().getColor(R.color.colorPrimary));
        imgAsscher.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);

        llRadiant.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvRadiant.setBackgroundColor(getResources().getColor(R.color.gray));
        tvRadiant.setTextColor(getResources().getColor(R.color.colorPrimary));
        imgRadiant.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);

        llTriangle.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvTriangle.setBackgroundColor(getResources().getColor(R.color.gray));
        tvTriangle.setTextColor(getResources().getColor(R.color.colorPrimary));
        imgTriangle.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);

        llOthers.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvOthers.setBackgroundColor(getResources().getColor(R.color.gray));
        tvOthers.setTextColor(getResources().getColor(R.color.colorPrimary));
        imgOthers.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);

        //       rvShape.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvLab.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvColor.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvClarity.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvCut.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvPolish.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvSymmetry.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvFi.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvBgm.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvSize.setLayoutManager(new GridLayoutManager(context, 4));
        rvInclusion.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvNatts.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        hsSize.setBackgroundDrawable(Const.setBackgoundBorder(2, 10, Color.TRANSPARENT, Color.BLACK));
        llSearch.setBackgroundDrawable(Const.setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));
        llReset.setBackgroundDrawable(Const.setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));

        arraySymbol.add("BROWN PATCH OF COLOR");
        arraySymbol.add("BRUISE");
        arraySymbol.add("CAVITY");
        arraySymbol.add("CLOUD");
        arraySymbol.add("CHIP");
        arraySymbol.add("CRYSTAL");
        arraySymbol.add("ETCH CHANNEL");
        arraySymbol.add("EXTRA FACET");
        arraySymbol.add("FEATHER");
        arraySymbol.add("INDENTED");
        arraySymbol.add("INDENTED NATURAL");
        arraySymbol.add("INTERNAL GRAINING");
        for (int i = 0; i < arraySymbol.size(); i++) {
            selectedSymbol.put(i, arraySymbol.get(i));
        }

        imgMenu.setOnClickListener(clickListener);
        imgCall.setOnClickListener(clickListener);
        etSymbol.setOnClickListener(clickListener);
        imgEmail.setOnClickListener(clickListener);
        llReset.setOnClickListener(clickListener);
        imgSizeBtn.setOnClickListener(clickListener);
        llSearch.setOnClickListener(clickListener);
    }

    private void clearSelected() {
        selectedSymbol = new HashMap<>();
        for (int i = 0; i < arraySymbol.size(); i++) {
            selectedSymbol.put(i, arraySymbol.get(i));
        }
        selectedShape = new HashMap<>();
        selectedLab = new HashMap<>();
        selectedColor = new HashMap<>();
        selectedClarity = new HashMap<>();
        selectedCut = new HashMap<>();
        selectedPolish = new HashMap<>();
        selectedSymmetry = new HashMap<>();
        selectedFi = new HashMap<>();
        selectedBgm = new HashMap<>();
        selectedSize = new HashMap<>();
        selectedInclusion = new HashMap<>();
        selectedNatts = new HashMap<>();
        shapeAdp.notifyDataSetChanged();
        labAdp.notifyDataSetChanged();
        colorAdp.notifyDataSetChanged();
        clarityAdp.notifyDataSetChanged();
        cutAdp.notifyDataSetChanged();
        polishAdp.notifyDataSetChanged();
        symmetryAdp.notifyDataSetChanged();
        fiAdp.notifyDataSetChanged();
        bgmAdp.notifyDataSetChanged();
        sizeAdp.notifyDataSetChanged();
        inclusionAdp.notifyDataSetChanged();
        nattsAdp.notifyDataSetChanged();
        hsSize.setVisibility(View.GONE);
        llSizeFromTo.setVisibility(View.VISIBLE);
        cbMovie.setChecked(false);
        cbImage.setChecked(false);
        Const.clearForm(rlMain);
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

    private void openKeyToSymbol() {
        final Dialog dialog = new Dialog(context, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(Const.setBackgoundBorder(0, 20, Color.WHITE, Color.WHITE));

        LinearLayout layout = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(params);
        layout.setOrientation(LinearLayout.VERTICAL);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int h = displayMetrics.heightPixels;
        LinearLayout.LayoutParams paramsss = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (h / 2));
        final RecyclerView rv = new RecyclerView(context);
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setLayoutParams(paramsss);

        LinearLayout layoutt = new LinearLayout(context);

        LinearLayout.LayoutParams paramss = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 2);
        layoutt.setOrientation(LinearLayout.HORIZONTAL);

        Button buttonOk = new Button(context);
        buttonOk.setTypeface(ResourcesCompat.getFont(context, R.font.proxima_nova));
        buttonOk.setText("OK");
        buttonOk.setLayoutParams(paramss);

        Button buttonReset = new Button(context);
        buttonReset.setTypeface(ResourcesCompat.getFont(context, R.font.proxima_nova));
        buttonReset.setText("Reset");
        buttonReset.setLayoutParams(paramss);

        layoutt.addView(buttonOk);
        layoutt.addView(buttonReset);

        layout.addView(rv);
        layout.addView(layoutt);

        dialog.setContentView(layout);

        SymbolsAdp adp = new SymbolsAdp(context, arraySymbol, selectedSymbol);
        rv.setAdapter(adp);
        adp.notifyDataSetChanged();

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int yes = 0, no = 0;
                for (int i = 0; i < selectedSymbol.size(); i++) {
                    if (selectedSymbol.get(i).equalsIgnoreCase("true")) {
                        yes++;
                    } else if (selectedSymbol.get(i).equalsIgnoreCase("false")) {
                        no++;
                    }
                }
                if (yes == 0 && no == 0) {
                    etSymbol.setText("");
                } else {
                    etSymbol.setText(yes + " - Selected  |  " + no + " - Deselected");
                }
                dialog.dismiss();
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedSymbol = new HashMap<>();
                for (int i = 0; i < arraySymbol.size(); i++) {
                    selectedSymbol.put(i, arraySymbol.get(i));
                }
                SymbolsAdp adp = new SymbolsAdp(context, arraySymbol, selectedSymbol);
                rv.setAdapter(adp);
                adp.notifyDataSetChanged();
            }
        });

        dialog.show();

    }

    private ArrayList<String> hmToArray(HashMap<Integer, String> selectedSize) {
        ArrayList<String> arrayList = new ArrayList<>();
        Iterator iterator = selectedSize.keySet().iterator();
        while (iterator.hasNext()) {
            Integer key = (Integer) iterator.next();
            String value = (String) selectedSize.get(key);
        }
        return arrayList;
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
                    startActivity(new Intent(context, SearchResultTwoActivity.class));
                    overridePendingTransition(0, 0);
                    break;
                case R.id.imgEmail:
                    emailDialog();
                    break;
                case R.id.etSymbol:
                    openKeyToSymbol();
                    break;
                case R.id.imgSizeBtn:
                    if (rvSize.getVisibility() == View.VISIBLE) {
                        rvSize.setVisibility(View.GONE);
                        imgSizeBtn.setImageResource(R.drawable.ic_plus);
                    } else {
                        rvSize.setVisibility(View.VISIBLE);
                        imgSizeBtn.setImageResource(R.drawable.ic_minus);
                    }
                    break;
                case R.id.llReset:
                    clearSelected();
                    break;
                case R.id.imgCall:
                    Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+9876543210"));
                    startActivity(i);
                    overridePendingTransition(0, 0);
                    break;
                case R.id.llAll:
                        if (all) {
                            llAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                            tvAll.setBackgroundColor(getResources().getColor(R.color.gray));
                            selectedShape.remove(0);
                            tvAll.setTextColor(getResources().getColor(R.color.colorPrimary));
                            imgAll.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                            all = false;

                            llRound.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                            tvRound.setBackgroundColor(getResources().getColor(R.color.gray));
                            selectedShape.remove(1);
                            tvRound.setTextColor(getResources().getColor(R.color.colorPrimary));
                            imgRound.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                            round = false;

                            llCushion.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                            tvCushion.setBackgroundColor(getResources().getColor(R.color.gray));
                            selectedShape.remove(2);
                            tvCushion.setTextColor(getResources().getColor(R.color.colorPrimary));
                            imgCushion.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                            cushion = false;

                            llEmerald.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                            tvEmerald.setBackgroundColor(getResources().getColor(R.color.gray));
                            selectedShape.remove(3);
                            tvEmerald.setTextColor(getResources().getColor(R.color.colorPrimary));
                            imgEmerald.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                            emerald = false;

                            llOval.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                            tvOval.setBackgroundColor(getResources().getColor(R.color.gray));
                            selectedShape.remove(4);
                            tvOval.setTextColor(getResources().getColor(R.color.colorPrimary));
                            imgOval.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                            oval = false;

                            llHeart.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                            tvHeart.setBackgroundColor(getResources().getColor(R.color.gray));
                            selectedShape.remove(5);
                            tvHeart.setTextColor(getResources().getColor(R.color.colorPrimary));
                            imgHeart.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                            heart = false;

                            llPear.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                            tvPear.setBackgroundColor(getResources().getColor(R.color.gray));
                            selectedShape.remove(6);
                            tvPear.setTextColor(getResources().getColor(R.color.colorPrimary));
                            imgPear.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                            pear = false;

                            llMarquise.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                            tvMarquise.setBackgroundColor(getResources().getColor(R.color.gray));
                            selectedShape.remove(7);
                            tvMarquise.setTextColor(getResources().getColor(R.color.colorPrimary));
                            imgMarquise.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                            marquise = false;

                            llPrincess.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                            tvPrincess.setBackgroundColor(getResources().getColor(R.color.gray));
                            selectedShape.remove(8);
                            tvPrincess.setTextColor(getResources().getColor(R.color.colorPrimary));
                            imgPrincess.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                            princess = false;

                            llAsscher.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                            tvAsscher.setBackgroundColor(getResources().getColor(R.color.gray));
                            selectedShape.remove(9);
                            tvAsscher.setTextColor(getResources().getColor(R.color.colorPrimary));
                            imgAsscher.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                            asscher = false;

                            llRadiant.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                            tvRadiant.setBackgroundColor(getResources().getColor(R.color.gray));
                            selectedShape.remove(10);
                            tvRadiant.setTextColor(getResources().getColor(R.color.colorPrimary));
                            imgRadiant.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                            radiant = false;

                            llTriangle.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                            tvTriangle.setBackgroundColor(getResources().getColor(R.color.gray));
                            selectedShape.remove(11);
                            tvTriangle.setTextColor(getResources().getColor(R.color.colorPrimary));
                            imgTriangle.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                            triangle = false;

                            llOthers.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                            tvOthers.setBackgroundColor(getResources().getColor(R.color.gray));
                            selectedShape.remove(12);
                            tvOthers.setTextColor(getResources().getColor(R.color.colorPrimary));
                            imgOthers.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                            others = false;
                        } else {
                            all = true;
                            selectedShape.put(0, "ALL");
                            llAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                            tvAll.setBackgroundColor(getResources().getColor(R.color.shapeText));
                            tvAll.setTextColor(getResources().getColor(R.color.white));
                            imgAll.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                            round = true;
                            selectedShape.put(1, "Round");
                            llRound.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                            tvRound.setBackgroundColor(getResources().getColor(R.color.shapeText));
                            tvRound.setTextColor(getResources().getColor(R.color.white));
                            imgRound.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                            cushion = true;
                            selectedShape.put(2, "Cushion");
                            llCushion.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                            tvCushion.setBackgroundColor(getResources().getColor(R.color.shapeText));
                            tvCushion.setTextColor(getResources().getColor(R.color.white));
                            imgCushion.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                            emerald = true;
                            selectedShape.put(3, "Emerald");
                            llEmerald.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                            tvEmerald.setBackgroundColor(getResources().getColor(R.color.shapeText));
                            tvEmerald.setTextColor(getResources().getColor(R.color.white));
                            imgEmerald.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                            oval = true;
                            selectedShape.put(4, "Oval");
                            llOval.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                            tvOval.setBackgroundColor(getResources().getColor(R.color.shapeText));
                            tvOval.setTextColor(getResources().getColor(R.color.white));
                            imgOval.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                            heart = true;
                            selectedShape.put(5, "Heart");
                            llHeart.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                            tvHeart.setBackgroundColor(getResources().getColor(R.color.shapeText));
                            tvHeart.setTextColor(getResources().getColor(R.color.white));
                            imgHeart.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                            pear = true;
                            selectedShape.put(6, "Pear");
                            llPear.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                            tvPear.setBackgroundColor(getResources().getColor(R.color.shapeText));
                            tvPear.setTextColor(getResources().getColor(R.color.white));
                            imgPear.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                            marquise = true;
                            selectedShape.put(7, "Marquise");
                            llMarquise.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                            tvMarquise.setBackgroundColor(getResources().getColor(R.color.shapeText));
                            tvMarquise.setTextColor(getResources().getColor(R.color.white));
                            imgMarquise.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                            princess = true;
                            selectedShape.put(8, "Princess");
                            llPrincess.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                            tvPrincess.setBackgroundColor(getResources().getColor(R.color.shapeText));
                            tvPrincess.setTextColor(getResources().getColor(R.color.white));
                            imgPrincess.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                            asscher = true;
                            selectedShape.put(9, "Asscher");
                            llAsscher.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                            tvAsscher.setBackgroundColor(getResources().getColor(R.color.shapeText));
                            tvAsscher.setTextColor(getResources().getColor(R.color.white));
                            imgAsscher.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                            radiant = true;
                            selectedShape.put(10, "Radiant");
                            llRadiant.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                            tvRadiant.setBackgroundColor(getResources().getColor(R.color.shapeText));
                            tvRadiant.setTextColor(getResources().getColor(R.color.white));
                            imgRadiant.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                            triangle = true;
                            selectedShape.put(11, "Triangle");
                            llTriangle.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                            tvTriangle.setBackgroundColor(getResources().getColor(R.color.shapeText));
                            tvTriangle.setTextColor(getResources().getColor(R.color.white));
                            imgTriangle.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                            others = true;
                            selectedShape.put(11, "Others");
                            llOthers.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                            tvOthers.setBackgroundColor(getResources().getColor(R.color.shapeText));
                            tvOthers.setTextColor(getResources().getColor(R.color.white));
                            imgOthers.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                            RotateAnimation rotateAnim = new RotateAnimation(0, 360, imgAll.getWidth() / 2, imgAll.getHeight() / 2);
                            rotateAnim.setDuration(500);
                            rotateAnim.setFillAfter(true);
                            imgAll.setAnimation(rotateAnim);
                            imgRound.setAnimation(rotateAnim);
                            imgCushion.setAnimation(rotateAnim);
                            imgEmerald.setAnimation(rotateAnim);
                            imgOval.setAnimation(rotateAnim);
                            imgHeart.setAnimation(rotateAnim);
                            imgPear.setAnimation(rotateAnim);
                            imgMarquise.setAnimation(rotateAnim);
                            imgPrincess.setAnimation(rotateAnim);
                            imgAsscher.setAnimation(rotateAnim);
                            imgRadiant.setAnimation(rotateAnim);
                            imgTriangle.setAnimation(rotateAnim);
                            imgOthers.setAnimation(rotateAnim);
                        }
                    break;
                case R.id.llRound:
                    if (round) {
                        llRound.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvRound.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove("Round");
                        tvRound.setTextColor(getResources().getColor(R.color.colorPrimary));
                        imgRound.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        round = false;

                        llAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvAll.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove("ALL");
                        tvAll.setTextColor(getResources().getColor(R.color.colorPrimary));
                        imgAll.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        all = false;

                    } else {
                        round = true;
                        selectedShape.put(1, "Round");
                        llRound.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tvRound.setBackgroundColor(getResources().getColor(R.color.shapeText));
                        tvRound.setTextColor(getResources().getColor(R.color.white));
                        imgRound.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                        RotateAnimation rotateAnim = new RotateAnimation(0, 360, imgRound.getWidth() / 2, imgRound.getHeight() / 2);
                        rotateAnim.setDuration(500);
                        rotateAnim.setFillAfter(true);
                        imgRound.setAnimation(rotateAnim);
                    }
                    break;
                case R.id.llCushion:
                    if (cushion) {
                        llCushion.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvCushion.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove("Cushion");
                        tvCushion.setTextColor(getResources().getColor(R.color.colorPrimary));
                        imgCushion.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        cushion = false;

                        llAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvAll.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove("ALL");
                        tvAll.setTextColor(getResources().getColor(R.color.colorPrimary));
                        imgAll.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        all = false;

                    } else {
                        cushion = true;
                        selectedShape.put(2, "Cushion");
                        llCushion.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tvCushion.setBackgroundColor(getResources().getColor(R.color.shapeText));
                        tvCushion.setTextColor(getResources().getColor(R.color.white));
                        imgCushion.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                        RotateAnimation rotateAnim = new RotateAnimation(0, 360, imgCushion.getWidth() / 2, imgCushion.getHeight() / 2);
                        rotateAnim.setDuration(500);
                        rotateAnim.setFillAfter(true);
                        imgCushion.setAnimation(rotateAnim);
                    }
                    break;
                case R.id.llEmerald:
                    if (emerald) {
                        llEmerald.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvEmerald.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove("Emerald");
                        tvEmerald.setTextColor(getResources().getColor(R.color.colorPrimary));
                        imgEmerald.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        emerald = false;

                        llAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvAll.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove("ALL");
                        tvAll.setTextColor(getResources().getColor(R.color.colorPrimary));
                        imgAll.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        all = false;

                    } else {
                        emerald = true;
                        selectedShape.put(3, "Emerald");
                        llEmerald.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tvEmerald.setBackgroundColor(getResources().getColor(R.color.shapeText));
                        tvEmerald.setTextColor(getResources().getColor(R.color.white));
                        imgEmerald.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                        RotateAnimation rotateAnim = new RotateAnimation(0, 360, imgEmerald.getWidth() / 2, imgEmerald.getHeight() / 2);
                        rotateAnim.setDuration(500);
                        rotateAnim.setFillAfter(true);
                        imgEmerald.setAnimation(rotateAnim);
                    }
                    break;
                case R.id.llOval:
                    if (oval) {
                        llOval.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvOval.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove("Oval");
                        tvOval.setTextColor(getResources().getColor(R.color.colorPrimary));
                        imgOval.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        oval = false;

                        llAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvAll.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove("ALL");
                        tvAll.setTextColor(getResources().getColor(R.color.colorPrimary));
                        imgAll.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        all = false;

                    } else {
                        oval = true;
                        selectedShape.put(4, "Oval");
                        llOval.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tvOval.setBackgroundColor(getResources().getColor(R.color.shapeText));
                        tvOval.setTextColor(getResources().getColor(R.color.white));
                        imgOval.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                        RotateAnimation rotateAnim = new RotateAnimation(0, 360, imgOval.getWidth() / 2, imgOval.getHeight() / 2);
                        rotateAnim.setDuration(500);
                        rotateAnim.setFillAfter(true);
                        imgOval.setAnimation(rotateAnim);
                    }
                    break;
                case R.id.llHeart:
                    if (heart) {
                        llHeart.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvHeart.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove("Heart");
                        tvHeart.setTextColor(getResources().getColor(R.color.colorPrimary));
                        imgHeart.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        heart = false;

                        llAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvAll.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove("ALL");
                        tvAll.setTextColor(getResources().getColor(R.color.colorPrimary));
                        imgAll.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        all = false;

                    } else {
                        heart = true;
                        selectedShape.put(5, "Heart");
                        llHeart.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tvHeart.setBackgroundColor(getResources().getColor(R.color.shapeText));
                        tvHeart.setTextColor(getResources().getColor(R.color.white));
                        imgHeart.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                        RotateAnimation rotateAnim = new RotateAnimation(0, 360, imgHeart.getWidth() / 2, imgHeart.getHeight() / 2);
                        rotateAnim.setDuration(500);
                        rotateAnim.setFillAfter(true);
                        imgHeart.setAnimation(rotateAnim);
                    }
                    break;
                case R.id.llPear:
                    if (pear) {
                        llPear.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvPear.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove("Pear");
                        tvPear.setTextColor(getResources().getColor(R.color.colorPrimary));
                        imgPear.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        pear = false;

                        llAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvAll.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove("ALL");
                        tvAll.setTextColor(getResources().getColor(R.color.colorPrimary));
                        imgAll.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        all = false;

                    } else {
                        pear = true;
                        selectedShape.put(6, "Pear");
                        llPear.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tvPear.setBackgroundColor(getResources().getColor(R.color.shapeText));
                        tvPear.setTextColor(getResources().getColor(R.color.white));
                        imgPear.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                        RotateAnimation rotateAnim = new RotateAnimation(0, 360, imgPear.getWidth() / 2, imgPear.getHeight() / 2);
                        rotateAnim.setDuration(500);
                        rotateAnim.setFillAfter(true);
                        imgPear.setAnimation(rotateAnim);
                    }
                    break;
                case R.id.llMarquise:
                    if (marquise) {
                        llMarquise.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvMarquise.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove("Marquise");
                        tvMarquise.setTextColor(getResources().getColor(R.color.colorPrimary));
                        imgMarquise.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        marquise = false;

                        llAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvAll.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove("ALL");
                        tvAll.setTextColor(getResources().getColor(R.color.colorPrimary));
                        imgAll.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        all = false;

                    } else {
                        marquise = true;
                        selectedShape.put(7, "Marquise");
                        llMarquise.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tvMarquise.setBackgroundColor(getResources().getColor(R.color.shapeText));
                        tvMarquise.setTextColor(getResources().getColor(R.color.white));
                        imgMarquise.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                        RotateAnimation rotateAnim = new RotateAnimation(0, 360, imgMarquise.getWidth() / 2, imgMarquise.getHeight() / 2);
                        rotateAnim.setDuration(500);
                        rotateAnim.setFillAfter(true);
                        imgMarquise.setAnimation(rotateAnim);
                    }
                    break;
                case R.id.llPrincess:
                    if (princess) {
                        llPrincess.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvPrincess.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove("Princess");
                        tvPrincess.setTextColor(getResources().getColor(R.color.colorPrimary));
                        imgPrincess.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        princess = false;

                        llAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvAll.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove("ALL");
                        tvAll.setTextColor(getResources().getColor(R.color.colorPrimary));
                        imgAll.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        all = false;

                    } else {
                        princess = true;
                        selectedShape.put(8, "Princess");
                        llPrincess.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tvPrincess.setBackgroundColor(getResources().getColor(R.color.shapeText));
                        tvPrincess.setTextColor(getResources().getColor(R.color.white));
                        imgPrincess.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                        RotateAnimation rotateAnim = new RotateAnimation(0, 360, imgPrincess.getWidth() / 2, imgPrincess.getHeight() / 2);
                        rotateAnim.setDuration(500);
                        rotateAnim.setFillAfter(true);
                        imgPrincess.setAnimation(rotateAnim);
                    }
                    break;
                case R.id.llAsscher:
                    if (asscher) {
                        llAsscher.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvAsscher.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove("Asscher");
                        tvAsscher.setTextColor(getResources().getColor(R.color.colorPrimary));
                        imgAsscher.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        asscher = false;

                        llAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvAll.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove("ALL");
                        tvAll.setTextColor(getResources().getColor(R.color.colorPrimary));
                        imgAll.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        all = false;

                    } else {
                        asscher = true;
                        selectedShape.put(9, "Asscher");
                        llAsscher.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tvAsscher.setBackgroundColor(getResources().getColor(R.color.shapeText));
                        tvAsscher.setTextColor(getResources().getColor(R.color.white));
                        imgAsscher.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                        RotateAnimation rotateAnim = new RotateAnimation(0, 360, imgAsscher.getWidth() / 2, imgAsscher.getHeight() / 2);
                        rotateAnim.setDuration(500);
                        rotateAnim.setFillAfter(true);
                        imgAsscher.setAnimation(rotateAnim);
                    }
                    break;
                case R.id.llRadiant:
                    if (radiant) {
                        llRadiant.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvRadiant.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove("Radiant");
                        tvRadiant.setTextColor(getResources().getColor(R.color.colorPrimary));
                        imgRadiant.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        radiant = false;

                        llAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvAll.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove("ALL");
                        tvAll.setTextColor(getResources().getColor(R.color.colorPrimary));
                        imgAll.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        all = false;

                    } else {
                        radiant = true;
                        selectedShape.put(10, "Radiant");
                        llRadiant.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tvRadiant.setBackgroundColor(getResources().getColor(R.color.shapeText));
                        tvRadiant.setTextColor(getResources().getColor(R.color.white));
                        imgRadiant.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                        RotateAnimation rotateAnim = new RotateAnimation(0, 360, imgRadiant.getWidth() / 2, imgRadiant.getHeight() / 2);
                        rotateAnim.setDuration(500);
                        rotateAnim.setFillAfter(true);
                        imgRadiant.setAnimation(rotateAnim);
                    }
                    break;
                case R.id.llTriangle:
                    if (triangle) {
                        llTriangle.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvTriangle.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove("Triangle");
                        tvTriangle.setTextColor(getResources().getColor(R.color.colorPrimary));
                        imgTriangle.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        triangle = false;

                        llAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvAll.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove("ALL");
                        tvAll.setTextColor(getResources().getColor(R.color.colorPrimary));
                        imgAll.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        all = false;

                    } else {
                        triangle = true;
                        selectedShape.put(11, "Triangle");
                        llTriangle.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tvTriangle.setBackgroundColor(getResources().getColor(R.color.shapeText));
                        tvTriangle.setTextColor(getResources().getColor(R.color.white));
                        imgTriangle.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                        RotateAnimation rotateAnim = new RotateAnimation(0, 360, imgTriangle.getWidth() / 2, imgTriangle.getHeight() / 2);
                        rotateAnim.setDuration(500);
                        rotateAnim.setFillAfter(true);
                        imgTriangle.setAnimation(rotateAnim);
                    }
                    break;
                case R.id.llOthers:
                    if (others) {
                        llOthers.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvOthers.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove("Others");
                        tvOthers.setTextColor(getResources().getColor(R.color.colorPrimary));
                        imgOthers.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        others = false;

                        llAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvAll.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove("ALL");
                        tvAll.setTextColor(getResources().getColor(R.color.colorPrimary));
                        imgAll.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        all = false;

                    } else {
                        others = true;
                        selectedShape.put(12, "Others");
                        llOthers.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tvOthers.setBackgroundColor(getResources().getColor(R.color.shapeText));
                        tvOthers.setTextColor(getResources().getColor(R.color.white));
                        imgOthers.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                        RotateAnimation rotateAnim = new RotateAnimation(0, 360, imgOthers.getWidth() / 2, imgOthers.getHeight() / 2);
                        rotateAnim.setDuration(500);
                        rotateAnim.setFillAfter(true);
                        imgOthers.setAnimation(rotateAnim);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private class SymbolsAdp extends RecyclerView.Adapter<SymbolsAdp.RecyclerViewHolder> {
        List<String> array;
        HashMap<Integer, String> selectedArray;
        Context context;

        private SymbolsAdp(Context context, List<String> array, HashMap<Integer, String> selectedArray) {
            this.array = array;
            this.context = context;
            this.selectedArray = selectedArray;
        }

        @Override
        public SymbolsAdp.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_key_to_symbol, parent, false);
            SymbolsAdp.RecyclerViewHolder viewHolder = new SymbolsAdp.RecyclerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final SymbolsAdp.RecyclerViewHolder holder, final int position) {

            holder.tvName.setText(array.get(position));

            if (selectedArray.get(position).equalsIgnoreCase("true")) {
                holder.imgYes.setImageDrawable(getResources().getDrawable(R.drawable.ic_true));
                holder.imgNo.setImageDrawable(getResources().getDrawable(R.drawable.ic_emty_box));
            } else if (selectedArray.get(position).equalsIgnoreCase("false")) {
                holder.imgYes.setImageDrawable(getResources().getDrawable(R.drawable.ic_emty_box));
                holder.imgNo.setImageDrawable(getResources().getDrawable(R.drawable.ic_false));
            } else {
                holder.imgYes.setImageDrawable(getResources().getDrawable(R.drawable.ic_emty_box));
                holder.imgNo.setImageDrawable(getResources().getDrawable(R.drawable.ic_emty_box));
            }

            holder.imgYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.imgYes.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.ic_true).getConstantState()) {
                        holder.imgYes.setImageDrawable(getResources().getDrawable(R.drawable.ic_emty_box));
                        selectedArray.put(position, "");
                    } else {
                        holder.imgYes.setImageDrawable(getResources().getDrawable(R.drawable.ic_true));
                        holder.imgNo.setImageDrawable(getResources().getDrawable(R.drawable.ic_emty_box));
                        selectedArray.put(position, "true");
                    }
                }
            });

            holder.imgNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.imgNo.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.ic_false).getConstantState()) {
                        holder.imgNo.setImageDrawable(getResources().getDrawable(R.drawable.ic_emty_box));
                        selectedArray.put(position, "");
                    } else {
                        holder.imgNo.setImageDrawable(getResources().getDrawable(R.drawable.ic_false));
                        holder.imgYes.setImageDrawable(getResources().getDrawable(R.drawable.ic_emty_box));
                        selectedArray.put(position, "false");
                    }
                }
            });

            if (Const.isOdd(position)) {
                holder.rlMain.setBackgroundColor(getResources().getColor(R.color.layout));
            } else {
                holder.rlMain.setBackgroundColor(getResources().getColor(R.color.white));
            }

        }

        @Override
        public int getItemCount() {
            return array.size();
        }

        private class RecyclerViewHolder extends RecyclerView.ViewHolder {
            ImageView imgYes, imgNo;
            TextView tvName;
            RelativeLayout rlMain;

            private RecyclerViewHolder(View v) {
                super(v);
                imgNo = v.findViewById(R.id.imgNo);
                tvName = v.findViewById(R.id.tvName);
                imgYes = v.findViewById(R.id.imgYes);
                rlMain = v.findViewById(R.id.rlMain);
            }
        }

    }

    private class ShapeAdp extends RecyclerView.Adapter<ShapeAdp.RecyclerViewHolder> {
        String[] name;
        TypedArray image;
        Context context;

        private ShapeAdp(Context context, String[] name, TypedArray image) {
            this.name = name;
            this.image = image;
            this.context = context;
        }

        @Override
        public ShapeAdp.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_shape_two, parent, false);
            ShapeAdp.RecyclerViewHolder viewHolder = new ShapeAdp.RecyclerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ShapeAdp.RecyclerViewHolder holder, final int position) {
            holder.tvName.setText(name[position]);
            holder.imgIcon.setImageResource(image.getResourceId(position, -1));

            try {
                if (selectedShape.containsKey(position)) {
                    holder.llClick.setBackgroundDrawable(getResources().getDrawable(R.drawable.select_item));
                    holder.tvName.setTextColor(getResources().getColor(R.color.white));
                    holder.imgIcon.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
                } else {
                    holder.llClick.setBackgroundDrawable(getResources().getDrawable(R.drawable.deselect_item));
                    holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                    holder.imgIcon.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                }
            } catch (Exception e) {
                holder.llClick.setBackgroundDrawable(getResources().getDrawable(R.drawable.deselect_item));
                holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                holder.imgIcon.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
            }

            holder.llClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedShape.containsKey(position)) {
                        holder.llClick.setBackgroundDrawable(getResources().getDrawable(R.drawable.deselect_item));
                        selectedShape.remove(position);
                        holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                        holder.imgIcon.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                    } else {
                        holder.llClick.setBackgroundDrawable(getResources().getDrawable(R.drawable.select_item));
                        selectedShape.put(position, name[position]);
                        holder.tvName.setTextColor(getResources().getColor(R.color.white));
                        holder.imgIcon.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
                        RotateAnimation rotateAnim = new RotateAnimation(0, 360, holder.imgIcon.getWidth() / 2, holder.imgIcon.getHeight() / 2);
                        rotateAnim.setDuration(500);
                        rotateAnim.setFillAfter(true);
                        holder.imgIcon.startAnimation(rotateAnim);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return name.length;
        }

        private class RecyclerViewHolder extends RecyclerView.ViewHolder {
            ImageView imgIcon;
            TextView tvName;
            LinearLayout llClick;

            private RecyclerViewHolder(View v) {
                super(v);
                imgIcon = v.findViewById(R.id.imgIcon);
                tvName = v.findViewById(R.id.tvName);
                llClick = v.findViewById(R.id.llClick);
            }
        }

    }

    private class LabAdp extends RecyclerView.Adapter<LabAdp.RecyclerViewHolder> {
        String[] arraylist;
        Context context;
        int pL, pT, pR, pB;

        private LabAdp(Context context, String[] arraylist) {
            this.arraylist = arraylist;
            this.context = context;
        }

        @Override
        public LabAdp.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_name_two, parent, false);
            LabAdp.RecyclerViewHolder viewHolder = new LabAdp.RecyclerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final LabAdp.RecyclerViewHolder holder, final int position) {
            holder.tvName.setText(arraylist[position]);
            pL = holder.tvName.getPaddingLeft();
            pT = holder.tvName.getPaddingTop();
            pR = holder.tvName.getPaddingRight();
            pB = holder.tvName.getPaddingBottom();

            try {
                if (selectedLab.containsKey(position)) {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.select_item));
                    holder.tvName.setTextColor(getResources().getColor(R.color.white));
                } else {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.deselect_item));
                    holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            } catch (Exception e) {
                holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.deselect_item));
                holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
            }

            holder.tvName.setPadding(pL, pT, pR, pB);

            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedLab.containsKey(position)) {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.deselect_item));
                        selectedLab.remove(position);
                        holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.select_item));
                        selectedLab.put(position, arraylist[position]);
                        holder.tvName.setTextColor(getResources().getColor(R.color.white));
                    }
                    holder.tvName.setPadding(pL, pT, pR, pB);
                }
            });
        }

        @Override
        public int getItemCount() {
            return arraylist.length;
        }

        private class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView tvName;

            private RecyclerViewHolder(View v) {
                super(v);
                tvName = v.findViewById(R.id.tvName);
            }
        }

    }

    private class ColorAdp extends RecyclerView.Adapter<ColorAdp.RecyclerViewHolder> {
        String[] arraylist;
        Context context;
        int pL, pT, pR, pB;

        private ColorAdp(Context context, String[] arraylist) {
            this.arraylist = arraylist;
            this.context = context;
        }

        @Override
        public ColorAdp.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_name_two, parent, false);
            ColorAdp.RecyclerViewHolder viewHolder = new ColorAdp.RecyclerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ColorAdp.RecyclerViewHolder holder, final int position) {
            holder.tvName.setText(arraylist[position]);
            pL = holder.tvName.getPaddingLeft();
            pT = holder.tvName.getPaddingTop();
            pR = holder.tvName.getPaddingRight();
            pB = holder.tvName.getPaddingBottom();

            try {
                if (selectedColor.containsKey(position)) {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.select_item));
                    holder.tvName.setTextColor(getResources().getColor(R.color.white));
                } else {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.deselect_item));
                    holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            } catch (Exception e) {
                holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.deselect_item));
                holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
            }

            holder.tvName.setPadding(pL, pT, pR, pB);

            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedColor.containsKey(position)) {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.deselect_item));
                        selectedColor.remove(position);
                        holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.select_item));
                        selectedColor.put(position, arraylist[position]);
                        holder.tvName.setTextColor(getResources().getColor(R.color.white));
                    }
                    holder.tvName.setPadding(pL, pT, pR, pB);
                }
            });
        }

        @Override
        public int getItemCount() {
            return arraylist.length;
        }

        private class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView tvName;

            private RecyclerViewHolder(View v) {
                super(v);
                tvName = v.findViewById(R.id.tvName);
            }
        }

    }

    private class ClarityAdp extends RecyclerView.Adapter<ClarityAdp.RecyclerViewHolder> {
        String[] arraylist;
        Context context;
        int pL, pT, pR, pB;

        private ClarityAdp(Context context, String[] arraylist) {
            this.arraylist = arraylist;
            this.context = context;
        }

        @Override
        public ClarityAdp.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_name_two, parent, false);
            ClarityAdp.RecyclerViewHolder viewHolder = new ClarityAdp.RecyclerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ClarityAdp.RecyclerViewHolder holder, final int position) {
            holder.tvName.setText(arraylist[position]);
            pL = holder.tvName.getPaddingLeft();
            pT = holder.tvName.getPaddingTop();
            pR = holder.tvName.getPaddingRight();
            pB = holder.tvName.getPaddingBottom();

            try {
                if (selectedClarity.containsKey(position)) {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.select_item));
                    holder.tvName.setTextColor(getResources().getColor(R.color.white));
                } else {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.deselect_item));
                    holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            } catch (Exception e) {
                holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.deselect_item));
                holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
            }

            holder.tvName.setPadding(pL, pT, pR, pB);

            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedClarity.containsKey(position)) {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.deselect_item));
                        selectedClarity.remove(position);
                        holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.select_item));
                        selectedClarity.put(position, arraylist[position]);
                        holder.tvName.setTextColor(getResources().getColor(R.color.white));
                    }
                    holder.tvName.setPadding(pL, pT, pR, pB);
                }
            });
        }

        @Override
        public int getItemCount() {
            return arraylist.length;
        }

        private class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView tvName;

            private RecyclerViewHolder(View v) {
                super(v);
                tvName = v.findViewById(R.id.tvName);
            }
        }

    }

    private class CutAdp extends RecyclerView.Adapter<CutAdp.RecyclerViewHolder> {
        String[] arraylist;
        Context context;
        int pL, pT, pR, pB;

        private CutAdp(Context context, String[] arraylist) {
            this.arraylist = arraylist;
            this.context = context;
        }

        @Override
        public CutAdp.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_name_two, parent, false);
            CutAdp.RecyclerViewHolder viewHolder = new CutAdp.RecyclerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final CutAdp.RecyclerViewHolder holder, final int position) {
            holder.tvName.setText(arraylist[position]);
            pL = holder.tvName.getPaddingLeft();
            pT = holder.tvName.getPaddingTop();
            pR = holder.tvName.getPaddingRight();
            pB = holder.tvName.getPaddingBottom();

            try {
                if (selectedCut.containsKey(position)) {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.select_item));
                    holder.tvName.setTextColor(getResources().getColor(R.color.white));
                } else {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.deselect_item));
                    holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            } catch (Exception e) {
                holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.deselect_item));
                holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
            }

            holder.tvName.setPadding(pL, pT, pR, pB);

            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedCut.containsKey(position)) {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.deselect_item));
                        selectedCut.remove(position);
                        holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                        if (position == 0) {
                            selectedPolish.remove(position);
                            selectedSymmetry.remove(position);
                            polishAdp.notifyDataSetChanged();
                            symmetryAdp.notifyDataSetChanged();
                        }
                    } else {
                        if (position == 0) {
                            selectedPolish.put(position, "EX");
                            selectedSymmetry.put(position, "EX");
                            polishAdp.notifyDataSetChanged();
                            symmetryAdp.notifyDataSetChanged();
                        }
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.select_item));
                        selectedCut.put(position, arraylist[position]);
                        holder.tvName.setTextColor(getResources().getColor(R.color.white));
                    }
                    holder.tvName.setPadding(pL, pT, pR, pB);
                }
            });
        }

        @Override
        public int getItemCount() {
            return arraylist.length;
        }

        private class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView tvName;

            private RecyclerViewHolder(View v) {
                super(v);
                tvName = v.findViewById(R.id.tvName);
            }
        }

    }

    private class PolishAdp extends RecyclerView.Adapter<PolishAdp.RecyclerViewHolder> {
        String[] arraylist;
        Context context;
        int pL, pT, pR, pB;

        private PolishAdp(Context context, String[] arraylist) {
            this.arraylist = arraylist;
            this.context = context;
        }

        @Override
        public PolishAdp.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_name_two, parent, false);
            PolishAdp.RecyclerViewHolder viewHolder = new PolishAdp.RecyclerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final PolishAdp.RecyclerViewHolder holder, final int position) {
            holder.tvName.setText(arraylist[position]);
            pL = holder.tvName.getPaddingLeft();
            pT = holder.tvName.getPaddingTop();
            pR = holder.tvName.getPaddingRight();
            pB = holder.tvName.getPaddingBottom();

            try {
                if (selectedPolish.containsKey(position)) {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.select_item));
                    holder.tvName.setTextColor(getResources().getColor(R.color.white));
                } else {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.deselect_item));
                    holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            } catch (Exception e) {
                holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.deselect_item));
                holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
            }

            holder.tvName.setPadding(pL, pT, pR, pB);

            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position != 0 || !selectedCut.containsKey(position)) {
                        if (selectedPolish.containsKey(position)) {
                            holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.deselect_item));
                            selectedPolish.remove(position);
                            holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                        } else {
                            holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.select_item));
                            selectedPolish.put(position, arraylist[position]);
                            holder.tvName.setTextColor(getResources().getColor(R.color.white));
                        }
                        holder.tvName.setPadding(pL, pT, pR, pB);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return arraylist.length;
        }

        private class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView tvName;

            private RecyclerViewHolder(View v) {
                super(v);
                tvName = v.findViewById(R.id.tvName);
            }
        }

    }

    private class SymmetryAdp extends RecyclerView.Adapter<SymmetryAdp.RecyclerViewHolder> {
        String[] arraylist;
        Context context;
        int pL, pT, pR, pB;

        private SymmetryAdp(Context context, String[] arraylist) {
            this.arraylist = arraylist;
            this.context = context;
        }

        @Override
        public SymmetryAdp.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_name_two, parent, false);
            SymmetryAdp.RecyclerViewHolder viewHolder = new SymmetryAdp.RecyclerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final SymmetryAdp.RecyclerViewHolder holder, final int position) {
            holder.tvName.setText(arraylist[position]);
            pL = holder.tvName.getPaddingLeft();
            pT = holder.tvName.getPaddingTop();
            pR = holder.tvName.getPaddingRight();
            pB = holder.tvName.getPaddingBottom();

            try {
                if (selectedSymmetry.containsKey(position)) {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.select_item));
                    holder.tvName.setTextColor(getResources().getColor(R.color.white));
                } else {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.deselect_item));
                    holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            } catch (Exception e) {
                holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.deselect_item));
                holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
            }

            holder.tvName.setPadding(pL, pT, pR, pB);

            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position != 0 || !selectedCut.containsKey(position)) {
                        if (selectedSymmetry.containsKey(position)) {
                            holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.deselect_item));
                            selectedSymmetry.remove(position);
                            holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                        } else {
                            holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.select_item));
                            selectedSymmetry.put(position, arraylist[position]);
                            holder.tvName.setTextColor(getResources().getColor(R.color.white));
                        }
                        holder.tvName.setPadding(pL, pT, pR, pB);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return arraylist.length;
        }

        private class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView tvName;

            private RecyclerViewHolder(View v) {
                super(v);
                tvName = v.findViewById(R.id.tvName);
            }
        }

    }

    private class FiAdp extends RecyclerView.Adapter<FiAdp.RecyclerViewHolder> {
        String[] arraylist;
        Context context;
        int pL, pT, pR, pB;

        private FiAdp(Context context, String[] arraylist) {
            this.arraylist = arraylist;
            this.context = context;
        }

        @Override
        public FiAdp.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_name_two, parent, false);
            FiAdp.RecyclerViewHolder viewHolder = new FiAdp.RecyclerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final FiAdp.RecyclerViewHolder holder, final int position) {
            holder.tvName.setText(arraylist[position]);
            pL = holder.tvName.getPaddingLeft();
            pT = holder.tvName.getPaddingTop();
            pR = holder.tvName.getPaddingRight();
            pB = holder.tvName.getPaddingBottom();

            try {
                if (selectedFi.containsKey(position)) {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.select_item));
                    holder.tvName.setTextColor(getResources().getColor(R.color.white));
                } else {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.deselect_item));
                    holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            } catch (Exception e) {
                holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.deselect_item));
                holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
            }

            holder.tvName.setPadding(pL, pT, pR, pB);

            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedFi.containsKey(position)) {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.deselect_item));
                        selectedFi.remove(position);
                        holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.select_item));
                        selectedFi.put(position, arraylist[position]);
                        holder.tvName.setTextColor(getResources().getColor(R.color.white));
                    }
                    holder.tvName.setPadding(pL, pT, pR, pB);
                }
            });
        }

        @Override
        public int getItemCount() {
            return arraylist.length;
        }

        private class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView tvName;

            private RecyclerViewHolder(View v) {
                super(v);
                tvName = v.findViewById(R.id.tvName);
            }
        }

    }

    private class BgmAdp extends RecyclerView.Adapter<BgmAdp.RecyclerViewHolder> {
        String[] arraylist;
        Context context;
        int pL, pT, pR, pB;

        private BgmAdp(Context context, String[] arraylist) {
            this.arraylist = arraylist;
            this.context = context;
        }

        @Override
        public BgmAdp.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_location, parent, false);
            BgmAdp.RecyclerViewHolder viewHolder = new BgmAdp.RecyclerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final BgmAdp.RecyclerViewHolder holder, final int position) {
            holder.tvName.setText(arraylist[position]);
            pL = holder.tvName.getPaddingLeft();
            pT = holder.tvName.getPaddingTop();
            pR = holder.tvName.getPaddingRight();
            pB = holder.tvName.getPaddingBottom();

            try {
                if (selectedBgm.containsKey(position)) {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_card));
                    holder.tvName.setTextColor(getResources().getColor(R.color.white));
                } else {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.card_shape));
                    holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            } catch (Exception e) {
                holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.card_shape));
                holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
            }

            holder.tvName.setPadding(pL, pT, pR, pB);

            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedBgm.containsKey(position)) {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.card_shape));
                        selectedBgm.remove(position);
                        holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_card));
                        selectedBgm.put(position, arraylist[position]);
                        holder.tvName.setTextColor(getResources().getColor(R.color.white));
                    }
                    holder.tvName.setPadding(pL, pT, pR, pB);
                }
            });
        }

        @Override
        public int getItemCount() {
            return arraylist.length;
        }

        private class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView tvName;

            private RecyclerViewHolder(View v) {
                super(v);
                tvName = v.findViewById(R.id.tvName);
            }
        }

    }

    private class SizeAdp extends RecyclerView.Adapter<SizeAdp.RecyclerViewHolder> {
        String[] arraylist;
        Context context;
        int pL, pT, pR, pB;

        private SizeAdp(Context context, String[] arraylist) {
            this.arraylist = arraylist;
            this.context = context;
        }

        @Override
        public SizeAdp.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_size, parent, false);
            SizeAdp.RecyclerViewHolder viewHolder = new SizeAdp.RecyclerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
            holder.tvName.setText(arraylist[position]);
            pL = holder.tvName.getPaddingLeft();
            pT = holder.tvName.getPaddingTop();
            pR = holder.tvName.getPaddingRight();
            pB = holder.tvName.getPaddingBottom();

            try {
                if (selectedSize.containsKey(position)) {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.select_item));
                    holder.tvName.setTextColor(getResources().getColor(R.color.white));
                } else {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.deselect_item));
                    holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            } catch (Exception e) {
                holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.deselect_item));
                holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
            }

            holder.tvName.setPadding(pL, pT, pR, pB);

            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedSize.containsKey(position)) {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.deselect_item));
                        selectedSize.remove(position);
                        holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.select_item));
                        selectedSize.put(position, arraylist[position]);
                        holder.tvName.setTextColor(getResources().getColor(R.color.white));
                    }
                    holder.tvName.setPadding(pL, pT, pR, pB);
                    if (selectedSize.size() == 0) {
                        hsSize.setVisibility(View.GONE);
                        llSizeFromTo.setVisibility(View.VISIBLE);
                    } else {
                        hsSize.setVisibility(View.VISIBLE);
                        llSizeFromTo.setVisibility(View.GONE);
                        tvSelectedSize.setText(hmToArray(selectedSize).toString().replace("[", "").replace("]", ""));
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return arraylist.length;
        }

        private class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView tvName;

            private RecyclerViewHolder(View v) {
                super(v);
                tvName = v.findViewById(R.id.tvName);
            }
        }

    }

    private class InclusionAdp extends RecyclerView.Adapter<InclusionAdp.RecyclerViewHolder> {
        String[] arraylist;
        Context context;
        int pL, pT, pR, pB;

        private InclusionAdp(Context context, String[] arraylist) {
            this.arraylist = arraylist;
            this.context = context;
        }

        @Override
        public InclusionAdp.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_name, parent, false);
            InclusionAdp.RecyclerViewHolder viewHolder = new InclusionAdp.RecyclerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final InclusionAdp.RecyclerViewHolder holder, final int position) {
            holder.tvName.setText(arraylist[position]);
            pL = holder.tvName.getPaddingLeft();
            pT = holder.tvName.getPaddingTop();
            pR = holder.tvName.getPaddingRight();
            pB = holder.tvName.getPaddingBottom();

            try {
                if (selectedInclusion.containsKey(position)) {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.select_item));
                    holder.tvName.setTextColor(getResources().getColor(R.color.white));
                } else {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.deselect_item));
                    holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            } catch (Exception e) {
                holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.deselect_item));
                holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
            }

            holder.tvName.setPadding(pL, pT, pR, pB);

            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedInclusion.containsKey(position)) {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.deselect_item));
                        selectedInclusion.remove(position);
                        holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.select_item));
                        selectedInclusion.put(position, arraylist[position]);
                        holder.tvName.setTextColor(getResources().getColor(R.color.white));
                    }
                    holder.tvName.setPadding(pL, pT, pR, pB);
                }
            });
        }

        @Override
        public int getItemCount() {
            return arraylist.length;
        }

        private class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView tvName;

            private RecyclerViewHolder(View v) {
                super(v);
                tvName = v.findViewById(R.id.tvName);
            }
        }

    }

    private class NattsAdp extends RecyclerView.Adapter<NattsAdp.RecyclerViewHolder> {
        String[] arraylist;
        Context context;
        int pL, pT, pR, pB;

        private NattsAdp(Context context, String[] arraylist) {
            this.arraylist = arraylist;
            this.context = context;
        }

        @Override
        public NattsAdp.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_name, parent, false);
            NattsAdp.RecyclerViewHolder viewHolder = new NattsAdp.RecyclerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final NattsAdp.RecyclerViewHolder holder, final int position) {
            holder.tvName.setText(arraylist[position]);
            pL = holder.tvName.getPaddingLeft();
            pT = holder.tvName.getPaddingTop();
            pR = holder.tvName.getPaddingRight();
            pB = holder.tvName.getPaddingBottom();

            try {
                if (selectedNatts.containsKey(position)) {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.select_item));
                    holder.tvName.setTextColor(getResources().getColor(R.color.white));
                } else {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.deselect_item));
                    holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            } catch (Exception e) {
                holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.deselect_item));
                holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
            }

            holder.tvName.setPadding(pL, pT, pR, pB);

            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedNatts.containsKey(position)) {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.deselect_item));
                        selectedNatts.remove(position);
                        holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.select_item));
                        selectedNatts.put(position, arraylist[position]);
                        holder.tvName.setTextColor(getResources().getColor(R.color.white));
                    }
                    holder.tvName.setPadding(pL, pT, pR, pB);
                }
            });
        }

        @Override
        public int getItemCount() {
            return arraylist.length;
        }

        private class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView tvName;

            private RecyclerViewHolder(View v) {
                super(v);
                tvName = v.findViewById(R.id.tvName);
            }
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

}
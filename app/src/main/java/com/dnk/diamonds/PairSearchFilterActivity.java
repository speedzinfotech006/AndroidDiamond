package com.dnk.shairugems;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dnk.shairugems.Utils.Const;
import com.dnk.shairugems.Utils.Pref;
import com.dnk.shairugems.Volly.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PairSearchFilterActivity extends BaseDrawerActivity {

    Context context = PairSearchFilterActivity.this;
    TextView tvSelectedSize, tv3EX, tvX, tvVG, tv3VG;
    RadioGroup rgSize;
    RadioButton rbGeneral, rbSpecific;
    EditText etStoneOrCert, etSymbol, etFromCts, etToCts, etDisF, etDisT, etPcuF, etPcuT, etTauF, etTauT, etLengthF, etLengthT, etWidthF, etWidthT, etDepthF, etDepthT, etDepthPF, etDepthPT, etTablePF, etTablePT, etCrAngPF, etCrAngPT, etCrHtPF, etCrHtPT, etPavAngPF, etPavAngPT, etPavHtPF, etPavHtPT;
    ImageView imgMenu, imgEmail, imgSizeBtn, imgVideo, imgImage, imgOMenu;
    RecyclerView rvLab, rvColor, rvClarity, rvCut, rvPolish, rvSymmetry, rvFi, rvBgm, rvSize, rvInclusion, rvNatts, rvLocation, rvTableBlack, rvCrownBlack, rvTableWhite, rvCrownWhite, rvTableOpen, rvCrownOpen, rvPavOpen, rvGirdleOpen;
    HorizontalScrollView hsSize;
    Boolean img = true, vid = true;
    RelativeLayout rlMain, rlImage, rlVideo;
    LinearLayout llSizeFromTo, llSearch, llReset;
//    ShapeAdp shapeAdp;
    LabAdp labAdp;
    ColorAdp colorAdp;
    ClarityAdp clarityAdp;
    CutAdp cutAdp;
    PolishAdp polishAdp;
    SymmetryAdp symmetryAdp;
    FiAdp fiAdp;
    BgmAdp bgmAdp;
    SizeAdp sizeAdp;
    LocationAdp locAdp;
    CrownWhiteAdp cwAdp;
    TableWhiteAdp twAdp;
    CrownBlackAdp cbAdp;
    TableBlackAdp tbAdp;
    TableOpenAdp toAdp;
    CrownOpenAdp coAdp;
    PavOpenAdp poAdp;
    GirdleOpenAdp goAdp;
    NestedScrollView nsvHeader;
    List<String> arraySymbol = new ArrayList<>();
    List<String> arrayLocation = new ArrayList<>();
    List<String> arrayPointerSize = new ArrayList<>();
    List<String> arrayTO = new ArrayList<>();
    List<String> arrayCO = new ArrayList<>();
    List<String> arrayPO = new ArrayList<>();
    List<String> arrayGO = new ArrayList<>();
    List<String> arraySpecificSize = new ArrayList<>();
    List<String> arraySymbolTrue = new ArrayList<>();
    List<String> arraySymbolFalse = new ArrayList<>();
    HashMap<Integer, String> selectedSymbol = new HashMap<>();
    ArrayList<String> selectedShape = new ArrayList<String>();
    //   HashMap<Integer, String> selectedShape = new HashMap<>();
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
    HashMap<Integer, String> selectedLoc = new HashMap<>();
    HashMap<Integer, String> selectedTB = new HashMap<>();
    HashMap<Integer, String> selectedCB = new HashMap<>();
    HashMap<Integer, String> selectedTW = new HashMap<>();
    HashMap<Integer, String> selectedCW = new HashMap<>();
    HashMap<Integer, String> selectedTO = new HashMap<>();
    HashMap<Integer, String> selectedCO = new HashMap<>();
    HashMap<Integer, String> selectedPO = new HashMap<>();
    HashMap<Integer, String> selectedGO = new HashMap<>();

    LinearLayout llAll, llRound, llCushion, llEmerald, llOval, llHeart, llPear, llMarquise, llPrincess, llAsscher, llRadiant, llTriangle, llOthers;
    TextView tvAll, tvRound, tvCushion, tvEmerald, tvOval, tvHeart, tvPear, tvMarquise, tvPrincess, tvAsscher, tvRadiant, tvTriangle, tvOthers;
    ImageView imgAll, imgRound, imgCushion, imgEmerald, imgOval, imgHeart, imgPear, imgMarquise, imgPrincess, imgAsscher, imgRadiant, imgTriangle, imgOthers;
    boolean all = false, round = false, cushion = false, emerald = false, oval = false, heart = false, pear = false, marquise = false, princess = false, asscher = false, radiant = false, triangle = false, others = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_pair_search_filter, frameLayout);

        initView();

        etSymbol.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etSymbol.performClick();
                }
            }
        });

        etFromCts.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etFromCts.performClick();
                }
            }
        });

        etToCts.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etToCts.performClick();
                }
            }
        });

        rgSize.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                etFromCts.setText("");
                etToCts.setText("");
                tvSelectedSize.setText("");
                selectedSize = new HashMap<>();
                arraySpecificSize = new ArrayList<>();
                rvSize.setAdapter(null);
                switch (checkedId) {
                    case R.id.rbSpecific:
                        hsSize.setVisibility(View.GONE);
                        llSizeFromTo.setVisibility(View.VISIBLE);
                        imgSizeBtn.setVisibility(View.VISIBLE);
                        rvSize.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                        break;
                    case R.id.rbGeneral:
                        hsSize.setVisibility(View.VISIBLE);
                        llSizeFromTo.setVisibility(View.GONE);
                        rvSize.setVisibility(View.VISIBLE);
                        imgSizeBtn.setVisibility(View.GONE);
                        rvSize.setLayoutManager(new GridLayoutManager(context, 4));
                        sizeAdp = new SizeAdp(context, arrayPointerSize, true);
                        rvSize.setAdapter(sizeAdp);
                        break;
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

        locAdp = new LocationAdp(context, getResources().getStringArray(R.array.location));
        rvLocation.setAdapter(locAdp);

        tbAdp = new TableBlackAdp(context, getResources().getStringArray(R.array.table_black));
        rvTableBlack.setAdapter(tbAdp);

        cbAdp = new CrownBlackAdp(context, getResources().getStringArray(R.array.crown_black));
        rvCrownBlack.setAdapter(cbAdp);

        twAdp = new TableWhiteAdp(context, getResources().getStringArray(R.array.table_white));
        rvTableWhite.setAdapter(twAdp);

        cwAdp = new CrownWhiteAdp(context, getResources().getStringArray(R.array.crown_white));
        rvCrownWhite.setAdapter(cwAdp);

        getKeyToSymbols();
    }

    private void initView() {
        nsvHeader = findViewById(R.id.nsvHeader);
        imgMenu = findViewById(R.id.imgMenu);
        imgEmail = findViewById(R.id.imgEmail);
        rlVideo = findViewById(R.id.rlVideo);
        rlImage = findViewById(R.id.rlImage);
        etStoneOrCert = findViewById(R.id.etStoneOrCert);
        imgOMenu = findViewById(R.id.imgOMenu);
        tvSelectedSize = findViewById(R.id.tvSelectedSize);
        tv3EX = findViewById(R.id.tv3EX);
        tvX = findViewById(R.id.tvX);
        tvVG = findViewById(R.id.tvVG);
        tv3VG = findViewById(R.id.tv3VG);
        imgVideo = findViewById(R.id.imgVideo);
        rbSpecific = findViewById(R.id.rbSpecific);
        rbGeneral = findViewById(R.id.rbGeneral);
        rgSize = findViewById(R.id.rgSize);
        imgImage = findViewById(R.id.imgImage);
        llSearch = findViewById(R.id.llSearch);
        etSymbol = findViewById(R.id.etSymbol);
        etTablePF = findViewById(R.id.etTablePF);
        etTablePT = findViewById(R.id.etTablePT);
        etCrAngPF = findViewById(R.id.etCrAngPF);
        etCrAngPT = findViewById(R.id.etCrAngPT);
        etCrHtPF = findViewById(R.id.etCrHtPF);
        etCrHtPT = findViewById(R.id.etCrHtPT);
        etPavAngPF = findViewById(R.id.etPavAngPF);
        etPavAngPT = findViewById(R.id.etPavAngPT);
        etPavHtPF = findViewById(R.id.etPavHtPF);
        etPavHtPT = findViewById(R.id.etPavHtPT);
        etDepthPF = findViewById(R.id.etDepthPF);
        etDepthPT = findViewById(R.id.etDepthPT);
        etDepthF = findViewById(R.id.etDepthF);
        etDepthT = findViewById(R.id.etDepthT);
        etWidthF = findViewById(R.id.etWidthF);
        etWidthT = findViewById(R.id.etWidthT);
        etLengthF = findViewById(R.id.etLengthF);
        etLengthT = findViewById(R.id.etLengthT);
        etTauF = findViewById(R.id.etTauF);
        etTauT = findViewById(R.id.etTauT);
        etFromCts = findViewById(R.id.etFromCts);
        etToCts = findViewById(R.id.etToCts);
        etPcuF = findViewById(R.id.etPcuF);
        etPcuT = findViewById(R.id.etPcuT);
        etDisF = findViewById(R.id.etDisF);
        etDisT = findViewById(R.id.etDisT);
        rvLocation = findViewById(R.id.rvLocation);
        hsSize = findViewById(R.id.hsSize);
        rlMain = findViewById(R.id.rlMain);
        llSizeFromTo = findViewById(R.id.llSizeFromTo);
        imgSizeBtn = findViewById(R.id.imgSizeBtn);
        llReset = findViewById(R.id.llReset);
//        rvShape = findViewById(R.id.rvShape);
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
        rvCrownWhite = findViewById(R.id.rvCrownWhite);
        rvTableWhite = findViewById(R.id.rvTableWhite);
        rvCrownBlack = findViewById(R.id.rvCrownBlack);
        rvTableBlack = findViewById(R.id.rvTableBlack);
        rvTableOpen = findViewById(R.id.rvTableOpen);
        rvCrownOpen = findViewById(R.id.rvCrownOpen);
        rvPavOpen = findViewById(R.id.rvPavOpen);
        rvGirdleOpen = findViewById(R.id.rvGirdleOpen);

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

        rvTableBlack.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvCrownBlack.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvTableWhite.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvCrownWhite.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvTableOpen.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvCrownOpen.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvPavOpen.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvGirdleOpen.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
//        rvShape.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvLab.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvColor.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvClarity.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvCut.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvPolish.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvSymmetry.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvFi.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvBgm.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvSize.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvInclusion.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvNatts.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvLocation.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        hsSize.setBackgroundDrawable(Const.setBackgoundBorder(2, 10, Color.TRANSPARENT, Color.BLACK));
        llSearch.setBackgroundDrawable(Const.setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));
        llReset.setBackgroundDrawable(Const.setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));

        tv3EX.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvX.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvVG.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tv3VG.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));

        tv3EX.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvX.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvVG.setTextColor(getResources().getColor(R.color.colorPrimary));
        tv3VG.setTextColor(getResources().getColor(R.color.colorPrimary));

        imgMenu.setOnClickListener(clickListener);
        imgEmail.setOnClickListener(clickListener);
        etSymbol.setOnClickListener(clickListener);
        llReset.setOnClickListener(clickListener);
        imgSizeBtn.setOnClickListener(clickListener);
        llSearch.setOnClickListener(clickListener);
        rlVideo.setOnClickListener(clickListener);
        rlImage.setOnClickListener(clickListener);
        imgOMenu.setOnClickListener(clickListener);

        etFromCts.setOnClickListener(clickListener);
        etToCts.setOnClickListener(clickListener);

        tv3EX.setOnClickListener(clickListener);
        tvX.setOnClickListener(clickListener);
        tvVG.setOnClickListener(clickListener);
        tv3VG.setOnClickListener(clickListener);
    }

    private void clearSelected() {
        selectedSymbol = new HashMap<>();
        for (int i = 0; i < arraySymbol.size(); i++) {
            selectedSymbol.put(i, "");
        }
        rbSpecific.setChecked(true);
        arraySpecificSize = new ArrayList<>();
        arraySymbolFalse = new ArrayList<>();
        arraySymbolTrue = new ArrayList<>();
        selectedShape = new ArrayList<>();
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
        selectedLoc = new HashMap<>();
        selectedTB = new HashMap<>();
        selectedCB = new HashMap<>();
        selectedTW = new HashMap<>();
        selectedCW = new HashMap<>();
        selectedTO = new HashMap<>();
        selectedCO = new HashMap<>();
        selectedPO = new HashMap<>();
        selectedGO = new HashMap<>();
        toAdp.notifyDataSetChanged();
        coAdp.notifyDataSetChanged();
        poAdp.notifyDataSetChanged();
        goAdp.notifyDataSetChanged();
        cwAdp.notifyDataSetChanged();
        twAdp.notifyDataSetChanged();
        cbAdp.notifyDataSetChanged();
        tbAdp.notifyDataSetChanged();
 //       shapeAdp.notifyDataSetChanged();
        labAdp.notifyDataSetChanged();
        colorAdp.notifyDataSetChanged();
        clarityAdp.notifyDataSetChanged();
        cutAdp.notifyDataSetChanged();
        polishAdp.notifyDataSetChanged();
        symmetryAdp.notifyDataSetChanged();
        fiAdp.notifyDataSetChanged();
        bgmAdp.notifyDataSetChanged();
        locAdp.notifyDataSetChanged();
        tvSelectedSize.setText("");
        if (sizeAdp != null) {
            sizeAdp.notifyDataSetChanged();
        }
        rvSize.setAdapter(null);
        Const.resetParameters(context);
        Const.clearForm(rlMain);
        tv3EX.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tv3EX.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvX.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvX.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvVG.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvVG.setTextColor(getResources().getColor(R.color.colorPrimary));
        tv3VG.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tv3VG.setTextColor(getResources().getColor(R.color.colorPrimary));
        vid = true;
        img = true;
        all = false;
        round = false;
        cushion = false;
        emerald = false;
        oval = false;
        heart = false;
        pear = false;
        marquise = false;
        princess = false;
        asscher = false;
        radiant = false;
        triangle = false;
        others = false;
        imgVideo.setImageDrawable(getResources().getDrawable(R.drawable.ic_un_v));
        rlVideo.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        imgVideo.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
        imgImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_un_i));
        rlImage.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        imgImage.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);

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
                    if (selectedSymbol.get(i).split("'_'")[0].equalsIgnoreCase("true")) {
                        yes++;
                    } else if (selectedSymbol.get(i).split("'_'")[0].equalsIgnoreCase("false")) {
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
                    selectedSymbol.put(i, "");
                }
                SymbolsAdp adp = new SymbolsAdp(context, arraySymbol, selectedSymbol);
                rv.setAdapter(adp);
                adp.notifyDataSetChanged();
            }
        });

        dialog.show();

    }

    private void getKeyToSymbols() {
        Const.showProgress(context);
        arraySymbol = new ArrayList<>();
        selectedSymbol = new HashMap<>();
        final Map<String, String> map = new HashMap<>();
        Const.callPostApi(context, "Stock/GetKeyToSymbol", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
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
                                arraySymbol.add(hm.get("ssymbol"));
                                selectedSymbol.put(i, "");
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getPointerList();
            }

            @Override
            public void onFailerResponse(String error) {
                Const.dismissProgress();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        getKeyToSymbols();
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    private void getPointerList() {
        arrayPointerSize = new ArrayList<>();
        selectedSize = new HashMap<>();
        arrayLocation = new ArrayList<>();
        arrayTO = new ArrayList<>();
        arrayCO = new ArrayList<>();
        arrayPO = new ArrayList<>();
        arrayGO = new ArrayList<>();
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
                                if (object1.optString("ListType").equalsIgnoreCase("POINTER")) {
                                    arrayPointerSize.add(object1.optString("Value"));
                                }
                                if (object1.optString("ListType").equalsIgnoreCase("TABLEOPEN")) {
                                    arrayTO.add(object1.optString("Value"));
                                }
                                if (object1.optString("ListType").equalsIgnoreCase("CROWNOPEN")) {
                                    arrayCO.add(object1.optString("Value"));
                                }
                                if (object1.optString("ListType").equalsIgnoreCase("PAVILIONOPEN")) {
                                    arrayPO.add(object1.optString("Value"));
                                }
                                if (object1.optString("ListType").equalsIgnoreCase("GIRDLEOPEN")) {
                                    arrayGO.add(object1.optString("Value"));
                                }

                                toAdp = new TableOpenAdp(context, arrayTO);
                                rvTableOpen.setAdapter(toAdp);

                                coAdp = new CrownOpenAdp(context, arrayCO);
                                rvCrownOpen.setAdapter(coAdp);

                                poAdp = new PavOpenAdp(context, arrayPO);
                                rvPavOpen.setAdapter(poAdp);

                                goAdp = new GirdleOpenAdp(context, arrayGO);
                                rvGirdleOpen.setAdapter(goAdp);
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
                        Const.showProgress(context);
                        getPointerList();
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    private void openPopupMenu() {
        Context wrapper = new ContextThemeWrapper(context, R.style.PopupMenu);
        PopupMenu popup = new PopupMenu(wrapper, imgOMenu);
        popup.getMenu().add("Call").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        popup.getMenu().add("Email");
        popup.getMenu().add("Whatsapp");
        popup.getMenu().add("Skype");
        popup.getMenu().add("WeChat");
        popup.getMenu().add("QQ");
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getTitle().toString().equalsIgnoreCase("Call")) {
                    Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+1234567890"));
                    startActivity(i);
                    overridePendingTransition(0, 0);
                } else if (item.getTitle().toString().equalsIgnoreCase("Email")) {
                    emailDialog();
                }
                return true;
            }
        });
        popup.show();
    }

    private ArrayList<String> hmToArray(HashMap<Integer, String> selectedSize) {
        ArrayList<String> arrayList = new ArrayList<>();
        Iterator iterator = selectedSize.keySet().iterator();
        while (iterator.hasNext()) {
            Integer key = (Integer) iterator.next();
            String value = (String) selectedSize.get(key);
            arrayList.add(value);
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
                    arraySymbolFalse = new ArrayList<>();
                    arraySymbolTrue = new ArrayList<>();
                    Pref.removeValue(context, Const.stone_id);
                    Pref.removeValue(context, Const.certi_no);
                    Pref.setStringValue(context, Const.shape, selectedShape.toString().replace("[", "").replace("]", "").replace(", ", ","));
                    Pref.setStringValue(context, Const.lab, new ArrayList<String>(selectedLab.values()).toString().replace("[", "").replace("]", "").replace(", ", ","));
                    Pref.setStringValue(context, Const.color, new ArrayList<String>(selectedColor.values()).toString().replace("[", "").replace("]", "").replace(", ", ","));
                    Pref.setStringValue(context, Const.clarity, new ArrayList<String>(selectedClarity.values()).toString().replace("[", "").replace("]", "").replace(", ", ","));
                    Pref.setStringValue(context, Const.cut, new ArrayList<String>(selectedCut.values()).toString().replace("[", "").replace("]", "").replace(", ", ",").replace("F","FR"));
                    Pref.setStringValue(context, Const.polish, new ArrayList<String>(selectedPolish.values()).toString().replace("[", "").replace("]", "").replace(", ", ","));
                    Pref.setStringValue(context, Const.symmetry, new ArrayList<String>(selectedSymmetry.values()).toString().replace("[", "").replace("]", "").replace(", ", ","));
                    Pref.setStringValue(context, Const.fi, new ArrayList<String>(selectedFi.values()).toString().replace("[", "").replace("]", "").replace(", ", ","));
                    Pref.setStringValue(context, Const.bgm, new ArrayList<String>(selectedBgm.values()).toString().replace("[", "").replace("]", "").replace(", ", ","));
                    Pref.setStringValue(context, Const.location, new ArrayList<String>(selectedLoc.values()).toString().replace("[", "").replace("]", "").replace(", ", ","));
                    Pref.setStringValue(context, Const.inclusion, new ArrayList<String>(selectedTW.values()).toString().replace("[", "").replace("]", "").replace(", ", ","));
                    Pref.setStringValue(context, Const.natts, new ArrayList<String>(selectedTB.values()).toString().replace("[", "").replace("]", "").replace(", ", ","));
                    Pref.setStringValue(context, Const.crown_inclusion, new ArrayList<String>(selectedCW.values()).toString().replace("[", "").replace("]", "").replace(", ", ","));
                    Pref.setStringValue(context, Const.crown_natts, new ArrayList<String>(selectedCB.values()).toString().replace("[", "").replace("]", "").replace(", ", ","));
                    Pref.setStringValue(context, Const.table_open, new ArrayList<String>(selectedTO.values()).toString().replace("[", "").replace("]", "").replace(", ", ",").toUpperCase());
                    Pref.setStringValue(context, Const.crown_open, new ArrayList<String>(selectedCO.values()).toString().replace("[", "").replace("]", "").replace(", ", ",").toUpperCase());
                    Pref.setStringValue(context, Const.pav_open, new ArrayList<String>(selectedPO.values()).toString().replace("[", "").replace("]", "").replace(", ", ",").toUpperCase());
                    Pref.setStringValue(context, Const.girdle_open, new ArrayList<String>(selectedGO.values()).toString().replace("[", "").replace("]", "").replace(", ", ",").toUpperCase());
                    Pref.setStringValue(context, Const.image, !img ? "1" : "");
                    Pref.setStringValue(context, Const.video, !vid ? "1" : "");
                    Pref.setStringValue(context, Const.pointer, tvSelectedSize.getText().toString());
                    Pref.setStringValue(context, Const.from_disc, etDisF.getText().toString());
                    Pref.setStringValue(context, Const.to_disc, etDisT.getText().toString());
                    Pref.setStringValue(context, Const.from_pcu, etPcuF.getText().toString());
                    Pref.setStringValue(context, Const.to_pcu, etPcuT.getText().toString());
                    Pref.setStringValue(context, Const.from_na, etTauF.getText().toString());
                    Pref.setStringValue(context, Const.to_na, etTauT.getText().toString());
                    Pref.setStringValue(context, Const.from_length, etLengthF.getText().toString());
                    Pref.setStringValue(context, Const.to_length, etLengthT.getText().toString());
                    Pref.setStringValue(context, Const.from_width, etWidthF.getText().toString());
                    Pref.setStringValue(context, Const.to_width, etWidthT.getText().toString());
                    Pref.setStringValue(context, Const.from_depth, etDepthF.getText().toString());
                    Pref.setStringValue(context, Const.to_depth, etDepthT.getText().toString());
                    Pref.setStringValue(context, Const.from_depth_p, etDepthPF.getText().toString());
                    Pref.setStringValue(context, Const.to_depth_p, etDepthPT.getText().toString());
                    Pref.setStringValue(context, Const.from_table_p, etTablePF.getText().toString());
                    Pref.setStringValue(context, Const.to_table_p, etTablePT.getText().toString());
                    Pref.setStringValue(context, Const.from_crang_p, etCrAngPF.getText().toString());
                    Pref.setStringValue(context, Const.to_crang_p, etCrAngPT.getText().toString());
                    Pref.setStringValue(context, Const.from_crht_p, etCrHtPF.getText().toString());
                    Pref.setStringValue(context, Const.to_crht_p, etCrHtPT.getText().toString());
                    Pref.setStringValue(context, Const.from_pavang_p, etPavAngPF.getText().toString());
                    Pref.setStringValue(context, Const.to_pavang_p, etPavAngPT.getText().toString());
                    Pref.setStringValue(context, Const.from_pavht_p, etPavHtPF.getText().toString());
                    Pref.setStringValue(context, Const.to_pavht_p, etPavHtPT.getText().toString());
                    Pref.setStringValue(context, Const.from_cts, etFromCts.getText().toString());
                    Pref.setStringValue(context, Const.to_cts, etToCts.getText().toString());
                    if (arraySpecificSize.size() >= 1 & !Const.isEmpty(etFromCts) & !Const.isEmpty(etToCts)) {
                        Const.showErrorDialog(context, "Can Not Find Both Criteria (Carat and Pointer) Together");
                        return;
                    } else if (arraySpecificSize.size() >= 1) {
                        Pref.setStringValue(context, Const.pointer, arraySpecificSize.toString().replace("[", "").replace("]", "").replace(", ", ","));
                    }
                    if (!Const.isEmpty(etStoneOrCert)) {
                        Pref.setStringValue(context, Const.stone_id, etStoneOrCert.getText().toString().replaceAll(" ",",").replaceAll("\n",",").replaceAll("\t",",").replaceAll("\r",",").replaceAll("  ",","));
                    }

                    for (int i = 0; i < selectedSymbol.size(); i++) {
                        if (selectedSymbol.get(i).split("'_'")[0].equalsIgnoreCase("true")) {
                            arraySymbolTrue.add(selectedSymbol.get(i).split("'_'")[1]);
                        } else if (selectedSymbol.get(i).split("'_'")[0].equalsIgnoreCase("false")) {
                            arraySymbolFalse.add(selectedSymbol.get(i).split("'_'")[1]);
                        }
                    }
                    String tr = arraySymbolTrue.toString().replace("[", "").replace("]", "").replace(", ", ",");
                    String fa = arraySymbolFalse.toString().replace("[", "").replace("]", "").replace(", ", ",");
                    Pref.setStringValue(context, Const.keytosymbol, tr + "-" + fa);

                    startActivity(new Intent(context, PairSearchResultActivity.class));
                    overridePendingTransition(0, 0);
                    break;
                case R.id.imgOMenu:
                    openPopupMenu();
                    break;
                case R.id.imgEmail:
                    emailDialog();
                    break;
                case R.id.rlImage:
                    if (img) {
                        imgImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_select_i));
                        rlImage.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        imgImage.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
                        img = false;
                    } else {
                        imgImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_un_i));
                        rlImage.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        imgImage.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        img = true;
                    }
                    break;
                case R.id.rlVideo:
                    if (vid) {
                        imgVideo.setImageDrawable(getResources().getDrawable(R.drawable.ic_select_v));
                        rlVideo.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        imgVideo.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
                        vid = false;
                    } else {
                        imgVideo.setImageDrawable(getResources().getDrawable(R.drawable.ic_un_v));
                        rlVideo.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        imgVideo.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        vid = true;
                    }
                    break;
                case R.id.etSymbol:
                    openKeyToSymbol();
                    break;
                case R.id.imgSizeBtn:
                    if (Const.isEmpty(etFromCts) | Const.isEmpty(etToCts)) {
                        Const.showErrorDialog(context, "Please enter carat");
                    } else {
                        String f = String.format("%.2f", Double.parseDouble(etFromCts.getText().toString()));
                        String t = String.format("%.2f", Double.parseDouble(etToCts.getText().toString()));
//                        if (t < f) {
//                            etFromCts.setText(etToCts.getText().toString());
//                        }
                        arraySpecificSize.add(f + "-" + t);
                        Collections.reverse(arraySpecificSize);
                        sizeAdp = new SizeAdp(context, arraySpecificSize, false);
                        rvSize.setAdapter(sizeAdp);
                        sizeAdp.notifyDataSetChanged();
                        etFromCts.setText("");
                        etToCts.setText("");
                    }
                    break;
                case R.id.etFromCts:
                    nsvHeader.scrollTo(0, 300);
                    break;
                case R.id.etToCts:
                    nsvHeader.scrollTo(0, 300);
                    break;
                case R.id.llReset:
                    clearSelected();
                    break;
                case R.id.tv3EX:
                    if (tvX.getCurrentTextColor() == getResources().getColor(R.color.white)) {
                        tvX.performClick();
                    }
                    if (tvVG.getCurrentTextColor() == getResources().getColor(R.color.white)) {
                        tvVG.performClick();
                    }
                    if (tv3VG.getCurrentTextColor() == getResources().getColor(R.color.white)) {
                        tv3VG.performClick();
                    }
                    if (tv3EX.getCurrentTextColor() == getResources().getColor(R.color.white)) {
                        selectedCut.remove(1);
                        selectedPolish.remove(0);
                        selectedSymmetry.remove(0);
                        cutAdp.notifyDataSetChanged();
                        polishAdp.notifyDataSetChanged();
                        symmetryAdp.notifyDataSetChanged();

                        tv3EX.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tv3EX.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else {
                        selectedCut.put(1, "EX");
                        selectedPolish.put(0, "EX");
                        selectedSymmetry.put(0, "EX");
                        cutAdp.notifyDataSetChanged();
                        polishAdp.notifyDataSetChanged();
                        symmetryAdp.notifyDataSetChanged();

                        tv3EX.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tv3EX.setTextColor(getResources().getColor(R.color.white));
                    }
                    break;
                case R.id.tvX:
                    if (tv3EX.getCurrentTextColor() == getResources().getColor(R.color.white)) {
                        tv3EX.performClick();
                    }
                    if (tvVG.getCurrentTextColor() == getResources().getColor(R.color.white)) {
                        tvVG.performClick();
                    }
                    if (tv3VG.getCurrentTextColor() == getResources().getColor(R.color.white)) {
                        tv3VG.performClick();
                    }
                    if (tvX.getCurrentTextColor() == getResources().getColor(R.color.white)) {
                        selectedCut.remove(0);
                        selectedCut.remove(1);
                        cutAdp.notifyDataSetChanged();

                        tvX.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvX.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else {
                        selectedCut.put(0, "3EX");
                        selectedCut.put(1, "EX");
                        cutAdp.notifyDataSetChanged();

                        tvX.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tvX.setTextColor(getResources().getColor(R.color.white));
                    }
                    break;
                case R.id.tvVG:
                    if (tv3EX.getCurrentTextColor() == getResources().getColor(R.color.white)) {
                        tv3EX.performClick();
                    }
                    if (tvX.getCurrentTextColor() == getResources().getColor(R.color.white)) {
                        tvX.performClick();
                    }
                    if (tv3VG.getCurrentTextColor() == getResources().getColor(R.color.white)) {
                        tv3VG.performClick();
                    }
                    if (tvVG.getCurrentTextColor() == getResources().getColor(R.color.white)){
                        selectedCut.remove(1);
                        selectedCut.remove(2);
                        selectedPolish.remove(0);
                        selectedPolish.remove(1);
                        selectedSymmetry.remove(0);
                        selectedSymmetry.remove(1);

                        cutAdp.notifyDataSetChanged();
                        polishAdp.notifyDataSetChanged();
                        symmetryAdp.notifyDataSetChanged();

                        tvVG.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvVG.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }else {
                        selectedCut.put(1, "EX");
                        selectedCut.put(2, "VG");
                        selectedPolish.put(0, "EX");
                        selectedPolish.put(1, "VG");
                        selectedSymmetry.put(0, "EX");
                        selectedSymmetry.put(1, "VG");
                        cutAdp.notifyDataSetChanged();
                        polishAdp.notifyDataSetChanged();
                        symmetryAdp.notifyDataSetChanged();

                        tvVG.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tvVG.setTextColor(getResources().getColor(R.color.white));
                    }
                    break;
                case R.id.tv3VG:
                    if (tv3EX.getCurrentTextColor() == getResources().getColor(R.color.white)) {
                        tv3EX.performClick();
                    }
                    if (tvVG.getCurrentTextColor() == getResources().getColor(R.color.white)) {
                        tvVG.performClick();
                    }
                    if (tvX.getCurrentTextColor() == getResources().getColor(R.color.white)) {
                        tvX.performClick();
                    }
                    if (tv3VG.getCurrentTextColor() == getResources().getColor(R.color.white)){
                        selectedCut.remove(2);
                        selectedPolish.remove(1);
                        selectedSymmetry.remove(1);
                        cutAdp.notifyDataSetChanged();
                        polishAdp.notifyDataSetChanged();
                        symmetryAdp.notifyDataSetChanged();

                        tv3VG.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tv3VG.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }else {
                        selectedCut.put(2, "VG");
                        selectedPolish.put(1, "VG");
                        selectedSymmetry.put(1, "VG");
                        cutAdp.notifyDataSetChanged();
                        polishAdp.notifyDataSetChanged();
                        symmetryAdp.notifyDataSetChanged();

                        tv3VG.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tv3VG.setTextColor(getResources().getColor(R.color.white));
                    }
                    break;
                case R.id.llAll:
                    if (all) {
                        llAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvAll.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove("ALL");
                        tvAll.setTextColor(getResources().getColor(R.color.colorPrimary));
                        imgAll.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        all = false;

                        llRound.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvRound.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove("Round");
                        tvRound.setTextColor(getResources().getColor(R.color.colorPrimary));
                        imgRound.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        round = false;

                        llCushion.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvCushion.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove("Cushion");
                        tvCushion.setTextColor(getResources().getColor(R.color.colorPrimary));
                        imgCushion.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        cushion = false;

                        llEmerald.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvEmerald.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove("Emerald");
                        tvEmerald.setTextColor(getResources().getColor(R.color.colorPrimary));
                        imgEmerald.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        emerald = false;

                        llOval.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvOval.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove("Oval");
                        tvOval.setTextColor(getResources().getColor(R.color.colorPrimary));
                        imgOval.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        oval = false;

                        llHeart.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvHeart.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove("Heart");
                        tvHeart.setTextColor(getResources().getColor(R.color.colorPrimary));
                        imgHeart.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        heart = false;

                        llPear.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvPear.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove("Pear");
                        tvPear.setTextColor(getResources().getColor(R.color.colorPrimary));
                        imgPear.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        pear = false;

                        llMarquise.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvMarquise.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove("Marquise");
                        tvMarquise.setTextColor(getResources().getColor(R.color.colorPrimary));
                        imgMarquise.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        marquise = false;

                        llPrincess.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvPrincess.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove("Princess");
                        tvPrincess.setTextColor(getResources().getColor(R.color.colorPrimary));
                        imgPrincess.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        princess = false;

                        llAsscher.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvAsscher.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove("Asscher");
                        tvAsscher.setTextColor(getResources().getColor(R.color.colorPrimary));
                        imgAsscher.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        asscher = false;

                        llRadiant.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvRadiant.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove("Radiant");
                        tvRadiant.setTextColor(getResources().getColor(R.color.colorPrimary));
                        imgRadiant.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        radiant = false;

                        llTriangle.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvTriangle.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove("Triangle");
                        tvTriangle.setTextColor(getResources().getColor(R.color.colorPrimary));
                        imgTriangle.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        triangle = false;

                        llOthers.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvOthers.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove("Others");
                        tvOthers.setTextColor(getResources().getColor(R.color.colorPrimary));
                        imgOthers.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                        others = false;
                    } else {
                        all = true;
                        selectedShape.add("ALL");
                        llAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tvAll.setBackgroundColor(getResources().getColor(R.color.shapeText));
                        tvAll.setTextColor(getResources().getColor(R.color.white));
                        imgAll.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                        round = true;
                        selectedShape.add("Round");
                        llRound.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tvRound.setBackgroundColor(getResources().getColor(R.color.shapeText));
                        tvRound.setTextColor(getResources().getColor(R.color.white));
                        imgRound.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                        cushion = true;
                        selectedShape.add("Cushion");
                        llCushion.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tvCushion.setBackgroundColor(getResources().getColor(R.color.shapeText));
                        tvCushion.setTextColor(getResources().getColor(R.color.white));
                        imgCushion.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                        emerald = true;
                        selectedShape.add("Emerald");
                        llEmerald.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tvEmerald.setBackgroundColor(getResources().getColor(R.color.shapeText));
                        tvEmerald.setTextColor(getResources().getColor(R.color.white));
                        imgEmerald.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                        oval = true;
                        selectedShape.add("Oval");
                        llOval.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tvOval.setBackgroundColor(getResources().getColor(R.color.shapeText));
                        tvOval.setTextColor(getResources().getColor(R.color.white));
                        imgOval.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                        heart = true;
                        selectedShape.add("Heart");
                        llHeart.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tvHeart.setBackgroundColor(getResources().getColor(R.color.shapeText));
                        tvHeart.setTextColor(getResources().getColor(R.color.white));
                        imgHeart.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                        pear = true;
                        selectedShape.add("Pear");
                        llPear.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tvPear.setBackgroundColor(getResources().getColor(R.color.shapeText));
                        tvPear.setTextColor(getResources().getColor(R.color.white));
                        imgPear.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                        marquise = true;
                        selectedShape.add("Marquise");
                        llMarquise.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tvMarquise.setBackgroundColor(getResources().getColor(R.color.shapeText));
                        tvMarquise.setTextColor(getResources().getColor(R.color.white));
                        imgMarquise.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                        princess = true;
                        selectedShape.add("Princess");
                        llPrincess.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tvPrincess.setBackgroundColor(getResources().getColor(R.color.shapeText));
                        tvPrincess.setTextColor(getResources().getColor(R.color.white));
                        imgPrincess.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                        asscher = true;
                        selectedShape.add("Asscher");
                        llAsscher.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tvAsscher.setBackgroundColor(getResources().getColor(R.color.shapeText));
                        tvAsscher.setTextColor(getResources().getColor(R.color.white));
                        imgAsscher.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                        radiant = true;
                        selectedShape.add("Radiant");
                        llRadiant.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tvRadiant.setBackgroundColor(getResources().getColor(R.color.shapeText));
                        tvRadiant.setTextColor(getResources().getColor(R.color.white));
                        imgRadiant.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                        triangle = true;
                        selectedShape.add("Triangle");
                        llTriangle.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tvTriangle.setBackgroundColor(getResources().getColor(R.color.shapeText));
                        tvTriangle.setTextColor(getResources().getColor(R.color.white));
                        imgTriangle.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                        others = true;
                        selectedShape.add("Others");
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
                        selectedShape.add("Round");
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
                        selectedShape.add("Cushion");
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
                        selectedShape.add("Emerald");
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
                        selectedShape.add("Oval");
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
                        selectedShape.add("Heart");
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
                        selectedShape.add("Pear");
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
                        selectedShape.add("Marquise");
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
                        selectedShape.add("Princess");
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
                        selectedShape.add("Asscher");
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
                        selectedShape.add("Radiant");
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
                        selectedShape.add("Triangle");
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
                        selectedShape.add("Others");
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

            if (selectedArray.get(position).equalsIgnoreCase("true'_'" + array.get(position))) {
                holder.imgYes.setImageDrawable(getResources().getDrawable(R.drawable.ic_true));
                holder.imgNo.setImageDrawable(getResources().getDrawable(R.drawable.ic_emty_box));
            } else if (selectedArray.get(position).equalsIgnoreCase("false'_'" + array.get(position))) {
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
                        selectedArray.put(position, "true'_'" + array.get(position));
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
                        selectedArray.put(position, "false'_'" + array.get(position));
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

//    private class ShapeAdp extends RecyclerView.Adapter<ShapeAdp.RecyclerViewHolder> {
//        String[] name;
//        TypedArray image;
//        Context context;
//
//        private ShapeAdp(Context context, String[] name, TypedArray image) {
//            this.name = name;
//            this.image = image;
//            this.context = context;
//        }
//
//        @Override
//        public ShapeAdp.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_shape, parent, false);
//            ShapeAdp.RecyclerViewHolder viewHolder = new ShapeAdp.RecyclerViewHolder(view);
//            return viewHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(final ShapeAdp.RecyclerViewHolder holder, final int position) {
//            holder.tvName.setText(name[position]);
//            holder.imgIcon.setImageResource(image.getResourceId(position, -1));
//
//            try {
//                if (selectedShape.containsKey(position)) {
//                    holder.llClick.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
//                    holder.tvName.setBackgroundColor(getResources().getColor(R.color.shapeText));
//                    holder.tvName.setTextColor(getResources().getColor(R.color.white));
//                    holder.imgIcon.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
//                } else {
//                    holder.llClick.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
//                    holder.tvName.setBackgroundColor(getResources().getColor(R.color.gray));
//                    holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
//                    holder.imgIcon.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
//                }
//            } catch (Exception e) {
//                holder.llClick.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
//                holder.tvName.setBackgroundColor(getResources().getColor(R.color.gray));
//                holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
//                holder.imgIcon.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
//            }
//
//            holder.llClick.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (selectedShape.containsKey(position)) {
//                        holder.llClick.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
//                        holder.tvName.setBackgroundColor(getResources().getColor(R.color.gray));
//                        selectedShape.remove(position);
//                        holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
//                        holder.imgIcon.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
//                    } else {
//                        holder.llClick.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
//                        holder.tvName.setBackgroundColor(getResources().getColor(R.color.shapeText));
//                        selectedShape.put(position, name[position]);
//                        holder.tvName.setTextColor(getResources().getColor(R.color.white));
//                        holder.imgIcon.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
//                        RotateAnimation rotateAnim = new RotateAnimation(0, 360, holder.imgIcon.getWidth() / 2, holder.imgIcon.getHeight() / 2);
//                        rotateAnim.setDuration(500);
//                        rotateAnim.setFillAfter(true);
//                        holder.imgIcon.setAnimation(rotateAnim);
//                    }
//                }
//            });
//        }
//
//        @Override
//        public int getItemCount() {
//            return name.length;
//        }
//
//        private class RecyclerViewHolder extends RecyclerView.ViewHolder {
//            ImageView imgIcon;
//            TextView tvName;
//            LinearLayout llClick;
//
//            private RecyclerViewHolder(View v) {
//                super(v);
//                imgIcon = v.findViewById(R.id.imgIcon);
//                tvName = v.findViewById(R.id.tvName);
//                llClick = v.findViewById(R.id.llClick);
//            }
//        }
//
//    }

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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_name, parent, false);
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
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                    holder.tvName.setTextColor(getResources().getColor(R.color.white));
                } else {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                    holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            } catch (Exception e) {
                holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
            }

            holder.tvName.setPadding(pL, pT, pR, pB);

            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedLab.containsKey(position)) {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        selectedLab.remove(position);
                        holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_name, parent, false);
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
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                    holder.tvName.setTextColor(getResources().getColor(R.color.white));
                } else {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                    holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            } catch (Exception e) {
                holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
            }

            holder.tvName.setPadding(pL, pT, pR, pB);

            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedColor.containsKey(position)) {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        selectedColor.remove(position);
                        holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_name, parent, false);
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
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                    holder.tvName.setTextColor(getResources().getColor(R.color.white));
                } else {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                    holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            } catch (Exception e) {
                holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
            }

            holder.tvName.setPadding(pL, pT, pR, pB);

            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedClarity.containsKey(position)) {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        selectedClarity.remove(position);
                        holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_name, parent, false);
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
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                    holder.tvName.setTextColor(getResources().getColor(R.color.white));
                } else {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                    holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            } catch (Exception e) {
                holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
            }

            holder.tvName.setPadding(pL, pT, pR, pB);

            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position != 1 || tv3EX.getCurrentTextColor() == getResources().getColor(R.color.colorPrimary)) {
                        if (position != 0 || tvX.getCurrentTextColor() == getResources().getColor(R.color.colorPrimary)) {
                            if (position != 1 || tvX.getCurrentTextColor() == getResources().getColor(R.color.colorPrimary)) {
                                if (position != 2 || tvVG.getCurrentTextColor() == getResources().getColor(R.color.colorPrimary)) {
                                    if (position != 1 || tvVG.getCurrentTextColor() == getResources().getColor(R.color.colorPrimary)) {
                                        if (position != 2 || tv3VG.getCurrentTextColor() == getResources().getColor(R.color.colorPrimary)) {
                                            if (selectedCut.containsKey(position)) {
                                                holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                                                selectedCut.remove(position);
                                                holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                                            } else {
                                                holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                                                selectedCut.put(position, arraylist[position]);
                                                holder.tvName.setTextColor(getResources().getColor(R.color.white));
                                            }
                                            holder.tvName.setPadding(pL, pT, pR, pB);
                                        }
                                    }
                                }
                            }
                        }
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_name, parent, false);
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
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                    holder.tvName.setTextColor(getResources().getColor(R.color.white));
                } else {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                    holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            } catch (Exception e) {
                holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
            }

            holder.tvName.setPadding(pL, pT, pR, pB);

            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position != 0 || tv3EX.getCurrentTextColor() == getResources().getColor(R.color.colorPrimary)) {
                        if (position != 1 || tvVG.getCurrentTextColor() == getResources().getColor(R.color.colorPrimary)) {
                            if (position != 0 || tvVG.getCurrentTextColor() == getResources().getColor(R.color.colorPrimary)) {
                                if (position != 1 || tv3VG.getCurrentTextColor() == getResources().getColor(R.color.colorPrimary)) {
                                    if (selectedPolish.containsKey(position)) {
                                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                                        selectedPolish.remove(position);
                                        holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                                    } else {
                                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                                        selectedPolish.put(position, arraylist[position]);
                                        holder.tvName.setTextColor(getResources().getColor(R.color.white));
                                    }
                                    holder.tvName.setPadding(pL, pT, pR, pB);
                                }
                            }
                        }
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_name, parent, false);
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
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                    holder.tvName.setTextColor(getResources().getColor(R.color.white));
                } else {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                    holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            } catch (Exception e) {
                holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
            }

            holder.tvName.setPadding(pL, pT, pR, pB);

            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position != 0 || tv3EX.getCurrentTextColor() == getResources().getColor(R.color.colorPrimary)) {
                        if (position != 1 || tvVG.getCurrentTextColor() == getResources().getColor(R.color.colorPrimary)) {
                            if (position != 0 || tvVG.getCurrentTextColor() == getResources().getColor(R.color.colorPrimary)) {
                                if (position != 1 || tv3VG.getCurrentTextColor() == getResources().getColor(R.color.colorPrimary)) {
                                    if (selectedSymmetry.containsKey(position)) {
                                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                                        selectedSymmetry.remove(position);
                                        holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                                    } else {
                                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                                        selectedSymmetry.put(position, arraylist[position]);
                                        holder.tvName.setTextColor(getResources().getColor(R.color.white));
                                    }
                                    holder.tvName.setPadding(pL, pT, pR, pB);
                                }
                            }
                        }
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_name, parent, false);
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
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                    holder.tvName.setTextColor(getResources().getColor(R.color.white));
                } else {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                    holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            } catch (Exception e) {
                holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
            }

            holder.tvName.setPadding(pL, pT, pR, pB);

            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedFi.containsKey(position)) {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        selectedFi.remove(position);
                        holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
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
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                    holder.tvName.setTextColor(getResources().getColor(R.color.white));
                } else {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                    holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            } catch (Exception e) {
                holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
            }

            holder.tvName.setPadding(pL, pT, pR, pB);

            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedBgm.containsKey(position)) {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        selectedBgm.remove(position);
                        holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
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

    private class LocationAdp extends RecyclerView.Adapter<LocationAdp.RecyclerViewHolder> {
        String[] arraylist;
        Context context;
        int pL, pT, pR, pB;

        private LocationAdp(Context context, String[] arraylist) {
            this.arraylist = arraylist;
            this.context = context;
        }

        @Override
        public LocationAdp.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_location, parent, false);
            LocationAdp.RecyclerViewHolder viewHolder = new LocationAdp.RecyclerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final LocationAdp.RecyclerViewHolder holder, final int position) {
            holder.tvName.setText(arraylist[position]);
            pL = holder.tvName.getPaddingLeft();
            pT = holder.tvName.getPaddingTop();
            pR = holder.tvName.getPaddingRight();
            pB = holder.tvName.getPaddingBottom();

            try {
                if (selectedLoc.containsKey(position)) {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                    holder.tvName.setTextColor(getResources().getColor(R.color.white));
                } else {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                    holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            } catch (Exception e) {
                holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
            }

            holder.tvName.setPadding(pL, pT, pR, pB);

            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedLoc.containsKey(position)) {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        selectedLoc.remove(position);
                        holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        selectedLoc.put(position, arraylist[position]);
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

    private class SizeAdp extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        List<String> arraylist;
        Context context;
        boolean is;
        int pL, pT, pR, pB;

        private SizeAdp(Context context, List<String> arraylist, boolean is) {
            this.arraylist = arraylist;
            this.context = context;
            this.is = is;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (is) {
                View view = LayoutInflater.from(context).inflate(R.layout.layout_size, parent, false);
                return new SizeAdp.SizeHolder(view);
            } else {
                View view = LayoutInflater.from(context).inflate(R.layout.layout_specific_size, parent, false);
                return new SizeAdp.SpecifiSizeHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            if (is) {
                if (holder instanceof SizeAdp.SizeHolder) {
                    final SizeAdp.SizeHolder h = (SizeAdp.SizeHolder) holder;
                    h.tvName.setText(arraylist.get(position));
                    pL = h.tvName.getPaddingLeft();
                    pT = h.tvName.getPaddingTop();
                    pR = h.tvName.getPaddingRight();
                    pB = h.tvName.getPaddingBottom();

                    try {
                        if (selectedSize.containsKey(position)) {
                            h.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                            h.tvName.setTextColor(getResources().getColor(R.color.white));
                        } else {
                            h.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                            h.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                        }
                    } catch (Exception e) {
                        h.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        h.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }

                    h.tvName.setPadding(pL, pT, pR, pB);

                    h.tvName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (selectedSize.containsKey(position)) {
                                h.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                                selectedSize.remove(position);
                                h.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                            } else {
                                h.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                                selectedSize.put(position, arraylist.get(position));
                                h.tvName.setTextColor(getResources().getColor(R.color.white));
                            }
                            h.tvName.setPadding(pL, pT, pR, pB);
                            tvSelectedSize.setText(hmToArray(selectedSize).toString().replace("[", "").replace("]", "").replace(", ", ","));
                        }
                    });
                }
            } else {
                if (holder instanceof SizeAdp.SpecifiSizeHolder) {
                    SizeAdp.SpecifiSizeHolder h = (SizeAdp.SpecifiSizeHolder) holder;
                    h.tvText.setText(arraylist.get(position));
                    h.llMain.setBackgroundDrawable(Const.setBackgoundBorder(0, 50, getResources().getColor(R.color.gray), getResources().getColor(R.color.gray)));
                    h.imgRemove.setBackgroundDrawable(Const.setBackgoundBorder(0, 50, getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorPrimary)));
                    h.imgRemove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (removeItem(position)) {
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, getItemCount());
                            }
                        }
                    });
                }
            }

        }

        @Override
        public int getItemCount() {
            return arraylist.size();
        }

        private boolean removeItem(int position) {
            if (arraylist.size() >= position + 1) {
                arraylist.remove(position);
                return true;
            }
            return false;
        }

        private class SizeHolder extends RecyclerView.ViewHolder {

            TextView tvName;

            private SizeHolder(@NonNull View v) {
                super(v);
                tvName = v.findViewById(R.id.tvName);
            }
        }

        private class SpecifiSizeHolder extends RecyclerView.ViewHolder {

            LinearLayout llMain;
            ImageView imgRemove;
            TextView tvText;

            private SpecifiSizeHolder(@NonNull View v) {
                super(v);
                llMain = v.findViewById(R.id.llMain);
                imgRemove = v.findViewById(R.id.imgRemove);
                tvText = v.findViewById(R.id.tvText);
            }
        }

    }

    private class TableBlackAdp extends RecyclerView.Adapter<TableBlackAdp.RecyclerViewHolder> {
        String[] arraylist;
        Context context;
        int pL, pT, pR, pB;

        private TableBlackAdp(Context context, String[] arraylist) {
            this.arraylist = arraylist;
            this.context = context;
        }

        @Override
        public TableBlackAdp.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_name, parent, false);
            TableBlackAdp.RecyclerViewHolder viewHolder = new TableBlackAdp.RecyclerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final TableBlackAdp.RecyclerViewHolder holder, final int position) {
            holder.tvName.setText(arraylist[position]);
            pL = holder.tvName.getPaddingLeft();
            pT = holder.tvName.getPaddingTop();
            pR = holder.tvName.getPaddingRight();
            pB = holder.tvName.getPaddingBottom();

            try {
                if (selectedTB.containsKey(position)) {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                    holder.tvName.setTextColor(getResources().getColor(R.color.white));
                } else {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                    holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            } catch (Exception e) {
                holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
            }

            holder.tvName.setPadding(pL, pT, pR, pB);

            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedTB.containsKey(position)) {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        selectedTB.remove(position);
                        holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        selectedTB.put(position, arraylist[position]);
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

    private class CrownBlackAdp extends RecyclerView.Adapter<CrownBlackAdp.RecyclerViewHolder> {
        String[] arraylist;
        Context context;
        int pL, pT, pR, pB;

        private CrownBlackAdp(Context context, String[] arraylist) {
            this.arraylist = arraylist;
            this.context = context;
        }

        @Override
        public CrownBlackAdp.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_name, parent, false);
            CrownBlackAdp.RecyclerViewHolder viewHolder = new CrownBlackAdp.RecyclerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final CrownBlackAdp.RecyclerViewHolder holder, final int position) {
            holder.tvName.setText(arraylist[position]);
            pL = holder.tvName.getPaddingLeft();
            pT = holder.tvName.getPaddingTop();
            pR = holder.tvName.getPaddingRight();
            pB = holder.tvName.getPaddingBottom();

            try {
                if (selectedCB.containsKey(position)) {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                    holder.tvName.setTextColor(getResources().getColor(R.color.white));
                } else {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                    holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            } catch (Exception e) {
                holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
            }

            holder.tvName.setPadding(pL, pT, pR, pB);

            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedCB.containsKey(position)) {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        selectedCB.remove(position);
                        holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        selectedCB.put(position, arraylist[position]);
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

    private class TableWhiteAdp extends RecyclerView.Adapter<TableWhiteAdp.RecyclerViewHolder> {
        String[] arraylist;
        Context context;
        int pL, pT, pR, pB;

        private TableWhiteAdp(Context context, String[] arraylist) {
            this.arraylist = arraylist;
            this.context = context;
        }

        @Override
        public TableWhiteAdp.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_name, parent, false);
            TableWhiteAdp.RecyclerViewHolder viewHolder = new TableWhiteAdp.RecyclerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final TableWhiteAdp.RecyclerViewHolder holder, final int position) {
            holder.tvName.setText(arraylist[position]);
            pL = holder.tvName.getPaddingLeft();
            pT = holder.tvName.getPaddingTop();
            pR = holder.tvName.getPaddingRight();
            pB = holder.tvName.getPaddingBottom();

            try {
                if (selectedTW.containsKey(position)) {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                    holder.tvName.setTextColor(getResources().getColor(R.color.white));
                } else {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                    holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            } catch (Exception e) {
                holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
            }

            holder.tvName.setPadding(pL, pT, pR, pB);

            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedTW.containsKey(position)) {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        selectedTW.remove(position);
                        holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        selectedTW.put(position, arraylist[position]);
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

    private class CrownWhiteAdp extends RecyclerView.Adapter<CrownWhiteAdp.RecyclerViewHolder> {
        String[] arraylist;
        Context context;
        int pL, pT, pR, pB;

        private CrownWhiteAdp(Context context, String[] arraylist) {
            this.arraylist = arraylist;
            this.context = context;
        }

        @Override
        public CrownWhiteAdp.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_name, parent, false);
            CrownWhiteAdp.RecyclerViewHolder viewHolder = new CrownWhiteAdp.RecyclerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final CrownWhiteAdp.RecyclerViewHolder holder, final int position) {
            holder.tvName.setText(arraylist[position]);
            pL = holder.tvName.getPaddingLeft();
            pT = holder.tvName.getPaddingTop();
            pR = holder.tvName.getPaddingRight();
            pB = holder.tvName.getPaddingBottom();

            try {
                if (selectedCW.containsKey(position)) {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                    holder.tvName.setTextColor(getResources().getColor(R.color.white));
                } else {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                    holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            } catch (Exception e) {
                holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
            }

            holder.tvName.setPadding(pL, pT, pR, pB);

            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedCW.containsKey(position)) {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        selectedCW.remove(position);
                        holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        selectedCW.put(position, arraylist[position]);
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

        private class RecyclerViewHolder extends RecyclerView.ViewHolder
        {

            TextView tvName;

            private RecyclerViewHolder(View v) {
                super(v);
                tvName = v.findViewById(R.id.tvName);
            }
        }

    }

    private class TableOpenAdp extends RecyclerView.Adapter<TableOpenAdp.RecyclerViewHolder> {
        List<String> arraylist = new ArrayList<>();
        Context context;
        int pL, pT, pR, pB;

        private TableOpenAdp(Context context, List<String> arraylist) {
            this.arraylist = arraylist;
            this.context = context;
        }

        @Override
        public TableOpenAdp.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_name, parent, false);
            TableOpenAdp.RecyclerViewHolder viewHolder = new TableOpenAdp.RecyclerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final TableOpenAdp.RecyclerViewHolder holder, final int position) {
            holder.tvName.setText(arraylist.get(position));
            pL = holder.tvName.getPaddingLeft();
            pT = holder.tvName.getPaddingTop();
            pR = holder.tvName.getPaddingRight();
            pB = holder.tvName.getPaddingBottom();

            try {
                if (selectedTO.containsKey(position)) {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                    holder.tvName.setTextColor(getResources().getColor(R.color.white));
                } else {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                    holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            } catch (Exception e) {
                holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
            }

            holder.tvName.setPadding(pL, pT, pR, pB);

            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedTO.containsKey(position)) {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        selectedTO.remove(position);
                        holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        selectedTO.put(position, arraylist.get(position));
                        holder.tvName.setTextColor(getResources().getColor(R.color.white));
                    }
                    holder.tvName.setPadding(pL, pT, pR, pB);
                }
            });
        }

        @Override
        public int getItemCount() {
            return arraylist.size();
        }

        private class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView tvName;

            private RecyclerViewHolder(View v) {
                super(v);
                tvName = v.findViewById(R.id.tvName);
            }
        }

    }

    private class CrownOpenAdp extends RecyclerView.Adapter<CrownOpenAdp.RecyclerViewHolder> {
        List<String> arraylist = new ArrayList<>();
        Context context;
        int pL, pT, pR, pB;

        private CrownOpenAdp(Context context, List<String> arraylist) {
            this.arraylist = arraylist;
            this.context = context;
        }

        @Override
        public CrownOpenAdp.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_name, parent, false);
            CrownOpenAdp.RecyclerViewHolder viewHolder = new CrownOpenAdp.RecyclerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final CrownOpenAdp.RecyclerViewHolder holder, final int position) {
            holder.tvName.setText(arraylist.get(position));
            pL = holder.tvName.getPaddingLeft();
            pT = holder.tvName.getPaddingTop();
            pR = holder.tvName.getPaddingRight();
            pB = holder.tvName.getPaddingBottom();

            try {
                if (selectedCO.containsKey(position)) {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                    holder.tvName.setTextColor(getResources().getColor(R.color.white));
                } else {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                    holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            } catch (Exception e) {
                holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
            }

            holder.tvName.setPadding(pL, pT, pR, pB);

            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedCO.containsKey(position)) {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        selectedCO.remove(position);
                        holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        selectedCO.put(position, arraylist.get(position));
                        holder.tvName.setTextColor(getResources().getColor(R.color.white));
                    }
                    holder.tvName.setPadding(pL, pT, pR, pB);
                }
            });
        }

        @Override
        public int getItemCount() {
            return arraylist.size();
        }

        private class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView tvName;

            private RecyclerViewHolder(View v) {
                super(v);
                tvName = v.findViewById(R.id.tvName);
            }
        }

    }

    private class PavOpenAdp extends RecyclerView.Adapter<PavOpenAdp.RecyclerViewHolder> {
        List<String> arraylist = new ArrayList<>();
        Context context;
        int pL, pT, pR, pB;

        private PavOpenAdp(Context context, List<String> arraylist) {
            this.arraylist = arraylist;
            this.context = context;
        }

        @Override
        public PavOpenAdp.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_name, parent, false);
            PavOpenAdp.RecyclerViewHolder viewHolder = new PavOpenAdp.RecyclerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final PavOpenAdp.RecyclerViewHolder holder, final int position) {
            holder.tvName.setText(arraylist.get(position));
            pL = holder.tvName.getPaddingLeft();
            pT = holder.tvName.getPaddingTop();
            pR = holder.tvName.getPaddingRight();
            pB = holder.tvName.getPaddingBottom();

            try {
                if (selectedPO.containsKey(position)) {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                    holder.tvName.setTextColor(getResources().getColor(R.color.white));
                } else {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                    holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            } catch (Exception e) {
                holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
            }

            holder.tvName.setPadding(pL, pT, pR, pB);

            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedPO.containsKey(position)) {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        selectedPO.remove(position);
                        holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        selectedPO.put(position, arraylist.get(position));
                        holder.tvName.setTextColor(getResources().getColor(R.color.white));
                    }
                    holder.tvName.setPadding(pL, pT, pR, pB);
                }
            });
        }

        @Override
        public int getItemCount() {
            return arraylist.size();
        }

        private class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView tvName;

            private RecyclerViewHolder(View v) {
                super(v);
                tvName = v.findViewById(R.id.tvName);
            }
        }

    }

    private class GirdleOpenAdp extends RecyclerView.Adapter<GirdleOpenAdp.RecyclerViewHolder> {
        List<String> arraylist = new ArrayList<>();
        Context context;
        int pL, pT, pR, pB;

        private GirdleOpenAdp(Context context, List<String> arraylist) {
            this.arraylist = arraylist;
            this.context = context;
        }

        @Override
        public GirdleOpenAdp.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_name, parent, false);
            GirdleOpenAdp.RecyclerViewHolder viewHolder = new GirdleOpenAdp.RecyclerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final GirdleOpenAdp.RecyclerViewHolder holder, final int position) {
            holder.tvName.setText(arraylist.get(position));
            pL = holder.tvName.getPaddingLeft();
            pT = holder.tvName.getPaddingTop();
            pR = holder.tvName.getPaddingRight();
            pB = holder.tvName.getPaddingBottom();

            try {
                if (selectedGO.containsKey(position)) {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                    holder.tvName.setTextColor(getResources().getColor(R.color.white));
                } else {
                    holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                    holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            } catch (Exception e) {
                holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
            }

            holder.tvName.setPadding(pL, pT, pR, pB);

            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedGO.containsKey(position)) {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        selectedGO.remove(position);
                        holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        selectedGO.put(position, arraylist.get(position));
                        holder.tvName.setTextColor(getResources().getColor(R.color.white));
                    }
                    holder.tvName.setPadding(pL, pT, pR, pB);
                }
            });
        }

        @Override
        public int getItemCount() {
            return arraylist.size();
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
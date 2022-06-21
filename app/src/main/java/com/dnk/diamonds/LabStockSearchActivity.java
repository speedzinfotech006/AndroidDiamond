package com.dnk.shairugems;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.EditorInfo;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dnk.shairugems.Class.ModelClass;
import com.dnk.shairugems.Utils.Const;
import com.dnk.shairugems.Utils.Pref;
import com.dnk.shairugems.Volly.VolleyCallback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class LabStockSearchActivity extends BaseDrawerActivity {

    Context context = LabStockSearchActivity.this;
    Dialog dialogTrans;
    ListView trans_list;
    CheckBox all;
    String TransID = "";
    private String lsttransid;
    TextView tvIntensity, tvOvertone, tvFancyColor, tvSelectedSize, tv3EX, tvX, tvVG, tv3VG, tv3VG2;
    TextView tvBlankSymbol, tvBlankLength, tvBlankWidth, tvBlankDepth, tvBlankDepthPer, tvBlankTablePer, tvBlankCrAng, tvBlankCrHit, tvBlankPavAng, tvBlankPavHit;
    RadioGroup rgColor, rgSize;
    RadioButton rbRegular, rbFancy, rbGeneral, rbSpecific;
    EditText etFromDate, etToDate, etTransId, etStoneOrCert, etSymbol, etFromCts, etToCts, etDisF, etDisT, etPcuF, etPcuT, etTauF, etTauT, etLengthF, etLengthT, etWidthF, etWidthT, etDepthF, etDepthT, etDepthPF, etDepthPT, etTablePF, etTablePT, etCrAngPF, etCrAngPT, etCrHtPF, etCrHtPT, etPavAngPF, etPavAngPT, etPavHtPF, etPavHtPT;
    ImageView imgMenu, imgEmail, imgSizeBtn, imgVideo, imgImage, imgOMenu;
    RecyclerView rvShape, rvLab, rvColor, rvIntencity, rvOvertone, rvFancyColor, rvClarity, rvCut, rvPolish, rvSymmetry, rvFi, rvBgm, rvSize, rvInclusion, rvNatts, rvLocation, rvTableBlack, rvCrownBlack, rvTableWhite, rvCrownWhite, rvTableOpen, rvCrownOpen, rvPavOpen, rvGirdleOpen;
    HorizontalScrollView hsSize;
    Boolean img = true, vid = true;
    RelativeLayout rlMain, rlImage, rlVideo;
    LinearLayout llSizeFromTo, llSearch, llShare, llReset;
    ShapeNewAdp shapeAdp;
    LabAdp labAdp;
    ColorAdp colorAdp;
    IntencityAdp intencityAdp;
    OvertoneAdp overtoneAdp;
    FancyColorAdp fancycolorAdp;
    ClarityAdp clarityAdp;
    CutAdp cutAdp;
    PolishAdp polishAdp;
    SymmetryAdp symmetryAdp;
    FiAdp fiAdp;
    BgmAdp bgmAdp;
    SizeAdp sizeAdp;
    InclusionAdp inclusionAdp;
    NattsAdp nattsAdp;
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
    List<String> arrayShape = new ArrayList<>();
    List<String> arrayShapeImage = new ArrayList<>();
    List<String> arrayColor = new ArrayList<>();
    List<String> arrayIntencity = new ArrayList<>();
    List<String> arrayOvertone = new ArrayList<>();
    List<String> arrayFancyColor = new ArrayList<>();
    List<String> arrayClarity = new ArrayList<>();
    List<String> arrayLab = new ArrayList<>();
    List<String> arrayCut = new ArrayList<>();
    List<String> arrayPolish = new ArrayList<>();
    List<String> arraySymm = new ArrayList<>();
    List<String> arrayFi = new ArrayList<>();
    List<String> arrayBgm = new ArrayList<>();
    List<String> arraySymbol = new ArrayList<>();
    List<String> arrayTransId = new ArrayList<>();
    List<String> arrayTB = new ArrayList<>();
    List<String> arrayCB = new ArrayList<>();
    List<String> arrayTW = new ArrayList<>();
    List<String> arrayCW = new ArrayList<>();
    List<String> arrayTO = new ArrayList<>();
    List<String> arrayCO = new ArrayList<>();
    List<String> arrayPO = new ArrayList<>();
    List<String> arrayGO = new ArrayList<>();
    List<String> arrayLocation = new ArrayList<>();
    List<String> arrayPointerSize = new ArrayList<>();
    List<String> arraySpecificSize = new ArrayList<>();
    List<String> arraySymbolTrue = new ArrayList<>();
    List<String> arraySymbolFalse = new ArrayList<>();
    List<String> arrayTransTrue = new ArrayList<>();
    List<String> arrayTransFalse = new ArrayList<>();
    HashMap<Integer, String> selectedSymbol = new HashMap<>();
    HashMap<Integer, String> selectedTransId = new HashMap<>();
    HashMap<Integer, String> selectedShape = new HashMap<>();
    HashMap<Integer, String> selectedLab = new HashMap<>();
    HashMap<Integer, String> selectedColor = new HashMap<>();
    HashMap<Integer, String> selectedIntencity = new HashMap<>();
    HashMap<Integer, String> selectedOvertone = new HashMap<>();
    HashMap<Integer, String> selectedFancyColor = new HashMap<>();
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

    List<ModelClass> modelClassList = new ArrayList<>();
    private ArrayList<String> trans_lst = new ArrayList<>();
    ArrayList<String> selectedTrans= new ArrayList<>();

    long downID = 0, shareID = 0;
    String downloadNumber = "", shareNumber = "";
    DownloadManager downloadManager, shareManager;
    boolean isSelectedAllShape = false;
    boolean isSelectedAllShapeAnim = false;
    int isSelectedAnimPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_lab_stock_search, frameLayout);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

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

        etSymbol.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etSymbol.performClick();
                }
            }
        });

        etTransId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etTransId.performClick();
                }
            }
        });

//        etFromCts.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    etFromCts.performClick();
//                }
//            }
//        });
//
//        etToCts.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    etToCts.performClick();
//                }
//            }
//        });

        etFromCts.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.e("Lab Stock etFromCts : ", hasFocus + "");
                if(!hasFocus){
                    fromTextChanges(etFromCts,etToCts);
                }else{
                    etFromCts.performClick();
                }
            }
        });
        etToCts.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.e("Lab Stock etToCts : ", hasFocus + "");
                if(!hasFocus){
                    toTextChanges(etFromCts,etToCts);
                }else {
                    etToCts.performClick();
                }
            }
        });
        etToCts.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if ((keyEvent != null && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    toTextChanges(etFromCts,etToCts);
                    etToCts.setSelection(etToCts.getText().toString().length());
                }
                return false;
            }
        });
        etDisF.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.e("Lab Stock etDisF : ", hasFocus + "");
                if(!hasFocus){
                    fromTextChanges(etDisF,etDisT);
                }
            }
        });
        etDisT.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.e("Lab Stock etDisT : ", hasFocus + "");
                if(!hasFocus){
                    toTextChanges(etDisF,etDisT);
                }
            }
        });
        etTauF.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.e("Lab Stock etTauF : ", hasFocus + "");
                if(!hasFocus){
                    fromTextChanges(etTauF,etTauT);
                }
            }
        });
        etTauT.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.e("Lab Stock etTauT : ", hasFocus + "");
                if(!hasFocus){
                    toTextChanges(etTauF,etTauT);
                }
            }
        });
        etLengthF.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.e("Lab Stock etLengthF : ", hasFocus + "");
                if(!hasFocus){
                    fromTextChanges(etLengthF,etLengthT);
                }
            }
        });
        etLengthT.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.e("Lab Stock etLengthT : ", hasFocus + "");
                if(!hasFocus){
                    toTextChanges(etLengthF,etLengthT);
                }
            }
        });
        etWidthF.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.e("Lab Stock etWidthF : ", hasFocus + "");
                if(!hasFocus){
                    fromTextChanges(etWidthF,etWidthT);
                }
            }
        });
        etWidthT.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.e("Lab Stock etWidthT : ", hasFocus + "");
                if(!hasFocus){
                    toTextChanges(etWidthF,etWidthT);
                }
            }
        });
        etDepthF.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.e("Lab Stock etDepthF : ", hasFocus + "");
                if(!hasFocus){
                    fromTextChanges(etDepthF,etDepthT);
                }
            }
        });
        etDepthT.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.e("Lab Stock etDepthT : ", hasFocus + "");
                if(!hasFocus){
                    toTextChanges(etDepthF,etDepthT);
                }
            }
        });
        etDepthPF.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.e("Lab Stock etDepthPF : ", hasFocus + "");
                if(!hasFocus){
                    fromTextChanges(etDepthPF,etDepthPT);
                }
            }
        });
        etDepthPT.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.e("Lab Stock etDepthPT : ", hasFocus + "");
                if(!hasFocus){
                    toTextChanges(etDepthPF,etDepthPT);
                }
            }
        });
        etTablePF.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.e("Lab Stock etTablePF : ", hasFocus + "");
                if(!hasFocus){
                    fromTextChanges(etTablePF,etTablePT);
                }
            }
        });
        etTablePT.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.e("Lab Stock etTablePT : ", hasFocus + "");
                if(!hasFocus){
                    toTextChanges(etTablePF,etTablePT);
                }
            }
        });
        etCrAngPF.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.e("Lab Stock etCrAngPF : ", hasFocus + "");
                if(!hasFocus){
                    fromTextChanges(etCrAngPF,etCrAngPT);
                }
            }
        });
        etCrAngPT.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.e("Lab Stock etCrAngPT : ", hasFocus + "");
                if(!hasFocus){
                    toTextChanges(etCrAngPF,etCrAngPT);
                }
            }
        });
        etCrHtPF.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.e("Lab Stock etCrHtPF : ", hasFocus + "");
                if(!hasFocus){
                    fromTextChanges(etCrHtPF,etCrHtPT);
                }
            }
        });
        etCrHtPT.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.e("Lab Stock etCrHtPT : ", hasFocus + "");
                if(!hasFocus){
                    toTextChanges(etCrHtPF,etCrHtPT);
                }
            }
        });
        etPavAngPF.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.e("Lab Stock etPavAngPF : ", hasFocus + "");
                if(!hasFocus){
                    fromTextChanges(etPavAngPF,etPavAngPT);
                }
            }
        });
        etPavAngPT.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.e("Lab Stock etPavAngPT : ", hasFocus + "");
                if(!hasFocus){
                    toTextChanges(etPavAngPF,etPavAngPT);
                }
            }
        });
        etPavHtPF.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.e("Lab Stock etPavHtPF : ", hasFocus + "");
                if(!hasFocus){
                    fromTextChanges(etPavHtPF,etPavHtPT);
                }
            }
        });
        etPavHtPT.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.e("Lab Stock etPavHtPT : ", hasFocus + "");
                if(!hasFocus){
                    toTextChanges(etPavHtPF,etPavHtPT);
                }
            }
        });

        etPavHtPT.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if ((keyEvent != null && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    toTextChanges(etPavHtPF,etPavHtPT);
                    etPavHtPT.setSelection(etPavHtPT.getText().toString().length());
                }
                return false;
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

        tvIntensity.setVisibility(View.GONE);
        rvIntencity.setVisibility(View.GONE);
        tvOvertone.setVisibility(View.GONE);
        rvOvertone.setVisibility(View.GONE);
        tvFancyColor.setVisibility(View.GONE);
        rvFancyColor.setVisibility(View.GONE);

        rgColor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectedColor = new HashMap<>();
                selectedIntencity = new HashMap<>();
                selectedOvertone = new HashMap<>();
                selectedFancyColor = new HashMap<>();
                switch (checkedId) {
                    case R.id.rbRegular:
                        tvIntensity.setVisibility(View.GONE);
                        rvIntencity.setVisibility(View.GONE);
                        tvOvertone.setVisibility(View.GONE);
                        rvOvertone.setVisibility(View.GONE);
                        tvFancyColor.setVisibility(View.GONE);
                        rvFancyColor.setVisibility(View.GONE);
                        rvColor.setVisibility(View.VISIBLE);
                        colorAdp.notifyDataSetChanged();
                        break;
                    case R.id.rbFancy:
                        tvIntensity.setVisibility(View.VISIBLE);
                        rvIntencity.setVisibility(View.VISIBLE);
                        tvOvertone.setVisibility(View.VISIBLE);
                        rvOvertone.setVisibility(View.VISIBLE);
                        tvFancyColor.setVisibility(View.VISIBLE);
                        rvFancyColor.setVisibility(View.VISIBLE);
                        rvColor.setVisibility(View.GONE);
                        intencityAdp.notifyDataSetChanged();
                        overtoneAdp.notifyDataSetChanged();
                        fancycolorAdp.notifyDataSetChanged();
                        break;
                }
            }
        });

//        shapeAdp = new ShapeNewAdp(context, arrayShape, getResources().obtainTypedArray(R.array.shape_image));
//        rvShape.setAdapter(shapeAdp);

//        labAdp = new LabAdp(context, getResources().getStringArray(R.array.lab));
//        rvLab.setAdapter(labAdp);

//        colorAdp = new ColorAdp(context, getResources().getStringArray(R.array.color));
//        rvColor.setAdapter(colorAdp);

//        clarityAdp = new ClarityAdp(context, getResources().getStringArray(R.array.clarity));
//        rvClarity.setAdapter(clarityAdp);

//        cutAdp = new CutAdp(context, getResources().getStringArray(R.array.cut));
//        rvCut.setAdapter(cutAdp);
//
//        polishAdp = new PolishAdp(context, getResources().getStringArray(R.array.polish_symmetry));
//        rvPolish.setAdapter(polishAdp);
//
//        symmetryAdp = new SymmetryAdp(context, getResources().getStringArray(R.array.polish_symmetry));
//        rvSymmetry.setAdapter(symmetryAdp);

//        fiAdp = new FiAdp(context, getResources().getStringArray(R.array.fi));
//        rvFi.setAdapter(fiAdp);

//        bgmAdp = new BgmAdp(context, getResources().getStringArray(R.array.bgm));
//        rvBgm.setAdapter(bgmAdp);

        locAdp = new LocationAdp(context, getResources().getStringArray(R.array.location));
        rvLocation.setAdapter(locAdp);

        inclusionAdp = new InclusionAdp(context, getResources().getStringArray(R.array.table_black));
        rvInclusion.setAdapter(inclusionAdp);

        nattsAdp = new NattsAdp(context, getResources().getStringArray(R.array.table_black));
        rvNatts.setAdapter(nattsAdp);

        getKeyToSymbols();
        //getTransID();
    }

    public void fromTextChanges(EditText fromText, EditText toText){
        if(!fromText.getText().toString().equalsIgnoreCase("")){
            String f = String.format("%.2f", Double.parseDouble(fromText.getText().toString()));
            double disFrom = Double.parseDouble(fromText.getText().toString());
            if(disFrom <= 0){
                fromText.setText("");
            }else{
                fromText.setText(f);
                if(toText.getText().toString().equalsIgnoreCase("")){
                    toText.setText(f);
                }
                toText.setSelection(fromText.getText().toString().length());
            }
        }
    }

    public void toTextChanges(EditText fromText, EditText toText){
        double disFrom = 0;
        double disTo = 0;
        if(!fromText.getText().toString().equalsIgnoreCase("")){
            disFrom = Double.parseDouble(fromText.getText().toString());
        }
        if(!toText.getText().toString().equalsIgnoreCase("")){
            disTo = Double.parseDouble(toText.getText().toString());
        }
        if(disTo <= 0){
            toText.setText("");
            fromText.setText("");
        }else if(disFrom > disTo){
            String t = String.format("%.2f", disTo);
            toText.setText(t);
            fromText.setText(t);
            fromText.setSelection(fromText.getText().toString().length());
        }else{
            if (!toText.getText().toString().equalsIgnoreCase("")) {
                String t = String.format("%.2f", disTo);
                toText.setText(t);
            }
        }
    }

    private void initView() {
        nsvHeader = findViewById(R.id.nsvHeader);
        etFromDate = findViewById(R.id.etFromDate);
        etToDate = findViewById(R.id.etToDate);
        etTransId = findViewById(R.id.etTransId);
        imgMenu = findViewById(R.id.imgMenu);
        imgEmail = findViewById(R.id.imgEmail);
        rlVideo = findViewById(R.id.rlVideo);
        rlImage = findViewById(R.id.rlImage);
        etStoneOrCert = findViewById(R.id.etStoneOrCert);
        imgOMenu = findViewById(R.id.imgOMenu);
        tvSelectedSize = findViewById(R.id.tvSelectedSize);
        tvIntensity = findViewById(R.id.tvIntensity);
        tvOvertone = findViewById(R.id.tvOvertone);
        tvFancyColor = findViewById(R.id.tvFancyColor);
        tv3EX = findViewById(R.id.tv3EX);
        tvX = findViewById(R.id.tvX);
        tvVG = findViewById(R.id.tvVG);
        tv3VG = findViewById(R.id.tv3VG);
        tv3VG2 = findViewById(R.id.tv3VG2);

        tvBlankSymbol = findViewById(R.id.tvBlankSymbol);
        tvBlankLength = findViewById(R.id.tvBlankLength);
        tvBlankWidth = findViewById(R.id.tvBlankWidth);
        tvBlankDepth = findViewById(R.id.tvBlankDepth);
        tvBlankDepthPer = findViewById(R.id.tvBlankDepthPer);
        tvBlankTablePer = findViewById(R.id.tvBlankTablePer);
        tvBlankCrAng = findViewById(R.id.tvBlankCrAng);
        tvBlankCrHit = findViewById(R.id.tvBlankCrHit);
        tvBlankPavAng = findViewById(R.id.tvBlankPavAng);
        tvBlankPavHit = findViewById(R.id.tvBlankPavHit);

        imgVideo = findViewById(R.id.imgVideo);
        rbSpecific = findViewById(R.id.rbSpecific);
        rbGeneral = findViewById(R.id.rbGeneral);
        rgSize = findViewById(R.id.rgSize);
        rbRegular = findViewById(R.id.rbRegular);
        rbFancy = findViewById(R.id.rbFancy);
        rgColor = findViewById(R.id.rgColor);
        imgImage = findViewById(R.id.imgImage);
        llSearch = findViewById(R.id.llSearch);
        llShare = findViewById(R.id.llShare);
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
        rvShape = findViewById(R.id.rvShape);
        rvLab = findViewById(R.id.rvLab);
        rvColor = findViewById(R.id.rvColor);
        rvIntencity = findViewById(R.id.rvIntencity);
        rvOvertone = findViewById(R.id.rvOvertone);
        rvFancyColor = findViewById(R.id.rvFancyColor);
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
        rvTableBlack.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvCrownBlack.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvTableWhite.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvCrownWhite.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvTableOpen.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvCrownOpen.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvPavOpen.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvGirdleOpen.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvShape.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvLab.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvColor.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvIntencity.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvOvertone.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvFancyColor.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
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
        llShare.setBackgroundDrawable(Const.setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));
        llReset.setBackgroundDrawable(Const.setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));

        tv3EX.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvX.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvVG.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tv3VG.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tv3VG2.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));

        tv3EX.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvX.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvVG.setTextColor(getResources().getColor(R.color.colorPrimary));
        tv3VG.setTextColor(getResources().getColor(R.color.colorPrimary));
        tv3VG2.setTextColor(getResources().getColor(R.color.colorPrimary));

        tvBlankSymbol.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvBlankSymbol.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvBlankLength.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvBlankLength.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvBlankWidth.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvBlankWidth.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvBlankDepth.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvBlankDepth.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvBlankDepthPer.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvBlankDepthPer.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvBlankTablePer.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvBlankTablePer.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvBlankCrAng.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvBlankCrAng.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvBlankCrHit.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvBlankCrHit.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvBlankPavAng.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvBlankPavAng.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvBlankPavHit.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvBlankPavHit.setTextColor(getResources().getColor(R.color.colorPrimary));

        etFromDate.setText(new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date()));
        etToDate.setText(new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date()));

        imgMenu.setOnClickListener(clickListener);
        imgEmail.setOnClickListener(clickListener);
        etSymbol.setOnClickListener(clickListener);
        etTransId.setOnClickListener(clickListener);
        llReset.setOnClickListener(clickListener);
        imgSizeBtn.setOnClickListener(clickListener);
        llSearch.setOnClickListener(clickListener);
        llShare.setOnClickListener(clickListener);
        rlVideo.setOnClickListener(clickListener);
        rlImage.setOnClickListener(clickListener);
        imgOMenu.setOnClickListener(clickListener);

        etFromDate.setOnClickListener(clickListener);
        etToDate.setOnClickListener(clickListener);

        tv3EX.setOnClickListener(clickListener);
        tvX.setOnClickListener(clickListener);
        tvVG.setOnClickListener(clickListener);
        tv3VG.setOnClickListener(clickListener);
        tv3VG2.setOnClickListener(clickListener);

        tvBlankSymbol.setOnClickListener(clickListener);
        tvBlankLength.setOnClickListener(clickListener);
        tvBlankWidth.setOnClickListener(clickListener);
        tvBlankDepth.setOnClickListener(clickListener);
        tvBlankDepthPer.setOnClickListener(clickListener);
        tvBlankTablePer.setOnClickListener(clickListener);
        tvBlankCrAng.setOnClickListener(clickListener);
        tvBlankCrHit.setOnClickListener(clickListener);
        tvBlankPavAng.setOnClickListener(clickListener);
        tvBlankPavHit.setOnClickListener(clickListener);
    }

    private void clearSelected() {
        TransID="";
        selectedSymbol = new HashMap<>();
        for (int i = 0; i < arraySymbol.size(); i++) {
            selectedSymbol.put(i, "");
        }
        selectedTransId = new HashMap<>();
        for (int i = 0; i < arrayTransId.size(); i++) {
            selectedTransId.put(i, "");
        }
        rbRegular.setChecked(true);
        rbSpecific.setChecked(true);
        arraySpecificSize = new ArrayList<>();
        arraySymbolFalse = new ArrayList<>();
        arraySymbolTrue = new ArrayList<>();
        selectedShape = new HashMap<>();
        selectedLab = new HashMap<>();
        selectedColor = new HashMap<>();
        selectedIntencity = new HashMap<>();
        selectedOvertone = new HashMap<>();
        selectedFancyColor = new HashMap<>();
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
        cwAdp.notifyDataSetChanged();
        twAdp.notifyDataSetChanged();
        cbAdp.notifyDataSetChanged();
        tbAdp.notifyDataSetChanged();
        toAdp.notifyDataSetChanged();
        coAdp.notifyDataSetChanged();
        poAdp.notifyDataSetChanged();
        goAdp.notifyDataSetChanged();
        shapeAdp.notifyDataSetChanged();
        labAdp.notifyDataSetChanged();
        colorAdp.notifyDataSetChanged();
        intencityAdp.notifyDataSetChanged();
        overtoneAdp.notifyDataSetChanged();
        fancycolorAdp.notifyDataSetChanged();
        clarityAdp.notifyDataSetChanged();
        cutAdp.notifyDataSetChanged();
        polishAdp.notifyDataSetChanged();
        symmetryAdp.notifyDataSetChanged();
        fiAdp.notifyDataSetChanged();
        bgmAdp.notifyDataSetChanged();
        inclusionAdp.notifyDataSetChanged();
        nattsAdp.notifyDataSetChanged();
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
        tv3VG2.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tv3VG2.setTextColor(getResources().getColor(R.color.colorPrimary));
        vid = true;
        img = true;
        imgVideo.setImageDrawable(getResources().getDrawable(R.drawable.ic_un_v));
        rlVideo.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        imgVideo.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
        imgImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_un_i));
        rlImage.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        imgImage.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);

        etFromDate.setText(new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date()));
        etToDate.setText(new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date()));

        tvBlankSymbol.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvBlankSymbol.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvBlankLength.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvBlankLength.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvBlankWidth.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvBlankWidth.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvBlankDepth.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvBlankDepth.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvBlankDepthPer.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvBlankDepthPer.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvBlankTablePer.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvBlankTablePer.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvBlankCrAng.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvBlankCrAng.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvBlankCrHit.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvBlankCrHit.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvBlankPavAng.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvBlankPavAng.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvBlankPavHit.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
        tvBlankPavHit.setTextColor(getResources().getColor(R.color.colorPrimary));
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
       // date.getDatePicker().setMaxDate(new Date().getTime() - 10000);
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

    private void openTransID() {
        dialogTrans = new Dialog(context);
        dialogTrans.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogTrans.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogTrans.setContentView(R.layout.layout_dialog);
        final TextView tvTitle = dialogTrans.findViewById(R.id.tvTitle);
        trans_list = dialogTrans.findViewById(R.id.pointer_list);
        all = dialogTrans.findViewById(R.id.cbAll);
        final Button btnBack = dialogTrans.findViewById(R.id.btnBack);
        final Button btnOk = dialogTrans.findViewById(R.id.btnOk);

        tvTitle.setText("Select Trans ID");

        if (arrayTransId != null) {
            trans_list.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.layout_checkedtextview, arrayTransId));
        }

        if (lsttransid != null) {
            String[] lst = lsttransid.split(",");

            for (int j = 0; j < trans_list.getCount(); j++) {
                for (int i = 0; i < lst.length; i++) {
                    if (lst[i].contains(trans_list.getItemAtPosition(j).toString())) {
                        trans_list.setItemChecked(j, true);
                    }
                }
            }
            if (trans_list.getCount() == lst.length) {
                all.setChecked(true);
            }

        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogTrans.dismiss();
                final ArrayList<String> selected = new ArrayList<String>();
                selectedTrans = new ArrayList<String>();
                TransID = "";
                for (int i = 0; i < trans_list.getCount(); i++) {
                    if (trans_list.isItemChecked(i)) {
                        selectedTrans.add(getTraID(trans_list.getItemAtPosition(i).toString()));
                        selected.add(trans_list.getItemAtPosition(i).toString());
                    }
                }
                if (selected.size() != 0) {
                     TransID = selectedTrans.toString().replace("[", "").replace("]", "").trim();
                     etTransId.setText(selected.size() + " - Selected ");
                } else {
                     etTransId.setText(null);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogTrans.dismiss();
            }
        });


        trans_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                if (trans_list.isItemChecked(position)) {
                    if (trans_list.getCheckedItemCount() == arrayTransId.size()) {
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
                    for (int i = 0; i < trans_list.getCount(); i++) {
                        trans_list.setItemChecked(i, true);
                        all.setChecked(true);
                    }
                } else {

                    for (int i = 0; i < trans_list.getCount(); i++) {
                        trans_list.setItemChecked(i, false);
                        all.setChecked(false);
                    }
                }
            }
        });
    }

    private String getTraID(String itemname) {
        String idd = "";
        for (ModelClass pojo : modelClassList) {
            if (pojo.getOfferName().equals(itemname)) {
                idd = pojo.getTransID();
                break;
            }
        }
        return idd;
    }

//    private void getTransID() {
//        Const.showProgress(context);
//        arrayTransId = new ArrayList<>();
//        selectedTransId = new HashMap<>();
//        final Map<String, String> map = new HashMap<>();
//        map.put("FromDate", Const.notNullString(etFromDate.getText().toString().trim(),""));
//        map.put("ToDate",  Const.notNullString(etToDate.getText().toString().trim(),""));
//        Const.callPostApi(context, "LabStock/GetTransId", map, new VolleyCallback() {
//            @Override
//            public void onSuccessResponse(String result) {
//                Const.dismissProgress();
//                try {
//                    JSONObject object = new JSONObject(result);
//                    if (object.optString("Msg").equalsIgnoreCase("success")) {
//                        JSONArray array = object.optJSONArray("Data");
//                        if (array.length() != 0) {
//                            for (int i = 0; i < array.length(); i++) {
//                                JSONObject object1 = array.optJSONObject(i);
//                                Iterator<String> keys = object1.keys();
//                                HashMap<String, String> hm = new HashMap<>();
//                                while (keys.hasNext()) {
//                                    String key = keys.next();
//                                    hm.put(key.toLowerCase(), object1.optString(key).trim());
//                                }
//                                arrayTransId.add(hm.get("transid") + "-" + hm.get("offername"));
//                                ModelClass aClass = new ModelClass();
//                                aClass.setTransID(hm.get("transid") );
//                                aClass.setOfferName(hm.get("transid") + "-" + hm.get("offername"));
//                                modelClassList.add(aClass);
//                                if (hm.get("transid") != null) {
//                                    trans_lst.add(hm.get("transid"));
//                                } else {
//                                    trans_lst.add("");
//                                }
//                               // arrayTrans.add(hm.get("transid"));
//                                selectedTransId.put(i, "");
//                            }
//                        }
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                if (arrayTransId.size() != 0) {
//                    lsttransid = etTransId.getText().toString().trim();
//                    openTransID();
//                    dialogTrans.show();
//                } else {
//                    Const.showErrorDialog(context, "Trans ID Not Available");
//                }
//            }
//
//            @Override
//            public void onFailerResponse(String error) {
//                Const.dismissProgress();
//                Runnable runnable = new Runnable() {
//                    @Override
//                    public void run() {
//                        getTransID();
//                    }
//                };
//                Const.showErrorApiDialog(context, runnable);
//            }
//        });
//    }

    private void getTransID() {
        Const.showProgress(context);
        arrayTransId = new ArrayList<>();
        selectedTransId = new HashMap<>();
        final Map<String, String> map = new HashMap<>();
        map.put("FromDate", Const.notNullString(etFromDate.getText().toString().trim(),""));
        map.put("ToDate",  Const.notNullString(etToDate.getText().toString().trim(),""));
        Const.callPostApi(context, "LabStock/Lab_GetTransId", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Log.e("Trans ID : ", result);
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
                                arrayTransId.add(hm.get("transid") + "-" + hm.get("offername"));
                                ModelClass aClass = new ModelClass();
                                aClass.setTransID(hm.get("transid") );
                                aClass.setOfferName(hm.get("transid") + "-" + hm.get("offername"));
                                modelClassList.add(aClass);
                                if (hm.get("transid") != null) {
                                    trans_lst.add(hm.get("transid"));
                                } else {
                                    trans_lst.add("");
                                }
                                // arrayTrans.add(hm.get("transid"));
                                selectedTransId.put(i, "");
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (arrayTransId.size() != 0) {
                    lsttransid = etTransId.getText().toString().trim();
                    openTransID();
                    dialogTrans.show();
                } else {
                    Const.showErrorDialog(context, "Trans ID Not Available");
                }
            }

            @Override
            public void onFailerResponse(String error) {
                Const.dismissProgress();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        getTransID();
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
        arrayShape = new ArrayList<>();
        arrayShape.add("ALL");
        arrayShapeImage = new ArrayList<>();
        arrayShapeImage.add("ALL");
        arrayColor = new ArrayList<>();
        arrayIntencity = new ArrayList<>();
        arrayOvertone = new ArrayList<>();
        arrayFancyColor = new ArrayList<>();
        arrayClarity = new ArrayList<>();
        arrayLab = new ArrayList<>();
        arrayCut = new ArrayList<>();
        arrayPolish = new ArrayList<>();
        arraySymm = new ArrayList<>();
        arrayFi = new ArrayList<>();
        arrayBgm = new ArrayList<>();
        arrayTB = new ArrayList<>();
        arrayCB = new ArrayList<>();
        arrayTW = new ArrayList<>();
        arrayCW = new ArrayList<>();
        arrayTO = new ArrayList<>();
        arrayCO = new ArrayList<>();
        arrayPO = new ArrayList<>();
        arrayGO = new ArrayList<>();
        final Map<String, String> map = new HashMap<>();
        map.put("ListValue", "DP");
        Const.callPostApi(context, "Stock/GetListValue", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Log.e("ListValue : ", result);
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
                                if (object1.optString("ListType").equalsIgnoreCase("SHAPE")) {
                                    arrayShape.add(object1.optString("Value"));
                                    arrayShapeImage.add(object1.optString("UrlValueHov"));
                                }
                                if (object1.optString("ListType").equalsIgnoreCase("COLOR")) {
                                    arrayColor.add(object1.optString("Value"));
                                }
                                if (object1.optString("ListType").equalsIgnoreCase("CLARITY")) {
                                    arrayClarity.add(object1.optString("Value"));
                                }
                                if (object1.optString("ListType").equalsIgnoreCase("LAB")) {
                                    arrayLab.add(object1.optString("Value"));
                                }
                                if (object1.optString("ListType").equalsIgnoreCase("CUT")) {
                                    arrayCut.add(object1.optString("Value"));
                                }
                                if (object1.optString("ListType").equalsIgnoreCase("POLISH")) {
                                    arrayPolish.add(object1.optString("Value"));
                                }
                                if (object1.optString("ListType").equalsIgnoreCase("SYMM")) {
                                    arraySymm.add(object1.optString("Value"));
                                }
                                if (object1.optString("ListType").equalsIgnoreCase("FLS")) {
                                    arrayFi.add(object1.optString("Value"));
                                }
                                if (object1.optString("ListType").equalsIgnoreCase("BGM")) {
                                    arrayBgm.add(object1.optString("Value"));
                                }
                                if (object1.optString("ListType").equalsIgnoreCase("TABLE_NATTS")) {
                                    arrayTB.add(object1.optString("Value"));
                                }
                                if (object1.optString("ListType").equalsIgnoreCase("CROWN_NATTS")) {
                                    arrayCB.add(object1.optString("Value"));
                                }
                                if (object1.optString("ListType").equalsIgnoreCase("TABLE_INCL")) {
                                    arrayTW.add(object1.optString("Value"));
                                }
                                if (object1.optString("ListType").equalsIgnoreCase("CROWN_INCL")) {
                                    arrayCW.add(object1.optString("Value"));
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
                            }
                            arrayShape.add("OTHERS");
                            arrayShapeImage.add("OTHERS");

                            arrayBgm.add("BLANK");
                            arrayTB.add("BLANK");
                            arrayCB.add("BLANK");
                            arrayTW.add("BLANK");
                            arrayCW.add("BLANK");
                            arrayTO.add("BLANK");
                            arrayCO.add("BLANK");
                            arrayPO.add("BLANK");
                            arrayGO.add("BLANK");

                            shapeAdp = new ShapeNewAdp(context, arrayShape, arrayShapeImage);
                            rvShape.setAdapter(shapeAdp);

                            colorAdp = new ColorAdp(context, arrayColor);
                            rvColor.setAdapter(colorAdp);

                            intencityAdp = new IntencityAdp(context, getResources().getStringArray(R.array.intencity));
                            rvIntencity.setAdapter(intencityAdp);

                            overtoneAdp = new OvertoneAdp(context, getResources().getStringArray(R.array.overtone));
                            rvOvertone.setAdapter(overtoneAdp);

                            fancycolorAdp = new FancyColorAdp(context, getResources().getStringArray(R.array.fancycolor));
                            rvFancyColor.setAdapter(fancycolorAdp);

                            clarityAdp = new ClarityAdp(context, arrayClarity);
                            rvClarity.setAdapter(clarityAdp);

                            labAdp = new LabAdp(context, arrayLab);
                            rvLab.setAdapter(labAdp);

                            cutAdp = new CutAdp(context, arrayCut);
                            rvCut.setAdapter(cutAdp);

                            polishAdp = new PolishAdp(context, arrayPolish);
                            rvPolish.setAdapter(polishAdp);

                            symmetryAdp = new SymmetryAdp(context, arraySymm);
                            rvSymmetry.setAdapter(symmetryAdp);

                            fiAdp = new FiAdp(context, arrayFi);
                            rvFi.setAdapter(fiAdp);

                            bgmAdp = new BgmAdp(context, arrayBgm);
                            rvBgm.setAdapter(bgmAdp);

                            tbAdp = new TableBlackAdp(context, arrayTB);
                            rvTableBlack.setAdapter(tbAdp);

                            cbAdp = new CrownBlackAdp(context, arrayCB);
                            rvCrownBlack.setAdapter(cbAdp);

                            twAdp = new TableWhiteAdp(context, arrayTW);
                            rvTableWhite.setAdapter(twAdp);

                            cwAdp = new CrownWhiteAdp(context, arrayCW);
                            rvCrownWhite.setAdapter(cwAdp);

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

//    private void DownloadExcel(final String IsCustomer) {
//        Const.showProgress(context);
//        final Map<String, String> map = new HashMap<>();
//        map.put("Pointer", Pref.getStringValue(context, Const.pointer, ""));
//        map.put("RefNo", Pref.getStringValue(context, Const.stone_id, ""));
//        map.put("Shape", Pref.getStringValue(context, Const.shape, ""));
//        map.put("Color", Pref.getStringValue(context, Const.color, ""));
//        map.put("Clarity", Pref.getStringValue(context, Const.clarity, ""));
//        map.put("Cut", Pref.getStringValue(context, Const.cut, ""));
//        map.put("Polish", Pref.getStringValue(context, Const.polish, ""));
//        map.put("Symm", Pref.getStringValue(context, Const.symmetry, ""));
//        map.put("Fls", Pref.getStringValue(context, Const.fi, ""));
//        map.put("Lab", Pref.getStringValue(context, Const.lab, ""));
//        map.put("FromDate", Pref.getStringValue(context, Const.from_date, ""));
//        map.put("ToDate", Pref.getStringValue(context, Const.to_date, ""));
//        map.put("TransId", Pref.getStringValue(context, Const.TransId, ""));
//        map.put("Tablewhite", Pref.getStringValue(context, Const.inclusion, ""));
//        map.put("Tableblack", Pref.getStringValue(context, Const.natts, ""));
//        map.put("FromDisc", Pref.getStringValue(context, Const.from_disc, "0"));
//        map.put("ToDisc", Pref.getStringValue(context, Const.to_disc, "0"));
//        map.put("FromPrice", Pref.getStringValue(context, Const.from_pcu, "0"));
//        map.put("ToPrice", Pref.getStringValue(context, Const.to_pcu, "0"));
//        map.put("FromAmount", Pref.getStringValue(context, Const.from_na, "0"));
//        map.put("ToAmount", Pref.getStringValue(context, Const.to_na, "0"));
//        map.put("FromDepth", Pref.getStringValue(context, Const.from_depth, "0"));
//        map.put("ToDepth", Pref.getStringValue(context, Const.to_depth, "0"));
//        map.put("FromLength", Pref.getStringValue(context, Const.from_length, "0"));
//        map.put("ToLength", Pref.getStringValue(context, Const.to_length, "0"));
//        map.put("FromWidth", Pref.getStringValue(context, Const.from_width, "0"));
//        map.put("ToWidth", Pref.getStringValue(context, Const.to_width, "0"));
//        map.put("FromDepthPer", Pref.getStringValue(context, Const.from_depth_p, "0"));
//        map.put("ToDepthPer", Pref.getStringValue(context, Const.to_depth_p, "0"));
//        map.put("FromTablePer", Pref.getStringValue(context, Const.from_table_p, "0"));
//        map.put("ToTablePer", Pref.getStringValue(context, Const.to_table_p, "0"));
//        map.put("Crownwhite", Pref.getStringValue(context, Const.crown_inclusion, "0"));
//        map.put("Crownblack", Pref.getStringValue(context, Const.crown_natts, "0"));
//        map.put("FromCrownangle", Pref.getStringValue(context, Const.from_crang_p, "0"));
//        map.put("ToCrownangle", Pref.getStringValue(context, Const.to_crang_p, "0"));
//        map.put("FromCrownheight", Pref.getStringValue(context, Const.from_crht_p, "0"));
//        map.put("ToCrownheight", Pref.getStringValue(context, Const.to_crht_p, "0"));
//        map.put("FromPavangle", Pref.getStringValue(context, Const.from_pavang_p, "0"));
//        map.put("ToPavangle", Pref.getStringValue(context, Const.to_pavang_p, "0"));
//        map.put("FromPavheight", Pref.getStringValue(context, Const.from_pavht_p, "0"));
//        map.put("ToPavheight", Pref.getStringValue(context, Const.to_pavang_p, "0"));
//        map.put("IsCustomer", Const.notNullString(IsCustomer,""));
//
//        Log.e("Lab Stock Param : ", map.toString());
//        Const.callPostApi(context, "LabStock/CustomerExcel", map, new VolleyCallback() {
//            @Override
//            public void onSuccessResponse(String result1) {
//               // Const.dismissProgress();
//                try {
//                    String result = result1.replaceAll("^[\"']+|[\"']+$", "");
//                    if (!result.equalsIgnoreCase("No data found.") && (!result.startsWith("Something Went wrong"))){
//                        Date date = new Date();
//                        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy") ;
//
//                        Random objGenerator = new Random();
//                        for (int iCount = 0; iCount< 1; iCount++){
//                            downloadNumber = String.valueOf(objGenerator.nextInt(1000));
//                            System.out.println("Random No : " + downloadNumber);
//                        }
//                        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), dateFormat.format(date) + "-" +  downloadNumber + ".xlsx");
//                        registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
//                        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
//                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(result.toString()));
//                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
//                        request.setTitle(dateFormat.format(date) + "-" + downloadNumber);
//                        request.allowScanningByMediaScanner();
//                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, dateFormat.format(date) + "-" + downloadNumber + ".xlsx");
//                        request.setMimeType(MimeTypeMap.getSingleton().getMimeTypeFromExtension(
//                                MimeTypeMap.getFileExtensionFromUrl(result.toString())));
//                        downID = downloadManager.enqueue(request);
//
//                    } else {
//                        Const.dismissProgress();
//                        Const.showErrorDialog(context,"No data found.");
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailerResponse(String error) {
//                Const.dismissProgress();
//                Runnable runnable = new Runnable() {
//                    @Override
//                    public void run() {
//                        DownloadExcel(IsCustomer);
//                    }
//                };
//                Const.showErrorApiDialog(context, runnable);
//            }
//        });
//    }

    private void DownloadExcel(final String IsCustomer) {
        Const.showProgress(context);
        final Map<String, String> map = new HashMap<>();
        map.put("RefNo", Pref.getStringValue(context, Const.stone_id, ""));
        map.put("iVendor", Pref.getStringValue(context, Const.TransId, ""));
        map.put("sShape", Pref.getStringValue(context, Const.shape, ""));
        map.put("sPointer", Pref.getStringValue(context, Const.pointer, ""));
        map.put("sColorType", Pref.getStringValue(context, Const.colortype, ""));
        map.put("sColor", Pref.getStringValue(context, Const.color, ""));
        map.put("sINTENSITY", Pref.getStringValue(context, Const.intencitycolor, ""));
        map.put("sOVERTONE", Pref.getStringValue(context, Const.overtonecolor, ""));
        map.put("sFANCY_COLOR", Pref.getStringValue(context, Const.fancycolor, ""));
        map.put("sClarity", Pref.getStringValue(context, Const.clarity, ""));
        map.put("sCut", Pref.getStringValue(context, Const.cut, ""));
        map.put("sPolish", Pref.getStringValue(context, Const.polish, ""));
        map.put("sSymm", Pref.getStringValue(context, Const.symmetry, ""));
        map.put("sFls", Pref.getStringValue(context, Const.fi, ""));
        map.put("sLab", Pref.getStringValue(context, Const.lab, ""));
        map.put("FromDate", Pref.getStringValue(context, Const.from_date, ""));
        map.put("ToDate", Pref.getStringValue(context, Const.to_date, ""));
        map.put("dFromDisc", Pref.getStringValue(context, Const.from_disc, ""));
        map.put("dToDisc", Pref.getStringValue(context, Const.to_disc, ""));
//        map.put("FromPrice", Pref.getStringValue(context, Const.from_pcu, "0"));
//        map.put("ToPrice", Pref.getStringValue(context, Const.to_pcu, "0"));
        map.put("dFromTotAmt", Pref.getStringValue(context, Const.from_na, ""));
        map.put("dToTotAmt", Pref.getStringValue(context, Const.to_na, ""));
        map.put("dFromLength", Pref.getStringValue(context, Const.from_length, ""));
        map.put("dToLength", Pref.getStringValue(context, Const.to_length, ""));
        map.put("dFromWidth", Pref.getStringValue(context, Const.from_width, ""));
        map.put("dToWidth", Pref.getStringValue(context, Const.to_width, ""));
        map.put("dFromDepth", Pref.getStringValue(context, Const.from_depth, ""));
        map.put("dToDepth", Pref.getStringValue(context, Const.to_depth, ""));
        map.put("dFromDepthPer", Pref.getStringValue(context, Const.from_depth_p, ""));
        map.put("dToDepthPer", Pref.getStringValue(context, Const.to_depth_p, ""));
        map.put("dFromTablePer", Pref.getStringValue(context, Const.from_table_p, ""));
        map.put("dToTablePer", Pref.getStringValue(context, Const.to_table_p, ""));
        map.put("dFromCrAng", Pref.getStringValue(context, Const.from_crang_p, ""));
        map.put("dToCrAng", Pref.getStringValue(context, Const.to_crang_p, ""));
        map.put("dFromCrHt", Pref.getStringValue(context, Const.from_crht_p, ""));
        map.put("dToCrHt", Pref.getStringValue(context, Const.to_crht_p, ""));
        map.put("dFromPavAng", Pref.getStringValue(context, Const.from_pavang_p, ""));
        map.put("dToPavAng", Pref.getStringValue(context, Const.to_pavang_p, ""));
        map.put("dFromPavHt", Pref.getStringValue(context, Const.from_pavht_p, ""));
        map.put("dToPavHt", Pref.getStringValue(context, Const.to_pavht_p, ""));

        map.put("dKeytosymbol", Pref.getStringValue(context, Const.keytosymbol, ""));
        map.put("dCheckKTS", Pref.getStringValue(context, Const.keytosymboltrue, ""));
        map.put("dUNCheckKTS", Pref.getStringValue(context, Const.keytosymbolfalse, ""));
        map.put("sBGM", Pref.getStringValue(context, Const.bgm, ""));

        map.put("sCrownBlack", Pref.getStringValue(context, Const.crown_natts, ""));
        map.put("sTableBlack", Pref.getStringValue(context, Const.natts, ""));
        map.put("sCrownWhite", Pref.getStringValue(context, Const.crown_inclusion, ""));
        map.put("sTableWhite", Pref.getStringValue(context, Const.inclusion, ""));
        map.put("sTableOpen", Pref.getStringValue(context, Const.table_open, ""));
        map.put("sCrownOpen", Pref.getStringValue(context, Const.crown_open, ""));
        map.put("sPavOpen", Pref.getStringValue(context, Const.pav_open, ""));
        map.put("sGirdleOpen", Pref.getStringValue(context, Const.girdle_open, ""));


        map.put("Img", Pref.getStringValue(context, Const.image, ""));
        map.put("Vdo", Pref.getStringValue(context, Const.video, ""));
        map.put("ExcelType", Const.notNullString(IsCustomer,""));

        map.put("KTSBlank", Pref.getStringValue(context, Const.blank_symbol, ""));
        map.put("LengthBlank", Pref.getStringValue(context, Const.blank_length, ""));
        map.put("WidthBlank", Pref.getStringValue(context, Const.blank_width, ""));
        map.put("DepthBlank", Pref.getStringValue(context, Const.blank_depth, ""));
        map.put("DepthPerBlank", Pref.getStringValue(context, Const.blank_depth_per, ""));
        map.put("TablePerBlank", Pref.getStringValue(context, Const.blank_table_per, ""));
        map.put("CrAngBlank", Pref.getStringValue(context, Const.blank_cr_ang, ""));
        map.put("CrHtBlank", Pref.getStringValue(context, Const.blank_cr_hit, ""));
        map.put("PavAngBlank", Pref.getStringValue(context, Const.blank_pav_ang, ""));
        map.put("PavHtBlank", Pref.getStringValue(context, Const.blank_pav_hit, ""));

        Log.e("Lab Stock Param : ", map.toString());
        Const.callPostApi(context, "LabStock/LabSearchExcel_MobileApp", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result1) {
                // Const.dismissProgress();
                Log.e("Lab Stock Result : ", result1);
                try {
                    String result = result1.replaceAll("^[\"']+|[\"']+$", "");
                    if (!result.equalsIgnoreCase("No data found.") && (!result.startsWith("Something Went wrong"))){
                        Date date = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy") ;

                        Random objGenerator = new Random();
                        for (int iCount = 0; iCount< 1; iCount++){
                            downloadNumber = String.valueOf(objGenerator.nextInt(1000));
                            System.out.println("Random No : " + downloadNumber);
                        }
                        result = result.replaceAll(" ", "%20");
                        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), dateFormat.format(date) + "-" +  downloadNumber + ".xlsx");
                        registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(result.toString()));
                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                        request.setTitle(dateFormat.format(date) + "-" + downloadNumber);
                        request.allowScanningByMediaScanner();
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, dateFormat.format(date) + "-" + downloadNumber + ".xlsx");
                        request.setMimeType(MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                                MimeTypeMap.getFileExtensionFromUrl(result.toString())));
                        downID = downloadManager.enqueue(request);

                    } else {
                        Const.dismissProgress();
                        Const.showErrorDialog(context,"No data found.");
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
                        DownloadExcel(IsCustomer);
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy") ;
            if (downID == id) {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), dateFormat.format(date) + "-" + downloadNumber + ".xlsx");
                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.setType("application/vnd.ms-excel");
                Uri uri = FileProvider.getUriForFile(LabStockSearchActivity.this, BuildConfig.APPLICATION_ID + ".provider",file);
                share.putExtra(Intent.EXTRA_STREAM, uri);
                share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                builder.detectFileUriExposure();
                openDownloadedAttachment(context, downID);
            }
            unregisterReceiver(onDownloadComplete);
        }
    };

    private void openDownloadedAttachment(final Context context, final long downloadId) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
        Cursor cursor = downloadManager.query(query);
        if (cursor.moveToFirst()) {
            int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            String downloadLocalUri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
            String downloadMimeType = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE));
            if ((downloadStatus == DownloadManager.STATUS_SUCCESSFUL) && downloadLocalUri != null) {
                openDownloadedAttachment(context, Uri.parse(downloadLocalUri), downloadMimeType);
            }
        }
        cursor.close();
    }

    private void openDownloadedAttachment(final Context context, Uri attachmentUri, final String attachmentMimeType) {
        if(attachmentUri!=null) {
            // Get Content Uri.
            if (ContentResolver.SCHEME_FILE.equals(attachmentUri.getScheme())) {
                // FileUri - Convert it to contentUri.
                File file = new File(attachmentUri.getPath());
                attachmentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider",file);
            }

            final Intent openAttachmentIntent = new Intent(Intent.ACTION_VIEW);
            openAttachmentIntent.setDataAndType(attachmentUri, attachmentMimeType);
            openAttachmentIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
            dialog.setCancelable(false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.layout_update_dialog);

            TextView tvMessage = (TextView) dialog.findViewById(R.id.tvMessage);
            Button btnYes = (Button) dialog.findViewById(R.id.btnYes);
            Button btnNo = (Button) dialog.findViewById(R.id.btnNo);

            tvMessage.setText("Do you want to open this file ?");
            btnYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Const.dismissProgress();
                    dialog.dismiss();
                    try {
                        context.startActivity(openAttachmentIntent);
                        dialog.dismiss();
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(context, "No Apps Available to Open File", Toast.LENGTH_LONG).show();
                    }
                }
            });
            btnNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isNetworkAvailable()) {
                        Const.dismissProgress();
                        dialog.dismiss();
                    }
                }
            });
            dialog.show();
        }
    }

    private void ShareExcel(final String IsCustomer) {
        Const.showProgress(context);
        final Map<String, String> map = new HashMap<>();
        map.put("RefNo", Pref.getStringValue(context, Const.stone_id, ""));
        map.put("iVendor", Pref.getStringValue(context, Const.TransId, ""));
        map.put("sShape", Pref.getStringValue(context, Const.shape, ""));
        map.put("sPointer", Pref.getStringValue(context, Const.pointer, ""));
        map.put("sColorType", Pref.getStringValue(context, Const.colortype, ""));
        map.put("sColor", Pref.getStringValue(context, Const.color, ""));
        map.put("sINTENSITY", Pref.getStringValue(context, Const.intencitycolor, ""));
        map.put("sOVERTONE", Pref.getStringValue(context, Const.overtonecolor, ""));
        map.put("sFANCY_COLOR", Pref.getStringValue(context, Const.fancycolor, ""));
        map.put("sClarity", Pref.getStringValue(context, Const.clarity, ""));
        map.put("sCut", Pref.getStringValue(context, Const.cut, ""));
        map.put("sPolish", Pref.getStringValue(context, Const.polish, ""));
        map.put("sSymm", Pref.getStringValue(context, Const.symmetry, ""));
        map.put("sFls", Pref.getStringValue(context, Const.fi, ""));
        map.put("sLab", Pref.getStringValue(context, Const.lab, ""));
        map.put("FromDate", Pref.getStringValue(context, Const.from_date, ""));
        map.put("ToDate", Pref.getStringValue(context, Const.to_date, ""));
        map.put("dFromDisc", Pref.getStringValue(context, Const.from_disc, ""));
        map.put("dToDisc", Pref.getStringValue(context, Const.to_disc, ""));
//        map.put("FromPrice", Pref.getStringValue(context, Const.from_pcu, "0"));
//        map.put("ToPrice", Pref.getStringValue(context, Const.to_pcu, "0"));
        map.put("dFromTotAmt", Pref.getStringValue(context, Const.from_na, ""));
        map.put("dToTotAmt", Pref.getStringValue(context, Const.to_na, ""));
        map.put("dFromLength", Pref.getStringValue(context, Const.from_length, ""));
        map.put("dToLength", Pref.getStringValue(context, Const.to_length, ""));
        map.put("dFromWidth", Pref.getStringValue(context, Const.from_width, ""));
        map.put("dToWidth", Pref.getStringValue(context, Const.to_width, ""));
        map.put("dFromDepth", Pref.getStringValue(context, Const.from_depth, ""));
        map.put("dToDepth", Pref.getStringValue(context, Const.to_depth, ""));
        map.put("dFromDepthPer", Pref.getStringValue(context, Const.from_depth_p, ""));
        map.put("dToDepthPer", Pref.getStringValue(context, Const.to_depth_p, ""));
        map.put("dFromTablePer", Pref.getStringValue(context, Const.from_table_p, ""));
        map.put("dToTablePer", Pref.getStringValue(context, Const.to_table_p, ""));
        map.put("dFromCrAng", Pref.getStringValue(context, Const.from_crang_p, ""));
        map.put("dToCrAng", Pref.getStringValue(context, Const.to_crang_p, ""));
        map.put("dFromCrHt", Pref.getStringValue(context, Const.from_crht_p, ""));
        map.put("dToCrHt", Pref.getStringValue(context, Const.to_crht_p, ""));
        map.put("dFromPavAng", Pref.getStringValue(context, Const.from_pavang_p, ""));
        map.put("dToPavAng", Pref.getStringValue(context, Const.to_pavang_p, ""));
        map.put("dFromPavHt", Pref.getStringValue(context, Const.from_pavht_p, ""));
        map.put("dToPavHt", Pref.getStringValue(context, Const.to_pavht_p, ""));

        map.put("dKeytosymbol", Pref.getStringValue(context, Const.keytosymbol, ""));
        map.put("dCheckKTS", Pref.getStringValue(context, Const.keytosymboltrue, ""));
        map.put("dUNCheckKTS", Pref.getStringValue(context, Const.keytosymbolfalse, ""));
        map.put("sBGM", Pref.getStringValue(context, Const.bgm, ""));

        map.put("sCrownBlack", Pref.getStringValue(context, Const.crown_natts, ""));
        map.put("sTableBlack", Pref.getStringValue(context, Const.natts, ""));
        map.put("sCrownWhite", Pref.getStringValue(context, Const.crown_inclusion, ""));
        map.put("sTableWhite", Pref.getStringValue(context, Const.inclusion, ""));
        map.put("sTableOpen", Pref.getStringValue(context, Const.table_open, ""));
        map.put("sCrownOpen", Pref.getStringValue(context, Const.crown_open, ""));
        map.put("sPavOpen", Pref.getStringValue(context, Const.pav_open, ""));
        map.put("sGirdleOpen", Pref.getStringValue(context, Const.girdle_open, ""));


        map.put("Img", Pref.getStringValue(context, Const.image, ""));
        map.put("Vdo", Pref.getStringValue(context, Const.video, ""));
        map.put("ExcelType", Const.notNullString(IsCustomer,""));

        map.put("KTSBlank", Pref.getStringValue(context, Const.blank_symbol, ""));
        map.put("LengthBlank", Pref.getStringValue(context, Const.blank_length, ""));
        map.put("WidthBlank", Pref.getStringValue(context, Const.blank_width, ""));
        map.put("DepthBlank", Pref.getStringValue(context, Const.blank_depth, ""));
        map.put("DepthPerBlank", Pref.getStringValue(context, Const.blank_depth_per, ""));
        map.put("TablePerBlank", Pref.getStringValue(context, Const.blank_table_per, ""));
        map.put("CrAngBlank", Pref.getStringValue(context, Const.blank_cr_ang, ""));
        map.put("CrHtBlank", Pref.getStringValue(context, Const.blank_cr_hit, ""));
        map.put("PavAngBlank", Pref.getStringValue(context, Const.blank_pav_ang, ""));
        map.put("PavHtBlank", Pref.getStringValue(context, Const.blank_pav_hit, ""));

        Log.e("Lab Stock Param : ", map.toString());

        Const.callPostApi(context, "LabStock/LabSearchExcel_MobileApp", map, new VolleyCallback() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onSuccessResponse(String result1) {
              //  Const.dismissProgress();
                Log.e("Lab Stock Result : ", result1);
                try {
                    String result = result1.replaceAll("^[\"']+|[\"']+$", "");
                    if (!result.equalsIgnoreCase("No data found.") && (!result.startsWith("Something Went wrong"))){
                        Date date = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy") ;

                        Random objGenerator = new Random();
                        for (int iCount = 0; iCount< 1; iCount++){
                            shareNumber = String.valueOf(objGenerator.nextInt(1000));
                            System.out.println("Random No : " + shareNumber);
                        }
                        result = result.replaceAll(" ", "%20");
                        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), dateFormat.format(date) + "-" +  shareNumber + ".xlsx");
                            registerReceiver(onShareComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                            shareManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(result.toString()));
                            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                            request.setTitle(dateFormat.format(date) + "-" + shareNumber);
                            request.allowScanningByMediaScanner();
                           // request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, dateFormat.format(date) + "-" + shareNumber + ".xlsx");
                            request.setMimeType(MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                                    MimeTypeMap.getFileExtensionFromUrl(result.toString())));
                            shareID = shareManager.enqueue(request);

                    } else {
                        Const.dismissProgress();
                        Const.showErrorDialog(context,"No data found.");
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
                        ShareExcel(IsCustomer);
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    private BroadcastReceiver onShareComplete = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onReceive(Context context, Intent intent) {
            Const.dismissProgress();
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy") ;
            if (shareID == id) {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), dateFormat.format(date) + "-" + shareNumber + ".xlsx");
                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.setType("application/vnd.ms-excel");
                Uri uri = FileProvider.getUriForFile(LabStockSearchActivity.this, BuildConfig.APPLICATION_ID + ".provider",file);
                share.putExtra(Intent.EXTRA_STREAM, uri);
                share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                builder.detectFileUriExposure();
                startActivity(Intent.createChooser(share, "Share via"));
            }
            unregisterReceiver(onShareComplete);
        }
    };
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

                    if(isSelectedAllShape){
                        selectedShape.remove(0);
                    }

                    arraySymbolFalse = new ArrayList<>();
                    arraySymbolTrue = new ArrayList<>();
                    arrayTransFalse = new ArrayList<>();
                    arrayTransTrue = new ArrayList<>();
                    Pref.removeValue(context, Const.stone_id);
                    Pref.removeValue(context, Const.certi_no);
                    Pref.setStringValue(context, Const.shape, new ArrayList<String>(selectedShape.values()).toString().replace("[", "").replace("]", "").replace(", ", ",").toUpperCase());
                    Pref.setStringValue(context, Const.color, new ArrayList<String>(selectedColor.values()).toString().replace("[", "").replace("]", "").replace(", ", ","));
                    Pref.setStringValue(context, Const.colortype, rbRegular.isChecked() ? "Regular" : "Fancy");
                    Pref.setStringValue(context, Const.intencitycolor, new ArrayList<String>(selectedIntencity.values()).toString().replace("[", "").replace("]", "").replace(", ", ","));
                    Pref.setStringValue(context, Const.overtonecolor, new ArrayList<String>(selectedOvertone.values()).toString().replace("[", "").replace("]", "").replace(", ", ","));
                    Pref.setStringValue(context, Const.fancycolor, new ArrayList<String>(selectedFancyColor.values()).toString().replace("[", "").replace("]", "").replace(", ", ","));
                    Pref.setStringValue(context, Const.clarity, new ArrayList<String>(selectedClarity.values()).toString().replace("[", "").replace("]", "").replace(", ", ","));
                    Pref.setStringValue(context, Const.cut, new ArrayList<String>(selectedCut.values()).toString().replace("[", "").replace("]", "").replace(", ", ",").replace("F","FR"));
                    Pref.setStringValue(context, Const.polish, new ArrayList<String>(selectedPolish.values()).toString().replace("[", "").replace("]", "").replace(", ", ","));
                    Pref.setStringValue(context, Const.symmetry, new ArrayList<String>(selectedSymmetry.values()).toString().replace("[", "").replace("]", "").replace(", ", ","));
                    Pref.setStringValue(context, Const.fi, new ArrayList<String>(selectedFi.values()).toString().replace("[", "").replace("]", "").replace(", ", ","));
                    Pref.setStringValue(context, Const.bgm, new ArrayList<String>(selectedBgm.values()).toString().replace("[", "").replace("]", "").replace(", ", ","));
                    Pref.setStringValue(context, Const.lab, new ArrayList<String>(selectedLab.values()).toString().replace("[", "").replace("]", "").replace(", ", ","));
                    Pref.setStringValue(context, Const.inclusion, new ArrayList<String>(selectedTW.values()).toString().replace("[", "").replace("]", "").replace(", ", ",").toUpperCase());
                    Pref.setStringValue(context, Const.natts, new ArrayList<String>(selectedTB.values()).toString().replace("[", "").replace("]", "").replace(", ", ",").toUpperCase());
                    Pref.setStringValue(context, Const.crown_inclusion, new ArrayList<String>(selectedCW.values()).toString().replace("[", "").replace("]", "").replace(", ", ",").toUpperCase());
                    Pref.setStringValue(context, Const.crown_natts, new ArrayList<String>(selectedCB.values()).toString().replace("[", "").replace("]", "").replace(", ", ",").toUpperCase());
                    Pref.setStringValue(context, Const.table_open, new ArrayList<String>(selectedTO.values()).toString().replace("[", "").replace("]", "").replace(", ", ",").toUpperCase());
                    Pref.setStringValue(context, Const.crown_open, new ArrayList<String>(selectedCO.values()).toString().replace("[", "").replace("]", "").replace(", ", ",").toUpperCase());
                    Pref.setStringValue(context, Const.pav_open, new ArrayList<String>(selectedPO.values()).toString().replace("[", "").replace("]", "").replace(", ", ",").toUpperCase());
                    Pref.setStringValue(context, Const.girdle_open, new ArrayList<String>(selectedGO.values()).toString().replace("[", "").replace("]", "").replace(", ", ",").toUpperCase());
                    Pref.setStringValue(context, Const.image, !img ? "Yes" : "");
                    Pref.setStringValue(context, Const.video, !vid ? "Yes" : "");
                    Pref.setStringValue(context, Const.pointer, tvSelectedSize.getText().toString());
                    Pref.setStringValue(context, Const.from_disc, Const.notNullString(etDisF.getText().toString(),""));
                    Pref.setStringValue(context, Const.to_disc, Const.notNullString(etDisT.getText().toString(),""));
                    Pref.setStringValue(context, Const.from_na, Const.notNullString(etTauF.getText().toString(),""));
                    Pref.setStringValue(context, Const.to_na, Const.notNullString(etTauT.getText().toString(),""));
                    Pref.setStringValue(context, Const.from_length, Const.notNullString(etLengthF.getText().toString(),""));
                    Pref.setStringValue(context, Const.to_length, Const.notNullString(etLengthT.getText().toString(),""));
                    Pref.setStringValue(context, Const.from_width, Const.notNullString(etWidthF.getText().toString(),""));
                    Pref.setStringValue(context, Const.to_width, Const.notNullString(etWidthT.getText().toString(),""));
                    Pref.setStringValue(context, Const.from_depth, Const.notNullString(etDepthF.getText().toString(),""));
                    Pref.setStringValue(context, Const.to_depth, Const.notNullString(etDepthT.getText().toString(),""));
                    Pref.setStringValue(context, Const.from_depth_p, Const.notNullString(etDepthPF.getText().toString(),""));
                    Pref.setStringValue(context, Const.to_depth_p, Const.notNullString(etDepthPT.getText().toString(),""));
                    Pref.setStringValue(context, Const.from_table_p, Const.notNullString(etTablePF.getText().toString(),""));
                    Pref.setStringValue(context, Const.to_table_p, Const.notNullString(etTablePT.getText().toString(),""));
                    Pref.setStringValue(context, Const.from_crang_p, Const.notNullString(etCrAngPF.getText().toString(),""));
                    Pref.setStringValue(context, Const.to_crang_p, Const.notNullString(etCrAngPT.getText().toString(),""));
                    Pref.setStringValue(context, Const.from_crht_p, Const.notNullString(etCrHtPF.getText().toString(),""));
                    Pref.setStringValue(context, Const.to_crht_p, Const.notNullString(etCrHtPT.getText().toString(),""));
                    Pref.setStringValue(context, Const.from_pavang_p, Const.notNullString(etPavAngPF.getText().toString(),""));
                    Pref.setStringValue(context, Const.to_pavang_p, Const.notNullString(etPavAngPT.getText().toString(),""));
                    Pref.setStringValue(context, Const.from_pavht_p, Const.notNullString(etPavHtPF.getText().toString(),""));
                    Pref.setStringValue(context, Const.to_pavht_p, Const.notNullString(etPavHtPT.getText().toString(),""));
                    Pref.setStringValue(context, Const.from_date, etFromDate.getText().toString());
                    Pref.setStringValue(context, Const.to_date, etToDate.getText().toString());
                    Pref.setStringValue(context, Const.from_cts, Const.notNullString(etFromCts.getText().toString(),""));
                    Pref.setStringValue(context, Const.to_cts, Const.notNullString(etToCts.getText().toString(),""));

                    String Cts = "";
                    if (!Const.isEmpty(etFromCts) & !Const.isEmpty(etToCts)) {
                         Cts = (etFromCts.getText().toString() + "-" + etToCts.getText().toString());
                    }

                    if(tvSelectedSize.getText().toString().isEmpty()) {
                        if (!Cts.isEmpty()) {
                            Pref.setStringValue(context, Const.pointer, Cts.toString() + "," + arraySpecificSize.toString().replace("[", "").replace("]", "").replace(", ", ","));
                        } else {
                            Pref.setStringValue(context, Const.pointer, arraySpecificSize.toString().replace("[", "").replace("]", "").replace(", ", ","));
                        }
                    }


                    if (!Const.isEmpty(etStoneOrCert)) {
                        Pref.setStringValue(context, Const.stone_id, etStoneOrCert.getText().toString().replaceAll(" ",",").replaceAll("\n",",").replaceAll("\t",",").replaceAll("\r",",").replaceAll("  ",","));
                    }
                    Pref.setStringValue(context, Const.TransId, Const.notNullString(TransID.toString().trim(),""));

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
                    Pref.setStringValue(context, Const.keytosymboltrue, tr);
                    Pref.setStringValue(context, Const.keytosymbolfalse, fa);

                    Pref.setStringValue(context, Const.blank_symbol, tvBlankSymbol.getCurrentTextColor() == getResources().getColor(R.color.colorPrimary) ? "" : "true");
                    Pref.setStringValue(context, Const.blank_length, tvBlankLength.getCurrentTextColor() == getResources().getColor(R.color.colorPrimary) ? "" : "true");
                    Pref.setStringValue(context, Const.blank_width, tvBlankWidth.getCurrentTextColor() == getResources().getColor(R.color.colorPrimary) ? "" : "true");
                    Pref.setStringValue(context, Const.blank_depth, tvBlankDepth.getCurrentTextColor() == getResources().getColor(R.color.colorPrimary) ? "" : "true");
                    Pref.setStringValue(context, Const.blank_depth_per, tvBlankDepthPer.getCurrentTextColor() == getResources().getColor(R.color.colorPrimary) ? "" : "true");
                    Pref.setStringValue(context, Const.blank_table_per, tvBlankTablePer.getCurrentTextColor() == getResources().getColor(R.color.colorPrimary) ? "" : "true");
                    Pref.setStringValue(context, Const.blank_cr_ang, tvBlankCrAng.getCurrentTextColor() == getResources().getColor(R.color.colorPrimary) ? "" : "true");
                    Pref.setStringValue(context, Const.blank_cr_hit, tvBlankCrHit.getCurrentTextColor() == getResources().getColor(R.color.colorPrimary) ? "" : "true");
                    Pref.setStringValue(context, Const.blank_pav_ang, tvBlankPavAng.getCurrentTextColor() == getResources().getColor(R.color.colorPrimary) ? "" : "true");
                    Pref.setStringValue(context, Const.blank_pav_hit, tvBlankPavHit.getCurrentTextColor() == getResources().getColor(R.color.colorPrimary) ? "" : "true");

                    View contentView = View.inflate(context, R.layout.layout_lab_download, null);
                    final Dialog sheetDialog = new Dialog(context, R.style.Theme_Dialog);
                    sheetDialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
                    Window window = sheetDialog.getWindow();
                    WindowManager.LayoutParams wlp = window.getAttributes();
                    wlp.gravity = Gravity.BOTTOM;
                    wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                    window.setAttributes(wlp);
                    sheetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    sheetDialog.setContentView(contentView);
                    sheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    ImageView tvClose = sheetDialog.findViewById(R.id.tvClose);
                    LinearLayout llCustExcel = sheetDialog.findViewById(R.id.llCustExcel);
                    LinearLayout llCustExcelImage = sheetDialog.findViewById(R.id.llCustExcelImage);
                    LinearLayout llSuppExcel = sheetDialog.findViewById(R.id.llSuppExcel);
                    llCustExcel.setBackgroundDrawable(Const.setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));
                    llCustExcelImage.setBackgroundDrawable(Const.setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));
                    llSuppExcel.setBackgroundDrawable(Const.setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));

                    if (Pref.getStringValue(context, Const.loginIsEmp, "").equalsIgnoreCase("1")) {
                        llCustExcelImage.setVisibility(View.VISIBLE);
                        llCustExcel.setVisibility(View.GONE);
                        llSuppExcel.setVisibility(View.GONE);
                    }

                    llCustExcel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DownloadExcel("1");
                            sheetDialog.dismiss();
                        }
                    });
                    llCustExcelImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DownloadExcel("2");
                            sheetDialog.dismiss();
                        }
                    });
                    llSuppExcel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DownloadExcel("3");
                            sheetDialog.dismiss();
                        }
                    });
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        tvClose.setBackground(Const.setBackgoundBorder(2, 40, Color.TRANSPARENT, Color.WHITE));
                    }
                    tvClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            sheetDialog.dismiss();
                        }
                    });
                    sheetDialog.show();
                    break;
                case R.id.llShare:
                    if(isSelectedAllShape){
                        selectedShape.remove(0);
                    }

                    arraySymbolFalse = new ArrayList<>();
                    arraySymbolTrue = new ArrayList<>();
                    arrayTransFalse = new ArrayList<>();
                    arrayTransTrue = new ArrayList<>();
                    Pref.removeValue(context, Const.stone_id);
                    Pref.removeValue(context, Const.certi_no);
                    Pref.setStringValue(context, Const.shape, new ArrayList<String>(selectedShape.values()).toString().replace("[", "").replace("]", "").replace(", ", ",").toUpperCase());
                    Pref.setStringValue(context, Const.color, new ArrayList<String>(selectedColor.values()).toString().replace("[", "").replace("]", "").replace(", ", ","));
                    Pref.setStringValue(context, Const.colortype, rbRegular.isChecked() ? "Regular" : "Fancy");
                    Pref.setStringValue(context, Const.intencitycolor, new ArrayList<String>(selectedIntencity.values()).toString().replace("[", "").replace("]", "").replace(", ", ","));
                    Pref.setStringValue(context, Const.overtonecolor, new ArrayList<String>(selectedOvertone.values()).toString().replace("[", "").replace("]", "").replace(", ", ","));
                    Pref.setStringValue(context, Const.fancycolor, new ArrayList<String>(selectedFancyColor.values()).toString().replace("[", "").replace("]", "").replace(", ", ","));
                    Pref.setStringValue(context, Const.clarity, new ArrayList<String>(selectedClarity.values()).toString().replace("[", "").replace("]", "").replace(", ", ","));
                    Pref.setStringValue(context, Const.cut, new ArrayList<String>(selectedCut.values()).toString().replace("[", "").replace("]", "").replace(", ", ",").replace("F","FR"));
                    Pref.setStringValue(context, Const.polish, new ArrayList<String>(selectedPolish.values()).toString().replace("[", "").replace("]", "").replace(", ", ","));
                    Pref.setStringValue(context, Const.symmetry, new ArrayList<String>(selectedSymmetry.values()).toString().replace("[", "").replace("]", "").replace(", ", ","));
                    Pref.setStringValue(context, Const.fi, new ArrayList<String>(selectedFi.values()).toString().replace("[", "").replace("]", "").replace(", ", ","));
                    Pref.setStringValue(context, Const.bgm, new ArrayList<String>(selectedBgm.values()).toString().replace("[", "").replace("]", "").replace(", ", ","));
                    Pref.setStringValue(context, Const.lab, new ArrayList<String>(selectedLab.values()).toString().replace("[", "").replace("]", "").replace(", ", ","));
                    Pref.setStringValue(context, Const.inclusion, new ArrayList<String>(selectedTW.values()).toString().replace("[", "").replace("]", "").replace(", ", ",").toUpperCase());
                    Pref.setStringValue(context, Const.natts, new ArrayList<String>(selectedTB.values()).toString().replace("[", "").replace("]", "").replace(", ", ",").toUpperCase());
                    Pref.setStringValue(context, Const.crown_inclusion, new ArrayList<String>(selectedCW.values()).toString().replace("[", "").replace("]", "").replace(", ", ",").toUpperCase());
                    Pref.setStringValue(context, Const.crown_natts, new ArrayList<String>(selectedCB.values()).toString().replace("[", "").replace("]", "").replace(", ", ",").toUpperCase());
                    Pref.setStringValue(context, Const.table_open, new ArrayList<String>(selectedTO.values()).toString().replace("[", "").replace("]", "").replace(", ", ",").toUpperCase());
                    Pref.setStringValue(context, Const.crown_open, new ArrayList<String>(selectedCO.values()).toString().replace("[", "").replace("]", "").replace(", ", ",").toUpperCase());
                    Pref.setStringValue(context, Const.pav_open, new ArrayList<String>(selectedPO.values()).toString().replace("[", "").replace("]", "").replace(", ", ",").toUpperCase());
                    Pref.setStringValue(context, Const.girdle_open, new ArrayList<String>(selectedGO.values()).toString().replace("[", "").replace("]", "").replace(", ", ",").toUpperCase());
                    Pref.setStringValue(context, Const.image, !img ? "Yes" : "");
                    Pref.setStringValue(context, Const.video, !vid ? "Yes" : "");
                    Pref.setStringValue(context, Const.pointer, tvSelectedSize.getText().toString());
                    Pref.setStringValue(context, Const.from_disc, Const.notNullString(etDisF.getText().toString(),""));
                    Pref.setStringValue(context, Const.to_disc, Const.notNullString(etDisT.getText().toString(),""));
                    Pref.setStringValue(context, Const.from_na, Const.notNullString(etTauF.getText().toString(),""));
                    Pref.setStringValue(context, Const.to_na, Const.notNullString(etTauT.getText().toString(),""));
                    Pref.setStringValue(context, Const.from_length, Const.notNullString(etLengthF.getText().toString(),""));
                    Pref.setStringValue(context, Const.to_length, Const.notNullString(etLengthT.getText().toString(),""));
                    Pref.setStringValue(context, Const.from_width, Const.notNullString(etWidthF.getText().toString(),""));
                    Pref.setStringValue(context, Const.to_width, Const.notNullString(etWidthT.getText().toString(),""));
                    Pref.setStringValue(context, Const.from_depth, Const.notNullString(etDepthF.getText().toString(),""));
                    Pref.setStringValue(context, Const.to_depth, Const.notNullString(etDepthT.getText().toString(),""));
                    Pref.setStringValue(context, Const.from_depth_p, Const.notNullString(etDepthPF.getText().toString(),""));
                    Pref.setStringValue(context, Const.to_depth_p, Const.notNullString(etDepthPT.getText().toString(),""));
                    Pref.setStringValue(context, Const.from_table_p, Const.notNullString(etTablePF.getText().toString(),""));
                    Pref.setStringValue(context, Const.to_table_p, Const.notNullString(etTablePT.getText().toString(),""));
                    Pref.setStringValue(context, Const.from_crang_p, Const.notNullString(etCrAngPF.getText().toString(),""));
                    Pref.setStringValue(context, Const.to_crang_p, Const.notNullString(etCrAngPT.getText().toString(),""));
                    Pref.setStringValue(context, Const.from_crht_p, Const.notNullString(etCrHtPF.getText().toString(),""));
                    Pref.setStringValue(context, Const.to_crht_p, Const.notNullString(etCrHtPT.getText().toString(),""));
                    Pref.setStringValue(context, Const.from_pavang_p, Const.notNullString(etPavAngPF.getText().toString(),""));
                    Pref.setStringValue(context, Const.to_pavang_p, Const.notNullString(etPavAngPT.getText().toString(),""));
                    Pref.setStringValue(context, Const.from_pavht_p, Const.notNullString(etPavHtPF.getText().toString(),""));
                    Pref.setStringValue(context, Const.to_pavht_p, Const.notNullString(etPavHtPT.getText().toString(),""));
                    Pref.setStringValue(context, Const.from_date, etFromDate.getText().toString());
                    Pref.setStringValue(context, Const.to_date, etToDate.getText().toString());
                    Pref.setStringValue(context, Const.from_cts, Const.notNullString(etFromCts.getText().toString(),""));
                    Pref.setStringValue(context, Const.to_cts, Const.notNullString(etToCts.getText().toString(),""));

                    String Cts1 = "";
                    if (!Const.isEmpty(etFromCts) & !Const.isEmpty(etToCts)) {
                         Cts1 = (etFromCts.getText().toString() + "-" + etToCts.getText().toString());
                    }

                    if(tvSelectedSize.getText().toString().isEmpty()) {
                        if (!Cts1.isEmpty()) {
                            Pref.setStringValue(context, Const.pointer, Cts1.toString() + "," + arraySpecificSize.toString().replace("[", "").replace("]", "").replace(", ", ","));
                        } else {
                            Pref.setStringValue(context, Const.pointer, arraySpecificSize.toString().replace("[", "").replace("]", "").replace(", ", ","));
                        }
                    }


                    if (!Const.isEmpty(etStoneOrCert)) {
                        Pref.setStringValue(context, Const.stone_id, etStoneOrCert.getText().toString().replaceAll(" ",",").replaceAll("\n",",").replaceAll("\t",",").replaceAll("\r",",").replaceAll("  ",","));
                    }
                    Pref.setStringValue(context, Const.TransId, Const.notNullString(TransID.toString().trim(),""));

                    for (int i = 0; i < selectedSymbol.size(); i++) {
                        if (selectedSymbol.get(i).split("'_'")[0].equalsIgnoreCase("true")) {
                            arraySymbolTrue.add(selectedSymbol.get(i).split("'_'")[1]);
                        } else if (selectedSymbol.get(i).split("'_'")[0].equalsIgnoreCase("false")) {
                            arraySymbolFalse.add(selectedSymbol.get(i).split("'_'")[1]);
                        }
                    }
                    String tr1 = arraySymbolTrue.toString().replace("[", "").replace("]", "").replace(", ", ",");
                    String fa1 = arraySymbolFalse.toString().replace("[", "").replace("]", "").replace(", ", ",");
                    Pref.setStringValue(context, Const.keytosymbol, tr1 + "-" + fa1);
                    Pref.setStringValue(context, Const.keytosymboltrue, tr1);
                    Pref.setStringValue(context, Const.keytosymbolfalse, fa1);

                    Pref.setStringValue(context, Const.blank_symbol, tvBlankSymbol.getCurrentTextColor() == getResources().getColor(R.color.colorPrimary) ? "" : "true");
                    Pref.setStringValue(context, Const.blank_length, tvBlankLength.getCurrentTextColor() == getResources().getColor(R.color.colorPrimary) ? "" : "true");
                    Pref.setStringValue(context, Const.blank_width, tvBlankWidth.getCurrentTextColor() == getResources().getColor(R.color.colorPrimary) ? "" : "true");
                    Pref.setStringValue(context, Const.blank_depth, tvBlankDepth.getCurrentTextColor() == getResources().getColor(R.color.colorPrimary) ? "" : "true");
                    Pref.setStringValue(context, Const.blank_depth_per, tvBlankDepthPer.getCurrentTextColor() == getResources().getColor(R.color.colorPrimary) ? "" : "true");
                    Pref.setStringValue(context, Const.blank_table_per, tvBlankTablePer.getCurrentTextColor() == getResources().getColor(R.color.colorPrimary) ? "" : "true");
                    Pref.setStringValue(context, Const.blank_cr_ang, tvBlankCrAng.getCurrentTextColor() == getResources().getColor(R.color.colorPrimary) ? "" : "true");
                    Pref.setStringValue(context, Const.blank_cr_hit, tvBlankCrHit.getCurrentTextColor() == getResources().getColor(R.color.colorPrimary) ? "" : "true");
                    Pref.setStringValue(context, Const.blank_pav_ang, tvBlankPavAng.getCurrentTextColor() == getResources().getColor(R.color.colorPrimary) ? "" : "true");
                    Pref.setStringValue(context, Const.blank_pav_hit, tvBlankPavHit.getCurrentTextColor() == getResources().getColor(R.color.colorPrimary) ? "" : "true");

                    View contentView1 = View.inflate(context, R.layout.layout_lab_download, null);
                    final Dialog sheetDialog1 = new Dialog(context, R.style.Theme_Dialog);
                    sheetDialog1.getWindow().setWindowAnimations(R.style.DialogAnimation);
                    Window window1 = sheetDialog1.getWindow();
                    WindowManager.LayoutParams wlp1 = window1.getAttributes();
                    wlp1.gravity = Gravity.BOTTOM;
                    wlp1.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                    window1.setAttributes(wlp1);
                    sheetDialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    sheetDialog1.setContentView(contentView1);
                    sheetDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    ImageView tvClose1 = sheetDialog1.findViewById(R.id.tvClose);
                    LinearLayout llCustExcel1 = sheetDialog1.findViewById(R.id.llCustExcel);
                    LinearLayout llCustExcel1Image = sheetDialog1.findViewById(R.id.llCustExcelImage);
                    LinearLayout llSuppExcel1 = sheetDialog1.findViewById(R.id.llSuppExcel);
                    llCustExcel1.setBackgroundDrawable(Const.setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));
                    llCustExcel1Image.setBackgroundDrawable(Const.setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));
                    llSuppExcel1.setBackgroundDrawable(Const.setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));

                    if (Pref.getStringValue(context, Const.loginIsEmp, "").equalsIgnoreCase("1")) {
                        llCustExcel1Image.setVisibility(View.VISIBLE);
                        llCustExcel1.setVisibility(View.GONE);
                        llSuppExcel1.setVisibility(View.GONE);
                    }

                    llCustExcel1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ShareExcel("1");
                            sheetDialog1.dismiss();
                        }
                    });
                    llCustExcel1Image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ShareExcel("2");
                            sheetDialog1.dismiss();
                        }
                    });
                    llSuppExcel1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ShareExcel("3");
                            sheetDialog1.dismiss();
                        }
                    });
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        tvClose1.setBackground(Const.setBackgoundBorder(2, 40, Color.TRANSPARENT, Color.WHITE));
                    }
                    tvClose1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            sheetDialog1.dismiss();
                        }
                    });
                    sheetDialog1.show();
                    break;
                case R.id.etFromDate:
                    openFromDatePickerDialog();
                    break;
                case R.id.etToDate:
                    openToDatePickerDialog();
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
                case R.id.etTransId:
                    getTransID();
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
                    if (tv3VG2.getCurrentTextColor() == getResources().getColor(R.color.white)) {
                        tv3VG2.performClick();
                    }
                    if (tv3EX.getCurrentTextColor() == getResources().getColor(R.color.white)) {
                        selectedCut.remove(0);
                        selectedCut.remove(1);
                        selectedPolish.remove(0);
                        selectedSymmetry.remove(0);
                        cutAdp.notifyDataSetChanged();
                        polishAdp.notifyDataSetChanged();
                        symmetryAdp.notifyDataSetChanged();

                        tv3EX.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tv3EX.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else {
                        selectedCut.put(0, "3EX");
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
                case R.id.tv3VG2:
                    if (tv3EX.getCurrentTextColor() == getResources().getColor(R.color.white)) {
                        tv3EX.performClick();
                    }
                    if (tv3VG2.getCurrentTextColor() == getResources().getColor(R.color.white)){
                        selectedCut.remove(0);
                        selectedCut.remove(1);
                        selectedCut.remove(2);
                        selectedPolish.remove(0);
                        selectedPolish.remove(1);
                        selectedSymmetry.remove(0);
                        selectedSymmetry.remove(1);

                        cutAdp.notifyDataSetChanged();
                        polishAdp.notifyDataSetChanged();
                        symmetryAdp.notifyDataSetChanged();

                        tv3VG2.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tv3VG2.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }else {
                        selectedCut.put(0, "3EX");
                        selectedCut.put(1, "EX");
                        selectedCut.put(2, "VG");
                        selectedPolish.put(0, "EX");
                        selectedPolish.put(1, "VG");
                        selectedSymmetry.put(0, "EX");
                        selectedSymmetry.put(1, "VG");
                        cutAdp.notifyDataSetChanged();
                        polishAdp.notifyDataSetChanged();
                        symmetryAdp.notifyDataSetChanged();

                        tv3VG2.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tv3VG2.setTextColor(getResources().getColor(R.color.white));
                    }
                    break;
                case R.id.tvBlankSymbol:
                    if (tvBlankSymbol.getCurrentTextColor() == getResources().getColor(R.color.white)){

                        tvBlankSymbol.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvBlankSymbol.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }else {

                        tvBlankSymbol.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tvBlankSymbol.setTextColor(getResources().getColor(R.color.white));
                    }
                    break;
                case R.id.tvBlankLength:
                    if (tvBlankLength.getCurrentTextColor() == getResources().getColor(R.color.white)){

                        tvBlankLength.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvBlankLength.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }else {

                        tvBlankLength.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tvBlankLength.setTextColor(getResources().getColor(R.color.white));
                    }
                    break;
                case R.id.tvBlankWidth:
                    if (tvBlankWidth.getCurrentTextColor() == getResources().getColor(R.color.white)){

                        tvBlankWidth.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvBlankWidth.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }else {

                        tvBlankWidth.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tvBlankWidth.setTextColor(getResources().getColor(R.color.white));
                    }
                    break;
                case R.id.tvBlankDepth:
                    if (tvBlankDepth.getCurrentTextColor() == getResources().getColor(R.color.white)){

                        tvBlankDepth.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvBlankDepth.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }else {

                        tvBlankDepth.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tvBlankDepth.setTextColor(getResources().getColor(R.color.white));
                    }
                    break;
                case R.id.tvBlankDepthPer:
                    if (tvBlankDepthPer.getCurrentTextColor() == getResources().getColor(R.color.white)){

                        tvBlankDepthPer.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvBlankDepthPer.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }else {

                        tvBlankDepthPer.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tvBlankDepthPer.setTextColor(getResources().getColor(R.color.white));
                    }
                    break;
                case R.id.tvBlankTablePer:
                    if (tvBlankTablePer.getCurrentTextColor() == getResources().getColor(R.color.white)){

                        tvBlankTablePer.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvBlankTablePer.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }else {

                        tvBlankTablePer.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tvBlankTablePer.setTextColor(getResources().getColor(R.color.white));
                    }
                    break;
                case R.id.tvBlankCrAng:
                    if (tvBlankCrAng.getCurrentTextColor() == getResources().getColor(R.color.white)){

                        tvBlankCrAng.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvBlankCrAng.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }else {

                        tvBlankCrAng.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tvBlankCrAng.setTextColor(getResources().getColor(R.color.white));
                    }
                    break;
                case R.id.tvBlankCrHit:
                    if (tvBlankCrHit.getCurrentTextColor() == getResources().getColor(R.color.white)){

                        tvBlankCrHit.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvBlankCrHit.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }else {

                        tvBlankCrHit.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tvBlankCrHit.setTextColor(getResources().getColor(R.color.white));
                    }
                    break;
                case R.id.tvBlankPavAng:
                    if (tvBlankPavAng.getCurrentTextColor() == getResources().getColor(R.color.white)){

                        tvBlankPavAng.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvBlankPavAng.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }else {

                        tvBlankPavAng.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tvBlankPavAng.setTextColor(getResources().getColor(R.color.white));
                    }
                    break;
                case R.id.tvBlankPavHit:
                    if (tvBlankPavHit.getCurrentTextColor() == getResources().getColor(R.color.white)){

                        tvBlankPavHit.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        tvBlankPavHit.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }else {

                        tvBlankPavHit.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        tvBlankPavHit.setTextColor(getResources().getColor(R.color.white));
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


    private class TransAdp extends RecyclerView.Adapter<TransAdp.RecyclerViewHolder> {
        List<String> array;
        HashMap<Integer, String> selectedArray;
        Context context;

        private TransAdp(Context context, List<String> array, HashMap<Integer, String> selectedArray) {
            this.array = array;
            this.context = context;
            this.selectedArray = selectedArray;
        }

        @Override
        public TransAdp.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_key_to_symbol, parent, false);
            TransAdp.RecyclerViewHolder viewHolder = new TransAdp.RecyclerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final TransAdp.RecyclerViewHolder holder, final int position) {

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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_shape, parent, false);
            ShapeAdp.RecyclerViewHolder viewHolder = new ShapeAdp.RecyclerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ShapeAdp.RecyclerViewHolder holder, final int position) {
            holder.tvName.setText(name[position]);
            holder.imgIcon.setImageResource(image.getResourceId(position, -1));

            try {
                if (selectedShape.containsKey(position)) {
                    holder.llClick.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                    holder.tvName.setBackgroundColor(getResources().getColor(R.color.shapeText));
                    holder.tvName.setTextColor(getResources().getColor(R.color.white));
                    holder.imgIcon.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
                } else {
                    holder.llClick.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                    holder.tvName.setBackgroundColor(getResources().getColor(R.color.gray));
                    holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                    holder.imgIcon.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                }
            } catch (Exception e) {
                holder.llClick.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                holder.tvName.setBackgroundColor(getResources().getColor(R.color.gray));
                holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                holder.imgIcon.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
            }

            holder.llClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedShape.containsKey(position)) {
                        holder.llClick.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        holder.tvName.setBackgroundColor(getResources().getColor(R.color.gray));
                        selectedShape.remove(position);
                        holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                        holder.imgIcon.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                    } else {
                        holder.llClick.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        holder.tvName.setBackgroundColor(getResources().getColor(R.color.shapeText));
                        selectedShape.put(position, name[position]);
                        holder.tvName.setTextColor(getResources().getColor(R.color.white));
                        holder.imgIcon.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
                        RotateAnimation rotateAnim = new RotateAnimation(0, 360, holder.imgIcon.getWidth() / 2, holder.imgIcon.getHeight() / 2);
                        rotateAnim.setDuration(500);
                        rotateAnim.setFillAfter(true);
                        holder.imgIcon.setAnimation(rotateAnim);
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

    private class ShapeNewAdp extends RecyclerView.Adapter<ShapeNewAdp.RecyclerViewHolder> {
        List<String> name = new ArrayList<>();
        List<String> image = new ArrayList<>();
        Context context;

        private ShapeNewAdp(Context context, List<String> name, List<String> image) {
            this.name = name;
            this.image = image;
            this.context = context;
        }

        @Override
        public ShapeNewAdp.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_shape, parent, false);
            ShapeNewAdp.RecyclerViewHolder viewHolder = new ShapeNewAdp.RecyclerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ShapeNewAdp.RecyclerViewHolder holder, final int position) {
            holder.tvName.setText(name.get(position));
            if(name.get(position).equalsIgnoreCase("ALL")){
                holder.imgIcon.setImageResource(R.drawable.ic_all);
            }else if(name.get(position).equalsIgnoreCase("OTHERS")){
                holder.imgIcon.setImageResource(R.drawable.ic_round);
            }else{
                Picasso.with(context).load(image.get(position)).into(holder.imgIcon);
            }

            try {
                if (selectedShape.containsKey(position)) {
                    holder.llClick.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                    holder.tvName.setBackgroundColor(getResources().getColor(R.color.shapeText));
                    holder.tvName.setTextColor(getResources().getColor(R.color.white));
                    holder.imgIcon.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

                    if (isSelectedAllShapeAnim) {
                        RotateAnimation rotateAnim = new RotateAnimation(0, 360, holder.imgIcon.getWidth() / 2, holder.imgIcon.getHeight() / 2);
                        rotateAnim.setDuration(500);
                        rotateAnim.setFillAfter(true);
                        holder.imgIcon.setAnimation(rotateAnim);
                    } else {
                        if(isSelectedAnimPos == position){
                            isSelectedAnimPos = -1;
                            RotateAnimation rotateAnim = new RotateAnimation(0, 360, holder.imgIcon.getWidth() / 2, holder.imgIcon.getHeight() / 2);
                            rotateAnim.setDuration(500);
                            rotateAnim.setFillAfter(true);
                            holder.imgIcon.setAnimation(rotateAnim);
                        }else{
                            holder.imgIcon.clearAnimation();
                        }
                    }
                } else {
                    holder.llClick.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                    holder.tvName.setBackgroundColor(getResources().getColor(R.color.gray));
                    holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                    holder.imgIcon.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                    holder.imgIcon.clearAnimation();
                }
            } catch (Exception e) {
                holder.llClick.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                holder.tvName.setBackgroundColor(getResources().getColor(R.color.gray));
                holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                holder.imgIcon.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
                holder.imgIcon.clearAnimation();
            }

            holder.llClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isSelectedAnimPos = position;
                    if (position == 0) {
                        isSelectedAllShapeAnim = true;
                        isSelectedAllShape = !isSelectedAllShape;
                        selectedShape.clear();
                        if (isSelectedAllShape) {
                            for (int i = 0; i < name.size(); i++) {
                                selectedShape.put(i, name.get(i));
                            }
                        }
                    } else {
                        isSelectedAllShapeAnim = false;
                        if (selectedShape.containsKey(position)) {
                            holder.llClick.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                            holder.tvName.setBackgroundColor(getResources().getColor(R.color.gray));
                            selectedShape.remove(position);
                            holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                            holder.imgIcon.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);

                            if(selectedShape.size() == name.size()){
                                isSelectedAllShape = true;
                                isSelectedAllShapeAnim = true;
                                selectedShape.put(0, "ALL");
                            }else{
                                isSelectedAllShape = false;
                                selectedShape.remove(0);
                            }
                        } else {
                            holder.llClick.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                            holder.tvName.setBackgroundColor(getResources().getColor(R.color.shapeText));
                            selectedShape.put(position, name.get(position));
                            holder.tvName.setTextColor(getResources().getColor(R.color.white));
                            holder.imgIcon.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
//                            RotateAnimation rotateAnim = new RotateAnimation(0, 360, holder.imgIcon.getWidth() / 2, holder.imgIcon.getHeight() / 2);
//                            rotateAnim.setDuration(500);
//                            rotateAnim.setFillAfter(true);
//                            holder.imgIcon.setAnimation(rotateAnim);

                            if(selectedShape.size() == name.size() - 1){
                                isSelectedAllShape = true;
                                isSelectedAllShapeAnim = true;
                                selectedShape.put(0, "ALL");
                            }else{
                                isSelectedAllShape = false;
                                selectedShape.remove(0);
                            }
                        }
                    }
                    notifyDataSetChanged();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            isSelectedAnimPos = -1;
                            isSelectedAllShapeAnim = false;
                        }
                    },500);
                }
            });
        }

        @Override
        public int getItemCount() {
            return name.size();
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
        List<String> arraylist = new ArrayList<>();
        Context context;
        int pL, pT, pR, pB;

        private LabAdp(Context context, List<String> arraylist) {
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
            holder.tvName.setText(arraylist.get(position));
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
                        selectedLab.put(position, arraylist.get(position));
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

    private class ColorAdp extends RecyclerView.Adapter<ColorAdp.RecyclerViewHolder> {
        List<String> arraylist = new ArrayList<>();
        Context context;
        int pL, pT, pR, pB;

        private ColorAdp(Context context, List<String> arraylist) {
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
            holder.tvName.setText(arraylist.get(position));
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
                        selectedColor.put(position, arraylist.get(position));
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

    private class IntencityAdp extends RecyclerView.Adapter<IntencityAdp.RecyclerViewHolder> {
        String[] arraylist;
        Context context;
        int pL, pT, pR, pB;

        private IntencityAdp(Context context, String[] arraylist) {
            this.arraylist = arraylist;
            this.context = context;
        }

        @Override
        public IntencityAdp.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_location, parent, false);
            IntencityAdp.RecyclerViewHolder viewHolder = new IntencityAdp.RecyclerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final IntencityAdp.RecyclerViewHolder holder, final int position) {
            holder.tvName.setText(arraylist[position]);
            pL = holder.tvName.getPaddingLeft();
            pT = holder.tvName.getPaddingTop();
            pR = holder.tvName.getPaddingRight();
            pB = holder.tvName.getPaddingBottom();

            try {
                if (selectedIntencity.containsKey(position)) {
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
                    if (selectedIntencity.containsKey(position)) {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        selectedIntencity.remove(position);
                        holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        selectedIntencity.put(position, arraylist[position]);
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

    private class OvertoneAdp extends RecyclerView.Adapter<OvertoneAdp.RecyclerViewHolder> {
        String[] arraylist;
        Context context;
        int pL, pT, pR, pB;

        private OvertoneAdp(Context context, String[] arraylist) {
            this.arraylist = arraylist;
            this.context = context;
        }

        @Override
        public OvertoneAdp.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_location, parent, false);
            OvertoneAdp.RecyclerViewHolder viewHolder = new OvertoneAdp.RecyclerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final OvertoneAdp.RecyclerViewHolder holder, final int position) {
            holder.tvName.setText(arraylist[position]);
            pL = holder.tvName.getPaddingLeft();
            pT = holder.tvName.getPaddingTop();
            pR = holder.tvName.getPaddingRight();
            pB = holder.tvName.getPaddingBottom();

            try {
                if (selectedOvertone.containsKey(position)) {
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
                    if (selectedOvertone.containsKey(position)) {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        selectedOvertone.remove(position);
                        holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        selectedOvertone.put(position, arraylist[position]);
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

    private class FancyColorAdp extends RecyclerView.Adapter<FancyColorAdp.RecyclerViewHolder> {
        String[] arraylist;
        Context context;
        int pL, pT, pR, pB;

        private FancyColorAdp(Context context, String[] arraylist) {
            this.arraylist = arraylist;
            this.context = context;
        }

        @Override
        public FancyColorAdp.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_location, parent, false);
            FancyColorAdp.RecyclerViewHolder viewHolder = new FancyColorAdp.RecyclerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final FancyColorAdp.RecyclerViewHolder holder, final int position) {
            holder.tvName.setText(arraylist[position]);
            pL = holder.tvName.getPaddingLeft();
            pT = holder.tvName.getPaddingTop();
            pR = holder.tvName.getPaddingRight();
            pB = holder.tvName.getPaddingBottom();

            try {
                if (selectedFancyColor.containsKey(position)) {
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
                    if (selectedFancyColor.containsKey(position)) {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        selectedFancyColor.remove(position);
                        holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
                        selectedFancyColor.put(position, arraylist[position]);
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
        List<String> arraylist = new ArrayList<>();
        Context context;
        int pL, pT, pR, pB;

        private ClarityAdp(Context context, List<String> arraylist) {
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
            holder.tvName.setText(arraylist.get(position));
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
                        selectedClarity.put(position, arraylist.get(position));
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

    private class CutAdp extends RecyclerView.Adapter<CutAdp.RecyclerViewHolder> {
        List<String> arraylist = new ArrayList<>();
        Context context;
        int pL, pT, pR, pB;

        private CutAdp(Context context, List<String> arraylist) {
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
            holder.tvName.setText(arraylist.get(position));
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
                                                selectedCut.put(position, arraylist.get(position));
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

    private class PolishAdp extends RecyclerView.Adapter<PolishAdp.RecyclerViewHolder> {
        List<String> arraylist = new ArrayList<>();
        Context context;
        int pL, pT, pR, pB;

        private PolishAdp(Context context, List<String> arraylist) {
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
            holder.tvName.setText(arraylist.get(position));
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
                                        selectedPolish.put(position, arraylist.get(position));
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

    private class SymmetryAdp extends RecyclerView.Adapter<SymmetryAdp.RecyclerViewHolder> {
        List<String> arraylist = new ArrayList<>();
        Context context;
        int pL, pT, pR, pB;

        private SymmetryAdp(Context context, List<String> arraylist) {
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
            holder.tvName.setText(arraylist.get(position));
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
                                        selectedSymmetry.put(position, arraylist.get(position));
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

    private class FiAdp extends RecyclerView.Adapter<FiAdp.RecyclerViewHolder> {
        List<String> arraylist = new ArrayList<>();
        Context context;
        int pL, pT, pR, pB;

        private FiAdp(Context context, List<String> arraylist) {
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
            holder.tvName.setText(arraylist.get(position));
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
                        selectedFi.put(position, arraylist.get(position));
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

    private class BgmAdp extends RecyclerView.Adapter<BgmAdp.RecyclerViewHolder> {
        List<String> arraylist = new ArrayList<>();
        Context context;
        int pL, pT, pR, pB;

        private BgmAdp(Context context, List<String> arraylist) {
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
            holder.tvName.setText(arraylist.get(position));
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
                        selectedBgm.put(position, arraylist.get(position));
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
                    if (selectedInclusion.containsKey(position)) {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        selectedInclusion.remove(position);
                        holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
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
                    if (selectedNatts.containsKey(position)) {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.shadow_lightgray));
                        selectedNatts.remove(position);
                        holder.tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else {
                        holder.tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_shadow));
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

    private class TableBlackAdp extends RecyclerView.Adapter<TableBlackAdp.RecyclerViewHolder> {
        List<String> arraylist = new ArrayList<>();
        Context context;
        int pL, pT, pR, pB;

        private TableBlackAdp(Context context, List<String> arraylist) {
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
            holder.tvName.setText(arraylist.get(position));
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
                        selectedTB.put(position, arraylist.get(position));
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

    private class CrownBlackAdp extends RecyclerView.Adapter<CrownBlackAdp.RecyclerViewHolder> {
        List<String> arraylist = new ArrayList<>();
        Context context;
        int pL, pT, pR, pB;

        private CrownBlackAdp(Context context, List<String> arraylist) {
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
            holder.tvName.setText(arraylist.get(position));
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
                        selectedCB.put(position, arraylist.get(position));
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

    private class TableWhiteAdp extends RecyclerView.Adapter<TableWhiteAdp.RecyclerViewHolder> {
        List<String> arraylist = new ArrayList<>();
        Context context;
        int pL, pT, pR, pB;

        private TableWhiteAdp(Context context, List<String> arraylist) {
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
            holder.tvName.setText(arraylist.get(position));
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
                        selectedTW.put(position, arraylist.get(position));
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

    private class CrownWhiteAdp extends RecyclerView.Adapter<CrownWhiteAdp.RecyclerViewHolder> {
        List<String> arraylist = new ArrayList<>();
        Context context;
        int pL, pT, pR, pB;

        private CrownWhiteAdp(Context context, List<String> arraylist) {
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
            holder.tvName.setText(arraylist.get(position));
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
                        selectedCW.put(position, arraylist.get(position));
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

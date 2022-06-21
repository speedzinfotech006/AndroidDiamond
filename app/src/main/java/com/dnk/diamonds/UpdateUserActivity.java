package com.dnk.shairugems;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;

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

public class UpdateUserActivity extends AppCompatActivity {

    Toolbar toolbar;
    LinearLayout llUpdate;
    ImageView imgRefresh;
    EditText etCountry,etCity;
    ScrollView svScroll;
    TextView tvFortunePartyCode;
    CheckBox cbActive,cbIsCompany;
    EditText etUserName,etPassword,etUserType,etAssist1,etAssist2,etFirstName,etLastName,etOtherName,etStockType,etFortunePartyCode,etCompName,etAddress1,
            etAddress2,etZipCode,etISD1,etMobile1,etISD2,etMobile2,etAreaCode1,etOfficePhone1,etAreaCode2,etOfficePhone2,etCode,
            etFaxNo,etEmailAddress1,etEmailAddress2,etWebsite,etSkypeID,etWeChatID,etBusinessRegNo,etHKID,etPassportNo,etRapID,etRemark,etMessage;
    LinearLayout llMobile2,llOfficePhone2;
    RelativeLayout rlUserType,rlAssist1,rlAssist2,rlCompName,rlOtherName,rlStockType,rlHKID,rlPassportNo,rlRemark,rlMessage;
    ArrayAdapter adapter;
    ArrayList<String> arrayStockType;
    ArrayList<String> arrayUserType;
    ArrayList<HashMap<String, String>> arrayhmCountry, arrayhmCity, arrayhmMessage;
    ArrayList<String> arrayCountry, arrayCity;
    ArrayList<String> arrayAssist, arrayMessage;
    ArrayList<HashMap<String, String>> arrayhmAssist;
    List<HashMap<String, String>> arrayhmUser;
    HashMap<String, String> intentMap = new HashMap<>();

    Context context = UpdateUserActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        initView();
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        arrayStockType = new ArrayList<String>();

        arrayStockType.add("Total Stock");
        arrayStockType.add("Office Stock");
        arrayStockType.add("Office Stock with Consignment");
        arrayStockType.add("Office Stock with Hold");


        etStockType.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    etStockType.performClick();
                }
            }
        });

        etAssist1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    etAssist1.performClick();
                }
            }
        });

        etAssist2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    etAssist2.performClick();
                }
            }
        });

        etMessage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    etMessage.performClick();
                }
            }
        });

        arrayUserType = new ArrayList<String>();

        arrayUserType.add("Admin");
        arrayUserType.add("Employee");

        etUserType.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    etUserType.performClick();
                }
            }
        });
    }

    private void initView() {
        toolbar = findViewById(R.id.toolBar);
        svScroll = findViewById(R.id.svScroll);
        imgRefresh = findViewById(R.id.imgRefresh);
        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        etUserType = findViewById(R.id.etUserType);
        etAssist1 = findViewById(R.id.etAssist1);
        etAssist2 = findViewById(R.id.etAssist2);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etOtherName = findViewById(R.id.etOtherName);
        etStockType = findViewById(R.id.etStockType);
        etFortunePartyCode = findViewById(R.id.etFortunePartyCode);
        etCompName = findViewById(R.id.etCompName);
        etAddress1 = findViewById(R.id.etAddress1);
        etAddress2 = findViewById(R.id.etAddress2);
        etCity = findViewById(R.id.etCity);
        etZipCode = findViewById(R.id.etZipCode);
        etCountry = findViewById(R.id.etCountry);
        etISD1 = findViewById(R.id.etISD1);
        etMobile1 = findViewById(R.id.etMobile1);
        etISD2 = findViewById(R.id.etISD2);
        etMobile2 = findViewById(R.id.etMobile2);
        etAreaCode1 = findViewById(R.id.etAreaCode1);
        etOfficePhone1 = findViewById(R.id.etOfficePhone1);
        etAreaCode2 = findViewById(R.id.etAreaCode2);
        etOfficePhone2 = findViewById(R.id.etOfficePhone2);
        etCode = findViewById(R.id.etCode);
        etFaxNo = findViewById(R.id.etFaxNo);
        etEmailAddress1 = findViewById(R.id.etEmailAddress1);
        etEmailAddress2 = findViewById(R.id.etEmailAddress2);
        etWebsite = findViewById(R.id.etWebsite);
        etSkypeID = findViewById(R.id.etSkypeID);
        etWeChatID = findViewById(R.id.etWeChatID);
        etBusinessRegNo = findViewById(R.id.etBusinessRegNo);
        etHKID = findViewById(R.id.etHKID);
        etPassportNo = findViewById(R.id.etPassportNo);
        etRapID = findViewById(R.id.etRapID);
        etRemark = findViewById(R.id.etRemark);
        etMessage = findViewById(R.id.etMessage);

        llMobile2 = findViewById(R.id.llMobile2);
        llOfficePhone2 = findViewById(R.id.llOfficePhone2);

        rlUserType = findViewById(R.id.rlUserType);
        rlOtherName = findViewById(R.id.rlOtherName);
        rlCompName = findViewById(R.id.rlCompName);
        rlAssist1 = findViewById(R.id.rlAssist1);
        rlAssist2 = findViewById(R.id.rlAssist2);
        rlStockType = findViewById(R.id.rlStockType);
        rlHKID = findViewById(R.id.rlHKID);
        rlPassportNo = findViewById(R.id.rlPassportNo);
        rlRemark = findViewById(R.id.rlRemark);
        rlMessage = findViewById(R.id.rlMessage);

        cbActive = findViewById(R.id.cbActive);
        tvFortunePartyCode = findViewById(R.id.tvFortunePartyCode);
        cbIsCompany = findViewById(R.id.cbIsCompany);

        llUpdate = findViewById(R.id.llUpdate);
        llUpdate.setBackgroundDrawable(Const.setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));

        etCountry.setOnClickListener(clickListener);
        etCity.setOnClickListener(clickListener);
        etAssist1.setOnClickListener(clickListener);
        etAssist2.setOnClickListener(clickListener);
        etStockType.setOnClickListener(clickListener);
        etUserType.setOnClickListener(clickListener);
        etMessage.setOnClickListener(clickListener);
        cbActive.setOnClickListener(clickListener);

        llUpdate.setOnClickListener(clickListener);

        getAssistant();
        get_Message_Data();

        etCountry.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    etCountry.performClick();
                }
            }
        });

        etCity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    etCity.performClick();
                }
            }
        });

        CountryList();

        imgRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearForm();
            }
        });

        if (getIntent().hasExtra("map")) {
            intentMap = (HashMap<String, String>) getIntent().getSerializableExtra("map");
            Log.e("IntentMap : ", intentMap + "");
            CountryCodeList(Const.notNullString(intentMap.get("scompcountry"),""));

            if(Const.notNullString(intentMap.get("susertype"),"").equalsIgnoreCase("Admin")){
                cbIsCompany.setVisibility(View.GONE);
                rlStockType.setVisibility(View.GONE);
                rlAssist1.setVisibility(View.GONE);
                rlAssist2.setVisibility(View.GONE);
                rlCompName.setVisibility(View.GONE);
                llMobile2.setVisibility(View.GONE);
                llOfficePhone2.setVisibility(View.GONE);
                rlMessage.setVisibility(View.GONE);
            } else if(Const.notNullString(intentMap.get("susertype"),"").equalsIgnoreCase("Employee")){
                rlRemark.setVisibility(View.GONE);
                cbIsCompany.setVisibility(View.GONE);
                rlStockType.setVisibility(View.GONE);
                rlAssist1.setVisibility(View.GONE);
                rlAssist2.setVisibility(View.GONE);
                rlCompName.setVisibility(View.GONE);
                llMobile2.setVisibility(View.GONE);
                llOfficePhone2.setVisibility(View.GONE);
                rlMessage.setVisibility(View.GONE);
            } else if(Const.notNullString(intentMap.get("susertype"),"").equalsIgnoreCase("Registered")){
                rlRemark.setVisibility(View.GONE);
                rlHKID.setVisibility(View.GONE);
                rlPassportNo.setVisibility(View.GONE);
                rlOtherName.setVisibility(View.GONE);
                rlUserType.setVisibility(View.GONE);
                rlMessage.setVisibility(View.VISIBLE);
            }

            if (Pref.getStringValue(context, Const.loginIsAdmin, "").equalsIgnoreCase("1"))
            {
                rlRemark.setVisibility(View.VISIBLE);
                if(!intentMap.get("remark").equalsIgnoreCase("") && !intentMap.get("remark").equalsIgnoreCase("null")){
                    etRemark.setText(Const.notNullString(intentMap.get("remark"),""));
                }else{
                    etRemark.setText("");
                }
            }
            else
            {
                rlRemark.setVisibility(View.GONE);
            }

            if(!intentMap.get("susername").equalsIgnoreCase("") && !intentMap.get("susername").equalsIgnoreCase("null")){
                etUserName.setText(Const.notNullString(intentMap.get("susername"),""));
            }else{
                etUserName.setText("");
            }
            if(!intentMap.get("spassword").equalsIgnoreCase("") && !intentMap.get("spassword").equalsIgnoreCase("null")){
                etPassword.setText(Const.notNullString(intentMap.get("spassword"),""));
            }else{
                etPassword.setText("");
            }
            if(!intentMap.get("susertype").equalsIgnoreCase("") && !intentMap.get("susertype").equalsIgnoreCase("null")){
                etUserType.setText(Const.notNullString(intentMap.get("susertype"),""));
            }else{
                etUserType.setText("");
            }
            if(!intentMap.get("assistby1").equalsIgnoreCase("") && !intentMap.get("assistby1").equalsIgnoreCase("null")){
                etAssist1.setText(Const.notNullString(intentMap.get("assistby1"),""));
            }else{
                etAssist1.setText("");
            }
            if(!intentMap.get("assistby2").equalsIgnoreCase("") && !intentMap.get("assistby2").equalsIgnoreCase("null")){
                etAssist2.setText(Const.notNullString(intentMap.get("assistby2"),""));
            }else{
                etAssist2.setText("");
            }
            if (intentMap.get("messagename") != null) {
                if (!intentMap.get("messagename").equalsIgnoreCase("") && !intentMap.get("messagename").equalsIgnoreCase("null")) {
                    etMessage.setText(Const.notNullString(intentMap.get("messagename"), ""));
                } else {
                    etMessage.setText("");
                }
            }
            if(!intentMap.get("sfirstname").equalsIgnoreCase("") && !intentMap.get("sfirstname").equalsIgnoreCase("null")){
                etFirstName.setText(Const.notNullString(intentMap.get("sfirstname"),""));
            }else{
                etFirstName.setText("");
            }
            if(!intentMap.get("slastname").equalsIgnoreCase("") && !intentMap.get("slastname").equalsIgnoreCase("null")){
                etLastName.setText(Const.notNullString(intentMap.get("slastname"),""));
            }else{
                etLastName.setText("");
            }
            if(!intentMap.get("sothername").equalsIgnoreCase("") && !intentMap.get("sothername").equalsIgnoreCase("null")){
                etOtherName.setText(Const.notNullString(intentMap.get("sothername"),""));
            }else{
                etOtherName.setText("");
            }
            if(!intentMap.get("scompname").equalsIgnoreCase("") && !intentMap.get("scompname").equalsIgnoreCase("null")){
                etCompName.setText(Const.notNullString(intentMap.get("scompname"),""));
            }else{
                etCompName.setText("");
            }
            if(!intentMap.get("scompaddress").equalsIgnoreCase("") && !intentMap.get("scompaddress").equalsIgnoreCase("null")){
                etAddress1.setText(Const.notNullString(intentMap.get("scompaddress"),""));
            }else{
                etAddress1.setText("");
            }
            if(!intentMap.get("scompaddress2").equalsIgnoreCase("") && !intentMap.get("scompaddress2").equalsIgnoreCase("null")){
                etAddress2.setText(Const.notNullString(intentMap.get("scompaddress2"),""));
            }else{
                etAddress2.setText("");
            }
            if(!intentMap.get("scompcountry").equalsIgnoreCase("") && !intentMap.get("scompcountry").equalsIgnoreCase("null")){
                etCountry.setText(Const.notNullString(intentMap.get("scompcountry"),""));
            }else{
                etCountry.setText("");
            }
            if(!intentMap.get("scompcity").equalsIgnoreCase("") && !intentMap.get("scompcity").equalsIgnoreCase("null")){
                etCity.setText(Const.notNullString(intentMap.get("scompcity"),""));
            }else{
                etCity.setText("");
            }
            if(!intentMap.get("scompzipcode").equalsIgnoreCase("") && !intentMap.get("scompzipcode").equalsIgnoreCase("null")){
                etZipCode.setText(Const.notNullString(intentMap.get("scompzipcode"),""));
            }else{
                etZipCode.setText("");
            }

            if(!intentMap.get("scompmobile").equalsIgnoreCase("") && !intentMap.get("scompmobile").equalsIgnoreCase("null")){
                if(intentMap.get("scompmobile").contains("-")) {
                    etISD1.setText(Const.notNullString(intentMap.get("scompmobile").split("-")[0],""));
                   if(intentMap.get("scompmobile").length()>8) {
                       etMobile1.setText(Const.notNullString(intentMap.get("scompmobile").split("-")[1], ""));
                   }
                }else {
                    etISD1.setText("");
                    etMobile1.setText("");
                }
            } else {
                etISD1.setText("");
                etMobile1.setText("");
            }

            if(!intentMap.get("scompmobile2").equalsIgnoreCase("") && !intentMap.get("scompmobile2").equalsIgnoreCase("null")){
                if(intentMap.get("scompmobile2").contains("-")) {
                       etISD2.setText(Const.notNullString(intentMap.get("scompmobile2").split("-")[0],""));
                    if(intentMap.get("scompmobile2").length()>8) {
                        etMobile2.setText(Const.notNullString(intentMap.get("scompmobile2").split("-")[1], ""));
                    }
                }else {
                    etISD1.setText("");
                    etMobile1.setText("");
                }
            }else{
                etISD2.setText("");
                etMobile2.setText("");
            }

            if(!intentMap.get("scompphone").equalsIgnoreCase("") && !intentMap.get("scompphone").equalsIgnoreCase("null")){
                if(intentMap.get("scompphone").contains("-")) {
                        etAreaCode1.setText(Const.notNullString(intentMap.get("scompphone").split("-")[0], ""));
                    if(intentMap.get("scompphone").length()>8) {
                        etOfficePhone1.setText(Const.notNullString(intentMap.get("scompphone").split("-")[1], ""));
                    }
                }else {
                    etAreaCode1.setText("");
                    etOfficePhone1.setText("");
                }
            } else {
                etAreaCode1.setText("");
                etOfficePhone1.setText("");
            }

            if(!intentMap.get("scompphone2").equalsIgnoreCase("") && !intentMap.get("scompphone2").equalsIgnoreCase("null")){
                if(intentMap.get("scompphone2").contains("-")) {
                        etAreaCode2.setText(Const.notNullString(intentMap.get("scompphone2").split("-")[0], ""));
                    if(intentMap.get("scompphone2").length()>8) {
                        etOfficePhone2.setText(Const.notNullString(intentMap.get("scompphone2").split("-")[1], ""));
                    }
                }else{
                    etAreaCode2.setText("");
                    etOfficePhone2.setText("");
                }
            }else{
                etAreaCode2.setText("");
                etOfficePhone2.setText("");
            }

            if(!intentMap.get("scompfaxno").equalsIgnoreCase("") && !intentMap.get("scompfaxno").equalsIgnoreCase("null")){
                if(intentMap.get("scompfaxno").contains("-")) {
                        etCode.setText(Const.notNullString(intentMap.get("scompfaxno").split("-")[0], ""));
                    if(intentMap.get("scompfaxno").length()>8) {
                        etFaxNo.setText(Const.notNullString(intentMap.get("scompfaxno").split("-")[1], ""));
                    }
                }else{
                    etCode.setText("");
                    etFaxNo.setText("");
                }
            }else{
                etCode.setText("");
                etFaxNo.setText("");
            }

            if(!intentMap.get("scompemail").equalsIgnoreCase("") && !intentMap.get("scompemail").equalsIgnoreCase("null")){
                etEmailAddress1.setText(Const.notNullString(intentMap.get("scompemail"),""));
            }else{
                etEmailAddress1.setText("");
            }
            if(!intentMap.get("scompemail2").equalsIgnoreCase("") && !intentMap.get("scompemail2").equalsIgnoreCase("null")){
                etEmailAddress2.setText(Const.notNullString(intentMap.get("scompemail2"),""));
            }else{
                etEmailAddress2.setText("");
            }
            if(!intentMap.get("swebsite").equalsIgnoreCase("") && !intentMap.get("swebsite").equalsIgnoreCase("null")){
                etWebsite.setText(Const.notNullString(intentMap.get("swebsite"),""));
            }else{
                etWebsite.setText("");
            }
            if(!intentMap.get("sskypeid").equalsIgnoreCase("") && !intentMap.get("sskypeid").equalsIgnoreCase("null")){
                etSkypeID.setText(Const.notNullString(intentMap.get("sskypeid"),""));
            }else{
                etSkypeID.setText("");
            }
            if(!intentMap.get("swechatid").equalsIgnoreCase("") && !intentMap.get("swechatid").equalsIgnoreCase("null")){
                etWeChatID.setText(Const.notNullString(intentMap.get("swechatid"),""));
            }else{
                etWeChatID.setText("");
            }
            if(!intentMap.get("scompregno").equalsIgnoreCase("") && !intentMap.get("scompregno").equalsIgnoreCase("null")){
                etBusinessRegNo.setText(Const.notNullString(intentMap.get("scompregno"),""));
            }else{
                etBusinessRegNo.setText("");
            }
            if(!intentMap.get("srapnetid").equalsIgnoreCase("") && !intentMap.get("srapnetid").equalsIgnoreCase("null")){
                etRapID.setText(Const.notNullString(intentMap.get("srapnetid"),""));
            }else{
                etRapID.setText("");
            }
            if(!intentMap.get("fortunepartycode").equalsIgnoreCase("") && !intentMap.get("fortunepartycode").equalsIgnoreCase("null")){
                etFortunePartyCode.setText(Const.notNullString(intentMap.get("fortunepartycode"),""));
            }else{
                etFortunePartyCode.setText("");
            }
            if(!intentMap.get("shkid").equalsIgnoreCase("") && !intentMap.get("shkid").equalsIgnoreCase("null")){
                etHKID.setText(Const.notNullString(intentMap.get("shkid"),""));
            }else{
                etHKID.setText("");
            }
            if(!intentMap.get("spassportid").equalsIgnoreCase("") && !intentMap.get("spassportid").equalsIgnoreCase("null")){
                etPassportNo.setText(Const.notNullString(intentMap.get("spassportid"),""));
            }else{
                etPassportNo.setText("");
            }
            if(Const.notNullString(intentMap.get("bisactive"),"").equalsIgnoreCase("true")){
                cbActive.setChecked(true);
                tvFortunePartyCode.setText("Fortune Party Code*");
            }else{
                cbActive.setChecked(false);
                tvFortunePartyCode.setText("Fortune Party Code");
            }
            if(Const.notNullString(intentMap.get("biscompuser"),"").equalsIgnoreCase("true")){
                cbIsCompany.setChecked(true);
            }else{
                cbIsCompany.setChecked(false);
            }
            if(Const.notNullString(intentMap.get("sstockcategory"),"").equalsIgnoreCase("T")){
                etStockType.setText("Total Stock");
            } else if (Const.notNullString(intentMap.get("sstockcategory"),"").equalsIgnoreCase("O")){
                etStockType.setText("Office Stock");
            } else if (Const.notNullString(intentMap.get("sstockcategory"),"").equalsIgnoreCase("OC")) {
                etStockType.setText("Office Stock with Consignment");
            } else if (Const.notNullString(intentMap.get("sstockcategory"),"").equalsIgnoreCase("OH")) {
                etStockType.setText("Office Stock with Hold");
            }
        }
    }

    private void check_Fortune_party_code(final String code) {
        Const.showProgress(context);
        final Map<String, String> map = new HashMap<>();
        map.put("iUserId", Const.notNullString(intentMap.get("iuserid"),""));
        map.put("FortunePartyCode", Const.notNullString(code,""));
        Const.callPostApi(context, "/User/FortunePartyCode_Exist", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.optString("Status").equalsIgnoreCase("1")) {
                        String ob = new JSONObject(result).optString("Message");
                        // startActivity(new Intent(context, NewLoginActivity.class));
                        Update();
                    }
                    else if(object.optString("Status").equalsIgnoreCase("-1"))
                    {
                        Const.dismissProgress();
                        String ob = new JSONObject(result).optString("Message");
                        Const.showErrorDialog(context, Const.notNullString(ob, "Enter Fortune Party Code must be Unique"));
                    }
                    else
                    {
                        Const.dismissProgress();
                        String ob = new JSONObject(result).optString("Message");
                        Const.showErrorDialog(context, Const.notNullString(ob, "Something went wrong please try again later"));
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
                        check_Fortune_party_code(code);
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    private void get_Message_Data() {
        arrayhmMessage = new ArrayList<>();
        arrayMessage = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("iPgNo", "1");
        map.put("iPgSize", "1000");
        map.put("IsActive", "true");
        Const.callPostApiLogin(context, "User/Get_MessageMst", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Log.e("Message : ", result);
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
                                arrayhmMessage.add(hm);
                            }
                            for (int i = 0; i < arrayhmMessage.size(); i++) {
                                arrayMessage.add(arrayhmMessage.get(i).get("messagename"));
                            }
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailerResponse(String error) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                    }
                };
            }
        });
    }

    private void Update() {
        final Map<String, String> map = new HashMap<>();
        map.put("UserID", Const.notNullString(intentMap.get("iuserid"),""));
        map.put("iiUserid", Const.notNullString(intentMap.get("iuserid"),""));
//        map.put("iiModifiedBy", Pref.getStringValue(context, Const.loginId, ""));
        map.put("UserName", Const.notNullString(etUserName.getText().toString().trim(),""));
        map.put("Password", Const.notNullString(etPassword.getText().toString().trim(),""));
        map.put("Suspended", Const.notNullString(intentMap.get("suspended"),""));
        map.put("PrevIsActive", Const.notNullString(intentMap.get("bisactive"),""));
        map.put("IsActive", cbActive.isChecked() ? "true" : "false");
        map.put("IsCompanyUser", cbIsCompany.isChecked() ? "true" : "false");
        map.put("UserType", Const.notNullString(etUserType.getText().toString().trim(),""));
        map.put("EmpID1", etAssist1.getText().toString().equals("") ? "" : arrayhmAssist.get(arrayAssist.indexOf(etAssist1.getText().toString())).get("iuserid"));
        map.put("EmpID2", etAssist2.getText().toString().equals("") ? "" : arrayhmAssist.get(arrayAssist.indexOf(etAssist2.getText().toString())).get("iuserid"));
        map.put("FirstName", Const.notNullString(etFirstName.getText().toString().trim(),""));
        map.put("LastName", Const.notNullString(etLastName.getText().toString().trim(),""));
        map.put("OtherName", Const.notNullString(etOtherName.getText().toString().trim(),""));
        if (Pref.getStringValue(context, Const.loginIsAdmin, "").equalsIgnoreCase("1"))
        {
             map.put("Remark",Const.notNullString(etRemark.getText().toString().trim(),""));
        }


        if(etStockType.getText().toString().trim().equalsIgnoreCase("Total Stock")){
            map.put("StockType", "T");
        } else if(etStockType.getText().toString().trim().equalsIgnoreCase("Office Stock")){
            map.put("StockType", "O");
        } else if(etStockType.getText().toString().trim().equalsIgnoreCase("Office Stock with Consignment")){
            map.put("StockType", "OC");
        } else if(etStockType.getText().toString().trim().equalsIgnoreCase("Office Stock with Hold")){
            map.put("StockType", "OH");
        } else{
            map.put("StockType", "");
        }
        map.put("FortunePartyCode", Const.notNullString(etFortunePartyCode.getText().toString().trim(),""));
        map.put("CompanyName", Const.notNullString(etCompName.getText().toString().trim(),""));
        map.put("CompanyAddress", Const.notNullString(etAddress1.getText().toString().trim(),""));
        map.put("CompanyAddress2", Const.notNullString(etAddress2.getText().toString().trim(),""));
//        map.put("CompCityId", Const.isEmpty(etCity) ? "" : Const.notNullString(arrayhmCity.get(arrayCity.indexOf(etCity.getText().toString())).get("icityid"),""));
//        map.put("CompCountryId", Const.isEmpty(etCountry) ? "" : Const.notNullString(arrayhmCountry.get(arrayCountry.indexOf(etCountry.getText().toString())).get("icountryid"),""));
        map.put("CompCountry", Const.notNullString(etCountry.getText().toString().trim(),""));
        map.put("CompCity", Const.notNullString(etCity.getText().toString().trim(),""));
        map.put("CompZipCode", Const.notNullString(etZipCode.getText().toString().trim(),""));
        if((!etISD1.getText().toString().trim().equalsIgnoreCase("")) || (!etMobile1.getText().toString().trim().equalsIgnoreCase(""))){
            map.put("CompMobile", Const.notNullString(etISD1.getText().toString().trim(),"") + "-" + Const.notNullString(etMobile1.getText().toString().trim(),""));
        }else{
            map.put("CompMobile","");
        }
        if((!etISD2.getText().toString().trim().equalsIgnoreCase("")) || (!etMobile2.getText().toString().trim().equalsIgnoreCase(""))){
            map.put("CompMobile2", Const.notNullString(etISD2.getText().toString().trim(),"") + "-" + Const.notNullString(etMobile2.getText().toString().trim(),""));
        }else{
            map.put("CompMobile2","");
        }
        if((!etAreaCode1.getText().toString().trim().equalsIgnoreCase("")) || (!etOfficePhone1.getText().toString().trim().equalsIgnoreCase(""))){
            map.put("CompPhone", Const.notNullString(etAreaCode1.getText().toString().trim(),"") + "-" + Const.notNullString(etOfficePhone1.getText().toString().trim(),""));
        }else{
            map.put("CompPhone","");
        }
        if((!etAreaCode2.getText().toString().trim().equalsIgnoreCase("")) || (!etOfficePhone2.getText().toString().trim().equalsIgnoreCase(""))){
            map.put("CompPhone2", Const.notNullString(etAreaCode2.getText().toString().trim(),"") + "-" + Const.notNullString(etOfficePhone2.getText().toString().trim(),""));
        }else{
            map.put("CompPhone2","");
        }
        if((!etCode.getText().toString().trim().equalsIgnoreCase("")) || (!etFaxNo.getText().toString().trim().equalsIgnoreCase(""))){
            map.put("CompFaxNo",  Const.notNullString(etCode.getText().toString().trim(),"") + "-" + Const.notNullString(etFaxNo.getText().toString().trim(),""));
        }else{
            map.put("CompFaxNo","");
        }
        map.put("CompEmail", Const.notNullString(etEmailAddress1.getText().toString().trim(),""));
        map.put("CompEmail2", Const.notNullString(etEmailAddress2.getText().toString().trim(),""));
        map.put("Website", Const.notNullString(etWebsite.getText().toString().trim(),""));
        map.put("SkypeId", Const.notNullString(etSkypeID.getText().toString().trim(),""));
        map.put("WeChatId", Const.notNullString(etWeChatID.getText().toString().trim(),""));
        map.put("CompRegNo", Const.notNullString(etBusinessRegNo.getText().toString().trim(),""));
        map.put("HkId", Const.notNullString(etHKID.getText().toString().trim(),""));
        map.put("PassportId", Const.notNullString(etPassportNo.getText().toString().trim(),""));
        map.put("RapnetID", Const.notNullString(etRapID.getText().toString().trim(),""));
        map.put("MessageId", etMessage.getText().toString().equals("") ? "" : arrayhmMessage.get(arrayMessage.indexOf(etMessage.getText().toString())).get("id"));

        if(Const.notNullString(etUserType.getText().toString().trim(),"").equalsIgnoreCase("Registered")) {
            map.put("IsPrimary", cbActive.isChecked() ? "true" : "false");
        }
        else
        {
            map.put("IsPrimary", "");
        }

        Const.callPostApi(context, "/User/UpdateUser", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Const.dismissProgress();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.optString("Status").equalsIgnoreCase("1")) {
                        String ob = new JSONObject(result).optString("Message");
                        // startActivity(new Intent(context, NewLoginActivity.class));
                        showDialog(context, Const.notNullString(ob, "Profile Details Updated Successfully!"));
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
                        Update();
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    private void CountryList() {
        Const.showProgress(context);
        arrayhmCountry = new ArrayList<>();
        arrayCountry = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("sSearch", "");
        Const.callPostApi1(context, Const.BaseUrl + "Master/GetCountryListAutoComplete", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Const.dismissProgress();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.optString("Status").equalsIgnoreCase("1")) {
                        JSONArray array = object.optJSONArray("Data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject objectData = array.optJSONObject(i);
                            JSONObject object1 = array.optJSONObject(i);
                            Iterator<String> keys = object1.keys();
                            HashMap<String, String> hm = new HashMap<>();
                            while (keys.hasNext()) {
                                String key = keys.next();
                                hm.put(key.toLowerCase(), object1.optString(key).trim());
                            }
                            arrayhmCountry.add(hm);
                        }
                        for (int i = 0; i < arrayhmCountry.size(); i++) {
                            arrayCountry.add(arrayhmCountry.get(i).get("scountryname"));
                        }
                    } else {
                        Const.showErrorDialog(context, Const.notNullString(object.optString("Message"), "Something went wrong."));
                    }
                } catch (JSONException e) {
                    Log.e("errror", e.toString());
                    Const.showErrorDialog(context, "Something went wrong.");
                }
            }

            @Override
            public void onFailerResponse(String error) {
                Const.dismissProgress();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        CountryList();
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    private void CountryCodeList(final String CountryName) {
        Const.showProgress(context);
        Map<String, String> map = new HashMap<>();
        map.put("sCountryName",  Const.notNullString(CountryName,""));
        Const.callPostApi1(context, Const.BaseUrl + "Master/GetCountryFromCountryName", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
               // Const.dismissProgress();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.optString("Status").equalsIgnoreCase("1")) {
                        JSONArray array = object.optJSONArray("Data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject objectData = array.optJSONObject(i);
                            JSONObject object1 = array.optJSONObject(i);
                            Iterator<String> keys = object1.keys();
                            HashMap<String, String> hm = new HashMap<>();
                            while (keys.hasNext()) {
                                String key = keys.next();
                                hm.put(key.toLowerCase(), object1.optString(key).trim());
                            }
                            Pref.setStringValue(context, Const.CountryCode, Const.notNullString(hm.get("icountryid"), ""));
                        }
                        CityList(Pref.getStringValue(context, Const.CountryCode, ""));
                    } else {
                        Pref.setStringValue(context, Const.CountryCode, "");
                        Const.showErrorDialog(context, Const.notNullString(object.optString("Message"), "Something went wrong."));
                    }
                } catch (JSONException e) {
                    Log.e("errror", e.toString());
                    Const.showErrorDialog(context, "Something went wrong.");
                }
            }

            @Override
            public void onFailerResponse(String error) {
                Const.dismissProgress();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        CountryCodeList(CountryName);
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    private void CityList(final String CountryCode) {
        Const.showProgress(context);
        arrayhmCity = new ArrayList<>();
        arrayCity = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("sSearch", "");
        map.put("CountryId", Const.notNullString(CountryCode,""));
        Const.callPostApi1(context, Const.BaseUrl + "Master/GetCityListAutocomplete", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Const.dismissProgress();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.optString("Status").equalsIgnoreCase("1")) {
                        JSONArray array = object.optJSONArray("Data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject objectData = array.optJSONObject(i);
                            JSONObject object1 = array.optJSONObject(i);
                            Iterator<String> keys = object1.keys();
                            HashMap<String, String> hm = new HashMap<>();
                            while (keys.hasNext()) {
                                String key = keys.next();
                                hm.put(key.toLowerCase(), object1.optString(key).trim());
                            }
                            arrayhmCity.add(hm);
                        }
                        for (int i = 0; i < arrayhmCity.size(); i++) {
                            arrayCity.add(arrayhmCity.get(i).get("scityname"));
                        }
                    } else {
                          Const.showErrorDialog(context, Const.notNullString(object.optString("Message"), "Something went wrong."));
                    }
                } catch (JSONException e) {
                    Log.e("errror", e.toString());
                    Const.showErrorDialog(context, "Something went wrong.");
                }
            }

            @Override
            public void onFailerResponse(String error) {
                Const.dismissProgress();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        CityList(CountryCode);
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    public void showDialog(final Context context, String message) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_error_dialog);

        TextView tvMessage = (TextView) dialog.findViewById(R.id.tvMessage);
        tvMessage.setText(message);

        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(context, ManageUserActivity.class));
                finish();
            }
        });

        dialog.show();
    }

    private void getAssistant() {
        Const.showProgress(context);
        arrayAssist = new ArrayList<>();
        arrayhmAssist = new ArrayList<>();
        final Map<String, String> map = new HashMap<>();
        map.put("CountryName", "");
        map.put("UserName", "");
        map.put("CompanyName", "");
        map.put("UserFullName", "");
        map.put("UserType", "2");
        map.put("UserStatus", "");
        map.put("PageNo", "");
        Const.callPostApi(context, "User/GetFullUserList", map, new VolleyCallback() {
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
                                arrayhmAssist.add(hm);
                            }
                            for (int i = 0; i < arrayhmAssist.size(); i++) {
                                arrayAssist.add(arrayhmAssist.get(i).get("sfullname"));
                            }
                        } else {
                            Const.showErrorDialog(context, object.optString("Message"));
                        }
                    } else {
                        if (object.optString("Message").equalsIgnoreCase("Unauthorised")) {
                            Pref.removeValue(context, Const.loginId);
                            startActivity(new Intent(context, LoginActivity.class));
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        } else {
                            Const.showErrorDialog(context, Const.notNullString(object.optString("Message"), "Something want wrong !"));
                        }
                    }
                } catch (JSONException e) {
                    Const.showErrorDialog(context, "Something want wrong");
                }
            }

            @Override
            public void onFailerResponse(String error) {
                Const.dismissProgress();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        getAssistant();
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    private void openSelection(final EditText editText, ArrayList<String> array, final String title) {
        final Dialog dialog = new Dialog(context, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dialog.getWindow().setBackgroundDrawable(Const.setBackgoundBorder(0, 20, Color.WHITE, Color.WHITE));
        dialog.setContentView(R.layout.layout_selection_dialog);

        LinearLayout llNoData = dialog.findViewById(R.id.llNoData);
        TextView tvNoData = dialog.findViewById(R.id.tvNoData);
        Button btnClear = dialog.findViewById(R.id.btnClear);
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        final EditText etSearch = dialog.findViewById(R.id.etSearch);
        final ListView listView = dialog.findViewById(R.id.listView);
        tvTitle.setText(title);
        if (array.size() != 0) {
            adapter = new ArrayAdapter<String>(context, R.layout.layout_spinner_item, array);
            listView.setAdapter(adapter);
        } else {
            llNoData.setVisibility(View.VISIBLE);
            String[] name = title.split(" ");
            tvNoData.setText(name[1] + " Not Available");
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (title.equalsIgnoreCase("Select Country")) {
                    etCity.setText("");
                    etISD1.setText("");
                    etISD2.setText("");
                    etAreaCode1.setText("");
                    etAreaCode2.setText("");
                    etCode.setText("");
                    CityList(Const.notNullString(arrayhmCountry.get(arrayCountry.indexOf(listView.getItemAtPosition(i).toString())).get("icountryid"),""));
                } else if (title.equalsIgnoreCase("Select City")) {
                    if(!Const.notNullString(arrayhmCity.get(arrayCity.indexOf(listView.getItemAtPosition(i).toString())).get("sisdcode"),"").equalsIgnoreCase("0")){
                        etISD1.setText("+" + Const.notNullString(arrayhmCity.get(arrayCity.indexOf(listView.getItemAtPosition(i).toString())).get("sisdcode"),""));
                        etISD2.setText("+" + Const.notNullString(arrayhmCity.get(arrayCity.indexOf(listView.getItemAtPosition(i).toString())).get("sisdcode"),""));
                        etCode.setText("+" + Const.notNullString(arrayhmCity.get(arrayCity.indexOf(listView.getItemAtPosition(i).toString())).get("sisdcode"),"") + "  " + Const.notNullString(arrayhmCity.get(arrayCity.indexOf(listView.getItemAtPosition(i).toString())).get("istdcode"),""));
                    } else {
                        etISD1.setText("");
                        etISD2.setText("");
                        etCode.setText("+" + "" + "  " + Const.notNullString(arrayhmCity.get(arrayCity.indexOf(listView.getItemAtPosition(i).toString())).get("istdcode"),""));
                    }

                    if(!Const.notNullString(arrayhmCity.get(arrayCity.indexOf(listView.getItemAtPosition(i).toString())).get("istdcode"),"").equalsIgnoreCase("0")){
                        etAreaCode1.setText("+" + Const.notNullString(arrayhmCity.get(arrayCity.indexOf(listView.getItemAtPosition(i).toString())).get("istdcode"),""));
                        etAreaCode2.setText("+" + Const.notNullString(arrayhmCity.get(arrayCity.indexOf(listView.getItemAtPosition(i).toString())).get("istdcode"),""));
                        etCode.setText("+" + Const.notNullString(arrayhmCity.get(arrayCity.indexOf(listView.getItemAtPosition(i).toString())).get("sisdcode"),"") + "  " + Const.notNullString(arrayhmCity.get(arrayCity.indexOf(listView.getItemAtPosition(i).toString())).get("istdcode"),""));
                    } else {
                        etAreaCode1.setText("");
                        etAreaCode2.setText("");
                        etCode.setText("+" + Const.notNullString(arrayhmCity.get(arrayCity.indexOf(listView.getItemAtPosition(i).toString())).get("sisdcode"),"") + "  " + "");
                    }
                }
                editText.setText(listView.getItemAtPosition(i).toString());
                dialog.dismiss();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (title.equalsIgnoreCase("Select Country")) {
                    etCity.setText("");
                    etISD1.setText("");
                    etISD2.setText("");
                    etAreaCode1.setText("");
                    etAreaCode2.setText("");
                    etCode.setText("");
                } else if (title.equalsIgnoreCase("Select City")) {
                    etISD1.setText("");
                    etISD2.setText("");
                    etAreaCode1.setText("");
                    etAreaCode2.setText("");
                    etCode.setText("");
                }
                editText.setText("");
                dialog.dismiss();
            }
        });

        etSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (etSearch.getCompoundDrawables()[2] != null) {
                        if (event.getRawX() >= (etSearch.getRight() - etSearch.getCompoundDrawables()[2].getBounds().width())) {
                            etSearch.setText("");
                            etSearch.setCompoundDrawables(null, null, null, null);
                            return true;
                        }
                    }
                }
                return false;
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equalsIgnoreCase("")) {
                    Drawable wrappedDrawable = DrawableCompat.wrap(AppCompatResources.getDrawable(context, R.drawable.ic_close));
                    DrawableCompat.setTint(wrappedDrawable, Color.BLACK);
                    etSearch.setCompoundDrawablesWithIntrinsicBounds(null, null, wrappedDrawable, null);
                } else {
                    etSearch.setCompoundDrawables(null, null, null, null);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                adapter.getFilter().filter(editable.toString());
            }
        });

        dialog.show();
    }

    private void clearForm() {
        etUserName.setText("");
        etPassword.setText("");
        cbActive.setChecked(false);
        cbIsCompany.setChecked(false);
        etAssist1.setText("");
        etAssist2.setText("");
        etFirstName.setText("");
        etLastName.setText("");
        etOtherName.setText("");
        etStockType.setText("");
        etFortunePartyCode.setText("");
        etCompName.setText("");
        etAddress1.setText("");
        etAddress2.setText("");
        etCity.setText("");
        etZipCode.setText("");
        etCountry.setText("");
        etISD1.setText("");
        etMobile1.setText("");
        etISD2.setText("");
        etMobile2.setText("");
        etAreaCode1.setText("");
        etOfficePhone1.setText("");
        etAreaCode2.setText("");
        etOfficePhone2.setText("");
        etCode.setText("");
        etFaxNo.setText("");
        etEmailAddress1.setText("");
        etEmailAddress2.setText("");
        etWebsite.setText("");
        etSkypeID.setText("");
        etWeChatID.setText("");
        etBusinessRegNo.setText("");
        etRapID.setText("");
        etHKID.setText("");
        etPassportNo.setText("");
        etUserType.setText("");
        etMessage.setText("");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @SuppressLint("WrongConstant")
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.llUpdate:
                    if (etPassword.getText().toString().length() < 6) {
                        Const.showErrorDialog(context, "Please Enter Minimum 6 Characters Password");
                    } else if (Const.isEmpty(etAddress1)) {
                        Const.showErrorDialog(context, "Please Enter Address1");
                    } else if (Const.isEmpty(etCountry)) {
                        Const.showErrorDialog(context, "Please Enter Country");
                    } else if (Const.isEmpty(etCity)) {
                        Const.showErrorDialog(context, "Please Enter City");
                    } else if (Const.isEmpty(etMobile1)) {
                        Const.showErrorDialog(context, "Please Enter Mobile1");
                    } else if (Const.isValidEmail(etEmailAddress1)) {
                        Const.showErrorDialog(context, "Please Enter EmailAddress1");
                    } else if (Const.notNullString(intentMap.get("susertype"),"").equalsIgnoreCase("Registered") && Const.isEmpty(etAssist1)){
                        Const.showErrorDialog(context, "Please Select Assist 1");
                    } else if(cbActive.isChecked() && Const.isEmpty(etFortunePartyCode)){
                        Const.showErrorDialog(context, "Please Enter Fortune Party Code");
                    } else {
                        check_Fortune_party_code(etFortunePartyCode.getText().toString());
//                        Update();
                    }
                    break;
                case R.id.etStockType:
                     openSelection(etStockType, arrayStockType, "Select Stock Type");
                    break;
                case R.id.etAssist1:
                    openSelection(etAssist1, arrayAssist, "Select Assist 1");
                    break;
                case R.id.etAssist2:
                    openSelection(etAssist2, arrayAssist, "Select Assist 2");
                    break;
                case R.id.etUserType:
                    openSelection(etUserType, arrayUserType,"Select User Type");
                    break;
                case R.id.etMessage:
                    openSelection(etMessage, arrayMessage,"Select Message Name");
                    break;
                case R.id.cbActive:
                    if(cbActive.isChecked()){
                        tvFortunePartyCode.setText("Fortune Party Code*");
                    }else{
                        tvFortunePartyCode.setText("Fortune Party Code");
                    }
                     break;
                case R.id.etCountry:
                    if (arrayCountry.size() != 0) {
                        openSelection(etCountry, arrayCountry, "Select Country");
                    } else {
                        Const.showErrorDialog(context, "Country Not Available");
                    }
                    break;
                case R.id.etCity:
                    if (!Const.isEmpty(etCountry)) {
                        if (arrayCity.size() != 0) {
                            openSelection(etCity, arrayCity, "Select City");
                        } else {
                            Const.showErrorDialog(context, "City Not Available");
                        }
                    } else {
                        Const.showErrorDialog(context, "First Please Select Country");
                    }
                    break;
                case R.id.llReset:
                    clearForm();
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


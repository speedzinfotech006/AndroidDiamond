package com.dnk.shairugems;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.dnk.shairugems.Utils.Const;
import com.dnk.shairugems.Utils.Pref;
import com.dnk.shairugems.Volly.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    Toolbar toolbar;
    LinearLayout llUpdate;
    ImageView imgProfile;
    AutoCompleteTextView etCountry,etCity;
    ScrollView svScroll;
    EditText etFirstName,etLastName,etCompName,etAddress1,etAddress2,etZipCode,
            etISD1,etMobile1,etISD2,etMobile2,etAreaCode1,etOfficePhone1,etAreaCode2,etOfficePhone2,etCode,etFaxNo,etEmailAddress1,
            etEmailAddress2,etWebsite,etSkypeID,etWeChatID,etBusinessRegNo,etRapID;
    TextView tvFullName;
    ArrayList<HashMap<String, String>> arrayhmCountry, arrayhmCity;
    ArrayList<String> arrayCountry, arrayCity;
    List<HashMap<String, String>> arrayhmUser;

    private String ProfileBase64Img = "";
    private static int RESULT_LOAD_IMAGE = 1;
    Context context = ProfileActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        initView();

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initView() {
        toolbar = findViewById(R.id.toolBar);
        svScroll = findViewById(R.id.svScroll);
        imgProfile = findViewById(R.id.imgProfile);
        tvFullName = findViewById(R.id.tvFullName);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
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
        etRapID = findViewById(R.id.etRapID);

        llUpdate = findViewById(R.id.llUpdate);
        llUpdate.setBackgroundDrawable(Const.setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));

        llUpdate.setOnClickListener(clickListener);
        imgProfile.setOnClickListener(clickListener);

        etCountry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().startsWith(" ")) {
                    etCountry.setText("");
                } else {
                    if (!charSequence.toString().equalsIgnoreCase("")) {
                        CountryList();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        etCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                etCity.setText("");
                etISD1.setText("");
                etISD2.setText("");
                etAreaCode1.setText("");
                etAreaCode2.setText("");
                etCode.setText("");
            }
        });

        etCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().startsWith(" ")) {
                    etCity.setText("");
                } else {
                    if (!charSequence.toString().equalsIgnoreCase("")) {
                        if(!Const.isEmpty(etCountry)){
                            CityList();
                        }else{
                            Const.showErrorDialog(context, "First Please Enter Country");
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                CityListCode();
            }
        });

        GetUserDetails();
        GetProfilePicture();
    }

    private void GetUserDetails() {
        Const.showProgress(context);
        arrayhmUser = new ArrayList<>();
        final Map<String, String> map = new HashMap<>();
        Const.callPostApi(context, "User/GetUserDetails", map, new VolleyCallback() {
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
                                arrayhmUser.add(hm);
                            }
                            for (int i = 0; i < arrayhmUser.size(); i++) {

                                if(!arrayhmUser.get(i).get("sfullname").equalsIgnoreCase("") && !arrayhmUser.get(i).get("sfullname").equalsIgnoreCase("null")){
                                    tvFullName.setText(Const.notNullString(arrayhmUser.get(i).get("sfullname"),""));
                                }else{
                                    tvFullName.setText("");
                                }

                                if(!arrayhmUser.get(i).get("sfirstname").equalsIgnoreCase("") && !arrayhmUser.get(i).get("sfirstname").equalsIgnoreCase("null")){
                                    etFirstName.setText(Const.notNullString(arrayhmUser.get(i).get("sfirstname"),""));
                                }else{
                                    etFirstName.setText("");
                                }
                                if(!arrayhmUser.get(i).get("slastname").equalsIgnoreCase("") && !arrayhmUser.get(i).get("slastname").equalsIgnoreCase("null")){
                                    etLastName.setText(Const.notNullString(arrayhmUser.get(i).get("slastname"),""));
                                }else{
                                    etLastName.setText("");
                                }
                                if(!arrayhmUser.get(i).get("scompname").equalsIgnoreCase("") && !arrayhmUser.get(i).get("scompname").equalsIgnoreCase("null")){
                                    etCompName.setText(Const.notNullString(arrayhmUser.get(i).get("scompname"),""));
                                }else{
                                    etCompName.setText("");
                                }
                                if(!arrayhmUser.get(i).get("scompaddress").equalsIgnoreCase("") && !arrayhmUser.get(i).get("scompaddress").equalsIgnoreCase("null")){
                                    etAddress1.setText(Const.notNullString(arrayhmUser.get(i).get("scompaddress"),""));
                                }else{
                                    etAddress1.setText("");
                                }
                                if(!arrayhmUser.get(i).get("scompaddress2").equalsIgnoreCase("") && !arrayhmUser.get(i).get("scompaddress2").equalsIgnoreCase("null")){
                                    etAddress2.setText(Const.notNullString(arrayhmUser.get(i).get("scompaddress2"),""));
                                }else{
                                    etAddress2.setText("");
                                }
                                if(!arrayhmUser.get(i).get("scompcountry").equalsIgnoreCase("") && !arrayhmUser.get(i).get("scompcountry").equalsIgnoreCase("null")){
                                    etCountry.setText(Const.notNullString(arrayhmUser.get(i).get("scompcountry"),""));
                                }else{
                                    etCountry.setText("");
                                }
                                if(!arrayhmUser.get(i).get("scompcity").equalsIgnoreCase("") && !arrayhmUser.get(i).get("scompcity").equalsIgnoreCase("null")){
                                    etCity.setText(Const.notNullString(arrayhmUser.get(i).get("scompcity"),""));
                                }else{
                                    etCity.setText("");
                                }
                                if(!arrayhmUser.get(i).get("scompzipcode").equalsIgnoreCase("") && !arrayhmUser.get(i).get("scompzipcode").equalsIgnoreCase("null")){
                                    etZipCode.setText(Const.notNullString(arrayhmUser.get(i).get("scompzipcode"),""));
                                }else{
                                    etZipCode.setText("");
                                }
                                if(!arrayhmUser.get(i).get("scompmobile").equalsIgnoreCase("") && !arrayhmUser.get(i).get("scompmobile").equalsIgnoreCase("null")){
                                    if(arrayhmUser.get(i).get("scompmobile").contains("-")) {
                                            etISD1.setText(Const.notNullString(arrayhmUser.get(i).get("scompmobile").split("-")[0],""));
                                        if(arrayhmUser.get(i).get("scompmobile").length()>8) {
                                            etMobile1.setText(Const.notNullString(arrayhmUser.get(i).get("scompmobile").split("-")[1], ""));
                                        }
                                    }else {
                                        etISD1.setText("");
                                        etMobile1.setText("");
                                    }
                                } else {
                                    etISD1.setText("");
                                    etMobile1.setText("");
                                }

                                if(!arrayhmUser.get(i).get("scompmobile2").equalsIgnoreCase("") && !arrayhmUser.get(i).get("scompmobile2").equalsIgnoreCase("null")){
                                    if(arrayhmUser.get(i).get("scompmobile2").contains("-")) {
                                            etISD2.setText(Const.notNullString(arrayhmUser.get(i).get("scompmobile2").split("-")[0], ""));
                                        if(arrayhmUser.get(i).get("scompmobile2").length()>8) {
                                            etMobile2.setText(Const.notNullString(arrayhmUser.get(i).get("scompmobile2").split("-")[1], ""));
                                        }
                                    }else {
                                        etISD1.setText("");
                                        etMobile1.setText("");
                                    }
                                }else{
                                    etISD2.setText("");
                                    etMobile2.setText("");
                                }

                                if(!arrayhmUser.get(i).get("scompphone").equalsIgnoreCase("") && !arrayhmUser.get(i).get("scompphone").equalsIgnoreCase("null")){
                                    if(arrayhmUser.get(i).get("scompphone").contains("-")) {
                                            etAreaCode1.setText(Const.notNullString(arrayhmUser.get(i).get("scompphone").split("-")[0], ""));
                                        if(arrayhmUser.get(i).get("scompphone").length()>8) {
                                            etOfficePhone1.setText(Const.notNullString(arrayhmUser.get(i).get("scompphone").split("-")[1], ""));
                                        }
                                    }else {
                                        etAreaCode1.setText("");
                                        etOfficePhone1.setText("");
                                    }
                                } else {
                                    etAreaCode1.setText("");
                                    etOfficePhone1.setText("");
                                }

                                if(!arrayhmUser.get(i).get("scompphone2").equalsIgnoreCase("") && !arrayhmUser.get(i).get("scompphone2").equalsIgnoreCase("null")){
                                    if(arrayhmUser.get(i).get("scompphone2").contains("-")) {
                                            etAreaCode2.setText(Const.notNullString(arrayhmUser.get(i).get("scompphone2").split("-")[0], ""));
                                        if(arrayhmUser.get(i).get("scompphone2").length()>8) {
                                            etOfficePhone2.setText(Const.notNullString(arrayhmUser.get(i).get("scompphone2").split("-")[1], ""));
                                        }
                                    }else{
                                        etAreaCode2.setText("");
                                        etOfficePhone2.setText("");
                                    }
                                }else{
                                    etAreaCode2.setText("");
                                    etOfficePhone2.setText("");
                                }

                                if(!arrayhmUser.get(i).get("scompfaxno").equalsIgnoreCase("") && !arrayhmUser.get(i).get("scompfaxno").equalsIgnoreCase("null")){
                                    if(arrayhmUser.get(i).get("scompfaxno").contains("-")) {
                                            etCode.setText(Const.notNullString(arrayhmUser.get(i).get("scompfaxno").split("-")[0], ""));
                                        if(arrayhmUser.get(i).get("scompfaxno").length()>8) {
                                            etFaxNo.setText(Const.notNullString(arrayhmUser.get(i).get("scompfaxno").split("-")[1], ""));
                                        }
                                    }else{
                                        etCode.setText("");
                                        etFaxNo.setText("");
                                    }
                                }else{
                                    etCode.setText("");
                                    etFaxNo.setText("");
                                }
                                if(!arrayhmUser.get(i).get("scompemail").equalsIgnoreCase("") && !arrayhmUser.get(i).get("scompemail").equalsIgnoreCase("null")){
                                    etEmailAddress1.setText(Const.notNullString(arrayhmUser.get(i).get("scompemail"),""));
                                }else{
                                    etEmailAddress1.setText("");
                                }
                                if(!arrayhmUser.get(i).get("scompemail2").equalsIgnoreCase("") && !arrayhmUser.get(i).get("scompemail2").equalsIgnoreCase("null")){
                                    etEmailAddress2.setText(Const.notNullString(arrayhmUser.get(i).get("scompemail2"),""));
                                }else{
                                    etEmailAddress2.setText("");
                                }
                                if(!arrayhmUser.get(i).get("swebsite").equalsIgnoreCase("") && !arrayhmUser.get(i).get("swebsite").equalsIgnoreCase("null")){
                                    etWebsite.setText(Const.notNullString(arrayhmUser.get(i).get("swebsite"),""));
                                }else{
                                    etWebsite.setText("");
                                }
                                if(!arrayhmUser.get(i).get("sskypeid").equalsIgnoreCase("") && !arrayhmUser.get(i).get("sskypeid").equalsIgnoreCase("null")){
                                    etSkypeID.setText(Const.notNullString(arrayhmUser.get(i).get("sskypeid"),""));
                                }else{
                                    etSkypeID.setText("");
                                }
                                if(!arrayhmUser.get(i).get("swechatid").equalsIgnoreCase("") && !arrayhmUser.get(i).get("swechatid").equalsIgnoreCase("null")){
                                    etWeChatID.setText(Const.notNullString(arrayhmUser.get(i).get("swechatid"),""));
                                }else{
                                    etWeChatID.setText("");
                                }
                                if(!arrayhmUser.get(i).get("scompregno").equalsIgnoreCase("") && !arrayhmUser.get(i).get("scompregno").equalsIgnoreCase("null")){
                                    etBusinessRegNo.setText(Const.notNullString(arrayhmUser.get(i).get("scompregno"),""));
                                }else{
                                    etBusinessRegNo.setText("");
                                }
                                if(!arrayhmUser.get(i).get("srapnetid").equalsIgnoreCase("") && !arrayhmUser.get(i).get("srapnetid").equalsIgnoreCase("null")){
                                    etRapID.setText(Const.notNullString(arrayhmUser.get(i).get("srapnetid"),""));
                                }else{
                                    etRapID.setText("");
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
                        GetUserDetails();
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    private void GetProfilePicture() {
        Const.showProgress(context);
        final Map<String, String> map = new HashMap<>();
        Const.callPostApi(context, "User/GetUserProfilePicture", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result1) {
                Const.dismissProgress();
                try {
                    String result = result1.replaceAll("^[\"']+|[\"']+$", "");
                    if (!result.equalsIgnoreCase("")) {
                        try {
                            byte[] decodedString = Base64.decode(result, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            imgProfile.setImageBitmap(decodedByte);
                        } catch (Exception ex) {

                        }
                    } else {
                     //   Const.showErrorDialog(context,"Error to download video, video is not MP4..!");
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
                        GetProfilePicture();
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }


    private void Update() {
        Const.showProgress(context);
        final Map<String, String> map = new HashMap<>();
        map.put("UserID", Pref.getStringValue(context, Const.loginId, ""));
        map.put("FirstName", Const.notNullString(etFirstName.getText().toString().trim(),""));
        map.put("LastName", Const.notNullString(etLastName.getText().toString().trim(),""));
        map.put("CompanyName", Const.notNullString(etCompName.getText().toString().trim(),""));
        map.put("CompanyAddress", Const.notNullString(etAddress1.getText().toString().trim(),""));
        map.put("CompanyAddress2", Const.notNullString(etAddress2.getText().toString().trim(),""));
        map.put("CompCity", Const.notNullString(etCity.getText().toString().trim(),""));
        map.put("CompZipCode", Const.notNullString(etZipCode.getText().toString().trim(),""));
        map.put("CompCountry", Const.notNullString(etCountry.getText().toString().trim(),""));
        map.put("CompEmail", Const.notNullString(etEmailAddress1.getText().toString().trim(),""));
        map.put("RapnetID", Const.notNullString(etRapID.getText().toString().trim(),""));
        map.put("CompRegNo", Const.notNullString(etBusinessRegNo.getText().toString().trim(),""));
       // map.put("CompEmail2", Const.notNullString(etEmailAddress2.getText().toString().trim(),""));
        map.put("WeChatId", Const.notNullString(etWeChatID.getText().toString().trim(),""));
        map.put("SkypeId", Const.notNullString(etSkypeID.getText().toString().trim(),""));
        map.put("Website", Const.notNullString(etWebsite.getText().toString().trim(),""));
//        map.put("CompCityId", Const.isEmpty(etCity) ? "" : Const.notNullString(arrayhmCity.get(arrayCity.indexOf(etCity.getText().toString())).get("icityid"),""));
//        map.put("CompCountryId", Const.isEmpty(etCountry) ? "" : Const.notNullString(arrayhmCountry.get(arrayCountry.indexOf(etCountry.getText().toString())).get("icountryid"),""));
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
        map.put("IsProfileChanged", ProfileBase64Img == "" ? "0" : "1");
        Const.callPostApi(context, "User/UpdateUserProfileDetails", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Const.dismissProgress();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.optString("Status").equalsIgnoreCase("1")) {
                        String ob = new JSONObject(result).optString("Message");
                        // startActivity(new Intent(context, NewLoginActivity.class));
                        Const.showErrorDialog(context, Const.notNullString(ob, "Profile Details Updated Successfully!"));
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

    private void UpdateProfile() {
        Const.showProgress(context);
        final Map<String, String> map = new HashMap<>();
        map.put("Photo", ProfileBase64Img == "" ? "" : ProfileBase64Img);
        map.put("FileExtenstion", ".JPEG");
        Const.callPostApi(context, "User/UpdateUserProfilePicture", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Const.dismissProgress();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.optString("Status").equalsIgnoreCase("1")) {
                        String ob = new JSONObject(result).optString("Message");
                        // startActivity(new Intent(context, NewLoginActivity.class));
                        //Const.showErrorDialog(context, Const.notNullString(ob, "Profile Picture uploaded successfully!"));
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
                        UpdateProfile();
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    private void CountryList() {
        arrayhmCountry = new ArrayList<>();
        arrayCountry = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("sSearch", Const.notNullString(etCountry.getText().toString().trim(),""));
        Const.callPostApi1(context, Const.BaseUrl + "Master/GetCountryListAutoComplete", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
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
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.layout_search_item, R.id.tvText, arrayCountry);
                        etCountry.setAdapter(adapter);
                    } else {
                        //   Const.showErrorDialog(context, Const.notNullString(object.optString("Message"), "Something went wrong."));
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

    private void CityList() {
        arrayhmCity = new ArrayList<>();
        arrayCity = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("sSearch", Const.notNullString(etCity.getText().toString().trim(),""));
       // map.put("CountryId", Const.isEmpty(etCountry) ? "" : Const.notNullString(arrayhmCountry.get(arrayCountry.indexOf(etCountry.getText().toString())).get("icountryid"),""));
        Const.callPostApi1(context, Const.BaseUrl + "Master/GetCityListAutocomplete", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                //  Const.dismissProgress();
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
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.layout_search_item, R.id.tvText, arrayCity);
                        etCity.setAdapter(adapter);
                    } else {
                        // Const.showErrorDialog(context, Const.notNullString(object.optString("Message"), "Something went wrong."));
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
                        CityList();
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    private void CityListCode() {
        arrayhmCity = new ArrayList<>();
        arrayCity = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("sSearch", Const.notNullString(etCity.getText().toString().trim(),""));
        map.put("CountryId", Const.isEmpty(etCountry) ? "" : Const.notNullString(arrayhmCountry.get(arrayCountry.indexOf(etCountry.getText().toString())).get("icountryid"),""));
        Const.callPostApi1(context, Const.BaseUrl + "Master/GetCityListAutocomplete", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                //  Const.dismissProgress();
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
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.layout_search_item, R.id.tvText, arrayCity);
                        etCity.setAdapter(adapter);
                    } else {
                        // Const.showErrorDialog(context, Const.notNullString(object.optString("Message"), "Something went wrong."));
                    }
                } catch (JSONException e) {
                    Log.e("errror", e.toString());
                    Const.showErrorDialog(context, "Something went wrong.");
                }
                if(!Const.notNullString(arrayhmCity.get(arrayCity.indexOf(etCity.getText().toString())).get("sisdcode"),"").equalsIgnoreCase("0")){
                    etISD1.setText("+" + Const.notNullString(arrayhmCity.get(arrayCity.indexOf(etCity.getText().toString())).get("sisdcode"),""));
                    etISD2.setText("+" + Const.notNullString(arrayhmCity.get(arrayCity.indexOf(etCity.getText().toString())).get("sisdcode"),""));
                    etCode.setText("+" + Const.notNullString(arrayhmCity.get(arrayCity.indexOf(etCity.getText().toString())).get("sisdcode"),"") + "  " + Const.notNullString(arrayhmCity.get(arrayCity.indexOf(etCity.getText().toString())).get("istdcode"),""));
                }else{
                    etISD1.setText("");
                    etISD2.setText("");
                    etCode.setText("+" + "" + "  " + Const.notNullString(arrayhmCity.get(arrayCity.indexOf(etCity.getText().toString())).get("istdcode"),""));
                }

                if(!Const.notNullString(arrayhmCity.get(arrayCity.indexOf(etCity.getText().toString())).get("istdcode"),"").equalsIgnoreCase("0")){
                    etAreaCode1.setText("+" + Const.notNullString(arrayhmCity.get(arrayCity.indexOf(etCity.getText().toString())).get("istdcode"),""));
                    etAreaCode2.setText("+" + Const.notNullString(arrayhmCity.get(arrayCity.indexOf(etCity.getText().toString())).get("istdcode"),""));
                    etCode.setText("+" + Const.notNullString(arrayhmCity.get(arrayCity.indexOf(etCity.getText().toString())).get("sisdcode"),"") + "  " + Const.notNullString(arrayhmCity.get(arrayCity.indexOf(etCity.getText().toString())).get("istdcode"),""));
                }else{
                    etAreaCode1.setText("");
                    etAreaCode2.setText("");
                    etCode.setText("+" + Const.notNullString(arrayhmCity.get(arrayCity.indexOf(etCity.getText().toString())).get("sisdcode"),"") + "  " + "");
                }
                // etCode.setText("+" + Const.notNullString(arrayhmCity.get(arrayCity.indexOf(etCity.getText().toString())).get("sisdcode"),"") + "  " + Const.notNullString(arrayhmCity.get(arrayCity.indexOf(etCity.getText().toString())).get("istdcode"),""));
            }

            @Override
            public void onFailerResponse(String error) {
                Const.dismissProgress();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        CityListCode();
                    }
                };
                Const.showErrorApiDialog(context, runnable);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            if (selectedImage != null) {

                Bitmap bm = null;
                try {
                    bm = getBitmapFromUri(data.getData());

                    ByteArrayOutputStream bao = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 50, bao);

                    byte[] ba = bao.toByteArray();
                    String ba1 = Base64.encodeToString(ba, Base64.DEFAULT);

                    ProfileBase64Img = ba1;

                } catch (IOException e) {
                    e.printStackTrace();
                }
                imgProfile.setBackgroundResource(0);
                imgProfile.setImageURI(selectedImage);
            }
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    private void clearForm() {
        etFirstName.setText("");
        etLastName.setText("");
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
                        if (Const.isEmpty(etFirstName)) {
                            Const.showErrorDialog(context, "Please Enter First Name");
                        } else if (Const.isEmpty(etLastName)) {
                            Const.showErrorDialog(context, "Please Enter Last Name");
                        } else if (Const.isEmpty(etCompName)) {
                            Const.showErrorDialog(context, "Please Enter Company Name");
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
                    } else{
                            if(!ProfileBase64Img.equalsIgnoreCase("")){
                                UpdateProfile();
                            }
                        Update();
                    }
                    break;
                case R.id.imgProfile:
                    Intent i = new Intent(
                            Intent.ACTION_GET_CONTENT,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);
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

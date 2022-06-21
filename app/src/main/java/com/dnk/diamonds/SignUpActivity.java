package com.dnk.shairugems;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import java.util.Map;

import static com.dnk.shairugems.Utils.Const.setBackgoundBorder;

public class SignUpActivity extends AppCompatActivity {

    Toolbar toolbar;
    LinearLayout llSignUp, llReset;
    EditText etCountry,etCity;
    ScrollView svScroll;
    EditText etUserName,etPassword,etConfirmPassword,etFirstName,etLastName,etCompName,etAddress1,etAddress2,etZipCode,
            etISD1,etMobile1,etISD2,etMobile2,etAreaCode1,etOfficePhone1,etAreaCode2,etOfficePhone2,etCode,etFaxNo,etEmailAddress1,
            etEmailAddress2,etWebsite,etSkypeID,etWeChatID,etBusinessRegNo,etRapID;
    ArrayList<HashMap<String, String>> arrayhmCountry, arrayhmCity;
    ArrayList<String> arrayCountry, arrayCity;
    ArrayAdapter adapter;
    Context context = SignUpActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initView();

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initView() {
        toolbar = findViewById(R.id.toolBar);
        svScroll = findViewById(R.id.svScroll);
        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
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

        llSignUp = findViewById(R.id.llSignUp);
        llReset = findViewById(R.id.llReset);

        llSignUp.setBackgroundDrawable(setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));
        llReset.setBackgroundDrawable(setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));

        etCountry.setOnClickListener(clickListener);
        etCity.setOnClickListener(clickListener);
        llSignUp.setOnClickListener(clickListener);
        llReset.setOnClickListener(clickListener);

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
    }

    private void SignUp() {
        Const.showProgress(context);
        final Map<String, String> map = new HashMap<>();
        map.put("UserName", Const.notNullString(etUserName.getText().toString().trim(),""));
        map.put("Password", Const.notNullString(etPassword.getText().toString().trim(),""));
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
        map.put("CompEmail2", Const.notNullString(etEmailAddress2.getText().toString().trim(),""));
        map.put("WeChatId", Const.notNullString(etWeChatID.getText().toString().trim(),""));
        map.put("SkypeId", Const.notNullString(etSkypeID.getText().toString().trim(),""));
        map.put("Website", Const.notNullString(etWebsite.getText().toString().trim(),""));
        map.put("DeviceType", "ANDROID");
        map.put("CompCityId", Const.isEmpty(etCity) ? "" : Const.notNullString(arrayhmCity.get(arrayCity.indexOf(etCity.getText().toString())).get("icityid"),""));
        map.put("CompCountryId", Const.isEmpty(etCountry) ? "" : Const.notNullString(arrayhmCountry.get(arrayCountry.indexOf(etCountry.getText().toString())).get("icountryid"),""));
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
        Const.callPostApi(context, "User/RegisterUser", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                Const.dismissProgress();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.optString("Status").equalsIgnoreCase("1")) {
                        String ob = new JSONObject(result).optString("Message");
                        clearForm();
                        Pref.setStringValue(context, Const.isSignUp, "1");
                        Pref.setStringValue(context, Const.SignUpMsg, ob);
                        startActivity(new Intent(context, NewLoginActivity.class));
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
                        SignUp();
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
                      //  Const.showErrorDialog(context, Const.notNullString(object.optString("Message"), "Something went wrong."));
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

    private void clearForm() {
        etUserName.setText("");
        etPassword.setText("");
        etConfirmPassword.setText("");
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
                case R.id.llSignUp:
                    if (Const.isEmpty(etUserName)) {
                        Const.showErrorDialog(context, "Please Enter UserName");
                    }  else if (etUserName.getText().toString().contains(" ")) {
                        Const.showErrorDialog(context, "Space is not allowed In UserName");
                    }  else if (etUserName.getText().toString().length() < 5) {
                        Const.showErrorDialog(context, "Please Enter Minimum 5 Characters UserName");
                    } else if (Const.isEmpty(etPassword)) {
                        Const.showErrorDialog(context, "Please Enter Password");
                    } else if (etPassword.getText().toString().length() < 6) {
                        Const.showErrorDialog(context, "Please Enter Minimum 6 Characters Password");
                    } else if (Const.isEmpty(etConfirmPassword)) {
                        Const.showErrorDialog(context, "Please Enter Confirm Password");
                    } else if (!etConfirmPassword.getText().toString().trim().equalsIgnoreCase(etPassword.getText().toString().trim())) {
                        Const.showErrorDialog(context, "Please Enter Confirm Password Same as Password");
                    } else if (Const.isEmpty(etCompName)) {
                        Const.showErrorDialog(context, "Please Enter CompanyName");
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
                        SignUp();
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

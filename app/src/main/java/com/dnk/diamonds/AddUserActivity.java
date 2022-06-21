package com.dnk.shairugems;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dnk.shairugems.Adapter.AdapterDashboard;
import com.dnk.shairugems.Class.DashboardClass;
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

public class AddUserActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvTitle;
    TextView tvCompName;
    EditText etUserName,etPassword,etcnfPassword,etFirstName,etLastName,etMobile,etEmailId;
    CheckBox chAll,chSearchStock,chPlaceOrder,chOrderHistory,chMyCart,chMyWishlist,chQuickSearch;
    CheckBox chAcive;
    Boolean flag_chAll=false,
         flag_chSearchStock=false,flag_chPlaceOrder=false,flag_chOrderHistory=false,
         flag_chMyCart=false,flag_chMyWishlist=false,flag_chQuickSearch=false;
    Boolean flag_chAcive=false;
    int cnt=0;

    Toolbar toolbar;
    ImageView imgpass,imgcnfpass;
    boolean isshow_pass = false,isshow_cnfpass = false;
    private RecyclerView rv;
    private LinearLayout llHorizontal;
    private Dialog dialog;
    TextView tvSaveorUpdate;
    LinearLayout llReset,llSave;
    String iuserid="";

    HorizontalScrollView hview_rv;
    LinearLayout noresult_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        initview();
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    private void initview() {
        toolbar = findViewById(R.id.toolBar);

        tvTitle=findViewById(R.id.tvTitle);
        tvCompName=findViewById(R.id.tvCompName);

        etUserName=findViewById(R.id.etUserName);
        etPassword=findViewById(R.id.etPassword);
        etcnfPassword=findViewById(R.id.etcnfPassword);
        etFirstName=findViewById(R.id.etFirstName);
        etLastName=findViewById(R.id.etLastName);
        etMobile=findViewById(R.id.etMobile);
        etEmailId=findViewById(R.id.etEmailId);

        chAll=findViewById(R.id.chAll);
        chSearchStock=findViewById(R.id.chSearchStock);
        chPlaceOrder=findViewById(R.id.chPlaceOrder);
        chOrderHistory=findViewById(R.id.chOrderHistory);
        chMyCart=findViewById(R.id.chMyCart);
        chMyWishlist=findViewById(R.id.chMyWishlist);
        chQuickSearch=findViewById(R.id.chQuickSearch);

        chAcive=findViewById(R.id.chAcive);

        manage_chbox();

        tvSaveorUpdate=findViewById(R.id.tvSaveorUpdate);
        llReset=findViewById(R.id.llReset);
        llSave=findViewById(R.id.llSave);

        imgpass=findViewById(R.id.imgpass);
        imgcnfpass=findViewById(R.id.imgcnfpass);

        if (Const.isAdd){
            tvTitle.setText("Add User");
            tvSaveorUpdate.setText("SAVE");
            if (!Pref.getStringValue(AddUserActivity.this, Const.IsPrimary, "").equalsIgnoreCase("true")) {
                tvCompName.setOnClickListener(this);
            }
            else{
                iuserid=Pref.getStringValue(AddUserActivity.this, Const.loginId, "");
                tvCompName.setText(Pref.getStringValue(AddUserActivity.this, Const.CompanyName, ""));
            }
        }
        else
        {
            tvTitle.setText("Update User");
            tvSaveorUpdate.setText("UPDATE");
            iuserid=Const.iUserId;
            tvCompName.setText(Const.cmpName);
            get_user_detail();
        }
        imgpass.setOnClickListener(this);
        imgcnfpass.setOnClickListener(this);

        llSave.setBackgroundDrawable(Const.setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));
        llReset.setBackgroundDrawable(Const.setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));

        llReset.setOnClickListener(this);
        llSave.setOnClickListener(this);

    }

    private void get_user_detail() {
        final ArrayList<HashMap<String, String>> arrayhmAssist=new ArrayList<>();
        arrayhmAssist.clear();
        final Map<String, String> map = new HashMap<>();
        map.put("UserID",iuserid);
        map.put("UserName",Const.uname);
        map.put("UserType", "3");

        Const.callPostApi(this, "User/GetFullUserList", map, new VolleyCallback() {
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

                            if (arrayhmAssist.size()>0) {
                                for (int i = 0; i < arrayhmAssist.size(); i++) {
                                    HashMap<String,String> mp=arrayhmAssist.get(i);
                                    if (i==0)
                                    {
                                        etUserName.setText(Const.notNullString(mp.get("susername"),""));
                                        etPassword.setText(Const.notNullString(mp.get("spassword"),""));
                                        etcnfPassword.setText(Const.notNullString(mp.get("spassword"),""));
                                        etFirstName.setText(Const.notNullString(mp.get("sfirstname"),""));
                                        etLastName.setText(Const.notNullString(mp.get("slastname"),""));

                                        String currentString = Const.notNullString(mp.get("scompmobile"),"");
                                        String[] separated = currentString.split("-");

                                        etMobile.setText(Const.notNullString(separated[1],""));

                                        etEmailId.setText(Const.notNullString(mp.get("semail"),""));

                                        if (Const.notNullString(mp.get("bisactive"),"").equals("true"))
                                        { chAcive.setChecked(true); } else { chAcive.setChecked(false); }

                                        if (Const.notNullString(mp.get("searchstock"),"").equals("true"))
                                        { chSearchStock.setChecked(true); } else { chSearchStock.setChecked(false); }

                                        if (Const.notNullString(mp.get("placeorder"),"").equals("true"))
                                        { chPlaceOrder.setChecked(true); } else { chPlaceOrder.setChecked(false); }


                                        if (Const.notNullString(mp.get("orderhisrory"),"").equals("true"))
                                        { chOrderHistory.setChecked(true); } else { chOrderHistory.setChecked(false); }

                                        if (Const.notNullString(mp.get("mycart"),"").equals("true"))
                                        { chMyCart.setChecked(true); } else { chMyCart.setChecked(false); }


                                        if (Const.notNullString(mp.get("mywishlist"),"").equals("true"))
                                        { chMyWishlist.setChecked(true); } else { chMyWishlist.setChecked(false); }

                                        if (Const.notNullString(mp.get("quicksearch"),"").equals("true"))
                                        { chQuickSearch.setChecked(true); } else { chQuickSearch.setChecked(false); }

                                    }
                                }
                            }
                        } else {
                            Const.showErrorDialog(AddUserActivity.this, object.optString("Message"));
                        }
                    } else {
                        if (object.optString("Message").equalsIgnoreCase("Unauthorised")) {
                            Pref.removeValue(AddUserActivity.this, Const.loginId);
                            startActivity(new Intent(AddUserActivity.this, LoginActivity.class));
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        } else {
                            Const.showErrorDialog(AddUserActivity.this, Const.notNullString(object.optString("Message"), "Something want wrong !"));
                        }
                    }
                } catch (JSONException e) {
                    Const.showErrorDialog(AddUserActivity.this, "Something want wrong");
                }
            }

            @Override
            public void onFailerResponse(String error) {
                Const.dismissProgress();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        get_user_detail();
                    }
                };
                Const.showErrorApiDialog(AddUserActivity.this, runnable);
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tvCompName:
                openSelection();
                break;
            case R.id.imgpass:
                if (!etPassword.getText().toString().equalsIgnoreCase("")) {
                    if (!isshow_pass) {
                        isshow_pass = true;
                        imgpass.setImageResource(R.drawable.ic_show);
                        etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        etPassword.setSelection(etPassword.getText().length());
                    } else {
                        isshow_pass = false;
                        imgpass.setImageResource(R.drawable.ic_hide);
                        etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                        etPassword.setSelection(etPassword.getText().length());
                    }
                }
                break;
            case R.id.imgcnfpass:
                if (!etcnfPassword.getText().toString().equalsIgnoreCase("")) {
                    if (!isshow_cnfpass) {
                        isshow_cnfpass = true;
                        imgcnfpass.setImageResource(R.drawable.ic_show);
                        etcnfPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        etcnfPassword.setSelection(etcnfPassword.getText().length());
                    } else {
                        isshow_cnfpass = false;
                        imgcnfpass.setImageResource(R.drawable.ic_hide);
                        etcnfPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                        etcnfPassword.setSelection(etcnfPassword.getText().length());
                    }
                }
                break;
            case R.id.llReset:
                reset_addUser();
                break;
            case R.id.llSave:
                save_user_validtion();
                break;

        }
    }

    private void save_user_validtion() {
        if(tvCompName.getText().toString().equals("")) {
            Const.showErrorDialog(this, "Please Select Company Name");
        } else if (Const.isEmpty(etUserName)) {
            Const.showErrorDialog(this, "Please Enter UserName");
        } else if (etUserName.getText().toString().contains(" ")) {
            Const.showErrorDialog(this, "Space is not allowed In UserName");
        } else if (etUserName.getText().toString().length() < 5) {
            Const.showErrorDialog(this, "Please Enter Minimum 5 Characters UserName");
        }else if (Const.isEmpty(etPassword)) {
            Const.showErrorDialog(this, "Please Enter Password");
        } else if (etPassword.getText().toString().length() < 6) {
            Const.showErrorDialog(this, "Please Enter Minimum 6 Characters Password");
        } else if (Const.isEmpty(etcnfPassword)) {
            Const.showErrorDialog(this, "Please Enter Confirm Password");
        } else if (!etcnfPassword.getText().toString().trim().equalsIgnoreCase(etPassword.getText().toString().trim())) {
            Const.showErrorDialog(this, "Please Enter Confirm Password Same as Password");
        } else if (Const.isEmpty(etFirstName)) {
            Const.showErrorDialog(this, "Please Enter First Name");
        } else if (Const.isEmpty(etLastName)) {
            Const.showErrorDialog(this, "Please Enter Last Name");
        } else if (Const.isEmpty(etMobile)) {
            Const.showErrorDialog(this, "Please Enter Mobile Number");
        }else if (Const.isEmpty(etEmailId)) {
            Const.showErrorDialog(this, "Please Enter Email Address");
        } else if (Const.isValidEmail(etEmailId)) {
            Const.showErrorDialog(this, "Please Enter Valid Email Address");
        } else {
            save_user();
        }
    }

    private void save_user() {
        Const.showProgress(AddUserActivity.this);

        final Map<String, String> map = new HashMap<>();
        if (Const.isAdd)
        { map.put("Type","Add"); }
        else
        { map.put("Type","Edit"); }

        map.put("iUserId",iuserid);
        map.put("UserName",Const.notNullString(etUserName.getText().toString(),""));
        map.put("Password",Const.notNullString(etPassword.getText().toString(),""));
        map.put("FirstName",Const.notNullString(etFirstName.getText().toString(),""));
        map.put("LastName",Const.notNullString(etLastName.getText().toString(),""));
        map.put("MobileNo",Const.notNullString(etMobile.getText().toString(),""));
        map.put("EmailId",Const.notNullString(etEmailId.getText().toString(),""));

        map.put("IsActive",flag_chAcive+"");

        map.put("SearchStock",flag_chSearchStock+"");
        map.put("PlaceOrder",flag_chPlaceOrder+"");
        map.put("OrderHisrory",flag_chOrderHistory+"");
        map.put("MyCart",flag_chMyCart+"");
        map.put("MyWishlist",flag_chMyWishlist+"");
        map.put("QuickSearch",flag_chQuickSearch+"");

        Const.callPostApi(AddUserActivity.this, "User/Save_UserMgt", map, new VolleyCallback() {
                    @Override
                    public void onSuccessResponse(String result) {
                        Const.dismissProgress();
                        try {
                            JSONObject object = new JSONObject(result);
                            if (object.optString("Status").equalsIgnoreCase("1")) {
                                String ob = new JSONObject(result).optString("Message");
                                if (Const.isAdd) {
                                    showDialog(AddUserActivity.this, Const.notNullString(ob, "User Save Sucessfully!"));
                                }
                                else
                                {
                                    showDialog(AddUserActivity.this, Const.notNullString(ob, "User Update Sucessfully!"));
                                }
                            } else {
                                Const.showErrorDialog(AddUserActivity.this,object.optString("Message").toString());
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
                                save_user();
                            }
                        };
                        Const.showErrorApiDialog(AddUserActivity.this, runnable);
                    }
                });

    }

    public void showDialog(final Context context, String message) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.layout_error_dialog);

        TextView tvMessage = (TextView) dialog.findViewById(R.id.tvMessage);
        tvMessage.setText(message);

        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(context, UserMgtActivity.class));
                finish();
            }
        });

        dialog.show();
    }

    private void reset_addUser() {
        if (Const.isAdd) {
            tvCompName.setText("");
        }
        etUserName.setText("");
        etPassword.setText("");
        etcnfPassword.setText("");
        etFirstName.setText("");
        etLastName.setText("");
        etMobile.setText("");
        etEmailId.setText("");

        chSearchStock.setChecked(false);
        chPlaceOrder.setChecked(false);
        chOrderHistory.setChecked(false);
        chMyCart.setChecked(false);
        chMyWishlist.setChecked(false);
        chQuickSearch.setChecked(false);
        chAcive.setChecked(false);
        chAll.setChecked(false);


    }

    private void openSelection() {
        dialog = new Dialog(AddUserActivity.this, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dialog.getWindow().setBackgroundDrawable(Const.setBackgoundBorder(0, 20, Color.WHITE, Color.WHITE));
        dialog.setContentView(R.layout.layout_selection_company);

        llHorizontal=dialog.findViewById(R.id.llHorizontal);
        llHorizontal.setVisibility(View.GONE);
        LinearLayout llNoData = dialog.findViewById(R.id.llNoData);
        TextView tvNoData = dialog.findViewById(R.id.tvNoData);
        Button btnClear = dialog.findViewById(R.id.btnClear);
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        hview_rv=dialog.findViewById(R.id.hview_rv);
        noresult_layout=dialog.findViewById(R.id.noresult_layout);

        final EditText etSr = dialog.findViewById(R.id.etSearch);
        rv = dialog.findViewById(R.id.rvComp);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        dialog.setCancelable(false);
        dialog.show();

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvCompName.setText("");
                dialog.dismiss();
            }
        });

        etSr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (String.valueOf(charSequence).isEmpty())
                {
                    ResultAdapter adapter = null;
                    rv.setAdapter(null);
                    llHorizontal.setVisibility(View.GONE);
                }
                else
                {
                    get_companyName(charSequence+"");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

       /* tvTitle.setText(title);
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

        dialog.show();*/
    }

    private void get_companyName(final String s) {
//        Const.showProgress(AddUserActivity.this);
        final ArrayList<HashMap<String, String>> maps = new ArrayList<>();
        maps.clear();
        final Map<String, String> map = new HashMap<>();
        map.put("Search", s);
        Const.callPostApi(AddUserActivity.this, "User/GetCompanyForUserMgt", map, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
//                Const.dismissProgress();
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
                                maps.add(hm);
                            }
                            for (Map<String, String> a:maps) {
                                Log.e("print ",a.toString());
                            }
                            if (maps.size()>0)
                            {
                                hview_rv.setVisibility(View.VISIBLE);
                                noresult_layout.setVisibility(View.GONE);

                                llHorizontal.setVisibility(View.VISIBLE);
                                ResultAdapter adapter = new ResultAdapter(AddUserActivity.this, maps);
                                rv.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    } else {
                        ResultAdapter adapter = null;
                        rv.setAdapter(null);
                        llHorizontal.setVisibility(View.GONE);
                        hview_rv.setVisibility(View.GONE);
                        noresult_layout.setVisibility(View.VISIBLE);

//                        Const.showErrorDialog(AddUserActivity.this, "No Result Available");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailerResponse(String error) {
//                Const.dismissProgress();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        get_companyName(s);
                    }
                };
                Const.showErrorApiDialog(AddUserActivity.this, runnable);
            }
        });
    }

    public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder>
    {
        Context context;
        List<HashMap<String,String>> mainlist;

        public ResultAdapter(Context context, List<HashMap<String, String>> mainlist) {
            this.context = context;
            this.mainlist = mainlist;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.layout_company, parent, false);
            ResultAdapter.ViewHolder viewHolder = new ResultAdapter.ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final HashMap<String, String> map = mainlist.get(position);
            holder.tvCompName.setText(Const.notNullString(map.get("compname"), ""));
            holder.tvFpc.setText(Const.notNullString(map.get("fortunepartycode"), ""));
            holder.tvUserName.setText(Const.notNullString(map.get("username"), ""));
            holder.tvCustName.setText(Const.notNullString(map.get("custname"), ""));
            holder.llMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iuserid=Const.notNullString(map.get("iuserid"), "");
                    tvCompName.setText(Const.notNullString(map.get("compname"), ""));
                    dialog.dismiss();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mainlist.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            LinearLayout llMain;
            TextView tvCompName,tvFpc,tvUserName,tvCustName;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                llMain=itemView.findViewById(R.id.llMain);
                tvCompName=itemView.findViewById(R.id.tvCompName);
                tvFpc=itemView.findViewById(R.id.tvFpc);
                tvUserName=itemView.findViewById(R.id.tvUserName);
                tvCustName=itemView.findViewById(R.id.tvCustName);

            }
        }
    }

    private void manage_chbox() {

        chAcive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                flag_chAcive = b;
            }
        });

        chAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                flag_chAll=b;
                if (flag_chAll)
                {
                    chSearchStock.setChecked(true);
                    chPlaceOrder.setChecked(true);
                    chOrderHistory.setChecked(true);
                    chMyCart.setChecked(true);
                    chMyWishlist.setChecked(true);
                    chQuickSearch.setChecked(true);
                }
                else
                {
                    if (cnt==6)
                    {
                        chSearchStock.setChecked(false);
                        chPlaceOrder.setChecked(false);
                        chOrderHistory.setChecked(false);
                        chMyCart.setChecked(false);
                        chMyWishlist.setChecked(false);
                        chQuickSearch.setChecked(false);
                    }
                }
            }
        });

        chSearchStock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                flag_chSearchStock=b;
                if (flag_chSearchStock) {
                    cnt++;
                    if (cnt==6) { chAll.setChecked(true); }
                } else {
                    cnt--;
                    if (cnt<6) { chAll.setChecked(false); }
                }
            }
        });

        chPlaceOrder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                flag_chPlaceOrder=b;
                if (flag_chPlaceOrder) {
                    cnt++;
                    if (cnt==6) { chAll.setChecked(true); }
                } else {
                    cnt--;
                    if (cnt<6) { chAll.setChecked(false); }
                }
            }
        });

        chOrderHistory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                flag_chOrderHistory=b;
                if (flag_chOrderHistory) {
                    cnt++;
                    if (cnt==6) { chAll.setChecked(true); }
                } else {
                    cnt--;
                    if (cnt<6) { chAll.setChecked(false); }
                }
            }
        });

        chMyCart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                flag_chMyCart=b;
                if (flag_chMyCart) {
                    cnt++;
                    if (cnt==6) { chAll.setChecked(true); }
                } else {
                    cnt--;
                    if (cnt<6) { chAll.setChecked(false); }
                }
            }
        });

        chMyWishlist.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                flag_chMyWishlist=b;
                if (flag_chMyWishlist) {
                    cnt++;
                    if (cnt==6) { chAll.setChecked(true); }
                } else {
                    cnt--;
                    if (cnt<6) { chAll.setChecked(false); }
                }
            }
        });

        chQuickSearch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                flag_chQuickSearch=b;
                if (flag_chQuickSearch) {
                    cnt++;
                    if (cnt==6) { chAll.setChecked(true); }
                } else {
                    cnt--;
                    if (cnt<6) { chAll.setChecked(false); }
                }
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

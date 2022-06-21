package com.dnk.shairugems;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.dnk.shairugems.Fragment.SignInFragment;
import com.dnk.shairugems.Fragment.SignUpFragment;
import com.dnk.shairugems.Utils.Const;
import com.dnk.shairugems.Volly.VolleyCallback;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class LoginActivity extends AppCompatActivity {

    ViewPager viewPager;
    ViewPagerAdapter adp;
    LinearLayout llSignIn, llSignUp;
    TextView tvSignIn, tvSignUp;
    ArrayList<HashMap<String, String>> loginArray;
    Context context = LoginActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        initView();

        Login();

        llSignIn.setOnClickListener(clickListener);
        llSignUp.setOnClickListener(clickListener);

        adp = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adp);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    llSignIn.performClick();
                } else {
                    llSignUp.performClick();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        llSignIn.performClick();
    }

    private void initView() {
        viewPager = findViewById(R.id.viewPager);
        llSignUp = findViewById(R.id.llSignUp);
        llSignIn = findViewById(R.id.llSignIn);
        tvSignUp = findViewById(R.id.tvSignUp);
        tvSignIn = findViewById(R.id.tvSignIn);
    }

    private void Login() {
        loginArray = new ArrayList<>();
        Map<String, String> hm = new HashMap<>();
        hm.put("UserName", "sgadmin");
        hm.put("Password", "Sh@!ru123");
        hm.put("Source", "");
        hm.put("IpAddress", "");
        hm.put("UDID", "");
        hm.put("LoginMode", "");
        hm.put("DeviseType", "ANDROID");
        hm.put("DeviceName", "");
        hm.put("AppVersion", "");
        hm.put("Location", "");
        Const.callPostApi(context, "CheckLoginNew", hm, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                try {
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    factory.setIgnoringElementContentWhitespace(true);
                    DocumentBuilder build = null;
                    build = factory.newDocumentBuilder();
                    Document doc = build.parse(result);
                    doc.getDocumentElement().normalize();
//                    NodeList er = doc.getElementsByTagName("Error");
                    NodeList e = doc.getElementsByTagName("Table");
                    for (int i = 0; i < e.getLength(); i++) {
                        HashMap<String, String> child = new HashMap<String, String>();
                        NodeList list = e.item(i).getChildNodes();
                        for (int j = 0; j < list.getLength(); j++) {
                            if (!"#text".equals(list.item(j).getNodeName())) {
                                child.put(list.item(j).getNodeName().toLowerCase().trim(), list.item(j).getTextContent().trim());
                            }
                        }
                        loginArray.add(child);
                    }
                    Log.v("ser", loginArray.size() + "");
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailerResponse(String error) {
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String convertStreamToString(InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.llSignIn:
                    viewPager.setCurrentItem(0);
                    tvSignIn.setTypeface(null, Typeface.BOLD);
                    tvSignUp.setTypeface(null, Typeface.NORMAL);
                    tvSignIn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    tvSignUp.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                    break;
                case R.id.llSignUp:
                    viewPager.setCurrentItem(1);
                    tvSignIn.setTypeface(null, Typeface.NORMAL);
                    tvSignUp.setTypeface(null, Typeface.BOLD);
                    tvSignIn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                    tvSignUp.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    break;
                default:
                    break;
            }
        }
    };

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0) {
                fragment = new SignInFragment();
            } else if (position == 1) {
                fragment = new SignUpFragment();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
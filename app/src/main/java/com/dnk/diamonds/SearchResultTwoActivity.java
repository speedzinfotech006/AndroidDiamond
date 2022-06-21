package com.dnk.shairugems;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dnk.shairugems.Utils.Const;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchResultTwoActivity extends BaseDrawerActivity {

    ImageView imgMenu, imgOMenu, imgViewCount, imgLayout, imgSearch;
    LinearLayout llViewCount, llModify, llCart, llHorizontal;
    TextView tvTitle;
    EditText etSearch;
    RecyclerView rvList;
    ResultAdp adp;
    LinearLayout.LayoutParams parms, parms2;
    ArrayList<HashMap<String, String>> maps = new ArrayList<>();
    int isChange = 1;
    Context context = SearchResultTwoActivity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.search_result_two_activity, frameLayout);

        Log.e("Activity Open ","Search Result two Activity");
        initView();

        etSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (etSearch.getCompoundDrawables()[2] != null) {
                        if (event.getRawX() >= (etSearch.getRight() - etSearch.getCompoundDrawables()[2].getBounds().width())) {
                            if (Const.isEmpty(etSearch)) {
                                etSearch.setVisibility(View.GONE);
                                imgSearch.setVisibility(View.VISIBLE);
                                tvTitle.setVisibility(View.VISIBLE);
                                Const.hideSoftKeyboard(SearchResultTwoActivity.this);
                                etSearch.clearFocus();
                            } else {
                                etSearch.setText("");
                            }
                            return true;
                        }
                    }
                }
                return false;
            }
        });


    }

    private void initView() {
        imgMenu = findViewById(R.id.imgMenu);
        imgOMenu = findViewById(R.id.imgOMenu);
        etSearch = findViewById(R.id.etSearch);
        imgSearch = findViewById(R.id.imgSearch);
        imgViewCount = findViewById(R.id.imgViewCount);
        tvTitle = findViewById(R.id.tvTitle);
        imgLayout = findViewById(R.id.imgLayout);
        llViewCount = findViewById(R.id.llViewCount);
        llHorizontal = findViewById(R.id.llHorizontal);
        llModify = findViewById(R.id.llModify);
        llCart = findViewById(R.id.llCart);
        rvList = findViewById(R.id.rvList);
        rvList.setLayoutManager(new LinearLayoutManager(context));
        imgMenu.setOnClickListener(clickListener);
        imgViewCount.setOnClickListener(clickListener);
        imgOMenu.setOnClickListener(clickListener);
        llCart.setOnClickListener(clickListener);
        imgSearch.setOnClickListener(clickListener);
        llModify.setOnClickListener(clickListener);
        imgLayout.setOnClickListener(clickListener);

        llCart.setBackgroundDrawable(Const.setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));
        llModify.setBackgroundDrawable(Const.setBackgoundBorder(2, 15, getResources().getColor(R.color.colorPrimary), Color.TRANSPARENT));

        maps.add(new HashMap<String, String>());
        maps.add(new HashMap<String, String>());
        maps.add(new HashMap<String, String>());
        maps.add(new HashMap<String, String>());
        maps.add(new HashMap<String, String>());

        parms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        parms2 = new LinearLayout.LayoutParams(displayMetrics.widthPixels, ViewGroup.LayoutParams.MATCH_PARENT);

        Drawable wrappedDrawable = DrawableCompat.wrap(AppCompatResources.getDrawable(context, R.drawable.ic_close));
        etSearch.setCompoundDrawablesWithIntrinsicBounds(null, null, wrappedDrawable, null);

        imgLayout.performClick();
    }

    private void openPopupMenu() {
        Context wrapper = new ContextThemeWrapper(context, R.style.PopupMenu);
        PopupMenu popup = new PopupMenu(wrapper, imgOMenu);
        popup.getMenuInflater().inflate(R.menu.result_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                return true;
            }
        });
        popup.show();
    }

    private class ResultAdp extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        ArrayList<HashMap<String, String>> arraylist;
        Context context;
        int isLayout;

        private ResultAdp(Context context, ArrayList<HashMap<String, String>> arraylist, int isLayout) {
            this.arraylist = arraylist;
            this.isLayout = isLayout;
            this.context = context;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (isLayout == 1) {
                View view = LayoutInflater.from(context).inflate(R.layout.layout_result_view_three, parent, false);
                return new ResultAdp.GridViewHolder(view);
            } else if (isLayout == 0) {
                View view = LayoutInflater.from(context).inflate(R.layout.layout_hori_view, parent, false);
                return new ResultAdp.ListViewHolder(view);
            } else {
                View view = LayoutInflater.from(context).inflate(R.layout.layout_result_view_grid, parent, false);
                return new ResultAdp.ListGridHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            if (isLayout == 1) {
                if (holder instanceof ResultAdp.GridViewHolder) {
                    final ResultAdp.GridViewHolder h = (ResultAdp.GridViewHolder) holder;
                    h.tvStatus.setBackgroundDrawable(Const.setBackgoundBorder(0, 10, getResources().getColor(R.color.green), Color.TRANSPARENT));

//                    if (position == 1) {
//                        viewHolder.imgShape.setImageDrawable(getResources().getDrawable(R.drawable.ic_emerald));
//                    } else if (position == 2) {
//                        viewHolder.imgShape.setImageDrawable(getResources().getDrawable(R.drawable.ic_asscher));
//                    } else if (position == 3) {
//                        viewHolder.imgShape.setImageDrawable(getResources().getDrawable(R.drawable.ic_marquise));
//                    } else if (position == 4) {
//                        viewHolder.imgShape.setImageDrawable(getResources().getDrawable(R.drawable.ic_princess));
//                    }

                    if (position == 1) {
                        h.imgLocation.setImageDrawable(getResources().getDrawable(R.drawable.ic_hk));
                        h.imgVideo.setImageDrawable(getResources().getDrawable(R.drawable.ic_no_video));
                    } else {
                        h.imgLocation.setImageDrawable(getResources().getDrawable(R.drawable.ic_india));
                        h.imgVideo.setImageDrawable(getResources().getDrawable(R.drawable.ic_video));
                    }

                    h.imgView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (h.rlAdvView.getVisibility() == View.GONE) {
                                h.imgView.setImageDrawable(getResources().getDrawable(R.drawable.ic_minus));
                                h.rlAdvView.setVisibility(View.VISIBLE);
                            } else {
                                h.imgView.setImageDrawable(getResources().getDrawable(R.drawable.ic_plus));
                                h.rlAdvView.setVisibility(View.GONE);
                            }
                        }
                    });

                    if (Const.isOdd(position)) {
                        h.llMain.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_background_box));
                    } else {
                        h.llMain.setBackgroundColor(getResources().getColor(R.color.white));
                    }
                }
            } else if (isLayout == 0) {
                if (holder instanceof ResultAdp.ListViewHolder) {
                    ResultAdp.ListViewHolder lvHolder = (ResultAdp.ListViewHolder) holder;

                    if (Const.isOdd(position)) {
                        lvHolder.llMain.setBackgroundColor(getResources().getColor(R.color.layout));
                    } else {
                        lvHolder.llMain.setBackgroundColor(getResources().getColor(R.color.white));
                    }
                }
            } else {
                if (holder instanceof ResultAdp.ListGridHolder) {
                    ResultAdp.ListGridHolder lvHolder = (ResultAdp.ListGridHolder) holder;
                    lvHolder.rlMain.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(context, StoneDetailActivity.class));
                            overridePendingTransition(0, 0);
                        }
                    });
                }
            }

        }

        @Override
        public int getItemCount() {
            return arraylist.size();
        }

        private class GridViewHolder extends RecyclerView.ViewHolder {

            ImageView imgShape, imgLocation, imgView, imgVideo;
            RelativeLayout rlAdvView;
            LinearLayout llMain;
            TextView tvStatus;

            private GridViewHolder(@NonNull View v) {
                super(v);
//                imgShape = v.findViewById(R.id.imgShape);
                tvStatus = v.findViewById(R.id.tvStatus);
                rlAdvView = v.findViewById(R.id.rlAdvView);
                imgView = v.findViewById(R.id.imgView);
                imgVideo = v.findViewById(R.id.imgVideo);
                imgLocation = v.findViewById(R.id.imgLocation);
                llMain = v.findViewById(R.id.llMain);
            }
        }

        private class ListViewHolder extends RecyclerView.ViewHolder {

            LinearLayout llMain;

            private ListViewHolder(@NonNull View v) {
                super(v);
                llMain = v.findViewById(R.id.llMain);
            }
        }

        private class ListGridHolder extends RecyclerView.ViewHolder {

            RelativeLayout rlMain;

            private ListGridHolder(@NonNull View v) {
                super(v);
                rlMain = v.findViewById(R.id.rlMain);
            }
        }

    }

    private void expand(final View v) {
        int matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View) v.getParent()).getWidth(), View.MeasureSpec.EXACTLY);
        int wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(matchParentMeasureSpec, wrapContentMeasureSpec);
        final int targetHeight = v.getMeasuredHeight();
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1 ? ViewGroup.LayoutParams.WRAP_CONTENT : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density) + 200);
        v.startAnimation(a);
    }

    private void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density) + 200);
        v.startAnimation(a);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.imgMenu:
                    drawerLayout.openDrawer(Gravity.START);
                    break;
                case R.id.imgOMenu:
                    openPopupMenu();
                    break;
                case R.id.imgSearch:
                    tvTitle.setVisibility(View.GONE);
                    imgSearch.setVisibility(View.GONE);
                    etSearch.setVisibility(View.VISIBLE);
                    etSearch.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(etSearch, InputMethodManager.SHOW_IMPLICIT);
                    break;
                case R.id.llCart:
                    Toast.makeText(context, "Add to cart", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.imgLayout:

                    if (isChange == 0) {
                        isChange = 1;
                        imgLayout.setImageDrawable(getResources().getDrawable(R.drawable.ic_list));
                        llHorizontal.setVisibility(View.VISIBLE);
                        rvList.setLayoutParams(parms);
                        rvList.setLayoutManager(new LinearLayoutManager(context));
                        adp = new ResultAdp(context, maps, 0);
                        rvList.setAdapter(adp);
                        adp.notifyDataSetChanged();
                    } else if (isChange == 1) {
                        isChange = 2;
                        imgLayout.setImageDrawable(getResources().getDrawable(R.drawable.ic_grid));
                        llHorizontal.setVisibility(View.GONE);
                        rvList.setLayoutParams(parms2);
                        rvList.setLayoutManager(new LinearLayoutManager(context));
                        adp = new ResultAdp(context, maps, 1);
                        rvList.setAdapter(adp);
                        adp.notifyDataSetChanged();
                    } else {
                        isChange = 0;
                        imgLayout.setImageDrawable(getResources().getDrawable(R.drawable.ic_grid));
                        llHorizontal.setVisibility(View.GONE);
                        rvList.setLayoutParams(parms2);
                        rvList.setLayoutManager(new GridLayoutManager(context, 2));
                        adp = new ResultAdp(context, maps, 2);
                        rvList.setAdapter(adp);
                        adp.notifyDataSetChanged();
                    }
                    break;
                case R.id.llModify:
                    onBackPressed();
                    break;
                case R.id.imgViewCount:
                    if (llViewCount.getVisibility() == View.VISIBLE) {
                        imgViewCount.setImageDrawable(getResources().getDrawable(R.drawable.ic_down));
                        collapse(llViewCount);
                    } else {
                        imgViewCount.setImageDrawable(getResources().getDrawable(R.drawable.ic_up));
                        expand(llViewCount);
                    }
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
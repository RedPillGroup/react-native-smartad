package com.smartadserver.android.sassample;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.appbar.AppBarLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.smartadserver.android.library.model.SASAdPlacement;
import com.smartadserver.android.sassample.util.AdViewWrapper;
import com.smartadserver.android.sassample.util.BannerViewHolder;
import com.smartadserver.android.sassample.util.DividerItemDecoration;
import com.smartadserver.android.sassample.util.ListItemHolder;

public class CoordinatorLayoutActivity extends AppCompatActivity {

    /*****************************************
     * Ad Constants
     *****************************************/
    private final static int SITE_ID = 104808;
    private final static String PAGE_ID = "663531";
    private final static int FORMAT_ID = 15140;
    private final static String TARGET = "";

    // If you are an inventory reseller, you must provide your Supply Chain Object information.
    // More info here: https://help.smartadserver.com/s/article/Sellers-json-and-SupplyChain-Object
    private final static String SUPPLY_CHAIN_OBJECT_STRING = null; // "1.0,1!exchange1.com,1234,1,publisher,publisher.com";

    private static final int AD_POSITION = 10;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private AdViewWrapper bannerWrapper;

    private int appBarOffset = 0;
    private int actionBarHeight = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);

        // Get UI objects
        recyclerView = findViewById(R.id.rv_list);

        // Create layout manager
        layoutManager = new LinearLayoutManager(this);

        // Bind views
        bindViews();

        // Load ad
        createAndLoadAd();
    }

    @Override
    protected void onDestroy() {
        // Make sure to destroy banner to avoid leaks
        if (bannerWrapper != null && bannerWrapper.mBanner != null) {
            bannerWrapper.mBanner.onDestroy();
        }

        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // add a OnGlobalLayoutListener to execute adaptBannerHeight once the activity has its new size set (otherwise it
        // uses previous orientation's size which is not what we want).
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        recyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                updateBannersHeight();
                            }
                        });
                    }
                });
            }
        });
    }

    /**
     * Bind Views.
     */
    private void bindViews() {
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new CoordinatorLayoutActivity.ListLayoutAdapter());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // Uncomment for manually set the offset of the parallax.

        final TypedArray styledAttributes = getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.actionBarSize}
        );
        actionBarHeight = (int) styledAttributes.getDimension(0, 0);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                appBarOffset = verticalOffset + actionBarHeight;
                updateParallaxOffset();
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                updateParallaxOffset();
            }

        });

    }

    /**
     * Used to set the parallax offset manually.
     */
    private void updateParallaxOffset() {
        if (bannerWrapper != null && bannerWrapper.mBanner != null && bannerWrapper.mHolder != null) {
            bannerWrapper.mBanner.setParallaxOffset((int) bannerWrapper.mHolder.itemView.getY() + appBarOffset);
        }
    }

    /**
     * Create banner wrapper with param to load banner
     * See custom class "AdViewWrapper" for complete implementation
     */
    private void createAndLoadAd() {
        bannerWrapper = new AdViewWrapper(this);

        SASAdPlacement adPlacement = new SASAdPlacement(SITE_ID,
                PAGE_ID,
                FORMAT_ID,
                TARGET,
                SUPPLY_CHAIN_OBJECT_STRING);

        bannerWrapper.loadAd(adPlacement);
    }

    /**
     * Update banners height
     */
    private void updateBannersHeight() {
        if (bannerWrapper != null) {
            int defaultHeight = (int) (50 * getResources().getDisplayMetrics().density);
            bannerWrapper.updateBannerSize(defaultHeight);
        }
    }

    private class ListLayoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private static final int VIEW_TYPE_TEXT = 0;
        private static final int VIEW_TYPE_AD = 1;

        @Override
        public int getItemViewType(int position) {
            if (position == AD_POSITION) {
                return VIEW_TYPE_AD;
            }
            return VIEW_TYPE_TEXT;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == VIEW_TYPE_TEXT) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                return new ListItemHolder(v);
            } else {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_banner_holder, parent, false);
                return new BannerViewHolder(v);
            }
        }

        @Override
        public void onViewRecycled(RecyclerView.ViewHolder holder) {
            super.onViewRecycled(holder);
            if (holder instanceof BannerViewHolder) {
                if (bannerWrapper != null) {
                    bannerWrapper.setHolder(null);
                }
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder listViewHolder, int position) {
            if (getItemViewType(position) == VIEW_TYPE_TEXT) {
                ListItemHolder holder = (ListItemHolder) listViewHolder;
                String title = (position == 0) ? "Multiple banners in RecyclerView integration" : "Lorem ipsum dolor sit amet";
                String subTitle = (position == 0) ? "See implementation in RecyclerActivity. Please scroll down to see the ads." : "Phasellus in tellus eget arcu volutpat bibendum vulputate ac sapien. Vivamus enim elit, gravida vel consequat sit amet, scelerisque vitae ex.";
                holder.configureForItem(title, subTitle, position);
            } else {
                final BannerViewHolder bannerHolder = (BannerViewHolder) listViewHolder;
                if (bannerWrapper != null && bannerWrapper.isAvailable()) { //To be available a wrapper must not have a ViewHolder.
                    bannerWrapper.setHolder(bannerHolder);
                }
            }
        }

        @Override
        public int getItemCount() {
            return 30;
        }
    }

}

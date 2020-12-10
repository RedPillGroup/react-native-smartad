package com.smartadserver.android.sassample;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
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
import com.smartadserver.android.sassample.util.ListItemHolder;
import com.smartadserver.android.sassample.util.DividerItemDecoration;

import java.util.ArrayList;

public class RecyclerActivity extends AppCompatActivity {

    /*****************************************
     * Ad Constants
     *****************************************/
    // If you are an inventory reseller, you must provide your Supply Chain Object information.
    // More info here: https://help.smartadserver.com/s/article/Sellers-json-and-SupplyChain-Object
    private final static String SUPPLY_CHAIN_OBJECT_STRING = null; // "1.0,1!exchange1.com,1234,1,publisher,publisher.com";

    private final static SASAdPlacement BANNER_PLACEMENT = new SASAdPlacement(104808, "663262", 15140, "", SUPPLY_CHAIN_OBJECT_STRING);
    private final static SASAdPlacement VIDEOREAD_PLACEMENT = new SASAdPlacement(104808, "663530", 15140, "", SUPPLY_CHAIN_OBJECT_STRING);
    private final static SASAdPlacement PARALLAX_PLACEMENT = new SASAdPlacement(104808, "663531", 15140, "", SUPPLY_CHAIN_OBJECT_STRING);

    private final static int AD_SPACING = 6;

    /*****************************************
     * Members declarations
     *****************************************/
    // UI
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<AdViewWrapper> mBannerWrappers;

    /**
     * performs Activity initialization after creation
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        //Get UI Objects
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_recycler_recycler_view);

        //Create Layout Manager
        mLayoutManager = new LinearLayoutManager(this);

        //BindViews
        bindViews();

        //Load Ads
        createAndLoadAds();
    }

    /**
     * Overriden to clean up SASAdView instances. This must be done to avoid IntentReceiver leak.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Make sur to destory all banners to avoid leaks
        for (int i = 0; i < mBannerWrappers.size(); i++) {
            final AdViewWrapper wrapper = mBannerWrappers.get(i);
            if (wrapper.mBanner != null) {
                wrapper.mBanner.onDestroy();
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // add a OnGlobalLayoutListener to execute adaptBannerHeight once the activity has its new size set (otherwise it
        // uses previous orientation's size which is not what we want).
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        mRecyclerView.post(new Runnable() {
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
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(new ListLayoutAdapter());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    /**
     * Create and load Ads
     */
    private void createAndLoadAds() {
        mBannerWrappers = new ArrayList<AdViewWrapper>(3);
        mBannerWrappers.add(createBannerWrapper(BANNER_PLACEMENT));
        mBannerWrappers.add(createBannerWrapper(PARALLAX_PLACEMENT));
        mBannerWrappers.add(createBannerWrapper(VIDEOREAD_PLACEMENT));
    }


    /**
     * Create banner wrapper with param to load banner
     * See custom class "AdViewWrapper" for complete implementation
     */
    private AdViewWrapper createBannerWrapper(SASAdPlacement adPlacement) {
        AdViewWrapper bannerWrapper = new AdViewWrapper(this);

        bannerWrapper.loadAd(adPlacement);

        // To avoid having the parallax going below the actionBar, we have to set the parallax margin top, which is its top limit.
        // To do so, first we get the action bar height
        int actionBarHeight = 0;
        final TypedArray styledAttributes = getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.actionBarSize}
        );
        actionBarHeight = (int) styledAttributes.getDimension(0, 0);
        // Then we set its height as parallaxMarginTop. Note that you can do the same with the bottom margin by using the setParallaxMarginBottom() method.
        bannerWrapper.mBanner.setParallaxMarginTop(actionBarHeight);

        return bannerWrapper;
    }


    /**
     * Update banners height
     */
    private void updateBannersHeight() {
        if (mBannerWrappers != null && mBannerWrappers.size() > 0) {
            for (int i = 0; i < mBannerWrappers.size(); i++) {
                final AdViewWrapper wrapper = mBannerWrappers.get(i);
                int defaultHeight = (int) (50 * getResources().getDisplayMetrics().density);
                wrapper.updateBannerSize(defaultHeight);
            }
        }
    }

    /**
     * Find the correct AdViewWrapper for a given BannerViewHolder.
     * This will be used to unset holders in wrapper on recycle.
     */
    private AdViewWrapper wrapperForHolder(BannerViewHolder holder) {
        if (mBannerWrappers != null && mBannerWrappers.size() > 0) {
            for (int i = 0; i < mBannerWrappers.size(); i++) {
                final AdViewWrapper wrapper = mBannerWrappers.get(i);
                if (wrapper.mHolder == holder) {
                    return wrapper;
                }
            }
        }
        return null;
    }


    /**
     * The adapter class responsible for creating RecyclerView.ViewHolder instances for different cells.
     */
    private class ListLayoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private static final int VIEW_TYPE_TEXT = 0;
        private static final int VIEW_TYPE_AD = 1;

        @Override
        public int getItemViewType(int position) {
            if (position == 0 || position % AD_SPACING != 0) {
                return VIEW_TYPE_TEXT;
            } else {
                return VIEW_TYPE_AD;
            }
        }

        /**
         * Get the correct AdViewWrapper for a given position
         */
        private AdViewWrapper wrapperForPosition(int position) {
            int intermediaire = (position / AD_SPACING) - 1;
            int index = intermediaire % mBannerWrappers.size();
            if (index < mBannerWrappers.size()) {
                return mBannerWrappers.get(index);
            } else {
                return null;
            }
        }

        /**
         * On create : return the proper ViewHolder according to viewType.
         */
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (VIEW_TYPE_TEXT == viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                return new ListItemHolder(v);
            } else {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_banner_holder, parent, false);
                return new BannerViewHolder(v);
            }
        }

        /**
         * On bind :
         * Type Text : Populate list view cell as you wish.
         * Type Ad : Get the AdViewWrapper corresponding to position and set its holder.
         */
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder listViewHolder, final int position) {
            if (VIEW_TYPE_TEXT == getItemViewType(position)) {
                ListItemHolder holder = (ListItemHolder) listViewHolder;
                String title = (position == 0) ? "Multiple banners in RecyclerView integration" : "Lorem ipsum dolor sit amet";
                String subTitle = (position == 0) ? "See implementation in RecyclerActivity. Please scroll down to see the ads." : "Phasellus in tellus eget arcu volutpat bibendum vulputate ac sapien. Vivamus enim elit, gravida vel consequat sit amet, scelerisque vitae ex.";
                holder.configureForItem(title, subTitle, position);
            } else {
                final BannerViewHolder bannerHolder = (BannerViewHolder) listViewHolder;
                final AdViewWrapper wrapper = wrapperForPosition(position);
                if (wrapper != null && wrapper.isAvailable()) { //To be available a wrapper must not have a ViewHolder.
                    wrapper.setHolder(bannerHolder);
                }
            }
        }

        /**
         * On recycle :
         * If the ViewHolder to be recycled was holding an Ad, tell the wrapper to unset it's holder.
         * The wrapper will become available again to be displayed in another holder.
         */
        @Override
        public void onViewRecycled(RecyclerView.ViewHolder holder) {
            super.onViewRecycled(holder);
            if (holder instanceof BannerViewHolder) {
                AdViewWrapper wrapper = wrapperForHolder((BannerViewHolder) holder);
                if (wrapper != null) {
                    wrapper.setHolder(null);
                }
            }
        }


        @Override
        public int getItemCount() {
            return 400;
        }

    }

}

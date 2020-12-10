package com.smartadserver.android.sassample;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smartadserver.android.library.model.SASAdPlacement;
import com.smartadserver.android.library.model.SASNativeAdElement;
import com.smartadserver.android.library.model.SASNativeAdManager;
import com.smartadserver.android.library.ui.SASNativeAdMediaView;
import com.smartadserver.android.sassample.util.ListNativeAdWithMediaHolder;
import com.smartadserver.android.sassample.util.ListNativeItemHolder;
import com.smartadserver.android.sassample.util.DividerItemDecoration;


public class NativeActivity extends AppCompatActivity {


    /*****************************************
     * Ad Constants
     *****************************************/
    private final static int SITE_ID = 104808;
    private final static String PAGE_ID = "720265";
    private final static String PAGE_ID_WITH_MEDIA = "692588";
    private final static int FORMAT_ID = 15140;
    private final static String TARGET = "";

    // If you are an inventory reseller, you must provide your Supply Chain Object information.
    // More info here: https://help.smartadserver.com/s/article/Sellers-json-and-SupplyChain-Object
    private final static String SUPPLY_CHAIN_OBJECT_STRING = null; // "1.0,1!exchange1.com,1234,1,publisher,publisher.com";

    /*****************************************
     * Members declarations
     *****************************************/
    // UI
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;

    // Ad
    private SASAdPlacement mAdPlacement;
    private SASNativeAdManager mNativeAdManager;
    private SASNativeAdElement mCurrentNativeAd;
    private Bitmap mNativeAdIconBitmap;
    private Bitmap mNativeAdCoverBitmap;
    private boolean mIsAdLoaded = false;
    private static final int AD_POSITION = 8;
    private boolean mAdWithMedia = false;

    private HashSet<SASNativeAdMediaView> nativeAdMediaViews = new HashSet<SASNativeAdMediaView>();


    /**
     * Performs Activity initialization after creation
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native);

        //Get UI Objects
        mRecyclerView = findViewById(R.id.activity_native_recycler_view);

        //Create Layout Manager
        mLayoutManager = new LinearLayoutManager(this);

        //BindViews
        bindViews();

        //Do we want native ad with media ?
        mAdWithMedia = getIntent().getBooleanExtra("withMedia",false);

        if(mAdWithMedia) {
            setTitle("Native Ad with Media");
        }


        //Ad management
        createAndRequestNativeAd();

    }

    /**
     * Create a placement, a manager, a response handler and request a native ad.
     */
    private void createAndRequestNativeAd() {

        // Find correct PageID for ad
        String pageID = NativeActivity.PAGE_ID;
        if (mAdWithMedia) {
            pageID = NativeActivity.PAGE_ID_WITH_MEDIA;
        }

        // Create a native ad placement.
        mAdPlacement = new SASAdPlacement(SITE_ID, pageID, FORMAT_ID, TARGET, SUPPLY_CHAIN_OBJECT_STRING);

        // Create a native ad manager, initialized with the placement.
        mNativeAdManager = new SASNativeAdManager(this, mAdPlacement);

        // Create the native ad manager listener.
        mNativeAdManager.setNativeAdListener(new SASNativeAdManager.NativeAdListener() {
            @Override
            public void onNativeAdLoaded(SASNativeAdElement nativeAdElement) {
                mCurrentNativeAd = nativeAdElement;
                mIsAdLoaded = true;

                // Download icon for native ad
                if (nativeAdElement.getIcon() != null) {
                    String iconUrl = nativeAdElement.getIcon().getUrl();
                    if (iconUrl != null && iconUrl.length() > 0) {
                        mNativeAdIconBitmap  =  scaledBitmapFromUrl(iconUrl, nativeAdElement.getIcon().getWidth(), nativeAdElement.getIcon().getHeight());
                    }
                }

                // Download cover for native ad
                if (nativeAdElement.getCoverImage() != null) {
                    String coverUrl = nativeAdElement.getCoverImage().getUrl();
                    if (coverUrl != null && coverUrl.length() > 0) {
                        mNativeAdCoverBitmap  =  scaledBitmapFromUrl(coverUrl, nativeAdElement.getCoverImage().getWidth(), nativeAdElement.getCoverImage().getHeight());
                    }
                }

                mRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.getAdapter().notifyItemChanged(AD_POSITION);
                    }
                });
            }

            @Override
            public void onNativeAdFailedToLoad(Exception e) {
                Log.i("NativeActivity", "Native Ad Loading Failed.");
            }
        });

        mNativeAdManager.loadNativeAd();
    }

    /**
     * Overriden to clean up Ad instances. This must be done to avoid IntentReceiver leak.
     */
    @Override
    protected void onDestroy() {
        mNativeAdManager.onDestroy();
        for (SASNativeAdMediaView mediaView : nativeAdMediaViews) {
            mediaView.onDestroy();
        }
        super.onDestroy();
    }

    /**
     * Bind Views.
     */
    private void bindViews() {
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(new NativeActivity.ListLayoutAdapter());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    /**
     * The adapter class responsible for creating RecyclerView.ViewHolder instances for different cells.
     */
    private class ListLayoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private static final int TYPE_NATIVE_CONTENT = 0;
        private static final int TYPE_AD_WITH_MEDIA = 1;

        private boolean isAdViewAtPosition(int position) {
            return (position == AD_POSITION && mCurrentNativeAd != null && mIsAdLoaded);
        }

        @Override
        public int getItemViewType(int position) {

            // If Ad Position and Ad has a media, we will use a different ViewHolder
            // to take full avantage of this format
            if (isAdViewAtPosition(position) && mCurrentNativeAd.getMediaElement() != null) {
                return TYPE_AD_WITH_MEDIA;
            }

            // Else, display our classic content view
            return TYPE_NATIVE_CONTENT;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_AD_WITH_MEDIA) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_native_ad_media, parent, false);
                ListNativeAdWithMediaHolder mediaHolder = new ListNativeAdWithMediaHolder(v);
                // need to get ahold of the SASNativeAdMediaView created to release its resources when this Activity is destroyed
                if (mediaHolder.getMediaView() != null) {
                    nativeAdMediaViews.add(mediaHolder.getMediaView());
                }
                return mediaHolder;
            }

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_native_ad, parent, false);
            return new ListNativeItemHolder(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder listViewHolder, final int position) {

            // It's the ad position, configure the viewHolder for the Ad
            if (isAdViewAtPosition(position)) {

                // Check if NativeAd has a media and process accordingly
                // No media = ListNativeItemHolder
                // Media = ListNativeAdWithMediaHolder
                if (mCurrentNativeAd.getMediaElement() == null) {
                    configureViewHolderForNativeAd(listViewHolder, mCurrentNativeAd,mNativeAdIconBitmap);
                } else {
                    configureViewHolderForNativeAdWithMedia(listViewHolder, mCurrentNativeAd,mNativeAdIconBitmap, mNativeAdCoverBitmap);
                }

            } else { //It's not the ad position, configure the viewHolder for content
                ListNativeItemHolder holder = (ListNativeItemHolder) listViewHolder;
                String title = (position == 0) ? "Simple NativeAd integration" : "Lorem ipsum dolor sit amet";
                String subTitle = (position == 0) ? "See implementation in NativeActivity. Please scroll down to see the ad." : "Phasellus in tellus eget arcu volutpat bibendum vulputate ac sapien. Vivamus enim elit, gravida vel consequat sit amet, scelerisque vitae ex.";
                holder.configureForContentItem(title, subTitle, position);
            }

        }

        private void configureViewHolderForNativeAd(RecyclerView.ViewHolder listViewHolder, SASNativeAdElement nativeAd, Bitmap iconImage) {
            ListNativeItemHolder holder = (ListNativeItemHolder) listViewHolder;

            Bitmap scaledIconImage = null;

            //Has iconImage been loaded ?
            if (iconImage != null) {
                scaledIconImage = Bitmap.createScaledBitmap(iconImage, holder.posterImageView.getLayoutParams().width, holder.posterImageView.getLayoutParams().height, false);
            }

            //Extract nativeAd datas
            String title = nativeAd.getTitle();
            String subtitle = nativeAd.getSubtitle();
            String callToAction = nativeAd.getCalltoAction();

            //Configure View for Ad
            holder.configureForAdItem(title, subtitle, callToAction, scaledIconImage);

            //Register View For Native Ad
            nativeAd.registerView(listViewHolder.itemView);
        }

        private void configureViewHolderForNativeAdWithMedia(RecyclerView.ViewHolder listViewHolder, SASNativeAdElement nativeAd, Bitmap iconImage, Bitmap coverImage) {
            ListNativeAdWithMediaHolder holder = (ListNativeAdWithMediaHolder) listViewHolder;

            //Extract nativeAd datas
            String title = nativeAd.getTitle();
            String subtitle = nativeAd.getSubtitle();
            String callToAction = nativeAd.getCalltoAction();

            //Configure View for Ad
            holder.configureForAdItem(title, subtitle, callToAction, iconImage, coverImage);

            //Register View For Native Ad
            nativeAd.registerView(listViewHolder.itemView);

            // Register NativeAdElement on SASNativeAdMediaView
            // This will allow the media to be displayed / tracked properly
            try {
                holder.getMediaView().setNativeAdElement(nativeAd);

            } catch (Exception e) {
                Log.e("SASSample", "Cannot set NativeAdElement on SASNativeMediaView");
            }

            // Register NativeAdElement on SASAdChoiceView
            // This will allow the ad choice option to be opened properly.
            // This is required for Facebook Mediation
            try {
                holder.getAdChoicesView().setNativeAdElement(nativeAd);

            } catch (Exception e) {
                Log.e("SASSample", "Cannot set NativeAdElement on SASAdChoiceView");
            }

        }

        @Override
        public void onViewRecycled(RecyclerView.ViewHolder holder) {
            super.onViewRecycled(holder);
            if (mCurrentNativeAd != null) {
                mCurrentNativeAd.unregisterView(holder.itemView);
            }
        }

        @Override
        public int getItemCount() {
            return 40;
        }

    }

    /*
     * Download an image from an URL and scales it to targetWidth * targetHeight
     */
    private static Bitmap scaledBitmapFromUrl(String url,int targetWidth, int targetHeight) {
        Bitmap result = null;
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            int bWidth = bitmap.getWidth();
            int bHeight = bitmap.getHeight();
            double resizeRatio = Math.min(targetWidth / (double)bWidth, targetHeight / (double)bHeight);
            result = Bitmap.createScaledBitmap(bitmap, (int)(bWidth * resizeRatio), (int)(bHeight * resizeRatio), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}

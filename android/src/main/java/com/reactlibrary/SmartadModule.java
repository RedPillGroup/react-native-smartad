package com.reactlibrary;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;
import android.os.Bundle;
import android.os.Looper;
import android.os.Handler;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.smartadserver.android.library.model.SASReward;
import com.smartadserver.android.library.model.SASAdStatus;
import com.smartadserver.android.library.model.SASAdElement;
import com.smartadserver.android.library.model.SASAdPlacement;
import com.smartadserver.android.library.model.SASNativeVideoAdElement;
import com.smartadserver.android.library.ui.SASBannerView;
import com.smartadserver.android.library.util.SASConfiguration;
import com.smartadserver.android.library.rewarded.SASRewardedVideoManager;

import com.facebook.react.ReactActivity;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class SmartadModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    /****************************
     * Ad Constants
     ****************************/

     private final static String TAG               = "SmartadModule";

    /****************************
     * Ad Variables
     ****************************/

     SASAdPlacement                                mRewardedVideoPlacement;
     SASRewardedVideoManager                       mRewardedVideoManager;
     SASRewardedVideoManager.RewardedVideoListener mRewardedVideoListener;

     SASAdPlacement                                mBannerPlacement;
     SASBannerView mBannerView;
 
    /****************************
     * Members declarations
     ****************************/
    

    public SmartadModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "Smartad";
    }

    @ReactMethod
    public void initialize(final @NonNull int SITE_ID) {
        // Enables output to log.
        SASConfiguration.getSharedInstance().configure(reactContext, SITE_ID);
        SASConfiguration.getSharedInstance().setLoggingEnabled(true);
    }

    @ReactMethod
    public void initializeRewardedVideo(final @NonNull int SITE_ID, final @NonNull String PAGE_ID, final @NonNull int FORMAT_ID, final @Nullable String TARGET) {

        // Initializes the SmartAdServer on main thread
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                mRewardedVideoPlacement = new SASAdPlacement(SITE_ID, PAGE_ID, FORMAT_ID, TARGET);
                mRewardedVideoManager = new SASRewardedVideoManager(reactContext, mRewardedVideoPlacement);
                initRewardVideoListener();
                mRewardedVideoManager.setRewardedVideoListener(mRewardedVideoListener);
            }
        });
    }


    @ReactMethod
    public void loadRewardedVideoAd(@Nullable String securedTransactionToken) {
        if (mRewardedVideoManager != null) {
            if (securedTransactionToken == null)
                mRewardedVideoManager.loadRewardedVideo();
            else
                mRewardedVideoManager.loadRewardedVideo(securedTransactionToken);
        } else {
            sendEvent("smartAdRewardedVideoAdFailedToLoad", null);
        }
    }

    @ReactMethod
    public void showRewardedVideo() {
        if (mRewardedVideoManager != null && mRewardedVideoManager.getAdStatus() == SASAdStatus.READY) {
            mRewardedVideoManager.showRewardedVideo();
        } else {
            Log.e(SmartadModule.TAG, "RewardedVideo is not ready for the current placement.");
            sendEvent("smartAdRewardedVideoNotReady", null);
        }
    }
    private void initRewardVideoListener() {
        this.mRewardedVideoListener = new SASRewardedVideoManager.RewardedVideoListener() {
            @Override
            public void onRewardedVideoAdLoaded(SASRewardedVideoManager rewardedVideoManager, SASAdElement adElement) {
                Log.i(SmartadModule.TAG, "RewardedVideo Ad loading completed.");

                SASNativeVideoAdElement castedAd = (SASNativeVideoAdElement)adElement;
                if (!castedAd.getPosterImageUrl().isEmpty()) {
                    WritableMap params = Arguments.createMap();

                    HashMap<String, Object> extraParameters = adElement.getExtraParameters();
                    WritableMap extraParams = Arguments.createMap();
                    for (Map.Entry<String, Object> entry : extraParameters.entrySet()) {
                        extraParams.putString(entry.getKey(), entry.getValue().toString());
                    }

                    params.putString("url", castedAd.getPosterImageUrl());
                    params.putMap("extraparams", extraParams);

                    sendEvent("kSmartAdVignette", params);
                }
                sendEvent("smartAdRewardedVideoAdLoaded", null);
            }

            @Override
            public void onRewardedVideoAdFailedToLoad(SASRewardedVideoManager rewardedVideoManager, Exception exception) {
                Log.i(SmartadModule.TAG, "RewardedVideo Ad loading failed with exception: " + exception.getLocalizedMessage());
                WritableMap params = Arguments.createMap();
                params.putString("message", exception.getLocalizedMessage());
                sendEvent("smartAdRewardedVideoAdFailedToLoad", params);
            }

            @Override
            public void onRewardedVideoAdShown(SASRewardedVideoManager rewardedVideoManager) {
                Log.i(SmartadModule.TAG, "RewardedVideo ad is shown.");
                sendEvent("smartAdRewardedVideoAdShown", null);
            }

            @Override
            public void onRewardedVideoAdFailedToShow(SASRewardedVideoManager rewardedVideoManager, Exception exception) {
                Log.i(SmartadModule.TAG, "RewardedVideo playback failed with exception: " + exception.getLocalizedMessage());
                sendEvent("smartAdVideoAdFailedToShow", null);
            }

            @Override
            public void onRewardedVideoAdClosed(SASRewardedVideoManager rewardedVideoManager) {
                Log.i(SmartadModule.TAG, "RewardedVideo closed.");
                sendEvent("smartAdRewardedVideoAdClosed", null);
            }

            @Override
            public void onRewardReceived(SASRewardedVideoManager rewardedVideoManager, SASReward reward) {
                if (reward != null) {
                    Log.i(SmartadModule.TAG, "RewardedVideo collected a reward.");
                    Log.i(SmartadModule.TAG, "User should be rewarded with: " + reward.getAmount() + " " + reward.getCurrency() + ".");
                    WritableMap params = Arguments.createMap();
                    params.putDouble("amount", reward.getAmount());
                    params.putString("currency", reward.getCurrency());
                    params.putString("securedToken", reward.getSecuredTransactionToken());
                    sendEvent("smartAdRewardReceived", params);
                } else {
                    sendEvent("smartAdRewardNotReceived", null);
                }
            }

            @Override
            public void onRewardedVideoAdClicked(SASRewardedVideoManager rewardedVideoManager) {
                Log.i(SmartadModule.TAG, "RewardedVideo clicked.");
                sendEvent("smartAdRewardedVideoAdClicked", null);
            }

            @Override
            public void onRewardedVideoEvent(SASRewardedVideoManager rewardedVideoManager, int videoEvent) {
                Log.i(SmartadModule.TAG, "RewardedVideo did send event: " + videoEvent);
                sendEvent("smartAdRewardedVideoEvent", null);
            }

            @Override
            public void onRewardedVideoEndCardDisplayed(SASRewardedVideoManager rewardedVideoManager, ViewGroup endCardView) {
                Log.i(SmartadModule.TAG, "RewardedVideo HTML EndCard displayed.");
                sendEvent("smartAdRewardedVideoEndCardDisplayed", null);
            }
        };
    }

    @ReactMethod
    protected void reset() {
        if (mRewardedVideoManager != null) {
            mRewardedVideoManager.reset();
        }
    }

    @ReactMethod
    protected void onDestroy() {
        if (mRewardedVideoManager != null) {
            mRewardedVideoManager.onDestroy();
        }
        if(mBannerView != null) {
            mBannerView.onDestroy();
        }
    }

    private void sendEvent(String eventName, @Nullable WritableMap params) {
        getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }

    // *************** BANNER VIEW *************** //
    
    @ReactMethod
    public void initializeBanner(final @NonNull int SITE_ID, final @NonNull String PAGE_ID, final @NonNull int FORMAT_ID, final @Nullable String TARGET) {
        // Initializes the SmartAdServer on main thread
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                mBannerPlacement = new SASAdPlacement(SITE_ID, PAGE_ID, FORMAT_ID, TARGET);
                Log.i("Sample", "Init banner");
                mBannerView = new SASBannerView(reactContext);
                initializeBannerListener();
            }
        });
    }

    private void initializeBannerListener() {
        SASBannerView.BannerListener bannerListener = new SASBannerView.BannerListener() {
            @Override
            public void onBannerAdLoaded(SASBannerView bannerView, SASAdElement adElement) {
                Log.i("Sample", "Banner loading completed");
                sendEvent("smartAdBannerAdLoaded",null);
            }

            @Override
            public void onBannerAdFailedToLoad(SASBannerView bannerView, Exception e) {
                Log.i("Sample", "Banner loading failed: " + e.getMessage());
                sendEvent("smartAdBannerFailedToLoad",null);
            }

            @Override
            public void onBannerAdClicked(SASBannerView bannerView) {
                Log.i("Sample", "Banner was clicked");
                sendEvent("smartAdBannerAdClicked",null);
            }

            @Override
            public void onBannerAdExpanded(SASBannerView bannerView) {
                Log.i("Sample", "Banner was expanded");
                // sendEvent("smartAdBannerAdExpanded",null);
            }

            @Override
            public void onBannerAdCollapsed(SASBannerView bannerView) {
                Log.i("Sample", "Banner was collapsed");
                // sendEvent("smartAdBannerAdCollapsed",null);
            }

            @Override
            public void onBannerAdResized(SASBannerView bannerView) {
                Log.i("Sample", "Banner was resized");
                // sendEvent("smartAdBannerAdResized",null);
            }

            @Override
            public void onBannerAdClosed(SASBannerView bannerView) {
                Log.i("Sample", "Banner was closed");
                sendEvent("smartAdBannerAdClosed",null);
            }

            @Override
            public void onBannerAdVideoEvent(SASBannerView bannerView, int videoEvent) {
                Log.i("Sample", "Video event " + videoEvent + " was triggered on Banner");
                sendEvent("smartAdBannerAdVideoEvent",null);
            }
        };

        mBannerView.setBannerListener(bannerListener);
    }

    @ReactMethod
    public void loadBannerAd() {
        if (mBannerView != null) {
            mBannerView.loadAd(mBannerPlacement);
        } else {
            sendEvent("smartAdBannerAdFailedToLoad", null);
        }
    }
}

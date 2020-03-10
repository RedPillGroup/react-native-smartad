package com.reactlibrary;

import android.util.Log;
import androidx.annotation.Nullable;
import android.view.ViewGroup;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.smartadserver.android.library.model.SASAdPlacement;
import com.smartadserver.android.library.rewarded.SASRewardedVideoManager;
import com.smartadserver.android.library.util.SASConfiguration;
import com.smartadserver.android.library.model.SASAdStatus;
import com.smartadserver.android.library.model.SASReward;
import com.smartadserver.android.library.model.SASAdElement;

import com.facebook.react.ReactActivity;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class SmartadModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    /****************************
     * Ad Constants
     ****************************/
    private final static String TAG               = "SmartadModule";
    private final static int    SITE_ID           = 339683;
    private final static String PAGE_ID           = "1188835";
    private final static int    FORMAT_ID         = 87843;
    private final static String TARGET            = null;


    // /****************************
    //  * Members declarations
    //  ****************************/
    
    SASAdPlacement mRewaredVideoPlacement;
    SASRewardedVideoManager mRewardedVideoManager;
    SASRewardedVideoManager.RewardedVideoListener mRewardedVideoListener;

    public SmartadModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "Smartad";
    }

    @ReactMethod
    public void initializeRewardedVideo() {
        // DEV: Enable output to log.
        SASConfiguration.getSharedInstance().setLoggingEnabled(true);

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                SASConfiguration.getSharedInstance().configure(reactContext, SmartadModule.SITE_ID, "https://mobile.smartadserver.com");
                mRewaredVideoPlacement = new SASAdPlacement(SmartadModule.SITE_ID, SmartadModule.PAGE_ID, SmartadModule.FORMAT_ID, SmartadModule.TARGET);
                mRewardedVideoManager = new SASRewardedVideoManager(reactContext, mRewaredVideoPlacement);
                initRewardVideoListener();
                mRewardedVideoManager.setRewardedVideoListener(mRewardedVideoListener);
            }
        });
    }

    @ReactMethod
    public void loadRewardedVideoAd() {
        mRewardedVideoManager.loadRewardedVideo();
    }

    @ReactMethod
    public void showRewardedVideo() {
        if (mRewardedVideoManager.getAdStatus() == SASAdStatus.READY) {
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
                sendEvent("smartAdRewardedVideoAdLoaded", null);
            }

            @Override
            public void onRewardedVideoAdFailedToLoad(SASRewardedVideoManager rewardedVideoManager, Exception exception) {
                Log.i(SmartadModule.TAG, "RewardedVideo Ad loading failed with exception: " + exception.getLocalizedMessage());
                sendEvent("smartAdRewardedVideoAdFailedToLoad", null);
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
                    sendEvent("smartAdRewardReceived", null);
                }
                sendEvent("smartAdRewardNotReceived", null);
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

    protected void onDestroy() {
        mRewardedVideoManager.onDestroy();
    }

    private void sendEvent(String eventName, @Nullable WritableMap params) {
        getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }
}

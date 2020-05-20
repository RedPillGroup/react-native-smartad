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
    public void initializeRewardedVideo(final @NonNull int SITE_ID, final @NonNull String PAGE_ID, final @NonNull int FORMAT_ID, final @Nullable String TARGET) {
        // Enables output to log.
        SASConfiguration.getSharedInstance().setLoggingEnabled(true);

        // Initializes the SmartAdServer on main thread
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                SASConfiguration.getSharedInstance().configure(reactContext, SITE_ID, "https://mobile.smartadserver.com");
                mRewardedVideoPlacement = new SASAdPlacement(SITE_ID, PAGE_ID, FORMAT_ID, TARGET);
                mRewardedVideoManager = new SASRewardedVideoManager(reactContext, mRewardedVideoPlacement);
                initRewardVideoListener();
                mRewardedVideoManager.setRewardedVideoListener(mRewardedVideoListener);
            }
        });
    }

    @ReactMethod
    public void loadRewardedVideoAd() {
        if (mRewardedVideoManager != null) {
            mRewardedVideoManager.loadRewardedVideo();
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
                    WritableMap params = Arguments.createMap();
                    params.putDouble("amount", reward.getAmount());
                    params.putString("currency", reward.getCurrency());
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

    protected void onDestroy() {
        mRewardedVideoManager.onDestroy();
    }

    private void sendEvent(String eventName, @Nullable WritableMap params) {
        getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }
}

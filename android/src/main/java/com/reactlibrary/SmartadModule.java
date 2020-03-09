package com.reactlibrary;

import android.util.Log;

import com.smartadserver.android.library.model.SASAdPlacement;
import com.smartadserver.android.library.rewarded.SASRewardedVideoManager;
import com.smartadserver.android.library.util.SASConfiguration;
import com.smartadserver.android.library.model.SASAdStatus;
import com.smartadserver.android.library.model.SASReward;

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
    private final static int    SITE_ID           = 1234;
    private final static String PAGE_ID           = "1234";
    private final static int    FORMAT_ID         = 1234;
    private final static String KEYWORD_TARGETING = "rewardedvideo";


    /****************************
     * Members declarations
     ****************************/
    
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

        mRewaredVideoPlacement = new SASAdPlacement(SITE_ID, PAGE_ID, FORMAT_ID, KEYWORD_TARGETING, true);
        mRewardedVideoManager = new SASRewardedVideoManager(this, mRewaredVideoPlacement);
        initRewardVideoListener();
        mRewardedVideoManager.setRewardedVideoListener(mRewardedVideoListener);
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
            Log.e(RewardedVideoActivity.TAG, "RewardedVideo is not ready for the current placement.");
            sendEvent("smartAdRewardedVideoNotReady", null);
        }
    }

    private void initRewardVideoListener() {
        mRewardedVideoListener = new SASRewardedVideoManager.RewardedVideoListener() {
            @Override
            public void onRewardedVideoAdLoaded(SASRewardedVideoManager rewardedVideoManager, SASAdElement adElement) {
                Log.i(RewardedVideoActivity.TAG, "RewardedVideo Ad loading completed.");
                sendEvent("smartAdA", null);
            }

            @Override
            public void onRewardedVideoAdFailedToLoad(SASRewardedVideoManager rewardedVideoManager, Exception exception) {
                Log.i(RewardedVideoActivity.TAG, "RewardedVideo Ad loading failed with exception: " + exception.getLocalizedMessage());
                sendEvent("smartAdB", null);
            }

            @Override
            public void onRewardedVideoAdShown(SASRewardedVideoManager rewardedVideoManager) {
                Log.i(TAG, "RewardedVideo ad is shown.");
                sendEvent("smartAdC", null);
            }

            @Override
            public void onRewardedVideoAdFailedToShow(SASRewardedVideoManager rewardedVideoManager, Exception exception) {
                Log.i(RewardedVideoActivity.TAG, "RewardedVideo playback failed with exception: " + exception.getLocalizedMessage());
                sendEvent("smartAdD", null);
            }

            @Override
            public void onRewardedVideoAdClosed(SASRewardedVideoManager rewardedVideoManager) {
                Log.i(RewardedVideoActivity.TAG, "RewardedVideo closed.");
                sendEvent("smartAdE", null);
            }

            @Override
            public void onRewardReceived(SASRewardedVideoManager rewardedVideoManager, SASReward reward) {
                if (reward != null) {
                    Log.i(RewardedVideoActivity.TAG, "RewardedVideo collected a reward.");
                    Log.i(RewardedVideoActivity.TAG, "User should be rewarded with: " + reward.getAmount() + " " + reward.getCurrency() + ".");
                    sendEvent("smartAdF", null);
                }
                sendEvent("smartAdI", null);
            }

            @Override
            public void onRewardedVideoAdClicked(SASRewardedVideoManager rewardedVideoManager) {
                Log.i(RewardedVideoActivity.TAG, "RewardedVideo clicked.");
                sendEvent("smartAdJ", null);
            }

            @Override
            public void onRewardedVideoEvent(SASRewardedVideoManager rewardedVideoManager, int videoEvent) {
                Log.i(RewardedVideoActivity.TAG, "RewardedVideo did send event: " + videoEvent);
                sendEvent("smartAdK", null);
            }

            @Override
            public void onRewardedVideoEndCardDisplayed(SASRewardedVideoManager rewardedVideoManager, ViewGroup endCardView) {
                Log.i(RewardedVideoActivity.TAG, "RewardedVideo HTML EndCard displayed.");
                sendEvent("smartAdL", null);
            }
        };
    }

    @Override
    protected void onDestroy() {
        mRewardedVideoManager.onDestroy();
        super.onDestroy();
    }

    private void sendEvent(String eventName, @Nullable WritableMap params) {
        getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(event, params);
    }
}

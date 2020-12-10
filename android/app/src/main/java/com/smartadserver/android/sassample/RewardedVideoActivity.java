package com.smartadserver.android.sassample;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.smartadserver.android.library.model.SASAdElement;
import com.smartadserver.android.library.model.SASAdPlacement;
import com.smartadserver.android.library.model.SASAdStatus;
import com.smartadserver.android.library.model.SASReward;
import com.smartadserver.android.library.rewarded.SASRewardedVideoManager;
import com.smartadserver.android.library.util.SASConfiguration;


public class RewardedVideoActivity extends AppCompatActivity {

    /*****************************************
     * Ad Constants
     *****************************************/
    private final static String TAG = "RewardedVideoActivity";
    private final static int SITE_ID = 104808;
    private final static String PAGE_ID = "795153";
    private final static int FORMAT_ID = 12167;
    private final static String TARGET = "rewardedvideo";

    // If you are an inventory reseller, you must provide your Supply Chain Object information.
    // More info here: https://help.smartadserver.com/s/article/Sellers-json-and-SupplyChain-Object
    private final static String SUPPLY_CHAIN_OBJECT_STRING = null; // "1.0,1!exchange1.com,1234,1,publisher,publisher.com";

    /*****************************************
     * Members declarations
     *****************************************/
    // SASRewardedVideoPlacement
    SASAdPlacement mRewardedVideoPlacement;

    // SASRewardedVideoManager shared instance
    SASRewardedVideoManager mRewardedVideoManager;

    // Listener class to react to SASRewardedVideo events
    SASRewardedVideoManager.RewardedVideoListener mRewardedVideoListener;

    // Buttons declared in activity's xml layout.
    Button mLoadRewardedButton;
    Button mShowRewardedButton;


    /**
     * performs Activity initialization after creation
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewarded_video);

        // Enable output to Android Logcat (optional)
        SASConfiguration.getSharedInstance().setLoggingEnabled(true);

        // Initialize SASRewardedVideoPlacement
        mRewardedVideoPlacement = new SASAdPlacement(RewardedVideoActivity.SITE_ID,
                PAGE_ID,
                FORMAT_ID,
                TARGET,
                SUPPLY_CHAIN_OBJECT_STRING);

        // Instantiate a new rewarded video manager
        mRewardedVideoManager = new SASRewardedVideoManager(this, mRewardedVideoPlacement);

        // Initialize SASRewardedVideoListener
        initRewardedVideoListener();

        // Set the listener
        mRewardedVideoManager.setRewardedVideoListener(mRewardedVideoListener);

        // Create buttons to manually load and show Rewarded Video Ads
        createButtons();

        // Set show button state depending of the availability of an ad on the placement
        enableShowButton(mRewardedVideoManager.getAdStatus() == SASAdStatus.READY);
    }


    /**
     * Create Load and Show buttons
     */
    private void createButtons() {
        mLoadRewardedButton = (Button) this.findViewById(R.id.loadRewarded);
        mLoadRewardedButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loadRewardedVideoAd();
            }
        });

        mShowRewardedButton = (Button) this.findViewById(R.id.showRewarded);
        mShowRewardedButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showRewardedVideoAd();
            }
        });
    }


    /**
     * Enable / Disable show button.
     */
    private void enableShowButton(final boolean enabled) {
        // enforce execution in main thread, as this method might be called from a different thread
        mShowRewardedButton.post(new Runnable() {
            @Override
            public void run() {
                if (enabled) {
                    mShowRewardedButton.setVisibility(View.VISIBLE);
                } else {
                    mShowRewardedButton.setVisibility(View.INVISIBLE);
                }
            }
        });
    }


    /**
     * initialize the rewarded video listener.
     */
    private void initRewardedVideoListener() {
        mRewardedVideoListener = new SASRewardedVideoManager.RewardedVideoListener() {
            @Override
            public void onRewardedVideoAdLoaded(SASRewardedVideoManager rewardedVideoManager, SASAdElement adElement) {
                Log.i(RewardedVideoActivity.TAG, "RewardedVideo Ad loading completed.");
                enableShowButton(true);
            }

            @Override
            public void onRewardedVideoAdFailedToLoad(SASRewardedVideoManager rewardedVideoManager, Exception exception) {
                Log.i(RewardedVideoActivity.TAG, "RewardedVideo Ad loading failed with exception: " + exception.getLocalizedMessage());
                enableShowButton(false);
            }

            @Override
            public void onRewardedVideoAdShown(SASRewardedVideoManager rewardedVideoManager) {
                Log.i(TAG, "RewardedVideo ad is shown.");
                enableShowButton(false);
            }

            @Override
            public void onRewardedVideoAdFailedToShow(SASRewardedVideoManager rewardedVideoManager, Exception exception) {
                Log.i(RewardedVideoActivity.TAG, "RewardedVideo playback failed with exception: " + exception.getLocalizedMessage());
                enableShowButton(false);
            }

            @Override
            public void onRewardedVideoAdClosed(SASRewardedVideoManager rewardedVideoManager) {
                Log.i(RewardedVideoActivity.TAG, "RewardedVideo closed.");
            }

            @Override
            public void onRewardReceived(SASRewardedVideoManager rewardedVideoManager, SASReward reward) {
                if (reward != null) {
                    Log.i(RewardedVideoActivity.TAG, "RewardedVideo collected a reward.");
                    Log.i(RewardedVideoActivity.TAG, "User should be rewarded with: " + reward.getAmount() + " " + reward.getCurrency() + ".");
                }
            }

            @Override
            public void onRewardedVideoAdClicked(SASRewardedVideoManager rewardedVideoManager) {
                Log.i(RewardedVideoActivity.TAG, "RewardedVideo clicked.");
            }

            @Override
            public void onRewardedVideoEvent(SASRewardedVideoManager rewardedVideoManager, int videoEvent) {
                Log.i(RewardedVideoActivity.TAG, "RewardedVideo did send event: " + videoEvent);
            }

            @Override
            public void onRewardedVideoEndCardDisplayed(SASRewardedVideoManager rewardedVideoManager, ViewGroup endCardView) {
                Log.i(RewardedVideoActivity.TAG, "RewardedVideo HTML EndCard displayed.");
            }
        };
    }


    /**
     * Loads a Rewarded Video Ad for the current placement
     */
    private void loadRewardedVideoAd() {
        // Loads a Rewarded Video Ad for the current placement
        mRewardedVideoManager.loadRewardedVideo();
    }


    /**
     * Show a Rewarded Video Ad for the current placement
     */
    private void showRewardedVideoAd() {
        if (mRewardedVideoManager.getAdStatus() == SASAdStatus.READY) {
            // Show a Rewarded Video Ad for the current placement
            mRewardedVideoManager.showRewardedVideo();
        } else {
            Log.e(RewardedVideoActivity.TAG, "RewardedVideo is not ready for the current placement.");
        }
    }

    /**
     * Overriden to clean up resources used by the SASRewardedVideoManager
     */
    @Override
    protected void onDestroy() {
        mRewardedVideoManager.onDestroy();
        super.onDestroy();
    }


}

package com.smartadserver.android.sassample;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.smartadserver.android.library.headerbidding.SASBiddingAdResponse;
import com.smartadserver.android.library.headerbidding.SASBiddingFormatType;
import com.smartadserver.android.library.headerbidding.SASBiddingManager;
import com.smartadserver.android.library.model.SASAdElement;
import com.smartadserver.android.library.model.SASAdPlacement;
import com.smartadserver.android.library.ui.SASInterstitialManager;
import com.smartadserver.android.library.util.SASConfiguration;
import com.smartadserver.android.library.util.SASUtil;

public class InAppBiddingInterstitialActivity extends AppCompatActivity implements SASBiddingManager.SASBiddingManagerListener {

    /*****************************************
     * Ad Constants
     *****************************************/
    private final static SASAdPlacement INTERSTITIAL_PLACEMENT = new SASAdPlacement(104808,
            "1160279",
            85867,
            "interstitial-inapp-bidding");

    /*****************************************
     * Members declarations
     *****************************************/

    // Handler class to be notified of ad loading outcome
    private SASInterstitialManager.InterstitialListener interstitialListener;
    private SASInterstitialManager interstitialManager = null;

    // Manager object that will handle all bidding ad calls.
    private SASBiddingManager biddingManager;
    private boolean isBiddingManagerLoading = false;

    private SASBiddingAdResponse biddingAdResponse;

    // UI
    private Button loadButton;
    private Button showButton;
    private TextView statusTextView;

    /**
     * performs Activity initialization after creation
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inapp_bidding_interstitial);

        /*****************************************
         * Now perform Ad related code here
         *****************************************/
        // Enable output to Android Logcat (optional)
        SASConfiguration.getSharedInstance().setLoggingEnabled(true);

        // Enable debugging in Webview if available (optional)
        WebView.setWebContentsDebuggingEnabled(true);

        // Initialize SASInterstitialManager instance
        initInterstitialListener();

        // Create buttons to manually load the bidding ad
        loadButton = this.findViewById(R.id.loadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loadBiddingAd();
            }
        });

        // Create button to manually show the bidding ad in the interstitial
        showButton = this.findViewById(R.id.showButton);
        showButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showBiddingAd();
            }
        });

        // Create information TextView
        statusTextView = findViewById(R.id.statusTextView);

        // Create the bidding manager with appropriate Context, SASAdPlacement, SASBiddingFormatType, Currency and listener
        biddingManager = new SASBiddingManager(this, INTERSTITIAL_PLACEMENT, SASBiddingFormatType.INTERSTITIAL, "USD", this);

        updateUiStatus();
    }

    private void loadBiddingAd() {
        if (!isBiddingManagerLoading) {
            isBiddingManagerLoading = true;

            // Reset ad response
            biddingAdResponse = null;

            // Should not be null, but we check just in case
            if (biddingManager != null) {
                // Load the bidding ad.
                biddingManager.load();
            }

            updateUiStatus();
        }
    }

    /**
     * Shows the previously loaded bidding ad, if any.
     */
    private void showBiddingAd() {
        if (biddingAdResponse != null) {
            // We use our interstitial manager to display the bidding response retrieved earlier.
            //
            // Note: in an actual application, you would load Smart bidding ad response only if it
            // is better than responses you got from other third party SDKs.
            //
            // You can check the CPM associated with the bidding ad response by checking:
            // - biddingAdResponse.getBiddingAdPrice().getCpm()
            // - biddingAdResponse.getBiddingAdPrice().getCurrency()
            //
            // If you decide not to use the bidding ad response, you can simply discard it.

            // Reset current interstitial manager if any.
            if (interstitialManager != null) {
                interstitialManager.setInterstitialListener(null);
                interstitialManager = null;
            }

            // Create the interstitial manager
            interstitialManager = new SASInterstitialManager(this, biddingAdResponse);

            // Set listener
            interstitialManager.setInterstitialListener(interstitialListener);

            // Load bidding ad
            interstitialManager.loadAd();
        }

        updateUiStatus();
    }

    /**
     * Overriden to clean up the resources used by the {@link SASInterstitialManager}
     */
    @Override
    protected void onDestroy() {
        biddingManager.setBiddingManagerListener(null);

        if (interstitialManager != null) {
            interstitialManager.setInterstitialListener(null);
        }

        super.onDestroy();
    }


    /**
     * initialize the InterstitialListener instance of this Activity
     */
    private void initInterstitialListener() {
        // Create the InterstitialListener that will be used by all future created Interstitial Managers
        interstitialListener = new SASInterstitialManager.InterstitialListener() {
            @Override
            public void onInterstitialAdLoaded(SASInterstitialManager interstitialManager, SASAdElement adElement) {
                Log.i("Sample", "Interstitial loading completed");

                // In our case we decide to directly show the interstitial
                interstitialManager.show();
                updateUiStatus();
            }

            @Override
            public void onInterstitialAdFailedToLoad(SASInterstitialManager interstitialManager, Exception e) {
                Log.i("Sample", "Interstitial loading failed (" + e.getMessage() + ")");
                updateUiStatus();
            }

            @Override
            public void onInterstitialAdShown(SASInterstitialManager interstitialManager) {
                Log.i("Sample", "Interstitial was shown");
                updateUiStatus();
            }

            @Override
            public void onInterstitialAdFailedToShow(SASInterstitialManager interstitialManager, Exception e) {
                Log.i("Sample", "Interstitial failed to show (" + e.getMessage() + ")");
                updateUiStatus();
            }

            @Override
            public void onInterstitialAdClicked(SASInterstitialManager interstitialManager) {
                Log.i("Sample", "Interstitial was clicked");
            }

            @Override
            public void onInterstitialAdDismissed(SASInterstitialManager interstitialManager) {
                Log.i("Sample", "Interstitial was dismissed");
            }

            @Override
            public void onInterstitialAdVideoEvent(SASInterstitialManager interstitialManager, int videoEvent) {
                Log.i("Sample", "Video event " + videoEvent + " was triggered on Interstitial");
            }
        };
    }

    private boolean hasValidBiddingAdResponse() {
        // A bidding ad response is valid only if it has not been consumed already.
        return biddingAdResponse != null && !biddingAdResponse.isConsumed();
    }

    private void updateUiStatus() {
        SASUtil.getMainLooperHandler().post(new Runnable() {
            @Override
            public void run() {
                // Buttons
                loadButton.setEnabled(!isBiddingManagerLoading);
                showButton.setEnabled(hasValidBiddingAdResponse());

                // Status textview
                if (isBiddingManagerLoading) {
                    statusTextView.setText("loading a bidding ad…");
                } else if (biddingAdResponse != null) {
                    statusTextView.setText("bidding response: " + biddingAdResponse.getBiddingAdPrice().getCpm() + " " + biddingAdResponse.getBiddingAdPrice().getCurrency());
                } else {
                    statusTextView.setText("(no bidding ad response loaded)");
                }
            }
        });
    }

    /*******************************************
     * SASBiddingManagerListener implementation
     *******************************************/

    @Override
    public void onBiddingManagerAdLoaded(@NonNull SASBiddingAdResponse biddingAdResponse) {
        isBiddingManagerLoading = false;
        this.biddingAdResponse = biddingAdResponse;

        // A bidding ad response has been received.
        // you can now load it into an ad view or discard it. See showBiddingAd() for more info.

        Log.i("Sample", "A bidding ad response has been loaded: " + biddingAdResponse);

        updateUiStatus();
    }

    @Override
    public void onBiddingManagerAdFailedToLoad(@NonNull Exception e) {
        isBiddingManagerLoading = false;
        this.biddingAdResponse = null;

        Log.i("Sample", "Fail to load a bidding ad response: " + e.getMessage());

        updateUiStatus();
    }
}

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
import com.smartadserver.android.library.ui.SASBannerView;
import com.smartadserver.android.library.ui.SASRotatingImageLoader;
import com.smartadserver.android.library.util.SASConfiguration;
import com.smartadserver.android.library.util.SASUtil;

/**
 * Simple activity featuring a InApp bidding banner ad.
 */

public class InAppBiddingBannerActivity extends AppCompatActivity implements SASBiddingManager.SASBiddingManagerListener {

    /*****************************************
     * Ad Constants
     *****************************************/
    private final static SASAdPlacement BANNER_PLACEMENT = new SASAdPlacement(104808,
            "1160279",
            85867,
            "banner-inapp-bidding");


    /*****************************************
     * Members declarations
     *****************************************/
    // Banner view (as declared in the main.xml layout file, in res/layout)
    private SASBannerView bannerView;

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
        setContentView(R.layout.activity_inapp_bidding_banner);

        //Set Title
        setTitle(R.string.title_activity_inapp_bidding_banner);

        /*****************************************
         * now perform Ad related code here
         *****************************************/

        // Enable output to Android Logcat (optional)
        SASConfiguration.getSharedInstance().setLoggingEnabled(true);

        // Enable debugging in Webview if available (optional)
        WebView.setWebContentsDebuggingEnabled(true);

        // Initialize SASBannerView
        initBannerView();

        // Create button to manually load the bidding ad
        loadButton = findViewById(R.id.loadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loadBiddingAd();
            }
        });

        // Create button to manually show the banner ad
        showButton = findViewById(R.id.showButton);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBiddingAd();
            }
        });

        // Create Inapp Bidding information TextView
        statusTextView = findViewById(R.id.statusTextView);

        // Configuring the Bidding Manager with appropriate Context, SASAdPlacement, SASBiddingFormatType, Currency and SASBiddingManagerListener.
        biddingManager = new SASBiddingManager(this, BANNER_PLACEMENT, SASBiddingFormatType.BANNER, "USD", this);

        updateUiStatus();
    }

    /**
     * Overriden to clean up SASAdView instances. This must be done to avoid IntentReceiver leak.
     */
    @Override
    protected void onDestroy() {
        bannerView.onDestroy();
        biddingManager.setBiddingManagerListener(null);
        super.onDestroy();
    }

    /**
     * Initialize the SASBannerView instance of this Activity
     */
    private void initBannerView() {
        // Fetch the SASBannerView inflated from the main.xml layout file
        bannerView = this.findViewById(R.id.banner);

        // Add a loader view on the banner. This view covers the banner placement, to indicate progress, whenever the banner is loading an ad.
        // This is optional
        View loader = new SASRotatingImageLoader(this);
        loader.setBackgroundColor(getResources().getColor(R.color.colorLoaderBackground));
        bannerView.setLoaderView(loader);

        // Set the Banner listener
        bannerView.setBannerListener(new SASBannerView.BannerListener() {
            @Override
            public void onBannerAdLoaded(SASBannerView bannerView, SASAdElement adElement) {
                Log.i("Sample", "Banner loading completed");
            }

            @Override
            public void onBannerAdFailedToLoad(SASBannerView bannerView, Exception e) {
                Log.i("Sample", "Banner loading failed: " + e.getMessage());
            }

            @Override
            public void onBannerAdClicked(SASBannerView bannerView) {
                Log.i("Sample", "Banner was clicked");
            }

            @Override
            public void onBannerAdExpanded(SASBannerView bannerView) {
                Log.i("Sample", "Banner was expanded");
            }

            @Override
            public void onBannerAdCollapsed(SASBannerView bannerView) {
                Log.i("Sample", "Banner was collapsed");
            }

            @Override
            public void onBannerAdResized(SASBannerView bannerView) {
                Log.i("Sample", "Banner was resized");
            }

            @Override
            public void onBannerAdClosed(SASBannerView bannerView) {
                Log.i("Sample", "Banner was closed");
            }

            @Override
            public void onBannerAdVideoEvent(SASBannerView bannerView, int videoEvent) {
                Log.i("Sample", "Video event " + videoEvent + " was triggered on Banner");
            }
        });
    }

    private void loadBiddingAd() {
        if (!isBiddingManagerLoading) {
            isBiddingManagerLoading = true;

            // Reset banner and adResponse
            bannerView.reset();
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
            // We use our banner to display the bidding response retrieved earlier.
            //
            // Note: in an actual application, you would load Smart bidding ad response only if it
            // is better than responses you got from other third party SDKs.
            //
            // You can check the CPM associated with the bidding ad response by checking:
            // - biddingAdResponse.getBiddingAdPrice().getCpm()
            // - biddingAdResponse.getBiddingAdPrice().getCurrency()
            //
            // If you decide not to use the bidding ad response, you can simply discard it.
            bannerView.loadAd(biddingAdResponse);
            showButton.setEnabled(false);
        }
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

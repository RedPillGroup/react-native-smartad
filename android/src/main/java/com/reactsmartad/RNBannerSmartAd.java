package com.reactsmartad;

import android.util.Log;
import android.os.Looper;
import android.os.Handler;
import androidx.annotation.Nullable;
import java.util.Map;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.annotations.ReactProp;

import com.smartadserver.android.library.model.SASAdElement;
import com.smartadserver.android.library.model.SASAdPlacement;
import com.smartadserver.android.library.ui.SASBannerView;
import com.smartadserver.android.library.ui.SASRotatingImageLoader;
import com.smartadserver.android.library.util.SASConfiguration;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class RNBannerSmartAd extends SimpleViewManager<SASBannerView> {

  public static final String REACT_CLASS = "RNBannerSmartAd";
  SASBannerView mBannerView;
  private int SITE_ID = 0;
  private String PAGE_ID = null;
  private int FORMAT_ID = 0;
  private String TARGET = null;
  // Handler class to be notified of ad loading outcome
  SASBannerView.BannerListener bannerListener;
  private ThemedReactContext mThemedReactContext;
  private RCTEventEmitter mEventEmitter;

   public enum Events {
    EVENT_LOAD_BANNER("onBannerLoad"),
    EVENT_FAIL_TO_LOAD("onFailToLoad");

    private final String mName;

    Events(final String name) {
      mName = name;
    }

    @Override
    public String toString() {
      return mName;
    }
  }

  @Override
  public String getName() {
    return REACT_CLASS;
  }
  
  @Override
  protected SASBannerView createViewInstance(ThemedReactContext themedReactContext) {
    mThemedReactContext = themedReactContext;
    mEventEmitter = themedReactContext.getJSModule(RCTEventEmitter.class);
    mBannerView = new SASBannerView(themedReactContext);
    return mBannerView;
  }


  private void initializeSmart() {
      try {
          SASConfiguration.getSharedInstance().configure(this.mThemedReactContext, SITE_ID);
      } catch (SASConfiguration.ConfigurationException e) {
          Log.w("Sample", "Smart SDK configuration failed: " + e.getMessage());
      }
      new Handler(Looper.getMainLooper()).post(new Runnable() {
          @Override
          public void run() {
              Log.i("Sample", "Init banner");
              initBannerView();
          }
      });
    }

    /**
     * Initialize the SASBannerView instance of this Activity
     */
    private void initBannerView() {
        bannerListener = new SASBannerView.BannerListener() {
            @Override
            public void onBannerAdLoaded(SASBannerView bannerView, SASAdElement adElement) {
                Log.i("Sample", "Banner loading completed");
                mEventEmitter.receiveEvent(mBannerView.getId(), Events.EVENT_LOAD_BANNER.toString(), null);
            }

            @Override
            public void onBannerAdFailedToLoad(SASBannerView bannerView, Exception e) {
              WritableMap event = Arguments.createMap();
              Log.i("Sample", "Banner loading failed: " + e.getMessage());
              event.putString("error", e.getMessage());
              mEventEmitter.receiveEvent(mBannerView.getId(), Events.EVENT_FAIL_TO_LOAD.toString(), event);
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
        };

        mBannerView.setBannerListener(bannerListener);
    }
    @Override
    @Nullable
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
      MapBuilder.Builder<String, Object> builder = MapBuilder.builder();
      for (Events event : Events.values()) {
        builder.put(event.toString(), MapBuilder.of("registrationName", event.toString()));
      }
      return builder.build();
    }
    @ReactProp(name = "siteId")
    public void setSiteId(final SASBannerView view, final int siteId) {
      this.SITE_ID = siteId;
      initializeSmart();
    }

    @ReactProp(name = "pageId")
    public void setPageId(final SASBannerView view, final String pageId) {
      this.PAGE_ID = pageId;
    }

    @ReactProp(name = "formatId")
    public void setFormatId(final SASBannerView view, final int formatId) {
      this.FORMAT_ID = formatId;
    }
    
    @ReactProp(name = "target")
    public void setTarget(final SASBannerView view, final String target) {
      this.TARGET = target;
      loadBannerAd();
    }

    private void loadBannerAd() {
        SASAdPlacement adPlacement = new SASAdPlacement(SITE_ID,
                PAGE_ID,
                FORMAT_ID,
                TARGET
            );

        // Load banner ad with appropriate parameters (siteID,pageID,formatID,master,targeting,adResponseHandler)
        mBannerView.loadAd(adPlacement);
    }
}
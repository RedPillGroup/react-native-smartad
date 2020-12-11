package com.reactlibrary; // replace com.your-app-name with your app’s name
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import java.util.Map;
import java.util.HashMap;

public class Banner extends ReactContextBaseJavaModule {
    ReactContext context;
    BannerActivity mBanner;
    Banner(ReactApplicationContext context) {
       super(context);
       this.context = context;
    }
    @Override
    public String getName() {
        return "Banner";
    }

    @ReactMethod
    public void initialize(int SITE_ID) {
        inflate(this.context, R.layout.activity_banner, this);
        Log.i("Inflated XML", "ANDROID_SAMPLE_UI");
        mBanner = new BannerActivity(SITE_ID);
    }

    @ReactMethod
    public void loadBannerAd(int SITE_ID,String PAGE_ID, int FORMAT_ID,String TARGET) {
        mBanner.loadBannerAd(SITE_ID,PAGE_ID,FORMAT_ID,TARGET);
    }
}
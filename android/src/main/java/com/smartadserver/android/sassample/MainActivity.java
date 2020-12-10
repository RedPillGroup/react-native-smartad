package com.smartadserver.android.sassample;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.content.Intent;

import com.smartadserver.android.library.util.SASConfiguration;

public class MainActivity extends AppCompatActivity {

    static private final int SITE_ID = 1337; // Your SITE_ID

    private ListView mListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // First of all, configure the SDK
        try {
            SASConfiguration.getSharedInstance().configure(this, SITE_ID);
        } catch (SASConfiguration.ConfigurationException e) {
            Log.w("Sample", "Smart SDK configuration failed: " + e.getMessage());
        }

        //Set Title
        setTitle(R.string.title_activity_main);

        //Prepare listView
        mListView = createListView();

        //Setup clickListener
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 1:
                        startBannerActivity();
                        break;
                    case 2:
                        startInAppBiddingBannerActivity();
                        break;
                    case 3:
                        startInterstitialActivity();
                        break;
                    case 4:
                        startInAppBiddingInterstitialActivity();
                        break;
                    case 5:
                        startRewardedVideoActivity();
                        break;
                    case 6:
                        startRecyclerActivity();
                        break;
                    case 7:
                        startCoordinatorLayoutActivity();
                        break;
                    case 8:
                        startNativeActivity(false);
                        break;
                    case 9:
                        startNativeActivity(true);
                        break;

                    default:
                        break;
                }
            }

        });

        /**
         * TCF Consent String v2 manual setting.
         *
         * By uncommenting the following code, you will set the TCF consent string v2 manually.
         * Note: the Smart Display SDK will retrieve the TCF consent string from the SharedPreferences using the official IAB key "IABTCF_TCString".
         *
         * If you are using a CMP that does not store the consent string in the SharedPreferences using the official
         * IAB key, please store it yourself with the official key.
         */
        // SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        // SharedPreferences.Editor editor = prefs.edit();
        // editor.putString("IABTCF_TCString", "YourTCFConsentString");
        // editor.apply();

        /**
         * TCF Binary Consent Status manual setting.
         *
         * Some third party mediation SDKs are not IAB compliant concerning the TCF consent string. Those SDK use
         * most of the time a binary consent for the advertising purpose.
         * If you are using one or more of those SDK through Smart mediation, you can set this binary consent for
         * all adapters at once by setting the string '1' (if the consent is granted) or '0' (if the consent is denied)
         * in the SharedPreferences for the key 'Smart_advertisingConsentStatus'.
         */
        // SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        // SharedPreferences.Editor editor = prefs.edit();
        // editor.putString("Smart_advertisingConsentStatus", "1" or "0");
        // editor.apply();

        /**
         * CCPA Consent String manual setting.
         *
         * By uncommenting the following code, you will set the CCPA consent string manually.
         * Note: The Smart Display SDK will retrieve the CCPA consent string from the SharedPreferences using the official IAB key "IABUSPrivacy_String".
         *
         * If you are using a CMP that does not store the consent string in the SharedPreferences using the official
         * IAB key, please store it yourself with the official key.
         */
        // SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        // SharedPreferences.Editor editor = prefs.edit();
        // editor.putString("IABUSPrivacy_String", "YourCCPAConsentString");
        // editor.apply();
    }

    //////////////////////////
    // ListView Init
    //////////////////////////

    private ListView createListView() {

        //Create data to display
        Resources res = getResources();
        String[] itemList = res.getStringArray(R.array.activity_main_implementations_array);

        //Find listView and set Adapter
        ListView listView = (ListView) findViewById(R.id.list_view);

        //Create adapter
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, itemList);

        //Create header and footer
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.activity_main_header, mListView, false);
        ViewGroup footer = (ViewGroup) inflater.inflate(R.layout.activity_main_footer, mListView, false);

        //Add Header and footer
        listView.addHeaderView(header, null, false);
        listView.addFooterView(footer, null, false);

        //Set ListView Adapter
        listView.setAdapter(adapter);

        return listView;
    }

    //////////////////////////
    // Starting Activities
    //////////////////////////

    private void startBannerActivity() {
        Intent intent = new Intent(this, BannerActivity.class);
        startActivity(intent);
    }

    private void startInterstitialActivity() {
        Intent intent = new Intent(this, InterstitialActivity.class);
        startActivity(intent);
    }

    private void startRewardedVideoActivity() {
        Intent intent = new Intent(this, RewardedVideoActivity.class);
        startActivity(intent);
    }

    private void startInAppBiddingBannerActivity() {
        Intent intent = new Intent(this, InAppBiddingBannerActivity.class);
        startActivity(intent);
    }

    private void startInAppBiddingInterstitialActivity() {
        Intent intent = new Intent(this, InAppBiddingInterstitialActivity.class);
        startActivity(intent);
    }

    private void startRecyclerActivity() {
        Intent intent = new Intent(this, RecyclerActivity.class);
        startActivity(intent);
    }

    private void startCoordinatorLayoutActivity() {
        Intent intent = new Intent(this, CoordinatorLayoutActivity.class);
        startActivity(intent);
    }

    private void startNativeActivity(boolean withMedia) {
        Intent intent = new Intent(this, NativeActivity.class);

        if (withMedia) {
            intent.putExtra("withMedia", true);
        }

        startActivity(intent);
    }


}

package com.vpnmastersm.singaporevpn.homeui.main;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.startapp.sdk.adsbase.StartAppAd;
import com.vpnmastersm.singaporevpn.adapter.ServerLocation;
import com.bumptech.glide.Glide;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.InterstitialAd;
import com.vpnmastersm.singaporevpn.R;
import com.vpnmastersm.singaporevpn.VPN_Utils;
import com.vpnmastersm.singaporevpn.adapter.ServerCountryAdapter;
import com.vpnmastersm.singaporevpn.adscontrol.AdController;
import com.vpnmastersm.singaporevpn.autoconfig.AutoConfigVPN;
import com.vpnmastersm.singaporevpn.vpnhelper.CountryPrefs;
import com.vpnmastersm.singaporevpn.vpnhelper.VPNServerHelper;


import java.util.Arrays;
import java.util.List;

import de.blinkt.openvpn.core.ConnectionStatus;
import de.blinkt.openvpn.core.VpnStatus;

import static android.content.ContentValues.TAG;

public class Frag_Home_Activity extends Fragment implements VpnStatus.StateListener, ServerCountryAdapter.LocationClickListener {

    private AutoConfigVPN mConfig;
    private TextView connection_tv, taplocation,connection_tv2;
    private ServerCountryAdapter scadapter;
    private AdController controller;
    public static final String MyPREFERENCES = "MyPrefs" ;
    private ImageView background_cornerview;
    private CardView cv_connect_btn, cv_location,connection_status1_btn;
    private boolean isConnected = false;
    private LinearLayout banner_container;

    private Handler handler = new Handler();

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!isConnected) {
                vpnServerHelper.disconnectFromVpn();
                SharedPreferences sharedpreferences = getActivity().getSharedPreferences("VPN", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("ServerName", "Server Disconnected");
                editor.apply();
            }
        }
    };
   // private InterstitialAd interstitialAd;
    private AlertDialog RateAppDialog;
    private AdView adView;
    private InterstitialAd interstitialAd;

    private VPNServerHelper vpnServerHelper;
    private CountryPrefs countryPrefs;
    Dialog dialog;
    Context context;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

       // AdSettings.addTestDevice("328404cebf50ec1fdb05795c0007a8a7");  //You need to comment this line
       // AdSettings.setTestMode(true);//You need to comment this line

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home_activity, container, false);

        initView(view);
        List<String> testDeviceIds = Arrays.asList("33BE2250B43518CCDA7DE426D04EE231");
        RequestConfiguration configuration =
                new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        MobileAds.setRequestConfiguration(configuration);


        mConfig = new AutoConfigVPN(getContext());
        controller = new AdController(getActivity());
        vpnServerHelper = new VPNServerHelper(getContext(), mConfig);
        countryPrefs = new CountryPrefs(getContext());
        banner_container = view.findViewById(R.id.banner_container);
        adView = new AdView(this.getContext(), "299399755006063_299399998339372", AdSize.BANNER_HEIGHT_50); //banner code replace IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID
        banner_container.addView(adView);
        adView.loadAd();

        //footerAdView = view.findViewById(R.id.footer_av);
        //controller.loadBannerAds(footerAdView);
        dialog = new Dialog(getContext());
//        interstitialAd = new InterstitialAd(getContext());
//        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
//        loadInterstitial();
//        interstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdClosed() {
////gokull
//                Intent intent = new Intent(getContext(), NativeAdActivity.class);
//                startActivity(intent);
//            }
//        });
        interstitialAd = new InterstitialAd(getContext(), "ca-app-pub-3940256099942544/1033173712"); // interstitial code replce ca-app-pub-3940256099942544/1033173712
// Set listeners for the Interstitial Ad
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e(TAG, "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e(TAG, "Interstitial ad dismissed.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
                //  interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d(TAG, "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d(TAG, "Interstitial ad impression logged!");
            }
        };

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());


        return view;
    }

    private void initView(View view) {


        connection_tv = view.findViewById(R.id.connection_status);
        connection_tv2 = view.findViewById(R.id.connection_status2);


        cv_connect_btn = view.findViewById(R.id.cv_connection_status);
        connection_status1_btn = view.findViewById(R.id.connection_status1);

        cv_location = view.findViewById(R.id.cardview_location);
        background_cornerview = view.findViewById(R.id.background_circle);



        taplocation = view.findViewById(R.id.taplocation);






        connection_status1_btn.setOnClickListener(view1 -> {
            boolean isActive = VpnStatus.isVPNActive();
            if (!isActive) {

//                showLocationSelectDialog();
                String name = VPN_Utils.getRandomFastServer();
                countryPrefs.setSelectedLocation(name.split("\\.")[0]);
                VPNConnect(name);

                String server_name = name.split("\\.")[0];
                SharedPreferences sharedpreferences = getActivity().getSharedPreferences("VPN", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("ServerName", server_name);
                editor.apply();

                Log.e("Gokul error ", "Server name "+ name);
              //  showInterstitial();
//                Intent intent = new Intent(this.getContext(), NativeAdActivity.class);
//                startActivity(intent);

            } else {
                vpnServerHelper.disconnectFromVpn();
                SharedPreferences sharedpreferences = getActivity().getSharedPreferences("VPN", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("ServerName", "Server Disconnected");
                editor.apply();
               // showInterstitial();

////gokull
//                Intent intent = new Intent(this.getContext(), NativeAdActivity.class);
//                startActivity(intent);


            }
            StartAppAd.showAd(getContext());

        });

        cv_location.setOnClickListener(v -> showLocationSelectDialog());


    }

//    private void loadInterstitial() {
//        if (!interstitialAd.isLoading() && !interstitialAd.isLoaded()) {
//            AdRequest adRequest = new AdRequest.Builder().addTestDevice("BAD4BF3E75085088079835BCF34EB526").addTestDevice("206942360A63A907F9B8D906096C537F").addTestDevice("EB798838C864BCDFF7A12729ECCD95B6").build();
//            interstitialAd.loadAd(adRequest);
//        }
//    }
//
//
//    private void showInterstitial() {
//        // Show the ad if it's ready. Otherwise toast request ad.
//        if (interstitialAd != null && interstitialAd.isLoaded()) {
//            interstitialAd.show();
//        } else {
//            loadInterstitial();
//        }
//    }
private void loadInterstitial() {
    AudienceNetworkAds.initialize(getContext());

    //  AdSettings.addTestDevice("328404cebf50ec1fdb05795c0007a8a7");
    // AdSettings.setTestMode(true);// for get test ad in your device

    interstitialAd = new InterstitialAd(getContext(), "299399755006063_299401351672570"); //replace here fb interstiital
// Set listeners for the Interstitial Ad
    InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
        @Override
        public void onInterstitialDisplayed(Ad ad) {
            // Interstitial ad displayed callback
            Log.e(TAG, "Interstitial ad displayed.");
        }

        @Override
        public void onInterstitialDismissed(Ad ad) {
            // Interstitial dismissed callback
            Log.e(TAG, "Interstitial ad dismissed.");
        }

        @Override
        public void onError(Ad ad, AdError adError) {
            // Ad error callback
            Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
        }

        @Override
        public void onAdLoaded(Ad ad) {
            // Interstitial ad is loaded and ready to be displayed
            Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
            // Show the ad
            //  interstitialAd.show();
        }

        @Override
        public void onAdClicked(Ad ad) {
            // Ad clicked callback
            Log.d(TAG, "Interstitial ad clicked!");
        }

        @Override
        public void onLoggingImpression(Ad ad) {
            // Ad impression logged callback
            Log.d(TAG, "Interstitial ad impression logged!");
        }
    };

    interstitialAd.loadAd(
            interstitialAd.buildLoadAdConfig()
                    .withAdListener(interstitialAdListener)
                    .build());

}


    private void showInterstitial() {

        if(interstitialAd == null || !interstitialAd.isAdLoaded()) {
            loadInterstitial();
        }
        // Check if ad is already expired or invalidated, and do not show ad if that is the case. You will not get paid to show an invalidated ad.
        else if(interstitialAd.isAdInvalidated()) {
            // loadInterstitial();
        }
        else {

            interstitialAd.show();
        }

    }


    @Override
    public void onPause() {
        super.onPause();

//        if (footerAdView != null) {
//            footerAdView.pause();
//        }

        release();
    }

    @Override
    public void onResume() {
        super.onResume();

//        if (footerAdView != null) {
//            footerAdView.resume();
//        }


        VpnStatus.addStateListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

//        if (footerAdView != null) {
//            footerAdView.destroy();
//        }
        removeCallbacks();

        release();
    }

    private void removeCallbacks() {
        try {
            handler.removeCallbacks(runnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void release() {
        VpnStatus.removeStateListener(this);
    }


    private void showLocationSelectDialog() {

        dialog.setContentView(R.layout.bottom_sheet);
        scadapter = new ServerCountryAdapter(getContext(), VPN_Utils.getLocatons(), this, dialog);

        RecyclerView recyclerView = dialog.findViewById(R.id.rv_locations);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(scadapter);


        if (dialog.getWindow() != null) {
            dialog.getWindow().addFlags(Window.FEATURE_NO_TITLE);
        }
        dialog.show();

        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }




    @Override
    public void updateState(String state, String logmessage, int localizedResId, final ConnectionStatus level, Intent intent) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String stateMessage = VpnStatus.getLastCleanLogMessage(getContext());
                updateUI(stateMessage, level);
                Log.e("Vidhu","Level UpdateState Called------------"+level);

            }
        });
    }
    private void RateAppDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Rate Us");
        builder.setMessage("Please take a moment to Rate our Application");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.vpnmastersm.uaevpn")));

            }
        });

        builder.setNegativeButton("Later", (dialog, which) -> dialog.dismiss());


        RateAppDialog = builder.create();
        RateAppDialog.show();

    }
    @Override
    public void setConnectedVPN(String uuid) {
////gokull
//        Intent intent = new Intent(this.getContext(), NativeAdActivity.class);
//        startActivity(intent);
    }


    @SuppressLint("ResourceAsColor")
    private void updateUI(String stateMessage, ConnectionStatus level) {

        String selectedLocation = countryPrefs.getSelectedLocation();

        if (level == ConnectionStatus.LEVEL_CONNECTED) {
            cv_connect_btn.setEnabled(true);
            connection_status1_btn.setEnabled(true);
            connection_tv.setTextColor(Color.parseColor("#446e5c"));
            connection_tv.setText(getString(R.string.disconnect));

            connection_tv2.setTextColor(Color.parseColor("#446e5c"));
            connection_tv2.setText(getString(R.string.connected));

            taplocation.setText(String.format("%s To : %s", getString(R.string.connected), selectedLocation));
            SharedPreferences sharedpreferences = getActivity().getSharedPreferences("VPN", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("ServerName", selectedLocation);
            editor.apply();
            showInterstitial(); //Enabledd

            cv_location.setClickable(false);

            Glide.with(getContext()).load(R.drawable.earth_static_green).apply(new RequestOptions()
                    .fitCenter()
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .override(Target.SIZE_ORIGINAL)).into(background_cornerview);

            isConnected = true;


        } else if (level == ConnectionStatus.LEVEL_NOTCONNECTED) {
            connection_tv.setTextColor(Color.parseColor("#ffffff"));
            connection_tv.setText(getString(R.string.connect));
            connection_tv2.setTextColor(Color.parseColor("#E72C30"));
            connection_tv2.setText(getString(R.string.disconnected));
            cv_connect_btn.setEnabled(true);
            connection_status1_btn.setEnabled(true);
            SharedPreferences sharedpreferences = getActivity().getSharedPreferences("VPN", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("ServerName", "Server Disconnected");
            editor.apply();
            Glide.with(getContext()).load(R.drawable.earth_static).apply(new RequestOptions()
                    .fitCenter()
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .override(Target.SIZE_ORIGINAL)).into(background_cornerview);
            taplocation.setText(getString(R.string.tap_region));
            cv_location.setClickable(true);



            showInterstitial(); //Enabled
        } else {
            Glide.with(getContext()).load(R.drawable.earth).apply(new RequestOptions()
                    .fitCenter()
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .override(Target.SIZE_ORIGINAL)).into(background_cornerview);

            cv_connect_btn.setEnabled(true);
            connection_status1_btn.setEnabled(true);
            connection_tv.setTextColor(Color.parseColor("#ffffff"));
            connection_tv.setText("Connecting");

            //connection_tv.setText(stateMessage);
            connection_tv2.setText(stateMessage);

        }
    }


    @Override
    public void onClickItemListener(int position, ServerLocation serverLocation) {

        connecttovpn(position, serverLocation.getName(), VPN_Utils.getRandomServer(serverLocation.getVpnFileName()));
        SharedPreferences sharedpreferences = getActivity().getSharedPreferences("VPN", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("ServerName", serverLocation.getName());
        editor.apply();
       // showInterstitial();
    }


    private void startTimer() {
        removeCallbacks();
        handler.postDelayed(runnable, 10000);
    }


    private void connecttovpn(final int position, final String location, final String locationFileName) {


        countryPrefs.setSelectedLocation(location);
        VPNConnect(locationFileName);


    }


    private void VPNConnect(final String locationFileName) {
        startTimer();
        String server_name = locationFileName.split("\\.")[0];
        SharedPreferences sharedpreferences = getActivity().getSharedPreferences("VPN", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("ServerName", server_name);
        editor.apply();
        boolean value = vpnServerHelper.connectOrDisconnect(locationFileName);
        if(value)
        {
            Log.e("Vidhu","Location servername- TRUE------------"+server_name);

           // showInterstitial();
        }
        else {
            Log.e("Vidhu","Location servername- TRUE------------"+server_name);

        }
    }

}

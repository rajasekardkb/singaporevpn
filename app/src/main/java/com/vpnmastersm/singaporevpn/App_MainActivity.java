package com.vpnmastersm.singaporevpn;

import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;

import de.blinkt.openvpn.core.ICSOpenVPNApplication;

public class App_MainActivity extends ICSOpenVPNApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        StartAppSDK.init(this, "206196663", true);
        StartAppAd.disableSplash();
    }
}

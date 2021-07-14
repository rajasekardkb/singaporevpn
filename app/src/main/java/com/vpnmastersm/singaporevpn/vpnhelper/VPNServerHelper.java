package com.vpnmastersm.singaporevpn.vpnhelper;

import android.content.Context;
import android.util.Log;

import com.vpnmastersm.singaporevpn.autoconfig.AutoConfigVPN;

import de.blinkt.openvpn.OpenVpnConnector;
import de.blinkt.openvpn.core.VpnStatus;


public class VPNServerHelper {

    private AutoConfigVPN config;
    private Context context;

    public VPNServerHelper(Context context, AutoConfigVPN config) {
        this.context = context;
        this.config = config;
    }

    public boolean connectOrDisconnect(String locationFileName) {
        boolean isActive = VpnStatus.isVPNActive();
        if (isActive) {
            Log.e("Vidhu","Location File Name- Disconnected------------"+locationFileName);

            disconnectFromVpn();
            return true;
        } else {
            Log.e("Vidhu","Location File Name- Connected------------"+locationFileName);

            connectToVpn(locationFileName);
            return true;
        }
    }

    public void connectToVpn(String locationFileName) {
        try {
            String autoConfig = config.getAutoConfig(locationFileName);
            OpenVpnConnector.connectToVpn(context, autoConfig, null, null);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void disconnectFromVpn() {
        OpenVpnConnector.disconnectFromVpn(context);
    }

}

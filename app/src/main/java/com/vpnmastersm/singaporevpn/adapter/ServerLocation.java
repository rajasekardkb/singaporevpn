package com.vpnmastersm.singaporevpn.adapter;

public class ServerLocation {
    private String name;
    private String[] vpnFileName;



    public int getCountryflag() {
        return countryflag;
    }

    public void setCountryflag(int countryflag) {
        this.countryflag = countryflag;
    }

    private int countryflag;

    public ServerLocation(String name, String[] vpnFileName, int countryflag, boolean isReward) {
        this.name = name;
        this.vpnFileName = vpnFileName;
        this.countryflag = countryflag;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getVpnFileName() {
        return vpnFileName;
    }



}

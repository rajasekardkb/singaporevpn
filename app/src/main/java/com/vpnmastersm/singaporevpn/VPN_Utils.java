package com.vpnmastersm.singaporevpn;

import com.vpnmastersm.singaporevpn.adapter.ServerLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VPN_Utils {

    public static List<ServerLocation> getLocatons() {
        // Spinner Drop down elements
        List<ServerLocation> countries = new ArrayList<ServerLocation>();


        countries.add(new ServerLocation("Australia", new String[]{"sydney2.ovpn","sydney3.ovpn"}, R.drawable.ic_australia, false));
        countries.add(new ServerLocation("Canada", new String[]{"canada2.ovpn","canada3.ovpn"}, R.drawable.ic_canada, false));
        countries.add(new ServerLocation("England", new String[]{"england2.ovpn","england3.ovpn"}, R.drawable.ic_united_kingdom, false));
        countries.add(new ServerLocation("Japan", new String[]{"japan2.ovpn","japan3.ovpn"}, R.drawable.ic_japan, false));
        countries.add(new ServerLocation("Singapore ", new String[]{"singapore1.ovpn","singapore1.ovpn"}, R.drawable.ic_singapore, false));
        countries.add(new ServerLocation("Singapore", new String[]{"singapore2.ovpn","singapore3.ovpn"}, R.drawable.ic_singapore, false));
        countries.add(new ServerLocation("South Korea", new String[]{"korea1.ovpn","korea1.ovpn"}, R.drawable.ic_south_korea, false));
        countries.add(new ServerLocation("South Korea", new String[]{"korea2.ovpn","korea3.ovpn"}, R.drawable.ic_south_korea, false));
        countries.add(new ServerLocation("Germany", new String[]{"germany2.ovpn","germany3.ovpn"}, R.drawable.ic_germany, false));
        countries.add(new ServerLocation("India", new String[]{"india2.ovpn","india3.ovpn"}, R.drawable.ic_india, false));
        countries.add(new ServerLocation("Ireland", new String[]{"ireland1.ovpn","ireland2.ovpn"}, R.drawable.ic_ireland, false));
        countries.add(new ServerLocation("United States ", new String[]{"usa1.ovpn","usa2.ovpn"}, R.drawable.ic_united_states_of_america, false));





        return countries;

    }

    public static String getRandomFastServer() {
        String[] servers = new String[]{"singapore1.ovpn","korea1.ovpn","usa1.ovpn",};
        Random random = new Random();
        return servers[random.nextInt(servers.length)];
    }



    public static String getRandomServer(String[] servers) {
        Random random = new Random();
        return servers[random.nextInt(servers.length)];
    }


}

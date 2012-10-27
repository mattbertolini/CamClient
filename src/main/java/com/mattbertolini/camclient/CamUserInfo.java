package com.mattbertolini.camclient;

import java.net.InetAddress;

/**
 * @author Matt Bertolini
 */
public interface CamUserInfo {
    InetAddress getIpAddress();
    MacAddress getMacAddress();
    String getName();
    String getProvider();
    String getRole();
    String getOriginalRole();
    String getVlan();
    String getNewVlan();
    String getOperatingSystem();
}

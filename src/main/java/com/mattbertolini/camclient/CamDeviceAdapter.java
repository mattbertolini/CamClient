package com.mattbertolini.camclient;

import com.mattbertolini.camclient.response.CamResponse;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Matt Bertolini
 */
public class CamDeviceAdapter {
    public CamDevice buildCamDeviceFromResponse(CamResponse response) {
        List<Map<String,String>> responseData = response.getResponseData();
        Map<String, String> foundData = responseData.get(0);
        String foundStr = foundData.get("found");
        if(Boolean.FALSE == Boolean.valueOf(foundStr)) {
            return null;
        }
        Map<String, String> map = responseData.get(1);
        String macAddressStr = map.get("mac");
        String ipAddressStr = map.get("ip");
        String casAddressStr = map.get("cas");
        String typeStr = map.get("type");
        String role = map.get("role");
        String description = map.get("description");
        MacAddress macAddress = null;
        InetAddress ipAddress = null;
        InetAddress casAddress = null;
        Type type = null;
        try {
            macAddress = MacAddress.valueOf(macAddressStr);
            ipAddress = InetAddress.getByName(ipAddressStr);
            casAddress = InetAddress.getByName(casAddressStr);
            type = Type.fromName(typeStr);
        } catch (UnknownHostException e) {
            //
        }
        return new CamDevice(macAddress, ipAddress, casAddress, type, role, description);
    }

    public List<CamDevice> buildCamDeviceListFromResponse(CamResponse response) {
        List<CamDevice> retList = new ArrayList<CamDevice>();
        List<Map<String, String>> responseData = response.getResponseData();
        for(Map<String, String> record : responseData) {
            String macAddressStr = record.get("mac");
            String ipAddressStr = record.get("ip");
            String casAddressStr = record.get("cas");
            String typeStr = record.get("type");
            String role = record.get("role");
            String description = record.get("description");
            MacAddress macAddress = null;
            InetAddress ipAddress = null;
            InetAddress casAddress = null;
            Type type = null;
            try {
                macAddress = MacAddress.valueOf(macAddressStr);
                ipAddress = InetAddress.getByName(ipAddressStr);
                casAddress = InetAddress.getByName(casAddressStr);
                type = Type.fromName(typeStr);
            } catch (UnknownHostException e) {
                //
            }
            CamDevice device = new CamDevice(macAddress, ipAddress, casAddress, type, role, description);
            retList.add(device);

        }
        return retList;
    }
}

package com.mattbertolini.camclient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CamDeviceParser {
    private static final String MAC = "mac";
    private static final String IP = "ip";
    private static final String CAS = "cas";
    private static final String TYPE = "type";
    private static final String ROLE = "role";
    private static final String DESCRIPTION = "description";

    public static CamDevice parseMap(Map<String, String> data) {
        if(data == null) {
            throw new IllegalArgumentException("data cannot be null.");
        }
        String mac = data.get(MAC);
        String ip = data.get(IP);
        String cas = data.get(CAS);
        Type type = Type.fromName(data.get(TYPE));
        String role = data.get(ROLE);
        String description = data.get(DESCRIPTION);
        return new CamDevice(mac, ip, cas, type, role, description);
    }

    public static List<CamDevice> parseList(List<Map<String, String>> data) {
        List<CamDevice> retList = new ArrayList<CamDevice>();
        for(Map<String, String> row : data) {
            CamDevice device = parseMap(row);
            retList.add(device);
        }
        return retList;
    }
}

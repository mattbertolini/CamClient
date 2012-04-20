package com.mattbertolini.camclient;

import com.mattbertolini.camclient.net.CamConnection;
import com.mattbertolini.camclient.response.CamResponse;

import java.util.List;

public class CamClientImpl implements CamClient {
    private CamConnection conn;

    public CamClientImpl() {
        //
    }

    public CamClientImpl(CamConnection conn) {
        this.conn = conn;
    }

    @Override
    public void addCleanMacAddress(String macAddress) throws CamClientException {
        this.addCleanMacAddress(macAddress, null);
    }

    @Override
    public void addCleanMacAddress(String macAddress, String ssip) throws CamClientException {
        //
        if(ssip != null) {
            //
        }
        CamResponse response = this.conn.submitRequest(null);
        if(response.isError()) {
            throw new CamClientException("");
        }
    }

    @Override
    public void addLocalUser(String username, String password, String role) throws CamClientException {
        //
    }

    @Override
    public void addMacAddress(String macAddress) throws CamClientException {
        this.addMacAddress(macAddress, null, null, null, null, null);
    }

    @Override
    public void addMacAddress(String macAddress, String ipAddress, Type type, String role, String description, String ssip) throws CamClientException {
        //
        if(macAddress == null) {
            throw new IllegalArgumentException("MAC address cannot be null.");
        }
        //
        if(ipAddress != null) {
            //
        }
        if(type != null) {
        }
        CamResponse response = this.conn.submitRequest(null);
        if(response.isError()) {
            // throw new CamClientException();
        }
    }

    @Override
    public void addSubnet(String subnet, String mask) throws CamClientException {
        //
    }

    @Override
    public void addSubnet(String subnet, String mask, Type type, String role, String description, String ssip) throws CamClientException {
        //
    }

    @Override
    public void bouncePort(String switchId, int port) throws CamClientException {
        //
    }

    @Override
    public void bouncePortByMacAddress(String macAddress) throws CamClientException {
        //
    }

    @Override
    public void changeLoggedInUserRole(String ipAddress, String role) throws CamClientException {
        //
    }

    @Override
    public void changeUserRole(String ipAddress, String role) throws CamClientException {
        //
    }

    @Override
    public CamDevice checkMacAddress(String macAddress) throws CamClientException {
        return null;  //
    }

    @Override
    public CamDevice checkMacAddress(String macAddress, String ssip) throws CamClientException {
        return null;  //
    }

    @Override
    public void clearCertifiedList() throws CamClientException {
        //
    }

    @Override
    public void deleteAllLocalUsers() throws CamClientException {
        //
    }

    @Override
    public void deleteLocalUser(String username) throws CamClientException {
        //
    }

    @Override
    public String getCamVersion() throws CamClientException {
        return null;  //
    }

    @Override
    public List<CamLocalUser> getLocalUserList() throws CamClientException {
        return null;  //
    }

    @Override
    public List<CamDevice> getMacAddressList() throws CamClientException {
        return null;  //
    }

    @Override
    public String getUserAgent() {
        return null;  //
    }

    @Override
    public void kickOutOfBandUser(String macAddress) throws CamClientException {
        //
    }

    @Override
    public void kickUser(String ipAddress) throws CamClientException {
        //
    }

    @Override
    public void kickUserByMacAddress(String macAddress) throws CamClientException {
        //
    }

    @Override
    public void removeCleanMacAddress(String macAddress) throws CamClientException {
        //
    }

    @Override
    public void removeCleanMacAddress(String macAddress, String ssip) throws CamClientException {
        //
    }

    @Override
    public void removeMacAddress(String macAddress) throws CamClientException {
        //
    }

    @Override
    public void removeMacAddress(String macAddress, String ssip) throws CamClientException {
        //
    }

    @Override
    public void removeMacAddressList() throws CamClientException {
        //
    }

    @Override
    public void removeSubnet(String subnet, String mask, String ssip) throws CamClientException {
        //
    }

    @Override
    public void renewUserSessionTime(String ipAddress) throws CamClientException {
        //
    }

    @Override
    public void setUserAgent(String userAgent) {
        //
    }

    @Override
    public void updateSubnet(String subnet, String mask) throws CamClientException {
        //
    }

    @Override
    public void updateSubnet(String subnet, String mask, Type type, String role, String description, String ssip) throws CamClientException {
        //
    }
}

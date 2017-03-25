/*
 * Copyright (c) 2013, Matthew Bertolini
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *
 *     * Neither the name of CamClient nor the names of its contributors may be
 *       used to endorse or promote products derived from this software without
 *       specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.mattbertolini.camclient;

import com.mattbertolini.camclient.net.CamConnection;
import com.mattbertolini.camclient.request.CamRequest;
import com.mattbertolini.camclient.request.CamRequestImpl;
import com.mattbertolini.camclient.request.Operation;
import com.mattbertolini.camclient.request.RequestParameter;
import com.mattbertolini.camclient.response.CamResponse;

import java.net.InetAddress;
import java.util.List;
import java.util.Map;

public class CamClientImpl implements CamClient {
    private CamConnection connection;

    public CamClientImpl() {
        //
    }

    public CamClientImpl(CamConnection connection) {
        this.connection = connection;
    }

    @Override
    public void addCleanMacAddress(MacAddress macAddress) throws CamClientException {
        this.addCleanMacAddress(macAddress, null);
    }

    @Override
    public void addCleanMacAddress(MacAddress macAddress, InetAddress ssip) throws CamClientException {
        if(macAddress == null) {
            throw new IllegalArgumentException("MAC address cannot be null.");
        }
        CamRequest request = new CamRequestImpl(Operation.ADD_CLEAN_MAC_ADDRESS);
        request.addParameter(RequestParameter.MAC_ADDRESS, macAddress.toString(MacAddress.Delimiter.NONE));
        if(ssip != null) {
            request.addParameter(RequestParameter.SERVER_IP_ADDRESS, ssip.getHostAddress());
        }
        CamResponse response = this.connection.executeRequest(request);
        if(response.isError()) {
            throw this.createCamClientExceptionFromResponse(response);
        }
    }

    @Override
    public void addLocalUser(String username, String password, String role) throws CamClientException {
        if(username == null) {
            throw new IllegalArgumentException("Username cannot be null.");
        }
        if(password == null) {
            throw new IllegalArgumentException("Password cannot be null.");
        }
        if(role == null) {
            throw new IllegalArgumentException("Role cannot be null.");
        }
        CamRequest request = new CamRequestImpl(Operation.ADD_LOCAL_USER);
        request.addParameter(RequestParameter.USERNAME, username);
        request.addParameter(RequestParameter.USER_PASSWORD, password);
        request.addParameter(RequestParameter.USER_ROLE, role);
        CamResponse response = this.connection.executeRequest(request);
        if(response.isError()) {
            throw this.createCamClientExceptionFromResponse(response);
        }
    }

    @Override
    public void addMacAddress(MacAddress macAddress) throws CamClientException {
        this.addMacAddress(macAddress, null, null, null, null, null);
    }

    @Override
    public void addMacAddress(MacAddress macAddress, InetAddress ipAddress, Type type, String role, String description, InetAddress ssip) throws CamClientException {
        if(macAddress == null) {
            throw new IllegalArgumentException("MAC address cannot be null.");
        }
        CamRequest request = new CamRequestImpl(Operation.ADD_MAC_ADDRESS);
        request.addParameter(RequestParameter.MAC_ADDRESS, macAddress.toString(MacAddress.Delimiter.NONE));
        if(ipAddress != null) {
            request.addParameter(RequestParameter.IP_ADDRESS, ipAddress.getHostAddress());
        }
        if(type != null) {
            request.addParameter(RequestParameter.TYPE, type.getName());
            if((Type.USE_ROLE == type || Type.CHECK == type) && role == null) {
                throw new IllegalArgumentException("Role name must not be null if type is USE_ROLE or CHECK.");
            }
        }
        if(role != null) {
            request.addParameter(RequestParameter.ROLE_NAME, role);
        }
        if(description != null) {
            request.addParameter(RequestParameter.DESCRIPTION, description);
        }
        if(ssip != null) {
            request.addParameter(RequestParameter.SERVER_IP_ADDRESS, ssip.getHostAddress());
        }
        CamResponse response = this.connection.executeRequest(request);
        if(response.isError()) {
            throw this.createCamClientExceptionFromResponse(response);
        }
    }

    @Override
    public void addSubnet(InetAddress subnet, String mask) throws CamClientException {
        this.addSubnet(subnet, mask, null, null, null, null);
    }

    @Override
    public void addSubnet(InetAddress subnet, String mask, Type type, String role, String description, InetAddress ssip) throws CamClientException {
        if(subnet == null) {
            throw new IllegalArgumentException("Subnet cannot be null.");
        }
        if(mask == null) {
            throw new IllegalArgumentException("Subnet mask cannot be null.");
        }
        CamRequest request = new CamRequestImpl(Operation.ADD_SUBNET);
        request.addParameter(RequestParameter.SUBNET, subnet.getHostAddress());
        request.addParameter(RequestParameter.SUBNET_MASK, mask);
        if(type != null) {
            request.addParameter(RequestParameter.TYPE, type.getName());
            if(Type.USE_ROLE == type && role == null) {
                throw new IllegalArgumentException("Role name must not be null if type is USE_ROLE.");
            }
        }
        if(role != null) {
            request.addParameter(RequestParameter.ROLE_NAME, role);
        }
        if(description != null) {
            request.addParameter(RequestParameter.DESCRIPTION, description);
        }
        if(ssip != null) {
            request.addParameter(RequestParameter.SERVER_IP_ADDRESS, ssip.getHostAddress());
        }
        CamResponse response = this.connection.executeRequest(request);
        if(response.isError()) {
            throw this.createCamClientExceptionFromResponse(response);
        }
    }

    @Override
    public void bouncePort(String switchId, int port) throws CamClientException {
        if(switchId == null) {
            throw new IllegalArgumentException("Switch ID cannot be null.");
        }
        if(port < 0) {
            throw new IllegalArgumentException("Port cannot be negative.");
        }
        CamRequest request = new CamRequestImpl(Operation.BOUNCE_PORT);
        request.addParameter(RequestParameter.SWITCH_ID, switchId);
        request.addParameter(RequestParameter.SWITCH_PORT, Integer.toString(port));
        CamResponse response = this.connection.executeRequest(request);
        if(response.isError()) {
            throw this.createCamClientExceptionFromResponse(response);
        }
    }

    @Override
    public void bouncePortByMacAddress(MacAddress macAddress) throws CamClientException {
        if(macAddress == null) {
            throw new IllegalArgumentException("MAC address cannot be null.");
        }
        CamRequest request = new CamRequestImpl(Operation.BOUNCE_PORT_BY_MAC_ADDRESS);
        request.addParameter(RequestParameter.MAC_ADDRESS, macAddress.toString(MacAddress.Delimiter.NONE));
        CamResponse response = this.connection.executeRequest(request);
        if(response.isError()) {
            throw this.createCamClientExceptionFromResponse(response);
        }
    }

    @Override
    public void changeLoggedInUserRole(InetAddress ipAddress, String role) throws CamClientException {
        if(ipAddress == null) {
            throw new IllegalArgumentException("IP Address cannot be null.");
        }
        if(role == null) {
            throw new IllegalArgumentException("Role name cannot be null.");
        }
        CamRequest request = new CamRequestImpl(Operation.CHANGE_LOGGED_IN_USER_ROLE);
        request.addParameter(RequestParameter.IP_ADDRESS, ipAddress.getHostAddress());
        request.addParameter(RequestParameter.ROLE_NAME, role);
        CamResponse response = this.connection.executeRequest(request);
        if(response.isError()) {
            throw this.createCamClientExceptionFromResponse(response);
        }
    }

    @Override
    public void changeUserRole(InetAddress ipAddress, String role) throws CamClientException {
        if(ipAddress == null) {
            throw new IllegalArgumentException("IP Address cannot be null.");
        }
        if(role == null) {
            throw new IllegalArgumentException("Role name cannot be null.");
        }
        CamRequest request = new CamRequestImpl(Operation.CHANGE_USER_ROLE);
        request.addParameter(RequestParameter.IP_ADDRESS, ipAddress.getHostAddress());
        request.addParameter(RequestParameter.ROLE_NAME, role);
        CamResponse response = this.connection.executeRequest(request);
        if(response.isError()) {
            throw this.createCamClientExceptionFromResponse(response);
        }
    }

    @Override
    public CamDevice checkMacAddress(MacAddress macAddress) throws CamClientException {
        return this.checkMacAddress(macAddress, null);
    }

    @Override
    public CamDevice checkMacAddress(MacAddress macAddress, InetAddress ssip) throws CamClientException {
        if(macAddress == null) {
            throw new IllegalArgumentException("MAC address cannot be null.");
        }
        CamRequest request = new CamRequestImpl(Operation.CHECK_MAC_ADDRESS);
        request.addParameter(RequestParameter.MAC_ADDRESS, macAddress.toString(MacAddress.Delimiter.NONE));
        if(ssip != null) {
            request.addParameter(RequestParameter.SERVER_IP_ADDRESS, ssip.getHostAddress());
        }
        CamResponse response = this.connection.executeRequest(request);
        if(response.isError()) {
            throw this.createCamClientExceptionFromResponse(response);
        }
        CamDeviceAdapter adapter = new CamDeviceAdapter();
        return adapter.buildCamDeviceFromResponse(response);
    }

    @Override
    public void clearCertifiedList() throws CamClientException {
        CamRequest request = new CamRequestImpl(Operation.CLEAR_CERTIFIED_LIST);
        CamResponse response = this.connection.executeRequest(request);
        if(response.isError()) {
            throw this.createCamClientExceptionFromResponse(response);
        }
    }

    @Override
    public void deleteAllLocalUsers() throws CamClientException {
        CamRequest request = new CamRequestImpl(Operation.DELETE_LOCAL_USER);
        request.addParameter(RequestParameter.QUERY_TYPE, QueryType.ALL.getValue());
        request.addParameter(RequestParameter.QUERY_VALUE, "");
        CamResponse response = this.connection.executeRequest(request);
        if(response.isError()) {
            throw this.createCamClientExceptionFromResponse(response);
        }
    }

    @Override
    public void deleteLocalUser(String username) throws CamClientException {
        if(username == null) {
            throw new IllegalArgumentException("Username cannot be null.");
        }
        CamRequest request = new CamRequestImpl(Operation.DELETE_LOCAL_USER);
        request.addParameter(RequestParameter.QUERY_TYPE, QueryType.USERNAME.getValue());
        request.addParameter(RequestParameter.QUERY_VALUE, username);
        CamResponse response = this.connection.executeRequest(request);
        if(response.isError()) {
            throw this.createCamClientExceptionFromResponse(response);
        }
    }

    @Override
    public String getCamVersion() throws CamClientException {
        CamRequest request = new CamRequestImpl(Operation.GET_VERSION);
        CamResponse response = this.connection.executeRequest(request);
        if(response.isError()) {
            throw this.createCamClientExceptionFromResponse(response);
        }
        Map<String,String> versionMap = response.getResponseData().get(0);
        return versionMap.get("version");
    }

    @Override
    public List<CamLocalUser> getLocalUserList() throws CamClientException {
        return null;  //
    }

    @Override
    public List<CamDevice> getMacAddressList() throws CamClientException {
        CamRequest request = new CamRequestImpl(Operation.GET_MAC_ADDRESS_LIST);
        CamResponse response = this.connection.executeRequest(request);
        if(response.isError()) {
            throw this.createCamClientExceptionFromResponse(response);
        }
        CamDeviceAdapter adapter = new CamDeviceAdapter();
        return adapter.buildCamDeviceListFromResponse(response);
    }

    @Override
    public List<CamUserInfo> getUserInfo(QueryType queryType, String queryValue) {
        if(queryType == null) {
            throw new IllegalArgumentException("Query type cannot be null.");
        }
        if(QueryType.ALL != queryType && queryValue == null) {
            throw new IllegalArgumentException("Query value can only be null for query type of ALL.");
        }
        CamRequest request = new CamRequestImpl(Operation.GET_USER_INFO);
        request.addParameter(RequestParameter.QUERY_TYPE, queryType.getValue());
        String safeValue;
        if(QueryType.ALL == queryType) {
            safeValue = "";
        } else {
            safeValue = queryValue;
        }
        request.addParameter(RequestParameter.QUERY_VALUE, safeValue);
        CamResponse response = this.connection.executeRequest(request);
        if(response.isError()) {
            throw this.createCamClientExceptionFromResponse(response);
        }
        return null;
    }

    @Override
    public void kickOutOfBandUser(MacAddress macAddress) throws CamClientException {
        if(macAddress == null) {
            throw new IllegalArgumentException("MAC address cannot be null.");
        }
        CamRequest request = new CamRequestImpl(Operation.KICK_OOB_USER);
        request.addParameter(RequestParameter.MAC_ADDRESS, macAddress.toString(MacAddress.Delimiter.NONE));
        CamResponse response = this.connection.executeRequest(request);
        if(response.isError()) {
            throw this.createCamClientExceptionFromResponse(response);
        }
    }

    @Override
    public void kickUser(InetAddress ipAddress) throws CamClientException {
        if(ipAddress == null) {
            throw new IllegalArgumentException("IP address cannot be null.");
        }
        CamRequest request = new CamRequestImpl(Operation.KICK_OOB_USER);
        request.addParameter(RequestParameter.IP_ADDRESS, ipAddress.getHostAddress());
        CamResponse response = this.connection.executeRequest(request);
        if(response.isError()) {
            throw this.createCamClientExceptionFromResponse(response);
        }
    }

    @Override
    public void kickUserByMacAddress(MacAddress macAddress) throws CamClientException {
        if(macAddress == null) {
            throw new IllegalArgumentException("MAC address cannot be null.");
        }
        CamRequest request = new CamRequestImpl(Operation.KICK_USER_BY_MAC_ADDRESS);
        request.addParameter(RequestParameter.MAC_ADDRESS, macAddress.toString(MacAddress.Delimiter.NONE));
        CamResponse response = this.connection.executeRequest(request);
        if(response.isError()) {
            throw this.createCamClientExceptionFromResponse(response);
        }
    }

    @Override
    public void removeCleanMacAddress(MacAddress macAddress) throws CamClientException {
        this.removeCleanMacAddress(macAddress, null);
    }

    @Override
    public void removeCleanMacAddress(MacAddress macAddress, InetAddress ssip) throws CamClientException {
        if(macAddress == null) {
            throw new IllegalArgumentException("MAC address cannot be null.");
        }
        CamRequest request = new CamRequestImpl(Operation.REMOVE_CLEAN_MAC_ADDRESS);
        request.addParameter(RequestParameter.MAC_ADDRESS, macAddress.toString(MacAddress.Delimiter.NONE));
        if(ssip != null) {
            request.addParameter(RequestParameter.SERVER_IP_ADDRESS, ssip.getHostAddress());
        }
        CamResponse response = this.connection.executeRequest(request);
        if(response.isError()) {
            throw this.createCamClientExceptionFromResponse(response);
        }
    }

    @Override
    public void removeMacAddress(MacAddress macAddress) throws CamClientException {
        this.removeMacAddress(macAddress, null);
    }

    @Override
    public void removeMacAddress(MacAddress macAddress, InetAddress ssip) throws CamClientException {
        if(macAddress == null) {
            throw new IllegalArgumentException("MAC address cannot be null.");
        }
        CamRequest request = new CamRequestImpl(Operation.REMOVE_MAC_ADDRESS);
        request.addParameter(RequestParameter.MAC_ADDRESS, macAddress.toString(MacAddress.Delimiter.NONE));
        if(ssip != null) {
            request.addParameter(RequestParameter.SERVER_IP_ADDRESS, ssip.getHostAddress());
        }
        CamResponse response = this.connection.executeRequest(request);
        if(response.isError()) {
            throw this.createCamClientExceptionFromResponse(response);
        }
    }

    @Override
    public void removeMacAddressList() throws CamClientException {
        CamRequest request = new CamRequestImpl(Operation.REMOVE_MAC_ADDRESS_LIST);
        CamResponse response = this.connection.executeRequest(request);
        if(response.isError()) {
            throw this.createCamClientExceptionFromResponse(response);
        }
    }

    @Override
    public void removeSubnet(InetAddress subnet, String mask) throws CamClientException {
        this.removeSubnet(subnet, mask, null);
    }

    @Override
    public void removeSubnet(InetAddress subnet, String mask, InetAddress ssip) throws CamClientException {
        if(subnet == null) {
            throw new IllegalArgumentException("Subnet cannot be null.");
        }
        if(mask == null) {
            throw new IllegalArgumentException("Subnet mask cannot be null");
        }
        CamRequest request = new CamRequestImpl(Operation.REMOVE_SUBNET);
        request.addParameter(RequestParameter.MAC_ADDRESS, subnet.getHostAddress());
        request.addParameter(RequestParameter.SUBNET_MASK, mask);
        if(ssip != null) {
            request.addParameter(RequestParameter.SERVER_IP_ADDRESS, ssip.getHostAddress());
        }
        CamResponse response = this.connection.executeRequest(request);
        if(response.isError()) {
            throw this.createCamClientExceptionFromResponse(response);
        }
    }

    @Override
    public void renewUserSessionTime(InetAddress ipAddress) throws CamClientException {
        if(ipAddress == null) {
            throw new IllegalArgumentException("IP address cannot be null.");
        }
        CamRequest request = new CamRequestImpl(Operation.RENEW_USER_SESSION_TIME);
        request.addParameter(RequestParameter.IP_ADDRESS, ipAddress.getHostAddress());
        CamResponse response = this.connection.executeRequest(request);
        if(response.isError()) {
            throw this.createCamClientExceptionFromResponse(response);
        }
    }

    @Override
    public void updateSubnet(InetAddress subnet, String mask) throws CamClientException {
        this.updateSubnet(subnet, mask, null, null, null, null);
    }

    @Override
    public void updateSubnet(InetAddress subnet, String mask, Type type, String role, String description, InetAddress ssip) throws CamClientException {
        if(subnet == null) {
            throw new IllegalArgumentException("Subnet cannot be null.");
        }
        if(mask == null) {
            throw new IllegalArgumentException("Subnet mask cannot be null.");
        }
        CamRequest request = new CamRequestImpl(Operation.UPDATE_SUBNET);
        request.addParameter(RequestParameter.SUBNET, subnet.getHostAddress());
        request.addParameter(RequestParameter.SUBNET_MASK, mask);
        if(type != null) {
            request.addParameter(RequestParameter.TYPE, type.getName());
            if(Type.USE_ROLE == type && role == null) {
                throw new IllegalArgumentException("Role name must not be null if type is USE_ROLE.");
            }
        }
        if(role != null) {
            request.addParameter(RequestParameter.ROLE_NAME, role);
        }
        if(description != null) {
            request.addParameter(RequestParameter.DESCRIPTION, description);
        }
        if(ssip != null) {
            request.addParameter(RequestParameter.SERVER_IP_ADDRESS, ssip.getHostAddress());
        }
        CamResponse response = this.connection.executeRequest(request);
        if(response.isError()) {
            throw this.createCamClientExceptionFromResponse(response);
        }
    }

    public void setCamConnection(CamConnection connection) {
        this.connection = connection;
    }

    private CamClientException createCamClientExceptionFromResponse(CamResponse response) {
        return new CamClientException(response.getErrorText());
    }
}

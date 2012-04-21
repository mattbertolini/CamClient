/*
 * Copyright (c) 2012, Matthew Bertolini
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
import com.mattbertolini.camclient.request.Operation;
import com.mattbertolini.camclient.request.RequestParameter;
import com.mattbertolini.camclient.response.CamResponse;

import java.net.InetAddress;
import java.util.List;

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
        CamRequest request = new CamRequest(Operation.ADD_CLEAN_MAC_ADDRESS);
        request.addParameter(RequestParameter.MAC_ADDRESS, macAddress.toString(MacAddress.Delimiter.NONE));
        if(ssip != null) {
            request.addParameter(RequestParameter.SERVER_IP_ADDRESS, ssip.getHostAddress());
        }
        CamResponse response = this.connection.submitRequest(request);
        if(response.isError()) {
            throw new CamClientException("");
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
        CamRequest request = new CamRequest(Operation.ADD_LOCAL_USER);
        request.addParameter(RequestParameter.USERNAME, username);
        request.addParameter(RequestParameter.USER_PASSWORD, password);
        request.addParameter(RequestParameter.USER_ROLE, role);
        CamResponse response = this.connection.submitRequest(request);
        if(response.isError()) {
            //
        }
    }

    @Override
    public void addMacAddress(MacAddress macAddress) throws CamClientException {
        this.addMacAddress(macAddress, null, null, null, null, null);
    }

    @Override
    public void addMacAddress(MacAddress macAddress, InetAddress ipAddress, Type type, String role, String description, InetAddress ssip) throws CamClientException {
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
        CamResponse response = this.connection.submitRequest(null);
        if(response.isError()) {
            // throw new CamClientException();
        }
    }

    @Override
    public void addSubnet(String subnet, String mask) throws CamClientException {
        //
    }

    @Override
    public void addSubnet(String subnet, String mask, Type type, String role, String description, InetAddress ssip) throws CamClientException {
        //
    }

    @Override
    public void bouncePort(String switchId, int port) throws CamClientException {
        //
    }

    @Override
    public void bouncePortByMacAddress(MacAddress macAddress) throws CamClientException {
        //
    }

    @Override
    public void changeLoggedInUserRole(InetAddress ipAddress, String role) throws CamClientException {
        //
    }

    @Override
    public void changeUserRole(InetAddress ipAddress, String role) throws CamClientException {
        //
    }

    @Override
    public CamDevice checkMacAddress(MacAddress macAddress) throws CamClientException {
        return null;  //
    }

    @Override
    public CamDevice checkMacAddress(MacAddress macAddress, InetAddress ssip) throws CamClientException {
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
        CamRequest request = new CamRequest(Operation.GET_VERSION);
        CamResponse response = this.connection.submitRequest(request);
        if(response.isError()) {
            throw new CamClientException();
        }
        return null;
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
    public void kickUser(InetAddress ipAddress) throws CamClientException {
        //
    }

    @Override
    public void kickUserByMacAddress(MacAddress macAddress) throws CamClientException {
        //
        if(macAddress == null) {
            throw new IllegalArgumentException("MAC address cannot be null.");
        }
        CamRequest request = new CamRequest(Operation.KICK_USER_BY_MAC_ADDRESS);
        request.addParameter(RequestParameter.MAC_ADDRESS, macAddress.toString(MacAddress.Delimiter.NONE));
        CamResponse response = this.connection.submitRequest(request);
        if(response.isError()) {
            //
        }
    }

    @Override
    public void removeCleanMacAddress(MacAddress macAddress) throws CamClientException {
        //
    }

    @Override
    public void removeCleanMacAddress(MacAddress macAddress, InetAddress ssip) throws CamClientException {
        //
    }

    @Override
    public void removeMacAddress(MacAddress macAddress) throws CamClientException {
        //
    }

    @Override
    public void removeMacAddress(MacAddress macAddress, InetAddress ssip) throws CamClientException {
        //
    }

    @Override
    public void removeMacAddressList() throws CamClientException {
        //
    }

    @Override
    public void removeSubnet(String subnet, String mask, InetAddress ssip) throws CamClientException {
        //
    }

    @Override
    public void renewUserSessionTime(InetAddress ipAddress) throws CamClientException {
        //
    }

    @Override
    public void updateSubnet(String subnet, String mask) throws CamClientException {
        //
    }

    @Override
    public void updateSubnet(String subnet, String mask, Type type, String role, String description, InetAddress ssip) throws CamClientException {
        //
    }

    public void setCamConnection(CamConnection connection) {
        this.connection = connection;
    }
}

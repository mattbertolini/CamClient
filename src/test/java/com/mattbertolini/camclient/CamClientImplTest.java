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
import com.mattbertolini.camclient.request.CamRequestImpl;
import com.mattbertolini.camclient.request.Operation;
import com.mattbertolini.camclient.request.RequestParameter;
import com.mattbertolini.camclient.response.CamResponse;
import com.mattbertolini.camclient.response.CamResponseImpl;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

public class CamClientImplTest {
    private static final String SUCCESS_RESPONSE_STRING = "error=0";
    private static final String ERROR_RESPONSE_STRING = "error=CAM error";

    @Test
    public void testAddCleanMacAddressSuccess() {
        MacAddress macAddress = MacAddress.valueOf("01-23-45-67-89-AB");
        CamRequest expectedRequest = new CamRequestImpl(Operation.ADD_CLEAN_MAC_ADDRESS);
        expectedRequest.addParameter(RequestParameter.MAC_ADDRESS, macAddress.toString(MacAddress.Delimiter.NONE));
        List<Map<String, String>> responseData = Collections.emptyList();
        CamResponse expectedResponse = new CamResponseImpl(SUCCESS_RESPONSE_STRING, responseData, false, null);

        CamConnection mockConnection = mock(CamConnection.class);
        when(mockConnection.submitRequest(expectedRequest)).thenReturn(expectedResponse);
        CamClientImpl client = new CamClientImpl(mockConnection);
        client.addCleanMacAddress(macAddress);
        verify(mockConnection).submitRequest(expectedRequest);
    }

    @Test
    public void testAddCleanMacAddressWithSsipSuccess() throws UnknownHostException {
        MacAddress macAddress = MacAddress.valueOf("01-23-45-67-89-AB");
        InetAddress ssip = InetAddress.getLocalHost();
        CamRequest expectedRequest = new CamRequestImpl(Operation.ADD_CLEAN_MAC_ADDRESS);
        expectedRequest.addParameter(RequestParameter.MAC_ADDRESS, macAddress.toString(MacAddress.Delimiter.NONE));
        expectedRequest.addParameter(RequestParameter.SERVER_IP_ADDRESS, ssip.getHostAddress());
        List<Map<String, String>> responseData = Collections.emptyList();
        CamResponse expectedResponse = new CamResponseImpl(SUCCESS_RESPONSE_STRING, responseData, false, null);

        CamConnection mockConnection = mock(CamConnection.class);
        when(mockConnection.submitRequest(expectedRequest)).thenReturn(expectedResponse);
        CamClientImpl client = new CamClientImpl(mockConnection);
        client.addCleanMacAddress(macAddress, ssip);
        verify(mockConnection).submitRequest(expectedRequest);
    }

    @Test(expected = CamClientException.class)
    public void testAddCleanMacAddressErrorResponse() {
        MacAddress macAddress = MacAddress.valueOf("01-23-45-67-89-AB");
        CamRequest expectedRequest = new CamRequestImpl(Operation.ADD_CLEAN_MAC_ADDRESS);
        expectedRequest.addParameter(RequestParameter.MAC_ADDRESS, macAddress.toString(MacAddress.Delimiter.NONE));
        List<Map<String, String>> responseData = Collections.emptyList();
        CamResponse expectedResponse = new CamResponseImpl(ERROR_RESPONSE_STRING, responseData, true, "Cam Error");

        CamConnection mockConnection = mock(CamConnection.class);
        when(mockConnection.submitRequest(expectedRequest)).thenReturn(expectedResponse);
        CamClientImpl client = new CamClientImpl(mockConnection);
        client.addCleanMacAddress(macAddress);
        verify(mockConnection).submitRequest(expectedRequest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddCleanMacAddressNullMacAddress() {
        CamClientImpl client = new CamClientImpl();
        client.addCleanMacAddress(null);
    }

    @Test
    public void testAddLocalUserSuccess() {
        String username = "username";
        String password = "abcd1234";
        String role = "aRole";
        CamRequest expectedRequest = new CamRequestImpl(Operation.ADD_LOCAL_USER);
        expectedRequest.addParameter(RequestParameter.USERNAME, username);
        expectedRequest.addParameter(RequestParameter.USER_PASSWORD, password);
        expectedRequest.addParameter(RequestParameter.USER_ROLE, role);
        List<Map<String, String>> responseData = Collections.emptyList();
        CamResponse expectedResponse = new CamResponseImpl(SUCCESS_RESPONSE_STRING, responseData, false, null);

        CamConnection mockConnection = mock(CamConnection.class);
        when(mockConnection.submitRequest(expectedRequest)).thenReturn(expectedResponse);
        CamClientImpl client = new CamClientImpl(mockConnection);
        client.addLocalUser(username, password, role);
        verify(mockConnection).submitRequest(expectedRequest);
    }

    @Test(expected = CamClientException.class)
    public void testAddLocalUserErrorResponse() {
        String username = "username";
        String password = "abcd1234";
        String role = "aRole";
        CamRequest expectedRequest = new CamRequestImpl(Operation.ADD_LOCAL_USER);
        expectedRequest.addParameter(RequestParameter.USERNAME, username);
        expectedRequest.addParameter(RequestParameter.USER_PASSWORD, password);
        expectedRequest.addParameter(RequestParameter.USER_ROLE, role);
        List<Map<String, String>> responseData = Collections.emptyList();
        CamResponse expectedResponse = new CamResponseImpl(ERROR_RESPONSE_STRING, responseData, true, "CAM Error");

        CamConnection mockConnection = mock(CamConnection.class);
        when(mockConnection.submitRequest(expectedRequest)).thenReturn(expectedResponse);
        CamClientImpl client = new CamClientImpl(mockConnection);
        client.addLocalUser(username, password, role);
        verify(mockConnection).submitRequest(expectedRequest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddLocalUserNullUsername() {
        String username = null;
        String password = "abcd1234";
        String role = "aRole";
        CamClientImpl client = new CamClientImpl();
        client.addLocalUser(username, password, role);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddLocalUserNullPassword() {
        String username = "username";
        String password = null;
        String role = "aRole";
        CamClientImpl client = new CamClientImpl();
        client.addLocalUser(username, password, role);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddLocalUserNullRole() {
        String username = "username";
        String password = "abcd1234";
        String role = null;
        CamClientImpl client = new CamClientImpl();
        client.addLocalUser(username, password, role);
    }

    @Test
    public void testAddMacAddressSuccess() {
        MacAddress macAddress = MacAddress.valueOf("01-23-45-67-89-AB");
        CamRequest expectedRequest = new CamRequestImpl(Operation.ADD_MAC_ADDRESS);
        expectedRequest.addParameter(RequestParameter.MAC_ADDRESS, macAddress.toString(MacAddress.Delimiter.NONE));
        List<Map<String, String>> responseData = Collections.emptyList();
        CamResponse expectedResponse = new CamResponseImpl(SUCCESS_RESPONSE_STRING, responseData, false, null);

        CamConnection mockConnection = mock(CamConnection.class);
        when(mockConnection.submitRequest(expectedRequest)).thenReturn(expectedResponse);
        CamClientImpl client = new CamClientImpl(mockConnection);
        client.addMacAddress(macAddress);
        verify(mockConnection).submitRequest(expectedRequest);
    }

    @Test(expected = CamClientException.class)
    public void testAddMacAddressErrorResponse() {
        MacAddress macAddress = MacAddress.valueOf("01-23-45-67-89-AB");
        CamRequest expectedRequest = new CamRequestImpl(Operation.ADD_MAC_ADDRESS);
        expectedRequest.addParameter(RequestParameter.MAC_ADDRESS, macAddress.toString(MacAddress.Delimiter.NONE));
        List<Map<String, String>> responseData = Collections.emptyList();
        CamResponse expectedResponse = new CamResponseImpl(ERROR_RESPONSE_STRING, responseData, true, "CAM error");

        CamConnection mockConnection = mock(CamConnection.class);
        when(mockConnection.submitRequest(expectedRequest)).thenReturn(expectedResponse);
        CamClientImpl client = new CamClientImpl(mockConnection);
        client.addMacAddress(macAddress);
        verify(mockConnection).submitRequest(expectedRequest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddMacAddressNullMacAddress() {
        CamClientImpl client = new CamClientImpl();
        client.addMacAddress(null);
    }

    @Test
    public void testAddMacAddressWithIpAddressSuccess() throws UnknownHostException {
        MacAddress macAddress = MacAddress.valueOf("01-23-45-67-89-AB");
        InetAddress ipAddress = InetAddress.getLocalHost();
        CamRequest expectedRequest = new CamRequestImpl(Operation.ADD_MAC_ADDRESS);
        expectedRequest.addParameter(RequestParameter.MAC_ADDRESS, macAddress.toString(MacAddress.Delimiter.NONE));
        expectedRequest.addParameter(RequestParameter.IP_ADDRESS, ipAddress.getHostAddress());
        List<Map<String, String>> responseData = Collections.emptyList();
        CamResponse expectedResponse = new CamResponseImpl(SUCCESS_RESPONSE_STRING, responseData, false, null);

        CamConnection mockConnection = mock(CamConnection.class);
        when(mockConnection.submitRequest(expectedRequest)).thenReturn(expectedResponse);
        CamClientImpl client = new CamClientImpl(mockConnection);
        client.addMacAddress(macAddress, ipAddress, null, null, null, null);
        verify(mockConnection).submitRequest(expectedRequest);
    }

    @Test
    public void testAddMacAddressWithTypeSuccess() {
        MacAddress macAddress = MacAddress.valueOf("01-23-45-67-89-AB");
        Type type = Type.ALLOW;
        CamRequest expectedRequest = new CamRequestImpl(Operation.ADD_MAC_ADDRESS);
        expectedRequest.addParameter(RequestParameter.MAC_ADDRESS, macAddress.toString(MacAddress.Delimiter.NONE));
        expectedRequest.addParameter(RequestParameter.TYPE, type.getName());
        List<Map<String, String>> responseData = Collections.emptyList();
        CamResponse expectedResponse = new CamResponseImpl(SUCCESS_RESPONSE_STRING, responseData, false, null);

        CamConnection mockConnection = mock(CamConnection.class);
        when(mockConnection.submitRequest(expectedRequest)).thenReturn(expectedResponse);
        CamClientImpl client = new CamClientImpl(mockConnection);
        client.addMacAddress(macAddress, null, type, null, null, null);
        verify(mockConnection).submitRequest(expectedRequest);
    }

    @Test
    public void testAddMacAddressWithTypeUseRoleAndRoleNameSuccess() {
        MacAddress macAddress = MacAddress.valueOf("01-23-45-67-89-AB");
        Type type = Type.USE_ROLE;
        String roleName = "a_role_name";
        CamRequest expectedRequest = new CamRequestImpl(Operation.ADD_MAC_ADDRESS);
        expectedRequest.addParameter(RequestParameter.MAC_ADDRESS, macAddress.toString(MacAddress.Delimiter.NONE));
        expectedRequest.addParameter(RequestParameter.TYPE, type.getName());
        expectedRequest.addParameter(RequestParameter.ROLE_NAME, roleName);
        List<Map<String, String>> responseData = Collections.emptyList();
        CamResponse expectedResponse = new CamResponseImpl(SUCCESS_RESPONSE_STRING, responseData, false, null);

        CamConnection mockConnection = mock(CamConnection.class);
        when(mockConnection.submitRequest(expectedRequest)).thenReturn(expectedResponse);
        CamClientImpl client = new CamClientImpl(mockConnection);
        client.addMacAddress(macAddress, null, type, roleName, null, null);
        verify(mockConnection).submitRequest(expectedRequest);
    }

    @Test
    public void testAddMacAddressWithTypeCheckAndRoleNameSuccess() {
        MacAddress macAddress = MacAddress.valueOf("01-23-45-67-89-AB");
        Type type = Type.CHECK;
        String roleName = "a_role_name";
        CamRequest expectedRequest = new CamRequestImpl(Operation.ADD_MAC_ADDRESS);
        expectedRequest.addParameter(RequestParameter.MAC_ADDRESS, macAddress.toString(MacAddress.Delimiter.NONE));
        expectedRequest.addParameter(RequestParameter.TYPE, type.getName());
        expectedRequest.addParameter(RequestParameter.ROLE_NAME, roleName);
        List<Map<String, String>> responseData = Collections.emptyList();
        CamResponse expectedResponse = new CamResponseImpl(SUCCESS_RESPONSE_STRING, responseData, false, null);

        CamConnection mockConnection = mock(CamConnection.class);
        when(mockConnection.submitRequest(expectedRequest)).thenReturn(expectedResponse);
        CamClientImpl client = new CamClientImpl(mockConnection);
        client.addMacAddress(macAddress, null, type, roleName, null, null);
        verify(mockConnection).submitRequest(expectedRequest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddMacAddressWithTypeUseRoleAndNoRoleName() {
        MacAddress macAddress = MacAddress.valueOf("01-23-45-67-89-AB");
        Type type = Type.USE_ROLE;
        CamRequest expectedRequest = new CamRequestImpl(Operation.ADD_MAC_ADDRESS);
        expectedRequest.addParameter(RequestParameter.MAC_ADDRESS, macAddress.toString(MacAddress.Delimiter.NONE));
        expectedRequest.addParameter(RequestParameter.TYPE, type.getName());
        List<Map<String, String>> responseData = Collections.emptyList();
        CamResponse expectedResponse = new CamResponseImpl(SUCCESS_RESPONSE_STRING, responseData, false, null);

        CamConnection mockConnection = mock(CamConnection.class);
        when(mockConnection.submitRequest(expectedRequest)).thenReturn(expectedResponse);
        CamClientImpl client = new CamClientImpl(mockConnection);
        client.addMacAddress(macAddress, null, type, null, null, null);
        verify(mockConnection).submitRequest(expectedRequest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddMacAddressWithTypeCheckAndNoRoleName() {
        MacAddress macAddress = MacAddress.valueOf("01-23-45-67-89-AB");
        Type type = Type.CHECK;
        CamRequest expectedRequest = new CamRequestImpl(Operation.ADD_MAC_ADDRESS);
        expectedRequest.addParameter(RequestParameter.MAC_ADDRESS, macAddress.toString(MacAddress.Delimiter.NONE));
        expectedRequest.addParameter(RequestParameter.TYPE, type.getName());
        List<Map<String, String>> responseData = Collections.emptyList();
        CamResponse expectedResponse = new CamResponseImpl(SUCCESS_RESPONSE_STRING, responseData, false, null);

        CamConnection mockConnection = mock(CamConnection.class);
        when(mockConnection.submitRequest(expectedRequest)).thenReturn(expectedResponse);
        CamClientImpl client = new CamClientImpl(mockConnection);
        client.addMacAddress(macAddress, null, type, null, null, null);
        verify(mockConnection).submitRequest(expectedRequest);
    }

    @Test
    public void testAddMacAddressWithDescriptionSuccess() {
        MacAddress macAddress = MacAddress.valueOf("01-23-45-67-89-AB");
        String description = "example description";
        CamRequest expectedRequest = new CamRequestImpl(Operation.ADD_MAC_ADDRESS);
        expectedRequest.addParameter(RequestParameter.MAC_ADDRESS, macAddress.toString(MacAddress.Delimiter.NONE));
        expectedRequest.addParameter(RequestParameter.DESCRIPTION, description);
        List<Map<String, String>> responseData = Collections.emptyList();
        CamResponse expectedResponse = new CamResponseImpl(SUCCESS_RESPONSE_STRING, responseData, false, null);

        CamConnection mockConnection = mock(CamConnection.class);
        when(mockConnection.submitRequest(expectedRequest)).thenReturn(expectedResponse);
        CamClientImpl client = new CamClientImpl(mockConnection);
        client.addMacAddress(macAddress, null, null, null, description, null);
        verify(mockConnection).submitRequest(expectedRequest);
    }

    @Test
    public void testAddMacAddressWithSsipSuccess() throws UnknownHostException {
        MacAddress macAddress = MacAddress.valueOf("01-23-45-67-89-AB");
        InetAddress ssip = InetAddress.getLocalHost();
        CamRequest expectedRequest = new CamRequestImpl(Operation.ADD_MAC_ADDRESS);
        expectedRequest.addParameter(RequestParameter.MAC_ADDRESS, macAddress.toString(MacAddress.Delimiter.NONE));
        expectedRequest.addParameter(RequestParameter.SERVER_IP_ADDRESS, ssip.getHostAddress());
        List<Map<String, String>> responseData = Collections.emptyList();
        CamResponse expectedResponse = new CamResponseImpl(SUCCESS_RESPONSE_STRING, responseData, false, null);

        CamConnection mockConnection = mock(CamConnection.class);
        when(mockConnection.submitRequest(expectedRequest)).thenReturn(expectedResponse);
        CamClientImpl client = new CamClientImpl(mockConnection);
        client.addMacAddress(macAddress, null, null, null, null, ssip);
        verify(mockConnection).submitRequest(expectedRequest);
    }
}

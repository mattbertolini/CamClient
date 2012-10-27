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

import java.net.InetAddress;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author Matt Bertolini
 */
public interface CamAsyncClient {
    Future<Void> addCleanMacAddressAsync(MacAddress macAddress);
    Future<Void> addCleanMacAddressAsync(MacAddress macAddress, InetAddress ssip);
    Future<Void> addLocalUserAsync(String username, String password, String role);
    Future<Void> addMacAddressAsync(MacAddress macAddress);
    Future<Void> addMacAddressAsync(MacAddress macAddress, InetAddress ipAddress, Type type, String role, String description, InetAddress ssip);
    Future<Void> addSubnetAsync(InetAddress subnet, String mask);
    Future<Void> addSubnetAsync(InetAddress subnet, String mask, Type type, String role, String description, InetAddress ssip);
    Future<Void> bouncePortAsync(String switchId, int port);
    Future<Void> bouncePortByMacAddressAsync(MacAddress macAddress);
    Future<Void> changeLoggedInUserRoleAsync(InetAddress ipAddress, String role);
    Future<Void> changeUserRoleAsync(InetAddress ipAddress, String role) throws CamClientException;
    Future<CamDevice> checkMacAddressAsync(MacAddress macAddress) throws CamClientException;
    Future<CamDevice> checkMacAddressAsync(MacAddress macAddress, InetAddress ssip) throws CamClientException;
    Future<Void> clearCertifiedListAsync() throws CamClientException;
    Future<Void> deleteAllLocalUsersAsync() throws CamClientException;
    Future<Void> deleteLocalUserAsync(String username) throws CamClientException;
    Future<String> getCamVersionAsync() throws CamClientException;
    Future<List<CamLocalUser>> getLocalUserListAsync() throws CamClientException;
    Future<List<CamDevice>> getMacAddressListAsync() throws CamClientException;
    Future<List<CamUserInfo>> getUserInfoAsync(QueryType queryType, String queryValue);
    Future<Void> kickOutOfBandUserAsync(MacAddress macAddress) throws CamClientException;
    Future<Void> kickUserAsync(InetAddress ipAddress) throws CamClientException;
    Future<Void> kickUserByMacAddressAsync(MacAddress macAddress) throws CamClientException;
    Future<Void> removeCleanMacAddressAsync(MacAddress macAddress) throws CamClientException;
    Future<Void> removeCleanMacAddressAsync(MacAddress macAddress, InetAddress ssip) throws CamClientException;
    Future<Void> removeMacAddressAsync(MacAddress macAddress) throws CamClientException;
    Future<Void> removeMacAddressAsync(MacAddress macAddress, InetAddress ssip) throws CamClientException;
    Future<Void> removeMacAddressListAsync() throws CamClientException;
    Future<Void> removeSubnetAsync(InetAddress subnet, String mask) throws CamClientException;
    Future<Void> removeSubnetAsync(InetAddress subnet, String mask, InetAddress ssip) throws CamClientException;
    Future<Void> renewUserSessionTimeAsync(InetAddress ipAddress) throws CamClientException;
    Future<Void> updateSubnetAsync(InetAddress subnet, String mask) throws CamClientException;
    Future<Void> updateSubnetAsync(InetAddress subnet, String mask, Type type, String role, String description, InetAddress ssip) throws CamClientException;
}

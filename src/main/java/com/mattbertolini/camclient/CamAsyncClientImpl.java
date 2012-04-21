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

import java.net.InetAddress;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author Matt Bertolini
 */
public class CamAsyncClientImpl extends CamClientImpl implements CamAsyncClient {
    private ExecutorService executorService;

    public CamAsyncClientImpl() {
        //
    }

    public CamAsyncClientImpl(CamConnection connection, ExecutorService executorService) {
        super(connection);
        this.executorService = executorService;
    }

    @Override
    public Future<Void> addCleanMacAddressAsync(final MacAddress macAddress) {
        return this.executorService.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                addCleanMacAddress(macAddress);
                return null;
            }
        });
    }

    @Override
    public Future<Void> addCleanMacAddressAsync(final MacAddress macAddress, final InetAddress ssip) {
        return this.executorService.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                addCleanMacAddress(macAddress, ssip);
                return null;
            }
        });
    }

    @Override
    public Future<Void> addLocalUserAsync(final String username, final String password, final String role) {
        return this.executorService.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                addLocalUser(username, password, role);
                return null;
            }
        });
    }

    @Override
    public Future<Void> addMacAddressAsync(final MacAddress macAddress) {
        return this.executorService.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                addMacAddress(macAddress);
                return null;
            }
        });
    }

    @Override
    public Future<Void> addMacAddressAsync(final MacAddress macAddress, final InetAddress ipAddress, final Type type, final String role, final String description, final InetAddress ssip) {
        return this.executorService.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                addMacAddress(macAddress, ipAddress, type, role, description, ssip);
                return null;
            }
        });
    }

    @Override
    public Future<Void> addSubnetAsync(final String subnet, final String mask) {
        return this.executorService.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                addSubnet(subnet, mask);
                return null;
            }
        });
    }

    @Override
    public Future<Void> addSubnetAsync(final String subnet, final String mask, final Type type, final String role, final String description, final InetAddress ssip) {
        return this.executorService.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                addSubnet(subnet, mask, type, role, description, ssip);
                return null;
            }
        });
    }

    @Override
    public Future<Void> bouncePortAsync(final String switchId, final int port) {
        return this.executorService.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                bouncePort(switchId, port);
                return null;
            }
        });
    }

    @Override
    public Future<Void> bouncePortByMacAddressAsync(final MacAddress macAddress) {
        return this.executorService.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                bouncePortByMacAddress(macAddress);
                return null;
            }
        });
    }

    @Override
    public Future<Void> changeLoggedInUserRoleAsync(final InetAddress ipAddress, final String role) {
        return this.executorService.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                changeLoggedInUserRole(ipAddress, role);
                return null;
            }
        });
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }
}

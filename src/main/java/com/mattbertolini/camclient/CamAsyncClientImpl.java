package com.mattbertolini.camclient;

import com.mattbertolini.camclient.net.CamConnection;
import com.mattbertolini.camclient.net.urlconnection.HttpConnectionCamConnection;

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

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }
}

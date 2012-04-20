package com.mattbertolini.camclient;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author Matt Bertolini
 */
public class CamAsyncClientImpl extends CamClientImpl implements CamAsyncClient {
    private ExecutorService executorService;

    public CamAsyncClientImpl(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public Future<Void> addCleanMacAddressAsync(final String macAddress) {
        return this.executorService.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                addCleanMacAddress(macAddress);
                return null;
            }
        });
    }

    @Override
    public Future<Void> addCleanMacAddressAsync(final String macAddress, final String ssip) {
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
    public Future<Void> addMacAddressAsync(final String macAddress) {
        return this.executorService.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                addMacAddress(macAddress);
                return null;
            }
        });
    }
}

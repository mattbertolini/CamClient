package com.mattbertolini.camclient;

import java.net.InetAddress;
import java.util.concurrent.Future;

/**
 * @author Matt Bertolini
 */
public interface CamAsyncClient {
    Future<Void> addCleanMacAddressAsync(MacAddress macAddress);
    Future<Void> addCleanMacAddressAsync(MacAddress macAddress, InetAddress ssip);
    Future<Void> addLocalUserAsync(String username, String password, String role);
    Future<Void> addMacAddressAsync(MacAddress macAddress);
}

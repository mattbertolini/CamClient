package com.mattbertolini.camclient;

/**
 * @author Matt Bertolini
 */
public final class BasicCamCredentials implements CamCredentials {
    private final String username;
    private final String password;

    public BasicCamCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String toString() {
        return "BasicCamCredentials: " +
                "username=" + this.username + ", " +
                "password=********";
    }
}

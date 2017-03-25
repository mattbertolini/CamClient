package com.mattbertolini.camclient;

/**
 * @author Matt Bertolini
 */
public enum QueryType {
    IP_ADDRESS("ip"),
    MAC_ADDRESS("mac"),
    USERNAME("name"),
    ALL("all");

    private final String value;

    private QueryType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

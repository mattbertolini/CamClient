package com.mattbertolini.camclient.request;

import com.mattbertolini.camclient.net.Parameter;

public enum RequestParameter implements Parameter {
    ADMIN_USERNAME("admin"),
    ADMIN_PASSWORD("passwd"),
    DESCRIPTION("desc"),
    IP_ADDRESS("ip"),
    LIST("list"),
    MAC_ADDRESS("mac"),
    OPERATION("op"),
    QUERY_TYPE("qtype"),
    QUERY_VALUE("qval"),
    ROLE_NAME("role"),
    SERVER_IP_ADDRESS("ssip"),
    SUBNET("subnet"),
    SUBNET_MASK("mask"),
    SWITCH_ID("switch"),
    SWITCH_PORT("port"),
    TYPE("type"),
    USER_PASSWORD("userpass"),
    USER_ROLE("userrole"),
    USERNAME("username");

    private String name;

    private RequestParameter(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}

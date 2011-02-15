package com.mattbertolini.camclient;

public enum Type {
    ALLOW("allow"),
    CHECK("check"),
    DENY("deny"),
    IGNORE("ignore"),
    USE_ROLE("userole");

    private String name;

    private Type(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static Type fromName(final String name) {
        Type retVal = null;
        for(Type type : Type.values()) {
            if(type.getName().equalsIgnoreCase(name)) {
                retVal = type;
                break;
            }
        }
        return retVal;
    }
}

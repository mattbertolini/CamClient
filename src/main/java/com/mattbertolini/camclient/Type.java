package com.mattbertolini.camclient;

public enum Type {
    /**
     * Value sent to CAM: <code>allow</code>.
     */
    ALLOW("allow"),
    
    /**
     * Value sent to CAM: <code>check</code>.
     */
    CHECK("check"),
    
    /**
     * Value sent to CAM: <code>deny</code>.
     */
    DENY("deny"),
    
    /**
     * Value sent to CAM: <code>ignore</code>.
     */
    IGNORE("ignore"),
    
    /**
     * Value sent to CAM: <code>userole</code>.
     */
    USE_ROLE("userole");

    private String name;

    private Type(String name) {
        this.name = name;
    }

    /**
     * Gets the Clean Access Manager API supported name for the type. This value 
     * is sent to the CAM when making requests.
     * 
     * @return A String containing the type name used in the CAM.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the enum type with the specified CAM name. The name string can be 
     * case insensitive but must not have any extra spaces.
     * 
     * @param name The CAM name to have a Type enum returned.
     * @return The Type enum constant with the specified CAM name or null if a 
     * valid match cannot be found.
     */
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

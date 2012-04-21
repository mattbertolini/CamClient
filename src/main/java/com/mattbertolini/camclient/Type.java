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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Matt Bertolini
 */
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

    private static final Map<String, Type> lookupMap = new HashMap<String, Type>(Type.values().length);

    static {
        for(Type type : Type.values()) {
            lookupMap.put(type.getName(), type);
        }
    }

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
        if(name == null) {
            return null;
        }
        return lookupMap.get(name.toLowerCase());
    }
}

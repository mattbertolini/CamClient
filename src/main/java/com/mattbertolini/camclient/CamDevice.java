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

import java.net.InetAddress;

/**
 * @author Matt Bertolini
 */
public class CamDevice {
    private final MacAddress macAddress;
    private final InetAddress ipAddress;
    private final InetAddress casAddress;
    private final Type type;
    private final String role;
    private final String description;

    public CamDevice(MacAddress macAddress, Type type, String description) {
        this(macAddress, null, null, type, null, description);
    }

    public CamDevice(MacAddress macAddress, InetAddress ipAddress, InetAddress casAddress, Type type, String role, String description) {
        this.macAddress = macAddress;
        this.ipAddress = ipAddress;
        this.casAddress = casAddress;
        this.type = type;
        this.role = role;
        this.description = description;
    }

    public MacAddress getMacAddress() {
        return this.macAddress;
    }

    public InetAddress getIpAddress() {
        return this.ipAddress;
    }

    public InetAddress getCasAddress() {
        return this.casAddress;
    }

    public Type getType() {
        return this.type;
    }

    public String getRole() {
        return this.role;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((casAddress == null) ? 0 : casAddress.hashCode());
        result = prime * result
                + ((description == null) ? 0 : description.hashCode());
        result = prime * result
                + ((ipAddress == null) ? 0 : ipAddress.hashCode());
        result = prime * result
                + ((macAddress == null) ? 0 : macAddress.hashCode());
        result = prime * result + ((role == null) ? 0 : role.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        CamDevice other = (CamDevice) obj;
        if(casAddress == null) {
            if(other.casAddress != null) {
                return false;
            }
        } else if(!casAddress.equals(other.casAddress)) {
            return false;
        }
        if(description == null) {
            if(other.description != null) {
                return false;
            }
        } else if(!description.equals(other.description)) {
            return false;
        }
        if(ipAddress == null) {
            if(other.ipAddress != null) {
                return false;
            }
        } else if(!ipAddress.equals(other.ipAddress)) {
            return false;
        }
        if(macAddress == null) {
            if(other.macAddress != null) {
                return false;
            }
        } else if(!macAddress.equals(other.macAddress)) {
            return false;
        }
        if(role == null) {
            if(other.role != null) {
                return false;
            }
        } else if(!role.equals(other.role)) {
            return false;
        }
        if(type != other.type) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CamDevice [macAddress=" + macAddress + ", ipAddress="
                + ipAddress + ", casAddress=" + casAddress + ", type=" + type
                + ", role=" + role + ", description=" + description + "]";
    }
}

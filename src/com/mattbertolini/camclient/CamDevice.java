package com.mattbertolini.camclient;

public class CamDevice {
    private String macAddress;
    private String ipAddress;
    private String casAddress;
    private Type type;
    private String role;
    private String description;

    public CamDevice(String macAddress, String ipAddress, String casAddress, Type type, String role, String description) {
        this.macAddress = macAddress;
        this.ipAddress = ipAddress;
        this.casAddress = casAddress;
        this.type = type;
        this.role = role;
        this.description = description;
    }

    public String getMacAddress() {
        return this.macAddress;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public String getCasAddress() {
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

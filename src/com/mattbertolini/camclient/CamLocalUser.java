package com.mattbertolini.camclient;

public class CamLocalUser {
    private String name;
    private String role;
    
    public CamLocalUser(String name, String role) {
        this.name = name;
        this.role = role;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getRole() {
        return this.role;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        result = prime * result + ((this.role == null) ? 0 : this.role.hashCode());
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
        CamLocalUser other = (CamLocalUser) obj;
        if(this.name == null) {
            if(other.name != null) {
                return false;
            }
        } else if(!this.name.equals(other.name)) {
            return false;
        }
        if(this.role == null) {
            if(other.role != null) {
                return false;
            }
        } else if(!this.role.equals(other.role)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CamLocalUser [name=" + this.name + ", role=" + this.role + "]";
    }
}

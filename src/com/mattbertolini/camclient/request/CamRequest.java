package com.mattbertolini.camclient.request;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.mattbertolini.camclient.net.Parameter;

/**
 * @author Matt Bertolini
 */
public class CamRequest {
    private final Operation operation;
    private final Map<Parameter, String> parameters = new HashMap<Parameter, String>();
    
    /**
     * Constructs a new CamRequest object with the given Operation.
     * 
     * @param operation The Operation to create the request for.
     */
    public CamRequest(final Operation operation) {
        this.operation = operation;
    }
    
    /**
     * Adds the given Parameter name and value to the request. If the request 
     * already contains the given Parameter name, the old value will be 
     * replaced by the new value.
     * 
     * @param name The Parameter name to add to the request.
     * @param value The value to be associated with the Parameter.
     * @throws NullPointerException If the given Parameter name is null.
     */
    public void addParameter(final Parameter name, final String value) {
        if(name == null) {
            throw new NullPointerException("Parameter name cannot be null.");
        }
        this.parameters.put(name, value);
    }
    
    /**
     * Determines wither the given Parameter is currently set in the request 
     * object.
     * 
     * @param name The Parameter to check.
     * @return True if the given Parameter is currently set and false otherwise.
     */
    public boolean containsParameter(final Parameter name) {
        return this.parameters.containsKey(name);
    }
    
    /**
     * Gets the request operation set on the object when it was constructed.
     * 
     * @return An Operation value.
     */
    public Operation getOperation() {
        return this.operation;
    }
    
    /**
     * Retrieves the request parameters currently set on the CamRequest.
     * 
     * @return A {@link Collections#unmodifiableMap(Map)} keyed on the request 
     * parameter with String values.
     */
    public Map<Parameter, String> getParameters() {
        return Collections.unmodifiableMap(this.parameters);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((operation == null) ? 0 : operation.hashCode());
        result = prime * result
                + ((parameters == null) ? 0 : parameters.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        CamRequest other = (CamRequest) obj;
        if(this.operation == null) {
            if(other.operation != null) {
                return false;
            }
        } else if(!this.operation.equals(other.operation)) {
            return false;
        }
        if(this.parameters == null) {
            if(other.parameters != null) {
                return false;
            }
        } else if(!this.parameters.equals(other.parameters)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CamRequest [operation=" + this.operation + ", parameters="
                + this.parameters + "]";
    }
}

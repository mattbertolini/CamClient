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

package com.mattbertolini.camclient.request;

import com.mattbertolini.camclient.net.Parameter;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Matt Bertolini
 */
public final class CamRequestImpl implements CamRequest {
    private final Operation operation;
    private final Map<Parameter, String> parameters;

    /**
     * Constructs a new CamRequest object with the given Operation.
     *
     * @param operation The Operation to create the request for.
     */
    public CamRequestImpl(final Operation operation) {
        this.operation = operation;
        this.parameters = new LinkedHashMap<Parameter, String>();
    }

    /**
     * Adds the given Parameter name and value to the request. If the request
     * already contains the given Parameter name, the old value will be
     * replaced by the new value.
     *
     * @param name The Parameter name to add to the request.
     * @param value The value to be associated with the Parameter.
     * @throws IllegalArgumentException If the given Parameter name is null.
     */
    @Override
    public void addParameter(final Parameter name, final String value) {
        if(name == null) {
            throw new IllegalArgumentException("Parameter name cannot be null.");
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
    @Override
    public boolean containsParameter(final Parameter name) {
        return this.parameters.containsKey(name);
    }

    /**
     * Gets the request operation set on the object when it was constructed.
     *
     * @return An Operation value.
     */
    @Override
    public Operation getOperation() {
        return this.operation;
    }

    /**
     * Retrieves the request parameters currently set on the CamRequest.
     *
     * @return A {@link java.util.Collections#unmodifiableMap(Map)} keyed on the request
     * parameter with String values.
     */
    @Override
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
        CamRequestImpl other = (CamRequestImpl) obj;
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

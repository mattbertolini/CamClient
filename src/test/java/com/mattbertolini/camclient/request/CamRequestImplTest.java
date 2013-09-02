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
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Matt Bertolini
 */
public class CamRequestImplTest {
    @Test
    public void testContainsParameterTrue() {
        CamRequest req = new CamRequestImpl(Operation.ADD_MAC_ADDRESS);
        req.addParameter(RequestParameter.MAC_ADDRESS, "01:23:45:67:89:AB");
        Assert.assertTrue(req.containsParameter(RequestParameter.MAC_ADDRESS));
    }

    @Test
    public void testContainsParameterFalse() {
        CamRequest req = new CamRequestImpl(Operation.ADD_MAC_ADDRESS);
        req.addParameter(RequestParameter.DESCRIPTION, "Test");
        Assert.assertFalse(req.containsParameter(RequestParameter.MAC_ADDRESS));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddParameterNullName() {
        CamRequest req = new CamRequestImpl(Operation.ADD_MAC_ADDRESS);
        req.addParameter(null, "01:23:45:67:89:AB");
    }

    @Test
    public void testGetOperation() {
        CamRequest req = new CamRequestImpl(Operation.ADD_LOCAL_USER);
        Assert.assertEquals(Operation.ADD_LOCAL_USER, req.getOperation());
    }

    @Test
    public void testGetParameters() {
        Map<Parameter, String> expected = new LinkedHashMap<Parameter, String>();
        expected.put(RequestParameter.USERNAME, "test");
        expected.put(RequestParameter.USER_PASSWORD, "pass");
        expected.put(RequestParameter.USER_ROLE, "role");
        CamRequest req = new CamRequestImpl(Operation.ADD_LOCAL_USER);
        req.addParameter(RequestParameter.USERNAME, "test");
        req.addParameter(RequestParameter.USER_PASSWORD, "pass");
        req.addParameter(RequestParameter.USER_ROLE, "role");
        Assert.assertEquals(expected, req.getParameters());
    }

    @Test
    public void testEqualsAndHashCode() {
        EqualsVerifier.forClass(CamRequestImpl.class).verify();
    }
}

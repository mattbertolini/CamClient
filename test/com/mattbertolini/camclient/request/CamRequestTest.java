package com.mattbertolini.camclient.request;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mattbertolini.camclient.request.CamRequest;
import com.mattbertolini.camclient.request.Operation;
import com.mattbertolini.camclient.request.RequestParameter;
import com.mattbertolini.camclient.response.CamResponse;

public class CamRequestTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testContainsParameterTrue() {
        CamRequest req = new CamRequest(Operation.ADD_MAC_ADDRESS);
        req.addParameter(RequestParameter.MAC_ADDRESS, "01:23:45:67:89:AB");
        Assert.assertTrue(req.containsParameter(RequestParameter.MAC_ADDRESS));
    }

    @Test
    public void testContainsParameterFalse() {
        CamRequest req = new CamRequest(Operation.ADD_MAC_ADDRESS);
        req.addParameter(RequestParameter.DESCRIPTION, "Test");
        Assert.assertFalse(req.containsParameter(RequestParameter.MAC_ADDRESS));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddParameterNullName() {
        CamRequest req = new CamRequest(Operation.ADD_MAC_ADDRESS);
        req.addParameter(null, "01:23:45:67:89:AB");
    }

    @Test
    public void testHashCode() {
        CamRequest req1 = new CamRequest(Operation.ADD_MAC_ADDRESS);
        req1.addParameter(RequestParameter.MAC_ADDRESS, "01:23:45:67:89:AB");
        CamRequest req2 = new CamRequest(Operation.ADD_MAC_ADDRESS);
        req2.addParameter(RequestParameter.MAC_ADDRESS, "01:23:45:67:89:AB");
        Assert.assertEquals(req1.hashCode(), req2.hashCode());
    }

    @Test
    public void testEquals() {
        CamRequest req1 = new CamRequest(Operation.ADD_MAC_ADDRESS);
        req1.addParameter(RequestParameter.MAC_ADDRESS, "01:23:45:67:89:AB");
        CamRequest req2 = new CamRequest(Operation.ADD_MAC_ADDRESS);
        req2.addParameter(RequestParameter.MAC_ADDRESS, "01:23:45:67:89:AB");
        Assert.assertTrue(req1.equals(req2));
    }

    @Test
    public void testEqualsSameObject() {
        CamRequest req1 = new CamRequest(Operation.ADD_MAC_ADDRESS);
        req1.addParameter(RequestParameter.MAC_ADDRESS, "01:23:45:67:89:AB");
        Assert.assertTrue(req1.equals(req1));
    }

    @Test
    public void testNotEqualNull() {
        CamRequest req1 = new CamRequest(Operation.ADD_MAC_ADDRESS);
        req1.addParameter(RequestParameter.MAC_ADDRESS, "01:23:45:67:89:AB");
        Assert.assertFalse(req1.equals(null));
    }

    @Test
    public void testNotEqualDifferentObjects() {
        CamRequest req1 = new CamRequest(Operation.ADD_MAC_ADDRESS);
        req1.addParameter(RequestParameter.MAC_ADDRESS, "01:23:45:67:89:AB");
        CamResponse resp = new CamResponse(null, null, false, null);
        Assert.assertFalse(req1.equals(resp));
    }
}

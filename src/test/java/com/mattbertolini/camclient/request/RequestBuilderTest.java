package com.mattbertolini.camclient.request;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mattbertolini.camclient.request.CamRequest;
import com.mattbertolini.camclient.request.Operation;
import com.mattbertolini.camclient.request.RequestBuilder;
import com.mattbertolini.camclient.request.RequestParameter;

public class RequestBuilderTest {
    private RequestBuilder requestBuilder;

    @Before
    public void setUp() throws Exception {
        this.requestBuilder = new RequestBuilder();
    }

    @After
    public void tearDown() throws Exception {
        //
    }

    @Test
    public void testRequestWithNoParameters() {
        String expected = "op=getversion";
        CamRequest req = new CamRequest(Operation.GET_VERSION);
        String actual = this.requestBuilder.buildRequest(req);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testRequestWithParameters() {
        String expected = "op=addsubnet&subnet=10.210.0.0";
        CamRequest req = new CamRequest(Operation.ADD_SUBNET);
        req.addParameter(RequestParameter.SUBNET, "10.210.0.0");
        String actual = this.requestBuilder.buildRequest(req);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testRequestWithNullParameter() {
        String expected = "op=addsubnet&type=";
        CamRequest req = new CamRequest(Operation.ADD_SUBNET);
        req.addParameter(RequestParameter.TYPE, null);
        String actual = this.requestBuilder.buildRequest(req);
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void testRequestWithEmptyString() {
        String expected = "op=deletelocaluser&qtype=all&qval=";
        CamRequest req = new CamRequest(Operation.DELETE_LOCAL_USER);
        req.addParameter(RequestParameter.QUERY_TYPE, "all");
        req.addParameter(RequestParameter.QUERY_VALUE, "");
        String actual = this.requestBuilder.buildRequest(req);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testRequestWithSpecialEncoding() {
        String expected = "op=addsubnet&desc=This+is+a+test";
        CamRequest req = new CamRequest(Operation.ADD_SUBNET);
        req.addParameter(RequestParameter.DESCRIPTION, "This is a test");
        String actual = this.requestBuilder.buildRequest(req);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testAppendUserNameAndPassword() {
        String expected = "&admin=testuser&passwd=testpass";
        String actual = this.requestBuilder.appendUserNameAndPassword("testuser", "testpass");
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullRequestObject() {
        CamRequest req = null;
        this.requestBuilder.buildRequest(req);
    }

    @Test(expected = IllegalStateException.class)
    public void testNullOperationInRequestObject() {
        CamRequest req = new CamRequest(null);
        this.requestBuilder.buildRequest(req);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullUsername() {
        this.requestBuilder.appendUserNameAndPassword(null, "testpass");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullPassword() {
        this.requestBuilder.appendUserNameAndPassword("testuser", null);
    }
}

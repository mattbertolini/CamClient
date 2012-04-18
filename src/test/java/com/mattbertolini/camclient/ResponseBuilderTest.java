package com.mattbertolini.camclient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

import com.mattbertolini.camclient.net.CamConnectionImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mattbertolini.camclient.net.CamConnectionException;
import com.mattbertolini.camclient.net.MockHttpConnectionService;
import com.mattbertolini.camclient.net.TestParameter;
import com.mattbertolini.camclient.net.UserAgentBuilder;
import com.mattbertolini.camclient.request.CamRequest;
import com.mattbertolini.camclient.request.Operation;
import com.mattbertolini.camclient.request.RequestBuilder;
import com.mattbertolini.camclient.request.RequestParameter;
import com.mattbertolini.camclient.response.CamResponse;
import com.mattbertolini.camclient.response.ResponseBuilder;

public class ResponseBuilderTest {
    private RequestBuilder reqb;
    private ResponseBuilder respb;
    private String userAgent;

    @Before
    public void setUp() throws Exception {
        this.reqb = new RequestBuilder();
        this.respb = new ResponseBuilder();
        this.userAgent = new UserAgentBuilder().buildUserAgentString();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSuccessfulRequestWithNoReturnData() throws MalformedURLException, CamConnectionException {
        MockHttpConnectionService mhcs = new MockHttpConnectionService();
        URL url = new URL("https://example.com/");
        String user = "testuser";
        String pw = "testpass";
        CamConnectionImpl cc = new CamConnectionImpl(mhcs, this.reqb, this.respb, this.userAgent, url, null, user, pw);
        CamRequest req = new CamRequest(Operation.ADD_CLEAN_MAC_ADDRESS);
        req.addParameter(RequestParameter.MAC_ADDRESS, "01:23:45:67:89:AB");
        CamResponse resp = cc.submitRequest(req);
        Assert.assertFalse(resp.isError());
        Assert.assertEquals(Collections.emptyList(), resp.getResponseData());
    }

    @Test
    public void testSuccessfulRequestWithReturnData() throws MalformedURLException, CamConnectionException {
        MockHttpConnectionService mhcs = new MockHttpConnectionService();
        mhcs.addTestParameter(TestParameter.COUNT, "1");
        URL url = new URL("https://example.com/");
        String user = "testuser";
        String pw = "testpass";
        CamConnectionImpl cc = new CamConnectionImpl(mhcs, this.reqb, this.respb, this.userAgent, url, null, user, pw);
        CamRequest req = new CamRequest(Operation.GET_MAC_ADDRESS_LIST);
        CamResponse resp = cc.submitRequest(req);
        Assert.assertFalse(resp.isError());
        Assert.assertEquals(1, resp.getResponseData().size());
    }

    @Test
    public void testSuccessfulRequestWithNoSearchResults() throws MalformedURLException, CamConnectionException {
        MockHttpConnectionService mhcs = new MockHttpConnectionService();
        mhcs.addTestParameter(TestParameter.COUNT, "0");
        URL url = new URL("https://example.com/");
        String user = "testuser";
        String pw = "testpass";
        CamConnectionImpl cc = new CamConnectionImpl(mhcs, this.reqb, this.respb, this.userAgent, url, null, user, pw);
        CamRequest req = new CamRequest(Operation.GET_MAC_ADDRESS_LIST);
        CamResponse resp = cc.submitRequest(req);
        Assert.assertFalse(resp.isError());
        Assert.assertEquals(0, resp.getResponseData().size());
    }

    @Test
    public void testSuccessfulRequestWithErrorResponse() throws MalformedURLException, CamConnectionException {
        MockHttpConnectionService mhcs = new MockHttpConnectionService();
        mhcs.addTestParameter(TestParameter.RETURN_ERROR, Boolean.TRUE.toString());
        URL url = new URL("https://example.com/");
        String user = "testuser";
        String pw = "testpass";
        CamConnectionImpl cc = new CamConnectionImpl(mhcs, this.reqb, this.respb, this.userAgent, url, null, user, pw);
        CamRequest req = new CamRequest(Operation.GET_MAC_ADDRESS_LIST);
        CamResponse resp = cc.submitRequest(req);
        Assert.assertTrue(resp.isError());
        Assert.assertEquals("CAM Error - This is an error", resp.getErrorText());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullResponseString() {
        this.respb.buildResponse(null);
    }
}

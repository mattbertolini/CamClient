package com.mattbertolini.camclient.net;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mattbertolini.camclient.request.CamRequest;
import com.mattbertolini.camclient.request.Operation;
import com.mattbertolini.camclient.request.RequestBuilder;
import com.mattbertolini.camclient.request.RequestParameter;
import com.mattbertolini.camclient.response.CamResponse;
import com.mattbertolini.camclient.response.ResponseBuilder;

public class CamConnectionImplTest {
    private RequestBuilder reqb;
    private ResponseBuilder respb;
    private String userAgent;

    @Before
    public void setUp() {
        this.reqb = new RequestBuilder();
        this.respb = new ResponseBuilder();
        this.userAgent = new UserAgentBuilder().buildUserAgentString();
    }

    @After
    public void tearDown() {
        //
    }

    @Test
    public void testSuccessfulRequest() throws MalformedURLException, CamConnectionException {
        MockHttpConnectionService mhcs = new MockHttpConnectionService();
        URL url = new URL("https://example.com/");
        String user = "testuser";
        String pw = "testpass";
        CamConnectionImpl cc = new CamConnectionImpl(mhcs, this.reqb, this.respb, this.userAgent, url, null, user, pw);
        CamRequest req = new CamRequest(Operation.ADD_CLEAN_MAC_ADDRESS);
        req.addParameter(RequestParameter.MAC_ADDRESS, "01:23:45:67:89:AB");
        CamResponse resp = cc.submitRequest(req);
        Assert.assertNotNull(resp);
        Assert.assertFalse(resp.isError());
    }

    @Test
    public void testCustomUserAgentString() throws MalformedURLException {
        MockHttpConnectionService mhcs = new MockHttpConnectionService();
        URL url = new URL("https://example.com/");
        String user = "testuser";
        String pw = "testpass";
        CamConnectionImpl cc = new CamConnectionImpl(mhcs, this.reqb, this.respb, this.userAgent, url, null, user, pw);
        String expected = "TestUserAgentString";
        cc.setUserAgent(expected);
        String actual = cc.getUserAgent();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCustomTimeout() throws MalformedURLException {
        MockHttpConnectionService mhcs = new MockHttpConnectionService();
        URL url = new URL("https://example.com/");
        String user = "testuser";
        String pw = "testpass";
        CamConnectionImpl cc = new CamConnectionImpl(mhcs, this.reqb, this.respb, this.userAgent, url, null, user, pw);
        int expected = 30000;
        cc.setTimeout(expected);
        int actual = cc.getTimeout();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testToString() throws MalformedURLException {
        MockHttpConnectionService mhcs = new MockHttpConnectionService();
        URL url = new URL("https://example.com/");
        String user = "testuser";
        String pw = "testpass";
        CamConnectionImpl cc = new CamConnectionImpl(mhcs, this.reqb, this.respb, this.userAgent, url, null, user, pw);
        String expected = "CamConnectionImpl: https://example.com/";
        String actual = cc.toString();
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalStateException.class)
    public void testNullHttpConnectionServiceObject() throws MalformedURLException, CamConnectionException {
        URL url = new URL("https://example.com/");
        String user = "testuser";
        String pw = "testpass";
        CamConnectionImpl cc = new CamConnectionImpl(null, this.reqb, this.respb, this.userAgent, url, null, user, pw);
        CamRequest req = null;
        cc.submitRequest(req);
    }

    @Test(expected = IllegalStateException.class)
    public void testNullRequestBuilderObject() throws MalformedURLException, CamConnectionException {
        MockHttpConnectionService mhcs = new MockHttpConnectionService();
        URL url = new URL("https://example.com/");
        String user = "testuser";
        String pw = "testpass";
        CamConnectionImpl cc = new CamConnectionImpl(mhcs, null, this.respb, this.userAgent, url, null, user, pw);
        CamRequest req = null;
        cc.submitRequest(req);
    }

    @Test(expected = IllegalStateException.class)
    public void testNullResponseBuilderObject() throws MalformedURLException, CamConnectionException {
        MockHttpConnectionService mhcs = new MockHttpConnectionService();
        URL url = new URL("https://example.com/");
        String user = "testuser";
        String pw = "testpass";
        CamConnectionImpl cc = new CamConnectionImpl(mhcs, this.reqb, null, this.userAgent, url, null, user, pw);
        CamRequest req = null;
        cc.submitRequest(req);
    }

    @Test(expected = IllegalStateException.class)
    public void testNullUrlObject() throws MalformedURLException, CamConnectionException {
        MockHttpConnectionService mhcs = new MockHttpConnectionService();
        String user = "testuser";
        String pw = "testpass";
        CamConnectionImpl cc = new CamConnectionImpl(mhcs, this.reqb, this.respb, this.userAgent, null, null, user, pw);
        CamRequest req = null;
        cc.submitRequest(req);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullRequestObject() throws MalformedURLException, CamConnectionException {
        MockHttpConnectionService mhcs = new MockHttpConnectionService();
        URL url = new URL("https://example.com/");
        String user = "testuser";
        String pw = "testpass";
        CamConnectionImpl cc = new CamConnectionImpl(mhcs, this.reqb, this.respb, this.userAgent, url, null, user, pw);
        CamRequest req = null;
        cc.submitRequest(req);
    }

    @Test(expected = CamConnectionException.class)
    public void testNonSecureUrl() throws MalformedURLException, CamConnectionException {
        MockHttpConnectionService mhcs = new MockHttpConnectionService();
        URL url = new URL("http://example.com/");
        String user = "testuser";
        String pw = "testpass";
        CamConnectionImpl cc = new CamConnectionImpl(mhcs, this.reqb, this.respb, this.userAgent, url, null, user, pw);
        CamRequest req = new CamRequest(Operation.GET_VERSION);
        cc.submitRequest(req);
    }

    @Test(expected = CamConnectionException.class)
    public void testExceptionOnConnect() throws MalformedURLException, CamConnectionException {
        MockHttpConnectionService mhcs = new MockHttpConnectionService();
        mhcs.addTestParameter(TestParameter.THROW_IO_EXCEPTION_ON_CONNECT, Boolean.TRUE.toString());
        URL url = new URL("https://example.com/");
        String user = "testuser";
        String pw = "testpass";
        CamConnectionImpl cc = new CamConnectionImpl(mhcs, this.reqb, this.respb, this.userAgent, url, null, user, pw);
        CamRequest req = new CamRequest(Operation.GET_VERSION);
        cc.submitRequest(req);
    }

    @Test(expected = CamConnectionException.class)
    public void testResponseCode500() throws MalformedURLException, CamConnectionException {
        MockHttpConnectionService mhcs = new MockHttpConnectionService();
        mhcs.addTestParameter(TestParameter.RESPONSE_CODE, "500");
        URL url = new URL("https://example.com/");
        String user = "testuser";
        String pw = "testpass";
        CamConnectionImpl cc = new CamConnectionImpl(mhcs, this.reqb, this.respb, this.userAgent, url, null, user, pw);
        CamRequest req = new CamRequest(Operation.GET_VERSION);
        cc.submitRequest(req);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNullUserAgentString() throws MalformedURLException {
        MockHttpConnectionService mhcs = new MockHttpConnectionService();
        URL url = new URL("https://example.com/");
        String user = "testuser";
        String pw = "testpass";
        CamConnectionImpl cc = new CamConnectionImpl(mhcs, this.reqb, this.respb, this.userAgent, url, null, user, pw);
        cc.setUserAgent(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeTimeout() throws MalformedURLException {
        MockHttpConnectionService mhcs = new MockHttpConnectionService();
        URL url = new URL("https://example.com/");
        String user = "testuser";
        String pw = "testpass";
        CamConnectionImpl cc = new CamConnectionImpl(mhcs, this.reqb, this.respb, this.userAgent, url, null, user, pw);
        cc.setTimeout(-500);
    }
}

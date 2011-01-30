package com.mattbertolini.camclient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mattbertolini.camclient.net.CamConnection;
import com.mattbertolini.camclient.net.MockHttpConnectionService;
import com.mattbertolini.camclient.net.TestParameter;
import com.mattbertolini.camclient.net.UserAgentBuilder;
import com.mattbertolini.camclient.request.RequestBuilder;
import com.mattbertolini.camclient.response.ResponseBuilder;

public class CamClientTest {
    private MockHttpConnectionService hcs;
    private RequestBuilder reqBldr;
    private ResponseBuilder respBldr;
    private String ua;
    private URL u;
    private String name;
    private String pass;

    @Before
    public void setUp() throws MalformedURLException {
        this.hcs = new MockHttpConnectionService();
        this.reqBldr = new RequestBuilder();
        this.respBldr = new ResponseBuilder();
        this.ua = new UserAgentBuilder().buildUserAgentString();
        this.u = new URL("https://www.example.com");
        this.name = "testuser";
        this.pass = "testpass";
    }

    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testGetLocalUserList() throws CamException {
        this.hcs.resetTestParameters();
        this.hcs.addTestParameter(TestParameter.COUNT, "5");
        CamConnection conn = new CamConnection(this.hcs, this.reqBldr, this.respBldr, this.ua, this.u, null, this.name, this.pass);
        CamClient client = new CamClient(conn);
        int expectedCount = 5;
        List<CamLocalUser> actualList = client.getLocalUserList();
        Assert.assertNotNull(actualList);
        Assert.assertEquals(expectedCount, actualList.size());
    }

    @Test
    public void testGetMacAddressList() throws CamException {
        this.hcs.resetTestParameters();
        this.hcs.addTestParameter(TestParameter.COUNT, "5");
        CamConnection conn = new CamConnection(this.hcs, this.reqBldr, this.respBldr, this.ua, this.u, null, this.name, this.pass);
        CamClient client = new CamClient(conn);
        int expectedCount = 5;
        List<CamDevice> actualList = client.getMacAddressList();
        Assert.assertNotNull(actualList);
        Assert.assertEquals(expectedCount, actualList.size());
    }

    @Test(expected = CamException.class)
    public void testGetMacAddressListException() throws CamException {
        this.hcs.resetTestParameters();
        this.hcs.addTestParameter(TestParameter.RETURN_ERROR, Boolean.toString(true));
        CamConnection conn = new CamConnection(this.hcs, this.reqBldr, this.respBldr, this.ua, this.u, null, this.name, this.pass);
        CamClient client = new CamClient(conn);
        client.getMacAddressList();
    }

    @Test(expected = CamException.class)
    public void testGetMacAddressListConnectionException() throws CamException {
        this.hcs.resetTestParameters();
        this.hcs.addTestParameter(TestParameter.THROW_IO_EXCEPTION_ON_CONNECT, Boolean.toString(true));
        CamConnection conn = new CamConnection(this.hcs, this.reqBldr, this.respBldr, this.ua, this.u, null, this.name, this.pass);
        CamClient client = new CamClient(conn);
        client.getMacAddressList();
    }

    @Test
    public void testGetVersion() throws CamException {
        this.hcs.resetTestParameters();
        CamConnection conn = new CamConnection(this.hcs, this.reqBldr, this.respBldr, this.ua, this.u, null, this.name, this.pass);
        CamClient client = new CamClient(conn);
        String expected = "4.7(3)";
        String actual = client.getVersion();
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = CamException.class)
    public void testGetVersionException() throws CamException {
        this.hcs.resetTestParameters();
        this.hcs.addTestParameter(TestParameter.RETURN_ERROR, Boolean.toString(true));
        CamConnection conn = new CamConnection(this.hcs, this.reqBldr, this.respBldr, this.ua, this.u, null, this.name, this.pass);
        CamClient client = new CamClient(conn);
        client.getVersion();
    }

    @Test(expected = CamException.class)
    public void testGetVersionConnectionException() throws CamException {
        this.hcs.resetTestParameters();
        this.hcs.addTestParameter(TestParameter.THROW_IO_EXCEPTION_ON_CONNECT, Boolean.toString(true));
        CamConnection conn = new CamConnection(this.hcs, this.reqBldr, this.respBldr, this.ua, this.u, null, this.name, this.pass);
        CamClient client = new CamClient(conn);
        client.getVersion();
    }
}

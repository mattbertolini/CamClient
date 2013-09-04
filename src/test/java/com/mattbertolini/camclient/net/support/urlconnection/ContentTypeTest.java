package com.mattbertolini.camclient.net.support.urlconnection;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Matt Bertolini
 */
public class ContentTypeTest {
    @Test
    public void testFromHeaderNoCharset() {
        ContentType html = ContentType.fromHeader("text/html");
        Assert.assertEquals("text/html", html.getType());
        Assert.assertNull(html.getCharset());
        Assert.assertEquals("ISO-8859-1", html.getCharsetOrDefault());
    }

    @Test
    public void testFromHeaderWithCharset() {
        ContentType json = ContentType.fromHeader("application/json; charset=UTF-8");
        Assert.assertEquals("application/json", json.getType());
        Assert.assertEquals("UTF-8", json.getCharset());
        Assert.assertEquals("UTF-8", json.getCharsetOrDefault());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromHeaderInvalidInput() {
        ContentType.fromHeader("not valid input.");
    }
}

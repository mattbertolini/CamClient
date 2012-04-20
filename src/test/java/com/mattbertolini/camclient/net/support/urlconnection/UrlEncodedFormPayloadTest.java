package com.mattbertolini.camclient.net.support.urlconnection;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * @author Matt Bertolini
 */
public class UrlEncodedFormPayloadTest {
    @Test
    public void testGetContentType() {
        UrlEncodedFormPayload payload = new UrlEncodedFormPayload();
        Assert.assertEquals("application/x-www-form-urlencoded", payload.getContentType());
    }

    @Test
    public void testGetCharacterEncoding() {
        UrlEncodedFormPayload payload = new UrlEncodedFormPayload();
        Assert.assertEquals("UTF-8", payload.getCharacterEncoding());
    }

    @Test
    public void testGetInputStream() throws IOException {
        String expected = "param1=value1";
        UrlEncodedFormPayload payload = new UrlEncodedFormPayload();
        payload.addParameter("param1", "value1");
        InputStream inputStream = payload.getInputStream();
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        StringBuilder sb = new StringBuilder();
        while(scanner.hasNext()) {
            sb.append(scanner.next());
        }
        scanner.close();
        inputStream.close();
        Assert.assertEquals(expected, sb.toString());

    }

    @Test
    public void testWriteTo() throws IOException {
        String expected = "param1=value1&param2=One%2BTwo%3DThree";
        UrlEncodedFormPayload payload = new UrlEncodedFormPayload();
        payload.addParameter("param1", "value1");
        payload.addParameter("param2", "One+Two=Three");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        payload.writeTo(outputStream);
        outputStream.close();
        Assert.assertEquals(expected, outputStream.toString("UTF-8"));
    }
}

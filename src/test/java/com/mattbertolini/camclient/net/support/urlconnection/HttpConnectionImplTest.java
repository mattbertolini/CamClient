package com.mattbertolini.camclient.net.support.urlconnection;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * @author Matt Bertolini
 */
public class HttpConnectionImplTest {
    @Test
    public void testSubmitRequestPost() throws IOException {
        String expectedRequestPayloadString = "param1=value1&param2=value2";
        String expectedResponsePayloadString = "Hello World!";
        int expectedStatusCode = 200;
        String expectedStatusMessage = "OK";
        // The output stream the request payload will be written to. We check this stream to see if the class under
        // test wrote the payload successfully.
        ByteArrayOutputStream requestPayload = new ByteArrayOutputStream();
        ByteArrayInputStream responsePayload = new ByteArrayInputStream(expectedResponsePayloadString.getBytes("UTF-8"));

        HttpURLConnection mockHttpUrlConnection = Mockito.mock(HttpURLConnection.class);
        Mockito.when(mockHttpUrlConnection.getOutputStream()).thenReturn(requestPayload);
        Mockito.when(mockHttpUrlConnection.getResponseCode()).thenReturn(expectedStatusCode);
        Mockito.when(mockHttpUrlConnection.getResponseMessage()).thenReturn(expectedStatusMessage);
        Mockito.when(mockHttpUrlConnection.getInputStream()).thenReturn(responsePayload);
        Mockito.when(mockHttpUrlConnection.getHeaderFields()).thenReturn(Collections.<String, List<String>>emptyMap());
        Mockito.when(mockHttpUrlConnection.getHeaderField("Content-Type")).thenReturn("text/plain; UTF-8");

        Url mockUrl = Mockito.mock(Url.class);
        Mockito.when(mockUrl.getProtocol()).thenReturn("HTTP");
        Mockito.when(mockUrl.openConnection()).thenReturn(mockHttpUrlConnection);

        UrlEncodedFormPayload payload = new UrlEncodedFormPayload();
        payload.addParameter("param1", "value1");
        payload.addParameter("param2", "value2");

        HttpRequest httpRequest = new HttpRequestBuilder()
                .setUrl(mockUrl)
                .setMethod(RequestMethod.POST)
                .setPayload(payload)
                .build();

        HttpConnection connection = new HttpConnectionImpl();
        HttpResponse httpResponse = connection.submitRequest(httpRequest);

        Assert.assertEquals(expectedStatusCode, httpResponse.getStatusCode());
        Assert.assertEquals(expectedStatusMessage, httpResponse.getStatusMessage());

        HttpPayload httpResponsePayload = httpResponse.getPayload();
        InputStream inputStream = httpResponsePayload.getInputStream();
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        Assert.assertEquals(expectedResponsePayloadString, scanner.useDelimiter("\\A").next());

        Mockito.verify(mockHttpUrlConnection).setRequestMethod(RequestMethod.POST.toString());
        Mockito.verify(mockHttpUrlConnection).setDoOutput(true);
        Mockito.verify(mockHttpUrlConnection).connect();
        Mockito.verify(mockHttpUrlConnection).disconnect();

        String actualRequestPayloadString = requestPayload.toString("UTF-8");
        Assert.assertEquals(expectedRequestPayloadString, actualRequestPayloadString);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSubmitRequestNullArgument() throws IOException {
        HttpConnection connection = new HttpConnectionImpl();
        connection.submitRequest(null);
    }

    @Test(expected = IllegalStateException.class)
    public void testSubmitRequestNullUrl() throws IOException {
        HttpRequest request = new HttpRequestBuilder().build();
        HttpConnection connection = new HttpConnectionImpl();
        connection.submitRequest(request);
    }

    @Test(expected = IllegalStateException.class)
    public void testSubmitRequestNonHttpUrl() throws IOException {
        HttpRequest request = new HttpRequestBuilder().setUrl(new Url(new URL("ftp://localhost"))).build();
        HttpConnection connection = new HttpConnectionImpl();
        connection.submitRequest(request);
    }
}

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

package com.mattbertolini.camclient.net.support.urlconnection;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.*;

/**
 * @author Matt Bertolini
 */
public class HttpConnectionImplTest {
    @Test
    public void testSubmitRequestGet() throws IOException {
        String expectedResponsePayloadString = "Hello World!";
        int expectedStatusCode = 200;
        String expectedStatusMessage = "OK";
        ByteArrayInputStream responsePayload = new ByteArrayInputStream(expectedResponsePayloadString.getBytes("UTF-8"));

        HttpURLConnection mockHttpUrlConnection = Mockito.mock(HttpURLConnection.class);
        Mockito.when(mockHttpUrlConnection.getResponseCode()).thenReturn(expectedStatusCode);
        Mockito.when(mockHttpUrlConnection.getResponseMessage()).thenReturn(expectedStatusMessage);
        Mockito.when(mockHttpUrlConnection.getInputStream()).thenReturn(responsePayload);
        Mockito.when(mockHttpUrlConnection.getHeaderFields()).thenReturn(Collections.<String, List<String>>emptyMap());
        Mockito.when(mockHttpUrlConnection.getHeaderField("Content-Type")).thenReturn("text/plain");

        Url mockUrl = Mockito.mock(Url.class);
        Mockito.when(mockUrl.getProtocol()).thenReturn("HTTP");
        Mockito.when(mockUrl.openConnection()).thenReturn(mockHttpUrlConnection);

        HttpRequest httpRequest = new HttpRequestBuilder()
                .setUrl(mockUrl)
                .addHeader("x-header-1", "value1")
                .addHeader("x-header-2", Arrays.asList("value1", "value2"))
                .setMethod(RequestMethod.GET)
                .build();

        HttpConnection connection = new HttpConnectionImpl();
        HttpResponse httpResponse = connection.submitRequest(httpRequest);

        Assert.assertEquals(expectedStatusCode, httpResponse.getStatusCode());
        Assert.assertEquals(expectedStatusMessage, httpResponse.getStatusMessage());

        HttpPayload httpResponsePayload = httpResponse.getPayload();
        InputStream inputStream = httpResponsePayload.getInputStream();
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        Assert.assertEquals(expectedResponsePayloadString, scanner.useDelimiter("\\A").next());

        Mockito.verify(mockHttpUrlConnection).setRequestMethod(RequestMethod.GET.toString());
        Mockito.verify(mockHttpUrlConnection).connect();
        Mockito.verify(mockHttpUrlConnection).disconnect();
    }

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
        Mockito.when(mockHttpUrlConnection.getHeaderField("Content-Type")).thenReturn("text/plain; charset=UTF-8");

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

    @Test
    public void testSubmitRequestWithProxy() throws IOException {
        String expectedResponsePayloadString = "Hello World!";
        int expectedStatusCode = 200;
        String expectedStatusMessage = "OK";
        ByteArrayInputStream responsePayload = new ByteArrayInputStream(expectedResponsePayloadString.getBytes("UTF-8"));

        HttpURLConnection mockHttpUrlConnection = Mockito.mock(HttpURLConnection.class);
        Mockito.when(mockHttpUrlConnection.getResponseCode()).thenReturn(expectedStatusCode);
        Mockito.when(mockHttpUrlConnection.getResponseMessage()).thenReturn(expectedStatusMessage);
        Mockito.when(mockHttpUrlConnection.getInputStream()).thenReturn(responsePayload);
        Mockito.when(mockHttpUrlConnection.getHeaderFields()).thenReturn(Collections.<String, List<String>>emptyMap());
        Mockito.when(mockHttpUrlConnection.getHeaderField("Content-Type")).thenReturn("text/plain");

        Proxy mockProxy = Mockito.mock(Proxy.class);

        Url mockUrl = Mockito.mock(Url.class);
        Mockito.when(mockUrl.getProtocol()).thenReturn("HTTP");
        Mockito.when(mockUrl.openConnection(mockProxy)).thenReturn(mockHttpUrlConnection);

        HttpRequest httpRequest = new HttpRequestBuilder()
                .setUrl(mockUrl)
                .setProxy(mockProxy)
                .setMethod(RequestMethod.GET)
                .build();

        HttpConnection connection = new HttpConnectionImpl();
        HttpResponse httpResponse = connection.submitRequest(httpRequest);

        Assert.assertEquals(expectedStatusCode, httpResponse.getStatusCode());
        Assert.assertEquals(expectedStatusMessage, httpResponse.getStatusMessage());

        HttpPayload httpResponsePayload = httpResponse.getPayload();
        InputStream inputStream = httpResponsePayload.getInputStream();
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        Assert.assertEquals(expectedResponsePayloadString, scanner.useDelimiter("\\A").next());

        Mockito.verify(mockHttpUrlConnection).setRequestMethod(RequestMethod.GET.toString());
        Mockito.verify(mockHttpUrlConnection).connect();
        Mockito.verify(mockHttpUrlConnection).disconnect();
    }

    @Test
    public void testSubmitRequestErrorResponse() throws IOException {
        String expectedErrorResponsePayloadString = "Error 404. Not Found";
        int expectedStatusCode = 404;
        String expectedStatusMessage = "Not Found";
        ByteArrayInputStream errorResponsePayload = new ByteArrayInputStream(expectedErrorResponsePayloadString.getBytes("UTF-8"));

        HttpURLConnection mockHttpUrlConnection = Mockito.mock(HttpURLConnection.class);
        Mockito.when(mockHttpUrlConnection.getResponseCode()).thenReturn(expectedStatusCode);
        Mockito.when(mockHttpUrlConnection.getResponseMessage()).thenReturn(expectedStatusMessage);
        Mockito.when(mockHttpUrlConnection.getErrorStream()).thenReturn(errorResponsePayload);
        Mockito.when(mockHttpUrlConnection.getHeaderFields()).thenReturn(Collections.<String, List<String>>emptyMap());
        Mockito.when(mockHttpUrlConnection.getHeaderField("Content-Type")).thenReturn("text/plain");

        Url mockUrl = Mockito.mock(Url.class);
        Mockito.when(mockUrl.getProtocol()).thenReturn("HTTP");
        Mockito.when(mockUrl.openConnection()).thenReturn(mockHttpUrlConnection);

        HttpRequest httpRequest = new HttpRequestBuilder()
                .setUrl(mockUrl)
                .setMethod(RequestMethod.GET)
                .build();

        HttpConnection connection = new HttpConnectionImpl();
        HttpResponse httpResponse = connection.submitRequest(httpRequest);

        Assert.assertEquals(expectedStatusCode, httpResponse.getStatusCode());
        Assert.assertEquals(expectedStatusMessage, httpResponse.getStatusMessage());

        HttpPayload httpResponsePayload = httpResponse.getPayload();
        InputStream inputStream = httpResponsePayload.getInputStream();
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        Assert.assertEquals(expectedErrorResponsePayloadString, scanner.useDelimiter("\\A").next());

        Mockito.verify(mockHttpUrlConnection).setRequestMethod(RequestMethod.GET.toString());
        Mockito.verify(mockHttpUrlConnection).connect();
        Mockito.verify(mockHttpUrlConnection).disconnect();
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

    @Test(expected = IOException.class)
    public void testSubmitRequestExceptionGettingConnection() throws IOException {
        Url mockUrl = Mockito.mock(Url.class);
        Mockito.when(mockUrl.getProtocol()).thenReturn("HTTP");
        Mockito.when(mockUrl.openConnection()).thenThrow(new IOException());

        HttpRequest httpRequest = new HttpRequestBuilder()
                .setUrl(mockUrl)
                .setMethod(RequestMethod.GET)
                .build();

        HttpConnection connection = new HttpConnectionImpl();
        connection.submitRequest(httpRequest);
    }

    @Test(expected = IOException.class)
    public void testSubmitRequestExceptionHandlingResponse() throws IOException {
        String expectedResponsePayloadString = "Hello World!";
        int expectedStatusCode = 200;
        String expectedStatusMessage = "OK";
        ByteArrayInputStream responsePayload = new ByteArrayInputStream(expectedResponsePayloadString.getBytes("UTF-8"));

        HttpURLConnection mockHttpUrlConnection = Mockito.mock(HttpURLConnection.class);
        Mockito.when(mockHttpUrlConnection.getResponseCode()).thenReturn(expectedStatusCode);
        Mockito.when(mockHttpUrlConnection.getResponseMessage()).thenThrow(new IOException());
        Mockito.when(mockHttpUrlConnection.getInputStream()).thenReturn(responsePayload);
        Mockito.when(mockHttpUrlConnection.getHeaderFields()).thenReturn(Collections.<String, List<String>>emptyMap());
        Mockito.when(mockHttpUrlConnection.getHeaderField("Content-Type")).thenReturn("text/plain");

        Url mockUrl = Mockito.mock(Url.class);
        Mockito.when(mockUrl.getProtocol()).thenReturn("HTTP");
        Mockito.when(mockUrl.openConnection()).thenReturn(mockHttpUrlConnection);

        HttpRequest httpRequest = new HttpRequestBuilder()
                .setUrl(mockUrl)
                .addHeader("x-header-1", "value1")
                .addHeader("x-header-2", Arrays.asList("value1", "value2"))
                .setMethod(RequestMethod.GET)
                .build();

        HttpConnection connection = new HttpConnectionImpl();
        HttpResponse httpResponse = connection.submitRequest(httpRequest);

        Assert.assertEquals(expectedStatusCode, httpResponse.getStatusCode());
        Assert.assertEquals(expectedStatusMessage, httpResponse.getStatusMessage());

        HttpPayload httpResponsePayload = httpResponse.getPayload();
        InputStream inputStream = httpResponsePayload.getInputStream();
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        Assert.assertEquals(expectedResponsePayloadString, scanner.useDelimiter("\\A").next());

        Mockito.verify(mockHttpUrlConnection).setRequestMethod(RequestMethod.GET.toString());
        Mockito.verify(mockHttpUrlConnection).connect();
        Mockito.verify(mockHttpUrlConnection).disconnect();
    }
}

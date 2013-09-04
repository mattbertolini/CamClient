/*
 * Copyright (c) 2013, Matthew Bertolini
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

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

        HttpURLConnection mockHttpUrlConnection = mock(HttpURLConnection.class);
        when(mockHttpUrlConnection.getResponseCode()).thenReturn(expectedStatusCode);
        when(mockHttpUrlConnection.getResponseMessage()).thenReturn(expectedStatusMessage);
        when(mockHttpUrlConnection.getInputStream()).thenReturn(responsePayload);
        when(mockHttpUrlConnection.getHeaderFields()).thenReturn(Collections.<String, List<String>>emptyMap());
        when(mockHttpUrlConnection.getHeaderField("Content-Type")).thenReturn("text/plain");

        Url mockUrl = mock(Url.class);
        when(mockUrl.getProtocol()).thenReturn("HTTP");
        when(mockUrl.openConnection()).thenReturn(mockHttpUrlConnection);

        HttpRequest httpRequest = new HttpRequestImpl();
        httpRequest.setUrl(mockUrl);
        httpRequest.addHeader("x-header-1", "value1");
        httpRequest.addHeader("x-header-2", Arrays.asList("value1", "value2"));
        httpRequest.setMethod(Method.GET);

        HttpConnection connection = new HttpConnectionImpl();
        HttpResponse httpResponse = connection.executeRequest(httpRequest);

        assertEquals(expectedStatusCode, httpResponse.getStatusCode());
        assertEquals(expectedStatusMessage, httpResponse.getStatusMessage());

        HttpPayload httpResponsePayload = httpResponse.getPayload();
        InputStream inputStream = httpResponsePayload.getInputStream();
        Scanner scanner = new Scanner(inputStream, "ISO-8859-1");
        assertEquals(expectedResponsePayloadString, scanner.useDelimiter("\\A").next());

        verify(mockHttpUrlConnection).setRequestMethod(Method.GET.toString());
        verify(mockHttpUrlConnection).connect();
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

        HttpURLConnection mockHttpUrlConnection = mock(HttpURLConnection.class);
        when(mockHttpUrlConnection.getOutputStream()).thenReturn(requestPayload);
        when(mockHttpUrlConnection.getResponseCode()).thenReturn(expectedStatusCode);
        when(mockHttpUrlConnection.getResponseMessage()).thenReturn(expectedStatusMessage);
        when(mockHttpUrlConnection.getInputStream()).thenReturn(responsePayload);
        when(mockHttpUrlConnection.getHeaderFields()).thenReturn(Collections.<String, List<String>>emptyMap());
        when(mockHttpUrlConnection.getHeaderField("Content-Type")).thenReturn("text/plain; charset=UTF-8");

        Url mockUrl = mock(Url.class);
        when(mockUrl.getProtocol()).thenReturn("HTTP");
        when(mockUrl.openConnection()).thenReturn(mockHttpUrlConnection);

        UrlEncodedFormPayload payload = new UrlEncodedFormPayload();
        payload.addParameter("param1", "value1");
        payload.addParameter("param2", "value2");

        HttpRequest httpRequest = new HttpRequestImpl();
        httpRequest.setUrl(mockUrl);
        httpRequest.setMethod(Method.POST);
        httpRequest.setPayload(payload);

        HttpConnection connection = new HttpConnectionImpl();
        HttpResponse httpResponse = connection.executeRequest(httpRequest);

        assertEquals(expectedStatusCode, httpResponse.getStatusCode());
        assertEquals(expectedStatusMessage, httpResponse.getStatusMessage());

        HttpPayload httpResponsePayload = httpResponse.getPayload();
        InputStream inputStream = httpResponsePayload.getInputStream();
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        assertEquals(expectedResponsePayloadString, scanner.useDelimiter("\\A").next());

        verify(mockHttpUrlConnection).setRequestMethod(Method.POST.toString());
        verify(mockHttpUrlConnection).setDoOutput(true);
        verify(mockHttpUrlConnection).connect();

        String actualRequestPayloadString = requestPayload.toString("UTF-8");
        assertEquals(expectedRequestPayloadString, actualRequestPayloadString);
    }

    @Test
    public void testSubmitRequestWithProxy() throws IOException {
        String expectedResponsePayloadString = "Hello World!";
        int expectedStatusCode = 200;
        String expectedStatusMessage = "OK";
        ByteArrayInputStream responsePayload = new ByteArrayInputStream(expectedResponsePayloadString.getBytes("UTF-8"));

        HttpURLConnection mockHttpUrlConnection = mock(HttpURLConnection.class);
        when(mockHttpUrlConnection.getResponseCode()).thenReturn(expectedStatusCode);
        when(mockHttpUrlConnection.getResponseMessage()).thenReturn(expectedStatusMessage);
        when(mockHttpUrlConnection.getInputStream()).thenReturn(responsePayload);
        when(mockHttpUrlConnection.getHeaderFields()).thenReturn(Collections.<String, List<String>>emptyMap());
        when(mockHttpUrlConnection.getHeaderField("Content-Type")).thenReturn("text/plain");

        Proxy mockProxy = mock(Proxy.class);

        Url mockUrl = mock(Url.class);
        when(mockUrl.getProtocol()).thenReturn("HTTP");
        when(mockUrl.openConnection(mockProxy)).thenReturn(mockHttpUrlConnection);

        HttpRequest httpRequest = new HttpRequestImpl();
        httpRequest.setUrl(mockUrl);
        httpRequest.setProxy(mockProxy);
        httpRequest.setMethod(Method.GET);

        HttpConnection connection = new HttpConnectionImpl();
        HttpResponse httpResponse = connection.executeRequest(httpRequest);

        assertEquals(expectedStatusCode, httpResponse.getStatusCode());
        assertEquals(expectedStatusMessage, httpResponse.getStatusMessage());

        HttpPayload httpResponsePayload = httpResponse.getPayload();
        InputStream inputStream = httpResponsePayload.getInputStream();
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        assertEquals(expectedResponsePayloadString, scanner.useDelimiter("\\A").next());

        verify(mockHttpUrlConnection).setRequestMethod(Method.GET.toString());
        verify(mockHttpUrlConnection).connect();
    }

    @Test
    public void testSubmitRequestErrorResponse() throws IOException {
        String expectedErrorResponsePayloadString = "Error 404. Not Found";
        int expectedStatusCode = 404;
        String expectedStatusMessage = "Not Found";
        ByteArrayInputStream errorResponsePayload = new ByteArrayInputStream(expectedErrorResponsePayloadString.getBytes("UTF-8"));

        HttpURLConnection mockHttpUrlConnection = mock(HttpURLConnection.class);
        when(mockHttpUrlConnection.getResponseCode()).thenReturn(expectedStatusCode);
        when(mockHttpUrlConnection.getResponseMessage()).thenReturn(expectedStatusMessage);
        when(mockHttpUrlConnection.getErrorStream()).thenReturn(errorResponsePayload);
        when(mockHttpUrlConnection.getHeaderFields()).thenReturn(Collections.<String, List<String>>emptyMap());
        when(mockHttpUrlConnection.getHeaderField("Content-Type")).thenReturn("text/plain");

        Url mockUrl = mock(Url.class);
        when(mockUrl.getProtocol()).thenReturn("HTTP");
        when(mockUrl.openConnection()).thenReturn(mockHttpUrlConnection);

        HttpRequest httpRequest = new HttpRequestImpl();
        httpRequest.setUrl(mockUrl);
        httpRequest.setMethod(Method.GET);

        HttpConnection connection = new HttpConnectionImpl();
        HttpResponse httpResponse = connection.executeRequest(httpRequest);

        assertEquals(expectedStatusCode, httpResponse.getStatusCode());
        assertEquals(expectedStatusMessage, httpResponse.getStatusMessage());

        HttpPayload httpResponsePayload = httpResponse.getPayload();
        InputStream inputStream = httpResponsePayload.getInputStream();
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        assertEquals(expectedErrorResponsePayloadString, scanner.useDelimiter("\\A").next());

        verify(mockHttpUrlConnection).setRequestMethod(Method.GET.toString());
        verify(mockHttpUrlConnection).connect();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSubmitRequestNullArgument() throws IOException {
        HttpConnection connection = new HttpConnectionImpl();
        connection.executeRequest(null);
    }

    @Test(expected = IllegalStateException.class)
    public void testSubmitRequestNullUrl() throws IOException {
        HttpRequest request = new HttpRequestImpl();
        HttpConnection connection = new HttpConnectionImpl();
        connection.executeRequest(request);
    }

    @Test(expected = IllegalStateException.class)
    public void testSubmitRequestNonHttpUrl() throws IOException {
        HttpRequest request = new HttpRequestImpl();
        request.setUrl(new Url(new URL("ftp://localhost")));
        HttpConnection connection = new HttpConnectionImpl();
        connection.executeRequest(request);
    }

    @Test(expected = IOException.class)
    public void testSubmitRequestExceptionGettingConnection() throws IOException {
        Url mockUrl = mock(Url.class);
        when(mockUrl.getProtocol()).thenReturn("HTTP");
        when(mockUrl.openConnection()).thenThrow(new IOException());

        HttpRequest httpRequest = new HttpRequestImpl();
        httpRequest.setUrl(mockUrl);
        httpRequest.setMethod(Method.GET);

        HttpConnection connection = new HttpConnectionImpl();
        connection.executeRequest(httpRequest);
    }

    @Test(expected = IOException.class)
    public void testSubmitRequestExceptionHandlingResponse() throws IOException {
        String expectedResponsePayloadString = "Hello World!";
        int expectedStatusCode = 200;
        String expectedStatusMessage = "OK";
        ByteArrayInputStream responsePayload = new ByteArrayInputStream(expectedResponsePayloadString.getBytes("UTF-8"));

        HttpURLConnection mockHttpUrlConnection = mock(HttpURLConnection.class);
        when(mockHttpUrlConnection.getResponseCode()).thenReturn(expectedStatusCode);
        when(mockHttpUrlConnection.getResponseMessage()).thenThrow(new IOException());
        when(mockHttpUrlConnection.getInputStream()).thenReturn(responsePayload);
        when(mockHttpUrlConnection.getHeaderFields()).thenReturn(Collections.<String, List<String>>emptyMap());
        when(mockHttpUrlConnection.getHeaderField("Content-Type")).thenReturn("text/plain");

        Url mockUrl = mock(Url.class);
        when(mockUrl.getProtocol()).thenReturn("HTTP");
        when(mockUrl.openConnection()).thenReturn(mockHttpUrlConnection);

        HttpRequest httpRequest = new HttpRequestImpl();
        httpRequest.setUrl(mockUrl);
        httpRequest.addHeader("x-header-1", "value1");
        httpRequest.addHeader("x-header-2", Arrays.asList("value1", "value2"));
        httpRequest.setMethod(Method.GET);

        HttpConnection connection = new HttpConnectionImpl();
        HttpResponse httpResponse = connection.executeRequest(httpRequest);

        assertEquals(expectedStatusCode, httpResponse.getStatusCode());
        assertEquals(expectedStatusMessage, httpResponse.getStatusMessage());

        HttpPayload httpResponsePayload = httpResponse.getPayload();
        InputStream inputStream = httpResponsePayload.getInputStream();
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        assertEquals(expectedResponsePayloadString, scanner.useDelimiter("\\A").next());

        verify(mockHttpUrlConnection).setRequestMethod(Method.GET.toString());
        verify(mockHttpUrlConnection).connect();
        verify(mockHttpUrlConnection).disconnect();
    }
}

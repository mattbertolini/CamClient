package com.mattbertolini.camclient.net;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.Scanner;

import com.mattbertolini.camclient.request.CamRequest;
import com.mattbertolini.camclient.request.RequestBuilder;
import com.mattbertolini.camclient.response.CamResponse;
import com.mattbertolini.camclient.response.ResponseBuilder;

public class CamConnection {
    private static final int DEFAULT_TIMEOUT = 15 * 1000; // 15 seconds
    private static final String HTTPS = "https";
    private static final String POST = "POST";
    private static final String USER_AGENT = "User-Agent";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String FORM_URL_ENCODED = "application/x-www-form-urlencoded";
    private static final String LINE_SEPARATOR = "line.separator";

    private HttpConnectionService httpConnectionService;
    private RequestBuilder requestBuilder;
    private ResponseBuilder responseBuilder;
    private String userAgent;
    private URL url;
    private Proxy proxy;
    private int timeout;
    private String username;
    private String password;

    public CamConnection(HttpConnectionService connService, RequestBuilder requestBuilder, 
            ResponseBuilder responseBuilder, String userAgent, URL url, Proxy proxy, 
            String username, String password) {
        this.httpConnectionService = connService;
        this.requestBuilder = requestBuilder;
        this.responseBuilder = responseBuilder;
        this.userAgent = userAgent;
        this.url = url;
        this.proxy = proxy;
        this.timeout = DEFAULT_TIMEOUT;
        this.username = username;
        this.password = password;
    }

    public CamResponse submitRequest(CamRequest request) throws CamConnectionException {
        if(this.httpConnectionService == null) {
            throw new IllegalStateException("Null HttpConnectionService object.");
        }
        if(this.requestBuilder == null) {
            throw new IllegalStateException("Null RequestBuilder object.");
        }
        if(this.responseBuilder == null) {
            throw new IllegalStateException("Null ResponseBuilder object.");
        }
        if(this.url == null) {
            throw new IllegalStateException("Null URL object.");
        }
        if(request == null) {
            throw new IllegalArgumentException("CamRequest object cannot be null.");
        }
        if(!this.url.getProtocol().equalsIgnoreCase(HTTPS)) {
            throw new CamConnectionException("The CAM connection must connect via the HTTPS protocol.");
        }

        HttpURLConnection conn = null;
        Scanner in = null;
        CamResponse response = null;

        try {
            conn = this.httpConnectionService.getConnection(this.url, this.proxy);
            conn.setRequestMethod(POST);
            conn.setRequestProperty(USER_AGENT, this.userAgent);
            conn.setRequestProperty(CONTENT_TYPE, FORM_URL_ENCODED);
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            conn.setReadTimeout(this.timeout);

            conn.connect();

            // Create request
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            out.write(this.requestBuilder.buildRequest(request));
            out.write(this.requestBuilder.appendUserNameAndPassword(this.username, this.password));
            out.close(); // Closing the stream sends the request

            if(conn.getResponseCode() >= HttpURLConnection.HTTP_BAD_REQUEST) {
                throw new CamConnectionException("HTTP response code" + conn.getResponseCode());
            }

            // Create response
            in = new Scanner(new InputStreamReader(conn.getInputStream()));
            StringBuilder respStr = new StringBuilder();

            while(in.hasNextLine()) {
                respStr.append(in.nextLine());
                respStr.append(System.getProperty(LINE_SEPARATOR));
            }
            response = this.responseBuilder.buildResponse(respStr.toString());
        } catch(IOException e) {
            throw new CamConnectionException("A communication error has occurred.", e);
        } finally {
            if(in != null) {
                in.close();
            }
            if(conn != null) {
                conn.disconnect();
            }
        }
        return response;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public void setTimeout(int timeout) {
        if(timeout < 0) {
            throw new IllegalArgumentException("Timeout cannot be a negative number.");
        }
        this.timeout = timeout;
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    /**
     * Set the user agent string that will be sent to the CAM with every 
     * request.
     * 
     * @param userAgent The string to set for the user agent.
     * @throws NullPointerException If the string is null.
     */
    public void setUserAgent(String userAgent) {
        if(userAgent == null) {
            throw new NullPointerException("User agent cannot be null.");
        }
        this.userAgent = userAgent;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ": " + this.url.toExternalForm();
    }
}

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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A special HttpPayload type that enables quick building of URL encoded form requests.
 *
 * @author Matt Bertolini
 */
public class UrlEncodedFormPayload implements HttpPayload {
    private static final char EQUALS_SIGN = '=';
    private static final char AMPERSAND = '&';

    private Map<String, String> parameters;
    private ContentType contentType;

    public UrlEncodedFormPayload() {
        this(ContentType.APPLICATION_FORM_URLENCODED);
    }

    public UrlEncodedFormPayload(String encoding) {
        this(ContentType.APPLICATION_FORM_URLENCODED.withCharset(encoding));
    }

    private UrlEncodedFormPayload(ContentType contentType) {
        this.parameters = new LinkedHashMap<String, String>();
        this.contentType = contentType;
    }

    public void addParameter(String name, String value) {
        this.parameters.put(name, value);
    }

    @Override
    public ContentType getContentType() {
        return this.contentType;
    }

    @Override
    public InputStream getInputStream() {
        String s = this.buildPayloadString();
        try {
            return new ByteArrayInputStream(s.getBytes(this.getEncoding()));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        String s = this.buildPayloadString();
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);
        writer.write(s);
        writer.flush();
    }

    private String buildPayloadString() {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : this.parameters.entrySet()) {
            if(first) {
                first = false;
            } else {
                sb.append(AMPERSAND);
            }
            sb.append(this.urlEncode(entry.getKey(), this.getEncoding())).append(EQUALS_SIGN).append(this.urlEncode(entry.getValue(), this.getEncoding()));
        }
        return sb.toString();
    }

    private String urlEncode(String input, String encoding) {
        try {
            return URLEncoder.encode(input, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private String getEncoding() {
        return this.contentType.getCharsetOrDefault();
    }
}

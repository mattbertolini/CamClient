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
    public void testGetCharacterEncodingReturnNull() {
        UrlEncodedFormPayload payload = new UrlEncodedFormPayload();
        Assert.assertNull(payload.getCharacterEncoding());
    }

    @Test
    public void testGetCharacterEncodingReturnString() {
        UrlEncodedFormPayload payload = new UrlEncodedFormPayload("UTF-8");
        Assert.assertEquals("UTF-8", payload.getCharacterEncoding());
    }

    @Test
    public void testGetInputStream() throws IOException {
        String expected = "param1=value1";
        UrlEncodedFormPayload payload = new UrlEncodedFormPayload();
        payload.addParameter("param1", "value1");
        InputStream inputStream = payload.getInputStream();
        Scanner scanner = new Scanner(inputStream, "ISO-8859-1");
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

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

package com.mattbertolini.camclient.net;

import com.mattbertolini.camclient.response.CamResponse;
import com.mattbertolini.camclient.response.CamResponseImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @param <T> The object to create a CamResponse object from.
 * @author Matt Bertolini
 */
public abstract class AbstractCamResponseAdapter<T> implements CamResponseAdapter<T> {
    private static final String COMMENT_BEGIN_PATTERN = "\\s*<!--\\s*";
    private static final String COMMENT_END_PATTERN = "\\s*-->\\s*";
    private static final String NAME_VALUE_DELIMITER_PATTERN = "[=,]";
    private static final String ERROR_KEY = "error";
    private static final String COUNT_KEY = "count";
    private static final String EMPTY_STRING = "";
    private static final String ZERO = "0";
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public CamResponse foo(InputStream responsePayload, String encoding) {
        return this.foo(responsePayload, encoding, false, null);
    }

    public CamResponse foo(InputStream responsePayload, String encoding, boolean error, String errorText) {
        //
        Scanner scanner = new Scanner(responsePayload, encoding);
        StringBuilder sb = new StringBuilder();
        while(scanner.hasNextLine()) {
            sb.append(scanner.nextLine());
            sb.append(LINE_SEPARATOR);
        }
        scanner.close();
        try {
            responsePayload.close();
        } catch (IOException e) {
            //
        }

        String responseString = sb.toString();
        List<Map<String, String>> data = Collections.emptyList();
        if(!error) {
            String[] rows = responseString.split(COMMENT_END_PATTERN);
            data = new ArrayList<Map<String, String>>(rows.length);
            for(String row : rows) {
                Map<String, String> rowData = new HashMap<String, String>();
                row = row.replaceAll(COMMENT_BEGIN_PATTERN, EMPTY_STRING);
                String[] nvp = row.split(NAME_VALUE_DELIMITER_PATTERN);
                if(nvp.length % 2 != 0) {
                    // TODO: throw exception
                }

                for(int i = 0; i < nvp.length; i += 2) {
                    rowData.put(nvp[i].trim().toLowerCase(), nvp[i + 1].trim());
                }

                if(rowData.containsKey(ERROR_KEY)
                        && !rowData.get(ERROR_KEY).equals(ZERO)) {
                    error = true;
                    errorText = "CAM Error - " + rowData.get(ERROR_KEY);
                    data = Collections.emptyList();
                    break;
                } else if(rowData.containsKey(COUNT_KEY)) {
                    if(rowData.get(COUNT_KEY).equals(ZERO)) {
                        data = Collections.emptyList();
                        break;
                    }
                } else {
                    // Since the error value is zero (success), we don't need it
                    // anymore. Not adding it to the final data collection.
                    if(!rowData.containsKey(ERROR_KEY)
                            && !rowData.containsKey(COUNT_KEY)) {
                        data.add(rowData);
                    }
                }
            }
        }

        return new CamResponseImpl(responseString, data, error, errorText);
    }
}

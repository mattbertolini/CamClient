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

package com.mattbertolini.camclient.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class UserAgentBuilder {
    private static final String PROPERTIES_FILE_PATH = "/version-info.properties";
    public static final String NAME = "com.mattbertolini.camclient.name";
    public static final String VERSION = "com.mattbertolini.camclient.version";
    private static final String BUILD_DATE = "com.mattbertolini.camclient.build-date";
    private static final String JAVA_VERSION = "java.version";
    private static final String OS_NAME = "os.name";
    private static final String OS_VERSION = "os.version";
    private static final String OS_ARCHITECTURE = "os.arch";

    public String buildUserAgentString() {
        String libraryName = null;
        String libraryVersion = null;
        String libraryBuildDate = null;
        String runtimeVersion;
        String osName;
        String osVersion;
        String osArchitecture;

        InputStream is = null;
        Properties properties = new Properties();
        try {
            is = this.getClass().getResourceAsStream(PROPERTIES_FILE_PATH);
            if(is != null) {
                properties.load(new InputStreamReader(is, "UTF-8"));
                libraryName = properties.getProperty(NAME);
                libraryVersion = properties.getProperty(VERSION);
                libraryBuildDate = properties.getProperty(BUILD_DATE);
            }
        } catch (IOException e) {
            // Do nothing
        } finally {
            try {
                if(is != null) {
                    is.close();
                }
            } catch (IOException e) {
                // Do nothing
            }
        }

        runtimeVersion = System.getProperty(JAVA_VERSION);
        osName = System.getProperty(OS_NAME).replace(" ", "");
        osVersion = System.getProperty(OS_VERSION);
        osArchitecture = System.getProperty(OS_ARCHITECTURE);

        StringBuilder sb = new StringBuilder();
        sb.append(libraryName).append("/").append(libraryVersion);
        sb.append(" (Build ");
        sb.append(libraryBuildDate);
        sb.append(") ");
        sb.append("Java").append("/").append(runtimeVersion);
        sb.append(" ");
        sb.append(osName).append("/").append(osVersion);
        sb.append(" (");
        sb.append(osArchitecture).append(")");

        return sb.toString();
    }
}

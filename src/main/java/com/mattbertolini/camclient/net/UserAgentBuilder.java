package com.mattbertolini.camclient.net;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public class UserAgentBuilder {
    private static final String MANIFEST_PATH = "/META-INF/MANIFEST.MF";
    private static final String BUILD_DATE = "Build-Date";
    private static final String JAVA_VERSION = "java.version";
    private static final String OS_NAME = "os.name";
    private static final String OS_VERSION = "os.version";
    private static final String OS_ARCHITECTURE = "os.arch";

    public String buildUserAgentString() {
        String libraryName = null;
        String libraryVersion = null;
        String libraryBuildDate = null;
        String runtimeVersion = null;
        String osName = null;
        String osVersion = null;
        String osArchitecture = null;

        InputStream is = null;
        try {
            is = this.getClass().getResourceAsStream(MANIFEST_PATH);
            if(is != null) {
                Manifest manifest = new Manifest(is);
                Attributes attributes = manifest.getMainAttributes();
                libraryName = attributes.getValue(Attributes.Name.IMPLEMENTATION_TITLE);
                libraryVersion = attributes.getValue(Attributes.Name.IMPLEMENTATION_VERSION);
                libraryBuildDate = attributes.getValue(BUILD_DATE);
            }
        } catch(IOException e) {
            // Do nothing
        } finally {
            try {
                if(is != null) {
                    is.close();
                }
            } catch(IOException e) {
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

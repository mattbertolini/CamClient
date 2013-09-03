package com.mattbertolini.camclient.net;

/**
 * @author Matt Bertolini
 */
public abstract class AbstractCamConnection {
    public String getUserAgent() {
        //
        UserAgentBuilder userAgentBuilder = new UserAgentBuilder();
        return userAgentBuilder.buildUserAgentString();
    }
}

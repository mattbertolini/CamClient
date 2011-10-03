package com.mattbertolini.camclient.net;

/**
 * Interface that represents a parameter name that can be sent to the CAM.
 * 
 * @author Matt Bertolini
 */
public interface Parameter {
    /**
     * Gets the text name supported by the CAM.
     * 
     * @return The CAM parameter name.
     */
    String getName();
}

package com.mattbertolini.camclient.response;

import java.util.List;
import java.util.Map;

/**
 * @author Matt Bertolini
 */
public class CamResponseImpl implements CamResponse {
    private final String rawResponseText;
    private final List<Map<String, String>> responseData;
    private final boolean error;
    private final String errorText;

    public CamResponseImpl(String rawResponseText, List<Map<String, String>> responseData, boolean error, String errorText) {
        this.rawResponseText = rawResponseText;
        this.responseData = responseData;
        this.error = error;
        this.errorText = errorText;
    }

    /**
     * Gets the error message returned by the CAM when an error occurs
     * processing the request. This will return null if the request was
     * successful.
     *
     * @return A String containing the error text or null if the request was
     * successful.
     */
    @Override
    public String getErrorText() {
        return this.errorText;
    }

    /**
     * Returns the response data string parsed into a List of key value pairs.
     * This method will only return data if the requests operation is supposed
     * to return data. If an error is returned, no data will be returned.
     *
     * @return An ArrayList of HashMaps. Each HashMap represents one record in
     * the response.
     */
    @Override
    public List<Map<String, String>> getResponseData() {
        return this.responseData;
    }

    /**
     * Returns the raw response text returned by the CAM.
     *
     * @return A string containing the raw response text from the CAM.
     */
    @Override
    public String getRawResponseText() {
        return this.rawResponseText;
    }

    /**
     * Returns whether or not the request was successful. This flag relates to
     * the CAM's ability to process the request and return a valid response,
     * not whether a communications channel could be created.
     *
     * @return True if the request was successful, false otherwise.
     */
    @Override
    public boolean isError() {
        return this.error;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (error ? 1231 : 1237);
        result = prime * result
                + ((errorText == null) ? 0 : errorText.hashCode());
        result = prime * result
                + ((responseData == null) ? 0 : responseData.hashCode());
        result = prime * result
                + ((rawResponseText == null) ? 0 : rawResponseText.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        CamResponseImpl other = (CamResponseImpl) obj;
        if(this.error != other.error) {
            return false;
        }
        if(this.errorText == null) {
            if(other.errorText != null) {
                return false;
            }
        } else if(!this.errorText.equals(other.errorText)) {
            return false;
        }
        if(this.responseData == null) {
            if(other.responseData != null) {
                return false;
            }
        } else if(!responseData.equals(other.responseData)) {
            return false;
        }
        if(this.rawResponseText == null) {
            if(other.rawResponseText != null) {
                return false;
            }
        } else if(!this.rawResponseText.equals(other.rawResponseText)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CamResponseImpl [rawResponseText=" + this.rawResponseText
                + ", responseData=" + this.responseData + ", error="
                + this.error + ", errorText=" + this.errorText + "]";
    }
}

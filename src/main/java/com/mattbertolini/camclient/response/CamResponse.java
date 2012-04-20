package com.mattbertolini.camclient.response;

import java.util.List;
import java.util.Map;

public interface CamResponse {
    //
    String getErrorText();
    List<Map<String, String>> getResponseData();
    String getRawResponseText();
    boolean isError();
}

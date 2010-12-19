package com.mattbertolini.camclient.response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseBuilder {
    private static final String COMMENT_BEGIN_PATTERN = "\\s*<!--\\s*";
    private static final String COMMENT_END_PATTERN = "\\s*-->\\s*";
    private static final String NAME_VALUE_DELIMITER_PATTERN = "[=,]";
    private static final String ERROR_KEY = "error";
    private static final String COUNT_KEY = "count";
    private static final String EMPTY_STRING = "";
    private static final String ZERO = "0";

    public CamResponse buildResponse(final String responseString) {
        if(responseString == null) {
            throw new NullPointerException("Response string cannot be null.");
        }
        boolean error = false;
        String errorText = null;
        String[] rows = responseString.split(COMMENT_END_PATTERN);
        List<Map<String, String>> data = new ArrayList<Map<String, String>>(rows.length);
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
        CamResponse response = new CamResponse(responseString, data, error,
                errorText);
        return response;
    }
}

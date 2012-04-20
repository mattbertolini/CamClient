package com.mattbertolini.camclient.net.urlconnection;

import com.mattbertolini.camclient.net.CamResponseAdapter;
import com.mattbertolini.camclient.response.CamResponse;
import com.mattbertolini.camclient.response.CamResponseImpl;
import com.mattbertolini.camclient.net.support.urlconnection.HttpPayload;
import com.mattbertolini.camclient.net.support.urlconnection.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Matt Betolini
 */
public class HttpConnectionCamResponseAdapter implements CamResponseAdapter<HttpResponse> {
    private static final String COMMENT_BEGIN_PATTERN = "\\s*<!--\\s*";
    private static final String COMMENT_END_PATTERN = "\\s*-->\\s*";
    private static final String NAME_VALUE_DELIMITER_PATTERN = "[=,]";
    private static final String ERROR_KEY = "error";
    private static final String COUNT_KEY = "count";
    private static final String EMPTY_STRING = "";
    private static final String ZERO = "0";
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    @Override
    public CamResponse buildResponse(HttpResponse response) {
        //
        boolean error = false;
        String errorText = null;
        int httpStatusCode = response.getStatusCode();
        if(httpStatusCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
            error = true;
            errorText = response.getStatusMessage();

        }

        HttpPayload payload = response.getPayload();
        InputStream is = payload.getInputStream();
        Scanner scanner = new Scanner(is, "UTF-8");
        StringBuilder sb = new StringBuilder();
        while(scanner.hasNextLine()) {
            sb.append(scanner.nextLine());
            sb.append(LINE_SEPARATOR);
        }
        scanner.close();
        try {
            is.close();
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

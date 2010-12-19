package com.mattbertolini.camclient.net;

import java.util.HashMap;
import java.util.Map;

import com.mattbertolini.camclient.request.Operation;
import com.mattbertolini.camclient.request.RequestParameter;

public class TestResponseGenerator {
    private static final String ERROR_RESPONSE = "<!--error=This is an error-->";
    private static final String NO_ERROR = "<!--error=0-->";

    private static final String CHECK_MAC_ADDRESS_ROW = "<!--MAC=0A:13:07:9B:82:60,IP=x.x.x.x,CAS=y.y.y.y,TYPE=ALLOW,ROLE=zzz,DESCRIPTION=My Filter-->";
    private static final String GET_MAC_LIST_ROW = "<!--MAC=0A:13:07:9B:82:60,IP=x.x.x.x,CAS=y.y.y.y,TYPE=ALLOW,ROLE=zzz,DESCRIPTION=My Filter-->";
    private static final String GET_LOCAL_USER_LIST_ROW = "<!--NAME=jdoe,ROLE=Student-->";
    private static final String GET_VERSION_STRING = "<!--version=4.7(3)-->";
    private static final String GET_USER_INFO_ROW = "<!--IP=10.1.10.12,MAC=0A:13:07:9B:82:60,NAME=jdoe,PROVIDER=LDAP Server,ROLE=Student,ORIGROLE=Student,VLAN=1024,NEWVLAN=1024,OS=Windows XP-->";
    private static final String GET_OOB_USER_INFO_ROW = "<!--IP=10.1.10.12,MAC=0A:13:07:9B:82:60,NAME=jdoe,PROVIDER=LDAP Server,ROLE=Student,AUTHVLAN=10,ACCESSVLAN=1024,OS=Windows XP,SWITCHIP=10.1.10.1,PORTNUM=18-->";
    private static final String GET_CLEAN_USER_INFO_ROW = "<!--MAC=0A:13:07:9B:82:60,NAME=jdoe,PROVIDER=LDAP Server,ROLE=Student,VLAN=10-->";
    private static final String GET_REPORTS_ROW = "<!--status=status,user=user,agentType=agentType,ip=ip,mac=mac,os=os,time=time,text=text-->";

    private Map<TestParameter, String> testParameters;
    private Map<String, String> requestParameters;

    public TestResponseGenerator(Map<TestParameter, String> testParameters) {
        this.testParameters = testParameters;
    }

    public void parseRequest(String requestString) {
        String[] rows = requestString.split("&");
        this.requestParameters = new HashMap<String, String>();
        for(String row : rows) {
            String[] nvp = row.split("=");
            this.requestParameters.put(nvp[0], nvp[1]);
        }
    }

    public String getOperation() {
        return this.requestParameters.get(RequestParameter.OPERATION.getName());
    }

    public boolean throwIoExceptionOnConnect() {
        boolean retVal = false;
        if(this.testParameters.containsKey(TestParameter.THROW_IO_EXCEPTION_ON_CONNECT)) {
            retVal = Boolean.parseBoolean(this.testParameters.get(TestParameter.THROW_IO_EXCEPTION_ON_CONNECT));
        }
        return retVal;
    }

    public boolean getReturnError() {
        boolean retVal = false;
        if(this.testParameters.containsKey(TestParameter.RETURN_ERROR)) {
            retVal = Boolean.parseBoolean(this.testParameters.get(TestParameter.RETURN_ERROR));
        }
        return retVal;
    }

    public int getCount() {
        int count = 0;
        if(this.testParameters.containsKey(TestParameter.COUNT)) {
            count = Integer.parseInt(this.testParameters.get(TestParameter.COUNT));
        }
        return count;
    }

    public String generateResponse() {
        final boolean returnError = getReturnError();
        final String op = getOperation();
        final int count = getCount();
        if(returnError) {
            return ERROR_RESPONSE;
        } else if(Operation.CHECK_MAC_ADDRESS.getName().equals(op)) {
            return generateCheckMacAddress(true);
        } else if(Operation.GET_MAC_ADDRESS_LIST.getName().equals(op)) {
            return generateList(count, GET_MAC_LIST_ROW);
        } else if(Operation.GET_LOCAL_USER_LIST.getName().equals(op)) {
            return generateList(count, GET_LOCAL_USER_LIST_ROW);
        } else if(Operation.GET_USER_INFO.getName().equals(op)) {
            return generateList(count, GET_USER_INFO_ROW);
        } else if(Operation.GET_OOB_USER_INFO.getName().equals(op)) {
            return generateList(count, GET_OOB_USER_INFO_ROW);
        } else if(Operation.GET_CLEAN_USER_INFO.getName().equals(op)) {
            return generateList(count, GET_CLEAN_USER_INFO_ROW);
        } else if(Operation.GET_REPORTS.getName().equals(op)) {
            return generateList(count, GET_REPORTS_ROW);
        } else if(Operation.GET_VERSION.getName().equals(op)) {
            return generateGetVersion();
        } else {
            return NO_ERROR;
        }
    }

    public String generateList(int count, String rowString) {
        if(count < 0) {
            count = 0;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<!--count=").append(count).append("-->");
        for(int i = 0; i < count; i++) {
            sb.append(rowString);
        }
        return sb.toString();
    }

    public String generateCheckMacAddress(boolean found) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!--found=").append(found).append("-->");
        if(found) {
            sb.append(CHECK_MAC_ADDRESS_ROW);
        }
        return sb.toString();
    }

    public String generateGetVersion() {
        return GET_VERSION_STRING;
    }

    public int getResposneCode() {
        int respCode = 0;
        if(this.testParameters.containsKey(TestParameter.RESPONSE_CODE)) {
            respCode = Integer.parseInt(this.testParameters.get(TestParameter.RESPONSE_CODE));
        }
        return respCode;
    }
}

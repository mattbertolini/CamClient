package com.mattbertolini.camclient;

import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mattbertolini.camclient.net.CamConnectionImpl;
import com.mattbertolini.camclient.net.CamConnectionException;
import com.mattbertolini.camclient.net.CamConnectionFactory;
import com.mattbertolini.camclient.request.CamRequest;
import com.mattbertolini.camclient.request.Operation;
import com.mattbertolini.camclient.request.RequestParameter;
import com.mattbertolini.camclient.response.CamResponse;

public class CamClient {
    private static final String FOUND_KEY = "found";
    private static final String VERSION_KEY = "version";

    private CamConnectionImpl conn;

    public CamClient(CamConnectionImpl conn) {
        this.conn = conn;
    }

    public static CamClient connect(String urlString, String username, String password) throws MalformedURLException {
        return new CamClient(CamConnectionFactory.getConnection(urlString, username, password));
    }

    public static CamClient connect(URL url, String username, String password) {
        return new CamClient(CamConnectionFactory.getConnection(url, username, password));
    }

    public static CamClient connect(String urlString, Proxy proxy, String username, String password) throws MalformedURLException {
        return new CamClient(CamConnectionFactory.getConnection(urlString, proxy, username, password));
    }

    public static CamClient connect(URL url, Proxy proxy, String username, String password) {
        return new CamClient(CamConnectionFactory.getConnection(url, proxy, username, password));
    }

    /**
     * Adds a MAC address to the Certified Devices list as an exempted device.
     * 
     * @param macAddress The MAC address to add. Must be in the following 
     * format: <code>01:23:45:67:89:AB</code>
     * @throws CamException If an error occurs making a request to the server.
     * @throws IllegalArgumentException If the MAC address is null.
     */
    public void addCleanMacAddress(String macAddress) throws CamException {
        this.addCleanMacAddress(macAddress, null);
    }
    
    /**
     * Adds a MAC address to the Certified Devices list as an exempted device.
     * 
     * @param macAddress The MAC address to add. Must be in the following 
     * format: <code>01:23:45:67:89:AB</code>
     * @param ssip The Clean Access Server IP address.
     * @throws CamException If an error occurred making the request to the 
     * server.
     * @throws IllegalArgumentException If the MAC address is null.
     */
    public void addCleanMacAddress(String macAddress, String ssip) throws CamException {
        if(macAddress == null) {
            throw new IllegalArgumentException("MAC address cannot be null.");
        }
        CamRequest req = new CamRequest(Operation.ADD_CLEAN_MAC_ADDRESS);
        req.addParameter(RequestParameter.MAC_ADDRESS, macAddress);
        if(ssip != null && !ssip.isEmpty()) {
            req.addParameter(RequestParameter.SERVER_IP_ADDRESS, ssip);
        }
        try {
            CamResponse resp = this.conn.submitRequest(req);
            if(resp.isError()) {
                throw new CamException("Error adding clean MAC address: " + resp.getErrorText());
            }
        } catch(CamConnectionException e) {
            throw new CamException(e);
        }
    }

    /**
     * Adds a new local user account to the Clean Access Manager.
     * 
     * @param username The user name of the new account.
     * @param password The password of the new account.
     * @param role The user role of the new account.
     * @throws CamException If an error occurred making the request to the 
     * server.
     * @throws IllegalArgumentException If the user name, password, or role 
     * arguments are null.
     */
    public void addLocaluser(String username, String password, String role) throws CamException {
        if(username == null) {
            throw new IllegalArgumentException("Username cannot be null.");
        }
        if(password == null) {
            throw new IllegalArgumentException("Password cannot be null.");
        }
        if(role == null) {
            throw new IllegalArgumentException("Role cannot be null.");
        }
        CamRequest req = new CamRequest(Operation.ADD_LOCAL_USER);
        req.addParameter(RequestParameter.USERNAME, username);
        req.addParameter(RequestParameter.USER_PASSWORD, password);
        req.addParameter(RequestParameter.ROLE_NAME, role);
        try {
            CamResponse resp = this.conn.submitRequest(req);
            if(resp.isError()) {
                throw new CamException("Error adding local user: " + resp.getErrorText());
            }
        } catch(CamConnectionException e) {
            throw new CamException(e);
        }
    }
    
    /**
     * Adds the given MAC address to the Device Filters list.
     * 
     * @param macAddress The MAC address to add. Must be in the following 
     * format: <code>01:23:45:67:89:AB</code>
     * @throws CamException If an error occurred making the request to the 
     * server.
     * @throws IllegalArgumentException If the given MAC address is null.
     */
    public void addMacAddress(String macAddress) throws CamException {
        this.addMacAddress(macAddress, null, null, null, null, null);
    }
    
    /**
     * Adds the given MAC address to the Device Filters list.
     * 
     * @param macAddress The MAC address to add. Must be in the following 
     * format: <code>01:23:45:67:89:AB</code>
     * @param ipAddress The IP address to use for the MAC address.
     * @param type The type of filter to associate with this MAC address. 
     * Supported values are DENY, ALLOW, USE_ROLE, CHECK, and IGNORE.
     * @param role The role name to place this MAC address in. Only needed if 
     * type USE_ROLE or CHECK is given.
     * @param description A text description to give the MAC address.
     * @param ssip The Clean Access Server IP address.
     * @throws CamException If an error occurred making the request to the 
     * server.
     * @throws IllegalArgumentException If the given MAC address is null.
     */
    public void addMacAddress(String macAddress, String ipAddress, Type type, String role, String description, String ssip) throws CamException {
        if(macAddress == null) {
            throw new IllegalArgumentException("MAC address cannot be null.");
        }
        CamRequest req = new CamRequest(Operation.ADD_MAC_ADDRESS);
        req.addParameter(RequestParameter.MAC_ADDRESS, macAddress);
        if(ipAddress != null && !ipAddress.isEmpty()) {
            req.addParameter(RequestParameter.IP_ADDRESS, ipAddress);
        }
        if(type != null) {
            req.addParameter(RequestParameter.TYPE, type.getName());
        }
        if(role != null && type != null && (type == Type.USE_ROLE || type == Type.CHECK)) {
            req.addParameter(RequestParameter.ROLE_NAME, role);
        }
        if(description != null && !description.isEmpty()) {
            req.addParameter(RequestParameter.DESCRIPTION, description);
        }
        if(ssip != null && !ssip.isEmpty()) {
            req.addParameter(RequestParameter.SERVER_IP_ADDRESS, ssip);
        }
        try {
            CamResponse resp = this.conn.submitRequest(req);
            if(resp.isError()) {
                throw new CamException("Error adding MAC address: " + resp.getErrorText());
            }
        } catch(CamConnectionException e) {
            throw new CamException(e);
        }
    }
    
    /**
     * Adds the given subnet to the Devices list.
     * 
     * @param subnet The subnet address to add.
     * @param mask The subnet mask in CIDR format (e.g. 16).
     * @throws CamException If an error occurred making the request to the 
     * server.
     * @throws IllegalArgumentException If the subnet address or subnet mask are 
     * null.
     */
    public void addSubnet(String subnet, String mask) throws CamException {
        this.addSubnet(subnet, mask, null, null, null, null);
    }
    
    /**
     * Adds the given subnet to the Devices list.
     * 
     * @param subnet The subnet address to add.
     * @param mask The subnet mask in CIDR format (e.g. 16).
     * @param type The filter type. Possible values are DENY, ALLOW, and 
     * USE_ROLE. The default value is DENY.
     * @param role The role to place the subnet in if type is USE_ROLE.
     * @param description A text description of the subnet.
     * @param ssip The Clean Access Server IP address.
     * @throws CamException If an error occurred making the request to the 
     * server.
     * @throws IllegalArgumentException If the subnet address or subnet mask are 
     * null.
     */
    public void addSubnet(String subnet, String mask, Type type, String role, String description, String ssip) throws CamException {
        if(subnet == null) {
            throw new IllegalArgumentException("Subnet cannot be null.");
        }
        if(mask == null) {
            throw new IllegalArgumentException("Mask cannot be null.");
        }
        CamRequest req = new CamRequest(Operation.ADD_SUBNET);
        req.addParameter(RequestParameter.SUBNET, subnet);
        req.addParameter(RequestParameter.SUBNET_MASK, mask);
        if(type != null) {
            req.addParameter(RequestParameter.TYPE, type.getName());
        }
        if(role != null && type != null && type == Type.USE_ROLE) {
            req.addParameter(RequestParameter.ROLE_NAME, role);
        }
        if(description != null && !description.isEmpty()) {
            req.addParameter(RequestParameter.DESCRIPTION, description);
        }
        if(ssip != null && !ssip.isEmpty()) {
            req.addParameter(RequestParameter.SERVER_IP_ADDRESS, ssip);
        }
        try {
            CamResponse resp = this.conn.submitRequest(req);
            if(resp.isError()) {
                throw new CamException("Error adding subnet: " + resp.getErrorText());
            }
        } catch(CamConnectionException e) {
            throw new CamException(e);
        }
    }
    
    /**
     * Bounces and out-of-band port in a switch given a switch ID and a port 
     * number.
     * 
     * @param switchId The ID of the switch as inserted in the switches table.
     * @param port The port in the switch to bounce.
     * @throws CamException If an error occurs making the request to the server.
     * @throws IllegalArgumentException If the switch ID is null.
     * @throws IllegalArgumentException If the port number is less than zero.
     */
    public void bouncePort(String switchId, int port) throws CamException {
        if(switchId == null) {
            throw new IllegalArgumentException("Switch ID cannot be null.");
        }
        if(port < 0) {
            throw new IllegalArgumentException("Switch port cannot be less than zero.");
        }
        CamRequest req = new CamRequest(Operation.BOUNCE_PORT);
        req.addParameter(RequestParameter.SWITCH_ID, switchId);
        req.addParameter(RequestParameter.SWITCH_PORT, Integer.toString(port));
        try {
            CamResponse resp = this.conn.submitRequest(req);
            if(resp.isError()) {
                throw new CamException("Error bouncing port: " + resp.getErrorText());
            }
        } catch(CamConnectionException e) {
            throw new CamException(e);
        }
    }

    /**
     * Bounces an Out-of-Band port of the switch where the given MAC address is 
     * currently associated.
     * 
     * @param macAddress The MAC address of the connected device to bounce.
     * @throws CamException If an error occurred making the request to the 
     * server.
     * @throws IllegalArgumentException If the given MAC address is null.
     */
    public void bouncePortByMacAddress(String macAddress) throws CamException {
        if(macAddress == null) {
            throw new IllegalArgumentException("MAC address cannot be null.");
        }
        CamRequest req = new CamRequest(Operation.BOUNCE_PORT_BY_MAC_ADDRESS);
        req.addParameter(RequestParameter.MAC_ADDRESS, macAddress);
        try {
            CamResponse resp = this.conn.submitRequest(req);
            if(resp.isError()) {
                throw new CamException("Error bouncing port by mac address: " + resp.getErrorText());
            }
        } catch(CamConnectionException e) {
            throw new CamException(e);
        }
    }
    
    /**
     * Changes the role of the logged in user with the given IP address.
     * 
     * @param ipAddress The IP address of the user.
     * @param role The role to change the user to.
     * @throws CamException If an error occurred making the request to the 
     * server.
     * @throws IllegalArgumentException If the given IP address or role are null.
     */
    public void changeLoggedInUserRole(String ipAddress, String role) throws CamException {
        if(ipAddress == null) {
            throw new IllegalArgumentException("IP address cannot be null.");
        }
        if(role == null) {
            throw new IllegalArgumentException("Role cannot be null.");
        }
        CamRequest req = new CamRequest(Operation.CHANGE_LOGGED_IN_USER_ROLE);
        req.addParameter(RequestParameter.IP_ADDRESS, ipAddress);
        req.addParameter(RequestParameter.ROLE_NAME, role);
        try {
            CamResponse resp = this.conn.submitRequest(req);
            if(resp.isError()) {
                throw new CamException("Error changing user role: " + resp.getErrorText());
            }
        } catch(CamConnectionException e) {
            throw new CamException(e);
        }
    }
    
    /**
     * Changes the access role of the logged in user with the given IP address. 
     * This method removes the given user from the Online Users list and adds 
     * their MAC address to the Device Filters list with the given role.
     * 
     * @param ipAddress The IP address of the user.
     * @param role The role to place the user in the Device Filters list.
     * @throws CamException If an error occurred making the request to the 
     * server.
     * @throws IllegalArgumentException If the given IP address or role are null.
     */
    public void changeUserRole(String ipAddress, String role) throws CamException {
        if(ipAddress == null) {
            throw new IllegalArgumentException("IP address cannot be null.");
        }
        if(role == null) {
            throw new IllegalArgumentException("Role cannot be null.");
        }
        CamRequest req = new CamRequest(Operation.CHANGE_USER_ROLE);
        req.addParameter(RequestParameter.IP_ADDRESS, ipAddress);
        req.addParameter(RequestParameter.ROLE_NAME, role);
        try {
            CamResponse resp = this.conn.submitRequest(req);
            if(resp.isError()) {
                throw new CamException("Error changing user role: " + resp.getErrorText());
            }
        } catch(CamConnectionException e) {
            throw new CamException(e);
        }
    }
    
    /**
     * Checks the Device Filters list to see if the given MAC address exists. 
     * If the MAC address is found, collects device information from the 
     * filters list and places it in a CamDevice object.
     * 
     * @param macAddress The MAC address to search for. Must match the display 
     * format <code>01:23:45:67:89:AB</code>.
     * @return If the MAC is found, the method returns a CamDevice object. If 
     * no device is found, null is returned. 
     * @throws CamException If an error occurred making the request to the 
     * server.
     * @throws IllegalArgumentException If the given MAC address is null.
     */
    public CamDevice checkMacAddress(String macAddress) throws CamException {
        return this.checkMacAddress(macAddress, null);
    }
    
    /**
     * Checks the Device Filters list to see if the given MAC address exists. 
     * If the MAC address is found, collects device information from the 
     * filters list and places it in a CamDevice object.
     * 
     * @param macAddress The MAC address to search for. Must match the display 
     * format <code>01:23:45:67:89:AB</code>.
     * @param ssip The Clean Access Server IP address.
     * @return If the MAC is found, the method returns a CamDevice object. If 
     * no device is found, null is returned. 
     * @throws CamException If an error occurred making the request to the 
     * server.
     * @throws IllegalArgumentException If the given MAC address is null.
     */
    public CamDevice checkMacAddress(String macAddress, String ssip) throws CamException {
        if(macAddress == null) {
            throw new IllegalArgumentException("MAC address cannot be null.");
        }
        CamRequest req = new CamRequest(Operation.CHECK_MAC_ADDRESS);
        req.addParameter(RequestParameter.MAC_ADDRESS, macAddress);
        if(ssip != null && !ssip.isEmpty()) {
            req.addParameter(RequestParameter.SERVER_IP_ADDRESS, ssip);
        }
        CamDevice retVal = null;
        try {
            CamResponse resp = this.conn.submitRequest(req);
            if(resp.isError()) {
                throw new CamException("Error checking MAC address: " + resp.getErrorText());
            }
            List<Map<String, String>> respData = resp.getResponseData();
            boolean isFound = Boolean.parseBoolean(respData.get(0).get(FOUND_KEY));
            if(isFound) {
                retVal = CamDeviceParser.parseMap(respData.get(1));
            }
        } catch(CamConnectionException e) {
            throw new CamException(e);
        }
        return retVal;
    }

    /**
     * Deletes all entries in the Certified Devices list.
     * 
     * @throws CamException If an error occurs when making the request to the 
     * server.
     */
    public void clearCertifiedList() throws CamException {
        CamRequest req = new CamRequest(Operation.CLEAR_CERTIFIED_LIST);
        try {
            CamResponse resp = this.conn.submitRequest(req);
            if(resp.isError()) {
                throw new CamException("Error clearing certified list: " + resp.getErrorText());
            }
        } catch(CamConnectionException e) {
            throw new CamException(e);
        }
    }
    
    /**
     * Deletes all local user accounts from the Clean Access Manager.
     * 
     * @throws CamException If an error occurs when making the request to the 
     * server.
     */
    public void deleteAllLocalUsers() throws CamException {
        CamRequest req = new CamRequest(Operation.DELETE_LOCAL_USER);
        req.addParameter(RequestParameter.QUERY_TYPE, "all");
        req.addParameter(RequestParameter.QUERY_VALUE, "");
        try {
            CamResponse resp = this.conn.submitRequest(req);
            if(resp.isError()) {
                throw new CamException("Error deleting all local users: " + resp.getErrorText());
            }
        } catch(CamConnectionException e) {
            throw new CamException(e);
        }
    }
    
    /**
     * Deletes a local user account from the Clean Access Manager.
     * 
     * @param username The username of the user to delete.
     * @throws CamException If an error occurs when making the request to the 
     * server.
     * @throws IllegalArgumentException If the given username is null.
     */
    public void deleteLocalUser(String username) throws CamException {
        if(username == null) {
            throw new IllegalArgumentException("Username cannot be null.");
        }
        CamRequest req = new CamRequest(Operation.DELETE_LOCAL_USER);
        req.addParameter(RequestParameter.QUERY_TYPE, "name");
        req.addParameter(RequestParameter.QUERY_VALUE, username);
        try {
            CamResponse resp = this.conn.submitRequest(req);
            if(resp.isError()) {
                throw new CamException("Error deleting local user: " + resp.getErrorText());
            }
        } catch(CamConnectionException e) {
            throw new CamException(e);
        }
    }
    
    /**
     * Gets the version number of the CAM.
     * 
     * @return A String containing the version number.
     * @throws CamException If an error occurred making the request to the 
     * server.
     */
    public String getCamVersion() throws CamException {
        String retVal = null;
        CamRequest req = new CamRequest(Operation.GET_VERSION);
        try {
            CamResponse resp = this.conn.submitRequest(req);
            if(resp.isError()) {
                throw new CamException("Error getting version: " + resp.getErrorText());
            }
            retVal = resp.getResponseData().get(0).get(VERSION_KEY);
        } catch(CamConnectionException e) {
            throw new CamException(e);
        }
        return retVal;
    }

    /**
     * Gets the list of local user accounts currently in the CAM.
     * 
     * @return A list of CamLocalUser objects containing user name and role 
     * name.
     * @throws CamException If an error occurs making a request to the server.
     */
    public List<CamLocalUser> getLocalUserList() throws CamException {
        CamRequest req = new CamRequest(Operation.GET_LOCAL_USER_LIST);
        List<CamLocalUser> retList = new ArrayList<CamLocalUser>();
        try {
            CamResponse resp = this.conn.submitRequest(req);
            if(resp.isError()) {
                throw new CamException("Error retieving local user list: " + resp.getErrorText());
            }
            // TODO: Break this code out to separate method/class.
            List<Map<String, String>> respData = resp.getResponseData();
            for(Map<String, String> row : respData) {
                String name = row.get("name");
                String role = row.get("role");
                CamLocalUser user = new CamLocalUser(name, role);
                retList.add(user);
            }
        } catch(CamConnectionException e) {
            throw new CamException(e);
        }
        return retList;
    }

    /**
     * Retrieves the entire Device Filters list.
     * 
     * @return An ArrayList containing CamDevice objects representing each 
     * record in the Device Filters list. If no devices are found, an empty 
     * list is returned.
     * @throws CamException If an error occurs when making the request to the 
     * server.
     */
    public List<CamDevice> getMacAddressList() throws CamException {
        CamRequest req = new CamRequest(Operation.GET_MAC_ADDRESS_LIST);
        List<CamDevice> retList = new ArrayList<CamDevice>();
        try {
            CamResponse resp = this.conn.submitRequest(req);
            if(resp.isError()) {
                throw new CamException("Error getting MAC address list: " + resp.getErrorText());
            }
            retList = CamDeviceParser.parseList(resp.getResponseData());
        } catch(CamConnectionException e) {
            throw new CamException(e);
        }
        return retList;
    }
    
    /**
     * Gets the user agent string that is sent to the CAM with every request.
     * 
     * @return A string representing the user agent of the CAM connection.
     */
    public String getUserAgent() {
        return this.conn.getUserAgent();
    }

    /**
     * Ends the active session of the out-of-band user with the given MAC 
     * address and removes the user from the Out-of-Band Online Users list.
     * 
     * @param macAddress The MAC address of the out-of-band user to kick. Must 
     * be in the following format: <code>01:23:45:67:89:AB</code>
     * @throws CamException If an error occurred making the request to the 
     * server.
     * @throws IllegalArgumentException If the given MAC address is null.
     */
    public void kickOutOfBandUser(String macAddress) throws CamException {
        if(macAddress == null) {
            throw new IllegalArgumentException("MAC address cannot be null.");
        }
        CamRequest req = new CamRequest(Operation.KICK_OOB_USER);
        req.addParameter(RequestParameter.MAC_ADDRESS, macAddress);
        try {
            CamResponse resp = this.conn.submitRequest(req);
            if(resp.isError()) {
                throw new CamException("Error kicking OOB user: " + resp.getErrorText());
            }
        } catch(CamConnectionException e) {
            throw new CamException(e);
        }
    }
    
    /**
     * Ends the active session of the in-band user with the given IP address 
     * and removes the user from the In-Band Online Users list.
     * 
     * @param ipAddress The IP address of the in-band user to kick.
     * @throws CamException If an error occurred making the request to the 
     * server.
     * @throws IllegalArgumentException If the given IP address is null.
     */
    public void kickUser(String ipAddress) throws CamException {
        if(ipAddress == null) {
            throw new IllegalArgumentException("IP address cannot be null.");
        }
        CamRequest req = new CamRequest(Operation.KICK_USER);
        req.addParameter(RequestParameter.IP_ADDRESS, ipAddress);
        try {
            CamResponse resp = this.conn.submitRequest(req);
            if(resp.isError()) {
                throw new CamException("Error kicking user: " + resp.getErrorText());
            }
        } catch(CamConnectionException e) {
            throw new CamException(e);
        }
    }
    
    /**
     * Ends the active session of the user with the given MAC address and 
     * removes the user from the In-Band Online Users list.
     * 
     * @param macAddress The MAC address of the out-of-band user to kick. Must 
     * be in the following format: <code>01:23:45:67:89:AB</code>
     * @throws CamException If an error occurred making the request to the 
     * server.
     * @throws IllegalArgumentException If the given MAC address is null.
     */
    public void kickUserByMacAddress(String macAddress) throws CamException {
        if(macAddress == null) {
            throw new IllegalArgumentException("MAC address cannot be null.");
        }
        CamRequest req = new CamRequest(Operation.KICK_USER_BY_MAC_ADDRESS);
        req.addParameter(RequestParameter.MAC_ADDRESS, macAddress);
        try {
            CamResponse resp = this.conn.submitRequest(req);
            if(resp.isError()) {
                throw new CamException("Error kicking user: " + resp.getErrorText());
            }
        } catch(CamConnectionException e) {
            throw new CamException(e);
        }
    }

    /**
     * Removes the given MAC address from the Certified Devices list.
     * 
     * @param macAddress The MAC address to remove. Must be in the following 
     * format: <code>01:23:45:67:89:AB</code>.
     * @throws CamException If an error occurs making the request to the server.
     * @throws IllegalArgumentException If the MAC address is null.
     */
    public void removeCleanMacAddress(String macAddress) throws CamException {
        this.removeCleanMacAddress(macAddress, null);
    }
    
    /**
     * Removes the given MAC address from the Certified Devices list.
     * 
     * @param macAddress The MAC address to remove. Must be in the following 
     * format: <code>01:23:45:67:89:AB</code>.
     * @param ssip The Clean Access Server IP address. 
     * @throws CamException If an error occurs making the request to the server.
     * @throws IllegalArgumentException If the MAC address is null.
     */
    public void removeCleanMacAddress(String macAddress, String ssip) throws CamException {
        if(macAddress == null) {
            throw new IllegalArgumentException("MAC address cannot be null.");
        }
        CamRequest req = new CamRequest(Operation.REMOVE_CLEAN_MAC_ADDRESS);
        req.addParameter(RequestParameter.MAC_ADDRESS, macAddress);
        if(ssip != null && !ssip.isEmpty()) {
            req.addParameter(RequestParameter.SERVER_IP_ADDRESS, ssip);
        }
        try {
            CamResponse resp = this.conn.submitRequest(req);
            if(resp.isError()) {
                throw new CamException("Error removing clean MAC address: " + resp.getErrorText());
            }
        } catch(CamConnectionException e) {
            throw new CamException(e);
        }
    }
    
    /**
     * Removes the given MAC address from the Device Filters list.
     * 
     * @param macAddress The MAC address to remove. Must be in the following 
     * format: <code>01:23:45:67:89:AB</code>
     * @throws CamException If an error occurred making the request to the 
     * server.
     */
    public void removeMacAddress(String macAddress) throws CamException {
        this.removeMacAddress(macAddress, null);
    }

    /**
     * Removes the given MAC address from the Device Filters list.
     * 
     * @param macAddress The MAC address to remove. Must be in the following 
     * format: <code>01:23:45:67:89:AB</code>
     * @param ssip The Clean Access Server IP address.
     * @throws CamException If an error occurred making the request to the 
     * server.
     * @throws IllegalArgumentException If the given MAC address is null.
     */
    public void removeMacAddress(String macAddress, String ssip) throws CamException {
        if(macAddress == null) {
            throw new IllegalArgumentException("MAC address cannot be null.");
        }
        CamRequest req = new CamRequest(Operation.REMOVE_MAC_ADDRESS);
        req.addParameter(RequestParameter.MAC_ADDRESS, macAddress);
        if(ssip != null && !ssip.isEmpty()) {
            req.addParameter(RequestParameter.SERVER_IP_ADDRESS, ssip);
        }
        try {
            CamResponse resp = this.conn.submitRequest(req);
            if(resp.isError()) {
                throw new CamException("Error removing MAC address: " + resp.getErrorText());
            }
        } catch(CamConnectionException e) {
            throw new CamException(e);
        }
    }

    /**
     * Deletes all entries from the Device Filters list.
     * 
     * @throws CamException If an error occurs when making the request to the 
     * server.
     */
    public void removeMacAddressList() throws CamException {
        CamRequest req = new CamRequest(Operation.REMOVE_MAC_ADDRESS_LIST);
        try {
            CamResponse resp = this.conn.submitRequest(req);
            if(resp.isError()) {
                throw new CamException("Error removing MAC address list: " + resp.getErrorText());
            }
        } catch(CamConnectionException e) {
            throw new CamException(e);
        }
    }
    
    /**
     * Removes a subnet from the Devices list.
     * 
     * @param subnet The subnet address to remove.
     * @param mask The subnet mask in CIDR format (e.g. 16)
     * @param ssip The Clean Access Server IP address.
     * @throws CamException If an error occurred making the request to the 
     * server.
     * @throws IllegalArgumentException If the given subnet address or mask is null.
     */
    public void removeSubnet(String subnet, String mask, String ssip) throws CamException {
        if(subnet == null) {
            throw new IllegalArgumentException("Subnet cannot be null.");
        }
        if(mask == null) {
            throw new IllegalArgumentException("Mask cannot be null.");
        }
        CamRequest req = new CamRequest(Operation.REMOVE_SUBNET);
        req.addParameter(RequestParameter.SUBNET, subnet);
        req.addParameter(RequestParameter.SUBNET_MASK, mask);
        if(ssip != null && !ssip.isEmpty()) {
            req.addParameter(RequestParameter.SERVER_IP_ADDRESS, ssip);
        }
        try {
            CamResponse resp = this.conn.submitRequest(req);
            if(resp.isError()) {
                throw new CamException("Error removing subnet: " + resp.getErrorText());
            }
        } catch(CamConnectionException e) {
            throw new CamException(e);
        }
    }
    
    /**
     * Renews the session timeout of the user with the given IP address by one 
     * session.
     * 
     * @param ipAddress The IP address of the user to renew the session.
     * @throws CamException If an error occurred making the request to the 
     * server.
     * @throws IllegalArgumentException If the given IP address is null.
     */
    public void renewUserSessionTime(String ipAddress) throws CamException {
        if(ipAddress == null) {
            throw new IllegalArgumentException("IP address cannot be null.");
        }
        CamRequest req = new CamRequest(Operation.RENEW_USER_SESSION_TIME);
        req.addParameter(RequestParameter.LIST, ipAddress);
        try {
            CamResponse resp = this.conn.submitRequest(req);
            if(resp.isError()) {
                throw new CamException("Error renewing user session time: " + resp.getErrorText());
            }
        } catch(CamConnectionException e) {
            throw new CamException(e);
        }
    }
    
    /**
     * Set the user agent string that will be sent to the CAM with every 
     * request.
     * 
     * @param userAgent The string to set for the user agent.
     * @throws IllegalArgumentException If the string is null.
     */
    public void setUserAgent(final String userAgent) {
        this.conn.setUserAgent(userAgent);
    }
    
    /**
     * Updates the given subnet in the Devices list.
     * 
     * @param subnet The subnet address to update.
     * @param mask The subnet mask in CIDR format(e.g. 16)
     * @throws CamException If an error occurred making the request to the 
     * server.
     * @throws IllegalArgumentException If the given subnet address or mask are 
     * null.
     */
    public void updateSubnet(String subnet, String mask) throws CamException {
        this.updateSubnet(subnet, mask, null, null, null, null);
    }
    
    /**
     * Updates the given subnet in the Devices list.
     * 
     * @param subnet The subnet address to update.
     * @param mask The subnet mask in CIDR format (e.g. 16).
     * @param type The filter type to update the subnet with. Possible values 
     * are DENY, ALLOW, and USE_ROLE. The default value is DENY.
     * @param role The role to place the subnet in if the filter type is 
     * USE_ROLE.
     * @param description The description of the subnet.
     * @param ssip The Clean Access Server IP address.
     * @throws CamException If an error occurred making the request to the 
     * server.
     * @throws IllegalArgumentException If the given subnet address or mask are 
     * null.
     */
    public void updateSubnet(String subnet, String mask, Type type, String role, String description, String ssip) throws CamException {
        if(subnet == null) {
            throw new IllegalArgumentException("Subnet cannot be null.");
        }
        if(mask == null) {
            throw new IllegalArgumentException("Mask cannot be null.");
        }
        CamRequest req = new CamRequest(Operation.UPDATE_SUBNET);
        req.addParameter(RequestParameter.SUBNET, subnet);
        req.addParameter(RequestParameter.SUBNET_MASK, mask);
        if(type != null) {
            req.addParameter(RequestParameter.TYPE, type.getName());
        }
        if(role != null && type != null && type == Type.USE_ROLE) {
            req.addParameter(RequestParameter.ROLE_NAME, role);
        }
        if(description != null && !description.isEmpty()) {
            req.addParameter(RequestParameter.DESCRIPTION, description);
        }
        if(ssip != null && !ssip.isEmpty()) {
            req.addParameter(RequestParameter.SERVER_IP_ADDRESS, ssip);
        }
        try {
            CamResponse resp = this.conn.submitRequest(req);
            if(resp.isError()) {
                throw new CamException("Error updating subnet: " + resp.getErrorText());
            }
        } catch(CamConnectionException e) {
            throw new CamException(e);
        }
    }
}

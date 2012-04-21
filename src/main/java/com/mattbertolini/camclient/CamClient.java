package com.mattbertolini.camclient;

import java.net.InetAddress;
import java.util.List;

public interface CamClient {
    /**
     * Adds a MAC address to the Certified Devices list as an exempted device.
     * 
     * @param macAddress The MAC address to add.
     * @throws CamClientException If an error occurs making a request to the server.
     * @throws IllegalArgumentException If the MAC address is null.
     */
    void addCleanMacAddress(MacAddress macAddress) throws CamClientException;
    
    /**
     * Adds a MAC address to the Certified Devices list as an exempted device.
     * 
     * @param macAddress The MAC address to add.
     * @param ssip The Clean Access Server IP address.
     * @throws CamClientException If an error occurred making the request to the
     * server.
     * @throws IllegalArgumentException If the MAC address is null.
     */
    void addCleanMacAddress(MacAddress macAddress, InetAddress ssip) throws CamClientException;

    /**
     * Adds a new local user account to the Clean Access Manager.
     * 
     * @param username The user name of the new account.
     * @param password The password of the new account.
     * @param role The user role of the new account.
     * @throws CamClientException If an error occurred making the request to the
     * server.
     * @throws IllegalArgumentException If the user name, password, or role 
     * arguments are null.
     */
    void addLocalUser(String username, String password, String role) throws CamClientException;
    
    /**
     * Adds the given MAC address to the Device Filters list.
     * 
     * @param macAddress The MAC address to add.
     * @throws CamClientException If an error occurred making the request to the
     * server.
     * @throws IllegalArgumentException If the given MAC address is null.
     */
    void addMacAddress(MacAddress macAddress) throws CamClientException;
    
    /**
     * Adds the given MAC address to the Device Filters list.
     * 
     * @param macAddress The MAC address to add.
     * @param ipAddress The IP address to use for the MAC address.
     * @param type The type of filter to associate with this MAC address. 
     * Supported values are DENY, ALLOW, USE_ROLE, CHECK, and IGNORE.
     * @param role The role name to place this MAC address in. Only needed if 
     * type USE_ROLE or CHECK is given.
     * @param description A text description to give the MAC address.
     * @param ssip The Clean Access Server IP address.
     * @throws CamClientException If an error occurred making the request to the
     * server.
     * @throws IllegalArgumentException If the given MAC address is null.
     */
    void addMacAddress(MacAddress macAddress, InetAddress ipAddress, Type type, String role, String description, InetAddress ssip) throws CamClientException;
    
    /**
     * Adds the given subnet to the Devices list.
     * 
     * @param subnet The subnet address to add.
     * @param mask The subnet mask in CIDR format (e.g. 16).
     * @throws CamClientException If an error occurred making the request to the
     * server.
     * @throws IllegalArgumentException If the subnet address or subnet mask are 
     * null.
     */
    void addSubnet(String subnet, String mask) throws CamClientException;
    
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
     * @throws CamClientException If an error occurred making the request to the
     * server.
     * @throws IllegalArgumentException If the subnet address or subnet mask are 
     * null.
     */
    void addSubnet(String subnet, String mask, Type type, String role, String description, InetAddress ssip) throws CamClientException;
    
    /**
     * Bounces and out-of-band port in a switch given a switch ID and a port 
     * number.
     * 
     * @param switchId The ID of the switch as inserted in the switches table.
     * @param port The port in the switch to bounce.
     * @throws CamClientException If an error occurs making the request to the server.
     * @throws IllegalArgumentException If the switch ID is null or the port number is less than zero.
     */
    void bouncePort(String switchId, int port) throws CamClientException;

    /**
     * Bounces an Out-of-Band port of the switch where the given MAC address is 
     * currently associated.
     * 
     * @param macAddress The MAC address of the connected device to bounce.
     * @throws CamClientException If an error occurred making the request to the
     * server.
     * @throws IllegalArgumentException If the given MAC address is null.
     */
    void bouncePortByMacAddress(MacAddress macAddress) throws CamClientException;
    
    /**
     * Changes the role of the logged in user with the given IP address.
     * 
     * @param ipAddress The IP address of the user.
     * @param role The role to change the user to.
     * @throws CamClientException If an error occurred making the request to the
     * server.
     * @throws IllegalArgumentException If the given IP address or role are null.
     */
    void changeLoggedInUserRole(InetAddress ipAddress, String role) throws CamClientException;
    
    /**
     * Changes the access role of the logged in user with the given IP address. 
     * This method removes the given user from the Online Users list and adds 
     * their MAC address to the Device Filters list with the given role.
     * 
     * @param ipAddress The IP address of the user.
     * @param role The role to place the user in the Device Filters list.
     * @throws CamClientException If an error occurred making the request to the
     * server.
     * @throws IllegalArgumentException If the given IP address or role are null.
     */
    void changeUserRole(InetAddress ipAddress, String role) throws CamClientException;
    
    /**
     * Checks the Device Filters list to see if the given MAC address exists. 
     * If the MAC address is found, collects device information from the 
     * filters list and places it in a CamDevice object.
     * 
     * @param macAddress The MAC address to search for.
     * @return If the MAC is found, the method returns a CamDevice object. If 
     * no device is found, null is returned. 
     * @throws CamClientException If an error occurred making the request to the
     * server.
     * @throws IllegalArgumentException If the given MAC address is null.
     */
    CamDevice checkMacAddress(MacAddress macAddress) throws CamClientException;
    
    /**
     * Checks the Device Filters list to see if the given MAC address exists. 
     * If the MAC address is found, collects device information from the 
     * filters list and places it in a CamDevice object.
     * 
     * @param macAddress The MAC address to search for.
     * @param ssip The Clean Access Server IP address.
     * @return If the MAC is found, the method returns a CamDevice object. If 
     * no device is found, null is returned. 
     * @throws CamClientException If an error occurred making the request to the
     * server.
     * @throws IllegalArgumentException If the given MAC address is null.
     */
    CamDevice checkMacAddress(MacAddress macAddress, InetAddress ssip) throws CamClientException;

    /**
     * Deletes all entries in the Certified Devices list.
     * 
     * @throws CamClientException If an error occurs when making the request to the
     * server.
     */
    void clearCertifiedList() throws CamClientException;
    
    /**
     * Deletes all local user accounts from the Clean Access Manager.
     * 
     * @throws CamClientException If an error occurs when making the request to the
     * server.
     */
    void deleteAllLocalUsers() throws CamClientException;
    
    /**
     * Deletes a local user account from the Clean Access Manager.
     * 
     * @param username The username of the user to delete.
     * @throws CamClientException If an error occurs when making the request to the
     * server.
     * @throws IllegalArgumentException If the given username is null.
     */
    void deleteLocalUser(String username) throws CamClientException;
    
    /**
     * Gets the version number of the CAM.
     * 
     * @return A String containing the version number.
     * @throws CamClientException If an error occurred making the request to the
     * server.
     */
    String getCamVersion() throws CamClientException;

    /**
     * Gets the list of local user accounts currently in the CAM.
     * 
     * @return A list of CamLocalUser objects containing user name and role 
     * name.
     * @throws CamClientException If an error occurs making a request to the server.
     */
    List<CamLocalUser> getLocalUserList() throws CamClientException;

    /**
     * Retrieves the entire Device Filters list.
     * 
     * @return An ArrayList containing CamDevice objects representing each 
     * record in the Device Filters list. If no devices are found, an empty 
     * list is returned.
     * @throws CamClientException If an error occurs when making the request to the
     * server.
     */
    List<CamDevice> getMacAddressList() throws CamClientException;
    
    /**
     * Gets the user agent string that is sent to the CAM with every request.
     * 
     * @return A string representing the user agent of the CAM connection.
     */
    String getUserAgent();

    /**
     * Ends the active session of the out-of-band user with the given MAC 
     * address and removes the user from the Out-of-Band Online Users list.
     * 
     * @param macAddress The MAC address of the out-of-band user to kick. Must 
     * be in the following format: <code>01:23:45:67:89:AB</code>
     * @throws CamClientException If an error occurred making the request to the
     * server.
     * @throws IllegalArgumentException If the given MAC address is null.
     */
    void kickOutOfBandUser(String macAddress) throws CamClientException;
    
    /**
     * Ends the active session of the in-band user with the given IP address 
     * and removes the user from the In-Band Online Users list.
     * 
     * @param ipAddress The IP address of the in-band user to kick.
     * @throws CamClientException If an error occurred making the request to the
     * server.
     * @throws IllegalArgumentException If the given IP address is null.
     */
    void kickUser(InetAddress ipAddress) throws CamClientException;
    
    /**
     * Ends the active session of the user with the given MAC address and 
     * removes the user from the In-Band Online Users list.
     * 
     * @param macAddress The MAC address of the out-of-band user to kick.
     * @throws CamClientException If an error occurred making the request to the
     * server.
     * @throws IllegalArgumentException If the given MAC address is null.
     */
    void kickUserByMacAddress(MacAddress macAddress) throws CamClientException;

    /**
     * Removes the given MAC address from the Certified Devices list.
     * 
     * @param macAddress The MAC address to remove.
     * @throws CamClientException If an error occurs making the request to the server.
     * @throws IllegalArgumentException If the MAC address is null.
     */
    void removeCleanMacAddress(MacAddress macAddress) throws CamClientException;
    
    /**
     * Removes the given MAC address from the Certified Devices list.
     * 
     * @param macAddress The MAC address to remove.
     * @param ssip The Clean Access Server IP address. 
     * @throws CamClientException If an error occurs making the request to the server.
     * @throws IllegalArgumentException If the MAC address is null.
     */
    void removeCleanMacAddress(MacAddress macAddress, InetAddress ssip) throws CamClientException;
    
    /**
     * Removes the given MAC address from the Device Filters list.
     * 
     * @param macAddress The MAC address to remove.
     * @throws CamClientException If an error occurred making the request to the
     * server.
     */
    void removeMacAddress(MacAddress macAddress) throws CamClientException;

    /**
     * Removes the given MAC address from the Device Filters list.
     * 
     * @param macAddress The MAC address to remove.
     * @param ssip The Clean Access Server IP address.
     * @throws CamClientException If an error occurred making the request to the
     * server.
     * @throws IllegalArgumentException If the given MAC address is null.
     */
    void removeMacAddress(MacAddress macAddress, InetAddress ssip) throws CamClientException;

    /**
     * Deletes all entries from the Device Filters list.
     * 
     * @throws CamClientException If an error occurs when making the request to the
     * server.
     */
    void removeMacAddressList() throws CamClientException;
    
    /**
     * Removes a subnet from the Devices list.
     * 
     * @param subnet The subnet address to remove.
     * @param mask The subnet mask in CIDR format (e.g. 16)
     * @param ssip The Clean Access Server IP address.
     * @throws CamClientException If an error occurred making the request to the
     * server.
     * @throws IllegalArgumentException If the given subnet address or mask is null.
     */
    void removeSubnet(String subnet, String mask, InetAddress ssip) throws CamClientException;
    
    /**
     * Renews the session timeout of the user with the given IP address by one 
     * session.
     * 
     * @param ipAddress The IP address of the user to renew the session.
     * @throws CamClientException If an error occurred making the request to the
     * server.
     * @throws IllegalArgumentException If the given IP address is null.
     */
    void renewUserSessionTime(InetAddress ipAddress) throws CamClientException;
    
    /**
     * Set the user agent string that will be sent to the CAM with every 
     * request.
     * 
     * @param userAgent The string to set for the user agent.
     * @throws IllegalArgumentException If the string is null.
     */
    void setUserAgent(final String userAgent);
    
    /**
     * Updates the given subnet in the Devices list.
     * 
     * @param subnet The subnet address to update.
     * @param mask The subnet mask in CIDR format(e.g. 16)
     * @throws CamClientException If an error occurred making the request to the
     * server.
     * @throws IllegalArgumentException If the given subnet address or mask are 
     * null.
     */
    void updateSubnet(String subnet, String mask) throws CamClientException;
    
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
     * @throws CamClientException If an error occurred making the request to the
     * server.
     * @throws IllegalArgumentException If the given subnet address or mask are 
     * null.
     */
    void updateSubnet(String subnet, String mask, Type type, String role, String description, InetAddress ssip) throws CamClientException;
}

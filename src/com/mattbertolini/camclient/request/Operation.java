package com.mattbertolini.camclient.request;

/**
 * @author Matt Bertolini
 */
public enum Operation {
	ADD_CLEAN_MAC_ADDRESS("addcleanmac"),
	ADD_LOCAL_USER("addlocaluser"),
	ADD_MAC_ADDRESS("addmac"),
	ADD_SUBNET("addsubnet"),
	ADMIN_LOGIN("adminlogin"),
	ADMIN_LOGOUT("adminlogout"),
	BOUNCE_PORT("bounceport"),
	BOUNCE_PORT_BY_MAC_ADDRESS("bounceportbymac"),
	CHANGE_LOGGED_IN_USER_ROLE("cangeloggedinuserrole"),
	CHANGE_USER_ROLE("changeuserrole"),
	CHECK_MAC_ADDRESS("checkmac"),
	CLEAR_CERTIFIED_LIST("clearcertified"),
	DELETE_LOCAL_USER("deletelocaluser"),
	GET_CLEAN_USER_INFO("getcleanuserinfo"),
	GET_LOCAL_USER_LIST("getlocaluserlist"),
	GET_MAC_ADDRESS_LIST("getmaclist"),
	GET_OOB_USER_INFO("getoobuserinfo"),
	GET_REPORTS("getreports"),
	GET_USER_INFO("getuserinfo"),
	GET_VERSION("getversion"),
	KICK_OOB_USER("kickoobuser"),
	KICK_USER("kickuser"),
	KICK_USER_BY_MAC_ADDRESS("kickuserbymac"),
	QUERY_USER_SESSION_TIME("queryuserstime"),
	REMOVE_CLEAN_MAC_ADDRESS("removecleanmac"),
	REMOVE_MAC_ADDRESS("removemac"),
	REMOVE_MAC_ADDRESS_LIST("removemaclist"),
	REMOVE_SUBNET("removesubnet"),
	RENEW_USER_SESSION_TIME("renewuserstime"),
	UPDATE_SUBNET("updatesubnet");
	
	private String name;
	
	private Operation(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}

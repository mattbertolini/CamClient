package com.mattbertolini.camclient.request;

import com.mattbertolini.camclient.net.Parameter;

/**
 * @author Matt Bertolini
 */
public enum ReportParameter implements Parameter {
	STATUS("status"),
	USER("user"),
	AGENT_TYPE("agentType"),
	IP_ADDRESS("ip"),
	MAC_ADDRESS("mac"),
	OPERATING_SYSTEM("os"),
	TIME_RANGE("timeRange"),
	SHOW_TEXT("showText"),
	ORDER_BY("orderBy"),
	ORDER_DIRECTION("orderDir"),
	INSTALLED_SOFTWARE("instSoft"),
	REQUIREMENT_NAME("reqName"),
	REQUIREMENT_STATUS("reqStatus");
	
	private String name;
	
	private ReportParameter(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}

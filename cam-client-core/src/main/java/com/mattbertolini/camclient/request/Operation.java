/*
 * Copyright (c) 2012, Matthew Bertolini
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *
 *     * Neither the name of CamClient nor the names of its contributors may be
 *       used to endorse or promote products derived from this software without
 *       specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

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

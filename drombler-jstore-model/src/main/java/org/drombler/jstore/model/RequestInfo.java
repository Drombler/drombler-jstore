package org.drombler.jstore.model;


import java.security.Principal;

public class RequestInfo {
    private Principal userPrincipal;

    public Principal getUserPrincipal() {
        return userPrincipal;
    }

    public void setUserPrincipal(Principal userPrincipal) {
        this.userPrincipal = userPrincipal;
    }

    public String getUsername(){
        return userPrincipal != null ? userPrincipal.getName() : null;
    }
}

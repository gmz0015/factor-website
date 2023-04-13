package org.noah.entity;

import org.apache.shiro.authc.AuthenticationToken;

public class AuthToken implements AuthenticationToken {

    private static final long serialVersionUID = 1L;

    // accessToken
    private String token;

    public AuthToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

}

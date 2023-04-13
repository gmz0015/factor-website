package org.noah.service;

import io.jsonwebtoken.Claims;

import java.util.Date;

public interface AuthService {

    public String create(Object entity);

    public Claims parse(String token);

}
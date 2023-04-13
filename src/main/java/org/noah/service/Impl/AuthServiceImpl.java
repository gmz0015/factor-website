package org.noah.service.Impl;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import org.noah.service.AuthService;
import org.noah.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private String JWT_KEY;

    private String JWT_ISSUER;

    private String JWT_EXPIRY_TIME;

    private String JWT_SIGN_METHOD;

    private long ttlMillis;

    @PostConstruct
    private void init() {
        JWT_KEY = "022bdc63c3c5a45879ee6581508b9d03adfec4a4658c0ab3d722e50c91a351c42c231cf43bb8f86998202bd301ec52239a74fc0c9a9aeccce604743367c9646b";
        JWT_ISSUER = "NOAH";
        JWT_EXPIRY_TIME = "2";
        JWT_SIGN_METHOD = "SHA";
        ttlMillis = Integer.valueOf(JWT_EXPIRY_TIME) * 3600 * 1000;
    }

    /**
     * 由字符串生成加密key
     *
     * @return
     * @throws Exception
     */
    private SecretKey createSecretKey(String salt, String method) {
        return JwtUtils.createSecretKey(salt, method);
    }

    /**
     * 创建jwt
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @Override
    public String create(Object entity) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user", entity);
        SecretKey secretKey = createSecretKey(JWT_KEY, JWT_SIGN_METHOD);
        String entityJsonStr = JSON.toJSONString(entity);
        return JwtUtils.createJWT(claims, UUID.randomUUID().toString(), JWT_ISSUER, entityJsonStr, secretKey,
                ttlMillis);
    }

    /**
     * 解密jwt
     *
     * @param token
     * @return
     * @throws Exception
     */
    @Override
    public Claims parse(String token) {
        SecretKey secretKey = createSecretKey(JWT_KEY, JWT_SIGN_METHOD);
        return JwtUtils.parseJWT(token, secretKey);
    }

}

package org.noah.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Random;

@Component
public class JwtUtils {

    /**
     * 加盐参数
     */
    public final static String hashAlgorithmName = "MD5";

    public final static String defaultMethod = "SHA";

    /**
     * 循环次数
     */
    public final static int hashIterations = 10;

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    public static SecretKey createSecretKey(String key, String method) {
        // 生成签名的时候使用的秘钥secret，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。
        // 一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        byte[] encodedKey = Base64.getDecoder().decode(key);
        SecretKeySpec secretKeySpec = new SecretKeySpec(encodedKey, 0, encodedKey.length, method);
        return secretKeySpec;
    }
    public static SecretKey createSecretKey(String key) {
        // 生成签名的时候使用的秘钥secret，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。
        // 一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        byte[] encodedKey = Base64.getDecoder().decode(key);
        SecretKeySpec secretKeySpec = new SecretKeySpec(encodedKey, 0, encodedKey.length, defaultMethod);
        return secretKeySpec;
    }

    /**
     * 创建jwt
     *
     * @param claims
     * @param id
     * @param issuer
     * @param subject
     * @param secretKey
     * @param ttlMillis
     * @return
     * @throws Exception
     */
    public static String createJWT(Map<String, Object> claims, String id, String issuer, String subject,
                                   SecretKey secretKey, long ttlMillis) {

        // 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 生成JWT的时间
        Date currentDate = new Date();

        // 创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）

        // 下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder() // 这里其实就是new一个JwtBuilder，设置jwt的body
                .setClaims(claims) // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setId(id) // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setIssuedAt(currentDate) // iat: jwt的签发时间
                .setIssuer(issuer) // issuer：jwt签发人
                .setSubject(subject) // sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .signWith(signatureAlgorithm, secretKey); // 设置签名使用的签名算法和签名使用的秘钥

        // 设置过期时间
        if (ttlMillis >= 0) {
            long expMillis = currentDate.getTime() + ttlMillis;
            Date expDate = new Date(expMillis);
            builder.setExpiration(expDate);
        }
        return builder.compact();
    }

    /**
     * 创建jwt
     *
     * @param claims
     * @param id
     * @param issuer
     * @param subject
     * @param secretKey
     * @param expireDate
     * @return
     * @throws Exception
     */
    public static String createJWT(Map<String, Object> claims, String id, String issuer, String subject,
                                   SecretKey secretKey, Date expireDate) {

        // 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 生成JWT的时间
        Date currentDate = new Date();

        // 创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）

        // 下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder() // 这里其实就是new一个JwtBuilder，设置jwt的body
                .setClaims(claims) // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setId(id) // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setIssuedAt(currentDate) // iat: jwt的签发时间
                .setIssuer(issuer) // issuer：jwt签发人
                .setSubject(subject) // sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .signWith(signatureAlgorithm, secretKey); // 设置签名使用的签名算法和签名使用的秘钥

        // 设置过期时间
        builder.setExpiration(expireDate);
        return builder.compact();
    }

    /**
     * 解密jwt
     *
     * @param jwt
     * @param secretKey
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt, SecretKey secretKey) {
        // secretKey签名秘钥，和生成的签名的秘钥一模一样
        Claims claims = Jwts.parser() // 得到DefaultJwtParser
                .setSigningKey(secretKey) // 设置签名的秘钥
                .parseClaimsJws(jwt).getBody(); // 设置需要解析的jwt
        return claims;
    }

    public static String createSalt(Integer len) {
        Random random = new SecureRandom();
        byte[] saltBytes = new byte[len];
        random.nextBytes(saltBytes);
        String salt = Base64.getEncoder().encodeToString(saltBytes);
        return salt;
    }

    public static String md5(String credentials, String saltSource) {
        ByteSource salt = new Md5Hash(saltSource);
        return new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations).toString();
    }

}

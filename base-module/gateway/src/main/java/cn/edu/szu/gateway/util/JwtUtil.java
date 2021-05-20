package cn.edu.szu.gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

/**
 * @author GuangfaDU
 * @version 1.0
 */
public class JwtUtil {

    public static final Long JWT_TTL = 3600000L;

    public static final String JWT_KEY = "cn.edu.szu.utvs";

    /**
     * 生成jwt
     * @param id jwt唯一id
     * @param subject 颁发对象
     * @param ttlMillis jwt过期时长
     * @return jwt
     */
    public static String createJwt(String id, String subject, Long ttlMillis) {
        if (ttlMillis == null) {
            ttlMillis = JWT_TTL;
        }
        long curMillis = System.currentTimeMillis();
        long expiredMillis = curMillis + ttlMillis;
        Date nowDate = new Date(curMillis);
        Date expireDate = new Date(expiredMillis);

        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setSubject(subject)
                .setIssuer("admin")
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, generalSecretKey());

        return builder.compact();
    }

    /**
     * 解析令牌数据
     * @param jwt 令牌
     * @return 解析后的载荷
     */
    public static Claims parseJwt(String jwt) {
        return Jwts.parser()
                .setSigningKey(generalSecretKey())
                .parseClaimsJws(jwt)
                .getBody();
    }

    private static SecretKey generalSecretKey() {
        byte[] encodedKey = Base64.getEncoder().encode(JwtUtil.JWT_KEY.getBytes());
        return new SecretKeySpec(encodedKey, "AES");
    }
}

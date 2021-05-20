import cn.edu.szu.gateway.GatewayApplication;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JwtTest.class)
public class JwtTest {

    @Test
    public void testCreateJwt() {
        JwtBuilder builder = Jwts.builder()
                .setId("888")
                .setSubject("小白")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "cn.edu.szu");

        System.out.println(builder.compact());
    }

    @Test
    public void testParseJwt() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlsI_nmb0iLCJpYXQiOjE2MTU2Mzk0NzZ9.JSyaM4CHG9SH-B6E-0ftTyyivDwpAncsEr86aJAsT4g";
        Claims claims = Jwts.parser()
                .setSigningKey("cn.edu.szu")
                .parseClaimsJws(token)
                .getBody();
        System.out.println(claims);
    }
}

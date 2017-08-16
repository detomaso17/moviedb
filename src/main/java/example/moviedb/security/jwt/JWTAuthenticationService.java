package example.moviedb.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Date;

public class JWTAuthenticationService {

    private static final long EXPIRATION_TIME = 1000 * 60 * 15;// 15 minutes
    private static final String SECRET = "GHJGSFJFJHFDHVJDFHRTG4FKUYE";
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    public void addAuthentication(HttpServletResponse response, String username) {
        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        response.addHeader(AUTHORIZATION_HEADER, TOKEN_PREFIX + " " + token);
    }

    public Authentication getAuthentication(HttpServletRequest request) {

        String token = request.getHeader(AUTHORIZATION_HEADER);
        if (token == null) {
            return null;
        }
        String credentials = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();

        if (credentials == null) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(credentials, null, Collections.emptyList());
    }
}

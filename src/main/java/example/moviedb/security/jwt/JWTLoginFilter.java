package example.moviedb.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.moviedb.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    private JWTAuthenticationService jwtAuthenticationService;

    public JWTLoginFilter(String url,
                          JWTAuthenticationService jwtAuthenticationService,
                          AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(url));
        this.jwtAuthenticationService = jwtAuthenticationService;
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {

            User user = new ObjectMapper()
                    .readValue(request.getInputStream(), User.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword(),
                            new ArrayList<>()
                    )
            );

        } catch (IOException ioe) {

            throw new RuntimeException(ioe);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain filterChain,
                                            Authentication authentication) {

        jwtAuthenticationService.addAuthentication(response, authentication.getName());
    }
}

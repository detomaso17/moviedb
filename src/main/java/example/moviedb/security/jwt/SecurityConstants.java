package example.moviedb.security.jwt;

public class SecurityConstants {

    //TODO 5 days - for development, could be shorter
    public static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 5;
    public static final String SECRET = "GHJGSFJFJHFDHVJDFHRTG4FKUYE";
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String AUTHORIZATION_HEADER = "Authorization";
}

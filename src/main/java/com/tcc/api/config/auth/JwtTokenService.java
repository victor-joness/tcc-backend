package com.tcc.api.config.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class JwtTokenService {

    @Value("${strings.secretKey}")
    private String SECRET_KEY;

    @Value("${strings.issuer}")
    private String ISSUER ;

    @Value("${strings.zoneId}")
    private String ZONE_ID;

    @Value("${strings.tokenValidAfterHours}")
    private String TOKEN_VALID_AFTER_HOURS;


    public String generateToken(UserDetails user) {


        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        return JWT.create()
                .withIssuer(ISSUER)
                .withIssuedAt(creationDate())
                .withSubject(user.getUsername())
                .sign(algorithm);

    }

    public String getSubjectFromToken(String token) {

        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        return JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build()
                .verify(token)
                .getSubject();

    }

    public Instant creationDate() {
        return ZonedDateTime.now(ZoneId.of(ZONE_ID)).toInstant();
    }


    public Instant expirationDate() {
        return ZonedDateTime.now(ZoneId.of(ZONE_ID)).plusHours(Integer.parseInt(TOKEN_VALID_AFTER_HOURS)).toInstant();
    }

}

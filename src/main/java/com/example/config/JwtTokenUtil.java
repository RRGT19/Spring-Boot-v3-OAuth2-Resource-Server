package com.example.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    private final JwtEncoder encoder;

    // generate token
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        JwtClaimsSet jwtBuilder = jwtWithDefaults()
                .subject(username)
                .build();
        return getToken(jwtBuilder);
    }

    public String generateToken(Authentication authentication, Map<String, String> claims) {
        String username = authentication.getName();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet jwtBuilder = jwtWithDefaults()
                .subject(username)
                .claim("scope", scope)
                .build();
        return getToken(jwtBuilder);
    }

    private JwtClaimsSet.Builder jwtWithDefaults() {
        Instant now = Instant.now();
        Instant expire = now.plus(24, ChronoUnit.HOURS);
        return JwtClaimsSet.builder()
                .issuer("AdeptService")
                .issuedAt(now)
                .notBefore(now)
                .expiresAt(expire);
    }

    private String getToken(JwtClaimsSet claims) {
        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}
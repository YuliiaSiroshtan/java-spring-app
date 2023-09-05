package com.security.service.service;

import com.vault.service.JwtSecretService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;

@Service
public class JwtService {

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.issuer}")
    private String jwtIssuer;

    private final JwtSecretService secretService;

    @Autowired
    public JwtService(JwtSecretService secretService){
        this.secretService = secretService;
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .claim("permissions", getPermissions(userDetails))
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setIssuer(jwtIssuer)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private List<String> getPermissions(UserDetails user){
        List<String> permissions = new ArrayList<>();
        for(var scope : user.getAuthorities()){
            permissions.add(scope.getAuthority());
        }
        return permissions;
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secretService.accessToJwtSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

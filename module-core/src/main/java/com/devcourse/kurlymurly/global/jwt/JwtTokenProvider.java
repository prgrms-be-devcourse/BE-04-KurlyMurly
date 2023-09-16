package com.devcourse.kurlymurly.global.jwt;

import com.devcourse.kurlymurly.global.exception.ErrorCode;
import com.devcourse.kurlymurly.global.exception.KurlyBaseException;
import com.devcourse.kurlymurly.global.service.CustomUserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    private static final long expiration = 30 * 60 * 1000L;

    private final Key key;
    private final CustomUserDetailService userDetailService;

    public JwtTokenProvider(
            @Value("${secret-key}") String secretKey,
            CustomUserDetailService userDetailService) {
        byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);
        this.key = Keys.hmacShaKeyFor(secretByteKey);
        this.userDetailService = userDetailService;
    }

    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("authority", authorities)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);
        String username = extractUsername(claims);
        Collection<? extends GrantedAuthority> authorities = getAuthority(claims);

        UserDetails user = userDetailService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(user, null, authorities);
    }

    private String extractUsername(Claims claims) {
        return claims.getSubject();
    }

    private Collection<? extends GrantedAuthority> getAuthority(Claims claims) {
        try {
            String[] authorities = claims.get("authority").toString().split(",");
            return Arrays.stream(authorities)
                    .map(SimpleGrantedAuthority::new)
                    .toList();
        } catch (KurlyBaseException e) {
            throw new KurlyBaseException(ErrorCode.NOT_AUTHORIZED_TOKEN);
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new KurlyBaseException(ErrorCode.NOT_CORRECT_JWT_SIGN);
        } catch (ExpiredJwtException e) {
            throw new KurlyBaseException(ErrorCode.EXPIRED_JWT_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new KurlyBaseException(ErrorCode.NOT_SUPPORTED_JWT_TOKEN);
        } catch (IllegalArgumentException e) {
            throw new KurlyBaseException(ErrorCode.NOT_CORRECT_JWT);
        }
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}


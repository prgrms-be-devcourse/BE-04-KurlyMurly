package com.devcourse.kurlymurly.auth.jwt;

import com.devcourse.kurlymurly.common.exception.ErrorCode;
import com.devcourse.kurlymurly.common.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.devcourse.kurlymurly.common.exception.ErrorCode.EXPIRED_JWT_TOKEN;
import static com.devcourse.kurlymurly.common.exception.ErrorCode.NOT_CORRECT_JWT;
import static com.devcourse.kurlymurly.common.exception.ErrorCode.NOT_CORRECT_JWT_SIGN;
import static com.devcourse.kurlymurly.common.exception.ErrorCode.NOT_SUPPORTED_JWT_TOKEN;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider tokenProvider;
    private static final String BEARER_PREFIX = "Bearer ";

    public JwtAuthenticationFilter(JwtProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);

        try {
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            setErrorResponse(response, NOT_CORRECT_JWT_SIGN);
        } catch (ExpiredJwtException e) {
            setErrorResponse(response, EXPIRED_JWT_TOKEN);
        } catch (UnsupportedJwtException e) {
            setErrorResponse(response, NOT_SUPPORTED_JWT_TOKEN);
        } catch (IllegalArgumentException e) {
            setErrorResponse(response, NOT_CORRECT_JWT);
        }
    }

    private void setErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.addHeader("Content-Type", "application/json; charset=UTF-8");
        ErrorResponse errorResponse = new ErrorResponse(errorCode.name(), errorCode.getMessage());

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        return isBearerToken(bearerToken) ? extractJwt(bearerToken) : null;
    }

    private boolean isBearerToken(String token) {
        return token != null && token.startsWith(BEARER_PREFIX);
    }

    private String extractJwt(String bearerToken) {
        return bearerToken.substring(BEARER_PREFIX.length());
    }
}


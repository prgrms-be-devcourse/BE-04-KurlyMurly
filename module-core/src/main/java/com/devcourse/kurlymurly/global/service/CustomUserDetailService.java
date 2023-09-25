package com.devcourse.kurlymurly.global.service;

import com.devcourse.kurlymurly.global.exception.ErrorCode;
import com.devcourse.kurlymurly.global.exception.KurlyBaseException;
import com.devcourse.kurlymurly.module.auth.AuthRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final AuthRepository authRepository;

    public CustomUserDetailService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        return authRepository.findByLoginId(loginId)
                .orElseThrow(() -> new KurlyBaseException(ErrorCode.FAIL_USER_LOGIN));
    }
}

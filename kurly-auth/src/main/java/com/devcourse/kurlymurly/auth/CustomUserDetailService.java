package com.devcourse.kurlymurly.auth;

import com.devcourse.kurlymurly.core.exception.KurlyBaseException;
import com.devcourse.kurlymurly.domain.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import static com.devcourse.kurlymurly.core.exception.ErrorCode.FAIL_USER_LOGIN;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) {
        return userRepository.findByLoginId(loginId)
                .map(AuthUser::new)
                .orElseThrow(() -> new KurlyBaseException(FAIL_USER_LOGIN));
    }
}

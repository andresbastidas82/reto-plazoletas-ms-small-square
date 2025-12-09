package com.pragma.ms_small_square.infrastructure.security.adapter;

import com.pragma.ms_small_square.domain.spi.IAuthenticationServicePort;
import com.pragma.ms_small_square.infrastructure.security.PrincipalProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthAdapter implements IAuthenticationServicePort {

    private final PrincipalProvider principalProvider;

    @Override
    public Long getUserIdOfToken() {
        return principalProvider.getUserId();
    }
}

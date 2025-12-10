package com.pragma.ms_small_square.domain.spi;

public interface IAuthenticationServicePort {
    Long getUserIdOfToken();
    String getNameOfToken();
    String getEmailOfToken();
}

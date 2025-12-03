package com.pragma.ms_small_square.domain.spi;

public interface IUserClientPort {

    boolean isOwner(Long userId);
}

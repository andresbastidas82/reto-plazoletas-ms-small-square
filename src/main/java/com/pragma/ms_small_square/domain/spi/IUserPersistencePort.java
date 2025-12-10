package com.pragma.ms_small_square.domain.spi;

import com.pragma.ms_small_square.domain.model.User;

public interface IUserPersistencePort {

    User validateUser(User user);
}

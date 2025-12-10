package com.pragma.ms_small_square.infrastructure.out.jpa.adapter;

import com.pragma.ms_small_square.domain.model.User;
import com.pragma.ms_small_square.domain.spi.IUserPersistencePort;
import com.pragma.ms_small_square.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.ms_small_square.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;

    @Override
    public User validateUser(User user) {
        userRepository.save(new UserEntity(user.getId(), user.getName()));
        return user;
    }
}

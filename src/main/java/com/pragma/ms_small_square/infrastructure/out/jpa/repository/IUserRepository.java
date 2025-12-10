package com.pragma.ms_small_square.infrastructure.out.jpa.repository;

import com.pragma.ms_small_square.infrastructure.out.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {
}

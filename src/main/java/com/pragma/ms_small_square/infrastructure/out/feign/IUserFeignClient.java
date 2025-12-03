package com.pragma.ms_small_square.infrastructure.out.feign;

import com.pragma.ms_small_square.infrastructure.out.feign.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "${url-base.ms-users}")
public interface IUserFeignClient {

    @GetMapping("/{userId}")
    UserResponse getUserById(@PathVariable Long userId);
}

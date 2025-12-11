package com.pragma.ms_small_square.infrastructure.out.feign.adapter;

import com.pragma.ms_small_square.domain.spi.IUserClientPort;
import com.pragma.ms_small_square.infrastructure.exception.RestClientException;
import com.pragma.ms_small_square.infrastructure.out.feign.IUserFeignClient;
import com.pragma.ms_small_square.infrastructure.out.feign.dto.UserResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserClientAdapter implements IUserClientPort {

    private final IUserFeignClient userFeignClient;

    public UserResponse getUserById(Long userId) {
        try{
            return userFeignClient.getUserById(userId);
        } catch (FeignException e){
            log.error("Error querying data from the user microservice: {} ", e.getMessage());
            throw new RestClientException(e.getMessage());
        } catch (Exception e){
            log.error("Error: {} ", e.getMessage());
            throw new RestClientException(e.getMessage());
        }
    }


    @Override
    public boolean isOwner(Long userId) {
        UserResponse userResponse = getUserById(userId);
        return userResponse != null && userResponse.getRole().equalsIgnoreCase("OWNER");
    }

    @Override
    public String getPhoneCustomer(Long customerId) {
        UserResponse userResponse = getUserById(customerId);
        return userResponse != null && userResponse.getCellphone() != null ? userResponse.getCellphone() : null;
    }
}

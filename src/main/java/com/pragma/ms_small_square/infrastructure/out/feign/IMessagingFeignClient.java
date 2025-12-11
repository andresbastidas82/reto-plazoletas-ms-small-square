package com.pragma.ms_small_square.infrastructure.out.feign;

import com.pragma.ms_small_square.infrastructure.configuration.FeignClientConfig;
import com.pragma.ms_small_square.infrastructure.out.feign.dto.NotificationRequest;
import com.pragma.ms_small_square.infrastructure.out.feign.dto.NotificationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "user-messaging",
        url = "${url-base.ms-messaging}",
        configuration = FeignClientConfig.class)
public interface IMessagingFeignClient {

    @PostMapping("/send-notification")
    NotificationResponse sendNotification(NotificationRequest notificationRequest);
}

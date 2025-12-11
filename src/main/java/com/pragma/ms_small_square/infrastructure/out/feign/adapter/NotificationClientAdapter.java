package com.pragma.ms_small_square.infrastructure.out.feign.adapter;

import com.pragma.ms_small_square.domain.spi.INotificationClientPort;
import com.pragma.ms_small_square.infrastructure.out.feign.IMessagingFeignClient;
import com.pragma.ms_small_square.infrastructure.out.feign.dto.NotificationRequest;
import com.pragma.ms_small_square.infrastructure.out.feign.dto.NotificationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationClientAdapter implements INotificationClientPort {
    private final IMessagingFeignClient messagingFeignClient;

    private NotificationResponse sendNotificationClient(String phone, String message) {
        NotificationRequest request = NotificationRequest.builder().message(message).phone(phone).build();
        try {
            return messagingFeignClient.sendNotification(request);
        } catch (Exception e) {
            log.error("Error sending notification: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public Boolean sendNotification(String phone, String message) {
        NotificationResponse response = sendNotificationClient(phone, message);
        return response != null && response.getStatus().equalsIgnoreCase("OK");
    }
}

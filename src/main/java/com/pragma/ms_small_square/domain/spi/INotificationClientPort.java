package com.pragma.ms_small_square.domain.spi;

public interface INotificationClientPort {

    Boolean sendNotification(String phone, String message);

}

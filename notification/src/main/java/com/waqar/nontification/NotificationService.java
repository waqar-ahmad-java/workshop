package com.waqar.nontification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {
    public void sendNotification(NotificationRequest notificationRequest){
        log.info(notificationRequest.toString());
    }
}

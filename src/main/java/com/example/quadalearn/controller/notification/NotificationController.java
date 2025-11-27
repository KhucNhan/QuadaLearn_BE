package com.example.quadalearn.controller.notification;

import com.example.quadalearn.service.notification.InactiveUserNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final InactiveUserNotificationService notificationService;

    /**
     * Manual trigger để test notification system
     * GET /admin/notifications/check-inactive-users
     */
    @PostMapping("/check-inactive-users")
    public ResponseEntity<?> checkInactiveUsers() {
        try {
            notificationService.manualCheckAndSend();
            return ResponseEntity.ok(Map.of(
                    "message", "Notification check completed. Check server logs for details."
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }
}
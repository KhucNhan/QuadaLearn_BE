package com.example.quadalearn.service.notification;

import com.example.quadalearn.model.auth.User;
import com.example.quadalearn.model.notification.NotificationHistory;
import com.example.quadalearn.repository.notification.NotificationHistoryRepository;
import com.example.quadalearn.repository.auth.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InactiveUserNotificationService {

    private final IUserRepository userRepository;
    private final NotificationHistoryRepository notificationHistoryRepository;
    private final EmailService emailService;

    /**
     * Chạy mỗi ngày lúc 10:00 AM
     * Cron: giây phút giờ ngày tháng thứ
     */
    @Scheduled(cron = "0 0 10 * * *")
//    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void checkAndSendInactiveUserNotifications() {
        System.out.println("🔔 Starting inactive user notification check at: " + LocalDateTime.now());

        int totalSent = 0;

        for (Integer days : NOTIFICATION_MILESTONES) {
            LocalDateTime cutoffDate = LocalDateTime.now().minusDays(days);

            // Tìm users không login từ X ngày
            List<User> inactiveUsers = userRepository.findUsersInactiveSince(cutoffDate);

            for (User user : inactiveUsers) {
                try {
                    // Tính số ngày inactive chính xác
                    long actualDaysInactive = ChronoUnit.DAYS.between(
                            user.getLastLoginAt().toLocalDate(),
                            LocalDateTime.now().toLocalDate()
                    );

                    // Kiểm tra xem có khớp với milestone không (±1 ngày tolerance)
                    if (Math.abs(actualDaysInactive - days) <= 1) {
                        // Kiểm tra đã gửi notification type này chưa
                        String notificationType = "INACTIVE_" + days + "_DAYS";
                        boolean alreadySent = notificationHistoryRepository
                                .findByUserAndNotificationType(user, notificationType)
                                .isPresent();

                        if (!alreadySent) {
                            sendInactiveUserEmail(user, (int) actualDaysInactive, notificationType);
                            totalSent++;
                        }
                    }
                } catch (Exception e) {
                    System.err.println("❌ Error processing user " + user.getId() + ": " + e.getMessage());
                }
            }
        }

        System.out.println("✅ Finished. Sent " + totalSent + " notifications.");
    }
    // Các mốc gửi email (theo ngày)

    private static final List<Integer> NOTIFICATION_MILESTONES = Arrays.asList(
            1, 2, 3, 5, 7,      // 1-7 ngày
            14, 21,             // 2-3 tuần
            30, 60, 90,         // 1-3 tháng
            180, 365            // 6 tháng, 1 năm
    );

    /**
     * Gửi email và lưu lịch sử
     */
    private void sendInactiveUserEmail(User user, int daysInactive, String notificationType) {
        try {
            String subject = emailService.createInactiveUserEmailSubject(daysInactive);
            String body = emailService.createInactiveUserEmailBody(user.getName(), daysInactive);

            emailService.sendEmail(user.getEmail(), subject, body);

            // Lưu lịch sử
            NotificationHistory notification = new NotificationHistory(
                    user,
                    notificationType,
                    daysInactive,
                    user.getEmail(),
                    "SENT"
            );
            notificationHistoryRepository.save(notification);

            // Cập nhật lastNotificationSentAt
            user.setLastNotificationSentAt(LocalDateTime.now());
            userRepository.save(user);

            System.out.println("✅ Sent notification to " + user.getEmail() + " (" + daysInactive + " days inactive)");

        } catch (Exception e) {
            System.err.println("❌ Failed to send notification to " + user.getEmail());

            // Lưu lịch sử failed
            NotificationHistory notification = new NotificationHistory(
                    user,
                    notificationType,
                    daysInactive,
                    user.getEmail(),
                    "FAILED"
            );
            notificationHistoryRepository.save(notification);
        }
    }

    /**
     * Manual trigger để test (gọi từ controller)
     */
    public void manualCheckAndSend() {
        System.out.println("🔔 Manual trigger started");
        checkAndSendInactiveUserNotifications();
    }
}
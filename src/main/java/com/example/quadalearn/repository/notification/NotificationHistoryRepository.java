package com.example.quadalearn.repository.notification;

import com.example.quadalearn.model.auth.User;
import com.example.quadalearn.model.notification.NotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationHistoryRepository extends JpaRepository<NotificationHistory, Long> {

    // Kiểm tra xem user đã được gửi notification type này chưa
    Optional<NotificationHistory> findByUserAndNotificationType(User user, String notificationType);

    // Lấy notification cuối cùng của user
    Optional<NotificationHistory> findFirstByUserOrderBySentAtDesc(User user);

    // Lấy tất cả notifications của user
    List<NotificationHistory> findByUserOrderBySentAtDesc(User user);
}
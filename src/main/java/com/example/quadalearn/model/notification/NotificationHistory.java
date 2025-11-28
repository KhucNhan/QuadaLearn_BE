package com.example.quadalearn.model.notification;

import com.example.quadalearn.model.auth.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "notification_type")
    private String notificationType; // "INACTIVE_1_DAY", "INACTIVE_2_DAYS", etc.

    @Column(name = "days_inactive")
    private Integer daysInactive; // Số ngày không hoạt động

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "email_sent_to")
    private String emailSentTo;

    @Column(name = "status")
    private String status; // "SENT", "FAILED"

    public NotificationHistory(User user, String notificationType, Integer daysInactive,
                               String emailSentTo, String status) {
        this.user = user;
        this.notificationType = notificationType;
        this.daysInactive = daysInactive;
        this.sentAt = LocalDateTime.now();
        this.emailSentTo = emailSentTo;
        this.status = status;
    }
}
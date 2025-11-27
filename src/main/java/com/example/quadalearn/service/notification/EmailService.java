package com.example.quadalearn.service.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    /**
     * Gửi email đơn giản
     */
    public void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("khucnhanfb@gmail.com");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);
            System.out.println("✅ Email sent to: " + to);
        } catch (Exception e) {
            System.err.println("❌ Failed to send email to: " + to);
            e.printStackTrace();
            throw new RuntimeException("Failed to send email", e);
        }
    }

    /**
     * Tạo nội dung email cho inactive user
     */
    public String createInactiveUserEmailBody(String userName, int daysInactive) {
        return String.format("""
            Xin chào %s,
            
            Chúng tôi nhận thấy bạn đã không đăng nhập vào QuadaLearning trong %d ngày.
            
            Chúng tôi nhớ bạn! 😊
            
            Hãy quay lại và tiếp tục hành trình học tập của bạn:
            • Hoàn thành các bài test đang dở
            • Xem lại kết quả và phân tích chi tiết
            • Thử thách bản thân với các bài test mới
            
            Đăng nhập ngay: https://quadalearning.com/login
            
            Trân trọng,
            Đội ngũ QuadaLearning
            
            ---
            Nếu bạn không muốn nhận email này nữa, vui lòng cập nhật trong cài đặt tài khoản.
            """, userName, daysInactive);
    }

    /**
     * Tạo subject email theo số ngày inactive
     */
    public String createInactiveUserEmailSubject(int daysInactive) {
        if (daysInactive == 1) {
            return "🎯 Nhớ bạn quá! Quay lại học tiếp nào 😊";
        } else if (daysInactive <= 7) {
            return String.format("📚 Đã %d ngày rồi! Đừng quên học tiếp nhé", daysInactive);
        } else if (daysInactive <= 30) {
            return String.format("⏰ Đã %d ngày không gặp bạn - QuadaLearning nhớ bạn!", daysInactive);
        } else if (daysInactive <= 90) {
            return "🌟 Hành trình học tập của bạn đang chờ đợi!";
        } else {
            return "💙 Chúng tôi vẫn ở đây, sẵn sàng đồng hành cùng bạn!";
        }
    }
}
package FuelPass.Dev.FuelPass.Service;

import FuelPass.Dev.FuelPass.Entity.Otp;
import FuelPass.Dev.FuelPass.Repository.OtpRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class OtpCleanupService {

    private OtpRepository otpRepository;

    // Clean up expired OTPs every hour
    @Scheduled(fixedRate = 3600000) // 3600000 ms = 1 hour
    public void deleteExpiredOtps() {
        List<Otp> expiredOtps = otpRepository.findByExpireTimeBefore(LocalDateTime.now());
        otpRepository.deleteAll(expiredOtps);
    }
}
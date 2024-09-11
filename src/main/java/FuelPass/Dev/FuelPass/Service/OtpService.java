package FuelPass.Dev.FuelPass.Service;


import FuelPass.Dev.FuelPass.Entity.Otp;
import FuelPass.Dev.FuelPass.Repository.OtpRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class OtpService {

    private OtpRepository otpRepository;

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generates 6-digit OTP
        return String.valueOf(otp);
    }

    public Otp generateAndStoreNewOtp(String phoneNumber) {
        // Check for existing OTP
        Optional<Otp> existingOtp = otpRepository.findByPhoneNumberAndIsVerifiedFalse(phoneNumber);

        // Delete old OTP if exists
        existingOtp.ifPresent(otpRepository::delete);

        // Generate and save new OTP
        String otpCode = generateOtp();
        Otp otp = new Otp();
        otp.setPhoneNumber(phoneNumber);
        otp.setOtpCode(otpCode);
        otp.setExpireTime(LocalDateTime.now().plusMinutes(5)); // OTP expires in 5 minutes
        otpRepository.save(otp);

        return otp;
    }

    public boolean isOtpValid(String phoneNumber, String enteredOtp) {
        Optional<Otp> optionalOtp = otpRepository.findByPhoneNumberAndIsVerifiedFalse(phoneNumber);
        if (optionalOtp.isPresent()) {
            Otp otp = optionalOtp.get();

            // Check if OTP is expired
            if (otp.getExpireTime().isBefore(LocalDateTime.now())) {
                otpRepository.delete(otp); // Delete expired OTP
                return false;
            }

            // Check if entered OTP matches stored OTP
            if (otp.getOtpCode().equals(enteredOtp)) {
                otp.setIsVerified(true);
                otpRepository.delete(otp); // Delete OTP after verification
                return true;
            }
        }
        return false;
    }
}
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
        int otp = 100000 + random.nextInt(900000);
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
        otp.setExpireTime(LocalDateTime.now().plusMinutes(5));
        otpRepository.save(otp);

        return otp;
    }

    public boolean isOtpValid(String phoneNumber, String enteredOtp) {

        Optional<Otp> optionalOtp = otpRepository.findByPhoneNumberAndIsVerifiedFalse(phoneNumber);
        if (optionalOtp.isPresent()) {
            Otp otp = optionalOtp.get();

            System.out.println("Retrieved OTP: " + otp);
            System.out.println("Entered OTP: " + enteredOtp);

            if (otp.getExpireTime().isBefore(LocalDateTime.now())) {
                otpRepository.delete(otp);
                System.out.println("OTP expired and deleted.");
                return false;
            }

            if (otp.getOtpCode().equals(enteredOtp)) {
                otp.setIsVerified(true);
                otpRepository.delete(otp);
                System.out.println("OTP verified and deleted.");
                return true;
            } else {
                System.out.println("OTP code does not match.");
            }
        } else {
            System.out.println("No OTP found for phone number.");
        }
        return false;
    }

}
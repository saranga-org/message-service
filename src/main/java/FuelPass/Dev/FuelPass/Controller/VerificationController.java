package FuelPass.Dev.FuelPass.Controller;

import FuelPass.Dev.FuelPass.Entity.Otp;
import FuelPass.Dev.FuelPass.Service.OtpService;
import FuelPass.Dev.FuelPass.Service.TwilioService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/verify")
@AllArgsConstructor
public class VerificationController {

    private OtpService otpService;

    private TwilioService twilioService;

    @PostMapping("/generate-otp")
    public String generateOtp(@RequestParam String phoneNumber) {
        Otp otp = otpService.generateAndStoreNewOtp(phoneNumber);
        twilioService.sendVerificationCode(phoneNumber, otp.getOtpCode());
        return "OTP sent to " + phoneNumber;
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String phoneNumber, @RequestParam String otpCode) {
        boolean isValid = otpService.isOtpValid(phoneNumber, otpCode);
        return isValid ? "OTP is valid." : "OTP is invalid or expired.";
    }
}
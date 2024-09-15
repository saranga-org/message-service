package FuelPass.Dev.FuelPass.Controller;

import FuelPass.Dev.FuelPass.DTO.VerifyOtpDTO;
import FuelPass.Dev.FuelPass.Entity.Otp;
import FuelPass.Dev.FuelPass.Service.OtpService;
import FuelPass.Dev.FuelPass.Service.TwilioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/verify")
@AllArgsConstructor
public class VerificationController {

    private OtpService otpService;

    private TwilioService twilioService;

    @PostMapping("/generate-otp")
    public ResponseEntity<String> generateOtp(@RequestParam String phoneNumber) {
        try {
            Otp otp = otpService.generateAndStoreNewOtp(phoneNumber);
            twilioService.sendVerificationCode(phoneNumber, otp.getOtpCode());
            return ResponseEntity.ok("OTP sent to " + phoneNumber);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send OTP: " + e.getMessage());
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody VerifyOtpDTO verifyOtpDTO) {
        boolean isValid = otpService.isOtpValid(verifyOtpDTO.getPhoneNumber(), verifyOtpDTO.getOtpCode());
        return isValid ?
                ResponseEntity.ok("OTP is valid.") :
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("OTP is invalid or expired.");
    }

}
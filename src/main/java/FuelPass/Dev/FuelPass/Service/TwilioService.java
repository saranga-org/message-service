package FuelPass.Dev.FuelPass.Service;

import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

    private final String accountSid;
    private final String authToken;
    private final String fromNumber;

    @Autowired
    public TwilioService(@Value("${twilio.account-sid}") String accountSid,
                         @Value("${twilio.auth-token}") String authToken,
                         @Value("${twilio.phone-number}") String fromNumber) {
        this.accountSid = accountSid;
        this.authToken = authToken;
        this.fromNumber = fromNumber;

        Twilio.init(accountSid, authToken);
    }

    public void sendVerificationCode(String phoneNumber, String otpCode) {
        try {
            Message.creator(
                    new PhoneNumber(phoneNumber),
                    new PhoneNumber(fromNumber),
                    "Your OTP code is " + otpCode
            ).create();
            System.out.println("OTP sent successfully to: " + phoneNumber);
        } catch (ApiException e) {
            System.err.println("Failed to send OTP to: " + phoneNumber);
            System.err.println("Error: " + e.getMessage());
            throw new RuntimeException("Failed to send OTP. Please check the phone number.");
        }
    }

    public void sendNotification(String phoneNumber, String message) {
        try {
            Message.creator(
                    new PhoneNumber(phoneNumber),
                    new PhoneNumber(fromNumber),
                    message
            ).create();
            System.out.println("Message sent successfully to: " + phoneNumber);
        } catch (ApiException e) {
            System.err.println("Failed to send message to: " + phoneNumber);
            System.err.println("Error: " + e.getMessage());
            throw e;
        }
    }
}
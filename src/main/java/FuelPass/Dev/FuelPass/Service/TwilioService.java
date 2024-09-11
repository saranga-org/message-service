package FuelPass.Dev.FuelPass.Service;

import com.twilio.Twilio;
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
        Message.creator(
                new PhoneNumber(phoneNumber), // To
                new PhoneNumber(fromNumber), // From
                "Bath kanna waren kariyo" + otpCode
        ).create();
    }
}
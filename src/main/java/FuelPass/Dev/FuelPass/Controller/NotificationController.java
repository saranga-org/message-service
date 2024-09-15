package FuelPass.Dev.FuelPass.Controller;

import FuelPass.Dev.FuelPass.DTO.MessageDTO;
import FuelPass.Dev.FuelPass.Service.TwilioService;
import com.twilio.exception.ApiException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/notification")
@AllArgsConstructor
public class NotificationController {

    private TwilioService twilioService;

    @PostMapping("/send")
    public ResponseEntity<String> sendCustomMessage(@RequestBody MessageDTO messageDTO) {
        try {
            twilioService.sendNotification(messageDTO.getPhoneNumber(), messageDTO.getMessage());
            return ResponseEntity.ok("Message sent successfully to: " + messageDTO.getPhoneNumber());
        } catch (ApiException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send message. Reason: " + e.getMessage());
        }
    }

}

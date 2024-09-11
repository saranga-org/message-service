package FuelPass.Dev.FuelPass.Repository;

import FuelPass.Dev.FuelPass.Entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {

    Optional<Otp> findByPhoneNumberAndIsVerifiedFalse(String phoneNumber);

    List<Otp> findByExpireTimeBefore(LocalDateTime now);
}
package FuelPass.Dev.FuelPass.Repository;

import FuelPass.Dev.FuelPass.Entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {

    @Query("SELECT o FROM Otp o WHERE o.phoneNumber = :phoneNumber AND o.isVerified = false")
    Optional<Otp> findByPhoneNumberAndIsVerifiedFalse(@Param("phoneNumber") String phoneNumber);

    List<Otp> findByExpireTimeBefore(LocalDateTime now);
}
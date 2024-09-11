package FuelPass.Dev.FuelPass.Entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private int userId;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "id_type", nullable = false)
    private IdType idType;

    @Column(name = "firstName", nullable = false, length = 50)
    private String firstName;

    @Column(name = "lastName", nullable = false, length = 50)
    private String lastName;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "contact_number")
    private String  contactNumber;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean isVerified;
}

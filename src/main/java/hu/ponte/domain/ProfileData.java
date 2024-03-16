package hu.ponte.domain;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class ProfileData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private CustomUser customUser;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String motherFirstName;

    private String motherLastName;

    private String socialSecurityNumber;

    private String taxNumber;

    @OneToMany(mappedBy = "profileData")
    private List<Address> addresslist;

}

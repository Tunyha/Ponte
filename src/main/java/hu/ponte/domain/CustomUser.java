package hu.ponte.domain;

import hu.ponte.config.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class CustomUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;

    @OneToMany(mappedBy = "customUser")
    private List<PhoneNumber> phoneNumberList;

    private String password;

    private String activation;

    private Boolean enabled;

    @OneToOne(mappedBy = "customUser", cascade = CascadeType.ALL)
    private ProfileData profileData;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role")
    private List<UserRole> roles;
}

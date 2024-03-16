package hu.ponte.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private ProfileData profileData;

    private Integer zipCode;

    private String city;

    private String streetName;

    private String streetType;

    private String streetNumber;

    private boolean isDeleted;
}

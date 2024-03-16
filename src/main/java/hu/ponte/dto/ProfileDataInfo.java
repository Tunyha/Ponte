package hu.ponte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDataInfo {

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String motherFirstName;

    private String motherLastName;

    private String socialSecurityNumber;

    private String taxNumber;
}

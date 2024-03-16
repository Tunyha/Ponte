package hu.ponte.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDataUpdateCommand {

    @Schema(description = "Your first name.", example = "Zsolt")
    private String firstName;

    @Schema(description = "Your last name.", example = "Kiss")
    private String lastName;

    @Past
    @Schema(description = "Your birth date.", example = "1975.05.15")
    private LocalDate birthDate;

    @Schema(description = "Your mother's first name.", example = "Anna")
    private String motherFirstName;

    @Schema(description = "Your mother's last name.", example = "Nagy")
    private String motherLastName;

    @Schema(description = "Your social security number.", example = "115652436")
    private String socialSecurityNumber;

    @Schema(description = "Your tax number.", example = "0123456789")
    private String taxNumber;
}

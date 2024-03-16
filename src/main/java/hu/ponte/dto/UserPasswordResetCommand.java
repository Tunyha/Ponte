package hu.ponte.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordResetCommand {


    @NotBlank
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$",
            message = "Password must contain at least eight characters, " +
                    "at least one number and both lower and uppercase letters " +
                    "and special characters")
    @Schema(description = "The password of the User.", example = "abcDEF123$")
    private String password1;

    @NotBlank
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$",
            message = "Password must contain at least eight characters, " +
                    "at least one number and both lower and uppercase letters " +
                    "and special characters")
    @Schema(description = "The password of the User.", example = "abcDEF123$")
    private String password2;
}

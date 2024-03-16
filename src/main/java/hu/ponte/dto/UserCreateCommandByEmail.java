package hu.ponte.dto;
import hu.ponte.domain.PhoneNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateCommandByEmail {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Please provide a valid email address")
    @Schema(description = "The email of the User.", example = "example@gmail.com")
    private String email;

    @Schema(description = "The phone number of the User.", example = "06301278556")
    private List<PhoneNumberInfo> phoneNumberList;

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
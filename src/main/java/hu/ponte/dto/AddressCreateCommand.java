package hu.ponte.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressCreateCommand {

    @NotNull
    @Schema(description = "The ZIP code of the Address.", example = "1234")
    private Integer zipCode;

    @NotBlank
    @Schema(description = "The city of the Address.", example = "Budapest")
    private String city;

    @NotBlank
    @Schema(description = "The street name of the Address.", example = "Kerek")
    private String streetName;

    @NotBlank
    @Schema(description = "The street type of the Address.", example = "street")
    private String streetType;

    @NotBlank
    @Schema(description = "The street number of the Address.", example = "55")
    private String streetNumber;
}

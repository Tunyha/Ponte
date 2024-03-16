package hu.ponte.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressUpdateCommand {

    @Schema(description = "The ZIP code of the Address.", example = "1234")
    private Integer zipCode;

    @Schema(description = "The city of the Address.", example = "Budapest")
    private String city;

    @Schema(description = "The street name of the Address.", example = "Kerek")
    private String streetName;

    @Schema(description = "The street type of the Address.", example = "street")
    private String streetType;

    @Schema(description = "The street number of the Address.", example = "55")
    private String streetNumber;
}

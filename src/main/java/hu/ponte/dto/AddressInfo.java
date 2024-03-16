package hu.ponte.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressInfo {

    @Schema(example = "1234")
    private Integer zipCode;

    @Schema(example = "Budapest")
    private String city;

    @Schema(example = "Kerek")
    private String streetName;

    @Schema(example = "street")
    private String streetType;

    @Schema(example = "45")
    private String streetNumber;

    @Schema(example = "false")
    private boolean isDeleted;
}

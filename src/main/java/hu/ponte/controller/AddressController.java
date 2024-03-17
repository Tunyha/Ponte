package hu.ponte.controller;

import hu.ponte.dto.AddressCreateCommand;
import hu.ponte.dto.AddressInfo;
import hu.ponte.dto.AddressUpdateCommand;
import hu.ponte.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@Slf4j
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @Operation(summary = "Save an Address")
    @ApiResponse(
            responseCode = "201",
            description = "Address has been saved",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AddressInfo.class)))
    @ApiResponse(
            responseCode = "400",
            description = "City, Street name, Street number, Street type must not be blank and " +
                    "Zip code not be null.",
            content = @Content(
                    mediaType = "application/json"))

    @PostMapping("/{profileDataId}")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<AddressInfo> save(@Valid @RequestBody AddressCreateCommand command,
                                            @PathVariable("profileDataId") Integer profileDataId) {
        log.info("Http request, POST /api/addresses/{profileDataId}, " +
                "body: " + command.toString() + " and variable: " + profileDataId);
        AddressInfo addressInfo = addressService.saveAddress(command, profileDataId);
        return new ResponseEntity<>(addressInfo, HttpStatus.CREATED);
    }

    @Operation(summary = "List all Addresses")
    @ApiResponse(
            responseCode = "200",
            description = "Addresses have been listed",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = AddressInfo.class))))
    @GetMapping("/{profileDataId}")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<List<AddressInfo>> findAll(@PathVariable("profileDataId") Integer profileDataId) {
        log.info("Http request, GET /api/addresses/{profileDataId} with variable: " + profileDataId);
        List<AddressInfo> addressSaveInfoList = addressService.listAddresses(profileDataId);
        return new ResponseEntity<>(addressSaveInfoList, HttpStatus.OK);
    }

    @Operation(summary = "Update a Address")
    @ApiResponse(
            responseCode = "200",
            description = "Address has been updated",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AddressInfo.class)))
    @ApiResponse(
            responseCode = "400",
            description = "City, Street name, Street number, Street type must not be blank and " +
                    "Zip code not be null.",
            content = @Content(
                    mediaType = "application/json"))
    @PutMapping("/{addressId}")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<AddressInfo> update(@PathVariable("addressId") Integer addressId,
                                              @Valid @RequestBody AddressUpdateCommand command) {
        log.info("Http request, PUT /api/addresses/{addressId} body: " + command.toString() +
                " with variable: " + addressId);
        AddressInfo updated = addressService.update(addressId, command);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @Operation(summary = "Delete a Address")
    @ApiResponse(
            responseCode = "200",
            description = "Address has been deleted",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AddressInfo.class)))
    @DeleteMapping("/{addressId}")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<AddressInfo> delete(@PathVariable("addressId") Integer addressId) {
        log.info("Http request, DELETE /api/addresses/{addressId} with variable: " + addressId);
        AddressInfo deltedAddressInfo = addressService.logicalDelete(addressId);
        return new ResponseEntity<>(deltedAddressInfo, HttpStatus.OK);
    }
}

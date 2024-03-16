package hu.ponte.controller;

import hu.ponte.dto.AddressCreateCommand;
import hu.ponte.dto.AddressInfo;
import hu.ponte.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/address")
@Slf4j
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/{profileDataId}")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<AddressInfo> save(@Valid @RequestBody AddressCreateCommand command,
                                            @PathVariable("profileDataId") Integer profileDataId) {
        log.info("Http request, POST /api/addresses/{profileDataId}, " +
                "body: " + command.toString() + " and variable: " + profileDataId);
        AddressInfo addressInfo = addressService.saveAddress(command, profileDataId);
        return new ResponseEntity<>(addressInfo, HttpStatus.CREATED);
    }

}

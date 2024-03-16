package hu.ponte.controller;

import hu.ponte.dto.ProfileDataInfo;
import hu.ponte.dto.ProfileDataUpdateCommand;
import hu.ponte.service.ProfileDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/profileDatas")
@RequiredArgsConstructor
@Slf4j
public class ProfileDataController {

    private final ProfileDataService profileDataService;

    @PutMapping("/{profileDataId}")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<ProfileDataInfo> update(@PathVariable("profileDataId") Integer id,
                                                  @Valid @RequestBody ProfileDataUpdateCommand command) {
        log.info("Http request PUT /api/profileDatas/{profileDataId} body: " + command.toString() +
                " with variable: " + id);
        ProfileDataInfo updatedProfileData = profileDataService.update(id, command);
        return new ResponseEntity<>(updatedProfileData, HttpStatus.OK);
    }
}
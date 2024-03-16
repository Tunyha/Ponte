package hu.ponte.controller;

import hu.ponte.dto.UserCreateCommand;
import hu.ponte.dto.UserPasswordResetCommand;
import hu.ponte.dto.UserSaveInfo;
import hu.ponte.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Registers a User")
    @ApiResponse(
            responseCode = "201",
            description = "User has been saved",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserSaveInfo.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "The User's email and password must be valid",
            content = @Content(
                    mediaType = "application/json"))
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserCreateCommand command) {
        String result = userService.registerUserByEmail(command);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/activation/{activationCode}")
    public ResponseEntity<String> activate(@PathVariable("activationCode") String code) {
        String result = userService.userActivation(code);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/updateInfo")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<UserSaveInfo> update(@Valid @RequestBody UserPasswordResetCommand command) {
        log.info("Http request PUT /api/users/{userid} body: " + command.toString());
        UserSaveInfo updatedUser = userService.update(command);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @GetMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<List<UserSaveInfo>> findAll() {
        log.info("Http request, GET /api/users");
        List<UserSaveInfo> listCustomUsersEmailAddresses = userService.listCustomUsersEmailAddresses();
        return new ResponseEntity<>(listCustomUsersEmailAddresses, HttpStatus.OK);
    }

    @GetMapping("/forgottenPassword")
    public ResponseEntity<String> forgottenPassword(@RequestParam String email){
        log.info("Http request, GET /api/users/forgottenPassword");
        String passWordEmailResponse = userService.sendForgottenEmail(email);
        return new ResponseEntity<>(passWordEmailResponse, HttpStatus.OK);
    }
    @PutMapping("/passwordReset/{resetCode}")
    public ResponseEntity<String> passwordReset(@PathVariable String resetCode,
                                                @Valid @RequestBody UserPasswordResetCommand command){
        log.info("Http request, GET /api/users/forgottenPassword");
        String passwordResetResponse = userService.resetPassword(resetCode, command);
        return new ResponseEntity<>(passwordResetResponse, HttpStatus.OK);
    }
}


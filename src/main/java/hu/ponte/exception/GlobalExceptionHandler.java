package hu.ponte.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationError>> handleValidationException(MethodArgumentNotValidException exception) {
        List<ValidationError> validationErrors = exception.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProfileDataNotFoundException.class)
    public ResponseEntity<List<ValidationError>> handleProfileDataNotFound(ProfileDataNotFoundException exception) {
        ValidationError validationError = new ValidationError("profileDataId",
                "ProfileData not found with id: " + exception.getProfileDataId());
        return new ResponseEntity<>(List.of(validationError), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<List<ValidationError>> handleAddressNotFound(AddressNotFoundException exception) {
        ValidationError validationError = new ValidationError("addressId",
                "Address not found with id: " + exception.getAddressId());
        return new ResponseEntity<>(List.of(validationError), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserWithIdNotFoundException.class)
    public ResponseEntity<List<ValidationError>> handleUserNotFound(UserWithIdNotFoundException exception) {
        ValidationError validationError = new ValidationError("userId",
                "User not found with id: " + exception.getUserId());
        return new ResponseEntity<>(List.of(validationError), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserWithEmailNotFoundException.class)
    public ResponseEntity<List<ValidationError>> handleUserNotFound(UserWithEmailNotFoundException exception) {
        ValidationError validationError = new ValidationError("userEmail",
                "User not found with email: " + exception.getUserEmail());
        return new ResponseEntity<>(List.of(validationError), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordsDontMatchException.class)
    public ResponseEntity<List<ValidationError>> passwordsDontMatch() {
        ValidationError validationError = new ValidationError("password",
                "Passwords don't match");
        return new ResponseEntity<>(List.of(validationError), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserWithCodeNotFoundException.class)
    public ResponseEntity<List<ValidationError>> handleUserNotFound(UserWithCodeNotFoundException exception) {
        ValidationError validationError = new ValidationError("userCode",
                "User not found with code: " + exception.getUserCode());
        return new ResponseEntity<>(List.of(validationError), HttpStatus.BAD_REQUEST);
    }
}

package hu.ponte.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserWithEmailNotFoundException extends RuntimeException{
    private final String userEmail;
}

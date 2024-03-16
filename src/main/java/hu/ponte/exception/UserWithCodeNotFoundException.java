package hu.ponte.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserWithCodeNotFoundException extends RuntimeException {
    private final String userCode;
}
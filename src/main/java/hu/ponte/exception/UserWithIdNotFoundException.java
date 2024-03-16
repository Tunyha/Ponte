package hu.ponte.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserWithIdNotFoundException extends RuntimeException {
    private final int userId;
}

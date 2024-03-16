package hu.ponte.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProfileDataNotFoundException extends RuntimeException {
    private final int profileDataId;
}
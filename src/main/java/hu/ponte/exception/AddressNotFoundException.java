package hu.ponte.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AddressNotFoundException extends RuntimeException {
    private final Integer addressId;
}
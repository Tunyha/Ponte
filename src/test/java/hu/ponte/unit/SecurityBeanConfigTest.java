package hu.ponte.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class SecurityBeanConfigTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testPasswordEncoderConfiguration() {
        Assertions.assertNotNull(passwordEncoder);

        String rawPassword = "myPassword";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        Assertions.assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
    }
}

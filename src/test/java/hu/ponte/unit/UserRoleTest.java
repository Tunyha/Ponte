package hu.ponte.unit;

import hu.ponte.config.UserRole;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class UserRoleTest {

    @Test
    public void testUserRoleValues() {
        assertThat(UserRole.ROLE_USER).isEqualTo(UserRole.valueOf("ROLE_USER"));
        assertThat(UserRole.ROLE_ADMIN).isEqualTo(UserRole.valueOf("ROLE_ADMIN"));
    }

    @Test
    public void testUserRoleComparison() {
        assertThat(UserRole.ROLE_USER).isLessThan(UserRole.ROLE_ADMIN);
        assertThat(UserRole.ROLE_USER).isNotEqualTo(UserRole.ROLE_ADMIN);
    }

    @Test
    public void testUserRoleConversion() {
        String roleUserAsString = UserRole.ROLE_USER.toString();
        AssertionsForClassTypes.assertThat(roleUserAsString).isEqualTo("ROLE_USER");

        UserRole roleFromString = UserRole.valueOf("ROLE_ADMIN");
        assertThat(roleFromString).isEqualTo(UserRole.ROLE_ADMIN);
    }
}


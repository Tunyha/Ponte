package hu.ponte.IT.Address;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
@WithMockUser(roles = {"ADMIN", "USER"})
public class AddressSaveAsUserOrAdminIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    public void SaveTestProfileData() {
        entityManager.createNativeQuery(
                "INSERT INTO profile_data (first_name, last_name, birth_date, mother_first_name, mother_last_name, social_security_number, tax_number, last_login, id) " +
                        "VALUES ('Adam', 'Kiss', '1990-05-05', 'MotherFirstName', 'MotherLastName', '123456789', '987654321', '2023-01-01', 1);"
        ).executeUpdate();
    }

    @Test
    void test_successfulSave() throws Exception {
        String inputCommand = "{\n" +
                "    \"zipCode\": 1234,\n" +
                "    \"city\": \"Budapest\",\n" +
                "    \"streetName\": \"Nagy Imre\",\n" +
                "    \"streetType\": \"ROAD\",\n" +
                "    \"streetNumber\": \"20\"\n" +
                "}";

        mockMvc.perform(post("/api/addresses/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputCommand))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.city", is("Budapest")));
    }

    @Test
    void test_profileDataIdNotValid_NotExist() throws Exception {
        String inputCommand = "{\n" +
                "    \"zipCode\": 1234,\n" +
                "    \"city\": \"Budapest\",\n" +
                "    \"streetName\": \"Nagy Imre\",\n" +
                "    \"streetType\": \"ROAD\",\n" +
                "    \"streetNumber\": \"20\"\n" +
                "}";

        mockMvc.perform(post("/api/addresses/2")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputCommand))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("profileDataId")))
                .andExpect(jsonPath("$[0].errorMessage", is("ProfileData not found with id: 2")));
    }

    @Test
    void test_streetNumberNotValid_Blank() throws Exception {
        String inputCommand = "{\n" +
                "    \"zipCode\": 1234,\n" +
                "    \"city\": \"Budapest\",\n" +
                "    \"streetName\": \"Nagy Imre\",\n" +
                "    \"streetType\": \"ROAD\",\n" +
                "    \"streetNumber\": \"\"\n" +
                "}";

        mockMvc.perform(post("/api/addresses/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputCommand)).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("streetNumber")))
                .andExpect(jsonPath("$[0].errorMessage", is("must not be blank")));
    }

    @Test
    void test_zipCodeNotValid_Null() throws Exception {
        String inputCommand = "{\n" +
                "    \"zipCode\": \"\",\n" +
                "    \"city\": \"Budapest\",\n" +
                "    \"streetName\": \"Nagy Imre\",\n" +
                "    \"streetType\": \"ROAD\",\n" +
                "    \"streetNumber\": \"20\"\n" +
                "}";

        mockMvc.perform(post("/api/addresses/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputCommand))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("zipCode")))
                .andExpect(jsonPath("$[0].errorMessage", is("must not be null")));
    }

    @Test
    void test_cityNotValid_Blank() throws Exception {
        String inputCommand = "{\n" +
                "    \"zipCode\": 1234,\n" +
                "    \"city\": \"\",\n" +
                "    \"streetName\": \"Nagy Imre\",\n" +
                "    \"streetType\": \"ROAD\",\n" +
                "    \"streetNumber\": \"20\"\n" +
                "}";

        mockMvc.perform(post("/api/addresses/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputCommand)).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("city")))
                .andExpect(jsonPath("$[0].errorMessage", is("must not be blank")));
    }

    @Test
    void test_streetNameNotValid_Blank() throws Exception {
        String inputCommand = "{\n" +
                "    \"zipCode\": 1234,\n" +
                "    \"city\": \"Budapest\",\n" +
                "    \"streetName\": \"\",\n" +
                "    \"streetType\": \"ROAD\",\n" +
                "    \"streetNumber\": \"20\"\n" +
                "}";

        mockMvc.perform(post("/api/addresses/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputCommand)).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("streetName")))
                .andExpect(jsonPath("$[0].errorMessage", is("must not be blank")));
    }
}
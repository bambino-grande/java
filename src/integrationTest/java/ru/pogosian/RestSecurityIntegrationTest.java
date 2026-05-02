package ru.pogosian;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RestSecurityIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void protectedEndpointRejectsAnonymousUser() throws Exception {
        mockMvc.perform(get("/api/details/find-all")).andExpect(status().isUnauthorized());
    }

    @Test
    void userCarReadDetails() throws Exception {
        mockMvc.perform(get("/api/details/find-all").with(userJwt("90000000-0000-0000-0000-000000000001", "USER"))).andExpect(status().isOk());
    }

    @Test
    void userCannotCreateDetail() throws Exception {
        mockMvc.perform(post("/api/details")
                .with(userJwt("90000000-0000-0000-0000-000000000001", "USER"))
                .contentType("application/json")
                .content("""
                        {
                        "name": "test",
                        "carDetailTypes": "Interior",
                        "deltaPrice": 1000,
                        "compatibleModelsIds":[
                            "30000000-0000-0000-0000-000000000001"
                            ]
                        }
                        """)).andExpect(status().isForbidden());
    }
}

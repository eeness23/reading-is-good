package com.enes.readingisgood.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
public class RoleSecurity {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "test-customer", password = "test-customer", roles = "ADMIN")
    void adminRole_canNotSendRequestToMonthlyStatic_returnForbiddenStatus() throws Exception {
        mockMvc.perform(get("/api/statistics/monthly"))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    @WithMockUser(username = "test-customer", password = "test-customer", roles = "CUSTOMER")
    void customerRole_canNotSendRequestToCreateBook_returnForbiddenStatus() throws Exception {
        String requestBody = "{\n" +
                "    \"name\": \"Ne Kadar Da Duygusal Bir Kitap\",\n" +
                "    \"author\":\"Memetali\",\n" +
                "    \"stock\": 4,\n" +
                "    \"price\": 17.55\n" +
                "}";

        mockMvc.perform(post("/api/books").content(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }
}

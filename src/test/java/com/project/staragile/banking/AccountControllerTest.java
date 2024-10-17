package com.project.staragile.banking;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.project.staragile.banking.model.Account;
import com.project.staragile.banking.service.AccountService;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAccount_ShouldReturnSuccess() throws Exception {
        Account account = new Account(12345, "John Doe", "Savings", 5000.0);
        when(accountService.registerAccount(account)).thenReturn(account);

        mockMvc.perform(post("/account/createAccount")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(account)))
                .andExpect(status().isOk())
                .andExpect(content().string("Account created successfully."));
    }

    @Test
    void updateAccount_ShouldReturnSuccess() throws Exception {
        Account account = new Account(12345, "John Doe", "Savings", 10000.0);
        when(accountService.updateAccount("12345", account)).thenReturn(account); // Update to String accountNo

        mockMvc.perform(put("/account/updateAccount/12345")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(account)))
                .andExpect(status().isOk())
                .andExpect(content().string("Account updated successfully."));
    }

    @Test
    void viewAccount_ShouldReturnAccountDetails() throws Exception {
        Account account = new Account(12345, "John Doe", "Savings", 10000.0);
        when(accountService.viewAccount("12345")).thenReturn(account); // Update to String accountNo

        mockMvc.perform(get("/account/viewAccount/12345"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value(12345)) // Use the correct JSON field
                .andExpect(jsonPath("$.accountHolderName").value("John Doe"))
                .andExpect(jsonPath("$.balance").value(10000.0));
    }

    @Test
    void deleteAccount_ShouldReturnSuccess() throws Exception {
        doNothing().when(accountService).deleteAccount("12345"); // Ensure the delete method is called

        mockMvc.perform(delete("/account/deleteAccount/12345"))
                .andExpect(status().isOk())
                .andExpect(content().string("Account deleted successfully."));
    }
}

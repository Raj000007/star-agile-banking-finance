package com.project.staragile.banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

package com.project.staragile.banking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.project.staragile.banking.model.Account;
import com.project.staragile.banking.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
public class TestAccountService {

    @Autowired
    private AccountService accountService;

    @MockBean
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        // Mocks are initialized by the Spring context
    }

    @Test
    void testCreateAccount() {
        // Arrange
        Account expectedAccount = new Account(1010101010, "Shubham", "Saving Account", 20000.0);
        when(accountRepository.save(any(Account.class))).thenReturn(expectedAccount);

        // Act
        Account actualAccount = accountService.createAccount();

        // Assert
        assertNotNull(actualAccount);
        assertEquals(expectedAccount.getAccountNumber(), actualAccount.getAccountNumber());
        assertEquals(expectedAccount.getAccountName(), actualAccount.getAccountName());
        verify(accountRepository, times(1)).save(any(Account.class)); // Verify save method is called once
    }

    @Test
    void testRegisterAccount() {
        // Arrange
        Account inputAccount = new Account(2020202020, "John", "Current Account", 50000.0);
        when(accountRepository.save(inputAccount)).thenReturn(inputAccount);

        // Act
        Account savedAccount = accountService.registerAccount(inputAccount);

        // Assert
        assertNotNull(savedAccount);
        assertEquals(inputAccount.getAccountNumber(), savedAccount.getAccountNumber());
        assertEquals(inputAccount.getAccountName(), savedAccount.getAccountName());
        verify(accountRepository, times(1)).save(inputAccount); // Ensure save method is invoked
    }

    @Test
    void testGetAccountDetails_AccountExists() {
        // Arrange
        int accountId = 1010101010;
        Account expectedAccount = new Account(accountId, "Shubham", "Saving Account", 20000.0);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(expectedAccount));

        // Act
        Account actualAccount = accountService.getAccountDetails(accountId);

        // Assert
        assertNotNull(actualAccount);
        assertEquals(expectedAccount.getAccountNumber(), actualAccount.getAccountNumber());
        assertEquals(expectedAccount.getAccountName(), actualAccount.getAccountName());
        verify(accountRepository, times(1)).findById(accountId); // Ensure findById method is called once
    }

    @Test
    void testGetAccountDetails_AccountNotFound() {
        // Arrange
        int accountId = 999999999;
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            accountService.getAccountDetails(accountId);
        });

        String expectedMessage = "Account with ID " + accountId + " not found.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testRegisterDummyAccount() {
        // Arrange
        Account expectedAccount = new Account(1010101010, "Shubham", "Saving Account", 20000.0);

        // Act
        Account actualAccount = accountService.registerDummyAccount();

        // Assert
        assertNotNull(actualAccount);
        assertEquals(expectedAccount.getAccountNumber(), actualAccount.getAccountNumber());
        assertEquals(expectedAccount.getAccountName(), actualAccount.getAccountName());
    }
}

package com.project.staragile.banking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    // Create a new hardcoded account
    public Account createAccount() {
        Account account = new Account(1010101010, "Shubham", "Saving Account", 20000.0);
        return accountRepository.save(account);
    }

    // Register a new account provided by the request
    public Account registerAccount(Account account) {
        return accountRepository.save(account);
    }

    // Retrieve account details based on account ID
    public Account getAccountDetails(int accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isPresent()) {
            return account.get();
        } else {
            // Handle case where account is not found
            throw new RuntimeException("Account with ID " + accountId + " not found.");
        }
    }

    // Register a dummy account (for testing purposes)
    public Account registerDummyAccount() {
        return new Account(1010101010, "Shubham", "Saving Account", 20000.0);
    }
}

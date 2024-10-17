package com.project.staragile.banking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.project.staragile.banking.model.Account;
import com.project.staragile.banking.service.AccountService;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    // POST - /createAccount
    @PostMapping("/createAccount")
    public ResponseEntity<String> createAccount(@RequestBody Account account) {
        accountService.createAccount(account);
        return ResponseEntity.ok("Account created successfully.");
    }

    // PUT - /updateAccount/{accountNo}
    @PutMapping("/updateAccount/{accountNo}")
    public ResponseEntity<String> updateAccount(@PathVariable String accountNo, @RequestBody Account account) {
        accountService.updateAccount(accountNo, account);
        return ResponseEntity.ok("Account updated successfully.");
    }

    // GET - /viewAccount/{accountNo}
    @GetMapping("/viewAccount/{accountNo}")
    public ResponseEntity<Account> viewAccount(@PathVariable String accountNo) {
        Account account = accountService.viewAccount(accountNo);
        if (account != null) {
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE - /deleteAccount/{accountNo}
    @DeleteMapping("/deleteAccount/{accountNo}")
    public ResponseEntity<String> deleteAccount(@PathVariable String accountNo) {
        accountService.deleteAccount(accountNo);
        return ResponseEntity.ok("Account deleted successfully.");
    }
}

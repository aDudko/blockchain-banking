package net.dudko.project.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import net.dudko.project.model.dto.AccountDto;
import net.dudko.project.model.dto.TransactionDto;
import net.dudko.project.model.dto.TransferFundDto;
import net.dudko.project.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Tag(
        name = "REST API for using Account Resource",
        description = "REST API for using Account Resource like CRUD Account, put money into the account, " +
                "withdraw money, transfer fund and get transactions of account"
)
@AllArgsConstructor
@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    @Operation(
            summary = "CREATE Account REST API",
            description = "Create Account REST API to save Account"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP STATUS 201 CREATED"
    )
    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto) {
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "GET Account REST API",
            description = "Get Account REST API to get Account by ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP STATUS 200 OK"
    )
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccount(id));
    }

    @Operation(
            summary = "GET all Accounts REST API",
            description = "Get all Accounts REST API to get all Accounts"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP STATUS 200 OK"
    )
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @Operation(
            summary = "To put money into the Account",
            description = "Update Account REST API to put money into the Account"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP STATUS 200 OK"
    )
    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountDto> deposit(@PathVariable Long id,
                                              @RequestBody Map<String, Double> request) {
        Double amount = request.get("amount");
        return ResponseEntity.ok(accountService.deposit(id, amount));
    }

    @Operation(
            summary = "Withdraw money from the Account",
            description = "Update Account REST API to withdraw money from the Account"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP STATUS 200 OK"
    )
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> withdraw(@PathVariable Long id,
                                               @RequestBody Map<String, Double> request) {
        Double amount = request.get("amount");
        return ResponseEntity.ok(accountService.withdraw(id, amount));
    }

    @Operation(
            summary = "ACTIVATE the account",
            description = "Update Account REST API to activate the Account"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP STATUS 200 OK"
    )
    @PutMapping("/{id}/activate")
    public ResponseEntity<String> activateAccount(@PathVariable Long id) {
        accountService.activateAccount(id);
        return ResponseEntity.ok("Account is activate successfully");
    }

    @Operation(
            summary = "HOLD the account",
            description = "Update Account REST API to hold the Account"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP STATUS 200 OK"
    )
    @PutMapping("/{id}/hold")
    public ResponseEntity<String> holdAccount(@PathVariable Long id) {
        accountService.holdAccount(id);
        return ResponseEntity.ok("Account is hold successfully");
    }

    @Operation(
            summary = "DELETE the account",
            description = "Delete Account REST API to delete the Account"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP STATUS 200 OK"
    )
    @PutMapping("/{id}/delete")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Account is deleted successfully");
    }

    @Operation(
            summary = "Transfer fund between two Accounts"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP STATUS 200 OK"
    )
    @PostMapping("/transfer")
    public ResponseEntity<String> transferFund(@RequestBody TransferFundDto transferFundDto) {
        accountService.transferFunds(transferFundDto);
        return ResponseEntity.ok("Transfer Successful");
    }

    @Operation(
            summary = "GET all transactions of the account",
            description = "Get transactions REST API to get all transactions of the Account"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP STATUS 200 OK"
    )
    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<TransactionDto>> getTransactions(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountTransactions(id));
    }

}

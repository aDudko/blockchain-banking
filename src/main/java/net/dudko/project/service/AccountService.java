package net.dudko.project.service;

import net.dudko.project.model.dto.AccountDto;
import net.dudko.project.model.dto.TransactionDto;
import net.dudko.project.model.dto.TransferFundDto;

import java.util.List;

public interface AccountService {

    AccountDto createAccount(AccountDto accountDto);

    AccountDto getAccount(Long id);

    List<AccountDto> getAllAccounts();

    AccountDto deposit(Long id, double amount);

    AccountDto withdraw(Long id, double amount);

    AccountDto holdAccount(Long id);

    AccountDto deleteAccount(Long id);

    AccountDto activateAccount(Long id);

    void transferFunds(TransferFundDto transferFundDto);

    List<TransactionDto> getAccountTransactions(Long id);

}

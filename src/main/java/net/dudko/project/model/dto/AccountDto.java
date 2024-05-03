package net.dudko.project.model.dto;

import net.dudko.project.domain.entity.account.AccountStatus;
import net.dudko.project.domain.entity.account.AccountType;
import net.dudko.project.domain.entity.account.Currency;

public record AccountDto(Long id,
                         String accountNumber,
                         String accountHolderName,
                         AccountType accountType,
                         double balance,
                         Currency currency,
                         AccountStatus accountStatus) { }

package net.dudko.project.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import net.dudko.project.domain.entity.account.AccountStatus;
import net.dudko.project.domain.entity.account.AccountType;
import net.dudko.project.domain.entity.account.Currency;

@Schema(
        description = "Account DTO (Data Transfer Object) to transfer the data between client and server"
)
public record AccountDto(Long id,

                         @Schema(
                                 description = "Account number (CONTO)"
                         )
                         String accountNumber,

                         @Schema(
                                 description = "Account holder name"
                         )
                         String accountHolderName,

                         @Schema(
                                 description = "Associated with account types 'DEBIT' or 'CREDIT'"
                         )
                         AccountType accountType,

                         @Schema(
                                 description = "Account balance"
                         )
                         double balance,

                         @Schema(
                                 description = "Associated with account currencies like 'USD', 'EUR' or 'RUB' for example"
                         )
                         Currency currency,

                         @Schema(
                                 description = "Associated with account statuses 'ACTIVE', 'HOLD' or 'DELETED'"
                         )
                         AccountStatus accountStatus) { }

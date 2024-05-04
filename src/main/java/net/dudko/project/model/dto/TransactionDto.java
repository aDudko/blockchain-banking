package net.dudko.project.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import net.dudko.project.domain.entity.transaction.TransactionType;

import java.time.LocalDateTime;

@Schema(
        description = "Transaction DTO (Data Transfer Object) to transfer the data between client and server"
)
public record TransactionDto(Long id,

                             @Schema(
                                     description = "Account id from database"
                             )
                             Long accountId,

                             @Schema(
                                     description = "Transfer amount"
                             )
                             double amount,

                             @Schema(
                                     description = "Associated with transaction types 'DEPOSIT', 'WITHDRAWAL' or 'TRANSFER'"
                             )
                             TransactionType tractionType,

                             @Schema(
                                     description = "Transaction occurred date time"
                             )
                             LocalDateTime timestamp) { }

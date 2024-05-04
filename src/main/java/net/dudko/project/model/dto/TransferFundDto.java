package net.dudko.project.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "TransferFund DTO (Data Transfer Object) to transfer the data between client and server"
)
public record TransferFundDto(@Schema(
                                  description = "Account ID from which money is transferred"
                              )
                              Long fromAccountId,

                              @Schema(
                                   description = "Account ШВ to which money is transferred"
                              )
                              Long toAccountId,

                              @Schema(
                                   description = "Transfer amount"
                              )
                              double amount) { }

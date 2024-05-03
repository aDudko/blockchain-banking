package net.dudko.project.model.dto;

public record TransferFundDto(Long fromAccountId,
                              Long toAccountId,
                              double amount) { }

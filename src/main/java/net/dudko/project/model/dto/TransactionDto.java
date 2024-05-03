package net.dudko.project.model.dto;

import net.dudko.project.domain.entity.transaction.TransactionType;

import java.time.LocalDateTime;

public record TransactionDto(Long id,
                             Long accountId,
                             double amount,
                             TransactionType tractionType,
                             LocalDateTime timestamp) { }

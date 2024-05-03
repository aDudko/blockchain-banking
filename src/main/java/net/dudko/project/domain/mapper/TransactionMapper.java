package net.dudko.project.domain.mapper;

import net.dudko.project.domain.entity.transaction.Transaction;
import net.dudko.project.model.dto.TransactionDto;

public class TransactionMapper {

    public static TransactionDto mapToTransactionDto(Transaction transaction) {
        return new TransactionDto(
                transaction.getId(),
                transaction.getAccountId(),
                transaction.getAmount(),
                transaction.getTractionType(),
                transaction.getTimestamp());
    }

}

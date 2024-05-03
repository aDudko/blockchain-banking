package net.dudko.project.domain.repository;

import net.dudko.project.domain.entity.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountIdOrderByTimestampDesc(Long accountId);

    Transaction findTopByOrderByIdDesc();

}

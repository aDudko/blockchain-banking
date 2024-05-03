package net.dudko.project.domain.repository;

import net.dudko.project.domain.entity.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}

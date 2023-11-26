package tech.luau.appsalonmng.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tech.luau.appsalonmng.domain.FinancialTransaction;

/**
 * Spring Data JPA repository for the FinancialTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FinancialTransactionRepository extends JpaRepository<FinancialTransaction, Long> {}

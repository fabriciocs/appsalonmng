package tech.luau.appsalonmng.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tech.luau.appsalonmng.domain.StockHistory;

/**
 * Spring Data JPA repository for the StockHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockHistoryRepository extends JpaRepository<StockHistory, Long> {}

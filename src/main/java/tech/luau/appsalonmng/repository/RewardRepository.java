package tech.luau.appsalonmng.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tech.luau.appsalonmng.domain.Reward;

/**
 * Spring Data JPA repository for the Reward entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RewardRepository extends JpaRepository<Reward, Long> {}

package tech.luau.appsalonmng.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.luau.appsalonmng.domain.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}

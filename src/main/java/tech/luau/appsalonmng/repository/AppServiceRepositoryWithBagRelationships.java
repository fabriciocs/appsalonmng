package tech.luau.appsalonmng.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import tech.luau.appsalonmng.domain.AppService;

public interface AppServiceRepositoryWithBagRelationships {
    Optional<AppService> fetchBagRelationships(Optional<AppService> appService);

    List<AppService> fetchBagRelationships(List<AppService> appServices);

    Page<AppService> fetchBagRelationships(Page<AppService> appServices);
}

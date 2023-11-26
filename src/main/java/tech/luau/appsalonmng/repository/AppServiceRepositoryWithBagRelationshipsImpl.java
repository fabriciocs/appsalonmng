package tech.luau.appsalonmng.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import tech.luau.appsalonmng.domain.AppService;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class AppServiceRepositoryWithBagRelationshipsImpl implements AppServiceRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<AppService> fetchBagRelationships(Optional<AppService> appService) {
        return appService.map(this::fetchProducts);
    }

    @Override
    public Page<AppService> fetchBagRelationships(Page<AppService> appServices) {
        return new PageImpl<>(fetchBagRelationships(appServices.getContent()), appServices.getPageable(), appServices.getTotalElements());
    }

    @Override
    public List<AppService> fetchBagRelationships(List<AppService> appServices) {
        return Optional.of(appServices).map(this::fetchProducts).orElse(Collections.emptyList());
    }

    AppService fetchProducts(AppService result) {
        return entityManager
            .createQuery(
                "select appService from AppService appService left join fetch appService.products where appService.id = :id",
                AppService.class
            )
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<AppService> fetchProducts(List<AppService> appServices) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, appServices.size()).forEach(index -> order.put(appServices.get(index).getId(), index));
        List<AppService> result = entityManager
            .createQuery(
                "select appService from AppService appService left join fetch appService.products where appService in :appServices",
                AppService.class
            )
            .setParameter("appServices", appServices)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}

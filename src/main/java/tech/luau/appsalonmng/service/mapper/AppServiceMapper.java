package tech.luau.appsalonmng.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;
import tech.luau.appsalonmng.domain.AppService;
import tech.luau.appsalonmng.domain.Product;
import tech.luau.appsalonmng.service.dto.AppServiceDTO;
import tech.luau.appsalonmng.service.dto.ProductDTO;

/**
 * Mapper for the entity {@link AppService} and its DTO {@link AppServiceDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppServiceMapper extends EntityMapper<AppServiceDTO, AppService> {
    @Mapping(target = "products", source = "products", qualifiedByName = "productIdSet")
    AppServiceDTO toDto(AppService s);

    @Mapping(target = "removeProducts", ignore = true)
    AppService toEntity(AppServiceDTO appServiceDTO);

    @Named("productId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductDTO toDtoProductId(Product product);

    @Named("productIdSet")
    default Set<ProductDTO> toDtoProductIdSet(Set<Product> product) {
        return product.stream().map(this::toDtoProductId).collect(Collectors.toSet());
    }
}

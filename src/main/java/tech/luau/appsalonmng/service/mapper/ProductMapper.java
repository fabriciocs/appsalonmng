package tech.luau.appsalonmng.service.mapper;

import org.mapstruct.*;
import tech.luau.appsalonmng.domain.Product;
import tech.luau.appsalonmng.service.dto.ProductDTO;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {}

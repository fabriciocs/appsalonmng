package tech.luau.appsalonmng.service.mapper;

import org.mapstruct.*;
import tech.luau.appsalonmng.domain.Product;
import tech.luau.appsalonmng.domain.StockHistory;
import tech.luau.appsalonmng.service.dto.ProductDTO;
import tech.luau.appsalonmng.service.dto.StockHistoryDTO;

/**
 * Mapper for the entity {@link StockHistory} and its DTO {@link StockHistoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface StockHistoryMapper extends EntityMapper<StockHistoryDTO, StockHistory> {
    @Mapping(target = "product", source = "product", qualifiedByName = "productId")
    StockHistoryDTO toDto(StockHistory s);

    @Named("productId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductDTO toDtoProductId(Product product);
}

package tech.luau.appsalonmng.service.mapper;

import org.mapstruct.*;
import tech.luau.appsalonmng.domain.FinancialTransaction;
import tech.luau.appsalonmng.service.dto.FinancialTransactionDTO;

/**
 * Mapper for the entity {@link FinancialTransaction} and its DTO {@link FinancialTransactionDTO}.
 */
@Mapper(componentModel = "spring")
public interface FinancialTransactionMapper extends EntityMapper<FinancialTransactionDTO, FinancialTransaction> {}

package tech.luau.appsalonmng.service.mapper;

import org.mapstruct.*;
import tech.luau.appsalonmng.domain.Client;
import tech.luau.appsalonmng.service.dto.ClientDTO;

/**
 * Mapper for the entity {@link Client} and its DTO {@link ClientDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClientMapper extends EntityMapper<ClientDTO, Client> {}

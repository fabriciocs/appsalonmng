package tech.luau.appsalonmng.service.mapper;

import org.mapstruct.*;
import tech.luau.appsalonmng.domain.AppUser;
import tech.luau.appsalonmng.domain.Client;
import tech.luau.appsalonmng.service.dto.AppUserDTO;
import tech.luau.appsalonmng.service.dto.ClientDTO;

/**
 * Mapper for the entity {@link AppUser} and its DTO {@link AppUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppUserMapper extends EntityMapper<AppUserDTO, AppUser> {
    @Mapping(target = "client", source = "client", qualifiedByName = "clientId")
    AppUserDTO toDto(AppUser s);

    @Named("clientId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClientDTO toDtoClientId(Client client);
}

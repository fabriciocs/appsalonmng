package tech.luau.appsalonmng.service.mapper;

import org.mapstruct.*;
import tech.luau.appsalonmng.domain.AppService;
import tech.luau.appsalonmng.domain.AppUser;
import tech.luau.appsalonmng.domain.Appointment;
import tech.luau.appsalonmng.domain.Client;
import tech.luau.appsalonmng.service.dto.AppServiceDTO;
import tech.luau.appsalonmng.service.dto.AppUserDTO;
import tech.luau.appsalonmng.service.dto.AppointmentDTO;
import tech.luau.appsalonmng.service.dto.ClientDTO;

/**
 * Mapper for the entity {@link Appointment} and its DTO {@link AppointmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppointmentMapper extends EntityMapper<AppointmentDTO, Appointment> {
    @Mapping(target = "client", source = "client", qualifiedByName = "clientId")
    @Mapping(target = "appService", source = "appService", qualifiedByName = "appServiceId")
    @Mapping(target = "appUser", source = "appUser", qualifiedByName = "appUserUsername")
    AppointmentDTO toDto(Appointment s);

    @Named("clientId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClientDTO toDtoClientId(Client client);

    @Named("appServiceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppServiceDTO toDtoAppServiceId(AppService appService);

    @Named("appUserUsername")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    AppUserDTO toDtoAppUserUsername(AppUser appUser);
}

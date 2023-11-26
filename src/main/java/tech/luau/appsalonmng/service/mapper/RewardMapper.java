package tech.luau.appsalonmng.service.mapper;

import org.mapstruct.*;
import tech.luau.appsalonmng.domain.Reward;
import tech.luau.appsalonmng.service.dto.RewardDTO;

/**
 * Mapper for the entity {@link Reward} and its DTO {@link RewardDTO}.
 */
@Mapper(componentModel = "spring")
public interface RewardMapper extends EntityMapper<RewardDTO, Reward> {}

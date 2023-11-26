package tech.luau.appsalonmng.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.luau.appsalonmng.domain.AppService;
import tech.luau.appsalonmng.repository.AppServiceRepository;
import tech.luau.appsalonmng.service.dto.AppServiceDTO;
import tech.luau.appsalonmng.service.mapper.AppServiceMapper;

/**
 * Service Implementation for managing {@link tech.luau.appsalonmng.domain.AppService}.
 */
@Service
@Transactional
public class AppServiceService {

    private final Logger log = LoggerFactory.getLogger(AppServiceService.class);

    private final AppServiceRepository appServiceRepository;

    private final AppServiceMapper appServiceMapper;

    public AppServiceService(AppServiceRepository appServiceRepository, AppServiceMapper appServiceMapper) {
        this.appServiceRepository = appServiceRepository;
        this.appServiceMapper = appServiceMapper;
    }

    /**
     * Save a appService.
     *
     * @param appServiceDTO the entity to save.
     * @return the persisted entity.
     */
    public AppServiceDTO save(AppServiceDTO appServiceDTO) {
        log.debug("Request to save AppService : {}", appServiceDTO);
        AppService appService = appServiceMapper.toEntity(appServiceDTO);
        appService = appServiceRepository.save(appService);
        return appServiceMapper.toDto(appService);
    }

    /**
     * Update a appService.
     *
     * @param appServiceDTO the entity to save.
     * @return the persisted entity.
     */
    public AppServiceDTO update(AppServiceDTO appServiceDTO) {
        log.debug("Request to update AppService : {}", appServiceDTO);
        AppService appService = appServiceMapper.toEntity(appServiceDTO);
        appService = appServiceRepository.save(appService);
        return appServiceMapper.toDto(appService);
    }

    /**
     * Partially update a appService.
     *
     * @param appServiceDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AppServiceDTO> partialUpdate(AppServiceDTO appServiceDTO) {
        log.debug("Request to partially update AppService : {}", appServiceDTO);

        return appServiceRepository
            .findById(appServiceDTO.getId())
            .map(existingAppService -> {
                appServiceMapper.partialUpdate(existingAppService, appServiceDTO);

                return existingAppService;
            })
            .map(appServiceRepository::save)
            .map(appServiceMapper::toDto);
    }

    /**
     * Get all the appServices.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AppServiceDTO> findAll() {
        log.debug("Request to get all AppServices");
        return appServiceRepository.findAll().stream().map(appServiceMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the appServices with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AppServiceDTO> findAllWithEagerRelationships(Pageable pageable) {
        return appServiceRepository.findAllWithEagerRelationships(pageable).map(appServiceMapper::toDto);
    }

    /**
     * Get one appService by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AppServiceDTO> findOne(Long id) {
        log.debug("Request to get AppService : {}", id);
        return appServiceRepository.findOneWithEagerRelationships(id).map(appServiceMapper::toDto);
    }

    /**
     * Delete the appService by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AppService : {}", id);
        appServiceRepository.deleteById(id);
    }
}

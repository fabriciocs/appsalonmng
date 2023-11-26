package tech.luau.appsalonmng.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;
import tech.luau.appsalonmng.repository.AppServiceRepository;
import tech.luau.appsalonmng.service.AppServiceService;
import tech.luau.appsalonmng.service.dto.AppServiceDTO;
import tech.luau.appsalonmng.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tech.luau.appsalonmng.domain.AppService}.
 */
@RestController
@RequestMapping("/api/app-services")
public class AppServiceResource {

    private final Logger log = LoggerFactory.getLogger(AppServiceResource.class);

    private static final String ENTITY_NAME = "appService";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppServiceService appServiceService;

    private final AppServiceRepository appServiceRepository;

    public AppServiceResource(AppServiceService appServiceService, AppServiceRepository appServiceRepository) {
        this.appServiceService = appServiceService;
        this.appServiceRepository = appServiceRepository;
    }

    /**
     * {@code POST  /app-services} : Create a new appService.
     *
     * @param appServiceDTO the appServiceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appServiceDTO, or with status {@code 400 (Bad Request)} if the appService has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AppServiceDTO> createAppService(@Valid @RequestBody AppServiceDTO appServiceDTO) throws URISyntaxException {
        log.debug("REST request to save AppService : {}", appServiceDTO);
        if (appServiceDTO.getId() != null) {
            throw new BadRequestAlertException("A new appService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppServiceDTO result = appServiceService.save(appServiceDTO);
        return ResponseEntity
            .created(new URI("/api/app-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /app-services/:id} : Updates an existing appService.
     *
     * @param id the id of the appServiceDTO to save.
     * @param appServiceDTO the appServiceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appServiceDTO,
     * or with status {@code 400 (Bad Request)} if the appServiceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appServiceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AppServiceDTO> updateAppService(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AppServiceDTO appServiceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AppService : {}, {}", id, appServiceDTO);
        if (appServiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appServiceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appServiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AppServiceDTO result = appServiceService.update(appServiceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appServiceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /app-services/:id} : Partial updates given fields of an existing appService, field will ignore if it is null
     *
     * @param id the id of the appServiceDTO to save.
     * @param appServiceDTO the appServiceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appServiceDTO,
     * or with status {@code 400 (Bad Request)} if the appServiceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the appServiceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the appServiceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppServiceDTO> partialUpdateAppService(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AppServiceDTO appServiceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AppService partially : {}, {}", id, appServiceDTO);
        if (appServiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appServiceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appServiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppServiceDTO> result = appServiceService.partialUpdate(appServiceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appServiceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /app-services} : get all the appServices.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appServices in body.
     */
    @GetMapping("")
    public List<AppServiceDTO> getAllAppServices(@RequestParam(required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all AppServices");
        return appServiceService.findAll();
    }

    /**
     * {@code GET  /app-services/:id} : get the "id" appService.
     *
     * @param id the id of the appServiceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appServiceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AppServiceDTO> getAppService(@PathVariable Long id) {
        log.debug("REST request to get AppService : {}", id);
        Optional<AppServiceDTO> appServiceDTO = appServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appServiceDTO);
    }

    /**
     * {@code DELETE  /app-services/:id} : delete the "id" appService.
     *
     * @param id the id of the appServiceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppService(@PathVariable Long id) {
        log.debug("REST request to delete AppService : {}", id);
        appServiceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

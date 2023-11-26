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
import tech.luau.appsalonmng.repository.StockHistoryRepository;
import tech.luau.appsalonmng.service.StockHistoryService;
import tech.luau.appsalonmng.service.dto.StockHistoryDTO;
import tech.luau.appsalonmng.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tech.luau.appsalonmng.domain.StockHistory}.
 */
@RestController
@RequestMapping("/api/stock-histories")
public class StockHistoryResource {

    private final Logger log = LoggerFactory.getLogger(StockHistoryResource.class);

    private static final String ENTITY_NAME = "stockHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StockHistoryService stockHistoryService;

    private final StockHistoryRepository stockHistoryRepository;

    public StockHistoryResource(StockHistoryService stockHistoryService, StockHistoryRepository stockHistoryRepository) {
        this.stockHistoryService = stockHistoryService;
        this.stockHistoryRepository = stockHistoryRepository;
    }

    /**
     * {@code POST  /stock-histories} : Create a new stockHistory.
     *
     * @param stockHistoryDTO the stockHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stockHistoryDTO, or with status {@code 400 (Bad Request)} if the stockHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<StockHistoryDTO> createStockHistory(@Valid @RequestBody StockHistoryDTO stockHistoryDTO)
        throws URISyntaxException {
        log.debug("REST request to save StockHistory : {}", stockHistoryDTO);
        if (stockHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new stockHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StockHistoryDTO result = stockHistoryService.save(stockHistoryDTO);
        return ResponseEntity
            .created(new URI("/api/stock-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stock-histories/:id} : Updates an existing stockHistory.
     *
     * @param id the id of the stockHistoryDTO to save.
     * @param stockHistoryDTO the stockHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stockHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the stockHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stockHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<StockHistoryDTO> updateStockHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StockHistoryDTO stockHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update StockHistory : {}, {}", id, stockHistoryDTO);
        if (stockHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stockHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stockHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StockHistoryDTO result = stockHistoryService.update(stockHistoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stockHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /stock-histories/:id} : Partial updates given fields of an existing stockHistory, field will ignore if it is null
     *
     * @param id the id of the stockHistoryDTO to save.
     * @param stockHistoryDTO the stockHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stockHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the stockHistoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the stockHistoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the stockHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StockHistoryDTO> partialUpdateStockHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StockHistoryDTO stockHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update StockHistory partially : {}, {}", id, stockHistoryDTO);
        if (stockHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stockHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stockHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StockHistoryDTO> result = stockHistoryService.partialUpdate(stockHistoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stockHistoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /stock-histories} : get all the stockHistories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stockHistories in body.
     */
    @GetMapping("")
    public List<StockHistoryDTO> getAllStockHistories() {
        log.debug("REST request to get all StockHistories");
        return stockHistoryService.findAll();
    }

    /**
     * {@code GET  /stock-histories/:id} : get the "id" stockHistory.
     *
     * @param id the id of the stockHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stockHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<StockHistoryDTO> getStockHistory(@PathVariable Long id) {
        log.debug("REST request to get StockHistory : {}", id);
        Optional<StockHistoryDTO> stockHistoryDTO = stockHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stockHistoryDTO);
    }

    /**
     * {@code DELETE  /stock-histories/:id} : delete the "id" stockHistory.
     *
     * @param id the id of the stockHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStockHistory(@PathVariable Long id) {
        log.debug("REST request to delete StockHistory : {}", id);
        stockHistoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

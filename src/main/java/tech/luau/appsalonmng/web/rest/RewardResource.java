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
import tech.luau.appsalonmng.repository.RewardRepository;
import tech.luau.appsalonmng.service.RewardService;
import tech.luau.appsalonmng.service.dto.RewardDTO;
import tech.luau.appsalonmng.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tech.luau.appsalonmng.domain.Reward}.
 */
@RestController
@RequestMapping("/api/rewards")
public class RewardResource {

    private final Logger log = LoggerFactory.getLogger(RewardResource.class);

    private static final String ENTITY_NAME = "reward";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RewardService rewardService;

    private final RewardRepository rewardRepository;

    public RewardResource(RewardService rewardService, RewardRepository rewardRepository) {
        this.rewardService = rewardService;
        this.rewardRepository = rewardRepository;
    }

    /**
     * {@code POST  /rewards} : Create a new reward.
     *
     * @param rewardDTO the rewardDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rewardDTO, or with status {@code 400 (Bad Request)} if the reward has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RewardDTO> createReward(@Valid @RequestBody RewardDTO rewardDTO) throws URISyntaxException {
        log.debug("REST request to save Reward : {}", rewardDTO);
        if (rewardDTO.getId() != null) {
            throw new BadRequestAlertException("A new reward cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RewardDTO result = rewardService.save(rewardDTO);
        return ResponseEntity
            .created(new URI("/api/rewards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rewards/:id} : Updates an existing reward.
     *
     * @param id the id of the rewardDTO to save.
     * @param rewardDTO the rewardDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rewardDTO,
     * or with status {@code 400 (Bad Request)} if the rewardDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rewardDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RewardDTO> updateReward(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RewardDTO rewardDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Reward : {}, {}", id, rewardDTO);
        if (rewardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rewardDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rewardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RewardDTO result = rewardService.update(rewardDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rewardDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rewards/:id} : Partial updates given fields of an existing reward, field will ignore if it is null
     *
     * @param id the id of the rewardDTO to save.
     * @param rewardDTO the rewardDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rewardDTO,
     * or with status {@code 400 (Bad Request)} if the rewardDTO is not valid,
     * or with status {@code 404 (Not Found)} if the rewardDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the rewardDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RewardDTO> partialUpdateReward(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RewardDTO rewardDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Reward partially : {}, {}", id, rewardDTO);
        if (rewardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rewardDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rewardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RewardDTO> result = rewardService.partialUpdate(rewardDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rewardDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /rewards} : get all the rewards.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rewards in body.
     */
    @GetMapping("")
    public List<RewardDTO> getAllRewards() {
        log.debug("REST request to get all Rewards");
        return rewardService.findAll();
    }

    /**
     * {@code GET  /rewards/:id} : get the "id" reward.
     *
     * @param id the id of the rewardDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rewardDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RewardDTO> getReward(@PathVariable Long id) {
        log.debug("REST request to get Reward : {}", id);
        Optional<RewardDTO> rewardDTO = rewardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rewardDTO);
    }

    /**
     * {@code DELETE  /rewards/:id} : delete the "id" reward.
     *
     * @param id the id of the rewardDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReward(@PathVariable Long id) {
        log.debug("REST request to delete Reward : {}", id);
        rewardService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

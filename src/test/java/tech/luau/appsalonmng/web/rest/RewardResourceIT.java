package tech.luau.appsalonmng.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import tech.luau.appsalonmng.IntegrationTest;
import tech.luau.appsalonmng.domain.Reward;
import tech.luau.appsalonmng.repository.RewardRepository;
import tech.luau.appsalonmng.service.dto.RewardDTO;
import tech.luau.appsalonmng.service.mapper.RewardMapper;

/**
 * Integration tests for the {@link RewardResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RewardResourceIT {

    private static final Integer DEFAULT_POINTS = 0;
    private static final Integer UPDATED_POINTS = 1;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/rewards";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RewardRepository rewardRepository;

    @Autowired
    private RewardMapper rewardMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRewardMockMvc;

    private Reward reward;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reward createEntity(EntityManager em) {
        Reward reward = new Reward().points(DEFAULT_POINTS).description(DEFAULT_DESCRIPTION);
        return reward;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reward createUpdatedEntity(EntityManager em) {
        Reward reward = new Reward().points(UPDATED_POINTS).description(UPDATED_DESCRIPTION);
        return reward;
    }

    @BeforeEach
    public void initTest() {
        reward = createEntity(em);
    }

    @Test
    @Transactional
    void createReward() throws Exception {
        int databaseSizeBeforeCreate = rewardRepository.findAll().size();
        // Create the Reward
        RewardDTO rewardDTO = rewardMapper.toDto(reward);
        restRewardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rewardDTO)))
            .andExpect(status().isCreated());

        // Validate the Reward in the database
        List<Reward> rewardList = rewardRepository.findAll();
        assertThat(rewardList).hasSize(databaseSizeBeforeCreate + 1);
        Reward testReward = rewardList.get(rewardList.size() - 1);
        assertThat(testReward.getPoints()).isEqualTo(DEFAULT_POINTS);
        assertThat(testReward.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createRewardWithExistingId() throws Exception {
        // Create the Reward with an existing ID
        reward.setId(1L);
        RewardDTO rewardDTO = rewardMapper.toDto(reward);

        int databaseSizeBeforeCreate = rewardRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRewardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rewardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reward in the database
        List<Reward> rewardList = rewardRepository.findAll();
        assertThat(rewardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRewards() throws Exception {
        // Initialize the database
        rewardRepository.saveAndFlush(reward);

        // Get all the rewardList
        restRewardMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reward.getId().intValue())))
            .andExpect(jsonPath("$.[*].points").value(hasItem(DEFAULT_POINTS)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    void getReward() throws Exception {
        // Initialize the database
        rewardRepository.saveAndFlush(reward);

        // Get the reward
        restRewardMockMvc
            .perform(get(ENTITY_API_URL_ID, reward.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reward.getId().intValue()))
            .andExpect(jsonPath("$.points").value(DEFAULT_POINTS))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingReward() throws Exception {
        // Get the reward
        restRewardMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingReward() throws Exception {
        // Initialize the database
        rewardRepository.saveAndFlush(reward);

        int databaseSizeBeforeUpdate = rewardRepository.findAll().size();

        // Update the reward
        Reward updatedReward = rewardRepository.findById(reward.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedReward are not directly saved in db
        em.detach(updatedReward);
        updatedReward.points(UPDATED_POINTS).description(UPDATED_DESCRIPTION);
        RewardDTO rewardDTO = rewardMapper.toDto(updatedReward);

        restRewardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rewardDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rewardDTO))
            )
            .andExpect(status().isOk());

        // Validate the Reward in the database
        List<Reward> rewardList = rewardRepository.findAll();
        assertThat(rewardList).hasSize(databaseSizeBeforeUpdate);
        Reward testReward = rewardList.get(rewardList.size() - 1);
        assertThat(testReward.getPoints()).isEqualTo(UPDATED_POINTS);
        assertThat(testReward.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingReward() throws Exception {
        int databaseSizeBeforeUpdate = rewardRepository.findAll().size();
        reward.setId(longCount.incrementAndGet());

        // Create the Reward
        RewardDTO rewardDTO = rewardMapper.toDto(reward);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRewardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rewardDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rewardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reward in the database
        List<Reward> rewardList = rewardRepository.findAll();
        assertThat(rewardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReward() throws Exception {
        int databaseSizeBeforeUpdate = rewardRepository.findAll().size();
        reward.setId(longCount.incrementAndGet());

        // Create the Reward
        RewardDTO rewardDTO = rewardMapper.toDto(reward);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRewardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rewardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reward in the database
        List<Reward> rewardList = rewardRepository.findAll();
        assertThat(rewardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReward() throws Exception {
        int databaseSizeBeforeUpdate = rewardRepository.findAll().size();
        reward.setId(longCount.incrementAndGet());

        // Create the Reward
        RewardDTO rewardDTO = rewardMapper.toDto(reward);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRewardMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rewardDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Reward in the database
        List<Reward> rewardList = rewardRepository.findAll();
        assertThat(rewardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRewardWithPatch() throws Exception {
        // Initialize the database
        rewardRepository.saveAndFlush(reward);

        int databaseSizeBeforeUpdate = rewardRepository.findAll().size();

        // Update the reward using partial update
        Reward partialUpdatedReward = new Reward();
        partialUpdatedReward.setId(reward.getId());

        partialUpdatedReward.points(UPDATED_POINTS);

        restRewardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReward.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReward))
            )
            .andExpect(status().isOk());

        // Validate the Reward in the database
        List<Reward> rewardList = rewardRepository.findAll();
        assertThat(rewardList).hasSize(databaseSizeBeforeUpdate);
        Reward testReward = rewardList.get(rewardList.size() - 1);
        assertThat(testReward.getPoints()).isEqualTo(UPDATED_POINTS);
        assertThat(testReward.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateRewardWithPatch() throws Exception {
        // Initialize the database
        rewardRepository.saveAndFlush(reward);

        int databaseSizeBeforeUpdate = rewardRepository.findAll().size();

        // Update the reward using partial update
        Reward partialUpdatedReward = new Reward();
        partialUpdatedReward.setId(reward.getId());

        partialUpdatedReward.points(UPDATED_POINTS).description(UPDATED_DESCRIPTION);

        restRewardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReward.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReward))
            )
            .andExpect(status().isOk());

        // Validate the Reward in the database
        List<Reward> rewardList = rewardRepository.findAll();
        assertThat(rewardList).hasSize(databaseSizeBeforeUpdate);
        Reward testReward = rewardList.get(rewardList.size() - 1);
        assertThat(testReward.getPoints()).isEqualTo(UPDATED_POINTS);
        assertThat(testReward.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingReward() throws Exception {
        int databaseSizeBeforeUpdate = rewardRepository.findAll().size();
        reward.setId(longCount.incrementAndGet());

        // Create the Reward
        RewardDTO rewardDTO = rewardMapper.toDto(reward);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRewardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rewardDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rewardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reward in the database
        List<Reward> rewardList = rewardRepository.findAll();
        assertThat(rewardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReward() throws Exception {
        int databaseSizeBeforeUpdate = rewardRepository.findAll().size();
        reward.setId(longCount.incrementAndGet());

        // Create the Reward
        RewardDTO rewardDTO = rewardMapper.toDto(reward);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRewardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rewardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reward in the database
        List<Reward> rewardList = rewardRepository.findAll();
        assertThat(rewardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReward() throws Exception {
        int databaseSizeBeforeUpdate = rewardRepository.findAll().size();
        reward.setId(longCount.incrementAndGet());

        // Create the Reward
        RewardDTO rewardDTO = rewardMapper.toDto(reward);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRewardMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(rewardDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Reward in the database
        List<Reward> rewardList = rewardRepository.findAll();
        assertThat(rewardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReward() throws Exception {
        // Initialize the database
        rewardRepository.saveAndFlush(reward);

        int databaseSizeBeforeDelete = rewardRepository.findAll().size();

        // Delete the reward
        restRewardMockMvc
            .perform(delete(ENTITY_API_URL_ID, reward.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Reward> rewardList = rewardRepository.findAll();
        assertThat(rewardList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

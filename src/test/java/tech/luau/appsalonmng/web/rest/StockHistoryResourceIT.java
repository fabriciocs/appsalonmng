package tech.luau.appsalonmng.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static tech.luau.appsalonmng.web.rest.TestUtil.sameInstant;

import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
import tech.luau.appsalonmng.domain.StockHistory;
import tech.luau.appsalonmng.repository.StockHistoryRepository;
import tech.luau.appsalonmng.service.dto.StockHistoryDTO;
import tech.luau.appsalonmng.service.mapper.StockHistoryMapper;

/**
 * Integration tests for the {@link StockHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StockHistoryResourceIT {

    private static final Integer DEFAULT_QUANTITY_CHANGED = 1;
    private static final Integer UPDATED_QUANTITY_CHANGED = 2;

    private static final ZonedDateTime DEFAULT_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/stock-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StockHistoryRepository stockHistoryRepository;

    @Autowired
    private StockHistoryMapper stockHistoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStockHistoryMockMvc;

    private StockHistory stockHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockHistory createEntity(EntityManager em) {
        StockHistory stockHistory = new StockHistory()
            .quantityChanged(DEFAULT_QUANTITY_CHANGED)
            .dateTime(DEFAULT_DATE_TIME)
            .reason(DEFAULT_REASON);
        return stockHistory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StockHistory createUpdatedEntity(EntityManager em) {
        StockHistory stockHistory = new StockHistory()
            .quantityChanged(UPDATED_QUANTITY_CHANGED)
            .dateTime(UPDATED_DATE_TIME)
            .reason(UPDATED_REASON);
        return stockHistory;
    }

    @BeforeEach
    public void initTest() {
        stockHistory = createEntity(em);
    }

    @Test
    @Transactional
    void createStockHistory() throws Exception {
        int databaseSizeBeforeCreate = stockHistoryRepository.findAll().size();
        // Create the StockHistory
        StockHistoryDTO stockHistoryDTO = stockHistoryMapper.toDto(stockHistory);
        restStockHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stockHistoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the StockHistory in the database
        List<StockHistory> stockHistoryList = stockHistoryRepository.findAll();
        assertThat(stockHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        StockHistory testStockHistory = stockHistoryList.get(stockHistoryList.size() - 1);
        assertThat(testStockHistory.getQuantityChanged()).isEqualTo(DEFAULT_QUANTITY_CHANGED);
        assertThat(testStockHistory.getDateTime()).isEqualTo(DEFAULT_DATE_TIME);
        assertThat(testStockHistory.getReason()).isEqualTo(DEFAULT_REASON);
    }

    @Test
    @Transactional
    void createStockHistoryWithExistingId() throws Exception {
        // Create the StockHistory with an existing ID
        stockHistory.setId(1L);
        StockHistoryDTO stockHistoryDTO = stockHistoryMapper.toDto(stockHistory);

        int databaseSizeBeforeCreate = stockHistoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stockHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StockHistory in the database
        List<StockHistory> stockHistoryList = stockHistoryRepository.findAll();
        assertThat(stockHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkQuantityChangedIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockHistoryRepository.findAll().size();
        // set the field null
        stockHistory.setQuantityChanged(null);

        // Create the StockHistory, which fails.
        StockHistoryDTO stockHistoryDTO = stockHistoryMapper.toDto(stockHistory);

        restStockHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stockHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<StockHistory> stockHistoryList = stockHistoryRepository.findAll();
        assertThat(stockHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockHistoryRepository.findAll().size();
        // set the field null
        stockHistory.setDateTime(null);

        // Create the StockHistory, which fails.
        StockHistoryDTO stockHistoryDTO = stockHistoryMapper.toDto(stockHistory);

        restStockHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stockHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<StockHistory> stockHistoryList = stockHistoryRepository.findAll();
        assertThat(stockHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStockHistories() throws Exception {
        // Initialize the database
        stockHistoryRepository.saveAndFlush(stockHistory);

        // Get all the stockHistoryList
        restStockHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantityChanged").value(hasItem(DEFAULT_QUANTITY_CHANGED)))
            .andExpect(jsonPath("$.[*].dateTime").value(hasItem(sameInstant(DEFAULT_DATE_TIME))))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)));
    }

    @Test
    @Transactional
    void getStockHistory() throws Exception {
        // Initialize the database
        stockHistoryRepository.saveAndFlush(stockHistory);

        // Get the stockHistory
        restStockHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, stockHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stockHistory.getId().intValue()))
            .andExpect(jsonPath("$.quantityChanged").value(DEFAULT_QUANTITY_CHANGED))
            .andExpect(jsonPath("$.dateTime").value(sameInstant(DEFAULT_DATE_TIME)))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON));
    }

    @Test
    @Transactional
    void getNonExistingStockHistory() throws Exception {
        // Get the stockHistory
        restStockHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStockHistory() throws Exception {
        // Initialize the database
        stockHistoryRepository.saveAndFlush(stockHistory);

        int databaseSizeBeforeUpdate = stockHistoryRepository.findAll().size();

        // Update the stockHistory
        StockHistory updatedStockHistory = stockHistoryRepository.findById(stockHistory.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStockHistory are not directly saved in db
        em.detach(updatedStockHistory);
        updatedStockHistory.quantityChanged(UPDATED_QUANTITY_CHANGED).dateTime(UPDATED_DATE_TIME).reason(UPDATED_REASON);
        StockHistoryDTO stockHistoryDTO = stockHistoryMapper.toDto(updatedStockHistory);

        restStockHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stockHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stockHistoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the StockHistory in the database
        List<StockHistory> stockHistoryList = stockHistoryRepository.findAll();
        assertThat(stockHistoryList).hasSize(databaseSizeBeforeUpdate);
        StockHistory testStockHistory = stockHistoryList.get(stockHistoryList.size() - 1);
        assertThat(testStockHistory.getQuantityChanged()).isEqualTo(UPDATED_QUANTITY_CHANGED);
        assertThat(testStockHistory.getDateTime()).isEqualTo(UPDATED_DATE_TIME);
        assertThat(testStockHistory.getReason()).isEqualTo(UPDATED_REASON);
    }

    @Test
    @Transactional
    void putNonExistingStockHistory() throws Exception {
        int databaseSizeBeforeUpdate = stockHistoryRepository.findAll().size();
        stockHistory.setId(longCount.incrementAndGet());

        // Create the StockHistory
        StockHistoryDTO stockHistoryDTO = stockHistoryMapper.toDto(stockHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stockHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stockHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StockHistory in the database
        List<StockHistory> stockHistoryList = stockHistoryRepository.findAll();
        assertThat(stockHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStockHistory() throws Exception {
        int databaseSizeBeforeUpdate = stockHistoryRepository.findAll().size();
        stockHistory.setId(longCount.incrementAndGet());

        // Create the StockHistory
        StockHistoryDTO stockHistoryDTO = stockHistoryMapper.toDto(stockHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStockHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stockHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StockHistory in the database
        List<StockHistory> stockHistoryList = stockHistoryRepository.findAll();
        assertThat(stockHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStockHistory() throws Exception {
        int databaseSizeBeforeUpdate = stockHistoryRepository.findAll().size();
        stockHistory.setId(longCount.incrementAndGet());

        // Create the StockHistory
        StockHistoryDTO stockHistoryDTO = stockHistoryMapper.toDto(stockHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStockHistoryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stockHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StockHistory in the database
        List<StockHistory> stockHistoryList = stockHistoryRepository.findAll();
        assertThat(stockHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStockHistoryWithPatch() throws Exception {
        // Initialize the database
        stockHistoryRepository.saveAndFlush(stockHistory);

        int databaseSizeBeforeUpdate = stockHistoryRepository.findAll().size();

        // Update the stockHistory using partial update
        StockHistory partialUpdatedStockHistory = new StockHistory();
        partialUpdatedStockHistory.setId(stockHistory.getId());

        partialUpdatedStockHistory.quantityChanged(UPDATED_QUANTITY_CHANGED);

        restStockHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStockHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStockHistory))
            )
            .andExpect(status().isOk());

        // Validate the StockHistory in the database
        List<StockHistory> stockHistoryList = stockHistoryRepository.findAll();
        assertThat(stockHistoryList).hasSize(databaseSizeBeforeUpdate);
        StockHistory testStockHistory = stockHistoryList.get(stockHistoryList.size() - 1);
        assertThat(testStockHistory.getQuantityChanged()).isEqualTo(UPDATED_QUANTITY_CHANGED);
        assertThat(testStockHistory.getDateTime()).isEqualTo(DEFAULT_DATE_TIME);
        assertThat(testStockHistory.getReason()).isEqualTo(DEFAULT_REASON);
    }

    @Test
    @Transactional
    void fullUpdateStockHistoryWithPatch() throws Exception {
        // Initialize the database
        stockHistoryRepository.saveAndFlush(stockHistory);

        int databaseSizeBeforeUpdate = stockHistoryRepository.findAll().size();

        // Update the stockHistory using partial update
        StockHistory partialUpdatedStockHistory = new StockHistory();
        partialUpdatedStockHistory.setId(stockHistory.getId());

        partialUpdatedStockHistory.quantityChanged(UPDATED_QUANTITY_CHANGED).dateTime(UPDATED_DATE_TIME).reason(UPDATED_REASON);

        restStockHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStockHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStockHistory))
            )
            .andExpect(status().isOk());

        // Validate the StockHistory in the database
        List<StockHistory> stockHistoryList = stockHistoryRepository.findAll();
        assertThat(stockHistoryList).hasSize(databaseSizeBeforeUpdate);
        StockHistory testStockHistory = stockHistoryList.get(stockHistoryList.size() - 1);
        assertThat(testStockHistory.getQuantityChanged()).isEqualTo(UPDATED_QUANTITY_CHANGED);
        assertThat(testStockHistory.getDateTime()).isEqualTo(UPDATED_DATE_TIME);
        assertThat(testStockHistory.getReason()).isEqualTo(UPDATED_REASON);
    }

    @Test
    @Transactional
    void patchNonExistingStockHistory() throws Exception {
        int databaseSizeBeforeUpdate = stockHistoryRepository.findAll().size();
        stockHistory.setId(longCount.incrementAndGet());

        // Create the StockHistory
        StockHistoryDTO stockHistoryDTO = stockHistoryMapper.toDto(stockHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stockHistoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stockHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StockHistory in the database
        List<StockHistory> stockHistoryList = stockHistoryRepository.findAll();
        assertThat(stockHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStockHistory() throws Exception {
        int databaseSizeBeforeUpdate = stockHistoryRepository.findAll().size();
        stockHistory.setId(longCount.incrementAndGet());

        // Create the StockHistory
        StockHistoryDTO stockHistoryDTO = stockHistoryMapper.toDto(stockHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStockHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stockHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StockHistory in the database
        List<StockHistory> stockHistoryList = stockHistoryRepository.findAll();
        assertThat(stockHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStockHistory() throws Exception {
        int databaseSizeBeforeUpdate = stockHistoryRepository.findAll().size();
        stockHistory.setId(longCount.incrementAndGet());

        // Create the StockHistory
        StockHistoryDTO stockHistoryDTO = stockHistoryMapper.toDto(stockHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStockHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stockHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StockHistory in the database
        List<StockHistory> stockHistoryList = stockHistoryRepository.findAll();
        assertThat(stockHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStockHistory() throws Exception {
        // Initialize the database
        stockHistoryRepository.saveAndFlush(stockHistory);

        int databaseSizeBeforeDelete = stockHistoryRepository.findAll().size();

        // Delete the stockHistory
        restStockHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, stockHistory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StockHistory> stockHistoryList = stockHistoryRepository.findAll();
        assertThat(stockHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package tech.luau.appsalonmng.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static tech.luau.appsalonmng.web.rest.TestUtil.sameInstant;
import static tech.luau.appsalonmng.web.rest.TestUtil.sameNumber;

import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
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
import tech.luau.appsalonmng.domain.FinancialTransaction;
import tech.luau.appsalonmng.repository.FinancialTransactionRepository;
import tech.luau.appsalonmng.service.dto.FinancialTransactionDTO;
import tech.luau.appsalonmng.service.mapper.FinancialTransactionMapper;

/**
 * Integration tests for the {@link FinancialTransactionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FinancialTransactionResourceIT {

    private static final String DEFAULT_TRANSACTION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_TYPE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(0);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(1);

    private static final ZonedDateTime DEFAULT_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/financial-transactions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FinancialTransactionRepository financialTransactionRepository;

    @Autowired
    private FinancialTransactionMapper financialTransactionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFinancialTransactionMockMvc;

    private FinancialTransaction financialTransaction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FinancialTransaction createEntity(EntityManager em) {
        FinancialTransaction financialTransaction = new FinancialTransaction()
            .transactionType(DEFAULT_TRANSACTION_TYPE)
            .amount(DEFAULT_AMOUNT)
            .dateTime(DEFAULT_DATE_TIME)
            .description(DEFAULT_DESCRIPTION);
        return financialTransaction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FinancialTransaction createUpdatedEntity(EntityManager em) {
        FinancialTransaction financialTransaction = new FinancialTransaction()
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .amount(UPDATED_AMOUNT)
            .dateTime(UPDATED_DATE_TIME)
            .description(UPDATED_DESCRIPTION);
        return financialTransaction;
    }

    @BeforeEach
    public void initTest() {
        financialTransaction = createEntity(em);
    }

    @Test
    @Transactional
    void createFinancialTransaction() throws Exception {
        int databaseSizeBeforeCreate = financialTransactionRepository.findAll().size();
        // Create the FinancialTransaction
        FinancialTransactionDTO financialTransactionDTO = financialTransactionMapper.toDto(financialTransaction);
        restFinancialTransactionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(financialTransactionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FinancialTransaction in the database
        List<FinancialTransaction> financialTransactionList = financialTransactionRepository.findAll();
        assertThat(financialTransactionList).hasSize(databaseSizeBeforeCreate + 1);
        FinancialTransaction testFinancialTransaction = financialTransactionList.get(financialTransactionList.size() - 1);
        assertThat(testFinancialTransaction.getTransactionType()).isEqualTo(DEFAULT_TRANSACTION_TYPE);
        assertThat(testFinancialTransaction.getAmount()).isEqualByComparingTo(DEFAULT_AMOUNT);
        assertThat(testFinancialTransaction.getDateTime()).isEqualTo(DEFAULT_DATE_TIME);
        assertThat(testFinancialTransaction.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createFinancialTransactionWithExistingId() throws Exception {
        // Create the FinancialTransaction with an existing ID
        financialTransaction.setId(1L);
        FinancialTransactionDTO financialTransactionDTO = financialTransactionMapper.toDto(financialTransaction);

        int databaseSizeBeforeCreate = financialTransactionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFinancialTransactionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(financialTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FinancialTransaction in the database
        List<FinancialTransaction> financialTransactionList = financialTransactionRepository.findAll();
        assertThat(financialTransactionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTransactionTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = financialTransactionRepository.findAll().size();
        // set the field null
        financialTransaction.setTransactionType(null);

        // Create the FinancialTransaction, which fails.
        FinancialTransactionDTO financialTransactionDTO = financialTransactionMapper.toDto(financialTransaction);

        restFinancialTransactionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(financialTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        List<FinancialTransaction> financialTransactionList = financialTransactionRepository.findAll();
        assertThat(financialTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = financialTransactionRepository.findAll().size();
        // set the field null
        financialTransaction.setAmount(null);

        // Create the FinancialTransaction, which fails.
        FinancialTransactionDTO financialTransactionDTO = financialTransactionMapper.toDto(financialTransaction);

        restFinancialTransactionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(financialTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        List<FinancialTransaction> financialTransactionList = financialTransactionRepository.findAll();
        assertThat(financialTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = financialTransactionRepository.findAll().size();
        // set the field null
        financialTransaction.setDateTime(null);

        // Create the FinancialTransaction, which fails.
        FinancialTransactionDTO financialTransactionDTO = financialTransactionMapper.toDto(financialTransaction);

        restFinancialTransactionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(financialTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        List<FinancialTransaction> financialTransactionList = financialTransactionRepository.findAll();
        assertThat(financialTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFinancialTransactions() throws Exception {
        // Initialize the database
        financialTransactionRepository.saveAndFlush(financialTransaction);

        // Get all the financialTransactionList
        restFinancialTransactionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(financialTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(sameNumber(DEFAULT_AMOUNT))))
            .andExpect(jsonPath("$.[*].dateTime").value(hasItem(sameInstant(DEFAULT_DATE_TIME))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    void getFinancialTransaction() throws Exception {
        // Initialize the database
        financialTransactionRepository.saveAndFlush(financialTransaction);

        // Get the financialTransaction
        restFinancialTransactionMockMvc
            .perform(get(ENTITY_API_URL_ID, financialTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(financialTransaction.getId().intValue()))
            .andExpect(jsonPath("$.transactionType").value(DEFAULT_TRANSACTION_TYPE))
            .andExpect(jsonPath("$.amount").value(sameNumber(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.dateTime").value(sameInstant(DEFAULT_DATE_TIME)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFinancialTransaction() throws Exception {
        // Get the financialTransaction
        restFinancialTransactionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFinancialTransaction() throws Exception {
        // Initialize the database
        financialTransactionRepository.saveAndFlush(financialTransaction);

        int databaseSizeBeforeUpdate = financialTransactionRepository.findAll().size();

        // Update the financialTransaction
        FinancialTransaction updatedFinancialTransaction = financialTransactionRepository
            .findById(financialTransaction.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedFinancialTransaction are not directly saved in db
        em.detach(updatedFinancialTransaction);
        updatedFinancialTransaction
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .amount(UPDATED_AMOUNT)
            .dateTime(UPDATED_DATE_TIME)
            .description(UPDATED_DESCRIPTION);
        FinancialTransactionDTO financialTransactionDTO = financialTransactionMapper.toDto(updatedFinancialTransaction);

        restFinancialTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, financialTransactionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(financialTransactionDTO))
            )
            .andExpect(status().isOk());

        // Validate the FinancialTransaction in the database
        List<FinancialTransaction> financialTransactionList = financialTransactionRepository.findAll();
        assertThat(financialTransactionList).hasSize(databaseSizeBeforeUpdate);
        FinancialTransaction testFinancialTransaction = financialTransactionList.get(financialTransactionList.size() - 1);
        assertThat(testFinancialTransaction.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
        assertThat(testFinancialTransaction.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
        assertThat(testFinancialTransaction.getDateTime()).isEqualTo(UPDATED_DATE_TIME);
        assertThat(testFinancialTransaction.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingFinancialTransaction() throws Exception {
        int databaseSizeBeforeUpdate = financialTransactionRepository.findAll().size();
        financialTransaction.setId(longCount.incrementAndGet());

        // Create the FinancialTransaction
        FinancialTransactionDTO financialTransactionDTO = financialTransactionMapper.toDto(financialTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFinancialTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, financialTransactionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(financialTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FinancialTransaction in the database
        List<FinancialTransaction> financialTransactionList = financialTransactionRepository.findAll();
        assertThat(financialTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFinancialTransaction() throws Exception {
        int databaseSizeBeforeUpdate = financialTransactionRepository.findAll().size();
        financialTransaction.setId(longCount.incrementAndGet());

        // Create the FinancialTransaction
        FinancialTransactionDTO financialTransactionDTO = financialTransactionMapper.toDto(financialTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFinancialTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(financialTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FinancialTransaction in the database
        List<FinancialTransaction> financialTransactionList = financialTransactionRepository.findAll();
        assertThat(financialTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFinancialTransaction() throws Exception {
        int databaseSizeBeforeUpdate = financialTransactionRepository.findAll().size();
        financialTransaction.setId(longCount.incrementAndGet());

        // Create the FinancialTransaction
        FinancialTransactionDTO financialTransactionDTO = financialTransactionMapper.toDto(financialTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFinancialTransactionMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(financialTransactionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FinancialTransaction in the database
        List<FinancialTransaction> financialTransactionList = financialTransactionRepository.findAll();
        assertThat(financialTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFinancialTransactionWithPatch() throws Exception {
        // Initialize the database
        financialTransactionRepository.saveAndFlush(financialTransaction);

        int databaseSizeBeforeUpdate = financialTransactionRepository.findAll().size();

        // Update the financialTransaction using partial update
        FinancialTransaction partialUpdatedFinancialTransaction = new FinancialTransaction();
        partialUpdatedFinancialTransaction.setId(financialTransaction.getId());

        partialUpdatedFinancialTransaction
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .amount(UPDATED_AMOUNT)
            .dateTime(UPDATED_DATE_TIME)
            .description(UPDATED_DESCRIPTION);

        restFinancialTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFinancialTransaction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFinancialTransaction))
            )
            .andExpect(status().isOk());

        // Validate the FinancialTransaction in the database
        List<FinancialTransaction> financialTransactionList = financialTransactionRepository.findAll();
        assertThat(financialTransactionList).hasSize(databaseSizeBeforeUpdate);
        FinancialTransaction testFinancialTransaction = financialTransactionList.get(financialTransactionList.size() - 1);
        assertThat(testFinancialTransaction.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
        assertThat(testFinancialTransaction.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
        assertThat(testFinancialTransaction.getDateTime()).isEqualTo(UPDATED_DATE_TIME);
        assertThat(testFinancialTransaction.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateFinancialTransactionWithPatch() throws Exception {
        // Initialize the database
        financialTransactionRepository.saveAndFlush(financialTransaction);

        int databaseSizeBeforeUpdate = financialTransactionRepository.findAll().size();

        // Update the financialTransaction using partial update
        FinancialTransaction partialUpdatedFinancialTransaction = new FinancialTransaction();
        partialUpdatedFinancialTransaction.setId(financialTransaction.getId());

        partialUpdatedFinancialTransaction
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .amount(UPDATED_AMOUNT)
            .dateTime(UPDATED_DATE_TIME)
            .description(UPDATED_DESCRIPTION);

        restFinancialTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFinancialTransaction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFinancialTransaction))
            )
            .andExpect(status().isOk());

        // Validate the FinancialTransaction in the database
        List<FinancialTransaction> financialTransactionList = financialTransactionRepository.findAll();
        assertThat(financialTransactionList).hasSize(databaseSizeBeforeUpdate);
        FinancialTransaction testFinancialTransaction = financialTransactionList.get(financialTransactionList.size() - 1);
        assertThat(testFinancialTransaction.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
        assertThat(testFinancialTransaction.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
        assertThat(testFinancialTransaction.getDateTime()).isEqualTo(UPDATED_DATE_TIME);
        assertThat(testFinancialTransaction.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingFinancialTransaction() throws Exception {
        int databaseSizeBeforeUpdate = financialTransactionRepository.findAll().size();
        financialTransaction.setId(longCount.incrementAndGet());

        // Create the FinancialTransaction
        FinancialTransactionDTO financialTransactionDTO = financialTransactionMapper.toDto(financialTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFinancialTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, financialTransactionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(financialTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FinancialTransaction in the database
        List<FinancialTransaction> financialTransactionList = financialTransactionRepository.findAll();
        assertThat(financialTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFinancialTransaction() throws Exception {
        int databaseSizeBeforeUpdate = financialTransactionRepository.findAll().size();
        financialTransaction.setId(longCount.incrementAndGet());

        // Create the FinancialTransaction
        FinancialTransactionDTO financialTransactionDTO = financialTransactionMapper.toDto(financialTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFinancialTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(financialTransactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FinancialTransaction in the database
        List<FinancialTransaction> financialTransactionList = financialTransactionRepository.findAll();
        assertThat(financialTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFinancialTransaction() throws Exception {
        int databaseSizeBeforeUpdate = financialTransactionRepository.findAll().size();
        financialTransaction.setId(longCount.incrementAndGet());

        // Create the FinancialTransaction
        FinancialTransactionDTO financialTransactionDTO = financialTransactionMapper.toDto(financialTransaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFinancialTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(financialTransactionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FinancialTransaction in the database
        List<FinancialTransaction> financialTransactionList = financialTransactionRepository.findAll();
        assertThat(financialTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFinancialTransaction() throws Exception {
        // Initialize the database
        financialTransactionRepository.saveAndFlush(financialTransaction);

        int databaseSizeBeforeDelete = financialTransactionRepository.findAll().size();

        // Delete the financialTransaction
        restFinancialTransactionMockMvc
            .perform(delete(ENTITY_API_URL_ID, financialTransaction.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FinancialTransaction> financialTransactionList = financialTransactionRepository.findAll();
        assertThat(financialTransactionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

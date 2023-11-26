package tech.luau.appsalonmng.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static tech.luau.appsalonmng.web.rest.TestUtil.sameNumber;

import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import tech.luau.appsalonmng.IntegrationTest;
import tech.luau.appsalonmng.domain.AppService;
import tech.luau.appsalonmng.repository.AppServiceRepository;
import tech.luau.appsalonmng.service.AppServiceService;
import tech.luau.appsalonmng.service.dto.AppServiceDTO;
import tech.luau.appsalonmng.service.mapper.AppServiceMapper;

/**
 * Integration tests for the {@link AppServiceResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AppServiceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(0);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(1);

    private static final String ENTITY_API_URL = "/api/app-services";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AppServiceRepository appServiceRepository;

    @Mock
    private AppServiceRepository appServiceRepositoryMock;

    @Autowired
    private AppServiceMapper appServiceMapper;

    @Mock
    private AppServiceService appServiceServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppServiceMockMvc;

    private AppService appService;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppService createEntity(EntityManager em) {
        AppService appService = new AppService()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .duration(DEFAULT_DURATION)
            .price(DEFAULT_PRICE);
        return appService;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppService createUpdatedEntity(EntityManager em) {
        AppService appService = new AppService()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .duration(UPDATED_DURATION)
            .price(UPDATED_PRICE);
        return appService;
    }

    @BeforeEach
    public void initTest() {
        appService = createEntity(em);
    }

    @Test
    @Transactional
    void createAppService() throws Exception {
        int databaseSizeBeforeCreate = appServiceRepository.findAll().size();
        // Create the AppService
        AppServiceDTO appServiceDTO = appServiceMapper.toDto(appService);
        restAppServiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appServiceDTO)))
            .andExpect(status().isCreated());

        // Validate the AppService in the database
        List<AppService> appServiceList = appServiceRepository.findAll();
        assertThat(appServiceList).hasSize(databaseSizeBeforeCreate + 1);
        AppService testAppService = appServiceList.get(appServiceList.size() - 1);
        assertThat(testAppService.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAppService.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAppService.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testAppService.getPrice()).isEqualByComparingTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void createAppServiceWithExistingId() throws Exception {
        // Create the AppService with an existing ID
        appService.setId(1L);
        AppServiceDTO appServiceDTO = appServiceMapper.toDto(appService);

        int databaseSizeBeforeCreate = appServiceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppServiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appServiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AppService in the database
        List<AppService> appServiceList = appServiceRepository.findAll();
        assertThat(appServiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = appServiceRepository.findAll().size();
        // set the field null
        appService.setName(null);

        // Create the AppService, which fails.
        AppServiceDTO appServiceDTO = appServiceMapper.toDto(appService);

        restAppServiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appServiceDTO)))
            .andExpect(status().isBadRequest());

        List<AppService> appServiceList = appServiceRepository.findAll();
        assertThat(appServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = appServiceRepository.findAll().size();
        // set the field null
        appService.setDuration(null);

        // Create the AppService, which fails.
        AppServiceDTO appServiceDTO = appServiceMapper.toDto(appService);

        restAppServiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appServiceDTO)))
            .andExpect(status().isBadRequest());

        List<AppService> appServiceList = appServiceRepository.findAll();
        assertThat(appServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = appServiceRepository.findAll().size();
        // set the field null
        appService.setPrice(null);

        // Create the AppService, which fails.
        AppServiceDTO appServiceDTO = appServiceMapper.toDto(appService);

        restAppServiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appServiceDTO)))
            .andExpect(status().isBadRequest());

        List<AppService> appServiceList = appServiceRepository.findAll();
        assertThat(appServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAppServices() throws Exception {
        // Initialize the database
        appServiceRepository.saveAndFlush(appService);

        // Get all the appServiceList
        restAppServiceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appService.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAppServicesWithEagerRelationshipsIsEnabled() throws Exception {
        when(appServiceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAppServiceMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(appServiceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAppServicesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(appServiceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAppServiceMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(appServiceRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAppService() throws Exception {
        // Initialize the database
        appServiceRepository.saveAndFlush(appService);

        // Get the appService
        restAppServiceMockMvc
            .perform(get(ENTITY_API_URL_ID, appService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appService.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.price").value(sameNumber(DEFAULT_PRICE)));
    }

    @Test
    @Transactional
    void getNonExistingAppService() throws Exception {
        // Get the appService
        restAppServiceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppService() throws Exception {
        // Initialize the database
        appServiceRepository.saveAndFlush(appService);

        int databaseSizeBeforeUpdate = appServiceRepository.findAll().size();

        // Update the appService
        AppService updatedAppService = appServiceRepository.findById(appService.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAppService are not directly saved in db
        em.detach(updatedAppService);
        updatedAppService.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).duration(UPDATED_DURATION).price(UPDATED_PRICE);
        AppServiceDTO appServiceDTO = appServiceMapper.toDto(updatedAppService);

        restAppServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appServiceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appServiceDTO))
            )
            .andExpect(status().isOk());

        // Validate the AppService in the database
        List<AppService> appServiceList = appServiceRepository.findAll();
        assertThat(appServiceList).hasSize(databaseSizeBeforeUpdate);
        AppService testAppService = appServiceList.get(appServiceList.size() - 1);
        assertThat(testAppService.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAppService.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAppService.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testAppService.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    void putNonExistingAppService() throws Exception {
        int databaseSizeBeforeUpdate = appServiceRepository.findAll().size();
        appService.setId(longCount.incrementAndGet());

        // Create the AppService
        AppServiceDTO appServiceDTO = appServiceMapper.toDto(appService);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appServiceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appServiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppService in the database
        List<AppService> appServiceList = appServiceRepository.findAll();
        assertThat(appServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppService() throws Exception {
        int databaseSizeBeforeUpdate = appServiceRepository.findAll().size();
        appService.setId(longCount.incrementAndGet());

        // Create the AppService
        AppServiceDTO appServiceDTO = appServiceMapper.toDto(appService);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appServiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppService in the database
        List<AppService> appServiceList = appServiceRepository.findAll();
        assertThat(appServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppService() throws Exception {
        int databaseSizeBeforeUpdate = appServiceRepository.findAll().size();
        appService.setId(longCount.incrementAndGet());

        // Create the AppService
        AppServiceDTO appServiceDTO = appServiceMapper.toDto(appService);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppServiceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appServiceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppService in the database
        List<AppService> appServiceList = appServiceRepository.findAll();
        assertThat(appServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppServiceWithPatch() throws Exception {
        // Initialize the database
        appServiceRepository.saveAndFlush(appService);

        int databaseSizeBeforeUpdate = appServiceRepository.findAll().size();

        // Update the appService using partial update
        AppService partialUpdatedAppService = new AppService();
        partialUpdatedAppService.setId(appService.getId());

        partialUpdatedAppService.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).duration(UPDATED_DURATION).price(UPDATED_PRICE);

        restAppServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppService))
            )
            .andExpect(status().isOk());

        // Validate the AppService in the database
        List<AppService> appServiceList = appServiceRepository.findAll();
        assertThat(appServiceList).hasSize(databaseSizeBeforeUpdate);
        AppService testAppService = appServiceList.get(appServiceList.size() - 1);
        assertThat(testAppService.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAppService.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAppService.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testAppService.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    void fullUpdateAppServiceWithPatch() throws Exception {
        // Initialize the database
        appServiceRepository.saveAndFlush(appService);

        int databaseSizeBeforeUpdate = appServiceRepository.findAll().size();

        // Update the appService using partial update
        AppService partialUpdatedAppService = new AppService();
        partialUpdatedAppService.setId(appService.getId());

        partialUpdatedAppService.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).duration(UPDATED_DURATION).price(UPDATED_PRICE);

        restAppServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppService))
            )
            .andExpect(status().isOk());

        // Validate the AppService in the database
        List<AppService> appServiceList = appServiceRepository.findAll();
        assertThat(appServiceList).hasSize(databaseSizeBeforeUpdate);
        AppService testAppService = appServiceList.get(appServiceList.size() - 1);
        assertThat(testAppService.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAppService.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAppService.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testAppService.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    void patchNonExistingAppService() throws Exception {
        int databaseSizeBeforeUpdate = appServiceRepository.findAll().size();
        appService.setId(longCount.incrementAndGet());

        // Create the AppService
        AppServiceDTO appServiceDTO = appServiceMapper.toDto(appService);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appServiceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appServiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppService in the database
        List<AppService> appServiceList = appServiceRepository.findAll();
        assertThat(appServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppService() throws Exception {
        int databaseSizeBeforeUpdate = appServiceRepository.findAll().size();
        appService.setId(longCount.incrementAndGet());

        // Create the AppService
        AppServiceDTO appServiceDTO = appServiceMapper.toDto(appService);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appServiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppService in the database
        List<AppService> appServiceList = appServiceRepository.findAll();
        assertThat(appServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppService() throws Exception {
        int databaseSizeBeforeUpdate = appServiceRepository.findAll().size();
        appService.setId(longCount.incrementAndGet());

        // Create the AppService
        AppServiceDTO appServiceDTO = appServiceMapper.toDto(appService);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppServiceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(appServiceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppService in the database
        List<AppService> appServiceList = appServiceRepository.findAll();
        assertThat(appServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppService() throws Exception {
        // Initialize the database
        appServiceRepository.saveAndFlush(appService);

        int databaseSizeBeforeDelete = appServiceRepository.findAll().size();

        // Delete the appService
        restAppServiceMockMvc
            .perform(delete(ENTITY_API_URL_ID, appService.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppService> appServiceList = appServiceRepository.findAll();
        assertThat(appServiceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

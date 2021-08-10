package pt.frodrigues.challenge.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pt.frodrigues.challenge.web.rest.TestUtil.sameInstant;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pt.frodrigues.challenge.IntegrationTest;
import pt.frodrigues.challenge.domain.Difference;
import pt.frodrigues.challenge.repository.DifferenceRepository;

/**
 * Integration tests for the {@link DifferenceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DifferenceResourceIT {

    private static final ZonedDateTime DEFAULT_DATETIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATETIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_VALUE = 1L;
    private static final Long UPDATED_VALUE = 2L;

    private static final Long DEFAULT_NUMBER = 1L;
    private static final Long UPDATED_NUMBER = 2L;

    private static final Long DEFAULT_OCCURRENCES = 1L;
    private static final Long UPDATED_OCCURRENCES = 2L;

    private static final String ENTITY_API_URL = "/api/differences";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DifferenceRepository differenceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDifferenceMockMvc;

    private Difference difference;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Difference createEntity(EntityManager em) {
        Difference difference = new Difference()
            .datetime(DEFAULT_DATETIME)
            .value(DEFAULT_VALUE)
            .number(DEFAULT_NUMBER)
            .occurrences(DEFAULT_OCCURRENCES);
        return difference;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Difference createUpdatedEntity(EntityManager em) {
        Difference difference = new Difference()
            .datetime(UPDATED_DATETIME)
            .value(UPDATED_VALUE)
            .number(UPDATED_NUMBER)
            .occurrences(UPDATED_OCCURRENCES);
        return difference;
    }

    @BeforeEach
    public void initTest() {
        difference = createEntity(em);
    }

    @Test
    @Transactional
    void createDifference() throws Exception {
        int databaseSizeBeforeCreate = differenceRepository.findAll().size();
        // Create the Difference
        restDifferenceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(difference)))
            .andExpect(status().isCreated());

        // Validate the Difference in the database
        List<Difference> differenceList = differenceRepository.findAll();
        assertThat(differenceList).hasSize(databaseSizeBeforeCreate + 1);
        Difference testDifference = differenceList.get(differenceList.size() - 1);
        assertThat(testDifference.getDatetime()).isEqualTo(DEFAULT_DATETIME);
        assertThat(testDifference.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testDifference.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testDifference.getOccurrences()).isEqualTo(DEFAULT_OCCURRENCES);
    }

    @Test
    @Transactional
    void createDifferenceWithExistingId() throws Exception {
        // Create the Difference with an existing ID
        difference.setId(1L);

        int databaseSizeBeforeCreate = differenceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDifferenceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(difference)))
            .andExpect(status().isBadRequest());

        // Validate the Difference in the database
        List<Difference> differenceList = differenceRepository.findAll();
        assertThat(differenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = differenceRepository.findAll().size();
        // set the field null
        difference.setValue(null);

        // Create the Difference, which fails.

        restDifferenceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(difference)))
            .andExpect(status().isBadRequest());

        List<Difference> differenceList = differenceRepository.findAll();
        assertThat(differenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = differenceRepository.findAll().size();
        // set the field null
        difference.setNumber(null);

        // Create the Difference, which fails.

        restDifferenceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(difference)))
            .andExpect(status().isBadRequest());

        List<Difference> differenceList = differenceRepository.findAll();
        assertThat(differenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDifferences() throws Exception {
        // Initialize the database
        differenceRepository.saveAndFlush(difference);

        // Get all the differenceList
        restDifferenceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(difference.getId().intValue())))
            .andExpect(jsonPath("$.[*].datetime").value(hasItem(sameInstant(DEFAULT_DATETIME))))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].occurrences").value(hasItem(DEFAULT_OCCURRENCES.intValue())));
    }

    @Test
    @Transactional
    void getDifference() throws Exception {
        // Initialize the database
        differenceRepository.saveAndFlush(difference);

        // Get the difference
        restDifferenceMockMvc
            .perform(get(ENTITY_API_URL_ID, difference.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(difference.getId().intValue()))
            .andExpect(jsonPath("$.datetime").value(sameInstant(DEFAULT_DATETIME)))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.intValue()))
            .andExpect(jsonPath("$.occurrences").value(DEFAULT_OCCURRENCES.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingDifference() throws Exception {
        // Get the difference
        restDifferenceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDifference() throws Exception {
        // Initialize the database
        differenceRepository.saveAndFlush(difference);

        int databaseSizeBeforeUpdate = differenceRepository.findAll().size();

        // Update the difference
        Difference updatedDifference = differenceRepository.findById(difference.getId()).get();
        // Disconnect from session so that the updates on updatedDifference are not directly saved in db
        em.detach(updatedDifference);
        updatedDifference.datetime(UPDATED_DATETIME).value(UPDATED_VALUE).number(UPDATED_NUMBER).occurrences(UPDATED_OCCURRENCES);

        restDifferenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDifference.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDifference))
            )
            .andExpect(status().isOk());

        // Validate the Difference in the database
        List<Difference> differenceList = differenceRepository.findAll();
        assertThat(differenceList).hasSize(databaseSizeBeforeUpdate);
        Difference testDifference = differenceList.get(differenceList.size() - 1);
        assertThat(testDifference.getDatetime()).isEqualTo(UPDATED_DATETIME);
        assertThat(testDifference.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testDifference.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testDifference.getOccurrences()).isEqualTo(UPDATED_OCCURRENCES);
    }

    @Test
    @Transactional
    void putNonExistingDifference() throws Exception {
        int databaseSizeBeforeUpdate = differenceRepository.findAll().size();
        difference.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDifferenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, difference.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(difference))
            )
            .andExpect(status().isBadRequest());

        // Validate the Difference in the database
        List<Difference> differenceList = differenceRepository.findAll();
        assertThat(differenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDifference() throws Exception {
        int databaseSizeBeforeUpdate = differenceRepository.findAll().size();
        difference.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDifferenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(difference))
            )
            .andExpect(status().isBadRequest());

        // Validate the Difference in the database
        List<Difference> differenceList = differenceRepository.findAll();
        assertThat(differenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDifference() throws Exception {
        int databaseSizeBeforeUpdate = differenceRepository.findAll().size();
        difference.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDifferenceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(difference)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Difference in the database
        List<Difference> differenceList = differenceRepository.findAll();
        assertThat(differenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDifferenceWithPatch() throws Exception {
        // Initialize the database
        differenceRepository.saveAndFlush(difference);

        int databaseSizeBeforeUpdate = differenceRepository.findAll().size();

        // Update the difference using partial update
        Difference partialUpdatedDifference = new Difference();
        partialUpdatedDifference.setId(difference.getId());

        partialUpdatedDifference.datetime(UPDATED_DATETIME).value(UPDATED_VALUE).number(UPDATED_NUMBER).occurrences(UPDATED_OCCURRENCES);

        restDifferenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDifference.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDifference))
            )
            .andExpect(status().isOk());

        // Validate the Difference in the database
        List<Difference> differenceList = differenceRepository.findAll();
        assertThat(differenceList).hasSize(databaseSizeBeforeUpdate);
        Difference testDifference = differenceList.get(differenceList.size() - 1);
        assertThat(testDifference.getDatetime()).isEqualTo(UPDATED_DATETIME);
        assertThat(testDifference.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testDifference.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testDifference.getOccurrences()).isEqualTo(UPDATED_OCCURRENCES);
    }

    @Test
    @Transactional
    void fullUpdateDifferenceWithPatch() throws Exception {
        // Initialize the database
        differenceRepository.saveAndFlush(difference);

        int databaseSizeBeforeUpdate = differenceRepository.findAll().size();

        // Update the difference using partial update
        Difference partialUpdatedDifference = new Difference();
        partialUpdatedDifference.setId(difference.getId());

        partialUpdatedDifference.datetime(UPDATED_DATETIME).value(UPDATED_VALUE).number(UPDATED_NUMBER).occurrences(UPDATED_OCCURRENCES);

        restDifferenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDifference.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDifference))
            )
            .andExpect(status().isOk());

        // Validate the Difference in the database
        List<Difference> differenceList = differenceRepository.findAll();
        assertThat(differenceList).hasSize(databaseSizeBeforeUpdate);
        Difference testDifference = differenceList.get(differenceList.size() - 1);
        assertThat(testDifference.getDatetime()).isEqualTo(UPDATED_DATETIME);
        assertThat(testDifference.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testDifference.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testDifference.getOccurrences()).isEqualTo(UPDATED_OCCURRENCES);
    }

    @Test
    @Transactional
    void patchNonExistingDifference() throws Exception {
        int databaseSizeBeforeUpdate = differenceRepository.findAll().size();
        difference.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDifferenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, difference.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(difference))
            )
            .andExpect(status().isBadRequest());

        // Validate the Difference in the database
        List<Difference> differenceList = differenceRepository.findAll();
        assertThat(differenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDifference() throws Exception {
        int databaseSizeBeforeUpdate = differenceRepository.findAll().size();
        difference.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDifferenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(difference))
            )
            .andExpect(status().isBadRequest());

        // Validate the Difference in the database
        List<Difference> differenceList = differenceRepository.findAll();
        assertThat(differenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDifference() throws Exception {
        int databaseSizeBeforeUpdate = differenceRepository.findAll().size();
        difference.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDifferenceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(difference))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Difference in the database
        List<Difference> differenceList = differenceRepository.findAll();
        assertThat(differenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDifference() throws Exception {
        // Initialize the database
        differenceRepository.saveAndFlush(difference);

        int databaseSizeBeforeDelete = differenceRepository.findAll().size();

        // Delete the difference
        restDifferenceMockMvc
            .perform(delete(ENTITY_API_URL_ID, difference.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Difference> differenceList = differenceRepository.findAll();
        assertThat(differenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

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
import java.util.concurrent.TimeUnit;
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

    private static final ZonedDateTime DEFAULT_DATETIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_VALUE = 0L;

    private static final Long DEFAULT_NUMBER = 1L;

    private static final Long DEFAULT_OCCURRENCES = 1L;
    private static final Long UPDATED_OCCURRENCES = 2L;

    private static final String ENTITY_API_URL = "/difference";
    private static final String ENTITY_API_URL_NUMBER = ENTITY_API_URL + "?number={n}";

    @Autowired
    private DifferenceRepository differenceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDifferenceMockMvc;

    @Test
    @Transactional
    void getDifference() throws Exception {
        int databaseSizeBeforeCreate = differenceRepository.findAll().size();
        // Create the Difference
        restDifferenceMockMvc.perform(get(ENTITY_API_URL_NUMBER, DEFAULT_NUMBER)).andExpect(status().is2xxSuccessful());

        // Validate the Difference in the database
        List<Difference> differenceList = differenceRepository.findAll();
        assertThat(differenceList).hasSize(databaseSizeBeforeCreate + 1);
        Difference testDifference = differenceList.get(differenceList.size() - 1);
        assertThat(testDifference.getDatetime()).isAfterOrEqualTo(DEFAULT_DATETIME);
        assertThat(testDifference.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testDifference.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testDifference.getOccurrences()).isEqualTo(DEFAULT_OCCURRENCES);
    }

    @Test
    @Transactional
    void getRepeatedDifference() throws Exception {
        // Create the Difference
        restDifferenceMockMvc.perform(get(ENTITY_API_URL_NUMBER, DEFAULT_NUMBER)).andExpect(status().is2xxSuccessful());

        //Repeat with some time interval
        //We would need to change this if we needed to do a lot of tests
        TimeUnit.MILLISECONDS.sleep(1);

        restDifferenceMockMvc.perform(get(ENTITY_API_URL_NUMBER, DEFAULT_NUMBER)).andExpect(status().is2xxSuccessful());

        // Validate the Difference in the database
        List<Difference> differenceList = differenceRepository.findAll();
        Difference testDifference = differenceList.get(differenceList.size() - 1);
        assertThat(testDifference.getDatetime()).isAfter(DEFAULT_DATETIME);
        assertThat(testDifference.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testDifference.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testDifference.getOccurrences()).isEqualTo(UPDATED_OCCURRENCES);
    }

    @Test
    @Transactional
    void getDifferenceWithInvalidLowerNumber() throws Exception {
        int databaseSizeBeforeCreate = differenceRepository.findAll().size();

        restDifferenceMockMvc.perform(get(ENTITY_API_URL_NUMBER, -1)).andExpect(status().is4xxClientError());

        // Validate the Difference in the database
        List<Difference> differenceList = differenceRepository.findAll();
        assertThat(differenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getDifferenceWithInvalidHigherNumber() throws Exception {
        int databaseSizeBeforeCreate = differenceRepository.findAll().size();

        restDifferenceMockMvc.perform(get(ENTITY_API_URL_NUMBER, 101)).andExpect(status().is4xxClientError());

        // Validate the Difference in the database
        List<Difference> differenceList = differenceRepository.findAll();
        assertThat(differenceList).hasSize(databaseSizeBeforeCreate);
    }
}

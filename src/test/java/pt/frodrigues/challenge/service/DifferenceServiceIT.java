package pt.frodrigues.challenge.service;

import static org.assertj.core.api.Assertions.assertThat;

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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pt.frodrigues.challenge.IntegrationTest;
import pt.frodrigues.challenge.domain.Difference;
import pt.frodrigues.challenge.repository.DifferenceRepository;
import pt.frodrigues.challenge.service.DifferenceService;

/**
 * Integration tests for the {@link DifferenceService}.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
public class DifferenceServiceIT {

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
    private DifferenceService differenceService;

    @Test
    @Transactional
    void getDifference() {
        int databaseSizeBeforeCreate = differenceRepository.findAll().size();

        differenceService.getDifference(DEFAULT_NUMBER);

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
        int databaseSizeBeforeCreate = differenceRepository.findAll().size();

        differenceService.getDifference(DEFAULT_NUMBER);

        int databaseSizeAfterFirstGet = differenceRepository.findAll().size();

        //We would need to change this if we needed to do a lot of tests
        TimeUnit.MILLISECONDS.sleep(1);

        differenceService.getDifference(DEFAULT_NUMBER);

        // Validate the Difference in the database
        List<Difference> differenceList = differenceRepository.findAll();
        assertThat(differenceList).hasSize(databaseSizeAfterFirstGet);
        Difference testDifference = differenceList.get(differenceList.size() - 1);
        assertThat(testDifference.getDatetime()).isAfter(DEFAULT_DATETIME);
        assertThat(testDifference.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testDifference.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testDifference.getOccurrences()).isEqualTo(UPDATED_OCCURRENCES);
    }

    @Test
    void calculateDiff() {
        assertThat(differenceService.calculateDiff(1L)).isEqualTo(0L);
        assertThat(differenceService.calculateDiff(10L)).isEqualTo(2640L);
    }
}

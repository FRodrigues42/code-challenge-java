package pt.frodrigues.challenge.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.frodrigues.challenge.domain.Difference;
import pt.frodrigues.challenge.repository.DifferenceRepository;

/**
 * Service Implementation for managing {@link Difference}.
 */
@Service
@Transactional
public class DifferencesService {

    private final Logger log = LoggerFactory.getLogger(DifferencesService.class);

    private final DifferenceRepository differenceRepository;

    public DifferencesService(DifferenceRepository differenceRepository) {
        this.differenceRepository = differenceRepository;
    }

    /**
     * Save a difference.
     *
     * @param difference the entity to save.
     * @return the persisted entity.
     */
    public Difference save(Difference difference) {
        log.debug("Request to save Difference : {}", difference);
        return differenceRepository.save(difference);
    }

    /**
     * Partially update a difference.
     *
     * @param difference the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Difference> partialUpdate(Difference difference) {
        log.debug("Request to partially update Difference : {}", difference);

        return differenceRepository
            .findById(difference.getId())
            .map(
                existingDifference -> {
                    if (difference.getDatetime() != null) {
                        existingDifference.setDatetime(difference.getDatetime());
                    }
                    if (difference.getValue() != null) {
                        existingDifference.setValue(difference.getValue());
                    }
                    if (difference.getNumber() != null) {
                        existingDifference.setNumber(difference.getNumber());
                    }
                    if (difference.getOccurrences() != null) {
                        existingDifference.setOccurrences(difference.getOccurrences());
                    }

                    return existingDifference;
                }
            )
            .map(differenceRepository::save);
    }

    /**
     * Get all the differences.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Difference> findAll() {
        log.debug("Request to get all Differences");
        return differenceRepository.findAll();
    }

    /**
     * Get one difference by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Difference> findOne(Long id) {
        log.debug("Request to get Difference : {}", id);
        return differenceRepository.findById(id);
    }

    /**
     * Delete the difference by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Difference : {}", id);
        differenceRepository.deleteById(id);
    }
}

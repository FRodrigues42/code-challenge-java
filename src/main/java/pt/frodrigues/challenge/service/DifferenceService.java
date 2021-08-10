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
 * Service Implementation of the Main Exercise to get {@link Difference}.
 */
@Service
@Transactional
public class DifferenceService {

    private final Logger log = LoggerFactory.getLogger(DifferenceService.class);

    private final DifferenceRepository differenceRepository;

    public DifferenceService(DifferenceRepository differenceRepository) {
        this.differenceRepository = differenceRepository;
    }

    public Difference getDifference(Long n) {
        Optional<Difference> previous = differenceRepository.findByNumber(n);

        Difference diff;

        if (previous.isPresent()) {
            diff = previous.get();
            diff.setOccurrences(diff.getOccurrences() + 1);
        } else {
            diff = new Difference();
            diff.setNumber(n);
            diff.setValue(calculateDiff(n));
            diff.setOccurrences(1L);
        }

        diff.setDatetime(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("Europe/Lisbon")));
        return differenceRepository.save(diff);
    }

    Long calculateDiff(Long n) {
        long sumOfSquares = 0L;
        long sum = 0L;

        for (int i = 1; i <= n; ++i) {
            sumOfSquares += i * i;
            sum += i;
        }

        long squareOfSum = sum * sum;

        return squareOfSum - sumOfSquares;
    }
}

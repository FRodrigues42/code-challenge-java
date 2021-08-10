package pt.frodrigues.challenge.web.rest;

import java.util.Optional;
import java.util.function.Consumer;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.frodrigues.challenge.domain.Difference;
import pt.frodrigues.challenge.repository.DifferenceRepository;
import pt.frodrigues.challenge.service.DifferenceService;
import pt.frodrigues.challenge.service.DifferencesService;
import pt.frodrigues.challenge.web.rest.errors.InvalidNumberException;
import pt.frodrigues.challenge.web.rest.vm.DifferenceVM;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link Difference}.
 */
@RestController
@RequestMapping("")
public class DifferenceResource {

    private final Logger log = LoggerFactory.getLogger(DifferenceResource.class);

    private static final String ENTITY_NAME = "difference";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DifferenceService differenceService;

    private final DifferenceRepository differenceRepository;

    public DifferenceResource(DifferenceService differenceService, DifferenceRepository differenceRepository) {
        this.differenceService = differenceService;
        this.differenceRepository = differenceRepository;
    }

    /**
     * GET /difference : Get the difference between the sum of the squares of the N natural numbers and the square of the sum of the same numbers
     *
     * @param n Number for the first N natural numbers (required)
     * @return Successful calculation (status code 200)
     *         or Invalid number (must be greater than 0 and less or equal to 100) (status code 400)
     */
    @GetMapping("/difference")
    public ResponseEntity<DifferenceVM> getDifferenceByNumber(@NotNull @Min(1L) @Max(100L) @Valid @RequestParam(value = "number") Long n) {
        log.debug("REST request to get Difference : {}", n);

        if (n < 1 || n > 100) {
            throw new InvalidNumberException();
        }

        DifferenceVM result = new DifferenceVM(differenceService.getDifference(n));
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(applicationName, "Get difference of " + n, n.toString())).body(result);
    }
}

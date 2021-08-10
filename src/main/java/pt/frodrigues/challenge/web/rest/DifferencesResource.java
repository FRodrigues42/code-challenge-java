package pt.frodrigues.challenge.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.frodrigues.challenge.domain.Difference;
import pt.frodrigues.challenge.repository.DifferenceRepository;
import pt.frodrigues.challenge.service.DifferencesService;
import pt.frodrigues.challenge.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link pt.frodrigues.challenge.domain.Difference}.
 */
@RestController
@RequestMapping("/api")
public class DifferencesResource {

    private final Logger log = LoggerFactory.getLogger(DifferencesResource.class);

    private static final String ENTITY_NAME = "difference";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DifferencesService differencesService;

    private final DifferenceRepository differenceRepository;

    public DifferencesResource(DifferencesService differencesService, DifferenceRepository differenceRepository) {
        this.differencesService = differencesService;
        this.differenceRepository = differenceRepository;
    }

    /**
     * {@code POST  /differences} : Create a new difference.
     *
     * @param difference the difference to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new difference, or with status {@code 400 (Bad Request)} if the difference has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/differences")
    public ResponseEntity<Difference> createDifference(@Valid @RequestBody Difference difference) throws URISyntaxException {
        log.debug("REST request to save Difference : {}", difference);
        if (difference.getId() != null) {
            throw new BadRequestAlertException("A new difference cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Difference result = differencesService.save(difference);
        return ResponseEntity
            .created(new URI("/api/differences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /differences/:id} : Updates an existing difference.
     *
     * @param id the id of the difference to save.
     * @param difference the difference to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated difference,
     * or with status {@code 400 (Bad Request)} if the difference is not valid,
     * or with status {@code 500 (Internal Server Error)} if the difference couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/differences/{id}")
    public ResponseEntity<Difference> updateDifference(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Difference difference
    ) throws URISyntaxException {
        log.debug("REST request to update Difference : {}, {}", id, difference);
        if (difference.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, difference.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!differenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Difference result = differencesService.save(difference);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, difference.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /differences/:id} : Partial updates given fields of an existing difference, field will ignore if it is null
     *
     * @param id the id of the difference to save.
     * @param difference the difference to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated difference,
     * or with status {@code 400 (Bad Request)} if the difference is not valid,
     * or with status {@code 404 (Not Found)} if the difference is not found,
     * or with status {@code 500 (Internal Server Error)} if the difference couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/differences/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Difference> partialUpdateDifference(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Difference difference
    ) throws URISyntaxException {
        log.debug("REST request to partial update Difference partially : {}, {}", id, difference);
        if (difference.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, difference.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!differenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Difference> result = differencesService.partialUpdate(difference);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, difference.getId().toString())
        );
    }

    /**
     * {@code GET  /differences} : get all the differences.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of differences in body.
     */
    @GetMapping("/differences")
    public List<Difference> getAllDifferences() {
        log.debug("REST request to get all Differences");
        return differencesService.findAll();
    }

    /**
     * {@code GET  /differences/:id} : get the "id" difference.
     *
     * @param id the id of the difference to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the difference, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/differences/{id}")
    public ResponseEntity<Difference> getDifference(@PathVariable Long id) {
        log.debug("REST request to get Difference : {}", id);
        Optional<Difference> difference = differencesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(difference);
    }

    /**
     * {@code DELETE  /differences/:id} : delete the "id" difference.
     *
     * @param id the id of the difference to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/differences/{id}")
    public ResponseEntity<Void> deleteDifference(@PathVariable Long id) {
        log.debug("REST request to delete Difference : {}", id);
        differencesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

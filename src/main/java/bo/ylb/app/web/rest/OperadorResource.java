package bo.ylb.app.web.rest;

import bo.ylb.app.repository.OperadorRepository;
import bo.ylb.app.service.OperadorQueryService;
import bo.ylb.app.service.OperadorService;
import bo.ylb.app.service.criteria.OperadorCriteria;
import bo.ylb.app.service.dto.OperadorDTO;
import bo.ylb.app.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link bo.ylb.app.domain.Operador}.
 */
@RestController
@RequestMapping("/api/operadors")
public class OperadorResource {

    private static final Logger LOG = LoggerFactory.getLogger(OperadorResource.class);

    private static final String ENTITY_NAME = "operador";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OperadorService operadorService;

    private final OperadorRepository operadorRepository;

    private final OperadorQueryService operadorQueryService;

    public OperadorResource(
        OperadorService operadorService,
        OperadorRepository operadorRepository,
        OperadorQueryService operadorQueryService
    ) {
        this.operadorService = operadorService;
        this.operadorRepository = operadorRepository;
        this.operadorQueryService = operadorQueryService;
    }

    /**
     * {@code POST  /operadors} : Create a new operador.
     *
     * @param operadorDTO the operadorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new operadorDTO, or with status {@code 400 (Bad Request)} if the operador has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OperadorDTO> createOperador(@Valid @RequestBody OperadorDTO operadorDTO) throws URISyntaxException {
        LOG.debug("REST request to save Operador : {}", operadorDTO);
        if (operadorDTO.getId() != null) {
            throw new BadRequestAlertException("A new operador cannot already have an ID", ENTITY_NAME, "idexists");
        }
        operadorDTO = operadorService.save(operadorDTO);
        return ResponseEntity.created(new URI("/api/operadors/" + operadorDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, operadorDTO.getId().toString()))
            .body(operadorDTO);
    }

    /**
     * {@code PUT  /operadors/:id} : Updates an existing operador.
     *
     * @param id the id of the operadorDTO to save.
     * @param operadorDTO the operadorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated operadorDTO,
     * or with status {@code 400 (Bad Request)} if the operadorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the operadorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OperadorDTO> updateOperador(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OperadorDTO operadorDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Operador : {}, {}", id, operadorDTO);
        if (operadorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, operadorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!operadorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        operadorDTO = operadorService.update(operadorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, operadorDTO.getId().toString()))
            .body(operadorDTO);
    }

    /**
     * {@code PATCH  /operadors/:id} : Partial updates given fields of an existing operador, field will ignore if it is null
     *
     * @param id the id of the operadorDTO to save.
     * @param operadorDTO the operadorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated operadorDTO,
     * or with status {@code 400 (Bad Request)} if the operadorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the operadorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the operadorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OperadorDTO> partialUpdateOperador(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OperadorDTO operadorDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Operador partially : {}, {}", id, operadorDTO);
        if (operadorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, operadorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!operadorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OperadorDTO> result = operadorService.partialUpdate(operadorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, operadorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /operadors} : get all the operadors.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of operadors in body.
     */
    @GetMapping("")
    public ResponseEntity<List<OperadorDTO>> getAllOperadors(OperadorCriteria criteria) {
        LOG.debug("REST request to get Operadors by criteria: {}", criteria);

        List<OperadorDTO> entityList = operadorQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /operadors/count} : count all the operadors.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countOperadors(OperadorCriteria criteria) {
        LOG.debug("REST request to count Operadors by criteria: {}", criteria);
        return ResponseEntity.ok().body(operadorQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /operadors/:id} : get the "id" operador.
     *
     * @param id the id of the operadorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the operadorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OperadorDTO> getOperador(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Operador : {}", id);
        Optional<OperadorDTO> operadorDTO = operadorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(operadorDTO);
    }

    /**
     * {@code DELETE  /operadors/:id} : delete the "id" operador.
     *
     * @param id the id of the operadorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOperador(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Operador : {}", id);
        operadorService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

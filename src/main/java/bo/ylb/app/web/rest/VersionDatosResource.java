package bo.ylb.app.web.rest;

import bo.ylb.app.repository.VersionDatosRepository;
import bo.ylb.app.service.VersionDatosQueryService;
import bo.ylb.app.service.VersionDatosService;
import bo.ylb.app.service.criteria.VersionDatosCriteria;
import bo.ylb.app.service.dto.VersionDatosDTO;
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
 * REST controller for managing {@link bo.ylb.app.domain.VersionDatos}.
 */
@RestController
@RequestMapping("/api/version-datos")
public class VersionDatosResource {

    private static final Logger LOG = LoggerFactory.getLogger(VersionDatosResource.class);

    private static final String ENTITY_NAME = "versionDatos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VersionDatosService versionDatosService;

    private final VersionDatosRepository versionDatosRepository;

    private final VersionDatosQueryService versionDatosQueryService;

    public VersionDatosResource(
        VersionDatosService versionDatosService,
        VersionDatosRepository versionDatosRepository,
        VersionDatosQueryService versionDatosQueryService
    ) {
        this.versionDatosService = versionDatosService;
        this.versionDatosRepository = versionDatosRepository;
        this.versionDatosQueryService = versionDatosQueryService;
    }

    /**
     * {@code POST  /version-datos} : Create a new versionDatos.
     *
     * @param versionDatosDTO the versionDatosDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new versionDatosDTO, or with status {@code 400 (Bad Request)} if the versionDatos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<VersionDatosDTO> createVersionDatos(@Valid @RequestBody VersionDatosDTO versionDatosDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save VersionDatos : {}", versionDatosDTO);
        if (versionDatosDTO.getId() != null) {
            throw new BadRequestAlertException("A new versionDatos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        versionDatosDTO = versionDatosService.save(versionDatosDTO);
        return ResponseEntity.created(new URI("/api/version-datos/" + versionDatosDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, versionDatosDTO.getId().toString()))
            .body(versionDatosDTO);
    }

    /**
     * {@code PUT  /version-datos/:id} : Updates an existing versionDatos.
     *
     * @param id the id of the versionDatosDTO to save.
     * @param versionDatosDTO the versionDatosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated versionDatosDTO,
     * or with status {@code 400 (Bad Request)} if the versionDatosDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the versionDatosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<VersionDatosDTO> updateVersionDatos(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VersionDatosDTO versionDatosDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update VersionDatos : {}, {}", id, versionDatosDTO);
        if (versionDatosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, versionDatosDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!versionDatosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        versionDatosDTO = versionDatosService.update(versionDatosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, versionDatosDTO.getId().toString()))
            .body(versionDatosDTO);
    }

    /**
     * {@code PATCH  /version-datos/:id} : Partial updates given fields of an existing versionDatos, field will ignore if it is null
     *
     * @param id the id of the versionDatosDTO to save.
     * @param versionDatosDTO the versionDatosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated versionDatosDTO,
     * or with status {@code 400 (Bad Request)} if the versionDatosDTO is not valid,
     * or with status {@code 404 (Not Found)} if the versionDatosDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the versionDatosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VersionDatosDTO> partialUpdateVersionDatos(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VersionDatosDTO versionDatosDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update VersionDatos partially : {}, {}", id, versionDatosDTO);
        if (versionDatosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, versionDatosDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!versionDatosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VersionDatosDTO> result = versionDatosService.partialUpdate(versionDatosDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, versionDatosDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /version-datos} : get all the versionDatos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of versionDatos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<VersionDatosDTO>> getAllVersionDatos(VersionDatosCriteria criteria) {
        LOG.debug("REST request to get VersionDatos by criteria: {}", criteria);

        List<VersionDatosDTO> entityList = versionDatosQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /version-datos/count} : count all the versionDatos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countVersionDatos(VersionDatosCriteria criteria) {
        LOG.debug("REST request to count VersionDatos by criteria: {}", criteria);
        return ResponseEntity.ok().body(versionDatosQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /version-datos/:id} : get the "id" versionDatos.
     *
     * @param id the id of the versionDatosDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the versionDatosDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<VersionDatosDTO> getVersionDatos(@PathVariable("id") Long id) {
        LOG.debug("REST request to get VersionDatos : {}", id);
        Optional<VersionDatosDTO> versionDatosDTO = versionDatosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(versionDatosDTO);
    }

    /**
     * {@code DELETE  /version-datos/:id} : delete the "id" versionDatos.
     *
     * @param id the id of the versionDatosDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVersionDatos(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete VersionDatos : {}", id);
        versionDatosService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

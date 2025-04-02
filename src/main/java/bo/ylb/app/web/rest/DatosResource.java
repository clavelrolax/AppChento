package bo.ylb.app.web.rest;

import bo.ylb.app.repository.DatosRepository;
import bo.ylb.app.service.DatosQueryService;
import bo.ylb.app.service.DatosService;
import bo.ylb.app.service.criteria.DatosCriteria;
import bo.ylb.app.service.dto.DatosDTO;
import bo.ylb.app.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link bo.ylb.app.domain.Datos}.
 */
@RestController
@RequestMapping("/api/datos")
public class DatosResource {

    private static final Logger LOG = LoggerFactory.getLogger(DatosResource.class);

    private static final String ENTITY_NAME = "datos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DatosService datosService;

    private final DatosRepository datosRepository;

    private final DatosQueryService datosQueryService;

    public DatosResource(DatosService datosService, DatosRepository datosRepository, DatosQueryService datosQueryService) {
        this.datosService = datosService;
        this.datosRepository = datosRepository;
        this.datosQueryService = datosQueryService;
    }

    /**
     * {@code POST  /datos} : Create a new datos.
     *
     * @param datosDTO the datosDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new datosDTO, or with status {@code 400 (Bad Request)} if the datos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DatosDTO> createDatos(@RequestBody DatosDTO datosDTO) throws URISyntaxException {
        LOG.debug("REST request to save Datos : {}", datosDTO);
        if (datosDTO.getId() != null) {
            throw new BadRequestAlertException("A new datos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        datosDTO = datosService.save(datosDTO);
        return ResponseEntity.created(new URI("/api/datos/" + datosDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, datosDTO.getId().toString()))
            .body(datosDTO);
    }

    /**
     * {@code PUT  /datos/:id} : Updates an existing datos.
     *
     * @param id the id of the datosDTO to save.
     * @param datosDTO the datosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated datosDTO,
     * or with status {@code 400 (Bad Request)} if the datosDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the datosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DatosDTO> updateDatos(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DatosDTO datosDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Datos : {}, {}", id, datosDTO);
        if (datosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, datosDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!datosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        datosDTO = datosService.update(datosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, datosDTO.getId().toString()))
            .body(datosDTO);
    }

    /**
     * {@code PATCH  /datos/:id} : Partial updates given fields of an existing datos, field will ignore if it is null
     *
     * @param id the id of the datosDTO to save.
     * @param datosDTO the datosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated datosDTO,
     * or with status {@code 400 (Bad Request)} if the datosDTO is not valid,
     * or with status {@code 404 (Not Found)} if the datosDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the datosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DatosDTO> partialUpdateDatos(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DatosDTO datosDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Datos partially : {}, {}", id, datosDTO);
        if (datosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, datosDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!datosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DatosDTO> result = datosService.partialUpdate(datosDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, datosDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /datos} : get all the datos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of datos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DatosDTO>> getAllDatos(DatosCriteria criteria) {
        LOG.debug("REST request to get Datos by criteria: {}", criteria);

        List<DatosDTO> entityList = datosQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /datos/count} : count all the datos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countDatos(DatosCriteria criteria) {
        LOG.debug("REST request to count Datos by criteria: {}", criteria);
        return ResponseEntity.ok().body(datosQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /datos/:id} : get the "id" datos.
     *
     * @param id the id of the datosDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the datosDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DatosDTO> getDatos(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Datos : {}", id);
        Optional<DatosDTO> datosDTO = datosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(datosDTO);
    }

    /**
     * {@code DELETE  /datos/:id} : delete the "id" datos.
     *
     * @param id the id of the datosDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDatos(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Datos : {}", id);
        datosService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

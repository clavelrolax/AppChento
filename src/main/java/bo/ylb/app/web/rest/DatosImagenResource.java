package bo.ylb.app.web.rest;

import bo.ylb.app.repository.DatosImagenRepository;
import bo.ylb.app.service.DatosImagenQueryService;
import bo.ylb.app.service.DatosImagenService;
import bo.ylb.app.service.criteria.DatosImagenCriteria;
import bo.ylb.app.service.dto.DatosImagenDTO;
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
 * REST controller for managing {@link bo.ylb.app.domain.DatosImagen}.
 */
@RestController
@RequestMapping("/api/datos-imagens")
public class DatosImagenResource {

    private static final Logger LOG = LoggerFactory.getLogger(DatosImagenResource.class);

    private static final String ENTITY_NAME = "datosImagen";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DatosImagenService datosImagenService;

    private final DatosImagenRepository datosImagenRepository;

    private final DatosImagenQueryService datosImagenQueryService;

    public DatosImagenResource(
        DatosImagenService datosImagenService,
        DatosImagenRepository datosImagenRepository,
        DatosImagenQueryService datosImagenQueryService
    ) {
        this.datosImagenService = datosImagenService;
        this.datosImagenRepository = datosImagenRepository;
        this.datosImagenQueryService = datosImagenQueryService;
    }

    /**
     * {@code POST  /datos-imagens} : Create a new datosImagen.
     *
     * @param datosImagenDTO the datosImagenDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new datosImagenDTO, or with status {@code 400 (Bad Request)} if the datosImagen has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DatosImagenDTO> createDatosImagen(@RequestBody DatosImagenDTO datosImagenDTO) throws URISyntaxException {
        LOG.debug("REST request to save DatosImagen : {}", datosImagenDTO);
        if (datosImagenDTO.getId() != null) {
            throw new BadRequestAlertException("A new datosImagen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        datosImagenDTO = datosImagenService.save(datosImagenDTO);
        return ResponseEntity.created(new URI("/api/datos-imagens/" + datosImagenDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, datosImagenDTO.getId().toString()))
            .body(datosImagenDTO);
    }

    /**
     * {@code PUT  /datos-imagens/:id} : Updates an existing datosImagen.
     *
     * @param id the id of the datosImagenDTO to save.
     * @param datosImagenDTO the datosImagenDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated datosImagenDTO,
     * or with status {@code 400 (Bad Request)} if the datosImagenDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the datosImagenDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DatosImagenDTO> updateDatosImagen(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DatosImagenDTO datosImagenDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update DatosImagen : {}, {}", id, datosImagenDTO);
        if (datosImagenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, datosImagenDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!datosImagenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        datosImagenDTO = datosImagenService.update(datosImagenDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, datosImagenDTO.getId().toString()))
            .body(datosImagenDTO);
    }

    /**
     * {@code PATCH  /datos-imagens/:id} : Partial updates given fields of an existing datosImagen, field will ignore if it is null
     *
     * @param id the id of the datosImagenDTO to save.
     * @param datosImagenDTO the datosImagenDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated datosImagenDTO,
     * or with status {@code 400 (Bad Request)} if the datosImagenDTO is not valid,
     * or with status {@code 404 (Not Found)} if the datosImagenDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the datosImagenDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DatosImagenDTO> partialUpdateDatosImagen(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DatosImagenDTO datosImagenDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update DatosImagen partially : {}, {}", id, datosImagenDTO);
        if (datosImagenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, datosImagenDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!datosImagenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DatosImagenDTO> result = datosImagenService.partialUpdate(datosImagenDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, datosImagenDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /datos-imagens} : get all the datosImagens.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of datosImagens in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DatosImagenDTO>> getAllDatosImagens(DatosImagenCriteria criteria) {
        LOG.debug("REST request to get DatosImagens by criteria: {}", criteria);

        List<DatosImagenDTO> entityList = datosImagenQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /datos-imagens/count} : count all the datosImagens.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countDatosImagens(DatosImagenCriteria criteria) {
        LOG.debug("REST request to count DatosImagens by criteria: {}", criteria);
        return ResponseEntity.ok().body(datosImagenQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /datos-imagens/:id} : get the "id" datosImagen.
     *
     * @param id the id of the datosImagenDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the datosImagenDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DatosImagenDTO> getDatosImagen(@PathVariable("id") Long id) {
        LOG.debug("REST request to get DatosImagen : {}", id);
        Optional<DatosImagenDTO> datosImagenDTO = datosImagenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(datosImagenDTO);
    }

    /**
     * {@code DELETE  /datos-imagens/:id} : delete the "id" datosImagen.
     *
     * @param id the id of the datosImagenDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDatosImagen(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete DatosImagen : {}", id);
        datosImagenService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

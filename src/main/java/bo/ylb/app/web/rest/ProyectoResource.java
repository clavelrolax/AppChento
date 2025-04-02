package bo.ylb.app.web.rest;

import bo.ylb.app.repository.ProyectoRepository;
import bo.ylb.app.service.ProyectoQueryService;
import bo.ylb.app.service.ProyectoService;
import bo.ylb.app.service.criteria.ProyectoCriteria;
import bo.ylb.app.service.dto.ProyectoDTO;
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
 * REST controller for managing {@link bo.ylb.app.domain.Proyecto}.
 */
@RestController
@RequestMapping("/api/proyectos")
public class ProyectoResource {

    private static final Logger LOG = LoggerFactory.getLogger(ProyectoResource.class);

    private static final String ENTITY_NAME = "proyecto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProyectoService proyectoService;

    private final ProyectoRepository proyectoRepository;

    private final ProyectoQueryService proyectoQueryService;

    public ProyectoResource(
        ProyectoService proyectoService,
        ProyectoRepository proyectoRepository,
        ProyectoQueryService proyectoQueryService
    ) {
        this.proyectoService = proyectoService;
        this.proyectoRepository = proyectoRepository;
        this.proyectoQueryService = proyectoQueryService;
    }

    /**
     * {@code POST  /proyectos} : Create a new proyecto.
     *
     * @param proyectoDTO the proyectoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new proyectoDTO, or with status {@code 400 (Bad Request)} if the proyecto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ProyectoDTO> createProyecto(@Valid @RequestBody ProyectoDTO proyectoDTO) throws URISyntaxException {
        LOG.debug("REST request to save Proyecto : {}", proyectoDTO);
        if (proyectoDTO.getId() != null) {
            throw new BadRequestAlertException("A new proyecto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        proyectoDTO = proyectoService.save(proyectoDTO);
        return ResponseEntity.created(new URI("/api/proyectos/" + proyectoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, proyectoDTO.getId().toString()))
            .body(proyectoDTO);
    }

    /**
     * {@code PUT  /proyectos/:id} : Updates an existing proyecto.
     *
     * @param id the id of the proyectoDTO to save.
     * @param proyectoDTO the proyectoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated proyectoDTO,
     * or with status {@code 400 (Bad Request)} if the proyectoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the proyectoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProyectoDTO> updateProyecto(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProyectoDTO proyectoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Proyecto : {}, {}", id, proyectoDTO);
        if (proyectoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, proyectoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!proyectoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        proyectoDTO = proyectoService.update(proyectoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, proyectoDTO.getId().toString()))
            .body(proyectoDTO);
    }

    /**
     * {@code PATCH  /proyectos/:id} : Partial updates given fields of an existing proyecto, field will ignore if it is null
     *
     * @param id the id of the proyectoDTO to save.
     * @param proyectoDTO the proyectoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated proyectoDTO,
     * or with status {@code 400 (Bad Request)} if the proyectoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the proyectoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the proyectoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProyectoDTO> partialUpdateProyecto(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProyectoDTO proyectoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Proyecto partially : {}, {}", id, proyectoDTO);
        if (proyectoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, proyectoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!proyectoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProyectoDTO> result = proyectoService.partialUpdate(proyectoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, proyectoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /proyectos} : get all the proyectos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of proyectos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ProyectoDTO>> getAllProyectos(ProyectoCriteria criteria) {
        LOG.debug("REST request to get Proyectos by criteria: {}", criteria);

        List<ProyectoDTO> entityList = proyectoQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /proyectos/count} : count all the proyectos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countProyectos(ProyectoCriteria criteria) {
        LOG.debug("REST request to count Proyectos by criteria: {}", criteria);
        return ResponseEntity.ok().body(proyectoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /proyectos/:id} : get the "id" proyecto.
     *
     * @param id the id of the proyectoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the proyectoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProyectoDTO> getProyecto(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Proyecto : {}", id);
        Optional<ProyectoDTO> proyectoDTO = proyectoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(proyectoDTO);
    }

    /**
     * {@code DELETE  /proyectos/:id} : delete the "id" proyecto.
     *
     * @param id the id of the proyectoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProyecto(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Proyecto : {}", id);
        proyectoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

package bo.ylb.app.service;

import bo.ylb.app.domain.Proyecto;
import bo.ylb.app.repository.ProyectoRepository;
import bo.ylb.app.service.dto.ProyectoDTO;
import bo.ylb.app.service.mapper.ProyectoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link bo.ylb.app.domain.Proyecto}.
 */
@Service
@Transactional
public class ProyectoService {

    private static final Logger LOG = LoggerFactory.getLogger(ProyectoService.class);

    private final ProyectoRepository proyectoRepository;

    private final ProyectoMapper proyectoMapper;

    public ProyectoService(ProyectoRepository proyectoRepository, ProyectoMapper proyectoMapper) {
        this.proyectoRepository = proyectoRepository;
        this.proyectoMapper = proyectoMapper;
    }

    /**
     * Save a proyecto.
     *
     * @param proyectoDTO the entity to save.
     * @return the persisted entity.
     */
    public ProyectoDTO save(ProyectoDTO proyectoDTO) {
        LOG.debug("Request to save Proyecto : {}", proyectoDTO);
        Proyecto proyecto = proyectoMapper.toEntity(proyectoDTO);
        proyecto = proyectoRepository.save(proyecto);
        return proyectoMapper.toDto(proyecto);
    }

    /**
     * Update a proyecto.
     *
     * @param proyectoDTO the entity to save.
     * @return the persisted entity.
     */
    public ProyectoDTO update(ProyectoDTO proyectoDTO) {
        LOG.debug("Request to update Proyecto : {}", proyectoDTO);
        Proyecto proyecto = proyectoMapper.toEntity(proyectoDTO);
        proyecto = proyectoRepository.save(proyecto);
        return proyectoMapper.toDto(proyecto);
    }

    /**
     * Partially update a proyecto.
     *
     * @param proyectoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProyectoDTO> partialUpdate(ProyectoDTO proyectoDTO) {
        LOG.debug("Request to partially update Proyecto : {}", proyectoDTO);

        return proyectoRepository
            .findById(proyectoDTO.getId())
            .map(existingProyecto -> {
                proyectoMapper.partialUpdate(existingProyecto, proyectoDTO);

                return existingProyecto;
            })
            .map(proyectoRepository::save)
            .map(proyectoMapper::toDto);
    }

    /**
     * Get all the proyectos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ProyectoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return proyectoRepository.findAllWithEagerRelationships(pageable).map(proyectoMapper::toDto);
    }

    /**
     * Get one proyecto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProyectoDTO> findOne(Long id) {
        LOG.debug("Request to get Proyecto : {}", id);
        return proyectoRepository.findOneWithEagerRelationships(id).map(proyectoMapper::toDto);
    }

    /**
     * Delete the proyecto by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Proyecto : {}", id);
        proyectoRepository.deleteById(id);
    }
}

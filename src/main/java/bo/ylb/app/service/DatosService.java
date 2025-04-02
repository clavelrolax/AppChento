package bo.ylb.app.service;

import bo.ylb.app.domain.Datos;
import bo.ylb.app.repository.DatosRepository;
import bo.ylb.app.service.dto.DatosDTO;
import bo.ylb.app.service.mapper.DatosMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link bo.ylb.app.domain.Datos}.
 */
@Service
@Transactional
public class DatosService {

    private static final Logger LOG = LoggerFactory.getLogger(DatosService.class);

    private final DatosRepository datosRepository;

    private final DatosMapper datosMapper;

    public DatosService(DatosRepository datosRepository, DatosMapper datosMapper) {
        this.datosRepository = datosRepository;
        this.datosMapper = datosMapper;
    }

    /**
     * Save a datos.
     *
     * @param datosDTO the entity to save.
     * @return the persisted entity.
     */
    public DatosDTO save(DatosDTO datosDTO) {
        LOG.debug("Request to save Datos : {}", datosDTO);
        Datos datos = datosMapper.toEntity(datosDTO);
        datos = datosRepository.save(datos);
        return datosMapper.toDto(datos);
    }

    /**
     * Update a datos.
     *
     * @param datosDTO the entity to save.
     * @return the persisted entity.
     */
    public DatosDTO update(DatosDTO datosDTO) {
        LOG.debug("Request to update Datos : {}", datosDTO);
        Datos datos = datosMapper.toEntity(datosDTO);
        datos = datosRepository.save(datos);
        return datosMapper.toDto(datos);
    }

    /**
     * Partially update a datos.
     *
     * @param datosDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DatosDTO> partialUpdate(DatosDTO datosDTO) {
        LOG.debug("Request to partially update Datos : {}", datosDTO);

        return datosRepository
            .findById(datosDTO.getId())
            .map(existingDatos -> {
                datosMapper.partialUpdate(existingDatos, datosDTO);

                return existingDatos;
            })
            .map(datosRepository::save)
            .map(datosMapper::toDto);
    }

    /**
     * Get all the datos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<DatosDTO> findAllWithEagerRelationships(Pageable pageable) {
        return datosRepository.findAllWithEagerRelationships(pageable).map(datosMapper::toDto);
    }

    /**
     * Get one datos by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DatosDTO> findOne(Long id) {
        LOG.debug("Request to get Datos : {}", id);
        return datosRepository.findOneWithEagerRelationships(id).map(datosMapper::toDto);
    }

    /**
     * Delete the datos by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Datos : {}", id);
        datosRepository.deleteById(id);
    }
}

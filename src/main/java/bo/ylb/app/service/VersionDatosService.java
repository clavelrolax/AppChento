package bo.ylb.app.service;

import bo.ylb.app.domain.VersionDatos;
import bo.ylb.app.repository.VersionDatosRepository;
import bo.ylb.app.service.dto.VersionDatosDTO;
import bo.ylb.app.service.mapper.VersionDatosMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link bo.ylb.app.domain.VersionDatos}.
 */
@Service
@Transactional
public class VersionDatosService {

    private static final Logger LOG = LoggerFactory.getLogger(VersionDatosService.class);

    private final VersionDatosRepository versionDatosRepository;

    private final VersionDatosMapper versionDatosMapper;

    public VersionDatosService(VersionDatosRepository versionDatosRepository, VersionDatosMapper versionDatosMapper) {
        this.versionDatosRepository = versionDatosRepository;
        this.versionDatosMapper = versionDatosMapper;
    }

    /**
     * Save a versionDatos.
     *
     * @param versionDatosDTO the entity to save.
     * @return the persisted entity.
     */
    public VersionDatosDTO save(VersionDatosDTO versionDatosDTO) {
        LOG.debug("Request to save VersionDatos : {}", versionDatosDTO);
        VersionDatos versionDatos = versionDatosMapper.toEntity(versionDatosDTO);
        versionDatos = versionDatosRepository.save(versionDatos);
        return versionDatosMapper.toDto(versionDatos);
    }

    /**
     * Update a versionDatos.
     *
     * @param versionDatosDTO the entity to save.
     * @return the persisted entity.
     */
    public VersionDatosDTO update(VersionDatosDTO versionDatosDTO) {
        LOG.debug("Request to update VersionDatos : {}", versionDatosDTO);
        VersionDatos versionDatos = versionDatosMapper.toEntity(versionDatosDTO);
        versionDatos = versionDatosRepository.save(versionDatos);
        return versionDatosMapper.toDto(versionDatos);
    }

    /**
     * Partially update a versionDatos.
     *
     * @param versionDatosDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VersionDatosDTO> partialUpdate(VersionDatosDTO versionDatosDTO) {
        LOG.debug("Request to partially update VersionDatos : {}", versionDatosDTO);

        return versionDatosRepository
            .findById(versionDatosDTO.getId())
            .map(existingVersionDatos -> {
                versionDatosMapper.partialUpdate(existingVersionDatos, versionDatosDTO);

                return existingVersionDatos;
            })
            .map(versionDatosRepository::save)
            .map(versionDatosMapper::toDto);
    }

    /**
     * Get all the versionDatos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<VersionDatosDTO> findAllWithEagerRelationships(Pageable pageable) {
        return versionDatosRepository.findAllWithEagerRelationships(pageable).map(versionDatosMapper::toDto);
    }

    /**
     *  Get all the versionDatos where Datos is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<VersionDatosDTO> findAllWhereDatosIsNull() {
        LOG.debug("Request to get all versionDatos where Datos is null");
        return StreamSupport.stream(versionDatosRepository.findAll().spliterator(), false)
            .filter(versionDatos -> versionDatos.getDatos() == null)
            .map(versionDatosMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the versionDatos where DatosImagen is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<VersionDatosDTO> findAllWhereDatosImagenIsNull() {
        LOG.debug("Request to get all versionDatos where DatosImagen is null");
        return StreamSupport.stream(versionDatosRepository.findAll().spliterator(), false)
            .filter(versionDatos -> versionDatos.getDatosImagen() == null)
            .map(versionDatosMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one versionDatos by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VersionDatosDTO> findOne(Long id) {
        LOG.debug("Request to get VersionDatos : {}", id);
        return versionDatosRepository.findOneWithEagerRelationships(id).map(versionDatosMapper::toDto);
    }

    /**
     * Delete the versionDatos by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete VersionDatos : {}", id);
        versionDatosRepository.deleteById(id);
    }
}

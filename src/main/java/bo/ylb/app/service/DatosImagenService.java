package bo.ylb.app.service;

import bo.ylb.app.domain.DatosImagen;
import bo.ylb.app.repository.DatosImagenRepository;
import bo.ylb.app.service.dto.DatosImagenDTO;
import bo.ylb.app.service.mapper.DatosImagenMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link bo.ylb.app.domain.DatosImagen}.
 */
@Service
@Transactional
public class DatosImagenService {

    private static final Logger LOG = LoggerFactory.getLogger(DatosImagenService.class);

    private final DatosImagenRepository datosImagenRepository;

    private final DatosImagenMapper datosImagenMapper;

    public DatosImagenService(DatosImagenRepository datosImagenRepository, DatosImagenMapper datosImagenMapper) {
        this.datosImagenRepository = datosImagenRepository;
        this.datosImagenMapper = datosImagenMapper;
    }

    /**
     * Save a datosImagen.
     *
     * @param datosImagenDTO the entity to save.
     * @return the persisted entity.
     */
    public DatosImagenDTO save(DatosImagenDTO datosImagenDTO) {
        LOG.debug("Request to save DatosImagen : {}", datosImagenDTO);
        DatosImagen datosImagen = datosImagenMapper.toEntity(datosImagenDTO);
        datosImagen = datosImagenRepository.save(datosImagen);
        return datosImagenMapper.toDto(datosImagen);
    }

    /**
     * Update a datosImagen.
     *
     * @param datosImagenDTO the entity to save.
     * @return the persisted entity.
     */
    public DatosImagenDTO update(DatosImagenDTO datosImagenDTO) {
        LOG.debug("Request to update DatosImagen : {}", datosImagenDTO);
        DatosImagen datosImagen = datosImagenMapper.toEntity(datosImagenDTO);
        datosImagen = datosImagenRepository.save(datosImagen);
        return datosImagenMapper.toDto(datosImagen);
    }

    /**
     * Partially update a datosImagen.
     *
     * @param datosImagenDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DatosImagenDTO> partialUpdate(DatosImagenDTO datosImagenDTO) {
        LOG.debug("Request to partially update DatosImagen : {}", datosImagenDTO);

        return datosImagenRepository
            .findById(datosImagenDTO.getId())
            .map(existingDatosImagen -> {
                datosImagenMapper.partialUpdate(existingDatosImagen, datosImagenDTO);

                return existingDatosImagen;
            })
            .map(datosImagenRepository::save)
            .map(datosImagenMapper::toDto);
    }

    /**
     * Get all the datosImagens with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<DatosImagenDTO> findAllWithEagerRelationships(Pageable pageable) {
        return datosImagenRepository.findAllWithEagerRelationships(pageable).map(datosImagenMapper::toDto);
    }

    /**
     * Get one datosImagen by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DatosImagenDTO> findOne(Long id) {
        LOG.debug("Request to get DatosImagen : {}", id);
        return datosImagenRepository.findOneWithEagerRelationships(id).map(datosImagenMapper::toDto);
    }

    /**
     * Delete the datosImagen by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete DatosImagen : {}", id);
        datosImagenRepository.deleteById(id);
    }
}

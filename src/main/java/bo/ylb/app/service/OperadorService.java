package bo.ylb.app.service;

import bo.ylb.app.domain.Operador;
import bo.ylb.app.repository.OperadorRepository;
import bo.ylb.app.service.dto.OperadorDTO;
import bo.ylb.app.service.mapper.OperadorMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link bo.ylb.app.domain.Operador}.
 */
@Service
@Transactional
public class OperadorService {

    private static final Logger LOG = LoggerFactory.getLogger(OperadorService.class);

    private final OperadorRepository operadorRepository;

    private final OperadorMapper operadorMapper;

    public OperadorService(OperadorRepository operadorRepository, OperadorMapper operadorMapper) {
        this.operadorRepository = operadorRepository;
        this.operadorMapper = operadorMapper;
    }

    /**
     * Save a operador.
     *
     * @param operadorDTO the entity to save.
     * @return the persisted entity.
     */
    public OperadorDTO save(OperadorDTO operadorDTO) {
        LOG.debug("Request to save Operador : {}", operadorDTO);
        Operador operador = operadorMapper.toEntity(operadorDTO);
        operador = operadorRepository.save(operador);
        return operadorMapper.toDto(operador);
    }

    /**
     * Update a operador.
     *
     * @param operadorDTO the entity to save.
     * @return the persisted entity.
     */
    public OperadorDTO update(OperadorDTO operadorDTO) {
        LOG.debug("Request to update Operador : {}", operadorDTO);
        Operador operador = operadorMapper.toEntity(operadorDTO);
        operador = operadorRepository.save(operador);
        return operadorMapper.toDto(operador);
    }

    /**
     * Partially update a operador.
     *
     * @param operadorDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OperadorDTO> partialUpdate(OperadorDTO operadorDTO) {
        LOG.debug("Request to partially update Operador : {}", operadorDTO);

        return operadorRepository
            .findById(operadorDTO.getId())
            .map(existingOperador -> {
                operadorMapper.partialUpdate(existingOperador, operadorDTO);

                return existingOperador;
            })
            .map(operadorRepository::save)
            .map(operadorMapper::toDto);
    }

    /**
     * Get one operador by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OperadorDTO> findOne(Long id) {
        LOG.debug("Request to get Operador : {}", id);
        return operadorRepository.findById(id).map(operadorMapper::toDto);
    }

    /**
     * Delete the operador by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Operador : {}", id);
        operadorRepository.deleteById(id);
    }
}

package bo.ylb.app.service;

import bo.ylb.app.domain.*; // for static metamodels
import bo.ylb.app.domain.Operador;
import bo.ylb.app.repository.OperadorRepository;
import bo.ylb.app.service.criteria.OperadorCriteria;
import bo.ylb.app.service.dto.OperadorDTO;
import bo.ylb.app.service.mapper.OperadorMapper;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Operador} entities in the database.
 * The main input is a {@link OperadorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OperadorDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OperadorQueryService extends QueryService<Operador> {

    private static final Logger LOG = LoggerFactory.getLogger(OperadorQueryService.class);

    private final OperadorRepository operadorRepository;

    private final OperadorMapper operadorMapper;

    public OperadorQueryService(OperadorRepository operadorRepository, OperadorMapper operadorMapper) {
        this.operadorRepository = operadorRepository;
        this.operadorMapper = operadorMapper;
    }

    /**
     * Return a {@link List} of {@link OperadorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OperadorDTO> findByCriteria(OperadorCriteria criteria) {
        LOG.debug("find by criteria : {}", criteria);
        final Specification<Operador> specification = createSpecification(criteria);
        return operadorMapper.toDto(operadorRepository.findAll(specification));
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OperadorCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Operador> specification = createSpecification(criteria);
        return operadorRepository.count(specification);
    }

    /**
     * Function to convert {@link OperadorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Operador> createSpecification(OperadorCriteria criteria) {
        Specification<Operador> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Operador_.id));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), Operador_.nombre));
            }
            if (criteria.getNacionalidad() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNacionalidad(), Operador_.nacionalidad));
            }
            if (criteria.getFechaCreacion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFechaCreacion(), Operador_.fechaCreacion));
            }
            if (criteria.getProyectoId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getProyectoId(), root -> root.join(Operador_.proyectos, JoinType.LEFT).get(Proyecto_.id))
                );
            }
        }
        return specification;
    }
}

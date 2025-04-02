package bo.ylb.app.service;

import bo.ylb.app.domain.*; // for static metamodels
import bo.ylb.app.domain.Proyecto;
import bo.ylb.app.repository.ProyectoRepository;
import bo.ylb.app.service.criteria.ProyectoCriteria;
import bo.ylb.app.service.dto.ProyectoDTO;
import bo.ylb.app.service.mapper.ProyectoMapper;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Proyecto} entities in the database.
 * The main input is a {@link ProyectoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProyectoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProyectoQueryService extends QueryService<Proyecto> {

    private static final Logger LOG = LoggerFactory.getLogger(ProyectoQueryService.class);

    private final ProyectoRepository proyectoRepository;

    private final ProyectoMapper proyectoMapper;

    public ProyectoQueryService(ProyectoRepository proyectoRepository, ProyectoMapper proyectoMapper) {
        this.proyectoRepository = proyectoRepository;
        this.proyectoMapper = proyectoMapper;
    }

    /**
     * Return a {@link List} of {@link ProyectoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProyectoDTO> findByCriteria(ProyectoCriteria criteria) {
        LOG.debug("find by criteria : {}", criteria);
        final Specification<Proyecto> specification = createSpecification(criteria);
        return proyectoMapper.toDto(proyectoRepository.findAll(specification));
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProyectoCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Proyecto> specification = createSpecification(criteria);
        return proyectoRepository.count(specification);
    }

    /**
     * Function to convert {@link ProyectoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Proyecto> createSpecification(ProyectoCriteria criteria) {
        Specification<Proyecto> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Proyecto_.id));
            }
            if (criteria.getNombreProyecto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombreProyecto(), Proyecto_.nombreProyecto));
            }
            if (criteria.getObjetivo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getObjetivo(), Proyecto_.objetivo));
            }
            if (criteria.getTiempoProyecto() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTiempoProyecto(), Proyecto_.tiempoProyecto));
            }
            if (criteria.getFechaInicio() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFechaInicio(), Proyecto_.fechaInicio));
            }
            if (criteria.getFechaFin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFechaFin(), Proyecto_.fechaFin));
            }
            if (criteria.getOperadorId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getOperadorId(), root -> root.join(Proyecto_.operador, JoinType.LEFT).get(Operador_.id))
                );
            }
            if (criteria.getVersionDatosId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getVersionDatosId(), root ->
                        root.join(Proyecto_.versionDatos, JoinType.LEFT).get(VersionDatos_.id)
                    )
                );
            }
        }
        return specification;
    }
}

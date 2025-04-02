package bo.ylb.app.service;

import bo.ylb.app.domain.*; // for static metamodels
import bo.ylb.app.domain.VersionDatos;
import bo.ylb.app.repository.VersionDatosRepository;
import bo.ylb.app.service.criteria.VersionDatosCriteria;
import bo.ylb.app.service.dto.VersionDatosDTO;
import bo.ylb.app.service.mapper.VersionDatosMapper;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link VersionDatos} entities in the database.
 * The main input is a {@link VersionDatosCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VersionDatosDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VersionDatosQueryService extends QueryService<VersionDatos> {

    private static final Logger LOG = LoggerFactory.getLogger(VersionDatosQueryService.class);

    private final VersionDatosRepository versionDatosRepository;

    private final VersionDatosMapper versionDatosMapper;

    public VersionDatosQueryService(VersionDatosRepository versionDatosRepository, VersionDatosMapper versionDatosMapper) {
        this.versionDatosRepository = versionDatosRepository;
        this.versionDatosMapper = versionDatosMapper;
    }

    /**
     * Return a {@link List} of {@link VersionDatosDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VersionDatosDTO> findByCriteria(VersionDatosCriteria criteria) {
        LOG.debug("find by criteria : {}", criteria);
        final Specification<VersionDatos> specification = createSpecification(criteria);
        return versionDatosMapper.toDto(versionDatosRepository.findAll(specification));
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VersionDatosCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<VersionDatos> specification = createSpecification(criteria);
        return versionDatosRepository.count(specification);
    }

    /**
     * Function to convert {@link VersionDatosCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<VersionDatos> createSpecification(VersionDatosCriteria criteria) {
        Specification<VersionDatos> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), VersionDatos_.id));
            }
            if (criteria.getNombreVersion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombreVersion(), VersionDatos_.nombreVersion));
            }
            if (criteria.getFechaVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFechaVersion(), VersionDatos_.fechaVersion));
            }
            if (criteria.getCiteVersion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCiteVersion(), VersionDatos_.citeVersion));
            }
            if (criteria.getProyectoId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getProyectoId(), root -> root.join(VersionDatos_.proyecto, JoinType.LEFT).get(Proyecto_.id))
                );
            }
            if (criteria.getDatosId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getDatosId(), root -> root.join(VersionDatos_.datos, JoinType.LEFT).get(Datos_.id))
                );
            }
            if (criteria.getDatosImagenId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getDatosImagenId(), root ->
                        root.join(VersionDatos_.datosImagen, JoinType.LEFT).get(DatosImagen_.id)
                    )
                );
            }
        }
        return specification;
    }
}

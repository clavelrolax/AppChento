package bo.ylb.app.service;

import bo.ylb.app.domain.*; // for static metamodels
import bo.ylb.app.domain.DatosImagen;
import bo.ylb.app.repository.DatosImagenRepository;
import bo.ylb.app.service.criteria.DatosImagenCriteria;
import bo.ylb.app.service.dto.DatosImagenDTO;
import bo.ylb.app.service.mapper.DatosImagenMapper;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link DatosImagen} entities in the database.
 * The main input is a {@link DatosImagenCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DatosImagenDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DatosImagenQueryService extends QueryService<DatosImagen> {

    private static final Logger LOG = LoggerFactory.getLogger(DatosImagenQueryService.class);

    private final DatosImagenRepository datosImagenRepository;

    private final DatosImagenMapper datosImagenMapper;

    public DatosImagenQueryService(DatosImagenRepository datosImagenRepository, DatosImagenMapper datosImagenMapper) {
        this.datosImagenRepository = datosImagenRepository;
        this.datosImagenMapper = datosImagenMapper;
    }

    /**
     * Return a {@link List} of {@link DatosImagenDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DatosImagenDTO> findByCriteria(DatosImagenCriteria criteria) {
        LOG.debug("find by criteria : {}", criteria);
        final Specification<DatosImagen> specification = createSpecification(criteria);
        return datosImagenMapper.toDto(datosImagenRepository.findAll(specification));
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DatosImagenCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<DatosImagen> specification = createSpecification(criteria);
        return datosImagenRepository.count(specification);
    }

    /**
     * Function to convert {@link DatosImagenCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DatosImagen> createSpecification(DatosImagenCriteria criteria) {
        Specification<DatosImagen> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DatosImagen_.id));
            }
            if (criteria.getVersionDatosId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getVersionDatosId(), root ->
                        root.join(DatosImagen_.versionDatos, JoinType.LEFT).get(VersionDatos_.id)
                    )
                );
            }
        }
        return specification;
    }
}

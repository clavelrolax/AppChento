package bo.ylb.app.service;

import bo.ylb.app.domain.*; // for static metamodels
import bo.ylb.app.domain.Datos;
import bo.ylb.app.repository.DatosRepository;
import bo.ylb.app.service.criteria.DatosCriteria;
import bo.ylb.app.service.dto.DatosDTO;
import bo.ylb.app.service.mapper.DatosMapper;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Datos} entities in the database.
 * The main input is a {@link DatosCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DatosDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DatosQueryService extends QueryService<Datos> {

    private static final Logger LOG = LoggerFactory.getLogger(DatosQueryService.class);

    private final DatosRepository datosRepository;

    private final DatosMapper datosMapper;

    public DatosQueryService(DatosRepository datosRepository, DatosMapper datosMapper) {
        this.datosRepository = datosRepository;
        this.datosMapper = datosMapper;
    }

    /**
     * Return a {@link List} of {@link DatosDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DatosDTO> findByCriteria(DatosCriteria criteria) {
        LOG.debug("find by criteria : {}", criteria);
        final Specification<Datos> specification = createSpecification(criteria);
        return datosMapper.toDto(datosRepository.findAll(specification));
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DatosCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Datos> specification = createSpecification(criteria);
        return datosRepository.count(specification);
    }

    /**
     * Function to convert {@link DatosCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Datos> createSpecification(DatosCriteria criteria) {
        Specification<Datos> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Datos_.id));
            }
            if (criteria.getInversionTotal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInversionTotal(), Datos_.inversionTotal));
            }
            if (criteria.getIngresosxVentas() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIngresosxVentas(), Datos_.ingresosxVentas));
            }
            if (criteria.getGananciasYLB() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGananciasYLB(), Datos_.gananciasYLB));
            }
            if (criteria.getGoubernamentTak() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGoubernamentTak(), Datos_.goubernamentTak));
            }
            if (criteria.getRegalias() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRegalias(), Datos_.regalias));
            }
            if (criteria.getIue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIue(), Datos_.iue));
            }
            if (criteria.getIva() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIva(), Datos_.iva));
            }
            if (criteria.getIt() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIt(), Datos_.it));
            }
            if (criteria.gett1precioVentaprom() != null) {
                specification = specification.and(buildStringSpecification(criteria.gett1precioVentaprom(), Datos_.t1precioVentaprom));
            }
            if (criteria.gett1costoVariable() != null) {
                specification = specification.and(buildStringSpecification(criteria.gett1costoVariable(), Datos_.t1costoVariable));
            }
            if (criteria.gett1costoVartarifa() != null) {
                specification = specification.and(buildStringSpecification(criteria.gett1costoVartarifa(), Datos_.t1costoVartarifa));
            }
            if (criteria.gett1margenUnitario() != null) {
                specification = specification.and(buildStringSpecification(criteria.gett1margenUnitario(), Datos_.t1margenUnitario));
            }
            if (criteria.gett1costoFijo() != null) {
                specification = specification.and(buildStringSpecification(criteria.gett1costoFijo(), Datos_.t1costoFijo));
            }
            if (criteria.gett1costoTotalunitprom() != null) {
                specification = specification.and(
                    buildStringSpecification(criteria.gett1costoTotalunitprom(), Datos_.t1costoTotalunitprom)
                );
            }
            if (criteria.gett1puntoEquilibrio() != null) {
                specification = specification.and(buildStringSpecification(criteria.gett1puntoEquilibrio(), Datos_.t1puntoEquilibrio));
            }
            if (criteria.gett2tasainteres() != null) {
                specification = specification.and(buildStringSpecification(criteria.gett2tasainteres(), Datos_.t2tasainteres));
            }
            if (criteria.gett2tasadescuento() != null) {
                specification = specification.and(buildStringSpecification(criteria.gett2tasadescuento(), Datos_.t2tasadescuento));
            }
            if (criteria.gett2vandelProyecto() != null) {
                specification = specification.and(buildStringSpecification(criteria.gett2vandelProyecto(), Datos_.t2vandelProyecto));
            }
            if (criteria.gett2vanYlb() != null) {
                specification = specification.and(buildStringSpecification(criteria.gett2vanYlb(), Datos_.t2vanYlb));
            }
            if (criteria.gett2vp() != null) {
                specification = specification.and(buildStringSpecification(criteria.gett2vp(), Datos_.t2vp));
            }
            if (criteria.gett2tirProyecto() != null) {
                specification = specification.and(buildStringSpecification(criteria.gett2tirProyecto(), Datos_.t2tirProyecto));
            }
            if (criteria.gett2tirYlb() != null) {
                specification = specification.and(buildStringSpecification(criteria.gett2tirYlb(), Datos_.t2tirYlb));
            }
            if (criteria.getVersionDatosId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getVersionDatosId(), root ->
                        root.join(Datos_.versionDatos, JoinType.LEFT).get(VersionDatos_.id)
                    )
                );
            }
        }
        return specification;
    }
}

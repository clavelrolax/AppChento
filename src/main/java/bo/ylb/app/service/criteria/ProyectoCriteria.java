package bo.ylb.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link bo.ylb.app.domain.Proyecto} entity. This class is used
 * in {@link bo.ylb.app.web.rest.ProyectoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /proyectos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProyectoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nombreProyecto;

    private StringFilter objetivo;

    private IntegerFilter tiempoProyecto;

    private LocalDateFilter fechaInicio;

    private LocalDateFilter fechaFin;

    private LongFilter operadorId;

    private LongFilter versionDatosId;

    private Boolean distinct;

    public ProyectoCriteria() {}

    public ProyectoCriteria(ProyectoCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.nombreProyecto = other.optionalNombreProyecto().map(StringFilter::copy).orElse(null);
        this.objetivo = other.optionalObjetivo().map(StringFilter::copy).orElse(null);
        this.tiempoProyecto = other.optionalTiempoProyecto().map(IntegerFilter::copy).orElse(null);
        this.fechaInicio = other.optionalFechaInicio().map(LocalDateFilter::copy).orElse(null);
        this.fechaFin = other.optionalFechaFin().map(LocalDateFilter::copy).orElse(null);
        this.operadorId = other.optionalOperadorId().map(LongFilter::copy).orElse(null);
        this.versionDatosId = other.optionalVersionDatosId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public ProyectoCriteria copy() {
        return new ProyectoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNombreProyecto() {
        return nombreProyecto;
    }

    public Optional<StringFilter> optionalNombreProyecto() {
        return Optional.ofNullable(nombreProyecto);
    }

    public StringFilter nombreProyecto() {
        if (nombreProyecto == null) {
            setNombreProyecto(new StringFilter());
        }
        return nombreProyecto;
    }

    public void setNombreProyecto(StringFilter nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public StringFilter getObjetivo() {
        return objetivo;
    }

    public Optional<StringFilter> optionalObjetivo() {
        return Optional.ofNullable(objetivo);
    }

    public StringFilter objetivo() {
        if (objetivo == null) {
            setObjetivo(new StringFilter());
        }
        return objetivo;
    }

    public void setObjetivo(StringFilter objetivo) {
        this.objetivo = objetivo;
    }

    public IntegerFilter getTiempoProyecto() {
        return tiempoProyecto;
    }

    public Optional<IntegerFilter> optionalTiempoProyecto() {
        return Optional.ofNullable(tiempoProyecto);
    }

    public IntegerFilter tiempoProyecto() {
        if (tiempoProyecto == null) {
            setTiempoProyecto(new IntegerFilter());
        }
        return tiempoProyecto;
    }

    public void setTiempoProyecto(IntegerFilter tiempoProyecto) {
        this.tiempoProyecto = tiempoProyecto;
    }

    public LocalDateFilter getFechaInicio() {
        return fechaInicio;
    }

    public Optional<LocalDateFilter> optionalFechaInicio() {
        return Optional.ofNullable(fechaInicio);
    }

    public LocalDateFilter fechaInicio() {
        if (fechaInicio == null) {
            setFechaInicio(new LocalDateFilter());
        }
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateFilter fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateFilter getFechaFin() {
        return fechaFin;
    }

    public Optional<LocalDateFilter> optionalFechaFin() {
        return Optional.ofNullable(fechaFin);
    }

    public LocalDateFilter fechaFin() {
        if (fechaFin == null) {
            setFechaFin(new LocalDateFilter());
        }
        return fechaFin;
    }

    public void setFechaFin(LocalDateFilter fechaFin) {
        this.fechaFin = fechaFin;
    }

    public LongFilter getOperadorId() {
        return operadorId;
    }

    public Optional<LongFilter> optionalOperadorId() {
        return Optional.ofNullable(operadorId);
    }

    public LongFilter operadorId() {
        if (operadorId == null) {
            setOperadorId(new LongFilter());
        }
        return operadorId;
    }

    public void setOperadorId(LongFilter operadorId) {
        this.operadorId = operadorId;
    }

    public LongFilter getVersionDatosId() {
        return versionDatosId;
    }

    public Optional<LongFilter> optionalVersionDatosId() {
        return Optional.ofNullable(versionDatosId);
    }

    public LongFilter versionDatosId() {
        if (versionDatosId == null) {
            setVersionDatosId(new LongFilter());
        }
        return versionDatosId;
    }

    public void setVersionDatosId(LongFilter versionDatosId) {
        this.versionDatosId = versionDatosId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProyectoCriteria that = (ProyectoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nombreProyecto, that.nombreProyecto) &&
            Objects.equals(objetivo, that.objetivo) &&
            Objects.equals(tiempoProyecto, that.tiempoProyecto) &&
            Objects.equals(fechaInicio, that.fechaInicio) &&
            Objects.equals(fechaFin, that.fechaFin) &&
            Objects.equals(operadorId, that.operadorId) &&
            Objects.equals(versionDatosId, that.versionDatosId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombreProyecto, objetivo, tiempoProyecto, fechaInicio, fechaFin, operadorId, versionDatosId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProyectoCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalNombreProyecto().map(f -> "nombreProyecto=" + f + ", ").orElse("") +
            optionalObjetivo().map(f -> "objetivo=" + f + ", ").orElse("") +
            optionalTiempoProyecto().map(f -> "tiempoProyecto=" + f + ", ").orElse("") +
            optionalFechaInicio().map(f -> "fechaInicio=" + f + ", ").orElse("") +
            optionalFechaFin().map(f -> "fechaFin=" + f + ", ").orElse("") +
            optionalOperadorId().map(f -> "operadorId=" + f + ", ").orElse("") +
            optionalVersionDatosId().map(f -> "versionDatosId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}

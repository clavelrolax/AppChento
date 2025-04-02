package bo.ylb.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link bo.ylb.app.domain.Operador} entity. This class is used
 * in {@link bo.ylb.app.web.rest.OperadorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /operadors?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OperadorCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nombre;

    private StringFilter nacionalidad;

    private LocalDateFilter fechaCreacion;

    private LongFilter proyectoId;

    private Boolean distinct;

    public OperadorCriteria() {}

    public OperadorCriteria(OperadorCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.nombre = other.optionalNombre().map(StringFilter::copy).orElse(null);
        this.nacionalidad = other.optionalNacionalidad().map(StringFilter::copy).orElse(null);
        this.fechaCreacion = other.optionalFechaCreacion().map(LocalDateFilter::copy).orElse(null);
        this.proyectoId = other.optionalProyectoId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public OperadorCriteria copy() {
        return new OperadorCriteria(this);
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

    public StringFilter getNombre() {
        return nombre;
    }

    public Optional<StringFilter> optionalNombre() {
        return Optional.ofNullable(nombre);
    }

    public StringFilter nombre() {
        if (nombre == null) {
            setNombre(new StringFilter());
        }
        return nombre;
    }

    public void setNombre(StringFilter nombre) {
        this.nombre = nombre;
    }

    public StringFilter getNacionalidad() {
        return nacionalidad;
    }

    public Optional<StringFilter> optionalNacionalidad() {
        return Optional.ofNullable(nacionalidad);
    }

    public StringFilter nacionalidad() {
        if (nacionalidad == null) {
            setNacionalidad(new StringFilter());
        }
        return nacionalidad;
    }

    public void setNacionalidad(StringFilter nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public LocalDateFilter getFechaCreacion() {
        return fechaCreacion;
    }

    public Optional<LocalDateFilter> optionalFechaCreacion() {
        return Optional.ofNullable(fechaCreacion);
    }

    public LocalDateFilter fechaCreacion() {
        if (fechaCreacion == null) {
            setFechaCreacion(new LocalDateFilter());
        }
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateFilter fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LongFilter getProyectoId() {
        return proyectoId;
    }

    public Optional<LongFilter> optionalProyectoId() {
        return Optional.ofNullable(proyectoId);
    }

    public LongFilter proyectoId() {
        if (proyectoId == null) {
            setProyectoId(new LongFilter());
        }
        return proyectoId;
    }

    public void setProyectoId(LongFilter proyectoId) {
        this.proyectoId = proyectoId;
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
        final OperadorCriteria that = (OperadorCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(nacionalidad, that.nacionalidad) &&
            Objects.equals(fechaCreacion, that.fechaCreacion) &&
            Objects.equals(proyectoId, that.proyectoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, nacionalidad, fechaCreacion, proyectoId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OperadorCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalNombre().map(f -> "nombre=" + f + ", ").orElse("") +
            optionalNacionalidad().map(f -> "nacionalidad=" + f + ", ").orElse("") +
            optionalFechaCreacion().map(f -> "fechaCreacion=" + f + ", ").orElse("") +
            optionalProyectoId().map(f -> "proyectoId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}

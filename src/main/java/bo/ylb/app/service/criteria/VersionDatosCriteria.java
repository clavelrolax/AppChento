package bo.ylb.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link bo.ylb.app.domain.VersionDatos} entity. This class is used
 * in {@link bo.ylb.app.web.rest.VersionDatosResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /version-datos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VersionDatosCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nombreVersion;

    private LocalDateFilter fechaVersion;

    private StringFilter citeVersion;

    private LongFilter proyectoId;

    private LongFilter datosId;

    private LongFilter datosImagenId;

    private Boolean distinct;

    public VersionDatosCriteria() {}

    public VersionDatosCriteria(VersionDatosCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.nombreVersion = other.optionalNombreVersion().map(StringFilter::copy).orElse(null);
        this.fechaVersion = other.optionalFechaVersion().map(LocalDateFilter::copy).orElse(null);
        this.citeVersion = other.optionalCiteVersion().map(StringFilter::copy).orElse(null);
        this.proyectoId = other.optionalProyectoId().map(LongFilter::copy).orElse(null);
        this.datosId = other.optionalDatosId().map(LongFilter::copy).orElse(null);
        this.datosImagenId = other.optionalDatosImagenId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public VersionDatosCriteria copy() {
        return new VersionDatosCriteria(this);
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

    public StringFilter getNombreVersion() {
        return nombreVersion;
    }

    public Optional<StringFilter> optionalNombreVersion() {
        return Optional.ofNullable(nombreVersion);
    }

    public StringFilter nombreVersion() {
        if (nombreVersion == null) {
            setNombreVersion(new StringFilter());
        }
        return nombreVersion;
    }

    public void setNombreVersion(StringFilter nombreVersion) {
        this.nombreVersion = nombreVersion;
    }

    public LocalDateFilter getFechaVersion() {
        return fechaVersion;
    }

    public Optional<LocalDateFilter> optionalFechaVersion() {
        return Optional.ofNullable(fechaVersion);
    }

    public LocalDateFilter fechaVersion() {
        if (fechaVersion == null) {
            setFechaVersion(new LocalDateFilter());
        }
        return fechaVersion;
    }

    public void setFechaVersion(LocalDateFilter fechaVersion) {
        this.fechaVersion = fechaVersion;
    }

    public StringFilter getCiteVersion() {
        return citeVersion;
    }

    public Optional<StringFilter> optionalCiteVersion() {
        return Optional.ofNullable(citeVersion);
    }

    public StringFilter citeVersion() {
        if (citeVersion == null) {
            setCiteVersion(new StringFilter());
        }
        return citeVersion;
    }

    public void setCiteVersion(StringFilter citeVersion) {
        this.citeVersion = citeVersion;
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

    public LongFilter getDatosId() {
        return datosId;
    }

    public Optional<LongFilter> optionalDatosId() {
        return Optional.ofNullable(datosId);
    }

    public LongFilter datosId() {
        if (datosId == null) {
            setDatosId(new LongFilter());
        }
        return datosId;
    }

    public void setDatosId(LongFilter datosId) {
        this.datosId = datosId;
    }

    public LongFilter getDatosImagenId() {
        return datosImagenId;
    }

    public Optional<LongFilter> optionalDatosImagenId() {
        return Optional.ofNullable(datosImagenId);
    }

    public LongFilter datosImagenId() {
        if (datosImagenId == null) {
            setDatosImagenId(new LongFilter());
        }
        return datosImagenId;
    }

    public void setDatosImagenId(LongFilter datosImagenId) {
        this.datosImagenId = datosImagenId;
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
        final VersionDatosCriteria that = (VersionDatosCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nombreVersion, that.nombreVersion) &&
            Objects.equals(fechaVersion, that.fechaVersion) &&
            Objects.equals(citeVersion, that.citeVersion) &&
            Objects.equals(proyectoId, that.proyectoId) &&
            Objects.equals(datosId, that.datosId) &&
            Objects.equals(datosImagenId, that.datosImagenId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombreVersion, fechaVersion, citeVersion, proyectoId, datosId, datosImagenId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VersionDatosCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalNombreVersion().map(f -> "nombreVersion=" + f + ", ").orElse("") +
            optionalFechaVersion().map(f -> "fechaVersion=" + f + ", ").orElse("") +
            optionalCiteVersion().map(f -> "citeVersion=" + f + ", ").orElse("") +
            optionalProyectoId().map(f -> "proyectoId=" + f + ", ").orElse("") +
            optionalDatosId().map(f -> "datosId=" + f + ", ").orElse("") +
            optionalDatosImagenId().map(f -> "datosImagenId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}

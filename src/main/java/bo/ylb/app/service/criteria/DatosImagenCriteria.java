package bo.ylb.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link bo.ylb.app.domain.DatosImagen} entity. This class is used
 * in {@link bo.ylb.app.web.rest.DatosImagenResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /datos-imagens?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DatosImagenCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter versionDatosId;

    private Boolean distinct;

    public DatosImagenCriteria() {}

    public DatosImagenCriteria(DatosImagenCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.versionDatosId = other.optionalVersionDatosId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public DatosImagenCriteria copy() {
        return new DatosImagenCriteria(this);
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
        final DatosImagenCriteria that = (DatosImagenCriteria) o;
        return (
            Objects.equals(id, that.id) && Objects.equals(versionDatosId, that.versionDatosId) && Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, versionDatosId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DatosImagenCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalVersionDatosId().map(f -> "versionDatosId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}

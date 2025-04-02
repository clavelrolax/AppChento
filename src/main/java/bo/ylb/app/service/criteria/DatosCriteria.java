package bo.ylb.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link bo.ylb.app.domain.Datos} entity. This class is used
 * in {@link bo.ylb.app.web.rest.DatosResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /datos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DatosCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter inversionTotal;

    private StringFilter ingresosxVentas;

    private StringFilter gananciasYLB;

    private StringFilter goubernamentTak;

    private StringFilter regalias;

    private StringFilter iue;

    private StringFilter iva;

    private StringFilter it;

    private StringFilter t1precioVentaprom;

    private StringFilter t1costoVariable;

    private StringFilter t1costoVartarifa;

    private StringFilter t1margenUnitario;

    private StringFilter t1costoFijo;

    private StringFilter t1costoTotalunitprom;

    private StringFilter t1puntoEquilibrio;

    private StringFilter t2tasainteres;

    private StringFilter t2tasadescuento;

    private StringFilter t2vandelProyecto;

    private StringFilter t2vanYlb;

    private StringFilter t2vp;

    private StringFilter t2tirProyecto;

    private StringFilter t2tirYlb;

    private LongFilter versionDatosId;

    private Boolean distinct;

    public DatosCriteria() {}

    public DatosCriteria(DatosCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.inversionTotal = other.optionalInversionTotal().map(StringFilter::copy).orElse(null);
        this.ingresosxVentas = other.optionalIngresosxVentas().map(StringFilter::copy).orElse(null);
        this.gananciasYLB = other.optionalGananciasYLB().map(StringFilter::copy).orElse(null);
        this.goubernamentTak = other.optionalGoubernamentTak().map(StringFilter::copy).orElse(null);
        this.regalias = other.optionalRegalias().map(StringFilter::copy).orElse(null);
        this.iue = other.optionalIue().map(StringFilter::copy).orElse(null);
        this.iva = other.optionalIva().map(StringFilter::copy).orElse(null);
        this.it = other.optionalIt().map(StringFilter::copy).orElse(null);
        this.t1precioVentaprom = other.optionalt1precioVentaprom().map(StringFilter::copy).orElse(null);
        this.t1costoVariable = other.optionalt1costoVariable().map(StringFilter::copy).orElse(null);
        this.t1costoVartarifa = other.optionalt1costoVartarifa().map(StringFilter::copy).orElse(null);
        this.t1margenUnitario = other.optionalt1margenUnitario().map(StringFilter::copy).orElse(null);
        this.t1costoFijo = other.optionalt1costoFijo().map(StringFilter::copy).orElse(null);
        this.t1costoTotalunitprom = other.optionalt1costoTotalunitprom().map(StringFilter::copy).orElse(null);
        this.t1puntoEquilibrio = other.optionalt1puntoEquilibrio().map(StringFilter::copy).orElse(null);
        this.t2tasainteres = other.optionalt2tasainteres().map(StringFilter::copy).orElse(null);
        this.t2tasadescuento = other.optionalt2tasadescuento().map(StringFilter::copy).orElse(null);
        this.t2vandelProyecto = other.optionalt2vandelProyecto().map(StringFilter::copy).orElse(null);
        this.t2vanYlb = other.optionalt2vanYlb().map(StringFilter::copy).orElse(null);
        this.t2vp = other.optionalt2vp().map(StringFilter::copy).orElse(null);
        this.t2tirProyecto = other.optionalt2tirProyecto().map(StringFilter::copy).orElse(null);
        this.t2tirYlb = other.optionalt2tirYlb().map(StringFilter::copy).orElse(null);
        this.versionDatosId = other.optionalVersionDatosId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public DatosCriteria copy() {
        return new DatosCriteria(this);
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

    public StringFilter getInversionTotal() {
        return inversionTotal;
    }

    public Optional<StringFilter> optionalInversionTotal() {
        return Optional.ofNullable(inversionTotal);
    }

    public StringFilter inversionTotal() {
        if (inversionTotal == null) {
            setInversionTotal(new StringFilter());
        }
        return inversionTotal;
    }

    public void setInversionTotal(StringFilter inversionTotal) {
        this.inversionTotal = inversionTotal;
    }

    public StringFilter getIngresosxVentas() {
        return ingresosxVentas;
    }

    public Optional<StringFilter> optionalIngresosxVentas() {
        return Optional.ofNullable(ingresosxVentas);
    }

    public StringFilter ingresosxVentas() {
        if (ingresosxVentas == null) {
            setIngresosxVentas(new StringFilter());
        }
        return ingresosxVentas;
    }

    public void setIngresosxVentas(StringFilter ingresosxVentas) {
        this.ingresosxVentas = ingresosxVentas;
    }

    public StringFilter getGananciasYLB() {
        return gananciasYLB;
    }

    public Optional<StringFilter> optionalGananciasYLB() {
        return Optional.ofNullable(gananciasYLB);
    }

    public StringFilter gananciasYLB() {
        if (gananciasYLB == null) {
            setGananciasYLB(new StringFilter());
        }
        return gananciasYLB;
    }

    public void setGananciasYLB(StringFilter gananciasYLB) {
        this.gananciasYLB = gananciasYLB;
    }

    public StringFilter getGoubernamentTak() {
        return goubernamentTak;
    }

    public Optional<StringFilter> optionalGoubernamentTak() {
        return Optional.ofNullable(goubernamentTak);
    }

    public StringFilter goubernamentTak() {
        if (goubernamentTak == null) {
            setGoubernamentTak(new StringFilter());
        }
        return goubernamentTak;
    }

    public void setGoubernamentTak(StringFilter goubernamentTak) {
        this.goubernamentTak = goubernamentTak;
    }

    public StringFilter getRegalias() {
        return regalias;
    }

    public Optional<StringFilter> optionalRegalias() {
        return Optional.ofNullable(regalias);
    }

    public StringFilter regalias() {
        if (regalias == null) {
            setRegalias(new StringFilter());
        }
        return regalias;
    }

    public void setRegalias(StringFilter regalias) {
        this.regalias = regalias;
    }

    public StringFilter getIue() {
        return iue;
    }

    public Optional<StringFilter> optionalIue() {
        return Optional.ofNullable(iue);
    }

    public StringFilter iue() {
        if (iue == null) {
            setIue(new StringFilter());
        }
        return iue;
    }

    public void setIue(StringFilter iue) {
        this.iue = iue;
    }

    public StringFilter getIva() {
        return iva;
    }

    public Optional<StringFilter> optionalIva() {
        return Optional.ofNullable(iva);
    }

    public StringFilter iva() {
        if (iva == null) {
            setIva(new StringFilter());
        }
        return iva;
    }

    public void setIva(StringFilter iva) {
        this.iva = iva;
    }

    public StringFilter getIt() {
        return it;
    }

    public Optional<StringFilter> optionalIt() {
        return Optional.ofNullable(it);
    }

    public StringFilter it() {
        if (it == null) {
            setIt(new StringFilter());
        }
        return it;
    }

    public void setIt(StringFilter it) {
        this.it = it;
    }

    public StringFilter gett1precioVentaprom() {
        return t1precioVentaprom;
    }

    public Optional<StringFilter> optionalt1precioVentaprom() {
        return Optional.ofNullable(t1precioVentaprom);
    }

    public StringFilter t1precioVentaprom() {
        if (t1precioVentaprom == null) {
            sett1precioVentaprom(new StringFilter());
        }
        return t1precioVentaprom;
    }

    public void sett1precioVentaprom(StringFilter t1precioVentaprom) {
        this.t1precioVentaprom = t1precioVentaprom;
    }

    public StringFilter gett1costoVariable() {
        return t1costoVariable;
    }

    public Optional<StringFilter> optionalt1costoVariable() {
        return Optional.ofNullable(t1costoVariable);
    }

    public StringFilter t1costoVariable() {
        if (t1costoVariable == null) {
            sett1costoVariable(new StringFilter());
        }
        return t1costoVariable;
    }

    public void sett1costoVariable(StringFilter t1costoVariable) {
        this.t1costoVariable = t1costoVariable;
    }

    public StringFilter gett1costoVartarifa() {
        return t1costoVartarifa;
    }

    public Optional<StringFilter> optionalt1costoVartarifa() {
        return Optional.ofNullable(t1costoVartarifa);
    }

    public StringFilter t1costoVartarifa() {
        if (t1costoVartarifa == null) {
            sett1costoVartarifa(new StringFilter());
        }
        return t1costoVartarifa;
    }

    public void sett1costoVartarifa(StringFilter t1costoVartarifa) {
        this.t1costoVartarifa = t1costoVartarifa;
    }

    public StringFilter gett1margenUnitario() {
        return t1margenUnitario;
    }

    public Optional<StringFilter> optionalt1margenUnitario() {
        return Optional.ofNullable(t1margenUnitario);
    }

    public StringFilter t1margenUnitario() {
        if (t1margenUnitario == null) {
            sett1margenUnitario(new StringFilter());
        }
        return t1margenUnitario;
    }

    public void sett1margenUnitario(StringFilter t1margenUnitario) {
        this.t1margenUnitario = t1margenUnitario;
    }

    public StringFilter gett1costoFijo() {
        return t1costoFijo;
    }

    public Optional<StringFilter> optionalt1costoFijo() {
        return Optional.ofNullable(t1costoFijo);
    }

    public StringFilter t1costoFijo() {
        if (t1costoFijo == null) {
            sett1costoFijo(new StringFilter());
        }
        return t1costoFijo;
    }

    public void sett1costoFijo(StringFilter t1costoFijo) {
        this.t1costoFijo = t1costoFijo;
    }

    public StringFilter gett1costoTotalunitprom() {
        return t1costoTotalunitprom;
    }

    public Optional<StringFilter> optionalt1costoTotalunitprom() {
        return Optional.ofNullable(t1costoTotalunitprom);
    }

    public StringFilter t1costoTotalunitprom() {
        if (t1costoTotalunitprom == null) {
            sett1costoTotalunitprom(new StringFilter());
        }
        return t1costoTotalunitprom;
    }

    public void sett1costoTotalunitprom(StringFilter t1costoTotalunitprom) {
        this.t1costoTotalunitprom = t1costoTotalunitprom;
    }

    public StringFilter gett1puntoEquilibrio() {
        return t1puntoEquilibrio;
    }

    public Optional<StringFilter> optionalt1puntoEquilibrio() {
        return Optional.ofNullable(t1puntoEquilibrio);
    }

    public StringFilter t1puntoEquilibrio() {
        if (t1puntoEquilibrio == null) {
            sett1puntoEquilibrio(new StringFilter());
        }
        return t1puntoEquilibrio;
    }

    public void sett1puntoEquilibrio(StringFilter t1puntoEquilibrio) {
        this.t1puntoEquilibrio = t1puntoEquilibrio;
    }

    public StringFilter gett2tasainteres() {
        return t2tasainteres;
    }

    public Optional<StringFilter> optionalt2tasainteres() {
        return Optional.ofNullable(t2tasainteres);
    }

    public StringFilter t2tasainteres() {
        if (t2tasainteres == null) {
            sett2tasainteres(new StringFilter());
        }
        return t2tasainteres;
    }

    public void sett2tasainteres(StringFilter t2tasainteres) {
        this.t2tasainteres = t2tasainteres;
    }

    public StringFilter gett2tasadescuento() {
        return t2tasadescuento;
    }

    public Optional<StringFilter> optionalt2tasadescuento() {
        return Optional.ofNullable(t2tasadescuento);
    }

    public StringFilter t2tasadescuento() {
        if (t2tasadescuento == null) {
            sett2tasadescuento(new StringFilter());
        }
        return t2tasadescuento;
    }

    public void sett2tasadescuento(StringFilter t2tasadescuento) {
        this.t2tasadescuento = t2tasadescuento;
    }

    public StringFilter gett2vandelProyecto() {
        return t2vandelProyecto;
    }

    public Optional<StringFilter> optionalt2vandelProyecto() {
        return Optional.ofNullable(t2vandelProyecto);
    }

    public StringFilter t2vandelProyecto() {
        if (t2vandelProyecto == null) {
            sett2vandelProyecto(new StringFilter());
        }
        return t2vandelProyecto;
    }

    public void sett2vandelProyecto(StringFilter t2vandelProyecto) {
        this.t2vandelProyecto = t2vandelProyecto;
    }

    public StringFilter gett2vanYlb() {
        return t2vanYlb;
    }

    public Optional<StringFilter> optionalt2vanYlb() {
        return Optional.ofNullable(t2vanYlb);
    }

    public StringFilter t2vanYlb() {
        if (t2vanYlb == null) {
            sett2vanYlb(new StringFilter());
        }
        return t2vanYlb;
    }

    public void sett2vanYlb(StringFilter t2vanYlb) {
        this.t2vanYlb = t2vanYlb;
    }

    public StringFilter gett2vp() {
        return t2vp;
    }

    public Optional<StringFilter> optionalt2vp() {
        return Optional.ofNullable(t2vp);
    }

    public StringFilter t2vp() {
        if (t2vp == null) {
            sett2vp(new StringFilter());
        }
        return t2vp;
    }

    public void sett2vp(StringFilter t2vp) {
        this.t2vp = t2vp;
    }

    public StringFilter gett2tirProyecto() {
        return t2tirProyecto;
    }

    public Optional<StringFilter> optionalt2tirProyecto() {
        return Optional.ofNullable(t2tirProyecto);
    }

    public StringFilter t2tirProyecto() {
        if (t2tirProyecto == null) {
            sett2tirProyecto(new StringFilter());
        }
        return t2tirProyecto;
    }

    public void sett2tirProyecto(StringFilter t2tirProyecto) {
        this.t2tirProyecto = t2tirProyecto;
    }

    public StringFilter gett2tirYlb() {
        return t2tirYlb;
    }

    public Optional<StringFilter> optionalt2tirYlb() {
        return Optional.ofNullable(t2tirYlb);
    }

    public StringFilter t2tirYlb() {
        if (t2tirYlb == null) {
            sett2tirYlb(new StringFilter());
        }
        return t2tirYlb;
    }

    public void sett2tirYlb(StringFilter t2tirYlb) {
        this.t2tirYlb = t2tirYlb;
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
        final DatosCriteria that = (DatosCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(inversionTotal, that.inversionTotal) &&
            Objects.equals(ingresosxVentas, that.ingresosxVentas) &&
            Objects.equals(gananciasYLB, that.gananciasYLB) &&
            Objects.equals(goubernamentTak, that.goubernamentTak) &&
            Objects.equals(regalias, that.regalias) &&
            Objects.equals(iue, that.iue) &&
            Objects.equals(iva, that.iva) &&
            Objects.equals(it, that.it) &&
            Objects.equals(t1precioVentaprom, that.t1precioVentaprom) &&
            Objects.equals(t1costoVariable, that.t1costoVariable) &&
            Objects.equals(t1costoVartarifa, that.t1costoVartarifa) &&
            Objects.equals(t1margenUnitario, that.t1margenUnitario) &&
            Objects.equals(t1costoFijo, that.t1costoFijo) &&
            Objects.equals(t1costoTotalunitprom, that.t1costoTotalunitprom) &&
            Objects.equals(t1puntoEquilibrio, that.t1puntoEquilibrio) &&
            Objects.equals(t2tasainteres, that.t2tasainteres) &&
            Objects.equals(t2tasadescuento, that.t2tasadescuento) &&
            Objects.equals(t2vandelProyecto, that.t2vandelProyecto) &&
            Objects.equals(t2vanYlb, that.t2vanYlb) &&
            Objects.equals(t2vp, that.t2vp) &&
            Objects.equals(t2tirProyecto, that.t2tirProyecto) &&
            Objects.equals(t2tirYlb, that.t2tirYlb) &&
            Objects.equals(versionDatosId, that.versionDatosId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            inversionTotal,
            ingresosxVentas,
            gananciasYLB,
            goubernamentTak,
            regalias,
            iue,
            iva,
            it,
            t1precioVentaprom,
            t1costoVariable,
            t1costoVartarifa,
            t1margenUnitario,
            t1costoFijo,
            t1costoTotalunitprom,
            t1puntoEquilibrio,
            t2tasainteres,
            t2tasadescuento,
            t2vandelProyecto,
            t2vanYlb,
            t2vp,
            t2tirProyecto,
            t2tirYlb,
            versionDatosId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DatosCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalInversionTotal().map(f -> "inversionTotal=" + f + ", ").orElse("") +
            optionalIngresosxVentas().map(f -> "ingresosxVentas=" + f + ", ").orElse("") +
            optionalGananciasYLB().map(f -> "gananciasYLB=" + f + ", ").orElse("") +
            optionalGoubernamentTak().map(f -> "goubernamentTak=" + f + ", ").orElse("") +
            optionalRegalias().map(f -> "regalias=" + f + ", ").orElse("") +
            optionalIue().map(f -> "iue=" + f + ", ").orElse("") +
            optionalIva().map(f -> "iva=" + f + ", ").orElse("") +
            optionalIt().map(f -> "it=" + f + ", ").orElse("") +
            optionalt1precioVentaprom().map(f -> "t1precioVentaprom=" + f + ", ").orElse("") +
            optionalt1costoVariable().map(f -> "t1costoVariable=" + f + ", ").orElse("") +
            optionalt1costoVartarifa().map(f -> "t1costoVartarifa=" + f + ", ").orElse("") +
            optionalt1margenUnitario().map(f -> "t1margenUnitario=" + f + ", ").orElse("") +
            optionalt1costoFijo().map(f -> "t1costoFijo=" + f + ", ").orElse("") +
            optionalt1costoTotalunitprom().map(f -> "t1costoTotalunitprom=" + f + ", ").orElse("") +
            optionalt1puntoEquilibrio().map(f -> "t1puntoEquilibrio=" + f + ", ").orElse("") +
            optionalt2tasainteres().map(f -> "t2tasainteres=" + f + ", ").orElse("") +
            optionalt2tasadescuento().map(f -> "t2tasadescuento=" + f + ", ").orElse("") +
            optionalt2vandelProyecto().map(f -> "t2vandelProyecto=" + f + ", ").orElse("") +
            optionalt2vanYlb().map(f -> "t2vanYlb=" + f + ", ").orElse("") +
            optionalt2vp().map(f -> "t2vp=" + f + ", ").orElse("") +
            optionalt2tirProyecto().map(f -> "t2tirProyecto=" + f + ", ").orElse("") +
            optionalt2tirYlb().map(f -> "t2tirYlb=" + f + ", ").orElse("") +
            optionalVersionDatosId().map(f -> "versionDatosId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}

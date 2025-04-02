package bo.ylb.app.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link bo.ylb.app.domain.Datos} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DatosDTO implements Serializable {

    private Long id;

    @Schema(description = "fieldName")
    private String inversionTotal;

    private String ingresosxVentas;

    private String gananciasYLB;

    private String goubernamentTak;

    private String regalias;

    private String iue;

    private String iva;

    private String it;

    private String t1precioVentaprom;

    private String t1costoVariable;

    private String t1costoVartarifa;

    private String t1margenUnitario;

    private String t1costoFijo;

    private String t1costoTotalunitprom;

    private String t1puntoEquilibrio;

    private String t2tasainteres;

    private String t2tasadescuento;

    private String t2vandelProyecto;

    private String t2vanYlb;

    private String t2vp;

    private String t2tirProyecto;

    private String t2tirYlb;

    private VersionDatosDTO versionDatos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInversionTotal() {
        return inversionTotal;
    }

    public void setInversionTotal(String inversionTotal) {
        this.inversionTotal = inversionTotal;
    }

    public String getIngresosxVentas() {
        return ingresosxVentas;
    }

    public void setIngresosxVentas(String ingresosxVentas) {
        this.ingresosxVentas = ingresosxVentas;
    }

    public String getGananciasYLB() {
        return gananciasYLB;
    }

    public void setGananciasYLB(String gananciasYLB) {
        this.gananciasYLB = gananciasYLB;
    }

    public String getGoubernamentTak() {
        return goubernamentTak;
    }

    public void setGoubernamentTak(String goubernamentTak) {
        this.goubernamentTak = goubernamentTak;
    }

    public String getRegalias() {
        return regalias;
    }

    public void setRegalias(String regalias) {
        this.regalias = regalias;
    }

    public String getIue() {
        return iue;
    }

    public void setIue(String iue) {
        this.iue = iue;
    }

    public String getIva() {
        return iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }

    public String getIt() {
        return it;
    }

    public void setIt(String it) {
        this.it = it;
    }

    public String gett1precioVentaprom() {
        return t1precioVentaprom;
    }

    public void sett1precioVentaprom(String t1precioVentaprom) {
        this.t1precioVentaprom = t1precioVentaprom;
    }

    public String gett1costoVariable() {
        return t1costoVariable;
    }

    public void sett1costoVariable(String t1costoVariable) {
        this.t1costoVariable = t1costoVariable;
    }

    public String gett1costoVartarifa() {
        return t1costoVartarifa;
    }

    public void sett1costoVartarifa(String t1costoVartarifa) {
        this.t1costoVartarifa = t1costoVartarifa;
    }

    public String gett1margenUnitario() {
        return t1margenUnitario;
    }

    public void sett1margenUnitario(String t1margenUnitario) {
        this.t1margenUnitario = t1margenUnitario;
    }

    public String gett1costoFijo() {
        return t1costoFijo;
    }

    public void sett1costoFijo(String t1costoFijo) {
        this.t1costoFijo = t1costoFijo;
    }

    public String gett1costoTotalunitprom() {
        return t1costoTotalunitprom;
    }

    public void sett1costoTotalunitprom(String t1costoTotalunitprom) {
        this.t1costoTotalunitprom = t1costoTotalunitprom;
    }

    public String gett1puntoEquilibrio() {
        return t1puntoEquilibrio;
    }

    public void sett1puntoEquilibrio(String t1puntoEquilibrio) {
        this.t1puntoEquilibrio = t1puntoEquilibrio;
    }

    public String gett2tasainteres() {
        return t2tasainteres;
    }

    public void sett2tasainteres(String t2tasainteres) {
        this.t2tasainteres = t2tasainteres;
    }

    public String gett2tasadescuento() {
        return t2tasadescuento;
    }

    public void sett2tasadescuento(String t2tasadescuento) {
        this.t2tasadescuento = t2tasadescuento;
    }

    public String gett2vandelProyecto() {
        return t2vandelProyecto;
    }

    public void sett2vandelProyecto(String t2vandelProyecto) {
        this.t2vandelProyecto = t2vandelProyecto;
    }

    public String gett2vanYlb() {
        return t2vanYlb;
    }

    public void sett2vanYlb(String t2vanYlb) {
        this.t2vanYlb = t2vanYlb;
    }

    public String gett2vp() {
        return t2vp;
    }

    public void sett2vp(String t2vp) {
        this.t2vp = t2vp;
    }

    public String gett2tirProyecto() {
        return t2tirProyecto;
    }

    public void sett2tirProyecto(String t2tirProyecto) {
        this.t2tirProyecto = t2tirProyecto;
    }

    public String gett2tirYlb() {
        return t2tirYlb;
    }

    public void sett2tirYlb(String t2tirYlb) {
        this.t2tirYlb = t2tirYlb;
    }

    public VersionDatosDTO getVersionDatos() {
        return versionDatos;
    }

    public void setVersionDatos(VersionDatosDTO versionDatos) {
        this.versionDatos = versionDatos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DatosDTO)) {
            return false;
        }

        DatosDTO datosDTO = (DatosDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, datosDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DatosDTO{" +
            "id=" + getId() +
            ", inversionTotal='" + getInversionTotal() + "'" +
            ", ingresosxVentas='" + getIngresosxVentas() + "'" +
            ", gananciasYLB='" + getGananciasYLB() + "'" +
            ", goubernamentTak='" + getGoubernamentTak() + "'" +
            ", regalias='" + getRegalias() + "'" +
            ", iue='" + getIue() + "'" +
            ", iva='" + getIva() + "'" +
            ", it='" + getIt() + "'" +
            ", t1precioVentaprom='" + gett1precioVentaprom() + "'" +
            ", t1costoVariable='" + gett1costoVariable() + "'" +
            ", t1costoVartarifa='" + gett1costoVartarifa() + "'" +
            ", t1margenUnitario='" + gett1margenUnitario() + "'" +
            ", t1costoFijo='" + gett1costoFijo() + "'" +
            ", t1costoTotalunitprom='" + gett1costoTotalunitprom() + "'" +
            ", t1puntoEquilibrio='" + gett1puntoEquilibrio() + "'" +
            ", t2tasainteres='" + gett2tasainteres() + "'" +
            ", t2tasadescuento='" + gett2tasadescuento() + "'" +
            ", t2vandelProyecto='" + gett2vandelProyecto() + "'" +
            ", t2vanYlb='" + gett2vanYlb() + "'" +
            ", t2vp='" + gett2vp() + "'" +
            ", t2tirProyecto='" + gett2tirProyecto() + "'" +
            ", t2tirYlb='" + gett2tirYlb() + "'" +
            ", versionDatos=" + getVersionDatos() +
            "}";
    }
}

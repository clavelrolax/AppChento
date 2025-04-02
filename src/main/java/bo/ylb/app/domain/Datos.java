package bo.ylb.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Datos.
 */
@Entity
@Table(name = "datos")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Datos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * fieldName
     */
    @Column(name = "inversion_total")
    private String inversionTotal;

    @Column(name = "ingresosx_ventas")
    private String ingresosxVentas;

    @Column(name = "ganancias_ylb")
    private String gananciasYLB;

    @Column(name = "goubernament_tak")
    private String goubernamentTak;

    @Column(name = "regalias")
    private String regalias;

    @Column(name = "iue")
    private String iue;

    @Column(name = "iva")
    private String iva;

    @Column(name = "it")
    private String it;

    @Column(name = "t_1_precio_ventaprom")
    private String t1precioVentaprom;

    @Column(name = "t_1_costo_variable")
    private String t1costoVariable;

    @Column(name = "t_1_costo_vartarifa")
    private String t1costoVartarifa;

    @Column(name = "t_1_margen_unitario")
    private String t1margenUnitario;

    @Column(name = "t_1_costo_fijo")
    private String t1costoFijo;

    @Column(name = "t_1_costo_totalunitprom")
    private String t1costoTotalunitprom;

    @Column(name = "t_1_punto_equilibrio")
    private String t1puntoEquilibrio;

    @Column(name = "t_2_tasainteres")
    private String t2tasainteres;

    @Column(name = "t_2_tasadescuento")
    private String t2tasadescuento;

    @Column(name = "t_2_vandel_proyecto")
    private String t2vandelProyecto;

    @Column(name = "t_2_van_ylb")
    private String t2vanYlb;

    @Column(name = "t_2_vp")
    private String t2vp;

    @Column(name = "t_2_tir_proyecto")
    private String t2tirProyecto;

    @Column(name = "t_2_tir_ylb")
    private String t2tirYlb;

    @JsonIgnoreProperties(value = { "proyecto", "datos", "datosImagen" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private VersionDatos versionDatos;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Datos id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInversionTotal() {
        return this.inversionTotal;
    }

    public Datos inversionTotal(String inversionTotal) {
        this.setInversionTotal(inversionTotal);
        return this;
    }

    public void setInversionTotal(String inversionTotal) {
        this.inversionTotal = inversionTotal;
    }

    public String getIngresosxVentas() {
        return this.ingresosxVentas;
    }

    public Datos ingresosxVentas(String ingresosxVentas) {
        this.setIngresosxVentas(ingresosxVentas);
        return this;
    }

    public void setIngresosxVentas(String ingresosxVentas) {
        this.ingresosxVentas = ingresosxVentas;
    }

    public String getGananciasYLB() {
        return this.gananciasYLB;
    }

    public Datos gananciasYLB(String gananciasYLB) {
        this.setGananciasYLB(gananciasYLB);
        return this;
    }

    public void setGananciasYLB(String gananciasYLB) {
        this.gananciasYLB = gananciasYLB;
    }

    public String getGoubernamentTak() {
        return this.goubernamentTak;
    }

    public Datos goubernamentTak(String goubernamentTak) {
        this.setGoubernamentTak(goubernamentTak);
        return this;
    }

    public void setGoubernamentTak(String goubernamentTak) {
        this.goubernamentTak = goubernamentTak;
    }

    public String getRegalias() {
        return this.regalias;
    }

    public Datos regalias(String regalias) {
        this.setRegalias(regalias);
        return this;
    }

    public void setRegalias(String regalias) {
        this.regalias = regalias;
    }

    public String getIue() {
        return this.iue;
    }

    public Datos iue(String iue) {
        this.setIue(iue);
        return this;
    }

    public void setIue(String iue) {
        this.iue = iue;
    }

    public String getIva() {
        return this.iva;
    }

    public Datos iva(String iva) {
        this.setIva(iva);
        return this;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }

    public String getIt() {
        return this.it;
    }

    public Datos it(String it) {
        this.setIt(it);
        return this;
    }

    public void setIt(String it) {
        this.it = it;
    }

    public String gett1precioVentaprom() {
        return this.t1precioVentaprom;
    }

    public Datos t1precioVentaprom(String t1precioVentaprom) {
        this.sett1precioVentaprom(t1precioVentaprom);
        return this;
    }

    public void sett1precioVentaprom(String t1precioVentaprom) {
        this.t1precioVentaprom = t1precioVentaprom;
    }

    public String gett1costoVariable() {
        return this.t1costoVariable;
    }

    public Datos t1costoVariable(String t1costoVariable) {
        this.sett1costoVariable(t1costoVariable);
        return this;
    }

    public void sett1costoVariable(String t1costoVariable) {
        this.t1costoVariable = t1costoVariable;
    }

    public String gett1costoVartarifa() {
        return this.t1costoVartarifa;
    }

    public Datos t1costoVartarifa(String t1costoVartarifa) {
        this.sett1costoVartarifa(t1costoVartarifa);
        return this;
    }

    public void sett1costoVartarifa(String t1costoVartarifa) {
        this.t1costoVartarifa = t1costoVartarifa;
    }

    public String gett1margenUnitario() {
        return this.t1margenUnitario;
    }

    public Datos t1margenUnitario(String t1margenUnitario) {
        this.sett1margenUnitario(t1margenUnitario);
        return this;
    }

    public void sett1margenUnitario(String t1margenUnitario) {
        this.t1margenUnitario = t1margenUnitario;
    }

    public String gett1costoFijo() {
        return this.t1costoFijo;
    }

    public Datos t1costoFijo(String t1costoFijo) {
        this.sett1costoFijo(t1costoFijo);
        return this;
    }

    public void sett1costoFijo(String t1costoFijo) {
        this.t1costoFijo = t1costoFijo;
    }

    public String gett1costoTotalunitprom() {
        return this.t1costoTotalunitprom;
    }

    public Datos t1costoTotalunitprom(String t1costoTotalunitprom) {
        this.sett1costoTotalunitprom(t1costoTotalunitprom);
        return this;
    }

    public void sett1costoTotalunitprom(String t1costoTotalunitprom) {
        this.t1costoTotalunitprom = t1costoTotalunitprom;
    }

    public String gett1puntoEquilibrio() {
        return this.t1puntoEquilibrio;
    }

    public Datos t1puntoEquilibrio(String t1puntoEquilibrio) {
        this.sett1puntoEquilibrio(t1puntoEquilibrio);
        return this;
    }

    public void sett1puntoEquilibrio(String t1puntoEquilibrio) {
        this.t1puntoEquilibrio = t1puntoEquilibrio;
    }

    public String gett2tasainteres() {
        return this.t2tasainteres;
    }

    public Datos t2tasainteres(String t2tasainteres) {
        this.sett2tasainteres(t2tasainteres);
        return this;
    }

    public void sett2tasainteres(String t2tasainteres) {
        this.t2tasainteres = t2tasainteres;
    }

    public String gett2tasadescuento() {
        return this.t2tasadescuento;
    }

    public Datos t2tasadescuento(String t2tasadescuento) {
        this.sett2tasadescuento(t2tasadescuento);
        return this;
    }

    public void sett2tasadescuento(String t2tasadescuento) {
        this.t2tasadescuento = t2tasadescuento;
    }

    public String gett2vandelProyecto() {
        return this.t2vandelProyecto;
    }

    public Datos t2vandelProyecto(String t2vandelProyecto) {
        this.sett2vandelProyecto(t2vandelProyecto);
        return this;
    }

    public void sett2vandelProyecto(String t2vandelProyecto) {
        this.t2vandelProyecto = t2vandelProyecto;
    }

    public String gett2vanYlb() {
        return this.t2vanYlb;
    }

    public Datos t2vanYlb(String t2vanYlb) {
        this.sett2vanYlb(t2vanYlb);
        return this;
    }

    public void sett2vanYlb(String t2vanYlb) {
        this.t2vanYlb = t2vanYlb;
    }

    public String gett2vp() {
        return this.t2vp;
    }

    public Datos t2vp(String t2vp) {
        this.sett2vp(t2vp);
        return this;
    }

    public void sett2vp(String t2vp) {
        this.t2vp = t2vp;
    }

    public String gett2tirProyecto() {
        return this.t2tirProyecto;
    }

    public Datos t2tirProyecto(String t2tirProyecto) {
        this.sett2tirProyecto(t2tirProyecto);
        return this;
    }

    public void sett2tirProyecto(String t2tirProyecto) {
        this.t2tirProyecto = t2tirProyecto;
    }

    public String gett2tirYlb() {
        return this.t2tirYlb;
    }

    public Datos t2tirYlb(String t2tirYlb) {
        this.sett2tirYlb(t2tirYlb);
        return this;
    }

    public void sett2tirYlb(String t2tirYlb) {
        this.t2tirYlb = t2tirYlb;
    }

    public VersionDatos getVersionDatos() {
        return this.versionDatos;
    }

    public void setVersionDatos(VersionDatos versionDatos) {
        this.versionDatos = versionDatos;
    }

    public Datos versionDatos(VersionDatos versionDatos) {
        this.setVersionDatos(versionDatos);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Datos)) {
            return false;
        }
        return getId() != null && getId().equals(((Datos) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Datos{" +
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
            "}";
    }
}

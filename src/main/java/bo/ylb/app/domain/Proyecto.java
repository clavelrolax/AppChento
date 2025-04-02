package bo.ylb.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Proyecto.
 */
@Entity
@Table(name = "proyecto")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Proyecto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * fieldName
     */
    @NotNull
    @Column(name = "nombre_proyecto", nullable = false)
    private String nombreProyecto;

    @NotNull
    @Column(name = "objetivo", nullable = false)
    private String objetivo;

    @NotNull
    @Column(name = "tiempo_proyecto", nullable = false)
    private Integer tiempoProyecto;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "proyectos" }, allowSetters = true)
    private Operador operador;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "proyecto")
    @JsonIgnoreProperties(value = { "proyecto", "datos", "datosImagen" }, allowSetters = true)
    private Set<VersionDatos> versionDatos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Proyecto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreProyecto() {
        return this.nombreProyecto;
    }

    public Proyecto nombreProyecto(String nombreProyecto) {
        this.setNombreProyecto(nombreProyecto);
        return this;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public String getObjetivo() {
        return this.objetivo;
    }

    public Proyecto objetivo(String objetivo) {
        this.setObjetivo(objetivo);
        return this;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public Integer getTiempoProyecto() {
        return this.tiempoProyecto;
    }

    public Proyecto tiempoProyecto(Integer tiempoProyecto) {
        this.setTiempoProyecto(tiempoProyecto);
        return this;
    }

    public void setTiempoProyecto(Integer tiempoProyecto) {
        this.tiempoProyecto = tiempoProyecto;
    }

    public LocalDate getFechaInicio() {
        return this.fechaInicio;
    }

    public Proyecto fechaInicio(LocalDate fechaInicio) {
        this.setFechaInicio(fechaInicio);
        return this;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return this.fechaFin;
    }

    public Proyecto fechaFin(LocalDate fechaFin) {
        this.setFechaFin(fechaFin);
        return this;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Operador getOperador() {
        return this.operador;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
    }

    public Proyecto operador(Operador operador) {
        this.setOperador(operador);
        return this;
    }

    public Set<VersionDatos> getVersionDatos() {
        return this.versionDatos;
    }

    public void setVersionDatos(Set<VersionDatos> versionDatos) {
        if (this.versionDatos != null) {
            this.versionDatos.forEach(i -> i.setProyecto(null));
        }
        if (versionDatos != null) {
            versionDatos.forEach(i -> i.setProyecto(this));
        }
        this.versionDatos = versionDatos;
    }

    public Proyecto versionDatos(Set<VersionDatos> versionDatos) {
        this.setVersionDatos(versionDatos);
        return this;
    }

    public Proyecto addVersionDatos(VersionDatos versionDatos) {
        this.versionDatos.add(versionDatos);
        versionDatos.setProyecto(this);
        return this;
    }

    public Proyecto removeVersionDatos(VersionDatos versionDatos) {
        this.versionDatos.remove(versionDatos);
        versionDatos.setProyecto(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Proyecto)) {
            return false;
        }
        return getId() != null && getId().equals(((Proyecto) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Proyecto{" +
            "id=" + getId() +
            ", nombreProyecto='" + getNombreProyecto() + "'" +
            ", objetivo='" + getObjetivo() + "'" +
            ", tiempoProyecto=" + getTiempoProyecto() +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            "}";
    }
}

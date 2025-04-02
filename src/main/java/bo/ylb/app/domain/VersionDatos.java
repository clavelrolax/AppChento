package bo.ylb.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A VersionDatos.
 */
@Entity
@Table(name = "version_datos")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VersionDatos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * fieldName
     */
    @NotNull
    @Column(name = "nombre_version", nullable = false)
    private String nombreVersion;

    @NotNull
    @Column(name = "fecha_version", nullable = false)
    private LocalDate fechaVersion;

    @NotNull
    @Column(name = "cite_version", nullable = false)
    private String citeVersion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "operador", "versionDatos" }, allowSetters = true)
    private Proyecto proyecto;

    @JsonIgnoreProperties(value = { "versionDatos" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "versionDatos")
    private Datos datos;

    @JsonIgnoreProperties(value = { "versionDatos" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "versionDatos")
    private DatosImagen datosImagen;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public VersionDatos id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreVersion() {
        return this.nombreVersion;
    }

    public VersionDatos nombreVersion(String nombreVersion) {
        this.setNombreVersion(nombreVersion);
        return this;
    }

    public void setNombreVersion(String nombreVersion) {
        this.nombreVersion = nombreVersion;
    }

    public LocalDate getFechaVersion() {
        return this.fechaVersion;
    }

    public VersionDatos fechaVersion(LocalDate fechaVersion) {
        this.setFechaVersion(fechaVersion);
        return this;
    }

    public void setFechaVersion(LocalDate fechaVersion) {
        this.fechaVersion = fechaVersion;
    }

    public String getCiteVersion() {
        return this.citeVersion;
    }

    public VersionDatos citeVersion(String citeVersion) {
        this.setCiteVersion(citeVersion);
        return this;
    }

    public void setCiteVersion(String citeVersion) {
        this.citeVersion = citeVersion;
    }

    public Proyecto getProyecto() {
        return this.proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public VersionDatos proyecto(Proyecto proyecto) {
        this.setProyecto(proyecto);
        return this;
    }

    public Datos getDatos() {
        return this.datos;
    }

    public void setDatos(Datos datos) {
        if (this.datos != null) {
            this.datos.setVersionDatos(null);
        }
        if (datos != null) {
            datos.setVersionDatos(this);
        }
        this.datos = datos;
    }

    public VersionDatos datos(Datos datos) {
        this.setDatos(datos);
        return this;
    }

    public DatosImagen getDatosImagen() {
        return this.datosImagen;
    }

    public void setDatosImagen(DatosImagen datosImagen) {
        if (this.datosImagen != null) {
            this.datosImagen.setVersionDatos(null);
        }
        if (datosImagen != null) {
            datosImagen.setVersionDatos(this);
        }
        this.datosImagen = datosImagen;
    }

    public VersionDatos datosImagen(DatosImagen datosImagen) {
        this.setDatosImagen(datosImagen);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VersionDatos)) {
            return false;
        }
        return getId() != null && getId().equals(((VersionDatos) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VersionDatos{" +
            "id=" + getId() +
            ", nombreVersion='" + getNombreVersion() + "'" +
            ", fechaVersion='" + getFechaVersion() + "'" +
            ", citeVersion='" + getCiteVersion() + "'" +
            "}";
    }
}

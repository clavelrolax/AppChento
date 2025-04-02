package bo.ylb.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * The Entity entity.
 * @author A true hipster
 */
@Entity
@Table(name = "operador")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Operador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * fieldName
     */
    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "nacionalidad", nullable = false)
    private String nacionalidad;

    @NotNull
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fechaCreacion;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "operador")
    @JsonIgnoreProperties(value = { "operador", "versionDatos" }, allowSetters = true)
    private Set<Proyecto> proyectos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Operador id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Operador nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNacionalidad() {
        return this.nacionalidad;
    }

    public Operador nacionalidad(String nacionalidad) {
        this.setNacionalidad(nacionalidad);
        return this;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public LocalDate getFechaCreacion() {
        return this.fechaCreacion;
    }

    public Operador fechaCreacion(LocalDate fechaCreacion) {
        this.setFechaCreacion(fechaCreacion);
        return this;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Set<Proyecto> getProyectos() {
        return this.proyectos;
    }

    public void setProyectos(Set<Proyecto> proyectos) {
        if (this.proyectos != null) {
            this.proyectos.forEach(i -> i.setOperador(null));
        }
        if (proyectos != null) {
            proyectos.forEach(i -> i.setOperador(this));
        }
        this.proyectos = proyectos;
    }

    public Operador proyectos(Set<Proyecto> proyectos) {
        this.setProyectos(proyectos);
        return this;
    }

    public Operador addProyecto(Proyecto proyecto) {
        this.proyectos.add(proyecto);
        proyecto.setOperador(this);
        return this;
    }

    public Operador removeProyecto(Proyecto proyecto) {
        this.proyectos.remove(proyecto);
        proyecto.setOperador(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Operador)) {
            return false;
        }
        return getId() != null && getId().equals(((Operador) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Operador{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", nacionalidad='" + getNacionalidad() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            "}";
    }
}

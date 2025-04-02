package bo.ylb.app.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link bo.ylb.app.domain.Operador} entity.
 */
@Schema(description = "The Entity entity.\n@author A true hipster")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OperadorDTO implements Serializable {

    private Long id;

    @NotNull
    @Schema(description = "fieldName", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @NotNull
    private String nacionalidad;

    @NotNull
    private LocalDate fechaCreacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OperadorDTO)) {
            return false;
        }

        OperadorDTO operadorDTO = (OperadorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, operadorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OperadorDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", nacionalidad='" + getNacionalidad() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            "}";
    }
}

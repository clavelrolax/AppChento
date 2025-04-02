package bo.ylb.app.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link bo.ylb.app.domain.Proyecto} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProyectoDTO implements Serializable {

    private Long id;

    @NotNull
    @Schema(description = "fieldName", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombreProyecto;

    @NotNull
    private String objetivo;

    @NotNull
    private Integer tiempoProyecto;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private OperadorDTO operador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public Integer getTiempoProyecto() {
        return tiempoProyecto;
    }

    public void setTiempoProyecto(Integer tiempoProyecto) {
        this.tiempoProyecto = tiempoProyecto;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public OperadorDTO getOperador() {
        return operador;
    }

    public void setOperador(OperadorDTO operador) {
        this.operador = operador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProyectoDTO)) {
            return false;
        }

        ProyectoDTO proyectoDTO = (ProyectoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, proyectoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProyectoDTO{" +
            "id=" + getId() +
            ", nombreProyecto='" + getNombreProyecto() + "'" +
            ", objetivo='" + getObjetivo() + "'" +
            ", tiempoProyecto=" + getTiempoProyecto() +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", operador=" + getOperador() +
            "}";
    }
}

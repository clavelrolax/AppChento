package bo.ylb.app.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link bo.ylb.app.domain.VersionDatos} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VersionDatosDTO implements Serializable {

    private Long id;

    @NotNull
    @Schema(description = "fieldName", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombreVersion;

    @NotNull
    private LocalDate fechaVersion;

    @NotNull
    private String citeVersion;

    private ProyectoDTO proyecto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreVersion() {
        return nombreVersion;
    }

    public void setNombreVersion(String nombreVersion) {
        this.nombreVersion = nombreVersion;
    }

    public LocalDate getFechaVersion() {
        return fechaVersion;
    }

    public void setFechaVersion(LocalDate fechaVersion) {
        this.fechaVersion = fechaVersion;
    }

    public String getCiteVersion() {
        return citeVersion;
    }

    public void setCiteVersion(String citeVersion) {
        this.citeVersion = citeVersion;
    }

    public ProyectoDTO getProyecto() {
        return proyecto;
    }

    public void setProyecto(ProyectoDTO proyecto) {
        this.proyecto = proyecto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VersionDatosDTO)) {
            return false;
        }

        VersionDatosDTO versionDatosDTO = (VersionDatosDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, versionDatosDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VersionDatosDTO{" +
            "id=" + getId() +
            ", nombreVersion='" + getNombreVersion() + "'" +
            ", fechaVersion='" + getFechaVersion() + "'" +
            ", citeVersion='" + getCiteVersion() + "'" +
            ", proyecto=" + getProyecto() +
            "}";
    }
}

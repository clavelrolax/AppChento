package bo.ylb.app.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Lob;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link bo.ylb.app.domain.DatosImagen} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DatosImagenDTO implements Serializable {

    private Long id;

    @Schema(description = "fieldName")
    @Lob
    private byte[] imagen1;

    private String imagen1ContentType;

    @Lob
    private byte[] imagen2;

    private String imagen2ContentType;

    @Lob
    private byte[] imagen3;

    private String imagen3ContentType;

    @Lob
    private byte[] imagen4;

    private String imagen4ContentType;

    @Lob
    private byte[] imagen5;

    private String imagen5ContentType;

    @Lob
    private byte[] imagen6;

    private String imagen6ContentType;

    @Lob
    private byte[] imagen7;

    private String imagen7ContentType;

    @Lob
    private byte[] imagen8;

    private String imagen8ContentType;

    @Lob
    private byte[] imagen9;

    private String imagen9ContentType;

    @Lob
    private byte[] imagen10;

    private String imagen10ContentType;

    @Lob
    private byte[] imagen11;

    private String imagen11ContentType;

    @Lob
    private byte[] imagen12;

    private String imagen12ContentType;

    private VersionDatosDTO versionDatos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImagen1() {
        return imagen1;
    }

    public void setImagen1(byte[] imagen1) {
        this.imagen1 = imagen1;
    }

    public String getImagen1ContentType() {
        return imagen1ContentType;
    }

    public void setImagen1ContentType(String imagen1ContentType) {
        this.imagen1ContentType = imagen1ContentType;
    }

    public byte[] getImagen2() {
        return imagen2;
    }

    public void setImagen2(byte[] imagen2) {
        this.imagen2 = imagen2;
    }

    public String getImagen2ContentType() {
        return imagen2ContentType;
    }

    public void setImagen2ContentType(String imagen2ContentType) {
        this.imagen2ContentType = imagen2ContentType;
    }

    public byte[] getImagen3() {
        return imagen3;
    }

    public void setImagen3(byte[] imagen3) {
        this.imagen3 = imagen3;
    }

    public String getImagen3ContentType() {
        return imagen3ContentType;
    }

    public void setImagen3ContentType(String imagen3ContentType) {
        this.imagen3ContentType = imagen3ContentType;
    }

    public byte[] getImagen4() {
        return imagen4;
    }

    public void setImagen4(byte[] imagen4) {
        this.imagen4 = imagen4;
    }

    public String getImagen4ContentType() {
        return imagen4ContentType;
    }

    public void setImagen4ContentType(String imagen4ContentType) {
        this.imagen4ContentType = imagen4ContentType;
    }

    public byte[] getImagen5() {
        return imagen5;
    }

    public void setImagen5(byte[] imagen5) {
        this.imagen5 = imagen5;
    }

    public String getImagen5ContentType() {
        return imagen5ContentType;
    }

    public void setImagen5ContentType(String imagen5ContentType) {
        this.imagen5ContentType = imagen5ContentType;
    }

    public byte[] getImagen6() {
        return imagen6;
    }

    public void setImagen6(byte[] imagen6) {
        this.imagen6 = imagen6;
    }

    public String getImagen6ContentType() {
        return imagen6ContentType;
    }

    public void setImagen6ContentType(String imagen6ContentType) {
        this.imagen6ContentType = imagen6ContentType;
    }

    public byte[] getImagen7() {
        return imagen7;
    }

    public void setImagen7(byte[] imagen7) {
        this.imagen7 = imagen7;
    }

    public String getImagen7ContentType() {
        return imagen7ContentType;
    }

    public void setImagen7ContentType(String imagen7ContentType) {
        this.imagen7ContentType = imagen7ContentType;
    }

    public byte[] getImagen8() {
        return imagen8;
    }

    public void setImagen8(byte[] imagen8) {
        this.imagen8 = imagen8;
    }

    public String getImagen8ContentType() {
        return imagen8ContentType;
    }

    public void setImagen8ContentType(String imagen8ContentType) {
        this.imagen8ContentType = imagen8ContentType;
    }

    public byte[] getImagen9() {
        return imagen9;
    }

    public void setImagen9(byte[] imagen9) {
        this.imagen9 = imagen9;
    }

    public String getImagen9ContentType() {
        return imagen9ContentType;
    }

    public void setImagen9ContentType(String imagen9ContentType) {
        this.imagen9ContentType = imagen9ContentType;
    }

    public byte[] getImagen10() {
        return imagen10;
    }

    public void setImagen10(byte[] imagen10) {
        this.imagen10 = imagen10;
    }

    public String getImagen10ContentType() {
        return imagen10ContentType;
    }

    public void setImagen10ContentType(String imagen10ContentType) {
        this.imagen10ContentType = imagen10ContentType;
    }

    public byte[] getImagen11() {
        return imagen11;
    }

    public void setImagen11(byte[] imagen11) {
        this.imagen11 = imagen11;
    }

    public String getImagen11ContentType() {
        return imagen11ContentType;
    }

    public void setImagen11ContentType(String imagen11ContentType) {
        this.imagen11ContentType = imagen11ContentType;
    }

    public byte[] getImagen12() {
        return imagen12;
    }

    public void setImagen12(byte[] imagen12) {
        this.imagen12 = imagen12;
    }

    public String getImagen12ContentType() {
        return imagen12ContentType;
    }

    public void setImagen12ContentType(String imagen12ContentType) {
        this.imagen12ContentType = imagen12ContentType;
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
        if (!(o instanceof DatosImagenDTO)) {
            return false;
        }

        DatosImagenDTO datosImagenDTO = (DatosImagenDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, datosImagenDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DatosImagenDTO{" +
            "id=" + getId() +
            ", imagen1='" + getImagen1() + "'" +
            ", imagen2='" + getImagen2() + "'" +
            ", imagen3='" + getImagen3() + "'" +
            ", imagen4='" + getImagen4() + "'" +
            ", imagen5='" + getImagen5() + "'" +
            ", imagen6='" + getImagen6() + "'" +
            ", imagen7='" + getImagen7() + "'" +
            ", imagen8='" + getImagen8() + "'" +
            ", imagen9='" + getImagen9() + "'" +
            ", imagen10='" + getImagen10() + "'" +
            ", imagen11='" + getImagen11() + "'" +
            ", imagen12='" + getImagen12() + "'" +
            ", versionDatos=" + getVersionDatos() +
            "}";
    }
}

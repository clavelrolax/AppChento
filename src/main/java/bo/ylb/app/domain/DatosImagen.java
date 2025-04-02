package bo.ylb.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A DatosImagen.
 */
@Entity
@Table(name = "datos_imagen")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DatosImagen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * fieldName
     */
    @Lob
    @Column(name = "imagen_1")
    private byte[] imagen1;

    @Column(name = "imagen_1_content_type")
    private String imagen1ContentType;

    @Lob
    @Column(name = "imagen_2")
    private byte[] imagen2;

    @Column(name = "imagen_2_content_type")
    private String imagen2ContentType;

    @Lob
    @Column(name = "imagen_3")
    private byte[] imagen3;

    @Column(name = "imagen_3_content_type")
    private String imagen3ContentType;

    @Lob
    @Column(name = "imagen_4")
    private byte[] imagen4;

    @Column(name = "imagen_4_content_type")
    private String imagen4ContentType;

    @Lob
    @Column(name = "imagen_5")
    private byte[] imagen5;

    @Column(name = "imagen_5_content_type")
    private String imagen5ContentType;

    @Lob
    @Column(name = "imagen_6")
    private byte[] imagen6;

    @Column(name = "imagen_6_content_type")
    private String imagen6ContentType;

    @Lob
    @Column(name = "imagen_7")
    private byte[] imagen7;

    @Column(name = "imagen_7_content_type")
    private String imagen7ContentType;

    @Lob
    @Column(name = "imagen_8")
    private byte[] imagen8;

    @Column(name = "imagen_8_content_type")
    private String imagen8ContentType;

    @Lob
    @Column(name = "imagen_9")
    private byte[] imagen9;

    @Column(name = "imagen_9_content_type")
    private String imagen9ContentType;

    @Lob
    @Column(name = "imagen_10")
    private byte[] imagen10;

    @Column(name = "imagen_10_content_type")
    private String imagen10ContentType;

    @Lob
    @Column(name = "imagen_11")
    private byte[] imagen11;

    @Column(name = "imagen_11_content_type")
    private String imagen11ContentType;

    @Lob
    @Column(name = "imagen_12")
    private byte[] imagen12;

    @Column(name = "imagen_12_content_type")
    private String imagen12ContentType;

    @JsonIgnoreProperties(value = { "proyecto", "datos", "datosImagen" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private VersionDatos versionDatos;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DatosImagen id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImagen1() {
        return this.imagen1;
    }

    public DatosImagen imagen1(byte[] imagen1) {
        this.setImagen1(imagen1);
        return this;
    }

    public void setImagen1(byte[] imagen1) {
        this.imagen1 = imagen1;
    }

    public String getImagen1ContentType() {
        return this.imagen1ContentType;
    }

    public DatosImagen imagen1ContentType(String imagen1ContentType) {
        this.imagen1ContentType = imagen1ContentType;
        return this;
    }

    public void setImagen1ContentType(String imagen1ContentType) {
        this.imagen1ContentType = imagen1ContentType;
    }

    public byte[] getImagen2() {
        return this.imagen2;
    }

    public DatosImagen imagen2(byte[] imagen2) {
        this.setImagen2(imagen2);
        return this;
    }

    public void setImagen2(byte[] imagen2) {
        this.imagen2 = imagen2;
    }

    public String getImagen2ContentType() {
        return this.imagen2ContentType;
    }

    public DatosImagen imagen2ContentType(String imagen2ContentType) {
        this.imagen2ContentType = imagen2ContentType;
        return this;
    }

    public void setImagen2ContentType(String imagen2ContentType) {
        this.imagen2ContentType = imagen2ContentType;
    }

    public byte[] getImagen3() {
        return this.imagen3;
    }

    public DatosImagen imagen3(byte[] imagen3) {
        this.setImagen3(imagen3);
        return this;
    }

    public void setImagen3(byte[] imagen3) {
        this.imagen3 = imagen3;
    }

    public String getImagen3ContentType() {
        return this.imagen3ContentType;
    }

    public DatosImagen imagen3ContentType(String imagen3ContentType) {
        this.imagen3ContentType = imagen3ContentType;
        return this;
    }

    public void setImagen3ContentType(String imagen3ContentType) {
        this.imagen3ContentType = imagen3ContentType;
    }

    public byte[] getImagen4() {
        return this.imagen4;
    }

    public DatosImagen imagen4(byte[] imagen4) {
        this.setImagen4(imagen4);
        return this;
    }

    public void setImagen4(byte[] imagen4) {
        this.imagen4 = imagen4;
    }

    public String getImagen4ContentType() {
        return this.imagen4ContentType;
    }

    public DatosImagen imagen4ContentType(String imagen4ContentType) {
        this.imagen4ContentType = imagen4ContentType;
        return this;
    }

    public void setImagen4ContentType(String imagen4ContentType) {
        this.imagen4ContentType = imagen4ContentType;
    }

    public byte[] getImagen5() {
        return this.imagen5;
    }

    public DatosImagen imagen5(byte[] imagen5) {
        this.setImagen5(imagen5);
        return this;
    }

    public void setImagen5(byte[] imagen5) {
        this.imagen5 = imagen5;
    }

    public String getImagen5ContentType() {
        return this.imagen5ContentType;
    }

    public DatosImagen imagen5ContentType(String imagen5ContentType) {
        this.imagen5ContentType = imagen5ContentType;
        return this;
    }

    public void setImagen5ContentType(String imagen5ContentType) {
        this.imagen5ContentType = imagen5ContentType;
    }

    public byte[] getImagen6() {
        return this.imagen6;
    }

    public DatosImagen imagen6(byte[] imagen6) {
        this.setImagen6(imagen6);
        return this;
    }

    public void setImagen6(byte[] imagen6) {
        this.imagen6 = imagen6;
    }

    public String getImagen6ContentType() {
        return this.imagen6ContentType;
    }

    public DatosImagen imagen6ContentType(String imagen6ContentType) {
        this.imagen6ContentType = imagen6ContentType;
        return this;
    }

    public void setImagen6ContentType(String imagen6ContentType) {
        this.imagen6ContentType = imagen6ContentType;
    }

    public byte[] getImagen7() {
        return this.imagen7;
    }

    public DatosImagen imagen7(byte[] imagen7) {
        this.setImagen7(imagen7);
        return this;
    }

    public void setImagen7(byte[] imagen7) {
        this.imagen7 = imagen7;
    }

    public String getImagen7ContentType() {
        return this.imagen7ContentType;
    }

    public DatosImagen imagen7ContentType(String imagen7ContentType) {
        this.imagen7ContentType = imagen7ContentType;
        return this;
    }

    public void setImagen7ContentType(String imagen7ContentType) {
        this.imagen7ContentType = imagen7ContentType;
    }

    public byte[] getImagen8() {
        return this.imagen8;
    }

    public DatosImagen imagen8(byte[] imagen8) {
        this.setImagen8(imagen8);
        return this;
    }

    public void setImagen8(byte[] imagen8) {
        this.imagen8 = imagen8;
    }

    public String getImagen8ContentType() {
        return this.imagen8ContentType;
    }

    public DatosImagen imagen8ContentType(String imagen8ContentType) {
        this.imagen8ContentType = imagen8ContentType;
        return this;
    }

    public void setImagen8ContentType(String imagen8ContentType) {
        this.imagen8ContentType = imagen8ContentType;
    }

    public byte[] getImagen9() {
        return this.imagen9;
    }

    public DatosImagen imagen9(byte[] imagen9) {
        this.setImagen9(imagen9);
        return this;
    }

    public void setImagen9(byte[] imagen9) {
        this.imagen9 = imagen9;
    }

    public String getImagen9ContentType() {
        return this.imagen9ContentType;
    }

    public DatosImagen imagen9ContentType(String imagen9ContentType) {
        this.imagen9ContentType = imagen9ContentType;
        return this;
    }

    public void setImagen9ContentType(String imagen9ContentType) {
        this.imagen9ContentType = imagen9ContentType;
    }

    public byte[] getImagen10() {
        return this.imagen10;
    }

    public DatosImagen imagen10(byte[] imagen10) {
        this.setImagen10(imagen10);
        return this;
    }

    public void setImagen10(byte[] imagen10) {
        this.imagen10 = imagen10;
    }

    public String getImagen10ContentType() {
        return this.imagen10ContentType;
    }

    public DatosImagen imagen10ContentType(String imagen10ContentType) {
        this.imagen10ContentType = imagen10ContentType;
        return this;
    }

    public void setImagen10ContentType(String imagen10ContentType) {
        this.imagen10ContentType = imagen10ContentType;
    }

    public byte[] getImagen11() {
        return this.imagen11;
    }

    public DatosImagen imagen11(byte[] imagen11) {
        this.setImagen11(imagen11);
        return this;
    }

    public void setImagen11(byte[] imagen11) {
        this.imagen11 = imagen11;
    }

    public String getImagen11ContentType() {
        return this.imagen11ContentType;
    }

    public DatosImagen imagen11ContentType(String imagen11ContentType) {
        this.imagen11ContentType = imagen11ContentType;
        return this;
    }

    public void setImagen11ContentType(String imagen11ContentType) {
        this.imagen11ContentType = imagen11ContentType;
    }

    public byte[] getImagen12() {
        return this.imagen12;
    }

    public DatosImagen imagen12(byte[] imagen12) {
        this.setImagen12(imagen12);
        return this;
    }

    public void setImagen12(byte[] imagen12) {
        this.imagen12 = imagen12;
    }

    public String getImagen12ContentType() {
        return this.imagen12ContentType;
    }

    public DatosImagen imagen12ContentType(String imagen12ContentType) {
        this.imagen12ContentType = imagen12ContentType;
        return this;
    }

    public void setImagen12ContentType(String imagen12ContentType) {
        this.imagen12ContentType = imagen12ContentType;
    }

    public VersionDatos getVersionDatos() {
        return this.versionDatos;
    }

    public void setVersionDatos(VersionDatos versionDatos) {
        this.versionDatos = versionDatos;
    }

    public DatosImagen versionDatos(VersionDatos versionDatos) {
        this.setVersionDatos(versionDatos);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DatosImagen)) {
            return false;
        }
        return getId() != null && getId().equals(((DatosImagen) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DatosImagen{" +
            "id=" + getId() +
            ", imagen1='" + getImagen1() + "'" +
            ", imagen1ContentType='" + getImagen1ContentType() + "'" +
            ", imagen2='" + getImagen2() + "'" +
            ", imagen2ContentType='" + getImagen2ContentType() + "'" +
            ", imagen3='" + getImagen3() + "'" +
            ", imagen3ContentType='" + getImagen3ContentType() + "'" +
            ", imagen4='" + getImagen4() + "'" +
            ", imagen4ContentType='" + getImagen4ContentType() + "'" +
            ", imagen5='" + getImagen5() + "'" +
            ", imagen5ContentType='" + getImagen5ContentType() + "'" +
            ", imagen6='" + getImagen6() + "'" +
            ", imagen6ContentType='" + getImagen6ContentType() + "'" +
            ", imagen7='" + getImagen7() + "'" +
            ", imagen7ContentType='" + getImagen7ContentType() + "'" +
            ", imagen8='" + getImagen8() + "'" +
            ", imagen8ContentType='" + getImagen8ContentType() + "'" +
            ", imagen9='" + getImagen9() + "'" +
            ", imagen9ContentType='" + getImagen9ContentType() + "'" +
            ", imagen10='" + getImagen10() + "'" +
            ", imagen10ContentType='" + getImagen10ContentType() + "'" +
            ", imagen11='" + getImagen11() + "'" +
            ", imagen11ContentType='" + getImagen11ContentType() + "'" +
            ", imagen12='" + getImagen12() + "'" +
            ", imagen12ContentType='" + getImagen12ContentType() + "'" +
            "}";
    }
}

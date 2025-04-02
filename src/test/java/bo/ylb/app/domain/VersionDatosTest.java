package bo.ylb.app.domain;

import static bo.ylb.app.domain.DatosImagenTestSamples.*;
import static bo.ylb.app.domain.DatosTestSamples.*;
import static bo.ylb.app.domain.ProyectoTestSamples.*;
import static bo.ylb.app.domain.VersionDatosTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import bo.ylb.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VersionDatosTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VersionDatos.class);
        VersionDatos versionDatos1 = getVersionDatosSample1();
        VersionDatos versionDatos2 = new VersionDatos();
        assertThat(versionDatos1).isNotEqualTo(versionDatos2);

        versionDatos2.setId(versionDatos1.getId());
        assertThat(versionDatos1).isEqualTo(versionDatos2);

        versionDatos2 = getVersionDatosSample2();
        assertThat(versionDatos1).isNotEqualTo(versionDatos2);
    }

    @Test
    void proyectoTest() {
        VersionDatos versionDatos = getVersionDatosRandomSampleGenerator();
        Proyecto proyectoBack = getProyectoRandomSampleGenerator();

        versionDatos.setProyecto(proyectoBack);
        assertThat(versionDatos.getProyecto()).isEqualTo(proyectoBack);

        versionDatos.proyecto(null);
        assertThat(versionDatos.getProyecto()).isNull();
    }

    @Test
    void datosTest() {
        VersionDatos versionDatos = getVersionDatosRandomSampleGenerator();
        Datos datosBack = getDatosRandomSampleGenerator();

        versionDatos.setDatos(datosBack);
        assertThat(versionDatos.getDatos()).isEqualTo(datosBack);
        assertThat(datosBack.getVersionDatos()).isEqualTo(versionDatos);

        versionDatos.datos(null);
        assertThat(versionDatos.getDatos()).isNull();
        assertThat(datosBack.getVersionDatos()).isNull();
    }

    @Test
    void datosImagenTest() {
        VersionDatos versionDatos = getVersionDatosRandomSampleGenerator();
        DatosImagen datosImagenBack = getDatosImagenRandomSampleGenerator();

        versionDatos.setDatosImagen(datosImagenBack);
        assertThat(versionDatos.getDatosImagen()).isEqualTo(datosImagenBack);
        assertThat(datosImagenBack.getVersionDatos()).isEqualTo(versionDatos);

        versionDatos.datosImagen(null);
        assertThat(versionDatos.getDatosImagen()).isNull();
        assertThat(datosImagenBack.getVersionDatos()).isNull();
    }
}

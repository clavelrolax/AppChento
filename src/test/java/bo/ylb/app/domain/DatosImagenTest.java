package bo.ylb.app.domain;

import static bo.ylb.app.domain.DatosImagenTestSamples.*;
import static bo.ylb.app.domain.VersionDatosTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import bo.ylb.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DatosImagenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DatosImagen.class);
        DatosImagen datosImagen1 = getDatosImagenSample1();
        DatosImagen datosImagen2 = new DatosImagen();
        assertThat(datosImagen1).isNotEqualTo(datosImagen2);

        datosImagen2.setId(datosImagen1.getId());
        assertThat(datosImagen1).isEqualTo(datosImagen2);

        datosImagen2 = getDatosImagenSample2();
        assertThat(datosImagen1).isNotEqualTo(datosImagen2);
    }

    @Test
    void versionDatosTest() {
        DatosImagen datosImagen = getDatosImagenRandomSampleGenerator();
        VersionDatos versionDatosBack = getVersionDatosRandomSampleGenerator();

        datosImagen.setVersionDatos(versionDatosBack);
        assertThat(datosImagen.getVersionDatos()).isEqualTo(versionDatosBack);

        datosImagen.versionDatos(null);
        assertThat(datosImagen.getVersionDatos()).isNull();
    }
}

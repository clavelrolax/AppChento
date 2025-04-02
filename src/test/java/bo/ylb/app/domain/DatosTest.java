package bo.ylb.app.domain;

import static bo.ylb.app.domain.DatosTestSamples.*;
import static bo.ylb.app.domain.VersionDatosTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import bo.ylb.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DatosTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Datos.class);
        Datos datos1 = getDatosSample1();
        Datos datos2 = new Datos();
        assertThat(datos1).isNotEqualTo(datos2);

        datos2.setId(datos1.getId());
        assertThat(datos1).isEqualTo(datos2);

        datos2 = getDatosSample2();
        assertThat(datos1).isNotEqualTo(datos2);
    }

    @Test
    void versionDatosTest() {
        Datos datos = getDatosRandomSampleGenerator();
        VersionDatos versionDatosBack = getVersionDatosRandomSampleGenerator();

        datos.setVersionDatos(versionDatosBack);
        assertThat(datos.getVersionDatos()).isEqualTo(versionDatosBack);

        datos.versionDatos(null);
        assertThat(datos.getVersionDatos()).isNull();
    }
}

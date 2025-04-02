package bo.ylb.app.domain;

import static bo.ylb.app.domain.OperadorTestSamples.*;
import static bo.ylb.app.domain.ProyectoTestSamples.*;
import static bo.ylb.app.domain.VersionDatosTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import bo.ylb.app.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProyectoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Proyecto.class);
        Proyecto proyecto1 = getProyectoSample1();
        Proyecto proyecto2 = new Proyecto();
        assertThat(proyecto1).isNotEqualTo(proyecto2);

        proyecto2.setId(proyecto1.getId());
        assertThat(proyecto1).isEqualTo(proyecto2);

        proyecto2 = getProyectoSample2();
        assertThat(proyecto1).isNotEqualTo(proyecto2);
    }

    @Test
    void operadorTest() {
        Proyecto proyecto = getProyectoRandomSampleGenerator();
        Operador operadorBack = getOperadorRandomSampleGenerator();

        proyecto.setOperador(operadorBack);
        assertThat(proyecto.getOperador()).isEqualTo(operadorBack);

        proyecto.operador(null);
        assertThat(proyecto.getOperador()).isNull();
    }

    @Test
    void versionDatosTest() {
        Proyecto proyecto = getProyectoRandomSampleGenerator();
        VersionDatos versionDatosBack = getVersionDatosRandomSampleGenerator();

        proyecto.addVersionDatos(versionDatosBack);
        assertThat(proyecto.getVersionDatos()).containsOnly(versionDatosBack);
        assertThat(versionDatosBack.getProyecto()).isEqualTo(proyecto);

        proyecto.removeVersionDatos(versionDatosBack);
        assertThat(proyecto.getVersionDatos()).doesNotContain(versionDatosBack);
        assertThat(versionDatosBack.getProyecto()).isNull();

        proyecto.versionDatos(new HashSet<>(Set.of(versionDatosBack)));
        assertThat(proyecto.getVersionDatos()).containsOnly(versionDatosBack);
        assertThat(versionDatosBack.getProyecto()).isEqualTo(proyecto);

        proyecto.setVersionDatos(new HashSet<>());
        assertThat(proyecto.getVersionDatos()).doesNotContain(versionDatosBack);
        assertThat(versionDatosBack.getProyecto()).isNull();
    }
}

package bo.ylb.app.domain;

import static bo.ylb.app.domain.OperadorTestSamples.*;
import static bo.ylb.app.domain.ProyectoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import bo.ylb.app.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class OperadorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Operador.class);
        Operador operador1 = getOperadorSample1();
        Operador operador2 = new Operador();
        assertThat(operador1).isNotEqualTo(operador2);

        operador2.setId(operador1.getId());
        assertThat(operador1).isEqualTo(operador2);

        operador2 = getOperadorSample2();
        assertThat(operador1).isNotEqualTo(operador2);
    }

    @Test
    void proyectoTest() {
        Operador operador = getOperadorRandomSampleGenerator();
        Proyecto proyectoBack = getProyectoRandomSampleGenerator();

        operador.addProyecto(proyectoBack);
        assertThat(operador.getProyectos()).containsOnly(proyectoBack);
        assertThat(proyectoBack.getOperador()).isEqualTo(operador);

        operador.removeProyecto(proyectoBack);
        assertThat(operador.getProyectos()).doesNotContain(proyectoBack);
        assertThat(proyectoBack.getOperador()).isNull();

        operador.proyectos(new HashSet<>(Set.of(proyectoBack)));
        assertThat(operador.getProyectos()).containsOnly(proyectoBack);
        assertThat(proyectoBack.getOperador()).isEqualTo(operador);

        operador.setProyectos(new HashSet<>());
        assertThat(operador.getProyectos()).doesNotContain(proyectoBack);
        assertThat(proyectoBack.getOperador()).isNull();
    }
}

package bo.ylb.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import bo.ylb.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DatosDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DatosDTO.class);
        DatosDTO datosDTO1 = new DatosDTO();
        datosDTO1.setId(1L);
        DatosDTO datosDTO2 = new DatosDTO();
        assertThat(datosDTO1).isNotEqualTo(datosDTO2);
        datosDTO2.setId(datosDTO1.getId());
        assertThat(datosDTO1).isEqualTo(datosDTO2);
        datosDTO2.setId(2L);
        assertThat(datosDTO1).isNotEqualTo(datosDTO2);
        datosDTO1.setId(null);
        assertThat(datosDTO1).isNotEqualTo(datosDTO2);
    }
}

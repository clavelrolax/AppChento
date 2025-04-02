package bo.ylb.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import bo.ylb.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DatosImagenDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DatosImagenDTO.class);
        DatosImagenDTO datosImagenDTO1 = new DatosImagenDTO();
        datosImagenDTO1.setId(1L);
        DatosImagenDTO datosImagenDTO2 = new DatosImagenDTO();
        assertThat(datosImagenDTO1).isNotEqualTo(datosImagenDTO2);
        datosImagenDTO2.setId(datosImagenDTO1.getId());
        assertThat(datosImagenDTO1).isEqualTo(datosImagenDTO2);
        datosImagenDTO2.setId(2L);
        assertThat(datosImagenDTO1).isNotEqualTo(datosImagenDTO2);
        datosImagenDTO1.setId(null);
        assertThat(datosImagenDTO1).isNotEqualTo(datosImagenDTO2);
    }
}

package bo.ylb.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import bo.ylb.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OperadorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OperadorDTO.class);
        OperadorDTO operadorDTO1 = new OperadorDTO();
        operadorDTO1.setId(1L);
        OperadorDTO operadorDTO2 = new OperadorDTO();
        assertThat(operadorDTO1).isNotEqualTo(operadorDTO2);
        operadorDTO2.setId(operadorDTO1.getId());
        assertThat(operadorDTO1).isEqualTo(operadorDTO2);
        operadorDTO2.setId(2L);
        assertThat(operadorDTO1).isNotEqualTo(operadorDTO2);
        operadorDTO1.setId(null);
        assertThat(operadorDTO1).isNotEqualTo(operadorDTO2);
    }
}

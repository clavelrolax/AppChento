package bo.ylb.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import bo.ylb.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VersionDatosDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VersionDatosDTO.class);
        VersionDatosDTO versionDatosDTO1 = new VersionDatosDTO();
        versionDatosDTO1.setId(1L);
        VersionDatosDTO versionDatosDTO2 = new VersionDatosDTO();
        assertThat(versionDatosDTO1).isNotEqualTo(versionDatosDTO2);
        versionDatosDTO2.setId(versionDatosDTO1.getId());
        assertThat(versionDatosDTO1).isEqualTo(versionDatosDTO2);
        versionDatosDTO2.setId(2L);
        assertThat(versionDatosDTO1).isNotEqualTo(versionDatosDTO2);
        versionDatosDTO1.setId(null);
        assertThat(versionDatosDTO1).isNotEqualTo(versionDatosDTO2);
    }
}

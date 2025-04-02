package bo.ylb.app.service.mapper;

import static bo.ylb.app.domain.VersionDatosAsserts.*;
import static bo.ylb.app.domain.VersionDatosTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VersionDatosMapperTest {

    private VersionDatosMapper versionDatosMapper;

    @BeforeEach
    void setUp() {
        versionDatosMapper = new VersionDatosMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getVersionDatosSample1();
        var actual = versionDatosMapper.toEntity(versionDatosMapper.toDto(expected));
        assertVersionDatosAllPropertiesEquals(expected, actual);
    }
}

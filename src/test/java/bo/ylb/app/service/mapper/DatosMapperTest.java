package bo.ylb.app.service.mapper;

import static bo.ylb.app.domain.DatosAsserts.*;
import static bo.ylb.app.domain.DatosTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DatosMapperTest {

    private DatosMapper datosMapper;

    @BeforeEach
    void setUp() {
        datosMapper = new DatosMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDatosSample1();
        var actual = datosMapper.toEntity(datosMapper.toDto(expected));
        assertDatosAllPropertiesEquals(expected, actual);
    }
}

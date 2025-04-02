package bo.ylb.app.service.mapper;

import static bo.ylb.app.domain.DatosImagenAsserts.*;
import static bo.ylb.app.domain.DatosImagenTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DatosImagenMapperTest {

    private DatosImagenMapper datosImagenMapper;

    @BeforeEach
    void setUp() {
        datosImagenMapper = new DatosImagenMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDatosImagenSample1();
        var actual = datosImagenMapper.toEntity(datosImagenMapper.toDto(expected));
        assertDatosImagenAllPropertiesEquals(expected, actual);
    }
}

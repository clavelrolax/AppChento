package bo.ylb.app.service.mapper;

import static bo.ylb.app.domain.ProyectoAsserts.*;
import static bo.ylb.app.domain.ProyectoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProyectoMapperTest {

    private ProyectoMapper proyectoMapper;

    @BeforeEach
    void setUp() {
        proyectoMapper = new ProyectoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getProyectoSample1();
        var actual = proyectoMapper.toEntity(proyectoMapper.toDto(expected));
        assertProyectoAllPropertiesEquals(expected, actual);
    }
}

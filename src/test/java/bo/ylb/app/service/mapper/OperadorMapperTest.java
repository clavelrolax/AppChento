package bo.ylb.app.service.mapper;

import static bo.ylb.app.domain.OperadorAsserts.*;
import static bo.ylb.app.domain.OperadorTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperadorMapperTest {

    private OperadorMapper operadorMapper;

    @BeforeEach
    void setUp() {
        operadorMapper = new OperadorMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getOperadorSample1();
        var actual = operadorMapper.toEntity(operadorMapper.toDto(expected));
        assertOperadorAllPropertiesEquals(expected, actual);
    }
}

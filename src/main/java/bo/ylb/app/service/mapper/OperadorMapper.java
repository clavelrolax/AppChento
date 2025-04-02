package bo.ylb.app.service.mapper;

import bo.ylb.app.domain.Operador;
import bo.ylb.app.service.dto.OperadorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Operador} and its DTO {@link OperadorDTO}.
 */
@Mapper(componentModel = "spring")
public interface OperadorMapper extends EntityMapper<OperadorDTO, Operador> {}

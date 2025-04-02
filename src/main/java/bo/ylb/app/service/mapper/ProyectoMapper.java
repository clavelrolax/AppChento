package bo.ylb.app.service.mapper;

import bo.ylb.app.domain.Operador;
import bo.ylb.app.domain.Proyecto;
import bo.ylb.app.service.dto.OperadorDTO;
import bo.ylb.app.service.dto.ProyectoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Proyecto} and its DTO {@link ProyectoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProyectoMapper extends EntityMapper<ProyectoDTO, Proyecto> {
    @Mapping(target = "operador", source = "operador", qualifiedByName = "operadorNombre")
    ProyectoDTO toDto(Proyecto s);

    @Named("operadorNombre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    OperadorDTO toDtoOperadorNombre(Operador operador);
}

package bo.ylb.app.service.mapper;

import bo.ylb.app.domain.Datos;
import bo.ylb.app.domain.VersionDatos;
import bo.ylb.app.service.dto.DatosDTO;
import bo.ylb.app.service.dto.VersionDatosDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Datos} and its DTO {@link DatosDTO}.
 */
@Mapper(componentModel = "spring")
public interface DatosMapper extends EntityMapper<DatosDTO, Datos> {
    @Mapping(target = "versionDatos", source = "versionDatos", qualifiedByName = "versionDatosNombreVersion")
    DatosDTO toDto(Datos s);

    @Named("versionDatosNombreVersion")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombreVersion", source = "nombreVersion")
    VersionDatosDTO toDtoVersionDatosNombreVersion(VersionDatos versionDatos);
}

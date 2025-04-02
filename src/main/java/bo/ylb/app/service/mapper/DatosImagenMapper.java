package bo.ylb.app.service.mapper;

import bo.ylb.app.domain.DatosImagen;
import bo.ylb.app.domain.VersionDatos;
import bo.ylb.app.service.dto.DatosImagenDTO;
import bo.ylb.app.service.dto.VersionDatosDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DatosImagen} and its DTO {@link DatosImagenDTO}.
 */
@Mapper(componentModel = "spring")
public interface DatosImagenMapper extends EntityMapper<DatosImagenDTO, DatosImagen> {
    @Mapping(target = "versionDatos", source = "versionDatos", qualifiedByName = "versionDatosNombreVersion")
    DatosImagenDTO toDto(DatosImagen s);

    @Named("versionDatosNombreVersion")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombreVersion", source = "nombreVersion")
    VersionDatosDTO toDtoVersionDatosNombreVersion(VersionDatos versionDatos);
}

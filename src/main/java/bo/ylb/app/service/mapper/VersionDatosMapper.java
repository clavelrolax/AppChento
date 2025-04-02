package bo.ylb.app.service.mapper;

import bo.ylb.app.domain.Proyecto;
import bo.ylb.app.domain.VersionDatos;
import bo.ylb.app.service.dto.ProyectoDTO;
import bo.ylb.app.service.dto.VersionDatosDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VersionDatos} and its DTO {@link VersionDatosDTO}.
 */
@Mapper(componentModel = "spring")
public interface VersionDatosMapper extends EntityMapper<VersionDatosDTO, VersionDatos> {
    @Mapping(target = "proyecto", source = "proyecto", qualifiedByName = "proyectoNombreProyecto")
    VersionDatosDTO toDto(VersionDatos s);

    @Named("proyectoNombreProyecto")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombreProyecto", source = "nombreProyecto")
    ProyectoDTO toDtoProyectoNombreProyecto(Proyecto proyecto);
}

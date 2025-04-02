package bo.ylb.app.repository;

import bo.ylb.app.domain.DatosImagen;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DatosImagen entity.
 */
@Repository
public interface DatosImagenRepository extends JpaRepository<DatosImagen, Long>, JpaSpecificationExecutor<DatosImagen> {
    default Optional<DatosImagen> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<DatosImagen> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<DatosImagen> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select datosImagen from DatosImagen datosImagen left join fetch datosImagen.versionDatos",
        countQuery = "select count(datosImagen) from DatosImagen datosImagen"
    )
    Page<DatosImagen> findAllWithToOneRelationships(Pageable pageable);

    @Query("select datosImagen from DatosImagen datosImagen left join fetch datosImagen.versionDatos")
    List<DatosImagen> findAllWithToOneRelationships();

    @Query("select datosImagen from DatosImagen datosImagen left join fetch datosImagen.versionDatos where datosImagen.id =:id")
    Optional<DatosImagen> findOneWithToOneRelationships(@Param("id") Long id);
}

package bo.ylb.app.repository;

import bo.ylb.app.domain.VersionDatos;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the VersionDatos entity.
 */
@Repository
public interface VersionDatosRepository extends JpaRepository<VersionDatos, Long>, JpaSpecificationExecutor<VersionDatos> {
    default Optional<VersionDatos> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<VersionDatos> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<VersionDatos> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select versionDatos from VersionDatos versionDatos left join fetch versionDatos.proyecto",
        countQuery = "select count(versionDatos) from VersionDatos versionDatos"
    )
    Page<VersionDatos> findAllWithToOneRelationships(Pageable pageable);

    @Query("select versionDatos from VersionDatos versionDatos left join fetch versionDatos.proyecto")
    List<VersionDatos> findAllWithToOneRelationships();

    @Query("select versionDatos from VersionDatos versionDatos left join fetch versionDatos.proyecto where versionDatos.id =:id")
    Optional<VersionDatos> findOneWithToOneRelationships(@Param("id") Long id);
}

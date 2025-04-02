package bo.ylb.app.repository;

import bo.ylb.app.domain.Datos;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Datos entity.
 */
@Repository
public interface DatosRepository extends JpaRepository<Datos, Long>, JpaSpecificationExecutor<Datos> {
    default Optional<Datos> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Datos> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Datos> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(value = "select datos from Datos datos left join fetch datos.versionDatos", countQuery = "select count(datos) from Datos datos")
    Page<Datos> findAllWithToOneRelationships(Pageable pageable);

    @Query("select datos from Datos datos left join fetch datos.versionDatos")
    List<Datos> findAllWithToOneRelationships();

    @Query("select datos from Datos datos left join fetch datos.versionDatos where datos.id =:id")
    Optional<Datos> findOneWithToOneRelationships(@Param("id") Long id);
}

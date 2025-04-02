package bo.ylb.app.repository;

import bo.ylb.app.domain.Proyecto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Proyecto entity.
 */
@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Long>, JpaSpecificationExecutor<Proyecto> {
    default Optional<Proyecto> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Proyecto> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Proyecto> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select proyecto from Proyecto proyecto left join fetch proyecto.operador",
        countQuery = "select count(proyecto) from Proyecto proyecto"
    )
    Page<Proyecto> findAllWithToOneRelationships(Pageable pageable);

    @Query("select proyecto from Proyecto proyecto left join fetch proyecto.operador")
    List<Proyecto> findAllWithToOneRelationships();

    @Query("select proyecto from Proyecto proyecto left join fetch proyecto.operador where proyecto.id =:id")
    Optional<Proyecto> findOneWithToOneRelationships(@Param("id") Long id);
}

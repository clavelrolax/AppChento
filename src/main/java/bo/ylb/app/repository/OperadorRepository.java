package bo.ylb.app.repository;

import bo.ylb.app.domain.Operador;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Operador entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperadorRepository extends JpaRepository<Operador, Long>, JpaSpecificationExecutor<Operador> {}

package io.pine.examples.cargo.infrastructure.persistence;

import io.pine.examples.cargo.domain.model.cargo.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Frank
 * @sinace 2018/8/14 0014.
 */
@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {

}

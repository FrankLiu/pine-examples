package io.pine.examples.cargo.infrastructure.persistence;

import io.pine.examples.cargo.domain.model.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Frank
 * @sinace 2018/8/14 0014.
 */
public interface LocationRepository extends JpaRepository<Location, Long> {

}

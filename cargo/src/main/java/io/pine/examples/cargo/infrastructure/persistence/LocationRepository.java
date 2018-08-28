package io.pine.examples.cargo.infrastructure.persistence;

import io.pine.examples.cargo.domain.model.location.Location;
import io.pine.examples.cargo.domain.model.location.UnLocode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Frank
 * @sinace 2018/8/14 0014.
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    /**
     * Find Location by {@link UnLocode}
     *
     * @param unLocode must not be {@literal null}
     * @return a single entity matching the given {@link Location} or {@link Optional#empty()} if none was found.
     */
    Optional<Location> findByUnLocode(UnLocode unLocode);

}

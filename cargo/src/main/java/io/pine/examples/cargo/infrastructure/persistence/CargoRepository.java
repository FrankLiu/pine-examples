package io.pine.examples.cargo.infrastructure.persistence;

import io.pine.examples.cargo.domain.model.cargo.Cargo;
import io.pine.examples.cargo.domain.model.cargo.TrackingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Frank
 * @sinace 2018/8/14 0014.
 */
@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {
    Optional<Cargo> findByTrackingId(TrackingId trackingId);
}

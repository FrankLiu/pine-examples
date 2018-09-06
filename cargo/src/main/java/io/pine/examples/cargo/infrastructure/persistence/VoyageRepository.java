package io.pine.examples.cargo.infrastructure.persistence;

import io.pine.examples.cargo.domain.model.voyage.Voyage;
import io.pine.examples.cargo.domain.model.voyage.VoyageNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author Frank
 * @sinace 2018/8/14 0014.
 */
public interface VoyageRepository extends JpaRepository<Voyage, Long> {
    Optional<Voyage> findByVoyageNumber(VoyageNumber voyageNumber);
}

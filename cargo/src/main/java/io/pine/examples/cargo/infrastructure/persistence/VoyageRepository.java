package io.pine.examples.cargo.infrastructure.persistence;

import io.pine.examples.cargo.domain.model.voyage.Voyage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Frank
 * @sinace 2018/8/14 0014.
 */
public interface VoyageRepository extends JpaRepository<Voyage, Long> {

}

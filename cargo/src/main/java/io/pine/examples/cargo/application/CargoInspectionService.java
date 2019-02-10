package io.pine.examples.cargo.application;

import io.pine.examples.cargo.domain.model.cargo.Cargo;
import io.pine.examples.cargo.domain.model.cargo.TrackingId;
import io.pine.examples.cargo.domain.model.handling.HandlingEvent;
import io.pine.examples.cargo.domain.model.handling.HandlingHistory;
import io.pine.examples.cargo.infrastructure.persistence.CargoRepository;
import io.pine.examples.cargo.infrastructure.persistence.HandlingEventRepository;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Cargo inspection service.
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class CargoInspectionService {
  private final Logger logger = LoggerFactory.getLogger(CargoInspectionService.class);

  @Autowired
  private ApplicationEvents applicationEvents;

  @Autowired
  private CargoRepository cargoRepository;

  @Autowired
  private HandlingEventRepository handlingEventRepository;

  public void setApplicationEvents(ApplicationEvents applicationEvents) {
    this.applicationEvents = applicationEvents;
  }

  public void setCargoRepository(CargoRepository cargoRepository) {
    this.cargoRepository = cargoRepository;
  }

  public void setHandlingEventRepository(HandlingEventRepository handlingEventRepository) {
    this.handlingEventRepository = handlingEventRepository;
  }

  /**
   * Inspect cargo and send relevant notifications to interested parties,
   * for example if a cargo has been misdirected, or unloaded
   * at the final destination.
   *
   * @param trackingId cargo tracking id
   */
  @Transactional
  public void inspectCargo(TrackingId trackingId) {
    Validate.notNull(trackingId, "Tracking ID is required");

    final Optional<Cargo> cargo = cargoRepository.findByTrackingId(trackingId);
    if (!cargo.isPresent()) {
      logger.warn("Can't inspect non-existing cargo " + trackingId);
      return;
    }

    final HandlingHistory handlingHistory = new HandlingHistory(handlingEventRepository.findAllByTrackingId(trackingId));

    cargo.get().deriveDeliveryProgress(handlingHistory);

    if (cargo.get().getDelivery().isMisdirected()) {
      applicationEvents.cargoWasMisdirected(cargo.get());
    }

    if (cargo.get().getDelivery().isUnloadedAtDestination()) {
      applicationEvents.cargoHasArrived(cargo.get());
    }

    cargoRepository.save(cargo.get());
  }
}

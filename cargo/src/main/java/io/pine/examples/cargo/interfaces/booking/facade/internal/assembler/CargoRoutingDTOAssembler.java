package io.pine.examples.cargo.interfaces.booking.facade.internal.assembler;

import io.pine.examples.cargo.domain.model.cargo.Cargo;
import io.pine.examples.cargo.domain.model.cargo.Leg;
import io.pine.examples.cargo.domain.model.cargo.RoutingStatus;
import io.pine.examples.cargo.interfaces.booking.facade.dto.CargoRoutingDTO;

/**
 * Assembler class for the CargoRoutingDTO.
 */
public class CargoRoutingDTOAssembler {

  /**
   *
   * @param cargo cargo
   * @return A cargo routing DTO
   */
  public CargoRoutingDTO toDTO(final Cargo cargo) {
    final CargoRoutingDTO dto = new CargoRoutingDTO(
      cargo.getTrackingId().toString(),
      cargo.getOrigin().getUnLocode().toString(),
      cargo.getRouteSpecification().getDestination().getUnLocode().toString(),
      cargo.getRouteSpecification().getArrivalDeadline(),
      cargo.getDelivery().getRoutingStatus().sameValueAs(RoutingStatus.MISROUTED));
    for (Leg leg : cargo.getItinerary().getLegs()) {
      dto.addLeg(
        leg.getVoyage().voyageNumber().idString(),
        leg.getLoadLocation().getUnLocode().toString(),
        leg.getUnloadLocation().getUnLocode().toString(),
        leg.getLoadTime(),
        leg.getUnloadTime());
    }
    return dto;
  }

}

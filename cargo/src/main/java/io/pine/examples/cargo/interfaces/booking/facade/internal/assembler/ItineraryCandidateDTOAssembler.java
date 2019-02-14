package io.pine.examples.cargo.interfaces.booking.facade.internal.assembler;

import io.pine.examples.cargo.domain.model.cargo.Itinerary;
import io.pine.examples.cargo.domain.model.cargo.Leg;
import io.pine.examples.cargo.domain.model.location.Location;
import io.pine.examples.cargo.domain.model.location.UnLocode;
import io.pine.examples.cargo.domain.model.voyage.Voyage;
import io.pine.examples.cargo.domain.model.voyage.VoyageNumber;
import io.pine.examples.cargo.infrastructure.persistence.LocationRepository;
import io.pine.examples.cargo.infrastructure.persistence.VoyageRepository;
import io.pine.examples.cargo.interfaces.booking.facade.dto.LegDTO;
import io.pine.examples.cargo.interfaces.booking.facade.dto.RouteCandidateDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Assembler class for the ItineraryCandidateDTO.
 */
public class ItineraryCandidateDTOAssembler {

  /**
   * @param itinerary itinerary
   * @return A route candidate DTO
   */
  public RouteCandidateDTO toDTO(final Itinerary itinerary) {
    final List<LegDTO> legDTOs = new ArrayList<LegDTO>(itinerary.getLegs().size());
    for (Leg leg : itinerary.getLegs()) {
      legDTOs.add(toLegDTO(leg));
    }
    return new RouteCandidateDTO(legDTOs);
  }

  /**
   * @param leg leg
   * @return A leg DTO
   */
  protected LegDTO toLegDTO(final Leg leg) {
    final VoyageNumber voyageNumber = leg.getVoyage().voyageNumber();
    final UnLocode from = leg.getLoadLocation().getUnLocode();
    final UnLocode to = leg.getUnloadLocation().getUnLocode();
    return new LegDTO(voyageNumber.idString(), from.toString(), to.toString(), leg.getLoadTime(), leg.getUnloadTime());
  }

  /**
   * @param routeCandidateDTO route candidate DTO
   * @param voyageRepository voyage repository
   * @param locationRepository location repository
   * @return An itinerary
   */
  public Itinerary fromDTO(final RouteCandidateDTO routeCandidateDTO,
                           final VoyageRepository voyageRepository,
                           final LocationRepository locationRepository) {
    final List<Leg> legs = new ArrayList<Leg>(routeCandidateDTO.getLegs().size());
    for (LegDTO legDTO : routeCandidateDTO.getLegs()) {
      final VoyageNumber voyageNumber = new VoyageNumber(legDTO.getVoyageNumber());
      final Voyage voyage = voyageRepository.findByVoyageNumber(voyageNumber).get();
      final Location from = locationRepository.findByUnLocode(new UnLocode(legDTO.getFrom())).get();
      final Location to = locationRepository.findByUnLocode(new UnLocode(legDTO.getTo())).get();
      legs.add(new Leg(voyage, from, to, legDTO.getLoadTime(), legDTO.getUnloadTime()));
    }
    return new Itinerary(legs);
  }
}

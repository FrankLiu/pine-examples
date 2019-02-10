package io.pine.examples.cargo.domain.service;

import io.pine.examples.cargo.domain.model.cargo.Itinerary;
import io.pine.examples.cargo.domain.model.cargo.RouteSpecification;

import java.util.List;

/**
 * Routing service.
 *
 */
public interface RoutingService {

  /**
   * @param routeSpecification route specification
   * @return A list of itineraries that satisfy the specification. May be an empty list if no route is found.
   */
  List<Itinerary> fetchRoutesForSpecification(RouteSpecification routeSpecification);

}

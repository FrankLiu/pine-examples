package io.pine.examples.cargo.infrastructure.messaging;

import io.pine.examples.cargo.application.CargoInspectionService;
import io.pine.examples.cargo.domain.model.cargo.TrackingId;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Consumes JMS messages and delegates notification of misdirected
 * cargo to the tracking service.
 *
 * This is a programmatic hook into the JMS infrastructure to
 * make cargo inspection message-driven.
 */
@Component
public class CargoHandledConsumer {

  private CargoInspectionService cargoInspectionService;
  private final Log logger = LogFactory.getLog(getClass());

  @JmsListener(destination = "", containerFactory = "myFactory")
  public void onMessage(final Message message) {
    try {
      final TextMessage textMessage = (TextMessage) message;
      final String trackingidString = textMessage.getText();
      
      cargoInspectionService.inspectCargo(new TrackingId(trackingidString));
    } catch (Exception e) {
      logger.error(e, e);
    }
  }

  public void setCargoInspectionService(CargoInspectionService cargoInspectionService) {
    this.cargoInspectionService = cargoInspectionService;
  }
}

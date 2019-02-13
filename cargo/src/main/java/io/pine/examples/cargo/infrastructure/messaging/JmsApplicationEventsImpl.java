package io.pine.examples.cargo.infrastructure.messaging;

import io.pine.examples.cargo.application.ApplicationEvents;
import io.pine.examples.cargo.domain.model.cargo.Cargo;
import io.pine.examples.cargo.domain.model.handling.HandlingEvent;
import io.pine.examples.cargo.domain.model.handling.HandlingEventRegistrationAttempt;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * JMS based implementation.
 */
@Component
public final class JmsApplicationEventsImpl implements ApplicationEvents {

  private JmsOperations jmsOperations;
  private Destination cargoHandledQueue;
  private Destination misdirectedCargoQueue;
  private Destination deliveredCargoQueue;
  private Destination rejectedRegistrationAttemptsQueue;
  private Destination handlingEventQueue;

  private static final Log logger = LogFactory.getLog(JmsApplicationEventsImpl.class);

  @Override
  @JmsListener(destination = "", containerFactory = "myFactory")
  public void cargoWasHandled(final HandlingEvent event) {
    final Cargo cargo = event.getCargo();
    logger.info("Cargo was handled " + cargo);
    jmsOperations.send(cargoHandledQueue, new MessageCreator() {
      public Message createMessage(final Session session) throws JMSException {
        return session.createTextMessage(cargo.getTrackingId().toString());
      }
    });
  }

  @Override
  public void cargoWasMisdirected(final Cargo cargo) {
    logger.info("Cargo was misdirected " + cargo);
    jmsOperations.send(misdirectedCargoQueue, new MessageCreator() {
      public Message createMessage(Session session) throws JMSException {
        return session.createTextMessage(cargo.getTrackingId().toString());
      }
    });
  }

  @Override
  public void cargoHasArrived(final Cargo cargo) {
    logger.info("Cargo has arrived " + cargo);
    jmsOperations.send(deliveredCargoQueue, new MessageCreator() {
      public Message createMessage(Session session) throws JMSException {
        return session.createTextMessage(cargo.getTrackingId().toString());
      }
    });
  }

  @Override
  public void receivedHandlingEventRegistrationAttempt(final HandlingEventRegistrationAttempt attempt) {
    logger.info("Received handling event registration attempt " + attempt);
    jmsOperations.send(handlingEventQueue, new MessageCreator() {
      public Message createMessage(Session session) throws JMSException {
        return session.createObjectMessage(attempt);
      }
    });
  }

  public void setJmsOperations(JmsOperations jmsOperations) {
    this.jmsOperations = jmsOperations;
  }

  public void setCargoHandledQueue(Destination destination) {
    this.cargoHandledQueue = destination;
  }

  public void setMisdirectedCargoQueue(Destination destination) {
    this.misdirectedCargoQueue = destination;
  }

  public void setDeliveredCargoQueue(Destination destination) {
    this.deliveredCargoQueue = destination;
  }

  public void setRejectedRegistrationAttemptsQueue(Destination destination) {
    this.rejectedRegistrationAttemptsQueue = destination;
  }

  public void setHandlingEventQueue(Destination destination) {
    this.handlingEventQueue = destination;
  }
}

package io.pine.examples.cargo.infrastructure.messaging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.annotation.JmsListener;

import javax.jms.Message;
import javax.jms.MessageListener;

public class SimpleLoggingConsumer implements MessageListener {

  private final Log logger = LogFactory.getLog(SimpleLoggingConsumer.class);

  @Override
  @JmsListener(destination = "", containerFactory = "myFactory")
  public void onMessage(Message message) {
    logger.debug("Received JMS message: " + message);
  }

}

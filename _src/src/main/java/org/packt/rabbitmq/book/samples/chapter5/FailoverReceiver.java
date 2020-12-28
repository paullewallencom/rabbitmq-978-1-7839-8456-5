package org.packt.rabbitmq.book.samples.chapter5;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class FailoverReceiver {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(TransactionalReceiver.class);
	
	private static final String REQUEST_QUEUE = "tx_queue";

	public void receive() {
		Connection connection = null;
		Channel channel = null;
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			connection = factory.newConnection();
			channel = connection.createChannel();
			
			QueueingConsumer consumer = new QueueingConsumer(channel);
			channel.basicConsume(REQUEST_QUEUE, false, consumer);
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			LOGGER.info("Request received: " + message);
			channel.txSelect();
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
			channel.txCommit();
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
			if (channel != null) {
				try {
					channel.txRollback();
				} catch (IOException re) {
					LOGGER.error("Rollback failed: " + re.getMessage(), re);
				}
			}
		} catch (ShutdownSignalException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (ConsumerCancelledException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (IOException e) {
					LOGGER.warn("Failed to close connection: " + e.getMessage(), e);
				}
			}
		}
	}
}

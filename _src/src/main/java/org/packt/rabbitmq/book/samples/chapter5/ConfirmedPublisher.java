package org.packt.rabbitmq.book.samples.chapter5;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConfirmedPublisher {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(ConfirmedPublisher.class);

	public void send(String exchange, String key, String message) {
		Connection connection = null;
		Channel channel = null;
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.addConfirmListener(new ConfirmListener() {

				public void handleNack(long deliveryTag, boolean multiple)
						throws IOException {
					LOGGER.warn("Message(s) rejected.");
				}

				public void handleAck(long deliveryTag, boolean multiple)
						throws IOException {
					LOGGER.warn("Message(s) confirmed.");
				}
			});

			channel.confirmSelect();
			channel.basicPublish(exchange, key, null, message.getBytes());
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (IOException e) {
					LOGGER.warn(
							"Failed to close connection: " + e.getMessage(), e);
				}
			}
		}
	}
}

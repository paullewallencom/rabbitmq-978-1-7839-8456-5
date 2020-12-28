package org.packt.rabbitmq.book.samples.chapter5;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class TransactionalSender {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(TransactionalSender.class);

	public void send(String exchange, String key, String message) {
		Connection connection = null;
		Channel channel = null;
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			connection = factory.newConnection();
			channel = connection.createChannel();

			channel.txSelect();
			channel.basicPublish(exchange, key, null, message.getBytes());
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

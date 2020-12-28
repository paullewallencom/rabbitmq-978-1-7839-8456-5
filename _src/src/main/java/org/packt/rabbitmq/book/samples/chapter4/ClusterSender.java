package org.packt.rabbitmq.book.samples.chapter4;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Address;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ClusterSender {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ClusterSender.class);
	
	private final static String QUEUE_NAME = "event_queue";

	private static final String DEFAULT_EXCHANGE = "";

	private Channel channel;

	private Connection connection;

	public void initialize(Address... hosts) {
		try {

			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			connection = factory.newConnection(hosts);
			channel = connection.createChannel();
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	public void send(String message) {
		try {
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			channel.basicPublish(DEFAULT_EXCHANGE, QUEUE_NAME, null,
					message.getBytes());
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	public void destroy() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (IOException e) {
			LOGGER.warn(e.getMessage(), e);
		}
	}
}

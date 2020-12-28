package org.packt.rabbitmq.book.samples.chapter4;

import com.rabbitmq.client.Address;

public class ClusterSenderDemo {

	private static final String NODE_HOSTNAME = "localhost";
	
	// default port 5672 which corresponds 
	// to the 'rabbit@Domain' instance 
	// is being used for the connection to the broker
	public static void sendToDefaultExchange() {
		ClusterSender sender = new ClusterSender();
		Address address = new Address(NODE_HOSTNAME);
		sender.initialize(address);
		sender.send("Test message.");
		sender.destroy();
	}
	
	public static void main(String[] args) {
		sendToDefaultExchange();
	}
}

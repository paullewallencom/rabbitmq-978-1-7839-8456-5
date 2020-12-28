package org.packt.rabbitmq.book.samples.chapter4;

import com.rabbitmq.client.Address;

public class ClusterReceiverDemo {

	private static final String NODE_HOSTNAME = "localhost";

	// this is the port on which instance3 is running
	private static final int NODE_PORT = 5703;

	public static void main(String[] args) throws InterruptedException {
		final ClusterReceiver receiver = new ClusterReceiver();
		Address address = new Address(NODE_HOSTNAME, NODE_PORT);
		receiver.initialize(address);
		receiver.receive();
		receiver.destroy();
	}
}

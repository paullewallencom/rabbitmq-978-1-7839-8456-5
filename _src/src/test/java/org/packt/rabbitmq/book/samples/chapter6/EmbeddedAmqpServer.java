package org.packt.rabbitmq.book.samples.chapter6;

import org.apache.qpid.server.Broker;
import org.apache.qpid.server.BrokerOptions;
import org.apache.qpid.server.Main;

public class EmbeddedAmqpServer {

	public static void startServer() throws Exception {
		BrokerOptions configuration = new BrokerOptions();
		Broker broker = new Broker();
		broker.startup(configuration);
	}

	public static void main(String[] args) throws Exception {
//		startServer();
		Main.main(args);
	}
}

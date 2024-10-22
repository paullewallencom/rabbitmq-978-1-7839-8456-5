package org.packt.rabbitmq.book.samples.chapter6;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

public class ContainerListenerExample {

	public static void main(String[] args) {

		CachingConnectionFactory factory = new CachingConnectionFactory(
				"localhost");
		
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(
				factory);
		
		Object listener = new Object() {
			public void handleMessage(String message) {
				System.out.println("Message received: " + message);
			}
		};
		MessageListenerAdapter adapter = new MessageListenerAdapter(listener);
		container.setMessageListener(adapter);
		container.setQueueNames("sample-queue");
		container.start();
	}
}

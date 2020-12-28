package org.packt.rabbitmq.book.samples.chapter6;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RabbitTemplateSpringExample {

	public static void main(String[] args) {

		AbstractApplicationContext context = new ClassPathXmlApplicationContext(
				"configuration.xml");
		RabbitTemplate template = context.getBean(RabbitTemplate.class);
		template.convertAndSend("Sample Spring test message.");
		context.destroy();
	}
}

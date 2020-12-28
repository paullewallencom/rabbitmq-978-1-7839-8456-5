package org.packt.rabbitmq.book.samples.chapter2;

public class RequestReceiverDemo {
	
	public static void main(String[] args) throws InterruptedException {
		final RequestReceiver receiver = new RequestReceiver();
		receiver.initialize();
		receiver.receive();
		receiver.destroy();
	}
}


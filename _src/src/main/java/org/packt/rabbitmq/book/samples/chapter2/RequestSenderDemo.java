package org.packt.rabbitmq.book.samples.chapter2;

public class RequestSenderDemo {

	private static final String REQUEST_QUEUE = "request_queue";
	
	public static void sendToRequestReplyQueue() {
		Sender sender = new Sender();
		sender.initialize();
		sender.sendRequest(REQUEST_QUEUE, "Test message.", "MSG1");
		// String result = sender.waitForResponse("MSG1");
		sender.destroy();
//		return result;
	}

	public static void main(String[] args) {
		sendToRequestReplyQueue();
	}
	
}

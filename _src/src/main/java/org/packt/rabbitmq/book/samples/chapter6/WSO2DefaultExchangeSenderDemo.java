package org.packt.rabbitmq.book.samples.chapter6;

public class WSO2DefaultExchangeSenderDemo {
	
	public static void sendToDefaultExchange() {
		WSO2Sender sender = new WSO2Sender();
		sender.initialize();
		
		String soapMessage = "<soapenv:Envelope " + 
                "xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +  
                "<soapenv:Header/>\n" +
                "<soapenv:Body>\n" +
                "  <p:test xmlns:p=\"http://test.service.sample.com\">\n" + 
                "     <in>" + "sample message" + "</in>\n" +
                "  </p:test>\n" +
                "</soapenv:Body>\n" +
                "</soapenv:Envelope>";
		
		sender.send(soapMessage);
		sender.destroy();
	}

	public static void main(String[] args) {
		sendToDefaultExchange();
	}

}

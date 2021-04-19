package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import javax.xml.transform.Source;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.test.client.MockWebServiceServer;
import org.springframework.ws.test.client.RequestMatchers;
import org.springframework.ws.test.client.ResponseCreators;
import org.springframework.xml.transform.ResourceSource;

class SpringWsTestReproTest {

	@Test
	void Should_Work() throws IOException {
		final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(ExampleRequest.class, ExampleResponse.class);

		final WebServiceTemplate webServiceTemplate = new WebServiceTemplate(marshaller);
		webServiceTemplate.setDefaultUri("https://example.org");

		final MockWebServiceServer mockWebServiceServer = MockWebServiceServer.createServer(webServiceTemplate);

		final Source expectedSoapRequest = new ResourceSource(new ClassPathResource("expected_request.xml"));
		final Source soapResponse = new ResourceSource(new ClassPathResource("response.xml"));
		mockWebServiceServer.expect(RequestMatchers.soapEnvelope(expectedSoapRequest))
				.andRespond(ResponseCreators.withSoapEnvelope(soapResponse));

		final ExampleRequest request = new ExampleRequest();
		request.setMyData("123456");
		final ExampleResponse response = (ExampleResponse) webServiceTemplate.marshalSendAndReceive(request);

		assertEquals("654321", response.getMyData());
	}
}

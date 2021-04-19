package org.example;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ExampleRequest")
public class ExampleRequest {

	private String myData;

	public String getMyData() {
		return myData;
	}

	public void setMyData(final String myData) {
		this.myData = myData;
	}
}

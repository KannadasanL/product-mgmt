package com.learning.productapi.util;


public class ProductCustomMessage{
	
	private String errorMessage;
	
	public ProductCustomMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}

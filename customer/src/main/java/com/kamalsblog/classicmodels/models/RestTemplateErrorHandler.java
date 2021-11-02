package com.kamalsblog.classicmodels.models;

import java.io.IOException;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
public class RestTemplateErrorHandler implements ResponseErrorHandler {
	  @Override
	  public void handleError(ClientHttpResponse response) throws IOException {
	  }

	  @Override
	  public boolean hasError(ClientHttpResponse response) throws IOException {
		  return false;
	  }

	}
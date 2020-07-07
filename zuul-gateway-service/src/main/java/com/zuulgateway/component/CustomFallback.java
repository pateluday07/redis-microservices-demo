package com.zuulgateway.component;

import com.netflix.hystrix.exception.HystrixTimeoutException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

@Component
public class CustomFallback implements FallbackProvider {

  private static final String DEFAULT_MESSAGE = " Is Not Available At The Moment Please Try After A While";

  @Override
  public String getRoute() {
    return "*";
  }

  @Override
  public ClientHttpResponse fallbackResponse(String route, final Throwable cause) {
    if (cause instanceof HystrixTimeoutException) {
      return response(HttpStatus.GATEWAY_TIMEOUT, route);
    } else {
      return response(HttpStatus.INTERNAL_SERVER_ERROR, route);
    }
  }

  private ClientHttpResponse response(final HttpStatus status, final String route) {
    return new ClientHttpResponse() {
      @Override
      public HttpStatus getStatusCode() {
        return status;
      }

      @Override
      public int getRawStatusCode() {
        return status.value();
      }

      @Override
      public String getStatusText() {
        return status.getReasonPhrase();
      }

      @Override
      public void close() {
      }

      @Override
      public InputStream getBody() {
        return new ByteArrayInputStream(route.concat(DEFAULT_MESSAGE).getBytes());
      }

      @Override
      public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
      }
    };
  }
}

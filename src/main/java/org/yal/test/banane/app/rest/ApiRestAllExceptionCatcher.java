package org.yal.test.banane.app.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class ApiRestAllExceptionCatcher extends ResponseEntityExceptionHandler
{
  private final static Logger logger = LoggerFactory.getLogger(ApiRestAllExceptionCatcher.class);
  
  @ExceptionHandler
  public ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request)
  {
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    // return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(),
    // HttpStatus.CONFLICT, request);
    try
    {
      logger.error("erreur generique " + request.getContextPath() + " - " + request.getDescription(false), ex);
      return new ResponseEntity<>(new ObjectMapper().writeValueAsString(ex), headers, HttpStatus.CONFLICT);
    }
    catch (Exception e)
    {
      return new ResponseEntity<>("{\"messageErreur\":\"erreur generique trouvée\"}", headers, HttpStatus.CONFLICT);
    }
  }
}

package com.skymind.backend.exception;

public class NoFlightsFoundException extends RuntimeException{
  public   NoFlightsFoundException(String message){
        super(message);
    }
}

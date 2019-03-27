package com.etteplanmore.assignment.store;

public class StoreException extends RuntimeException {

  public StoreException(String message) {
    super(message);
  }

  public StoreException(Throwable cause) {
    super(cause);
  }

}

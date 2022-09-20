package org.ardvark.man3;

public class MismatchedTokenException extends RuntimeException {
  public MismatchedTokenException() {
  }

  public MismatchedTokenException(String message) {
    super(message);
  }

  public MismatchedTokenException(String message, Throwable cause) {
    super(message, cause);
  }

  public MismatchedTokenException(Throwable cause) {
    super(cause);
  }

  public MismatchedTokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}

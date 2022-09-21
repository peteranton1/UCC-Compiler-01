package org.ardvark._00_manual .man3;

public class RecognitionException extends RuntimeException {
  public RecognitionException() {
  }

  public RecognitionException(String message) {
    super(message);
  }

  public RecognitionException(String message, Throwable cause) {
    super(message, cause);
  }

  public RecognitionException(Throwable cause) {
    super(cause);
  }

  public RecognitionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}

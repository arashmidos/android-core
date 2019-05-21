package app.arash.androidcore.data.event;

import app.arash.androidcore.data.constant.StatusCodes;
import java.io.Serializable;

public class Event implements Serializable {

  protected String message;
  protected StatusCodes statusCode;

  public Event(StatusCodes statusCode, String message) {
    this.statusCode = statusCode;
    this.message = message;
  }

  public Event() {
  }

  public StatusCodes getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(StatusCodes statusCode) {
    this.statusCode = statusCode;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}

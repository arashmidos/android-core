package app.arash.androidcore.data.event;


import app.arash.androidcore.data.constant.StatusCodes;

public class ErrorEvent extends Event {

  public ErrorEvent(String message, StatusCodes statusCode) {
    this.message = message;
    this.statusCode = statusCode;
  }

  public ErrorEvent(StatusCodes statusCode) {
    this.statusCode = statusCode;
  }
}

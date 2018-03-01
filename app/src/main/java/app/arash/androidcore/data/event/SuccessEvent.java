package app.arash.androidcore.data.event;


import app.arash.androidcore.data.constant.StatusCodes;

public class SuccessEvent extends Event {

  public SuccessEvent(String message, StatusCodes statusCode) {
    this.message = message;
    this.statusCode = statusCode;
  }

  public SuccessEvent(StatusCodes statusCode) {
    this.statusCode = statusCode;
  }
}

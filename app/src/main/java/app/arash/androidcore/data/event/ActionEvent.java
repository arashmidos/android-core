package app.arash.androidcore.data.event;

import app.arash.androidcore.data.constant.StatusCodes;

public class ActionEvent extends Event {

  public ActionEvent(StatusCodes statusCodes) {
    this.statusCode = statusCodes;
  }
}

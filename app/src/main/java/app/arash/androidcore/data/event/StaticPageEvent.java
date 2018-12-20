package app.arash.androidcore.data.event;


import app.arash.androidcore.data.constant.StatusCodes;
import app.arash.androidcore.data.entity.StaticResponse;

public class StaticPageEvent extends Event {

  private final StaticResponse subResponse;

  public StaticPageEvent(StatusCodes statusCodes, StaticResponse subResponse) {
    this.statusCode = statusCodes;
    this.subResponse = subResponse;
  }

  public StaticResponse getSubResponse() {
    return subResponse;
  }
}
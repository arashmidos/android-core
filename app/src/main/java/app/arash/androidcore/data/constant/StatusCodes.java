package app.arash.androidcore.data.constant;

import android.content.Context;
import app.arash.androidcore.MedicApplication;
import app.arash.androidcore.util.Empty;

/**
 * @author Arash
 */
public enum StatusCodes {
  SUCCESS(200),
  SMS_SUCCESS(209),
  SIGNUP_SUCCESS(201),
  LOGIN_SUCCESS(202),
  SIGNIN_SUCCESS(203),
  INVALID_USERNAME(204),
  INVALID_PASSWORD(205),
  ORDER_SUCCESSFULL(206),
  DUPLICATE_USER(207),
  VERIFY_CODE_SUCCESS(208),
  AUTHENTICATE_ERROR(401),
  NO_NETWORK(1001),
  INVALID_DATA(1002),
  NETWORK_ERROR(1003),
  DATA_STORE_ERROR(1004),
  NO_DATA_ERROR(1005),
  UPDATE(1006),
  SERVER_ERROR(1100),
  PERMISSION_DENIED(2001),
  NEW_GPS_LOCATION(2002),
  ACTION_ADD_ORDER(901),
  ACTION_ADD_PAYMENT(902),
  ACTION_EXIT_VISIT(903),
  ACTION_START_CAMERA(904),
  ACTION_REFRESH_DATA(905),
  ACTION_FINISH_TRANSFER(906),
  ACTION_CANCEL_TRANSFER(907),
  ACTION_PLAYER_FAILED(908);

  private int statusCode;
  private int message;

  StatusCodes(int statusCode) {
    this.statusCode = statusCode;
  }

  public static String getDisplayTitle(Context context, int status) {
    StatusCodes foundStatus = findByStatusCode(status);
    if (Empty.isNotEmpty(foundStatus)) {
      return context.getString(foundStatus.getMessage());
    } else {
      return "";
    }
  }

  private static StatusCodes findByStatusCode(int statusCode) {
    for (StatusCodes status : StatusCodes.values()) {
      if (status.getId() == statusCode) {
        return status;
      }
    }
    return null;
  }

  public int getId() {
    return statusCode;
  }

  public void setId(int statusCode) {
    this.statusCode = statusCode;
  }

  public int getMessage() {
    return message;
  }

  public void setMessage(int message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return MedicApplication.getInstance().getString(message);
  }
}

package app.arash.androidcore.data.entity;

import app.arash.androidcore.R;

/**
 * Created by Arash
 */
public enum MeasureDetailType {
  WEIGHT(10, R.string.weight, R.string.weight_unit),
  BLOOD_PRESSURE(11, R.string.blood_pressure, R.string.blood_pressure_unit),
  HEART_BEAT(12, R.string.heart_beat, R.string.heart_beat_unit),
  BLOOD_GLUCOSE(13, R.string.blood_glucose, R.string.blood_glucose_unit),
  BODY_TEMPERATURE(14, R.string.body_temperature, R.string.body_temperature_unit),
  DAILY_WALK(15, R.string.daily_walk, R.string.daily_walk_unit),
  CALORIES_CONSUMED(16, R.string.calories_consumed, R.string.calories_consumed_unit),
  TRIGLYCERIDE(17, R.string.triglyceride, R.string.blood_glucose_unit),
  COLESTEROL_LDL(18, R.string.colesterol_ldl, R.string.blood_glucose_unit),
  COLESTEROL_HDL(19, R.string.colesterol_hdl, R.string.blood_glucose_unit),
  CALORIES_RECIEVED(20, R.string.calories_recieved, R.string.calories_consumed_unit);


  private final int type;
  private final int unit;
  private int id;

  MeasureDetailType(int id, int type, int unit) {
    this.id = id;
    this.unit = unit;
    this.type = type;
  }

  public static MeasureDetailType getByTypeId(int id) {
    MeasureDetailType found = null;
    for (MeasureDetailType visitType : MeasureDetailType.values()) {
      if (visitType.id == id) {
        found = visitType;
      }
    }
    return found;
  }

  public int getType() {
    return type;
  }

  public int getUnit() {
    return unit;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}

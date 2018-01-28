package app.arash.androidcore.data.entity;

import app.arash.androidcore.R;

/**
 * Created by Arash
 */
public enum MeasureDetailType {
  WEIGHT(10L, R.string.weight, R.string.weight_unit),
  BLOOD_PRESSURE(11L, R.string.blood_pressure, R.string.blood_pressure_unit),
  HEART_BEAT(12L, R.string.heart_beat, R.string.heart_beat_unit),
  BLOOD_GLUCOSE(13L, R.string.blood_glucose, R.string.blood_glucose_unit),
  BODY_TEMPERATURE(14L, R.string.body_temperature, R.string.body_temperature_unit),
  DAILY_WALK(15L, R.string.daily_walk, R.string.daily_walk_unit),
  CALORIES_CONSUMED(16L, R.string.calories_consumed, R.string.calories_consumed_unit),
  TRIGLYCERIDE(17L, R.string.triglyceride, R.string.blood_glucose_unit),
  COLESTEROL_LDL(18L, R.string.colesterol_ldl, R.string.blood_glucose_unit),
  COLESTEROL_HDL(19L, R.string.colesterol_hdl, R.string.blood_glucose_unit),
  CALORIES_RECIEVED(20L, R.string.calories_recieved, R.string.calories_consumed_unit);


  private final int type;
  private final int unit;
  private long id;

  MeasureDetailType(long value, int type, int unit) {
    this.id = id;
    this.unit = unit;
    this.type = type;
  }

  public static MeasureDetailType getByTypeId(long id) {
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

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }
}

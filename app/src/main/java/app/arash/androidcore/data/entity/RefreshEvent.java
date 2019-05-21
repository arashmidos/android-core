package app.arash.androidcore.data.entity;

/**
 * Created by arash on 1/28/18.
 */

public class RefreshEvent {

  private Drug drug;

  public RefreshEvent(Drug drug) {
    this.drug = drug;
  }

  public RefreshEvent() {
  }

  public Drug getDrug() {
    return drug;
  }

  public void setDrug(Drug drug) {
    this.drug = drug;
  }
}

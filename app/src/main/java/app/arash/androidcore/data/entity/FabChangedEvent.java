package app.arash.androidcore.data.entity;

/**
 * Created by arash on 2/1/18.
 */

public class FabChangedEvent {

  private FabStatus fabStatus;

  public FabChangedEvent() {
  }

  public FabChangedEvent(FabStatus fabStatus) {
    this.fabStatus = fabStatus;
  }

  public FabStatus getFabStatus() {
    return fabStatus;
  }

  public void setFabStatus(FabStatus fabStatus) {
    this.fabStatus = fabStatus;
  }

  public enum FabStatus {
    EXPANDED, COLLAPSED
  }
}

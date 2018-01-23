package app.arash.androidcore.data.entity;

import android.support.v7.widget.LinearLayoutManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shkbhbb on 1/23/18.
 */

public class Drug {

  private String drugName;
  private boolean isStared;
  private boolean hasAlarmSet;

  public Drug(String drugName, boolean isStared, boolean hasAlarmSet) {
    this.drugName = drugName;
    this.isStared = isStared;
    this.hasAlarmSet = hasAlarmSet;
  }

  public String getDrugName() {
    return drugName;
  }

  public void setDrugName(String drugName) {
    this.drugName = drugName;
  }

  public boolean isStared() {
    return isStared;
  }

  public void setStared(boolean stared) {
    isStared = stared;
  }

  public boolean isHasAlarmSet() {
    return hasAlarmSet;
  }

  public void setHasAlarmSet(boolean hasAlarmSet) {
    this.hasAlarmSet = hasAlarmSet;
  }

  public static List<Drug> getDrugList() {
    List<Drug> drugs = new ArrayList<>();
    drugs.add(new Drug("آ.اس.آ", true, false));
    drugs.add(new Drug("آپروتینین", false, true));
    drugs.add(new Drug("آتراکوریوم", false, false));
    drugs.add(new Drug("آتروپین", false, false));
    drugs.add(new Drug("آتورواستاتین", true, true));
    drugs.add(new Drug("آتروپین", true, true));
    drugs.add(new Drug("آتنولول", false, false));
    drugs.add(new Drug("آزاتیوپرین", true, false));
    drugs.add(new Drug("آزیترومایسین", true, true));
    return drugs;
  }

  public static List<Drug> getFavoriteDrugList() {
    List<Drug> drugs = new ArrayList<>();
    drugs.add(new Drug("آ.اس.آ", true, false));
    drugs.add(new Drug("آپروتینین", true, true));
    drugs.add(new Drug("آتراکوریوم", true, false));
    drugs.add(new Drug("آتروپین", true, false));
    drugs.add(new Drug("آتورواستاتین", true, true));
    drugs.add(new Drug("آتروپین", true, true));
    drugs.add(new Drug("آتنولول", true, false));
    drugs.add(new Drug("آزاتیوپرین", true, false));
    drugs.add(new Drug("آزیترومایسین", true, true));
    return drugs;
  }
}

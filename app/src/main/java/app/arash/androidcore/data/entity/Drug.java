package app.arash.androidcore.data.entity;

import android.support.v7.widget.LinearLayoutManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shkbhbb on 1/23/18.
 */

public class Drug implements Serializable {

  private String drugName;
  private String usage;
  private String notUsage;
  private String sideEffect;
  private String drugConflict;
  private String pregnancy;
  private String instruction;
  private String other;
  private boolean isStared;
  private boolean hasAlarmSet;


  public Drug(String drugName, String usage, String notUsage, String sideEffect,
      String drugConflict, String pregnancy, String instruction, String other, boolean isStared,
      boolean hasAlarmSet) {
    this.drugName = drugName;
    this.usage = usage;
    this.notUsage = notUsage;
    this.sideEffect = sideEffect;
    this.drugConflict = drugConflict;
    this.pregnancy = pregnancy;
    this.instruction = instruction;
    this.other = other;
    this.isStared = isStared;
    this.hasAlarmSet = hasAlarmSet;
  }

  public String getUsage() {
    return usage;
  }

  public void setUsage(String usage) {
    this.usage = usage;
  }

  public String getNotUsage() {
    return notUsage;
  }

  public void setNotUsage(String notUsage) {
    this.notUsage = notUsage;
  }

  public String getSideEffect() {
    return sideEffect;
  }

  public void setSideEffect(String sideEffect) {
    this.sideEffect = sideEffect;
  }

  public String getDrugConflict() {
    return drugConflict;
  }

  public void setDrugConflict(String drugConflict) {
    this.drugConflict = drugConflict;
  }

  public String getPregnancy() {
    return pregnancy;
  }

  public void setPregnancy(String pregnancy) {
    this.pregnancy = pregnancy;
  }

  public String getInstruction() {
    return instruction;
  }

  public void setInstruction(String instruction) {
    this.instruction = instruction;
  }

  public String getOther() {
    return other;
  }

  public void setOther(String other) {
    this.other = other;
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
    drugs.add(new Drug("آ.اس.آ", "usage", "not usage", "side effect", "drug conflict", "pregency",
        "instruction", "other", true, false));
    drugs
        .add(new Drug("آپروتینین", "usage", "not usage", "side effect", "drug conflict", "pregency",
            "instruction", "other", true, false));
    drugs.add(
        new Drug("آتراکوریوم", "usage", "not usage", "side effect", "drug conflict", "pregency",
            "instruction", "other", true, false));
    drugs.add(new Drug("آتروپین", "usage", "not usage", "side effect", "drug conflict", "pregency",
        "instruction", "other", true, false));
    drugs.add(
        new Drug("آتورواستاتین", "usage", "not usage", "side effect", "drug conflict", "pregency",
            "instruction", "other", true, false));
    drugs.add(new Drug("آتروپین", "usage", "not usage", "side effect", "drug conflict", "pregency",
        "instruction", "other", true, false));
    drugs.add(new Drug("آتنولول", "usage", "not usage", "side effect", "drug conflict", "pregency",
        "instruction", "other", true, false));
    drugs.add(
        new Drug("آزاتیوپرین", "usage", "not usage", "side effect", "drug conflict", "pregency",
            "instruction", "other", true, false));
    drugs.add(
        new Drug("آزیترومایسین", "usage", "not usage", "side effect", "drug conflict", "pregency",
            "instruction", "other", true, false));
    return drugs;
  }

  public static List<Drug> getFavoriteDrugList() {
    List<Drug> drugs = new ArrayList<>();
    drugs.add(new Drug("آ.اس.آ", "usage", "not usage", "side effect", "drug conflict", "pregency",
        "instruction", "other", true, false));
    drugs
        .add(new Drug("آپروتینین", "usage", "not usage", "side effect", "drug conflict", "pregency",
            "instruction", "other", true, false));
    drugs.add(
        new Drug("آتراکوریوم", "usage", "not usage", "side effect", "drug conflict", "pregency",
            "instruction", "other", true, false));
    drugs.add(new Drug("آتروپین", "usage", "not usage", "side effect", "drug conflict", "pregency",
        "instruction", "other", true, false));
    drugs.add(
        new Drug("آتورواستاتین", "usage", "not usage", "side effect", "drug conflict", "pregency",
            "instruction", "other", true, false));
    drugs.add(new Drug("آتروپین", "usage", "not usage", "side effect", "drug conflict", "pregency",
        "instruction", "other", true, false));
    drugs.add(new Drug("آتنولول", "usage", "not usage", "side effect", "drug conflict", "pregency",
        "instruction", "other", true, false));
    drugs.add(
        new Drug("آزاتیوپرین", "usage", "not usage", "side effect", "drug conflict", "pregency",
            "instruction", "other", true, false));
    drugs.add(
        new Drug("آزیترومایسین", "usage", "not usage", "side effect", "drug conflict", "pregency",
            "instruction", "other", true, false));
    return drugs;
  }
}

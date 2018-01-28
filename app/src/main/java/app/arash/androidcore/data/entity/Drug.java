package app.arash.androidcore.data.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shkbhbb on 1/23/18.
 */

public class Drug extends BaseEntity<Long> implements Serializable {

  public static final String TABLE_NAME = "drug";

  public static final String COL_ID = "_id";
  public static final String COL_CATEGORY_FA = "category_fa";
  public static final String COL_CATEGORY_EN = "category_en";
  public static final String COL_NAME_FA = "name_fa";
  public static final String COL_NAME_EN = "name_en";
  public static final String COL_USAGE = "drug_usage";//5
  public static final String COL_MECHANISM = "mechanism";
  public static final String COL_FARM = "farm";
  public static final String COL_NOT_USAGE = "not_usage";
  public static final String COL_SIDE_EFFECT = "side_effect";
  public static final String COL_CONFLICT = "drug_conflict";//10
  public static final String COL_CAUTIONS = "cautions";
  public static final String COL_TOSIE = "tosie";
  public static final String COL_ASHKAL = "ashkal";
  public static final String COL_STAR = "star";
  public static final String COL_ALARM = "has_alarm";//15
  public static final String COL_MY = "my_drug";//16

  private Long id;
  private String categoryFa;
  private String categoryEn;
  private String nameFa;
  private String nameEn;
  private String usage;//5
  private String mechanism;
  private String farm;
  private String notUsage;
  private String sideEffect;
  private String drugConflict;//10
  private String pregnancy;
  private String cautions;
  private String instruction;//tosie
  private String ashkal;
  private String other;
  private boolean isStared;
  private boolean hasAlarmSet;
  private boolean isMyDrug;

  public Drug(String nameFa, String usage, String notUsage, String sideEffect,
      String drugConflict, String pregnancy, String instruction, String other, boolean isStared,
      boolean hasAlarmSet) {
    this.nameFa = nameFa;
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

  public Drug() {
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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCategoryFa() {
    return categoryFa;
  }

  public void setCategoryFa(String categoryFa) {
    this.categoryFa = categoryFa;
  }

  public String getCategoryEn() {
    return categoryEn;
  }

  public void setCategoryEn(String categoryEn) {
    this.categoryEn = categoryEn;
  }

  public String getNameEn() {
    return nameEn;
  }

  public void setNameEn(String nameEn) {
    this.nameEn = nameEn;
  }

  public String getMechanism() {
    return mechanism;
  }

  public void setMechanism(String mechanism) {
    this.mechanism = mechanism;
  }

  public String getFarm() {
    return farm;
  }

  public void setFarm(String farm) {
    this.farm = farm;
  }

  public String getCautions() {
    return cautions;
  }

  public void setCautions(String cautions) {
    this.cautions = cautions;
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

  public String getNameFa() {
    return nameFa;
  }

  public void setNameFa(String nameFa) {
    this.nameFa = nameFa;
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

  @Override
  public Long getPrimaryKey() {
    return id;
  }

  public String getAshkal() {
    return ashkal;
  }

  public void setAshkal(String ashkal) {
    this.ashkal = ashkal;
  }

  public boolean isMyDrug() {
    return isMyDrug;
  }

  public void setMyDrug(boolean myDrug) {
    isMyDrug = myDrug;
  }
}

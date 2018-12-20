package app.arash.androidcore.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactPage {

  @SerializedName("about_us")
  @Expose
  private String aboutUs;
  @SerializedName("contact_us_text")
  @Expose
  private String contactUsText;
  @SerializedName("contact_us_telephone")
  @Expose
  private String contactUsTelephone;
  @SerializedName("contact_us_email")
  @Expose
  private String contactUsEmail;

  public String getAboutUs() {
    return aboutUs;
  }

  public void setAboutUs(String aboutUs) {
    this.aboutUs = aboutUs;
  }

  public String getContactUsText() {
    return contactUsText;
  }

  public void setContactUsText(String contactUsText) {
    this.contactUsText = contactUsText;
  }

  public String getContactUsTelephone() {
    return contactUsTelephone;
  }

  public void setContactUsTelephone(String contactUsTelephone) {
    this.contactUsTelephone = contactUsTelephone;
  }

  public String getContactUsEmail() {
    return contactUsEmail;
  }

  public void setContactUsEmail(String contactUsEmail) {
    this.contactUsEmail = contactUsEmail;
  }

}

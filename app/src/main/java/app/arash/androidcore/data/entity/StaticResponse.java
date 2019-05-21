package app.arash.androidcore.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StaticResponse {

  @SerializedName("contact_page")
  @Expose
  private ContactPage contactPage;
  @SerializedName("send_code_page")
  @Expose
  private SendCodePage sendCodePage;

  public ContactPage getContactPage() {
    return contactPage;
  }

  public void setContactPage(ContactPage contactPage) {
    this.contactPage = contactPage;
  }

  public SendCodePage getSendCodePage() {
    return sendCodePage;
  }

  public void setSendCodePage(SendCodePage sendCodePage) {
    this.sendCodePage = sendCodePage;
  }

}

package app.arash.androidcore.service;

import app.arash.androidcore.MedicApplication;
import app.arash.androidcore.data.constant.StatusCodes;
import app.arash.androidcore.data.entity.Category;
import app.arash.androidcore.data.entity.SendSmsRequest;
import app.arash.androidcore.data.entity.TokenResponse;
import app.arash.androidcore.data.entity.VerifyCodeRequest;
import app.arash.androidcore.data.entity.Video;
import app.arash.androidcore.data.event.ActionEvent;
import app.arash.androidcore.data.event.CategoryEvent;
import app.arash.androidcore.data.event.ErrorEvent;
import app.arash.androidcore.data.event.VideoEvent;
import app.arash.androidcore.util.Empty;
import app.arash.androidcore.util.NetworkUtil;
import app.arash.androidcore.util.PreferenceHelper;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by arash on 2/19/18.
 */

public class VideoService {


  public void getCategoryList() {
    if (!NetworkUtil.isNetworkAvailable(MedicApplication.getInstance())) {
      EventBus.getDefault().post(new ErrorEvent(StatusCodes.NO_NETWORK));
      return;
    }
    RestService restService = ServiceGenerator.createService(RestService.class);

    Call<List<Category>> call = restService.getCategoryList();

    call.enqueue(new Callback<List<Category>>() {
      @Override
      public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
        if (response.isSuccessful()) {
          List<Category> categoryList = response.body();
          if (categoryList != null && categoryList.size() > 0) {

            EventBus.getDefault().post(new CategoryEvent(categoryList));
          } else {
            EventBus.getDefault().post(new ErrorEvent(StatusCodes.NO_DATA_ERROR));
          }
        } else {
          if (response.code() == 401 || response.code() == 403) {
            EventBus.getDefault().post(new ErrorEvent(StatusCodes.AUTHENTICATE_ERROR));
          } else {
            EventBus.getDefault().post(new ErrorEvent(StatusCodes.SERVER_ERROR));
          }
        }
      }

      @Override
      public void onFailure(Call<List<Category>> call, Throwable t) {
        EventBus.getDefault().post(new ErrorEvent(StatusCodes.NETWORK_ERROR));
      }
    });
  }

  public void sendSms(String phone) {
    if (!NetworkUtil.isNetworkAvailable(MedicApplication.getInstance())) {
      EventBus.getDefault().post(new ErrorEvent(StatusCodes.NO_NETWORK));
      return;
    }
    RestService restService = ServiceGenerator.createService(RestService.class);

    Call<String> call = restService.sendSms(new SendSmsRequest(phone));

    call.enqueue(new Callback<String>() {
      @Override
      public void onResponse(Call<String> call, Response<String> response) {
        if (response.code() == 204) {
          EventBus.getDefault().post(new ActionEvent(StatusCodes.SMS_SUCCESS));
        } else {
          EventBus.getDefault().post(new ErrorEvent(StatusCodes.SERVER_ERROR));
        }
      }

      @Override
      public void onFailure(Call<String> call, Throwable t) {
        EventBus.getDefault().post(new ErrorEvent(StatusCodes.NETWORK_ERROR));
      }
    });
  }

  public void verifyCode(String phone, String code) {
    if (!NetworkUtil.isNetworkAvailable(MedicApplication.getInstance())) {
      EventBus.getDefault().post(new ErrorEvent(StatusCodes.NO_NETWORK));
      return;
    }
    RestService restService = ServiceGenerator.createService(RestService.class);
//TODO:
//    phone = phone.replace("0", "1");
    Call<TokenResponse> call = restService.verifyCode(new VerifyCodeRequest(phone,code));
//    Call<TokenResponse> call = restService.testGetToken(new VerifyCodeRequest(phone, code));

    call.enqueue(new Callback<TokenResponse>() {
      @Override
      public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
        if (response.isSuccessful()) {
          TokenResponse tokenResponse = response.body();
          String accessToken = tokenResponse.getAccessToken();
          if (Empty.isNotEmpty(accessToken)) {
            PreferenceHelper.setToken(accessToken);
            EventBus.getDefault().post(new ActionEvent(StatusCodes.SUCCESS));
          } else {
            EventBus.getDefault().post(new ErrorEvent(StatusCodes.AUTHENTICATE_ERROR));
          }
        } else {
          EventBus.getDefault().post(new ErrorEvent(StatusCodes.SERVER_ERROR));
        }
      }

      @Override
      public void onFailure(Call<TokenResponse> call, Throwable t) {
        EventBus.getDefault().post(new ErrorEvent(StatusCodes.NETWORK_ERROR));
      }
    });
  }

  public void getVideoList(int categoryId) {
    getVideoList(categoryId, null);
  }

  public void getVideoList(Integer categoryId, Integer count) {
    if (!NetworkUtil.isNetworkAvailable(MedicApplication.getInstance())) {
      EventBus.getDefault().post(new ErrorEvent(StatusCodes.NO_NETWORK));
      return;
    }
    RestService restService = ServiceGenerator.createService(RestService.class);

    Call<List<Video>> call = restService.getVideoList(categoryId, count);

    call.enqueue(new Callback<List<Video>>() {
      @Override
      public void onResponse(Call<List<Video>> call, Response<List<Video>> response) {
        if (response.isSuccessful()) {
          List<Video> videoList = response.body();
          if (videoList != null && videoList.size() > 0) {

            EventBus.getDefault().post(new VideoEvent(videoList));
          } else {
            EventBus.getDefault().post(new ErrorEvent(StatusCodes.NO_DATA_ERROR));
          }
        } else {
          if (response.code() == 401 || response.code() == 403) {
            EventBus.getDefault().post(new ErrorEvent(StatusCodes.AUTHENTICATE_ERROR));
          } else {
            EventBus.getDefault().post(new ErrorEvent(StatusCodes.SERVER_ERROR));
          }
        }
      }

      @Override
      public void onFailure(Call<List<Video>> call, Throwable t) {
        EventBus.getDefault().post(new ErrorEvent(StatusCodes.NETWORK_ERROR));
      }
    });
  }
}

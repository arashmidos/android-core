package app.arash.androidcore.service;

import android.support.annotation.NonNull;
import app.arash.androidcore.MedicApplication;
import app.arash.androidcore.data.constant.StatusCodes;
import app.arash.androidcore.data.entity.Category;
import app.arash.androidcore.data.entity.GeneralResponse;
import app.arash.androidcore.data.entity.SendSmsRequest;
import app.arash.androidcore.data.entity.StaticResponse;
import app.arash.androidcore.data.entity.SubscriptionResponse;
import app.arash.androidcore.data.entity.TokenResponse;
import app.arash.androidcore.data.entity.VerifyCodeRequest;
import app.arash.androidcore.data.entity.Video;
import app.arash.androidcore.data.event.ActionEvent;
import app.arash.androidcore.data.event.CategoryEvent;
import app.arash.androidcore.data.event.ErrorEvent;
import app.arash.androidcore.data.event.StaticPageEvent;
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
      public void onResponse(@NonNull Call<List<Category>> call,
          @NonNull Response<List<Category>> response) {
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

    Call<GeneralResponse> call = restService.sendSms(new SendSmsRequest(phone));

    call.enqueue(new Callback<GeneralResponse>() {
      @Override
      public void onResponse(@NonNull Call<GeneralResponse> call,
          Response<GeneralResponse> response) {
        if (response.code() == 204 || response.code() == 200) {
          EventBus.getDefault().post(new ActionEvent(StatusCodes.SMS_SUCCESS));
        } else {
          EventBus.getDefault().post(new ErrorEvent(StatusCodes.SERVER_ERROR));
        }
      }

      @Override
      public void onFailure(@NonNull Call<GeneralResponse> call, @NonNull Throwable t) {
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

//    phone = phone.replace("0", "1");
    Call<TokenResponse> call = restService.verifyCode(new VerifyCodeRequest(phone, code));
//    Call<TokenResponse> call = restService.testGetToken(new VerifyCodeRequest(phone, code));

    call.enqueue(new Callback<TokenResponse>() {
      @Override
      public void onResponse(@NonNull Call<TokenResponse> call,
          @NonNull Response<TokenResponse> response) {
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
      public void onResponse(@NonNull Call<List<Video>> call,
          @NonNull Response<List<Video>> response) {
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

  public void unsubscribe() {
    if (!NetworkUtil.isNetworkAvailable(MedicApplication.getInstance())) {
      EventBus.getDefault().post(new ErrorEvent(StatusCodes.NO_NETWORK));
      return;
    }
    RestService restService = ServiceGenerator.createService(RestService.class);

    Call<Void> call = restService.unsubscribe();

    call.enqueue(new Callback<Void>() {
      @Override
      public void onResponse(Call<Void> call, Response<Void> response) {
        if (response.isSuccessful()) {
          EventBus.getDefault().post(new ActionEvent(StatusCodes.SUCCESS));
        } else {
          EventBus.getDefault().post(new ErrorEvent(StatusCodes.AUTHENTICATE_ERROR));
        }
      }

      @Override
      public void onFailure(Call<Void> call, Throwable t) {
        EventBus.getDefault().post(new ErrorEvent(StatusCodes.NETWORK_ERROR));
      }
    });
  }

  public void checkUserSubscription() {
    if (!NetworkUtil.isNetworkAvailable(MedicApplication.getInstance())) {
      EventBus.getDefault().post(new ErrorEvent(StatusCodes.NO_NETWORK));
      return;
    }
    RestService restService = ServiceGenerator.createService(RestService.class);

    Call<SubscriptionResponse> call = restService
        .checkSubscription(PreferenceHelper.getPhoneNumber());

    call.enqueue(new Callback<SubscriptionResponse>() {
      @Override
      public void onResponse(Call<SubscriptionResponse> call,
          Response<SubscriptionResponse> response) {
        if (response.isSuccessful()) {
          SubscriptionResponse subResponse = response.body();
          if (subResponse != null && subResponse.getIsUserPartOfMedic() != null
              && subResponse.getIsUserPartOfMedic() == 1) {
            EventBus.getDefault().post(new ActionEvent(StatusCodes.SUCCESS));
          } else {
            EventBus.getDefault().post(new ErrorEvent(StatusCodes.AUTHENTICATE_ERROR));
          }
        } else {
          EventBus.getDefault().post(new ErrorEvent(StatusCodes.AUTHENTICATE_ERROR));
        }
      }

      @Override
      public void onFailure(Call<SubscriptionResponse> call, Throwable t) {
        EventBus.getDefault().post(new ErrorEvent(StatusCodes.NETWORK_ERROR));
      }
    });
  }


  public void getStatics() {
    if (!NetworkUtil.isNetworkAvailable(MedicApplication.getInstance())) {
      EventBus.getDefault().post(new ErrorEvent(StatusCodes.NO_NETWORK));
      return;
    }
    RestService restService = ServiceGenerator.createService(RestService.class);

    Call<StaticResponse> call = restService.statics();

    call.enqueue(new Callback<StaticResponse>() {
      @Override
      public void onResponse(@NonNull Call<StaticResponse> call,
          @NonNull Response<StaticResponse> response) {
        if (response.isSuccessful()) {
          StaticResponse subResponse = response.body();
          if (subResponse != null) {
            EventBus.getDefault().post(new StaticPageEvent(StatusCodes.SUCCESS, subResponse));
          } else {
            EventBus.getDefault().post(new ErrorEvent(StatusCodes.AUTHENTICATE_ERROR));
          }
        } else {
          EventBus.getDefault().post(new ErrorEvent(StatusCodes.AUTHENTICATE_ERROR));
        }
      }

      @Override
      public void onFailure(@NonNull Call<StaticResponse> call, @NonNull Throwable t) {
        EventBus.getDefault().post(new ErrorEvent(StatusCodes.NETWORK_ERROR));
      }
    });
  }

}

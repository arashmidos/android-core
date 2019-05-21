package app.arash.androidcore.service;

import app.arash.androidcore.data.entity.Category;
import app.arash.androidcore.data.entity.GeneralResponse;
import app.arash.androidcore.data.entity.SendSmsRequest;
import app.arash.androidcore.data.entity.StaticResponse;
import app.arash.androidcore.data.entity.SubscriptionResponse;
import app.arash.androidcore.data.entity.TokenResponse;
import app.arash.androidcore.data.entity.VerifyCodeRequest;
import app.arash.androidcore.data.entity.Video;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Arash on 2018-02-16.
 */

public interface RestService {

  @POST("medic/send_verification_code")
  Call<GeneralResponse> sendSms(@Body SendSmsRequest sendSmsRequest);

  @POST("medic/verify_token")
  Call<TokenResponse> verifyCode(@Body VerifyCodeRequest verifyCodeRequest);

  @POST("medic/test_get_token")
  Call<TokenResponse> testGetToken(@Body VerifyCodeRequest verifyCodeRequest);

  @GET("medic/category")
  Call<List<Category>> getCategoryList();

  @DELETE("medic/unsubscribe")
  Call<Void> unsubscribe();

  @GET("medic/check_subscription")
  Call<SubscriptionResponse> checkSubscription(@Query("mobilenum") String number);

  @GET("medic/video")
  Call<List<Video>> getVideoList(@Query("category_id") Integer categoryId,
      @Query("count") Integer count);


  @GET("/medic/statics")
  Call<StaticResponse> statics();
}


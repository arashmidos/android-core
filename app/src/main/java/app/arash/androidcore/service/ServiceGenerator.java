package app.arash.androidcore.service;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import app.arash.androidcore.BuildConfig;
import app.arash.androidcore.util.Empty;
import app.arash.androidcore.util.PreferenceHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Arash on 2017-02-16. Generate different Retrofit services to call REST services
 */

public class ServiceGenerator {

  private static Retrofit retrofit;
  private static OkHttpClient.Builder httpClient =
      new OkHttpClient.Builder()
          .readTimeout(120, TimeUnit.SECONDS)
          .connectTimeout(120, TimeUnit.SECONDS);
  //Change different level of logging here
  private static HttpLoggingInterceptor logging =
      new HttpLoggingInterceptor()
          .setLevel(Level.BODY);
  private static Retrofit.Builder builder;

  public static <S> S createService(Class<S> serviceClass) {

    return createService(serviceClass, null, null);
  }

  public static <S> S createService(Class<S> serviceClass, String username, String password) {
    String authToken = null;
    if (Empty.isNotEmpty(username) && Empty.isNotEmpty(password)) {
      authToken = Credentials.basic(username, password);
    }
    return createService(serviceClass, authToken);
  }

  public static <S> S createService(Class<S> serviceClass, final String authToken) {
    AuthenticationInterceptor interceptor;
    if (!TextUtils.isEmpty(authToken)) {
      interceptor = new AuthenticationInterceptor(authToken);
    } else {
      interceptor = new AuthenticationInterceptor(null);
    }
    httpClient.interceptors().clear();
//    if (!httpClient.interceptors().contains(interceptor)) {
    httpClient.addInterceptor(interceptor);
//    }
    if (BuildConfig.DEBUG) {
      httpClient.addInterceptor(logging);
    }

    String baseUrl = "https://rasavas.ir";
//    String baseUrl = "http://91.121.179.217:8087";
    // String baseUrl = "http://195.201.187.154";

//    if (Empty.isEmpty(baseUrl)) {
//      baseUrl = "http://www.google.com";
//    }
    builder = new Retrofit.Builder().baseUrl(baseUrl + "/");

    GsonBuilder gsonBuilder = new GsonBuilder();
//    gsonBuilder.registerTypeAdapter(BigDecimal.class, new JsonSerializer<BigDecimal>() {
//      @Override
//      public JsonElement serialize(final BigDecimal src, final Type typeOfSrc,
//          final JsonSerializationContext context) {
//
//        return new JsonPrimitive(src);
//      }
//    });

    Gson gson = gsonBuilder.setLenient().excludeFieldsWithoutExposeAnnotation().create();

    builder.addConverterFactory(GsonConverterFactory.create(gson));
    builder.client(httpClient.build());
    retrofit = builder.build();
//    Gson gson = new GsonBuilder()
//        .registerTypeAdapter(Id.class, new IdTypeAdapter())
//        .enableComplexMapKeySerialization()
//        .serializeNulls()
//        .setDateFormat(DateFormat.LONG)
//        .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
//        .setPrettyPrinting()
//        .setVersion(1.0)
//        .create();
    return retrofit.create(serviceClass);
  }

  static class AuthenticationInterceptor implements Interceptor {

    private String authToken;

    public AuthenticationInterceptor(String token) {
      this.authToken = token;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
      Request original = chain.request();

      String encodedRequest = original.url().toString();
//      encodedRequest = encodedRequest.replace("|", "%7c");
//
      Request.Builder builder;
      if (Empty.isEmpty(authToken)) {
        String token = PreferenceHelper.getToken();
        builder = original.newBuilder().addHeader("Authorization", "Bearer " + token);
      } else {
        builder = original.newBuilder().header("Authorization", authToken);
      }
      builder.addHeader("User-Agent", "MyClinic");

      Request request = builder.url(encodedRequest).build();
      return chain.proceed(request);

    }
  }
}

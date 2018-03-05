package app.arash.androidcore.service;

import app.arash.androidcore.MedicApplication;
import app.arash.androidcore.data.constant.StatusCodes;
import app.arash.androidcore.data.entity.Category;
import app.arash.androidcore.data.entity.Video;
import app.arash.androidcore.data.event.CategoryEvent;
import app.arash.androidcore.data.event.ErrorEvent;
import app.arash.androidcore.data.event.VideoEvent;
import app.arash.androidcore.util.NetworkUtil;
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
          EventBus.getDefault().post(new ErrorEvent(StatusCodes.SERVER_ERROR));
        }
      }

      @Override
      public void onFailure(Call<List<Category>> call, Throwable t) {
        EventBus.getDefault().post(new ErrorEvent(StatusCodes.NETWORK_ERROR));
      }
    });
  }

  public void getVideoList(int categoryId) {
    if (!NetworkUtil.isNetworkAvailable(MedicApplication.getInstance())) {
      EventBus.getDefault().post(new ErrorEvent(StatusCodes.NO_NETWORK));
      return;
    }
    RestService restService = ServiceGenerator.createService(RestService.class);

    Call<List<Video>> call = restService.getVideoList(categoryId);

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
          EventBus.getDefault().post(new ErrorEvent(StatusCodes.SERVER_ERROR));
        }
      }

      @Override
      public void onFailure(Call<List<Video>> call, Throwable t) {
        EventBus.getDefault().post(new ErrorEvent(StatusCodes.NETWORK_ERROR));
      }
    });
  }
}

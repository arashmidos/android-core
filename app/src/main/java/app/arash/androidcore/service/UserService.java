package app.arash.androidcore.service;

import app.arash.androidcore.MedicApplication;
import app.arash.androidcore.data.constant.StatusCodes;
import app.arash.androidcore.data.event.CategoryEvent;
import app.arash.androidcore.data.event.ErrorEvent;
import app.arash.androidcore.util.NetworkUtil;
import java.util.List;
import java.util.Locale.Category;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by arash on 2/19/18.
 */

public class UserService {


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
}

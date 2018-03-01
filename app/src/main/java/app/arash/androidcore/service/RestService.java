package app.arash.androidcore.service;

import app.arash.androidcore.data.entity.Category;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Arash on 2018-02-16.
 */

public interface RestService {

  @GET("category")
  Call<List<Category>> getCategoryList();

//  Call<List<Category>> getCategoryList(@Query("category_id") String categoryId);

}


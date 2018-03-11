package app.arash.androidcore.service;

import app.arash.androidcore.data.entity.Category;
import app.arash.androidcore.data.entity.Video;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Arash on 2018-02-16.
 */

public interface RestService {

  @GET("category")
  Call<List<Category>> getCategoryList();

  @GET("video_list")
  Call<List<Video>> getVideoList(@Query("category_id") Integer categoryId,
      @Query("count") Integer count);


}


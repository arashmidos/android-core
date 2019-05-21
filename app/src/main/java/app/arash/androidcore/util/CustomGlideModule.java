package app.arash.androidcore.util;

import android.content.Context;
import android.support.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;

@GlideModule
public class CustomGlideModule extends AppGlideModule {

  @Override
  public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
    Builder builder = new Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS);

    OkHttpClient client = builder.build();

    OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory(client);

    glide.getRegistry().replace(GlideUrl.class, InputStream.class, factory);
  }
}
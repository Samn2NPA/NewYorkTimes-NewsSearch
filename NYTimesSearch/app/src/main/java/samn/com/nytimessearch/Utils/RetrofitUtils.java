package samn.com.nytimessearch.Utils;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import samn.com.nytimessearch.BuildConfig;
import samn.com.nytimessearch.Logging.LoggingInterceptor;
import samn.com.nytimessearch.api.ApiResponse;

import static com.loopj.android.http.AsyncHttpClient.log;

/**
 * Created by Samn on 26-Jun-17.
 */

public class RetrofitUtils {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final Gson GSON = new Gson();

    public static Retrofit get(){
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(client())
                .baseUrl(ResourceUtils.WEB_URL)
                .build();
    }

    public static OkHttpClient client(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(apiKeyInterceptor())
                .addInterceptor(apiResponseInterceptor())
                .addInterceptor(new LoggingInterceptor())
                .build();
    }

    private static Interceptor apiKeyInterceptor(){
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                HttpUrl url = request.url()
                        .newBuilder()
                        .addQueryParameter("api_key", BuildConfig.API_KEY)
                        .build();

                request = request.newBuilder()
                        .url(url)
                        .build();

                // Log.d("Retrofit request: ", chain.proceed(request).toString());

                return chain.proceed(request);
            }
        };
    }

    private static Interceptor loggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

    private static Interceptor apiResponseInterceptor(){
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);

                ResponseBody body = response.body();

                log.w("AAA", body.string());
                ApiResponse apiResponse = GSON.fromJson(body.string(), ApiResponse.class);
                body.close();

                Log.w("apiResponseInterceptor", body.string());
                Log.w("apiResponseInterceptor", apiResponse.getStatus() + "||" + apiResponse.getResponse());

                return response.newBuilder()
                        .body(ResponseBody.create(JSON, GSON.toJson(apiResponse.getResponse())))
                        .build();
            }
        };
    }
}

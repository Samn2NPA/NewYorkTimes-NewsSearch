package samn.com.nytimessearch.api;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import samn.com.nytimessearch.model.SearchResult;

/**
 * Created by Samn on 26-Jun-17.
 */

public interface ArticleAPI {

    @GET("articlesearch.json")
    Call<SearchResult> search(@QueryMap (encoded = true)Map<String, String> options);

   //Call<ApiResponse> search(@QueryMap(encoded = true) Map<String, String> options);
}

package samn.com.nytimessearch.activity;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import samn.com.nytimessearch.R;
import samn.com.nytimessearch.Utils.RetrofitUtils;
import samn.com.nytimessearch.adapter.ArticleAdapter;
import samn.com.nytimessearch.api.ArticleAPI;
import samn.com.nytimessearch.model.Article;
import samn.com.nytimessearch.model.SearchRequest;
import samn.com.nytimessearch.model.SearchResult;

import static android.support.v7.widget.StaggeredGridLayoutManager.VERTICAL;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = SearchActivity.class.getSimpleName();

    private List<Article> article;
    private ArticleAdapter articleAdapter;

    private SearchRequest seachRequest;
    private ArticleAPI articleApi;

    @BindView(R.id.rvArticle)
    RecyclerView rvArticle;

    @BindView(R.id.pbLoadMore)
    ProgressBar pbLoadMore;

    @BindView(R.id.pbLoading)
    ProgressBar pbLoading;

    @BindView(R.id.toolbar) Toolbar toolbar;

    EditText searchViewEditText;

    private MenuItem miActionProgressItem; //menu Item Action Progress Item

    private interface Listener{
        void onResult(SearchResult searchResult);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupView();
        setupApi();
        search();

        //xử lý OnClick
        OnClickHandler();

    }

    private void setupView(){
        articleAdapter = new ArticleAdapter(this);
        rvArticle.setAdapter(articleAdapter);

        StaggeredGridLayoutManager layoutManager = new
                                StaggeredGridLayoutManager(2,VERTICAL); //(column , Orientation)
        rvArticle.setLayoutManager(layoutManager);
    }

    private void setupApi(){
        seachRequest = new SearchRequest();
        articleApi = RetrofitUtils.get().create(ArticleAPI.class);
    }

    private void search(){
        seachRequest.resetPage();
        pbLoading.setVisibility(View.VISIBLE);
        fetchArticle(new Listener() {
            @Override
            public void onResult(SearchResult searchResult) {
                Log.d(TAG, "searchResult :: " + searchResult.getArticles());
                articleAdapter.setData(searchResult.getArticles());
            }
        });
        /*fetchArticle(new Listener() {
            @Override
            public void onResult(SearchResult searchResult) {
                Log.d(TAG, "DEBUG:: " + new Gson().toJson(searchResult));
            }
        });*/


    }

    private void fetchArticle(final Listener listener){
        articleApi.search(seachRequest.toQueryMap()).enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                if(response.body() != null){
                    Log.d(TAG, "Response:: " + response.body().toString() + "|| "
                                    + response.body().getArticles().size());

                    listener.onResult(response.body());
                    handleComplete();
                }
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {

            }
        });

    }

    private void handleComplete(){
        pbLoading.setVisibility(View.GONE);
        pbLoadMore.setVisibility(View.GONE);
    }

    /*private void fetchArticle(final Listener listener){
        articleApi.search(seachRequest.toQueryMap()).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.body() != null){
                    try{
                        Log.d(TAG, "Response:: " + response.body().toString() + "|| "
                                + response.body().getResponse() + "|| "
                                + response.body().getResponse().getString("docs") );
                        listener.onResult((SearchResult) response.body().getResponse().get("docs"));
                        handleResponse((SearchResult) response.body().getResponse().get("docs"));
                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

            }
        });
    }*/

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //extract the action view from menu item
        miActionProgressItem = menu.findItem(R.id.action_progress);
        ProgressBar progressBar = (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);
        return super.onPrepareOptionsMenu(menu);
    }

    public void showProgressBar(){
        miActionProgressItem.setVisible(true);
    }

    public void hideProgressBar(){
        miActionProgressItem.setVisible(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchview = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchViewEditText = (EditText) searchview.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchViewEditText.setHint(getResources().getString(R.string.hint_search));

        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchview.clearFocus();
                seachRequest.setQuery(searchViewEditText.getText().toString());
                showProgressBar();
                search();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void OnClickHandler(){
        /*gvResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Article item = article.get(position);
                Intent intent = new Intent(getApplicationContext(), ArticleActivity.class);
                intent.putExtra(url, item.getWebUrl() );
                startActivity(intent);
            }
        });*/
    }

    /*private void onArticleSearch(){
        String query = etQuery.getText().toString();

        showProgressBar();
        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();
        params.put("api-key",  BuildConfig.API_KEY);
        params.put("q",query);

        client.get(ResourceUtils.WEB_URL, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray articleJsonResult = null;

                try {
                    articleJsonResult = response.getJSONObject("response").getJSONArray("docs");

                    //set data to article
                    *//* note:
                    * nếu để article.addAll -> adapter.notify CHANGE
                    * nếu để adapter.addAll -> không cần notify change *//*
                    if(article.size() != 0){
                        article.clear();
                        adapter.notifyDataSetChanged();
                    }
                    article.addAll(Article.fromJsonArray(articleJsonResult));
                    adapter.notifyDataSetChanged();
                    hideProgressBar();

                }catch (JSONException e){
                    e.printStackTrace();
                    Log.e(TAG, "JSONException:: " + e.toString());
                }
            }
        });
    }*/
}

package samn.com.nytimessearch.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Samn on 26-Jun-17.
 */

public class SearchResult {
    @SerializedName("docs")
    private List<Article> articles;

    public List<Article> getArticles(){ return this.articles; }
}

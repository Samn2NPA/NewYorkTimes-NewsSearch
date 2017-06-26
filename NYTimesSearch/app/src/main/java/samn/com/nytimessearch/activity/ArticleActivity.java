package samn.com.nytimessearch.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import samn.com.nytimessearch.R;
import samn.com.nytimessearch.Utils.ResourceUtils;

public class ArticleActivity extends AppCompatActivity {

    private static final String TAG = ArticleActivity.class.getSimpleName();

    @BindView(R.id.wvArticle) WebView wvArticle;
    @BindView(R.id.toolbar) Toolbar toolbar;

   // MenuItem mi_ActionProgressItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        //set up view
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        //get URL form previous action
        String url = getIntent().getStringExtra(ResourceUtils.url);

        settingsWebView();

        //load the URL
        wvArticle.loadUrl(url);
    }

    //setting for web view
    private void settingsWebView(){
        wvArticle.getSettings().setLoadsImagesAutomatically(true);
        wvArticle.getSettings().setJavaScriptEnabled(true);
        wvArticle.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wvArticle.setWebViewClient(new WebBrowser()); //configure the client to use when url loaded
    }

    // Manages the behavior when URLs are loaded
    private class WebBrowser extends WebViewClient{
        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }
    }

    /*@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mi_ActionProgressItem = menu.findItem(R.id.article_action_progress);

        //extract the action view from menu item
        ProgressBar progressBar = (ProgressBar) MenuItemCompat.getActionView(mi_ActionProgressItem);

        return super.onPrepareOptionsMenu(menu);
    }

    public void showProgressBar(){
        mi_ActionProgressItem.setVisible(true);
    }

    public void hideProgressBar(){
        mi_ActionProgressItem.setVisible(false);
    }*/
}

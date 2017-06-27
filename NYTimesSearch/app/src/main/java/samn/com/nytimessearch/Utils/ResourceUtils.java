package samn.com.nytimessearch.Utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import samn.com.nytimessearch.R;

/**
 * Created by Samn on 25-Jun-17.
 */

public class ResourceUtils {

    public static final String WEB_URL = "https://api.nytimes.com/svc/search/v2/";
    public static final String ARTICLE_OBJECT = "article_object";
    public static final String url = "url";
    public static final String BASE_URL = "http://www.nytimes.com/";

    public static void loadImage(Context context, ImageView imageView, String url){
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .into(imageView);
    }

    public static void loadImage_noPlaceholder(Context context, ImageView imageView, String url){
        Glide.with(context)
                .load(url)
                .into(imageView);
    }
}

package samn.com.nytimessearch.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import samn.com.nytimessearch.R;
import samn.com.nytimessearch.Utils.ResourceUtils;
import samn.com.nytimessearch.model.Article;

/**
 * Created by Samn on 25-Jun-17.
 */

public class ArticleArrayAdapter extends ArrayAdapter<Article> {

    private static final String TAG = ArticleArrayAdapter.class.getSimpleName();
    private List<Article> articles;

    public ArticleArrayAdapter(@NonNull Context context, List<Article> articles) {
        super(context, -1);
        this.articles = articles;
    }

    public void setArticles(List<Article> articles){
        this.articles = articles;
    }

    @Override
    public int getCount() {
        return articles.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewholder;

        if(convertView == null){
            convertView = LayoutInflater.from(getContext())
                                    .inflate(R.layout.item_article, parent, false);

            viewholder = new ViewHolder(convertView);

            convertView.setTag(viewholder);
        }
        else {
            viewholder = (ViewHolder) convertView.getTag();
        }

        bindView(position,viewholder);

        return convertView;
    }

    private void bindView(int position, ViewHolder viewHolder){
        Article article = articles.get(position);

        viewHolder.tvTitle.setText(article.getMainHeadLine());
        ResourceUtils.loadImage(getContext(), viewHolder.ivThumbnail, article.getMultimedia().get(0).getUrl());
    }

    static class ViewHolder{
        @BindView(R.id.tvTitle)
        TextView tvTitle;

        @BindView(R.id.ivCover)
        ImageView ivThumbnail;

        public ViewHolder(View convertView){
            ButterKnife.bind(this,convertView);
        }
    }

}

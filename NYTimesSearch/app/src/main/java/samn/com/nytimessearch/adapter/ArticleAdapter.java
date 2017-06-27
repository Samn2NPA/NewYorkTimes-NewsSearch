package samn.com.nytimessearch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import samn.com.nytimessearch.R;
import samn.com.nytimessearch.Utils.ResourceUtils;
import samn.com.nytimessearch.model.Article;

/**
 * Created by Samn on 26-Jun-17.
 */

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int NO_IMAGE = 0;
    private static final int HAS_IMAGE = 1;

    private final List<Article> articles;
    private final Context context;

    /* Listener to Load More */
    private Listener listener;

    public interface Listener{
        void onLoadMore();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
    /*End __  Listener to Load More */

    public ArticleAdapter(Context context) {
        this.articles = new ArrayList<>();
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if(hasImageAt(position))
            return HAS_IMAGE;
        else
            return NO_IMAGE;
    }

    private boolean hasImageAt(int position){
        Article article = articles.get(position);
         return (article.getMultimedia() != null & article.getMultimedia().size() > 0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == NO_IMAGE){
            View itemView = LayoutInflater.from(context)
                            .inflate(R.layout.item_article_no_image, parent,false);
            return new NoImageViewHolder(itemView);
        }
        else{
            View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_article, parent,false);
            return new HasImageViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Article article = articles.get(position);
        if(holder instanceof NoImageViewHolder){
            bindNoImage((NoImageViewHolder) holder, article);
        } else if (holder instanceof HasImageViewHolder){
            bindHasImage((HasImageViewHolder) holder, article);
        }

        if(position == articles.size() - 1){
            //if reach the last position => load more
            listener.onLoadMore();
        }
    }

    private void bindNoImage(NoImageViewHolder holder, Article article){
        holder.tvTitle_noImage.setText(article.getMainHeadLine());
    }

    private void bindHasImage(HasImageViewHolder holder, Article article){
        holder.tvTitle.setText(article.getMainHeadLine());
        Article.Media media = article.getMultimedia().get(0);

        ViewGroup.LayoutParams params = holder.ivCover.getLayoutParams();
        params.height = media.getHeight();
        holder.ivCover.setLayoutParams(params);

        ResourceUtils.loadImage_noPlaceholder(context, holder.ivCover, media.getUrl());
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setData(List<Article> newArticles) {
        articles.clear();
        articles.addAll(newArticles);
        notifyDataSetChanged();
    }

    public void appendData(List<Article> newArticles) {
        int nextPos = articles.size();
        articles.addAll(nextPos, newArticles);
        notifyItemRangeChanged(nextPos,newArticles.size());
    }

    class HasImageViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvTitle)
        TextView tvTitle;

        @BindView(R.id.ivCover)
        ImageView ivCover;

        public HasImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class NoImageViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tvTitle_noImage)
        TextView tvTitle_noImage;

        public NoImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

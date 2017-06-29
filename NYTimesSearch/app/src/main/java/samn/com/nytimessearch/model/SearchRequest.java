package samn.com.nytimessearch.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Samn on 26-Jun-17.
 */

public class SearchRequest implements Parcelable {

    private int page = 0;
    private String query = "";
    private String beginDate = "";
    private String sort = "";
    private String newsDesk = "news_desk:";
    private boolean hasArt;
    private boolean hasFashionAndStyle;
    private boolean hasSports;

    public SearchRequest(){

    }

    public void setSettingSearch(String beginDate, String sort, boolean hasArt, boolean hasFashionAndStyle, boolean hasSports) {
        setBegin_date(beginDate);
        setSort(sort);
        setHasArt(hasArt);
        setHasFashionAndStyle(hasFashionAndStyle);
        setHasSports(hasSports);
    }

    public void setHasArt(boolean hasArt) {
        this.hasArt = hasArt;
    }

    public void setHasFashionAndStyle(boolean hasFashionAndStyle) {
        this.hasFashionAndStyle = hasFashionAndStyle;
    }

    public void setHasSports(boolean hasSports) {
        this.hasSports = hasSports;
    }

    public void resetPage(){ page = 0; }

    public void nextPage() { page += 1; }

    public int getPage() {
        return page;
    }

    public String getQuery() {
        return query;
    }

    public String getBegin_date() {
        return beginDate;
    }

    public String getSort() {
        return sort;
    }

    public String getNews_desk() {
        if(!hasArt && !hasFashionAndStyle && !hasSports) return null;

        String value = "";
        if(hasArt) value += "Art,Arts,";
        if(hasFashionAndStyle) value += "Fashion,Style,";
        if(hasSports) value+= "Sport";
        return value.trim();
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setBegin_date(String begin_date) {
        this.beginDate = begin_date;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Map<String, String> toQueryMap(){
        Map<String, String> options = new HashMap<>();
        options.put("page", String.valueOf(page));
        if(!query.isEmpty() && query!=null) options.put("q", query);
        if(beginDate!=null && !beginDate.isEmpty() ) options.put("begin_date", beginDate);
        if(!sort.isEmpty() && sort!=null) options.put("sort", sort.toLowerCase());
        if(getNews_desk()!= null) options.put("fq", newsDesk + getNews_desk());

        return options;
    }

    protected SearchRequest(Parcel in) {
        page = in.readInt();
        query = in.readString();
        beginDate = in.readString();
        sort = in.readString();
        newsDesk = in.readString();
    }
    public static final Creator<SearchRequest> CREATOR = new Creator<SearchRequest>() {
        @Override
        public SearchRequest createFromParcel(Parcel in) {
            return new SearchRequest(in);
        }

        @Override
        public SearchRequest[] newArray(int size) {
            return new SearchRequest[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(page);
        parcel.writeString(query);
        parcel.writeString(beginDate);
        parcel.writeString(sort);
        parcel.writeString(newsDesk);
    }
}

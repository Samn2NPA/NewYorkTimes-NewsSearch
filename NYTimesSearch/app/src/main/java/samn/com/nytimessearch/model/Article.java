package samn.com.nytimessearch.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import samn.com.nytimessearch.Utils.ResourceUtils;

/**
 * Created by Samn on 25-Jun-17.
 */

/*
* web_url: "https://www.nytimes.com/reuters/2017/06/25/world/americas/25reuters-mexico-violence.html",
snippet: "Eleven people were killed in Mexico's Veracruz state by criminal gangs on Saturday, including four children and the head of the state's federal police and two other federal police officers, Governor Miguel Angel Yunes said....",
lead_paragraph: "Eleven people were killed in Mexico's Veracruz state by criminal gangs on Saturday, including four children and the head of the state's federal police and two other federal police officers, Governor Miguel Angel Yunes said.",
abstract: null,
print_page: null,
blog: [ ],
source: "Reuters",
multimedia: [ ],
headline: {
main: "Criminal Gangs Kill 11 in Mexico's Veracruz State",
print_headline: "Criminal Gangs Kill 11 in Mexico's Veracruz State"
},
keywords: [ ],
pub_date: "2017-06-25T06:51:27+0000",
document_type: "article",
news_desk: "None",
section_name: "World",
subsection_name: "Americas",
byline: {
person: [ ],
original: "By REUTERS",
organization: "REUTERS"
},
type_of_material: "News",
_id: "594f5d747c459f257c1abe18",
word_count: 230,
slideshow_credits: null*/
public class Article {
    @SerializedName("web_url")
    private String webUrl;

    @SerializedName("headline")
    private JsonObject headLine;

    @SerializedName("multimedia")
    private List<Media> multimedia;

    private String mainHeadLine;


    public String getWebUrl() {
        return webUrl;
    }
    public JsonObject getHeadLine() {
        return headLine;
    }
    public List<Media> getMultimedia() {
        return multimedia;
    }

    public String getMainHeadLine() {
        if (getHeadLine()!=null)
            this.mainHeadLine = headLine.get("main").toString();
        else
            this.mainHeadLine = "";
        return this.mainHeadLine;
    }

    public static class Media{
        private String url;
        private String type;
        private int height;
        private int width;

        public String getUrl() {
            return ResourceUtils.BASE_URL + url;
        }
        public String getType() {
            return type;
        }
        public int gẢetHeight() {
            return height;
        }
        public int getWidth() {
            return width;
        }
    }


    /*public Article(JSONObject jsonObject){
        try {
            this.webUrl = jsonObject.getString("web_url");
            this.headline = jsonObject.getJSONObject("headline").getString("main");

            JSONArray multimedia = jsonObject.getJSONArray("multimedia");
            if(multimedia.length() > 0){
                JSONObject multimediaJson = multimedia.getJSONObject(0);
                this.thumbNail = "http://www.nytimes.com/" + multimediaJson.getString("url");
            } else {
                this.thumbNail = "";
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public static ArrayList<Article> fromJsonArray(JSONArray jsonArray){
        ArrayList<Article> results = new ArrayList<>();

        for (int i = 0; i< jsonArray.length(); i++){
            try{
                results.add(new Article(jsonArray.getJSONObject(i)));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        return results;
    }*/
}

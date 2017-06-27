package samn.com.nytimessearch.api;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Samn on 26-Jun-17.
 */

public class ApiResponse {

    @SerializedName("response")
    private JsonObject response;

    @SerializedName("status")
    private String status;

    public JsonObject getResponse() {
        if (response == null) {
            return new JsonObject();
        }
        return response;
    }

    public String getStatus() {
        return status;
    }
}

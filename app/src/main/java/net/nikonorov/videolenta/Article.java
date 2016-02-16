package net.nikonorov.videolenta;

import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vitaly on 16.02.16.
 */
public class Article {
    private String header;
    private String footer;
    private Uri videoUri;

    public Article(JSONObject data) throws JSONException {
        header = data.getString("header");
        footer = data.getString("footer");
        videoUri = Uri.parse(data.getString("gif"));
    }

    public String getFooter() {
        return footer;
    }

    public String getHeader() {
        return header;
    }

    public Uri getVideoUri() {
        return videoUri;
    }
}

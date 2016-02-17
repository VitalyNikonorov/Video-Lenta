package net.nikonorov.videolenta;

import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by vitaly on 16.02.16.
 */


@Root(name = "item")
public class Article {

    @Element(name = "header")
    private String header;

    @Element(name = "footer")
    private String footer;

    @Element(name = "gif")
    private Gif gif;

    private Uri videoUri;

    public Article() {
    }

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

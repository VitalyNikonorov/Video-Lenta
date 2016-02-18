package net.nikonorov.videolenta.api;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by vitaly on 16.02.16.
 */


@Root(name = "item")
public class Post {

    @Element(name = "header")
    private String header;

    @Element(name = "footer")
    private String footer;

    @Element(name = "gif")
    private Gif gif;

    public Post() {
    }

    public String getFooter() {
        return footer;
    }

    public String getHeader() {
        return header;
    }

    public Gif getGif() {
        return gif;
    }
}

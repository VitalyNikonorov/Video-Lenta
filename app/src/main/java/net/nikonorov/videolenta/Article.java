package net.nikonorov.videolenta;

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

    public Article() {
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

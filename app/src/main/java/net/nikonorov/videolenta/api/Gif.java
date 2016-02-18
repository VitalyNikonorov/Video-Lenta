package net.nikonorov.videolenta.api;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by vitaly on 17.02.16.
 */
@Root(name = "gif")
public class Gif{
    @Attribute(name = "url")
    private String url;

    public String getUrl() {
        return url;
    }
}
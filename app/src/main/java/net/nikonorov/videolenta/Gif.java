package net.nikonorov.videolenta;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by vitaly on 17.02.16.
 */
@Root(name = "gif")
class Gif{
    @Attribute(name = "url")
    String url;
}
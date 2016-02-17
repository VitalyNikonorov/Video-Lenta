package net.nikonorov.videolenta;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vitaly on 17.02.16.
 */
@Root(name = "items")
public class PostList {
    @ElementList(required=true, inline=true)
    private List<Article> articleList = new ArrayList<>();
}

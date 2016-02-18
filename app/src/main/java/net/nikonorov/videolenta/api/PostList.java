package net.nikonorov.videolenta.api;

import net.nikonorov.videolenta.api.Post;

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
    private List<Post> postList = new ArrayList<>();

    public List<Post> getPostList() {
        return postList;
    }
}

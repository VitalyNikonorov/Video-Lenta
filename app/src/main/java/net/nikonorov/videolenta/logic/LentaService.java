package net.nikonorov.videolenta.logic;

import net.nikonorov.videolenta.Article;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by vitaly on 17.02.16.
 */
public interface LentaService {


    @GET("/lenta.xml")
    public Call<ArrayList<Article>> getPosts();
}

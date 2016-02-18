package net.nikonorov.videolenta.logic;

import android.content.Context;
import android.content.Loader;

import net.nikonorov.videolenta.api.PostList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by vitaly on 18.02.16.
 */
public class RowLoader extends Loader<PostList> {

    private PostList data;
    private Retrofit retrofit;

    public RowLoader(Context context) {
        super(context);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://91.215.138.197")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
    }

    @Override
    public void deliverResult(PostList data) {
        super.deliverResult(data);

        this.data = data;
    }

    @Override
    protected void onStartLoading() {
        if (data != null) {
            deliverResult(data);
        } else {
            forceLoad();
        }
    }

    @Override
    protected void onForceLoad() {

        LentaService myAPI = retrofit.create(LentaService.class);

        Call<PostList> call = myAPI.getPosts();

        call.enqueue(new Callback<PostList>() {
            @Override
            public void onResponse(retrofit2.Call<PostList> call, Response<PostList> response) {
                if(response.isSuccess()) {
                    deliverResult(response.body());
                }else{
                    deliverResult(null);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<PostList> call, Throwable t) {
                try {
                    deliverResult(null);
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
    }
}

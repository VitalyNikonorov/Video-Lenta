package net.nikonorov.videolenta;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import net.nikonorov.videolenta.logic.LentaService;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by vitaly on 16.02.16.
 */
public class ActivityMain extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RVAdapter adapter;
    private ArrayList<Article> data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rv_lenta);
        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityMain.this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://nikonorov.net")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        LentaService myAPI = retrofit.create(LentaService.class);

        Call<Article> call = myAPI.getPosts();

        call.enqueue(new Callback<Article>() {
            @Override
            public void onResponse(retrofit2.Call<Article> call, Response<Article> response) {
                //data = new ArrayList<>(response.body());

                Log.i("TAG", response.body().toString());
                Log.i("TAG", response.body().toString());
                //adapter = new RVAdapter(data, ActivityMain.this);
                //recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(retrofit2.Call<Article> call, Throwable t) {

            }
        });
    }
}

package net.nikonorov.videolenta;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.nikonorov.videolenta.logic.LentaService;

import java.util.ArrayList;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
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

        //String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><items><item><header>Header</header> <gif url=\\\"….\\\"/><footer>Footer</footer></item><item><header>Header2</header> <gif url=\"…2.\"/><footer>Footer2</footer></item></items>";
        //JSONObject jsonObject = null;

        recyclerView = (RecyclerView) findViewById(R.id.rv_lenta);
        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityMain.this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://nikonorov.net/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        LentaService myAPI = retrofit.create(LentaService.class);

        Call<ArrayList<Article>> call = myAPI.getPosts();

        call.enqueue(new Callback<ArrayList<Article>>() {
            @Override
            public void onResponse(retrofit2.Call<ArrayList<Article>> call, Response<ArrayList<Article>> response) {
                data = new ArrayList<>(response.body());

                adapter = new RVAdapter(data, ActivityMain.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(retrofit2.Call<ArrayList<Article>> call, Throwable t) {

            }
        });


//        try {
//            data.add(new Article(new JSONObject("{\"header\":\"header1\", \"footer\":\"footer1\", \"gif\":\""+"android.resource://" + getPackageName() +"/"+R.raw.video1+"\"}")));
//            data.add(new Article(new JSONObject("{\"header\":\"header2\", \"footer\":\"footer2\", \"gif\":\""+"android.resource://" + getPackageName() +"/"+R.raw.video2+"\"}")));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }
}

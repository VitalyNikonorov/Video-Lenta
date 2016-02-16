package net.nikonorov.videolenta;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><items><item><header>Header</header> <gif url=\\\"….\\\"/><footer>Footer</footer></item><item><header>Header2</header> <gif url=\"…2.\"/><footer>Footer2</footer></item></items>";
        JSONObject jsonObject = null;

        recyclerView = (RecyclerView) findViewById(R.id.rv_lenta);
        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityMain.this));

        data = new ArrayList<>();
        try {
            data.add(new Article(new JSONObject("{\"header\":\"header1\", \"footer\":\"footer1\", \"gif\":\""+"android.resource://" + getPackageName() +"/"+R.raw.video1+"\"}")));
            data.add(new Article(new JSONObject("{\"header\":\"header2\", \"footer\":\"footer2\", \"gif\":\""+"android.resource://" + getPackageName() +"/"+R.raw.video2+"\"}")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new RVAdapter(data, this);
        recyclerView.setAdapter(adapter);
    }
}

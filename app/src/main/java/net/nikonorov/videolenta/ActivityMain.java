package net.nikonorov.videolenta;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.nikonorov.videolenta.api.Post;
import net.nikonorov.videolenta.api.PostList;
import net.nikonorov.videolenta.logic.RowLoader;

import java.util.ArrayList;


/**
 * Created by vitaly on 16.02.16.
 */
public class ActivityMain extends AppCompatActivity implements LoaderManager.LoaderCallbacks<PostList> {

    private RecyclerView recyclerView;
    private RVAdapter adapter;
    private ArrayList<Post> data;

    private final static int LOADER_ID = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rv_lenta);
        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityMain.this));

        data = new ArrayList<Post>(new ArrayList<Post>());

        adapter = new RVAdapter(data, ActivityMain.this);
        recyclerView.setAdapter(adapter);
        getLoaderManager().initLoader(1, Bundle.EMPTY, ActivityMain.this);
    }

    @Override
    public Loader<PostList> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_ID:
                return new RowLoader(ActivityMain.this);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<PostList> loader, PostList list) {
        data.clear();
        data.addAll(list.getPostList());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });

        getLoaderManager().destroyLoader(LOADER_ID);

    }

    @Override
    public void onLoaderReset(Loader<PostList> loader) {

    }
}

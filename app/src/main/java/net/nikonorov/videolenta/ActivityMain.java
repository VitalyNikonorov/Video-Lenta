package net.nikonorov.videolenta;

import android.app.Dialog;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;

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
    private LinearLayoutManager llm;

    private int screenHeight;

    ProgressDialog progress;

    private final static int LOADER_ID = 1;

    private final static int X = 0;
    private final static int Y = 1;

    private final static int TOP = 0;
    private final static int BOTTOM = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rv_lenta);
        llm = new LinearLayoutManager(ActivityMain.this);
        recyclerView.setLayoutManager(llm);

        data = new ArrayList<Post>(new ArrayList<Post>());

        adapter = new RVAdapter(data, ActivityMain.this);
        recyclerView.setAdapter(adapter);

        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Loading row...");

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        screenHeight = displaymetrics.heightPixels;

        final int rvCoordinates[] = new int[2];

        final View rvFrame = findViewById(R.id.main_background);
        rvFrame.getLocationInWindow(rvCoordinates);


        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_DRAGGING) {

//                    int visibilityBorders[] = new int[2];

//                    visibilityBorders[TOP] = rvCoordinates[Y] - rvFrame.getHeight()/2;
//                    visibilityBorders[BOTTOM] = rvCoordinates[Y] + rvFrame.getHeight()/2;

                    int firstVisiblePosition = llm.findFirstVisibleItemPosition();
                    int lastVisiblePosition = llm.findLastVisibleItemPosition();

                    for (int i = firstVisiblePosition; i <= lastVisiblePosition; i++) {
                        RVAdapter.CardViewHolder holder = (RVAdapter.CardViewHolder) recyclerView.findViewHolderForAdapterPosition(i);

                        int coordinates[] = new int[2];
                        holder.vvVideo.getLocationOnScreen(coordinates);

                        if (coordinates[Y] > 0 && coordinates[Y] < screenHeight) {
                            holder.vvVideo.start();
                        } else {
                            holder.vvVideo.pause();
                        }
                    }

                    //Log.i("LOG", "\n-----\n");
                }
                return false;
            }
        });


        getLoaderManager().initLoader(1, Bundle.EMPTY, ActivityMain.this);
    }

    @Override
    public Loader<PostList> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_ID:
                progress.show();
                return new RowLoader(ActivityMain.this);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<PostList> loader, PostList list) {
        progress.dismiss();
        if(list != null) {
            data.clear();
            data.addAll(list.getPostList());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });

        }else {

            final Dialog dialog = new Dialog(ActivityMain.this);

            dialog.setContentView(R.layout.dialog);
            dialog.setTitle("Error");

            Button btnDismiss = (Button) dialog.findViewById(R.id.reconnect_close);
            btnDismiss.setOnClickListener(new Button.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            Button reconnect = (Button) dialog.findViewById(R.id.reconnect_reconnect);
            reconnect.setOnClickListener(new Button.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    getLoaderManager().initLoader(1, Bundle.EMPTY, ActivityMain.this);
                }
            });

            dialog.show();
        }

        getLoaderManager().destroyLoader(LOADER_ID);

    }

    @Override
    public void onLoaderReset(Loader<PostList> loader) {
        progress.dismiss();
    }
}

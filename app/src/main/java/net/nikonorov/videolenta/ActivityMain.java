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
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

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

        final int height = getStatusBarHeight();

        int actionBarHeight = 0;

        TypedValue tv = new TypedValue();

        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
        }

        final int finalActionBarHeight = actionBarHeight;

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int visibilityBorders[] = new int[2];

                visibilityBorders[TOP] = height + finalActionBarHeight;
                visibilityBorders[BOTTOM] = screenHeight;



                int firstVisiblePosition = llm.findFirstVisibleItemPosition();
                int lastVisiblePosition = llm.findLastVisibleItemPosition();


                if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                    turnVisibleItems(recyclerView, visibilityBorders, firstVisiblePosition, lastVisiblePosition, RecyclerView.SCROLL_STATE_DRAGGING);
                } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                    turnVisibleItems(recyclerView, visibilityBorders, firstVisiblePosition, lastVisiblePosition, RecyclerView.SCROLL_STATE_SETTLING);
                }

                return false;
            }
        });

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


                int firstVisiblePosition = llm.findFirstVisibleItemPosition();
                int lastVisiblePosition = llm.findLastVisibleItemPosition();

                int visibilityBorders[] = new int[2];

                visibilityBorders[TOP] = height + finalActionBarHeight;
                visibilityBorders[BOTTOM] = screenHeight;

                if(newState == RecyclerView.SCROLL_STATE_DRAGGING || newState == RecyclerView.SCROLL_STATE_SETTLING){
                    for (int i = firstVisiblePosition; i <= lastVisiblePosition; i++) {
                        RVAdapter.CardViewHolder holder = (RVAdapter.CardViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
                        holder.vvVideo.pause();
                    }
                }else {
                    turnVisibleItems(recyclerView, visibilityBorders, firstVisiblePosition, lastVisiblePosition, RecyclerView.SCROLL_STATE_SETTLING);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibilityBorders[] = new int[2];

                visibilityBorders[TOP] = height + finalActionBarHeight;
                visibilityBorders[BOTTOM] = screenHeight;



                int firstVisiblePosition = llm.findFirstVisibleItemPosition();
                int lastVisiblePosition = llm.findLastVisibleItemPosition();

                for (int i = firstVisiblePosition; i <= lastVisiblePosition; i++) {
                    RVAdapter.CardViewHolder holder = (RVAdapter.CardViewHolder) recyclerView.findViewHolderForAdapterPosition(i);

                    int coordinates[] = new int[2];
                    holder.vvVideo.getLocationOnScreen(coordinates);

                    coordinates[Y] += holder.vvVideo.getHeight() / 2;

                    if (coordinates[Y] > visibilityBorders[TOP] && coordinates[Y] < visibilityBorders[BOTTOM] && recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {  // && recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_DRAGGING
                        holder.vvVideo.start();
                    } else {
                        holder.vvVideo.pause();
                    }
                }

            }
        });

        getLoaderManager().initLoader(1, Bundle.EMPTY, ActivityMain.this);
    }

    private void turnVisibleItems(RecyclerView recyclerView, int[] visibilityBorders, int firstVisiblePosition, int lastVisiblePosition, int scrollState) {
        for (int i = firstVisiblePosition; i <= lastVisiblePosition; i++) {
            RVAdapter.CardViewHolder holder = (RVAdapter.CardViewHolder) recyclerView.findViewHolderForAdapterPosition(i);

            int coordinates[] = new int[2];
            holder.vvVideo.getLocationOnScreen(coordinates);

            coordinates[Y] += holder.vvVideo.getHeight() / 2;

            if (coordinates[Y] > visibilityBorders[TOP] && coordinates[Y] < visibilityBorders[BOTTOM] && recyclerView.getScrollState() != scrollState) {  // && recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_DRAGGING
                holder.vvVideo.start();
            } else {
                holder.vvVideo.pause();
            }
        }
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
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

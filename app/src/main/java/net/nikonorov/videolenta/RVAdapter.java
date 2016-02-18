package net.nikonorov.videolenta;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import net.nikonorov.videolenta.api.Post;

import java.util.ArrayList;

/**
 * Created by vitaly on 16.02.16.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.CardViewHolder> {

    private ArrayList<Post> data = null;
    private Context context;

    public RVAdapter(ArrayList<Post> data, Context context){
        this.data = data;
        this.context = context;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_listitem, parent, false);
        CardViewHolder viewHolder = new CardViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        holder.tvHeader.setText(data.get(position).getHeader());
        holder.tvFooter.setText(data.get(position).getFooter());
        holder.vvVideo.setVideoURI(Uri.parse(data.get(position).getGif().getUrl()));
        holder.vvVideo.setMediaController(null);
        holder.vvVideo.requestFocus(0);
        holder.vvVideo.start();

        holder.vvVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                mp.setVolume(0, 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder{

        CardView cv;
        TextView tvHeader;
        TextView tvFooter;
        VideoView vvVideo;

        public CardViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv_listitem);
            tvHeader = (TextView) itemView.findViewById(R.id.tv_header);
            tvFooter = (TextView) itemView.findViewById(R.id.tv_footer);
            vvVideo = (VideoView) itemView.findViewById(R.id.vv_animated_image);

        }

    }

}

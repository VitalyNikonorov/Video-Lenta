package net.nikonorov.videolenta;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;

/**
 * Created by vitaly on 16.02.16.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.CardViewHolder> {

    private ArrayList<Article> data = null;
    private Context context;

    public RVAdapter(ArrayList<Article> data, Context context){
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
        holder.vvVideo.setVideoURI(data.get(position).getVideoUri());
        holder.vvVideo.setMediaController(new MediaController(context));
        holder.vvVideo.requestFocus(0);
        holder.vvVideo.start();
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

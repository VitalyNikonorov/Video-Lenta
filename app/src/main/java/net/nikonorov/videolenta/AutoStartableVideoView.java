package net.nikonorov.videolenta;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

import net.nikonorov.videolenta.logic.VideoSturtable;

/**
 * Created by vitaly on 18.02.16.
 */
public class AutoStartableVideoView extends VideoView implements VideoSturtable{
    public AutoStartableVideoView(Context context) {
        super(context);
    }

    public AutoStartableVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoStartableVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onVideoStart() {
        this.start();
    }

    @Override
    public void onPause() {
        this.pause();
    }
}

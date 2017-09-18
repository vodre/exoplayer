package com.labs.vdrx.myapplication;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.brightcove.player.analytics.Analytics;
import com.brightcove.player.edge.Catalog;
import com.brightcove.player.edge.VideoListener;
import com.brightcove.player.event.Event;
import com.brightcove.player.event.EventEmitter;
import com.brightcove.player.event.EventListener;
import com.brightcove.player.event.EventType;
import com.brightcove.player.mediacontroller.BrightcoveMediaController;
import com.brightcove.player.model.Video;
import com.brightcove.player.view.BrightcoveExoPlayerVideoView;

import java.util.ArrayList;
import java.util.List;


public class VideoGalleryFragment extends Fragment {

    private VideoPagerAdapter videoPagerAdapter;
    private CustomViewPager galleryViewPager;

    private final String TAG = this.getClass().getSimpleName();


    public VideoGalleryFragment() {
    }

    public static VideoGalleryFragment newInstance() {
        VideoGalleryFragment frag = new VideoGalleryFragment();
        return frag;
    }

    @Override
    public void onCreate(Bundle args) {
        super.onCreate(args);
        videoPagerAdapter = new VideoPagerAdapter(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        galleryViewPager = (CustomViewPager) view.findViewById(R.id.gallery_pager);
        galleryViewPager.setAdapter(videoPagerAdapter);

        return view;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

    @Override
    public void onPause() {

        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void setView(boolean isLandscape) {
    }


    public class VideoPagerAdapter extends PagerAdapter {

        private Context context;
        private List<String> videoUrls;
        private LayoutInflater inflater;

        public VideoPagerAdapter(Context context) {
            this.context = context;
            this.videoUrls = new ArrayList<>();

            videoUrls.add("http://c.brightcove.com/services/mobile/streaming/index/master.m3u8?videoId=5312907340001&pubId=1661991771001");
            videoUrls.add("http://c.brightcove.com/services/mobile/streaming/index/master.m3u8?videoId=5309774878001&pubId=1661991771001");
            videoUrls.add("http://c.brightcove.com/services/mobile/streaming/index/master.m3u8?videoId=5309743698001&pubId=1661991771001");

            if (inflater == null) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
        }

        @Override
        public int getCount() {
            if (videoUrls == null) return 0;
            return videoUrls.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(final ViewGroup container, final int position) {
            final RelativeLayout frame = (RelativeLayout) inflater.inflate(R.layout.item_video, container, false);


            final BrightcoveExoPlayerVideoView brightcoveVideoView = frame.findViewById(R.id.brightcove_video_view);
            (frame.findViewById(R.id.progressBar)).setVisibility(View.GONE);
            brightcoveVideoView.setVisibility(View.VISIBLE);
            (frame.findViewById(R.id.sponsored_video_label)).setVisibility(View.VISIBLE);

            BrightcoveMediaController mediaController = new BrightcoveMediaController(brightcoveVideoView, R.layout.brightcove_control);
            brightcoveVideoView.setMediaController(mediaController);
            mediaController.show();

            EventEmitter eventEmitter = brightcoveVideoView.getEventEmitter();

            eventEmitter.on(EventType.ANY, new EventListener() {
                @Override
                public void processEvent(Event event) {
                    String eventType =  event.getType();
                   Log.d(TAG, eventType);
                }
            });

            String accountId = "";
            String policyId = "";

            if (getContext() != null){
                accountId = getContext().getString(R.string.brightcove_account_id);
                policyId = getContext().getString(R.string.brightcove_policy_key);
            }


            Catalog catalog = new Catalog(eventEmitter, accountId, policyId);


            String videoID = "";

            if (videoUrls != null && videoUrls.get(position) != null && !videoUrls.get(position).isEmpty()){
                Uri uri = Uri.parse(videoUrls.get(position));
                if (uri != null){
                    videoID = uri.getQueryParameter("videoId");
                }
            }



            catalog.findVideoByID(videoID, new VideoListener() {

                @Override
                public void onVideo(Video video) {
                    Log.d(TAG, "on video: " + video);
                    brightcoveVideoView.add(video);
                    brightcoveVideoView.start();
                }

                @Override
                public void onError(String error) {
                    super.onError(error);
                    Log.e(TAG, error);
                }
            });


            Analytics analytics = brightcoveVideoView.getAnalytics();
            analytics.setAccount(accountId);



            container.addView(frame);
            return frame;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        final View view = (View) object;
        container.removeView(view);
    }

}

}
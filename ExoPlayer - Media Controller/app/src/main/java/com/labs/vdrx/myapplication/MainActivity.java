package com.labs.vdrx.myapplication;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {

    private CustomViewPager viewPager;
    private static int  GALLERY_POSITION = 0;
    private static int  VIDEO_POSITION = 1;
    private boolean viewCreated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        if(!viewCreated){
            viewCreated = true;
            viewPager = (CustomViewPager) findViewById(R.id.pager);
            viewPager.setPagingEnabled(true);
            TabLayout tabLayout = (TabLayout) findViewById(R.id.image_gallery_tablayout);
            final PagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
            viewPager.setAdapter(adapter);


            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }

            });
        }else{

            TabPagerAdapter adapter = (TabPagerAdapter)viewPager.getAdapter();

            if(adapter != null){
                if (viewPager.getCurrentItem() == VIDEO_POSITION){
                    VideoGalleryFragment videoFragment = (VideoGalleryFragment) adapter.instantiateItem(viewPager, viewPager.getCurrentItem());
                }else{
                    ImageGalleryFragment imageFragment = (ImageGalleryFragment) adapter.instantiateItem(viewPager, viewPager.getCurrentItem());
                }
            }
        }
    }


    class TabPagerAdapter extends FragmentPagerAdapter {

        int tabCount;

        public TabPagerAdapter(FragmentManager fm, int numberOfTabs) {
            super(fm);
            this.tabCount = numberOfTabs;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return ImageGalleryFragment.newInstance();
                case 1:
                    return VideoGalleryFragment.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return tabCount;
        }
    }
}

package com.labs.vdrx.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class ImageGalleryFragment extends Fragment {

    public ImageGalleryFragment() {}

    public static ImageGalleryFragment newInstance() {
        ImageGalleryFragment frag = new ImageGalleryFragment();
        return frag;
    }

    /**
     * Oncreate method calls the super class CarsFragment
     *
     * @param savedInstanceState - save data if backgrounded
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);


        return view;
    }
}
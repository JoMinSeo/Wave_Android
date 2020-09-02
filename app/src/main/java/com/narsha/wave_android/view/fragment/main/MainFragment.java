package com.narsha.wave_android.view.fragment.main;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.narsha.wave_android.R;
import com.narsha.wave_android.data.slider.SliderItem;
import com.narsha.wave_android.databinding.FragmentHomeBinding;
import com.narsha.wave_android.view.adapter.imageslider.MainImageSlider;
import com.narsha.wave_android.view.adapter.recyclerview.PlaylistAdapter;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class MainFragment extends Fragment {
    SliderView sliderView;
    private MainImageSlider adapter;
    private PlaylistAdapter[] playlistAdapters;
    private RecyclerView[] recyclerViews;
    private int[] recyclerViewID = {R.id.recyclerView1, R.id.recyclerView2, R.id.recyclerView3};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        playlistAdapters = new PlaylistAdapter[recyclerViewID.length];
        recyclerViews = new RecyclerView[recyclerViewID.length];

        for(int i = 0; i < recyclerViewID.length; i++){
            recyclerViews[i] = view.findViewById(recyclerViewID[i]);
            playlistAdapters[i] = new PlaylistAdapter();
            recyclerViews[i].setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
            recyclerViews[i].setAdapter(playlistAdapters[i]);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        sliderView = root.findViewById(R.id.imageSlider);
        adapter = new MainImageSlider(getContext());

        addNewItem();
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.BLACK);
        sliderView.setIndicatorUnselectedColor(Color.WHITE);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

        return root;
    }
    public void addNewItem() {
        for(int i = 0 ; i < 4; i ++) {
            SliderItem sliderItem = new SliderItem();
            sliderItem.setImageUrl(R.drawable.ic_launcher_background);
            adapter.addItem(sliderItem);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
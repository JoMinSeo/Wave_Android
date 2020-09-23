package com.narsha.wave_android.view.fragment.main;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.narsha.wave_android.R;
import com.narsha.wave_android.data.request.signup.SelectGenre;
import com.narsha.wave_android.data.response.music.List_db;
import com.narsha.wave_android.data.response.music.Song;
import com.narsha.wave_android.data.slider.SliderItem;
import com.narsha.wave_android.network.Server;
import com.narsha.wave_android.view.adapter.imageslider.MainImageSlider;
import com.narsha.wave_android.view.adapter.recyclerview.MainSongAdapter;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends Fragment {
    SliderView sliderView;
    private MainImageSlider adapter;
    private MainSongAdapter adapter_main;
    private RecyclerView recycler_first, recycler_second, recycler_third;
    private Call<List<Song>> listmusic;
    private List<Song> list_musics;
    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recycler_first = view.findViewById(R.id.recycler_first);
        recycler_second = view.findViewById(R.id.recycler_second);
        recycler_third = view.findViewById(R.id.recycler_third);

        adapter_main = new MainSongAdapter(getContext());

        recycler_first.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recycler_second.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recycler_third.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        recycler_first.setAdapter(adapter_main);

        listmusic = Server.getInstance().getApi().getList();

        listmusic.enqueue(new Callback<List<Song>>() {

            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                if(response.code() == 200){
                    list_musics = response.body();
                    adapter_main.setData(list_musics);
                }
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {

            }
        });

        sliderView = view.findViewById(R.id.imageSlider);
        adapter = new MainImageSlider(getContext());

        addNewItem();
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        return root;
    }
    public void addNewItem() {
        for(int i = 0 ; i < 4; i ++) {
            SliderItem sliderItem = new SliderItem();
            sliderItem.setImageUrl(R.drawable.ic_launcher_background);
            adapter.addItem(sliderItem);
        }
    }
}
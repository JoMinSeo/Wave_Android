package com.narsha.wave_android.view.fragment.main;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.narsha.wave_android.R;
import com.narsha.wave_android.data.response.music.RecommendList;
import com.narsha.wave_android.data.response.music.PlayList;
import com.narsha.wave_android.data.slider.SliderItem;
import com.narsha.wave_android.network.Server;
import com.narsha.wave_android.view.adapter.imageslider.MainImageSlider;
import com.narsha.wave_android.view.adapter.recyclerview.MainSongAdapter;
import com.narsha.wave_android.viewmodel.MainViewModel;
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
    private MainSongAdapter adapter_main, adapter_main2, adapter_main3;
    private RecyclerView recycler_first, recycler_second, recycler_third;
    private Call<List<RecommendList>> listmusic;
    private List<RecommendList> list_musics;
    private TextView genre1, genre2, genre3;
    private MainViewModel model;
    private NavController navController;
    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void onItemClick(int index, int position, PlayList playList){
        model.playList.setValue(playList);
        navController.navigate(R.id.action_navigation_home_to_songListFragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        model = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        navController = Navigation.findNavController(view);

        recycler_first = view.findViewById(R.id.recycler_first);
        recycler_second = view.findViewById(R.id.recycler_second);
        recycler_third = view.findViewById(R.id.recycler_third);

        genre1 = view.findViewById(R.id.genre1);
        genre2 = view.findViewById(R.id.genre2);
        genre3 = view.findViewById(R.id.genre3);

        adapter_main = new MainSongAdapter(getContext(),((position, playList) -> onItemClick(1, position, playList)));
        adapter_main2 = new MainSongAdapter(getContext(), ((position, playList) -> onItemClick(2, position, playList)));
        adapter_main3 = new MainSongAdapter(getContext(), ((position, playList) -> onItemClick(3, position, playList)));



        recycler_first.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recycler_second.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recycler_third.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        recycler_first.setAdapter(adapter_main);
        recycler_second.setAdapter(adapter_main);
        recycler_third.setAdapter(adapter_main);

        listmusic = Server.getInstance().getApi().getList();

        listmusic.enqueue(new Callback<List<RecommendList>>() {

            @Override
            public void onResponse(Call<List<RecommendList>> call, Response<List<RecommendList>> response) {
                if(response.code() == 200){
                    list_musics = response.body();
                    adapter_main.setData(list_musics.get(0).getList());
                    adapter_main2.setData(list_musics.get(1).getList());
                    adapter_main3.setData(list_musics.get(2).getList());

                    genre1.setText("#"+list_musics.get(0).getGenreName());
                    genre2.setText("#"+list_musics.get(1).getGenreName());
                    genre3.setText("#"+list_musics.get(2).getGenreName());

                } else if(response.code() == 500) {
                    Toast.makeText(requireContext(), "서버오류", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<RecommendList>> call, Throwable t) {

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
package com.narsha.wave_android.view.fragment.playlist;

import androidx.lifecycle.ViewModelProvider;

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

import com.narsha.wave_android.R;
import com.narsha.wave_android.data.response.music.PlayList;
import com.narsha.wave_android.view.adapter.recyclerview.SongListAdapter;
import com.narsha.wave_android.viewmodel.MainViewModel;

public class SongListFragment extends Fragment {

    private MainViewModel model;

    public static SongListFragment newInstance() {
        return new SongListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_song_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        model = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        PlayList playList = model.playList.getValue();


        if(playList!=null){
            RecyclerView list = view.findViewById(R.id.songs);
            SongListAdapter adapter = new SongListAdapter(playList.getSongs());
            list.setAdapter(adapter);
            list.setLayoutManager(new LinearLayoutManager(requireContext()));
        }
    }
}
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
import com.narsha.wave_android.data.User;
import com.narsha.wave_android.data.response.music.PlayList;
import com.narsha.wave_android.data.response.music.Song;
import com.narsha.wave_android.network.Server;
import com.narsha.wave_android.view.adapter.recyclerview.SongListAdapter;
import com.narsha.wave_android.viewmodel.MainViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SongListFragment extends Fragment {

    private MainViewModel model;
    private Call<PlayList> request;
    private PlayList playList;
    private List<Song> songs;

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

        request = Server.getInstance().getApi().getListInfo(model.playList.getValue());
        request.enqueue(new Callback<PlayList>() {
            @Override
            public void onResponse(Call<PlayList> call, Response<PlayList> response) {
                if(response.code()==200){
                    playList = response.body();
                    songs = playList.getSong();
                }
            }

            @Override
            public void onFailure(Call<PlayList> call, Throwable t) {

            }
        });
    }
}
package com.narsha.wave_android.view.fragment.playlist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.narsha.wave_android.R;
import com.narsha.wave_android.data.response.music.PlayList;
import com.narsha.wave_android.view.adapter.listener.OnItemClickListener;
import com.narsha.wave_android.view.adapter.recyclerview.PlaylistAdapter;
import com.narsha.wave_android.viewmodel.MainViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayListFragment extends Fragment {

    private MainViewModel model;
    private NavController navController;

    public static PlayListFragment newInstance(String param1, String param2) {
        PlayListFragment fragment = new PlayListFragment();
        return fragment;
    }

    private OnItemClickListener listener =  (position, playList)->{
        model.playList.setValue(playList);
        navController.navigate(R.id.action_navigation_home_to_songListFragment);
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_play_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        model = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        // 서버에서 내 플레이리스트 받기
        // 응답 받으면  Adapter 초기화하기 (List<Playlist>, listener)
        // 각 항목이 클릭 되면 SongList로 가기
    }
}
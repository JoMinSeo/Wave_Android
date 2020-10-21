package com.narsha.wave_android.view.fragment.playlist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.narsha.wave_android.R;
import com.narsha.wave_android.data.RequestUser;
import com.narsha.wave_android.data.response.music.PlayList;
import com.narsha.wave_android.network.Server;
import com.narsha.wave_android.view.adapter.listener.OnPlaylistItemClickListener;
import com.narsha.wave_android.view.adapter.recyclerview.PlaylistAdapter;
import com.narsha.wave_android.viewmodel.MainViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayListFragment extends Fragment {

    private MainViewModel model;
    private PlaylistAdapter adapter;
    private Call<List<PlayList>> getMyPlayList;
    private RecyclerView recyclerView;
    private NavController navController;

    public static PlayListFragment newInstance(String param1, String param2) {
        PlayListFragment fragment = new PlayListFragment();
        return fragment;
    }

    private OnPlaylistItemClickListener listener =  (position, playList)->{
        model.playList.setValue(playList);
        navController.navigate(R.id.action_navigation_playlist_to_songListFragment);
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

        navController = Navigation.findNavController(view);

        recyclerView = view.findViewById(R.id.selectedPlayList);

        // 서버에서 내 플레이리스트 받기
        // 응답 받으면  Adapter 초기화하기 (List<Playlist>, listener)
        // 각 항목이 클릭 되면 SongList로 가기
        RequestUser user= new RequestUser();
        user.setUserid(model.user.getValue().getUserId());
        getMyPlayList = Server.getInstance().getApi().getMyPlayList(user);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        getMyPlayList.enqueue(new Callback<List<PlayList>>() {
            @Override
            public void onResponse(Call<List<PlayList>> call, Response<List<PlayList>> response) {
                if(response.code()==200) {
                    List<PlayList> list = response.body();
                    Log.i("list", "list.size" + list.size() + list.get(1).getTitle());

                    adapter = new PlaylistAdapter(list, listener);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(requireContext(), "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PlayList>> call, Throwable t) {
            }
        });
    }
}
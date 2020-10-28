package com.narsha.wave_android.view.fragment.signup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.narsha.wave_android.R;
import com.narsha.wave_android.data.request.genre.Genre;
import com.narsha.wave_android.network.Server;
import com.narsha.wave_android.view.adapter.signup.SongSelectAdapter;
import com.narsha.wave_android.viewmodel.MainViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SongSelectFragment1 extends Fragment {
    private RecyclerView select_Recycler;
    private List<Genre> selectGenreList;
    private SongSelectAdapter adapter;
    private Call<List<Genre>> selectGenre;
    private MainViewModel model;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_song_select1, container, false);

        return root;
    }
    public void addList(){
        selectGenre = Server.getInstance().getApi().getGenre1();

        selectGenre.enqueue(new Callback<List<Genre>>() {
            @Override
            public void onResponse(Call<List<Genre>> call, Response<List<Genre>> response) {
                if(response.code() == 200){
                    selectGenreList = response.body();
                    adapter.setGenreData(selectGenreList);
                }else{
                    Log.i("E",response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Genre>> call, Throwable t) {

            }

        });
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new SongSelectAdapter(getContext(), ((position, id) -> saveSelected(id)));

        addList();

        select_Recycler = view.findViewById(R.id.recyclers);
        select_Recycler.setAdapter(adapter);
        select_Recycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
        Button next = view.findViewById(R.id.next_2);

        model = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        next.setOnClickListener(view1 ->{
            NavController controller = Navigation.findNavController(view);
            controller.navigate(R.id.action_songSelectFragment1_to_songSelectFragment2);
        });
    }

    private void saveSelected(int id){
        model.mainGenreId.setValue(id);
    }
}
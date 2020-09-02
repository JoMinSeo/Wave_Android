package com.narsha.wave_android.view.fragment.signup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.narsha.wave_android.R;
import com.narsha.wave_android.data.request.signup.SelectGenre;
import com.narsha.wave_android.view.adapter.signup.SongSelectAdapter1;

import java.util.ArrayList;
import java.util.List;


public class SongSelectFragment1 extends Fragment {
    private RecyclerView select_Recycler;
    private List<SelectGenre> selectGenreList;
    private SongSelectAdapter1 adapter;

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
        selectGenreList = new ArrayList<SelectGenre>();
        for(int i = 1; i <= 9; i ++){
            selectGenreList.add(new SelectGenre("장르 "+i));
        }
        adapter.setData(selectGenreList);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new SongSelectAdapter1();

        addList();

        select_Recycler = view.findViewById(R.id.recyclers);
        select_Recycler.setAdapter(adapter);
        select_Recycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
        Button next = view.findViewById(R.id.next_2);

        next.setOnClickListener(view1 ->{
            NavController controller = Navigation.findNavController(view);
            controller.navigate(R.id.action_songSelectFragment1_to_songSelectFragment2);
        });
    }
}
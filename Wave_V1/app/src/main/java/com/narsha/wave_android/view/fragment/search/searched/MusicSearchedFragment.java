package com.narsha.wave_android.view.fragment.search.searched;

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
import com.narsha.wave_android.data.searched.SearchedData;
import com.narsha.wave_android.data.viewtype.SearchedViewType;
import com.narsha.wave_android.view.adapter.search.SearchedAllAdapter;

import java.util.ArrayList;

public class MusicSearchedFragment extends Fragment {
    ArrayList<SearchedData> arraySerached = new ArrayList<>();
    public MusicSearchedFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_music_searched, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        arraySerached.add(new SearchedData("사랑이란 멜로는 없어", null, null, SearchedViewType.ViewType.MUSIC));
        arraySerached.add(new SearchedData("그대 행복해야 해요", null, null, SearchedViewType.ViewType.MUSIC));
        arraySerached.add(new SearchedData("사랑은 지날수록 더욱 선명하게 남아", null, null, SearchedViewType.ViewType.MUSIC));
        arraySerached.add(new SearchedData("어디에도", null, null, SearchedViewType.ViewType.MUSIC));
        arraySerached.add(new SearchedData("사계", null, null, SearchedViewType.ViewType.MUSIC));
        arraySerached.add(new SearchedData("넘쳐흘러", null, null, SearchedViewType.ViewType.MUSIC));
        arraySerached.add(new SearchedData("야생화", null, null, SearchedViewType.ViewType.MUSIC));
        arraySerached.add(new SearchedData("오래된 노래", null, null, SearchedViewType.ViewType.MUSIC));
        arraySerached.add(new SearchedData("너를 만나", null, null, SearchedViewType.ViewType.MUSIC));



        RecyclerView recyclerView = view.findViewById(R.id.recyclerSearchedMusic);

        LinearLayoutManager manager
                = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(manager); // LayoutManager 등록
        recyclerView.setAdapter(new SearchedAllAdapter(arraySerached));  // Adapter 등록
    }
}
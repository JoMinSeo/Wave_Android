package com.narsha.wave_android.view.fragment.search.searched;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.narsha.wave_android.R;
import com.narsha.wave_android.data.searched.SearchedData;
import com.narsha.wave_android.data.viewtype.SearchedViewType;
import com.narsha.wave_android.view.adapter.search.SearchedAllAdapter;
import com.narsha.wave_android.view.adapter.search.StateFragment;

import java.util.ArrayList;


public class AllSearchedFragment extends Fragment {

    ArrayList<SearchedData> arraySerached = new ArrayList<>();

    public AllSearchedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_searched, container, false);

        arraySerached.add(new SearchedData("진짜 죽여주는 노래", null, "ㅎㅇ", SearchedViewType.ViewType.MUSIC_BIG));
        arraySerached.add(new SearchedData("아티스트", null, null, SearchedViewType.ViewType.CATEGORY));
        arraySerached.add(new SearchedData("전상근", null, null, SearchedViewType.ViewType.ARTIST));
        arraySerached.add(new SearchedData("곡", null, null, SearchedViewType.ViewType.CATEGORY));
        arraySerached.add(new SearchedData("사랑이란 멜로는 없어", null, null, SearchedViewType.ViewType.MUSIC));
        arraySerached.add(new SearchedData("그대 행복해야 해요", null, null, SearchedViewType.ViewType.MUSIC));
        arraySerached.add(new SearchedData("사랑은 지날수록 더욱 선명하게 남아", null, null, SearchedViewType.ViewType.MUSIC));



        RecyclerView recyclerView = view.findViewById(R.id.recyclerSearched);

        LinearLayoutManager manager
                = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(manager); // LayoutManager 등록
        recyclerView.setAdapter(new SearchedAllAdapter(arraySerached));  // Adapter 등록

        return view;

    }
}
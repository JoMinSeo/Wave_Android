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


public class ArtistSearchedFragment extends Fragment {

    ArrayList<SearchedData> arraySerached = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        arraySerached.add(new SearchedData("전상근", null, null, SearchedViewType.ViewType.ARTIST));
        arraySerached.add(new SearchedData("이수", null, null, SearchedViewType.ViewType.ARTIST));
        arraySerached.add(new SearchedData("나얼", null, null, SearchedViewType.ViewType.ARTIST));
        arraySerached.add(new SearchedData("김범수", null, null, SearchedViewType.ViewType.ARTIST));
        arraySerached.add(new SearchedData("임재현", null, null, SearchedViewType.ViewType.ARTIST));
        arraySerached.add(new SearchedData("박효신", null, null, SearchedViewType.ViewType.ARTIST));




        RecyclerView recyclerView = view.findViewById(R.id.recyclerSearchedArtist);

        LinearLayoutManager manager
                = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(manager); // LayoutManager 등록
        recyclerView.setAdapter(new SearchedAllAdapter(arraySerached));  // Adapter 등록
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_artist_searched, container, false);
    }
}
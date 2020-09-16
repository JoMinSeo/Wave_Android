package com.narsha.wave_android.view.adapter.search;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.narsha.wave_android.view.fragment.search.searched.AllSearchedFragment;
import com.narsha.wave_android.view.fragment.search.searched.ArtistSearchedFragment;
import com.narsha.wave_android.view.fragment.search.searched.MusicSearchedFragment;

public class StateFragment extends FragmentStateAdapter {
    public StateFragment(@NonNull Fragment fragment) {
        super(fragment);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        if(position==0){
                fragment = new AllSearchedFragment();
        } else if(position == 1) {
                fragment = new MusicSearchedFragment();
        }else{
                fragment = new ArtistSearchedFragment();
        }

        return fragment;
    }
    @Override
    public int getItemCount() {
        return 3;
    }
}
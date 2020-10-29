package com.project.wave_v2.widget

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.project.wave_v2.view.fragment.AlbumSearchedFragment
import com.project.wave_v2.view.fragment.AllSearchedFragment
import com.project.wave_v2.view.fragment.ArtistSearchedFragment
import com.project.wave_v2.view.fragment.MusicSearchedFragment

class StateFragment(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        var fragment : Fragment
        if (position == 0) {
            fragment = AllSearchedFragment()
        } else if (position == 1) {
            fragment = AlbumSearchedFragment()
        } else if (position == 2) {
            fragment = MusicSearchedFragment()
        } else {
            fragment = ArtistSearchedFragment()
        }

        return fragment
    }

}
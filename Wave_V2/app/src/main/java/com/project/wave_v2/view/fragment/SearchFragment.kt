package com.project.wave_v2.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.project.wave_v2.R
import com.project.wave_v2.widget.StateFragment
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = StateFragment(this)
        pager.adapter = adapter

        TabLayoutMediator(searched_tab, pager
        ) { tab: TabLayout.Tab, position: Int -> tab.text = if (position + 1 == 1) "전체" else if (position + 1 == 2) "앨범" else if (position + 1 == 3) "곡" else "아티스트" }.attach()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }
}
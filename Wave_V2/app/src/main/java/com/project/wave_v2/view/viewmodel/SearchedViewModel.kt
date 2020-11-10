package com.project.wave_v2.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.narsha.wave_android.view.adapter.search.SearchedAllAdapter
import com.project.wave_v2.data.response.search.SearchModel

class SearchedViewModel : ViewModel(){
    var searchModel : MutableLiveData<SearchModel> = MutableLiveData()
}
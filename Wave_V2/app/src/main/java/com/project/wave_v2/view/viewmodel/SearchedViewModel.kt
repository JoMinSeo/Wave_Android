package com.project.wave_v2.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.wave_v2.data.response.SearchModel

class SearchedViewModel : ViewModel(){
    var searchModel : MutableLiveData<SearchModel> = MutableLiveData()
}
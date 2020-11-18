package com.project.wave_v2.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.narsha.wave_android.view.adapter.search.SearchedAllAdapter
import com.project.wave_v2.data.response.play.PlayModel
import com.project.wave_v2.data.response.playlist.MyPlayListModel
import com.project.wave_v2.data.response.search.SearchModel
import com.project.wave_v2.data.response.search.Song

class SearchedViewModel : ViewModel(){
    var searchModel : MutableLiveData<SearchModel> ?= MutableLiveData()
    var isViewing : MutableLiveData<Boolean> ?= MutableLiveData()
    var playingModel : MutableLiveData<PlayModel> ?= MutableLiveData()
    var playingModelList : MutableLiveData<List<PlayModel>> ?= MutableLiveData()
    var playList : MutableLiveData<List<Song>> ?= MutableLiveData()
    var songTitle : MutableLiveData<String> ?= MutableLiveData()
}
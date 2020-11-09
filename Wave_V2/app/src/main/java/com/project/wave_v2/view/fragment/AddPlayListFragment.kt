package com.project.wave_v2.view.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.project.wave_v2.R
import com.project.wave_v2.data.request.like.LikeFeelBody
import com.project.wave_v2.data.request.playlist.CreatePlayListBody
import com.project.wave_v2.data.response.ResultModel
import com.project.wave_v2.data.response.like.LikeFeelModel
import com.project.wave_v2.data.response.like.LikeGenreModel
import com.project.wave_v2.network.RetrofitClient
import com.project.wave_v2.network.Service
import kotlinx.android.synthetic.main.fragment_add_play_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddPlayListFragment : DialogFragment() {

    var API: Service? = null


    var mainGenreList: List<LikeGenreModel>? = ArrayList<LikeGenreModel>()
    var mainGenreNames: ArrayList<String> = ArrayList<String>()
    var subGenreList: List<LikeFeelModel>? = ArrayList<LikeFeelModel>()
    var subGenreNames: ArrayList<String> = ArrayList<String>()

    lateinit var mainSpinner: Spinner
    lateinit var subSpinner: Spinner
    lateinit var listName : EditText
    lateinit var mainAdapter: ArrayAdapter<*>
    lateinit var subAdapter: ArrayAdapter<*>

    lateinit var mainRequest: Call<List<LikeGenreModel>>
    lateinit var subRequest: Call<List<LikeFeelModel>>

    lateinit var dialogView: View
    lateinit var userId: String

    var mainGenreId = 0
    var subGenreId = 0

    var mainSpinnerListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            mainGenreId = position + 1
            callSubGenre(mainGenreId)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }

    var subSpinnerListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            subGenreId = position + 1
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }

    private fun callMainGenre() {
        mainRequest = RetrofitClient.getService()?.likeGenre()!!
        mainRequest.enqueue(object : Callback<List<LikeGenreModel>> {
            override fun onResponse(
                call: Call<List<LikeGenreModel>>,
                response: Response<List<LikeGenreModel>>
            ) {
                if (response.code() == 200) {
                    mainGenreList = response.body()
                    mainGenreList?.let {
                        mainGenreNames.clear()
                        val iterator = it.iterator()
                        while (iterator.hasNext()) {
                            iterator.next().mainGenreName?.let { mainGenreNames.add(it) }
                        }
                        mainAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<List<LikeGenreModel>>, t: Throwable) {
                Log.i("Dialog", "main response fail=${t.toString()}")
            }
        })
    }

    private fun callSubGenre(mainGenreId: Int) {
        val likeFeelBody = LikeFeelBody(mainGenreId)
        subRequest = RetrofitClient.getService()?.likeFeel(likeFeelBody)!!
        subRequest.enqueue(object : Callback<List<LikeFeelModel>> {
            override fun onResponse(
                call: Call<List<LikeFeelModel>>,
                response: Response<List<LikeFeelModel>>
            ) {
                if (response.code() == 200) {
                    subGenreList = response.body()
                    subGenreList?.let {
                        subGenreNames.clear()
                        val iterator = it.iterator()
                        while (iterator.hasNext()) {
                            iterator.next().subGenreName?.let { subGenreNames.add(it) }
                        }
                        subAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<List<LikeFeelModel>>, t: Throwable) {
                Log.i("Dialog", "main response fail=${t.toString()}")
            }
        })
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        userId = requireActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE)
            .getString("userId", "").toString()

        initWidgets()
        var listener = DialogListener()

        val builder = AlertDialog.Builder(requireActivity())
            .setTitle("플레이리스트 추가하기")
            .setView(dialogView)
            .setPositiveButton("확인", listener)
            .setNegativeButton("취소", listener)
        val alert = builder.create()
        return alert
    }

    private fun initWidgets() {
        dialogView = layoutInflater.inflate(R.layout.fragment_add_play_list, null)
        mainSpinner = dialogView.findViewById(R.id.mainGenre_Sp)
        subSpinner = dialogView.findViewById(R.id.subGenre_Sp)
        mainSpinner.onItemSelectedListener = mainSpinnerListener
        subSpinner.onItemSelectedListener = subSpinnerListener

        listName = dialogView.findViewById(R.id.playlistTitle_Et)

        mainAdapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            mainGenreNames
        )
        mainAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        mainSpinner.adapter = mainAdapter

        subAdapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            subGenreNames
        )
        subAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        subSpinner.adapter = subAdapter

        callMainGenre()
    }

    private fun mainInfoSetting(listName : String) {

        API = RetrofitClient.getService()
        val data = CreatePlayListBody(userId, listName, mainGenreId.toString(), subGenreId.toString())
        API?.createPlayList(data)
            ?.enqueue(object : Callback<ResultModel> {
                override fun onResponse(call: Call<ResultModel>, response: Response<ResultModel>) {
                    if (response.code() == 200) {
                        dismiss()
                    }
                }
                override fun onFailure(call: Call<ResultModel>, t: Throwable) {

                }
            })
        }

    inner class DialogListener : DialogInterface.OnClickListener{
        override fun onClick(dialog: DialogInterface?, which: Int) {
            when(which) {
                DialogInterface.BUTTON_POSITIVE -> { // 확인 버튼
                    if (listName.text.isEmpty()) {
                        Toast.makeText(requireContext(), "플레이리스트의 이름을 입력해주세요", Toast.LENGTH_SHORT).show()
                    } else {
                        mainInfoSetting(listName.text.toString())
                    }
                    Log.d("Logd", "확인버튼입력됨")
                }
                DialogInterface.BUTTON_NEGATIVE -> { // 취소 버튼
                }
            }
        }
    }
}
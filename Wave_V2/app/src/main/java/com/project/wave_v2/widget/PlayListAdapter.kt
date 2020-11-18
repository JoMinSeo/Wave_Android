package com.project.wave_v2.widget

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.wave_v2.R
import com.project.wave_v2.data.request.playlist.PlayListBody
import com.project.wave_v2.data.response.ResultModel
import com.project.wave_v2.data.response.playlist.MyPlayListModel
import com.project.wave_v2.network.RetrofitClient
import com.project.wave_v2.network.Service
import com.project.wave_v2.view.activity.SongListActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class PlayListAdapter(val playList: ArrayList<MyPlayListModel>, val context: Context) : RecyclerView.Adapter<PlayListAdapter.Holder>(){

    var API: Service? = null
    lateinit var retrofit: Retrofit
    private var positionCheck = 0
    private var isStartViewCheck = true


    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var title = itemView.findViewById<TextView>(R.id.item_title_text)
        var img = itemView.findViewById<ImageView>(R.id.item_image_IV)
        var shareButton = itemView.findViewById<Button>(R.id.shareButton)
        var viewAnimationLayout = itemView.findViewById<CardView>(R.id.viewAnimationLayout)
        var deleteButton = itemView.findViewById<Button>(R.id.deleteButton)

        fun bind(list: MyPlayListModel) {
            title.text = list.listName
            retrofit = RetrofitClient.getInstance()
            API = RetrofitClient.getService()
            title.setSelected(true)

            if (isStartViewCheck) {
                if (position > 6) isStartViewCheck = false
            } else {
                if (position > positionCheck) {
                    viewAnimationLayout.animation = AnimationUtils.loadAnimation(context, R.anim.fall_down)
                } else {
                    viewAnimationLayout.animation = AnimationUtils.loadAnimation(context, R.anim.rising_up)
                }
            }

            val glide = Glide.with(itemView.context)
            glide.load(Uri.parse(list.jacket)).into(img)

            deleteButton.setOnClickListener{
                API?.delPlayList(PlayListBody(list.listId))!!.enqueue( object : Callback<ResultModel>{
                    override fun onResponse(call: Call<ResultModel>, response: Response<ResultModel>) {
                        Toast.makeText(context , "성공적으로 삭제되었습니다",Toast.LENGTH_LONG).show()
                        for(i in playList.indices)
                        notifyDataSetChanged()
                    }

                    override fun onFailure(call: Call<ResultModel>, t: Throwable) {
                        Toast.makeText(context , "삭제 실패 메세지 : "+t.message , Toast.LENGTH_LONG).show()
                        Log.d("error", t.message)
                    }

                })
            }
            shareButton.setOnClickListener {
                val alert = AlertDialog.Builder(context)
                        .setTitle("성공적으로 공유가 되었습니다.")
                        .setMessage("리스트 아이디 : ${list.listId}")

                var  build = alert.create()

                alert.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                    build.dismiss()
                }

                build = alert.create()
                build.show()
           }

            itemView.setOnClickListener {
                var intent = Intent(itemView.context, SongListActivity::class.java)
                intent.putExtra("listId", list.listId)
                intent.putExtra("listName", list.listName)
                itemView.context.startActivity(intent)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_playlist, parent, false)
        return  Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(playList[position])
    }

    override fun getItemCount(): Int {
        return playList.size
    }
}
package com.project.wave_v2.widget

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.wave_v2.R
import com.project.wave_v2.data.request.playlist.PlayListDeleteBody
import com.project.wave_v2.data.response.ResultModel
import com.project.wave_v2.data.response.playlist.MyPlayListModel
import com.project.wave_v2.network.RetrofitClient
import com.project.wave_v2.network.Service
import com.project.wave_v2.view.activity.SongListActivity
import com.project.wave_v2.view.fragment.searched.onclick.OnItemClick
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.Serializable

class PlayListAdapter(
    val playList: ArrayList<MyPlayListModel>,
    val context: Context,
    val userId: String,
) : RecyclerView.Adapter<PlayListAdapter.Holder>(), Serializable{

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
                    viewAnimationLayout.animation = AnimationUtils.loadAnimation(
                        context,
                        R.anim.fall_down
                    )
                } else {
                    viewAnimationLayout.animation = AnimationUtils.loadAnimation(
                        context,
                        R.anim.rising_up
                    )
                }
            }

            val glide = Glide.with(itemView.context)
            glide.load(Uri.parse(list.jacket)).into(img)

            deleteButton.setOnClickListener{
                API?.delPlayList(PlayListDeleteBody(userId, list.listId))!!.enqueue(object :
                    Callback<ResultModel> {
                    override fun onResponse(
                        call: Call<ResultModel>,
                        response: Response<ResultModel>
                    ) {
                        Toast.makeText(context, "성공적으로 삭제되었습니다", Toast.LENGTH_LONG).show()
                        for (i in playList.indices) {
                            if (i != 0) {
                                if (playList[i - 1].listId == list.listId) {
                                    playList.remove(list)
                                }
                            } else {
                                if (playList[i].listId == list.listId) {
                                    playList.remove(list)
                                }
                            }
                        }
                        notifyDataSetChanged()
                    }

                    override fun onFailure(call: Call<ResultModel>, t: Throwable) {
                        Toast.makeText(context, "삭제 실패 메세지 : " + t.message, Toast.LENGTH_LONG)
                            .show()
                        Log.d("error", t.message)
                    }

                })
            }
            shareButton.setOnClickListener {
                val view : View = LayoutInflater.from(context).inflate(
                    R.layout.dialog_success_shared,
                    null
                )
                val positiveButton : Button = view.findViewById(R.id.positiveButton)
                val textResult : TextView = view.findViewById(R.id.resultText)

                val alert = AlertDialog.Builder(context)
                    .setView(view)


                var build = alert.create()
                build.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

                textResult.gravity = Gravity.CENTER
                textResult.text = "공유에 성공했습니다.\n리스트 아이디 : ${list.listId}\n친구나 다른 사람들과 공유해보세요!"
                positiveButton.setOnClickListener {
                    build.dismiss()
                }
                build = alert.create()
                build.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

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
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_playlist,
            parent,
            false
        )
        return  Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(playList[position])
    }

    override fun getItemCount(): Int {
        return playList.size
    }
}
package com.project.wave_v2.widget

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.wave_v2.R
import com.project.wave_v2.data.response.playlist.MyPlayListModel
import com.project.wave_v2.view.activity.SongListActivity

class PlayListAdapter(val playList: ArrayList<MyPlayListModel>, val context: Context) : RecyclerView.Adapter<PlayListAdapter.Holder>(){

    private var positionCheck = 0
    private var isStartViewCheck = true


    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var title = itemView.findViewById<TextView>(R.id.item_title_text)
        var img = itemView.findViewById<ImageView>(R.id.item_image_IV)
        var shareButton = itemView.findViewById<Button>(R.id.shareButton)
        var viewAnimationLayout = itemView.findViewById<CardView>(R.id.viewAnimationLayout)


        fun bind(list: MyPlayListModel) {
            title.text = list.listName

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

            shareButton.setOnClickListener {
//                val alert = AlertDialog.Builder(activity)
//                        .setTitle("성공적으로 공유가 되었습니다.")
//                        .setMessage("리스트 아이디 : ${list.listId}")
//                        .setOnDismissListener {
//
//                        }
//
//                val build = alert.create()
//
//                build.show()
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
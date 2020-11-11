package com.project.wave_v2.widget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.project.wave_v2.R
import com.project.wave_v2.data.SliderItem
import com.smarteist.autoimageslider.SliderViewAdapter
import java.util.*

class MainImageSlider(private val context: Context?) : SliderViewAdapter<MainImageSlider.SliderAdapterVH>() {
    private var mSliderItems: MutableList<SliderItem> = ArrayList<SliderItem>()
    fun renewItems(sliderItems: MutableList<SliderItem>) {
        mSliderItems = sliderItems
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        mSliderItems.removeAt(position)
        notifyDataSetChanged()
    }

    fun addItem(sliderItem: SliderItem) {
        mSliderItems.add(sliderItem)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate: View = LayoutInflater.from(parent.context).inflate(R.layout.item_image_slider, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        val sliderItem: SliderItem = mSliderItems[position]
        Glide.with(viewHolder.itemView)
                .load(sliderItem.ImageUrl)
                .fitCenter()
                .dontTransform()
                .into(viewHolder.imageViewBackground)
        viewHolder.itemView.setOnClickListener { Toast.makeText(context, "This is item in position $position", Toast.LENGTH_SHORT).show() }
    }

    override fun getCount(): Int {
        //slider view count could be dynamic size
        return mSliderItems.size
    }

    inner class SliderAdapterVH(itemView: View) : ViewHolder(itemView) {
        lateinit var item: View
        var imageViewBackground: ImageView

        init {
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider)
            item = itemView
        }
    }
}
package com.rejowan.videoapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rejowan.videoapp.R
import com.rejowan.videoapp.databinding.SingleVideoItemBinding
import com.rejowan.videoapp.interfaces.OnItemClicked
import com.rejowan.videoapp.model.VideoModel

class VideoAdapter(
    private val videoList: MutableList<VideoModel>,
    private val onItemClicked: OnItemClicked
) : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            SingleVideoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = videoList[position]

        Glide.with(holder.itemView.context)
            .load(item.thumbnailUrl)
            .placeholder(R.drawable.bg)
            .error(R.drawable.glide_error)
            .into(holder.binding.thumbnail)

        holder.binding.duration.text = item.duration
        holder.binding.uploadTime.text = item.uploadTime
        holder.binding.views.text = item.views
        holder.binding.title.text = item.title
        holder.binding.author.text = item.author

        var isExpanded = false
        holder.binding.description.text = item.description
        holder.binding.description.maxLines = 2

        holder.binding.showMore.setOnClickListener {
            if (isExpanded) {
                holder.binding.description.maxLines = 2
                holder.binding.showMore.text = "Show More"
                isExpanded = false
            } else {
                holder.binding.description.maxLines = Int.MAX_VALUE
                holder.binding.showMore.text = "Show Less"
                isExpanded = true
            }
        }

        holder.binding.description.setOnClickListener {
            if (isExpanded) {
                holder.binding.description.maxLines = 2
                holder.binding.showMore.text = "Show More"
                isExpanded = false
            } else {
                holder.binding.description.maxLines = Int.MAX_VALUE
                holder.binding.showMore.text = "Show Less"
                isExpanded = true
            }
        }

        holder.binding.thumbnail.setOnClickListener {
            onItemClicked.onItemClicked(item)
        }


        if (item.isLive){
            holder.binding.title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.live, 0, 0, 0)
        } else {
            holder.binding.title.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        }




    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    fun updateList(newList: List<VideoModel>) {
        videoList.clear()
        videoList.addAll(newList)
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: SingleVideoItemBinding) : RecyclerView.ViewHolder(binding.root)


}
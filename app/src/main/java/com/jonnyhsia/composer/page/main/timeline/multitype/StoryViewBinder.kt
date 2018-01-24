package com.jonnyhsia.composer.page.main.timeline.multitype

import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.jonnyhsia.composer.R
import com.jonnyhsia.composer.app.GlideApp
import com.jonnyhsia.composer.biz.story.entity.Story
import com.jonnyhsia.composer.kit.ItemClick
import com.jonnyhsia.composer.kit.dp2Px
import com.jonnyhsia.composer.kit.find
import com.jonnyhsia.composer.kit.setOnClickListener
import com.jonnyhsia.composer.kit.setText
import com.jonnyhsia.composer.kit.sp2px
import me.drakeet.multitype.ItemViewBinder

open class StoryViewBinder(
        private val onStoryClick: ItemClick,
        private val onStoryCollect: ItemClick
) : ItemViewBinder<Story, StoryViewBinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val root = inflater.inflate(R.layout.item_story, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, story: Story) {
        val spannableTitle = SpannableString(story.title)
        spannableTitle.setSpan(StyleSpan(Typeface.BOLD), 0, story.title.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)

        val spannableContent = SpannableString(story.content)
        spannableContent.setSpan(AbsoluteSizeSpan(12.sp2px()), 0, story.content.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)

        val spannableString = SpannableStringBuilder(spannableTitle).append("\n").append(spannableContent)
        holder.setText(R.id.tvStoryContent, spannableString)
        holder.itemView.setOnClickListener {
            onStoryClick(holder.adapterPosition)
        }
        holder.setOnClickListener(R.id.imgMore) {

        }
        holder.setOnClickListener(R.id.imgCollect) {
            onStoryCollect(holder.adapterPosition)
        }

        GlideApp.with(holder.itemView)
                .load("http://ou4f31a1x.bkt.clouddn.com/18-1-20/31790037.jpg")
                .transforms(CenterCrop(), RoundedCorners(4.dp2Px()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imgStory)

        when (holder.adapterPosition) {
            0 -> {
                appendItemPadding(holder.itemView, true)
            }
            adapter.itemCount - 1 -> {
                appendItemPadding(holder.itemView, false)
            }
            else -> {
            }
        }
    }

    private fun appendItemPadding(view: View, appendPaddingTop: Boolean) {
        with(view) {
            if (appendPaddingTop) {
                setPaddingRelative(paddingStart, paddingTop + 6.dp2Px(), paddingEnd, paddingBottom)
            } else {
                setPaddingRelative(paddingStart, paddingTop, paddingEnd, paddingBottom + 6.dp2Px())
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgStory: ImageView? = find(R.id.imgStory)
        val imgAvatar: ImageView? = find(R.id.imgAvatar)
    }
}

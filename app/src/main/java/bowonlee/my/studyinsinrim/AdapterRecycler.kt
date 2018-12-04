package bowonlee.my.studyinsinrim


import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import bowonlee.my.studyinsinrim.databinding.ItemRecyclerviewYoutubeBinding
import com.bumptech.glide.Glide

import com.google.api.services.youtube.model.SearchResult




class AdapterRecycler(val parentContext: Context) : RecyclerView.Adapter<ListHolderSearchItem>() {
    private var searchResultList : List<SearchResult>? = null


    override fun onBindViewHolder(holder: ListHolderSearchItem, position: Int) {
        searchResultList?.get(position)?.let { holder.setItem(it) }



    }
    override fun getItemCount(): Int {
        return searchResultList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolderSearchItem {
        return ListHolderSearchItem(ItemRecyclerviewYoutubeBinding.inflate(LayoutInflater.from(parent.context)),parentContext)
    }

    fun setSearchResult(searchResultList: List<SearchResult>){
        this.searchResultList = searchResultList
    }

}


class ListHolderSearchItem : RecyclerView.ViewHolder, View.OnClickListener{
    private var viewhloderBinder : ItemRecyclerviewYoutubeBinding
    private var parentContext : Context
    private lateinit var videoId:String


    constructor(itemView:ItemRecyclerviewYoutubeBinding,parentContext:Context) : super(itemView.root) {
        this.viewhloderBinder = itemView
        this.parentContext = parentContext

        itemView.root.setOnClickListener(this)
    }
    fun setItem(result : SearchResult){
        Log.d("IN HOLDER","${result.snippet.title}, ${result.snippet.thumbnails.default.url}")
        videoId = result.id.videoId

//        Glide.with(this.itemView).load(result.snippet.thumbnails.default.url).into(viewhloderBinder.imageViewThumnail)
        GlideApp.with(this.itemView).load(result.snippet.thumbnails.default.url).centerInside().into(viewhloderBinder.imageViewThumnail)
        viewhloderBinder.textviewTitleViewholder.setText(result.snippet.title)
        viewhloderBinder.textviewUploaderViewholder.setText(result.snippet.channelTitle)
//        viewhloderBinder.textviewInfoViewholder.setText(result.snippet.publishedAt.toStringRfc3339())
    }
    override fun onClick(p0: View?) {
        startPlayer(videoId)
    }


    private fun startPlayer(videoId :String){
        val intent = Intent(parentContext,MyYoutubeActivity::class.java)
        intent.putExtra("youtubeKey",videoId)
        parentContext.startActivity(intent)
    }

}


// 미리보기 이미지, 제목, 작성자, 기타 정보
// TODO 일단 받아오고 작성
//data class YoutubeSearchListItems()
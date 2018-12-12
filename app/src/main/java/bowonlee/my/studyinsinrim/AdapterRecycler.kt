package bowonlee.my.studyinsinrim


import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import bowonlee.my.studyinsinrim.databinding.ItemRecyclerviewYoutubeBinding
import com.google.api.services.youtube.YouTube

import com.google.api.services.youtube.model.SearchResult
import com.google.api.services.youtube.model.Video
import com.google.api.services.youtube.model.VideoListResponse



class AdapterRecycler : RecyclerView.Adapter<ListHolderSearchItem>() {


    private var searchResultList : List<SearchResult>? = null


    override fun onBindViewHolder(holder: ListHolderSearchItem, position: Int) {
        searchResultList?.get(position)?.let { holder.setItem(it) }
    }
    override fun getItemCount(): Int {
        return searchResultList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolderSearchItem {
        return ListHolderSearchItem(ItemRecyclerviewYoutubeBinding.inflate(LayoutInflater.from(parent.context)))
    }

    fun setSearchResult(searchResultList: List<SearchResult>){
        this.searchResultList = searchResultList
        notifyDataSetChanged()
    }


}


class ListHolderSearchItem : RecyclerView.ViewHolder, View.OnClickListener{
    private var viewhloderBinder : ItemRecyclerviewYoutubeBinding
    private var parentContext : Context
    private var videoDetail : YouTube.Videos.List
    private lateinit var videoId:String



    constructor(itemView:ItemRecyclerviewYoutubeBinding) : super(itemView.root) {
        this.viewhloderBinder = itemView
        this.parentContext = itemView.root.context
        this.videoDetail = getYoutube().videos().list("contentDetails,statistics")
        this.videoDetail.setKey(getYoutubeDataAPIKey(this.parentContext))
        itemView.root.setOnClickListener(this)
    }

    fun setItem(result : SearchResult){
        Log.d("IN HOLDER","${result.id}")
        videoId = result.id.videoId

        VideoDetailsTask().execute(SearchPair(videoDetail,videoId))
        /**
         * 인자 : Youtube.Video.List객체 , VideoId
         * 반환 : VideoListResponse
         *
         * 동작 : ViewHolder에 부가정보 데이터(조회수, 동영상 시간 등)를 업데이트
         * */
        GlideApp.with(this.itemView).load(result.snippet.thumbnails.default.url).centerInside().into(viewhloderBinder.imageViewThumnail)
        viewhloderBinder.textviewTitleViewholder.setText(result.snippet.title)
        viewhloderBinder.textviewUploaderViewholder.setText(result.snippet.channelTitle)
        viewhloderBinder.textviewInfoViewholder.setText(result.snippet.publishedAt?.toString())

    }


    override fun onClick(p0: View?) {
        startPlayer(videoId)
    }


    private fun startPlayer(videoId :String){
        val intent = Intent(parentContext,MyYoutubeActivity::class.java)
        intent.putExtra("youtubeKey",videoId)
        parentContext.startActivity(intent)
    }

    inner class VideoDetailsTask : AsyncTask< SearchPair<YouTube.Videos.List> , Unit, MutableList<Video>?>() {

        override fun doInBackground(vararg searchPair: SearchPair<YouTube.Videos.List>): MutableList<Video>? {
            val videos = searchPair.get(0).type
            val videoId = searchPair.get(0).query

            videos.setId(videoId)

            var response : VideoListResponse = videos.execute()

            return response.items
        }

        override fun onPostExecute(result: MutableList<Video>?) {
            super.onPostExecute(result)

            result?.forEach { Log.e("asd","${it.contentDetails.duration} == ${ parseDuration(it.contentDetails.duration) }") }

            viewhloderBinder.textviewDuration?.setText(result?.get(0)?.contentDetails?.duration?.let { parseDuration(it) })
            viewhloderBinder.textviewViewcountViewholder?.setText("${result?.get(0)?.statistics?.viewCount?.let { parseViewcount(it) }}회")

        }

    }
}


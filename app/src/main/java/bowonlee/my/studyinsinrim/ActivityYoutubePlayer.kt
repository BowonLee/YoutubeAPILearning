package bowonlee.my.studyinsinrim

import android.databinding.DataBindingUtil
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import bowonlee.my.studyinsinrim.databinding.ActivityYoutubeplayerBinding
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.SearchListResponse
import com.google.api.services.youtube.model.SearchResult
import com.google.api.services.youtube.model.Video
import com.google.api.services.youtube.model.VideoListResponse
import kotlinx.android.synthetic.main.activity_youtubeplayer.*

/**
 * 비디오의 Id를 이용하여 Video에 대한 정보를 다시 한번 불러온다
 * 이전 정보에서 가져 올 정보
 * [VideoID]
 * 새로 요청할 정보
 * [video 전부 다]
 *
 * */
class ActivityYoutubePlayer : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    private lateinit var videoId:String
    private lateinit var youtubeplayerBinding : ActivityYoutubeplayerBinding
    private lateinit var video :YouTube.Videos.List
    private var isFullScreen:Boolean = false
    private val mAdapterListView = AdapterRecycler()

    private lateinit var search:YouTube.Search.List

    private var mYoutubePlayer : YouTubePlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        youtubeplayerBinding = DataBindingUtil.setContentView(this,R.layout.activity_youtubeplayer)
        videoId = intent.extras.getString("youtubeKey")
        startPlayer()
        video = getVideo(this,"snippet,contentDetails,statistics")
        VideoInfoTask().execute(SearchPair(video,videoId))
        setShowDiscriptionEvent()

        setForSearch()
        setListviewRelated(videoId)
    }

    fun startPlayer(){
        youtubeplayerBinding.youtubeplayer.initialize(getYoutubeDataAPIKey(this),this )

    }

    fun setShowDiscriptionEvent(){
        youtubeplayerBinding.checkboxPlayerShowDescription.setOnCheckedChangeListener { compoundButton, status ->
            if(status){
                youtubeplayerBinding.textviewPlayerDiscription.visibility = View.VISIBLE
            }else{
                youtubeplayerBinding.textviewPlayerDiscription.visibility = View.GONE
            }

        }
    }

    fun setListviewRelated(videoId:String){

        youtubeplayerBinding.listviewPlayerRelated.layoutManager = object : LinearLayoutManager(this){
            override fun canScrollVertically(): Boolean {
                return false
            }
        }

        youtubeplayerBinding.listviewPlayerRelated.adapter = mAdapterListView

        RelateVideoTask().execute(SearchPair(search,videoId))
        /**
         * @param search : 검색을 실행하기 위한 유튜브 객체
         * @param videoId : 현제 실행되는 유튜브 영상
         *
         * 현제 출력되는 영상의 관련된 영상 목록을 찾아 리사이클러 뷰에 업데이트 시킨다.
         * */
    }



    /**
     * MainActivity에서 객체와 중복 사용
     * */
    fun setForSearch(){
        // 여기서 구체적으로 어떤 정보를 가져오는지 문자 열 형태로 정의하는 듯 하다.
        search = getYoutube().search().list("id,snippet")
        search.setKey(getYoutubeDataAPIKey(this))
        search.setType("video")
        search.setFields("items(id,snippet),nextPageToken,pageInfo")
        search.setOrder("viewCount")
        search.setMaxResults(MAX_RESULT)
    }

    override fun onBackPressed() {

        if(isFullScreen){
            mYoutubePlayer?.setFullscreen(false)
            return
        }else {
            super.onBackPressed()
        }

    }
    /**
     * VideoId를 활용하여 비디오의 세부정보들을 가져오고, 또한 UI를 셋팅하는 부분
     * */
    inner class VideoInfoTask: AsyncTask<SearchPair<YouTube.Videos.List>, Unit, MutableList<Video>?>() {

        override fun doInBackground(vararg videoPair: SearchPair<YouTube.Videos.List>?): MutableList<Video>? {

            val response : VideoListResponse? = videoPair.get(0)?.type?.setId(videoPair.get(0)?.query)?.execute()
            return response?.items
        }

        override fun onPostExecute(result: MutableList<Video>?) {
            super.onPostExecute(result)
            val video = result?.get(0)
            youtubeplayerBinding.textviewPlayerVideoTitle?.setText(video?.snippet?.title?.trim())
            youtubeplayerBinding.textViewPlayerViewcount?.setText(video?.statistics?.viewCount?.let { parseViewcount(it) })
            youtubeplayerBinding.textviewPlayerLikecount?.setText(video?.statistics?.likeCount?.let { parseViewcount(it) })
            youtubeplayerBinding.textviewPlayerDislikecount?.setText(video?.statistics?.dislikeCount?.let { parseViewcount(it) })
            youtubeplayerBinding.textviewPlayerDiscription?.setText(video?.snippet?.description )
        }
    }

    inner class RelateVideoTask: AsyncTask<SearchPair<YouTube.Search.List>, Unit, SearchListResponse?>(){
        private var mSearchResultList : List<SearchResult>? = null
        override fun doInBackground(vararg searchPair: SearchPair<YouTube.Search.List>?): SearchListResponse? {
            return searchPair.get(0)?.type?.setRelatedToVideoId(searchPair.get(0)?.query)?.execute()
        }

        override fun onPostExecute(result: SearchListResponse?) {
            mSearchResultList = result?.items
            mSearchResultList?.let { mAdapterListView.setSearchResult(it) }

        }
    }



    /**
     * player 시작 설정
     * */
    override fun onInitializationFailure(provider: YouTubePlayer.Provider?, error: YouTubeInitializationResult?) {
        //TODO 생성 실패시 처리
    }
    override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, player: YouTubePlayer?, wasRestored: Boolean) {
        if(isConnectedWifi(this@ActivityYoutubePlayer)) {
            player?.loadVideo(videoId)

        }else{
            player?.cueVideo(videoId)
        }

        player?.setOnFullscreenListener {
            this@ActivityYoutubePlayer.isFullScreen = it
         if(isFullScreen){ youtubeplayerBinding.scrollView2.visibility = View.GONE }
         else{ youtubeplayerBinding.scrollView2.visibility = View.VISIBLE }
        }
        this@ActivityYoutubePlayer.mYoutubePlayer = player
    }

}
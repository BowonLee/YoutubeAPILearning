package bowonlee.my.studyinsinrim


import android.databinding.DataBindingUtil
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import bowonlee.my.studyinsinrim.databinding.ActivityMainBinding

import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.SearchListResponse
import com.google.api.services.youtube.model.SearchResult



const val MAX_RESULT : Long = 8L

class MainActivity : AppCompatActivity() {

    /**
    * youtube의 검색과 사용을 위한 예제
    * */
    lateinit var mainBinding:ActivityMainBinding

    lateinit var search:YouTube.Search.List
    lateinit var videos:YouTube.Videos.List

    lateinit var adapterListView : AdapterRecycler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main)
        setForSearch()
        setVideos()
        setRecyclerView()
        setSearchBtn()

    }
    fun setRecyclerView(){
        adapterListView = AdapterRecycler()
        mainBinding.recyclerViewInMain.layoutManager = LinearLayoutManager(this)
        mainBinding.recyclerViewInMain.adapter = adapterListView
    }

    fun setSearchBtn(){
        mainBinding.buttonSearchMain.setOnClickListener {
            YoutubeSearchTask().execute(SearchPair(search, mainBinding.searchBarInMain.text.toString()))
            /**
            * 인자 : Youtube.Search.List객체 , 검색어
            * 반환 : SearchResponse
            *
            * 동작 : List에 해당 데이터를 업데이트
            * */

        }
    }

    //TODO 함수명은 적당한 이름으로 추후 리펙토링
    /*
    * 검색을 위한 셋팅
    * */
    fun setForSearch(){
        // 여기서 구체적으로 어떤 정보를 가져오는지 문자 열 형태로 정의하는 듯 하다.
        search = getYoutube().search().list("id,snippet")
        search.setKey(getYoutubeDataAPIKey(this))
        search.setType("video")
        search.setFields("items(id,snippet)")
        search.setMaxResults(MAX_RESULT)

    }
    fun setVideos(){
        videos = getYoutube().videos().list("contentDetails,statistics")
        videos.setKey(getYoutubeDataAPIKey(this))
    }


    inner class YoutubeSearchTask : AsyncTask<SearchPair<YouTube.Search.List>, Unit, List<SearchResult>>() {
        lateinit var mSearchResultList : List<SearchResult>
        lateinit var searchResponse : SearchListResponse
        lateinit var search : YouTube.Search.List
        override fun doInBackground(vararg searchPair: SearchPair<YouTube.Search.List>): List<SearchResult> {

            search = searchPair.get(0).type.setQ(searchPair.get(0).query)
            searchResponse = search.execute()

            mSearchResultList = searchResponse.items
            Log.i("PAGE TOKEN","pageInfo ${searchResponse.kind}")

            return mSearchResultList
        }

        override fun onPostExecute(result: List<SearchResult>?) {
            super.onPostExecute(result)

            result?.forEach {
                val thumnail = it.snippet.thumbnails.get("default")
                Log.d("SEARCH ITEMS"," ID : ${it.id.videoId} TITLE : ${it.snippet.title}  URL" +
                        " : ${thumnail} ")
            }

            result?.let { adapterListView.setSearchResult(it)
                Log.d("Is It run?","Excuted")}
            mainBinding.recyclerViewInMain.invalidate()

        }
    }


}



/**
 * taketoken = String ,null
 * 최초에는 null이 들어온다.
 * Java.textUtils isEmpty
 *
 * */





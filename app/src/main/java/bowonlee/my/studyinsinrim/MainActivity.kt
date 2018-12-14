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
    private lateinit var mainBinding:ActivityMainBinding
    private lateinit var search:YouTube.Search.List
    private lateinit var mAdapterListView : AdapterRecycler
    private var nextPageToken : String? = null
    private lateinit var searchText:String
    private lateinit var mSearchItemsList : MutableList<SearchResult>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main)
        setForSearch()
        setRecyclerView()
        setSearchBtn()

    }

    fun setSearchBtn(){
        mainBinding.buttonSearchMain.setOnClickListener {
            searchText = mainBinding.searchBarInMain.text.toString()
            YoutubeSearchTask().execute(SearchPair(search, searchText))
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
    fun setRecyclerView(){
        mAdapterListView = AdapterRecycler()
        mainBinding.recyclerViewInMain.layoutManager = LinearLayoutManager(this)
        mainBinding.recyclerViewInMain.adapter = mAdapterListView
        mAdapterListView.setOnScrollListener(object : AdapterRecycler.OnScrollListener {
            override fun onScrollLast() {
                //TODO 이전 페이지의 페이지 토큰을 이용하여 다음 페이지를 요청한다.
                nextPageToken?.let {
                    search.setPageToken(it)
                    YoutubeSearchTask().execute(SearchPair(search, searchText))
                }
            }
        })
        mSearchItemsList = mutableListOf()

    }
    fun setForSearch(){
        // 여기서 구체적으로 어떤 정보를 가져오는지 문자 열 형태로 정의하는 듯 하다.
        search = getYoutube().search().list("id,snippet")
        search.setKey(getYoutubeDataAPIKey(this))
        search.setType("video")
        search.setFields("items(id,snippet),nextPageToken,pageInfo")
        search.setMaxResults(MAX_RESULT)
    }

    inner class YoutubeSearchTask : AsyncTask<SearchPair<YouTube.Search.List>, Unit, SearchListResponse>() {
        private var tSearchResultList : List<SearchResult>? = null
        private lateinit var tSearchResponse : SearchListResponse
        private lateinit var tSearch : YouTube.Search.List
        override fun doInBackground(vararg searchPair: SearchPair<YouTube.Search.List>): SearchListResponse {
            search = searchPair.get(0).type.setQ(searchPair.get(0).query)
            tSearchResponse = search.execute()
            return tSearchResponse
        }

        override fun onPostExecute(result: SearchListResponse?) {
            super.onPostExecute(result)

            tSearchResultList = result?.items
            nextPageToken = result!!.nextPageToken
            tSearchResultList?.let {
                mSearchItemsList.addAll(it)
                mAdapterListView.setSearchResult(mSearchItemsList)
            }


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





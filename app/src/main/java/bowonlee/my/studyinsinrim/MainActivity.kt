package bowonlee.my.studyinsinrim


import android.databinding.DataBindingUtil
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import bowonlee.my.studyinsinrim.databinding.ActivityMainBinding
import com.google.api.client.http.HttpRequest
import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.http.HttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.SearchListResponse
import com.google.api.services.youtube.model.SearchResult
import java.io.IOException


const val tempString = "아이유"

class MainActivity : AppCompatActivity() {


    /*
    * 단순 검색 기능을 구현하기 위한 임시 텍스트
    * */

    /**
    * youtube의 검색과 사용을 위한 예제
    * */
    lateinit var mainBinding:ActivityMainBinding
    lateinit var youTube: YouTube
    var HTTP_TRANSPORT : HttpTransport = NetHttpTransport()
    var JSON_FACTORY : JacksonFactory = JacksonFactory()

    lateinit var mSearchResultList : List<SearchResult>
    lateinit var searchResponse : SearchListResponse
    lateinit var search:YouTube.Search.List

    lateinit var adapterListView : AdapterRecycler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main)
        setRecyclerView()
        setForSearch()
        setSearchBtn()

    }
    fun setRecyclerView(){
        adapterListView = AdapterRecycler(this)
        mainBinding.recyclerViewInMain.layoutManager = LinearLayoutManager(this)
        mainBinding.recyclerViewInMain.adapter = adapterListView
    }
    fun setSearchBtn(){
        mainBinding.buttonSearchMain.setOnClickListener {
            //TODO 검색 작업 시작
            excuteSearch(tempString)

            /**
            * 요청 : 검색어
            * 반환 : SearchResult
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
        youTube = YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY , object : HttpRequestInitializer {
            @Throws(IOException::class)
            override fun initialize(request: HttpRequest) {
            }
        }).setApplicationName("youtube-cmdline-search-sample").build()

        search = youTube.search().list("id,snippet")
        search.setKey(getYoutubeDataAPIKey(this))
        search.setType("video")

        search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
        search.setMaxResults(5L)

        /**
         * 가져오는 작업 확인
         *
         * */


    }

    fun excuteSearch(searchText : String){
        /**
         * 이후 입력을 받아 검색하도록 하면 아해의 tempString을 searchText로 변경
         * */
        search.setQ(tempString)
        object : AsyncTask<Unit, Unit, List<SearchResult>>() {
            override fun doInBackground(vararg p0: Unit?): List<SearchResult> {
                searchResponse = search.execute()

                mSearchResultList = searchResponse.items
                Log.i("PAGE TOKEN","pageInfo ${searchResponse}")
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
        }.execute()

    }

    /**
     * taketoken = String ,null
     * 최초에는 null이 들어온다.
     * Java.textUtils isEmpty
     *
     * */

}

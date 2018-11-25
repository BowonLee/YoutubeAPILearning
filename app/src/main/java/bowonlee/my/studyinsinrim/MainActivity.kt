package bowonlee.my.studyinsinrim

import android.app.Activity
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import bowonlee.my.studyinsinrim.databinding.ActivityMainBinding
import com.fasterxml.jackson.core.JsonFactory
import com.google.api.client.http.HttpRequest
import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.http.HttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.SearchListResponse
import com.google.api.services.youtube.model.SearchResult


import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.HTTP
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {

    /*
    * youtube의 검색과 사용을 위한 예제
    * */
    lateinit var mainBinding:ActivityMainBinding
    lateinit var youTube: YouTube
    lateinit var retrofit: Retrofit
    var HTTP_TRANSPORT : HttpTransport = NetHttpTransport()
    var JSON_FACTORY : JacksonFactory = JacksonFactory()

    lateinit var mSearchResultList : List<SearchResult>
    lateinit var searchResponse : SearchListResponse

    lateinit var search:YouTube.Search.List

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main)

        setForSearch()

    }
    fun setSearchBtn(){
        mainBinding.buttonSearchMain.setOnClickListener {
            //TODO 검색 작업 시작
            it->
            /*
            * 요청 : 검색어
            *
            * */
        }
    }

    //TODO 함수명은 적당한 이름으로 추후 리펙토링
    /*
    * 검색을 위한 셋팅
    * */
    fun setForSearch(){
//        retrofit = Retrofit.Builder().baseUrl("https://www.googleapis.com/youtube/v3/search").build()
        // 여기서 구체적으로 어떤 정보를 가져오는지 문자 열 형태로 정의하는 듯 하다.

        search = youTube.search().list("id,snippet")
        search.setKey(getYoutubeAPIKey())
        search.setType("vedio")
        search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")

        youTube = YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY , object : HttpRequestInitializer {
            @Throws(IOException::class)
            override fun initialize(request: HttpRequest) {
            }
        }).setApplicationName("youtube-cmdline-search-sample").build()
    }
    fun startSearch(){


    }

    fun getYoutubeAPIKey():String{
        return when((Math.random()%5).toInt()){
            0->getString(R.string.YOUTUBEAPIKEY1)
            1->getString(R.string.YOUTUBEAPIKEY2)
            2->getString(R.string.YOUTUBEAPIKEY3)
            3->getString(R.string.YOUTUBEAPIKEY4)
            else-> getString(R.string.YOUTUBEAPIKEY5)
        }
    }

}

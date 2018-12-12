package bowonlee.my.studyinsinrim

import android.content.Context
import com.google.api.client.http.HttpRequest
import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.http.HttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube
import java.io.IOException

val HTTP_TRANSPORT : HttpTransport = NetHttpTransport()
val JSON_FACTORY : JacksonFactory = JacksonFactory()
/**
 * 쿼리 수 제한을 해결하기 위해 여러게를 받은 유튜브 키 중 하나를 리턴한다.
 * @param Context
 * @return String
 *
 * **/
fun <T : Context?> getYoutubeDataAPIKey(context: T): String? {
    return context?.resources?.getStringArray(R.array.ARRAY_OF_YOUTUBEKEY)?.get((Math.random()%5).toInt())
}



fun getYoutube(): YouTube{

    return YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY , object : HttpRequestInitializer {
        @Throws(IOException::class)
        override fun initialize(request: HttpRequest) {
        }
    }).setApplicationName("youtube-cmdline-search-sample").build()

}
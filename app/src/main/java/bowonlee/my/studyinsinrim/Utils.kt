package bowonlee.my.studyinsinrim

import android.content.Context


/**
 * 쿼리 수 제한을 해결하기 위해 여러게를 받은 유튜브 키 중 하나를 리턴한다.
 * @param Context
 * @return String
 *
 * **/
public fun getYoutubeDataAPIKey(context:Context):String{
    return context.getString(when((Math.random()%5).toInt()){
        1 -> R.string.YOUTUBEAPIKEY1
        2 -> R.string.YOUTUBEAPIKEY2
        3 -> R.string.YOUTUBEAPIKEY3
        4 -> R.string.YOUTUBEAPIKEY4
        else -> R.string.YOUTUBEAPIKEY5
    })

}
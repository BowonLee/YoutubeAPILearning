package bowonlee.my.studyinsinrim

import android.os.Bundle
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment


class FragmentYoutubePlayer : YouTubePlayerSupportFragment(){

    override fun onCreate(p0: Bundle?) {
        super.onCreate(p0)



    }

    fun startPlayer(){

        initialize(getYoutubeDataAPIKey(this.context),object :YouTubePlayer.OnInitializedListener{
            override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

            }

            override fun onInitializationSuccess(p0: YouTubePlayer.Provider?, p1: YouTubePlayer?, p2: Boolean) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })

    }


}
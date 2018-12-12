package bowonlee.my.studyinsinrim

import android.databinding.DataBindingUtil
import android.os.Bundle
import bowonlee.my.studyinsinrim.databinding.ActivityYoutubeplayerBinding
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer

class MyYoutubeActivity : YouTubeBaseActivity() {
    private lateinit var  videoId:String
    private lateinit var  youtubeplayerBinding : ActivityYoutubeplayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        youtubeplayerBinding = DataBindingUtil.setContentView(this,R.layout.activity_youtubeplayer)
        videoId = intent.extras.getString("youtubeKey")

        startPlayer()

    }

    fun startPlayer(){
        youtubeplayerBinding.youtubeplayer.initialize(getYoutubeDataAPIKey(this),object : YouTubePlayer.OnInitializedListener{
            override fun onInitializationFailure(provider: YouTubePlayer.Provider?, error: YouTubeInitializationResult?) {
                //TODO 생성 실패시 처리
            }
            override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, player: YouTubePlayer?, wasRestored: Boolean) {
//               p1.cueVideo() - 정지된 상태로 시작하는 비디오
                player?.loadVideo(videoId)
            }

        })
    }

}
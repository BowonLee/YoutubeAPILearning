package bowonlee.my.studyinsinrim

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bowonlee.my.studyinsinrim.databinding.FragmentSearchResultListBinding
import com.google.api.services.youtube.YouTube

/**
 * 검색 결과의 리스트가 보여지는 Fragment
 * 검색 동작을 실행하면 생성 + 실행 + 업데이트하여 사용한다.
 * 검색 결과를 인자로 받는 메서드
 *
 * 생성자의 조건 ?
 * 만들면 재미있을 만한 service
 * */

/**
 * Action : 쿼리를 입력받는 경우, 검색을 실행한 뒤 해당 검색 결과를 리스트로 출력해준다
 * */


/**
 * SearchPair 타입을 인자로 받아 해당 타입을 다시 이용 하도록 한다.
 * 검색 쿼리의 조건은 별도의 메서드를 통해 설정 하도록 한다.
 *
 * */
class SearchResultListFragment : Fragment() {

    private lateinit var componentBinder : FragmentSearchResultListBinding
    var searchQuery : String = ""
    lateinit var search : YouTube.Search.List
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        componentBinder = DataBindingUtil.setContentView(requireActivity(),R.layout.fragment_search_result_list)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        inflater.inflate(R.layout.fragment_search_result_list,container,false)

        return super.onCreateView(inflater, container, savedInstanceState)
    }


    public fun setSearchQ(seachQuary : String){

    }
    fun setForSearch(){
        // 여기서 구체적으로 어떤 정보를 가져오는지 문자 열 형태로 정의하는 듯 하다.
        search = getYoutube().search().list("id,snippet")
        search.setKey(getYoutubeDataAPIKey(this.context))
        search.setType("video")
        search.setFields("items(id,snippet),nextPageToken,pageInfo")
        search.setMaxResults(MAX_RESULT)
    }

}
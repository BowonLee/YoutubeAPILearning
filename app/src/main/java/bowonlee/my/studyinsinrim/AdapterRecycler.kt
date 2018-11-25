package bowonlee.my.studyinsinrim

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.TextView
import bowonlee.my.studyinsinrim.databinding.ActivityMainBinding
import bowonlee.my.studyinsinrim.databinding.ItemRecyclerviewYoutubeBinding

class AdapterRecycler : RecyclerView.Adapter<ListHolderSearchItem>() {



    override fun onBindViewHolder(holder: ListHolderSearchItem, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolderSearchItem {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview_youtube,parent,false)
        var holder = ListHolderSearchItem(view)
        //TODO holder의 클릭리스너 구현
        return holder
    }

}


class ListHolderSearchItem : RecyclerView.ViewHolder, View.OnClickListener{
    private lateinit var viewhloderBinder : ItemRecyclerviewYoutubeBinding

    constructor(itemView: View?) : super(itemView) {



    }


    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

// 미리보기 이미지, 제목, 작성자, 기타 정보
// TODO 일단 받아오고 작성
//data class YoutubeSearchListItems()
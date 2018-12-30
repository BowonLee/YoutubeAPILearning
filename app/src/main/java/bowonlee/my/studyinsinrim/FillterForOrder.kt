package bowonlee.my.studyinsinrim

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.content.DialogInterface
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bowonlee.my.studyinsinrim.databinding.DialogFillterOrdersBinding

private var orderQueryType : String = ""
private var orderQueryOrder : String = ""


class DialogFillterForOrder : DialogFragment() {


    companion object {
        fun newInstance(mainMsg : String): DialogFillterForOrder {
            val bundle : Bundle = Bundle()
            bundle.putString("asd",mainMsg)
            val fragment : DialogFillterForOrder = DialogFillterForOrder()
            fragment.arguments = bundle
            return fragment
        }
    }
//    lateinit var binder : DialogFillterOrdersBinding

    /**
     *
     * binder의 사용에 있어서 상위 엑티비티의 내용을 자신의 것으로 덮어 씌우는 현상이 발생하였다.
     * binding 할 대상을 지정하는 대 있어 주의가 필요 할 것 같다.
     * */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder : AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setTitle("검색 조건")
        builder.setView(this.activity.layoutInflater.inflate(R.layout.dialog_fillter_orders,null))

        builder.setPositiveButton("positive", DialogInterface.OnClickListener { dialogInterface, id ->

        }).setNegativeButton("negative", DialogInterface.OnClickListener { dialogInterface, i ->
            dissmissDialog()
        })
        return builder.create()
    }

    private fun dissmissDialog(){
        this.dismiss()
    }
}



/**
 * 검색 필터의 구성
 * 1. 정렬 기준 : 관련성, 업로드 날자, 조회수, 평점 -  순
 * 2. 구분 : 동영상, 채널, 재생목록, 영화, 프로그램
 * 3. 업로드 날자 : 전체, 1시간, 오늘 이번주 이번 달, 올해
 * 4. 길이 : 짧은(4분 이하), 긴(20분 이상)
 * 기능별
 * <테그 형태의 정보를 통해 정렬 - 위의 필터 구현 뒤>
 *
 * positive : 적용
 * negative : 취소
 * */
fun getOrderQueryType():String{
    return orderQueryType
}

fun getOrderQueryOrder():String{
    return orderQueryOrder
}
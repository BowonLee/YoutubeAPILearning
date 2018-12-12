package bowonlee.my.studyinsinrim


import java.math.BigInteger


/**
 * PT#M#S 형식으로 전달 된 시간 데이터를 파싱하는 메서드
 * java8 이상, Oreo 버전 이상부터는 Duration 클레스를 이용할 수 있지만
 * 이전 버전은 대응이 불가능하기에 별도의 파싱 메서드를 이용하도록 한다.
 * @param String (PT#M#S) 타입의 시간 문자열
 * @return String (hh:mm:ss)타입의 시간 문자열
 * */

private const val PARSE_UNIT_HOUR = 'H'
private const val PARSE_UNIT_MINUTE = 'M'
private const val PARSE_UNIT_SECOND = 'S'

fun parseDuration(timeData:String):String{

    var timeData = timeData
    var result:String = ""

    val timeUnits = arrayOf(Pair(PARSE_UNIT_HOUR,3600),Pair(PARSE_UNIT_MINUTE,60),Pair(PARSE_UNIT_SECOND,1))
    timeData = timeData.drop(2)
    timeUnits.forEach {
        if(timeData.indexOf(it.first) != -1){


            if (it.first == PARSE_UNIT_SECOND) {
                result += String.format("%02d", (timeData.take(timeData.indexOf(it.first)).toInt() ))
            }else{
                result += "${timeData.take(timeData.indexOf(it.first))}:"
            }
            timeData = timeData.drop(timeData.indexOf(it.first) +1)
        }
        else{
            if(it.first== PARSE_UNIT_MINUTE){
               if(result.equals("")){
                   result += "0:"
               }else{
                   result +="00:"
               }
            }
            if(it.first == PARSE_UNIT_SECOND){
                result+="00"
            }
        }
    }

    return result

}

/**
 * BigInteager(=A).Compare(B)
 *  A<B -1
 *  A=B 0
 *  A>B 1
 *
 * 1000 단위 이전까지는 숫자 그대로
 * @Todo 1000~10000 는 x천 단위 <- 1~10000 사이에서만
 * 10000(만)~100000(십만) 까지는 x.y만 과 같은 형태
 * 100000(십만)~10000000(천만) 까지는
 *
 * 10000(만)의 단위기준으로 명칭이 달라짐
 * number + 단위
 * */
private const val UNIT_100K = 100000L
private const val UNIT_10K = 10000L

fun parseViewcount(viewCount:BigInteger):String{
    var viewCount = viewCount
    val unitsBy10K = arrayOf("","만","억","조","경")

    var result :String = ""
    unitsBy10K.forEachIndexed { index, numberUnit ->
        if(viewCount.compareTo(BigInteger.valueOf(UNIT_100K) ) == -1){
            if(viewCount.compareTo(BigInteger.valueOf(UNIT_10K)) ==- -1 ){
                /**viewcount == 10K~100K*/
                result = String.format("${(viewCount.toDouble()/10000).toString().take(3)} ${unitsBy10K[index+1]} ")
                return result
            }else {
                /**viewcount == 1 ~ 10K */
                result = String.format("${viewCount} $numberUnit" )
                return result
            }
        }

        viewCount=viewCount.divide(BigInteger.valueOf(UNIT_10K))

    }



    return result
}












package bowonlee.my.studyinsinrim

import android.os.Parcel
import android.os.Parcelable
import java.math.BigInteger


/** Youtube 비동기 호출시 검색할 타입과 타입마다 검색 할 쿼리를 하나의 쌍으로 묶는다.  */
data class SearchPair< T >( var type : T, var query : String)
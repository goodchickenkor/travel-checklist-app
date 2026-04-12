package com.example.perfecttrip.data

import com.example.perfecttrip.model.Category
import com.example.perfecttrip.model.ChecklistTemplate
import com.example.perfecttrip.model.displayName
import com.example.perfecttrip.ui.checklist.ChecklistItem

object ChecklistRepository {


    private val baseItems = listOf(
        "상비약", "구급상자",
        "칫솔", "치약",
        "샴푸", "바디워시", "클렌징 폼", "화장품", "화장 클렌징 제품", "스킨케어", "면도기", "여성청결제",
        "물티슈", "휴지", "비닐봉지", "데오드란트", "지퍼백", "생리대",
        "인공눈물", "렌즈", "렌즈세척액", "렌즈케이스", "안경",
        "휴대폰 충전기", "보조 배터리", "이어폰",
        "향수", "왁스 헤어 에센스", "헤어 스프레이", "고데기", "브러쉬", "머리끈"
    )

    private val templates = mapOf(

        // ===== 동행자 =====
        Category.PET to listOf(
            "켄넬", "배변패드", "애견 샴푸", "애견 빗", "배변 봉투", "리드줄",
            "간식", "강아지 장난감", "사료", "강아지 약", "강아지 옷"
        ),

        Category.BABY to listOf(
            "기저귀", "기저귀매트", "젖병", "젖병건조대", "젖병솔", "젖병세제",
            "아기 수저", "아기 옷", "아기 장난감", "분유",
            "아기 치약", "아기 칫솔", "아기 바디워시", "아기 샴푸", "아기 로션",
            "공갈 젖꼭지", "아기 물티슈", "이유식", "유축기", "아기 약",
            "아기 수첩", "휴대용 유모차", "힙시트", "담요", "턱받이",
            "체온계", "아기띠", "손수건"
        ),

        Category.PATIENT to listOf(
            "처방약", "마스크", "손 소독제", "영문 진단서"
        ),

        Category.ELDER to listOf(
            "지팡이", "허리보호대", "처방약", "안경", "보청기", "틀니"
        ),

        Category.DISABLED to listOf(
            "기저귀", "휠체어 타이어튜브", "휠체어 공기주입기", "휠체어 포장비닐",
            "긴 샤워기 줄", "공기 방석", "물티슈", "카테터",
            "영문 장애인 증명서", "장애인 등록증", "처방약"
        ),

        // ===== 여행 유형 =====
        Category.FISHING to listOf(
            "낚싯대", "릴", "낚시줄", "낚싯바늘", "미끼", "뜰채", "뜰망",
            "태클 박스", "낚시 장갑", "낚시 가위", "팔토시", "낚시 의자",
            "모자", "살림통", "선글라스", "아이스박스", "구명조끼",
            "포셉집게", "계측 자", "헤드랜턴"
        ),

        Category.GOLF to listOf(
            "골프 클럽", "골프백", "골프화", "골프웨어", "모자", "장갑",
            "티", "거리측정기", "골프 공", "자외선 차단제",
            "골프 우산", "골프 클럽 헤드커버", "골프백 항공커버"
        ),

        Category.MOUNTAIN to listOf(
            "등산화", "등산복", "등산 스틱", "모자", "벌레 기피제",
            "등산 장갑", "간식", "물", "텀블러", "등산 배낭", "보조배터리"
        ),

        Category.PHOTO to listOf(
            "카메라", "카메라 배터리", "카메라 충전기", "메모리 카드",
            "렌즈", "삼각대", "볼헤드", "릴리즈", "필터", "스트로보",
            "반사판", "카메라 레인커버", "여행용 어댑터", "콘센트",
            "액션캠", "후레쉬", "마이크", "조명", "하드 드라이브"
        ),

        Category.PICNIC to listOf(
            "돗자리", "의자", "웨건", "테이블", "아이스박스",
            "파라솔", "텐트", "타프", "간식", "수저"
        ),

        Category.SKI to listOf(
            "스키 장갑", "스키 마스크", "귀마개", "고글", "스키 모자",
            "보호대", "핫팩", "스키복", "헬멧", "넥워머"
        ),

        Category.SWIM to listOf(
            "수영복", "수영모", "물안경", "튜브", "아쿠아슈즈",
            "휴대폰 방수팩", "비치 타월", "구명조끼", "태닝 오일", "돗자리"
        ),

        Category.TRACKING to listOf(
            "운동화", "바람막이", "기능성 상의", "모자",
            "가방 커버", "물병", "우비", "팔토시", "지퍼백"
        ),

        Category.VALLEY to listOf(
            "물안경", "돗자리", "아쿠아슈즈", "텐트",
            "구명조끼", "튜브", "휴대폰 방수팩", "아이스박스", "수저"
        )
    )


    fun getGroupedItems(categories: List<Category>): List<ChecklistItem> {

        val result = mutableListOf<ChecklistItem>()


        result.add(ChecklistItem.Header("기본 준비물"))
        baseItems.forEach {
            result.add(
                ChecklistItem.Item(
                    name = it,
                    category = Category.BASE
                )
            )
        }


        categories.forEach { category ->

            val items = templates[category] ?: return@forEach

            result.add(ChecklistItem.Header(category.displayName()))

            items.forEach {
                result.add(
                    ChecklistItem.Item(
                        name = it,
                        category = category
                    )
                )
            }
        }

        return result
    }
}
package com.example.perfecttrip.model

enum class Category {
    BASE,

    BABY,
    PET,
    PATIENT,
    ELDER,
    DISABLED,

    FISHING,
    GOLF,
    MOUNTAIN,
    PHOTO,
    PICNIC,
    SKI,
    SWIM,
    TRACKING,
    VALLEY
}

fun Category.displayName(): String {
    return when (this) {
        Category.BASE -> "기본 준비물"

        Category.BABY -> "아기"
        Category.PET -> "반려견"
        Category.PATIENT -> "환자"
        Category.ELDER -> "노인"
        Category.DISABLED -> "장애인"

        Category.FISHING -> "낚시"
        Category.GOLF -> "골프"
        Category.MOUNTAIN -> "등산"
        Category.PHOTO -> "사진"
        Category.PICNIC -> "피크닉"
        Category.SKI -> "스키"
        Category.SWIM -> "수영"
        Category.TRACKING -> "트래킹"
        Category.VALLEY -> "계곡"
    }
}
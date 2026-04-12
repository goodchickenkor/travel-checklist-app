# 🧳 PerfectTrip - 여행 준비물 체크리스트 앱

## 📌 프로젝트 소개

PerfectTrip은 여행을 준비하는 사용자를 위해
**여행 상황(동행자, 여행 유형)에 맞는 맞춤형 준비물 체크리스트를 자동 생성**해주는 Android 앱입니다.

사용자는 간단한 선택만으로 필요한 준비물을 빠짐없이 확인하고,
체크리스트를 통해 효율적으로 여행 준비를 할 수 있습니다.

---

## 🚀 주요 기능

### 1️⃣ 여행 생성

* 여행 이름 및 여행 기간 설정
* Material DatePicker를 활용한 기간 선택

### 2️⃣ 동행자 선택 (Step2)

* 아기 / 반려견 / 환자 / 노인 / 장애인 선택
* 토글 UI로 직관적인 선택 가능

### 3️⃣ 여행 유형 선택 (Step3)

* 낚시, 골프, 등산, 수영 등 다양한 여행 카테고리 제공
* 선택한 유형 기반 체크리스트 생성

### 4️⃣ 맞춤형 체크리스트 생성

* 기본 준비물 + 선택한 카테고리별 준비물 자동 구성
* 카테고리별 그룹 UI 제공

### 5️⃣ 체크리스트 관리

* 체크 시 해당 카테고리 내에서 아래로 이동
* 체크 해제 시 원래 위치로 복귀
* 직관적인 정렬 UX 제공

### 6️⃣ 여행 저장 및 조회

* Room DB를 활용한 데이터 저장
* 홈 화면에서 여행 목록 확인 가능
* 여행 클릭 시 체크리스트 재확인 및 수정 가능

### 7️⃣ 여행 삭제 기능

* 각 여행 카드에서 삭제 버튼 제공
* 관련 체크리스트까지 함께 삭제 (CASCADE)

---

## 🛠️ 기술 스택

* **Language**: Kotlin
* **Architecture**: MVVM
* **UI**: XML + ViewBinding
* **Database**: Room
* **Jetpack**

  * Navigation Component
  * ViewModel
  * LiveData / Flow
* **Material Design**

  * MaterialDatePicker

---

## 📂 프로젝트 구조

```
com.example.perfecttrip
│
├── data
│   ├── local
│   │   ├── db
│   │   ├── dao
│   │   └── entity
│   └── repository
│
├── ui
│   ├── home
│   ├── create (Step1, Step2, Step3)
│   └── checklist
│
├── viewmodel
└── util
```

---

## ⚙️ 주요 구현 포인트

### ✔️ 1. 상태 유지 (ViewModel 활용)

* Step1 → Step2 → Step3 이동 시 데이터 유지
* 뒤로가기 시 입력값 유지
* 새 여행 시작 시 ViewModel 초기화

---

### ✔️ 2. 카테고리 기반 체크리스트 구조

* `sealed class ChecklistItem`

  * Header
  * Item
* RecyclerView에서 Multi ViewType 처리

---

### ✔️ 3. 카테고리 내부 정렬 알고리즘

* 체크 상태에 따라 동일 카테고리 내부에서만 이동
* UX를 고려한 자연스러운 정렬 처리

---

### ✔️ 4. Room DB 설계

* Trip ↔ ChecklistItem (1:N 관계)
* ForeignKey + CASCADE 적용
* 여행 삭제 시 체크리스트 자동 삭제

---

### ✔️ 5. Navigation 기반 화면 전환

* Fragment 기반 단계별 UI 구성
* Argument를 통한 데이터 전달 (tripId)

---

## 📸 실행 화면 

![perfect_trip_움짤](https://github.com/user-attachments/assets/55783bb1-04ea-4bfa-8fe6-a8d10ca2ed42)


---

## 📈 향후 개선 계획

* 체크리스트 진행률 표시
* 체크 애니메이션 및 UI 개선
* 클라우드 동기화
* 다크모드 지원
* 여행지 날씨 예보 추가

---

## 💡 프로젝트를 통해 배운 점

* MVVM 아키텍처 기반 앱 설계 경험
* Room DB를 활용한 데이터 관리
* RecyclerView 고급 처리 (Multi ViewType)
* 사용자 경험(UX)을 고려한 인터랙션 구현
* 상태 관리 및 화면 복원 로직 이해

---

## 👨‍💻 개발자

* Android Developer: 황준영

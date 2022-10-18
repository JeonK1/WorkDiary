# WorkDiary_MVVM
WorkDiary MVVM
<br>개발기간 : 2022.10.04 ~ <진행중>
<h2>기술 스택</h2>
- Android Kotlin <br>
- Room database를 활용하여 데이터 관리 <br>
- Room + LiveData + ViewModel 을 활용한 MVVM 아키텍쳐 구현 <br>
- Dagger Hilt 로 Di 적용 <br>
- View Binding + Data Binding <br>
- ConstraintLayout 을 이용하여 View depth 최적화 <br>
- ListAdapter 적용 <br>
<br><br>
<h2>프로젝트 개요</h2>
- 기존에 개발되었던 것을 MVVM 패턴으로 rebuilding 하는 작업을 진행하였다. (<a href="https://github.com/JeonK1/WorkDiary/tree/mvvm">WorkDiary MVVM Version</a>) <br>
- <a href="https://blog.naver.com/ponson1017/222902098163">[WorkDiary 재개편] 1. 코드 전체 리팩토링 </a><br>
<br><br>
<h2>코드파일 구성</h2>
<img src="/readme_img/4.png" width=600 />
- MVVM의 디자인 패턴으로 코드가 작성되으며, 각 파일별 연결관계는 다음과 같다.
<br><br>
<h2>개발 환경</h2>
Complie SDK Version: 31<br>
Minimum SDK Version: API 21 <br>
Gradle Version: 6.7.1 <br>
JDK version: jdk_1_8 <br>

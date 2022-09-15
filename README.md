# CGV 클론 프로젝트
***
## 1. 개요
##### CGV 홈페이지를 클론 코딩하여 구현하였습니다.
***
### 2_1 기능 구현
- 카카오, 네이버 소셜 회원가입 및 로그인
- CGVmovie crawling
- 맘에드는 영화 찜하기
- 영화 예매하기
- 마이페이지(내가 본 영화, 찜한 영화 보기)

### 2_2 와이어프레임
![와이어프레임](https://user-images.githubusercontent.com/110075438/190330020-671b8c7d-adba-47f3-aa15-33f994a3be28.png)

### 2_3 API명세서
![Api1](https://user-images.githubusercontent.com/110075438/190330984-bee998a6-4eb8-4b59-aae6-1b56fa56b203.PNG)
![Api2](https://user-images.githubusercontent.com/110075438/190331009-a6ae72cf-880b-40dc-a9d4-d0c8398bc25b.PNG)
![APi3](https://user-images.githubusercontent.com/110075438/190331020-cd4481a5-428d-43ff-9976-b54b861e5e79.PNG)
![Api4](https://user-images.githubusercontent.com/110075438/190331030-1ad44200-2d2e-4487-af6f-8abc70867376.PNG)

## 트러블슈팅
***
1. Data truncation: Data too long for column 'detail' at row 1 error
 - 크롤링시 발생, 인텔리제이 로그읽어보니 위와같은 문구가 표시
 - 데이터가 컬럼 속성보다 길어서 발생하는 문제로 인식하고 디비버를 통해 detail 컬럼 속성을 varchar(255)에서 text로 변환, 해결
 - 추후 varchar(255~65535)괄호 데이터량을 1500으로 지정
 ||index의 일부 사용여부|max size limit 가능여부|
 |---|---|---|
 |text|불가능|only 65535|
 |varchar|가능|1~65535|
 
 2. 카카오 로그인시 500error 발생
  - 서버 쪽에서 로그인시 받는 개인정보를 회원가입으로 저장시 알고리즘 오류
  - 토큰을 카카오측으로 보내면 사용자정보를 String으로 받는데 그것을 JSONObject 이용하여 형변환
  - 네이버 소셜로그인과 비슷하다 생각하고 알고리즘을 짰는데 오는 내용을 보니 크게 묶여서 왔기때문에 추출 과정에서 에러 발생
  - 해당하는 내용을 포함하고 있는 정보를 추출해서 다시 String으로 변환 후 split과 substring을 사용하여 필요한 정보만 추출

## 📆 프로젝트 기간
**2024.01.15 ~ 2024.02.15 / 서비스 런칭 : 2024.01.30**

<br/>

### 👉🏻 [옷사러 가시죠 !](https://techit.kro.kr/)
### 🎬 [시연 보실분 !](https://youtu.be/lVXoZkI9BdE)

<br/>

## ✔️ 사이트 유의사항

1. 구매후기는 물품 구매 후 '물품후기 작성하기' 버튼이 생성됩니다. (**결제 금액은 뜨지만 실제로 금액이 결제되진 않습니다.**)

2. 문의사항 qna 답변은 관리자만 가능합니다. (**관리자 테스트 trendhub7 / trendhub7@**)

<br/>

## ⚙️ 서비스 아키텍쳐
![stack](https://github.com/TECKIT-SHOPPING/Trend-hub/assets/131260371/60836e50-410e-4c7e-873d-2dbfa7a3d5ce)

<br/>

## 📒 기술스택

#### FRONT-END
 <img src="https://img.shields.io/badge/HTML-E34F26?style=for-the-badge&logo=HTML5&logoColor=white"/> <img src="https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white"/>  <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=JavaScript&logoColor=black"/> 

#### BACK-END
<img src="https://img.shields.io/badge/Thymeleaf-6DB33F?style=for-the-badge&logo=Thymeleaf&logoColor=white"> <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white"/> <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white"/> ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
<br> 
<img src="https://img.shields.io/badge/NCP RDS-03C75A?style=for-the-badge&logo=Naver&logoColor=white"/> <img src="https://img.shields.io/badge/NCP S3-03C75A?style=for-the-badge&logo=Naver&logoColor=white"/> <img src="https://img.shields.io/badge/NCP EC2-03C75A?style=for-the-badge&logo=Naver&logoColor=white"/> 
<br> 
<img src="https://img.shields.io/badge/GitHub Actions-2088FF?style=for-the-badge&logo=GitHub Actions&logoColor=white"/> <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white"/> ![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
<br/>

### Design
![Figma](https://img.shields.io/badge/figma-%23F24E1E.svg?style=for-the-badge&logo=figma&logoColor=white)

### etc
![Google](https://img.shields.io/badge/google-4285F4?style=for-the-badge&logo=google&logoColor=white) ![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white) 

## 🧱 ERD
![TrundHub3-ERD](https://github.com/TECKIT-SHOPPING/Trend-hub/assets/84388081/7c16ef83-dd16-4f11-b5fa-66163dea764a)

<br/>

</details>

<br/>

## 🛠️ 트러블슈팅


<details>

<summary>BACK-END</summary>

### 상품목록 조회 좋아요 유무 N+1 이슈

|요구 사항| 핵심 기술을 선택한 이유 및 근거|
|:---|:---|
|:scream: 문제| 페이징처리된 상품목록 20개를 가져오는데 각 상품별로 유저가 좋아요 유무를 판단하는 쿼리문은 상품 <br/>갯수 20개만큼 쿼리문 날리는 문제 발생|
|:thinking: 원인| 상품들을 먼저 조회하고 likes에서 exists로 찾기 때문에 N+1 문제 발생하는 것으로 판단했다.|
|:sob: 시도| • 구글링을 통해 게시판 구현한 코드들을 봐도 N+1 이슈가 발생하도록 코드가 작성되어있어 쿼리문을 애초에 다르게 만들어야겠다고 생각함.</br> • product랑 likes를 조인하고 where절에 user를 eq조건에 넣어 해결해봐도 N+1발생 </br> • on절에 productId일치, user가 두 테이블 간에 일치하도록하여 해결함. |
|:smile: 해결|likes 엔터티 간의 leftJoin하여 on절에 product 및 user가 두 테이블 간에 일치 조건을 기반으로 하여 데이터를 한꺼번에 가져오는 방식으로 해결함|

### 유효성 검사 에러
|요구 사항| 핵심 기술을 선택한 이유 및 근거|
|:---|:---|
|:scream: 문제| 후기 작성하기 페이지 만드는 과정 중 'validation failed for object="reviewDto"' 란 에러 발생.|
|:thinking: 원인| 현재 로직에 타임리프 에러메세지 처리 부분 혹은 DTO 부분에 문제가 있을거라 판단.|
|:sob: 시도| • 백 부터 Controller를 시작으로 service, DTO 등 순으로 검사 시작.<br/>• DTO에서 유효성 검사하는 부분을 지우고 Controller에서 좀 더 고민<br/>• 타임리프 에러 메세지 처리 부분에서도 한 번씩 확인했지만 로직 상 큰 문제가 없어보여 백엔드 쪽에서 문제가 생겼을 거라 확신.<br/>• 결국 Controller 부분에서 PostMapping 뿐만 아니라 GetMapping에서도 @Valid 를 사용한 것을 확인 후 제거 |
|:smile: 해결|@GetMapping 부분 @Valid를 제거함으로써 후기 작성 부분 에러 메세지가 잘 처리되는 것을 확인할 수 있었음|




</details>

<br/>

## ‍🧑‍💻 프로젝트 멤버

|[🔰안우성](https://github.com/Anwooseong)|[🔰최도영](https://github.com/mabyoungg)|[🔰김부권](https://github.com/bukwon)|[🔰현진영](https://github.com/jinyoung121636)|[🔰이재윤](https://github.com/leejaeyoon22)|[🔰신석우](https://github.com/bukgomi)
|:---:|:---:|:---:|:---:|:---:|:---:|
|![안우성](https://github.com/TECKIT-SHOPPING/Trend-hub/assets/131260371/fd3c0092-f6d3-4581-8d95-fdb371ac7df6)|![최도영](https://github.com/TECKIT-SHOPPING/Trend-hub/assets/131260371/d4e4a061-905a-4f74-ae43-c48425208c93)|![김부권](https://github.com/TECKIT-SHOPPING/Trend-hub/assets/131260371/dcc4377a-b276-498a-8d50-c14c70c18363)|![현진영](https://github.com/TECKIT-SHOPPING/Trend-hub/assets/131260371/00f9e9a0-af2c-457b-b549-08b793321020)|![이재윤](https://github.com/TECKIT-SHOPPING/Trend-hub/assets/131260371/caf86912-325d-4926-bcc9-ccf04c8cd2bf)|![신석우](https://github.com/TECKIT-SHOPPING/Trend-hub/assets/131260371/0641779a-6af2-47fa-a27b-a1b3302510ab)|
|BACK-END|BACK-END|BACK-END|BACK-END|BACK-END|BACK-END|

# **[2주차] Project_12팀 진행상황 공유**

## 팀 구성원, 개인 별 역할

- 팀원 : 김부권, 신석우, 안우성, 이재윤, 최도영, 현진영
- 팀원 전체 : 명세서 피드백 수정, ERD 작성
- 화면 구체화 : 현진영, 신석우
- 로그인 : 김부권
- 회원가입 : 안우성
- 메인화면 : 현진영
- 내 정보화면 : 최도영
- 코디 작성 : 이재윤
- 카테고리 상품목록 화면 : 신석우

## 팀 내부 회의 진행 회차 및 일자

- 1회차(2024.01.22) 디스코드 - 엔티티 작성, 명세서 피드백 수정
- 2회차(2024.01.23) 디스코드 - 웹쟁이 화면 구체화 진행에 관한 회의
- 3회차(2024.01.24) 디스코드 - 로그인 코드 수정
- 4회차(2024.01.25) 디스코드 - 로그인 기능 구현, 회원가입 기능 구현
- 5회차(2024.01.26) 디스코드 - NCP Obeject Storage 파일 업로드 구현

- 일주일간 진행횟수 : 5회

## 현재까지 개발 과정 요약 (최소 500자 이상)

- **화면 상세화:**
    - 내 정보 화면을 제외한 다른 화면들은 상세화 작업이 완료되었습니다.
- **로그인:**
    - 앱 자체 로그인 기능은 구현되었으며, 카카오 로그인 부분은 세션 처리가 완료되었습니다. 다만, ‘ROLE_MEMBER’ 권한 설정이 추가적으로 남았습니다.
    - User 엔터티에서 **`String provider`**를 **`enum`**인 **`SocialProvider provider`**로 변경하여 정의한 타입 이외**의** 타입을 가진 데이터값을 컴파일 할 시 체크하게 되어 유지보수 비용을 줄일 수 있게 되었습니다.
- **회원가입:**
    - 입력한 이메일을 전송하여 인증번호를 확인하는 기능이 구현되었습니다.
    - 기존 회원 여부를 판별하여 회원가입 기능이 완료되었습니다.
- **코디 작성:**
    - NCP Object Storage를 활용하여 멀티파트 이미지 업로드 기능이 구현되었습니다.
- **메인화면:**
    - 헤더를 통한 홈페이지 이동 처리가 진행 중입니다.
    - 메인화면이 구현되었으며, 로그인한 사용자라면 로그인 버튼이 로그아웃 버튼으로 변경되도록 구현하였습니다.
- **카테고리 상품목록 화면:**
    - 브랜드 더보기 접기 기능이 구현 중에 있습니다.

## 개발 과정에서 나왔던 질문 (최소 200자 이상)

- 스프링부트 NPC Object Storage 사용
    - genFile사용할지 NPC Object Storage을 사용할 지 고민하다가 확장성이 뛰어나고 서버 간에 파일을 공유할 필요가 없어 효율적으로 사용하기 위해 NPC Object Storage하기로 결정하였다.
    - 아래 사이트는 동영상을 업로드했는데 우리 서비스는 이미지 업로드만 필요해서 코드를 수정했다.
    
    [How to use Naver Cloud Object Storage API With Spring Boot](https://jyp-on.medium.com/how-to-use-naver-cloud-object-storage-api-with-spring-boot-d92b01bf467f)
    
- SecurityConfig 초반에 permitAll()
    - 초반에는 모든 url에 대해서 permitAll()로 하고 어느 정도 화면과 기능 구현이 완료된 상태에서 상세하게 SecurityConfig 설정할 예정
- git pull 관련 문제
    - develop 브랜치에서 pull하는 과정에서 충돌이 생겨 git status를 먼저하고 modify 된 파일들을 확인하여 github에 있는 코드를 복사하여 해결하였다.
    - feature/login에서 develop 브랜치로 pr을 하려고했으나 conflict 문제가 생겨 코드를 github에서 수동으로 작성하여 해결하였다.    
- 더미 데이터 관련
    - 상의, 하의, 아우터 위주로 인당 84개씩 넣기, 프로젝트 마무리 단계에서 나머지 카테고리 상품도 더미 추가
    - 코디 작성은 인당 10개씩

## 개발 결과물 공유

Github Repository URL: https://github.com/TECKIT-SHOPPING/Trend-hub

- 필수) 팀원들과 함께 찍은 인증샷(온라인 만남시 스크린 캡쳐)도 함께 업로드 해주세요 🙂
- <img width="1410" alt="스크린샷 2024-01-26 17 16 08" src="https://github.com/TECKIT-SHOPPING/Trend-hub/assets/84388081/7a4ac4e3-6d64-48bf-8597-bde150848cf3">

  

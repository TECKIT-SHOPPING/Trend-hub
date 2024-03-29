# **[3주차] Project_12팀 진행상황 공유**

## 팀 구성원, 개인 별 역할

- 팀원 : 김부권, 신석우, 안우성, 이재윤, 최도영, 현진영
- 화면 상세화 : 현진영, 신석우
- 배포 : 최도영
- 카카오 로그인, 아이디 찾기, 비밀번호 찾기 : 김부권
- 메인화면 제작 : 현진영
- 메인화면 기능 : 안우성
- 내 정보화면 : 최도영
- 후기 작성 : 이재윤
- 카테고리 상품목록 화면 : 신석우

## 팀 내부 회의 진행 회차 및 일자

- 1회차(2024.01.29) 디스코드 - 카테고리 상품 목록 화면 scipt 작성, 배포, 카카오 로그인 1차 완성, 더미데이터 1차 기입
- 2회차(2024.01.30) 디스코드 - 배포, 카카오 로그인 완료, 더미데이터 2차 기입, 코드 작성 페이지 기능 구현, 메인화면에 카테고리 컴포넌트 추가
- 3회차(2024.01.31) 디스코드 - 아이디 비밀번호 찾기 로직 설계, 내 정보 변경 로직 구현
- 4회차(2024.02.01) 디스코드 - 아이디 비밀번호 찾기 로직 구현
- 5회차(2024.02.02) 디스코드 - 카카오 이메일 필수, 카카오 회원가입시 로그인에 이메일 넣기

- 일주일간 진행횟수 : 5회

## 현재까지 개발 과정 요약 (최소 500자 이상)

- **화면 상세화:**
    - 마이페이지 화면 구현 완료 (중복되는 부분은 fragment 사용)
    - 코디 모음 페이지 화면 구현
- **메인화면**:
    - 배너이미지가 3장인데 3장이 한꺼번에 보여 수정중에 있음
    - 상품 화면에서 좋아요 추가 삭제 기능 구현 완료, 좋아요 버튼 변환은 구현 중
    - 베스트 코디 목록 5개 → 4개로 변경, 닉네임, 프로필, 댓글수, 좋아요 수 디자인 추가 필요
- **상품 목록 화면:**
    - 브랜드 체크박스 필터 생성
    - 카테고리별 상품목록 필터, 브랜드 목록 필터 등 구현중에 있음
- **배포**:
    - naver cloud platform (ncp)로 배포
    - github actions으로 ci/cd 구현
- **내 정보**:
    - 내 정보 변경 기능 구현 ( 비밀번호, 닉네임, 프로필 이미지, 주소)
- **후기작성:**
    - 별점 부여 기능을 ★을 눌러서 선택할 수 있게 하고 사용자가 입력한 데이터를 서버로 받아올 수 있도록 구현중
    - 후기를 남기려는 상품에 대한 정보 받아올 수 있도록 구현해야함
- **카카오 로그인**:
    - 카카오 로그인 중 이메일 권한 얻은 후 페이지 데이터베이스에 사용자 ID로 사용하도록 수정
- **아이디 및 비밀번호 찾기**:
    - 아이디 및 비밀번호 찾기 로직을 어떻게 구현할 것인지 고민해봤으며 타임리프를 사용하는 것 대신 백으로 처리해 비밀번호는 임시 비밀번호, 아이디는 사용자 이메일과 이름으로 유효성 검사를 해 아이디를 사용자 이메일에 보내는 형식으로 구현

## 개발 과정에서 나왔던 질문 (최소 200자 이상)

- 카카오 로그인시 이메일 필수로 가져올 것인가
    - 시큐리티에서 로그인 id를 저장하기 때문에 사용자 조회할때 통일성을 유지하기 위해 이메일을 필수로 가지고 오는 것으로 결정
- resources안에 static/image에서 한글.png로 되어있으면 배포시 ./gradlew build no daemon시 에러발생
    - 한글.png를 영어로 변환하니까 해결완료
- git 관련
    - develop에 pull시 충돌날때 resolve후 git add . → git rebase —continue해서 해결될때까지 반복 후 강제 push

## 개발 결과물 공유

Github Repository URL: 
[GitHub - TECKIT-SHOPPING/Trend-hub](https://github.com/TECKIT-SHOPPING/Trend-hub)

- 필수) 팀원들과 함께 찍은 인증샷(온라인 만남시 스크린 캡쳐)도 함께 업로드 해주세요 🙂
  ![image](https://github.com/TECKIT-SHOPPING/Trend-hub/assets/84388081/8d88f298-d85e-47f1-a0b5-85d3de4febd2)

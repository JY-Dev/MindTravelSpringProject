# 마인드 트래블

### 프로젝트 개요

요즘들어 정신적으로 힘들어 하거나 매체의 발달로 인해 은연중에 외로움을 많이 느껴 우울증에 걸리거나 우울을 경험한 사람들이 많다고 느껴졌습니다. 전문적인 기관(상담소, 정신과 병원)을 통해 해결하는 사람도 있겠지만 아직까지는 많은 사람들이 이러한 기관에 방문하는 것을 사회적인 시선에 의해 안 좋게 보는 경우가 많아 방문을 꺼려한다고 생각이 들어 쉽게 접근할 수 있게 어플리케이션으로 제작해서 익명성을 보장하면서 서로의 고민을 올려서 공감을 받거나  전문 상담사와 1대1 채팅으로 대화하거나 하루하루 자신의 기분 등을 작성해 보면서  어느정도의 문제를 해결했으면 좋겠다는 취지로 프로젝트를 진행하게 되었습니다.

### 소프트웨어 개발 도구

### 

### 나머지 도구

- 서버
    - Spring Boot
    - Spring Security
    - Spring Data Jpa
    - JPA
    - QueryDsl
    - MySql
    - AWS
    - JAVA

- 모바일
    - Android
    - Kotlin
    

- 기획 및 기록
    - Figma
    - Erd-Cloud
    - Noton
    

### 와이어프레임

[https://www.figma.com/file/eVrUVwfDzdrguUDE1JcFPJ/Untitled?node-id=89%3A33&t=m8gB7xtbHOGJhUgd-1](https://www.figma.com/file/eVrUVwfDzdrguUDE1JcFPJ/Untitled?node-id=89%3A33&t=m8gB7xtbHOGJhUgd-1)

### ERD

[MindTravel](https://www.erdcloud.com/d/TPdEDQWooRWKrJDnZ)

### Android 프로젝트 링크

[https://github.com/JY-Dev/MindTravelApplication](https://github.com/JY-Dev/MindTravelApplication)

### TODO
- Transaction Read Only 적용
- 프로필 사진 등록
    - S3 이용
- 상담사랑 채팅 기능
    - 카카오페이 결제
    - 상담사 선착순 매칭
    - 채팅 시간제한
- Github Action을 이용한 CI/CD
- EC2 배포

### 로그인 및 회원가입 화면
- 로그인 및 회원가입 화면입니다. 
- 로그인은 Naver, Goolge Sns 로그인으로만 구성되어있습니다.
- 간편한 회원가입으로 닉네임만 설정하면 자동으로 가입됩니다.
- 닉네임은 고유하게 설정하였습니다.

![로그인 및 회원가입](https://user-images.githubusercontent.com/45057493/233777008-3c666c4e-90d2-4dee-af43-3454617a399e.gif)

### 기분 기록하기 화면
- 현재의 기분을 기록하는 화면입니다.
- 나쁨 좋지 않음 보통 좋음 이렇게 4가지의 기분을 선택할 수 있습니다.
- 현재 기분에 대한 내용을 작성할 수 있습니다.
- 날짜별 검색이 가능합니다.

![기분기록하기](https://user-images.githubusercontent.com/45057493/233777037-e99920f2-5a31-4d4d-9199-53369f454026.gif)

### 글 작성 화면
- 글을 작성해서 다른 사람들과 공유할 수 있는 화면입니다.
- 카테고리 별로 선택해서 작성할 수 있습니다.

![글작성](https://user-images.githubusercontent.com/45057493/233777070-6d1395f2-597d-4e68-9c09-77912437b3e6.gif)

### 댓글 좋아요 작성 화면
- 댓글 좋아요 화면입니다.
- 댓글, 대댓글을 작성할 수 있고 수정 삭제가 가능합니다.
- 좋아요 리스트를 확인할 수 있습니다.
- 댓글 작성시 알림이 가도록 구현하였습니다.

![댓글 좋아요 작성](https://user-images.githubusercontent.com/45057493/233777105-6c22779e-a140-4ce9-86a4-792f0da2c5d4.gif)

### 프로젝트 진행하면서 작성한 글

[Spring Security 이용해서 SNS 로그인 Rest API 환경으로 구현하기](https://velog.io/@kjy0302014/Spring-Security-이용해서-SNS-로그인-Rest-API-환경으로-구현하기)

[QueryDsl Date 조건 처리](https://velog.io/@kjy0302014/QueryDsl-Date-조건-처리)

[Jpa MultipleBagFetchException](https://velog.io/@kjy0302014/Jpa-MultipleBagFetchException)

[Jpa QueryDsl로 대댓글 구현하기](https://velog.io/@kjy0302014/Jpa-QueryDsl로-대댓글-구현하기)

[Spring Boot Logback 적용하기](https://velog.io/@kjy0302014/Spring-Boot-LogBack-%EC%A0%81%EC%9A%A9%ED%95%98%EA%B8%B0)

[FCM 댓글 알림 구현하기](https://velog.io/@kjy0302014/FCM-%EB%8C%93%EA%B8%80-%EC%95%8C%EB%A6%BC-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0)
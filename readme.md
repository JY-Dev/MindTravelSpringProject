# 마인드 트래블

### 프로젝트 개요
익명성을 보장하면서 서로의 고민을 올려서 공감을 받거나 상담사와 1대1 채팅으로 대화하거나 하루하루 자신의 기분 등을 작성해 보면서 자신의 감정을 조절하는 Application 입니다.

### 기술스택

- 백엔드
    - Spring Boot 3.0.4
    - Spring Security
    - Spring Data Jpa
    - Docker, Docker Compose
    - QueryDsl
    - MySql
    - AWS
    - JAVA 17

- 프론트엔드
    - Android
    - Kotlin
    

- 기획 및 기록
    - Figma
    - Erd-Cloud
    - Noton
    

### 와이어프레임!
![스크린샷 2023-05-11 오전 11 56 11](https://github.com/JY-Dev/MindTravelSpringProject/assets/45057493/a48118da-72fd-4dab-8fc0-feb66118fdc4)


[https://www.figma.com/file/eVrUVwfDzdrguUDE1JcFPJ/Untitled?node-id=89%3A33&t=m8gB7xtbHOGJhUgd-1](https://www.figma.com/file/eVrUVwfDzdrguUDE1JcFPJ/Untitled?node-id=89%3A33&t=m8gB7xtbHOGJhUgd-1)

### ERD
![스크린샷 2023-05-11 오전 11 55 23](https://github.com/JY-Dev/MindTravelSpringProject/assets/45057493/be33cdfa-6710-426a-bce0-9c48de69a767)

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

[Docker Compose Container Connection Fail](https://velog.io/@kjy0302014/Docker-Compose-Container-Connection-Fail)

[Spring Boot Docker-Compose 무중단 배포](https://velog.io/@kjy0302014/Spring-Boot-Docker-Compose-%EB%AC%B4%EC%A4%91%EB%8B%A8-%EB%B0%B0%ED%8F%AC)

[결제 처리는 어떻게 해주는게 좋을까?](https://velog.io/@kjy0302014/%EA%B2%B0%EC%A0%9C-%EC%B2%98%EB%A6%AC%EB%8A%94-%EC%96%B4%EB%96%BB%EA%B2%8C-%ED%95%B4%EC%A3%BC%EB%8A%94%EA%B2%8C-%EC%A2%8B%EC%9D%84%EA%B9%8C)

### 프로젝트 회고
아무래도 혼자서 기획 디자인 프론트 백엔드까지 맡아서 하다보니 기능하나 추가하기가 쉬운일은 아니였던 것 같습니다. 하지만 퀄리티 있는 소프트웨어를 만들어 보고 싶었고 그렇다 보니 처음에 기반을 다지기 위한 여러가지 설정하는 시간이 대부분 이었던 것 같습니다. 처음엔 간단한 기능인 기분 기록부터 시작해서 글 작성 댓글 작성 알림 점점 복잡한 기능들을 구현하면서 문제 해결 능력이 많이 상승했다고 생각이 들고 개발을 진행하면서 여러가지 고민들을 하면서 문제에 대해 여러 방면으로 생각해 볼 수 있어서 좋았습니다. 기획, 디자인, 프론트 , 백엔드를 전부 경험해 보면서 각 역할들의 고충들을 알 수 있었고 아쉬웠던 점은 상담기능을 구현하지 못했던 부분인데요 해당 기능은 좀 더 좋게 설계하기 위한 아직 능력이 부족하다고 생각이 들어서 추후에 개발할 예정입니다. 혼자 어플리케이션을 만들다보니 재미도 있었고 물론 힘든점이 많았지만 좋은 경험이었다고 생각이듭니다.

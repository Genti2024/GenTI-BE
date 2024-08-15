## branch 전략, work flow
![bracnh](https://github.com/Genti2024/GenTI-BE/assets/93697934/c4e0f87f-9574-4ab4-83a5-e6467e46e77d)
<img width="672" alt="pr" src="https://github.com/Genti2024/GenTI-BE/assets/93697934/f45eacd9-049a-4118-a1f4-529edc5d8a6c">

## code convention

https://naver.github.io/hackday-conventions-java/

1. record 타입 사용시 해당 recood 타입을 사용할 때 new 대신 static method(of, from ...) 를 사용
2. record 타입 뿐만 아니라 모든 상황에서 new 키워드 사용 자제(자제 라는 단어가 convention과 어울리는지는 모르겠으나 추후 리팩터링할때 new 씨를 말릴겁니다.)

## commit convention
예시
```
Feat: #issue Oauth 기능 구현
```
```
[Feat]: 새로운 기능 구현
[Fix]: 버그, 오류 해결, 코드 수정
[Add]: Feat 이외의 부수적인 코드 추가
[Del]: 쓸모없는 코드, 주석 삭제
[Refactor]: 전면 수정이 있을 때 사용합니다
[Remove]: 파일 삭제
[Rename]: 파일 이동 및 이름 수정
[Chore]: 그 이외의 잡일/ 버전 코드 수정, 패키지 구조 변경, 파일 이동, 파일이름 변경
[Docs]: README나 WIKI 등의 문서 개정
[Setting]: 프로젝트 관련 세팅
```

## 기술 스택

### Base
Springboot 3.2.2 <br>
MySQL 8.0 <br>

### Infra
aws EC2 <br>
aws RDS <br>
aws S3 <br>
aws Cloudfront <br>


### External
OAuth{애플, 카카오, 구글} <br>
Discord - 오류 및 어드민 알림용 <br>
ChatGPT - 사진 생성 프롬프트 Upgrade <br>
Firebase - 앱 푸시알림

### 아키텍쳐(구)
![genti_architecture-Genti 1 0 drawio](https://github.com/Genti2024/GenTI-BE/assets/93697934/f569e8c2-db44-412f-914c-00de01a5f41e)


### CQRS
1 Level의 CQRS 패턴을 적용 (CUD / R 분리) <br>

### 클린 아키텍쳐 적용 예정

JPA의 더티 체킹 등 강력한 기능을 활용하기 위해
도메인 엔티티와 JPA 엔티티를 분리하진 않았음 <br>

#### 클린 아키텍쳐 제약사항들

1. [**UseCase**] CQRS 적용을 위해 CUD는 UseCase, R은 Query로 네이밍
2. [**Port**] CQRS 적용을 위해 CUD는 {Something}StatePort, R은 Load{Something}Port로 네이밍
3. UseCase(Query) 끼리 참조해야할때는 Service에서 UseCase를 참조한다.
4. Command와 Dto 분리 - Dto로 Command를 생성하는 것이 보통이다.
    1. [**Dto**]외부에 응답하거나, 간단한 데이터를 전달, 혹은 Client로부터 Controller 레이어에서 값을 받아오는 경우는 Dto
    2. [**Command**] Controller layer 에서 Service layer로 값을 전달해야 하는 경우 Command로 네이밍, 
5. Domain/Jpa Entity는 구분하지 않고 JpaEntity만 우선 사용한다.


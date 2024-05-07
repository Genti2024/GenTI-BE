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

## ㅁㄴㅇㄹ

### CQRS
1 Level의 CQRS 패턴을 적용 (CUD / R 분리) <br>

### 클린 아키텍쳐 적용

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


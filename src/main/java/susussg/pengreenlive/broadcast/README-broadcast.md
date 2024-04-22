# Broadcast 패키지

이 패키지는 라이브 커머스 플랫폼의 모든 방송 관련 작업을 처리합니다. 방송 시청화면, 라이브보드, 방송 등록등이 포함됩니다.

## 구성 요소

### Controller
RestApi 요청을 수신하고 처리하는 컨트롤러들을 포함합니다.

### Domain
MyBatis에서 사용하는 도메인 모델을 정의합니다.

### DTO
데이터 전송 객체(Data Transfer Object) 정의를 포함합니다.

### Mapper
MyBatis를 사용하여 데이터베이스와의 매핑을 관리합니다.

### Repository
JPA를 사용하여 데이터베이스 테이블과 매핑된 도메인 모델을 포함합니다.

### Service
비즈니스 로직을 수행하는 서비스 계층을 제공하며, 인터페이스와 해당 구현체로 구성되어 관리됩니다. 
예를 들어, UserService 인터페이스와 그 구현체인 UserServiceImpl이 이에 해당합니다.
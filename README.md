# 팀리부뜨 - AI 기반 B2B SaaS 플랫폼 서비스

과제의 요구사항에 따라 작성하였습니다.

---

## 🔧 기술 스탭

| 구별       | 기술                                       |
|------------|------------------------------------------|
| Language   | Kotlin                                   |
| Framework  | Spring Boot 3.4.4, Spring Data JPA       |
| DB         | MariaDB 11.7.2                           |
| Infra      | Docker, Docker Compose                   |
| Build Tool | Gradle                                   |
| Testing    | JUnit5, Mockito                          |
| Others     | Domain-Driven Design |

---

## 🧹 주요 기능

### 1. **요금제(Plan) 관리**
- 다양한 기능 조합으로 새로운 서비스 요금제 생성
- 기능별 제한량 및 크레딧 설정 가능
- 기능 조합 정보(`PlanFeature`) 저장

### 2. **고객(Customer) 관리**
- 고객 등록 시 기본 크레딧 및 요금제 할당
- 고객에게 요금제 변경 및 재할당 가능

### 3. **기능 사용 처리**
- 기능 사용 시 크레딧 차감
- 기능별 사용 제한(문자수 / 월 횟수) 검증
- 기능 사용 이력 저장
- 크레딧 부족 / 초과 사용 시 비즈니스 예외 처리

### 4. **사용 통계 조회**
- 기능별 사용량 및 크레딧 사용량 조회 (기간 필터 가능)

## 📦 프로젝트 시작 방법

### 🔹 사전 준비

- Docker, Docker Compose 설치 필요

### 🔹 시작

```bash
docker-compose up --build
```

- 서버: `http://localhost:8080`
- MariaDB: `localhost:13306`

| 항목     | 값          |
| -------- |------------|
| DB Name  | teamreboot |
| Username | user       |
| Password | pass       |

---
## 🔬 테스트

- 단위 테스트: 서비스 로직 및 예외 처리
- Mockito 기반 Mock Test 작성

```bash
./gradlew test
```

---

## 📁 디렉토리 구조 요조

```bash
src/main/java
├── api
├── entity
│   └── enums
├── repository
├── service
├── model
├── config
├── common
```

---

## 🧠 아키텍처 특징

- 도메인 중심 설계 (DDD 패턴 일부 적용)
- 테스트 원칙 가지고 가능성과 책임 분리
- RESTful API 구조 준수

---

## 🏁 기타

- Swagger UI: `/swagger-ui/index.html` (springdoc 적용 시)
- 로컬 DB 초기화:

```bash
docker-compose down -v
```
---

## 🤛 하고싶은 말

제가 알고있는 지식을 최대한 동원하여 최신 버전 기준으로 작성하였습니다.
잘 부탁드리겠습니다.
감사합니다.

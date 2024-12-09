# REST API 설계 및 문서화 가이드

## REST API 설계 도구

1**Swagger/OpenAPI** *(선택)*
- API 설계 및 문서화 도구.
- 협업이 아닌 개인 개발자 중심으로 Swagger 선택함.

---

## 코드 기반 자동 생성 방식 vs 수동 작성 방식

    - 참고 : Swagger(https://swagger.io/docs/specification/v3_0/basic-structure)

## MySQL vs MongoDB
MySQL은 Security와 쉽게 통합할 수 있으나,
향후 소셜 로그인(다양한 프로필 데이터 처리), 클라우드 기반 서비스,
비정형 데이터를 다루는 환경에서는 MongoDB가 유연하여 유리하다고 판단함.


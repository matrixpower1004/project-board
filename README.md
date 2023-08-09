# project-board
게시판 만들기 프로젝트. Java + Springboot와 관련 기술들을 공부한다.

## 개발 환경

- Intellij IDEA Ultimate 2023.1.5 - 
- Java 17
- Gradle 7.6.1
- Spring Boot 2.7.13

## 기술 세부 스택

### Spring Boot

- Spring Boot Actuator
- Spring Web
- Spring Data JPA
- Rest Repositories
- Rest Repositories HAL Explorer
- Thymeleaf
- Spring Security
- H2 Database
- MySQL Driver
- Lombok
- Spring Boot DevTools
- Spring Configuration Processor

### 그 외

- QueryDSL 5.0.0
- Bootstrap 5.3.1

## Reference
- Usecase diagram : 
  https://github.com/matrixpower1004/project-board/blob/main/document/use-case.svg
- API Endpoint : https://docs.google.com/spreadsheets/d/15-0zZ_vmxEgqW62uNUNb9chC4As2JHcaNMpm9dv-5qg/edit?usp=sharing

## Study note

- Thymeleaf3 decoupled template logic :
  - reference docs : https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#decoupled-template-logic
  - reference code : https://gist.github.com/djkeh/6e1d557ce8c466135b1541d342b1c25c
 
- start.spring.io 사이트를 이용하여 Thymeleaf dependency 추가시 주의사항 :
  - thymeleaf-extras-springsecurity6 디펜던시는 Spring Security와 Thymeleaf를 동시에 선택해야 목록에 나타난다.   
```yaml
implementation 'org.thymeleaf.extras:thymeleaf-extras-springsecurity6'
```
- Spring Security의 기본 form login 기능을 이용하면 `DefaultLoginPageGeneratingFilter` 클래스에서 화면을 만들어준다.
- Spring boot와 Spring Security를 연동하여 사용할 때는 `@EnableWebSecurity` 애너테이션은 생략해도 된다. Auto-Configuration에 포함되어 있다.
- SecurityConfig의 설정을 Test에도 적용하려면 `@Import(SecurityConfig.class)` 애너테이션을 클래스 레벨로 붙여준다.
  - 기본적으로 `@WebMvcTest` 애너테이션을 붙인 테스트는 Spring Security와 MockMvc도 자동으로 구성한다. 
  - reference : https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/test/autoconfigure/web/servlet/WebMvcTest.html
```java
@DisplayName("View 컨트롤러 - 게시글")
@Import(SecurityConfig.class)
@WebMvcTest(ArticleController.class)
class ArticleControllerTest {
  ...
}
```
# project-board
게시판 만들기 프로젝트. Java + Springboot와 관련 기술들을 공부한다.

## 개발 환경

- Intellij IDEA Ultimate 2023.1.5 - 2023.2
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
  ![Image](https://user-images.githubusercontent.com/104916288/259623671-ee89dab7-54f3-4ecd-956e-c2fe7165070a.svg)

- ERD :
  ![Image](https://user-images.githubusercontent.com/104916288/259964098-1b3eef4d-5fe8-4c36-bdea-c95b9a0e846d.svg)
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
- enum은 자바 클래스의 예약어라 팩키지명으로 사용하면 어떤 문제를 일으킬지 예측할 수 없다. Java 예약어는 팩키지명이나 변수명으로 사용하지 말자. IntelliJ에서는 예약어를 팩키지명으로 사용하면 미리 경고를 해준다.
- `@GetMapping`에 여러 주소의 다중 매핑이 가능하다. `value = { "/urlA", "/urlB" }` 의 형태로 작성해준다.
```java
@GetMapping(value = {"", "/"})
```
- `@WebMvcTest()` 어노테이션의 ( ) 내부에 테스트 할 대상을 명시해 줌으로써 Bean scanning을 최소화 할 수 있다.
```java
@WebMvcTest(MainController.class)
```
- MySQL에서 FK 제약조건이 걸려 있는 table은 일반적인 방법으로 drop 할 수 없기 때문에 `set FOREIGN_KEY_CHECKS = 0;`으로 제약 조건에 관계없이 table을 drop 할 수 있도록 설정하고, table drop 후 다시 `set FOREIGN_KEY_CHECKS = 1;`로 원상복구 해준다.
```mysql
set FOREIGN_KEY_CHECKS = 0;
drop table [table_name];
set FOREIGN_KEY_CHECKS = 1;
```
- underscore(_)는 JPA Property Expressions에서 property 탐색을 위한 traversal points(순회 지점)을 수동으로 설정하는 예약어로 일반적인 경우에 사용해서는 안 된다. 아래와 같이 댓글 목록을 가져오는데 게시글의 id를 조회 조건으로 이용하거나, Pserson 객체의 목록을 조회하는데 Adderss 객체의 ZipCode property를 조회 조건으로 사용하는 등 property가 계층적(트리 구조) 연관관계를 가질 때 사용할 수 있다.
  - 공식 문서에서는 Property Expressions에 스네이크 표기법이 아닌 자바에서 사용하는 카멜표기법을 사용할 것을 추천하고 있다.
  - reference : https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-property-expressions
```java
List<Person> findByAddress_ZipCode(ZipCode zipCode);
List<ArticleComment> findByArticle_Id(Long articleId);
```
- `Page` interface의 `map` 메서드를 이용하여 DB에서 가져온 Entity Object를 간편하게 Dto로 변환할 수 있다.
```java
return articleRepository.findAll(pageable).map(ArticleDto::from);
```
- JPA method name에 `Containing`을 붙이면 부분 검색이 가능하다. 하지만 like `'%[keyword]%'`와 같이 % wild card가 앞뒤로 붙는 형태이므로 인덱스를 사용하지 못하여 성능 관련 이슈가 발생할 수 있다.
```java
Page<Article> findByContentContaining(String content, Pageable pageable);
```
- 보통 연관 관계에 있는 엔티티를 저장하기 위해 `findById()` 메서드를 사용하여 Entity를 불러오는데, 이는 불필요한 select문이 실행되는 결과를 낳는다. 이를 개선하기 위해 `getReferenceById()` 메서드를 사용하면 엔티티 생성에 프록시 객체를 넣어 주기 때문에 블필요한 select문이 실행되지 않는다.
- 주의사항:  `findById()` 메서드는 Optional로 반환되기 때문에 예외 처리에 유리하지만, `getReferenceById()` 는 프록시 객체를 가져오기 때문에 예외 발생 위험이 있다.

- `getOne()` 메서드는 Spring Boot 2.7에서 deprecated 되었고, 현재는 `getReferenceById()` 메서드 사용을 권장하고 있다. 
- reference : https://docs.spring.io/spring-data/jpa/docs/current/api/deprecated-list.html
```java
Article article = articleRepository.getReferenceById(dto.id());
```
- `@MockBean` 애너테이션은 필드 주입만 가능하고 생성자 주입은 불가능하다. `@AutoWired` 애너테이션이 METHOD, PARAMETER 에서 사용할 수 있도록 구현이 되어 있는 반면, `@MockBean` 애너테이션은 TYPE, FIELD에서 사용할 수 있도록 구현이 되어 있다.
```java
// AutoWired 애너테이션
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {

// MockBean 애너테이션
@Target({ ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(MockBeans.class)
public @interface MockBean {
```
- Mockito의 `any()` 메서드는 일종의 argument matcher로서 filed의 일부만 matrcher 할 수 없다. match 대상 field에 `eq()`를 사용하여 모든 method field가 argument matcher로 이루어져 있어야 에러가 나지 않는다.
```java
given(articleService.searchArticles(eq(null), eq(null), any(Pageable.class))).willReturn(Page.empty());
```

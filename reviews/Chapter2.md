# Chapter2

## 1. 유닛테스트

- 하나의 단위에 집중해서 수행하는 테스트

  <br/>

## 2. JUnit

- 테스트를 위한 프레임워크

- @Test 애노테이션을 붙혀서 테스트할 기능을 명시

  - public void이며 파라미터가 없어야 함

- @Before 애노테이션을 통해 테스트 이전에 일괄적으로 수행될 작업 명시

  <br/>

## 3. TDD

- 테스트 주도 개발

- 테스트를 작성하고 테스트를 통과하기 위한 코드를 작성한다

  <br/>

## 4. 스프링 테스트 컨텍스트 프레임워크

- 테스트를 위한 어플리케이션 컨텍스트를 애노테이션을 통해 접근할 수 있다
- 하나의 어플리케이션 컨텍스트를 생성해 테스트 오브젝트에 주입하기에 속도 이점이 있다
- 스프링 어플리케이션 컨텍스트는 초기화 할 때 자기 자신도 빈으로 등록해 DI가 가능하다

```java
@Runwith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="applicationContext.xml")
....
@Autowired ApplicationContext context; //DI
```

<br/>
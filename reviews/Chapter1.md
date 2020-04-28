Chapter1

==

**1. 리팩토링**

- 기존 코드를 외부 동작방식에 변화 없이 내부 구조를 변경해서 재구성하는 작업



**2. 객체지향 설계 원칙**

​	**2.1 SRP 단일 책임 원칙**

- 하나의 클래스는 하나의 목적만을 가지고 설계해야 한다

​	**2.2 OCP 개방 폐쇄 원칙**

- 확장에는 열려있고 변경에는 닫혀있어야 한다

​	**2.3 LSP 리스코프 치환 원칙**

- 자식클래스는 부모클래스의 기능을 모두 사용할 수 있어야 한다

​	**2.4 ISP 인터페이스 분리 원칙**

- 자신에게 필요하지 않은 인터페이스는 구현하지 않는다

​	**2.5 DIP 의존관계 역전 원칙**

- 추상적 구현에 의존해야 한다



**3.IOC 제어의 역전**

- 프로그램의 제어 흐름 구조가 뒤바끼는 것

- 제어의 역전에서는 오브젝트가 자신이 사용할 오브젝트를 스스로 선택하지도, 생성하지도 않는다

- 또 자신도 어떻게 만들어지고 어디서 사용되는지를 알 수 없다

- 모든 제어 권한을 자신이 아닌 다른 대상에게 위임하기 때문이다



**4.스프링의 IOC**

​	**4.1 Bean**

- 제어권을 가지고 직접 만들고 관계를 부여하는 오브젝트

​	**4.2 빈 팩토리**

- 빈의 생성과 관계설정 같은 제어를 담당하는 IoC 오브젝트

- 빈을 생성하고 관계를 설정하는 IoC의 기본 기능에 초점을 둔다

​	**4.3 애플리케이션 컨텍스트**

- 빈 팩토리와 같은 의미

- 애플리케이션 전반에 걸쳐 모든 구성요소의 제어 작업을 담당하는 IoC 엔진이라는 의미가 더 강하다

- 싱글톤으로 빈 오브젝트를 생성하고 관리한다



**5. 동일성과 동등성**

​	**5.1 동일성**

- 두 개의 오브젝트가 동일하다면 하나의 오브젝트만 존재하고 두 개의 오브젝트 레퍼런스 변수를 갖는다

​	**5.2 동등성**

- 두 개의 오브젝트가 동일하지는 않지만 동등한 경우에는 두 개의 각기 다른 오브젝트가 메모리상에 존재한다



**6. DI 의존관계 주입**

- 클래스 모델이나 코드에는 런타임 시점의 의존관계가 드러나지 않는다. 그러기 위해서는 인터페이스에만 의존하고 있어야한다
- 런타임 시점의 의존관계를 컨테이너나 팩토리 같은 제3의 존재(애플리케이션 컨텍스트)가 결정한다
- 의존관계는 사용할 오브젝트에 대한 레퍼런스를 외부에서 제공해줌으로 써 만들어진다
- 의존관계 주입에서는 자신이 스프링의 빈이여야 한다
- 컨테이너가 오브젝트를 주입해 주려면 자신에 대한 생성과 초기화 권한을 가지고 있어야 하기 때문이다
- 인터페이스를 통해 결합도가 낮은 코드를 만드는 장점이 있다(OOP 5원칙 : DIP)

​	**6.1 의존관계 주입 방법**

​		**6.1.1 생성자를 통한 주입**

```java
public class UserDao() {
	private ConnectionMaker connectionMaker;
  public UserDao(ConnectionMaker connectionMaker){
    this.connectionMaker = connectionMaker;
  }
}
```

​		**6.1.2 수정자를 통한 주입**

```java
public class UserDao() {
	private ConnectionMaker connectionMaker;
  public void setConnectionMaker(ConnectionMaker connectionMaker){
    this.connectionMaker = connectionMaker;
  }
}
```

​		**6.1.3 일반 메소드를 통한 주입**

```java
@Bean
public UserDao userDao(){
  UserDao userDao = new UserDao();
  userDao.setConnectionMaker(connectionMaker());
  return userDao;
}
```



**7. DL 의존관계 검색**

- 자신이 필요로 하는 의존 오브젝트를 능동적으로 찾는다
- 자신이 어떤 클래스의 오브젝트를 이용할지는 결정하지 않는다
- 의존관계 검색에서는 검색하는 오브젝트는 자신이 스프링의 빈일 필요가 없다

```java
public UserDao() {
	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
	this.connectionMaker = context.getBean("connectionMaker",ConnectionMaker.class);
}
```


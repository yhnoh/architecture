### 1. 클린 아키텍처란?
---

- 각 계층의 결합도를 낮추며 관심사를 분리하기위한 가장 이상적인 아키텍처 구조이다.
- 관심사를 분리하지 않거나 서로의 계층이 결합도가 높으면 어떤 문제가 발생할까?
  - 외부 인프라 관련 코드가 비지니스 코드에 많은 영향을 미칠 수 있다.
  - 반대로 비지니스 코드의 수정이 외부 인프라 관련 코드에 영향을 미칠 수 있다.
  - 위의 말을 한줄로 표현하면 코드 수정하기 참 어려운 구조라는 의미이다.
  - 서로가 서로에게 영향을 주고 있기 때문에 무엇을 수정 하든 쉽지 않은 길이 될 수 있다. 
> 클린 아키텍처는 모든 아키텍처에 적용될 수 있으며 프로그램의 응답과 요청 코드를 유지보수 용이하게 설명한 추상적인 아키텍처이다.

### 2. 내가 겪은 코드들의 문제점
---

#### 2.1. 명확하지 않은 클래스명으로 인한 클래스 역할의 애매함

- 아래 코드를 보면 각 계층 분리는 되어 있지만 해당 클래스 명을 통해서 정확히 어떤 역할을 하는지 알 수 없다.
  ```java
  // 회원 관련 서비스인건 알겠는데 도대체 뭘 하는 서비스지?
  public class MemberService {

      private final MemberJpaRepository memberJpaRepository;
      private final MemberMapper memberMapper;

      //메서드들....
  } 
  ```
- 명확하지 않은 클래스명은 해당 객체가 정확히 어떤 역할을 하는지 알 수 없다.
- 어떤 역할인지 알 수 없으니 명확하지 않은 코드들이 덕지덕지 붙어나갈 가능성이 높다.
  - 예를 들어 MemberService내에서 갑자기 Member 객체가 아닌 다른 객체에 대한 행위 코드가 추가될 수 있다.
- 서비스 코드 뿐만 아니라 값을 리턴하는 값 객체들도 정확히 어떤 역할을 하는지 이해하기 쉽지 않다.

#### 2.2. 계층간의 강한 결합도로 인한 사이드 이펙트

> 의존성 역전 원칙 (Dependency Inversion Principle) <br/>
> 고수준 모듈이 저수준 모듈의 구현에 의존해서는 안된다. 저수준 모듈이 정의해둔 추상 타입에 의존을 해야한다.
- 해당 내용은 객체 지향 원칙 중 하나의 원칙이지만 이를 계층에 빗대어 한번 알아보자.
  ```java
  public class MemberService {

      private final MemberJpaRepository memberJpaRepository;
  }
  ```
- MemberService가 Jpa라는 특정 기술에 의존하고 있다. 이는 곧 Jpa가 아닌 다른 인프라 관련 코드로 변경해야할 경우 해당 서비스를 전부 수정해야한다는 의미이다.
- 또한 Jpa내에서 @Entity 관련 객체의 속성 변경이나 구조의 변경이 모든 서비스 객체에 영향을 줄 수 있다.
- 서비스 코드가 외부에 노출되는 경우에 대해서도 한번 생각해보자.
  ```java
  public class MemberService {

      private final MemberJpaRepository memberJpaRepository;
      
      public List<MemberDTO> getMembers(){
        //...
      }
  }

  public class MemberController {
      private fianl MemberService memberService;
      @GetMapping("/members")
      public ResponseEntity<MemberDTO> getMembers() {
          return ResponseEntity.ok(memberService.getMembers());
      }
  }
  ```
- 서비스에서 리턴된 MemberDTO의 객체 구조, 필드명의 변경이 컨트롤러의 응답 바디의 내용을 변경할수 있다.
- 이로 인해서 /members 엔드포인트를 응답 받는 모든 서비스들에게 영향을 줄 수 있다.

#### 2.3. 계층간의 강한 결합도로 인한 인프라 관련 코드 제어의 힘듬

- 계층간의 강한 결합도로 인해서 인프라 관련 코드 및 서비스 관련 코드가 전부 섞이게 된다.
- 섞이게 되는 것 까지는 상관 없지만 이로 인해서 인프라 관련 코드를 제어하기 힘들다는 문제가 발생한다.
- 가장 대표적으로 JPA의 지연, 즉시 로딩 문제가 있다. 내가 원하지 않은 상황 또는 내가 원하는 상황에 로딩이 되냐 안되냐의 문제로 비지니스 관련 코드를 작성하는 시간 자체를 너무 허비하는 경우가 많이 발생한다.
  - 이는 수정에 관련된 상황에서도 로딩이 되어야하는지 안되어야하는지를 계속적으로 체크해야한다.
- API 통신 관련해서도 몇가지 문제가 발생할 수 있다.
  - 객체의 필드명을 정의하고 난다음에 해당 객체를 어떻게 직렬화, 역직렬화 해야하는지 내용이 추가가 될 수 있는데, 이 코드 자체가 비지니스 코드를 작성하는 개발자에게 큰 혼동을 줄 수 있다.
- Redis도 마찬가지이다.
  - 현재 많은 클래스에 java.io.Serializable 인터페이스를 implements 하고 있는데 이는 주로 Redis에 값을 저장하거나 가지고 올때 사용한다.
  - Serializable 인터페이스로 인해서 서비스 코드 내에서 어떤 문제가 발생할지도 예측할 수 없으며, 무의미하게 해당 인터페이스를 implements 하는 경우가 많다. 이는 비지니스 코드를 작성하는 개발자에게 많은 혼동을 줄 수 있다.




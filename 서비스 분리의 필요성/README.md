### 1. 서비스란?
---

- 비지니스 로직을 수행하는 역할을 하며 애플리케이션 내부에서 수행해야하는 거의 모든 작업을 의미한다.
> 비지니스 로직을 단순히 CRUD의 의미로 받아들이면 안되고, 회원가입, 회원 정보 조회와 같은 사용자의 하나의 행위를 담아낸 로직이라고 이해하는 것이 좋다. <br/>
> 때문에 단순 CURD보다 특정 상황에 맞는 더 복잡한 로직들을 구현해야하는 경우가 많다. <br/>
> 이렇게 해야 사용자 행위와 비슷한 네이밍이 클래스 정보에 담길 수 있다.

 

### 2. 서비스의 역할
---

1. 애플리케이션 내부 사용자가 필요한 데이터를 리턴 ex) DTO, Domain… (출력을 반환)
   - 외부에게 노출되는 데이터가 아니므로 오해해서는 안된다. ex) Response
   - 때문에 외부에게 필요한 데이터를 응답하기 위해서는 따로 매핑을 진행해야한다. (논의 필요)
2. 데이터를 조회, 삽입, 수정, 삭제를 진행하기 위해서 필요한 모든 행위를 진행한다.
   - 해당 행위는 비지니스 로직을 위한 행위여야 한다.
3. 입력 모델에 대한 유효성 검사
   - 해당 행위는 서비스 내에서 진행할 수 있고 입력 모델 내에서도 진행할 수 있다.
4. 비지니스 로직에 대한 유효성 검사
   - 비지니스 로직에 대한 유효성 검사는 서비스 내에서 진행하는 것이 좋다.

 

### 3. 서비스 분리의 필요성
---

- 서비스를 분리하지 않을 경우 어떤 일이 발생하는지 알아보자.

#### 3.1. 하나의 서비스에 많은 코드가 생성된다.
- 하나의 서비스에 많은 코드가 생성된다.

```java
@Service
@RequiredArgsConstructor
public class MemberService{
    private final MemberRepository memberRepository;

    //회원 가입
    public void join(Member member){
    //...
    }

    //회원 탈퇴
    public void deleteMember(String memberId){
    //...
    }

    //회원 수정
    public void modifyMember(ModifyMember member){
    //...
    }

    //조회 메서드....    
}
```
- 하나의 서비스에 많은 내용이 담겨짐으로 인해서 사용하는 입장에서 어떤걸 사용해야하는지 알 수 없다.
- 코드양이 많아 지기 때문에 해당 로직을 파악하기가 힘들어진다.
  - 파악이 힘들어진다는 것은 수정자체가 어려워진다.
- 다른 개발자가 회원 관련 추가 기능을 만들 때, MemberService 에 지속적으로 코드를 추가할 확률이 높다.
  > [깨진 창문 이론](https://namu.wiki/w/%EA%B9%A8%EC%A7%84%20%EC%9C%A0%EB%A6%AC%EC%B0%BD%20%EC%9D%B4%EB%A1%A0) : 사소한 무질서를 방치했다간 나중엔 지역 전체로 확산될 가능성이 높다는 의미를 담고 있다. <br/>
  > 코드 작업을 깨진 창문 이론에 빗대어 설명하면 다음과 같은 의미가 된다. <br/>
  > - 품질이 낮은 코드에서 작업할 때 더 낮은 품질의 코드를 추가하기 쉽다. <br/>
  > - 코딩 규칙을 많이 어긴 코드에서 작업할 때 또 다른 규칙을 어기기가 쉽다. <br/>
  > - 지름길을 많이 사용한 코드에서 작업할 때 또 다른 지름길을 추가하기 쉽다.
  
#### 3.2. 서비스가 분리되어 있지 않으면 불필요한 의존성이 많아진다.

- 객체지향언어에서는 응집도를 높이라는 말이 있다.
  - 응집도란 하나의 모듈이 하나의 기능을 수행하는 요소들간의 연관도를 이야기한다.
  - 때문에 응집도가 낮으면 이해하기 힘들고, 유지보수하기 힘든 코드가 된다.
- 클래스레벨로 넘어가서 모듈을 클래스, 기능을 클래스의 메서드라고 먼저 생각해보고 아래의 코드를 보자.

```java
public class ABCD {

    private int a;
    private int b;
    private int c;
    private int d;

    public int ab() {
        return a + b;
    }

    public int cd() {
        return c + d;
    }
}
```
  - `ab()` 메서드는 `int a, int b`의 변수만 활용하고 있고, `cd()` 메서드는 `int c, int d` 변수만 활용하고 있다.
  - 위와 같은 코드가 바로 응집도가 낮은 클래스이다.
  - 응집도를 높이기 위해서는 아래와 같이 필요한 변수만 사용하는 메서드를 사용하도록 클래스를 분리해야한다.
```java
public class AB {

    private int a;
    private int b;

    public int ab() {
        return a + b;
    }
}

public class CD {
    private int c;
    private int d;
    public int cd() {
        return c + d;
    }

}
```
- `MemberService`라는 하나의 서비스에서 불필요한 의존성을 많이 추가할 수록 코드를 이해하기 힘들고, 유지보수가 힘든 코드가 된다.
- 때문에 기능별 또는 팀간의 회의를 통해서 서비스를 분리해야한다.

> 도메인 주도 설계 철저 입문, 나루세 마사노부, p133-141

#### 3.3. 서비스가 분리되어있지 않아 테스크 코드 작성이 어려워진다.

- 하나의 서비스에 기능에 대한 모든 코드가 담겨져 있다면 유닛 테스트 코드 작성 자체가 어려워진다.
- 또한 불필요한 의존성이 많아 어떤 의존성을 Mock으로 만들어 테스트해야할지 헷갈리기 시작한다.
- 만약 Mock으로 만들어서 테스트를 진행한다 하여도 테스트 코드 내에서 어떤걸 목적으로 테스트하는지 불분명하다.
- 뿐만아니라 프로덕션 코드의 패키지 및 이름을 따와서 테스트를 작성하기 때문에 테스토 코드 자체 내에서도 코드양이 많아진다.
  - 후에 해당 테스트 코드가 무엇인지 파악하기가 힘들다.
> 테스트 코드 작성자체가 어렵다는 말은 해당 서비스의 구조 자체가 잘못되었다는 의미이기도 하다. 
- 위와 같은 이유로 인해서 테스트 코드를 작성하기 힘들어지면 누군가는 해당 테스트 코드를 작성하지 않는 경우가 발생할 수 있다.
  - 왜냐하면 테스트코드는 강제성이 없기 때문이다.
  - 이는 즉, 변경에 취약한 서비스가 만들어지기 쉽다.



### 4. 어떤 식으로 서비스를 분리할 것인가?
---

#### 4.1. 조회 서비스와 수정, 삭제, 삽입 서비스를 분리하자.

- 조회 서비스와 나머지 기능을 분리하는 이유가 뭘까?
  - ***해당 클래스가 어떤 기능인지 명확해진다.***
    - `MemberService`라는 클래스를 보면 어떤 역할을 하는지 명확히 이해가 가능한가?
      - 너무 포괄적인 의미가 담겨있음으로 인해서 어떤 역할을 하는지 이해하기가 힘들다.
      - 물론 작은 프로젝트에서는 크게 의미가 없을수는 있지만 프로젝트 규모가 크다면 점점 이해하기가 힘들어진다.
    - `GetMemberService`라고 클래스를 만들면 `Member`라는 객체를 가져오는 역할이 명확한 클래스 네이밍을 가진다.
    - 여기서 더 고도화를 시킨다면 Member를 가져오는 서비스 MemberDTO를 가져오는 서비스와 같은 역할을 가진 클래스를 만들 수 있다.
      ```java
      @RequiredArgsConstructor
      @Service
      @Transactional(readOnly = true)
      class GetAreaService implements GetAreaQuery {
          // Area를 가져오는 Service
      }

      @Service
      @RequiredArgsConstructor
      @Transactional(readOnly = true)
      class GetAreaBaseDTOService implements GetAreaBaseDTOQuery {
          // AreaBaseDTO를 가져오는 Service
      }
      ```
      - 조회 서비스에서 특정 조회 서비스를 따로 클래스로 분리함으로 인해서 명확한 책임을 가진 클래스가 된다.
        - 위에 코드에서 `~Query` 인터페이스를 만들고 있는데 이는 CQRS 패턴을 참고한 것이다.
        > [CQRS 패턴](https://learn.microsoft.com/ko-kr/azure/architecture/patterns/cqrs)<br/>
        > CQRS는 데이터 저장소에 대한 읽기 및 업데이트 작업을 구분하는 패턴인 명령과 쿼리의 역할 분리를 의미합니다.
  - ***응집도가 올라갈 가능성이 높다.***
    - 조회와 나머지 기능을 분리하면 분리할 수록 필요한 의존성만 넣는것이 가능하다.
    - 기본적으로 조회관련 서비스는 단순히 entity만 반환하지 않고, 다양한 출력 모델을 가진다.
      - 도메인, 응답 모델, DTO... 
    - `MemberService`에 Member와 관련된 모든 메서드가 하나에 있다고 생각해보자.    
      - 회원가입과 관련된 메서드에는 A,B
      - 회원수정과 관련된 메서드에는 B,C 
      - DTO 출력 관련된 메서드는 특정 매핑 의존성
      - 엔티티 관련된 내용에는 Repository 관련된 내용
      - 등등....
    - 매우 응집도가 낮고, 의존성이 여기저기 퍼지게 됨으로 인해서 파악하기 힘들어지는 코드가 된다.
    - 때문에 일단 가장 간단하게 리팩토링 할 수 있는 조회 서비스 부터 나눠보는 것이 좋다.
  - ***`@Transactional`의 readonly 기능을 활성화 시키기 쉽다.***
    - 조회와 나머지 기능을 나눔으로 인해서 클래스 레벨에서 `@Transactional` 읽기 전용과 나머지를 활성화 시키는 것이 가능하다.
    - 조회에는 readonly 기능을 활성화 시키고, 나머지 기능에는 readonly 기능을 비활성화 시킴으로 인해서 성능을 향상시킬 수 있다.
      > 스프링에서 JPA를 사용하고 있을 경우, readonly 기능을 활성화 시킨 서비스를 사용하면 강제로 플러시를 호출하지 않는 이상 플러시가 일어나지 않는다. <br/>
      > 트랜잭션을 커밋하더라도 영속성 컨텍스트가 플러시 되지 않기 때문에 엔티티의 등록, 수정, 삭제가 동작하지 않아 영속성 컨텍스트는 변경 감지를 위한 스냅샷을 보관하지 않으므로 성능이 향상된다.

#### 4.2. 매핑 서비스를 만들자.

- 매핑서비스를 만들어야 되는 이유가 뭘까?
- 매핑서비스를 만들지 않으면 테스트 코드의 작성에 대한 목적이 점점 모호해지면 작성이 힘들어진다.
```java
public class MemberService {
  private final MemberJpaRepository memberJpaRepository;
  public MemberEntity join(MemberJoinRequest memberJoinRequest) {
    
    Optional<MemberEntity> findMemberEntity = memberJpaRepository.findById(memberJoinRequest.getId());
    
    if(findMemberEntity.isPresent()){
      throw new IllegalArguementException("회원 아이디가 중복되어 있습니다.");
    }

    
    MemberEntity memberEntity = new MemberEntity();      
    //... memberJoinRequest를 이용해 MemberEntity 값 셋팅

    return memberJapRepository.save(memberEntity);
  }
}
```
  - `join` 테스트 코드를 작성하다 보면 회원 중복 체크에 대한 로직과 `MemberEntity`의 상태를 체크하는 로직을 함께 테스트 코드로 작성할 수 밖에 없어진다.
  - 이게 무엇이 문제일까?
    - 회원가입로직을 확인할때 객체의 상태를 확인하고 싶은걸까?
    - 아니면 회원가입로직 자체에 대한 확인을 하고 싶은걸까?
    - 이 두가지를 같이하고 싶은걸까?
  - 테스트의 목적 자체가 조금씩 모호해지면서 회원가입 메서드가 현재는 작아서 그렇지 만약 조금 복잡한 로직을 테스트하는 경우 테스트 자체가 힘들어질 수 있다.
- 때문에 매핑 서비스를 만들어 따로 상태와 로직에 대한 테스트를 분리해보자.
```java
public class MemberService {
  private final MemberJpaRepository memberJpaRepository;
  //매핑 서비스 제작
  private final MemberMapper memberMapper;

  public MemberEntity join(MemberJoinRequest memberJoinRequest) {
    
    Optional<MemberEntity> findMemberEntity = memberJpaRepository.findById(memberJoinRequest.getId());
    
    if(findMemberEntity.isPresent()){
      throw new IllegalArguementException("회원 아이디가 중복되어 있습니다.");
    }

    //... 매핑 서비스를 이용한 MemberEntity 값 셋팅
    MemberEntity memberEntity = memberMapper.toMemberEntity(memberJoinRequest);      
    

    return memberJapRepository.save(memberEntity);
  }
}
```
- 매핑 서비스를 분리함으로 인해서 테스트 코드의 목적이 명확해진다.
  - 객체의 상태를 확인하는 테스트는 매핑 서비스에서 진행하면 된다.
  - 회원가입 로직에 대한 테스트는 회원가입 서비스에서 진행하면 된다.
- 단순히 테스트 코드의 목적을 위해서만을 위해 매핑 서비스를 분리하는 것이 아니다.
  - 매핑은 많은 곳에서 활용가능하기 때문에 재사용성이 증가한다.

#### 4.3. 수정, 삭제, 삽입을 각 클래스 별로 만들자.
- 객체지향 5원칙(SOLID)에서 단일 책임 원칙에 대해서 들어봤을 것이다.
- 책임이 무엇이라고 생각하는가?
  - 책임은 바로 변화이다.
  - 즉, 단일책임원칙은 하나의 클래스안에 하나의 변화만 작성하라는 의미이기도 하다.
- 그렇다면 변화는 어디에서 가장 많이 일어나는가?
  - 바로 수정, 삭제, 삽입 로직에서 많이 일어난다.
- 때문에 단일 책임 원칙에 가장 이상론적인 접근 방법은 수정, 삭제, 삽입과 관련된 비지니스 로직 하나당 하나의 클래스를 만들어야한다.
  - 이렇게 함으로 인해서 클래스 자체에 대한 네이밍이 자유로워 지며 해당 클래스가 어떤 역할을 하는지 파악하기 쉬워진다.
  - 이렇게 작업함으로 인해서 변경에 대한 용이함, 테스트 코드 작성의 간단함, 응집도가 올라가며, 재사용성도 올라간다. 
- 물론 얼마나 나눌지는 팀간의 논의가 필요하다.
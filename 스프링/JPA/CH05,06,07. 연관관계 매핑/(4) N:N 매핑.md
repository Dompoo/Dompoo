# N:N 매핑

- N:N 매핑에서는 중간 테이블을 활용하여 1:N, N:1로 풀어내는 것이 best practice이다.
  - 따라서 이때 연관관계의 주인은 중간 테이블이 된다.
- 이때 각 1:N, N:1 관계는 필요에 따라 양방향/단방향으로 구성할 수 있다.

## 양쪽을 모두 양방향 연관관계로 구성

```java
@Entity
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}

@Entity
public class Member {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}

@Entity
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @OneToMany(mappedBy = "product")
    private List<Order> orders = new ArrayList<>();
}
```

## 한쪽만 양방향 연관관계로 구성

```java
@Entity
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}

@Entity
public class Member {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}

@Entity
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
```

## 중간 엔티티의 비즈니스 로직

- 중간 엔티티가 연관관계 주인이 되며, 이 엔티티에게 비즈니스 로직을 부여하여 더 객체지향적인 코드를 작성할 수 있다.

```java
public static Order order(Member member, Product product) {
    Order order = new Order(member, product);
    member.setOrder(order);
    product.setProduct(product);
    return order;
}
```

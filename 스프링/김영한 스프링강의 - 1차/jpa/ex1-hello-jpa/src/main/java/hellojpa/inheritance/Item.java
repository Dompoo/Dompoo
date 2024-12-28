package hellojpa.inheritance;

import hellojpa.BaseEntity;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) //조인테이블 전략 / 단일테이블 전략
@DiscriminatorColumn //단일테이블 전략에서는 자동생성, 운영상 항상 있는 것이 이득
public class Item extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;

    }

    public void setPrice(int price) {
        this.price = price;
    }
}

package hello.itemservice.repository.jpa;

import hello.itemservice.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * SpringDataJpa를 이용한 Repository
 */
public interface SpringDataJpaItemRepository extends JpaRepository<Item, Long> {
    //Like는 파라미티로 like형식을 따라야 한다. ( "%" + ~ + "%" )
    List<Item> findByItemNameLike(String name);

    List<Item> findByPriceLessThanEqual(Integer price);

    //쿼리메서드 (아래 메서드와 같은 기능 수행)
    List<Item> findByItemNameLikeAndPriceLessThanEqual(String name, Integer price);

    //쿼리 직접 실행
    @Query("select i from Item i where i.itemName like :itemName and i.price <= :price")
    List<Item> findItems(@Param("itemName") String itemName, @Param("price") Integer price);



}
